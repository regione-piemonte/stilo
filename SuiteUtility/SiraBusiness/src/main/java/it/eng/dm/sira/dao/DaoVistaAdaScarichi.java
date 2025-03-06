package it.eng.dm.sira.dao;

import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.DaoGenericOperations;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.core.business.converter.UtilPopulate;
import it.eng.dm.sira.dao.converter.VistaAdaScarichiBeanToVistaAdaScarichi;
import it.eng.dm.sira.dao.converter.VistaAdaScarichiToVistaAdaScarichiBean;
import it.eng.dm.sira.entity.VistaAdaScarichi;
import it.eng.dm.sira.service.hibernate.SiraHibernate;
import it.eng.dm.sirabusiness.bean.SiraVistaAdaScarichiBean;
import it.eng.spring.utility.SpringAppContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;

@Service(name = "DaoVistaAdaScarichi")
public class DaoVistaAdaScarichi extends DaoGenericOperations<SiraVistaAdaScarichiBean> {

	@Override
	@Operation(name = "save")
	public SiraVistaAdaScarichiBean save(SiraVistaAdaScarichiBean bean) throws Exception {
		Session session = null;
		try {
			SpringAppContext.getContext().getBean(SiraHibernate.class).initSubject();
			session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			VistaAdaScarichi toInsert = (VistaAdaScarichi) UtilPopulate.populate(bean, VistaAdaScarichi.class,
					new VistaAdaScarichiBeanToVistaAdaScarichi());
			session.save(toInsert);
			session.flush();
			bean = (SiraVistaAdaScarichiBean) UtilPopulate.populate(toInsert, SiraVistaAdaScarichiBean.class,
					new VistaAdaScarichiToVistaAdaScarichiBean());
			lTransaction.commit();
			return bean;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	@Override
	@Operation(name = "search")
	public TPagingList<SiraVistaAdaScarichiBean> search(TFilterFetch<SiraVistaAdaScarichiBean> filter) throws Exception {
		Session session = null;
		try {
			SpringAppContext.getContext().getBean(SiraHibernate.class).initSubject();
			session = HibernateUtil.begin();
			// Creo i criteri di ricerca
			Criteria criteria = this.buildHibernateCriteriaByFilter(session, filter);
			// Conto i record totali
			Long count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
			if (count == null) {
				count = new Long(0);
			}
			Integer startRow = (filter != null && filter.getStartRow() != null) ? filter.getStartRow() : 0;
			Integer endRow = (filter != null && filter.getEndRow() != null) ? filter.getEndRow() : startRow + count.intValue() - 1;
			// Creo l'oggetto paginatore
			TPagingList<SiraVistaAdaScarichiBean> paginglist = new TPagingList<SiraVistaAdaScarichiBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, filter.getStartRow(), filter.getEndRow())) {
				SiraVistaAdaScarichiBean bean = (SiraVistaAdaScarichiBean) UtilPopulate.populate((VistaAdaScarichi) obj,
						SiraVistaAdaScarichiBean.class, new VistaAdaScarichiToVistaAdaScarichiBean());
				paginglist.addData(bean);
			}
			return paginglist;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	@Operation(name = "get")
	public SiraVistaAdaScarichiBean get(String idAda) throws Exception {
		Session session = null;
		SiraVistaAdaScarichiBean result = null;
		try {
			SpringAppContext.getContext().getBean(SiraHibernate.class).initSubject();
			session = HibernateUtil.begin();
			VistaAdaScarichi mgo = (VistaAdaScarichi) session.get(VistaAdaScarichi.class, idAda);
			result = (SiraVistaAdaScarichiBean) UtilPopulate.populate(mgo, SiraVistaAdaScarichiBean.class,
					new VistaAdaScarichiToVistaAdaScarichiBean());
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
		return result;
	}

	@Override
	@Operation(name = "update")
	public SiraVistaAdaScarichiBean update(SiraVistaAdaScarichiBean bean) throws Exception {
		Session session = null;
		try {
			SpringAppContext.getContext().getBean(SiraHibernate.class).initSubject();
			session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			VistaAdaScarichi toInsert = (VistaAdaScarichi) UtilPopulate.populate(bean, VistaAdaScarichi.class,
					new VistaAdaScarichiBeanToVistaAdaScarichi());
			session.update(toInsert);
			session.flush();
			bean = (SiraVistaAdaScarichiBean) UtilPopulate.populate(toInsert, SiraVistaAdaScarichiBean.class,
					new VistaAdaScarichiToVistaAdaScarichiBean());
			lTransaction.commit();
			return bean;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	@Override
	@Operation(name = "delete")
	public void delete(SiraVistaAdaScarichiBean bean) throws Exception {
		Session session = null;
		try {
			SpringAppContext.getContext().getBean(SiraHibernate.class).initSubject();
			session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			VistaAdaScarichi toInsert = (VistaAdaScarichi) UtilPopulate.populate(bean, VistaAdaScarichi.class,
					new VistaAdaScarichiToVistaAdaScarichiBean());
			session.delete(toInsert);
			session.flush();
			lTransaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}

	}

	@Override
	@Operation(name = "forcedelete")
	public void forcedelete(SiraVistaAdaScarichiBean bean) throws Exception {
		Session session = null;
		try {
			SpringAppContext.getContext().getBean(SiraHibernate.class).initSubject();
			session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			VistaAdaScarichi toInsert = (VistaAdaScarichi) UtilPopulate.populate(bean, VistaAdaScarichi.class,
					new VistaAdaScarichiBeanToVistaAdaScarichi());
			session.delete(toInsert);
			session.flush();
			lTransaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<SiraVistaAdaScarichiBean> filter) {
		Criteria lCriteria = session.createCriteria(VistaAdaScarichi.class);
		return lCriteria;
	}

}