package it.eng.dm.sira.dao.converter;

import it.eng.core.business.converter.IBeanPopulate;
import it.eng.dm.sira.entity.DmtProcessTypeEvents;
import it.eng.dm.sira.entity.DmtProcessTypeEventsId;
import it.eng.dm.sira.service.bean.TipiEventiProcBean;

public class TipiEventiProcBeanToDmtProcessTypeEvents implements IBeanPopulate<TipiEventiProcBean, DmtProcessTypeEvents> {

	@Override
	public void populate(TipiEventiProcBean src, DmtProcessTypeEvents dest) throws Exception {
		if(src.getIdProcessType() != null && !"".equals(src.getIdProcessType()) && 
		   src.getIdEventType() != null && !"".equals(src.getIdEventType())) {
				DmtProcessTypeEventsId id = new DmtProcessTypeEventsId(src.getIdProcessType(), src.getIdEventType());
				dest.setId(id);
		}
	}

	@Override
	public void populateForUpdate(TipiEventiProcBean src, DmtProcessTypeEvents dest) throws Exception {
		if(src.getIdProcessType() != null && !"".equals(src.getIdProcessType()) && 
		   src.getIdEventType() != null && !"".equals(src.getIdEventType())) {
				DmtProcessTypeEventsId id = new DmtProcessTypeEventsId(src.getIdProcessType(), src.getIdEventType());
				dest.setId(id);
		}
	}

}