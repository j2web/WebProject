package hk.com.mtr.pcis.exception;

public class ServiceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -1865513214490924849L;

	public ServiceNotFoundException(String message) {
		super(message);
	}

	public ServiceNotFoundException(String message, Exception e) {
		super(message, e);
	}

}
