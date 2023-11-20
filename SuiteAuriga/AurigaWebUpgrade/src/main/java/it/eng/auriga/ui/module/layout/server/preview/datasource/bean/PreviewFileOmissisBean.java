/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class PreviewFileOmissisBean extends PreviewFileBean{

	private String fileName;
	// numero delle pagine da rimuovere separate da ','
	private String pagesToRemove;
	// stringa json con coordinate e numero pagina su cui 
	// è stata applicata la peccetta
	private String ritagli;
	// esito pulizia del testo sotto le peccette
	private String esitoPeccette;
	// almeno una peccetta applicata su testo
	private boolean tryToCleanText = false;
	// gradi di rotazione file
	private Integer rotatePdf;
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getPagesToRemove() {
		return pagesToRemove;
	}

	public void setPagesToRemove(String pagesToRemove) {
		this.pagesToRemove = pagesToRemove;
	}

	public String getRitagli() {
		return ritagli;
	}

	public void setRitagli(String ritagli) {
		this.ritagli = ritagli;
	}

	public String getEsitoPeccette() {
		return esitoPeccette;
	}
	
	public void setEsitoPeccette(String esitoPeccette) {
		this.esitoPeccette = esitoPeccette;
	}

	public boolean isTryToCleanText() {
		return tryToCleanText;
	}

	public void setTryToCleanText(boolean tryToCleanText) {
		this.tryToCleanText = tryToCleanText;
	}

	public Integer getRotatePdf() {
		return rotatePdf;
	}

	public void setRotatePdf(Integer rotatePdf) {
		this.rotatePdf = rotatePdf;
	}
	
}
