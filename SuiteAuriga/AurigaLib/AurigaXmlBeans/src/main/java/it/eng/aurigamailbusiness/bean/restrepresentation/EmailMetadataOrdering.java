/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class EmailMetadataOrdering {
	
	private EmailMetadata intestazioneColonna;
	private boolean discendente;
	
	public EmailMetadata getIntestazioneColonna() {
		return intestazioneColonna;
	}
	public void setIntestazioneColonna(EmailMetadata intestazioneColonna) {
		this.intestazioneColonna = intestazioneColonna;
	}
	
	public boolean isDiscendente() {
		return discendente;
	}
	public void setDiscendente(boolean discendente) {
		this.discendente = discendente;
	}
	
}
