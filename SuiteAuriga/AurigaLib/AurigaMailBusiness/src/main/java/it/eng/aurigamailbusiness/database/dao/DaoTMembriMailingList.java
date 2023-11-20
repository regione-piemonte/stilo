/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import it.eng.aurigamailbusiness.bean.TMembriMailingListBean;
import it.eng.aurigamailbusiness.converters.TMembriMailingListBeanToTMembriMailingList;
import it.eng.aurigamailbusiness.converters.TMembriMailingListToTMembriMailingListBean;
import it.eng.aurigamailbusiness.database.mail.TMembriMailingList;
import it.eng.aurigamailbusiness.database.mail.TRubricaEmail;
import it.eng.aurigamailbusiness.exception.AurigaMailBusinessException;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.DaoGenericOperations;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TOrderBy;
import it.eng.core.business.TOrderBy.OrderByType;
import it.eng.core.business.TPagingList;
import it.eng.core.business.converter.UtilPopulate;
import it.eng.util.ListUtil;

@Service(name = "DaoTMembriMailingList")
public class DaoTMembriMailingList extends DaoGenericOperations<TMembriMailingListBean> {

	public DaoTMembriMailingList() {
	}

	private static String errorSearch = "Nessun filtro impostato. Interrotta la ricerca altrimenti sarebbero restituite tutte le mailing list che fanno parte della rubrica";

