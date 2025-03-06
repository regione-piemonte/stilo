package it.eng.dm.sira.dao;

import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.DaoGenericOperations;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.core.business.converter.UtilPopulate;
import it.eng.dm.sira.dao.converter.ComuniItaliaBeanToComuniItalia;
import it.eng.dm.sira.dao.converter.ComuniItaliaToComuniItaliaBean;
import it.eng.dm.sira.entity.ComuniItalia;
import it.eng.dm.sira.service.bean.SiraComuniItaliaBean;
import it.eng.dm.sira.service.hibernate.SiraHibernate;
import it.eng.spring.utility.SpringAppContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

@Service(name = "DaoComuniItalia")
public class DaoComuniItalia extends DaoGenericOperations<SiraComuniItaliaBean> {

	@Override
	@Operation(name = "save")
	public SiraComuniItaliaBean save(SiraComuniItaliaBean bean) throws Exception {
		Session session = null;
		try {
			SpringAppContext.getContext().getBean(SiraHibernate.class).initSubject();
			session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			ComuniItalia toInsert = (ComuniItalia) UtilPopulate.populate(bean, ComuniItalia.class, new ComuniItaliaBeanToComuniItalia());
			session.save(toInsert);
			session.flush();
			lTransaction.commit();
			bean = (SiraComuniItaliaBean) UtilPopulate.populate(toInsert, SiraComuniItaliaBean.class, new ComuniItaliaToComuniItaliaBean());
			return bean;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	@Override
	@Operation(name = "search")
	public TPagingList<SiraComuniItaliaBean> search(TFilterFetch<SiraComuniItaliaBean> filter) throws Exception {
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
			TPagingList<SiraComuniItaliaBean> paginglist = new TPagingList<SiraComuniItaliaBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, filter.getStartRow(), filter.getEndRow())) {
				SiraComuniItaliaBean bean = (SiraComuniItaliaBean) UtilPopulate.populate((ComuniItalia) obj, SiraComuniItaliaBean.class,
						new ComuniItaliaToComuniItaliaBean());
				paginglist.addData(bean);
			}
			return paginglist;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}
	
	@Operation(name = "searchlikecomune")
	public TPagingList<SiraComuniItaliaBean> searchLikeComune(TFilterFetch<SiraComuniItaliaBean> filter) throws Exception {
		Session session = null;
		try {
			SpringAppContext.getContext().getBean(SiraHibernate.class).initSubject();
			session = HibernateUtil.begin();
			// Creo i criteri di ricerca
			Criteria criteria = session.createCriteria(ComuniItalia.class);
			if (filter.getFilter().getComune() != null) {
				criteria.add(Restrictions.ilike("comune", "%" + filter.getFilter().getComune()+ "%"));
			}
			if (filter.getFilter().getRegione() != null) {
				criteria.add(Restrictions.eq("regione", filter.getFilter().getRegione()));
			} 
			criteria.addOrder(Order.asc("comune"));
			// Conto i record totali
			Long count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
			if (count == null) {
				count = new Long(0);
			}
			Integer startRow = (filter != null && filter.getStartRow() != null) ? filter.getStartRow() : 0;
			Integer endRow = (filter != null && filter.getEndRow() != null) ? filter.getEndRow() : startRow + count.intValue() - 1;
			// Creo l'oggetto paginatore
			TPagingList<SiraComuniItaliaBean> paginglist = new TPagingList<SiraComuniItaliaBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, filter.getStartRow(), filter.getEndRow())) {
				SiraComuniItaliaBean bean = (SiraComuniItaliaBean) UtilPopulate.populate((ComuniItalia) obj, SiraComuniItaliaBean.class,
						new ComuniItaliaToComuniItaliaBean());
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
	public SiraComuniItaliaBean get(String idAda) throws Exception {
		Session session = null;
		SiraComuniItaliaBean result = null;
		try {
			SpringAppContext.getContext().getBean(SiraHibernate.class).initSubject();
			session = HibernateUtil.begin();
			ComuniItalia mgo = (ComuniItalia) session.get(ComuniItalia.class, idAda);
			result = (SiraComuniItaliaBean) UtilPopulate.populate(mgo, SiraComuniItaliaBean.class, new ComuniItaliaToComuniItaliaBean());
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
		return result;
	}

	@Override
	@Operation(name = "update")
	public SiraComuniItaliaBean update(SiraComuniItaliaBean bean) throws Exception {
		Session session = null;
		try {
			SpringAppContext.getContext().getBean(SiraHibernate.class).initSubject();
			session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			ComuniItalia toInsert = (ComuniItalia) UtilPopulate.populate(bean, ComuniItalia.class, new ComuniItaliaBeanToComuniItalia());
			session.update(toInsert);
			session.flush();
			bean = (SiraComuniItaliaBean) UtilPopulate.populate(toInsert, SiraComuniItaliaBean.class, new ComuniItaliaToComuniItaliaBean());
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
	public void delete(SiraComuniItaliaBean bean) throws Exception {
		Session session = null;
		try {
			SpringAppContext.getContext().getBean(SiraHibernate.class).initSubject();
			session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			ComuniItalia toInsert = (ComuniItalia) UtilPopulate.populate(bean, ComuniItalia.class, new ComuniItaliaToComuniItaliaBean());
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
	public void forcedelete(SiraComuniItaliaBean bean) throws Exception {
		Session session = null;
		try {
			SpringAppContext.getContext().getBean(SiraHibernate.class).initSubject();
			session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			ComuniItalia toInsert = (ComuniItalia) UtilPopulate.populate(bean, ComuniItalia.class, new ComuniItaliaBeanToComuniItalia());
			session.delete(toInsert);
			session.flush();
			lTransaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<SiraComuniItaliaBean> filter) {
		Criteria lCriteria = session.createCriteria(ComuniItalia.class);
		if (filter.getFilter().getComune() != null) {
			lCriteria.add(Restrictions.eq("comune", filter.getFilter().getComune()));
		}
		if (filter.getFilter().getProvincia() != null) {
			lCriteria.add(Restrictions.eq("provincia", filter.getFilter().getProvincia()));
		}
		if (filter.getFilter().getRegione() != null) {
			lCriteria.add(Restrictions.eq("regione", filter.getFilter().getRegione()));
		}
		if (filter.getFilter().getIstatCom() != null) {
			lCriteria.add(Restrictions.eq("id.istatCom", filter.getFilter().getIstatCom()));
		}
		if (filter.getFilter().getIstatProv() != null) {
			lCriteria.add(Restrictions.eq("id.istatProv", filter.getFilter().getIstatProv()));
		}
		if (filter.getFilter().getIstatReg() != null) {
			lCriteria.add(Restrictions.eq("id.istatReg", filter.getFilter().getIstatReg()));
		}
		return lCriteria;
	}

}