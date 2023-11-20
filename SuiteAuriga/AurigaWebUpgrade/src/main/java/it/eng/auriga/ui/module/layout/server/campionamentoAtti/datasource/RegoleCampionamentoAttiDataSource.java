/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.xml.bind.Marshaller;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_statistiche.bean.DmpkStatisticheApplicaregolacampionamentoattiBean;
import it.eng.auriga.database.store.dmpk_statistiche.bean.DmpkStatisticheTrovaregolecampionamentoattiBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.campionamentoAtti.datasource.bean.GruppoAttiCampionamentoBean;
import it.eng.auriga.ui.module.layout.server.campionamentoAtti.datasource.bean.GruppoAttiCampionamentoXmlBean;
import it.eng.auriga.ui.module.layout.server.campionamentoAtti.datasource.bean.RegoleCampionamentoAttiBean;
import it.eng.client.DmpkStatisticheApplicaregolacampionamentoatti;
import it.eng.client.DmpkStatisticheTrovaregolecampionamentoatti;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;

/**
 * 
 * @author matzanin
 *
 */

@Datasource(id = "RegoleCampionamentoAttiDataSource")
public class RegoleCampionamentoAttiDataSource extends AbstractFetchDataSource<RegoleCampionamentoAttiBean> {

	private static final Logger log = Logger.getLogger(RegoleCampionamentoAttiDataSource.class);

	@Override
	public String getNomeEntita() {
		return "regoleCampionamentoAtti";
	}

	@Override
	public PaginatorBean<RegoleCampionamentoAttiBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
	
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		boolean isNuovaRegola = getExtraparams().get("isNuovaRegola") != null ? new Boolean(getExtraparams().get("isNuovaRegola")) : false;		
		
		Date dataRiferimento = null;
		String idTipoAtto = null;
//		String codiceAtto = null;
		String flgCodiceAttoParticolare = null;
		String flgDeterminaAContrarre = null;
//		String idUoProponente = null;
		String rangeImporto = null;
		String percentualeCampionamento = null;
		String idRegola = null;
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion crit : criteria.getCriteria()) {
				if("dataRiferimento".equals(crit.getFieldName())) {
					// se è una nuova regola non devo considerare questo filtro
					if(!isNuovaRegola) {
						Date[] estremiDataRiferimento = getDateFilterValue(crit);
						dataRiferimento = estremiDataRiferimento[0];						
					}
				} 
				else if ("idTipoAtto".equals(crit.getFieldName())) {
					idTipoAtto = getTextFilterValue(crit);
				} 
//				else if ("codiceAtto".equals(crit.getFieldName())) {
//					codiceAtto = getTextFilterValue(crit);
//				} 
				else if ("flgCodiceAttoParticolare".equals(crit.getFieldName())) {
					flgCodiceAttoParticolare = getTextFilterValue(crit);
				} 
				else if ("flgDeterminaAContrarre".equals(crit.getFieldName())) {
					flgDeterminaAContrarre = getTextFilterValue(crit);
				} 
//				else if ("idUoProponente".equals(crit.getFieldName())) {
//					idUoProponente = getTextFilterValue(crit);
//					if(idUoProponente != null && idUoProponente.startsWith("UO")) {
//						idUoProponente = idUoProponente.substring(2);
//					}
//				} 
				else if ("rangeImporto".equals(crit.getFieldName())) {
					rangeImporto = getTextFilterValue(crit);
				} 
				else if("percentualeCampionamento".equals(crit.getFieldName())) {
					// se è una nuova regola non devo considerare questo filtro
					if(!isNuovaRegola) {
						percentualeCampionamento = getTextFilterValue(crit);
					}
				} 
				else if("idRegola".equals(crit.getFieldName())) {
					// se è una nuova regola non devo considerare questo filtro
					if(!isNuovaRegola) {
						idRegola = getTextFilterValue(crit);
					}
				}
			}
		}
		
		DmpkStatisticheTrovaregolecampionamentoattiBean input = new DmpkStatisticheTrovaregolecampionamentoattiBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		
		SezioneCache scFiltro = new SezioneCache();
		
		if(StringUtils.isNotBlank(idTipoAtto)) {
			Variabile varIdTipoAtto = new Variabile();
			varIdTipoAtto.setNome("IdDocType");
			varIdTipoAtto.setValoreSemplice(idTipoAtto);
			scFiltro.getVariabile().add(varIdTipoAtto);
		}
		
