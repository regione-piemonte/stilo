/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.dao.beans.PreferenceBean;
import it.eng.auriga.module.business.dao.beansconverters.PreferenceBeanToTUserPreferencesConverter;
import it.eng.auriga.module.business.dao.beansconverters.TUserPreferencesToPreferenceBeanConverter;
import it.eng.auriga.module.business.entity.TUserPreferences;
import it.eng.auriga.module.business.entity.TUserPreferencesId;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.DaoGenericOperations;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.core.business.converter.UtilPopulate;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;


/**
 * 
 * Classe che implementa le operazioni hibernate sulla tabella T_USER_PREFERENCES
 * @author upescato
 *
 */
@Service(name="DaoTUserPreferences")
public class DaoTUserPreferences extends DaoGenericOperations<PreferenceBean>{

	private static final Logger logger = Logger.getLogger(DaoTUserPreferences.class);

	public DaoTUserPreferences() {		
	}

	@Override
	public TPagingList<PreferenceBean> search(TFilterFetch<PreferenceBean> filter)
	throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();			
			//Creo i criteri di ricerca
			Criteria criteria = this.buildHibernateCriteriaByFilter(session, filter);			
			//Conto i record totali
			Long count = (Long)criteria.setProjection(Projections.rowCount()).uniqueResult();			
			Integer startRow = (filter != null && filter.getStartRow() != null) ? filter.getStartRow() : 0;
			Integer endRow = (filter != null && filter.getEndRow() != null) ? filter.getEndRow() : startRow + count.intValue() - 1;			
			//Creo l'oggetto paginatore
			TPagingList<PreferenceBean> paginglist = new TPagingList<PreferenceBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);			
			//Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			/*
			 * Se si vuole ottenere dalla ricerca solo un set di colonne, commentare le due righe precedenti 
			 * e decomentare le righe successive modificando le property richieste
			 */
