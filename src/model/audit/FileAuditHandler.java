package model.audit;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileAuditHandler extends BaseAuditHandler {
    private static final String FILE_NAME = "audit.log";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void handle(String operation, Object entity) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            String timestamp = LocalDateTime.now().format(FORMATTER);
            String line = timestamp + " [AUDIT] Operation: " + operation + " | Entity: " + entity;
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("[AUDIT ERROR] Failed to write to audit log: " + e.getMessage());
        }
        handleNext(operation, entity);
    }
}