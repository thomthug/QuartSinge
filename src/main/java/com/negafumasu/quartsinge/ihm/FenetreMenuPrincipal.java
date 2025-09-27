package com.negafumasu.quartsinge.ihm;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.negafumasu.quartsinge.Constants;
import com.negafumasu.quartsinge.GenericParam;
import com.negafumasu.quartsinge.data.EnumModeJeu;
import com.negafumasu.quartsinge.data.EnumNiveauRobot;
import com.negafumasu.quartsinge.data.EnumTypeJoueur;
import com.negafumasu.quartsinge.data.Joueur;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FenetreMenuPrincipal extends Application {
//public class FenetreMenuPrincipal {

	private static final Logger LOGGER = LogManager.getLogger(FenetreMenuPrincipal.class);

	// Menu Principale
	private Button playBtn;
	private Button configurationBtn;
	private TextField player1Tf;
	private ComboBox<EnumTypeJoueur> player1TypeCb;
	private TextField player2Tf;
	private ComboBox<EnumTypeJoueur> player2TypeCb;
	private TextField player3Tf;
	private ComboBox<EnumTypeJoueur> player3TypeCb;
	private TextField player4Tf;
	private ComboBox<EnumTypeJoueur> player4TypeCb;
	//////////////////////////////////////
	private ComboBox<Integer> numberOfPlayersCb;

	private Label player1Label;
	private Label player2Label;
	private Label player3Label;
	private Label player4Label;

	// Joueurs
	private Joueur player1;
	private Joueur player2;
	private Joueur player3;
	private Joueur player4;

	// Autres
	private EnumNiveauRobot robotsLevel = EnumNiveauRobot.DIFFICILE;
	private String dictionaryFileName;
	private String logFileName;
	private EnumModeJeu gameMode = EnumModeJeu.CLASSIQUE;
	private int numberOfPlayers;

	FenetreEcranJeu ecranJeu;
	
//	ComboBox<String> modeComboBox = new ComboBox<>();

	public FenetreMenuPrincipal() {
		// TODO Auto-generated constructor stub
	}
	
	public FenetreMenuPrincipal(FenetreEcranJeu fenetreEcranJeu) {
		this.ecranJeu = fenetreEcranJeu;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
//	public void start(Stage primaryStage) {

//		modeComboBox.getItems().addAll("Local", "En ligne");
//		modeComboBox.setValue("Local"); // Par défaut
//		modeComboBox.setOnAction(event -> {
//		    String selectedMode = modeComboBox.getValue();
//		    if ("En ligne".equals(selectedMode)) {
//		        // Activer les options pour le jeu en ligne
//		        System.out.println("Mode en ligne sélectionné !");
//		    } else {
//		        // Activer les options pour le jeu local
//		        System.out.println("Mode local sélectionné !");
//		    }
//		});
		
		if (ecranJeu != null) {
			setNumberOfPlayers(ecranJeu.getNumberOfPlayer());
			setGameMode(ecranJeu.getGameMode());
			setRobotsLevel(ecranJeu.getRobotsLevel());
			setDictionaryFileName(ecranJeu.getDictionaryFileName());
			setLogFileName(ecranJeu.getLogFileName());

			// recuperation des joueurs
			setPlayer1(ecranJeu.getPlayer1());
			setPlayer2(ecranJeu.getPlayer2());
			setPlayer3(ecranJeu.getPlayer3());
			setPlayer4(ecranJeu.getPlayer4());
		}

		Scene menuScene = new Scene(initializeView(ecranJeu, 0, primaryStage), 500, 720);
		primaryStage.setScene(menuScene);
		primaryStage.setTitle("Quart Singe | Menu Principal");
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/com/negafumasu/img/singe_04.png")));
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	private VBox initializeView(FenetreEcranJeu ecranJeu, int nbJoueurs, Stage primaryStage) {
		LOGGER.info("===========Menu Principal=========");
		getFileParametersValues();

		VBox mainBox = new VBox();
		mainBox.setStyle(Constants.FX_BACKGROUND_GRAY);

		HBox titleBox = new HBox();
		Label title = new Label(" Bienvenue dans le jeu : Quart de singe ! ");
		title.setStyle(GenericParam.getLabelStyle2("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
		title.setPadding(new Insets(10, 10, 10, 10));
		titleBox.setAlignment(Pos.CENTER);
		titleBox.getChildren().add(title);

		HBox rulesBox = new HBox();
		Label rules = new Label("    Un joueur propose une lettre. Le suivant ajoute une lettre à la première.\r\n"
				+ "    Ainsi de suite jusqu'à former un mot.\r\n"
				+ "    Chacun peut demander au précédent à quel mot il pense en tapant \"?\".\r\n"
				+ "    Si le joueur interrogé a ecrit une lettre sans penser à un mot, il est quart de singe!\r\n"
				+ "    De même pour le joueur qui vient après celui qui a terminé le mot.\r\n"
				+ "    Pour se consoler c'est lui qui relance le jeu.\r\n"
				+ "    On a perdu lorsque après avoir été quart de singe, on devient demi de singe, \r\n"
				+ "    trois quarts de singe et enfin singe complet. ");
		rules.setStyle(GenericParam.getLabelStyle2("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
		rulesBox.setAlignment(Pos.CENTER);
		rulesBox.setPadding(new Insets(0, 0, 10, 0));
		rulesBox.getChildren().add(rules);

		HBox playerNumberBox = new HBox();
		Label playerNumber = new Label(" ********** 2 à 4 joueurs ********** ");
		playerNumber.setStyle(GenericParam.getLabelStyle2("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
		playerNumberBox.setAlignment(Pos.CENTER);
		playerNumberBox.setPadding(new Insets(0, 0, 10, 0));
		playerNumberBox.getChildren().add(playerNumber);

		HBox gameExplainBox = new HBox();
		Label gameExplain = new Label("    Pour lancer une partie, veuillez saisir le nom des joueurs.\r\n"
				+ "    L'ordre de jeu est défini selon le numero du joueur : J1 puis J2 etc. ");
		gameExplain.setStyle(GenericParam.getLabelStyle2("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
		gameExplainBox.setAlignment(Pos.CENTER);
		gameExplainBox.setPadding(new Insets(0, 0, 10, 0));
		gameExplainBox.getChildren().add(gameExplain);

		HBox buttonExplainBox = new HBox();
		Label buttonExplain = new Label("    Jouer :  \r\n"
				+ "        - Appuyez sur le bouton orange avec un triangle au centre.\r\n" + "\r\n"
				+ "    Parametrer : \r\n" + "        - Appuyer sur le bouton bleu avec l'ecrou pour modifier \r\n"
				+ "        ou consulter les parametres de jeu. ");
		buttonExplain.setStyle(GenericParam.getLabelStyle2("16", Constants.COLOR_WHITE, Constants.FAMILLY_CALIBRI));
		buttonExplainBox.setAlignment(Pos.CENTER);
		buttonExplainBox.setPadding(new Insets(0, 0, 10, 0));
		buttonExplainBox.getChildren().add(buttonExplain);

		HBox buttonsBox = new HBox();
		// Charger l'image
		Image playBtnImage = new Image(getClass().getResourceAsStream("/com/negafumasu/img/jouer_03.png"));
		// Créer une ImageView avec l'image
		ImageView playBtnImageView = new ImageView(playBtnImage);
		playBtnImageView.setFitWidth(30); // Largeur de l'image
		playBtnImageView.setFitHeight(30); // Hauteur de l'image
		playBtn = new Button("", playBtnImageView);
		playBtn.setPrefSize(80, 65);
		playBtn.setPadding(new Insets(0, 20, 0, 20));
//		BackgroundFill playBtnBF = new BackgroundFill(Color.ORANGE, new CornerRadii(5),
//				new Insets(0.0,0.0,0.0,0.0));// or null for the padding
//		playBtn.setBackground(new Background(playBtnBF));
		/***/
		String playBtnStyle = GenericParam.getButtonStyle("14", Constants.COLOR_ORANGE, "1em", Constants.COLOR_WHITE,
				"1 1 1 1", Constants.COLOR_WHITE, "1em", "0");
		String playBtnStyleHover = GenericParam.getButtonStyle("14", Constants.COLOR_GOLD, "1em",
				Constants.COLOR_BLACK, "1 1 1 1", Constants.COLOR_BLACK, "1em", "0");

		playBtn.setStyle(playBtnStyle);
		playBtn.setOnMouseEntered(e -> playBtn.setStyle(playBtnStyleHover));
		playBtn.setOnMouseExited(e -> playBtn.setStyle(playBtnStyle));
		/***/
		// Configurer l'action du bouton
		playBtn.setOnAction(e -> actionPerformed(e, primaryStage));
		playBtn.setTooltip(new Tooltip("Jouer une partie"));

		// Charger l'image
		Image configBtnImage = new Image(getClass().getResourceAsStream("/com/negafumasu/img/ecrou_05.png"));
		// Créer une ImageView avec l'image
		ImageView configBtnImageView = new ImageView(configBtnImage);
		configBtnImageView.setFitWidth(30); // Largeur de l'image
		configBtnImageView.setFitHeight(30); // Hauteur de l'image
		configurationBtn = new Button("", configBtnImageView);
		configurationBtn.setPrefSize(80, 65);
		configurationBtn.setPadding(new Insets(0, 20, 0, 20));
//		BackgroundFill configBtnBF = new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(5),
//				new Insets(0.0,0.0,0.0,0.0));// or null for the padding
//		playBtn.setBackground(new Background(configBtnBF));
		/***/
		String configBtnStyle = GenericParam.getButtonStyle("14", Constants.COLOR_BLUE, "1em", Constants.COLOR_WHITE,
				"1 1 1 1", Constants.COLOR_WHITE, "1em", "0");
		String configBtnStyleHover = GenericParam.getButtonStyle("14", Constants.COLOR_LIGHTBLUE, "1em",
				Constants.COLOR_BLACK, "1 1 1 1", Constants.COLOR_BLACK, "1em", "0");

		configurationBtn.setStyle(configBtnStyle);
		configurationBtn.setOnMouseEntered(e -> configurationBtn.setStyle(configBtnStyleHover));
		configurationBtn.setOnMouseExited(e -> configurationBtn.setStyle(configBtnStyle));
		/***/
		// Configurer l'action du bouton
		configurationBtn.setOnAction(e -> actionPerformed(e, primaryStage));
		configurationBtn.setTooltip(new Tooltip("Parametres de jeu"));

		Label espace = new Label();
		espace.setPrefSize(50, 30);
		
		buttonsBox.setAlignment(Pos.CENTER);
		buttonsBox.setPadding(new Insets(0, 0, 10, 0));
		buttonsBox.getChildren().addAll(playBtn, espace, configurationBtn);

		VBox playerSelectionBox = initializePlayerSelection(ecranJeu, nbJoueurs);
		playerSelectionBox.setAlignment(Pos.CENTER);

		mainBox.getChildren().addAll(titleBox, rulesBox, playerNumberBox, gameExplainBox, buttonExplainBox, buttonsBox,
				playerSelectionBox);

		return mainBox;
	}

	private VBox initializePlayerSelection(FenetreEcranJeu ecranJeu, int numberPlayers) {

		VBox playerSelectionBox = new VBox();
		VBox playerSelectionBorderBox = new VBox();

		// Créer un TitledPane avec un titre
		TitledPane titledPane = new TitledPane("Choix des Joueurs :", playerSelectionBorderBox);
		titledPane.setStyle(GenericParam.getLabelStyle2("16", Constants.COLOR_BLACK, Constants.FAMILLY_CALIBRI));
		titledPane.setAlignment(Pos.CENTER);
		titledPane.setCollapsible(false); // Empêche de réduire/étendre

		// Contenu du FieldSet
		Label numberOfPlayerLabel = new Label("Nombre de Joueur : ");
		numberOfPlayerLabel.setPrefSize(455, 30);
		numberOfPlayerLabel
				.setStyle(GenericParam.getLabelStyle2("16", Constants.COLOR_BLACK, Constants.FAMILLY_CALIBRI));

		numberOfPlayersCb = new ComboBox<>();
		numberOfPlayersCb.setPrefSize(135, 30);

		player1Label = new Label("Joueur 1 : ");
		player1Label.setPrefSize(240, 30);
		player1Label.setStyle(GenericParam.getLabelStyle2("16", Constants.COLOR_BLACK, Constants.FAMILLY_CALIBRI));
		player1Tf = new TextField(ecranJeu != null ? ecranJeu.getPlayer1().getPlayerName() : "Joueur1");
		player1Tf.setPrefSize(240, 30);
		player1Tf.setStyle(GenericParam.getLabelStyle2("16", Constants.COLOR_BLACK, Constants.FAMILLY_CALIBRI));
		player1TypeCb = initPlayerTypeComboBox(
				ecranJeu != null ? ecranJeu.getPlayer1().getPlayerType() : EnumTypeJoueur.HUMAIN);
		player1TypeCb.setOnAction(e -> numberChanged(e));
		player1Tf.setEditable(EnumTypeJoueur.ROBOT.equals(player1TypeCb.getValue()) ? false : true);

		player2Label = new Label("Joueur 2 : ");
		player2Label.setPrefSize(240, 30);
		player2Label.setStyle(GenericParam.getLabelStyle2("16", Constants.COLOR_BLACK, Constants.FAMILLY_CALIBRI));
		player2Tf = new TextField(ecranJeu != null ? ecranJeu.getPlayer2().getPlayerName() : "Robot2");
		player2Tf.setPrefSize(240, 30);
		player2Tf.setStyle(GenericParam.getLabelStyle2("16", Constants.COLOR_BLACK, Constants.FAMILLY_CALIBRI));
		player2TypeCb = initPlayerTypeComboBox(
				ecranJeu != null ? ecranJeu.getPlayer2().getPlayerType() : EnumTypeJoueur.ROBOT);
		player2TypeCb.setOnAction(e -> numberChanged(e));
		player2Tf.setEditable(EnumTypeJoueur.ROBOT.equals(player2TypeCb.getValue()) ? false : true);

		player3Label = new Label("Joueur 3 : ");
		player3Label.setPrefSize(240, 30);
		player3Label.setStyle(GenericParam.getLabelStyle2("16", Constants.COLOR_BLACK, Constants.FAMILLY_CALIBRI));
		player3Tf = new TextField(
				ecranJeu != null && ecranJeu.getPlayer3() != null ? ecranJeu.getPlayer3().getPlayerName() : "Robot3");
		player3Tf.setPrefSize(240, 30);
		player3Tf.setStyle(GenericParam.getLabelStyle2("16", Constants.COLOR_BLACK, Constants.FAMILLY_CALIBRI));
		player3TypeCb = initPlayerTypeComboBox(
				ecranJeu != null && ecranJeu.getPlayer3() != null ? ecranJeu.getPlayer3().getPlayerType()
						: EnumTypeJoueur.ROBOT);
		player3TypeCb.setOnAction(e -> numberChanged(e));
		player3Tf.setEditable(EnumTypeJoueur.ROBOT.equals(player3TypeCb.getValue()) ? false : true);

		player4Label = new Label("Joueur 4 : ");
		player4Label.setPrefSize(240, 30);
		player4Label.setStyle(GenericParam.getLabelStyle2("16", Constants.COLOR_BLACK, Constants.FAMILLY_CALIBRI));
		player4Tf = new TextField(
				ecranJeu != null && ecranJeu.getPlayer4() != null ? ecranJeu.getPlayer4().getPlayerName() : "Robot4");
		player4Tf.setPrefSize(240, 30);
		player4Tf.setStyle(GenericParam.getLabelStyle2("16", Constants.COLOR_BLACK, Constants.FAMILLY_CALIBRI));
		player4TypeCb = initPlayerTypeComboBox(
				ecranJeu != null && ecranJeu.getPlayer4() != null ? ecranJeu.getPlayer4().getPlayerType()
						: EnumTypeJoueur.ROBOT);
		player4TypeCb.setOnAction(e -> numberChanged(e));
		player4Tf.setEditable(EnumTypeJoueur.ROBOT.equals(player4TypeCb.getValue()) ? false : true);

		///////
		HBox numberOfplayerBox = new HBox();
		numberOfplayerBox.getChildren().add(numberOfPlayerLabel);
		numberOfplayerBox.getChildren().add(numberOfPlayersCb);
		playerSelectionBorderBox.getChildren().add(numberOfplayerBox);
		//// joueurs
		HBox player1Box = new HBox();
		player1Box.getChildren().add(player1Label);
		player1Box.getChildren().add(player1Tf);
		player1Box.getChildren().add(player1TypeCb);
		playerSelectionBorderBox.getChildren().add(player1Box);
		HBox player2Box = new HBox();
		player2Box.getChildren().add(player2Label);
		player2Box.getChildren().add(player2Tf);
		player2Box.getChildren().add(player2TypeCb);
		playerSelectionBorderBox.getChildren().add(player2Box);
		HBox player3Box = new HBox();
		player3Box.getChildren().add(player3Label);
		player3Box.getChildren().add(player3Tf);
		player3Box.getChildren().add(player3TypeCb);
		playerSelectionBorderBox.getChildren().add(player3Box);
		HBox player4Box = new HBox();
		player4Box.getChildren().add(player4Label);
		player4Box.getChildren().add(player4Tf);
		player4Box.getChildren().add(player4TypeCb);
		playerSelectionBorderBox.getChildren().add(player4Box);

		playerSelectionBox.getChildren().add(titledPane);
		///////////// eviter un appel intempestif du listener
		ObservableList<Integer> nombreJoueur = FXCollections.observableArrayList();
		nombreJoueur.addAll(2, 3, 4);
		numberOfPlayersCb.setItems(nombreJoueur);
		numberOfPlayersCb.setValue(numberPlayers > 0 ? numberPlayers : 2);
		numberOfPlayersCb.setStyle(GenericParam.getLabelStyle2("16", Constants.COLOR_BLACK, Constants.FAMILLY_CALIBRI));
		numberOfPlayersCb.setOnAction(e -> numberChanged(e));
		setPlayersAvailibility();
		changePlayerType(player1TypeCb, player1Tf, player1Label, 1);
		changePlayerType(player2TypeCb, player2Tf, player2Label, 2);
		changePlayerType(player3TypeCb, player3Tf, player3Label, 3);
		changePlayerType(player4TypeCb, player4Tf, player4Label, 4);

		return playerSelectionBox;
	}

	public void numberChanged(ActionEvent e) {
		Object source = e.getSource();
		if (source == numberOfPlayersCb) {
			setPlayersAvailibility();
		} else if (source == player1TypeCb) {
			changePlayerType(player1TypeCb, player1Tf, player1Label, 1);
		} else if (source == player2TypeCb) {
			changePlayerType(player2TypeCb, player2Tf, player2Label, 2);
		} else if (source == player3TypeCb) {
			changePlayerType(player3TypeCb, player3Tf, player3Label, 3);
		} else if (source == player4TypeCb) {
			changePlayerType(player4TypeCb, player4Tf, player4Label, 4);
		}
	}

	private ComboBox<EnumTypeJoueur> initPlayerTypeComboBox(EnumTypeJoueur defaultPlayerType) {
		ComboBox<EnumTypeJoueur> playerCb = new ComboBox<>();
		playerCb.setPrefSize(190, 30);
		playerCb.setStyle(GenericParam.getLabelStyle2("16", Constants.COLOR_BLACK, Constants.FAMILLY_CALIBRI));

		ObservableList<EnumTypeJoueur> typesJoueur = FXCollections.observableArrayList();
		typesJoueur.addAll(EnumTypeJoueur.values());
		playerCb.setItems(typesJoueur);

		playerCb.setValue(defaultPlayerType);
		return playerCb;
	}

	private void getFileParametersValues() {
		File configurationFolder = new File(Constants.SINGE_CONFIG_FOLDER);
		File configurationFile = new File(Constants.SINGE_CONFIG_FOLDER + File.separator + Constants.SINGE_CONFIG_FILE);

		if (!configurationFolder.exists()) {
			configurationFolder.mkdirs();
		}
		if (!configurationFile.exists()) {
			try {
				configurationFile.createNewFile();
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
				JOptionPane.showInternalMessageDialog(null, Constants.MSG_CONFIG_ERROR,
						"Erreur de configuraton fichier", JOptionPane.ERROR_MESSAGE);
			}
		}
		try {
			// Le fichier d'entrée
			Scanner scanner = new Scanner(configurationFile);

			// renvoie true s'il y a une autre ligne à lire
			while (scanner.hasNextLine()) {
				String config = scanner.nextLine();

				switch (config.split("=")[0]) {
				case "modeJeu":
					setGameMode(EnumModeJeu.valueOf(config.split("=")[1]));
					System.out.println(config.split("=")[1]);
					break;
				case "niveauRobot":
					setRobotsLevel(EnumNiveauRobot.valueOf(config.split("=")[1]));
					System.out.println(config.split("=")[1]);
					break;
				case "fichierDico":
					setDictionaryFileName(config.split("=")[1]);
					System.out.println(config.split("=")[1]);
					break;
				case "fichierLog":
					setLogFileName(config.split("=")[1]);
					System.out.println(config.split("=")[1]);
					break;
				default:
					break;
				}
			}
			scanner.close();
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
			JOptionPane.showInternalMessageDialog(null, Constants.MSG_CONFIG_ERROR, "Erreur de configuraton fichier",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void runGameAction(Stage primaryStage) {
		File dictionaryFile = new File(getDictionaryFileName());

		if (dictionaryFile.exists()) {
			System.out.println("Button : jouer");
			createPlayerByName();
			diplayPlayers();
			afficherFenetreEcranJeu(primaryStage);
//			dispose();
		} else {
			JOptionPane.showInternalMessageDialog(null, Constants.MSG_DICT_ERROR, "Erreur de configuraton fichier",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void afficherFenetreEcranJeu(Stage primaryStage) {
		new FenetreEcranJeu(primaryStage, this);
	}

//	private void afficherGestionDecks(Stage primaryStage) {
//		new GestionDecks(primaryStage);
//	}

	private void createPlayerByName() {
		// J1
		player1 = new Joueur();
		EnumTypeJoueur player1Type = (EnumTypeJoueur) player1TypeCb.getValue();
		player1.setPlayerName(
				!player1Tf.getText().equals("") ? player1Tf.getText() : getPlayerDefaultName(1, player1Type));
		player1.setPlayerType(player1Type);
		// J2
		player2 = new Joueur();
		EnumTypeJoueur player2Type = (EnumTypeJoueur) player2TypeCb.getValue();
		player2.setPlayerName(
				!player2Tf.getText().equals("") ? player2Tf.getText() : getPlayerDefaultName(2, player2Type));
		player2.setPlayerType(player2Type);
		// J3
		player3 = new Joueur();
		EnumTypeJoueur player3Type = (EnumTypeJoueur) player3TypeCb.getValue();
		player3.setPlayerName(
				!player3Tf.getText().equals("") ? player3Tf.getText() : getPlayerDefaultName(3, player3Type));
		player3.setPlayerType(player3Type);
		// J4
		player4 = new Joueur();
		EnumTypeJoueur player4Type = (EnumTypeJoueur) player4TypeCb.getValue();
		player4.setPlayerName(
				!player4Tf.getText().equals("") ? player4Tf.getText() : getPlayerDefaultName(4, player4Type));
		player4.setPlayerType(player4Type);

		// il y a au minimum 2 joueurs
		switch (numberOfPlayers) {
		case 2:
			player3 = null;
			player4 = null;
			break;
		case 3:
			player4 = null;
			break;
		}
	}

	private String getPlayerDefaultName(int playerNumber, EnumTypeJoueur playerType) {
		switch (playerType) {
		case HUMAIN:
			return "Joueur" + playerNumber;
		case ROBOT:
			return "Robot" + playerNumber;
		default:
			return "JoueurInconnu";
		}
	}

	private void diplayPlayers() {
		StringBuilder stringBuilder = new StringBuilder();

		if (player1 != null && !player1.getPlayerName().isEmpty()) {
			stringBuilder.append(player1.getPlayerName() + "; ");
		}
		if (player2 != null && !player2.getPlayerName().isEmpty()) {
			stringBuilder.append(player2.getPlayerName() + "; ");
		}
		if (player3 != null && !player3.getPlayerName().isEmpty()) {
			stringBuilder.append(player3.getPlayerName() + "; ");
		}
		if (player4 != null && !player4.getPlayerName().isEmpty()) {
			stringBuilder.append(player4.getPlayerName() + "; ");
		}

		if (!stringBuilder.toString().isEmpty()) {
			System.out.println(" liste joueur : " + stringBuilder.toString());
		}
	}

	public void actionPerformed(ActionEvent e, Stage primaryStage) {
		Object source = e.getSource();

		/******** Action Bouton : Menu Principale ********/
		if (source == playBtn) {
			runGameAction(primaryStage);
		} else if (source == configurationBtn) {
//			new FenetreConfiguration(primaryStage, this).start(primaryStage);
			new FenetreConfiguration(primaryStage, this).start();
		}
	}

	/**
	 * Change player type when choosing type in the comboxBox
	 * 
	 * @param playerTypeCb
	 * @param playerTf
	 * @param playerLabel
	 */
	private void changePlayerType(ComboBox<EnumTypeJoueur> playerTypeCb, TextField playerTf, Label playerLabel,
			int playerNumber) {
		EnumTypeJoueur playerType = (EnumTypeJoueur) playerTypeCb.getValue();
		playerTf.setText(
				EnumTypeJoueur.ROBOT.equals(playerType) ? getPlayerDefaultName(playerNumber, EnumTypeJoueur.ROBOT)
						: getPlayerDefaultName(playerNumber, EnumTypeJoueur.HUMAIN));

		playerTf.setEditable(EnumTypeJoueur.ROBOT.equals(playerType) ? false : true);

		playerLabel.setStyle(GenericParam.getLabelStyle2("16",
				(EnumTypeJoueur.ROBOT.equals(playerType) ? Constants.COLOR_BLUE : Constants.COLOR_ORANGE),
				Constants.FAMILLY_CALIBRI));
	}

	/**
	 * Change l'accessibilité des combobox des joueurs 3 et 4.
	 */
	private void setPlayersAvailibility() {
		switch (numberOfPlayersCb.getValue()) {
		case 2:
			player3Tf.setDisable(true);
			player3TypeCb.setDisable(true);
			player4Tf.setDisable(true);
			player4TypeCb.setDisable(true);
			numberOfPlayers = 2;
			break;
		case 3:
			player3Tf.setDisable(false);
			player3TypeCb.setDisable(false);
			player4Tf.setDisable(true);
			player4TypeCb.setDisable(true);
			numberOfPlayers = 3;
			break;
		case 4:
			player3Tf.setDisable(false);
			player3TypeCb.setDisable(false);
			player4Tf.setDisable(false);
			player4TypeCb.setDisable(false);
			numberOfPlayers = 4;
			break;
		default:
			break;
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

	public EnumNiveauRobot getRobotsLevel() {
		return robotsLevel;
	}

	public void setRobotsLevel(EnumNiveauRobot robotsLevel) {
		this.robotsLevel = robotsLevel;
	}

	public EnumModeJeu getModeJeu() {
		return gameMode;
	}

	public void setGameMode(EnumModeJeu gameMode) {
		this.gameMode = gameMode;
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

	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}

	public FenetreEcranJeu getEcranJeu() {
		return ecranJeu;
	}

	public void setEcranJeu(FenetreEcranJeu ecranJeu) {
		this.ecranJeu = ecranJeu;
	}
}
