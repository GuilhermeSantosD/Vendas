package model.validation;

import model.entities.Seller;

@ValidationStep(entity = Seller.class, order = 1)
public class SellerNameValidator extends BaseValidator<Seller> {
public void validate(Seller entity) {
    if (entity.getName() == null || entity.getName().trim().isEmpty()) {
        throw new ValidationException("Nome do vendedor não pode ser vazio");
    }
    validateNext(entity);
}

}
