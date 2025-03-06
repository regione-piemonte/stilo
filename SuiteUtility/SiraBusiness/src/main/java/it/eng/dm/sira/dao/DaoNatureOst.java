package it.eng.dm.sira.dao;

import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.dm.sira.entity.NatureOst;
import it.eng.dm.sira.service.hibernate.SiraHibernate;
import it.eng.spring.utility.SpringAppContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class DaoNatureOst {

	public NatureOst get(String codice) throws Exception {
		Session session = null;
		NatureOst result = null;
		try {
			SpringAppContext.getContext().getBean(SiraHibernate.class).initSubject();
			session = HibernateUtil.begin();
			result = getInSession(codice, session);
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
		return result;
	}

	public NatureOst getInSession(String idEmail, Session session) throws Exception {
		NatureOst cat = (NatureOst) session.load(NatureOst.class, idEmail);
		return cat;
	}

	public TPagingList<NatureOst> search(TFilterFetch<NatureOst> filter) throws Exception {
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
			TPagingList<NatureOst> paginglist = new TPagingList<NatureOst>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, filter.getStartRow(), filter.getEndRow())) {
				paginglist.addData((NatureOst) obj);
			}
			return paginglist;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<NatureOst> filter) {
		Criteria lCriteria = session.createCriteria(NatureOst.class);
		if (filter.getFilter().getCodice() != null) {
			lCriteria.add(Restrictions.eq("codice", filter.getFilter().getCodice()));
		}
		if (filter.getFilter().getDescrizione() != null) {
			lCriteria.add(Restrictions.ilike("descrizione", filter.getFilter().getDescrizione(),MatchMode.ANYWHERE));
		}
		return lCriteria;
	}

}