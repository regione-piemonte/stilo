/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_cogito.bean.DmpkCogitoIucogitologBean;
import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ClassificaBean;
import it.eng.auriga.ui.module.layout.server.titolarioCogito.datasource.bean.TitolarioCogitoBean;
import it.eng.auriga.ui.module.layout.server.titolarioCogito.datasource.bean.TracciaturaTitolarioCogitoBean;
import it.eng.client.DmpkCogitoIucogitolog;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.client.RecuperoFile;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.cogito.CogitoUtility;
import it.eng.utility.cogito.exception.AurigaCogitoException;
import it.eng.utility.cogito.response.CategorizzazioneCogitoBean;
import it.eng.utility.cogito.response.CogitoResponseBean;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.utility.ui.user.UserUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id = "TitolarioCogitoDatasource")
public class TitolarioCogitoDatasource extends AbstractFetchDataSource<TitolarioCogitoBean> {

	private BigDecimal idCogitoLogIO = null;
	private static final Logger logger = Logger.getLogger(TitolarioCogitoDatasource.class);
	
	@Override
	public PaginatorBean<TitolarioCogitoBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		// uo protocollante
		String uoProtocollante  = StringUtils.isNotBlank(getExtraparams().get("uoProtocollante")) ? getExtraparams().get("uoProtocollante") : "";
		String idUo = (StringUtils.isNotBlank(uoProtocollante) ? uoProtocollante.substring(2) : null);
		
		List<File> streamFileProtocollo = new ArrayList<File>();		
		List<TitolarioCogitoBean> data = new ArrayList<TitolarioCogitoBean>();
		PaginatorBean<TitolarioCogitoBean> lPaginatorBean = null;

