/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_process_types.bean.DmpkProcessTypesDfasewfBean;
import it.eng.auriga.database.store.dmpk_process_types.bean.DmpkProcessTypesGetprofattivitafasewfBean;
import it.eng.auriga.database.store.dmpk_process_types.bean.DmpkProcessTypesSetprofattivitafasewfBean;
import it.eng.auriga.database.store.dmpk_wf.bean.DmpkWfTrovaattflussowfBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.configurazioneFlussi.datasource.bean.ACLConfigFlussiBean;
import it.eng.auriga.ui.module.layout.server.configurazioneFlussi.datasource.bean.ACLConfigFlussiXmlBean;
import it.eng.auriga.ui.module.layout.server.configurazioneFlussi.datasource.bean.AttributiAddEditabiliXmlBean;
import it.eng.auriga.ui.module.layout.server.configurazioneFlussi.datasource.bean.ConfigurazioneFlussiBean;
import it.eng.auriga.ui.module.layout.server.configurazioneFlussi.datasource.bean.ConfigurazioneFlussiXmlBean;
import it.eng.client.DmpkProcessTypesDfasewf;
import it.eng.client.DmpkProcessTypesGetprofattivitafasewf;
import it.eng.client.DmpkProcessTypesSetprofattivitafasewf;
import it.eng.client.DmpkWfTrovaattflussowf;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id="ConfigurazioneFlussiDatasource")
public class ConfigurazioneFlussiDatasource extends AbstractFetchDataSource<ConfigurazioneFlussiBean>{
	
	@Override
	public PaginatorBean<ConfigurazioneFlussiBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	

		ConfigurazioneFlussiBean filter = new ConfigurazioneFlussiBean();
		
		buildFilterBeanFromCriteria(filter, criteria);
		
		DmpkWfTrovaattflussowfBean input = new DmpkWfTrovaattflussowfBean();
		input.setCodidconnectiontokenin(loginBean.getToken());
		input.setFlgsenzapaginazionein(1);
		
		if (StringUtils.isNotEmpty(filter.getCodTipoFlusso())){
			input.setCitypeflussowfio(filter.getCodTipoFlusso());
		}
		if (StringUtils.isNotEmpty(filter.getNomeFase())){
			input.setStrinnomefasein(filter.getNomeFase());
		}		
		if (StringUtils.isNotEmpty(filter.getNomeTask())){
			input.setStrinnomeattin(filter.getNomeTask());
		}
		
		DmpkWfTrovaattflussowf store = new DmpkWfTrovaattflussowf();
		
		StoreResultBean<DmpkWfTrovaattflussowfBean> lStoreResult = store.execute(getLocale(), loginBean, input);
		if (lStoreResult.isInError()){
			throw new StoreException(lStoreResult);
		}
		
		List<ConfigurazioneFlussiXmlBean> lListResult = XmlListaUtility.recuperaLista(lStoreResult.getResultBean().getListaxmlout(), ConfigurazioneFlussiXmlBean.class);
		List<ConfigurazioneFlussiBean> lListToReturn = new ArrayList<ConfigurazioneFlussiBean>(lListResult.size());
		for (ConfigurazioneFlussiXmlBean lConfigurazioneFlussiXmlBean : lListResult){
			ConfigurazioneFlussiBean lConfigurazioneFlussiBean = new ConfigurazioneFlussiBean();			
			lConfigurazioneFlussiBean.setCodTipoFlusso(filter.getCodTipoFlusso());
			lConfigurazioneFlussiBean.setIdTask(lConfigurazioneFlussiXmlBean.getIdTask());
			lConfigurazioneFlussiBean.setNomeTask(lConfigurazioneFlussiXmlBean.getNomeTask());
			lConfigurazioneFlussiBean.setFlgValido(lConfigurazioneFlussiXmlBean.getFlgValido() != null && "1".equals(lConfigurazioneFlussiXmlBean.getFlgValido()));
			lConfigurazioneFlussiBean.setIdFase(lConfigurazioneFlussiXmlBean.getIdFase());
			lConfigurazioneFlussiBean.setNomeFase(lConfigurazioneFlussiXmlBean.getNomeFase());
			lConfigurazioneFlussiBean.setNumeroOrdine(lConfigurazioneFlussiXmlBean.getNumeroOrdine());
			lListToReturn.add(lConfigurazioneFlussiBean);
		}
		
