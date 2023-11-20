/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_wf.bean.DmpkWfCtrlexecatt_byattrBean;
import it.eng.auriga.database.store.dmpk_wf.store.impl.Ctrlexecatt_byattrImpl;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.core.business.HibernateUtil;
import it.eng.storeutil.AnalyzeResult;
import java.sql.Connection;
import java.sql.SQLException;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import it.eng.auriga.database.store.bean.SchemaBean;
/**
 * @author Procedure Wrapper 1.0
 * Classe generata per la chiamata alla store procedure 
 */
@Service(name="DmpkWfCtrlexecatt_byattr")
public class Ctrlexecatt_byattr {
		
	private DmpkWfCtrlexecatt_byattrBean bean;
		  
	public void setBean(DmpkWfCtrlexecatt_byattrBean bean){
		this.bean = bean;
	}
	
	@Operation(name="execute")
	public StoreResultBean<DmpkWfCtrlexecatt_byattrBean> execute(SchemaBean lSchemaBean, DmpkWfCtrlexecatt_byattrBean pBean) throws Exception{
	   final Ctrlexecatt_byattrImpl lCtrlexecatt_byattr = new Ctrlexecatt_byattrImpl();
	   setBean(pBean);
	   lCtrlexecatt_byattr.setBean(bean);
       setCommit();
	   SubjectBean subject =  SubjectUtil.subject.get();
	   subject.setIdDominio(lSchemaBean.getSchema());	
	   SubjectUtil.subject.set(subject);    
	   Session session = null;
		try {
			session = HibernateUtil.begin();
			session.doWork(new Work() {
				@Override
				public void execute(Connection paramConnection) throws SQLException {
					paramConnection.setAutoCommit(false);
					lCtrlexecatt_byattr.execute(paramConnection);
				}
			});
			StoreResultBean<DmpkWfCtrlexecatt_byattrBean> result = new StoreResultBean<DmpkWfCtrlexecatt_byattrBean>();
			AnalyzeResult.analyze(bean, result);
			result.setResultBean(bean);
			return result;
		}catch(Exception e){
			if (e.getCause() != null && e.getCause().getMessage() != null && e.getCause().getMessage().equals("Chiusura forzata")) throw new Exception("Chiusura forzata");
			else throw e;
		}finally{
			HibernateUtil.release(session);
		}
		
	}
	
	protected void setCommit() {
	}
}