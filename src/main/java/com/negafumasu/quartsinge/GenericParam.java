package com.negafumasu.quartsinge;

public class GenericParam {

	// Background
	public static String getFxBackgroundColor(String value) {
		return Constants.FX_BACKGROUND_COLOR + value + ";";
	};

	public static String getFxBackgroundInsets(String value) {
		return Constants.FX_BACKGROUND_INSETS + value + ";";
	};

	public static String getFxBackgroundRadius(String value) {
		return Constants.FX_BACKGROUND_RADIUS + value + ";";
	};

	// Border
	public static String getFxBorderColor(String value) {
		return Constants.FX_BORDER_COLOR + value + ";";
	};

	public static String getFxBorderRadius(String value) {
		return Constants.FX_BORDER_RADIUS + value + ";";
	};

	public static String getFxBorderWidth(String value) {
		return Constants.FX_BORDER_WIDTH + value + ";";
	};

	// Font
	public static String getFxFontFamilly(String value) {
		return Constants.FX_FONT_FAMILLY + value + ";";
	};

	public static String getFxFontSize(String value) {
		return Constants.FX_FONT_SIZE + value + ";";
	};

	public static String getFxFontWeight(String value) {
		return Constants.FX_FONT_WEIGHT + value + ";";
	};

	// Padding
	public static String getFxPadding(String value) {
		return Constants.FX_PADDING + value + ";";
	};

	// Text
	public static String getFxTextAllign(String value) {
		return Constants.FX_TEXT_ALIGN + value + ";";
	};

	public static String getFxTextFill(String value) {
		return Constants.FX_TEXT_FILL + value + ";";
	};
	
	public static String getFxFill(String value) {
		return Constants.FX_FILL + value + ";";
	};

	public static String getTitleStyle(String fontSize, String padding, String backgroundRadius) {
		StringBuilder titleStyle = new StringBuilder();
		titleStyle.append(Constants.FX_FONT_BOLD).append(getFxFontSize(fontSize)).append(getFxPadding(padding))
				.append(Constants.FX_BACKGROUND_GOLD).append(Constants.FX_TEXT_ALIGN_CENTER)
				.append(getFxBackgroundRadius(backgroundRadius));
		return titleStyle.toString();
	}

	public static String getTitleStyle(String fontSize, String padding, String backgroundColor, String textAlignment) {
		StringBuilder titleStyle = new StringBuilder();
		titleStyle.append(Constants.FX_FONT_BOLD).append(getFxFontSize(fontSize)).append(getFxPadding(padding))
				.append(getFxBackgroundColor(backgroundColor)).append(getFxTextAllign(textAlignment));
		return titleStyle.toString();
	}

	public static String getTitleStyle(String fontSize, String padding, String backgroundColor, String textAlignment,
			String backgroundRadius) {
		StringBuilder titleStyle = new StringBuilder();
		titleStyle.append(Constants.FX_FONT_BOLD).append(getFxFontSize(fontSize)).append(getFxPadding(padding))
				.append(getFxBackgroundColor(backgroundColor)).append(getFxTextAllign(textAlignment))
				.append(getFxBackgroundRadius(backgroundRadius));
		return titleStyle.toString();
	}

	public static String getButtonStyle(String fontSize, String borderRadius, String borderWidth,
			String backgroundRadius, String backgroundInsets) {
		StringBuilder buttonStyle = new StringBuilder();
		buttonStyle.append(Constants.FX_FONT_BOLD).append(getFxFontSize(fontSize))
				.append(Constants.FX_BACKGROUND_BLUEVIOLET).append(getFxBorderRadius(borderRadius))
				.append(Constants.FX_FONT_FAMILLY_MARCELLUS).append(Constants.FX_TEXT_FILL_WHITE)
				.append(getFxBorderWidth(borderWidth)).append(Constants.FX_BORDER_BLACK)
				.append(getFxBackgroundRadius(backgroundRadius)).append(getFxBackgroundInsets(backgroundInsets));
		return buttonStyle.toString();
	}

