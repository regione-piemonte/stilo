/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_amm_trasp.bean.DmpkAmmTraspDsezioneBean;
import it.eng.auriga.database.store.dmpk_amm_trasp.bean.DmpkAmmTraspGetabilitatisezBean;
import it.eng.auriga.database.store.dmpk_amm_trasp.bean.DmpkAmmTraspIusezioneBean;
import it.eng.auriga.database.store.dmpk_amm_trasp.bean.DmpkAmmTraspSetabilitatisezBean;
import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.gestioneContenutiAmministrazioneTrasparente.datasource.bean.ContenutiAmmTraspTreeNodeBean;
import it.eng.auriga.ui.module.layout.server.gestioneContenutiAmministrazioneTrasparente.datasource.bean.ContenutiAmmTraspTreeXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneContenutiAmministrazioneTrasparente.datasource.bean.PrivilegiEntitaSezioneBean;
import it.eng.auriga.ui.module.layout.server.gestioneContenutiAmministrazioneTrasparente.datasource.bean.PrivilegiEntitaSezioneSetXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneContenutiAmministrazioneTrasparente.datasource.bean.PrivilegiEntitaSezioneGetXmlBean;
import it.eng.client.DmpkAmmTraspDsezione;
import it.eng.client.DmpkAmmTraspGetabilitatisez;
import it.eng.client.DmpkAmmTraspIusezione;
import it.eng.client.DmpkAmmTraspSetabilitatisez;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractTreeDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.shared.bean.Navigator;
import it.eng.utility.ui.module.layout.shared.bean.NavigatorBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id="ContenutiAmmTraspTreeDatasource")
public class ContenutiAmmTraspTreeDatasource extends AbstractTreeDataSource<ContenutiAmmTraspTreeNodeBean> {
	
	private static Logger mLogger = Logger.getLogger(ContenutiAmmTraspTreeDatasource.class);

	@Override
	public PaginatorBean<ContenutiAmmTraspTreeNodeBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() != null ? AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() : "";

		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("SEZIONI_AMM_TRASPARENTE");
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("ID_USER_LAVORO|*|" + idUserLavoro);
		
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		
		String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
		
