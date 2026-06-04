package model.validation;

import model.entities.Seller;

@ValidationStep(entity = Seller.class, order = 4)
public class SellerDepartmentValidator extends BaseValidator<Seller> {
    public void validate(Seller entity) {
        if (entity.getDepartment() == null) {
            throw new ValidationException("O vendedor deve ter um departamento");
        }
        validateNext(entity);
    }
}