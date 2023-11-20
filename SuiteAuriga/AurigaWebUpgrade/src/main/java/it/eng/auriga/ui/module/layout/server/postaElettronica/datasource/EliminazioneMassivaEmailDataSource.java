/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailEliminaemailBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.EliminazioneEmailBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.PostaElettronicaBean;
import it.eng.client.DmpkIntMgoEmailEliminaemail;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.XmlUtility;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.AurigaUserUtil;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

@Datasource(id = "EliminazioneMassivaEmailDataSource")
public class EliminazioneMassivaEmailDataSource extends AbstractDataSource<EliminazioneEmailBean, EliminazioneEmailBean> {

	private static Logger mLogger = Logger.getLogger(EliminazioneMassivaEmailDataSource.class);

	@Override
	public EliminazioneEmailBean add(EliminazioneEmailBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		HashMap<String, String> errorMessages = new HashMap<String, String>();

		for (PostaElettronicaBean email : bean.getListaRecord()) {

			if (email.getStatoLavorazione() != null && email.getStatoLavorazione().equalsIgnoreCase("lavorazione conclusa")) {
				if (errorMessages == null) {
					errorMessages = new HashMap<String, String>();
					errorMessages.put(email.getIdEmail(), "E-mail già archiviata: operazione non consentita");
				}
			} else {

				DmpkIntMgoEmailEliminaemailBean input = new DmpkIntMgoEmailEliminaemailBean();
				input.setCodidconnectiontokenin(token);
				input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
				input.setIdemailin(email.getIdEmail());
				input.setFlgforzaeliminazionefisicain(null);

				DmpkIntMgoEmailEliminaemail dmpkIntMgoEmailEliminaemail = new DmpkIntMgoEmailEliminaemail();
				StoreResultBean<DmpkIntMgoEmailEliminaemailBean> output = dmpkIntMgoEmailEliminaemail.execute(getLocale(), loginBean, input);

				if (output.isInError()) {
					if (errorMessages == null) {
						errorMessages = new HashMap<String, String>();
					}
					if (StringUtils.isNotBlank(output.getDefaultMessage())) {
						mLogger.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
						errorMessages.put(email.getIdEmail(), output.getDefaultMessage());
					} else {
						errorMessages.put(email.getIdEmail(), "Errore generico");
					}
				} else if (output.getResultBean().getUrifiledaeliminareout() != null) {
					StringReader sr = new StringReader(output.getResultBean().getUrifiledaeliminareout());
					Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
					if (lista != null) {
						for (int i = 0; i < lista.getRiga().size(); i++) {
							Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
							String uriDaEliminare = v.get(0);
							// TODO STORAGE UTIL ELIMINA EMAIL
							StorageImplementation.getStorage().delete(uriDaEliminare);
						}
					}
				}
			}
		}
		bean.setErrorMessages(errorMessages);

		return bean;
	}

	@Override
	public EliminazioneEmailBean get(EliminazioneEmailBean bean) throws Exception {
		
		return null;
	}

	@Override
	public EliminazioneEmailBean remove(EliminazioneEmailBean bean) throws Exception {
		
		return null;
	}

	@Override
	public EliminazioneEmailBean update(EliminazioneEmailBean bean, EliminazioneEmailBean oldvalue) throws Exception {
		
		return null;
	}

	@Override
	public PaginatorBean<EliminazioneEmailBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(EliminazioneEmailBean bean) throws Exception {
		
		return null;
	}
}
