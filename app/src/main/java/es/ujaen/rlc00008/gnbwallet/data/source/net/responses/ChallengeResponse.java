package es.ujaen.rlc00008.gnbwallet.data.source.net.responses;

/**
 * Created by Ricardo on 5/6/16.
 */
public class ChallengeResponse {

	private String idChallenge;
	private String question;

	public ChallengeResponse() {
	}

	public String getIdChallenge() {
		return idChallenge;
	}

	public void setIdChallenge(String idChallenge) {
		this.idChallenge = idChallenge;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
}
