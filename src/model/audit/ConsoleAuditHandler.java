package model.audit;

public class ConsoleAuditHandler extends BaseAuditHandler{
 
 @Override
 public void handle(String operation, Object entity){
    System.out.println("[AUDIT] Operation: " + operation + " | Entity: " + entity);
    handleNext(operation, entity);
 }

       
    
}