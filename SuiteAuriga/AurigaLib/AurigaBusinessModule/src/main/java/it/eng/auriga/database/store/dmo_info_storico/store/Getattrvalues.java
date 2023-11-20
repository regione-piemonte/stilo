/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmo_info_storico.bean.DmoInfoStoricoGetattrvaluesBean;
import it.eng.auriga.database.store.dmo_info_storico.store.impl.GetattrvaluesImpl;
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
@Service(name="DmoInfoStoricoGetattrvalues")
public class Getattrvalues {
		
	private DmoInfoStoricoGetattrvaluesBean bean;
		  
	public void setBean(DmoInfoStoricoGetattrvaluesBean bean){
		this.bean = bean;
	}
	
	@Operation(name="execute")
	public StoreResultBean<DmoInfoStoricoGetattrvaluesBean> execute(SchemaBean lSchemaBean, DmoInfoStoricoGetattrvaluesBean pBean) throws Exception{
	   final GetattrvaluesImpl lGetattrvalues = new GetattrvaluesImpl();
	   setBean(pBean);
	   lGetattrvalues.setBean(bean);
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
					lGetattrvalues.execute(paramConnection);
				}
			});
			StoreResultBean<DmoInfoStoricoGetattrvaluesBean> result = new StoreResultBean<DmoInfoStoricoGetattrvaluesBean>();
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