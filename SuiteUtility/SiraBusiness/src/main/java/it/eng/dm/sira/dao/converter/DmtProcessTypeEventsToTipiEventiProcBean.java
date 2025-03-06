package it.eng.dm.sira.dao.converter;

import it.eng.core.business.converter.IBeanPopulate;
import it.eng.dm.sira.entity.DmtProcessTypeEvents;
import it.eng.dm.sira.service.bean.TipiEventiProcBean;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class DmtProcessTypeEventsToTipiEventiProcBean implements IBeanPopulate<DmtProcessTypeEvents, TipiEventiProcBean> {

	private Session session;
	
	public DmtProcessTypeEventsToTipiEventiProcBean(Session session) {
		this.session = session;
	}
	
	@Override
	public void populate(DmtProcessTypeEvents src, TipiEventiProcBean dest) throws Exception {
		if(src.getId() != null) {			
			dest.setIdProcessType(src.getId().getIdProcessType());
			dest.setNomeProcessType(getNomeProcessType(src.getId().getIdProcessType(), session));
			dest.setIdEventType(src.getId().getIdEventType());
			dest.setDesEventType(getDesEventType(src.getId().getIdEventType(), session));			
		}		
	}
	
	private String getNomeProcessType(BigDecimal idProcessType, Session session) {
		String hql = null;
		hql = "select t.nomeProcessType from DmtProcessTypes t where t.idProcessType = " + idProcessType;					
		Query query = session.createQuery(hql);
		List<String> results = query.list();
		if(results != null && results.size() > 0) {
			return results.get(0);
		}		
		return null;
	}
	
	private String getDesEventType(BigDecimal idEventType, Session session) {
		String hql = null;
		hql = "select t.desEventType from DmtEventTypes t where t.idEventType = " + idEventType;					
		Query query = session.createQuery(hql);
		List<String> results = query.list();
		if(results != null && results.size() > 0) {
			return results.get(0);
		}		
		return null;
	}

	@Override
	public void populateForUpdate(DmtProcessTypeEvents src, TipiEventiProcBean dest) throws Exception {
		// TODO Auto-generated method stub

	}

}