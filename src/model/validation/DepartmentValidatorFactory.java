public class DepartmentValidatorFactory {

    public static Validator<Department> createValidator() {
        
        return new DepartmentNameValidator();
    }
}