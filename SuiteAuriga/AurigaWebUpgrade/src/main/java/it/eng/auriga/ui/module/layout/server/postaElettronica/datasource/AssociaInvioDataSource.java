/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailColleganotificaBean;
import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.AssociaInvioBean;
import it.eng.client.DmpkIntMgoEmailColleganotifica;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

@Datasource(id = "AssociaInvioDataSource")
public class AssociaInvioDataSource extends AbstractDataSource<AssociaInvioBean, AssociaInvioBean> {

	private static Logger mLogger = Logger.getLogger(AssociaInvioDataSource.class);

	@Override
	public AssociaInvioBean get(AssociaInvioBean bean) throws Exception {
		
		return null;
	}

	@Override
	public AssociaInvioBean add(AssociaInvioBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		DmpkIntMgoEmailColleganotificaBean input = new DmpkIntMgoEmailColleganotificaBean();

		input.setCodidconnectiontokenin(loginBean.getToken());
		input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
		input.setIdemailricevutain(bean.getIdEmailRicevuta());
		input.setIddestemailinviatain(bean.getIdDestinatario());
		input.setNotein(bean.getNota());

		DmpkIntMgoEmailColleganotifica collega = new DmpkIntMgoEmailColleganotifica();

		StoreResultBean<DmpkIntMgoEmailColleganotificaBean> output = collega.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				mLogger.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		return bean;
	}

	@Override
	public AssociaInvioBean remove(AssociaInvioBean bean) throws Exception {
		
		return null;
	}

	@Override
	public AssociaInvioBean update(AssociaInvioBean bean, AssociaInvioBean oldvalue) throws Exception {
		
		return null;
	}

	@Override
	// POLOLO LA COMBO DEGLI INDIRIZZI
	public PaginatorBean<AssociaInvioBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		String tipoDocumento = getExtraparams().get("estremiDocumentoInviato");
		String anno = getExtraparams().get("anno");
		String numero = getExtraparams().get("numero");

		List<AssociaInvioBean> resultList = new ArrayList<AssociaInvioBean>();

		if ((anno != null && !anno.equals("")) && (numero != null && !numero.equals(""))) {
			String indirizzoDestinatario = "";
			if (criteria != null && criteria.getCriteria() != null) {
				for (Criterion criterion : criteria.getCriteria()) {
					if (criterion.getFieldName().equals("indirizzoDestinatario")) {
						indirizzoDestinatario = (String) criterion.getValue();
						break;
					}
				}
			}
			// Input
			DmpkLoadComboDmfn_load_comboBean inputParamComboBean = new DmpkLoadComboDmfn_load_comboBean();
			inputParamComboBean.setTipocomboin("DEST_INVIO_UD_EMAIL");

			if (tipoDocumento.equals("PG"))
				inputParamComboBean.setAltriparametriin("COD_CATEGORIA_REG_UD|*|PG|*|ANNO_REG_UD|*|" + anno + "|*|NUM_REG_UD|*|" + numero
						+ "|*|STR_IN_IND_DEST|*|" + indirizzoDestinatario);

			else if (tipoDocumento.equals("PI"))
				inputParamComboBean.setAltriparametriin("COD_CATEGORIA_REG_UD|*|A|*|SIGLA_REG_UD|*|P.I.|*|ANNO_REG_UD|*|" + anno + "|*|NUM_REG_UD|*|" + numero
						+ "|*|STR_IN_IND_DEST|*|" + indirizzoDestinatario);
			else if (tipoDocumento.equals("NI"))
				inputParamComboBean.setAltriparametriin("COD_CATEGORIA_REG_UD|*|A|*|SIGLA_REG_UD|*|N.I.|*|ANNO_REG_UD|*|" + anno + "|*|NUM_REG_UD|*|" + numero
						+ "|*|STR_IN_IND_DEST|*|" + indirizzoDestinatario);

			inputParamComboBean.setFlgsolovldin(null);

			// Output
			DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
			StoreResultBean<DmpkLoadComboDmfn_load_comboBean> storeResult = lDmpkLoadComboDmfn_load_combo.execute(getLocale(),
					AurigaUserUtil.getLoginInfo(getSession()), inputParamComboBean);

			String xmlLista = storeResult.getResultBean().getListaxmlout();

			if (xmlLista != null && !xmlLista.equals("")) {

				List<AssociaInvioBean> listaXML = XmlListaUtility.recuperaLista(xmlLista, AssociaInvioBean.class);
				resultList = new ArrayList<AssociaInvioBean>();

				for (AssociaInvioBean associaInvioBean : listaXML) {

					AssociaInvioBean associaInvioBeanTmp = new AssociaInvioBean();

					associaInvioBeanTmp.setDataInvio(associaInvioBean.getDataInvio());
					associaInvioBeanTmp.setIdDestinatario(associaInvioBean.getIdDestinatario());
					associaInvioBeanTmp.setIndirizzoDestinatario(associaInvioBean.getIndirizzoDestinatario());

					resultList.add(associaInvioBeanTmp);
				}
			}
		}

		PaginatorBean<AssociaInvioBean> lPaginatorBean = new PaginatorBean<AssociaInvioBean>();
		lPaginatorBean.setData(resultList);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(resultList.size());
		lPaginatorBean.setTotalRows(resultList.size());

		return lPaginatorBean;
	}

	@Override
	public Map<String, ErrorBean> validate(AssociaInvioBean bean) throws Exception {
		
		return null;
	}

}
