package it.eng.hybrid.module.stampaEtichette.bean;

import java.util.List;

public class StampaEtichettaPropertiesBean {
	//Generali
	private String etichettePrinter;
	private Integer fontSizeTestata;
	private String exepath;
	private String runningExe;
	private String labelSensorMode;
	private String fontName;
	private String cmdChangeDefaulPrinter;
	private Integer labelWidth;
	private Integer labelLength;
	private Integer secToSleep;
	private Integer labelGapLength;
	private Integer labelOffsetLength;
	//Barcode
	private Integer offsetXBarcode;
	private Integer offsetYBarcode;
	//Righe
	private List<ZebraRigaBean> righe;
	private String fontTesto;
	public String getEtichettePrinter() {
		return etichettePrinter;
	}
	public void setEtichettePrinter(String etichettePrinter) {
		this.etichettePrinter = etichettePrinter;
	}
	public Integer getFontSizeTestata() {
		return fontSizeTestata;
	}
	public void setFontSizeTestata(Integer fontSizeTestata) {
		this.fontSizeTestata = fontSizeTestata;
	}
	public String getExepath() {
		return exepath;
	}
	public void setExepath(String exepath) {
		this.exepath = exepath;
	}
	public String getRunningExe() {
		return runningExe;
	}
	public void setRunningExe(String runningExe) {
		this.runningExe = runningExe;
	}
	public String getLabelSensorMode() {
		return labelSensorMode;
	}
	public void setLabelSensorMode(String labelSensorMode) {
		this.labelSensorMode = labelSensorMode;
	}
	public String getFontName() {
		return fontName;
	}
	public void setFontName(String fontName) {
		this.fontName = fontName;
	}
	public String getCmdChangeDefaulPrinter() {
		return cmdChangeDefaulPrinter;
	}
	public void setCmdChangeDefaulPrinter(String cmdChangeDefaulPrinter) {
		this.cmdChangeDefaulPrinter = cmdChangeDefaulPrinter;
	}
	public Integer getLabelWidth() {
		return labelWidth;
	}
	public void setLabelWidth(Integer labelWidth) {
		this.labelWidth = labelWidth;
	}
	public Integer getLabelLength() {
		return labelLength;
	}
	public void setLabelLength(Integer labelLength) {
		this.labelLength = labelLength;
	}
	public Integer getSecToSleep() {
		return secToSleep;
	}
	public void setSecToSleep(Integer secToSleep) {
		this.secToSleep = secToSleep;
	}
	public Integer getLabelGapLength() {
		return labelGapLength;
	}
	public void setLabelGapLength(Integer labelGapLength) {
		this.labelGapLength = labelGapLength;
	}
	public Integer getOffsetXBarcode() {
		return offsetXBarcode;
	}
	public void setOffsetXBarcode(Integer offsetXBarcode) {
		this.offsetXBarcode = offsetXBarcode;
	}
	public Integer getOffsetYBarcode() {
		return offsetYBarcode;
	}
	public void setOffsetYBarcode(Integer offsetYBarcode) {
		this.offsetYBarcode = offsetYBarcode;
	}
	public List<ZebraRigaBean> getRighe() {
		return righe;
	}
	public void setRighe(List<ZebraRigaBean> righe) {
		this.righe = righe;
	}
	public String getFontTesto() {
		return fontTesto;
	}
	public void setFontTesto(String fontTesto) {
		this.fontTesto = fontTesto;
	}
	public Integer getLabelOffsetLength() {
		return labelOffsetLength;
	}
	public void setLabelOffsetLength(Integer labelOffsetLength) {
		this.labelOffsetLength = labelOffsetLength;
	}
	
}
