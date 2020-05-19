package leandoer.example.exception;

import java.util.Map;

public class ErrorResponse {
    Map<String, String> fields;

    public ErrorResponse(Map<String, String> fields) {
        this.fields = fields;
    }

    public Map<String, String> getFields() {
        return fields;
    }

    public void setFields(Map<String, String> fields) {
        this.fields = fields;
    }
}
