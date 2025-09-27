package com.negafumasu.quartsinge.data;

/**
 * Singe Application Robot Niveau Enum
 * 
 * @author tcazako
 *
 */
public enum EnumNiveauRobot {
	DIFFICILE(0), MOYEN(1), FACILE(2);

	private int levelValue;

	private EnumNiveauRobot(int levelValue) {
		this.levelValue = levelValue;
	}

	public int getLevelValue() {
		return levelValue;
	}
}
