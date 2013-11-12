package com.tl.common.exception;

@SuppressWarnings("serial")
public class ApplicationException extends RuntimeException {

	// �쳣����ؼ��ֱ��
	String key;
	Object[] values;

	public String getKey() {
		return key;
	}

	public ApplicationException(String message, String key) {
		super(message);
		this.key = key;
	}

	public ApplicationException() {
		super();
	}

	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApplicationException(String message) {
		super(message);
	}

	public Object[] getValues() {
		return values;
	}

	public ApplicationException(Throwable cause) {
		super(cause);
	}

	public ApplicationException(String message, String key, Object[] values) {
		super(message);
		this.key = key;
		this.values = new Object[] { values };
	}

	public ApplicationException(String message, String key, Object value) {
		super(message);
		this.key = key;
		this.values = new Object[] { value };
	}

}
