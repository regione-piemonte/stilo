/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_anagrafica.bean.DmpkAnagraficaDgrupposoggrubricaBean;
import it.eng.auriga.database.store.dmpk_anagrafica.bean.DmpkAnagraficaIugrupposoggrubricaBean;
import it.eng.auriga.database.store.dmpk_anagrafica.bean.DmpkAnagraficaLoaddettgrupposoggrubricaBean;
import it.eng.auriga.database.store.dmpk_anagrafica.bean.DmpkAnagraficaTrovagruppisoggrubricaBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.AnagraficaGruppiSoggettiBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.AttributiDinamiciGruppiSoggettiXmlBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.SoggettoGruppoBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.SoggettoGruppoXmlBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DettColonnaAttributoListaBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.LoadAttrDinamicoListaOutputBean;
import it.eng.auriga.ui.module.layout.server.common.SezioneCacheAttributiDinamici;
import it.eng.client.DmpkAnagraficaDgrupposoggrubrica;
import it.eng.client.DmpkAnagraficaIugrupposoggrubrica;
import it.eng.client.DmpkAnagraficaLoaddettgrupposoggrubrica;
import it.eng.client.DmpkAnagraficaTrovagruppisoggrubrica;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;

import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;

@Datasource(id = "AnagraficaGruppiSoggettiDataSource")
public class AnagraficaGruppiSoggettiDataSource extends AbstractFetchDataSource<AnagraficaGruppiSoggettiBean> {

	@Override
	public String getNomeEntita() {
		return "gruppi_soggetti";
	}
	
