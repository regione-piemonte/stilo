/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Arrays;

public class InfoFileBean {

	private String correctFileName;
	private long bytes;
	private String algoritmo;
	private String encoding;
	private String idFormato;
	private String mimetype;
	private String impronta;
	private boolean firmato;
	private String tipoFirma;
	private String infoFirma;
	private boolean convertibile;
	private boolean firmaValida;
	private String[] firmatari;
	private Firmatari[] buste;
	private boolean daScansione;
	private String[] openFolders;
	
	public String getCorrectFileName() {
		return correctFileName;
	}
	public void setCorrectFileName(String correctFileName) {
		this.correctFileName = correctFileName;
	}
	
	public long getBytes() {
		return bytes;
	}
	public void setBytes(long bytes) {
		this.bytes = bytes;
	}
	
	public String getAlgoritmo() {
		return algoritmo;
	}
	public void setAlgoritmo(String algoritmo) {
		this.algoritmo = algoritmo;
	}
	
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	public String getIdFormato() {
		return idFormato;
	}
	public void setIdFormato(String idFormato) {
		this.idFormato = idFormato;
	}
	
	public String getMimetype() {
		return mimetype;
	}
	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}
	
	public String getImpronta() {
		return impronta;
	}
	public void setImpronta(String impronta) {
		this.impronta = impronta;
	}
	
	public boolean isFirmato() {
		return firmato;
	}
	public void setFirmato(boolean firmato) {
		this.firmato = firmato;
	}
	
	public String getTipoFirma() {
		return tipoFirma;
	}
	public void setTipoFirma(String tipoFirma) {
		this.tipoFirma = tipoFirma;
	}
	
	public String getInfoFirma() {
		return infoFirma;
	}
	public void setInfoFirma(String infoFirma) {
		this.infoFirma = infoFirma;
	}
	
	public boolean isConvertibile() {
		return convertibile;
	}
	public void setConvertibile(boolean convertibile) {
		this.convertibile = convertibile;
	}
	
	public boolean isFirmaValida() {
		return firmaValida;
	}
	public void setFirmaValida(boolean firmaValida) {
		this.firmaValida = firmaValida;
	}
	
	public String[] getFirmatari() {
		return firmatari;
	}
	public void setFirmatari(String[] firmatari) {
		this.firmatari = firmatari;
	}
	
	public Firmatari[] getBuste() {
		return buste;
	}
	public void setBuste(Firmatari[] buste) {
		this.buste = buste;
	}
	
	public boolean isDaScansione() {
		return daScansione;
	}
	public void setDaScansione(boolean daScansione) {
		this.daScansione = daScansione;
	}
	
	public String[] getOpenFolders() {
		return openFolders;
	}
	public void setOpenFolders(String[] openFolders) {
		this.openFolders = openFolders;
	}
	
	@Override
	public String toString() {
		return String
				.format("InfoFileBean [correctFileName=%s, bytes=%s, algoritmo=%s, encoding=%s, idFormato=%s, mimetype=%s, impronta=%s, firmato=%s, tipoFirma=%s, infoFirma=%s, convertibile=%s, firmaValida=%s, firmatari=%s, buste=%s, daScansione=%s, openFolders=%s]",
						correctFileName, bytes, algoritmo, encoding, idFormato, mimetype, impronta, firmato, tipoFirma, infoFirma,
						convertibile, firmaValida, Arrays.toString(firmatari), Arrays.toString(buste), daScansione,
						Arrays.toString(openFolders));
	}
	
}
