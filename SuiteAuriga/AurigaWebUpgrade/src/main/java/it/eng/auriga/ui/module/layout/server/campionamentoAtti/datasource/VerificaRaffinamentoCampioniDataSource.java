/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpSession;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_statistiche.bean.DmpkStatisticheRaffinacampattictrlregammBean;
import it.eng.auriga.database.store.dmpk_statistiche.bean.DmpkStatisticheVerificacampioniattictrlregammBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.campionamentoAtti.datasource.bean.OperazioneMassivaVerificaRaffinamentoCampioniBean;
import it.eng.auriga.ui.module.layout.server.campionamentoAtti.datasource.bean.VerificaRaffinamentoCampioniBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.SimpleValueBean;
import it.eng.client.DmpkStatisticheRaffinacampattictrlregamm;
import it.eng.client.DmpkStatisticheVerificacampioniattictrlregamm;
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

@Datasource(id = "VerificaRaffinamentoCampioniDataSource")
public class VerificaRaffinamentoCampioniDataSource extends AbstractFetchDataSource<VerificaRaffinamentoCampioniBean> {

	private static final Logger log = Logger.getLogger(VerificaRaffinamentoCampioniDataSource.class);

	@Override
	public String getNomeEntita() {
		return "verificaRaffinamentoCampioni";
	}

