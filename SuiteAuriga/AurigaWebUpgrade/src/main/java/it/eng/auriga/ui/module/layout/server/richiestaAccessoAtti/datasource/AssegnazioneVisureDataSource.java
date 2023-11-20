/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesSetsogginterniBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.procedimentiInIter.bean.IstruttoreProcBean;
import it.eng.auriga.ui.module.layout.server.richiestaAccessoAtti.datasource.bean.AssegnazioneVisureBean;
import it.eng.auriga.ui.module.layout.server.richiestaAccessoAtti.datasource.bean.SoggettoAssegnazioneVisureBean;
import it.eng.auriga.ui.module.layout.server.richiestaAccessoAtti.datasource.bean.VisureBean;
import it.eng.client.DmpkProcessesSetsogginterni;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;

/**
 * 
 * @author DANCRIST
 *
 */

@Datasource(id = "AssegnazioneVisureDataSource")
public class AssegnazioneVisureDataSource extends AbstractDataSource<AssegnazioneVisureBean, AssegnazioneVisureBean> {

	@Override
	public AssegnazioneVisureBean add(AssegnazioneVisureBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		HashMap<String, String> errorMessages = null;

		for (VisureBean proc : bean.getListaRecord()) {

			DmpkProcessesSetsogginterniBean input = new DmpkProcessesSetsogginterniBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);

			if (proc.getIdProcedimento() != null) {
				input.setIdprocessin(new BigDecimal(proc.getIdProcedimento()));
			}

			input.setRuolosoggettiin("ISTRUTTORE");

			input.setListaxmlin(getXmlAssegnazione(bean));

			DmpkProcessesSetsogginterni dmpkProcessesSetsogginterni = new DmpkProcessesSetsogginterni();
			StoreResultBean<DmpkProcessesSetsogginterniBean> output = dmpkProcessesSetsogginterni.execute(getLocale(),
					loginBean, input);

			if (output.getDefaultMessage() != null) {
				if (errorMessages == null)
					errorMessages = new HashMap<String, String>();
				errorMessages.put(proc.getIdProcedimento(), output.getDefaultMessage());
			}
		}

		if (errorMessages != null) {
			bean.setErrorMessages(errorMessages);
		}

		return bean;
	}

	public String getXmlAssegnazione(AssegnazioneVisureBean bean) throws Exception {
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		return lXmlUtilitySerializer.bindXmlList(getListaAssegnazione(bean));
	}

	private List<SoggettoAssegnazioneVisureBean> getListaAssegnazione(AssegnazioneVisureBean bean) throws Exception {
		List<SoggettoAssegnazioneVisureBean> listaAssegnazione = new ArrayList<SoggettoAssegnazioneVisureBean>();
		if (bean.getListaAssegnazione() != null) {
			for (IstruttoreProcBean assegnatarioBean : bean.getListaAssegnazione()) {
				SoggettoAssegnazioneVisureBean lSoggettoAssegnazioneVisureBean = new SoggettoAssegnazioneVisureBean();
				String tipoIstruttoreProc = assegnatarioBean.getTipoIstruttoreProc();
				if (tipoIstruttoreProc != null && "UT".equals(tipoIstruttoreProc)) {
					lSoggettoAssegnazioneVisureBean.setFlgUoSvUt("UT");
					lSoggettoAssegnazioneVisureBean.setIdUoSvUt(assegnatarioBean.getIdUtenteIstruttoreProc());
				}
				if (tipoIstruttoreProc != null && "LD".equals(tipoIstruttoreProc)) {
					lSoggettoAssegnazioneVisureBean.setFlgUoSvUt("G");
					lSoggettoAssegnazioneVisureBean.setIdUoSvUt(assegnatarioBean.getIdGruppoIstruttoreProc());
				}
				listaAssegnazione.add(lSoggettoAssegnazioneVisureBean);
			}
		}
		return listaAssegnazione;
	}

	@Override
	public AssegnazioneVisureBean get(AssegnazioneVisureBean bean) throws Exception {
		return null;
	}

	@Override
	public AssegnazioneVisureBean remove(AssegnazioneVisureBean bean) throws Exception {
		return null;
	}

	@Override
	public AssegnazioneVisureBean update(AssegnazioneVisureBean bean, AssegnazioneVisureBean oldvalue)
			throws Exception {
		return bean;
	}

	@Override
	public PaginatorBean<AssegnazioneVisureBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow,
			List<OrderByBean> orderby) throws Exception {
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(AssegnazioneVisureBean bean) throws Exception {
		return null;
	}

}