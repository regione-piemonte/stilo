package it.eng.dm.sira.dao;

import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.core.business.converter.UtilPopulate;
import it.eng.dm.sira.dao.converter.DmtProcessTypeEventsToTipiEventiProcBean;
import it.eng.dm.sira.dao.converter.TipiEventiProcBeanToDmtProcessTypeEvents;
import it.eng.dm.sira.entity.DmtProcessTypeEvents;
import it.eng.dm.sira.entity.DmtProcessTypeEventsId;
import it.eng.dm.sira.service.bean.TipiEventiProcBean;
import it.eng.dm.sira.service.hibernate.SiraHibernate;
import it.eng.spring.utility.SpringAppContext;

import java.math.BigDecimal;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class DaoDmtProcessTypeEvents {
	
	public TipiEventiProcBean save(TipiEventiProcBean bean) throws Exception {
		Session session = null;
		try {
			SpringAppContext.getContext().getBean(SiraHibernate.class).initSubject();
			session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			DmtProcessTypeEvents toInsert = (DmtProcessTypeEvents) UtilPopulate.populate(bean, DmtProcessTypeEvents.class, new TipiEventiProcBeanToDmtProcessTypeEvents());
			session.save(toInsert);
			session.flush();			
			bean = (TipiEventiProcBean) UtilPopulate.populate(toInsert, TipiEventiProcBean.class, new DmtProcessTypeEventsToTipiEventiProcBean(session));			
			lTransaction.commit();
			return bean;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	public TipiEventiProcBean get(BigDecimal idProcessType, BigDecimal idEventType) throws Exception {
		Session session = null;
		TipiEventiProcBean result = null;
		try {
			SpringAppContext.getContext().getBean(SiraHibernate.class).initSubject();
			session = HibernateUtil.begin();
			DmtProcessTypeEventsId id = new DmtProcessTypeEventsId(idProcessType, idEventType);
			DmtProcessTypeEvents rel = (DmtProcessTypeEvents) session.get(DmtProcessTypeEvents.class, id);			
			result = (TipiEventiProcBean) UtilPopulate.populate(rel, TipiEventiProcBean.class, new DmtProcessTypeEventsToTipiEventiProcBean(session));
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
		return result;
	}
	
	public TipiEventiProcBean update(TipiEventiProcBean bean) throws Exception {
		Session session = null;
		try {
			SpringAppContext.getContext().getBean(SiraHibernate.class).initSubject();
			session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			DmtProcessTypeEvents toUpdate = (DmtProcessTypeEvents) UtilPopulate.populate(bean, DmtProcessTypeEvents.class, new TipiEventiProcBeanToDmtProcessTypeEvents());
			session.update(toUpdate);
			session.flush();
			bean = (TipiEventiProcBean) UtilPopulate.populate(toUpdate, TipiEventiProcBean.class, new DmtProcessTypeEventsToTipiEventiProcBean(session));
			lTransaction.commit();
			return bean;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}		
	}

	public TPagingList<TipiEventiProcBean> search(TFilterFetch<TipiEventiProcBean> filter) throws Exception {
		Session session = null;
		try {
			SpringAppContext.getContext().getBean(SiraHibernate.class).initSubject();
			session = HibernateUtil.begin();
			// Creo i criteri di ricerca
			Criteria criteria = this.buildHibernateCriteriaByFilter(session, filter);
			// Conto i record totali
			Long count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
			Integer startRow = (filter != null && filter.getStartRow() != null) ? filter.getStartRow() : 0;
			Integer endRow = (filter != null && filter.getEndRow() != null) ? filter.getEndRow() : startRow + count.intValue() - 1;
			// Creo l'oggetto paginatore
			TPagingList<TipiEventiProcBean> paginglist = new TPagingList<TipiEventiProcBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, filter.getStartRow(), filter.getEndRow())) {
				TipiEventiProcBean bean = (TipiEventiProcBean) UtilPopulate.populate((DmtProcessTypeEvents) obj, TipiEventiProcBean.class,
						new DmtProcessTypeEventsToTipiEventiProcBean(session));
				paginglist.addData(bean);
			}
			return paginglist;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<TipiEventiProcBean> filter) {
		Criteria lCriteria = session.createCriteria(DmtProcessTypeEvents.class);
		if (filter.getFilter().getIdProcessType() != null) {
			lCriteria.add(Restrictions.eq("id.idProcessType", filter.getFilter().getIdProcessType()));
		}
		if (filter.getFilter().getIdEventType() != null) {
			lCriteria.add(Restrictions.eq("id.idEventType", filter.getFilter().getIdEventType()));
		}
		return lCriteria;
	}

}