
 public abstract class BaseValidator <T> implements Validator<T> {
    protected Validator<T> next;

    public void setNext(Validator<T> next){
        this.next = next;
    }
    
    protected void validateNext(T entity){
        if(next != null){
            next.validate(entity);
        }
    }                                                                                                                                     
}