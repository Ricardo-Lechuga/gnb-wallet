package es.ujaen.rlc00008.gnbwallet.data.source.net;

/**
 * Created by Ricardo Lechuga on 22/1/16.
 */
public class Meta {

	public static final int CODE_OK = 0;

	private int code = -1;
	private String errorDetail;
	private String errorMessage;

	public Meta() {
	}

	///// GETTERS AND SETTERS /////
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getErrorDetail() {
		return errorDetail;
	}

	public void setErrorDetail(String errorDetail) {
		this.errorDetail = errorDetail;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