//		if(StringUtils.isNotBlank(codiceAtto)) {
//			Variabile varCodiceAtto = new Variabile();
//			varCodiceAtto.setNome("CodiciAtto");
//			varCodiceAtto.setValoreSemplice(codiceAtto);
//			scFiltro.getVariabile().add(varCodiceAtto);
//		}
		
		
		if(StringUtils.isNotBlank(flgCodiceAttoParticolare)) {
			Variabile varFlgCodiceAttoParticolare = new Variabile();
			varFlgCodiceAttoParticolare.setNome("FlgCodTipoAttoParticolare");
			varFlgCodiceAttoParticolare.setValoreSemplice(flgCodiceAttoParticolare.equalsIgnoreCase("SI") ? "1" : "0");
			scFiltro.getVariabile().add(varFlgCodiceAttoParticolare);
		}
		
		if(StringUtils.isNotBlank(flgDeterminaAContrarre)) {
			Variabile varFlgDeterminaAContrarre = new Variabile();
			varFlgDeterminaAContrarre.setNome("FlgDetContrarre");
			varFlgDeterminaAContrarre.setValoreSemplice(flgDeterminaAContrarre.equalsIgnoreCase("SI") ? "1" : "0");
			scFiltro.getVariabile().add(varFlgDeterminaAContrarre);
		}
		
