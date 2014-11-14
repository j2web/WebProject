package hk.com.mtr.pcis.exception;

public class ReportException extends RuntimeException {

	private static final long serialVersionUID = -7685400016604155931L;

	public ReportException(String message) {
		super(message);
	}

	public ReportException(String message, Exception e) {
		super(message, e);
	}
}
