public class SellerNameValidator extends BaseValidator<Seller> {
public void validate(Seller entity) {
    if (entity.getName() == null || entity.getName().trim().isEmpty()) {
        throw new ValidationException("Nome do vendedor não pode ser vazio");
    }
    validateNext(entity);
}

}
