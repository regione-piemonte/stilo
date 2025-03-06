package it.eng.applet.configuration;

import java.util.List;

public class ParameterBean {

	public enum TipoStampa {
		
		PDF("pdf"), PRN("prn");
		
		private String value;
		
		private TipoStampa(String pString){
			value = pString;
		}
		
		public String getValue(){
			return value;
		}
		public static TipoStampa getRealValue(String pString){
			for (TipoStampa lTipoStampa : TipoStampa.values()){
				if (lTipoStampa.getValue().equalsIgnoreCase(pString)){
					return lTipoStampa;
				}
			}
			return null;
		}
	}
	private String callbackClose;
	private String callbackCancel;
	private List<TestoBarcodeBean> testo;
	private int numeroCopie;
	private String propertiesServlet;
	private String pdfServlet;
	private String idUtente;
	private String nomeStampante;
	private TipoStampa tipoStampa;
	private String idSchema;
	private String idDominio;
	private String barcodeEncoding;
	private String printCodeInSecondLabel;
	private boolean autoClose;
	
	public int getNumeroCopie() {
		return numeroCopie;
	}
	public void setNumeroCopie(int numeroCopie) {
		this.numeroCopie = numeroCopie;
	}
	public List<TestoBarcodeBean> getTesto() {
		return testo;
	}
	public void setTesto(List<TestoBarcodeBean> testo) {
		this.testo = testo;
	}
	public String getPropertiesServlet() {
		return propertiesServlet;
	}
	public void setPropertiesServlet(String propertiesServlet) {
		this.propertiesServlet = propertiesServlet;
	}
	public String getPdfServlet() {
		return pdfServlet;
	}
	public void setPdfServlet(String pdfServlet) {
		this.pdfServlet = pdfServlet;
	}
	public String getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(String idUtente) {
		this.idUtente = idUtente;
	}
	public String getNomeStampante() {
		return nomeStampante;
	}
	public void setNomeStampante(String nomeStampante) {
		this.nomeStampante = nomeStampante;
	}
	public TipoStampa getTipoStampa() {
		return tipoStampa;
	}
	public void setTipoStampa(TipoStampa tipoStampa) {
		this.tipoStampa = tipoStampa;
	}
	public String getCallbackClose() {
		return callbackClose;
	}
	public void setCallbackClose(String callbackClose) {
		this.callbackClose = callbackClose;
	}
	public String getCallbackCancel() {
		return callbackCancel;
	}
	public void setCallbackCancel(String callbackCancel) {
		this.callbackCancel = callbackCancel;
	}
	public String getIdSchema() {
		return idSchema;
	}
	public void setIdSchema(String idSchema) {
		this.idSchema = idSchema;
	}
	public String getIdDominio() {
		return idDominio;
	}
	public void setIdDominio(String idDominio) {
		this.idDominio = idDominio;
	}
	public String getBarcodeEncoding() {
		return barcodeEncoding;
	}
	public void setBarcodeEncoding(String barcodeEncoding) {
		this.barcodeEncoding = barcodeEncoding;
	}
	public String getPrintCodeInSecondLabel() {
		return printCodeInSecondLabel;
	}
	public void setPrintCodeInSecondLabel(String printCodeInSecondLabel) {
		this.printCodeInSecondLabel = printCodeInSecondLabel;
	}
	public boolean isAutoClose() {
		return autoClose;
	}
	public void setAutoClose(boolean autoClose) {
		this.autoClose = autoClose;
	}
	
}
