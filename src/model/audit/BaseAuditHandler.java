package model.audit;

public abstract class BaseAuditHandler implements AuditHandler{
    protected AuditHandler next;

    @Override
    public void setNext(AuditHandler next) {
        this.next = next;
    }

    protected void handleNext(String operation, Object entity) {
        if (next != null) {
            next.handle(operation, entity);
        }
    }
}