		List<ContenutiAmmTraspTreeNodeBean> lListResult = new ArrayList<ContenutiAmmTraspTreeNodeBean>();
		try {
			List<ContenutiAmmTraspTreeXmlBean> dataXml = XmlListaUtility.recuperaLista(xmlLista, ContenutiAmmTraspTreeXmlBean.class);
			if(dataXml != null && !dataXml.isEmpty()) {
				for (ContenutiAmmTraspTreeXmlBean lXmlBean : dataXml){			
					ContenutiAmmTraspTreeNodeBean lBean = new ContenutiAmmTraspTreeNodeBean();
					lBean.setIdNode(lXmlBean.getIdNode());
					lBean.setParentId(lXmlBean.getParentId());
					lBean.setNome(lXmlBean.getNome());
					
					String flgCol4 = (lXmlBean.getFlgToAbil() != null                 && !"".equalsIgnoreCase(lXmlBean.getFlgToAbil())                 ? lXmlBean.getFlgToAbil()                 : "0");
					String flgCol5 = (lXmlBean.getFlgToAbilContenutiTabella() != null && !"".equalsIgnoreCase(lXmlBean.getFlgToAbilContenutiTabella()) ? lXmlBean.getFlgToAbilContenutiTabella() : "0");
					String flgCol7 = (lXmlBean.getLivelloGerarchico() != null         && !"".equalsIgnoreCase(lXmlBean.getLivelloGerarchico())         ? lXmlBean.getLivelloGerarchico()         : "0");
					
					lBean.setShowRifNormativiButton(true);
					lBean.setShowHeaderButton(true);
					lBean.setShowTitoloSezioneNewButton(true);
					lBean.setShowFineSezioneNewButton(true);
					lBean.setShowParagrafoNewButton(true);
					lBean.setShowDocumentoSempliceNewButton(true);
					lBean.setShowDocumentoConAllegatiNewButton(true);
					lBean.setShowTabellaNewButton(true);
					lBean.setShowContenutiTabellaButton(true);
					lBean.setShowContextMenuTree(true);
					lBean.setShowAggiungiSottoSezioneMenu(true);
					
					// Se col4=0 ==> non deve poter far nulla (non possono né pubblicare nella sezione né gestirne contenuti)					
					if (flgCol4.equalsIgnoreCase("0")){
						lBean.setShowRifNormativiButton(false);
						lBean.setShowHeaderButton(false);
						lBean.setShowTitoloSezioneNewButton(false);
						lBean.setShowFineSezioneNewButton(false);
						lBean.setShowParagrafoNewButton(false);
						lBean.setShowDocumentoSempliceNewButton(false);
						lBean.setShowDocumentoConAllegatiNewButton(false);
						lBean.setShowTabellaNewButton(false);
						lBean.setShowContenutiTabellaButton(false);
						lBean.setShowContextMenuTree(false);
					}
						
					// Se col4=1 AND col5=0 ==> deve poter far tutto (sono abilitato a gestire i contenuti della sezione (anche definire nuove sotto-sezioni, tabelle ecc, settare rif. normativi e header)
					if (flgCol4.equalsIgnoreCase("1") && flgCol5.equalsIgnoreCase("0")){
						lBean.setShowRifNormativiButton(true);
						lBean.setShowHeaderButton(true);
						lBean.setShowTitoloSezioneNewButton(true);
						lBean.setShowFineSezioneNewButton(true);
						lBean.setShowParagrafoNewButton(true);
						lBean.setShowDocumentoSempliceNewButton(true);
						lBean.setShowDocumentoConAllegatiNewButton(true);
						lBean.setShowTabellaNewButton(true);
						lBean.setShowContenutiTabellaButton(true);
						lBean.setShowContextMenuTree(true);
					}
					
					// Se col4=1 AND col5=1 ==> deve poter SOLO aggiungere/modificare documenti, paragrafi e contenuti delle tabelle già esistenti
					if (flgCol4.equalsIgnoreCase("1") && flgCol5.equalsIgnoreCase("1")){
						lBean.setShowRifNormativiButton(false);
						lBean.setShowHeaderButton(false);
						lBean.setShowTitoloSezioneNewButton(false);
						lBean.setShowFineSezioneNewButton(false);
						lBean.setShowParagrafoNewButton(true);              // Attivo il bottone per aggiungere/modificare paragrafi
						lBean.setShowDocumentoSempliceNewButton(true);      // Attivo il bottone per aggiungere/modificare documenti semplici               
						lBean.setShowDocumentoConAllegatiNewButton(true);   // Attivo il bottone per aggiungere/modificare documenti con allegati        
						lBean.setShowTabellaNewButton(false);
						lBean.setShowContenutiTabellaButton(true);
						lBean.setShowContextMenuTree(false);
					}
					
					// Se una sezione ha colonna 7 = 3 (ovvero livello gerarchico 3) devono essere SEMPRE inibite/nascoste le azioni di:
					// Aggiunta sotto-sezione (sull’albero)
					// Aggiunta/modifica rif. normativi e header (sia da sopra la lista che da menu contestuale sui contenuti)
					// Aggiunta titolo e fine lista contenuti    (sia da sopra la lista che da menu contestuale sui contenuti)					
					if (flgCol7.equalsIgnoreCase("3")){
						lBean.setShowAggiungiSottoSezioneMenu(false);
						lBean.setShowRifNormativiButton(false);
						lBean.setShowHeaderButton(false);
						lBean.setShowTitoloSezioneNewButton(false);
						lBean.setShowFineSezioneNewButton(false);
					}

					lBean.setFlgSezApertaATutti(lXmlBean.getFlgSezApertaATutti() != null && "1".equalsIgnoreCase(lXmlBean.getFlgSezApertaATutti()));
					
					lBean.setFlgEsplodiNodo("2"); // Apro i nodi di secondo livello
					lListResult.add(lBean);
				}
			}
		} catch (Exception e) {
			mLogger.warn(e);
		}