//		if(StringUtils.isNotBlank(idUoProponente)) {
//			Variabile varIdUoProponente = new Variabile();
//			varIdUoProponente.setNome("IdUOProponente");
//			varIdUoProponente.setValoreSemplice(idUoProponente);
//			scFiltro.getVariabile().add(varIdUoProponente);
//		}
				
		if(StringUtils.isNotBlank(rangeImporto)) {
			Variabile varRangeImporto = new Variabile();
			varRangeImporto.setNome("RangeImporto");
			varRangeImporto.setValoreSemplice(rangeImporto);
			scFiltro.getVariabile().add(varRangeImporto);
		}
				
		if(percentualeCampionamento != null) {
			Variabile varPercentualeCampionamento = new Variabile();
			varPercentualeCampionamento.setNome("Percentuale");
			varPercentualeCampionamento.setValoreSemplice(percentualeCampionamento);
			scFiltro.getVariabile().add(varPercentualeCampionamento);
		}
		
		if(idRegola != null) {
			Variabile varIdRegola = new Variabile();
			varIdRegola.setNome("IdRegola");
			varIdRegola.setValoreSemplice(idRegola);
			scFiltro.getVariabile().add(varIdRegola);
		}
		
		if(dataRiferimento != null) {
			Variabile varDataRiferimento = new Variabile();
			varDataRiferimento.setNome("DataRif");
			varDataRiferimento.setValoreSemplice(new SimpleDateFormat(AbstractDataSource.FMT_STD_DATA).format(dataRiferimento));
			scFiltro.getVariabile().add(varDataRiferimento);
		}
		
		StringWriter sw = new StringWriter();
		Marshaller lMarshaller = SingletonJAXBContext.getInstance().createMarshaller();
		lMarshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
		lMarshaller.marshal(scFiltro, sw);
		String xmlFiltro = sw.toString();		
		input.setFiltroin(xmlFiltro);
		
		DmpkStatisticheTrovaregolecampionamentoatti store = new DmpkStatisticheTrovaregolecampionamentoatti();
		StoreResultBean<DmpkStatisticheTrovaregolecampionamentoattiBean> output = store.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				log.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		List<RegoleCampionamentoAttiBean> data = new ArrayList<RegoleCampionamentoAttiBean>();
		
		if (output.getResultBean().getResultout() != null) {
			StringReader sr = new StringReader(output.getResultBean().getResultout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if (lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
					RegoleCampionamentoAttiBean bean = new RegoleCampionamentoAttiBean();														
					// 1: Id. del tipo di atto
					// 2: Nome del tipo di atto
					// 3: Cod. tipo atto
					// 4: Descrizione del codice tipo atto
					// 5: Flag di determinazione a contrarre (SI/NO)
					// 6: Id. struttura proponente
					// 7: Codice (nri livelli) struttura proponente
					// 8: Descrizione struttura proponente
					// 9: Codice del range di importo in cui ricade l'atto
					// 10: Percentuale di campionamento
					// 11: Id. della regola di campionamento da cui deriva la %
					// 12: Data e ora da cui decorre la regola
					// 13: Colonne su cui è applicata la regola
					// 14: Flag di tipo atto particolare (SI/NO)
					bean.setIdTipoAtto(v.get(0));
					bean.setTipologiaAtto(v.get(1));
//					if(StringUtils.isNotBlank(v.get(2)) || StringUtils.isNotBlank(v.get(3))) {
//						if(StringUtils.isNotBlank(v.get(2)) && StringUtils.isNotBlank(v.get(3))) {
//							bean.setCodiceAtto(v.get(2) + " - " + v.get(3));	
//						} else if(StringUtils.isNotBlank(v.get(2))) {
//							bean.setCodiceAtto(v.get(2));
//						} else if(StringUtils.isNotBlank(v.get(3))) {
//							bean.setCodiceAtto(v.get(3));
//						}						
//					}
					bean.setFlgDeterminaAContrarre(v.get(4));
//					bean.setIdUoProponente(v.get(5));
//					if(StringUtils.isNotBlank(v.get(6)) || StringUtils.isNotBlank(v.get(7))) {
//						if(StringUtils.isNotBlank(v.get(6)) && StringUtils.isNotBlank(v.get(7))) {
//							bean.setStrutturaProponente(v.get(6) + " - " + v.get(7));	
//						} else if(StringUtils.isNotBlank(v.get(6))) {
//							bean.setStrutturaProponente(v.get(6));
//						} else if(StringUtils.isNotBlank(v.get(7))) {
//							bean.setStrutturaProponente(v.get(7));
//						}						
//					}
					bean.setRangeImporto(v.get(8));				
					bean.setPercentualeCampionamento(v.get(9));
					bean.setIdRegola(v.get(10));
					bean.setDataDecorrenzaRegola(v.get(11) != null ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(11)) : null);
					if(v.size() > 12) {
						if(StringUtils.isNotBlank(v.get(12))) {
							String colonneRegola = v.get(12);
							colonneRegola = colonneRegola.replace("IdDocType", "tipologiaAtto");
//							colonneRegola = colonneRegola.replace("CodiceAtto", "codiceAtto");
							colonneRegola = colonneRegola.replace("FlgCodTipoAttoParticolare", "flgCodiceAttoParticolare");
							colonneRegola = colonneRegola.replace("FlgDetContrarre", "flgDeterminaAContrarre");
//							colonneRegola = colonneRegola.replace("IdUOProponente", "strutturaProponente");
							colonneRegola = colonneRegola.replace("RangeImporto", "rangeImporto");
							bean.setColonneRegola(colonneRegola);
						}	
					}
					bean.setFlgCodiceAttoParticolare(v.get(13));
					data.add(bean);
				}
			}
		}
		
		PaginatorBean<RegoleCampionamentoAttiBean> lPaginatorBean = new PaginatorBean<RegoleCampionamentoAttiBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		
		return lPaginatorBean;
	}

	@Override
	public RegoleCampionamentoAttiBean add(RegoleCampionamentoAttiBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkStatisticheApplicaregolacampionamentoattiBean input = new DmpkStatisticheApplicaregolacampionamentoattiBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		
		SezioneCache scFiltro = new SezioneCache();
		
		if(StringUtils.isNotBlank(bean.getIdTipoAtto())) {
			Variabile varIdTipoAtto = new Variabile();
			varIdTipoAtto.setNome("IdDocType");
			varIdTipoAtto.setValoreSemplice(bean.getIdTipoAtto());
			scFiltro.getVariabile().add(varIdTipoAtto);
		}
		
//		if(StringUtils.isNotBlank(bean.getCodiceAtto())) {
//			Variabile varCodiceAtto = new Variabile();
//			varCodiceAtto.setNome("CodiciAtto");
//			varCodiceAtto.setValoreSemplice(bean.getCodiceAtto());
//			scFiltro.getVariabile().add(varCodiceAtto);
//		}
		
		if(StringUtils.isNotBlank(bean.getFlgCodiceAttoParticolare())) {
			Variabile varFlgCodiceAttoParticolare = new Variabile();
			varFlgCodiceAttoParticolare.setNome("FlgCodTipoAttoParticolare");
			varFlgCodiceAttoParticolare.setValoreSemplice(bean.getFlgCodiceAttoParticolare().equalsIgnoreCase("SI") ? "1" : "0");
			scFiltro.getVariabile().add(varFlgCodiceAttoParticolare);
		}
		
		if(StringUtils.isNotBlank(bean.getFlgDeterminaAContrarre())) {
			Variabile varFlgDeterminaAContrarre = new Variabile();
			varFlgDeterminaAContrarre.setNome("FlgDetContrarre");
			varFlgDeterminaAContrarre.setValoreSemplice(bean.getFlgDeterminaAContrarre().equalsIgnoreCase("SI") ? "1" : "0");
			scFiltro.getVariabile().add(varFlgDeterminaAContrarre);
		}
		
//		if(StringUtils.isNotBlank(bean.getIdUoProponente())) {
//			Variabile varIdUoProponente = new Variabile();
//			varIdUoProponente.setNome("IdUOProponente");
//			if(bean.getIdUoProponente().startsWith("UO")) {
//				varIdUoProponente.setValoreSemplice(bean.getIdUoProponente().substring(2));
//			} else {
//				varIdUoProponente.setValoreSemplice(bean.getIdUoProponente());
//			}			
//			scFiltro.getVariabile().add(varIdUoProponente);
//		}
				
		if(StringUtils.isNotBlank(bean.getRangeImporto())) {
			Variabile varRangeImporto = new Variabile();
			varRangeImporto.setNome("RangeImporto");
			varRangeImporto.setValoreSemplice(bean.getRangeImporto());
			scFiltro.getVariabile().add(varRangeImporto);
		}
		
		StringWriter sw = new StringWriter();
		Marshaller lMarshaller = SingletonJAXBContext.getInstance().createMarshaller();
		lMarshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
		lMarshaller.marshal(scFiltro, sw);
		String xmlFiltro = sw.toString();		
		input.setFiltroin(xmlFiltro);
		
		if(StringUtils.isNotBlank(bean.getPercentualeCampionamento())) {
			input.setPercentualecampionamentoin(new BigDecimal(bean.getPercentualeCampionamento()));
		}
		
		if(bean.getListaGruppiAttiCampionamento() != null) {
			List<GruppoAttiCampionamentoXmlBean> listaGruppiAttiCampionamento = new ArrayList<GruppoAttiCampionamentoXmlBean>();
			for (GruppoAttiCampionamentoBean lGruppoAttiCampionamentoBean : bean.getListaGruppiAttiCampionamento()) {			
				// 1: (obblig.)Id. del tipo di atto
				// 3: (obblig.)Cod. tipo atto
				// 5: (obblig.)Flag di determinazione a contrarre (SI/NO)
				// 6: (obblig.)Id. struttura proponente
				// 9: (obblig.)Codice del range di importo in cui ricade l'atto
				// 11: Id. di eventuale regola di campionamento pre-esistente
				// 12: (obblig. se 11 valorizzata) Flag M/C che indica se Mantenere (=M) e Cessare (=C) eventuale regola pre-esistente
				// 13: (obblig.)Flag di tipo atto particolare (SI/NO)
				GruppoAttiCampionamentoXmlBean lGruppoAttiCampionamentoXmlBean = new GruppoAttiCampionamentoXmlBean();
				lGruppoAttiCampionamentoXmlBean.setIdTipoAtto(lGruppoAttiCampionamentoBean.getIdTipoAtto());
//				lGruppoAttiCampionamentoXmlBean.setCodiceAtto(lGruppoAttiCampionamentoBean.getCodiceAtto());
				lGruppoAttiCampionamentoXmlBean.setFlgDeterminaAContrarre(lGruppoAttiCampionamentoBean.getFlgDeterminaAContrarre());
//				lGruppoAttiCampionamentoXmlBean.setIdUoProponente(lGruppoAttiCampionamentoBean.getIdUoProponente());
				lGruppoAttiCampionamentoXmlBean.setRangeImporto(lGruppoAttiCampionamentoBean.getRangeImporto());
				lGruppoAttiCampionamentoXmlBean.setIdRegola(lGruppoAttiCampionamentoBean.getIdRegola());
				if(lGruppoAttiCampionamentoBean.getFlgMantieniRegolaPreEsistente() != null && lGruppoAttiCampionamentoBean.getFlgMantieniRegolaPreEsistente()) {
					lGruppoAttiCampionamentoXmlBean.setFlgMantieniCessaRegola("M");					
				} else if(lGruppoAttiCampionamentoBean.getFlgCessaRegolaPreEsistente() != null && lGruppoAttiCampionamentoBean.getFlgCessaRegolaPreEsistente()) {
					lGruppoAttiCampionamentoXmlBean.setFlgMantieniCessaRegola("C");					
				} 
				lGruppoAttiCampionamentoXmlBean.setFlgCodiceAttoParticolare(lGruppoAttiCampionamentoBean.getFlgCodiceAttoParticolare());
				listaGruppiAttiCampionamento.add(lGruppoAttiCampionamentoXmlBean);				
			}
			String xmlGruppiAtti = new XmlUtilitySerializer().bindXmlList(listaGruppiAttiCampionamento);
			input.setGruppiattiin(xmlGruppiAtti);
		}
		
		DmpkStatisticheApplicaregolacampionamentoatti store = new DmpkStatisticheApplicaregolacampionamentoatti();
		
		StoreResultBean<DmpkStatisticheApplicaregolacampionamentoattiBean> result = store.execute(getLocale(), loginBean, input);
		
		if (result.isInError()) {
			throw new StoreException(result);
		} 
		
		bean.setIdRegola(result.getResultBean().getIdregolaout());
			
		return bean;
	}
	
	@Override
	public RegoleCampionamentoAttiBean update(RegoleCampionamentoAttiBean bean, RegoleCampionamentoAttiBean oldvalue) throws Exception {
		return bean;
	}
	
	@Override
	public RegoleCampionamentoAttiBean get(RegoleCampionamentoAttiBean bean) throws Exception {
		return bean;
	}

	@Override
	public RegoleCampionamentoAttiBean remove(RegoleCampionamentoAttiBean bean) throws Exception {
		return bean;
	}

}
