/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import it.eng.auriga.database.store.dmpk_repository_gui.bean.DmpkRepositoryGuiLoaddettudxguimodprotBean;
import it.eng.auriga.database.store.dmpk_repository_gui.store.Loaddettudxguimodprot;
import it.eng.auriga.database.store.dmpk_repository_gui.store.impl.LoaddettudxguimodprotImpl;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.document.function.bean.DocumentoXmlOutBean;
import it.eng.document.function.bean.RecuperaDocumentoInBean;
import it.eng.document.function.bean.RecuperaDocumentoOutBean;
import it.eng.storeutil.AnalyzeResult;
import it.eng.xml.XmlUtilityDeserializer;

@Service(name = "RecuperoDocumenti")
public class RecuperoDocumenti {

	private static Logger mLogger = Logger.getLogger(RecuperoDocumenti.class);
	
	@Operation(name = "loadDocumento")
	public RecuperaDocumentoOutBean loadDocumento(AurigaLoginBean pAurigaLoginBean, RecuperaDocumentoInBean pRecuperaDocumentoInBean) throws Exception{
		mLogger.debug("Recupero documento con idUd " + pRecuperaDocumentoInBean.getIdUd());
		
		DmpkRepositoryGuiLoaddettudxguimodprotBean bean = new DmpkRepositoryGuiLoaddettudxguimodprotBean();
		bean.setIduserlavoroin(StringUtils.isNotBlank(pAurigaLoginBean.getIdUserLavoro()) ? new BigDecimal(pAurigaLoginBean.getIdUserLavoro()) : null);
		bean.setIdudin(pRecuperaDocumentoInBean.getIdUd());
		bean.setTsanndatiin(pRecuperaDocumentoInBean.getTsAnnDati());
		bean.setFlgsoloabilazioniin(StringUtils.isNotBlank(pRecuperaDocumentoInBean.getFlgSoloAbilAzioni()) ? new Integer(pRecuperaDocumentoInBean.getFlgSoloAbilAzioni()) : null);
		bean.setIdprocessin(pRecuperaDocumentoInBean.getIdProcess());
		bean.setTasknamein(pRecuperaDocumentoInBean.getTaskName());
		bean.setIdpubblicazionein(pRecuperaDocumentoInBean.getIdRichPubbl());
		bean.setCinodoscrivaniain(pRecuperaDocumentoInBean.getIdNodeScrivania());
		bean.setListaidudscansionidaassin(pRecuperaDocumentoInBean.getListaIdUdScansioni());
		
		Loaddettudxguimodprot store = new Loaddettudxguimodprot();
		StoreResultBean<DmpkRepositoryGuiLoaddettudxguimodprotBean> resultStore = store.execute(pAurigaLoginBean, bean);
		if (resultStore.isInError()){
			mLogger.error("Errore store " + resultStore.getErrorContext() + " - " + resultStore.getErrorCode() + " - " + resultStore.getDefaultMessage());
			RecuperaDocumentoOutBean lRecuperaDocumentoOutBean = new RecuperaDocumentoOutBean();
			BeanUtilsBean2.getInstance().copyProperties(lRecuperaDocumentoOutBean, resultStore);
			return lRecuperaDocumentoOutBean;
		}
		DmpkRepositoryGuiLoaddettudxguimodprotBean result = resultStore.getResultBean();
		String lStrXmlIn = result.getDatiudxmlout();
		mLogger.debug(result.getDatiudxmlout());
		XmlUtilityDeserializer lXmlUtility = new XmlUtilityDeserializer();
		DocumentoXmlOutBean lDocumentoXmlOutBean = lXmlUtility.unbindXml(lStrXmlIn, DocumentoXmlOutBean.class);
		RecuperaDocumentoOutBean lRecuperaDocumentoOutBean = new RecuperaDocumentoOutBean();
		lRecuperaDocumentoOutBean.setDocumento(lDocumentoXmlOutBean);
		
		return lRecuperaDocumentoOutBean;
	}
	
