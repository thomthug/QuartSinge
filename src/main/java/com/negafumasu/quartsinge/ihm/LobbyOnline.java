package com.negafumasu.quartsinge.ihm;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;
import java.util.*;

import com.negafumasu.quartsinge.data.Joueur;

public class LobbyOnline {
	 private Socket socket;
	    private PrintWriter out;
	    private BufferedReader in;

	    private TextField inputNom;
	    private Button boutonConnexion;
	    private Button boutonPlay;
	    private TextArea listeJoueurs;

//	    @Override
	    public void start(Stage primaryStage) {
	        // Interface utilisateur
	        VBox root = new VBox(10);
	        root.setStyle("-fx-padding: 10;");
	        
	        Label labelNom = new Label("Nom du joueur :");
	        inputNom = new TextField();
	        boutonConnexion = new Button("Se connecter");
	        boutonPlay = new Button("Play");
	        boutonPlay.setDisable(true); // Désactivé au début

	        listeJoueurs = new TextArea();
	        listeJoueurs.setEditable(false);
	        listeJoueurs.setPrefHeight(200);

	        root.getChildren().addAll(labelNom, inputNom, boutonConnexion, listeJoueurs, boutonPlay);

	        Scene scene = new Scene(root, 400, 300);
	        primaryStage.setTitle("Lobby - Quart de Singe");
	        primaryStage.setScene(scene);
	        primaryStage.show();

	        // Actions des boutons
	        boutonConnexion.setOnAction(e -> connecterAuServeur(primaryStage));
	        boutonPlay.setOnAction(e -> envoyerDemandePlay());
	    }

	    private void connecterAuServeur(Stage primaryStage) {
	        String nom = inputNom.getText().trim();
	        if (nom.isEmpty()) {
	            afficherAlerte("Erreur", "Veuillez entrer un nom valide.");
	            return;
	        }

	        try {
	            socket = new Socket("localhost", 12345); // Connexion au serveur
	            out = new PrintWriter(socket.getOutputStream(), true);
	            out.println(nom); // Envoi du nom au serveur
	            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

	            

	            new Thread(() -> recevoirMessages(primaryStage)).start(); // Démarrer l'écoute des messages serveur

	            boutonConnexion.setDisable(true);
	            inputNom.setDisable(true);
	        } catch (IOException e) {
	            afficherAlerte("Erreur", "Connexion au serveur échouée.");
	        }
	    }

	    private void envoyerDemandePlay() {
	        if (out != null) {
	            out.println("PLAY");
	        }
	    }

	    private void recevoirMessages(Stage primaryStage) {
	        try {
	            String message;
	            while ((message = in.readLine()) != null) {
	                if (message.startsWith("UPDATE:")) {
	                    mettreAJourListeJoueurs(message);
	                } else if (message.startsWith("START")) {
	                    lancerPartie(primaryStage);
	                } else if (message.startsWith("ERREUR:")) {
	                    afficherAlerte("Erreur", message.substring(7));
	                }
	            }
	        } catch (IOException e) {
	        	Platform.runLater(() -> afficherAlerte("Erreur", "Connexion perdue avec le serveur."));
	        }
	    }

	    private void mettreAJourListeJoueurs(String message) {
	        // Extraction des informations
	        String[] parts = message.split("\\|");
	        String joueurs = parts[0].substring(7); // Liste des joueurs
	        boolean playEnabled = parts[1].equals("PLAY:true"); // Statut du bouton Play

	        // Mise à jour de l'interface
	        listeJoueurs.setText(joueurs.replace(",", "\n"));
	        boutonPlay.setDisable(!playEnabled);
	    }

	    private void lancerPartie(Stage primaryStage) {
	    	Platform.runLater(() -> afficherAlerte("Information", "La partie commence !"));
	        System.out.println("Démarrage de la partie...");
	        String[] joueursListe = listeJoueurs.getText().split("\n");
	        
	        List<Joueur> joueurs = new ArrayList<Joueur>();
	        
	        for(String joueur : joueursListe) {
	        	joueurs.add(new Joueur(joueur));
	        }
	        
	        Platform.runLater(() -> { 
//	        	new FenetreEcranJeu(primaryStage, joueurs)
	        	FenetreJeuEnLigne jeuEnLigne = new FenetreJeuEnLigne();
	        	jeuEnLigne.setIn(in);
	        	jeuEnLigne.setOut(out);
	        	jeuEnLigne.start(primaryStage);
	        	});
	        // Transition vers l'écran du jeu ici
	    }

	    private void afficherAlerte(String titre, String message) {
	        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle(titre);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }
}
