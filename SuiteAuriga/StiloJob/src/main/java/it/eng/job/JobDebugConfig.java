/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

public class JobDebugConfig implements Serializable {

	private static final long serialVersionUID = 80941552362789589L;
	
	private boolean debugAttivo = false;
	private Integer numMaxCicli = Integer.MAX_VALUE;
	private boolean saltaVerificaPresenzaFile;
	private boolean saltaInvioMail;
	private String mittFarloccoInvioMail;
	private List<String> destFarlocchiInvioMail;
	private List<String> destFarlocchiInvioMailPEC;
	private boolean usaFileDiTest;
	private boolean popolaFatture;
	private boolean popolaNotifiche;
	
	public JobDebugConfig() {
	}

	public Integer getNumMaxCicli() {
		return numMaxCicli;
	}
	public void setNumMaxCicli(Integer numMaxCicli) {
		this.numMaxCicli = numMaxCicli;
	}
	public void setNumMaxCicli(String numMaxCicli) {
		this.numMaxCicli = Integer.valueOf(numMaxCicli);
	}
	
	public boolean isDebugAttivo() {
		return debugAttivo;
	}
	public void setDebugAttivo(boolean debugAttivo) {
		this.debugAttivo = debugAttivo;
	}
	
	public boolean isSaltaVerificaPresenzaFile() {
		return saltaVerificaPresenzaFile;
	}
	public void setSaltaVerificaPresenzaFile(boolean saltaVerificaPresenzaFile) {
		this.saltaVerificaPresenzaFile = saltaVerificaPresenzaFile;
	}

	public boolean isSaltaInvioMail() {
		return saltaInvioMail;
	}
	public void setSaltaInvioMail(boolean saltaInvioMail) {
		this.saltaInvioMail = saltaInvioMail;
	}

	public String getMittFarloccoInvioMail() {
		return mittFarloccoInvioMail;
	}
	public void setMittFarloccoInvioMail(String mittFarloccoInvioMail) {
		this.mittFarloccoInvioMail = mittFarloccoInvioMail;
	}
	
	public List<String> getDestFarlocchiInvioMailPEC() {
		return destFarlocchiInvioMailPEC;
	}
	public void setDestFarlocchiInvioMailPEC(
			List<String> destFarlocchiInvioMailPEC) {
		this.destFarlocchiInvioMailPEC = destFarlocchiInvioMailPEC;
	}
	
	public List<String> getDestFarlocchiInvioMail() {
		return destFarlocchiInvioMail;
	}
	public void setDestFarlocchiInvioMail(List<String> destFarlocchiInvioMail) {
		this.destFarlocchiInvioMail = destFarlocchiInvioMail;
	}
	
	public boolean isUsaFileDiTest() {
		return usaFileDiTest;
	}
	public void setUsaFileDiTest(boolean usaFileDiTest) {
		this.usaFileDiTest = usaFileDiTest;
	}
	
	/**
	 * @return the popolaFatture
	 */
	public boolean isPopolaFatture() {
		return popolaFatture;
	}

	/**
	 * @param popolaFatture the popolaFatture to set
	 */
	public void setPopolaFatture(boolean popolaFatture) {
		this.popolaFatture = popolaFatture;
	}

	/**
	 * @return the popolaNotifiche
	 */
	public boolean isPopolaNotifiche() {
		return popolaNotifiche;
	}

	/**
	 * @param popolaNotifiche the popolaNotifiche to set
	 */
	public void setPopolaNotifiche(boolean popolaNotifiche) {
		this.popolaNotifiche = popolaNotifiche;
	}

	@Override
	public String toString() {
		return String.format(
				"JobDebugConfig [debugAttivo=%s, numMaxCicli=%s, saltaVerificaPresenzaFile=%s, saltaInvioMail=%s, mittFarloccoInvioMail=%s, destFarlocchiInvioMail=%s, destFarlocchiInvioMailPEC=%s, usaFileDiTest=%s, popolaFatture=%s, popolaNotifiche=%s]",
				debugAttivo, numMaxCicli, saltaVerificaPresenzaFile, saltaInvioMail, mittFarloccoInvioMail,
				destFarlocchiInvioMail, destFarlocchiInvioMailPEC, usaFileDiTest, popolaFatture, popolaNotifiche);
	}

}
