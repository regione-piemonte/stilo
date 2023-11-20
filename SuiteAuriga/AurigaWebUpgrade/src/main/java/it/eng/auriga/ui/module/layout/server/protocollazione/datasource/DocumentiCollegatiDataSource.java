/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpddocudBean;
import it.eng.auriga.database.store.dmpk_repository_gui.bean.DmpkRepositoryGuiGetudcollegateBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DocCollegatoBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DocumentiCollegatiUdBean;
import it.eng.client.DmpkCoreUpddocud;
import it.eng.client.DmpkRepositoryGuiGetudcollegate;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.document.function.bean.RelazioneVsUdBean;
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
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.xml.XmlUtilitySerializer;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;

@Datasource(id = "DocumentiCollegatiDataSource")
public class DocumentiCollegatiDataSource extends AbstractFetchDataSource<DocumentiCollegatiUdBean>{	

	@Override
	public DocumentiCollegatiUdBean get(DocumentiCollegatiUdBean bean) throws Exception {		
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
				
		if(StringUtils.isNotBlank(bean.getIdUd())) {
				        
	        DmpkRepositoryGuiGetudcollegateBean input = new DmpkRepositoryGuiGetudcollegateBean();
	        input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			input.setIdudin(new BigDecimal(bean.getIdUd()));
			
			DmpkRepositoryGuiGetudcollegate dmpkRepositoryGuiGetudcollegate = new DmpkRepositoryGuiGetudcollegate();
			StoreResultBean<DmpkRepositoryGuiGetudcollegateBean> output = dmpkRepositoryGuiGetudcollegate.execute(getLocale(),loginBean, input);
			
			if(output.getResultBean().getListarelazionivsudout() != null) {
				StringReader sr = new StringReader(output.getResultBean().getListarelazionivsudout());
				// Lista con le registrazioni che sono possibili duplicati della registrazione che si sta per effettuare (XML conforme a schema LISTA_STD.xsd)
				Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);			
				if(lista != null) {
					List<DocCollegatoBean> listaDocumentiCollegati = new ArrayList<DocCollegatoBean>();					
					for (int i = 0; i < lista.getRiga().size(); i++) {
						Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));																										
						DocCollegatoBean doc = new DocCollegatoBean();
						doc.setIdUdCollegata(v.get(0)); //colonna 1 dell'xml
			       		String categoria = v.get(1); //colonna 2 dell'xml
			       		String sigla = v.get(2); //colonna 3 dell'xml
			       		if("PG".equals(categoria)) {
			       			doc.setTipo("PG");
			       		} else if("R".equals(categoria)) {
			       			doc.setTipo("R");
			       			doc.setSiglaRegistro(sigla);
			       		} else if("P.I.".equals(sigla)) {
			       			doc.setTipo("PI");
			       		} else if("N.I.".equals(sigla)) {
			       			doc.setTipo("NI");			       			
			       		} else if("A".equals(categoria)) {
			       			doc.setTipo("A");
			       			if(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_PROTOCOLLO_WIZARD")) {
			       				doc.setSiglaRegistro(sigla);
			       			} else {
			       				doc.setSiglaRegistroAltraNum(sigla);
			       			}
			       		} else if("I".equals(categoria)) {
			       			doc.setTipo("I");
			       			doc.setSiglaRegistro(sigla);
			       		}
			       		doc.setAnno(StringUtils.isNotBlank(v.get(3)) ? new Integer(v.get(3)) : null); //colonna 4 dell'xml
			       		doc.setNumero(v.get(4)); //colonna 5 dell'xml
			       		doc.setOggetto(v.get(12)); //colonna 13 dell'xml
			       		doc.setMotivi(v.get(11)); //colonna 12 dell'xml
			       		doc.setEstremiReg(v.get(17)); //colonna 18 dell'xml
			       		doc.setDatiCollegamento(v.get(18)); //colonna 19 dell'xml
			       		doc.setFlgLocked(v.get(20) != null && "1".equals(v.get(20))); //colonna 21 dell'xml			       		
			       		listaDocumentiCollegati.add(doc);
			    	}
					bean.setListaDocumentiCollegati(listaDocumentiCollegati);
				}					
			}
		}
				
		return bean;
	}	
	
	@Override
	public DocumentiCollegatiUdBean add(DocumentiCollegatiUdBean bean) throws Exception {		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
				
		DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
		input.setCodidconnectiontokenin(loginBean.getToken());
		input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
		
		List<RelazioneVsUdBean> relazioniVsUd = new ArrayList<RelazioneVsUdBean>();
		CreaModDocumentoInBean lModificaDocumentoInBean = new CreaModDocumentoInBean();
		if(bean.getListaDocumentiCollegati() != null) {
			for(DocCollegatoBean docCollegato : bean.getListaDocumentiCollegati()) {
				RelazioneVsUdBean relVsUd = new RelazioneVsUdBean();
				relVsUd.setIdUdCollegata(docCollegato.getIdUdCollegata());
				if(docCollegato.getTipo() != null) {
					if(docCollegato.getTipo().equals("PG")) {					
						relVsUd.setCategoria("PG");
						relVsUd.setSiglaProt(null);					
					} else if(docCollegato.getTipo().equals("PI")) {
						relVsUd.setCategoria("I");					
						relVsUd.setSiglaProt("P.I.");					
					} else if(docCollegato.getTipo().equals("NI")) {
						relVsUd.setCategoria("I");					
						relVsUd.setSiglaProt("N.I.");					
					} else if(docCollegato.getTipo().equals("R")) {
						relVsUd.setCategoria("R");					
						relVsUd.setSiglaProt(docCollegato.getSiglaRegistro());					
					} else if(docCollegato.getTipo().equals("A")) {
						relVsUd.setCategoria("A");	
						if(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_PROTOCOLLO_WIZARD")) {
							relVsUd.setSiglaProt(docCollegato.getSiglaRegistro());
						} else {
							relVsUd.setSiglaProt(docCollegato.getSiglaRegistroAltraNum());
						}
					} else if(docCollegato.getTipo().equals("I")) {
						relVsUd.setCategoria("I");		
						relVsUd.setSiglaProt(docCollegato.getSiglaRegistro());
					}
				}
				relVsUd.setAnnoProt(docCollegato.getAnno() != null ? String.valueOf(docCollegato.getAnno()) : null);
				relVsUd.setNroProt(docCollegato.getNumero());
				relVsUd.setValoreFisso("C");
				relVsUd.setMotiviCollegamento(docCollegato.getMotivi());
				relazioniVsUd.add(relVsUd);
			}
		}
		lModificaDocumentoInBean.setRelazioniVsUd(relazioniVsUd);
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(lModificaDocumentoInBean));		
		
		input.setFlgtipotargetin("U");
		input.setIduddocin(StringUtils.isNotBlank(bean.getIdUd()) ? new BigDecimal(bean.getIdUd()) : null);				
		
		DmpkCoreUpddocud dmpkCoreUpddocud = new DmpkCoreUpddocud();		
		StoreResultBean<DmpkCoreUpddocudBean> output = dmpkCoreUpddocud.execute(getLocale(), loginBean, input);
		
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		return bean;
	}
	
	@Override
	public DocumentiCollegatiUdBean update(DocumentiCollegatiUdBean bean,
			DocumentiCollegatiUdBean oldvalue) throws Exception {
		return bean;
	}
	
	@Override
	public DocumentiCollegatiUdBean remove(DocumentiCollegatiUdBean bean)
	throws Exception {
		return null;
	}

	@Override
	public PaginatorBean<DocumentiCollegatiUdBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(DocumentiCollegatiUdBean bean)
	throws Exception {
		return null;
	}

}
