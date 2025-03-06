package it.eng.hybrid.module.pieChart.parameters;

/**
 * Classe che rappresenta i parametri in ingresso 
 * @author Rametta
 *
 */
public class ReportParameterBean {

	private String percentageServlet;
	private String pdfBuilderPercentageServlet;
	private String xlsBuilderPercentageServlet;
	private int width;
	private int height;
	private String idUtente;
	private String idDominio;
	private String richiesta;
	private String idSchema;
	private String level;
	private String tipoReport;
	private String tipoRegistrazione;
	private String da;
	private String a;
	/**
	 * Restituisce la servlet da cui reperire le informazioni del grafico principale
	 * @return
	 */
	public String getPercentageServlet() {
		return percentageServlet;
	}
	/**
	 * Setta la servlet da cui reperire le informazioni del grafico principale
	 * @param percentageServlet
	 */
	public void setPercentageServlet(String percentageServlet) {
		this.percentageServlet = percentageServlet;
	}
	/**
	 * Restituisce la proprietà width 
	 * @return
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * Setta la proprietà width 
	 * @return
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * Restituisce la proprietà height
	 * @return
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * Restituisce la proprietà height 
	 * @return
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	public String getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(String idUtente) {
		this.idUtente = idUtente;
	}
	public String getIdDominio() {
		return idDominio;
	}
	public void setIdDominio(String idDominio) {
		this.idDominio = idDominio;
	}
	public String getRichiesta() {
		return richiesta;
	}
	public void setRichiesta(String richiesta) {
		this.richiesta = richiesta;
	}
	public String getIdSchema() {
		return idSchema;
	}
	public void setIdSchema(String idSchema) {
		this.idSchema = idSchema;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getTipoReport() {
		return tipoReport;
	}
	public void setTipoReport(String tipoReport) {
		this.tipoReport = tipoReport;
	}
	public String getTipoRegistrazione() {
		return tipoRegistrazione;
	}
	public void setTipoRegistrazione(String tipoRegistrazione) {
		this.tipoRegistrazione = tipoRegistrazione;
	}
	public String getDa() {
		return da;
	}
	public void setDa(String da) {
		this.da = da;
	}
	public String getA() {
		return a;
	}
	public void setA(String a) {
		this.a = a;
	}
	public void setPdfBuilderPercentageServlet(
			String pdfBuilderPercentageServlet) {
		this.pdfBuilderPercentageServlet = pdfBuilderPercentageServlet;
	}
	public String getPdfBuilderPercentageServlet() {
		return pdfBuilderPercentageServlet;
	}
	public String getXlsBuilderPercentageServlet() {
		return xlsBuilderPercentageServlet;
	}
	public void setXlsBuilderPercentageServlet(String xlsBuilderPercentageServlet) {
		this.xlsBuilderPercentageServlet = xlsBuilderPercentageServlet;
	}
	
	
}
