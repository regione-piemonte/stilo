/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_toponomastica.bean.DmpkToponomasticaTrovavieBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.toponomastica.datasource.bean.TrovaVieBean;
import it.eng.auriga.ui.module.layout.server.toponomastica.datasource.bean.XmlTrovaVieFilterBean;
import it.eng.client.DmpkToponomasticaTrovavie;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;

/**
 * 
 * @author cristiano
 *
 */
@Datasource(id = "TrovaVieDataSource")
public class TrovaVieDataSource extends AbstractFetchDataSource<TrovaVieBean> {

	private static final Logger log = Logger.getLogger(TrovaVieDataSource.class);

	@Override
	public String getNomeEntita() {
		return "trova_vie";
	};

	@Override
	public PaginatorBean<TrovaVieBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		String colsOrderBy = null;
		String flgDescOrderBy = null;
		Integer numPagina = null;
		Integer numRighePagina = null;

		List<TrovaVieBean> data = new ArrayList<TrovaVieBean>();

		DmpkToponomasticaTrovavieBean input = new DmpkToponomasticaTrovavieBean();
		XmlTrovaVieFilterBean xmlTrovaVieFilterBean = new XmlTrovaVieFilterBean();

		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion crit : criteria.getCriteria()) {
				if ("descrNome".equals(crit.getFieldName())) {
					xmlTrovaVieFilterBean.setDesToponimo(getValueStringaFullTextMista(crit));
					xmlTrovaVieFilterBean.setOperToponimo(getOperatorStringaFullTextMista(crit));
				}
			}
		}
		String codIstatComune = getExtraparams().get("codIstatComune");
		if(StringUtils.isNotBlank(codIstatComune)) {
			xmlTrovaVieFilterBean.setCodIstatComune(codIstatComune);
		}

		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setColorderbyio(colsOrderBy);
		input.setFlgdescorderbyio(flgDescOrderBy);
		input.setFlgsenzapaginazionein(1);
		input.setNropaginaio(numPagina);
		input.setBachsizeio(numRighePagina);
		input.setOverflowlimitin(null);
		input.setFlgsenzatotin(null);

		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		String advancedFilters = lXmlUtilitySerializer.bindXml(xmlTrovaVieFilterBean);
		input.setFiltriio(advancedFilters);

		DmpkToponomasticaTrovavie dmpkToponomasticaTrovavie = new DmpkToponomasticaTrovavie();
		StoreResultBean<DmpkToponomasticaTrovavieBean> output = dmpkToponomasticaTrovavie.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				log.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		if (output.getResultBean().getResultout() != null) {
			StringReader sr = new StringReader(output.getResultBean().getResultout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if (lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
					TrovaVieBean bean = new TrovaVieBean();

					bean.setIdToponimo(v.get(0));
					bean.setDescrNomeToponimo(v.get(1));
					bean.setTipoToponimo(v.get(2));
					bean.setCodiceViarioToponimo(v.get(5));
					bean.setCapVie(v.get(6));
					bean.setZonaVie(v.get(7));

					data.add(bean);
				}
			}
		}

		PaginatorBean<TrovaVieBean> lPaginatorBean = new PaginatorBean<TrovaVieBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());

		return lPaginatorBean;
	}

}
