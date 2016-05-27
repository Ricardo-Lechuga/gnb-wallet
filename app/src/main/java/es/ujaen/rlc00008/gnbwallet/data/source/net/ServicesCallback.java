package es.ujaen.rlc00008.gnbwallet.data.source.net;

/**
 * Created by Ricardo Lechuga on 2/2/16.
 */
public abstract class ServicesCallback<ResponseData> {

	public abstract void resultOk(ResponseData response);

	public abstract void resultError(Meta meta);

	public abstract void genericException(Throwable t);
}