		try {
			// Leggo gli stream dei file 
			streamFileProtocollo = getStreamFileProtocollo();
			
			// Leggo le titolazioni di COGITO
			data = getTitolazioniCogito(streamFileProtocollo, idUo);
			lPaginatorBean = new PaginatorBean<TitolarioCogitoBean>();
			// Restituisco le titolazioni 		
			lPaginatorBean.setData(data);
			lPaginatorBean.setStartRow(startRow);
			lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
			lPaginatorBean.setTotalRows(data.size());
		} catch (Exception e) {
			logger.error(e);
			addMessage("Impossibile ottenere il suggerimento di classificazione da COGITO con i dati presenti", "", MessageType.WARNING);
//			throw new AurigaCogitoException("Impossibile ottenere il suggerimento di classificazione da COGITO con i dati presenti");
		}
		return lPaginatorBean;
	}

	
	private List<File> getStreamFileProtocollo()  throws Exception {

		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		List<File> result = new ArrayList<File>();
		
		// Leggo l'uri del file primario
		String uriFilePrimario  = StringUtils.isNotBlank(getExtraparams().get("uriFilePrimario")) ? getExtraparams().get("uriFilePrimario") : "";
		
		// Leggo il flag remoteUriFilePrimario
		String remoteUriFilePrimarioString = StringUtils.isNotBlank(getExtraparams().get("remoteUriFilePrimario")) ? getExtraparams().get("remoteUriFilePrimario") : "false";
		
		Boolean remoteUriFilePrimario = (StringUtils.isNotBlank(remoteUriFilePrimarioString) && remoteUriFilePrimarioString.equalsIgnoreCase("true") ? true : false); 
		
		
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		// Leggo lo stream del file primario
		if (StringUtils.isNotBlank(uriFilePrimario)){						
			File lFilePrimario = null;					
			if (remoteUriFilePrimario != null && remoteUriFilePrimario) {
				RecuperoFile lRecuperoFile = new RecuperoFile();
				FileExtractedIn lFileExtractedIn = new FileExtractedIn();
				lFileExtractedIn.setUri(uriFilePrimario);
				FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(), lAurigaLoginBean, lFileExtractedIn);
				lFilePrimario = out.getExtracted();
			} else {
				// File locale
				lFilePrimario = StorageImplementation.getStorage().extractFile(uriFilePrimario);
			}		
			
			File lFilePrimarioSbustato = File.createTempFile("unsign", "");	
			InputStream lInputStream = null;
			lInputStream = lInfoFileUtility.sbusta(lFilePrimario, "");
			try(OutputStream outputStream = new FileOutputStream(lFilePrimarioSbustato)){
			    IOUtils.copy(lInputStream, outputStream);
			} catch (FileNotFoundException e) {
				throw new Exception("Errore nella getStreamFileProtocollo() = " + e.getMessage());
			} catch (IOException e) {
				throw new Exception("Errore nella getStreamFileProtocollo() = " + e.getMessage());
			}
			
			result.add(lFilePrimarioSbustato);
		}

		// Leggo le info dei file allegati (separati da |*|)
		String listaAllegati  = StringUtils.isNotBlank(getExtraparams().get("listaAllegati")) ? getExtraparams().get("listaAllegati") : "";
		String[] listaInfoAllegati = null;
		if (StringUtils.isNotBlank(listaAllegati)){
			
			// Splitto uri|*|remote ; uri|*|remote...; 
			listaInfoAllegati = listaAllegati.split(";");
			if (listaInfoAllegati!=null && listaInfoAllegati.length>0){
				for (String infoAllegato : listaInfoAllegati) {
					// Splitto uri|*|remote	
					String[] infoAllegatoSplit  = infoAllegato.split("\\|\\*\\|");
					String uriFileAlleato              = infoAllegatoSplit[0];
					String remoteUriFileAllegatoString = infoAllegatoSplit[1];
					Boolean remoteUriFileAllegato = (StringUtils.isNotBlank(remoteUriFileAllegatoString) && remoteUriFileAllegatoString.equalsIgnoreCase("true") ? true : false); 
					// Leggo lo stream del file allegato
					if (StringUtils.isNotBlank(uriFileAlleato)){	
						File lFileAllegato = null;
						if (remoteUriFileAllegato != null && remoteUriFileAllegato) {
							RecuperoFile lRecuperoFile = new RecuperoFile();
							FileExtractedIn lFileExtractedIn = new FileExtractedIn();
							lFileExtractedIn.setUri(uriFileAlleato);
							FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(), lAurigaLoginBean, lFileExtractedIn);
							lFileAllegato = out.getExtracted();
						}
						else {
							// File locale
							lFileAllegato = StorageImplementation.getStorage().extractFile(uriFileAlleato);
						}	
						
						File lFileAllegatoSbustato = File.createTempFile("unsign", "");	
						InputStream lInputStream = null;
						lInputStream = lInfoFileUtility.sbusta(lFileAllegato, "");
						try(OutputStream outputStream = new FileOutputStream(lFileAllegatoSbustato)){
						    IOUtils.copy(lInputStream, outputStream);
						} catch (FileNotFoundException e) {
							throw new Exception("Errore nella getStreamFileProtocollo() allegati = " + e.getMessage());
						} catch (IOException e) {
							throw new Exception("Errore nella getStreamFileProtocollo() allegati = " + e.getMessage());
						}
						result.add(lFileAllegatoSbustato);
					}
				}
			}
		}		
		return result;
	}
	
	// Legge le titolazioni di COGITO  
	private List<TitolarioCogitoBean> getTitolazioniCogito(List<File> streamFileIn, String idUo)  throws Exception {
		
		String nroLivello = null;                   // -- 1:  N.ro che indica il livello gerarchico della classificazione
		String idClassificazione = null;            // -- 2:  Identificativo della classificazione
		String descrizione = null;                  // -- 3:  Descrizione della classificazione
		String tipo = null;                         // -- 4:  Tipo di classificazione (titolo, classe, sottoclasse ecc)
		String descrizioneEstesa = null;            // -- 5:  Descrizione estesa (cioè incluse le descrizioni delle classifiche superiori)
		String paroleChiave = null;                 // -- 6:  Parole chiave legate alla classificazione
		String indice = null;                       // -- 7:  Indice della classificazione (come va mostrato a GUI)
		String tsValidaDal = null;                  // -- 8:  Data di inizio validità della classificazione ( nel formato dd/MM/yyyy)
		String tsValidaFinoAl = null;               // -- 9:  Data di fine validità della classificazione ( nel formato dd/MM/yyyy)
		String periodoConservAnni = null;           // -- 17: Tempo di conservazione prestabilito per gli oggetti con la data classificazione
		String flgSelXFinalita = "1";               // -- 19: (1/0) Se 1 indica che il record è selezionabile per la finalità indicata in FinalitaIn, se 0 che non è selezionabile
		String idClassificaSup = null;              // -- 20: Id. della classifica superiore (valorizzata solo se questa è diversa da CercaInClassificazioneIO)
		String desClassificaSup = null;             // -- 21: Denominazione della classifica superiore (valorizzata solo se questa è diversa da CercaInClassificazioneIO)
		String score = null;                        // -- 24: Score del record restituito dall'indicizzatore (valori interi da 1 a 5) (valorizzato solo se effettuata ricerca full-text)
		String indiceXOrd = null;                   // -- 25: Nri livello della classificazione (come memorizzati in DB)
		
		
		
		
		
		// Leggo le titolazioni di COGITO
		CogitoUtility cogito = new CogitoUtility();    
		CogitoResponseBean cogitoResponse = new CogitoResponseBean();
		DmpkCogitoIucogitologBean beanLog = new DmpkCogitoIucogitologBean();
		beanLog.setIdcogitooperationin(new BigDecimal(1));
		beanLog.setNrilivellisceltain("");
		beanLog.setNrilivellirestituitein("");
		beanLog.setIduoin(StringUtils.isNotBlank(idUo) ? new BigDecimal(idUo) : null);
		
		try {
			cogitoResponse = cogito.submitFilesToCogito(streamFileIn);
			beanLog.setTsiniziochiamatain(new SimpleDateFormat(FMT_STD_TIMESTAMP_WITH_SEC).format(cogito.getStartCall()));
			beanLog.setTsfinechiamatain(new SimpleDateFormat(FMT_STD_TIMESTAMP_WITH_SEC).format(cogito.getEndCall()));
		} catch (Exception e) {
			if (cogito.getError() != null && !cogito.getError().equals("")) {
				beanLog.setTsiniziochiamatain(new SimpleDateFormat(FMT_STD_TIMESTAMP_WITH_SEC).format(cogito.getStartCall() != null ? cogito.getStartCall() : new Date()));
				beanLog.setTsfinechiamatain(new SimpleDateFormat(FMT_STD_TIMESTAMP_WITH_SEC).format(cogito.getEndCall() != null ? cogito.getEndCall() : new Date()));	
				beanLog.setErrmsgcogitoin(cogito.getError());
				this.tracciaLogTitolario(beanLog);
			}
			logger.error(e);
			throw new AurigaCogitoException("Impossibile ottenere il suggerimento di classificazione da COGITO con i dati presenti");
		}
		// lista classificazioni risultanti da cogito
		List<CategorizzazioneCogitoBean> lista = cogitoResponse.getListaCategorizzazioni(); 
		
		// lista dati da visualizzare in maschera
		List<TitolarioCogitoBean> result = new ArrayList<TitolarioCogitoBean>();	
		String nriLivelliRestituiteIn = "";
		if (lista.size() == 0) {
			nriLivelliRestituiteIn = "";
		}
		for (int i = 0; i < lista.size(); i++) {
			CategorizzazioneCogitoBean classificazione = lista.get(i);
		
			String[] livello = classificazione.getCodeCategorizzazione().split(".");
			String codeCategorizzazione = classificazione.getCodeCategorizzazione();
			nroLivello = ""+livello.length;
			nriLivelliRestituiteIn += codeCategorizzazione;
			if (i != lista.size()-1) {
				nriLivelliRestituiteIn += ";";
			}
			// Copio ogni titolazione di COGITO nel bean 		
			TitolarioCogitoBean node = new TitolarioCogitoBean();
			node.setNroLivello(nroLivello);                                   
			node.setDescrizione(classificazione.getDescrCategorizzazione());                              
			node.setTipo(nroLivello);                                      
			node.setDescrizioneEstesa(classificazione.getDescrHierarchy()); 
			node.setParoleChiave(paroleChiave); 
			node.setIndice(classificazione.getCodeCategorizzazione()); 
			node.setTsValidaDal(tsValidaDal != null ? new SimpleDateFormat(FMT_STD_DATA).parse(tsValidaDal) : null);
			node.setTsValidaFinoAl(tsValidaFinoAl != null ? new SimpleDateFormat(FMT_STD_DATA).parse(tsValidaFinoAl) : null);
			node.setPeriodoConservAnni(periodoConservAnni != null ? periodoConservAnni : null);
			node.setFlgSelXFinalita(flgSelXFinalita != null && "1".equals(flgSelXFinalita)); 
			node.setIdClassificaSup(idClassificaSup); 
			node.setDesClassificaSup(desClassificaSup);
			node.setScore(classificazione.getFreq() != null ? classificazione.getFreq() : null);
			node.setIndiceXOrd(indiceXOrd);
			
			idClassificazione = getIdClassificazione(classificazione.getCodeCategorizzazione(), "","");
			node.setIdClassificazione(idClassificazione);    
			
			// Aggiungo la titolazione di COGITO nella lista 
			result.add(node);			
		}
				
		// Formatto i livelli con gli 0 iniziali				
		if (StringUtils.isNotBlank(nriLivelliRestituiteIn) && !nriLivelliRestituiteIn.equalsIgnoreCase("")){
			String nriLivelliRestituitePadIn =nriLivelliRestituiteIn;	
			nriLivelliRestituiteIn = ConvertNriLivelliTitXDB(nriLivelliRestituitePadIn);
		}
			
		beanLog.setNrilivellirestituitein(nriLivelliRestituiteIn);
		beanLog.setNrilivellisceltain("");

		this.tracciaLogTitolario(beanLog);
		// Restituisco alla GUI la lista delle titolazioni 
		if (result.size() == 0) {
			TitolarioCogitoBean bean = new TitolarioCogitoBean();
			bean.setIdCogitoLogIO(idCogitoLogIO.toString());
			bean.setIndice("nessun_risultato");
			result.add(bean);
		} else {
			for (TitolarioCogitoBean bean : result) {
				bean.setIdCogitoLogIO(idCogitoLogIO.toString());
			}
		}
		return result;
	}

    private String ConvertNriLivelliTitXDB(String nriLivelli) throws Exception {
    
		
		// nota : Nella tabella DMT_DEF_CONFIF_PARAM i parametri devono avere FLG_X_GUI = 1 
		String separatoreLivelli   = ParametriDBUtil.getParametroDB(getSession(), "SEP_LIVELLI_CLASSIFICAZIONE"); 
		separatoreLivelli   = StringUtils.isNotBlank(separatoreLivelli) ? separatoreLivelli : ".";
				
		String maxLenNroLiv   = ParametriDBUtil.getParametroDB(getSession(), "MAX_LEN_NRO_LIV_CLASSIF");
		maxLenNroLiv   = StringUtils.isNotBlank(maxLenNroLiv) ? maxLenNroLiv : "3";
		
    	String result = null;
    	
    	// Estraggo i livelli
    	String[] livelli = extractLivelliDaNriLivelli(nriLivelli, separatoreLivelli);
    	
    	result = getLivelliPadded(livelli, maxLenNroLiv, separatoreLivelli);
    	
    	return result;
    }
    
    
    public String[] extractLivelliDaNriLivelli(String livelliIn, String separatoreIn) throws Exception {
		
		String[] result = null;
		if (livelliIn!=null && !livelliIn.equalsIgnoreCase("")){
			String[] livelliSplit = livelliIn.split("\\"+separatoreIn);
			result = new String[livelliSplit.length];	
			for (int i = 0; i < livelliSplit.length; i++) {
				if (livelliSplit[i]!=null && !livelliSplit[i].equalsIgnoreCase("")){
					result[i] =  String.valueOf(Integer.parseInt(livelliSplit [i]));
				}	
				else{
					result[i] = "0";
				}
			}
		}		
		return result;
	}
	
    
    public String getLivelliPadded(String[] livelliIn, String maxLenNroLivIn, String separatoreLivIn) {
    	
		String result = "";
		
		if (livelliIn.length>0){
			if (maxLenNroLivIn == null || ("").equalsIgnoreCase(maxLenNroLivIn)) {
				maxLenNroLivIn = "0";
			}	
			for (int i = 0; i < livelliIn.length; i++) {
				if(livelliIn[i]!=null && !livelliIn[i].equalsIgnoreCase("")){
					if(i==0)
						result += StringUtils.leftPad(livelliIn[i].trim(), Integer.parseInt(maxLenNroLivIn), "0");
					else
						result += separatoreLivIn + StringUtils.leftPad(livelliIn[i].trim(), Integer.parseInt(maxLenNroLivIn), "0");
				}	
			}
		}
		return result;
	}
	
    
	private String getIdClassificazione(String indice, String idClassifica, String descrizione)  throws Exception {
				
		String result = null;
		List<ClassificaBean> lListResult = new ArrayList<ClassificaBean>();
		
		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() != null ? AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() : "";

		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("CLASSIFICAZIONE");
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("ID_USER_LAVORO|*|" + idUserLavoro + "|*|FLG_SOLO_ASSEGNABILI|*|1|*|INDICE|*|" + indice + "|*|STR_IN_DES|*|" + descrizione + "|*|CI_TO_ADD|*|" + idClassifica);
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(BigDecimal.ONE);

		// Eseguo il servizio
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		
		// Leggo il result
		if(StringUtils.isBlank(lStoreResultBean.getDefaultMessage())) {
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			try {
				lListResult = XmlListaUtility.recuperaLista(xmlLista, ClassificaBean.class);
			} catch (Exception e) {
				throw new Exception("Errore nella getIdClassificazione() = " + e.getMessage());
			}							
		} 
		
		// Cerco l'indice nella lista delle classifiche 
		if (lListResult!=null && lListResult.size()>0){			
			for (ClassificaBean lClassificaBean : lListResult) {
				 // Confornto l'indice in input con quello restituito dalla store  
				 if (StringUtils.isNotBlank(lClassificaBean.getIdClassifica()) && !("").equals(lClassificaBean.getIdClassifica())){
					 result = lClassificaBean.getIdClassifica();
				     break;	 
				 }
			}
		}
		return result;
	}
	
	private void tracciaLogTitolario (DmpkCogitoIucogitologBean input) throws Exception {
		DmpkCogitoIucogitolog store = new DmpkCogitoIucogitolog ();
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		if (idCogitoLogIO != null) {
			input.setIdcogitologio(idCogitoLogIO);
		}
		StoreResultBean<DmpkCogitoIucogitologBean> result = store.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), input);
		if (result.isInError()) {
			throw new StoreException(result);
		}
		idCogitoLogIO = result.getResultBean().getIdcogitologio();
	}
	
	@Override
	public TitolarioCogitoBean get(TitolarioCogitoBean bean) throws Exception {
			return bean;
	}	
	
	@Override
	public TitolarioCogitoBean add(TitolarioCogitoBean bean) throws Exception {
			return bean;
	}
	
	@Override
	public TitolarioCogitoBean update(TitolarioCogitoBean bean, TitolarioCogitoBean oldvalue) throws Exception {
		return bean;
	}
	
	@Override
	public TitolarioCogitoBean remove(TitolarioCogitoBean bean) throws Exception {
		return bean;
	}
	
	public void tracciaSceltaDaCogito (TracciaturaTitolarioCogitoBean input) throws Exception{
		DmpkCogitoIucogitologBean bean = new DmpkCogitoIucogitologBean();
		
		bean.setIdcogitologio(new BigDecimal(input.getIdCogitoLogIO()));
		
		// Formatto i livelli con gli 0 iniziali		
		String nriLivelliSceltaIn = input.getNriLivelliSceltaIn();
		if (StringUtils.isNotBlank(nriLivelliSceltaIn) && !nriLivelliSceltaIn.equalsIgnoreCase("")){
			String nriLivelliSceltaPadIn = nriLivelliSceltaIn;	
			nriLivelliSceltaIn = ConvertNriLivelliTitXDB(nriLivelliSceltaPadIn);
		}
		
		bean.setNrilivellisceltain(nriLivelliSceltaIn);
		bean.setIdcogitooperationin(new BigDecimal(2));
		bean.setErrmsgcogitoin("");
		
		// Viene passato #NO_UPD come default per non fare update su questo campo
		bean.setNrilivellirestituitein("#NO_UPD");
		
		// Viene passato 01/01/9999 come default per non fare update su questi due campi
		bean.setTsiniziochiamatain("01/01/9999");
		bean.setTsfinechiamatain("01/01/9999");

		// Viene passato -999 come default per non fare update su questo campo
		bean.setIduoin(new BigDecimal(-999));
		if (input.getIdUdCogito() != null && !input.getIdUdCogito().equals("")) {
			bean.setIdudin(new BigDecimal(input.getIdUdCogito()));
		}
		tracciaLogTitolario(bean);
	}
}
