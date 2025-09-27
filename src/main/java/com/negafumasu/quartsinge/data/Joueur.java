package com.negafumasu.quartsinge.data;

/**
 * Singe Application Player Class
 * 
 * @author tcazako
 *
 */
public class Joueur {

	String playerName;
	EnumTypeJoueur playerType;
	Float playerScore = 0f;
	
	public Joueur() {
		// TODO Auto-generated constructor stub
	}
	
	public Joueur(String playerName) {
		this.playerName = playerName;
		playerType = EnumTypeJoueur.HUMAIN;
	}

	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public EnumTypeJoueur getPlayerType() {
		return playerType;
	}
	public void setPlayerType(EnumTypeJoueur playerType) {
		this.playerType = playerType;
	}
	public Float getPlayerScore() {
		return playerScore;
	}
	public void setPlayerScore(Float playerScore) {
		this.playerScore = playerScore;
	}
}
