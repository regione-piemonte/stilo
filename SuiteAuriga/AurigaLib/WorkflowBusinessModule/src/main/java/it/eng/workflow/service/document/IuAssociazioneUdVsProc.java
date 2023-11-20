/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesIuassociazioneudvsprocBean;
import it.eng.auriga.database.store.dmpk_processes.store.impl.IuassociazioneudvsprocImpl;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.storeutil.AnalyzeResult;
import it.eng.workflow.service.storecaller.StoreCaller;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

public class IuAssociazioneUdVsProc extends StoreCaller<DmpkProcessesIuassociazioneudvsprocBean>{
	
	private static Logger mLogger = Logger.getLogger(IuAssociazioneUdVsProc.class);

	public StoreResultBean<DmpkProcessesIuassociazioneudvsprocBean> salvaAssociazioneUdVsProc(BigDecimal idProcesso, BigDecimal idUd, AurigaLoginBean pAurigaLoginBean, Session lSession) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		DmpkProcessesIuassociazioneudvsprocBean lDmpkProcessesIuassociazioneudvsprocBean = creaBean(idProcesso, idUd, pAurigaLoginBean);
		final IuassociazioneudvsprocImpl lIuassociazioneudvsprocImpl = new IuassociazioneudvsprocImpl();
		lIuassociazioneudvsprocImpl.setBean(lDmpkProcessesIuassociazioneudvsprocBean);
		lSession.doWork(new Work() {
			@Override
			public void execute(Connection paramConnection) throws SQLException {
				paramConnection.setAutoCommit(false);
				lIuassociazioneudvsprocImpl.execute(paramConnection);
			}
		});
		StoreResultBean<DmpkProcessesIuassociazioneudvsprocBean> result = new StoreResultBean<DmpkProcessesIuassociazioneudvsprocBean>();
		AnalyzeResult.analyze(lDmpkProcessesIuassociazioneudvsprocBean, result);
		result.setResultBean(lDmpkProcessesIuassociazioneudvsprocBean);
		return result;		
	}

	private DmpkProcessesIuassociazioneudvsprocBean creaBean(BigDecimal idProcesso, BigDecimal idUd, AurigaLoginBean pAurigaLoginBean) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		DmpkProcessesIuassociazioneudvsprocBean lDmpkProcessesIuassociazioneudvsprocBean = new DmpkProcessesIuassociazioneudvsprocBean();
		lDmpkProcessesIuassociazioneudvsprocBean.setIdprocessin(idProcesso);
		lDmpkProcessesIuassociazioneudvsprocBean.setIdudin(idUd);
		lDmpkProcessesIuassociazioneudvsprocBean.setFlgacqprodin("P");
		lDmpkProcessesIuassociazioneudvsprocBean.setCodruolodocinprocin("INI");
		setTokenAndUserId(pAurigaLoginBean, lDmpkProcessesIuassociazioneudvsprocBean);
		return lDmpkProcessesIuassociazioneudvsprocBean;
	}

	public StoreResultBean<DmpkProcessesIuassociazioneudvsprocBean> salvaAssociazioneUdRichiestaVsProc(BigDecimal idProcesso, BigDecimal idUdRichiesta, AurigaLoginBean pAurigaLoginBean, Session lSession) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		DmpkProcessesIuassociazioneudvsprocBean lDmpkProcessesIuassociazioneudvsprocBean = creaBeanRichiesta(idProcesso, idUdRichiesta, pAurigaLoginBean);
		final IuassociazioneudvsprocImpl lIuassociazioneudvsprocImpl = new IuassociazioneudvsprocImpl();
		lIuassociazioneudvsprocImpl.setBean(lDmpkProcessesIuassociazioneudvsprocBean);
		lSession.doWork(new Work() {
			@Override
			public void execute(Connection paramConnection) throws SQLException {
				paramConnection.setAutoCommit(false);
				lIuassociazioneudvsprocImpl.execute(paramConnection);
			}
		});
		StoreResultBean<DmpkProcessesIuassociazioneudvsprocBean> result = new StoreResultBean<DmpkProcessesIuassociazioneudvsprocBean>();
		AnalyzeResult.analyze(lDmpkProcessesIuassociazioneudvsprocBean, result);
		result.setResultBean(lDmpkProcessesIuassociazioneudvsprocBean);
		return result;		
	}
	
	private DmpkProcessesIuassociazioneudvsprocBean creaBeanRichiesta(BigDecimal idProcesso, BigDecimal idUd, AurigaLoginBean pAurigaLoginBean) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		DmpkProcessesIuassociazioneudvsprocBean lDmpkProcessesIuassociazioneudvsprocBean = new DmpkProcessesIuassociazioneudvsprocBean();
		lDmpkProcessesIuassociazioneudvsprocBean.setIdprocessin(idProcesso);
		lDmpkProcessesIuassociazioneudvsprocBean.setIdudin(idUd);
		lDmpkProcessesIuassociazioneudvsprocBean.setFlgacqprodin("A");
		lDmpkProcessesIuassociazioneudvsprocBean.setCodruolodocinprocin("IST");
		setTokenAndUserId(pAurigaLoginBean, lDmpkProcessesIuassociazioneudvsprocBean);
		return lDmpkProcessesIuassociazioneudvsprocBean;
	}

}
