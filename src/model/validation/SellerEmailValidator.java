package model.validation;

import model.entities.Seller;

@ValidationStep(entity = Seller.class, order = 2)
public class SellerEmailValidator extends BaseValidator<Seller> {
    public void validate(Seller entity) {

        if (entity.getEmail() == null || !entity.getEmail().contains("@")) {
            throw new ValidationException("Email do vendedor inválido");
        }
        validateNext(entity);
    }
}