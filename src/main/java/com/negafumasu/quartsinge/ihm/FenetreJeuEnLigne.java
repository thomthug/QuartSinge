package com.negafumasu.quartsinge.ihm;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class FenetreJeuEnLigne extends Application {

    private Label labelMotActuel;
    private TextField inputLettre;
    private Button btnProposer;
    private Button btnDefier;
//    private Button btnValider;
    private ListView<String> listeJoueurs;
    private TextArea zoneMessage;
    
    private Label lblMot = new Label("Mot : ");
    private TextField txtLettre = new TextField();
    private Button btnValider = new Button("Valider");
    private Button btnAbandonner = new Button("Abandonner");
    private TextArea chatArea = new TextArea();
    private TextField chatInput = new TextField();
    private Button btnEnvoyer = new Button("Envoyer");

    private List<String> joueurs = new ArrayList<>(); // Liste des joueurs
    private int joueurActuel = 0; // Index du joueur actuel
    private String motActuel = ""; // Mot en cours
    private String[] niveaux = {"Quart", "Demi", "Trois quarts", "Singe complet"}; // Statuts
//	private Label lblMot = new Label();
	private Label lblStatut = new Label();
	private PrintWriter out;
    private BufferedReader in;
    private Socket socket;

    @Override
    public void start(Stage primaryStage) {
        // --- Configuration de la scène ---
//        BorderPane root = new BorderPane();
    	// Interface utilisateur
        VBox root = new VBox(10);
        root.getChildren().addAll(lblMot, txtLettre, btnValider, btnAbandonner, chatArea, chatInput, btnEnvoyer);

        chatArea.setEditable(false);
        
        // Zone d'affichage du mot actuel
        labelMotActuel = new Label("Mot : ");
        labelMotActuel.setStyle("-fx-font-size: 20px;");

        // Champ pour saisir une lettre
        inputLettre = new TextField();
        inputLettre.setPromptText("Lettre");

        // Bouton pour proposer une lettre
        btnProposer = new Button("Proposer");
        btnProposer.setOnAction(e -> proposerLettre());

        // Bouton pour défier avec "?"
        btnDefier = new Button("?");
        btnDefier.setOnAction(e -> defierJoueur());

        // Zone pour afficher les messages
        zoneMessage = new TextArea();
        zoneMessage.setEditable(false);
        zoneMessage.setPrefHeight(100);

        // Liste des joueurs avec leurs statuts
        listeJoueurs = new ListView<>();
        listeJoueurs.getItems().addAll("Joueur 1 : OK", "Joueur 2 : OK"); // Exemple

        // Mise en page
        HBox controles = new HBox(10, inputLettre, btnProposer, btnDefier, btnValider);
        controles.setAlignment(Pos.CENTER);

        VBox topPane = new VBox(10, labelMotActuel, controles);
        topPane.setAlignment(Pos.CENTER);

//        root.setTop(topPane);
//        root.setCenter(listeJoueurs);
//        root.setBottom(zoneMessage);
        
     // Gestion de l'entrée utilisateur
//        btnValider.setOnAction(e -> {
//            String lettre = inputLettre.getText().trim();
//            if (!lettre.isEmpty()) {
//                // Envoie la lettre au serveur
//                out.println("LETTRE " + lettre);
//                inputLettre.clear();
//            }
//        });
        
     // Réception des mises à jour depuis le serveur
//        Thread listener = new Thread(() -> {
//            try {
//                String message;
//                while ((message = in.readLine()) != null) {
//                    // Met à jour l'interface selon le message reçu
//                	final String message2 = message;
//                    Platform.runLater(() -> {
//                        if (message2.startsWith("MOT ")) {
//                        	labelMotActuel.setText(message2.substring(4)); // Mise à jour du mot
//                        } else if (message2.startsWith("STATUT ")) {
//                            lblStatut.setText(message2.substring(7)); // Mise à jour des statuts
//                        }
//                    });
//                }
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        });
//        listener.start();
        
     // Connexion au serveur
        connectToServer();

        // Gestion des boutons
        btnValider.setOnAction(e -> envoyerLettre());
        btnAbandonner.setOnAction(e -> quitterPartie());
        btnEnvoyer.setOnAction(e -> envoyerMessageChat());

        // Création de la scène
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Quart de Singe - Jeu en Ligne");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // --- Actions ---
    private void proposerLettre() {
        String lettre = inputLettre.getText().trim();
        if (lettre.isEmpty() || lettre.length() > 1) {
            afficherMessage("Entrez une seule lettre.");
            return;
        }

        // Ajoute la lettre au mot actuel
        motActuel += lettre;
        labelMotActuel.setText("Mot : " + motActuel);
        inputLettre.clear();

        // Passe au joueur suivant
        joueurActuel = (joueurActuel + 1) % joueurs.size();
        afficherMessage("C'est au tour du joueur " + (joueurActuel + 1));
    }

    private void defierJoueur() {
        afficherMessage("Défi lancé au joueur " + (joueurActuel + 1));
        // Envoyer un message au serveur pour demander un mot valide
        // (à implémenter avec le réseau)
    }

    private void afficherMessage(String message) {
        zoneMessage.appendText(message + "\n");
    }

    private void connectToServer() {
        try {
            socket = new Socket("localhost", 12345); // Remplacer par l'IP et le port si nécessaire
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Thread pour écouter les messages du serveur
            new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        String finalMessage = message;

                        // Mise à jour de l'interface graphique sur le thread JavaFX
                        Platform.runLater(() -> processServerMessage(finalMessage));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
            afficherAlerte("Erreur", "Impossible de se connecter au serveur.");
        }
    }

    private void envoyerLettre() {
        String lettre = txtLettre.getText().trim();
        if (!lettre.isEmpty()) {
            out.println("LETTRE " + lettre);
            txtLettre.clear();
        } else {
            afficherAlerte("Erreur", "Veuillez entrer une lettre !");
        }
    }

    private void envoyerMessageChat() {
        String message = chatInput.getText().trim();
        if (!message.isEmpty()) {
            out.println("CHAT " + message);
            chatInput.clear();
        }
    }

    private void quitterPartie() {
        out.println("QUIT");
        System.exit(0);
    }

    private void processServerMessage(String message) {
        if (message.startsWith("MOT ")) {
            lblMot.setText("Mot : " + message.substring(4));
        } else if (message.startsWith("CHAT ")) {
            chatArea.appendText(message.substring(5) + "\n");
        } else if (message.startsWith("ALERTE ")) {
            afficherAlerte("Alerte", message.substring(7));
        }
    }

    private void afficherAlerte(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setContentText(contenu);
        alert.showAndWait();
    }
    
//    public static void main(String[] args) {
//        launch(args);
//    }

	public BufferedReader getIn() {
		return in;
	}

	public void setIn(BufferedReader in) {
		this.in = in;
	}

	public PrintWriter getOut() {
		return out;
	}

	public void setOut(PrintWriter out) {
		this.out = out;
	}
    
}
