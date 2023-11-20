/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Calendar;
import java.util.Map;

import javax.xml.ws.BindingProvider;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.csi.siac.documenti.svc._1.ElaboraAttiAmministrativi;
import it.csi.siac.documenti.svc._1.ElaboraAttiAmministrativiResponse;
import it.csi.siac.stilo.svc._1_0.StiloService;
import it.eng.utility.client.contabilia.CustomPropertyPlaceholderConfigurer;
import it.eng.utility.client.contabilia.documenti.data.ContenutoDocumentoParam;
import it.eng.utility.client.contabilia.documenti.data.ElaboraAttiAmministrativiRequest;
import it.eng.utility.client.contabilia.documenti.data.OutputElaboraAttiAmministrativi;

public class ClientContabiliaDocumenti {
	
	public static final String BEAN_ID = "clientContabiliaDocumenti";
	private static final Logger logger = Logger.getLogger(ClientContabiliaDocumenti.class);
	private final String SEPARATORE = " ";
	private final String SEPARATORE_BLOCCO = "|";
	private StiloService proxy;
	private CustomPropertyPlaceholderConfigurer property;
	private String contabiliaFruitore;
	private String contabiliaEnte;
	
	public OutputElaboraAttiAmministrativi invioProposta(ElaboraAttiAmministrativiRequest input) {
		OutputElaboraAttiAmministrativi result = new OutputElaboraAttiAmministrativi();
		
		// rielaborazione parametri input per composizione stringa contenutoDocumento
		input = rielaborazioneInput(input);
		
		// rielaborazione default input ente
		String ente = rielaborazioneEnte(input.getIdSpAoo());
		
		// i campi chiavi non devono essere valorizzati per tipo variazione = I
		String annoAttoChiave = StringUtils.rightPad("", 4, " ");
		String numeroAttoChiave = StringUtils.rightPad("", 5, " ");
		String tipoAttoChiave = StringUtils.rightPad("", 4, " ");
		String direzoneChiave = StringUtils.rightPad("", 10, " ");
		String centroCostoChiave = StringUtils.rightPad("", 10, " ");
		
		// composizione bean per generare stringa contenutoDocumento
		ContenutoDocumentoParam param = new ContenutoDocumentoParam();
		param.setTipoVariazione("I");
		param.setEnte(ente);
		param.setAnnoAttoChiave(annoAttoChiave);
		param.setNumeroAttoChiave(numeroAttoChiave);
		param.setTipoAttoChiave(tipoAttoChiave);
		param.setDirezioneChiave(direzoneChiave);
		param.setCentroCostoChiave(centroCostoChiave);
		param.setAnnoAtto(Integer.toString(input.getAnnoAttoProposta()));
		param.setNumeroAtto(input.getNumeroAttoProposta());
		param.setTipoAtto(input.getTipoAttoProposta());
		param.setDirezione(input.getCentroResponsabilita());
		param.setCentroCosto(input.getCentroCosto());
		param.setDataCreazione(input.getDataCreazione());
		param.setDataProposta(input.getDataProposta());
		param.setDataApprovazione(input.getDataApprovazione());
		param.setDataEsecutivita(input.getDataEsecutivita());
		param.setStatoOperativo("P");
		param.setOggetto(input.getOggetto());
		param.setNote(input.getNote());
		param.setIdentificativo(input.getIdentificativo());
		param.setDirigenteResponsabile(input.getDirigenteResponsabile());
		param.setTrasparenza(input.getTrasparenza());
		param.setCodiceBlocco("0");
		
		// generazione stringa da passare come parametro al WS elaboraAttiAmministrativi
		String contenutoDocumento = generazioneContenutoDocumento(param);
		
		// chiamata elaboraAttiAmministrativi - insert per invioProposta
		it.eng.utility.client.contabilia.documenti.data.ElaboraAttiAmministrativiResponse response = elaboraAttiAmministrativi(contenutoDocumento, input.getIdSpAoo(), "invioProposta");
		
		result.setEntry(response);
		return result;
	}
	
