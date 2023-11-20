/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.JSON;

import it.eng.document.function.bean.Flag;

public class InfoFileRecord extends Record{
	
	public static final DateTimeFormat display_datetime_format = DateTimeFormat.getFormat("dd/MM/yyyy HH:mm");
	public static final DateTimeFormat upload_datetime_format = DateTimeFormat.getFormat("MMM dd, yyyy hh:mm:ss a");
	public static final DateTimeFormat date_format = DateTimeFormat.getFormat("yyyy-MM-dd");
	public static final DateTimeFormat time_format = DateTimeFormat.getFormat("HH:mm:ss");
	public static final DateTimeFormat datetime_format = DateTimeFormat.getFormat("yyyy-MM-dd'T'HH:mm:ss");
	public static final DateTimeFormat datetime_format_toda = DateTimeFormat.getFormat("yyyyMMdd'T'HHmmss'Z'");
	public static final DateTimeFormat display_date_format = DateTimeFormat.getFormat("dd/MM/yyyy");
	
	DateTimeFormat dtf = DateTimeFormat.getFormat("MM/dd/yyyy HH:mm");


	private boolean firmato;
	private String tipoFirma;
	private String infoFirma;
	private String[] firmatari;
	private FirmatarioBean[] buste;
	private boolean convertibile = true;
	private String mimetype;
	private long bytes;
	private boolean firmaValida;
	private String correctFileName;
	private String impronta;
	private String improntaPdf;
	private String improntaFilePreFirma;
	private boolean daScansione;
	private boolean uploadError = false;
	private String[] openFolders;
	private InfoFirmaMarcaRecord infoFirmaMarca; 
	private boolean pdfProtetto;
	private boolean pdfEditabile;
	private boolean pdfConCommenti;
	private Integer numPaginePdf;
	
	public static InfoFileRecord buildInfoFileRecord(Object lRecord){
		if (lRecord == null) return null;
		else return new InfoFileRecord((JavaScriptObject)lRecord);
	}
	
	public static InfoFileRecord buildInfoFileString(String lRecord){
		return lRecord != null ? new InfoFileRecord(JSON.decode(lRecord)) : null;
	}
	
