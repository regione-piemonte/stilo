package it.eng.dm.sira.dao;

import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.dm.sira.entity.VCategoriaNatura;
import it.eng.dm.sira.service.hibernate.SiraHibernate;
import it.eng.spring.utility.SpringAppContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class DaoVCategoriaNatura {

	public TPagingList<VCategoriaNatura> search(TFilterFetch<VCategoriaNatura> filter) throws Exception {
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
			TPagingList<VCategoriaNatura> paginglist = new TPagingList<VCategoriaNatura>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, filter.getStartRow(), filter.getEndRow())) {
				paginglist.addData((VCategoriaNatura) obj);
			}
			return paginglist;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}
	
	public Long count(TFilterFetch<VCategoriaNatura> filter) throws Exception{
		Session session = null;
		try {
			session = HibernateUtil.begin();
			// Creo i criteri di ricerca
			Criteria criteria = this.buildHibernateCriteriaByFilter(session, filter);
			// Conto i record totali
			Long count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
			return count;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
		
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<VCategoriaNatura> filter) {
		Criteria lCriteria = session.createCriteria(VCategoriaNatura.class);
		if (filter.getFilter().getCodCost()!=null){
			lCriteria.add(Restrictions.eq("codCost", filter.getFilter().getCodCost()));
		}
		if (filter.getFilter().getCodNost()!=null){
			lCriteria.add(Restrictions.eq("codNost", filter.getFilter().getCodNost()));
		}
		return lCriteria;
	}

}