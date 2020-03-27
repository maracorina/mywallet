package wllt.exceptions;

/**
 *
 * @author Mara Corina
 */
public class ValidationException extends Exception {

    private String errorCode;

    public ValidationException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
