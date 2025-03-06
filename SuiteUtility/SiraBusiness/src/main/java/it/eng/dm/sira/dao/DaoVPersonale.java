package it.eng.dm.sira.dao;

import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.dm.sira.entity.VPersonale;
import it.eng.dm.sira.service.hibernate.SiraHibernate;
import it.eng.spring.utility.SpringAppContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class DaoVPersonale {

	public TPagingList<VPersonale> search(TFilterFetch<VPersonale> filter) throws Exception {
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
			TPagingList<VPersonale> paginglist = new TPagingList<VPersonale>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, filter.getStartRow(), filter.getEndRow())) {
				paginglist.addData((VPersonale) obj);
			}
			return paginglist;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<VPersonale> filter) {
		Criteria lCriteria = session.createCriteria(VPersonale.class);
		if (filter.getFilter().getCognome() != null) {
			lCriteria.add(Restrictions.eq("cognome", filter.getFilter().getCognome()));
		}
		if (filter.getFilter().getCognome() != null) {
			lCriteria.add(Restrictions.eq("nome", filter.getFilter().getNome()));
		}
		if (filter.getFilter().getCodiceFiscale() != null) {
			lCriteria.add(Restrictions.eq("codiceFiscale", filter.getFilter().getCodiceFiscale()));
		}
		if (filter.getFilter().getIdEcg() != null) {
			lCriteria.add(Restrictions.eq("idEcg", filter.getFilter().getIdEcg()));
		}
		return lCriteria;
	}
}