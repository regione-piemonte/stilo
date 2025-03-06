package it.eng.dm.sira.dao;

import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.dm.sira.entity.TAttributesFilter;
import it.eng.dm.sira.entity.TAttributesFilterId;
import it.eng.dm.sira.service.hibernate.SiraHibernate;
import it.eng.spring.utility.SpringAppContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class DaoTAttributesFilter {

	public TAttributesFilter save(TAttributesFilter bean) throws Exception {
		SpringAppContext.getContext().getBean(SiraHibernate.class).initSubject();
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			session.save(bean);
			session.flush();
			lTransaction.commit();
			return bean;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	public TAttributesFilter get(TAttributesFilterId id) throws Exception {
		SpringAppContext.getContext().getBean(SiraHibernate.class).initSubject();
		Session session = null;
		try {
			session = HibernateUtil.begin();
			TAttributesFilter bean = (TAttributesFilter) session.load(TAttributesFilter.class, id);
			return bean;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	public TPagingList<TAttributesFilter> search(TFilterFetch<TAttributesFilter> filter) throws Exception {
		SpringAppContext.getContext().getBean(SiraHibernate.class).initSubject();
		Session session = null;
		try {
			session = HibernateUtil.begin();
			// Creo i criteri di ricerca
			Criteria criteria = this.buildHibernateCriteriaByFilter(session, filter);
			// Conto i record totali
			Long count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
			Integer startRow = (filter != null && filter.getStartRow() != null) ? filter.getStartRow() : 0;
			Integer endRow = (filter != null && filter.getEndRow() != null) ? filter.getEndRow() : startRow + count.intValue() - 1;
			// Creo l'oggetto paginatore
			TPagingList<TAttributesFilter> paginglist = new TPagingList<TAttributesFilter>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, filter.getStartRow(), filter.getEndRow())) {
				paginglist.addData((TAttributesFilter) obj);
			}
			return paginglist;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<TAttributesFilter> filter) {
		Criteria lCriteria = session.createCriteria(TAttributesFilter.class);
		if (filter.getFilter().getId().getIdProcessType() != null) {
			lCriteria.add(Restrictions.eq("id.idProcessType", filter.getFilter().getId().getIdProcessType()));
		}
		if (filter.getFilter().getId().getAttrName() != null) {
			lCriteria.add(Restrictions.eq("id.attrName", filter.getFilter().getId().getAttrName()));
		}
		if (filter.getFilter().getId().getFlgAnn() != null) {
			lCriteria.add(Restrictions.eq("id.flgAnn", filter.getFilter().getId().getFlgAnn()));
		}
		if (filter.getFilter().getFlgProv() != null) {
			lCriteria.add(Restrictions.eq("flgProv", filter.getFilter().getFlgProv()));
		}
		return lCriteria;
	}

}