//			  criteria.setProjection(Projections.projectionList().add(Projections.property("id"), "id")
//					   .add(Projections.property("settingTime"), "settingTime")
//					   .add(Projections.property("idUo"), "idUo")
//					   .add(Projections.property("flgVisibSottoUo"), "flgVisibSottoUo")
//					   .add(Projections.property("flgGestSottoUo"), "flgGestSottoUo"))
//					.setResultTransformer(Transformers.aliasToBean(TUserPreferences.class));
			
			for(Object obj : HibernateUtil.pagingByCriteria(criteria, filter.getStartRow(), filter.getEndRow())) {
				PreferenceBean bean = (PreferenceBean) UtilPopulate.populate((TUserPreferences) obj, PreferenceBean.class, new TUserPreferencesToPreferenceBeanConverter());
				paginglist.addData(bean);
			}			
				
			return paginglist;
		}catch(Exception e){
			logger.error("Errore " + e.getMessage(), e);
			throw e;
		}finally{
			HibernateUtil.release(session);
		}
	}

	@Operation(name="search")	
	public TPagingList<PreferenceBean> searchWithLogin(AurigaLoginBean pAurigaLoginBean, TFilterFetch<PreferenceBean> filter)
	throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		return this.search(filter);
	}
	
	@Override
	public PreferenceBean save(PreferenceBean bean) throws Exception {
		Session session = null;
		Transaction ltransaction = null;
		try {			
			session = HibernateUtil.begin();	
			ltransaction = session.beginTransaction();
			if(bean != null) {					
				TUserPreferencesId id = new TUserPreferencesId(bean.getUserid(), bean.getPrefKey(), bean.getPrefName());

				if(session.get(TUserPreferences.class, id) != null) {
					return bean;
				}

				//Creo la tupla di T_UTENTI
				TUserPreferences preference = (TUserPreferences) UtilPopulate.populate(bean, TUserPreferences.class, new PreferenceBeanToTUserPreferencesConverter(session));			
				session.save(preference);
				bean = (PreferenceBean) UtilPopulate.populate(preference, PreferenceBean.class, new TUserPreferencesToPreferenceBeanConverter());
			}								
			session.flush();
			ltransaction.commit();
			return bean;
		} catch(Exception e){
			throw e;
		}finally{
			HibernateUtil.release(session);
		}
	}	
	
	@Operation(name="save")	
	public PreferenceBean saveWithLogin(AurigaLoginBean pAurigaLoginBean, PreferenceBean bean) throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		return this.save(bean);
	}

	@Override
	public PreferenceBean update(PreferenceBean bean) throws Exception {
		Session session = null;
		Transaction ltransaction = null;
		try {			
			session = HibernateUtil.begin();
			ltransaction = session.beginTransaction();
			if(bean != null) {													
				TUserPreferences preference = (TUserPreferences) UtilPopulate.populateForUpdate(session, bean, TUserPreferences.class, new PreferenceBeanToTUserPreferencesConverter(session));
				session.update(preference);	
				bean = (PreferenceBean) UtilPopulate.populate(preference, PreferenceBean.class, new TUserPreferencesToPreferenceBeanConverter());
			}								
			session.flush();
			ltransaction.commit();
			return bean;
		} catch(Exception e){
			throw e;
		}finally{
			HibernateUtil.release(session);
		}
	}
	
	@Operation(name="update")	
	public PreferenceBean updateWithLogin(AurigaLoginBean pAurigaLoginBean, PreferenceBean bean) throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		return this.update(bean);
	}

	@Override
	public void delete(PreferenceBean bean) throws Exception {
		Session session = null;
		Transaction ltransaction = null;
		try {
			session = HibernateUtil.begin();
			ltransaction = session.beginTransaction();
			TUserPreferencesId id = new TUserPreferencesId(bean.getUserid(), bean.getPrefKey(), bean.getPrefName());
			TUserPreferences preference = (TUserPreferences) session.get(TUserPreferences.class, id);

			if(preference != null) {
				session.delete(preference);
			}

			session.flush();
			ltransaction.commit();
		}catch(Exception e){
			throw e;
		}finally{
			HibernateUtil.release(session);
		}		
	}
	
	@Operation(name="delete")	
	public void deleteWithLogin(AurigaLoginBean pAurigaLoginBean, PreferenceBean bean) throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		this.delete(bean);
	}

	@Override
	public void forcedelete(PreferenceBean bean) throws Exception {
		this.delete(bean);
	}

	@Operation(name="forcedelete")	
	public void forcedeleteWithLogin(AurigaLoginBean pAurigaLoginBean, PreferenceBean bean) throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		this.forcedelete(bean);
	}

	//Metodo privato per costruire i criteri di ricerca
	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<PreferenceBean> filter) throws Exception {		
		Criteria criteria = session.createCriteria(TUserPreferences.class);
		if(filter != null) {
			PreferenceBean bean = filter.getFilter();
			if(bean != null) {		
				if(StringUtils.isNotBlank(bean.getUserid())) {
					StringTokenizer st = new StringTokenizer(bean.getUserid(), ";");
					List<String> tokens = new ArrayList<String>();
					while(st.hasMoreTokens()) {
						tokens.add(st.nextToken().toString());
	        		}
					if(tokens.size() == 1) {
						criteria.add(Restrictions.eq("id.userid", tokens.get(0)));				        		
					} else if(tokens.size() == 2) {
						criteria.add(Restrictions.or(Restrictions.eq("id.userid", tokens.get(0)), Restrictions.eq("id.userid", tokens.get(1))));					
					}					
				}
				if(StringUtils.isNotBlank(bean.getPrefKey())) {
					criteria.add(Restrictions.eq("id.prefKey", bean.getPrefKey()));
				}
				if(StringUtils.isNotBlank(bean.getPrefName())) {
					criteria.add(Restrictions.eq("id.prefName", bean.getPrefName()));	
				}

				//TODO completare i filtri di ricerca, se necessario...
			}
			HibernateUtil.addOrderCriteria(criteria, filter.getOrders());			
		}			
		return criteria;
	}
	
	public PreferenceBean get(PreferenceBean bean)
	throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();			
		
			TUserPreferencesId id = new TUserPreferencesId();
			id.setPrefKey(bean.getPrefKey());
			id.setPrefName(bean.getPrefName());
			id.setUserid(bean.getUserid());
			TUserPreferences preference = (TUserPreferences) session.get(TUserPreferences.class, id);
			return (PreferenceBean) UtilPopulate.populate(preference, PreferenceBean.class, new TUserPreferencesToPreferenceBeanConverter());			
				
		}catch(Exception e){
			logger.error("Errore " + e.getMessage(), e);
			throw e;
		}finally{
			HibernateUtil.release(session);
		}
	}

	@Operation(name="get")	
	public PreferenceBean getWithLogin(AurigaLoginBean pAurigaLoginBean, PreferenceBean bean)
	throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		return this.get(bean);
	}

}
