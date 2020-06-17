package be.pxl.student.util.exception;

public class InvalidPaymentException extends Exception {
    // This is a checked exception because it extends from Exception.
    // This has to be caught somewhere in the code.

    // An unchecked exception doesn't need to be caught.
    public InvalidPaymentException(String message) {
        super(message);
    }
}
