package it.eng.dm.sira.dao;

import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.dm.sira.entity.VTipologieMotivazioni;
import it.eng.dm.sira.service.hibernate.SiraHibernate;
import it.eng.spring.utility.SpringAppContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class DaoTipologieMotivazioni {

	public TPagingList<VTipologieMotivazioni> search(TFilterFetch<VTipologieMotivazioni> filter) throws Exception {
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
			TPagingList<VTipologieMotivazioni> paginglist = new TPagingList<VTipologieMotivazioni>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, filter.getStartRow(), filter.getEndRow())) {
				paginglist.addData((VTipologieMotivazioni) obj);
			}
			return paginglist;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<VTipologieMotivazioni> filter) {
		Criteria lCriteria = session.createCriteria(VTipologieMotivazioni.class);
		if (filter.getFilter().getAttivo() != null) {
			lCriteria.add(Restrictions.eq("attivo", filter.getFilter().getAttivo()));
		}
		if (filter.getFilter().getTipoFunzione() != null) {
			lCriteria.add(Restrictions.eq("tipoFunzione", filter.getFilter().getTipoFunzione()));
		}
		
		if (filter.getFilter().getCodRagAta() != null) {
			lCriteria.add(Restrictions.eq("codRagAta", filter.getFilter().getCodRagAta()));
		}
		return lCriteria;
	}

}