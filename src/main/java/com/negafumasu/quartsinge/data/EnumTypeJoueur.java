package com.negafumasu.quartsinge.data;

/**
 * Singe Application Player Type Enum
 * 
 * @author tcazako
 *
 */
public enum EnumTypeJoueur {
	HUMAIN('H'), ROBOT('R');

	private char playerType;

	private EnumTypeJoueur(char playerType) {
		this.playerType = playerType;
	}

	public int getPlayerType() {
		return playerType;
	}

}
