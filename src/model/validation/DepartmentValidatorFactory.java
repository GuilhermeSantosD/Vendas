package model.validation;

import model.entities.Department;

public class DepartmentValidatorFactory {

    public static Validator<Department> createValidator() {
        
        return new DepartmentNameValidator();
    }
}