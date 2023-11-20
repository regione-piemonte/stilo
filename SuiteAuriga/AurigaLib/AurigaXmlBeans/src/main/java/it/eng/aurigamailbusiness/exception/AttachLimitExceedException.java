/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class AttachLimitExceedException extends Exception {

	private static final long serialVersionUID = -1097973838831777087L;
	private String fileName;
	private Double limit;
	private String azione;

	public AttachLimitExceedException(String fileName, Double limit) {
		super("Impossibile spedire la mail, allegato " + fileName + " di dimensioni superiori al limite previsto di:  " + limit + " Mb");
		this.fileName = fileName;
		this.limit = limit;
	}

	public AttachLimitExceedException(String fileName, Double limit, String azione) {
		super("Impossibile " + azione + " la mail, allegato " + fileName + " di dimensioni superiori al limite previsto di:  " + limit + " Mb");
		this.fileName = fileName;
		this.limit = limit;
		this.azione = azione;
	}

	public AttachLimitExceedException(Double limit) {
		super("Impossibile spedire la mail, la dimensione complessiva degli allegati supera il limite consentito di: " + limit + " Mb");
		this.limit = limit;
	}

	public AttachLimitExceedException(String messagge, String fileName, Double limit, String azione) {
		super(messagge);
		this.fileName = fileName;
		this.limit = limit;
		this.azione = azione;
	}

	public AttachLimitExceedException(Double limit, String azione) {
		super("Impossibile spedire la mail:  la dimensione complessiva degli allegati supera la soglia di " +limit+ " MB; per poter effettuare l'invio a più destinatari devi selezionare l'opzione di invio separato per ogni destinatario");
		this.limit = limit;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Double getLimit() {
		return limit;
	}

	public void setLimit(Double limit) {
		this.limit = limit;
	}

	public String getAzione() {
		return azione;
	}

	public void setAzione(String azione) {
		this.azione = azione;
	}

}
