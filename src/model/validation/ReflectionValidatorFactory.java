package model.validation;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class ReflectionValidatorFactory {

	private ReflectionValidatorFactory() {
	}

	@SuppressWarnings("unchecked")
	public static <T> Validator<T> buildChain(Class<T> entityClass) {
		List<Validator<T>> validators = ValidationRegistry.getValidatorClasses().stream()
			.map(clazz -> new ValidatorEntry(clazz, clazz.getAnnotation(ValidationStep.class)))
			.filter(entry -> entry.annotation != null && entry.annotation.entity().equals(entityClass))
			.sorted(Comparator.comparingInt(entry -> entry.annotation.order()))
			.map(entry -> (Validator<T>) instantiate(entry.clazz))
			.collect(Collectors.toList());

		for (int i = 0; i < validators.size() - 1; i++) {
			validators.get(i).setNext(validators.get(i + 1));
		}

		return validators.isEmpty() ? null : validators.get(0);
	}

	private static Validator<?> instantiate(Class<? extends Validator<?>> clazz) {
		try {
			return clazz.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new ValidationException("Failed to instantiate validator: " + clazz.getName());
		}
	}

	private static class ValidatorEntry {
		private final Class<? extends Validator<?>> clazz;
		private final ValidationStep annotation;

		private ValidatorEntry(Class<? extends Validator<?>> clazz, ValidationStep annotation) {
			this.clazz = clazz;
			this.annotation = annotation;
		}
	}
}
