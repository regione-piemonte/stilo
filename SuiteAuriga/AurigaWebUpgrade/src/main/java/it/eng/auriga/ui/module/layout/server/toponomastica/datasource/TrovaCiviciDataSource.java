/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_toponomastica.bean.DmpkToponomasticaTrovaciviciBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.toponomastica.datasource.bean.TrovaCiviciBean;
import it.eng.auriga.ui.module.layout.server.toponomastica.datasource.bean.XmlTrovaCiviciFilterBean;
import it.eng.client.DmpkToponomasticaTrovacivici;
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

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * 
 * @author cristiano
 *
 */
@Datasource(id = "TrovaCiviciDataSource")
public class TrovaCiviciDataSource extends AbstractFetchDataSource<TrovaCiviciBean> {

	private static final Logger log = Logger.getLogger(TrovaCiviciDataSource.class);

	@Override
	public String getNomeEntita() {
		return "trova_civici";
	};

	@Override
	public PaginatorBean<TrovaCiviciBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		String colsOrderBy = null;
		String flgDescOrderBy = null;
		Integer numPagina = null;
		Integer numRighePagina = null;

		List<TrovaCiviciBean> data = new ArrayList<TrovaCiviciBean>();
		DmpkToponomasticaTrovaciviciBean input = new DmpkToponomasticaTrovaciviciBean();
		XmlTrovaCiviciFilterBean xmlTrovaCiviciFilterBean = new XmlTrovaCiviciFilterBean();

		String codToponimo = StringUtils.isNotBlank(getExtraparams().get("codToponimo")) ? getExtraparams().get("codToponimo") : "";
		xmlTrovaCiviciFilterBean.setIdToponimo(codToponimo);

		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion crit : criteria.getCriteria()) {
				if ("nrCivico".equals(crit.getFieldName())) {
					String[] nrCivicoF = getNumberFilterValue(crit);

					String nrCivicoDa = nrCivicoF[0] != null ? nrCivicoF[0] : null;
					String nrCivicoA = nrCivicoF[1] != null ? nrCivicoF[1] : null;

					xmlTrovaCiviciFilterBean.setNrCivicoDa(nrCivicoDa);
					xmlTrovaCiviciFilterBean.setNrCivicoA(nrCivicoA);
				} else if ("barrato".equals(crit.getFieldName())) {
					xmlTrovaCiviciFilterBean.setEsponente(getTextFilterValue(crit));
				} else if ("zona".equals(crit.getFieldName())) {
					xmlTrovaCiviciFilterBean.setZona(getTextFilterValue(crit));
				}
			}
		}

		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		String advancedFilters = lXmlUtilitySerializer.bindXml(xmlTrovaCiviciFilterBean);
		input.setFiltriio(advancedFilters);

		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setColorderbyio(colsOrderBy);
		input.setFlgdescorderbyio(flgDescOrderBy);
		input.setFlgsenzapaginazionein(1);
		input.setNropaginaio(numPagina);
		input.setBachsizeio(numRighePagina);
		input.setOverflowlimitin(null);
		input.setFlgsenzatotin(null);

		DmpkToponomasticaTrovacivici dmpkToponomasticaTrovacivici = new DmpkToponomasticaTrovacivici();
		StoreResultBean<DmpkToponomasticaTrovaciviciBean> output = dmpkToponomasticaTrovacivici.execute(getLocale(), loginBean, input);
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
					TrovaCiviciBean bean = new TrovaCiviciBean();

					bean.setIdCivico(v.get(0));
					bean.setCivico(v.get(1));
					bean.setNrCivico(v.get(2) != null ? new Integer(v.get(2)) : null);
					bean.setEsponenteBarrato(v.get(3));
					bean.setCap(v.get(6));
					bean.setZona(v.get(7));

					data.add(bean);
				}
			}
		}

		PaginatorBean<TrovaCiviciBean> lPaginatorBean = new PaginatorBean<TrovaCiviciBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());

		return lPaginatorBean;
	}
}
