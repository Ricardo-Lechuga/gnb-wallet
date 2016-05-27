package es.ujaen.rlc00008.gnbwallet.data.source.net;

/**
 * Created by Ricardo Lechuga on 22/1/16.
 */
public class ResponseWrapper<ResponseData> {

	private Meta meta;
	private ResponseData response;

	public ResponseWrapper() {
	}

	///// GETTERS AND SETTERS /////
	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	public ResponseData getResponse() {
		return response;
	}

	public void setResponse(ResponseData response) {
		this.response = response;
	}
}