	public OutputElaboraAttiAmministrativi aggiornaProposta(ElaboraAttiAmministrativiRequest input) {
		OutputElaboraAttiAmministrativi result = new OutputElaboraAttiAmministrativi();
		
		// rielaborazione parametri input per composizione stringa contenutoDocumento
		input = rielaborazioneInput(input);
		
		// rielaborazione default input (ente, direzone, centro di costo)
		String ente = rielaborazioneEnte(input.getIdSpAoo());
		
		// composizione bean per generare stringa contenutoDocumento
		ContenutoDocumentoParam param = new ContenutoDocumentoParam();
		param.setTipoVariazione("U");
		param.setEnte(ente);
		param.setAnnoAttoChiave(Integer.toString(input.getAnnoAttoProposta()));
		param.setNumeroAttoChiave(input.getNumeroAttoProposta());
		param.setTipoAttoChiave(input.getTipoAttoProposta());
		param.setDirezioneChiave(input.getCentroResponsabilita());
		param.setCentroCostoChiave(input.getCentroCosto());
		param.setAnnoAtto(Integer.toString(input.getAnnoAttoProposta()));
		param.setNumeroAtto(input.getNumeroAttoProposta());
		param.setTipoAtto(input.getTipoAttoProposta());
		param.setDirezione(input.getCentroResponsabilita());
		param.setCentroCosto(input.getCentroCosto());
		param.setDataCreazione(input.getDataCreazione());
		param.setDataProposta(input.getDataProposta());
		param.setDataApprovazione(input.getDataApprovazione());
		param.setDataEsecutivita(input.getDataEsecutivita());
		param.setStatoOperativo("P");
		param.setOggetto(input.getOggetto());
		param.setNote(input.getNote());
		param.setIdentificativo(input.getIdentificativo());
		param.setDirigenteResponsabile(input.getDirigenteResponsabile());
		param.setTrasparenza(input.getTrasparenza());
		param.setCodiceBlocco("0");
		
		// generazione stringa da passare come parametro al WS elaboraAttiAmministrativi
		String contenutoDocumento = generazioneContenutoDocumento(param);
		
		// chiamata elaboraAttiAmministrativi - update per aggiornaProposta
		it.eng.utility.client.contabilia.documenti.data.ElaboraAttiAmministrativiResponse response = elaboraAttiAmministrativi(contenutoDocumento, input.getIdSpAoo(), "aggiornaProposta");
		
		result.setEntry(response);
		return result;
	}
	
	public OutputElaboraAttiAmministrativi bloccoDatiProposta(ElaboraAttiAmministrativiRequest input) {
		OutputElaboraAttiAmministrativi result = new OutputElaboraAttiAmministrativi();
		
		// rielaborazione parametri input per composizione stringa contenutoDocumento
		input = rielaborazioneInput(input);
		
		// rielaborazione default input (ente, direzone, centro di costo)
		String ente = rielaborazioneEnte(input.getIdSpAoo());
		
		// composizione bean per generare stringa contenutoDocumento
		ContenutoDocumentoParam param = new ContenutoDocumentoParam();
		param.setTipoVariazione("U");
		param.setEnte(ente);
		param.setAnnoAttoChiave(Integer.toString(input.getAnnoAttoProposta()));
		param.setNumeroAttoChiave(input.getNumeroAttoProposta());
		param.setTipoAttoChiave(input.getTipoAttoProposta());
		param.setDirezioneChiave(input.getCentroResponsabilita());
		param.setCentroCostoChiave(input.getCentroCosto());
		param.setAnnoAtto(Integer.toString(input.getAnnoAttoProposta()));
		param.setNumeroAtto(input.getNumeroAttoProposta());
		param.setTipoAtto(input.getTipoAttoProposta());
		param.setDirezione(input.getCentroResponsabilita());
		param.setCentroCosto(input.getCentroCosto());
		param.setDataCreazione(input.getDataCreazione());
		param.setDataProposta(input.getDataProposta());
		param.setDataApprovazione(input.getDataApprovazione());
		param.setDataEsecutivita(input.getDataEsecutivita());
		param.setStatoOperativo("P");
		param.setOggetto(input.getOggetto());
		param.setNote(input.getNote());
		param.setIdentificativo(input.getIdentificativo());
		param.setDirigenteResponsabile(input.getDirigenteResponsabile());
		param.setTrasparenza(input.getTrasparenza());
		param.setCodiceBlocco("1");
		
		// generazione stringa da passare come parametro al WS elaboraAttiAmministrativi
		String contenutoDocumento = generazioneContenutoDocumento(param);
		
		// chiamata elaboraAttiAmministrativi - update per bloccoDatiProposta
		it.eng.utility.client.contabilia.documenti.data.ElaboraAttiAmministrativiResponse response = elaboraAttiAmministrativi(contenutoDocumento, input.getIdSpAoo(), "bloccoDatiProposta");
		
		result.setEntry(response);
		return result;
	}
	
