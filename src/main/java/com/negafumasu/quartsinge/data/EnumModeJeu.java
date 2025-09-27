package com.negafumasu.quartsinge.data;

/**
 * Singe Application Game Mode Enum
 * 
 * @author tcazako
 *
 */
public enum EnumModeJeu {
	CLASSIQUE(0), SENSIBLE(1);

	int modeCode;

	private EnumModeJeu(int modeCode) {
		this.modeCode = modeCode;
	}

	public int getModeCode() {
		return modeCode;
	}

	public void setModeCode(int modeCode) {
		this.modeCode = modeCode;
	}

}
