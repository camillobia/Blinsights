package CTI.BackEnd.service;

public class PythonEtlException extends RuntimeException {

    public PythonEtlException(String message) {
        super(message);
    }

    public PythonEtlException(String message, Throwable cause) {
        super(message, cause);
    }
}