	public OutputElaboraAttiAmministrativi invioAttoDef(ElaboraAttiAmministrativiRequest input) {
		OutputElaboraAttiAmministrativi result = new OutputElaboraAttiAmministrativi();
		
		// rielaborazione parametri input per composizione stringa contenutoDocumento
		input = rielaborazioneInput(input);
			
		// rielaborazione default input (ente, direzone, centro di costo)
		String ente = rielaborazioneEnte(input.getIdSpAoo());
		
		// composizione bean per generare stringa contenutoDocumento
		ContenutoDocumentoParam param = new ContenutoDocumentoParam();
		param.setTipoVariazione("U");
		param.setEnte(ente);
		param.setAnnoAttoChiave(Integer.toString(input.getAnnoAttoProposta()));
		param.setNumeroAttoChiave(input.getNumeroAttoProposta());
		param.setTipoAttoChiave(input.getTipoAttoProposta());
		param.setDirezioneChiave(input.getCentroResponsabilita());
		param.setCentroCostoChiave(input.getCentroCosto());
		param.setAnnoAtto(Integer.toString(input.getAnnoAttoDefinitivo()));
		param.setNumeroAtto(input.getNumeroAttoDefinitivo());
		param.setTipoAtto(input.getTipoAttoDefinitivo());
		param.setDirezione(input.getCentroResponsabilita());
		param.setCentroCosto(input.getCentroCosto());
		param.setDataCreazione(input.getDataCreazione());
		param.setDataProposta(input.getDataProposta());
		param.setDataApprovazione(input.getDataApprovazione());
		param.setDataEsecutivita(input.getDataEsecutivita());
		param.setStatoOperativo("D");
		param.setOggetto(input.getOggetto());
		param.setNote(input.getNote());
		param.setIdentificativo(input.getIdentificativo());
		param.setDirigenteResponsabile(input.getDirigenteResponsabile());
		param.setTrasparenza(input.getTrasparenza());
		param.setCodiceBlocco("1");
		
		// generazione stringa da passare come parametro al WS elaboraAttiAmministrativi
		String contenutoDocumento = generazioneContenutoDocumento(param);
		
		// chiamata elaboraAttiAmministrativi - update per invioAttoDef
		it.eng.utility.client.contabilia.documenti.data.ElaboraAttiAmministrativiResponse response = elaboraAttiAmministrativi(contenutoDocumento, input.getIdSpAoo(), "invioAttoDef");
		
		result.setEntry(response);
		return result;
	}
	
	public OutputElaboraAttiAmministrativi invioAttoLquidazioneDef(ElaboraAttiAmministrativiRequest input) {
		OutputElaboraAttiAmministrativi result = new OutputElaboraAttiAmministrativi();
		
		// rielaborazione parametri input per composizione stringa contenutoDocumento
		input = rielaborazioneInput(input);
			
		// rielaborazione default input (ente, direzone, centro di costo)
		String ente = rielaborazioneEnte(input.getIdSpAoo());
		
		// composizione bean per generare stringa contenutoDocumento
		ContenutoDocumentoParam param = new ContenutoDocumentoParam();
		param.setTipoVariazione("U");
		param.setEnte(ente);
		param.setAnnoAttoChiave(Integer.toString(input.getAnnoAttoProposta()));
		param.setNumeroAttoChiave(input.getNumeroAttoProposta());
		param.setTipoAttoChiave(input.getTipoAttoProposta());
		param.setDirezioneChiave(input.getCentroResponsabilita());
		param.setCentroCostoChiave(input.getCentroCosto());
		param.setAnnoAtto(Integer.toString(input.getAnnoAttoDefinitivo()));
		param.setNumeroAtto(input.getNumeroAttoDefinitivo());
		param.setTipoAtto(input.getTipoAttoDefinitivo());
		param.setDirezione(input.getCentroResponsabilita());
		param.setCentroCosto(input.getCentroCosto());
		param.setDataCreazione(input.getDataCreazione());
		param.setDataProposta(input.getDataProposta());
		param.setDataApprovazione(input.getDataApprovazione());
		param.setDataEsecutivita(input.getDataEsecutivita());
		param.setStatoOperativo("D");
		param.setOggetto(input.getOggetto());
		param.setNote(input.getNote());
		param.setIdentificativo(input.getIdentificativo());
		param.setDirigenteResponsabile(input.getDirigenteResponsabile());
		param.setTrasparenza(input.getTrasparenza());
		param.setCodiceBlocco("0");
		
		// generazione stringa da passare come parametro al WS elaboraAttiAmministrativi
		String contenutoDocumento = generazioneContenutoDocumento(param);
		
		// chiamata elaboraAttiAmministrativi - update per invioAttoDef
		it.eng.utility.client.contabilia.documenti.data.ElaboraAttiAmministrativiResponse response = elaboraAttiAmministrativi(contenutoDocumento, input.getIdSpAoo(), "invioAttoLquidazioneDef");
		
		result.setEntry(response);
		return result;
	}
	
