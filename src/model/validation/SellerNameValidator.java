public class SellerNameValidator extends BaseValidator<Seller> {
public void validate(Seller entity) {
    if (entity.getName() == null || entity.getName().trim().isEmpty()) {
        throw new ValidationException("Seller name cannot be empty");
    }
    validateNext(entity);
}

}
