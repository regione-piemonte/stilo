/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.iterAtti.datasource.bean.ImpostaFilePrimarioBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ModelloXTipoDocBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

import java.util.List;

import org.apache.log4j.Logger;

/**
 * Recupera il modello da utilizzare come file primario per l'istanza di processo di cui è stato passato l'id
 * 
 * @author massimo malvestio
 *
 */
@Datasource(id = "GeneraFilePrimarioAttiDatasource")
public class GeneraFilePrimarioAttiDatasource extends AbstractServiceDataSource<ImpostaFilePrimarioBean, ImpostaFilePrimarioBean> {

	private static Logger logger = Logger.getLogger(GeneraFilePrimarioAttiDatasource.class);

	@Override
	public ImpostaFilePrimarioBean call(ImpostaFilePrimarioBean input) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String idUserLavoro = loginBean.getIdUserLavoro() != null ? loginBean.getIdUserLavoro() : "";

		DmpkLoadComboDmfn_load_comboBean bean = new DmpkLoadComboDmfn_load_comboBean();

		bean.setCodidconnectiontokenin(loginBean.getToken());
		bean.setTipocomboin("MODELLO_X_TIPO_DOC");
		bean.setAltriparametriin("ID_USER_LAVORO|*|" + idUserLavoro + "|*|ID_PROCESS|*|" + input.getIdProcess() + "|*|ID_DOC_TYPE|*|" + input.getIdDocType());

		DmpkLoadComboDmfn_load_combo store = new DmpkLoadComboDmfn_load_combo();

		ImpostaFilePrimarioBean retValue = new ImpostaFilePrimarioBean();

		try {

			// recupero il documento di modello da utilizzare come file primario
			StoreResultBean<DmpkLoadComboDmfn_load_comboBean> result = store.execute(getLocale(), loginBean, bean);

			String xmlOut = result.getResultBean().getListaxmlout();

			// l'xml non contiene dati validi, devo ritornare il messaggio di errore
			if (xmlOut != null && !xmlOut.isEmpty()) {

				List<ModelloXTipoDocBean> values = XmlListaUtility.recuperaLista(xmlOut, ModelloXTipoDocBean.class);

				if (values != null && values.size() > 0) {

					retValue.setEsito(true);

					String uriModello = values.get(0).getUriFileModello();
					String nomeModello = values.get(0).getNomeModello();

					retValue.setUriFilePrimario(uriModello);
					retValue.setNomeFilePrimario(nomeModello.replaceAll(" ", "_"));

				} else {

					retValue.setEsito(false);

					String message = "Non è stato trovato alcun modello utilizzando i dati impostati dall'utente";

					retValue.setError(message);

					message = message
							+ String.format(" Parametri di lancio idUserLavoro; %1$s, tipoComboIN: MODELLO_X_TIPO_DOC, idProcess: %2$s", idUserLavoro,
									input.getIdProcess());

					logger.error(message);
				}

			} else {

				retValue.setEsito(false);

				String message = "Non è stato trovato alcun modello utilizzando i dati impostati dall'utente";

				retValue.setError(message);

				message = message
						+ String.format(" Parametri di lancio idUserLavoro; %1$s, tipoComboIN: MODELLO_X_TIPO_DOC, idProcess: %2$s, eccezione ", idUserLavoro,
								input.getIdProcess());

				logger.error(message);
			}

		} catch (Exception e) {

			logger.error("Si è verificata la seguente eccezione mentre si tentava di recuperare il file primario");

			String message = String.format("Parametri di lancio idUserLavoro; %1$s, tipoComboIN: MODELLO_X_TIPO_DOC, idProcess: %2$s, eccezione ",
					idUserLavoro, input.getIdProcess());

			logger.error(message, e);

			retValue.setError("Si è verificata un'eccezione durante la configurazione del file primario, verificare i log");
			retValue.setEsito(false);
		}

		return retValue;
	}

}
