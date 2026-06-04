package model.validation;

import model.entities.Seller;

@ValidationStep(entity = Seller.class, order = 3)
public class SellerSalaryValidator extends BaseValidator<Seller> {
    public void validate(Seller entity) {
        if (entity.getBaseSalary() == null || entity.getBaseSalary() <= 0) {
            throw new ValidationException("O salário do vendedor deve ser positivo");
        }
        validateNext(entity);
    }
}