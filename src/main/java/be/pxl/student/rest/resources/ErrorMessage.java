package be.pxl.student.rest.resources;

public class ErrorMessage {
    private String message;

    public ErrorMessage(Exception exception) {
        this.message = exception.getMessage();
    }

    // TODO ??? klopt deze klasse???
}
