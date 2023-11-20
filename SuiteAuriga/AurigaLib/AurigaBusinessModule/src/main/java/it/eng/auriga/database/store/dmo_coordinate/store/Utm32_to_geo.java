/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmo_coordinate.bean.DmoCoordinateUtm32_to_geoBean;
import it.eng.auriga.database.store.dmo_coordinate.store.impl.Utm32_to_geoImpl;
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
@Service(name="DmoCoordinateUtm32_to_geo")
public class Utm32_to_geo {
		
	private DmoCoordinateUtm32_to_geoBean bean;
		  
	public void setBean(DmoCoordinateUtm32_to_geoBean bean){
		this.bean = bean;
	}
	
	@Operation(name="execute")
	public StoreResultBean<DmoCoordinateUtm32_to_geoBean> execute(SchemaBean lSchemaBean, DmoCoordinateUtm32_to_geoBean pBean) throws Exception{
	   final Utm32_to_geoImpl lUtm32_to_geo = new Utm32_to_geoImpl();
	   setBean(pBean);
	   lUtm32_to_geo.setBean(bean);
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
					lUtm32_to_geo.execute(paramConnection);
				}
			});
			StoreResultBean<DmoCoordinateUtm32_to_geoBean> result = new StoreResultBean<DmoCoordinateUtm32_to_geoBean>();
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