	public InfoFileRecord(JavaScriptObject lJavaScriptObject){
		super(lJavaScriptObject);
		firmato = getAttributeAsBoolean("firmato");
		infoFirma = getAttribute("infoFirma");
		convertibile = getAttributeAsBoolean("convertibile");
		mimetype = getAttribute("mimetype");
		bytes = getAttributeAsLong("bytes");
		firmaValida = getAttributeAsBoolean("firmaValida");
		setTipoFirma(getAttribute("tipoFirma"));
		correctFileName = getAttribute("correctFileName");
		firmatari = getAttributeAsStringArray("firmatari");
		openFolders = getAttributeAsStringArray("openFolders");
		Record[] lRecords= getAttributeAsRecordArray("buste");
		if (lRecords!=null){
			buste = new FirmatarioBean[lRecords.length];
			int count = 0;
			for (Record lRecord : lRecords){
				FirmatarioBean lFirmatarioBean = new FirmatarioBean();
				lFirmatarioBean.setFiglioDi(lRecord.getAttribute("figlioDi"));
				lFirmatarioBean.setId(lRecord.getAttribute("id"));
				lFirmatarioBean.setSubject(lRecord.getAttribute("subject"));
				lFirmatarioBean.setNomeFirmatario(lRecord.getAttribute("nomeFirmatario"));
				lFirmatarioBean.setEnteCertificatore(lRecord.getAttribute("enteCertificatore"));
				lFirmatarioBean.setDataFirma(convertAttributeToDate(lRecord, "dataFirma"));
				lFirmatarioBean.setDataScadenza(convertAttributeToDate(lRecord, "dataScadenza"));
				lFirmatarioBean.setDataEmissione(convertAttributeToDate(lRecord, "dataEmissione"));
				lFirmatarioBean.setStatoCertificato(lRecord.getAttribute("statoCertificato"));
				lFirmatarioBean.setNumeroCertificato(lRecord.getAttribute("numeroCertificato"));
				lFirmatarioBean.setSerialNumber(lRecord.getAttribute("serialNumber"));
				lFirmatarioBean.setQcStatements(lRecord.getAttributeAsStringArray("qcStatements"));
				lFirmatarioBean.setKeyUsages(lRecord.getAttributeAsStringArray("keyUsages"));
				lFirmatarioBean.setVerificationStatus(lRecord.getAttribute("verificationStatus"));
				lFirmatarioBean.setCertExpiration(lRecord.getAttribute("certExpiration"));
				lFirmatarioBean.setCrlResult(lRecord.getAttribute("crlResult"));
				lFirmatarioBean.setCaReliability(lRecord.getAttribute("caReliability"));
				lFirmatarioBean.setFirmaValida(Boolean.parseBoolean(lRecord.getAttribute("firmaValida")));
				lFirmatarioBean.setDataVerificaFirma(convertAttributeToDate(lRecord, "dataVerificaFirma"));				
				lFirmatarioBean.setTitolareFirma(lRecord.getAttribute("titolareFirma"));
				lFirmatarioBean.setCodiceActivityFirma(lRecord.getAttribute("codiceActivityFirma"));
				lFirmatarioBean.setIdUtenteLavoroFirma(lRecord.getAttribute("idUtenteLavoroFirma"));
				lFirmatarioBean.setIdUtenteLoggatoFirma(lRecord.getAttribute("idUtenteLoggatoFirma"));
				Record marcaRecord = lRecord.getAttributeAsRecord("marcaTemporale");
				if (marcaRecord != null) {
					MarcaBean marcaBean = new MarcaBean();
					marcaBean.setTsaName(marcaRecord.getAttribute("tsaName"));
					marcaBean.setSerialNumber(marcaRecord.getAttribute("serialNumber"));
					marcaBean.setDate(marcaRecord.getAttribute("date"));
					marcaBean.setPolicy(marcaRecord.getAttribute("policy"));
					lFirmatarioBean.setMarcaTemporale(marcaBean);
				}
				buste[count] = lFirmatarioBean;
				count++;
			}
		}
		impronta = getAttribute("impronta");
		improntaPdf = getAttribute("improntaPdf");
		improntaFilePreFirma = getAttribute("improntaFilePreFirma");
		daScansione = getAttributeAsBoolean("daScansione");
		uploadError = getAttributeAsBoolean("uploadError");
		
		if (getAttributeAsRecord("infoFirmaMarca") != null) {
			Record attributeRecord = getAttributeAsRecord("infoFirmaMarca");
			InfoFirmaMarcaRecord lInfoFirmaMarcaRecord = new InfoFirmaMarcaRecord();
			lInfoFirmaMarcaRecord.setBustaCrittograficaFattaDaAuriga(attributeRecord.getAttributeAsBoolean("bustaCrittograficaFattaDaAuriga"));
			lInfoFirmaMarcaRecord.setBustaCrittograficaInComplPassoIter(attributeRecord.getAttributeAsBoolean("bustaCrittograficaInComplPassoIter"));
			lInfoFirmaMarcaRecord.setTipoBustaCrittografica(attributeRecord.getAttribute("tipoBustaCrittografica"));
			lInfoFirmaMarcaRecord.setInfoBustaCrittografica(attributeRecord.getAttribute("infoBustaCrittografica"));
			lInfoFirmaMarcaRecord.setFirmeNonValideBustaCrittografica(attributeRecord.getAttributeAsBoolean("firmeNonValideBustaCrittografica"));
			lInfoFirmaMarcaRecord.setFirmeExtraAuriga(attributeRecord.getAttributeAsBoolean("firmeExtraAuriga"));
			lInfoFirmaMarcaRecord.setDataOraVerificaFirma(convertAttributeToDate(attributeRecord, "dataOraVerificaFirma"));
			lInfoFirmaMarcaRecord.setMarcaTemporaleAppostaDaAuriga(attributeRecord.getAttributeAsBoolean("marcaTemporaleAppostaDaAuriga"));
			lInfoFirmaMarcaRecord.setDataOraMarcaTemporale(convertAttributeToDate(attributeRecord, "dataOraMarcaTemporale"));
			lInfoFirmaMarcaRecord.setTipoMarcaTemporale(attributeRecord.getAttribute("tipoMarcaTemporale"));
			lInfoFirmaMarcaRecord.setInfoMarcaTemporale(attributeRecord.getAttribute("infoMarcaTemporale"));
			lInfoFirmaMarcaRecord.setMarcaTemporaleNonValida(attributeRecord.getAttributeAsBoolean("marcaTemporaleNonValida"));
			lInfoFirmaMarcaRecord.setDataOraVerificaMarcaTemporale(convertAttributeToDate(attributeRecord, "dataOraVerificaMarcaTemporale"));
			infoFirmaMarca = lInfoFirmaMarcaRecord;
		}
		
		pdfProtetto = getAttributeAsBoolean("pdfProtetto");
		pdfEditabile = getAttributeAsBoolean("pdfEditabile");
		pdfConCommenti = getAttributeAsBoolean("pdfConCommenti");
		numPaginePdf = getAttributeAsInt("numPaginePdf");
	}
	
	private Date convertAttributeToDate(Record record, String property) {
		if (record.getAttribute(property) == null){
			return null;
		} else {
			
			try {
				return display_datetime_format.parse(record.getAttribute(property));
			} catch (Exception e) {}	
			try {
				return upload_datetime_format.parse(record.getAttribute(property));
			} catch (Exception e) {}	
			try {
				return datetime_format.parse(record.getAttribute(property));
			} catch (Exception e) {}	
			try {
				return date_format.parse(record.getAttribute(property));
			} catch (Exception e) {}		
			try {
				return display_date_format.parse(record.getAttribute(property));
			} catch (Exception e) {}	
			return null;
		}
	}
	
