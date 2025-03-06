package it.eng.dm.sira.dao;

import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.core.business.converter.UtilPopulate;
import it.eng.dm.sira.dao.converter.RelProcessTipoAreaEnteBeanToTRelProcessTipoAreaEnte;
import it.eng.dm.sira.dao.converter.TRelProcessTipoAreaEnteToRelProcessTipoAreaEnteBean;
import it.eng.dm.sira.entity.TRelProcessTipoAreaEnte;
import it.eng.dm.sira.service.bean.RelProcessTipoAreaEnteBean;
import it.eng.dm.sira.service.hibernate.SiraHibernate;
import it.eng.spring.utility.SpringAppContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class DaoTRelProcessTipoAreaEnte {
	
	public RelProcessTipoAreaEnteBean save(RelProcessTipoAreaEnteBean bean) throws Exception {
		Session session = null;
		try {
			SpringAppContext.getContext().getBean(SiraHibernate.class).initSubject();
			session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			TRelProcessTipoAreaEnte toInsert = (TRelProcessTipoAreaEnte) UtilPopulate.populate(bean, TRelProcessTipoAreaEnte.class, new RelProcessTipoAreaEnteBeanToTRelProcessTipoAreaEnte());
			session.save(toInsert);
			session.flush();			
			bean = (RelProcessTipoAreaEnteBean) UtilPopulate.populate(toInsert, RelProcessTipoAreaEnteBean.class, new TRelProcessTipoAreaEnteToRelProcessTipoAreaEnteBean(session));			
			lTransaction.commit();
			return bean;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	public RelProcessTipoAreaEnteBean get(String idRel) throws Exception {
		Session session = null;
		RelProcessTipoAreaEnteBean result = null;
		try {
			SpringAppContext.getContext().getBean(SiraHibernate.class).initSubject();
			session = HibernateUtil.begin();
			TRelProcessTipoAreaEnte rel = getInSession(idRel, session);
			result = (RelProcessTipoAreaEnteBean) UtilPopulate.populate(rel, RelProcessTipoAreaEnteBean.class, new TRelProcessTipoAreaEnteToRelProcessTipoAreaEnteBean(session));
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
		return result;
	}

	public TRelProcessTipoAreaEnte getInSession(String idRel, Session session) throws Exception {
		TRelProcessTipoAreaEnte cat = (TRelProcessTipoAreaEnte) session.get(TRelProcessTipoAreaEnte.class, idRel);
		return cat;
	}
	
	public RelProcessTipoAreaEnteBean update(RelProcessTipoAreaEnteBean bean) throws Exception {
		Session session = null;
		try {
			SpringAppContext.getContext().getBean(SiraHibernate.class).initSubject();
			session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			TRelProcessTipoAreaEnte toUpdate = (TRelProcessTipoAreaEnte) UtilPopulate.populate(bean, TRelProcessTipoAreaEnte.class, new RelProcessTipoAreaEnteBeanToTRelProcessTipoAreaEnte());
			toUpdate = updateInSession(toUpdate, session);
			bean = (RelProcessTipoAreaEnteBean) UtilPopulate.populate(toUpdate, RelProcessTipoAreaEnteBean.class, new TRelProcessTipoAreaEnteToRelProcessTipoAreaEnteBean(session));
			lTransaction.commit();
			return bean;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}		
	}

	public TRelProcessTipoAreaEnte updateInSession(TRelProcessTipoAreaEnte bean, Session session) throws Exception {
		session.update(bean);
		session.flush();
		return bean;
	}

	public TPagingList<RelProcessTipoAreaEnteBean> search(TFilterFetch<RelProcessTipoAreaEnteBean> filter) throws Exception {
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
			TPagingList<RelProcessTipoAreaEnteBean> paginglist = new TPagingList<RelProcessTipoAreaEnteBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, filter.getStartRow(), filter.getEndRow())) {
				RelProcessTipoAreaEnteBean bean = (RelProcessTipoAreaEnteBean) UtilPopulate.populate((TRelProcessTipoAreaEnte) obj, RelProcessTipoAreaEnteBean.class,
								new TRelProcessTipoAreaEnteToRelProcessTipoAreaEnteBean(session));
				paginglist.addData(bean);
			}
			return paginglist;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<RelProcessTipoAreaEnteBean> filter) {
		Criteria lCriteria = session.createCriteria(TRelProcessTipoAreaEnte.class);
		if (filter.getFilter().getIdProcess() != null) {
			lCriteria.add(Restrictions.eq("idProcess", filter.getFilter().getIdProcess()));
		}
		if (filter.getFilter().getIdArea() != null) {
			lCriteria.add(Restrictions.eq("idArea", filter.getFilter().getIdArea()));
		}
		if (filter.getFilter().getIdDominio() != null) {
			lCriteria.add(Restrictions.like("idDominio", "%"+filter.getFilter().getIdDominio()+"%"));
		}
		if (filter.getFilter().getIdTipologia() != null) {
			lCriteria.add(Restrictions.eq("idTipologia", filter.getFilter().getIdTipologia()));
		}
		if (filter.getFilter().getIdRel() != null) {
			lCriteria.add(Restrictions.eq("idRel", filter.getFilter().getIdRel()));
		}
		return lCriteria;
	}

}