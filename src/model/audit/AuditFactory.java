package model.audit;

public class AuditFactory {
    public static AuditHandler createAuditHandler(){
        ConsoleAuditHandler ConsoleAuditHandler = new ConsoleAuditHandler();
        FileAuditHandler FileAuditHandler = new FileAuditHandler();
        ConsoleAuditHandler.setNext(FileAuditHandler);
        return ConsoleAuditHandler;
    }
}