	public OutputElaboraAttiAmministrativi invioAttoDefEsec(ElaboraAttiAmministrativiRequest input) {
		OutputElaboraAttiAmministrativi result = new OutputElaboraAttiAmministrativi();
		
		// rielaborazione parametri input per composizione stringa contenutoDocumento
		input = rielaborazioneInput(input);
		
		// rielaborazione default input (ente, direzone, centro di costo)
		String ente = rielaborazioneEnte(input.getIdSpAoo());
		
		// composizione bean per generare stringa contenutoDocumento
		ContenutoDocumentoParam param = new ContenutoDocumentoParam();
		param.setTipoVariazione("U");
		param.setEnte(ente);
		param.setAnnoAttoChiave(Integer.toString(input.getAnnoAttoProposta()));
		param.setNumeroAttoChiave(input.getNumeroAttoProposta());
		param.setTipoAttoChiave(input.getTipoAttoProposta());
		param.setDirezioneChiave(input.getCentroResponsabilita());
		param.setCentroCostoChiave(input.getCentroCosto());
		param.setAnnoAtto(Integer.toString(input.getAnnoAttoDefinitivo()));
		param.setNumeroAtto(input.getNumeroAttoDefinitivo());
		param.setTipoAtto(input.getTipoAttoDefinitivo());
		param.setDirezione(input.getCentroResponsabilita());
		param.setCentroCosto(input.getCentroCosto());
		param.setDataCreazione(input.getDataCreazione());
		param.setDataProposta(input.getDataProposta());
		param.setDataApprovazione(input.getDataApprovazione());
		param.setDataEsecutivita(input.getDataEsecutivita());
		param.setStatoOperativo("E");
		param.setOggetto(input.getOggetto());
		param.setNote(input.getNote());
		param.setIdentificativo(input.getIdentificativo());
		param.setDirigenteResponsabile(input.getDirigenteResponsabile());
		param.setTrasparenza(input.getTrasparenza());
		param.setCodiceBlocco("1");
		
		// generazione stringa da passare come parametro al WS elaboraAttiAmministrativi
		String contenutoDocumento = generazioneContenutoDocumento(param);
		
		// chiamata elaboraAttiAmministrativi - update per invioAttoDefEsec
		it.eng.utility.client.contabilia.documenti.data.ElaboraAttiAmministrativiResponse response = elaboraAttiAmministrativi(contenutoDocumento, input.getIdSpAoo(), "invioAttoDefEsec");
		
		result.setEntry(response);
		return result;
	}
	
	public OutputElaboraAttiAmministrativi invioAttoEsec(ElaboraAttiAmministrativiRequest input) {
		OutputElaboraAttiAmministrativi result = new OutputElaboraAttiAmministrativi();
		
		// rielaborazione parametri input per composizione stringa contenutoDocumento
		input = rielaborazioneInput(input);
		
		// rielaborazione default input (ente, direzone, centro di costo)
		String ente = rielaborazioneEnte(input.getIdSpAoo());
		
		// composizione bean per generare stringa contenutoDocumento
		ContenutoDocumentoParam param = new ContenutoDocumentoParam();
		param.setTipoVariazione("U");
		param.setEnte(ente);
		param.setAnnoAttoChiave(Integer.toString(input.getAnnoAttoDefinitivo()));
		param.setNumeroAttoChiave(input.getNumeroAttoDefinitivo());
		param.setTipoAttoChiave(input.getTipoAttoDefinitivo());
		param.setDirezioneChiave(input.getCentroResponsabilita());
		param.setCentroCostoChiave(input.getCentroCosto());
		param.setAnnoAtto(Integer.toString(input.getAnnoAttoDefinitivo()));
		param.setNumeroAtto(input.getNumeroAttoDefinitivo());
		param.setTipoAtto(input.getTipoAttoDefinitivo());
		param.setDirezione(input.getCentroResponsabilita());
		param.setCentroCosto(input.getCentroCosto());
		param.setDataCreazione(input.getDataCreazione());
		param.setDataProposta(input.getDataProposta());
		param.setDataApprovazione(input.getDataApprovazione());
		param.setDataEsecutivita(input.getDataEsecutivita());
		param.setStatoOperativo("E");
		param.setOggetto(input.getOggetto());
		param.setNote(input.getNote());
		param.setIdentificativo(input.getIdentificativo());
		param.setDirigenteResponsabile(input.getDirigenteResponsabile());
		param.setTrasparenza(input.getTrasparenza());
		param.setCodiceBlocco("1");
		
		// generazione stringa da passare come parametro al WS elaboraAttiAmministrativi
		String contenutoDocumento = generazioneContenutoDocumento(param);
		
		// chiamata elaboraAttiAmministrativi - update per invioAttoEsec
		it.eng.utility.client.contabilia.documenti.data.ElaboraAttiAmministrativiResponse response = elaboraAttiAmministrativi(contenutoDocumento, input.getIdSpAoo(), "invioAttoEsec");
		
		result.setEntry(response);
		return result;
	}
	
