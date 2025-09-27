package com.negafumasu.quartsinge.ihm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.negafumasu.quartsinge.Constants;
import com.negafumasu.quartsinge.GenericParam;
import com.negafumasu.quartsinge.data.EnumModeJeu;
import com.negafumasu.quartsinge.data.EnumNiveauRobot;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FenetreConfiguration {
	private static final Logger LOGGER = LogManager.getLogger(FenetreConfiguration.class);
	/** Constants **/

	private static final int COMPONENTS_HEIGHT = 30;

	// Application
	private FenetreMenuPrincipal menuPrincipal;
	private FenetreEcranJeu ecranJeu;

	private EnumNiveauRobot robotsLevel;
	private EnumModeJeu gameMode;

	// booleen pour savoir si l'on vient d'ouvrir la fenetre
	private boolean isWindowInitialized;

	/** graphics **/
	private TextArea configurationExplainationTa;
	private ComboBox<EnumModeJeu> gameModeCb;
	private Label gameModeLabel;
	private ComboBox<EnumNiveauRobot> robotsLevelCb;
	private Label robotsLevelLabel;
	private Button dictionaryFileBtn;
	private TextField dictionaryFileTF;
	private Label dictionaryFileLabel;
	private Button logFileBtn;
	private TextField logFileTF;
	private Label logFileLabel;
	private Button recordBtn;
	//////////////////////////////

	/*** config **/
	private String robotLevelConfiguration;
	private String dictionaryFileConfiguration;
	private String logFileConfiguration;
	private String gameModeConfiguration;
	
	public FenetreConfiguration(Stage primaryStage, FenetreMenuPrincipal menuPrincipal) {
		
		/** recuperation des valeurs de parametres dans singe **/
		this.menuPrincipal = menuPrincipal;
		setGameMode(menuPrincipal.getModeJeu());
		setRobotsLevel(menuPrincipal.getRobotsLevel());
		setDictionaryConfigurationFile(menuPrincipal.getDictionaryFileName());
		setLogConfigurationFile(menuPrincipal.getLogFileName());
		LOGGER.info("************* ! config nivRobots = " + robotsLevel + " ! ************* ");
		LOGGER.info("************* ! config modeJeu = " + gameMode + " ! ************* ");

//		start(primaryStage);
	}
	
	public FenetreConfiguration(Stage primaryStage, FenetreEcranJeu ecranJeu) {

		/** recuperation des valeurs de parametres dans singe **/
		this.ecranJeu = ecranJeu;
		setGameMode(ecranJeu.getGameMode());
		setRobotsLevel(ecranJeu.getRobotsLevel());
		setDictionaryConfigurationFile(ecranJeu.getDictionaryFileName());
		setLogConfigurationFile(ecranJeu.getLogFileName());
		LOGGER.info("************* ! config nivRobots = " + robotsLevel + " ! ************* ");
		LOGGER.info("************* ! config modeJeu = " + gameMode + " ! ************* ");

//		start(primaryStage);
	}
	
	public void start() {
		Stage primaryStage = new Stage();
		VBox mainBox = new VBox(10);
		mainBox = initializeView(primaryStage);
		
		Scene menuScene = new Scene(mainBox, 520, 600);
		primaryStage.setScene(menuScene);
		primaryStage.setTitle("Quart Singe | Configuration");
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/com/negafumasu/img/ecrou_04.png")));
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	private VBox initializeView(Stage primaryStage) {
		
		VBox parametersBox = new VBox(10);
		
		/** chemin dossier log **/
//		ImageIcon folderIcon = CommonSingleton.getImageIcon("img/dossier_01.png", 25, 25);
//		logFileBtn = new Button(folderIcon);
//		logFileBtn.setToolTipText("choisir le dossier de fichier log");
//		logFileBtn.setSize(new Dimension(35, COMPONENTS_HEIGHT));
//		logFileBtn.setPreferredSize(logFileBtn.getSize());
//		logFileBtn.setFont(new Font(SingeConstantes.CALIBRI_POLICE, Font.BOLD, 20));
//		logFileBtn.setBackground(SingeConstantes.SINGE_ORANGE);
//		logFileBtn.addActionListener(this);
		// Charger l'image
		Image logFileBtnImage = new Image(getClass().getResourceAsStream("/com/negafumasu/img/dossier_01.png"));
		// Créer une ImageView avec l'image
		ImageView logFileBtnImageView = new ImageView(logFileBtnImage);
		logFileBtnImageView.setFitWidth(20); // Largeur de l'image
		logFileBtnImageView.setFitHeight(20); // Hauteur de l'image
		logFileBtn = new Button("", logFileBtnImageView);
		logFileBtn.setPrefSize(40, 35);
		logFileBtn.setPadding(new Insets(0, 0, 0, 0));
		/***/
		String logFileBtnStyle = GenericParam.getButtonStyle("14", Constants.COLOR_ORANGE, "0.5em", Constants.COLOR_WHITE,
				"1 1 1 1", Constants.COLOR_WHITE, "0.5em", "0");
		String logFileBtnStyleHover = GenericParam.getButtonStyle("14", Constants.COLOR_GOLD, "0.5em",
				Constants.COLOR_BLACK, "1 1 1 1", Constants.COLOR_BLACK, "0.5em", "0");

		logFileBtn.setStyle(logFileBtnStyle);
		logFileBtn.setOnMouseEntered(e -> logFileBtn.setStyle(logFileBtnStyleHover));
		logFileBtn.setOnMouseExited(e -> logFileBtn.setStyle(logFileBtnStyle));
		/***/
		// Configurer l'action du bouton
		logFileBtn.setOnAction(e -> actionPerformed(e, primaryStage));
		logFileBtn.setTooltip(new Tooltip("choisir le dossier de fichier log"));

		logFileTF = new TextField(getLogFileName());
		logFileTF.setPrefSize(365, COMPONENTS_HEIGHT);
//		logFileTF.setPreferredSize(logFileTF.getSize());
//		logFileTF.setFont(new Font(SingeConstantes.CALIBRI_POLICE, Font.BOLD, 20));

		logFileLabel = new Label("* Fichier log : ");
		logFileLabel.setPrefSize(103, COMPONENTS_HEIGHT);
//		logFileLabel.setFont(new Font(SingeConstantes.CALIBRI_POLICE, Font.BOLD, 20));

		/** chemin fichier dico **/
//		dictionaryFileBtn = new Button(folderIcon);
//		dictionaryFileBtn.setToolTipText("choisir le dossier fichier de dictionnaire");
//		dictionaryFileBtn.setSize(new Dimension(35, COMPONENTS_HEIGHT));
//		dictionaryFileBtn.setPreferredSize(dictionaryFileBtn.getSize());
//		dictionaryFileBtn.setFont(new Font(SingeConstantes.CALIBRI_POLICE, Font.BOLD, 20));
//		dictionaryFileBtn.setBackground(SingeConstantes.SINGE_ORANGE);
//		dictionaryFileBtn.addActionListener(this);
		// Charger l'image
		Image dictionaryFileBtnImage = new Image(getClass().getResourceAsStream("/com/negafumasu/img/dossier_01.png"));
		// Créer une ImageView avec l'image
		ImageView dictionaryFileBtnImageView = new ImageView(dictionaryFileBtnImage);
		dictionaryFileBtnImageView.setFitWidth(20); // Largeur de l'image
		dictionaryFileBtnImageView.setFitHeight(20); // Hauteur de l'image
		dictionaryFileBtn = new Button("", dictionaryFileBtnImageView);
		dictionaryFileBtn.setPrefSize(40, 35);
		dictionaryFileBtn.setPadding(new Insets(0, 0, 0, 0));
		/***/
		String dictionaryFileBtnStyle = GenericParam.getButtonStyle("14", Constants.COLOR_ORANGE, "0.5em", Constants.COLOR_WHITE,
				"1 1 1 1", Constants.COLOR_WHITE, "0.5em", "0");
		String dictionaryFileBtnStyleHover = GenericParam.getButtonStyle("14", Constants.COLOR_GOLD, "0.5em",
				Constants.COLOR_BLACK, "1 1 1 1", Constants.COLOR_BLACK, "0.5em", "0");

		dictionaryFileBtn.setStyle(dictionaryFileBtnStyle);
		dictionaryFileBtn.setOnMouseEntered(e -> dictionaryFileBtn.setStyle(dictionaryFileBtnStyleHover));
		dictionaryFileBtn.setOnMouseExited(e -> dictionaryFileBtn.setStyle(dictionaryFileBtnStyle));
		/***/
		// Configurer l'action du bouton
		dictionaryFileBtn.setOnAction(e -> actionPerformed(e, primaryStage));
		dictionaryFileBtn.setTooltip(new Tooltip("choisir le dossier de fichier log"));

		dictionaryFileTF = new TextField(getDictionaryFileName());
		dictionaryFileTF.setPrefSize(355, COMPONENTS_HEIGHT);
//		dictionaryFileTF.setPreferredSize(dictionaryFileTF.getSize());
//		dictionaryFileTF.setFont(new Font(SingeConstantes.CALIBRI_POLICE, Font.BOLD, 20));

		dictionaryFileLabel = new Label("* Fichier dico : ");
		dictionaryFileLabel.setPrefSize(110, COMPONENTS_HEIGHT);
//		dictionaryFileLabel.setFont(new Font(SingeConstantes.CALIBRI_POLICE, Font.BOLD, 20));

//		ImageIcon recordIcon = CommonSingleton.getImageIcon("img/enregistrer_01.png", 40, 40);
//		recordBtn = new Button(recordIcon);
//		recordBtn.setToolTipText("enregistrer les parametres");
//		recordBtn.setSize(new Dimension(80, 50));
//		recordBtn.setPreferredSize(recordBtn.getSize());
//		recordBtn.setFont(new Font(SingeConstantes.CALIBRI_POLICE, Font.BOLD, 20));
//		recordBtn.setBackground(SingeConstantes.SINGE_BLUE);
//		recordBtn.addActionListener(this);
		// Charger l'image
		Image recordBtnImage = new Image(getClass().getResourceAsStream("/com/negafumasu/img/enregistrer_01.png"));
		// Créer une ImageView avec l'image
		ImageView recordBtnImageView = new ImageView(recordBtnImage);
		recordBtnImageView.setFitWidth(30); // Largeur de l'image
		recordBtnImageView.setFitHeight(30); // Hauteur de l'image
		recordBtn = new Button("", recordBtnImageView);
		recordBtn.setPrefSize(80, 65);
		recordBtn.setPadding(new Insets(0, 20, 0, 20));
		/***/
		String recordBtnStyle = GenericParam.getButtonStyle("14", Constants.COLOR_BLUE, "1em", Constants.COLOR_WHITE,
				"1 1 1 1", Constants.COLOR_WHITE, "1em", "0");
		String recordBtnStyleHover = GenericParam.getButtonStyle("14", Constants.COLOR_LIGHTBLUE, "1em",
				Constants.COLOR_BLACK, "1 1 1 1", Constants.COLOR_BLACK, "1em", "0");

		recordBtn.setStyle(recordBtnStyle);
		recordBtn.setOnMouseEntered(e -> recordBtn.setStyle(recordBtnStyleHover));
		recordBtn.setOnMouseExited(e -> recordBtn.setStyle(recordBtnStyle));
		/***/
		// Configurer l'action du bouton
		recordBtn.setOnAction(e -> actionPerformed(e, primaryStage));
		recordBtn.setTooltip(new Tooltip("choisir le dossier de fichier log"));

		/** niveau Robots **/
		robotsLevelCb = new ComboBox<>();
		robotsLevelCb.setPrefSize(170, 25);
//		robotsLevelCb.setPreferredSize(robotsLevelCb.getSize());
//		robotsLevelCb.setFont(new Font(SingeConstantes.CALIBRI_POLICE, Font.BOLD, 20));
//		robotsLevelCb.addActionListener(this);
		robotsLevelCb.setOnAction(e -> actionPerformed(e, primaryStage));

		robotsLevelCb.getItems().addAll(EnumNiveauRobot.values());
//		for (EnumNiveauRobot robotLevel : EnumNiveauRobot.values()) {
//			robotsLevelCb.addItem(robotLevel);
//		}
		robotsLevelCb.setValue(robotsLevel);
//		robotsLevelCb.setSelectedItem(robotsLevel);
		

		robotsLevelLabel = new Label("* Niveau Robots :");
		robotsLevelLabel.setPrefSize(420, COMPONENTS_HEIGHT);
//		robotsLevelLabel.setFont(new Font(SingeConstantes.CALIBRI_POLICE, Font.BOLD, 20));

		/** mode Jeu **/
		gameModeCb = new ComboBox<>();
		gameModeCb.setPrefSize(180, 25);
//		gameModeCb.setPreferredSize(gameModeCb.getSize());
//		gameModeCb.setFont(new Font(SingeConstantes.CALIBRI_POLICE, Font.BOLD, 20));
//		gameModeCb.addActionListener(this);
		gameModeCb.setOnAction(e -> actionPerformed(e, primaryStage));

//		for (EnumModeJeu modeJeu : EnumModeJeu.values()) {
//			gameModeCb.addItem(modeJeu);
//		}
//		gameModeCb.setSelectedItem(gameMode);
		gameModeCb.getItems().addAll(EnumModeJeu.values());
		gameModeCb.setValue(gameMode);

		gameModeLabel = new Label("* Mode Jeu :");
		gameModeLabel.setPrefSize(395, COMPONENTS_HEIGHT);
//		gameModeLabel.setFont(new Font(SingeConstantes.CALIBRI_POLICE, Font.BOLD, 20));

		// init composants ComboBox ok, sinon appel lors de la boucle (methode:
		// add(...))
		isWindowInitialized = true;

		//////// FieldSet /////////
		// Créer un TitledPane avec un titre
		VBox configFieldSetPanel = new VBox(10);
		TitledPane titledPane = new TitledPane("Parametres :", configFieldSetPanel);
		titledPane.setStyle(GenericParam.getLabelStyle2("16", Constants.COLOR_BLACK, Constants.FAMILLY_CALIBRI));
		titledPane.setAlignment(Pos.CENTER);
		titledPane.setCollapsible(false); // Empêche de réduire/étendre
//		Border titleBorder = new TitledBorder(new LineBorder(Color.DARK_GRAY), "Parametres :");
//		configFieldSetPanel.setBorder(titleBorder);
		configFieldSetPanel.setPrefSize(550, 200);
//		configFieldSetPanel.setPreferredSize(configFieldSetPanel.getSize());
		HBox gameModeBox = new HBox(10);
//		configFieldSetPanel.getChildren().add(gameModeLabel);
//		configFieldSetPanel.getChildren().add(gameModeCb);
		gameModeBox.getChildren().add(gameModeLabel);
		gameModeBox.getChildren().add(gameModeCb);
		
		HBox robotLevelBox = new HBox(10);
//		configFieldSetPanel.getChildren().add(robotsLevelLabel);
//		configFieldSetPanel.getChildren().add(robotsLevelCb);
		robotLevelBox.getChildren().add(robotsLevelLabel);
		robotLevelBox.getChildren().add(robotsLevelCb);
		//////////////////////////////
		HBox dictionaryFileBox = new HBox(10);
//		configFieldSetPanel.getChildren().add(dictionaryFileLabel);
//		configFieldSetPanel.getChildren().add(dictionaryFileTF);
//		configFieldSetPanel.getChildren().add(dictionaryFileBtn);
		dictionaryFileBox.getChildren().add(dictionaryFileLabel);
		dictionaryFileBox.getChildren().add(dictionaryFileTF);
		dictionaryFileBox.getChildren().add(dictionaryFileBtn);
		
		HBox logFileBox = new HBox(10);
//		configFieldSetPanel.getChildren().add(logFileLabel);
//		configFieldSetPanel.getChildren().add(logFileTF);
//		configFieldSetPanel.getChildren().add(logFileBtn);
		logFileBox.getChildren().add(logFileLabel);
		logFileBox.getChildren().add(logFileTF);
		logFileBox.getChildren().add(logFileBtn);
		
		configFieldSetPanel.getChildren().addAll(gameModeBox, robotLevelBox, dictionaryFileBox, logFileBox);
		//////////////////////////

		/** explication parametres **/
		configurationExplainationTa = new TextArea();
		configurationExplainationTa.setPrefSize(500, 200);
		configurationExplainationTa.setStyle(GenericParam.getFxBackgroundColor(Constants.COLOR_DARKGRAY)+Constants.FX_FONT_BOLD);
//		configurationExplainationTa.setBackground(Color.LIGHT_GRAY);
//		configurationExplainationTa.setForeground(Color.DARK_GRAY);
//		configurationExplainationTa.setFont(new Font(SingeConstantes.CALIBRI_POLICE, Font.BOLD, 16));
		configurationExplainationTa.setEditable(false);
		configurationExplainationTa.setText(Constants.CARRIAGE_RETURN + Constants.FRONT_SPACE
				+ "Ici vous pouvez configurer le jeu. Voici ce qu'il est possible de configurer : "
				+ Constants.CARRIAGE_RETURN + Constants.FRONT_SPACE
				+ " - Le mode de jeu : Classique ou Sensible." + Constants.CARRIAGE_RETURN
				+ Constants.FRONT_SPACE
				+ " - La difficulté des joueurs 'Robot' ('R') : Facile, Moyen ou Difficile."
				+ Constants.CARRIAGE_RETURN + Constants.FRONT_SPACE
				+ " - Le fichier utilisé comme base dictionnaire pour les parties." + Constants.CARRIAGE_RETURN
				+ Constants.FRONT_SPACE
				+ " - Le dossier des fichiers* utilisés comme logs récapitulatifs des parties."
				+ Constants.CARRIAGE_RETURN + Constants.FRONT_SPACE
				+ " * pour l'instant accéssible en appuyant sur le bouton 'quitter'." + Constants.CARRIAGE_RETURN);

//		VBox configPanel = new VBox(10);
//		configPanel.setPrefSize(600, 600);
//		configPanel.setPreferredSize(configPanel.getSize());
		//////////////////////////////
//		configPanel.getChildren().add(configurationExplainationTa);
		/////////////////////////////
//		configPanel.getChildren().add(configFieldSetPanel);
		/////////////////////////////
//		configPanel.getChildren().add(recordBtn);
		
		parametersBox.getChildren().addAll(configurationExplainationTa, titledPane, recordBtn);
		parametersBox.setAlignment(Pos.TOP_CENTER);
		
		return parametersBox;
	}
	
	/// *****///

		public String getDictionaryFileName() {
			if (menuPrincipal != null) {
				return menuPrincipal.getDictionaryFileName();
			} else if (ecranJeu != null) {
				return ecranJeu.getDictionaryFileName();
			}
			return null;
		}

		public String getLogFileName() {
			if (menuPrincipal != null) {
				return menuPrincipal.getLogFileName();
			} else if (ecranJeu != null) {
				return ecranJeu.getLogFileName();
			}
			return null;
		}

		public EnumModeJeu getGameMode() {
			return gameMode;
		}

		public void setGameMode(EnumModeJeu modeJeu) {
			this.gameMode = modeJeu;
		}

		public EnumNiveauRobot getRobotsLevel() {
			return robotsLevel;
		}

		public void setRobotsLevel(EnumNiveauRobot nivRobots) {
			this.robotsLevel = nivRobots;
		}

		public String getFichierDicoConfig() {
			return dictionaryFileConfiguration;
		}

		public void setDictionaryConfigurationFile(String fichierDicoConfig) {
			this.dictionaryFileConfiguration = fichierDicoConfig;
		}

		public String getFichierLogConfig() {
			return logFileConfiguration;
		}

		public void setLogConfigurationFile(String fichierLogConfig) {
			this.logFileConfiguration = fichierLogConfig;
		}

		public void actionPerformed(ActionEvent e, Stage primaryStage) {

			Object source = e.getSource();
			FileChooser fileChooser = new FileChooser();
			 fileChooser.setTitle("Sélectionnez des fichiers");
//			fileChooser.setInitialDirectory(new File(System.getProperty("user.home"))); // Dossier utilisateur

			// Filtre de types
//			    fileChooser.getExtensionFilters().addAll(
//			        new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif"),
//			        new FileChooser.ExtensionFilter("Tous les fichiers", "*.*")
//			    );

			if (source == gameModeCb && isWindowInitialized) {
				EnumModeJeu selectedGameMode = (EnumModeJeu) gameModeCb.getValue();
				setGameMode(selectedGameMode);
				LOGGER.info("************* selectedModeJeu = " + selectedGameMode + " ************* ");
			} else if (source == robotsLevelCb && isWindowInitialized) {
				EnumNiveauRobot selectedRobotsLevel = (EnumNiveauRobot) robotsLevelCb.getValue();
				setRobotsLevel(selectedRobotsLevel);
				LOGGER.info("************* selectedNiveauRobots = " + selectedRobotsLevel + " ************* ");
			} else if (source == dictionaryFileBtn) {
				File choosenFile = fileChooser.showOpenDialog(null);
				String dictionaryPath = dictionaryFileTF.getText();
				if (choosenFile != null) {
					dictionaryPath = choosenFile.getAbsolutePath();
				}
				dictionaryFileTF.setText(dictionaryPath);
				LOGGER.info(
						"************* ! fileChooser files dico = " + choosenFile + " ! ************* ");
			} else if (source == logFileBtn) {
//				fileChooser.setFileSelectionMode(FileChooser.DIRECTORIES_ONLY);
//				fileChooser.setFileSelectionMode();
				File choosenFile = fileChooser.showOpenDialog(null);
				String logPath = logFileTF.getText();
				if (choosenFile != null) {
					logPath = choosenFile.getAbsolutePath();
				}
				logFileTF.setText(logPath);
				LOGGER.info(
						"************* ! fileChooser files log = " + choosenFile + " ! ************* ");
			} else if (source == recordBtn) {
				int recordResponse = JOptionPane.showConfirmDialog(null, "Voulez vous enregistrer les Parametres ?",
						"Enregistrer ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (recordResponse == JOptionPane.YES_OPTION) {
					if (menuPrincipal != null) {
						menuPrincipal.setGameMode(getGameMode());
						menuPrincipal.setRobotsLevel(getRobotsLevel());
						menuPrincipal.setDictionaryFileName(dictionaryFileTF.getText());
						menuPrincipal.setLogFileName(logFileTF.getText());
					} else if (ecranJeu != null) {
						ecranJeu.setGameMode(getGameMode());
						ecranJeu.setRobotsLevel(getRobotsLevel());
						ecranJeu.setDictionaryFileName(dictionaryFileTF.getText());
						ecranJeu.setLogFileName(logFileTF.getText());
						ecranJeu.initializeDictionaryData();
					}

					gameModeConfiguration = "modeJeu=" + getGameMode() + Constants.CARRIAGE_RETURN;
					robotLevelConfiguration = "niveauRobot=" + getRobotsLevel() + Constants.CARRIAGE_RETURN;
					dictionaryFileConfiguration = "fichierDico=" + dictionaryFileTF.getText()
							+ Constants.CARRIAGE_RETURN;
					logFileConfiguration = "fichierLog=" + logFileTF.getText() + Constants.CARRIAGE_RETURN;

					LOGGER.info("******* parametres sauvegarder !");

					JOptionPane.showConfirmDialog(null, "Vos paramètres on été sauvegarder !", "Confirmation",
							JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);

					setParameterValuesInFile();
					primaryStage.close();
				}

			}
		}

		private void setParameterValuesInFile() {
			File dossierConfig = new File(Constants.SINGE_CONFIG_FOLDER);
			File fichierConfig = new File(
					Constants.SINGE_CONFIG_FOLDER + File.separator + Constants.SINGE_CONFIG_FILE);

			if (!dossierConfig.exists()) {
				dossierConfig.mkdirs();
			}
			if (!fichierConfig.exists()) {
				try {
					fichierConfig.createNewFile();
				} catch (IOException e) {
					LOGGER.error("Erreur creation du fichier " + fichierConfig.getName() + " : ", e);
					JOptionPane.showInternalMessageDialog(null, Constants.MSG_DICT_ERROR,
							"Erreur de configuraton fichier", JOptionPane.ERROR_MESSAGE);
				}
			}
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(fichierConfig.getAbsolutePath());
				fos.write(gameModeConfiguration.getBytes());
				fos.write(robotLevelConfiguration.getBytes());
				fos.write(dictionaryFileConfiguration.getBytes());
				fos.write(logFileConfiguration.getBytes());
				fos.flush();
				fos.close();
			} catch (FileNotFoundException e) {
				LOGGER.error("Erreur Fichier non trouvé : ", e);
				JOptionPane.showInternalMessageDialog(null, Constants.MSG_NO_FILE_ERROR, "Erreur Fichier non trouvé",
						JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
				LOGGER.error("Erreur à l'ouverture du fichier : ", e);
				JOptionPane.showInternalMessageDialog(null, Constants.MSG_FILE_OPEN_ERROR,
						"Erreur à l'ouverture du fichier", JOptionPane.ERROR_MESSAGE);
			}
		}
}
