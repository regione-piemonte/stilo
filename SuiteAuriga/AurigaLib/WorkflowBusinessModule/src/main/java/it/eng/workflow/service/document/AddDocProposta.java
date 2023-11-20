/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAdddocBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAddfolderBean;
import it.eng.auriga.database.store.dmpk_core.store.impl.AdddocImpl;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.document.function.bean.Flag;
import it.eng.storeutil.AnalyzeResult;
import it.eng.workflow.service.bean.Assegnatario;
import it.eng.workflow.service.bean.NumerazioneDaDare;
import it.eng.workflow.service.bean.XmlDocProposta;
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

public class AddDocProposta extends StoreCaller<DmpkCoreAdddocBean>{
	
	private static Logger mLogger = Logger.getLogger(AddDocProposta.class);

	public StoreResultBean<DmpkCoreAdddocBean> addDocAvvioIterAtti(String idTipoDoc, String sigla, String idUoProponente, AurigaLoginBean pAurigaLoginBean, Session lSession) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		DmpkCoreAdddocBean lDmpkCoreAdddocBean = creaBeanDocAvvioIterAtti(idTipoDoc, sigla, idUoProponente, pAurigaLoginBean);
		final AdddocImpl lAdddocImpl = new AdddocImpl();
		lAdddocImpl.setBean(lDmpkCoreAdddocBean);
		lSession.doWork(new Work() {
			@Override
			public void execute(Connection paramConnection) throws SQLException {
				paramConnection.setAutoCommit(false);
				lAdddocImpl.execute(paramConnection);
			}
		});
		StoreResultBean<DmpkCoreAdddocBean> result = new StoreResultBean<DmpkCoreAdddocBean>();
		AnalyzeResult.analyze(lDmpkCoreAdddocBean, result);
		result.setResultBean(lDmpkCoreAdddocBean);
		return result;		
	}
	
	private DmpkCoreAdddocBean creaBeanDocAvvioIterAtti(String idTipoDoc, String sigla, String idUoProponente, AurigaLoginBean pAurigaLoginBean) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		DmpkCoreAdddocBean lDmpkCoreAdddocBean = new DmpkCoreAdddocBean();
		XmlDocProposta lXmlDocProposta = new XmlDocProposta();
		lXmlDocProposta.setIdDocType(new BigDecimal(idTipoDoc));
		List<NumerazioneDaDare> listaNumerazioniDaDare = new ArrayList<NumerazioneDaDare>();
		NumerazioneDaDare numerazioneDaDare = new NumerazioneDaDare();
		numerazioneDaDare.setSigla(sigla);
		numerazioneDaDare.setIdUo(idUoProponente);
		listaNumerazioniDaDare.add(numerazioneDaDare);
		lXmlDocProposta.setListaNumerazioniDaDare(listaNumerazioniDaDare);
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		String lStrXml = lXmlUtilitySerializer.bindXml(lXmlDocProposta);
		lDmpkCoreAdddocBean.setAttributiuddocxmlin(lStrXml);
		lDmpkCoreAdddocBean.setFlgrollbckfullin(1);
		lDmpkCoreAdddocBean.setFlgautocommitin(null);
		setTokenAndUserId(pAurigaLoginBean, lDmpkCoreAdddocBean);
		return lDmpkCoreAdddocBean;
	}

}
