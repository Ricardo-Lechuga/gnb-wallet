package es.ujaen.rlc00008.gnbwallet.data.source.net;

import es.ujaen.rlc00008.gnbwallet.data.source.net.responses.LoginResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Url;

/**
 * Created by Mobilers on 18/1/16.
 */
public interface GNBServices {

	@Headers("Content-Type: application/json")
	@GET
	Call<ResponseWrapper<LoginResponse>> userLogin(
			@Url String absoluteUrl,
			@Header("X-User-Token") String userToken,
			@Header("X-User-Locale") String locale
	);
}
