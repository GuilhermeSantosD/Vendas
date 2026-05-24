package model.validation;

import model.entities.Seller;

public class SellerValidatorFactory {

    public static Validator<Seller> createValidator() {

        SellerNameValidator name = new SellerNameValidator();
        SellerEmailValidator email = new SellerEmailValidator();
        SellerSalaryValidator salary = new SellerSalaryValidator();
        SellerDepartmentValidator department = new SellerDepartmentValidator();

        name.setNext(email);
        email.setNext(salary);
        salary.setNext(department);
        
        return name;
    }
}