	public OutputElaboraAttiAmministrativi sbloccoDatiProposta(ElaboraAttiAmministrativiRequest input) {
		OutputElaboraAttiAmministrativi result = new OutputElaboraAttiAmministrativi();
		
		// rielaborazione parametri input per composizione stringa contenutoDocumento
		input = rielaborazioneInput(input);
		
		// rielaborazione default input (ente, direzone, centro di costo)
		String ente = rielaborazioneEnte(input.getIdSpAoo());
		
		// composizione bean per generare stringa contenutoDocumento
		ContenutoDocumentoParam param = new ContenutoDocumentoParam();
		param.setTipoVariazione("U");
		param.setEnte(ente);
		param.setAnnoAttoChiave(Integer.toString(input.getAnnoAttoProposta()));
		param.setNumeroAttoChiave(input.getNumeroAttoProposta());
		param.setTipoAttoChiave(input.getTipoAttoProposta());
		param.setDirezioneChiave(input.getCentroResponsabilita());
		param.setCentroCostoChiave(input.getCentroCosto());
		param.setAnnoAtto(Integer.toString(input.getAnnoAttoProposta()));
		param.setNumeroAtto(input.getNumeroAttoProposta());
		param.setTipoAtto(input.getTipoAttoProposta());
		param.setDirezione(input.getCentroResponsabilita());
		param.setCentroCosto(input.getCentroCosto());
		param.setDataCreazione(input.getDataCreazione());
		param.setDataProposta(input.getDataProposta());
		param.setDataApprovazione(input.getDataApprovazione());
		param.setDataEsecutivita(input.getDataEsecutivita());
		param.setStatoOperativo("P");
		param.setOggetto(input.getOggetto());
		param.setNote(input.getNote());
		param.setIdentificativo(input.getIdentificativo());
		param.setDirigenteResponsabile(input.getDirigenteResponsabile());
		param.setTrasparenza(input.getTrasparenza());
		param.setCodiceBlocco("0");
		
		// generazione stringa da passare come parametro al WS elaboraAttiAmministrativi
		String contenutoDocumento = generazioneContenutoDocumento(param);
		
		// chiamata elaboraAttiAmministrativi - update per sbloccoDatiProposta
		it.eng.utility.client.contabilia.documenti.data.ElaboraAttiAmministrativiResponse response = elaboraAttiAmministrativi(contenutoDocumento, input.getIdSpAoo(), "sbloccoDatiProposta");
		
		result.setEntry(response);
		return result;
	}
	
	public OutputElaboraAttiAmministrativi annullamentoProposta(ElaboraAttiAmministrativiRequest input) {
		OutputElaboraAttiAmministrativi result = new OutputElaboraAttiAmministrativi();
		
		// rielaborazione parametri input per composizione stringa contenutoDocumento
		input = rielaborazioneInput(input);
		
		// rielaborazione default input (ente, direzone, centro di costo)
		String ente = rielaborazioneEnte(input.getIdSpAoo());
		
		// composizione bean per generare stringa contenutoDocumento
		ContenutoDocumentoParam param = new ContenutoDocumentoParam();
		param.setTipoVariazione("U");
		param.setEnte(ente);
		param.setAnnoAttoChiave(Integer.toString(input.getAnnoAttoProposta()));
		param.setNumeroAttoChiave(input.getNumeroAttoProposta());
		param.setTipoAttoChiave(input.getTipoAttoProposta());
		param.setDirezioneChiave(input.getCentroResponsabilita());
		param.setCentroCostoChiave(input.getCentroCosto());
		param.setAnnoAtto(Integer.toString(input.getAnnoAttoProposta()));
		param.setNumeroAtto(input.getNumeroAttoProposta());
		param.setTipoAtto(input.getTipoAttoProposta());
		param.setDirezione(input.getCentroResponsabilita());
		param.setCentroCosto(input.getCentroCosto());
		param.setDataCreazione(input.getDataCreazione());
		param.setDataProposta(input.getDataProposta());
		param.setDataApprovazione(input.getDataApprovazione());
		param.setDataEsecutivita(input.getDataEsecutivita());
		param.setStatoOperativo("A");
		param.setOggetto(input.getOggetto());
		param.setNote(input.getNote());
		param.setIdentificativo(input.getIdentificativo());
		param.setDirigenteResponsabile(input.getDirigenteResponsabile());
		param.setTrasparenza(input.getTrasparenza());
		param.setCodiceBlocco("0");
		
		// generazione stringa da passare come parametro al WS elaboraAttiAmministrativi
		String contenutoDocumento = generazioneContenutoDocumento(param);
		
		// chiamata elaboraAttiAmministrativi - update per annullamentoProposta
		it.eng.utility.client.contabilia.documenti.data.ElaboraAttiAmministrativiResponse response = elaboraAttiAmministrativi(contenutoDocumento, input.getIdSpAoo(), "annullamentoProposta");
		
		result.setEntry(response);
		return result;
	}
	
	public OutputElaboraAttiAmministrativi creaLiquidazione(ElaboraAttiAmministrativiRequest input) {
		// invio proposta
		input.setAnnoAttoProposta(input.getAnnoAttoDefinitivo());
		input.setNumeroAttoProposta(input.getNumeroAttoDefinitivo());
		input.setTipoAttoProposta(input.getTipoAttoDefinitivo());
		OutputElaboraAttiAmministrativi resultInvioProposta = invioProposta(input);
		
		OutputElaboraAttiAmministrativi resultDefinitivo = null;
		if (resultInvioProposta != null && resultInvioProposta.getEntry().isResponseElaborazione()) {
			logger.info("Creata proposta di liquidazione: " + input.getNumeroAttoProposta() + " - " + input.getAnnoAttoProposta() + " - " + input.getTipoAttoProposta());
			
			// invia attoDefinitivo con codice blocco = 0
			resultDefinitivo = invioAttoLquidazioneDef(input);
			
			if (resultDefinitivo != null && resultDefinitivo.getEntry().isResponseElaborazione()) {
				logger.info("Creato definitivo di liquidazione: " + input.getNumeroAttoDefinitivo() + " - " + input.getAnnoAttoDefinitivo() + " - " + input.getTipoAttoDefinitivo() + " - con CodiceBlocco = 0");
			}
		}
		
		return resultDefinitivo;
	}
	