	public static String getButtonStyle(String fontSize, String backgroundColor, String borderRadius, String textFill,
			String borderWidth, String borderColor, String backgroundRadius, String backgroundInsets) {
		StringBuilder buttonStyle = new StringBuilder();
		buttonStyle.append(Constants.FX_FONT_BOLD).append(getFxFontSize(fontSize))
				.append(getFxBackgroundColor(backgroundColor)).append(getFxBorderRadius(borderRadius))
				.append(Constants.FX_FONT_FAMILLY_CALIBRI).append(getFxTextFill(textFill))
				.append(getFxBorderWidth(borderWidth)).append(getFxBorderColor(borderColor))
				.append(getFxBackgroundRadius(backgroundRadius)).append(getFxBackgroundInsets(backgroundInsets));
		return buttonStyle.toString();
	}

	public static String getButtonStyle(String fontSize, String backgroundColor, String borderRadius,
			String fontFamilly, String textFill, String borderWidth, String borderColor, String backgroundRadius,
			String backgroundInsets) {
		StringBuilder buttonStyle = new StringBuilder();
		buttonStyle.append(Constants.FX_FONT_BOLD).append(getFxFontSize(fontSize))
				.append(getFxBackgroundColor(backgroundColor)).append(getFxBorderRadius(borderRadius))
				.append(getFxFontFamilly(fontFamilly)).append(getFxTextFill(textFill))
				.append(getFxBorderWidth(borderWidth)).append(getFxBorderColor(borderColor))
				.append(getFxBackgroundRadius(backgroundRadius)).append(getFxBackgroundInsets(backgroundInsets));
		return buttonStyle.toString();
	}

	public static String getSectionStyle(String fontSize) {
		StringBuilder sectionStyle = new StringBuilder();
		sectionStyle.append(Constants.FX_FONT_BOLD).append(getFxFontSize(fontSize))
				.append(Constants.FX_BACKGROUND_LIGHTGREEN);
		return sectionStyle.toString();
	}

	public static String getSectionStyle(String fontSize, String backgroundColor) {
		StringBuilder sectionStyle = new StringBuilder();
		sectionStyle.append(Constants.FX_FONT_BOLD).append(getFxFontSize(fontSize))
				.append(getFxBackgroundColor(backgroundColor));
		return sectionStyle.toString();
	}

	public static String getSectionStyle(String fontSize, String backgroundColor, String padding, String textAlign) {
		StringBuilder sectionStyle = new StringBuilder();
		sectionStyle.append(Constants.FX_FONT_BOLD).append(getFxFontSize(fontSize)).append(getFxPadding(padding))
				.append(getFxBackgroundColor(backgroundColor)).append(getFxTextAllign(textAlign));
		return sectionStyle.toString();
	}
	
	public static String getLabelStyle(String fontSize, String backgroundColor, String textFill) {
		StringBuilder sectionStyle = new StringBuilder();
		sectionStyle.append(Constants.FX_FONT_BOLD).append(getFxFontSize(fontSize))
				.append(getFxBackgroundColor(backgroundColor)).append(getFxTextFill(textFill));
		return sectionStyle.toString();
	}
	
	public static String getLabelStyle2(String fontSize, String textFill, String fontFamilly) {
		StringBuilder sectionStyle = new StringBuilder();
		sectionStyle.append(Constants.FX_FONT_BOLD).append(getFxFontSize(fontSize))
				.append(getFxTextFill(textFill)).append(getFxFontFamilly(fontFamilly));
		return sectionStyle.toString();
	}
	
	public static String getLabelStyle3(String fontSize, String fill, String fontFamilly) {
		StringBuilder sectionStyle = new StringBuilder();
		sectionStyle.append(Constants.FX_FONT_BOLD).append(getFxFontSize(fontSize))
				.append(getFxFill(fill)).append(getFxFontFamilly(fontFamilly));
		return sectionStyle.toString();
	}
	
	public static String getLabelStyle4(String fontSize, String backgroundColor, String fontFamilly) {
		StringBuilder sectionStyle = new StringBuilder();
		sectionStyle.append(Constants.FX_FONT_BOLD).append(getFxFontSize(fontSize))
				.append(getFxBackgroundColor(backgroundColor)).append(getFxFontFamilly(fontFamilly));
		return sectionStyle.toString();
	}
	
	public static String getHighlighCarteStyle(String borderColor, String borderWidth) {
		StringBuilder sectionStyle = new StringBuilder();
		sectionStyle.append(getFxBorderColor(borderColor)).append(getFxBorderWidth(borderWidth));
		return sectionStyle.toString();
	}
	
	//////////////////////////////////////////////////////////////////////////////////:
	

}
