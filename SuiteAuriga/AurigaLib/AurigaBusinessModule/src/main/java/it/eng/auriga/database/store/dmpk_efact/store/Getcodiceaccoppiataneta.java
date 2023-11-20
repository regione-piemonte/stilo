/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_efact.bean.DmpkEfactGetcodiceaccoppiatanetaBean;
import it.eng.auriga.database.store.dmpk_efact.store.impl.GetcodiceaccoppiatanetaImpl;
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
@Service(name="DmpkEfactGetcodiceaccoppiataneta")
public class Getcodiceaccoppiataneta {
		
	private DmpkEfactGetcodiceaccoppiatanetaBean bean;
		  
	public void setBean(DmpkEfactGetcodiceaccoppiatanetaBean bean){
		this.bean = bean;
	}
	
	@Operation(name="execute")
	public StoreResultBean<DmpkEfactGetcodiceaccoppiatanetaBean> execute(SchemaBean lSchemaBean, DmpkEfactGetcodiceaccoppiatanetaBean pBean) throws Exception{
	   final GetcodiceaccoppiatanetaImpl lGetcodiceaccoppiataneta = new GetcodiceaccoppiatanetaImpl();
	   setBean(pBean);
	   lGetcodiceaccoppiataneta.setBean(bean);
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
					lGetcodiceaccoppiataneta.execute(paramConnection);
				}
			});
			StoreResultBean<DmpkEfactGetcodiceaccoppiatanetaBean> result = new StoreResultBean<DmpkEfactGetcodiceaccoppiatanetaBean>();
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