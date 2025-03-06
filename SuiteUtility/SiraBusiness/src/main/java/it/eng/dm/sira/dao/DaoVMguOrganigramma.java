package it.eng.dm.sira.dao;

import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.dm.sira.entity.VMguOrganigramma;
import it.eng.dm.sira.service.hibernate.SiraHibernate;
import it.eng.spring.utility.SpringAppContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class DaoVMguOrganigramma {

	public TPagingList<VMguOrganigramma> search(TFilterFetch<VMguOrganigramma> filter) throws Exception {
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
			TPagingList<VMguOrganigramma> paginglist = new TPagingList<VMguOrganigramma>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, filter.getStartRow(), filter.getEndRow())) {
				VMguOrganigramma oggetto = ((VMguOrganigramma) obj);
				if (oggetto.getIdPadre() != null
						&& (filter.getFilter().getDescrizione() != null || filter.getFilter().getDominio() != null)) {
					VMguOrganigramma padre = getInSession(oggetto.getIdPadre(), session);
					oggetto.setSettorePadre(padre != null ? padre.getDescrizione() : "");
				}
				paginglist.addData(oggetto);
			}
			return paginglist;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	public VMguOrganigramma get(Long idDominio) throws Exception {
		Session session = null;
		VMguOrganigramma result = null;
		try {
			SpringAppContext.getContext().getBean(SiraHibernate.class).initSubject();
			session = HibernateUtil.begin();
			VMguOrganigramma org = getInSession(idDominio, session);
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
		return result;
	}

	public VMguOrganigramma getInSession(Long idDominio, Session session) throws Exception {
		VMguOrganigramma cat = (VMguOrganigramma) session.get(VMguOrganigramma.class, idDominio);
		return cat;
	}

	public Long count(TFilterFetch<VMguOrganigramma> filter) throws Exception {
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

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<VMguOrganigramma> filter) {
		Criteria lCriteria = session.createCriteria(VMguOrganigramma.class);
		if (filter.getFilter().getDominio() != null) {
			lCriteria.add(Restrictions.eq("dominio", filter.getFilter().getDominio()));
		}
		if (filter.getFilter().getDescrizione() != null) {
			lCriteria.add(Restrictions.ilike("descrizione", filter.getFilter().getDescrizione()));
		}
		if (filter.getFilter().getIdPadre() != null) {
			if (filter.getFilter().getIdPadre() != -1) {
				lCriteria.add(Restrictions.eq("idPadre", filter.getFilter().getIdPadre()));
			} else {
				lCriteria.add(Restrictions.isNull("idPadre"));
			}
		}
		if (filter.getFilter().getStato() != null) {
			lCriteria.add(Restrictions.eq("stato", filter.getFilter().getStato()));
		}
		return lCriteria;
	}

}