package it.eng.applet.bean;

/**
 * Classe bean che contiene i parametri di formato dell'etichetta
 * i valori di default sono quelli testati nel laboratorio di Bologna
 * con un'etichetta di circa 10 cm di larghezza.
 * @author Francesco Fioravanti
 *
 */
public class LablFmt {
	int fontSizeTestata=3;
	int fontSizePrimaRiga=4;
	int fontSizeTesto=2;

	int offsetXTestata = 224;
	int offsetXPrimariga=24;
	int offsetXTesto=24;
	int offsetXBarcode=224;

	int offsetYTestata = 10;
	int offsetYTesto1=53;
	int offsetYTesto2=81;
	int offsetYTesto3=99;
	int offsetYTesto4=116;
	int offsetYTesto5=134;
	int offsetYTesto6=152;
	int offsetYBarcode=185;

	int labelWidth = 560;
	int labelLength = 256;
	String labelSensorMode = "GAP"; //GAP, BL, CONT
	int labelGapLength = 24;
	int labelOffsetLength = 8;

	// 11.01.2013 - ottavio : aggiunto il parametro per il font
	String fontTesto = "";
	String fontPrimaRiga = "";
	String fontTestata = "";

	public int getFontSizeTestata() {
		return fontSizeTestata;
	}
	public void setFontSizeTestata(int fontSizeTestata) {
		this.fontSizeTestata = fontSizeTestata;
	}
	public int getFontSizePrimaRiga() {
		return fontSizePrimaRiga;
	}
	public void setFontSizePrimaRiga(int fontSizePrimaRiga) {
		this.fontSizePrimaRiga = fontSizePrimaRiga;
	}
	public int getFontSizeTesto() {
		return fontSizeTesto;
	}
	public void setFontSizeTesto(int fontSizeTesto) {
		this.fontSizeTesto = fontSizeTesto;
	}


	// 11.01.2013 - ottavio : aggiunto il parametro per il font

	public String getFontTestata() {


		return fontTestata;
	}
	public void setFontTestata(String fontTestata) {
		this.fontTestata = fontTestata;
	}

	public String getFontPrimaRiga() {
		return fontPrimaRiga;
	}
	public void setFontPrimaRiga(String fontPrimaRiga) {
		this.fontPrimaRiga = fontPrimaRiga;
	}

	public String  getFontTesto() {
		return fontTesto;
	}
	public void setFontTesto(String fontTesto) {
		this.fontTesto = fontTesto;
	}




	// ======================================================================
	public int getOffsetXTestata() {
		return offsetXTestata;
	}
	public void setOffsetXTestata(int offsetTestata) {
		this.offsetXTestata = offsetTestata;
	}
	public int getOffsetXPrimariga() {
		return offsetXPrimariga;
	}
	public void setOffsetXPrimariga(int offsetPrimariga) {
		this.offsetXPrimariga = offsetPrimariga;
	}
	public int getOffsetXTesto() {
		return offsetXTesto;
	}
	public void setOffsetXTesto(int offsetTesto) {
		this.offsetXTesto = offsetTesto;
	}
	public int getOffsetXBarcode() {
		return offsetXBarcode;
	}
	public void setOffsetXBarcode(int offsetBarcode) {
		this.offsetXBarcode = offsetBarcode;
	}


	public int getOffsetYTestata() {
		return offsetYTestata;
	}
	public void setOffsetYTestata(int offsetYTestata) {
		this.offsetYTestata = offsetYTestata;
	}
	public int getOffsetYTesto1() {
		return offsetYTesto1;
	}
	public void setOffsetYTesto1(int offsetYTesto1) {
		this.offsetYTesto1 = offsetYTesto1;
	}
	public int getOffsetYTesto2() {
		return offsetYTesto2;
	}
	public void setOffsetYTesto2(int offsetYTesto2) {
		this.offsetYTesto2 = offsetYTesto2;
	}
	public int getOffsetYTesto3() {
		return offsetYTesto3;
	}
	public void setOffsetYTesto3(int offsetYTesto3) {
		this.offsetYTesto3 = offsetYTesto3;
	}
	public int getOffsetYTesto4() {
		return offsetYTesto4;
	}
	public void setOffsetYTesto4(int offsetYTesto4) {
		this.offsetYTesto4 = offsetYTesto4;
	}
	public int getOffsetYTesto5() {
		return offsetYTesto5;
	}
	public void setOffsetYTesto5(int offsetYTesto5) {
		this.offsetYTesto5 = offsetYTesto5;
	}
	public int getOffsetYTesto6() {
		return offsetYTesto6;
	}
	public void setOffsetYTesto6(int offsetYTesto6) {
		this.offsetYTesto6 = offsetYTesto6;
	}
	public int getOffsetYBarcode() {
		return offsetYBarcode;
	}
	public void setOffsetYBarcode(int offsetYBarcode) {
		this.offsetYBarcode = offsetYBarcode;
	}


	public int getLabelWidth() {
		return labelWidth;
	}
	public void setLabelWidth(int labelWidth) {
		this.labelWidth = labelWidth;
	}
	public int getLabelLength() {
		return labelLength;
	}
	public void setLabelLength(int labelLength) {
		this.labelLength = labelLength;
	}
	public String getLabelSensorMode() {
		return labelSensorMode;
	}
	public void setLabelSensorMode(String sensorMode) {
		labelSensorMode = "GAP";
		if (sensorMode.equals("GAP") || sensorMode.equals("G")) {
			this.labelSensorMode = "GAP";	
		}
		if (sensorMode.equals("BL") || sensorMode.equals("B")) {
			this.labelSensorMode = "BL";	
		}
		if (sensorMode.equals("CONT") || sensorMode.equals("C")) {
			this.labelSensorMode = "CONT";	
		}
		System.out.println("> Nuovo valore di LabelSensorMode = "+labelSensorMode);
	}
	public int getLabelGapLength() {
		return labelGapLength;
	}
	public void setLabelGapLength(int labelGapLength) {
		this.labelGapLength = labelGapLength;
	}
	public int getLabelOffsetLength() {
		return labelOffsetLength;
	}
	public void setLabelOffsetLength(int labelOffsetLength) {
		this.labelOffsetLength = labelOffsetLength;
	}

	public String makeLabelLengthFmtString() {
		String retVal = "";

		if (labelSensorMode.equals("GAP")) {
			retVal = String.format("%s,%s+%s", labelLength, labelGapLength, labelOffsetLength);
		}
		if (labelSensorMode.equals("BL")) {
			if (labelOffsetLength < 0) {
				retVal = String.format("%s,B%s%s", labelLength, labelGapLength, labelOffsetLength);
			} else {
				retVal = String.format("%s,B%s+%s", labelLength, labelGapLength, labelOffsetLength);	
			}
		}
		if (labelSensorMode.equals("CONT")) {
			retVal = String.format("%s,0+%s", labelLength, labelOffsetLength);
		}
		return retVal;
	}
}