	@Override
	public PaginatorBean<VerificaRaffinamentoCampioniBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
	
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		Date dataInizioPeriodoVerifica = null;
		Date dataFinePeriodoVerifica = null;
		String tipoRaggruppamento = null;
		String nroSogliaAttiAdottati = null;
		String sogliaImporto = null;
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion crit : criteria.getCriteria()) {
				if("periodoVerifica".equals(crit.getFieldName())) {
					Date[] estremiPeriodoVerifica = getDateFilterValue(crit);
					dataInizioPeriodoVerifica = estremiPeriodoVerifica[0];
					dataFinePeriodoVerifica = estremiPeriodoVerifica[1];
				} 
				else if ("tipoRaggruppamento".equals(crit.getFieldName())) {
					tipoRaggruppamento = getTextFilterValue(crit);
				} 
				else if ("nroSogliaAttiAdottati".equals(crit.getFieldName())) {
					nroSogliaAttiAdottati = getTextFilterValue(crit);
				} 
				else if ("sogliaImporto".equals(crit.getFieldName())) {
					sogliaImporto = getTextFilterValue(crit);
				}
			}
		}
		
		DmpkStatisticheVerificacampioniattictrlregammBean input = new DmpkStatisticheVerificacampioniattictrlregammBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setTiporaggruppamentoin(tipoRaggruppamento);
		
		SezioneCache scFiltro = new SezioneCache();
		
		if(dataInizioPeriodoVerifica != null) {
			Variabile varDataInizioPeriodoVerifica = new Variabile();
			varDataInizioPeriodoVerifica.setNome("DataInizioPeriodoVerifica");
			varDataInizioPeriodoVerifica.setValoreSemplice(new SimpleDateFormat(AbstractDataSource.FMT_STD_DATA).format(dataInizioPeriodoVerifica));
			scFiltro.getVariabile().add(varDataInizioPeriodoVerifica);
		}
		
		if(dataFinePeriodoVerifica != null) {
			Variabile varDataFinePeriodoVerifica = new Variabile();
			varDataFinePeriodoVerifica.setNome("DataFinePeriodoVerifica");
			varDataFinePeriodoVerifica.setValoreSemplice(new SimpleDateFormat(AbstractDataSource.FMT_STD_DATA).format(dataFinePeriodoVerifica));
			scFiltro.getVariabile().add(varDataFinePeriodoVerifica);
		}
		
		if(StringUtils.isNotBlank(nroSogliaAttiAdottati)) {
			Variabile varNumeroSogliaAttiAdottati = new Variabile();
			varNumeroSogliaAttiAdottati.setNome("NumeroSogliaAttiAdottati");
			varNumeroSogliaAttiAdottati.setValoreSemplice(nroSogliaAttiAdottati);
			scFiltro.getVariabile().add(varNumeroSogliaAttiAdottati);
		}
		
		if(StringUtils.isNotBlank(sogliaImporto)) {
			Variabile varSogliaImporto = new Variabile();
			varSogliaImporto.setNome("SogliaImporto");
			varSogliaImporto.setValoreSemplice(sogliaImporto);
			scFiltro.getVariabile().add(varSogliaImporto);
		}
		
		StringWriter sw = new StringWriter();
		Marshaller lMarshaller = SingletonJAXBContext.getInstance().createMarshaller();
		lMarshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
		lMarshaller.marshal(scFiltro, sw);
		String xmlFiltro = sw.toString();		
		input.setFiltroin(xmlFiltro);
				
		DmpkStatisticheVerificacampioniattictrlregamm store = new DmpkStatisticheVerificacampioniattictrlregamm();
		StoreResultBean<DmpkStatisticheVerificacampioniattictrlregammBean> output = store.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				log.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		List<VerificaRaffinamentoCampioniBean> data = new ArrayList<VerificaRaffinamentoCampioniBean>();
		
		if (output.getResultBean().getGruppiattiout() != null) {
			StringReader sr = new StringReader(output.getResultBean().getGruppiattiout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if (lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
					VerificaRaffinamentoCampioniBean bean = new VerificaRaffinamentoCampioniBean();														
					bean.setCodiceAttoStruttura(v.get(0));
					if(StringUtils.isNotBlank(v.get(0)) || StringUtils.isNotBlank(v.get(1))) {
						if(StringUtils.isNotBlank(v.get(0)) && StringUtils.isNotBlank(v.get(1))) {
							bean.setDesCodiceAttoStruttura(v.get(0) + " - " + v.get(1));	
						} else if(StringUtils.isNotBlank(v.get(0))) {
							bean.setDesCodiceAttoStruttura(v.get(0));
						} else if(StringUtils.isNotBlank(v.get(1))) {
							bean.setDesCodiceAttoStruttura(v.get(1));
						}						
					}
					bean.setIdUoProponente(v.get(2));					
					bean.setNumeroAttiAdottati(v.get(3));
					bean.setPercentualeCampionamento(v.get(4));					
					bean.setFlgCodiceAttoParticolare(v.get(5));
					bean.setNumeroAttiEstrattiXControllo(v.get(6));
					data.add(bean);
				}
			}
		}
		
		PaginatorBean<VerificaRaffinamentoCampioniBean> lPaginatorBean = new PaginatorBean<VerificaRaffinamentoCampioniBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		
		return lPaginatorBean;
	}
	
	public OperazioneMassivaVerificaRaffinamentoCampioniBean nuovoCampionamento(OperazioneMassivaVerificaRaffinamentoCampioniBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo((HttpSession) this.getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		if(bean.getTipoRaggruppamento() != null) {
			
			DmpkStatisticheRaffinacampattictrlregammBean input = new DmpkStatisticheRaffinacampattictrlregammBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			input.setTiporaggruppamentoin(bean.getTipoRaggruppamento());			
			if(StringUtils.isNotBlank(bean.getPercentualeCampionamento())) {
				input.setPercentualein(new BigDecimal(bean.getPercentualeCampionamento()));
			}
			
			SezioneCache scFiltro = new SezioneCache();
			
			if(bean.getDataInizioPeriodoVerifica() != null) {
				Variabile varDataInizioPeriodoRif = new Variabile();
				varDataInizioPeriodoRif.setNome("DataInizioPeriodoRif");
				varDataInizioPeriodoRif.setValoreSemplice(new SimpleDateFormat(AbstractDataSource.FMT_STD_DATA).format(bean.getDataInizioPeriodoVerifica()));
				scFiltro.getVariabile().add(varDataInizioPeriodoRif);
			}
			
			if(bean.getDataFinePeriodoVerifica() != null) {
				Variabile varDataFinePeriodoRif = new Variabile();
				varDataFinePeriodoRif.setNome("DataFinePeriodoRif");
				varDataFinePeriodoRif.setValoreSemplice(new SimpleDateFormat(AbstractDataSource.FMT_STD_DATA).format(bean.getDataFinePeriodoVerifica()));
				scFiltro.getVariabile().add(varDataFinePeriodoRif);
			}
			
			StringWriter sw = new StringWriter();
			Marshaller lMarshaller = SingletonJAXBContext.getInstance().createMarshaller();
			lMarshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
			lMarshaller.marshal(scFiltro, sw);
			String xmlFiltro = sw.toString();		
			input.setFiltroin(xmlFiltro);
						
			List<SimpleValueBean> listaGruppiAtti = new ArrayList<SimpleValueBean>();
			if(bean.getListaRecord() != null) {
				for(int i = 0; i < bean.getListaRecord().size(); i++) {
					VerificaRaffinamentoCampioniBean lVerificaRaffinamentoCampioniBean = (VerificaRaffinamentoCampioniBean) bean.getListaRecord().get(i);				
					SimpleValueBean lSimpleValueBean = new SimpleValueBean();			
					if("CODICE_ATTO".equals(bean.getTipoRaggruppamento())) {
						lSimpleValueBean.setValue(lVerificaRaffinamentoCampioniBean.getCodiceAttoStruttura());
					} else if("STRUTTURA".equals(bean.getTipoRaggruppamento())) {
						lSimpleValueBean.setValue(lVerificaRaffinamentoCampioniBean.getIdUoProponente());
					}								
					listaGruppiAtti.add(lSimpleValueBean);				
				}								
			}
			input.setGruppiattiin(new XmlUtilitySerializer().bindXmlList(listaGruppiAtti));	
			
			DmpkStatisticheRaffinacampattictrlregamm store = new DmpkStatisticheRaffinacampattictrlregamm();
			
			StoreResultBean<DmpkStatisticheRaffinacampattictrlregammBean> output = store.execute(getLocale(), loginBean, input);
			
			if (output.isInError()) {
				throw new StoreException(output);
			} 		
			
		}
		
		return bean;		
	}

	@Override
	public VerificaRaffinamentoCampioniBean add(VerificaRaffinamentoCampioniBean bean) throws Exception {
		return bean;
	}
	
	@Override
	public VerificaRaffinamentoCampioniBean update(VerificaRaffinamentoCampioniBean bean, VerificaRaffinamentoCampioniBean oldvalue) throws Exception {
		return bean;
	}
	
	@Override
	public VerificaRaffinamentoCampioniBean get(VerificaRaffinamentoCampioniBean bean) throws Exception {
		return bean;
	}

	@Override
	public VerificaRaffinamentoCampioniBean remove(VerificaRaffinamentoCampioniBean bean) throws Exception {
		return bean;
	}

}
