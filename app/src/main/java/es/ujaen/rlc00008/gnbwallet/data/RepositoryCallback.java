package es.ujaen.rlc00008.gnbwallet.data;

import es.ujaen.rlc00008.gnbwallet.data.source.net.Meta;

/**
 * Created by Ricardo on 2/2/16.
 */
public abstract class RepositoryCallback<ResponseData> {

	public abstract void resultOk(ResponseData response);

	public abstract void resultError(Meta meta);

	public abstract void genericException(Throwable t);
}
