package es.ujaen.rlc00008.gnbwallet.data.source.net;

import es.ujaen.rlc00008.gnbwallet.data.source.net.responses.LoginResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by Mobilers on 18/1/16.
 */
public interface GNBServices {

	@Headers({
			"Content-Type: application/json",
	})
	@GET("/pfc/ws/users/{login}/userLogin.json")
	Call<ResponseWrapper<LoginResponse>> userLogin(
			@Path("login") String userLogin
	);
}
