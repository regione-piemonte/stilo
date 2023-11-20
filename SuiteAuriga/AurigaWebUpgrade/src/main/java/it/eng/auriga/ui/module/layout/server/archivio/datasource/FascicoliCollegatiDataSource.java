/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpdfolderBean;
import it.eng.auriga.database.store.dmpk_repository_gui.bean.DmpkRepositoryGuiGetfoldercollegatiBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.FascCollegatoBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.FascicoliCollegatiBean;
import it.eng.client.DmpkCoreUpdfolder;
import it.eng.client.DmpkRepositoryGuiGetfoldercollegati;
import it.eng.document.function.bean.RelazioneVsFolderBean;
import it.eng.document.function.bean.XmlFascicoloIn;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;

@Datasource(id = "FascicoliCollegatiDataSource")
public class FascicoliCollegatiDataSource extends AbstractFetchDataSource<FascicoliCollegatiBean>{	

	@Override
	public FascicoliCollegatiBean get(FascicoliCollegatiBean bean) throws Exception {		
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
				
		if(StringUtils.isNotBlank(bean.getIdFolder())) {
				        
	        DmpkRepositoryGuiGetfoldercollegatiBean input = new DmpkRepositoryGuiGetfoldercollegatiBean();
	        input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			input.setIdfolderin(new BigDecimal(bean.getIdFolder()));
			
			DmpkRepositoryGuiGetfoldercollegati lstore = new DmpkRepositoryGuiGetfoldercollegati();
			StoreResultBean<DmpkRepositoryGuiGetfoldercollegatiBean> output = lstore.execute(getLocale(),loginBean, input);
			
			if(output.getResultBean().getListarelazionivsfolderout() != null) {
				StringReader sr = new StringReader(output.getResultBean().getListarelazionivsfolderout());
				
				// Lista con le registrazioni che sono possibili duplicati della registrazione che si sta per effettuare (XML conforme a schema LISTA_STD.xsd)
				Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);			
				if(lista != null) {
					List<FascCollegatoBean> listaFascicoliCollegati = new ArrayList<FascCollegatoBean>();					
					for (int i = 0; i < lista.getRiga().size(); i++) 
					{
						Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));																										
						FascCollegatoBean fasc = new FascCollegatoBean();
						fasc.setIdFolderFascicolo(v.get(0)); 						                                        // 1: Identificativo della cartella
						fasc.setPathFolder(v.get(1)); 				                                                        // 2: Percorso e nome della cartella
			       		fasc.setAnnoFascicolo(StringUtils.isNotBlank(v.get(2)) ? new Integer(v.get(2)) : null);       		// 3: Anno di apertura della cartella (se basata sul titolario di classificazione)
			       		fasc.setIndice(v.get(3));																			// 4: Livelli di classificazione della cartella se basata sul titolario di classificazione (separati dal separatore standard previsto per i livelli di classificazione nel dominio)
			       		fasc.setNroFascicolo(v.get(4)); 																	// 5: N.ro di fascicolo (se cartella basata sul titolario di classificazione)
			       		fasc.setMotivi(v.get(11));                                                                          // 12: Dettagli della relazione (campo descrittivo)
			       		fasc.setNroSottofascicolo(v.get(13));                                                               // 14: N.ro di sotto-fascicolo (se sottofascicolo basato sul titolario di classificazione)
			       		fasc.setNroInserto(v.get(14));                                                                      // 15: N.ro di inserto (se inserto di sottofascicolo basato sul titolario di classificazione)
			       		fasc.setCodice(v.get(15));                                                                          // 16: N.ro secondario della cartella collegata
			       		fasc.setNomeFascicolo(v.get(16));                                                                   // 17: Nome della cartella collegata
			       		fasc.setDatiCollegamento(v.get(18));                                                                // 19: Tipo, oggetto + dati di trace sulla cartella collegata da usare come alt della stessa
			       		fasc.setIdClassifica(v.get(20));                                                                    // 21: Id. della classifica
			       		fasc.setClassifiche(v.get(20));                                                                     // 21: Id. della classifica
			       		fasc.setDescrizioneClassifica(v.get(21));                                                           // 22: Descrizione della classifica
			       		listaFascicoliCollegati.add(fasc);
			    	}
					bean.setListaFascicoliCollegati(listaFascicoliCollegati);
				}					
			}
		}				
		return bean;
	}	
	
	@Override
	public FascicoliCollegatiBean add(FascicoliCollegatiBean bean) throws Exception {		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		
		// Inizializzo input 
		DmpkCoreUpdfolderBean input = new DmpkCoreUpdfolderBean();
		input.setCodidconnectiontokenin(loginBean.getToken());
		input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
		input.setIdfolderin(new BigDecimal(bean.getIdFolder()));
				
		List<RelazioneVsFolderBean> relazioniVsFolder = new ArrayList<RelazioneVsFolderBean>();
		if(bean.getListaFascicoliCollegati() != null) {
			for(FascCollegatoBean fascCollegato : bean.getListaFascicoliCollegati()) {
				RelazioneVsFolderBean relVsFolder = new RelazioneVsFolderBean();
				relVsFolder.setIdFolder(fascCollegato.getIdFolderFascicolo());
				relVsFolder.setPathFolder(fascCollegato.getPathFolder());
				relVsFolder.setAnnoFascicolo(fascCollegato.getAnnoFascicolo() != null ? String.valueOf(fascCollegato.getAnnoFascicolo()) : null);
				relVsFolder.setLivelliClassificazione((StringUtils.isNotBlank(fascCollegato.getIndice()) ? (fascCollegato.getIndice()) : ""));

				String riferimentoFascicolo =  (StringUtils.isNotBlank(fascCollegato.getNroFascicolo()) ? (fascCollegato.getNroFascicolo()) : "");
				riferimentoFascicolo = riferimentoFascicolo + (StringUtils.isNotBlank(fascCollegato.getNroSottofascicolo()) ? ("/" + fascCollegato.getNroSottofascicolo()) : "");
				riferimentoFascicolo = riferimentoFascicolo + (StringUtils.isNotBlank(fascCollegato.getNroInserto()) ? ("/" + fascCollegato.getNroInserto()) : "");
						
				relVsFolder.setRiferimentoFascicolo(riferimentoFascicolo);
				relVsFolder.setMotiviCollegamento(fascCollegato.getMotivi());
				relVsFolder.setValoreFisso("C");
				relazioniVsFolder.add(relVsFolder);
			}
		}
		
		XmlFascicoloIn xmlFascicoloIn = new XmlFascicoloIn();
		xmlFascicoloIn.setRelazioniVsFolder(relazioniVsFolder);
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		input.setAttributixmlin(lXmlUtilitySerializer.bindXml(xmlFascicoloIn));
		
		// Lancio la store
		DmpkCoreUpdfolder dmpkCoreUpdfolder = new DmpkCoreUpdfolder();
		StoreResultBean<DmpkCoreUpdfolderBean> output = dmpkCoreUpdfolder.execute(getLocale(),loginBean, input);
		
		// Leggo il risultato
		if(output.getDefaultMessage() != null) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		return bean;
	}
	
	@Override
	public FascicoliCollegatiBean update(FascicoliCollegatiBean bean,FascicoliCollegatiBean oldvalue) throws Exception {
		return bean;
	}
	
	@Override
	public FascicoliCollegatiBean remove(FascicoliCollegatiBean bean) throws Exception {
		return null;
	}

	@Override
	public PaginatorBean<FascicoliCollegatiBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(FascicoliCollegatiBean bean) throws Exception {
		return null;
	}
}