	public StiloService getProxy() {
		return proxy;
	}
	
	public void setProxy(StiloService proxy) {
		this.proxy = proxy;
	}
	
	public CustomPropertyPlaceholderConfigurer getProperty() {
		return property;
	}
	
	public void setProperty(CustomPropertyPlaceholderConfigurer property) {
		this.property = property;
	}
	
	public String getContabiliaFruitore() {
		return contabiliaFruitore;
	}
	
	public void setContabiliaFruitore(String contabiliaFruitore) {
		this.contabiliaFruitore = contabiliaFruitore;
	}
	
	public String getContabiliaEnte() {
		return contabiliaEnte;
	}
	
	public void setContabiliaEnte(String contabiliaEnte) {
		this.contabiliaEnte = contabiliaEnte;
	}
	
	
	/*
	 * Rielaborazione parametri di input: valori a lunghezza fissa, se minori viene inserito spazio o 0
	 */
	private ElaboraAttiAmministrativiRequest rielaborazioneInput(ElaboraAttiAmministrativiRequest input) {
		//  verifica che valori siano null ma stringa vuota
		String numeroAttoProposta = input.getNumeroAttoProposta() == null ? "" : input.getNumeroAttoProposta();
		String tipoAttoProposta = input.getTipoAttoProposta() == null ? "" : input.getTipoAttoProposta();
		String numeroAttoDefinitivo = input.getNumeroAttoDefinitivo() == null ? "" : input.getNumeroAttoDefinitivo();
		String tipoAttoDefinitivo = input.getTipoAttoDefinitivo() == null ? "" : input.getTipoAttoDefinitivo();
		String centroResponsabilita = input.getCentroResponsabilita() == null ? "" : input.getCentroResponsabilita();
		String centroCosto = input.getCentroCosto() == null ? "" : input.getCentroCosto();
		String dataCreazione = input.getDataCreazione() == null ? "" : input.getDataCreazione();
		String dataProposta = input.getDataProposta() == null ? "" : input.getDataProposta();
		String dataApprovazione = input.getDataApprovazione() == null ? "" : input.getDataApprovazione();
		String dataEsecutivita = input.getDataEsecutivita() == null ? "" : input.getDataEsecutivita();
		String oggetto = input.getOggetto() == null ? "" : input.getOggetto();
		String note = input.getNote() == null ? "" : input.getNote();
		String identificativo = input.getIdentificativo() == null ? "" : input.getIdentificativo();
		String dirigente = input.getDirigenteResponsabile() == null ? "" : input.getDirigenteResponsabile();
		String trasparenza = input.getTrasparenza() == null ? "" : input.getTrasparenza();
		
		// per ogni valore imposto lunghezza fissa
		String numeroAttoPropostaNew = StringUtils.leftPad(numeroAttoProposta, 5, "0");
		
		// substring o rightpad
		String tipoAttoPropostaNew = null;
		if (tipoAttoProposta.length() > 4) {
			tipoAttoPropostaNew = tipoAttoProposta.substring(0, 4);
		}
		else {
			tipoAttoPropostaNew = StringUtils.rightPad(tipoAttoProposta, 4, " ");
		}
		
		String numeroAttoDefinitivoNew = StringUtils.leftPad(numeroAttoDefinitivo, 5, "0");
		
		String tipoAttoDefinitivoNew = null;
		if (tipoAttoDefinitivo.length() > 4) {
			tipoAttoDefinitivoNew = tipoAttoDefinitivo.substring(0, 4);
		}
		else {
			tipoAttoDefinitivoNew = StringUtils.rightPad(tipoAttoDefinitivo, 4, " ");
		}
		
		String centroResponsabilitaNew = null;
		if (centroResponsabilita.length() > 10) {
			centroResponsabilitaNew = centroResponsabilita.substring(0, 10);
		}
		else {
			centroResponsabilitaNew = StringUtils.rightPad(centroResponsabilita, 10, " ");
		}
		
		String centroCostoNew = null;
		if (centroCosto.length() > 10) {
			centroCostoNew = centroCosto.substring(0, 10);
		}
		else {
			centroCostoNew = StringUtils.rightPad(centroCosto, 10, " ");
		}
		
		String dataCreazioneNew = null;
		if (dataCreazione.length() > 14) {
			dataCreazioneNew = dataCreazione.substring(0, 14);
		}
		else {
			dataCreazioneNew = StringUtils.rightPad(dataCreazione, 14, "0");
		}
		
		String dataPropostaNew = null;
		if (dataProposta.length() > 8) {
			dataPropostaNew = dataProposta.substring(0, 8);
		}
		else {
			dataPropostaNew = StringUtils.rightPad(dataProposta, 8, " ");
		}
		
		String dataApprovazioneNew = null;
		if (dataApprovazione.length() > 14) {
			dataApprovazioneNew = dataApprovazione.substring(0, 14);
		}
		else {
			dataApprovazioneNew = StringUtils.rightPad(dataApprovazione, 14, "0");
		}
		
		String dataEsecutivitaNew = null;
		if (dataEsecutivita.length() > 8) {
			dataEsecutivitaNew = dataEsecutivita.substring(0, 8);
		}
		else {
			dataEsecutivitaNew = StringUtils.rightPad(dataEsecutivita, 8, " ");
		}
		
		String oggettoNew = null;
		if (oggetto.length() > 500) {
			oggettoNew = oggetto.substring(0, 500);
		}
		else {
			oggettoNew = StringUtils.rightPad(oggetto, 500, " ");
		}
		
		String noteNew = null;
		if (note.length() > 180) {
			noteNew = note.substring(0, 180);
		}
		else {
			noteNew = StringUtils.rightPad(note, 180, " ");
		}
		
		String identificativoNew = null;
		if (identificativo.length() > 8) {
			identificativoNew = identificativo.substring(0, 8);
		}
		else {
			identificativoNew = StringUtils.rightPad(identificativo, 8, " ");
		}
		
		String dirigenteNew = null;
		if (dirigente.length() > 100) {
			dirigenteNew = dirigente.substring(0, 100);
		}
		else {
			dirigenteNew = StringUtils.rightPad(dirigente, 100, " ");
		}
		
		String trasparenzaNew = null;
		if (trasparenza.length() > 180) {
			trasparenzaNew = trasparenza.substring(0, 180);
		}
		else {
			trasparenzaNew = StringUtils.rightPad(trasparenza, 180, " ");
		}
		
		input.setNumeroAttoProposta(numeroAttoPropostaNew);
		input.setTipoAttoProposta(tipoAttoPropostaNew);
		input.setNumeroAttoDefinitivo(numeroAttoDefinitivoNew);
		input.setTipoAttoDefinitivo(tipoAttoDefinitivoNew);
		input.setCentroResponsabilita(centroResponsabilitaNew);
		input.setCentroCosto(centroCostoNew);
		input.setDataCreazione(dataCreazioneNew);
		input.setDataProposta(dataPropostaNew);
		input.setDataApprovazione(dataApprovazioneNew);
		input.setDataEsecutivita(dataEsecutivitaNew);
		input.setOggetto(oggettoNew);
		input.setNote(noteNew);
		input.setIdentificativo(identificativoNew);
		input.setDirigenteResponsabile(dirigenteNew);
		input.setTrasparenza(trasparenzaNew);
		
		return input;
	}
	
