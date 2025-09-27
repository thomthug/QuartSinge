package com.negafumasu.quartsinge.ihm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.function.UnaryOperator;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.negafumasu.quartsinge.Constants;
import com.negafumasu.quartsinge.GenericParam;
import com.negafumasu.quartsinge.data.EnumModeJeu;
import com.negafumasu.quartsinge.data.EnumNiveauRobot;
import com.negafumasu.quartsinge.data.EnumTypeJoueur;
import com.negafumasu.quartsinge.data.Joueur;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class FenetreEcranJeu {
	/** Constants **/
	private static final Logger LOGGER = LogManager.getLogger(FenetreEcranJeu.class);

	/** Objets graphique **/

	// Menu Principale
//	private FenetreMenuPrincipal menuPrincipalFrame;
	FenetreMenuPrincipal fenetreMenuPrincipal;

	// Ecran Jeu
	private Button validateBtn;
	private Button exitBtn;
	private Button replayBtn;
	private ScrollPane gameScreenSp;
	private TextFlow gameScreenTp;
	private TextField playerEntryTF;
	private TextField currentWordTF;
	private TextField topPlayerTF;

	/** Jeu **/

	// Joueurs
	private Joueur player1;
	private Joueur player2;
	private Joueur player3;
	private Joueur player4;
	/////////

	private Joueur previousPlayer;
	private Joueur currentPlayer;
	private Joueur nextPlayer;
	private Joueur questionningPlayer;

	// Mots
	private String previousWord = "";
	private String currentWord = "";
	private String questionnedWord = "";

	// Fichiers
	private List<String> dictionaryWords;

	// Autres
	private int numberOfPlayers;
	private int numberOfRound;
	private boolean runGame = true;
	/** parametres **/
	private EnumModeJeu gameMode = EnumModeJeu.CLASSIQUE;
	private EnumNiveauRobot robotsLevel = EnumNiveauRobot.DIFFICILE;
	private String dictionaryFileName;
	private String logFileName;
	private String foundWords = "";

	// Menu
	MenuItem gameScreenExitMi;
	MenuItem gameScreenConfigurationMi;
	MenuItem gameScreenRecordHistoryMi;
	MenuItem gameScreenReplayMi;
	MenuItem gameScreenBackToMainMenuMi;

	

//	SimpleAttributeSet wordStyle = new SimpleAttributeSet();

	// document lié au jTextpane
//	StyledDocument document;

	public FenetreEcranJeu(Stage primaryStage, List<Joueur> joueurs) {

		VBox mainBox = new VBox(10);

		// Initiallisation des variables
		setNumberOfPlayer(joueurs.size());
		setGameMode(EnumModeJeu.CLASSIQUE);
		setRobotsLevel(EnumNiveauRobot.FACILE);
		setDictionaryFileName("C:\\Users\\thomas.cazako\\Documents\\Projets_Java\\QuartSinge\\config\\liste_francais_sans_car_spe.txt");
		setLogFileName("C:\\Users\\thomas.cazako\\Documents\\Projets_Java\\QuartSinge\\config\\config.txt");

		// Recuperation des joueurs
		setPlayer1(joueurs.get(0));
		setPlayer2(joueurs.get(1));
		if(joueurs.size() == 3) {
			setPlayer3(joueurs.get(2));
		}
		
		if(joueurs.size() == 4) {
			setPlayer4(joueurs.get(3));
		}
		
		

		initializeDictionaryData();

		// TODO à modifier par des logs
		System.out.println("************* ModeJeu = " + getGameMode() + " ************* ");
		System.out.println("************* NiveauRobots = " + getRobotsLevel() + " ************* ");

		mainBox = initializeView(primaryStage);

		Scene menuScene = new Scene(mainBox, 520, 700);
		primaryStage.setScene(menuScene);
		primaryStage.setTitle("Quart Singe | Ecran Jeu");
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/com/negafumasu/img/singe_04.png")));
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public FenetreEcranJeu(Stage primaryStage, FenetreMenuPrincipal menuPrincipal) {

		VBox mainBox = new VBox(10);

		this.fenetreMenuPrincipal = menuPrincipal;

		// Initiallisation des variables
		setNumberOfPlayer(fenetreMenuPrincipal.getNumberOfPlayers());
		setGameMode(fenetreMenuPrincipal.getModeJeu());
		setRobotsLevel(fenetreMenuPrincipal.getRobotsLevel());
		setDictionaryFileName(fenetreMenuPrincipal.getDictionaryFileName());
		setLogFileName(fenetreMenuPrincipal.getLogFileName());

		// Recuperation des joueurs
		setPlayer1(fenetreMenuPrincipal.getPlayer1());
		setPlayer2(fenetreMenuPrincipal.getPlayer2());
		setPlayer3(fenetreMenuPrincipal.getPlayer3());
		setPlayer4(fenetreMenuPrincipal.getPlayer4());

		initializeDictionaryData();

		// TODO à modifier par des logs
		System.out.println("************* ModeJeu = " + getGameMode() + " ************* ");
		System.out.println("************* NiveauRobots = " + getRobotsLevel() + " ************* ");

		mainBox = initializeView(primaryStage);

		Scene menuScene = new Scene(mainBox, 520, 700);
		primaryStage.setScene(menuScene);
		primaryStage.setTitle("Quart Singe | Ecran Jeu");
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/com/negafumasu/img/singe_04.png")));
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	private VBox initializeView(Stage primaryStage) {

		VBox gameScreenPanel = new VBox(10);
		gameScreenPanel.setPrefSize(400, 700);

		VBox topBox = new VBox(10);

		HBox middleBox = new HBox(10);
		VBox westBox = new VBox(10);
		VBox centerBox = new VBox(10);
		VBox eastBox = new VBox(10);
		middleBox.getChildren().addAll(westBox, centerBox, eastBox);

		HBox bottomBox = new HBox(10);

		/////////////////////////////////////////////////////// :

		// Menu
		MenuBar gameScreenMenuBar = new MenuBar();
		Menu gameScreenMenu = new Menu("Fichier");
		gameScreenExitMi = new MenuItem("Quitter");
//		gameScreenExitMi.addActionListener(this);
		gameScreenMenu.getItems().add(gameScreenExitMi);

		gameScreenReplayMi = new MenuItem("Rejouer");
//		gameScreenReplayMi.addActionListener(this);
		gameScreenMenu.getItems().add(gameScreenReplayMi);

		gameScreenConfigurationMi = new MenuItem("Parametres");
//		gameScreenConfigurationMi.addActionListener(this);
		gameScreenMenu.getItems().add(gameScreenConfigurationMi);

		gameScreenRecordHistoryMi = new MenuItem("Enregistrer Historique Partie");
//		gameScreenRecordHistoryMi.addActionListener(this);
		gameScreenMenu.getItems().add(gameScreenRecordHistoryMi);

		gameScreenBackToMainMenuMi = new MenuItem("Retourner au Menu Principal");
//		gameScreenBackToMainMenuMi.addActionListener(this);
		gameScreenMenu.getItems().add(gameScreenBackToMainMenuMi);

		gameScreenMenuBar.getMenus().add(gameScreenMenu);

//		topBox.getChildren().add(gameScreenMenuBar);

		//// current word label and text field
		Label currentWordLabel = new Label("Mot en cours : ");
		currentWordLabel.setPrefSize(84, 20);
//		currentWordLabel.setPreferredSize(currentWordLabel.getPreferredSize());
//		currentWordLabel.setPreferredSize(currentWordLabel.getSize());
		currentWordTF = new TextField();
		currentWordTF.setPrefSize(80, 20);
		currentWordTF.setEditable(false);
//		currentWordTF.setPreferredSize(currentWordTF.getSize());

		// top player label and text field

		Label topPlayerLabel = new Label("Meilleur Joueur : ");
//		ImageIcon crownIcon = CommonSingleton.getImageIcon("img/couronne.png", 15, 15);
//		ImageIcon crownIcon = CommonSingleton.getImageIcon("img/couronne2.png", 50, 50);
//		topPlayerLabel.setIcon(crownIcon);
		topPlayerLabel.setPrefSize(118, 20);
//		topPlayerLabel.setPreferredSize(topPlayerLabel.getPreferredSize());
//		topPlayerLabel.setPreferredSize(topPlayerLabel.getSize());
		topPlayerTF = new TextField();
		topPlayerTF.setPrefSize(80, 20);
		topPlayerTF.setEditable(false);
//		topPlayerTF.setPreferredSize(topPlayerTF.getSize());

		//////////////
		// Boutons
		//////////////
//		ImageIcon exitIcon = CommonSingleton.getImageIcon("img/quitter_03.png", 50, 50);
//		exitBtn = new JButton(exitIcon);
//		exitBtn.setToolTipText("Quitter le Jeu");
//		exitBtn.setPrefSize(new Dimension(60, 55));
//		exitBtn.setPreferredSize(exitBtn.getSize());
//		exitBtn.setBackground(ColorConverterUtils.hex2Rgb("#FF5588"));
//		exitBtn.setFont(new Font(SingeConstantes.CALIBRI_POLICE, Font.BOLD, 20));
//		exitBtn.addActionListener(this);
		// Charger l'image
		Image exitBtnImage = new Image(getClass().getResourceAsStream("/com/negafumasu/img/quitter_03.png"));
		// Créer une ImageView avec l'image
		ImageView exitBtnImageView = new ImageView(exitBtnImage);
		exitBtnImageView.setFitWidth(40); // Largeur de l'image
		exitBtnImageView.setFitHeight(50); // Hauteur de l'image
		exitBtn = new Button("", exitBtnImageView);
		exitBtn.setPrefSize(50, 50);
		exitBtn.setPadding(new Insets(0, 20, 0, 20));
		/***/
		String exitBtnStyle = GenericParam.getButtonStyle("14", Constants.COLOR_RED, "0.5em", Constants.COLOR_WHITE,
				"1 1 1 1", Constants.COLOR_WHITE, "1em", "0");
		String exitBtnStyleHover = GenericParam.getButtonStyle("14", Constants.COLOR_PINK, "1em", Constants.COLOR_BLACK,
				"1 1 1 1", Constants.COLOR_BLACK, "1em", "0");

		exitBtn.setStyle(exitBtnStyle);
		exitBtn.setOnMouseEntered(e -> exitBtn.setStyle(exitBtnStyleHover));
		exitBtn.setOnMouseExited(e -> exitBtn.setStyle(exitBtnStyle));
		/***/
		// Configurer l'action du bouton
		exitBtn.setOnAction(e -> actionPerformed(e, primaryStage));
		exitBtn.setTooltip(new Tooltip("Quitter le Jeu"));

//		ImageIcon replayicon = CommonSingleton.getImageIcon("img/rejouer_02.png", 50, 50);
//		replayBtn = new JButton(replayicon);
//		replayBtn.setToolTipText("Rejouer une Partie");
//		replayBtn.setPrefSize(new Dimension(60, 55));
//		replayBtn.setPreferredSize(exitBtn.getSize());
//		replayBtn.setBackground(ColorConverterUtils.hex2Rgb("#3333FF"));
//		replayBtn.setFont(new Font(SingeConstantes.CALIBRI_POLICE, Font.BOLD, 20));
//		replayBtn.addActionListener(this);
		// Charger l'image
		Image replayBtnImage = new Image(getClass().getResourceAsStream("/com/negafumasu/img/rejouer_02.png"));
		// Créer une ImageView avec l'image
		ImageView replayBtnImageView = new ImageView(replayBtnImage);
		replayBtnImageView.setFitWidth(40); // Largeur de l'image
		replayBtnImageView.setFitHeight(50); // Hauteur de l'image
		replayBtn = new Button("", replayBtnImageView);
		replayBtn.setPrefSize(80, 65);
		replayBtn.setPadding(new Insets(0, 20, 0, 20));
		/***/
		String replayBtnStyle = GenericParam.getButtonStyle("14", Constants.COLOR_BLUE, "1em", Constants.COLOR_WHITE,
				"1 1 1 1", Constants.COLOR_WHITE, "1em", "0");
		String replayBtnStyleHover = GenericParam.getButtonStyle("14", Constants.COLOR_LIGHTBLUE, "1em",
				Constants.COLOR_BLACK, "1 1 1 1", Constants.COLOR_BLACK, "1em", "0");

		replayBtn.setStyle(replayBtnStyle);
		replayBtn.setOnMouseEntered(e -> replayBtn.setStyle(replayBtnStyleHover));
		replayBtn.setOnMouseExited(e -> replayBtn.setStyle(replayBtnStyle));
		/***/
		// Configurer l'action du bouton
		replayBtn.setOnAction(e -> actionPerformed(e, primaryStage));
		replayBtn.setTooltip(new Tooltip("Rejouer une Partie"));

//		ImageIcon validateIcon = CommonSingleton.getImageIcon("img/valider_05.png", 50, 50);
//		validateBtn = new JButton(validateIcon);
//		validateBtn.setToolTipText("Valider la Saisie");
//		validateBtn.setPrefSize(new Dimension(60, 55));
//		validateBtn.setPreferredSize(validateBtn.getSize());
//		validateBtn.setBackground(ColorConverterUtils.hex2Rgb("#77FF88"));
//		validateBtn.setFont(new Font(SingeConstantes.CALIBRI_POLICE, Font.BOLD, 20));
//		validateBtn.addActionListener(this);
		// Charger l'image
		Image validateBtnImage = new Image(getClass().getResourceAsStream("/com/negafumasu/img/valider_05.png"));
		// Créer une ImageView avec l'image
		ImageView validateBtnImageView = new ImageView(validateBtnImage);
		validateBtnImageView.setFitWidth(40); // Largeur de l'image
		validateBtnImageView.setFitHeight(50); // Hauteur de l'image
		validateBtn = new Button("", validateBtnImageView);
		validateBtn.setPrefSize(80, 65);
		validateBtn.setPadding(new Insets(0, 20, 0, 20));
		/***/
		String validateBtnStyle = GenericParam.getButtonStyle("14", Constants.COLOR_GREEN, "1em", Constants.COLOR_WHITE,
				"1 1 1 1", Constants.COLOR_WHITE, "1em", "0");
		String validateBtnStyleHover = GenericParam.getButtonStyle("14", Constants.COLOR_LIGHTGREEN, "1em",
				Constants.COLOR_BLACK, "1 1 1 1", Constants.COLOR_BLACK, "1em", "0");

		validateBtn.setStyle(validateBtnStyle);
		validateBtn.setOnMouseEntered(e -> validateBtn.setStyle(validateBtnStyleHover));
		validateBtn.setOnMouseExited(e -> validateBtn.setStyle(validateBtnStyle));
		/***/
		// Configurer l'action du bouton
		validateBtn.setOnAction(e -> actionPerformed(e, primaryStage));
		validateBtn.setTooltip(new Tooltip("Valider la Saisie"));

		// Utiliser un TextFormatter pour forcer les majuscules
		UnaryOperator<TextFormatter.Change> filter = change -> {
			change.setText(change.getText().toUpperCase()); // Convertir en majuscules
			return change;
		};
		TextFormatter<String> textFormatter = new TextFormatter<>(filter);

		playerEntryTF = new TextField();
		playerEntryTF.setTextFormatter(textFormatter);
		playerEntryTF.setPrefSize(400, 55);
//		playerEntryTF.setPreferredSize(playerEntryTF.getSize());
//		playerEntryTF.setBackground(Color.WHITE);
//		playerEntryTF.setFont(new Font(SingeConstantes.CALIBRI_POLICE, Font.BOLD, 55));
//		playerEntryTF.addActionListener(this);
		playerEntryTF.setOnAction(e -> {actionPerformed(e, primaryStage); playerEntryTF.requestFocus();});

		// force les caratere à etre en majuscule
//		DocumentFilter fdocumentFilter = new UppercaseJTextField();
//		AbstractDocument playerEntryDoc = (AbstractDocument) playerEntryTF.getDocument();
//		playerEntryDoc.setDocumentFilter(fdocumentFilter);

		// Initialisation du JTextPane
		gameScreenTp = new TextFlow();
//		gameScreenTp.setPrefSize(new Dimension(600, 600));
//		gameScreenTp.setPrefSize(601, 600);
		gameScreenTp.setPrefSize(500, 600);
//		gameScreenTp.setMinimumSize(new Dimension(600, 600));
//		gameScreenTp.setPreferredSize(gameScreenTp.getSize());
//		gameScreenTp.setBackground(Color.BLACK);
//		gameScreenTp.setForeground(Color.WHITE);
//		gameScreenTp.setFont(new Font(SingeConstantes.CALIBRI_POLICE, Font.BOLD, 16));
		gameScreenTp.setDisable(true);
//        ecranJeuTp.setLayout(new FlowLayout(SwingConstants.TRAILING));
		gameScreenTp.autosize();
		gameScreenTp.setStyle(Constants.FX_BACKGROUND_BLACK + GenericParam.getFxTextFill(Constants.COLOR_WHITE));
		/////

		// ScrollPane du JTextpane
		gameScreenSp = new ScrollPane(gameScreenTp);
//		gameScreenSp.setAutoscrolls(true);
//		gameScreenSp.setPreferredSize(gameScreenTp.getSize());
		gameScreenSp.setPrefSize(500, 600);
		gameScreenSp.setFitToWidth(true); // Pour que le TextFlow s'élargisse correctement
		gameScreenSp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		/////

		// Define a keyword attribute
//		wordStyle = new SimpleAttributeSet();
//		StyleConstants.setForeground(wordStyle, Color.RED);
//		StyleConstants.setBackground(wordStyle, Color.YELLOW);
//		StyleConstants.setBold(wordStyle, true);

		// init JTextpane Document
//		document = gameScreenTp.getStyledDocument();

		// panel to put components
//		HBox gameScreenPanel = new HBox(10);
//		gameScreenPanel.setPrefSize(600, 700);
//		gameScreenPanel.setPreferredSize(gameScreenPanel.getSize());
		/********** TOP **********/
		// Menu
//		gameScreenPanel.add(gameScreenMenuBar);
		topBox.getChildren().addAll(gameScreenMenuBar);

//		// Separator
//		gameScreenPanel.add(new Label("|"));
//		
//		//// current word label and text field
//		gameScreenPanel.add(currentWordLabel);
//		gameScreenPanel.add(currentWordTF);
//		
//		// Separator
//		gameScreenPanel.add(new Label("|"));
//		
//		// top player label and text field
//		gameScreenPanel.add(topPlayerLabel);
//		gameScreenPanel.add(topPlayerTF);

		/********** MIDDLE **********/
		// game Screen
//		gameScreenPanel.add(gameScreenSp);
//		// entry field
//		gameScreenPanel.add(playerEntryTF);
//		centerBox.getChildren().addAll(gameScreenSp, playerEntryTF);
		centerBox.getChildren().addAll(gameScreenSp);

		/********** BOTTOM **********/
		// buttons
//		gameScreenPanel.add(validateBtn);
//		gameScreenPanel.add(replayBtn);
//		gameScreenPanel.add(exitBtn);
		bottomBox.getChildren().addAll(playerEntryTF, validateBtn, replayBtn, exitBtn);
//		bottomBox.getChildren().addAll(validateBtn, replayBtn, exitBtn);
		bottomBox.setAlignment(Pos.CENTER);
		bottomBox.setPadding(new Insets(10, 10, 10, 10));

		gameScreenPanel.getChildren().addAll(topBox, middleBox, bottomBox);
//		addWindowListener(new WindowAdapter() {
//			@Override
//			public void windowOpened(WindowEvent e) {
//				playerEntryTF.requestFocus();
//			}
//		});
		playerEntryTF.requestFocus();
//		mainBox.getChildren().addAll(topBox, middleBox, bottomBox);

		// affichage des joueurs dans le texte area
		displayPlayersOnGameStart();

		new Thread(() -> startGame()).start();

		return gameScreenPanel;
	}

	private void startGame() {

		// on desactive le bouton "rejouer"
		replayBtn.setDisable(true);
		gameScreenReplayMi.setDisable(true);

		// on set le 1 tour à 1
		numberOfRound = 1;

		// recuperer le 1er joueur
		currentPlayer = player1;

//		try {
//			Style style = document.addStyle("StyleName", null);
//			ImageIcon iconFleche = CommonSingleton.getImageIcon("img/fleche_verte2.png", 20, 20);
//			StyleConstants.setIcon(style, iconFleche);

		// Insert the image at the end of the text
//			document.insertString(document.getLength(), "*fleche_verte*", style);
//			document.insertString(document.getLength(), " Le joueur [", null);
//			displayCustomisedPlayerName(currentPlayer);
//			document.insertString(document.getLength(), "] commence la partie ..." + SingeConstantes.CARRIAGE_RETURN, null);
//
//			document.insertString(document.getLength(),
//					SingeConstantes.FRONT_SPACE + "Veuillez entrer une lettre ..." + SingeConstantes.CARRIAGE_RETURN,
//					null);
//		} catch (Exception e) {
//			System.out.println(e);
//		}
		Text text1 = new Text();
//		StringBuilder commencerPartieStr = new StringBuilder();
		// Insert the image at the end of the text
		text1.setText(Constants.CARRIAGE_RETURN + " Le joueur [");
		text1.setStyle(GenericParam.getLabelStyle3("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
		gameScreenTp.getChildren().add(text1);
		Platform.runLater(() -> gameScreenSp.setVvalue(1.0));
		displayCustomisedPlayerName(currentPlayer);
		Text text2 = new Text();
		text2.setText("] commence la partie ..." + Constants.CARRIAGE_RETURN + Constants.FRONT_SPACE
				+ "Veuillez entrer une lettre ..." + Constants.CARRIAGE_RETURN);
		text2.setStyle(GenericParam.getLabelStyle3("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
		gameScreenTp.getChildren().add(text2);
		Platform.runLater(() -> gameScreenSp.setVvalue(1.0));
//		.append(Constants.FRONT_SPACE + "Veuillez entrer une lettre ..." + Constants.CARRIAGE_RETURN);
//		displayCustomisedPlayerName(currentPlayer);
//		commencerPartieTxt.setText(commencerPartieStr.toString());

		setCurrentPlayerAction();

		if (EnumTypeJoueur.ROBOT.equals(currentPlayer.getPlayerType())) {
			robotAction();
		}

//		if (currentPlayer instanceof Robot) {
//			Robot robotPlayer = (Robot) currentPlayer;
//			robotPlayer.robotAction(robotsLevel, numberOfRound, previousWord, questionnedWord, currentWord, playerEntryTF);
//		}

		// revoir la partie timer
		// si pas d'action validerBTN.doclick(); sinon relancer timer
//        TimerTask timerTask = new MaTache();
//        Timer timer = new Timer(true);
//        timer.scheduleAtFixedRate(timerTask, 0, 5000);
//        System.out.println("Lancement execution");
//        if(timer.) {
//
//        }
		
	}

	private void gameScreenAction(int sleepTime) {
		try {
			// TODO à modifier par des logs
			System.out.println("!!!!! Joueur courant : " + currentPlayer.getPlayerName() + " !!!!!");

			// recuperation du joueur suivant
			getNextPlayer();
			System.out.println("Mot courant avant saisie : " + currentWord);

			// joueur tape une valeur
			System.out.println("Joueur [" + currentPlayer.getPlayerName() + "] a tapé : " + playerEntryTF.getText());

//			document.insertString(document.getLength(), SingeConstantes.FRONT_SPACE + "[", null);
//			displayCustomisedPlayerName(currentPlayer);
//			document.insertString(document.getLength(), "] propose : " + playerEntryTF.getText() + SingeConstantes.CARRIAGE_RETURN,
//					null);

			Text text1 = new Text();
//			StringBuilder commencerPartieStr = new StringBuilder();
			// Insert the image at the end of the text
			text1.setText(Constants.FRONT_SPACE + "[");
			text1.setStyle(GenericParam.getLabelStyle3("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
			gameScreenTp.getChildren().add(text1);
			Platform.runLater(() -> gameScreenSp.setVvalue(1.0));
			displayCustomisedPlayerName(currentPlayer);
			Text text2 = new Text();
			text2.setText("] propose : " + playerEntryTF.getText() + Constants.CARRIAGE_RETURN);
			text2.setStyle(GenericParam.getLabelStyle3("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
			gameScreenTp.getChildren().add(text2);
			Platform.runLater(() -> gameScreenSp.setVvalue(1.0));

			// mise à jour du mot courant
			currentWord += playerEntryTF.getText();

//			wordStyle = new SimpleAttributeSet();
//			StyleConstants.setForeground(wordStyle, Color.YELLOW);
//			StyleConstants.setItalic(wordStyle, true);

//			document.insertString(document.getLength(),
//					" >>> Le mot en cours est '" + currentWord + "...' <<<" + SingeConstantes.CARRIAGE_RETURN, wordStyle);
			Text text3 = new Text();
			text3.setText(" >>> Le mot en cours est '" + currentWord + "...' <<<" + Constants.CARRIAGE_RETURN);
			text3.setStyle(GenericParam.getLabelStyle3("16", Constants.COLOR_YELLOW, Constants.FAMILLY_CALIBRI));
			gameScreenTp.getChildren().add(text3);
			Platform.runLater(() -> gameScreenSp.setVvalue(1.0));

//			gameScreenTp.setCaretPosition(document.getLength());

			// TODO à modifier par des logs
			System.out.println("Mot courant après saisie: " + currentWord);

			boolean existingWord = false;

			String currentWordWithoutQuestionMark = StringUtils.replace(questionnedWord, "?", "");
			if (questionnedWord.equals("")) {
				currentWordWithoutQuestionMark = StringUtils.replace(currentWord, "?", "");
			}

			// si le mot courant existe dans le dictionnaire , le joueur courant prend un
			// quart de singe
			List<String> refinedDictionaryWord = getRefinedDictionaryWords();
			// TODO à modifier par des logs
			System.out.println("!!!!! mot precedent : " + previousWord + " !!!!!");

			if (EnumModeJeu.CLASSIQUE.equals(gameMode)) {
				for (int i = 0; i < refinedDictionaryWord.size(); i++) {

					if ((refinedDictionaryWord.get(i) != null
							&& (refinedDictionaryWord.get(i).equals(currentWord) && refinedDictionaryWord.size() == 1))
							|| ((previousWord.equals("?") && refinedDictionaryWord.get(i).equals(currentWord))
									&& refinedDictionaryWord.get(i).startsWith(currentWordWithoutQuestionMark))
							|| (refinedDictionaryWord.get(i).equals(currentWord) && refinedDictionaryWord.size() == 2
									&& (!refinedDictionaryWord.get(i).endsWith("S")
											|| !refinedDictionaryWord.get(i).endsWith("X")))) {

						if (!foundWords.contains(currentWord + ";")) {
							foundWords += currentWord + ";";
							// TODO à modifier par des logs
							System.out.println("+ Mot existant : [" + refinedDictionaryWord.get(i)
									+ "] | Mot courant : [" + currentWord + "]");
//							document.insertString(document.getLength(), SingeConstantes.FRONT_SPACE + "Le mot [" + currentWord
//									+ "] existe ! " + SingeConstantes.CARRIAGE_RETURN, null);
//							gameScreenTp.setCaretPosition(document.getLength());
							Text text4 = new Text();
							text4.setText(Constants.FRONT_SPACE + "Le mot [" + currentWord + "] existe ! "
									+ Constants.CARRIAGE_RETURN);
							text4.setStyle(GenericParam.getLabelStyle3("16", Constants.COLOR_WHITE,
									Constants.FAMILLY_CALIBRI));
							gameScreenTp.getChildren().add(text4);
							Platform.runLater(() -> gameScreenSp.setVvalue(1.0));
							if (previousWord.equals("?")) {
								currentPlayer = questionningPlayer;
							} else {
								shiftToNextPlayer();
							}
						} else {
							// TODO à modifier par des logs
							System.out.println("+ Mot existant déjà sortie : [" + refinedDictionaryWord.get(i)
									+ "] | Mot courant : [" + currentWord + "]");
//							document.insertString(document.getLength(), SingeConstantes.FRONT_SPACE + "Le mot [" + currentWord
//									+ "] à déjà été proposé ! " + SingeConstantes.CARRIAGE_RETURN, null);
//							gameScreenTp.setCaretPosition(document.getLength());
							Text text4 = new Text();
							text4.setText(Constants.FRONT_SPACE + "Le mot [" + currentWord + "] à déjà été proposé ! "
									+ Constants.CARRIAGE_RETURN);
							text4.setStyle(GenericParam.getLabelStyle3("16", Constants.COLOR_WHITE,
									Constants.FAMILLY_CALIBRI));
							gameScreenTp.getChildren().add(text4);
							
						}

						existingWord = true;

						addSingeQuarter();
						break;
					}
				}
			} else {
				for (int i = 0; i < dictionaryWords.size(); i++) {
					if (dictionaryWords.get(i) != null && dictionaryWords.get(i).equals(currentWord)
							&& dictionaryWords.get(i).startsWith(currentWordWithoutQuestionMark)) {
						// TODO à modifier par des logs
						System.out.println("+ Mot existant : [" + dictionaryWords.get(i) + "] | Mot courant : ["
								+ currentWord + "]");
//						document.insertString(document.getLength(), SingeConstantes.FRONT_SPACE + "Le mot [" + currentWord
//								+ "] existe ! " + SingeConstantes.CARRIAGE_RETURN, null);
//						gameScreenTp.setCaretPosition(document.getLength());
						Text text4 = new Text();
						text4.setText(Constants.FRONT_SPACE + "Le mot [" + currentWord + "] existe ! "
								+ Constants.CARRIAGE_RETURN);
						text4.setStyle(
								GenericParam.getLabelStyle3("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
						gameScreenTp.getChildren().add(text4);
						Platform.runLater(() -> gameScreenSp.setVvalue(1.0));
						existingWord = true;
						if (previousWord.equals("?")) {
							currentPlayer = questionningPlayer;
						} else {
							shiftToNextPlayer();
						}

						addSingeQuarter();
						break;
					}
				}
			}

			if (!existingWord && previousWord.equals("?")) {

				if (currentWord.startsWith(currentWordWithoutQuestionMark)) {
					// TODO à modifier par des logs
					System.out.println("Mot '" + currentWord + "' n'existe pas!");
//					document.insertString(document.getLength(), SingeConstantes.FRONT_SPACE + "+ Le mot [" + currentWord
//							+ "] n'existe pas dans le dictionnaire ! " + SingeConstantes.CARRIAGE_RETURN, null);
//					gameScreenTp.setCaretPosition(document.getLength());
					Text text4 = new Text();
					text4.setText(Constants.FRONT_SPACE + "+ Le mot [" + currentWord
							+ "] n'existe pas dans le dictionnaire ! " + Constants.CARRIAGE_RETURN);
					text4.setStyle(GenericParam.getLabelStyle3("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
					gameScreenTp.getChildren().add(text4);
					Platform.runLater(() -> gameScreenSp.setVvalue(1.0));
				} else {
					// TODO à modifier par des logs
					System.out.println("Mot '" + currentWord + "' ne commence pas comme la combinaison précédente : '"
							+ currentWordWithoutQuestionMark + "' !");
//					document.insertString(document.getLength(),
//							SingeConstantes.FRONT_SPACE + "+ Le mot [" + currentWord
//									+ "] ne commence pas comme la combinaison précédente : '" + currentWordWithoutQuestionMark
//									+ "' !" + SingeConstantes.CARRIAGE_RETURN,
//							null);
//					gameScreenTp.setCaretPosition(document.getLength());
					Text text4 = new Text();
					text4.setText(Constants.FRONT_SPACE + "+ Le mot [" + currentWord
							+ "] ne commence pas comme la combinaison précédente : '" + currentWordWithoutQuestionMark
							+ "' !" + Constants.CARRIAGE_RETURN);
					text4.setStyle(GenericParam.getLabelStyle3("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
					gameScreenTp.getChildren().add(text4);
					Platform.runLater(() -> gameScreenSp.setVvalue(1.0));
				}

				addSingeQuarter();
			}

			// set current word to specified text field
			currentWordTF.setText(currentWord.replace('?', ' '));

			// verifier que les lettres sont en majuscule
			// si tour = 1 et taille valeur courante sup. à 1 ou si valeur precedente
			// different de '?' et taille valeur tapée sup. à 1
			// ou si le le joueur saisie ? et le mot precedent à une taille inf. à 2
			// alors joueur courant reçoit 1 quart de singe.
			if (numberOfRound == 1 && playerEntryTF.getText().length() > 1 && !previousWord.equals("?")
					|| !previousWord.equals("?") && playerEntryTF.getText().length() > 1
					|| currentWord.equals("?") && previousWord.length() < 2 || playerEntryTF.getText().equals("")) {
				// TODO à modifier par des logs
				System.out.println("-> Valeur '" + currentWord + "' non valide !!! ");
//				document.insertString(document.getLength(), SingeConstantes.FRONT_SPACE + "+ La valeur [" + currentWord
//						+ "] n'est pas valide ... " + SingeConstantes.CARRIAGE_RETURN, null);
//				gameScreenTp.setCaretPosition(document.getLength());
				Text text4 = new Text();
				text4.setText(Constants.FRONT_SPACE + "+ La valeur [" + currentWord + "] n'est pas valide ... "
						+ Constants.CARRIAGE_RETURN);
				text4.setStyle(GenericParam.getLabelStyle3("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
				gameScreenTp.getChildren().add(text4);
				Platform.runLater(() -> gameScreenSp.setVvalue(1.0));
				// joueur courant reçoit un quat de singe
				addSingeQuarter();
				currentWord = "";
				// lancer une nouvelle manche
				numberOfRound = 1;
			} else if (playerEntryTF.getText().equals("?")) {

				// 3 car motCourant contient le '?' dans sa valeur
				if (currentWord.length() < 3) {
					// joueur courant reçoit un quat de singe
					addSingeQuarter();
					currentWord = "";
					numberOfRound = 1;
				} else {
					// recuperation du mot Questionné
					questionnedWord = currentWord;
					questionningPlayer = currentPlayer;
					// TODO à modifier par des logs
					System.out.println("+Joueur Questionnant : " + questionningPlayer.getPlayerName());
					currentPlayer = previousPlayer;
					// TODO à modifier par des logs
					System.out.println("+Joueur Courant : " + currentPlayer.getPlayerName());
					previousWord = "?";
					currentWord = "";
					// TODO à modifier par des logs
					System.out.println("+ motPrecedent : " + previousWord);
					System.out.println("+ motCourant : " + currentWord);
					// ***ici texte avec question
//					document.insertString(document.getLength(), SingeConstantes.FRONT_SPACE + "Le joueur [", null);
					Text text4 = new Text();
					text4.setText(Constants.FRONT_SPACE + "Le joueur [");
					text4.setStyle(GenericParam.getLabelStyle3("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
					gameScreenTp.getChildren().add(text4);
					Platform.runLater(() -> gameScreenSp.setVvalue(1.0));
					displayCustomisedPlayerName(questionningPlayer);
//					document.insertString(document.getLength(), "] demande au joueur [", null);
					Text text5 = new Text();
					text5.setText("] demande au joueur [");
					text5.setStyle(GenericParam.getLabelStyle3("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
					gameScreenTp.getChildren().add(text5);
					Platform.runLater(() -> gameScreenSp.setVvalue(1.0));
					displayCustomisedPlayerName(currentPlayer);
//					document.insertString(document.getLength(),	"] à quel mot il pense ..." + SingeConstantes.CARRIAGE_RETURN,	null);
					Text text6 = new Text();
					text6.setText("] à quel mot il pense ..." + Constants.CARRIAGE_RETURN);
					text6.setStyle(GenericParam.getLabelStyle3("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
					gameScreenTp.getChildren().add(text6);
					Platform.runLater(() -> gameScreenSp.setVvalue(1.0));

//					gameScreenTp.setCaretPosition(document.getLength());
					numberOfRound++;
				}
			} else if (!previousWord.equals("?")) {
				if (currentWord.equals("")) {
					numberOfRound = 1;
				} else {
					// cas classique
					shiftToNextPlayer();
					numberOfRound++;
				}
				previousWord = currentWord;
			} else {
				previousWord = "";
				currentWord = "";
			}

			// nettoyage du champs text
			playerEntryTF.setText("");
			setCurrentPlayerAction();
			// TODO à modifier par des logs
			System.out.println(" **************** le Joueur courant devient : " + currentPlayer.getPlayerName()
					+ " ******************");
			if (numberOfRound > 1) {
//				document.insertString(document.getLength(), SingeConstantes.FRONT_SPACE + "Au tour du joueur [", null);
				Text text4 = new Text();
				text4.setText(Constants.FRONT_SPACE + "Au tour du joueur [");
				text4.setStyle(GenericParam.getLabelStyle3("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
				gameScreenTp.getChildren().add(text4);
				Platform.runLater(() -> gameScreenSp.setVvalue(1.0));
				displayCustomisedPlayerName(currentPlayer);
//				document.insertString(document.getLength(), "] ... " + SingeConstantes.CARRIAGE_RETURN, null);
				Text text5 = new Text();
				text5.setText("] ... " + Constants.CARRIAGE_RETURN);
				text5.setStyle(GenericParam.getLabelStyle3("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
				gameScreenTp.getChildren().add(text5);
				Platform.runLater(() -> gameScreenSp.setVvalue(1.0));

//				gameScreenTp.setCaretPosition(document.getLength());
			}

			if (EnumTypeJoueur.ROBOT.equals(nextPlayer.getPlayerType()) && !currentWord.equals("")
					|| EnumTypeJoueur.ROBOT.equals(currentPlayer.getPlayerType())) {
				try {
					/// a remplacer par un "Timer()"
					Thread.sleep(850);
				} catch (InterruptedException e) {
					System.out.println(" Problème de Thread au niveau du 'Sleep' : " + e.toString());
				}
				robotAction();
			} else {
//				playerEntryTF.addFocusListener(this);
//				validateBtn.addFocusListener(this);
				playerEntryTF.requestFocus();
				validateBtn.requestFocus();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void addSingeQuarter() {

		currentPlayer.setPlayerScore(currentPlayer.getPlayerScore() + 0.25f);

		if (player1.getPlayerScore().equals(1f) || player2.getPlayerScore().equals(1f)
				|| player3 != null && player3.getPlayerScore().equals(1f)
				|| player4 != null && player4.getPlayerScore().equals(1f)) {
			endOfGame();
			resetScores();
			currentWord = "";
			// ********************************************tous reinitialiser !!!!
		} else {
			try {
//				document.insertString(document.getLength(), SingeConstantes.FRONT_SPACE + "Le joueur '", null);
				Text text4 = new Text();
				text4.setText(Constants.FRONT_SPACE + "Le joueur '");
				text4.setStyle(GenericParam.getLabelStyle3("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
				gameScreenTp.getChildren().add(text4);
				Platform.runLater(() -> gameScreenSp.setVvalue(1.0));
				displayCustomisedPlayerName(currentPlayer);
//				document.insertString(document.getLength(), "' reçoit un quart de singe ! " + SingeConstantes.CARRIAGE_RETURN,
//				null);
				Text text5 = new Text();
				text5.setText("' reçoit un quart de singe ! " + Constants.CARRIAGE_RETURN);
				text5.setStyle(GenericParam.getLabelStyle3("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
				gameScreenTp.getChildren().add(text5);
				Platform.runLater(() -> gameScreenSp.setVvalue(1.0));

//				gameScreenTp.setCaretPosition(document.getLength());
			} catch (Exception e) {
				System.out.println(e);
			}
			endOfRound();
			currentWord = "";
			numberOfRound = 1;
		}
	}

	private void endOfGame() {
		displayScores("Partie");

		try {
//			document.insertString(document.getLength(),
//					SingeConstantes.FRONT_SPACE
//							+ " Merci d'avoir jouer !! Pour relancer une partie cliquez sur le bouton rejouer en bleu "
//							+ SingeConstantes.CARRIAGE_RETURN,
//					null);
			Text text4 = new Text();
			text4.setText(Constants.FRONT_SPACE
					+ " Merci d'avoir jouer !! Pour relancer une partie cliquez sur le bouton rejouer en bleu "
					+ Constants.CARRIAGE_RETURN);
			text4.setStyle(GenericParam.getLabelStyle3("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
			gameScreenTp.getChildren().add(text4);
			Platform.runLater(() -> gameScreenSp.setVvalue(1.0));
//			document.insertString(document.getLength(), SingeConstantes.FRONT_SPACE
//					+ " ############################################################## " + Constants.CARRIAGE_RETURN,
//					null);
			Text text5 = new Text();
			text5.setText(Constants.FRONT_SPACE + " ############################################################## "
					+ Constants.CARRIAGE_RETURN);
			text5.setStyle(GenericParam.getLabelStyle3("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
			gameScreenTp.getChildren().add(text5);
			Platform.runLater(() -> gameScreenSp.setVvalue(1.0));
//			gameScreenTp.setCaretPosition(document.getLength());
		} catch (Exception e) {
			System.out.println(e);
		}
		runGame = false;
//		replayBtn.setEnabled(true);
//		gameScreenReplayMi.setEnabled(true);
		replayBtn.setDisable(false);
		gameScreenReplayMi.setDisable(false);
	}

	private void endOfRound() {
		displayScores("Manche");
		try {
//			document.insertString(document.getLength(), SingeConstantes.FRONT_SPACE + "> Le joueur ["
//					+ currentPlayer.getPlayerName() + "] commence la nouvelle manche ..." + SingeConstantes.CARRIAGE_RETURN,
//					null);
			Text text4 = new Text();
			text4.setText(Constants.FRONT_SPACE + "> Le joueur [");
			text4.setStyle(GenericParam.getLabelStyle3("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
			gameScreenTp.getChildren().add(text4);
			displayCustomisedPlayerName(currentPlayer);
			Text text5 = new Text();
			text5.setText("] commence la nouvelle manche ..." + Constants.CARRIAGE_RETURN);
			text5.setStyle(GenericParam.getLabelStyle3("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
			gameScreenTp.getChildren().add(text5);
			Platform.runLater(() -> gameScreenSp.setVvalue(1.0));
//			gameScreenTp.setCaretPosition(document.getLength()); displayCustomisedPlayerName
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void displayScores(String partieOuManche) {
		try {
//			document.insertString(document.getLength(),	SingeConstantes.FRONT_SPACE + "- Fin " + partieOuManche + " : score ", null);
			Text text1 = new Text();
			text1.setText(Constants.FRONT_SPACE + "- Fin " + partieOuManche + " : score ");
			text1.setStyle(GenericParam.getLabelStyle3("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
			gameScreenTp.getChildren().add(text1);
			Platform.runLater(() -> gameScreenSp.setVvalue(1.0));
			displayCustomisedPlayerName(player1);
			System.out.println("Fin " + partieOuManche + " : scoreJ1 = " + player1.getPlayerScore() + " | scoreJ2 = "
					+ player2.getPlayerScore());
//			document.insertString(document.getLength(), " = "	+ player1.getPlayerScore() + " | score ", null);
			Text text2 = new Text();
			text2.setText(" = " + player1.getPlayerScore() + " | score ");
			text2.setStyle(GenericParam.getLabelStyle3("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
			gameScreenTp.getChildren().add(text2);
			Platform.runLater(() -> gameScreenSp.setVvalue(1.0));
			displayCustomisedPlayerName(player2);
//			document.insertString(document.getLength(), " = "	+ player2.getPlayerScore() + SingeConstantes.CARRIAGE_RETURN, null);
//			gameScreenTp.setCaretPosition(document.getLength());
			Text text3 = new Text();
			text3.setText(" = " + player2.getPlayerScore() + Constants.CARRIAGE_RETURN);
			text3.setStyle(GenericParam.getLabelStyle3("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
			gameScreenTp.getChildren().add(text3);
			Platform.runLater(() -> gameScreenSp.setVvalue(1.0));
			switch (numberOfPlayers) {
//			case 2:
			// TODO à modifier par des logs
//				System.out.println("Fin " + partieOuManche + " : scoreJ1 = " + player1.getPlayerScore() + " | scoreJ2 = "
//						+ player2.getPlayerScore());
//				docu.insertString(docu.getLength(),	SingeConstantes.ESPACE_DEVANT + "- Fin " + partieOuManche + " : score ", null); 
//				afficherNomJoueurCustomise(joueur1); 
//				document.insertString(document.getLength(), " = "	+ player1.getPlayerScore() + " | score ", null); 
//				displayCustomisedPlayerName(player2);  
//				document.insertString(document.getLength(), " = "	+ player2.getPlayerScore() + SingeConstantes.CARRIAGE_RETURN, null);
//				gameScreenTp.setCaretPosition(document.getLength());
//				break;
			case 3:
				// TODO à modifier par des logs
				System.out.println("Fin " + partieOuManche + " : scoreJ1 = " + player1.getPlayerScore()
						+ " | scoreJ2 = " + player2.getPlayerScore() + " | scoreJ3 = " + player3.getPlayerScore());
//				docu.insertString(docu.getLength(), SingeConstantes.ESPACE_DEVANT + "- Fin " + partieOuManche + " : score ", null);  
//				afficherNomJoueurCustomise(joueur1);
//				document.insertString(document.getLength(), " = "	+ player1.getPlayerScore() + " | score ", null);  
//				displayCustomisedPlayerName(player2);
//				document.insertString(document.getLength(), " = " + player2.getPlayerScore() + " | score ", null);
				Text text4 = new Text();
				text4.setText(" = " + player2.getPlayerScore() + " | score ");
				text4.setStyle(GenericParam.getLabelStyle3("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
				gameScreenTp.getChildren().add(text4);
				Platform.runLater(() -> gameScreenSp.setVvalue(1.0));
				displayCustomisedPlayerName(player3);
//				document.insertString(document.getLength(), " = " + player3.getPlayerScore() + SingeConstantes.CARRIAGE_RETURN, null);
//				gameScreenTp.setCaretPosition(document.getLength());
				Text text5 = new Text();
				text5.setText(" = " + player3.getPlayerScore() + Constants.CARRIAGE_RETURN);
				text5.setStyle(GenericParam.getLabelStyle3("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
				gameScreenTp.getChildren().add(text5);
				Platform.runLater(() -> gameScreenSp.setVvalue(1.0));
				break;
			case 4:
				// TODO à modifier par des logs
				System.out.println("Fin " + partieOuManche + " : scoreJ1 = " + player1.getPlayerScore()
						+ " | scoreJ2 = " + player2.getPlayerScore() + " | scoreJ3 = " + player3.getPlayerScore()
						+ " | scoreJ4 = " + player4.getPlayerScore());
//				docu.insertString(docu.getLength(), SingeConstantes.ESPACE_DEVANT + "- Fin " + partieOuManche + " : score ", null);  
//				afficherNomJoueurCustomise(joueur1);
//				document.insertString(document.getLength(), " = "	+ player1.getPlayerScore() + " | score ", null);  
//				displayCustomisedPlayerName(player2);
//				document.insertString(document.getLength(), " = "	+ player2.getPlayerScore() + " | score ", null);  
				Text text6 = new Text();
				text6.setText(" = " + player2.getPlayerScore() + " | score ");
				text6.setStyle(GenericParam.getLabelStyle3("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
				gameScreenTp.getChildren().add(text6);
				Platform.runLater(() -> gameScreenSp.setVvalue(1.0));
				displayCustomisedPlayerName(player3);
//				document.insertString(document.getLength(), " = "	+ player3.getPlayerScore() + " | score ", null);
				Text text7 = new Text();
				text7.setText(" = " + player3.getPlayerScore() + " | score ");
				text7.setStyle(GenericParam.getLabelStyle3("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
				gameScreenTp.getChildren().add(text7);
				Platform.runLater(() -> gameScreenSp.setVvalue(1.0));
				displayCustomisedPlayerName(player4);
//				document.insertString(document.getLength(), " = " + player4.getPlayerScore() + SingeConstantes.CARRIAGE_RETURN, null);
//				gameScreenTp.setCaretPosition(document.getLength());
				Text text8 = new Text();
				text8.setText(" = " + player4.getPlayerScore() + Constants.CARRIAGE_RETURN);
				text8.setStyle(GenericParam.getLabelStyle3("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
				gameScreenTp.getChildren().add(text8);
				Platform.runLater(() -> gameScreenSp.setVvalue(1.0));
				break;
			}
			displayTopPlayer();
		} catch (Exception e) {
			System.out.println(e);
		}
		numberOfRound = 1;
	}

	private Joueur getTopPlayer(Joueur playerA, Joueur playerB) {
		if (playerA.getPlayerScore().compareTo(playerB.getPlayerScore()) < 0) {
			return playerA;
		} else {
			return playerB;
		}
	}

	private void displayTopPlayer() {
		Joueur topPlayer = getTopPlayer(player1, player2);

		switch (numberOfPlayers) {
		case 3:
			topPlayer = getTopPlayer(topPlayer, player3);
			break;
		case 4:
			topPlayer = getTopPlayer(topPlayer, player4);
			break;
		}

		topPlayerTF.setText(topPlayer.getPlayerName());
	}

	private void resetScores() {

		player1.setPlayerScore(0f);
		player2.setPlayerScore(0f);
		if (player3 != null) {
			player3.setPlayerScore(0f);
		}
		if (player4 != null) {
			player4.setPlayerScore(0f);
		}
	}

	// change current player to next player
	private void shiftToNextPlayer() {
		previousPlayer = currentPlayer;

		switch (numberOfPlayers) {
		case 2:
			if (currentPlayer.equals(player1)) {
				currentPlayer = player2;
			} else if (currentPlayer.equals(player2)) {
				currentPlayer = player1;
			}
			break;
		case 3:
			if (currentPlayer.equals(player1)) {
				currentPlayer = player2;
			} else if (currentPlayer.equals(player2)) {
				currentPlayer = player3;
			} else if (currentPlayer.equals(player3)) {
				currentPlayer = player1;
			}
			break;
		case 4:
			if (currentPlayer.equals(player1)) {
				currentPlayer = player2;
			} else if (currentPlayer.equals(player2)) {
				currentPlayer = player3;
			} else if (currentPlayer.equals(player3)) {
				currentPlayer = player4;
			} else if (currentPlayer.equals(player4)) {
				currentPlayer = player1;
			}
			break;
		}
	}

	private void getNextPlayer() {
		switch (numberOfPlayers) {
		case 2:
			if (currentPlayer.equals(player1)) {
				nextPlayer = player2;
			} else if (currentPlayer.equals(player2)) {
				nextPlayer = player1;
			}
			break;
		case 3:
			if (currentPlayer.equals(player1)) {
				nextPlayer = player2;
			} else if (currentPlayer.equals(player2)) {
				nextPlayer = player3;
			} else if (currentPlayer.equals(player3)) {
				nextPlayer = player1;
			}
			break;
		case 4:
			if (currentPlayer.equals(player1)) {
				nextPlayer = player2;
			} else if (currentPlayer.equals(player2)) {
				nextPlayer = player3;
			} else if (currentPlayer.equals(player3)) {
				nextPlayer = player4;
			} else if (currentPlayer.equals(player4)) {
				nextPlayer = player1;
			}
			break;
		}
	}

	private void setCurrentPlayerAction() {
		if (EnumTypeJoueur.HUMAIN.equals(currentPlayer.getPlayerType())) {
			// joueur 1 est un humain : demander la saisie d'une lettre et debloquer le
			// bouton
//			playerEntryTF.setEnabled(true);
//			validateBtn.setEnabled(true);

			playerEntryTF.setDisable(false);
			validateBtn.setDisable(false);
		} else {
			// joueur 1 est un robot : bloquer la saisie, jeu auto et bloquer le bouton
//			playerEntryTF.setEnabled(false);
//			validateBtn.setEnabled(false);

			playerEntryTF.setDisable(true);
			validateBtn.setDisable(true);
		}
	}

	private void robotAction() {
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
				playerEntryTF.setText(
						currentWordWithoutQuestionMark + getRandomAlphabetLetter(min, max, randomNumber, random));
			} else {
				robotRandomLetterEntry(min, max, randomNumber, random);
			}
		} else if (randomLevelChoice == 1) {
			robotQuestionMarkEntry(min, max, randomNumber, random);
		} else if (randomLevelChoice == 0) {
			robotLetterEntryForExistingWord(min, max, randomNumber, random);
		}

		if (runGame) {
			gameScreenAction(1000);
		}
		playerEntryTF.setText("");
	}

	private void robotQuestionMarkEntry(int min, int max, int randomNumber, Random random) {
		if (currentWord.length() > numberOfPlayers) {
			playerEntryTF.setText("?");
		} else {
			robotLetterEntryForExistingWord(min, max, randomNumber, random);
		}
	}

	private void robotRandomLetterEntry(int min, int max, int randomNumber, Random random) {
		playerEntryTF.setText(getRandomAlphabetLetter(min, max, randomNumber, random));
	}

	private void robotLetterEntryForExistingWord(int min, int max, int randomNumber, Random random) {
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
			if (!potentialWords.isEmpty()
					&& potentialWords.get(randomNumber).startsWith(currentWordWithoutQuestionMark)) {
				playerEntryTF.setText(potentialWords.get(randomNumber));
			} else {
				playerEntryTF.setText(
						currentWordWithoutQuestionMark + getRandomAlphabetLetter(min, max, randomNumber, random));
			}
		} else {
			playerEntryTF.setText(potentialLetter);
		}
	}

	private List<String> getRefinedDictionaryWords() {

		List<String> definedDictionaryWord = new ArrayList<>();

		if (currentWord.equals("")) {
			currentWord = questionnedWord;
		}

		if (dictionaryWords != null && !dictionaryWords.isEmpty()) {
			for (int i = 0; i < dictionaryWords.size(); i++) {
				String currentWordWithoutQuestionMark = StringUtils.replace(currentWord, "?", "");
				if (dictionaryWords.get(i) != null
						&& dictionaryWords.get(i).startsWith(currentWordWithoutQuestionMark)) {
					definedDictionaryWord.add(dictionaryWords.get(i));
				}
			}
		} else {
			JOptionPane.showInternalMessageDialog(null, Constants.MSG_DICT_ERROR, "Erreur de configuraton fichier",
					JOptionPane.ERROR_MESSAGE);
		}

		return definedDictionaryWord;
	}

	private String getRandomAlphabetLetter(int min, int max, int randomNumber, Random random) {
		char alphabetLetter[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
				'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

		max = alphabetLetter.length - 1;
		randomNumber = random.nextInt(max - min + 1) + min;
		// TODO à modifier par des logs
		System.out.println("random number : " + randomNumber);
		System.out.println("random Character : " + alphabetLetter[randomNumber]);
		return alphabetLetter[randomNumber] + "";
	}

	private String getSpaceAndTabulationBeforeText(int numberOfSpace, int numberOftabulation) {
		StringBuilder tabs = new StringBuilder();
		for (int i = 0; i < numberOftabulation; i++) {
			tabs.append("\t");
		}

		StringBuilder space = new StringBuilder();
		for (int i = 0; i < numberOfSpace; i++) {
			space.append(" ");
		}
		return tabs.append(space.toString()).toString();
	}

	private void displayPlayersOnGameStart() {
		String endOfString = " | ";
		try {
			String spaceAndTab = getSpaceAndTabulationBeforeText(10, 2);

			// espace pour center le texte ( à optimiser)
//			document.insertString(document.getLength(), spaceAndTab, null);
			Text text1 = new Text();
			text1.setText(spaceAndTab);
			text1.setStyle(GenericParam.getLabelStyle3("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
			gameScreenTp.getChildren().add(text1);
			Platform.runLater(() -> gameScreenSp.setVvalue(1.0));

			// The image must first be wrapped in a style
//			Style style = document.addStyle("StyleName", null);
//			ImageIcon singeIcon = CommonSingleton.getImageIcon("img/singe_03.png", 20, 20);
//			StyleConstants.setIcon(style, singeIcon);

			// Insert the image at the end of the text
//			document.insertString(document.getLength(), "*singe*", style);
			// Insertion du texte nombre joueur
//			document.insertString(document.getLength(), "**** Nombre de Joueurs : " + numberOfPlayers + " ****", null);
			Text text2 = new Text();
			text2.setText(Constants.CARRIAGE_RETURN + "\t\t\t\t **** Nombre de Joueurs : " + numberOfPlayers + " ****"
					+ Constants.CARRIAGE_RETURN);
			text2.setStyle(GenericParam.getLabelStyle3("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
			text2.setTextAlignment(TextAlignment.CENTER);
			gameScreenTp.getChildren().add(text2);
			Platform.runLater(() -> gameScreenSp.setVvalue(1.0));
			// Insertion image singe
//			document.insertString(document.getLength(), "*singe*" + SingeConstantes.CARRIAGE_RETURN, style);

//			ImageIcon starIcon = CommonSingleton.getImageIcon("img/etoile.png", 20, 20);
//			StyleConstants.setIcon(style, starIcon);

			// TODO espace devant affichage ( a optimiser avec l'ensemble des textes
			// similaires)
//			document.insertString(document.getLength(), SingeConstantes.FRONT_SPACE, style);
			// Insert the image at the end of the text
//			document.insertString(document.getLength(), "*etoile*", style);
			// texte prepartion liste des joueurs
//			document.insertString(document.getLength(), " Liste des joueurs : ", null);
			Text text3 = new Text();
			text3.setText(Constants.FRONT_SPACE + " Liste des joueurs : ");
			text3.setStyle(GenericParam.getLabelStyle3("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
			gameScreenTp.getChildren().add(text3);
			Platform.runLater(() -> gameScreenSp.setVvalue(1.0));

			// ajout affichage du nom des joueurs
			if (player1 != null && !player1.getPlayerName().isEmpty()) {
				displayCustomisedPlayerName(player1);
//				document.insertString(document.getLength(), endOfString, null);
				Text text4 = new Text();
				text4.setText(endOfString);
				text4.setStyle(GenericParam.getLabelStyle3("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
				gameScreenTp.getChildren().add(text4);
				Platform.runLater(() -> gameScreenSp.setVvalue(1.0));
			}
			if (player2 != null && !player2.getPlayerName().isEmpty()) {
				if (numberOfPlayers == 2) {
					endOfString = Constants.CARRIAGE_RETURN;
				}
				displayCustomisedPlayerName(player2);
//				document.insertString(document.getLength(), endOfString, null);
				Text text5 = new Text();
				text5.setText(endOfString);
				text5.setStyle(GenericParam.getLabelStyle3("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
				gameScreenTp.getChildren().add(text5);
				Platform.runLater(() -> gameScreenSp.setVvalue(1.0));
			}
			if (player3 != null && !player3.getPlayerName().isEmpty()) {
				if (numberOfPlayers == 3) {
					endOfString = Constants.CARRIAGE_RETURN;
				}
				displayCustomisedPlayerName(player3);
//				document.insertString(document.getLength(), endOfString, null);
				Text text6 = new Text();
				text6.setText(endOfString);
				text6.setStyle(GenericParam.getLabelStyle3("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
				gameScreenTp.getChildren().add(text6);
				Platform.runLater(() -> gameScreenSp.setVvalue(1.0));
			}
			if (player4 != null && !player4.getPlayerName().isEmpty()) {
				if (numberOfPlayers == 4) {
					endOfString = Constants.CARRIAGE_RETURN;
				}
				displayCustomisedPlayerName(player4);
//				document.insertString(document.getLength(), endOfString, null);
				Text text7 = new Text();
				text7.setText(endOfString);
				text7.setStyle(GenericParam.getLabelStyle3("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
				gameScreenTp.getChildren().add(text7);
				Platform.runLater(() -> gameScreenSp.setVvalue(1.0));
			}
//			document.insertString(document.getLength(),
//					SingeConstantes.FRONT_SPACE
//							+ "______________________________________________________________________"
//							+ SingeConstantes.CARRIAGE_RETURN,
//					null);
//			gameScreenTp.setCaretPosition(document.getLength());
			Text text8 = new Text();
			text8.setText(Constants.FRONT_SPACE + "___________________________________________________"
					+ Constants.CARRIAGE_RETURN);
			text8.setStyle(GenericParam.getLabelStyle3("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
			gameScreenTp.getChildren().add(text8);
			Platform.runLater(() -> gameScreenSp.setVvalue(1.0));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void displayCustomisedPlayerName(Joueur player) {
//		wordStyle = new SimpleAttributeSet();
//		StyleConstants.setForeground(wordStyle,
//				EnumTypeJoueur.ROBOT.equals(player.getPlayerType()) ? SingeConstantes.SINGE_BLUE
//						: SingeConstantes.SINGE_ORANGE);
//		StyleConstants.setBold(wordStyle, true);
//		document.insertString(document.getLength(), player.getPlayerName(), wordStyle);
		Text text = new Text(player.getPlayerName());
		text.setStyle(GenericParam.getLabelStyle3("16",
				(EnumTypeJoueur.ROBOT.equals(player.getPlayerType()) ? Constants.COLOR_BLUE : Constants.COLOR_ORANGE),
				Constants.FAMILLY_CALIBRI));
		gameScreenTp.getChildren().add(text);
		Platform.runLater(() -> gameScreenSp.setVvalue(1.0));

	}

	public void initializeDictionaryData() {
		try {
			FileInputStream dictionaryFile = new FileInputStream(dictionaryFileName);
			Scanner scanner = new Scanner(dictionaryFile);

			dictionaryWords = new ArrayList<String>();
			// renvoie true s'il y a une autre ligne à lire
			while (scanner.hasNextLine()) {
				String dictionaryWord = scanner.nextLine();
				if (dictionaryWord.length() > 2 && !dictionaryWord.contains(" ") && !dictionaryWord.contains(".")) {
					dictionaryWords.add(StringUtils.toRootUpperCase(dictionaryWord));
					// TODO à modifier par des logs
//					System.out.println(dictionaryWords.get(0));
				}
			}
			scanner.close();
		} catch (IOException e) {
			LOGGER.error(Constants.MSG_DICT_ERROR, e);
			JOptionPane.showInternalMessageDialog(null, Constants.MSG_DICT_ERROR, "Erreur de configuraton fichier",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void recordGameHistory() {

		String localDateTimeStr = StringUtils.replace(LocalDateTime.now().toString(), ":", "_");
		StringBuilder fileContent = new StringBuilder();
//		byte[] bytes = null;
//		int i = 0;
		for (Node node : gameScreenTp.getChildren()) {
			Text text = (Text) node;
			fileContent.append(text.getText());
		}

		FileOutputStream fos;
		try {
			fos = new FileOutputStream(getLogFileName() + "\\log_" + gameMode + "_" + localDateTimeStr + ".txt");
//			fos.write(gameScreenTp.getText().getBytes());
			fos.write(fileContent.toString().getBytes());
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO gerer,les exceptions
			System.out.println("Fichier non trouvé :" + e.toString());
			LOGGER.error("Fichier non trouvé :", e);
			JOptionPane.showInternalMessageDialog(null, Constants.MSG_FILE_NOT_FOUND_ERROR, "Fichier non trouvé :",
					JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			// TODO gerer,les exceptions
			System.out.println("Erreur à l'ouverture du fichier :" + e.toString());
			JOptionPane.showInternalMessageDialog(null, Constants.MSG_CONFIG_ERROR, "Erreur de configuraton fichier",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void actionPerformed(ActionEvent e, Stage primaryStage) {
		Object source = e.getSource();

		/******** Action Bouton : Menu Principale ********/
		if (source == validateBtn || source == playerEntryTF) {
			File dictionaryFile = new File(getDictionaryFileName());

			if (dictionaryFile.exists()) {
				gameScreenAction(0);
			} else {
				JOptionPane.showInternalMessageDialog(null, Constants.MSG_CONFIG_ERROR,
						"Erreur de configuraton fichier", JOptionPane.ERROR_MESSAGE);
			}
		} else if (source == replayBtn || source == gameScreenReplayMi) {
			currentPlayer = null;
			currentWord = "";
			runGame = true;
			new Thread(() -> startGame()).start();
		} else if (source == exitBtn || source == gameScreenExitMi) {
			recordGameHistory();
			JOptionPane.showConfirmDialog(null, "Merci d'avoir jouer! \r\n A bientôt !", "Fin partie Singe",
					JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
			primaryStage.close();
//			dispose();
		} else if (source == gameScreenConfigurationMi) {
//			new FenetreConfiguration(primaryStage, this).start(primaryStage);
			new FenetreConfiguration(primaryStage, this).start();
		} else if (source == gameScreenRecordHistoryMi) {
			recordGameHistory();
//			JOptionPane.showConfirmDialog(this, "La partie à été sauvegarder dans le dossier de log !",
//					"Sauvegarde fichier Partie Courante", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
			JOptionPane.showConfirmDialog(null, "La partie à été sauvegarder dans le dossier de log !",
					"Sauvegarde fichier Partie Courante", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
		} else if (source == gameScreenBackToMainMenuMi) {
			try {
				new FenetreMenuPrincipal(this).start(primaryStage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
//			dispose();
			primaryStage.close();
		}
	}

	public String getDictionaryFileName() {
		return dictionaryFileName;
	}

	public void setDictionaryFileName(String dictionaryFileName) {
		this.dictionaryFileName = dictionaryFileName;
	}

	public String getLogFileName() {
		return logFileName;
	}

	public void setLogFileName(String logFileName) {
		this.logFileName = logFileName;
	}

	public FenetreMenuPrincipal getMenuPrincipalFrame() {
		return fenetreMenuPrincipal;
	}

	public EnumNiveauRobot getRobotsLevel() {
		return robotsLevel;
	}

	public void setRobotsLevel(EnumNiveauRobot robotsLevel) {
		this.robotsLevel = robotsLevel;
	}

	public EnumModeJeu getGameMode() {
		return gameMode;
	}

	public void setGameMode(EnumModeJeu gameMode) {
		this.gameMode = gameMode;
	}

	public int getNumberOfPlayer() {
		return numberOfPlayers;
	}

	public void setNumberOfPlayer(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}

	public Joueur getPlayer1() {
		return player1;
	}

	public void setPlayer1(Joueur player1) {
		this.player1 = player1;
	}

	public Joueur getPlayer2() {
		return player2;
	}

	public void setPlayer2(Joueur player2) {
		this.player2 = player2;
	}

	public Joueur getPlayer3() {
		return player3;
	}

	public void setPlayer3(Joueur player3) {
		this.player3 = player3;
	}

	public Joueur getPlayer4() {
		return player4;
	}

	public void setPlayer4(Joueur player4) {
		this.player4 = player4;
	}

}