	@Override
	public PaginatorBean<AnagraficaGruppiSoggettiBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {			
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		String colsOrderBy = null;
		String flgDescOrderBy = null;
		Integer flgSenzaPaginazione = 1;			// 1 : Lista non paginata
		Integer numPagina = null;
		Integer numRighePagina = null;
		String nome = null;		
        String codiceRapido = null;
		Integer flgSoggettiInterni = null;
		Integer flgIncludiAnnullati = null;
		
		if(criteria!=null && criteria.getCriteria()!=null){					
			for(Criterion crit : criteria.getCriteria()){
				if("nome".equals(crit.getFieldName())) {
					nome = getTextFilterValue(crit);		
				}
				else if("codiceRapido".equals(crit.getFieldName())) {
					codiceRapido = getTextFilterValue(crit);													
				} 
				else if("flgSoggettiInterni".equals(crit.getFieldName())) {
					if(crit.getValue() != null) {
//						flgSoggettiInterni = new Boolean(String.valueOf(crit.getValue())) ? 1 : 0;
						flgSoggettiInterni = new Integer(getTextFilterValue(crit));	
					}									
				} 
				else if("flgIncludiAnnullati".equals(crit.getFieldName())) {
					if(crit.getValue() != null) {
						flgIncludiAnnullati = new Boolean(String.valueOf(crit.getValue())) ? 1 : 0;
					}									
				} 				
			}
		}
	
		// Inizializzo l'INPUT
		DmpkAnagraficaTrovagruppisoggrubricaBean input = new DmpkAnagraficaTrovagruppisoggrubricaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setColorderbyio(colsOrderBy);
		input.setFlgdescorderbyio(flgDescOrderBy);		
		input.setFlgsenzapaginazionein((flgSenzaPaginazione == null) ? 0 : flgSenzaPaginazione);
		input.setNropaginaio(numPagina);
		input.setBachsizeio(numRighePagina);
		input.setOverflowlimitin(null);
		input.setFlgsenzatotin(null);
				
		// Setto i filtri all'input del servizio
		input.setCodrapidogruppoin(codiceRapido);
		input.setNomegruppoin(nome);
		input.setFlgsologruppisoggintin(flgSoggettiInterni);
		input.setFlgincludinonvalidiin(flgIncludiAnnullati);
		
		// Inizializzo l'OUTPUT
		DmpkAnagraficaTrovagruppisoggrubrica lDmpkAnagraficaTrovagruppisoggrubrica = new DmpkAnagraficaTrovagruppisoggrubrica();
		StoreResultBean<DmpkAnagraficaTrovagruppisoggrubricaBean> output = lDmpkAnagraficaTrovagruppisoggrubrica.execute(getLocale(), loginBean, input);
		
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		List<AnagraficaGruppiSoggettiBean> data = new ArrayList<AnagraficaGruppiSoggettiBean>();		
		if (output.getResultBean().getNrototrecout() != null){	
			StringReader sr = new StringReader(output.getResultBean().getListaxmlout());	
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);			
			if(lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) 
				{
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));					
					AnagraficaGruppiSoggettiBean node = new AnagraficaGruppiSoggettiBean();	        				        		
		       		// Setto i valori dell'XML nel bean		       		
		       		node.setIdGruppo(v.get(0)); //colonna 1  : ID GRUPPO
		       		node.setCodiceRapido(v.get(1)); //colonna 2  : CODICE RAPIDO
					node.setNome(v.get(2)); //colonna 3  : NOME		       		
		       		node.setFlgSoggettiGruppo(v.get(3)); //colonna 4  : FLAG SOGGETTI INTERNI
		       		node.setDtValiditaDa(v.get(5) != null ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(5)) : null); //colonna 6  : VALIDO DA 
                    node.setDtValiditaA(v.get(6) != null ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(6)) : null); //colonna 7  : VALIDO A 
		       		node.setDescUtenteIns(v.get(13)); //colonna 14 : DESC UTENTE INS
		       		node.setDataIns(v.get(14) != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(v.get(14)) : null); //colonna 15 : DATA INS
		       		node.setDescUtenteUltMod(v.get(16)); //colonna 17 : DESC UTENTE ULT MOD
		       		node.setDataUltMod(v.get(17) != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(v.get(17)) : null); //colonna 18 : DATA ULT MOD				 		       		
		       		node.setRecProtetto(v.get(18)); //colonna 19  : REC PROTETTO
		       		//node.setRecProtetto("0");
		       		node.setFlgValido("1");				       		
		       		data.add(node);
				}
			}						
		}
		
		PaginatorBean<AnagraficaGruppiSoggettiBean> lPaginatorBean = new PaginatorBean<AnagraficaGruppiSoggettiBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		return lPaginatorBean;
	}
	
	
	@Override
	public AnagraficaGruppiSoggettiBean get(AnagraficaGruppiSoggettiBean bean) throws Exception {			
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());			
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
	
		// Inizializzo l'INPUT		
		DmpkAnagraficaLoaddettgrupposoggrubricaBean input = new DmpkAnagraficaLoaddettgrupposoggrubricaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);	
		input.setIdgruppoio(new BigDecimal(bean.getIdGruppo()));
				
		// Inizializzo l'OUTPUT
		DmpkAnagraficaLoaddettgrupposoggrubrica loaddettgrupposoggrubrica = new DmpkAnagraficaLoaddettgrupposoggrubrica();
		StoreResultBean<DmpkAnagraficaLoaddettgrupposoggrubricaBean> output = loaddettgrupposoggrubrica.execute(getLocale(), loginBean, input);

		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}		
		
		AnagraficaGruppiSoggettiBean result = new AnagraficaGruppiSoggettiBean();		
		
		// Setto i dati del gruppo  
		result.setIdGruppo(output.getResultBean().getIdgruppoio().toString()); 						// ID GRUPPO
		result.setCodiceRapido(output.getResultBean().getCodrapidoout()); 							// CODICE RAPIDO
		result.setNome(output.getResultBean().getNomegruppoout()); 				            		// NOME		       		
		result.setFlgSoggettiGruppo(output.getResultBean().getFlggruppointernoout().toString()); 	// FLAG SOGGETTI INTERNI		                                                                                            
		result.setDtValiditaDa(StringUtils.isNotBlank(output.getResultBean().getDtiniziovldout()) ? new SimpleDateFormat(FMT_STD_DATA).parse(String.valueOf(output.getResultBean().getDtiniziovldout())) : null);	// VALIDO DA																								
		result.setDtValiditaA(StringUtils.isNotBlank(output.getResultBean().getDtfinevldout())    ? new SimpleDateFormat(FMT_STD_DATA).parse(String.valueOf(output.getResultBean().getDtfinevldout())) : null); 	// VALIDO A	           	
		result.setRecProtetto(output.getResultBean().getFlglockedout().toString()); 				// REC PROTETTO

		// Setto i valori dell'XML contenente i soggetti del gruppo
		List<SoggettoGruppoBean> listaSoggettiGruppo = new ArrayList<SoggettoGruppoBean>();
		if(output.getResultBean().getXmlmembriout() != null) {
			StringReader sr = new StringReader(output.getResultBean().getXmlmembriout());			
			Lista lista = (Lista)SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);			
			if(lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) 
				{
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));					
					SoggettoGruppoBean soggettoGruppoBean = new SoggettoGruppoBean();	        							
					soggettoGruppoBean.setTipoMembro(v.get(0)); // TIPO MEMBRO - Valori possibili: S = Soggetto rubrica; G = Gruppo
					soggettoGruppoBean.setIdSoggettoGruppo(v.get(1)); // ID SOGGETTO/GRUPPO
					soggettoGruppoBean.setCodiceRapidoSoggetto(v.get(2)); // CODICE RAPIDO SOGGETTO/GRUPPO
					soggettoGruppoBean.setDenominazioneSoggetto(v.get(3)); // DENOMINAZIONE/NOME DEL SOGGETTO/GRUPPO
					soggettoGruppoBean.setCodfiscaleSoggetto(v.get(4)); // CODICE FISCALE DEL SOGGETTO	
					soggettoGruppoBean.setTipologiaSoggetto(v.get(6)); // TIPOLOGIA DEL SOGGETTO
					soggettoGruppoBean.setFlgInOrganigramma(v.get(7)); // IN ORGANIGRAMMA
					listaSoggettiGruppo.add(soggettoGruppoBean);
				}
			}	
		}
		result.setListaSoggettiGruppo(listaSoggettiGruppo);
				
		result.setRowid(output.getResultBean().getRowidout());
		
		// legge gli attributi
		LoadAttrDinamicoListaOutputBean lAttributiDinamici = new LoadAttrDinamicoListaOutputBean();
		List<DettColonnaAttributoListaBean> ldatiDettLista = new ArrayList<DettColonnaAttributoListaBean>();

		String xmlListaAttributi = output.getResultBean().getAttributiaddout();
		try {
			ldatiDettLista = XmlListaUtility.recuperaLista(xmlListaAttributi, DettColonnaAttributoListaBean.class);
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}
		
		
		if (ldatiDettLista != null && ldatiDettLista.size() > 0) {
			for (DettColonnaAttributoListaBean lDettColonnaAttributoListaBean : ldatiDettLista) {
				// FLG_INIBITA_ASS
				if (lDettColonnaAttributoListaBean.getNome().equalsIgnoreCase("FLG_INIBITA_ASS")) {
					result.setFlgInibitaAss(lDettColonnaAttributoListaBean.getValoreDefault() != null ? new Boolean(lDettColonnaAttributoListaBean.getValoreDefault().equalsIgnoreCase("1")) : false);
				}				
			}
		}
		
		return result;
	}
	
	@Override
	public AnagraficaGruppiSoggettiBean update(AnagraficaGruppiSoggettiBean bean, AnagraficaGruppiSoggettiBean oldvalue) throws Exception {
				
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		// Inizializzo l'INPUT
		DmpkAnagraficaIugrupposoggrubricaBean input = new DmpkAnagraficaIugrupposoggrubricaBean();
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);	
		input.setCodidconnectiontokenin(token);
		
		input.setIdgruppoio(new BigDecimal(bean.getIdGruppo()));			// Id. del gruppo da modificare		
		input.setCodrapidoin(bean.getCodiceRapido());						// Codice rapido
		input.setNomegruppoin(bean.getNome());								// Nome del gruppo  (obblig.)
																			
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        
		// Data di decorrenza della validita' del gruppo			
		String ldataIniziovldin = null;
		if (bean.getDtValiditaDa() != null)
			ldataIniziovldin = 	df.format(bean.getDtValiditaDa() );			
		input.setDtiniziovldin(ldataIniziovldin != null ? ldataIniziovldin : null);

        // Data di fine della validita' del gruppo
		String ldataFinevldin = null;
		if (bean.getDtValiditaA() != null )
			ldataFinevldin = df.format(bean.getDtValiditaA());			
		input.setDtfinevldin(ldataFinevldin != null ? ldataFinevldin : null);
		
		input.setFlgmodmembriin("C");										// Indica se i membri dle gruppo sono forniti in modo incrementale (=I) oppure completo (=C)
		
		// Lista con i membri - sogg. della rubrica o altri gruppi - del gruppo da modificare
        // -- 1: (obblig.) Tipo di membro. Valori possibili: S = Soggetto rubrica; G = Gruppo
        // -- 2: Id. del soggetto rubrica o gruppo membro
        // -- 3: Cod. rapido del soggetto rubrica o gruppo membro
        // -- 4: Denominazione / nome del soggetto rubrica o gruppo membro
        // -- 5: Cod. fiscale del soggetto rubrica membro
        // -- 6: (valori 1/0/NULL) Flag di membro da eliminare
		String xmlMembri = null;		
		if(bean.getListaSoggettiGruppo() != null && bean.getListaSoggettiGruppo().size() > 0) {
			xmlMembri = getXmlMembri(bean);
		}
		input.setXmlmembriin(xmlMembri);
		
		// Salvo gli attributi
		input.setAttributiaddin(getXMLAttributiDinamici(bean));

		
		
		DmpkAnagraficaIugrupposoggrubrica iugrupposoggrubrica = new DmpkAnagraficaIugrupposoggrubrica();		
		StoreResultBean<DmpkAnagraficaIugrupposoggrubricaBean> output = iugrupposoggrubrica.execute(getLocale(), loginBean, input);
		
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		if (output.getResultBean().getNomegruppoin()==null)
			output.getResultBean().setNomegruppoin("");		
		
		bean.setIdGruppo((String.valueOf(output.getResultBean().getIdgruppoio())));
		bean.setCodiceRapido((String.valueOf(output.getResultBean().getCodrapidoin())));
		bean.setNome((String.valueOf(output.getResultBean().getNomegruppoin())));
		bean.setDtValiditaDa(StringUtils.isNotBlank(output.getResultBean().getDtiniziovldin()) ? new SimpleDateFormat(FMT_STD_DATA).parse(String.valueOf(output.getResultBean().getDtiniziovldin())) : null); 		           
		bean.setDtValiditaA(StringUtils.isNotBlank(output.getResultBean().getDtfinevldin())    ? new SimpleDateFormat(FMT_STD_DATA).parse(String.valueOf(output.getResultBean().getDtfinevldin()))   : null); 		           
				
		if (output.getResultBean().getFlglockedin()==null)
			output.getResultBean().setFlglockedin(0);
				
		bean.setRecProtetto((String.valueOf(output.getResultBean().getFlglockedin())));

		return bean;
	}
		

    @Override
	public AnagraficaGruppiSoggettiBean add(AnagraficaGruppiSoggettiBean bean) throws Exception {
				
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
			
		// Inizializzo l'INPUT
		DmpkAnagraficaIugrupposoggrubricaBean input = new DmpkAnagraficaIugrupposoggrubricaBean();
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);	
		input.setCodidconnectiontokenin(token);		
		input.setCodrapidoin(bean.getCodiceRapido());						// Codice rapido
		input.setNomegruppoin(bean.getNome());								// Nome del gruppo  (obblig.)
																			
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        
		// Data di decorrenza della validita' del gruppo			
		String ldataIniziovldin = null;
		if (bean.getDtValiditaDa() != null)
			ldataIniziovldin = 	df.format(bean.getDtValiditaDa() );			
		input.setDtiniziovldin(ldataIniziovldin != null ? ldataIniziovldin : null);
				
        // Data di fine della validita' del gruppo
		String ldataFinevldin = null;
		if (bean.getDtValiditaA() != null )
			ldataFinevldin = df.format(bean.getDtValiditaA());			
		input.setDtfinevldin(ldataFinevldin != null ? ldataFinevldin : null);
		
		input.setFlgmodmembriin("C");										// Indica se i membri dle gruppo sono forniti in modo incrementale (=I) oppure completo (=C)
		
		// Lista con i membri - sogg. della rubrica o altri gruppi - del gruppo da modificare
        // -- 1: (obblig.) Tipo di membro. Valori possibili: S = Soggetto rubrica; G = Gruppo
        // -- 2: Id. del soggetto rubrica o gruppo membro
        // -- 3: Cod. rapido del soggetto rubrica o gruppo membro
        // -- 4: Denominazione / nome del soggetto rubrica o gruppo membro
        // -- 5: Cod. fiscale del soggetto rubrica membro
        // -- 6: (valori 1/0/NULL) Flag di membro da eliminare
		String xmlMembri = null;		
		if(bean.getListaSoggettiGruppo() != null && bean.getListaSoggettiGruppo().size() > 0) {
			xmlMembri = getXmlMembri(bean);
		}
		input.setXmlmembriin(xmlMembri);
		
		// Salvo gli attributi		
		input.setAttributiaddin(getXMLAttributiDinamici(bean));
				
		DmpkAnagraficaIugrupposoggrubrica iugrupposoggrubrica = new DmpkAnagraficaIugrupposoggrubrica();
		StoreResultBean<DmpkAnagraficaIugrupposoggrubricaBean> output = iugrupposoggrubrica.execute(getLocale(), loginBean, input);
		
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		if (output.getResultBean().getNomegruppoin()==null)
			output.getResultBean().setNomegruppoin("");		
		
		bean.setIdGruppo((String.valueOf(output.getResultBean().getIdgruppoio())));
		bean.setCodiceRapido((String.valueOf(output.getResultBean().getCodrapidoin())));
		bean.setNome((String.valueOf(output.getResultBean().getNomegruppoin())));
		bean.setDtValiditaDa(StringUtils.isNotBlank(output.getResultBean().getDtiniziovldin()) ? new SimpleDateFormat(FMT_STD_DATA).parse(String.valueOf(output.getResultBean().getDtiniziovldin())) : null); 		           
		bean.setDtValiditaA(StringUtils.isNotBlank(output.getResultBean().getDtfinevldin())    ? new SimpleDateFormat(FMT_STD_DATA).parse(String.valueOf(output.getResultBean().getDtfinevldin()))   : null); 		           
				
		if (output.getResultBean().getFlglockedin()==null)
			output.getResultBean().setFlglockedin(0);
				
		bean.setRecProtetto((String.valueOf(output.getResultBean().getFlglockedin())));

		return bean;
	}  
    
    @Override
    public AnagraficaGruppiSoggettiBean remove(AnagraficaGruppiSoggettiBean bean)
    		throws Exception {
    	
    	AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkAnagraficaDgrupposoggrubricaBean input = new DmpkAnagraficaDgrupposoggrubricaBean();			
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);		
		input.setIdgruppoin(new BigDecimal(bean.getIdGruppo()));
		input.setMotiviin(null);
		input.setFlgcancfisicain(null);
				
		DmpkAnagraficaDgrupposoggrubrica dgrupposoggrubrica = new DmpkAnagraficaDgrupposoggrubrica();
		StoreResultBean<DmpkAnagraficaDgrupposoggrubricaBean> output = dgrupposoggrubrica.execute(getLocale(), loginBean, input);
		
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		return bean;
    }
    
    protected String getXmlMembri(AnagraficaGruppiSoggettiBean bean)
			throws JAXBException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		String xmlMembri;
		List<SoggettoGruppoXmlBean> lListMembri = new ArrayList<SoggettoGruppoXmlBean>();
		for (SoggettoGruppoBean lSoggettoGruppoBean : bean.getListaSoggettiGruppo()){			
			SoggettoGruppoXmlBean lSoggettoGruppoXmlBean = 
				new SoggettoGruppoXmlBean();
			
			lSoggettoGruppoXmlBean.setTipoMembro(lSoggettoGruppoBean.getTipoMembro());
			lSoggettoGruppoXmlBean.setIdSoggettoGruppo(lSoggettoGruppoBean.getIdSoggettoGruppo());
			lSoggettoGruppoXmlBean.setCodiceRapidoSoggetto(lSoggettoGruppoBean.getCodiceRapidoSoggetto());
			lSoggettoGruppoXmlBean.setDenominazioneSoggetto(lSoggettoGruppoBean.getDenominazioneSoggetto());	 
			lSoggettoGruppoXmlBean.setCodfiscaleSoggetto(lSoggettoGruppoBean.getCodfiscaleSoggetto());	
			lSoggettoGruppoXmlBean.setTipologiaSoggetto(lSoggettoGruppoBean.getTipologiaSoggetto());	
			lSoggettoGruppoXmlBean.setFlgInOrganigramma(lSoggettoGruppoBean.getFlgInOrganigramma());	
			lSoggettoGruppoXmlBean.setFlgMembroDaEliminare(lSoggettoGruppoBean.getFlgMembroDaEliminare());	
							
			lListMembri.add(lSoggettoGruppoXmlBean);
			
		}
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		xmlMembri = lXmlUtilitySerializer.bindXmlList(lListMembri);
		return xmlMembri;
	}

    private String getXMLAttributiDinamici(AnagraficaGruppiSoggettiBean bean) throws Exception {
    	
    	// Salvo gli attributi custom
    	AttributiDinamiciGruppiSoggettiXmlBean lAttributiDinamiciGruppiSoggettiXmlBean = new AttributiDinamiciGruppiSoggettiXmlBean();
    			
    	if (bean.getFlgInibitaAss() != null && bean.getFlgInibitaAss().equals(new Boolean(true)))
			lAttributiDinamiciGruppiSoggettiXmlBean.setFlgInibitaAss("1");
		else
			lAttributiDinamiciGruppiSoggettiXmlBean.setFlgInibitaAss("0");
		
		
		// Attributi dinamici
		Map<String, Object> valori = bean.getValori() != null ? bean.getValori() : new HashMap<String, Object>();
		Map<String, String> tipiValori = bean.getTipiValori() != null ? bean.getTipiValori() : new HashMap<String, String>();
		SezioneCache sezioneCacheAttributiDinamici = SezioneCacheAttributiDinamici.createSezioneCacheAttributiDinamici(null, valori, tipiValori, getSession());
		lAttributiDinamiciGruppiSoggettiXmlBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);
		
		String xmlAttributiDinamici = null;
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		xmlAttributiDinamici = lXmlUtilitySerializer.bindXml(lAttributiDinamiciGruppiSoggettiXmlBean);
		
		return xmlAttributiDinamici;
    }
}
