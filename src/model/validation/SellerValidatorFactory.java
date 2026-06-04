package model.validation;

import model.entities.Seller;

public class SellerValidatorFactory {

    public static Validator<Seller> createValidator() {
        return ReflectionValidatorFactory.buildChain(Seller.class);
    }
}