	public InfoFileRecord(Record lRecord){
		this(lRecord.getJsObj());
	}
	
	public InfoFileRecord(Object lRecord){
		this((JavaScriptObject)lRecord);
	}

	public boolean isFirmato() {
		return firmato;
	}

	public void setFirmato(boolean firmato) {
		this.firmato = firmato;
		setAttribute("firmato", firmato);
	}

	public String getInfoFirma() {
		return infoFirma;
	}

	public void setInfoFirma(String infoFirma) {
		this.infoFirma = infoFirma;
		setAttribute("infoFirma", infoFirma);
	}

	public boolean isConvertibile() {
		return convertibile;
	}

	public void setConvertibile(boolean convertibile) {
		this.convertibile = convertibile;
		setAttribute("convertibile", convertibile);
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
		setAttribute("mimetype", mimetype);
	}

	public boolean isFirmaValida() {
		return firmaValida;
	}

	public void setFirmaValida(boolean firmaValida) {
		this.firmaValida = firmaValida;
		setAttribute("firmaValida", firmaValida);
	}

	public void setTipoFirma(String tipoFirma) {
		this.tipoFirma = tipoFirma;
		setAttribute("tipoFirma", tipoFirma);
	}

	public String getTipoFirma() {
		return tipoFirma;
	}

	public void setCorrectFileName(String correctFileName) {
		this.correctFileName = correctFileName;
		setAttribute("correctFileName", correctFileName);
	}

	public String getCorrectFileName() {
		return correctFileName;
	}

	public String[] getFirmatari() {
		return firmatari;
	}

	public void setFirmatari(String[] firmatari) {
		this.firmatari = firmatari;
		setAttribute("firmatari", firmatari);
	}

	public void setImpronta(String impronta) {
		this.impronta = impronta;
		setAttribute("impronta", impronta);
	}

	public String getImpronta() {
		return impronta;
	}
	
	public void setImprontaPdf(String improntaPdf) {
		this.improntaPdf = improntaPdf;
		setAttribute("improntaPdf", improntaPdf);
	}

	public String getImprontaPdf() {
		return improntaPdf;
	}
		
	public String getImprontaFilePreFirma() {
		return improntaFilePreFirma;
	}

	public void setImprontaFilePreFirma(String improntaFilePreFirma) {
		this.improntaFilePreFirma = improntaFilePreFirma;
		setAttribute("improntaFilePreFirma", improntaFilePreFirma);
	}

	public void setDaScansione(boolean daScansione) {
		this.daScansione = daScansione;
		setAttribute("daScansione", daScansione);
	}

	public boolean isDaScansione() {
		return daScansione;
	}

	public boolean isUploadError() {
		return uploadError;
	}

	public void setUploadError(boolean uploadError) {
		this.uploadError = uploadError;
		setAttribute("uploadError", uploadError);
	}

	public void setBuste(FirmatarioBean[] buste) {
		this.buste = buste;
		setAttribute("buste", buste);
	}

	public FirmatarioBean[] getBuste() {
		return buste;
	}

	public String[] getOpenFolders() {
		return openFolders;
	}

	public void setOpenFolders(String[] openFolders) {
		this.openFolders = openFolders;
		setAttribute("openFolders", openFolders);
	}

	public long getBytes() {
		return bytes;
	}

	public void setBytes(long bytes) {
		this.bytes = bytes;
		setAttribute("bytes", bytes);
	}
	
	public InfoFirmaMarcaRecord getInfoFirmaMarca() {
		return infoFirmaMarca;
	}

	public void setInfoFirmaMarca(InfoFirmaMarcaRecord infoFirmaMarca) {
		this.infoFirmaMarca = infoFirmaMarca;
	}
	
	public boolean isPdfProtetto() {
		return pdfProtetto;
	}

	public void setPdfProtetto(boolean pdfProtetto) {
		this.pdfProtetto = pdfProtetto;
	}

	public boolean isPdfEditabile() {
		return pdfEditabile;
	}

	public boolean isPdfConCommenti() {
		return pdfConCommenti;
	}

	public void setPdfEditabile(boolean pdfEditabile) {
		this.pdfEditabile = pdfEditabile;
	}

	public void setPdfConCommenti(boolean pdfConCommenti) {
		this.pdfConCommenti = pdfConCommenti;
	}

	public Integer getNumPaginePdf() {
		return numPaginePdf;
	}

	public void setNumPaginePdf(Integer numPaginePdf) {
		this.numPaginePdf = numPaginePdf;
	}
	
}
