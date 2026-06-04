package model.validation;

import java.util.List;

public final class ValidationRegistry {

	private ValidationRegistry() {
	}

	public static List<Class<? extends Validator<?>>> getValidatorClasses() {
		return List.of(
			SellerNameValidator.class,
			SellerEmailValidator.class,
			SellerSalaryValidator.class,
			SellerDepartmentValidator.class,
			DepartmentNameValidator.class
		);
	}
}
