/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.AzioneRapidaBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.DestinatarioPreferitoBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.LoadDestinatariPreferitiBean;
import it.eng.auriga.ui.module.layout.shared.util.AzioniRapide;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id = "LoadComboDestinatariPreferiti")
public class LoadComboDestinatariPreferiti
		extends AbstractDataSource<LoadDestinatariPreferitiBean, LoadDestinatariPreferitiBean> {

	private static Logger mLogger = Logger.getLogger(LoadComboDestinatariPreferiti.class);

	/**
	 * Restituisce una lista di destinatari preferiti associati all'utente
	 * (autenticato o in delega se presente), in base al tipo di azioni
	 * richieste
	 * 
	 * @param pLoadDestinatariPreferitiBean:
	 *            bean dove sono indicate le tipologie di azioni per cui
	 *            recuperare la lista di preferiti relativa
	 * @return
	 */

	public LoadDestinatariPreferitiBean getDestinatariPreferitiUtente(LoadDestinatariPreferitiBean bean) {

		LoadDestinatariPreferitiBean result = new LoadDestinatariPreferitiBean();
		result.setSuccess(false);

		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() != null
				? AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() : "";

		List<AzioneRapidaBean> listaAzioniDaFare = bean.getListaAzioniRapide();
		HashMap<String , List<DestinatarioPreferitoBean>> mappaUOPreferite = new HashMap<String , List<DestinatarioPreferitoBean>>();
		HashMap<String , List<DestinatarioPreferitoBean>> mappaUtentiPreferiti = new HashMap<String , List<DestinatarioPreferitoBean>>();
		
		try {

			// Itera sulle azioni da fare
			for (AzioneRapidaBean azioneDaFare : listaAzioniDaFare) {

				// selezionare la finalità
				String finalita = "";

				if (azioneDaFare.getAzioneRapida().equals(AzioniRapide.METTI_IN_CARICO.getValue())
						|| azioneDaFare.getAzioneRapida().equals(AzioniRapide.MANDA_IN_APPROVAZIONE.getValue())) {
					finalita = "SET_USER_IN_CARICO_MAIL";
				} else if (azioneDaFare.getAzioneRapida().equals(AzioniRapide.ASSEGNA_UO_COMPETENTE.getValue())) {
					finalita = "SET_UO_COMP_EMAIL";
				} else if (azioneDaFare.getAzioneRapida().equals(AzioniRapide.ASSEGNA_DOC.getValue())) {
					finalita = "ASSEGNA_DOC";
				} else if (azioneDaFare.getAzioneRapida().equals(AzioniRapide.ASSEGNA_FOLDER.getValue())) {
					finalita = "ASSEGNA_FOLDER";
				} else if (azioneDaFare.getAzioneRapida().equals(AzioniRapide.INVIO_CC_DOC.getValue())) {
					finalita = "INVIO_CC_DOC";
				} else if (azioneDaFare.getAzioneRapida().equals(AzioniRapide.INVIO_CC_FOLDER.getValue())) {
					finalita = "INVIO_CC_FOLDER";
				} else if (azioneDaFare.getAzioneRapida().equals(AzioniRapide.SMISTA_DOC.getValue())) {
					finalita = "SMISTA_DOC";
				} else if (azioneDaFare.getAzioneRapida().equals(AzioniRapide.SMISTA_FOLDER.getValue())) {
					finalita = "SMISTA_FOLDER";
				} 

				DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
				// Inizializzo l'INPUT
				DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
				lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("DEST_PREFERITI");
				String altriParametri = "ID_USER_LAVORO|*|" + idUserLavoro + "|*|FINALITA|*|" + finalita;
				if(StringUtils.isNotBlank(bean.getIdUd())) {
					altriParametri += "|*|ID_UD|*|" + bean.getIdUd(); 
				}
				lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(altriParametri);
				lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(BigDecimal.ONE);
				StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo
						.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()),
								lDmpkLoadComboDmfn_load_comboBean);

				if (StringUtils.isBlank(lStoreResultBean.getDefaultMessage())) {

					List<DestinatarioPreferitoBean> lista = XmlListaUtility.recuperaLista(
							lStoreResultBean.getResultBean().getListaxmlout(), DestinatarioPreferitoBean.class);

					if (azioneDaFare.getAzioneRapida().equals(AzioniRapide.ASSEGNA_UO_COMPETENTE.getValue())) {
//						result.setListaUOPreferiteMail(lista);
						mappaUOPreferite.put(azioneDaFare.getAzioneRapida(), lista);
					} else if (azioneDaFare.getAzioneRapida().equals(AzioniRapide.METTI_IN_CARICO.getValue())
							|| azioneDaFare.getAzioneRapida().equals(AzioniRapide.MANDA_IN_APPROVAZIONE.getValue())) {
//						result.setListaUtentiPreferitiMail(lista);
						mappaUtentiPreferiti.put(azioneDaFare.getAzioneRapida(), lista);
					} else {
						// ASSEGNA_DOC, ASSEGNA_FOLDER, INVIO_CC_DOC,
						// INVIO_CC_FOLDER
						List<DestinatarioPreferitoBean> listaUtenti = new ArrayList<DestinatarioPreferitoBean>();
						List<DestinatarioPreferitoBean> listaUO = new ArrayList<DestinatarioPreferitoBean>();
						for (DestinatarioPreferitoBean element : lista) {
							if (element.getTipoDestinatarioPreferito().equals("UO")) {
								listaUO.add(element);
							} else {
								listaUtenti.add(element);
							}
						}

						if (azioneDaFare.getAzioneRapida().equals(AzioniRapide.ASSEGNA_DOC.getValue())
								|| azioneDaFare.getAzioneRapida().equals(AzioniRapide.SMISTA_DOC.getValue())
								|| azioneDaFare.getAzioneRapida().equals(AzioniRapide.INVIO_CC_DOC.getValue())) {
//							result.setListaUOPreferiteDoc(listaUO);
//							result.setListaUtentiPreferitiDoc(listaUtenti);
							mappaUOPreferite.put(azioneDaFare.getAzioneRapida(), listaUO);
							mappaUtentiPreferiti.put(azioneDaFare.getAzioneRapida(), listaUtenti);
						} else if (azioneDaFare.getAzioneRapida().equals(AzioniRapide.ASSEGNA_FOLDER.getValue())
								|| azioneDaFare.getAzioneRapida().equals(AzioniRapide.SMISTA_FOLDER.getValue())
								|| azioneDaFare.getAzioneRapida().equals(AzioniRapide.INVIO_CC_FOLDER.getValue())) {
							// ASSEGNA_FOLDER, INVIO_CC_FOLDER
//							result.setListaUOPreferiteFolder(listaUO);
//							result.setListaUtentiPreferitiFolder(listaUtenti);
							mappaUOPreferite.put(azioneDaFare.getAzioneRapida(), listaUO);
							mappaUtentiPreferiti.put(azioneDaFare.getAzioneRapida(), listaUtenti);
						}

					}

				} else {
					mLogger.error("Errore getDestinatariPreferitiUtente:" + lStoreResultBean.getDefaultMessage());
					result.setErrorMessage(lStoreResultBean.getDefaultMessage());
					throw new Exception(lStoreResultBean.getDefaultMessage());
				}
			}
			
			result.setMappaUOPreferite(mappaUOPreferite);
			result.setMappaUtentiPreferiti(mappaUtentiPreferiti);
			result.setSuccess(true);

		} catch (Exception e) {
			mLogger.error("Errore getDestinatariPreferitiUtente:", e);
			result.setErrorMessage(e.getMessage());
		}

		return result;
	}
	
	@Override
	public LoadDestinatariPreferitiBean get(LoadDestinatariPreferitiBean bean) throws Exception {
		return null;
	}

	@Override
	public LoadDestinatariPreferitiBean add(LoadDestinatariPreferitiBean bean) throws Exception {
		return null;
	}

	@Override
	public LoadDestinatariPreferitiBean remove(LoadDestinatariPreferitiBean bean) throws Exception {
		return null;
	}

	@Override
	public LoadDestinatariPreferitiBean update(LoadDestinatariPreferitiBean bean, LoadDestinatariPreferitiBean oldvalue)
			throws Exception {
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(LoadDestinatariPreferitiBean bean) throws Exception {
		return null;
	}

	@Override
	public PaginatorBean<LoadDestinatariPreferitiBean> fetch(AdvancedCriteria criteria, Integer startRow,
			Integer endRow, List<OrderByBean> orderby) throws Exception {
		return null;
	}

}