	public RecuperaDocumentoOutBean loadDocumentoInTransaction(AurigaLoginBean pAurigaLoginBean, RecuperaDocumentoInBean pRecuperaDocumentoInBean, Session session) throws Exception{
		mLogger.debug("Recupero documento con idUd " + pRecuperaDocumentoInBean.getIdUd());
		//Loaddettudxguimodprot store = new Loaddettudxguimodprot();
		DmpkRepositoryGuiLoaddettudxguimodprotBean bean = new DmpkRepositoryGuiLoaddettudxguimodprotBean();
		bean.setIduserlavoroin(StringUtils.isNotBlank(pAurigaLoginBean.getIdUserLavoro()) ? new BigDecimal(pAurigaLoginBean.getIdUserLavoro()) : null);
		bean.setCodidconnectiontokenin( pAurigaLoginBean.getToken() );
		bean.setIdudin(pRecuperaDocumentoInBean.getIdUd());
		bean.setTsanndatiin(pRecuperaDocumentoInBean.getTsAnnDati());
		bean.setFlgsoloabilazioniin(StringUtils.isNotBlank(pRecuperaDocumentoInBean.getFlgSoloAbilAzioni()) ? new Integer(pRecuperaDocumentoInBean.getFlgSoloAbilAzioni()) : null);
		bean.setIdprocessin(pRecuperaDocumentoInBean.getIdProcess());
		bean.setTasknamein(pRecuperaDocumentoInBean.getTaskName());		
		
		final LoaddettudxguimodprotImpl store = new LoaddettudxguimodprotImpl();
		store.setBean(bean);
		session.doWork(new Work() {

			@Override
			public void execute(Connection paramConnection) throws SQLException {
				paramConnection.setAutoCommit(false);
				store.execute(paramConnection);
			}
		});
		
		StoreResultBean<DmpkRepositoryGuiLoaddettudxguimodprotBean> lDmpkRepositoryGuiLoaddettudxguimodprotResultBean = new StoreResultBean<DmpkRepositoryGuiLoaddettudxguimodprotBean>();
		AnalyzeResult.analyze(bean, lDmpkRepositoryGuiLoaddettudxguimodprotResultBean);
		lDmpkRepositoryGuiLoaddettudxguimodprotResultBean.setResultBean(bean);
		
		if (lDmpkRepositoryGuiLoaddettudxguimodprotResultBean.isInError()){
			mLogger.error("Errore store " + lDmpkRepositoryGuiLoaddettudxguimodprotResultBean.getErrorContext() + " - " + lDmpkRepositoryGuiLoaddettudxguimodprotResultBean.getErrorCode() + " - " + lDmpkRepositoryGuiLoaddettudxguimodprotResultBean.getDefaultMessage());
			RecuperaDocumentoOutBean lRecuperaDocumentoOutBean = new RecuperaDocumentoOutBean();
			BeanUtilsBean2.getInstance().copyProperties(lRecuperaDocumentoOutBean, lDmpkRepositoryGuiLoaddettudxguimodprotResultBean);
			return lRecuperaDocumentoOutBean;
		}
		DmpkRepositoryGuiLoaddettudxguimodprotBean result = lDmpkRepositoryGuiLoaddettudxguimodprotResultBean.getResultBean();
		String lStrXmlIn = result.getDatiudxmlout();
		mLogger.debug(result.getDatiudxmlout());
		XmlUtilityDeserializer lXmlUtility = new XmlUtilityDeserializer();
		DocumentoXmlOutBean lDocumentoXmlOutBean = lXmlUtility.unbindXml(lStrXmlIn, DocumentoXmlOutBean.class);
		RecuperaDocumentoOutBean lRecuperaDocumentoOutBean = new RecuperaDocumentoOutBean();
		lRecuperaDocumentoOutBean.setDocumento(lDocumentoXmlOutBean);
		
		return lRecuperaDocumentoOutBean;
	}
}
