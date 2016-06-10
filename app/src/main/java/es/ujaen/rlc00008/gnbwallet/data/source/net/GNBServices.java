package es.ujaen.rlc00008.gnbwallet.data.source.net;

import es.ujaen.rlc00008.gnbwallet.data.source.net.responses.CCVResponse;
import es.ujaen.rlc00008.gnbwallet.data.source.net.responses.CardTransactionsResponse;
import es.ujaen.rlc00008.gnbwallet.data.source.net.responses.ChallengeResponse;
import es.ujaen.rlc00008.gnbwallet.data.source.net.responses.GlobalPositionResponse;
import es.ujaen.rlc00008.gnbwallet.data.source.net.responses.LoginResponse;
import es.ujaen.rlc00008.gnbwallet.data.source.net.responses.PinResponse;
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

	@Headers({
			"Content-Type: application/json",
	})
	@GET("/pfc/ws/users/{login}/getGlobalPosition.json")
	Call<ResponseWrapper<GlobalPositionResponse>> getGlobalPosition(
			@Path("login") String userLogin
	);

	@Headers({
			"Content-Type: application/json",
	})
	@GET("/pfc/ws/users/{login}/generateChallenge.json")
	Call<ResponseWrapper<ChallengeResponse>> generateChallenge(
			@Path("login") String userLogin
	);

	@Headers({
			"Content-Type: application/json",
	})
	@GET("/pfc/ws/cards/{pan}/cardTransactions.json")
	Call<ResponseWrapper<CardTransactionsResponse>> getCardTransactions(
			@Path("pan") String cardPan
	);

	@Headers({
			"Content-Type: application/json",
	})
	@GET("/pfc/ws/cards/{pan}/getPin.json")
	Call<ResponseWrapper<PinResponse>> getPin(
			@Path("pan") String cardPan
	);

	@Headers({
			"Content-Type: application/json",
	})
	@GET("/pfc/ws/cards/{pan}/getCCV.json")
	Call<ResponseWrapper<CCVResponse>> getCCV(
			@Path("pan") String cardPan
	);

	@Headers({
			"Content-Type: application/json",
	})
	@GET("/pfc/ws/cards/{pan}/disableCard.json")
	Call<ResponseWrapper<Void>> disableCard(
			@Path("pan") String cardPan
	);

	@Headers({
			"Content-Type: application/json",
	})
	@GET("/pfc/ws/cards/{pan}/enableCard.json")
	Call<ResponseWrapper<Void>> enableCard(
			@Path("pan") String cardPan
	);

	@Headers({
			"Content-Type: application/json",
	})
	@GET("/pfc/ws/cards/{pan}/setFavorite.json")
	Call<ResponseWrapper<Void>> setFavorite(
			@Path("pan") String cardPan
	);

	@Headers({
			"Content-Type: application/json",
	})
	@GET("/pfc/ws/cards/{pan}/unsetFavorite.json")
	Call<ResponseWrapper<Void>> unsetFavorite(
			@Path("pan") String cardPan
	);
}
