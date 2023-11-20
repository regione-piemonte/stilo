/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AllegatoDocumentoInBean extends CreaModDocumentoInBean {

	private static final long serialVersionUID = -1253867719412757541L;

	//#DisplayFilename_Ver 		(obblig. se si vuole caricare la ver. elettronica) Nome con cui mostrare/in cui estrarre il file corrispondente alla versione elettronica del documento
	//#URI_Ver					(obblig. se si vuole caricare la ver. elettronica) Riferimento univoco per reperire nella repository il file corrispondente alla versione del documento
	//#Impronta_Ver			(obblig. se si vuole caricare la ver. elettronica) Impronta (SHA1) versione elettronica del documento
	//#Algoritmo_Impronta_Ver	(obblig. se si vuole caricare la ver. elettronica) Algoritmo con cui è stata calcolata l'impronta (SHA-1, SHA-256, MD5 ecc)
	//#Encoding_Impronta_Ver	(obblig. se si vuole caricare la ver. elettronica) Encoding con cui è stata calcolata l'impronta: hex = esadecimale, base64 = in base 64
	//#Dimensione_Ver			(obblig. se si vuole caricare la ver. elettronica)  Dimensione in bytes della versione
	//#FlgFirmata_Ver			(valori 1/0/NULL) Se 1 significa che la versione elettronica è firmata digitalmente, se 0 o NULL che non è firmata
	//#Mimetype_Ver			(obblig. se si vuole caricare la ver. elettronica) Mimetype del file della versione (se è firmato con busta p7m, m7m o tsd è il formato del file "sbustato)
	//#Cod_Ver				Codice identificativo da assegnare alla versione elettronica
	//						Se non è valorizzato/manca del tutto nell'XML alla versione viene assegnato in automatico un codice
	//#Note_Ver	 			Note versione elettronica del documento
	//#FlgPubblicata_Ver 		(valori 1/0/NULL) Se 1 indica che la versione è pubblicata
	//#IdFormatoEl_Ver 			Identificativo del formato elettronico della versione (quello con cui è registrato in banca dati)
	//#NomeFormatoEl_Ver			Nome del formato elettronico della versione (quello con cui è registrato in banca dati)
	//#FlgDaScansione_Ver 			(valori 1/0/NULL) Se 1 indica che la versione elettronica è un'immagine acquisita da scanner
	//#DtScansione_Ver 			Data in cui l'immagine corrispondente alla versione elettronica è stata acquisita da scanner (nel formato dato dal parametro di conf. FMT_STD_DATA)
	//#IdUserScansione_Ver 			Id. (quello interno al sistema) dell'utente che ha scansionato l'immagine corrispondente alla versione
	//#CIEstUserScansione_Ver		Codice che identifica nell'applicazione esterna da cui ci si connette l'utente che ha scansionato l'immagine corrispondente alla versione
	//#DesUserScansione_Ver 		Denominazione dell'utente che ha scansionato l'immagine corrispondente alla versione
	//#SpecificheScansione_Ver 		Specifiche (es: risoluzione) sul processo di digitalizzazione che ha prodotto la versione

	@XmlVariabile(nome="#IdUD", tipo = TipoVariabile.SEMPLICE)
	private BigDecimal idUD;
	@XmlVariabile(nome="#CodNaturaRelVsUD", tipo = TipoVariabile.SEMPLICE)
	private String natura = "ALL";
	@XmlVariabile(nome="", tipo = TipoVariabile.NESTED)
	private FileStoreBean fileStoreBean;

	public BigDecimal getIdUD() {
		return idUD;
	}
	public void setIdUD(BigDecimal idUD) {
		this.idUD = idUD;
	}

	public String getNatura() {
		return natura;
	}
	public void setNatura(String natura) {
		this.natura = natura;
	}
	
	public FileStoreBean getFileStoreBean() {
		return fileStoreBean;
	}
	public void setFileStoreBean(FileStoreBean fileStoreBean) {
		this.fileStoreBean = fileStoreBean;
	}
}
