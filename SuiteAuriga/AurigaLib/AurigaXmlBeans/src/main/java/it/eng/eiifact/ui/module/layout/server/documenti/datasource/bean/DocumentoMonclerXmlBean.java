/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

/**
 * Rappresentazione java dei dati estratti dalla store sottoforma di xml
 * @author ottavio passalacqua
 *
 */
public class DocumentoMonclerXmlBean implements Serializable {

 
	private static final long serialVersionUID = 1L;

	@NumeroColonna(numero = "1")
	private String flgUdFolder;
	
	@NumeroColonna(numero = "2")
	private BigDecimal idUd;

	@NumeroColonna(numero = "15")
    @TipoData(tipo=Tipo.DATA)
	private Date dtArrivo;
	
	@NumeroColonna(numero = "18")
	private String oggetto;

	@NumeroColonna(numero = "22")
	private String NomeFilePrimario;

	@NumeroColonna(numero = "33")
	private String idDoc;
	
	@NumeroColonna(numero = "41")
	private String azioniPossibili;

	@NumeroColonna(numero = "230")
	private String uriFilePrimario;
	
	// Attributi custom
	@NumeroColonna(numero = "101")
	private String idRegion;

	@NumeroColonna(numero = "102")
	private String idCountry;

	@NumeroColonna(numero = "103")
	private String isoLanguage;
	
	@NumeroColonna(numero = "104")
	private String desRegion;
	
	@NumeroColonna(numero = "105")
	private String desCountry;
	
	@NumeroColonna(numero = "106")
	private String idCustomer;
	
	@NumeroColonna(numero = "107")
	private String desCustomer;

	@NumeroColonna(numero = "108")
	private String telCustomer;

	@NumeroColonna(numero = "109")
	private String emailCustomer;

	@NumeroColonna(numero = "110")
	private String idTemplate;

	@NumeroColonna(numero = "111")
	private String template;

	@NumeroColonna(numero = "112")
	private String verTemplate;

	@NumeroColonna(numero = "113")
	@TipoData(tipo=Tipo.DATA)
	private Date dtFirma;

	@NumeroColonna(numero = "114")
	@TipoData(tipo=Tipo.DATA)
	private Date dtVisFirma;

	@NumeroColonna(numero = "115")
	private String idDocVers;

	@NumeroColonna(numero = "116")
	private String idTpDocVers;

	@NumeroColonna(numero = "117")
	private String desTpDocVers;
    
    private String abilViewFilePrimario;

	public String getFlgUdFolder() {
		return flgUdFolder;
	}

	public void setFlgUdFolder(String flgUdFolder) {
		this.flgUdFolder = flgUdFolder;
	}

	public BigDecimal getIdUd() {
		return idUd;
	}

	public void setIdUd(BigDecimal idUd) {
		this.idUd = idUd;
	}

	public Date getDtArrivo() {
		return dtArrivo;
	}

	public void setDtArrivo(Date dtArrivo) {
		this.dtArrivo = dtArrivo;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getNomeFilePrimario() {
		return NomeFilePrimario;
	}

	public void setNomeFilePrimario(String nomeFilePrimario) {
		NomeFilePrimario = nomeFilePrimario;
	}

	public String getIdDoc() {
		return idDoc;
	}

	public void setIdDoc(String idDoc) {
		this.idDoc = idDoc;
	}

	public String getAzioniPossibili() {
		return azioniPossibili;
	}

    public void setAzioniPossibili(String azioniPossibili) {
		
		if (azioniPossibili == null) {
			azioniPossibili = "";
		}
		
		this.azioniPossibili = azioniPossibili;
		
        if(!azioniPossibili.equalsIgnoreCase("")) {
        	
        	if(azioniPossibili.charAt(18) == '1')                                                                                 //        -- flag 19: Visualizzazione file primario
        		setAbilViewFilePrimario("1");
			else
				setAbilViewFilePrimario("0");			
		} 
	}

	public String getUriFilePrimario() {
		return uriFilePrimario;
	}

	public void setUriFilePrimario(String uriFilePrimario) {
		this.uriFilePrimario = uriFilePrimario;
	}

	public String getIdRegion() {
		return idRegion;
	}

	public void setIdRegion(String idRegion) {
		this.idRegion = idRegion;
	}

	public String getIdCountry() {
		return idCountry;
	}

	public void setIdCountry(String idCountry) {
		this.idCountry = idCountry;
	}

	

	public String getDesRegion() {
		return desRegion;
	}

	public void setDesRegion(String desRegion) {
		this.desRegion = desRegion;
	}

	public String getDesCountry() {
		return desCountry;
	}

	public void setDesCountry(String desCountry) {
		this.desCountry = desCountry;
	}

	public String getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(String idCustomer) {
		this.idCustomer = idCustomer;
	}

	public String getDesCustomer() {
		return desCustomer;
	}

	public void setDesCustomer(String desCustomer) {
		this.desCustomer = desCustomer;
	}

	public String getTelCustomer() {
		return telCustomer;
	}

	public void setTelCustomer(String telCustomer) {
		this.telCustomer = telCustomer;
	}

	public String getEmailCustomer() {
		return emailCustomer;
	}

	public void setEmailCustomer(String emailCustomer) {
		this.emailCustomer = emailCustomer;
	}

	public String getIdTemplate() {
		return idTemplate;
	}

	public void setIdTemplate(String idTemplate) {
		this.idTemplate = idTemplate;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getVerTemplate() {
		return verTemplate;
	}

	public void setVerTemplate(String verTemplate) {
		this.verTemplate = verTemplate;
	}

	public Date getDtFirma() {
		return dtFirma;
	}

	public void setDtFirma(Date dtFirma) {
		this.dtFirma = dtFirma;
	}

	public Date getDtVisFirma() {
		return dtVisFirma;
	}

	public void setDtVisFirma(Date dtVisFirma) {
		this.dtVisFirma = dtVisFirma;
	}

	public String getIdDocVers() {
		return idDocVers;
	}

	public void setIdDocVers(String idDocVers) {
		this.idDocVers = idDocVers;
	}

	public String getIdTpDocVers() {
		return idTpDocVers;
	}

	public void setIdTpDocVers(String idTpDocVers) {
		this.idTpDocVers = idTpDocVers;
	}

	public String getDesTpDocVers() {
		return desTpDocVers;
	}

	public void setDesTpDocVers(String desTpDocVers) {
		this.desTpDocVers = desTpDocVers;
	}

	public String getAbilViewFilePrimario() {
		return abilViewFilePrimario;
	}

	public void setAbilViewFilePrimario(String abilViewFilePrimario) {
		this.abilViewFilePrimario = abilViewFilePrimario;
	}

	public String getIsoLanguage() {
		return isoLanguage;
	}

	public void setIsoLanguage(String isoLanguage) {
		this.isoLanguage = isoLanguage;
	}
	
	
	

	

	
}