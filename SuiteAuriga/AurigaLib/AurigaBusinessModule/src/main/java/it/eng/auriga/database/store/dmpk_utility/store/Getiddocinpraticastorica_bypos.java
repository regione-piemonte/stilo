/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityGetiddocinpraticastorica_byposBean;
import it.eng.auriga.database.store.dmpk_utility.store.impl.Getiddocinpraticastorica_byposImpl;
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
@Service(name="DmpkUtilityGetiddocinpraticastorica_bypos")
public class Getiddocinpraticastorica_bypos {
		
	private DmpkUtilityGetiddocinpraticastorica_byposBean bean;
		  
	public void setBean(DmpkUtilityGetiddocinpraticastorica_byposBean bean){
		this.bean = bean;
	}
	
	@Operation(name="execute")
	public StoreResultBean<DmpkUtilityGetiddocinpraticastorica_byposBean> execute(SchemaBean lSchemaBean, DmpkUtilityGetiddocinpraticastorica_byposBean pBean) throws Exception{
	   final Getiddocinpraticastorica_byposImpl lGetiddocinpraticastorica_bypos = new Getiddocinpraticastorica_byposImpl();
	   setBean(pBean);
	   lGetiddocinpraticastorica_bypos.setBean(bean);
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
					lGetiddocinpraticastorica_bypos.execute(paramConnection);
				}
			});
			StoreResultBean<DmpkUtilityGetiddocinpraticastorica_byposBean> result = new StoreResultBean<DmpkUtilityGetiddocinpraticastorica_byposBean>();
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