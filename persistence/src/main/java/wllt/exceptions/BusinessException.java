package wllt.exceptions;

/**
 *
 * @author Mara Corina
 */
public class BusinessException extends Exception {

    private String errorCode;

    public BusinessException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
