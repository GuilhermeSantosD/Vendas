package model.validation;

import model.entities.Department;

@ValidationStep(entity = Department.class, order = 1)
public class DepartmentNameValidator extends BaseValidator<Department> {
    public void validate(Department entity) {
        if (entity.getName() == null || entity.getName().trim().isEmpty()) {
            throw new ValidationException("Nome do departamento não pode ser vazio");
        }
        validateNext(entity);
    }
}