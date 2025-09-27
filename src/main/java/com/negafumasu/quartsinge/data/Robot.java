package com.negafumasu.quartsinge.data;

import java.util.List;
import java.util.Random;

import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Singe Application Robot Player Class
 * 
 *  @author tcazako
 */
public class Robot extends Joueur{
	private static final Logger LOGGER = LogManager.getLogger(Robot.class);
	private static final char ALPHABET_LETTER[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
			'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	private EnumNiveauRobot niveauRobot = EnumNiveauRobot.MOYEN;
	private List<String> refinedDictionaryWords;
	
	private String getRandomAlphabetLetter(int min, int max, int randomNumber, Random random) {
		
		max = ALPHABET_LETTER.length - 1;
		randomNumber = random.nextInt(max - min + 1) + min;
		// TODO à modifier par des logs
		System.out.println("random number : " + randomNumber);
		System.out.println("random Character : " + ALPHABET_LETTER[randomNumber]);
		return ALPHABET_LETTER[randomNumber] + "";
	}
	
	/**
	 * Define Robot Action to do during his turn
	 * @param robotsLevel
	 * @param numberOfRound
	 */
	public void robotAction(EnumNiveauRobot robotsLevel, int numberOfRound, String previousWord, String questionnedWord, String currentWord
			, JTextField playerEntryTF) {
		Random random = new Random();
		int max = 0;
		int min = 0;
		int randomNumber = 0;

		int randomLevelChoice = random.nextInt(robotsLevel.getLevelValue() - min + 1) + min;
		System.out.println("++++++++++++++++ 'choixNivAlea' : " + randomLevelChoice);

		if (numberOfRound == 1 || randomLevelChoice == 2) {
			if (previousWord.equals("?")) {
				String currentWordWithoutQuestionMark = StringUtils.replace(questionnedWord, "?", "");
				// nettoyage de mot courant
				currentWord = "";
				playerEntryTF.setText(currentWordWithoutQuestionMark + getRandomAlphabetLetter(min, max, randomNumber, random));
			} else {
				robotRandomLetterEntry(min, max, randomNumber, random, playerEntryTF);
			}
		} else if (randomLevelChoice == 1) {
			robotQuestionMarkEntry(min, max, randomNumber, random, currentWord, numberOfRound, playerEntryTF);
		} else if (randomLevelChoice == 0) {
			robotLetterEntryForExistingWord(min, max, randomNumber, random, previousWord, currentWord, playerEntryTF);
		}

		// call gameScreenAction(int) at the end if runGame is true
//		if (runGame) {
//			gameScreenAction(1000);
//		}
		playerEntryTF.setText("");
	}
	
	private void robotQuestionMarkEntry(int min, int max, int randomNumber, Random random, String currentWord, int numberOfPlayers
			, JTextField playerEntryTF) {
		if (currentWord.length() > numberOfPlayers) {
			playerEntryTF.setText("?");
		} else {
			robotLetterEntryForExistingWord(min, max, randomNumber, random, currentWord, currentWord, playerEntryTF);
		}
	}

	private void robotRandomLetterEntry(int min, int max, int randomNumber, Random random
			, JTextField playerEntryTF) {
		playerEntryTF.setText(getRandomAlphabetLetter(min, max, randomNumber, random));
	}

	private void robotLetterEntryForExistingWord(int min, int max, int randomNumber, Random random, String previousWord, String currentWord
			, JTextField playerEntryTF) {
		// parcourir le dico
		List<String> potentialWords = getRefinedDictionaryWords();

		max = potentialWords.size() > 0 ? potentialWords.size() - 1 : 0;

		String currentWordWithoutQuestionMark = StringUtils.replace(currentWord, "?", "");

		LOGGER.info(" ++++++ saisieLettreMotExistantRobot >>>>>> min : " + min + " | max : " + max);
		randomNumber = random.nextInt(max - min + 1) + min;

		// trouve un mot dont le debut est le meme que le mot courant au hazard
		// TODO à modifier par des logs
		System.out.println("random number : " + randomNumber);
		System.out.println(
				"random String : " + (!potentialWords.isEmpty() ? potentialWords.get(randomNumber) : "'valeur null'"));
		// si le robot ne trouve pas de valeur, demander à quoi le jouur suivant pense
		// TODO à modifier par des logs
		System.out.println("taille motCourant : " + currentWordWithoutQuestionMark.length());
		String potentialLetter = !potentialWords.isEmpty() && currentWordWithoutQuestionMark.length() >= 1
				&& currentWordWithoutQuestionMark.length() < potentialWords.get(randomNumber).length()
						? potentialWords.get(randomNumber).substring(currentWordWithoutQuestionMark.length(),
								currentWordWithoutQuestionMark.length() + 1)
						: "?";
		System.out.println(" lettrePotentiel : " + potentialLetter);

		if (previousWord.equals("?")) {
			// nettoyage de mot courant
			currentWord = "";
			if (!potentialWords.isEmpty() && potentialWords.get(randomNumber).startsWith(currentWordWithoutQuestionMark)) {
				playerEntryTF.setText(potentialWords.get(randomNumber));
			} else {
				playerEntryTF.setText(currentWordWithoutQuestionMark + getRandomAlphabetLetter(min, max, randomNumber, random));
			}
		} else {
			playerEntryTF.setText(potentialLetter);
		}
	}
	
	public EnumNiveauRobot getRobotLevel() {
		return niveauRobot;
	}

	public void setRobotLevel(EnumNiveauRobot niveauRobot) {
		this.niveauRobot = niveauRobot;
	}

	public void setRefinedDictionaryWords(List<String> refinedDictionaryWords) {
		this.refinedDictionaryWords = refinedDictionaryWords;
	}
	
	public List<String> getRefinedDictionaryWords() {
		return refinedDictionaryWords;
	}
	
}
