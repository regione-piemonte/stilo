/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAddfolderBean;
import it.eng.auriga.database.store.dmpk_core.store.impl.AddfolderImpl;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.document.function.bean.Flag;
import it.eng.storeutil.AnalyzeResult;
import it.eng.workflow.service.bean.Assegnatario;
import it.eng.workflow.service.bean.XmlFolderProcesso;
import it.eng.workflow.service.storecaller.StoreCaller;
import it.eng.xml.XmlUtilitySerializer;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

public class AddFolderProcess extends StoreCaller<DmpkCoreAddfolderBean>{
	
	private static Logger mLogger = Logger.getLogger(AddFolderProcess.class);

	public StoreResultBean<DmpkCoreAddfolderBean> addFolderAvvioProcedimento(BigDecimal idProcesso, AurigaLoginBean pAurigaLoginBean, Session lSession) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		DmpkCoreAddfolderBean lDmpkCoreAddfolderBean = creaBeanFolderAvvioProcedimento(idProcesso, pAurigaLoginBean);
		final AddfolderImpl lAddfolderImpl = new AddfolderImpl();
		lAddfolderImpl.setBean(lDmpkCoreAddfolderBean);
		lSession.doWork(new Work() {
			@Override
			public void execute(Connection paramConnection) throws SQLException {
				paramConnection.setAutoCommit(false);
				lAddfolderImpl.execute(paramConnection);
			}
		});
		StoreResultBean<DmpkCoreAddfolderBean> result = new StoreResultBean<DmpkCoreAddfolderBean>();
		AnalyzeResult.analyze(lDmpkCoreAddfolderBean, result);
		result.setResultBean(lDmpkCoreAddfolderBean);
		return result;	
	}
	
	public StoreResultBean<DmpkCoreAddfolderBean> addFolderAvvioIterAtti(BigDecimal idProcesso, String idUoProponente, AurigaLoginBean pAurigaLoginBean, Session lSession) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		DmpkCoreAddfolderBean lDmpkCoreAddfolderBean = creaBeanFolderAvvioIterAtti(idProcesso, idUoProponente, pAurigaLoginBean);
		final AddfolderImpl lAddfolderImpl = new AddfolderImpl();
		lAddfolderImpl.setBean(lDmpkCoreAddfolderBean);
		lSession.doWork(new Work() {
			@Override
			public void execute(Connection paramConnection) throws SQLException {
				paramConnection.setAutoCommit(false);
				lAddfolderImpl.execute(paramConnection);
			}
		});
		StoreResultBean<DmpkCoreAddfolderBean> result = new StoreResultBean<DmpkCoreAddfolderBean>();
		AnalyzeResult.analyze(lDmpkCoreAddfolderBean, result);
		result.setResultBean(lDmpkCoreAddfolderBean);
		return result;		
	}
	
	private DmpkCoreAddfolderBean creaBeanFolderAvvioProcedimento(BigDecimal idProcesso, AurigaLoginBean pAurigaLoginBean) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		DmpkCoreAddfolderBean lDmpkCoreAddfolderBean = new DmpkCoreAddfolderBean();
		XmlFolderProcesso lXmlFolderProcesso = new XmlFolderProcesso();
		lXmlFolderProcesso.setIdProcessFrom(idProcesso);
		lXmlFolderProcesso.setFlgFascTitolario(Flag.NOT_SETTED);
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		String lStrXml = lXmlUtilitySerializer.bindXml(lXmlFolderProcesso);
		lDmpkCoreAddfolderBean.setAttributixmlin(lStrXml);
		lDmpkCoreAddfolderBean.setFlgrollbckfullin(1);
		lDmpkCoreAddfolderBean.setFlgautocommitin(null);
		setTokenAndUserId(pAurigaLoginBean, lDmpkCoreAddfolderBean);
		return lDmpkCoreAddfolderBean;
	}
	
	private DmpkCoreAddfolderBean creaBeanFolderAvvioIterAtti(BigDecimal idProcesso, String idUoProponente, AurigaLoginBean pAurigaLoginBean) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		DmpkCoreAddfolderBean lDmpkCoreAddfolderBean = new DmpkCoreAddfolderBean();
		XmlFolderProcesso lXmlFolderProcesso = new XmlFolderProcesso();
		lXmlFolderProcesso.setIdProcessFrom(idProcesso);
		lXmlFolderProcesso.setFlgFascTitolario(Flag.NOT_SETTED);		
		List<Assegnatario> listaAssegnatari = new ArrayList<Assegnatario>();
		Assegnatario assegnatario = new Assegnatario();
		assegnatario.setIdUo(idUoProponente);
		listaAssegnatari.add(assegnatario);
		lXmlFolderProcesso.setListaAssegnatari(listaAssegnatari);
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		String lStrXml = lXmlUtilitySerializer.bindXml(lXmlFolderProcesso);
		lDmpkCoreAddfolderBean.setAttributixmlin(lStrXml);
		lDmpkCoreAddfolderBean.setFlgrollbckfullin(1);
		lDmpkCoreAddfolderBean.setFlgautocommitin(null);
		setTokenAndUserId(pAurigaLoginBean, lDmpkCoreAddfolderBean);
		return lDmpkCoreAddfolderBean;
	}

}
