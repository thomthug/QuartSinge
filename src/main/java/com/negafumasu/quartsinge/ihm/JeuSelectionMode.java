package com.negafumasu.quartsinge.ihm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class JeuSelectionMode extends Application {
	
//	private Socket socket;
//    private BufferedReader in;
//    private PrintWriter out;

//    private TextArea displayArea = new TextArea();
//    private TextField inputField = new TextField();
	TextField ipField;
	TextField portField;
	
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Création du conteneur principal
        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Titre
        Label titleLabel = new Label("Choisissez votre mode de jeu");
        titleLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        // ComboBox pour le choix du mode
        ComboBox<String> modeComboBox = new ComboBox<>();
        modeComboBox.getItems().addAll("Local", "En ligne");
        modeComboBox.setValue("Local"); // Mode par défaut

        // Conteneurs dynamiques pour les options
        VBox optionsBox = new VBox(10);
        optionsBox.setStyle("-fx-padding: 10; -fx-alignment: center;");

        // Options pour le mode "En ligne"
        ipField = new TextField();
        ipField.setPromptText("Adresse IP du serveur");
        portField = new TextField();
        portField.setPromptText("Port (ex: 12345)");

        Button connectButton = new Button("Se connecter");
        connectButton.setOnAction(e -> {
            String ip = ipField.getText();
            String port = portField.getText();
            if (ip.isEmpty() || port.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "L'adresse IP et le port doivent être renseignés !");
            } else {
                System.out.println("Connexion au serveur " + ip + " sur le port " + port + "...");
                
                // Ajouter ici le code pour se connecter au serveur
//             lancerPartieEnLigne();
                new LobbyOnline().start(primaryStage);
                
            }
        });

        // Options pour le mode "Local"
        Button startLocalButton = new Button("Lancer une partie locale");
        startLocalButton.setOnAction(e -> {
            System.out.println("Partie locale lancée !");
//            Application.launch(FenetreMenuPrincipal.class);
				try {
					new FenetreMenuPrincipal().start(primaryStage);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            // Ajouter ici le code pour démarrer une partie locale
        });

        // Gestion du changement de mode
        modeComboBox.setOnAction(event -> {
            optionsBox.getChildren().clear();
            if ("En ligne".equals(modeComboBox.getValue())) {
                optionsBox.getChildren().addAll(new Label("Mode En ligne :"), ipField, portField, connectButton);
            } else {
                optionsBox.getChildren().addAll(new Label("Mode Local :"), startLocalButton);
            }
        });

        // Initialisation des options par défaut
        optionsBox.getChildren().addAll(new Label("Mode Local :"), startLocalButton);

        // Ajout des composants principaux
        root.getChildren().addAll(titleLabel, modeComboBox, optionsBox);

        // Création de la scène et affichage
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Quart de Singe - Choix du Mode");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

//    @FXML
//    private void lancerPartieEnLigne() {
//        try {
//            String adresse = ipField.getText();  // Adresse saisie dans un champ texte
//            int port = Integer.parseInt(portField.getText());  // Port saisi dans un champ texte
//            Client client = new Client(adresse, port);
//
//            // Exemple de communication
//            client.sendMessage("Joueur connecté !");
//            String message = client.receiveMessage();
//            System.out.println("Message reçu : " + message);
//
//            // Passer au jeu en ligne
////            FenetreEcranJeu jeu = new FenetreEcranJeu(client);
////            jeu.afficher();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    public static void main(String[] args) {
//        launch(args);
//    }
}