		PaginatorBean<ContenutiAmmTraspTreeNodeBean> lPaginatorBean = new PaginatorBean<ContenutiAmmTraspTreeNodeBean>();
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());
		return lPaginatorBean;
	}
	
	@Override
	public ContenutiAmmTraspTreeNodeBean add(ContenutiAmmTraspTreeNodeBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		// Inizializzo l'INPUT		
		DmpkAmmTraspIusezioneBean input = new DmpkAmmTraspIusezioneBean();
		input.setIdsezioneio(StringUtils.isNotBlank(bean.getIdSezione()) ? new BigDecimal(bean.getIdSezione()) : null);
		input.setIdsezioneprecin(StringUtils.isNotBlank(bean.getIdSezionePrec()) ? new BigDecimal(bean.getIdSezionePrec()) : null);
		input.setIdsezionepadrein(StringUtils.isNotBlank(bean.getIdSezionePadre()) ? new BigDecimal(bean.getIdSezionePadre()) : null);
		input.setNomesezionein(bean.getNome());
		
		// Eseguo il servizio
		try {
			DmpkAmmTraspIusezione store = new DmpkAmmTraspIusezione();
			StoreResultBean<DmpkAmmTraspIusezioneBean> result = store.execute(getLocale(), loginBean, input);
			
			// Leggo l'esito
			if (StringUtils.isNotBlank(result.getDefaultMessage())) {
				if (result.isInError()) {
					throw new StoreException(result.getDefaultMessage());
				} else {
					addMessage(result.getDefaultMessage(), "", MessageType.WARNING);
				}
			}
			
			// Restituisco il nuovo id sezione
			bean.setIdNode(result.getResultBean().getIdsezioneio() !=null ? String.valueOf (result.getResultBean().getIdsezioneio()) : null);
			
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		} 
		
		return bean;
	}

	@Override
	public ContenutiAmmTraspTreeNodeBean update(ContenutiAmmTraspTreeNodeBean bean, ContenutiAmmTraspTreeNodeBean oldvalue) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		// Inizializzo l'INPUT		
		DmpkAmmTraspIusezioneBean input = new DmpkAmmTraspIusezioneBean();
		
		// IdSezioneIO = id. sezione del nodo selezionato
		// IdSezionePrecIn = non valorizzata
		// IdSezionePadreIn = id. sezione del nodo padre
		// NomeSezioneIn = nome compilato nella pop-up
		// FlgPubblApertaIn = seziona aperta a tutti
		input.setIdsezioneio(StringUtils.isNotBlank(bean.getIdSezione()) ? new BigDecimal(bean.getIdSezione()) : null);
		input.setIdsezioneprecin(StringUtils.isNotBlank(bean.getIdSezionePrec()) ? new BigDecimal(bean.getIdSezionePrec()) : null);
		input.setIdsezionepadrein(StringUtils.isNotBlank(bean.getIdSezionePadre()) ? new BigDecimal(bean.getIdSezionePadre()) : null);
		input.setNomesezionein(bean.getNome());
		input.setFlgpubblapertain(bean.getFlgSezApertaATutti() != null && bean.getFlgSezApertaATutti() ? new Integer("1") : null);
				
		// Eseguo il servizio
		try {
			DmpkAmmTraspIusezione store = new DmpkAmmTraspIusezione();
			StoreResultBean<DmpkAmmTraspIusezioneBean> result = store.execute(getLocale(), loginBean, input);
			
			// Leggo l'esito
			if (StringUtils.isNotBlank(result.getDefaultMessage())) {
				if (result.isInError()) {
					throw new StoreException(result.getDefaultMessage());
				} else {
					addMessage(result.getDefaultMessage(), "", MessageType.WARNING);
				}
			}
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		} 
		
		return bean;
	}
	
	@Override
	public ContenutiAmmTraspTreeNodeBean remove(ContenutiAmmTraspTreeNodeBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		// Inizializzo l'INPUT		
		DmpkAmmTraspDsezioneBean input = new DmpkAmmTraspDsezioneBean();
		
		// IdSezioneIn l'ID del nodo selezionato
		// MotivoIn i motivi compilati nella pop-up
		input.setIdsezionein(StringUtils.isNotBlank(bean.getIdSezione()) ? new BigDecimal(bean.getIdSezione()) : null);
		input.setMotivoin(bean.getMotivoCancellazione());                                                                                                     // Tipo di contenuto
		
		// Eseguo il servizio
		try {
			DmpkAmmTraspDsezione store = new DmpkAmmTraspDsezione();
			StoreResultBean<DmpkAmmTraspDsezioneBean> result = store.execute(getLocale(), loginBean, input);
			
			// Leggo l'esito
			if (StringUtils.isNotBlank(result.getDefaultMessage())) {
				if (result.isInError()) {
					throw new StoreException(result.getDefaultMessage());
				} else {
					addMessage(result.getDefaultMessage(), "", MessageType.WARNING);
				}
			}
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		} 		
		return bean;
	}

	public ContenutiAmmTraspTreeNodeBean removeSezione(ContenutiAmmTraspTreeNodeBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		// Inizializzo l'INPUT		
		DmpkAmmTraspDsezioneBean input = new DmpkAmmTraspDsezioneBean();
		
		// IdSezioneIn l'ID del nodo selezionato
		// MotivoIn i motivi compilati nella pop-up
		input.setIdsezionein(StringUtils.isNotBlank(bean.getIdSezione()) ? new BigDecimal(bean.getIdSezione()) : null);
		input.setMotivoin(bean.getMotivoCancellazione());                                                                                                     // Tipo di contenuto
		
		// Eseguo il servizio
		//StoreResultBean<DmpkAmmTraspDsezioneBean> result;
		try {
			DmpkAmmTraspDsezione store = new DmpkAmmTraspDsezione();
			StoreResultBean<DmpkAmmTraspDsezioneBean> result = store.execute(getLocale(), loginBean, input);
			
			// Leggo l'esito
			if (StringUtils.isNotBlank(result.getDefaultMessage())) {
				if (result.isInError()) {
					throw new StoreException(result.getDefaultMessage());
				} else {
					addMessage(result.getDefaultMessage(), "", MessageType.WARNING);
				}
			}
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		} 		
		return bean;
	}

	public ContenutiAmmTraspTreeNodeBean leggiEntitaAbilitate(ContenutiAmmTraspTreeNodeBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		// Inizializzo l'INPUT		
		DmpkAmmTraspGetabilitatisezBean input = new DmpkAmmTraspGetabilitatisezBean();
		
		// IdSezioneIn l'ID del nodo selezionato
		input.setIdsezionein(StringUtils.isNotBlank(bean.getIdSezione()) ? new BigDecimal(bean.getIdSezione()) : null);
		
		// TipoEntitaIn -- (valori UO/UT) Se UO indica uffici, UT utenti
		input.setTipoentitain(StringUtils.isNotBlank(bean.getTipoEntita()) ? bean.getTipoEntita() : null);
		
		// Eseguo il servizio		
		try {
			DmpkAmmTraspGetabilitatisez store = new DmpkAmmTraspGetabilitatisez();
			StoreResultBean<DmpkAmmTraspGetabilitatisezBean> result = store.execute(getLocale(), loginBean, input);
			
			// Leggo l'esito
			if (StringUtils.isNotBlank(result.getDefaultMessage())) {
				if (result.isInError()) {
					throw new StoreException(result.getDefaultMessage());
				} else {
					addMessage(result.getDefaultMessage(), "", MessageType.WARNING);
				}
			}
			
			// Leggo i privilegi
			List<PrivilegiEntitaSezioneGetXmlBean> listaPrivilegiXml = new ArrayList<PrivilegiEntitaSezioneGetXmlBean>();
			if (result.getResultBean().getListaabilitatiout() != null) {
				listaPrivilegiXml = XmlListaUtility.recuperaLista(result.getResultBean().getListaabilitatiout(), PrivilegiEntitaSezioneGetXmlBean.class);
			}
			
			if(listaPrivilegiXml!=null && listaPrivilegiXml.size()>0) { 
				// Estraggo i privilegi 
	        	List<PrivilegiEntitaSezioneBean> listaPrivilegi = new ArrayList<PrivilegiEntitaSezioneBean>();
	        	listaPrivilegi = getPrivilegiEntitaSezione(listaPrivilegiXml);        	
	        	bean.setListaPrivilegiEntitaAbilitate(listaPrivilegi);
			 }
			
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		} 		
		
		return bean;		
	}	
	
	public ContenutiAmmTraspTreeNodeBean salvaEntitaAbilitate(ContenutiAmmTraspTreeNodeBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		// Inizializzo l'INPUT		
		DmpkAmmTraspSetabilitatisezBean input = new DmpkAmmTraspSetabilitatisezBean();
		
		// IdSezioneIn l'ID del nodo selezionato
		input.setIdsezionein(StringUtils.isNotBlank(bean.getIdSezione()) ? new BigDecimal(bean.getIdSezione()) : null);
		
		// TipoEntitaIn -- (valori UO/UT) Se UO indica uffici, UT utenti
		input.setTipoentitain(StringUtils.isNotBlank(bean.getTipoEntita()) ? bean.getTipoEntita() : null);
		
		// Salvo tutti i privilegi
		List<PrivilegiEntitaSezioneSetXmlBean> listaPrivilegi = new ArrayList<PrivilegiEntitaSezioneSetXmlBean>();
				
		// Salvo i privilegi 		
		setPrivilegi(listaPrivilegi, bean.getListaPrivilegiEntitaAbilitate());
		
		input.setValoriabiltoaddin(new XmlUtilitySerializer().bindXmlList(listaPrivilegi));			
		
		// Eseguo il servizio		
		try {
			DmpkAmmTraspSetabilitatisez store = new DmpkAmmTraspSetabilitatisez();
			StoreResultBean<DmpkAmmTraspSetabilitatisezBean> result = store.execute(getLocale(), loginBean, input);
			
			// Leggo l'esito
			if (StringUtils.isNotBlank(result.getDefaultMessage())) {
				if (result.isInError()) {
					throw new StoreException(result.getDefaultMessage());
				} else {
					addMessage(result.getDefaultMessage(), "", MessageType.WARNING);
				}
			}
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		} 
		
		return bean;
	}
	
	private List<PrivilegiEntitaSezioneBean> getPrivilegiEntitaSezione(List<PrivilegiEntitaSezioneGetXmlBean> listaPrivilegiIn)  throws Exception  {
		List<PrivilegiEntitaSezioneBean> lList = new ArrayList<PrivilegiEntitaSezioneBean>();
		if (listaPrivilegiIn!=null && listaPrivilegiIn.size()>0){
			for (PrivilegiEntitaSezioneGetXmlBean privilegioBean : listaPrivilegiIn) {
				PrivilegiEntitaSezioneBean privilegio = new PrivilegiEntitaSezioneBean();
				privilegio.setIdOggettoPriv(privilegioBean.getIdOggetto());
				privilegio.setTipoOggettoPriv(privilegioBean.getTipoOggetto());
				privilegio.setCodiceOggettoPriv(privilegioBean.getCodiceOggetto());
				privilegio.setDenominazioneOggettoPriv(privilegioBean.getDenominazioneOggetto());	
				
				if(StringUtils.isNotBlank(privilegioBean.getListaCodiciPrivilegiOggetto())){
					String[] values = privilegioBean.getListaCodiciPrivilegiOggetto().split(";");							
					if(values!=null && values.length>0){
						List<String> listaPrivilegiOggettoPriv = new ArrayList<String>();
						for (String item : values) {
							listaPrivilegiOggettoPriv.add(item);
						}
						privilegio.setListaPrivilegiOggettoPriv(listaPrivilegiOggettoPriv);
					}
				}
				lList.add(privilegio);
			}			
		}			
		return lList;		
	}

	public void setPrivilegi(List<PrivilegiEntitaSezioneSetXmlBean> listaPrivilegiOut,  List<PrivilegiEntitaSezioneBean> listaPrivilegiFunzioneIn) throws Exception {
		   
	   if (listaPrivilegiFunzioneIn!=null && listaPrivilegiFunzioneIn.size()>0){
		  	for (PrivilegiEntitaSezioneBean privilegioBean : listaPrivilegiFunzioneIn) {
		  		PrivilegiEntitaSezioneSetXmlBean privilegiEntitaSezioneSetXmlBean = new PrivilegiEntitaSezioneSetXmlBean();
		  		privilegiEntitaSezioneSetXmlBean.setIdOggetto(privilegioBean.getIdOggettoPriv());         // -- 1: (n° intero) Id. univoco del entita
		  		privilegiEntitaSezioneSetXmlBean.setTipoOggetto(privilegioBean.getTipoOggettoPriv());     // -- 2: tipo entità
		  		                                                                                          // -- 3: Indica i privilegi (se più di uno separati da ;)
				if (privilegioBean.getListaPrivilegiOggettoPriv()!=null && privilegioBean.getListaPrivilegiOggettoPriv().size()>0){
					String listaCodiciPrivilegiOggetto = StringUtils.join(privilegioBean.getListaPrivilegiOggettoPriv(), ";");
					privilegiEntitaSezioneSetXmlBean.setListaCodiciPrivilegiOggetto(listaCodiciPrivilegiOggetto);
				}				
				listaPrivilegiOut.add(privilegiEntitaSezioneSetXmlBean);				
			}			
	   }	   
	}
	
	public Navigator getPercorsoIniziale() throws Exception {
		return (Navigator) getSession().getAttribute("CONTENUTIAMMTRASP_NAVIGATOR");
	}
	
	public Navigator calcolaPercorso(ContenutiAmmTraspTreeNodeBean bean) throws Exception {
		bean.setIdFolder(bean.getIdNode());
		return calcolaPercorsoFromList(bean);
	}	
	
	public Navigator calcolaPercorsoFromList(ContenutiAmmTraspTreeNodeBean bean) throws Exception {
		List<NavigatorBean> percorso = new ArrayList<NavigatorBean>();
		Navigator navigator = new Navigator();
		navigator.setPercorso(percorso);
		navigator.setFlgMostraContenuti(true);
		return navigator;
	}
}