	/*
	 * Rielaborazione ente: se minore di 6 inseriti spazi
	 */
	private String rielaborazioneEnte(String idSpAoo) {
		// acquisizione ente da file di configurazione
		String ente = getEnte(idSpAoo);
		
		String result = StringUtils.rightPad(ente, 6, " ");
		
		return result;
	}
	
	/*
	 * Generazione stringa contenutoDocumento
	 */
	private String generazioneContenutoDocumento(ContenutoDocumentoParam param) {
		// composizione stringa contenutoDocumento da passare come parametro al WS
		String contenutoDocumento = param.getTipoVariazione() + SEPARATORE + 
									param.getEnte() + SEPARATORE + 
									param.getAnnoAttoChiave() + SEPARATORE + 
									param.getNumeroAttoChiave() + SEPARATORE + 
									param.getTipoAttoChiave() + SEPARATORE + 
									param.getDirezioneChiave() + SEPARATORE +
									param.getCentroCostoChiave() + SEPARATORE +
									param.getAnnoAtto() + SEPARATORE + 
									param.getNumeroAtto() + SEPARATORE + 
									param.getTipoAtto() + SEPARATORE +
									param.getDirezione() + SEPARATORE + 
									param.getCentroCosto() + SEPARATORE + 
									param.getDataCreazione() + SEPARATORE +
									param.getDataProposta() + SEPARATORE + 
									param.getDataApprovazione() + SEPARATORE +
									param.getDataEsecutivita() + SEPARATORE + 
									param.getStatoOperativo() + SEPARATORE + 
									param.getOggetto() + SEPARATORE +
									param.getNote() + SEPARATORE + 
									param.getIdentificativo() + SEPARATORE + 
									param.getDirigenteResponsabile() + SEPARATORE + 
									param.getTrasparenza() + SEPARATORE_BLOCCO + 
									param.getCodiceBlocco();
		
		return contenutoDocumento;
	}
	
