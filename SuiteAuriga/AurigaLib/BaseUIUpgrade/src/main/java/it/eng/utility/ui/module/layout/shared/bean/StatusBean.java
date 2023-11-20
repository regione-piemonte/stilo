/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public class StatusBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2058490531499093696L;

	private String smartId;
	private int caricato;
	private int numero;
	private boolean forceStopDownload;
	private boolean finito;
	private boolean finish;
	private boolean uploadCancellato;
	private int numFileInElaborazione;
	private int numFileTotali;
	
	public String getSmartId() {
		return smartId;
	}
	
	public void setSmartId(String smartId) {
		this.smartId = smartId;
	}

	public void setCaricato(int caricato) {
		this.caricato = caricato;
	}

	public int getCaricato() {
		return caricato;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getNumero() {
		return numero;
	}

	public void setForceStopDownload(boolean forceStopDownload) {
		this.forceStopDownload = forceStopDownload;
	}

	public boolean isForceStopDownload() {
		return forceStopDownload;
	}

	public void setFinito(boolean finito) {
		this.finito = finito;
	}

	public boolean getFinito() {
		return finito;
	}

	public void setFinish(boolean finish) {
		this.finish = finish;
	}

	public boolean isFinish() {
		return finish;
	}

	public boolean isUploadCancellato() {
		return uploadCancellato;
	}

	public void setUploadCancellato(boolean uploadCancellato) {
		this.uploadCancellato = uploadCancellato;
	}

	public int getNumFileInElaborazione() {
		return numFileInElaborazione;
	}

	public void setNumFileInElaborazione(int numFileInElaborazione) {
		this.numFileInElaborazione = numFileInElaborazione;
	}

	public int getNumFileTotali() {
		return numFileTotali;
	}

	public void setNumFileTotali(int numFileTotali) {
		this.numFileTotali = numFileTotali;
	}

}
