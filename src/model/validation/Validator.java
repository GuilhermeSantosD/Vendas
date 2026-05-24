package model.validation;

public interface Validator<T>{
    void setNext(Validator<T> next);
    void validate(T entity);
}