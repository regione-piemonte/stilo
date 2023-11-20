/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_dmlsuoggetti.bean.DmpkDmlsuoggettiExecute_immediateBean;
import it.eng.auriga.database.store.dmpk_dmlsuoggetti.store.impl.Execute_immediateImpl;
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
@Service(name="DmpkDmlsuoggettiExecute_immediate")
public class Execute_immediate {
		
	private DmpkDmlsuoggettiExecute_immediateBean bean;
		  
	public void setBean(DmpkDmlsuoggettiExecute_immediateBean bean){
		this.bean = bean;
	}
	
	@Operation(name="execute")
	public StoreResultBean<DmpkDmlsuoggettiExecute_immediateBean> execute(SchemaBean lSchemaBean, DmpkDmlsuoggettiExecute_immediateBean pBean) throws Exception{
	   final Execute_immediateImpl lExecute_immediate = new Execute_immediateImpl();
	   setBean(pBean);
	   lExecute_immediate.setBean(bean);
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
					lExecute_immediate.execute(paramConnection);
				}
			});
			StoreResultBean<DmpkDmlsuoggettiExecute_immediateBean> result = new StoreResultBean<DmpkDmlsuoggettiExecute_immediateBean>();
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