package hk.com.mtr.pcis.exception;

public class BusinessException extends Exception {

	private static final long serialVersionUID = 2065539895868742246L;

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Exception e) {
		super(message, e);
	}

}
