/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesSetobjprocessforBean;
import it.eng.auriga.database.store.dmpk_processes.store.impl.SetobjprocessforImpl;
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

public class SetObjProcessFor extends StoreCaller<DmpkProcessesSetobjprocessforBean>{
	
	private static Logger mLogger = Logger.getLogger(SetObjProcessFor.class);
	
	public StoreResultBean<DmpkProcessesSetobjprocessforBean> setObjProcessForAvvioProc(BigDecimal idProcesso, BigDecimal idObj, String flgTpObj, AurigaLoginBean pAurigaLoginBean, Session lSession) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		DmpkProcessesSetobjprocessforBean lDmpkProcessesSetobjprocessforBean = creaBeanAvvioProc(idProcesso, idObj, flgTpObj, pAurigaLoginBean);		
		final SetobjprocessforImpl lSetobjprocessforImpl = new SetobjprocessforImpl();
		lSetobjprocessforImpl.setBean(lDmpkProcessesSetobjprocessforBean);
		lSession.doWork(new Work() {
			@Override
			public void execute(Connection paramConnection) throws SQLException {
				paramConnection.setAutoCommit(false);
				lSetobjprocessforImpl.execute(paramConnection);
			}
		});
		StoreResultBean<DmpkProcessesSetobjprocessforBean> result = new StoreResultBean<DmpkProcessesSetobjprocessforBean>();
		AnalyzeResult.analyze(lDmpkProcessesSetobjprocessforBean, result);
		result.setResultBean(lDmpkProcessesSetobjprocessforBean);
		return result;
		
	}

	private DmpkProcessesSetobjprocessforBean creaBeanAvvioProc(BigDecimal idProcesso, BigDecimal idObj, String flgTpObj, AurigaLoginBean pAurigaLoginBean) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		DmpkProcessesSetobjprocessforBean lDmpkProcessesSetobjprocessforBean = new DmpkProcessesSetobjprocessforBean();
		lDmpkProcessesSetobjprocessforBean.setIdprocessin(idProcesso);
		lDmpkProcessesSetobjprocessforBean.setIdobjprocessonin(idObj);
		lDmpkProcessesSetobjprocessforBean.setFlgtpobjprocessonin(flgTpObj);
		lDmpkProcessesSetobjprocessforBean.setFlgrollbckfullin(1);
		lDmpkProcessesSetobjprocessforBean.setFlgautocommitin(null);
		setTokenAndUserId(pAurigaLoginBean, lDmpkProcessesSetobjprocessforBean);		
		return lDmpkProcessesSetobjprocessforBean;
	}
	
}
