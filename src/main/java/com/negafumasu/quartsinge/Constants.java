package com.negafumasu.quartsinge;

import com.negafumasu.utils.ColorConverterUtils;
import com.negafumasu.utils.FxColorConverterUtils;

import javafx.scene.paint.Color;

public class Constants {

	/*
	 * DEFAULT
	 */
	// COLOR
	public static final String COLOR_BLACK = "black";
	public static final String COLOR_BLUE = "blue";
	public static final String COLOR_BLUEVIOLET = "blueviolet";
	public static final String COLOR_DARKGRAY = "darkgray";
	public static final String COLOR_GOLD = "gold";
	public static final String COLOR_GRAY = "gray";
	public static final String COLOR_GREEN = "green";
	public static final String COLOR_LIGHTGRAY = "lightgray";
	public static final String COLOR_LIGHTGREEN = "lightgreen";
	public static final String COLOR_LIGHTBLUE = "lightblue";
	public static final String COLOR_ORANGE = "orange";
	public static final String COLOR_PINK = "pink";
	public static final String COLOR_RED = "red";
	public static final String COLOR_SILVER = "silver";
	public static final String COLOR_WHITE = "white";
	public static final String COLOR_YELLOW = "yellow";
	
	// FAMILLY
	public static final String FAMILLY_ARIAL = "Arial";
	public static final String FAMILLY_CALIBRI = "Calibri";
	public static final String FAMILLY_MARCELLUS = "Marcellus";
	public static final String FAMILLY_SEGOE = "Segoe";
	public static final String FAMILLY_SEGOEUI = "SegoeUI";
	public static final String FAMILLY_WINGDINGS = "Wingdings";

	// ORIENTATION
	public static final String ORIENTATION_CENTER = "center";

	// WEIGHT
	public static final String WEIGHT_BOLD = "bold";
	public static final String WEIGHT_ITALIC = "italic";
	
	/*
	 * Fichier
	 */
	// RESOURCES
	public static final String RESOURCES_FOLDER = "src/main/resources";
	
	/*
	 * FX
	 */
	// public static final String FX_ = "";
	// BACKGROUND
	public static final String FX_BACKGROUND_BLACK = "-fx-background-color: black;";
	public static final String FX_BACKGROUND_BLUE = "-fx-background-color: blue;";
	public static final String FX_BACKGROUND_BLUEVIOLET = "-fx-background-color: blueviolet;";
	public static final String FX_BACKGROUND_DARKGRAY = "-fx-background-color: darkgray;";
	public static final String FX_BACKGROUND_GOLD = "-fx-background-color: gold;";
	public static final String FX_BACKGROUND_GRAY = "-fx-background-color: gray;";
	public static final String FX_BACKGROUND_GREEN = "-fx-background-color: green;";
	public static final String FX_BACKGROUND_LIGHTGRAY = "-fx-background-color: lightgray;";
	public static final String FX_BACKGROUND_LIGHTGREEN = "-fx-background-color: lightgreen;";
	public static final String FX_BACKGROUND_LIGHTBLUE = "-fx-background-color: lightblue;";
	public static final String FX_BACKGROUND_PINK = "-fx-background-color: pink;";
	public static final String FX_BACKGROUND_RED = "-fx-background-color: red;";
	public static final String FX_BACKGROUND_SILVER = "-fx-background-color: silver;";
	public static final String FX_BACKGROUND_WHITE = "-fx-background-color: white;";
	public static final String FX_BACKGROUND_YELLOW = "-fx-background-color: yellow;";
	
	public static final String FX_BACKGROUND_COLOR = "-fx-background-color: ";
	public static final String FX_BACKGROUND_INSETS = "-fx-background-insets: ";
	public static final String FX_BACKGROUND_RADIUS = "-fx-background-radius: ";

	// BORDER
	public static final String FX_BORDER_BLACK = "-fx-border-color: black;";
	public static final String FX_BORDER_WHITE = "-fx-border-color: white;";
	
	public static final String FX_BORDER_COLOR = "-fx-border-color: ";
	public static final String FX_BORDER_RADIUS = "-fx-border-radius: ";
	public static final String FX_BORDER_WIDTH = "-fx-border-width: ";

	// FONT
	public static final String FX_FONT_BOLD = "-fx-font-weight: bold;";
	public static final String FX_FONT_FAMILLY_ARIAL = "-fx-font-family: Arial;";
	public static final String FX_FONT_FAMILLY_CALIBRI = "-fx-font-family: Calibri;";
	public static final String FX_FONT_FAMILLY_MARCELLUS = "-fx-font-family: Marcellus;";
	public static final String FX_FONT_FAMILLY_SEGOE = "-fx-font-family: Segoe;";
	public static final String FX_FONT_FAMILLY_SEGOEUI = "-fx-font-family: SegoeUI;";
	public static final String FX_FONT_FAMILLY_WINGDINGS = "-fx-font-family: Wingdings;";
	
	public static final String FX_FONT_FAMILLY = "-fx-font-family: ";
	public static final String FX_FONT_SIZE = "-fx-font-size: ";
	public static final String FX_FONT_WEIGHT = "-fx-font-weight: ";
	
	// PADDING
	public static final String FX_PADDING = "-fx-padding: ";

	// TEXT
	public static final String FX_TEXT_ALIGN_CENTER = "-fx-text-alignment: center;";
	public static final String FX_TEXT_FILL_WHITE = "-fx-text-fill: white;";
	public static final String FX_TEXT_FILL_BLACK = "-fx-text-fill: black;";

	public static final String FX_TEXT_ALIGN = "-fx-text-alignment: ";
	public static final String FX_TEXT_FILL = "-fx-text-fill: ";
	
	public static final String FX_FILL = "-fx-fill: ";
	
	/*
	 * Autres
	 */
	
	public static final String FRONT_SPACE = "    ";
	public static final String CARRIAGE_RETURN = "\r\n";
	public static final String SINGE_CONFIG_FOLDER = "config";
	public static final String SINGE_CONFIG_FILE = "config.txt";
	public static final Color COLOR_QUART_SINGE_BLUE = FxColorConverterUtils.hex2Rgb("#0088FF");
	public static final Color COLOR_QUART_SINGE_ORANGE = FxColorConverterUtils.hex2Rgb("#FFAA22");
	public static final String MSG_DICT_ERROR = "Erreur durant la recuperation du fichier Dictionnaire: \n "
			+ "Fichier pas trouvé, Vérifier la configuration  du fichier dictionnaire";
	public static final String MSG_CONFIG_ERROR = "Erreur durant la recuperation du fichier de configuration: \n "
			+ "Fichier pas trouvé, Vérifier la configuration  du fichier configuration";
	public static final String MSG_NO_FILE_ERROR = "Le fichier n'a pas été trouvé !";
	public static final String MSG_FILE_OPEN_ERROR = "le fichier ne peut pas être ouvert.";
	public static final String MSG_FILE_NOT_FOUND_ERROR = "Fichier non trouvé";
	
}
