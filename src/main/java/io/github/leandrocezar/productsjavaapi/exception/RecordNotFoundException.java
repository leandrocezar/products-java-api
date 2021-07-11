package io.github.leandrocezar.productsjavaapi.exception;

public class RecordNotFoundException extends Exception {

    private static final long serialVersionUID = 6814135791610976215L;

    public RecordNotFoundException() {
	super();
    }

    public RecordNotFoundException(String msg) {
	super(msg);
    }

    public RecordNotFoundException(String msg, Throwable cause) {
	super(msg, cause);
    }

}
