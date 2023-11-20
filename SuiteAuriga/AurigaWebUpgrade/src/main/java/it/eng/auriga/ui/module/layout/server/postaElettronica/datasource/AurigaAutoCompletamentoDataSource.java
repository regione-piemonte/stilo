/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailGetpropostainddestBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.AurigaAutoCompletamentoBean;
import it.eng.client.DmpkIntMgoEmailGetpropostainddest;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id = "AurigaAutoCompletamentoDataSource")
public class AurigaAutoCompletamentoDataSource extends AbstractFetchDataSource<AurigaAutoCompletamentoBean> {

	private static Logger log = Logger.getLogger(AurigaAutoCompletamentoDataSource.class);

	@Override
	public PaginatorBean<AurigaAutoCompletamentoBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		String destinatario = getExtraparams().get("destinatario") != null ? getExtraparams().get("destinatario") : "";
		
		String tipoIndirizzo = getExtraparams().get("tipoIndirizzo") != null ? getExtraparams().get("tipoIndirizzo") : "";
		String tipoDestinatario = getExtraparams().get("tipoDestinatario") != null ? getExtraparams().get("tipoDestinatario") : "";
		String idSoggetto = getExtraparams().get("idSoggetto") != null ? getExtraparams().get("idSoggetto") : null;

		List<AurigaAutoCompletamentoBean> data = new ArrayList<AurigaAutoCompletamentoBean>();
		PaginatorBean<AurigaAutoCompletamentoBean> paginatorBean = new PaginatorBean<AurigaAutoCompletamentoBean>();

		DmpkIntMgoEmailGetpropostainddestBean input = new DmpkIntMgoEmailGetpropostainddestBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setDestinatariio(destinatario);
		
		// Tipo indirizzo
		if(StringUtils.isNotBlank(tipoIndirizzo)) {
			if("PEC".equalsIgnoreCase(tipoIndirizzo)) {
				input.setRestringipecpeoin("C");
			} else if("PEO".equalsIgnoreCase(tipoIndirizzo)) {
				input.setRestringipecpeoin("O");
			}
		}
		
		// Tipo destinatario
		if(StringUtils.isNotBlank(tipoDestinatario)) {
			if("I".equalsIgnoreCase(tipoDestinatario)) {
				input.setFlgdestinterniesterniin("I");
			} else if("E".equalsIgnoreCase(tipoDestinatario)) {
				input.setFlgdestinterniesterniin("E");
				input.setIdsoggrubricain(StringUtils.isNotBlank(idSoggetto) ? new BigDecimal(idSoggetto) : null);
			}
		}
		
		DmpkIntMgoEmailGetpropostainddest dmpkIntMgoEmailGetpropostainddest = new DmpkIntMgoEmailGetpropostainddest();
		StoreResultBean<DmpkIntMgoEmailGetpropostainddestBean> output = dmpkIntMgoEmailGetpropostainddest.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				log.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		if (output.getResultBean().getPropostaxmlout() != null) {
			String xmlLista = output.getResultBean().getPropostaxmlout();
			List<AurigaAutoCompletamentoBean> lista = XmlListaUtility.recuperaLista(xmlLista, AurigaAutoCompletamentoBean.class);
			for (AurigaAutoCompletamentoBean item : lista) {
				AurigaAutoCompletamentoBean lAurigaAutoCompletamentoBean = new AurigaAutoCompletamentoBean();
				
				if(item.getFlgMailingList() != null && item.getFlgMailingList() == 0){
					lAurigaAutoCompletamentoBean.setFlgMailingList(0);
					lAurigaAutoCompletamentoBean.setIndirizzoEmail(item.getIndirizzoEmail());
					lAurigaAutoCompletamentoBean.setDescVoceRubrica(item.getDescVoceRubrica());
				} else {
					lAurigaAutoCompletamentoBean.setFlgMailingList(1);
					lAurigaAutoCompletamentoBean.setIndirizzoEmail(item.getIndirizzoEmail());
					lAurigaAutoCompletamentoBean.setDescVoceRubrica(item.getDescVoceRubrica());
				}

				//lAurigaAutoCompletamentoBean.setDestinatario(destinatario);
				lAurigaAutoCompletamentoBean.setTipoIndirizzo(item.getTipoIndirizzo());
				lAurigaAutoCompletamentoBean.setIdVoceRubrica(item.getIdVoceRubrica());
	
				data.add(lAurigaAutoCompletamentoBean);
			}
		}

		paginatorBean.setData(data);
		paginatorBean.setStartRow(0);
		paginatorBean.setEndRow(data.size());
		paginatorBean.setTotalRows(data.size());

		return paginatorBean;
	}
}