	/*
	 * Metodo chiamata WS al servizio elaboraAttiAmministrativi
	 */
	private it.eng.utility.client.contabilia.documenti.data.ElaboraAttiAmministrativiResponse elaboraAttiAmministrativi(String contenutoDocumento, String idSpAoo, String metodo) {
		it.eng.utility.client.contabilia.documenti.data.ElaboraAttiAmministrativiResponse response = new it.eng.utility.client.contabilia.documenti.data.ElaboraAttiAmministrativiResponse();
		
		try {
			String ente = getEnte(idSpAoo);
			String fruitore = getFruitore(idSpAoo);
			String endpointWs = getEndpoint(idSpAoo);
			
			ElaboraAttiAmministrativi param = new ElaboraAttiAmministrativi();
			param.setAnnoBilancio(Calendar.getInstance().get(Calendar.YEAR));
			param.setCodiceEnte(ente);
			param.setCodiceFruitore(fruitore);
			param.setContenutoDocumento(contenutoDocumento);
			
			logger.info("Chiamata WS " + metodo + " parametri - annoBilancio: " + param.getAnnoBilancio() + " - codiceEnte: " + param.getCodiceEnte() + " - codiceFruitore: " + param.getCodiceFruitore() + " - contenutoDocumento: " + param.getContenutoDocumento());
			
			// valorizzazione endpoint da file di configurazione
			setEndpointProxy(endpointWs);
			
			logger.info("endpointWs "+endpointWs);
			logger.info("proxy: "+proxy);
			logger.info("param: "+param);
			// chiamata al servizio elaboraAttiAmministrativi
			ElaboraAttiAmministrativiResponse responseWs = proxy.elaboraAttiAmministrativi(param);
			
			logger.info("responseWs "+responseWs.toString());
			
			if (responseWs != null && responseWs.getMessaggi().size() > 0) {
				response.setResponseElaborazione(true);
				
				response.setCodiceElaborazione(responseWs.getMessaggi().get(0).getCodice());
				response.setMessaggioElaborazione(responseWs.getMessaggi().get(0).getDescrizione());
			} else {
				response.setResponseElaborazione(false);
				if (responseWs.getErrori().size() > 0) {
					response.setCodiceElaborazione(responseWs.getErrori().get(0).getCodice());
					response.setMessaggioElaborazione(responseWs.getErrori().get(0).getDescrizione());
				}
			}
			response.setEsitoElaborazione(responseWs.getEsito().value());
			
			logger.info("Response chiamata WS " + metodo + 
						" - esito: " + response.isResponseElaborazione() + 
						" - " + response.getEsitoElaborazione() + 
						" - codice: " + response.getCodiceElaborazione() + 
						" - messaggio: " + response.getMessaggioElaborazione());
			
		} catch (Exception e) {
			response.setResponseElaborazione(false);
			response.setMessaggioElaborazione(e.getMessage());
			
			logger.error("Errore chiamnata WS " + metodo + " - " + e.getMessage());
		}
		
		return response;
	}
	
	/*
	 * Metodo che mappa tutte le properties del file di configurazione
	 */
	private Map<String, String> getProperties() {
		Map<String, String> properties = property.getResolvedProps();
		
		return properties;
	}
	
	/*
	 * Acquisizione ente da file properties
	 */
	private String getEnte(String idSpAoo) {
		String result = contabiliaEnte;
		
		if (idSpAoo != null) {
			// acquisizione property da file di configurazione
			Map<String, String> properties = getProperties();
			
			if (properties != null) {
				if (properties.containsKey("contabilia.ente." + idSpAoo)) {
					result = properties.get("contabilia.ente." + idSpAoo);
				}
			}
		}
		
		return result;
	}
	
	/*
	 * Acquisizione fruitore da file properties
	 */
	private String getFruitore(String idSpAoo) {
		String result = contabiliaFruitore;
		
		if (idSpAoo != null) {
			// acquisizione property da file di configurazione
			Map<String, String> properties = getProperties();
			
			if (properties != null) {
				if (properties.containsKey("contabilia.fruitore." + idSpAoo)) {
					result = properties.get("contabilia.fruitore." + idSpAoo);
				}
			}
		}
		
		return result;
	}
	
	/*
	 * Acquisizione endpoint da file properties
	 */
	private String getEndpoint(String idSpAoo) {
		String result = null;
		
		if (idSpAoo != null) {
			// acquisizione property da file di configurazione
			Map<String, String> properties = getProperties();
			
			if (properties != null) {
				result = properties.get("contabilia.stilo.endpoint");
				
				if (properties.containsKey("contabilia.stilo.endpoint." + idSpAoo)) {
					result = properties.get("contabilia.stilo.endpoint." + idSpAoo);
				}
			}
		}
		
		return result;
	}
	
	/*
	 * Metodo per settare endpoint specializzato all'oggetto ProxyClient
	 * se null endpoint di default impostato in file xml di configurazione
	 */
	private void setEndpointProxy(String endpointWs) {
		if (endpointWs != null) {
			logger.info("Endpoint specializzato per servizio elaboraAttiAmministrativi: " + endpointWs);
			
			BindingProvider bp = (BindingProvider)proxy;
			bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointWs);
		}
	}
	
	/*
	private DocumentiService getDocumentiService(String endpointWs) throws MalformedURLException  {
		URL url = new URL(endpointWs);
		QName qname = new QName("http://siac.csi.it/documenti/svc/1.0", "DocumentiService");
		Service service = Service.create(url, qname);
		DocumentiService result = service.getPort(DocumentiService.class);
		
		return result;
	}
	*/
	
}