		return new PaginatorBean<ConfigurazioneFlussiBean>(lListToReturn);
	}
	
	@Override
	public ConfigurazioneFlussiBean get(ConfigurazioneFlussiBean bean)
			throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	

		DmpkProcessTypesGetprofattivitafasewfBean input = new DmpkProcessTypesGetprofattivitafasewfBean();
		input.setCodidconnectiontokenin(loginBean.getToken());
		input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);

		input.setCitypeflussowfin(bean.getCodTipoFlusso());
		input.setCiattivitain(bean.getIdTask());
		input.setCifasein(bean.getIdFase());
		
		DmpkProcessTypesGetprofattivitafasewf store = new DmpkProcessTypesGetprofattivitafasewf();
		
		StoreResultBean<DmpkProcessTypesGetprofattivitafasewfBean> lStoreResult = store.execute(getLocale(), loginBean, input);
		if (lStoreResult.isInError()){
			throw new StoreException(lStoreResult);
		}
		
		bean.setNumeroOrdine(lStoreResult.getResultBean().getNroordinevisout() != null ? String.valueOf(lStoreResult.getResultBean().getNroordinevisout()) : null);
		bean.setIdFaseOld(bean.getIdFase());
		bean.setNomeFase(bean.getIdFase());
		
		List<ACLConfigFlussiBean> listaAcl = new ArrayList<ACLConfigFlussiBean>();
		if(StringUtils.isNotBlank(lStoreResult.getResultBean().getXmlaclout())) {
			List<ACLConfigFlussiXmlBean> listaAclXml = XmlListaUtility.recuperaLista(lStoreResult.getResultBean().getXmlaclout(), ACLConfigFlussiXmlBean.class);
			if(listaAclXml != null) {
				for(int i = 0; i < listaAclXml.size(); i++) {
					ACLConfigFlussiXmlBean lACLConfigFlussiXmlBean = listaAclXml.get(i);
					ACLConfigFlussiBean lACLConfigFlussiBean = new ACLConfigFlussiBean();					
					String tipoDestinatario = lACLConfigFlussiXmlBean.getTipoDestinatario();
					lACLConfigFlussiBean.setTipoDestinatario(tipoDestinatario);
					lACLConfigFlussiBean.setDenominazione(lACLConfigFlussiXmlBean.getDenominazioneDestinatario());
					if(tipoDestinatario != null && "UT".equals(tipoDestinatario)) {
						lACLConfigFlussiBean.setIdUtente(lACLConfigFlussiXmlBean.getIdDestinatario());
						lACLConfigFlussiBean.setCodiceRapido(lACLConfigFlussiXmlBean.getCodiceRapido());
					} else if(tipoDestinatario != null && "SV".equals(tipoDestinatario)) {
						lACLConfigFlussiBean.setOrganigramma(lACLConfigFlussiXmlBean.getTipoDestinatario() + lACLConfigFlussiXmlBean.getIdDestinatario());
						lACLConfigFlussiBean.setIdOrganigramma(lACLConfigFlussiXmlBean.getIdDestinatario());
						lACLConfigFlussiBean.setCodiceUo(lACLConfigFlussiXmlBean.getCodiceRapido());
					} else if(tipoDestinatario != null && "UO".equals(tipoDestinatario)) {
						lACLConfigFlussiBean.setOrganigramma(lACLConfigFlussiXmlBean.getTipoDestinatario() + lACLConfigFlussiXmlBean.getIdDestinatario());
						lACLConfigFlussiBean.setIdOrganigramma(lACLConfigFlussiXmlBean.getIdDestinatario());
						lACLConfigFlussiBean.setCodiceUo(lACLConfigFlussiXmlBean.getCodiceRapido());
					} else if(tipoDestinatario != null && "G".equals(tipoDestinatario)) {
						lACLConfigFlussiBean.setIdGruppo(lACLConfigFlussiXmlBean.getIdDestinatario());
						lACLConfigFlussiBean.setIdGruppoInterno(lACLConfigFlussiXmlBean.getIdDestinatario());
						lACLConfigFlussiBean.setCodiceRapido(lACLConfigFlussiXmlBean.getCodiceRapido());		
					} else if(tipoDestinatario != null && "R".equals(tipoDestinatario)) {
						lACLConfigFlussiBean.setIdRuoloAmm(lACLConfigFlussiXmlBean.getIdDestinatario());
						if(StringUtils.isNotBlank(lACLConfigFlussiXmlBean.getIdUoRuoloAmm())) {
							lACLConfigFlussiBean.setFlgUoRuoloAmm(true);
							lACLConfigFlussiBean.setIdUoRuoloAmm(lACLConfigFlussiXmlBean.getIdUoRuoloAmm());
							lACLConfigFlussiBean.setCodiceUoRuoloAmm(lACLConfigFlussiXmlBean.getCodiceUoRuoloAmm());
							lACLConfigFlussiBean.setDescrizioneUoRuoloAmm(lACLConfigFlussiXmlBean.getDescrizioneUoRuoloAmm());
							lACLConfigFlussiBean.setOrganigrammaUoRuoloAmm("UO" + lACLConfigFlussiXmlBean.getIdUoRuoloAmm());
							lACLConfigFlussiBean.setFlgUoSubordinateRuoloAmm(lACLConfigFlussiXmlBean.getFlgUoSubordinateRuoloAmm() != null && "1".equals(lACLConfigFlussiXmlBean.getFlgUoSubordinateRuoloAmm()));
						}
						if(StringUtils.isNotBlank(lACLConfigFlussiXmlBean.getIdRuoloProcUoRuoloAmm())) {
							lACLConfigFlussiBean.setFlgRuoloProcUoRuoloAmm(true);
							lACLConfigFlussiBean.setIdRuoloProcUoRuoloAmm(lACLConfigFlussiXmlBean.getIdRuoloProcUoRuoloAmm());
							lACLConfigFlussiBean.setDescrRuoloProcUoRuoloAmm(lACLConfigFlussiXmlBean.getIdRuoloProcUoRuoloAmm());
						}
						if(StringUtils.isNotBlank(lACLConfigFlussiXmlBean.getIdRuoloProcSvRuoloAmm())) {
							lACLConfigFlussiBean.setFlgRuoloProcSvRuoloAmm(true);
							lACLConfigFlussiBean.setIdRuoloProcSvRuoloAmm(lACLConfigFlussiXmlBean.getIdRuoloProcSvRuoloAmm());
							lACLConfigFlussiBean.setDescrRuoloProcSvRuoloAmm(lACLConfigFlussiXmlBean.getIdRuoloProcSvRuoloAmm());
						}
					} else if(tipoDestinatario != null && "RP".equals(tipoDestinatario)) {
						lACLConfigFlussiBean.setIdRuoloAttSoggIntProc(lACLConfigFlussiXmlBean.getIdDestinatario());				
					}	
					if(lACLConfigFlussiXmlBean.getFlgVisibilita() != null && "1".equals(lACLConfigFlussiXmlBean.getFlgVisibilita())) {						
						// Se FlgEsecuzione <> 1 e <> 2 allora OpzioniAccesso = V    (solo visibiltà)
						// Se FlgEsecuzione = 1         allora OpzioniAccesso = E    (esecuzione)
						// Se FlgEsecuzione = 2         allora OpzioniAccesso = E-GS (esecuzione (solo in gestione estesa))
						if(lACLConfigFlussiXmlBean.getFlgEsecuzione() != null && !"".equals(lACLConfigFlussiXmlBean.getFlgEsecuzione())) {
							if("1".equals(lACLConfigFlussiXmlBean.getFlgEsecuzione())) {
								lACLConfigFlussiBean.setOpzioniAccesso("E");
							} else if ("2".equals(lACLConfigFlussiXmlBean.getFlgEsecuzione())) {
								lACLConfigFlussiBean.setOpzioniAccesso("E-GS");
							} else {
								lACLConfigFlussiBean.setOpzioniAccesso("V");
							}
						}
						else{
							lACLConfigFlussiBean.setOpzioniAccesso("V");
						}						
					}
					
					if (lACLConfigFlussiXmlBean.getFlgInibitaEscalationSovraordinati() != null && !lACLConfigFlussiXmlBean.getFlgInibitaEscalationSovraordinati().equalsIgnoreCase("")) {
						if (lACLConfigFlussiXmlBean.getFlgInibitaEscalationSovraordinati().equalsIgnoreCase("1"))
							lACLConfigFlussiBean.setFlgInibitaEscalationSovraordinati(true);
						else
							lACLConfigFlussiBean.setFlgInibitaEscalationSovraordinati(false);
					}
					else{
						lACLConfigFlussiBean.setFlgInibitaEscalationSovraordinati(false);
					}
					
					if(lACLConfigFlussiBean.getFlgRuoloProcUoRuoloAmm() != null && lACLConfigFlussiBean.getFlgRuoloProcUoRuoloAmm()) {
						lACLConfigFlussiBean.setUoSovraordinataDiLivello(lACLConfigFlussiXmlBean.getUoSovraordinataDiLivello());
						lACLConfigFlussiBean.setUoSovraordinataDiTipo(lACLConfigFlussiXmlBean.getUoSovraordinataDiTipo());
					}
					
					if(lACLConfigFlussiBean.getFlgRuoloProcSvRuoloAmm() != null && lACLConfigFlussiBean.getFlgRuoloProcSvRuoloAmm()) {
						lACLConfigFlussiBean.setUoSovraordinataDiLivello(lACLConfigFlussiXmlBean.getUoSovraordinataDiLivello());
						lACLConfigFlussiBean.setUoSovraordinataDiTipo(lACLConfigFlussiXmlBean.getUoSovraordinataDiTipo());
					}
					
					listaAcl.add(lACLConfigFlussiBean);
				}
			}
			
		}	
		bean.setListaAcl(listaAcl);
		
		bean.setFlgNessuno(false);
		List<AttributiAddEditabiliXmlBean> listaAttributiAddEditabili = new ArrayList<AttributiAddEditabiliXmlBean>();
		if(StringUtils.isNotBlank(bean.getIdTask())) {			
			if(StringUtils.isNotBlank(lStoreResult.getResultBean().getNomiattraddeditabiliout())) {
				listaAttributiAddEditabili = XmlListaUtility.recuperaLista(lStoreResult.getResultBean().getNomiattraddeditabiliout(), AttributiAddEditabiliXmlBean.class);
				if(listaAttributiAddEditabili != null && listaAttributiAddEditabili.size() == 1 && listaAttributiAddEditabili.get(0).getNomeAttributo().equals("#NONE")) {					
					listaAttributiAddEditabili = new ArrayList<AttributiAddEditabiliXmlBean>();
					bean.setFlgNessuno(true);		
				}
			}				
		}
		bean.setListaAttributiAddEditabili(listaAttributiAddEditabili);	
				
		return bean;
	}

	@Override
	public ConfigurazioneFlussiBean add(ConfigurazioneFlussiBean bean)
			throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	

		DmpkProcessTypesSetprofattivitafasewfBean input = new DmpkProcessTypesSetprofattivitafasewfBean();
		input.setCodidconnectiontokenin(loginBean.getToken());
		input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);

		// Creazione nuova fase
		input.setCitypeflussowfin(bean.getCodTipoFlusso());
		input.setCifasein(bean.getIdFase());
		input.setNroordinevisin(StringUtils.isNotBlank(bean.getNumeroOrdine()) ? Integer.valueOf(bean.getNumeroOrdine()) : null);
		input.setFlgmodaclin("C");
		input.setXmlaclin(setXmlAcl(bean));

		DmpkProcessTypesSetprofattivitafasewf store = new DmpkProcessTypesSetprofattivitafasewf();
		
		StoreResultBean<DmpkProcessTypesSetprofattivitafasewfBean> lStoreResult = store.execute(getLocale(), loginBean, input);
		if (lStoreResult.isInError()){
			throw new StoreException(lStoreResult);
		}
		
		return bean;
	}

	@Override
	public ConfigurazioneFlussiBean update(ConfigurazioneFlussiBean bean,
			ConfigurazioneFlussiBean oldvalue) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	

		DmpkProcessTypesSetprofattivitafasewfBean input = new DmpkProcessTypesSetprofattivitafasewfBean();
		input.setCodidconnectiontokenin(loginBean.getToken());
		input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);

		input.setCitypeflussowfin(bean.getCodTipoFlusso());
		
		if(StringUtils.isNotBlank(bean.getIdTask())) {
			// Profilatura task
			input.setCiattivitain(bean.getIdTask());
			input.setCifasein(bean.getIdFase());
			input.setNroordinevisin(StringUtils.isNotBlank(bean.getNumeroOrdine()) ? Integer.valueOf(bean.getNumeroOrdine()) : null);
			input.setFlgmodaclin("C");
			input.setXmlaclin(setXmlAcl(bean));
			input.setCifaseoldin(bean.getIdFaseOld());			
			input.setNomiattraddeditabiliin(setNomiAttrAddEditabili(bean));		
		} else {
			// Profilatura fase			
			input.setCifasein(bean.getIdFase());
			input.setNroordinevisin(StringUtils.isNotBlank(bean.getNumeroOrdine()) ? Integer.valueOf(bean.getNumeroOrdine()) : null);
			input.setFlgmodaclin("C");		
			input.setXmlaclin(setXmlAcl(bean));
			input.setCifaseoldin(bean.getIdFaseOld());
		}
		
		DmpkProcessTypesSetprofattivitafasewf store = new DmpkProcessTypesSetprofattivitafasewf();
		
		StoreResultBean<DmpkProcessTypesSetprofattivitafasewfBean> lStoreResult = store.execute(getLocale(), loginBean, input);
		if (lStoreResult.isInError()){
			throw new StoreException(lStoreResult);
		}
		
		return bean;
	}
	
	/*
	 -- Ogni ACE è un tag "Riga" che può contenere le seguenti colonne:
 	 -- 1:  Flag che indica il tipo di soggetto a cui sono assentiti/negati permessi. Valori:
	 --     SP    = Soggetto produttore 
	 --     AOO    = AOO
	 --     UT     = Utente
	 --     SV    = Scrivania virtuale (vale a dire la scrivania che un utente occupa nello svolgimento di un dato ruolo nella struttura organizzativa)
	 --     UO    = Unità organizzativa (vale a dire tutte le scrivanie virtuali ad essa afferenti)
	 --     G    = Gruppo (di utenti, UO, scrivanie)
	 --     R    = Ruolo amministrativo contestualizzato ovvero i soggetti che hanno un certo ruolo amministrativo (eventualmente rispetto ad una certa UO o un dato livello della struttura organizzativa o entrambi)
	 --     RP    = Ruolo/attore di processo, ovvero il/i soggetti (che possono essere di uno qualsiasi dei tipi precedenti tranne SP e AOO) che svolgono un certo ruolo nell'ambito del processo
	 -- 2:  Id. del soggetto nell'anagrafe di origine (se SP o AOO è l'ID_SP_AOO, se UT l'ID_USER, se SV l'ID_SCRIVANIA, se UO l'ID_UO, se G l'ID_GRUPPO, se R l'ID_RUOLO_AMM, se RP il RUOLO_SOGG_IN_PROC)
	 --     ATTENZIONE: Se non è congruente con le altre colonne che danno gli estremi del soggetto viene ignorato     
	 -- 3:  Denominazione/nome del destinatario del permesso (se trattasi di UO può essere la denominazione semplice o preceduta da quella di tutte le UO superiori separate da "|")
	 -- 4:  Identificativo della UO cui appartiene la scrivania destinataria del permesso 
	 -- 5:  Nri livello della UO destinataria del permesso o a cui appartiene la scrivania destinataria (tutti o alcuni; però se si specifica un livello è obbligatorio indicare tutti i livelli superiori; vanno specificati come appaiono nella GUI, ovvero romani se previsto, 0 o -- se nulli; sono separati dal separatore dei livelli valido per il soggetto produttore/AOO di appartenenza della UO)
	 -- 6:  Denominazione della UO cui appartiene la scrivania destinataria del permesso (può essere la denominazione semplice o preceduta da quella di tutte le UO superiori separate da "|")
	 -- 7:  Codice che identifica nell'applicazione esterna da cui ci si connette l'utente destinatario del permesso o quello che occupa la scrivania destinataria del permesso
	 -- 8:  Denominazione dell'utente che occupa la scrivania destinataria del permesso
	 -- 9:  (valore 1) Indica che il destinatario del permesso, se UO, sono anche tutte le sue sotto-UO
	 -- 10: (valori interi da 1 a n) Livello della struttura organizzativa in cui si espleta il ruolo amministrativo destinatario del permesso
	 -- 11: Se colonna 1 =R: Codice del tipo di unità organizzativa in cui si espleta il ruolo amministrativo destinatario del permesso (alternativo alla colonna 10)
	 --     Se colonna 1 =UT o SV: Username (delle credenziali locali) dell'utente destinatario del permesso o che occupa la scrivania destinataria del permesso
	 -- 12: Se colonna 1 =R: Descrizione del tipo di unità organizzativa (es: settore, servizio, ecc) in cui si espleta il ruolo amministrativo destinatario del permesso (alternativo alla colonna 10)
	 --    	Se colonna 1 =UT o SV: N.ro di matricola dell'utente destinatario del permesso o che occupa la scrivania destinataria del permesso
	 -- 13: Identificativo della UO in cui si espleta il ruolo amministrativo destinatario del permesso o dalla quale risalire alla/e UO del livello/tipo indicati in cui si espleta il ruolo amministrativo destinatario del permesso
	 -- 14: Denominazione/nome della UO in cui si espleta il ruolo amministrativo destinatario del permesso o dalla quale risalire alla/e UO del livello/tipo indicati in cui si espleta il ruolo amministrativo destinatario del permesso (può essere la denominazione semplice o preceduta da quella di tutte le UO superiori separate da "|")
	 -- 15: Nri livello della UO in cui si espleta il ruolo amministrativo destinatario del permesso o dalla quale risalire alla/e UO del livello/tipo indicati in cui si espleta il ruolo amministrativo destinatario del permesso (tutti o alcuni; però se si specifica un livello è obbligatorio indicare tutti i livelli superiori; vanno specificati come appaiono nella GUI, ovvero romani se previsto, 0 o -- se nulli; sono separati dal separatore dei livelli valido per il soggetto produttore/AOO di appartenenza della UO)
	 -- 16:	(valore 1) Indica che il ruolo amministrativo destinatario del permesso si espleta anche nelle sotto-UO della/e UO rintracciate tramite i valori delle 3 colonne precedenti
	 -- 17: Ruolo di processo (es: ufficio proponente) che hanno la/e UO in cui si espleta il ruolo o da cui risalire alla/e UO (con il livello o tipo indicati) in cui si espleta il ruolo indicato        
	 -- 18: Ruolo di processo (es: Assessore di riferimento) che hanno la/e scrivanie da cui risalire alla/e UO (con il livello o tipo indicati) in cui si espleta il ruolo indicato        
	 -- 19: Flag di visibilità dell'attività (o delle attività della fase). Valori:
	 -- 	1: Consentita
	 -- 	0: Negata
	 -- 	NUL = Nulla di specificato 
	 -- 20: Flag di eseguibilità dell'attività (o delle attività della fase). Valori:
	 -- 	1: Consentita
	 -- 	0: Negata
	 -- 	NUL = Nulla di specificato 
	 -- 21: Flag che indica se l'attività è abilitata (come esecuzione) al soggetto specificato. Valori:
	 --    	2 : solo se è assegnatario del documento/folder cui è relativo il processo
	 --    	1 : se il documento/folder cui è relativo il processo gli è assegnato o se non è assegnato ad alcuno (cioè salvo in caso di diversa assegnazione)
	 --    	0 o NULL: a prescindere dall'assegnazione del documento/folder cui è relativo il processo
	 -- 22: (valori da 1 a 99): Se valorizzato significa che qualcuno corrispondente ad una ACE con NRO_ORD_SEQ valorizzato e < di quello della ACE corrente deve aver già svolto l'attività perchè la possa svolgere anche chi ricade nel soggetto della ACE corrente
	 */
	
	protected String setXmlAcl(ConfigurazioneFlussiBean bean) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		if (bean.getListaAcl() != null && bean.getListaAcl().size() > 0) {			
			List<ACLConfigFlussiXmlBean> listaAclXml = new ArrayList<ACLConfigFlussiXmlBean>();
			for (ACLConfigFlussiBean lACLConfigFlussiBean : bean.getListaAcl()) {
				ACLConfigFlussiXmlBean lACLConfigFlussiXmlBean = new ACLConfigFlussiXmlBean();
				String tipoDestinatario = lACLConfigFlussiBean.getTipoDestinatario();
				lACLConfigFlussiXmlBean.setTipoDestinatario(tipoDestinatario);
				if(tipoDestinatario != null && "UT".equals(tipoDestinatario)) {
					lACLConfigFlussiXmlBean.setIdDestinatario(lACLConfigFlussiBean.getIdUtente());
					lACLConfigFlussiXmlBean.setCodiceRapido(lACLConfigFlussiBean.getCodiceUo());	
				} else if(tipoDestinatario != null && "SV".equals(tipoDestinatario)) {
					lACLConfigFlussiXmlBean.setIdDestinatario(lACLConfigFlussiBean.getIdOrganigramma());
					lACLConfigFlussiXmlBean.setCodiceRapido(lACLConfigFlussiBean.getCodiceUo());	
				} else if(tipoDestinatario != null && "UO".equals(tipoDestinatario)) {
					lACLConfigFlussiXmlBean.setIdDestinatario(lACLConfigFlussiBean.getIdOrganigramma());
					lACLConfigFlussiXmlBean.setCodiceRapido(lACLConfigFlussiBean.getCodiceUo());		
				} else if(tipoDestinatario != null && "G".equals(tipoDestinatario)) {
					lACLConfigFlussiXmlBean.setIdDestinatario(lACLConfigFlussiBean.getIdGruppoInterno());
					lACLConfigFlussiXmlBean.setCodiceRapido(lACLConfigFlussiBean.getCodiceRapido());				
				} else if(tipoDestinatario != null && "R".equals(tipoDestinatario)) {
					lACLConfigFlussiXmlBean.setIdDestinatario(lACLConfigFlussiBean.getIdRuoloAmm());
					if (lACLConfigFlussiBean.getFlgUoRuoloAmm() != null && lACLConfigFlussiBean.getFlgUoRuoloAmm()) {
						lACLConfigFlussiXmlBean.setIdUoRuoloAmm(lACLConfigFlussiBean.getIdUoRuoloAmm());
						if (lACLConfigFlussiBean.getFlgUoSubordinateRuoloAmm() != null && lACLConfigFlussiBean.getFlgUoSubordinateRuoloAmm()) {
							lACLConfigFlussiXmlBean.setFlgUoSubordinateRuoloAmm("1");
						} else {
							lACLConfigFlussiXmlBean.setFlgUoSubordinateRuoloAmm("");
						}
					} else if (lACLConfigFlussiBean.getFlgRuoloProcUoRuoloAmm() != null && lACLConfigFlussiBean.getFlgRuoloProcUoRuoloAmm()) {
						lACLConfigFlussiXmlBean.setIdRuoloProcUoRuoloAmm(lACLConfigFlussiBean.getIdRuoloProcUoRuoloAmm());
						lACLConfigFlussiXmlBean.setUoSovraordinataDiLivello(lACLConfigFlussiBean.getUoSovraordinataDiLivello());
						lACLConfigFlussiXmlBean.setUoSovraordinataDiTipo(lACLConfigFlussiBean.getUoSovraordinataDiTipo());
					} else if (lACLConfigFlussiBean.getFlgRuoloProcSvRuoloAmm() != null && lACLConfigFlussiBean.getFlgRuoloProcSvRuoloAmm()) {
						lACLConfigFlussiXmlBean.setIdRuoloProcSvRuoloAmm(lACLConfigFlussiBean.getIdRuoloProcSvRuoloAmm());
						lACLConfigFlussiXmlBean.setUoSovraordinataDiLivello(lACLConfigFlussiBean.getUoSovraordinataDiLivello());
						lACLConfigFlussiXmlBean.setUoSovraordinataDiTipo(lACLConfigFlussiBean.getUoSovraordinataDiTipo());
					}
				} else if(tipoDestinatario != null && "RP".equals(tipoDestinatario)) {
					lACLConfigFlussiXmlBean.setIdDestinatario(lACLConfigFlussiBean.getIdRuoloAttSoggIntProc());				
				}	
				if(StringUtils.isNotBlank(lACLConfigFlussiBean.getOpzioniAccesso())) {					
					// Se OpzioniAccesso = V (solo visibiltà)                              allora FlgVisibilita = 1 e FlgEsecuzione = ""
					// Se OpzioniAccesso = E (esecuzione)                                  allora FlgVisibilita = 1 e FlgEsecuzione = "1"
					// Se OpzioniAccesso = E-GS (esecuzione (solo in gestione estesa))     allora FlgVisibilita = 1 e FlgEsecuzione = "2"	
					if ("V".equals(lACLConfigFlussiBean.getOpzioniAccesso())) {
						lACLConfigFlussiXmlBean.setFlgVisibilita("1");
						lACLConfigFlussiXmlBean.setFlgEsecuzione("");
					} else if ("E".equals(lACLConfigFlussiBean.getOpzioniAccesso())) {
						lACLConfigFlussiXmlBean.setFlgVisibilita("1");
						lACLConfigFlussiXmlBean.setFlgEsecuzione("1");					
					} else if ("E-GS".equals(lACLConfigFlussiBean.getOpzioniAccesso())) {
						lACLConfigFlussiXmlBean.setFlgVisibilita("1");
						lACLConfigFlussiXmlBean.setFlgEsecuzione("2");					
					}
				}
				
				// Inibita escalation ai sovraordinati
				if(lACLConfigFlussiBean.getFlgInibitaEscalationSovraordinati()!=null && lACLConfigFlussiBean.getFlgInibitaEscalationSovraordinati()) {		
					lACLConfigFlussiXmlBean.setFlgInibitaEscalationSovraordinati("1");
				}
				else{
					lACLConfigFlussiXmlBean.setFlgInibitaEscalationSovraordinati("0");
				}
				
				listaAclXml.add(lACLConfigFlussiXmlBean);			
			}
			XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
			return lXmlUtilitySerializer.bindXmlList(listaAclXml);		
		}
		return null;
	}
	
	protected String setNomiAttrAddEditabili(ConfigurazioneFlussiBean bean) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();		
		if(bean.getFlgNessuno() != null && bean.getFlgNessuno()) {
			List<AttributiAddEditabiliXmlBean> listaAttributiAddEditabili = new ArrayList<AttributiAddEditabiliXmlBean>();
			AttributiAddEditabiliXmlBean lAttributiAddEditabiliXmlBean = new AttributiAddEditabiliXmlBean();
			lAttributiAddEditabiliXmlBean.setNomeAttributo("#NONE");
			listaAttributiAddEditabili.add(lAttributiAddEditabiliXmlBean);
			return lXmlUtilitySerializer.bindXmlList(listaAttributiAddEditabili);		
		} else if (bean.getListaAttributiAddEditabili() != null && bean.getListaAttributiAddEditabili().size() > 0) {
			return lXmlUtilitySerializer.bindXmlList(bean.getListaAttributiAddEditabili());		
		}
		return null;
	}
	
	@Override
	public ConfigurazioneFlussiBean remove(ConfigurazioneFlussiBean bean)
			throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkProcessTypesDfasewfBean input = new DmpkProcessTypesDfasewfBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		
		input.setCitypeflussowfin(bean.getCodTipoFlusso());
		input.setCifasein(bean.getIdFase());

		DmpkProcessTypesDfasewf dFasewf = new DmpkProcessTypesDfasewf();
		StoreResultBean<DmpkProcessTypesDfasewfBean> output = dFasewf.execute(getLocale(), loginBean, input);

		if (output.isInError()) {
			throw new StoreException(output);
		} 

		return bean;
	}

	@Override
	public Map<String, ErrorBean> validate(ConfigurazioneFlussiBean bean)
			throws Exception {
		return null;
	}

	@Override
	public String getNomeEntita() {
		return "configurazione_flussi_modellati";
	}
	
}
