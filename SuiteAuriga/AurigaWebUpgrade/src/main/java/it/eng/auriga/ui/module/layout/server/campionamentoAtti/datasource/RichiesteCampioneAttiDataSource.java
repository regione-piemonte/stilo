/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.xml.bind.Marshaller;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_statistiche.bean.DmpkStatisticheCreacampioneattiBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.campionamentoAtti.datasource.bean.RichiestaCampioneAttiBean;
import it.eng.client.DmpkStatisticheCreacampioneatti;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;

/**
 * 
 * @author matzanin
 *
 */

@Datasource(id = "RichiesteCampioneAttiDataSource")
public class RichiesteCampioneAttiDataSource extends AbstractFetchDataSource<RichiestaCampioneAttiBean> {

	private static final Logger log = Logger.getLogger(RichiesteCampioneAttiDataSource.class);

	@Override
	public String getNomeEntita() {
		return "richiesteCampioneAtti";
	}

	@Override
	public PaginatorBean<RichiestaCampioneAttiBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
	
		//TODO
		
		return null;
	}

	@Override
	public RichiestaCampioneAttiBean add(RichiestaCampioneAttiBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkStatisticheCreacampioneattiBean input = new DmpkStatisticheCreacampioneattiBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		
		SezioneCache scFiltro = new SezioneCache();
		
		if(StringUtils.isNotBlank(bean.getIdTipoAtto())) {
			Variabile varIdTipoAtto = new Variabile();
			varIdTipoAtto.setNome("IdDocType");
			varIdTipoAtto.setValoreSemplice(bean.getIdTipoAtto());
			scFiltro.getVariabile().add(varIdTipoAtto);
		}
		
		if(StringUtils.isNotBlank(bean.getCodiceAtto())) {
			Variabile varCodiceAtto = new Variabile();
			varCodiceAtto.setNome("CodiciAtto");
			varCodiceAtto.setValoreSemplice(bean.getCodiceAtto());
			scFiltro.getVariabile().add(varCodiceAtto);
		}
		
		if(StringUtils.isNotBlank(bean.getFlgDeterminaAContrarre())) {
			Variabile varFlgDeterminaAContrarre = new Variabile();
			varFlgDeterminaAContrarre.setNome("FlgDetContrarre");
			varFlgDeterminaAContrarre.setValoreSemplice(bean.getFlgDeterminaAContrarre().equalsIgnoreCase("SI") ? "1" : "0");
			scFiltro.getVariabile().add(varFlgDeterminaAContrarre);
		}
		
		if(StringUtils.isNotBlank(bean.getIdUoProponente())) {
			Variabile varIdUoProponente = new Variabile();
			varIdUoProponente.setNome("IdUOProponente");
			if(bean.getIdUoProponente().startsWith("UO")) {
				varIdUoProponente.setValoreSemplice(bean.getIdUoProponente().substring(2));
			} else {
				varIdUoProponente.setValoreSemplice(bean.getIdUoProponente());
			}
			scFiltro.getVariabile().add(varIdUoProponente);
		}
				
		if(StringUtils.isNotBlank(bean.getRangeImporto())) {
			Variabile varRangeImporto = new Variabile();
			varRangeImporto.setNome("RangeImporto");
			varRangeImporto.setValoreSemplice(bean.getRangeImporto());
			scFiltro.getVariabile().add(varRangeImporto);
		}
				
		if(bean.getDataInizioPeriodoRif() != null) {
			Variabile varDataInizioPeriodoRif = new Variabile();
			varDataInizioPeriodoRif.setNome("DataInizioPeriodoRif");
			varDataInizioPeriodoRif.setValoreSemplice(new SimpleDateFormat(AbstractDataSource.FMT_STD_DATA).format(bean.getDataInizioPeriodoRif()));
			scFiltro.getVariabile().add(varDataInizioPeriodoRif);
		}
		
		if(bean.getDataFinePeriodoRif() != null) {
			Variabile varDataFinePeriodoRif = new Variabile();
			varDataFinePeriodoRif.setNome("DataFinePeriodoRif");
			varDataFinePeriodoRif.setValoreSemplice(new SimpleDateFormat(AbstractDataSource.FMT_STD_DATA).format(bean.getDataFinePeriodoRif()));
			scFiltro.getVariabile().add(varDataFinePeriodoRif);
		}
		
		StringWriter sw = new StringWriter();
		Marshaller lMarshaller = SingletonJAXBContext.getInstance().createMarshaller();
		lMarshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
		lMarshaller.marshal(scFiltro, sw);
		String xmlFiltro = sw.toString();		
		input.setFiltroin(xmlFiltro);
		
		DmpkStatisticheCreacampioneatti store = new DmpkStatisticheCreacampioneatti();
		
		StoreResultBean<DmpkStatisticheCreacampioneattiBean> result = store.execute(getLocale(), loginBean, input);
		
		if (result.isInError()) {
			throw new StoreException(result);
		} 
		
		bean.setIdCampione(result.getResultBean().getIdcampioneout());
		bean.setNroAttiCampione((result.getResultBean().getNroatticampioneout() != null) ? String.valueOf(result.getResultBean().getNroatticampioneout().longValue()) : null);
			
		return bean;
	}

	@Override
	public RichiestaCampioneAttiBean update(RichiestaCampioneAttiBean bean, RichiestaCampioneAttiBean oldvalue) throws Exception {
		return bean;
	}
	
	@Override
	public RichiestaCampioneAttiBean get(RichiestaCampioneAttiBean bean) throws Exception {
		return bean;
	}

	@Override
	public RichiestaCampioneAttiBean remove(RichiestaCampioneAttiBean bean) throws Exception {
		return bean;
	}

}
