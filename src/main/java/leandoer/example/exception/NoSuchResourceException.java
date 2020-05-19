package leandoer.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchResourceException extends RuntimeException {
    public static final String cause = "resource with given id has not been found";
    private String resource;
    private String id;

    public NoSuchResourceException(String resource, String id) {
        this.resource = resource;
        this.id = id;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
