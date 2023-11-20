/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.module.business.entity.BnlConsumerWs;
import it.eng.core.annotation.Service;
import it.eng.core.business.HibernateUtil;

	
	@Service(name="DaoBnlConsumerWS")
	public class DaoBnlConsumerWS{
		
		private static final Logger logger = Logger.getLogger(DaoBnlConsumerWS.class);
		
		public Boolean verificaCredenziali(SchemaBean pSchemaBean, String userid, String password) throws Exception{
			
			Boolean result = null;
			
			if(StringUtils.isBlank(userid) || StringUtils.isBlank(password)){
				throw new Exception("Userid o password non valorizzate");
			}
			
			Session session = null;
			try {
				
				session = HibernateUtil.openSession(pSchemaBean.getSchema());
				Criteria criteria = session.createCriteria(BnlConsumerWs.class);
				
				criteria.add(Restrictions.eq("id.userid", userid));
				criteria.add(Restrictions.eq("id.password", password));
				
				Number count = (Number) criteria.setProjection(Projections.rowCount()).uniqueResult();
				result = count != null && count.intValue() == 1;
				
			}
			catch(Exception e){
				logger.error("Errore verificaCredenziali BnlConsumerWS", e);
				throw e;
			}
			finally{
				HibernateUtil.release(session);
			}
			
			return result;
			
			
		}

}
