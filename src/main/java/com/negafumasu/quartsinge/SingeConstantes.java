package com.negafumasu.quartsinge;

import java.awt.Color;

import com.negafumasu.utils.ColorConverterUtils;

/**
 * Singe Application Constants Class
 * 
 *  @author tcazako
 */
public class SingeConstantes {

	public static final String FRONT_SPACE = "    ";
	public static final String CARRIAGE_RETURN = "\r\n";
	public static final String CALIBRI_POLICE = "Calibri";
	public static final String SINGE_CONFIG_FOLDER = "SingeConfig";
	public static final String SINGE_CONFIG_FILE = "singe_config.txt";
	public static final Color SINGE_BLUE = ColorConverterUtils.hex2Rgb("#0088FF");
	public static final Color SINGE_ORANGE = ColorConverterUtils.hex2Rgb("#FFAA22");
	public static final String MSG_DICT_ERROR = "Erreur durant la recuperation du fichier Dictionnaire: \n "
			+ "Fichier pas trouvé, Vérifier la configuration  du fichier dictionnaire";
	public static final String MSG_CONFIG_ERROR = "Erreur durant la recuperation du fichier de configuration: \n "
			+ "Fichier pas trouvé, Vérifier la configuration  du fichier configuration";
	public static final String MSG_NO_FILE_ERROR = "Le fichier n'a pas été trouvé !";
	public static final String MSG_FILE_OPEN_ERROR = "le fichier ne peut pas être ouvert.";
	public static final String MSG_FILE_NOT_FOUND_ERROR = "Fichier non trouvé";
}