	@Operation(name = "findAllMembriMailingList")
	public TPagingList<TMembriMailingListBean> findAllMembriMailingList(TMembriMailingListBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();

			List<TMembriMailingListBean> data = new ArrayList<TMembriMailingListBean>(
					findAllMembriSingoliMailingListRicorsiva(session, bean.getIdVoceRubricaMailingList()).values());

			// Creo l'oggetto paginatore
			TPagingList<TMembriMailingListBean> paginglist = new TPagingList<TMembriMailingListBean>();
			paginglist.setTotalRows(data.size());
			paginglist.setStartRow(0);
			paginglist.setEndRow(data.size() - 1);
			paginglist.setData(data);

			return paginglist;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	public LinkedHashMap<String, TMembriMailingListBean> findAllMembriSingoliMailingListRicorsiva(Session session, String idVoceRubricaMailingList)
			throws Exception {
		LinkedHashMap<String, TMembriMailingListBean> membri = new LinkedHashMap<String, TMembriMailingListBean>();
		if (StringUtils.isNotBlank(idVoceRubricaMailingList)) {
			Criteria criteria = session.createCriteria(TMembriMailingList.class);
			criteria.add(Restrictions.eq("TRubricaEmailByIdVoceRubricaMailingList.idVoceRubricaEmail", idVoceRubricaMailingList));
			for (Object obj : criteria.list()) {
				TMembriMailingListBean lTMembriMailingListBean = (TMembriMailingListBean) UtilPopulate.populate((TMembriMailingList) obj,
						TMembriMailingListBean.class, new TMembriMailingListToTMembriMailingListBean());
				TRubricaEmail lTRubricaEmail = (TRubricaEmail) session.load(TRubricaEmail.class, lTMembriMailingListBean.getIdVoceRubricaMembro());
				if (lTRubricaEmail.isFlgMailingList()) {
					membri.putAll(findAllMembriSingoliMailingListRicorsiva(session, lTRubricaEmail.getIdVoceRubricaEmail()));
				} else
					membri.put(lTRubricaEmail.getIdVoceRubricaEmail(), lTMembriMailingListBean);
			}
		}
		return membri;
	}

	public boolean isPresenteInMailingListRicorsiva(Session session, String idVoceRubricaMailingList, String idVoceRubricaToFind) throws Exception {
		Criteria criteria = session.createCriteria(TMembriMailingList.class);
		criteria.add(Restrictions.eq("TRubricaEmailByIdVoceRubricaMailingList.idVoceRubricaEmail", idVoceRubricaMailingList));
		for (Object obj : criteria.list()) {
			TMembriMailingListBean lTMembriMailingListBean = (TMembriMailingListBean) UtilPopulate.populate((TMembriMailingList) obj,
					TMembriMailingListBean.class, new TMembriMailingListToTMembriMailingListBean());
			TRubricaEmail lTRubricaEmailMembro = (TRubricaEmail) session.load(TRubricaEmail.class, lTMembriMailingListBean.getIdVoceRubricaMembro());
			if (lTRubricaEmailMembro.getIdVoceRubricaEmail().equals(idVoceRubricaToFind) || (lTRubricaEmailMembro.isFlgMailingList()
					&& isPresenteInMailingListRicorsiva(session, lTRubricaEmailMembro.getIdVoceRubricaEmail(), idVoceRubricaToFind))) {
				return true;
			}
		}
		return false;
	}

	@Override
	@Operation(name = "search")
	public TPagingList<TMembriMailingListBean> search(TFilterFetch<TMembriMailingListBean> filter) throws Exception {
		Session session = null;
		try {
			if (filter == null) {
				throw new AurigaMailBusinessException(errorSearch);
			}
			session = HibernateUtil.begin();
			// Creo i criteri di ricerca
			Criteria criteria = this.buildHibernateCriteriaByFilter(session, filter);
			// Conto i record totali
			Long count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
			Integer startRow = filter.getStartRow() != null ? filter.getStartRow() : 0;
			Integer endRow = filter != null && filter.getEndRow() != null ? filter.getEndRow() : startRow + count.intValue() - 1;
			// Creo l'oggetto paginatore
			TPagingList<TMembriMailingListBean> paginglist = new TPagingList<TMembriMailingListBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			List<TMembriMailingListBean> data = new ArrayList<TMembriMailingListBean>();
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
				TMembriMailingListBean bean = (TMembriMailingListBean) UtilPopulate.populate((TMembriMailingList) obj, TMembriMailingListBean.class,
						new TMembriMailingListToTMembriMailingListBean());
				data.add(bean);
			}
			paginglist.setData(data);
			return paginglist;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	@Override
	@Operation(name = "save")
	public TMembriMailingListBean save(TMembriMailingListBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			bean = saveInSession(bean, session);
			transaction.commit();
			return bean;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	public TMembriMailingListBean saveInSession(TMembriMailingListBean bean, Session session) throws Exception {
		TMembriMailingList toInsert = (TMembriMailingList) UtilPopulate.populate(bean, TMembriMailingList.class,
				new TMembriMailingListBeanToTMembriMailingList());
		session.save(toInsert);
		session.flush();
		bean = (TMembriMailingListBean) UtilPopulate.populate(toInsert, TMembriMailingListBean.class, new TMembriMailingListToTMembriMailingListBean());
		return bean;
	}

	@Override
	@Operation(name = "update")
	public TMembriMailingListBean update(TMembriMailingListBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TMembriMailingList toInsert = (TMembriMailingList) UtilPopulate.populate(bean, TMembriMailingList.class,
					new TMembriMailingListBeanToTMembriMailingList());
			session.update(toInsert);
			session.flush();
			bean = (TMembriMailingListBean) UtilPopulate.populate(toInsert, TMembriMailingListBean.class, new TMembriMailingListToTMembriMailingListBean());
			transaction.commit();
			return bean;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	@Override
	@Operation(name = "delete")
	public void delete(TMembriMailingListBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TMembriMailingList toInsert = (TMembriMailingList) UtilPopulate.populate(bean, TMembriMailingList.class,
					new TMembriMailingListBeanToTMembriMailingList());
			session.delete(toInsert);
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	public void deleteInSession(TMembriMailingListBean bean, Session session) throws Exception {
		TMembriMailingList toInsert = (TMembriMailingList) UtilPopulate.populate(bean, TMembriMailingList.class,
				new TMembriMailingListBeanToTMembriMailingList());
		session.delete(toInsert);
		session.flush();
	}

	@Override
	@Operation(name = "forcedelete")
	public void forcedelete(TMembriMailingListBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TMembriMailingList toInsert = (TMembriMailingList) UtilPopulate.populate(bean, TMembriMailingList.class,
					new TMembriMailingListBeanToTMembriMailingList());
			session.delete(toInsert);
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<TMembriMailingListBean> filter) throws AurigaMailBusinessException {
		if (filter == null || filter.getFilter() == null || filter.getFilter().getIdVoceRubricaMailingList() == null) {
			throw new AurigaMailBusinessException(errorSearch);
		}
		Criteria lCriteria = session.createCriteria(TMembriMailingList.class);
		if (filter.getFilter().getIdVoceRubricaMailingList() != null) {
			lCriteria.add(Restrictions.eq("TRubricaEmailByIdVoceRubricaMailingList.idVoceRubricaEmail", filter.getFilter().getIdVoceRubricaMailingList()));
		}
		if (ListUtil.isNotEmpty(filter.getOrders())) {
			for (TOrderBy tOrderBy : filter.getOrders()) {
				if (tOrderBy.getType().equals(OrderByType.ASCENDING)) {
					lCriteria.addOrder(Order.asc(tOrderBy.getPropname()));
				} else {
					lCriteria.addOrder(Order.desc(tOrderBy.getPropname()));
				}
			}
		}
		return lCriteria;
	}
}