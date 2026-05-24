package model.audit;

public interface AuditHandler{
    void setNext(AuditHandler next);
    void handle(String operation, Object entity);
}