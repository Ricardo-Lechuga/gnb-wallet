package es.ujaen.rlc00008.gnbwallet.data.entities;

/**
 * Created by Ricardo on 9/6/16.
 */
public class ChallengeDTO {

	private String idChallenge;
	private String question;

	public ChallengeDTO() {
	}

	public ChallengeDTO(ChallengeDTO challengeDTO) {
		this.idChallenge = challengeDTO.idChallenge;
		this.question = challengeDTO.question;
	}

	public ChallengeDTO(String idChallenge, String question) {
		this.idChallenge = idChallenge;
		this.question = question;
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
