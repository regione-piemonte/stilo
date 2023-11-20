/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.dmpk_repository_gui.bean.DmpkRepositoryGuiGetdatiudrispostaBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.ProtocolloUtility;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AssPreselMittBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DocCollegatoBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ProtocollazioneBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.client.DmpkRepositoryGuiGetdatiudrisposta;
import it.eng.document.function.bean.DocumentoXmlOutBean;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilityDeserializer;

@Datasource(id = "GetDatiUDRispostaDataSource")
public class GetDatiUDRispostaDataSource extends AbstractDataSource<ProtocollazioneBean, ProtocollazioneBean>{
	
	private static final Logger log = Logger.getLogger(GetDatiUDRispostaDataSource.class);

	@Override
	public ProtocollazioneBean get(ProtocollazioneBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkRepositoryGuiGetdatiudrispostaBean input = new DmpkRepositoryGuiGetdatiudrispostaBean();
		
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);

		input.setIdudin(bean.getIdUd());
		
		DmpkRepositoryGuiGetdatiudrisposta lDmpkRepositoryGuiGetDatiUDRisposta = new DmpkRepositoryGuiGetdatiudrisposta();
		StoreResultBean<DmpkRepositoryGuiGetdatiudrispostaBean> output = 
				lDmpkRepositoryGuiGetDatiUDRisposta.execute(getLocale(), loginBean, input);
		
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				log.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		XmlUtilityDeserializer lXmlUtilityDeserializer = new XmlUtilityDeserializer();
		DocumentoXmlOutBean lDocumentoXmlOutBean = lXmlUtilityDeserializer.unbindXml(output.getResultBean().getDatiudxmlout(), DocumentoXmlOutBean.class);
		
		ProtocolloUtility lProtocolloUtility = new ProtocolloUtility(getSession());
		ProtocollazioneBean lProtocollazioneBean = lProtocolloUtility.getProtocolloFromDocumentoXml(lDocumentoXmlOutBean, getExtraparams());
		if(lProtocollazioneBean.getFlgPresentiAssPreselMitt() != null && lProtocollazioneBean.getFlgPresentiAssPreselMitt()) {			
			DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();			
			DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
			lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("ASS_PRESEL_MITT");		
			lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("ID_UD|*|" + String.valueOf(bean.getIdUd().longValue()));				
			StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);			
			lProtocollazioneBean.setListaAssPreselMitt(XmlListaUtility.recuperaLista(lStoreResultBean.getResultBean().getListaxmlout(), AssPreselMittBean.class));				
		} 
		lProtocollazioneBean.setIdEmailArrivo(lDocumentoXmlOutBean.getIdEmailArrivo());
		lProtocollazioneBean.setEmailArrivoInteroperabile(lDocumentoXmlOutBean.getEmailArrivoInteroperabile());
		lProtocollazioneBean.setEmailInviataFlgPEC(lDocumentoXmlOutBean.getEmailInviataFlgPEC());
		lProtocollazioneBean.setEmailInviataFlgPEO(lDocumentoXmlOutBean.getEmailInviataFlgPEO());
		lProtocollazioneBean.setSegnatura(lDocumentoXmlOutBean.getSegnatura());
		lProtocollazioneBean.setCodSupportoOrig(lDocumentoXmlOutBean.getCodSupportoOrig());
		
		lProtocollazioneBean.setCodCategoriaRegPrimariaRisposta(lDocumentoXmlOutBean.getCodCategoriaRegPrimariaRisposta());
		lProtocollazioneBean.setSiglaRegistroRegPrimariaRisposta(lDocumentoXmlOutBean.getSiglaRegistroRegPrimariaRisposta());
		lProtocollazioneBean.setDesRegistroRegPrimariaRisposta(lDocumentoXmlOutBean.getDesRegistroRegPrimariaRisposta());
		lProtocollazioneBean.setCategoriaRepertorioRegPrimariaRisposta(lDocumentoXmlOutBean.getCategoriaRepertorioRegPrimariaRisposta());
		lProtocollazioneBean.setCodCategoriaRegSecondariaRisposta(lDocumentoXmlOutBean.getCodCategoriaRegSecondariaRisposta());
		lProtocollazioneBean.setSiglaRegistroRegSecondariaRisposta(lDocumentoXmlOutBean.getSiglaRegistroRegSecondariaRisposta());
		lProtocollazioneBean.setDesRegistroRegSecondariaRisposta(lDocumentoXmlOutBean.getDesRegistroRegSecondariaRisposta());
		lProtocollazioneBean.setCategoriaRepertorioRegSecondariaRisposta(lDocumentoXmlOutBean.getCategoriaRepertorioRegSecondariaRisposta());
		
		if(!isAttivoProtocolloWizard()) {
			lProtocollazioneBean.setListaDocumentiDaCollegare(lProtocollazioneBean.getListaDocumentiCollegati());
			lProtocollazioneBean.setListaDocumentiCollegati(new ArrayList<DocCollegatoBean>());
		}
		
		return lProtocollazioneBean;
	}
	
	public boolean isAttivoProtocolloWizard() {
		return ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_PROTOCOLLO_WIZARD");
	}

	@Override
	public ProtocollazioneBean add(ProtocollazioneBean bean) throws Exception {
		return null;
	}
	
	@Override
	public PaginatorBean<ProtocollazioneBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow,
			List<OrderByBean> orderby) throws Exception {
		return null;
	}

	@Override
	public ProtocollazioneBean remove(ProtocollazioneBean bean) throws Exception {
		return null;
	}

	@Override
	public ProtocollazioneBean update(ProtocollazioneBean bean, ProtocollazioneBean oldvalue) throws Exception {
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(ProtocollazioneBean bean) throws Exception {
		return null;
	}
}