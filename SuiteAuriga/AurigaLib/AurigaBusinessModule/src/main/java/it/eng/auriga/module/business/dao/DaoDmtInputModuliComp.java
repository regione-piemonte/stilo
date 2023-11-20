/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.dao.beans.InputModuliCompBean;
import it.eng.auriga.module.business.dao.beans.TSerializableList;
import it.eng.auriga.module.business.dao.beansconverters.DmtInputModuliCompToInputModuliCompBeanConverter;
import it.eng.auriga.module.business.dao.beansconverters.InputModuliCompBeanToDmtInputModuliCompConverter;
import it.eng.auriga.module.business.entity.DmtInputModuliComp;
import it.eng.auriga.module.business.entity.DmtInputModuliCompId;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.DaoGenericOperations;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.core.business.converter.UtilPopulate;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;

@Service(name="DaoDmtInputModuliComp")
public class DaoDmtInputModuliComp extends DaoGenericOperations<InputModuliCompBean>{
	
	private static final Logger logger = Logger.getLogger(DaoDmtInputModuliComp.class);
	
	public DaoDmtInputModuliComp() {

	}

	@Operation(name="search")	
	public TPagingList<InputModuliCompBean> searchWithLogin(AurigaLoginBean pAurigaLoginBean, TFilterFetch<InputModuliCompBean> filter)
	throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		return this.search(filter);
	}
	
	@Override
	public TPagingList<InputModuliCompBean> search(TFilterFetch<InputModuliCompBean> filter) throws Exception {		
		Session session = null;
		try {			
			session = HibernateUtil.begin();			
			Criteria criteria = this.buildHibernateCriteriaByFilter(session, filter);				
			Long count = (Long)criteria.setProjection(Projections.rowCount()).uniqueResult();			
			Integer startRow = (filter != null && filter.getStartRow() != null) ? filter.getStartRow() : 0;
			Integer endRow = (filter != null && filter.getEndRow() != null) ? filter.getEndRow() : startRow + count.intValue() - 1;			
			//Creo l'oggetto paginatore
			TPagingList<InputModuliCompBean> paginglist = new TPagingList<InputModuliCompBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);					
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for(Object obj : HibernateUtil.pagingByCriteria(criteria, filter.getStartRow(), filter.getEndRow())) {
				InputModuliCompBean bean = (InputModuliCompBean) UtilPopulate.populate((DmtInputModuliComp) obj, InputModuliCompBean.class, new DmtInputModuliCompToInputModuliCompBeanConverter());
				paginglist.addData(bean);
			}							
			return paginglist;			
		} catch(Exception e) {
			logger.error("Errore " + e.getMessage(), e);
			throw e;
		} finally {
			HibernateUtil.release(session);
		}		
	}
	
	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<InputModuliCompBean> filter) throws Exception {
		Criteria criteria = session.createCriteria(DmtInputModuliComp.class);
		if(filter != null) {
			InputModuliCompBean bean = filter.getFilter();
			if(bean != null) {		
				if(bean.getIdModello() != null && !bean.getIdModello().equals("")) { 
					criteria.add(Restrictions.eq("id.idModello", bean.getIdModello()));				        		
				}				
			}
			HibernateUtil.addOrderCriteria(criteria, filter.getOrders());			
		}			
		return criteria;		
	}
	
	@Operation(name="get")	
	public InputModuliCompBean getWithLogin(AurigaLoginBean pAurigaLoginBean, InputModuliCompBean bean) throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		return this.get(bean);
	}
	
	public InputModuliCompBean get(InputModuliCompBean bean)	throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();		
			DmtInputModuliCompId lDmtInputModuliCompId = new DmtInputModuliCompId(bean.getIdModello(), bean.getNomeInput(), bean.getFlgMultivalore());
			DmtInputModuliComp lDmtInputModuliComp = (DmtInputModuliComp) session.get(DmtInputModuliComp.class, lDmtInputModuliCompId);
			return (InputModuliCompBean) UtilPopulate.populate(lDmtInputModuliComp, InputModuliCompBean.class, new DmtInputModuliCompToInputModuliCompBeanConverter());							
		} catch(Exception e) {
			logger.error("Errore " + e.getMessage(), e);
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	@Operation(name="save")	
	public InputModuliCompBean saveWithLogin(AurigaLoginBean pAurigaLoginBean, InputModuliCompBean bean) throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		return this.save(bean);
	}
	
	@Override
	public InputModuliCompBean save(InputModuliCompBean bean) throws Exception {
		Session session = null;
		Transaction ltransaction = null;
		try {			
			session = HibernateUtil.begin();	
			ltransaction = session.beginTransaction();
			if(bean != null) {					
				DmtInputModuliComp lDmtInputModuliComp = (DmtInputModuliComp) UtilPopulate.populate(bean, DmtInputModuliComp.class, new InputModuliCompBeanToDmtInputModuliCompConverter());			
				session.save(lDmtInputModuliComp);				
			}								
			session.flush();
			ltransaction.commit();
			return bean;
		} catch(Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}		
	}
	
	@Operation(name="update")	
	public InputModuliCompBean updateWithLogin(AurigaLoginBean pAurigaLoginBean, InputModuliCompBean bean) throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		return this.update(bean);
	}

	@Override
	public InputModuliCompBean update(InputModuliCompBean bean) throws Exception {
		Session session = null;
		Transaction ltransaction = null;
		try {			
			session = HibernateUtil.begin();
			ltransaction = session.beginTransaction();
			if(bean != null) {					
				DmtInputModuliComp lDmtInputModuliComp = (DmtInputModuliComp) UtilPopulate.populate(bean, DmtInputModuliComp.class, new InputModuliCompBeanToDmtInputModuliCompConverter());			
				session.update(lDmtInputModuliComp);				
			}							
			session.flush();
			ltransaction.commit();
			return bean;
		} catch(Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}		
	}
	
	@Operation(name="delete")	
	public void deleteWithLogin(AurigaLoginBean pAurigaLoginBean, InputModuliCompBean bean) throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		this.delete(bean);
	}

	@Override
	public void delete(InputModuliCompBean bean) throws Exception {		
		Session session = null;
		Transaction ltransaction = null;
		try {
			session = HibernateUtil.begin();
			ltransaction = session.beginTransaction();
			if(bean != null) {					
				DmtInputModuliComp lDmtInputModuliComp = (DmtInputModuliComp) UtilPopulate.populate(bean, DmtInputModuliComp.class, new InputModuliCompBeanToDmtInputModuliCompConverter());			
				session.delete(lDmtInputModuliComp);				
			}				
			session.flush();
			ltransaction.commit();
		} catch(Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}				
	}

	@Operation(name="forcedelete")	
	public void forcedeleteWithLogin(AurigaLoginBean pAurigaLoginBean, InputModuliCompBean bean) throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		this.forcedelete(bean);
	}
	
	@Override
	public void forcedelete(InputModuliCompBean bean) throws Exception {
		this.delete(bean);	
	}
	
	@Operation(name = "salvaInputFileModello")
	public void salvaInputFileModelloWithLogin(AurigaLoginBean loginBean, String idModello, TSerializableList<InputModuliCompBean> lista) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(loginBean.getSchema());
		SubjectUtil.subject.set(subject);
		salvaInputFileModello(idModello, lista);
	}
	
	public void salvaInputFileModello(String idModello, TSerializableList<InputModuliCompBean> lista) throws Exception {
		Session session = null;
		Transaction ltransaction = null;
		try {
			session = HibernateUtil.begin();
			ltransaction = session.beginTransaction();
			salvaInputFileModelloInSession(session, idModello, lista);				
			session.flush();
			ltransaction.commit();
		} catch(Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}		
	}
	
	public boolean areEqual(Boolean value1, Boolean value2) {
		boolean boolValue1 = value1 != null && value1;
		boolean boolValue2 = value2 != null && value2;		
		return (boolValue1 && boolValue2) || (!boolValue1 && !boolValue2);
	}
	
	public boolean areEqual(String value1, String value2) {
		String strValue1 = value1 != null ? value1 : "";
		String strValue2 = value2 != null ? value2 : "";		
		return strValue1.equals(strValue2);
	}
	
	public void salvaInputFileModelloInSession(Session session, String idModello, TSerializableList<InputModuliCompBean> lista) throws Exception {
		// verifico se c'erano già delle associazioni precedenti su quel modello
		TFilterFetch<InputModuliCompBean> filter = new TFilterFetch<InputModuliCompBean>();
		InputModuliCompBean filterBean = new InputModuliCompBean();
		filterBean.setIdModello(StringUtils.isNotBlank(idModello) ? new BigDecimal(idModello) : null);
		filter.setFilter(filterBean);				
		TPagingList<InputModuliCompBean> lTPagingListInputModuliComp = search(filter);	
		// devo fare il match delle associazioni vecchie con gli input recuperati a partire dal file del modello, mantenendo la profilatura dove possibile
		LinkedHashMap<String, InputModuliCompBean> mappaDmtInputModuliCompSalvati = new LinkedHashMap<String, InputModuliCompBean>();
		if(lTPagingListInputModuliComp != null && lTPagingListInputModuliComp.getData() != null) {
			for(int i = 0; i < lTPagingListInputModuliComp.getData().size(); i++) {							
				InputModuliCompBean lInputModuliCompBean = lTPagingListInputModuliComp.getData().get(i);							
				mappaDmtInputModuliCompSalvati.put(lInputModuliCompBean.getNomeInput(), lInputModuliCompBean);
			}
		}			
		if(lista != null && lista.getData() != null) {
			for(InputModuliCompBean lInputModuliCompBean : lista.getData()) {
				InputModuliCompBean lInputModuliCompSalvatoBean = mappaDmtInputModuliCompSalvati.get(lInputModuliCompBean.getNomeInput());
				// se trovo delle associazioni vecchie che corrispondono ai nuovi input ne mantengo la profilatura e i dati relativi al barcode (se è un immagine) 
				if(lInputModuliCompSalvatoBean != null) {
					// solo se le vecchie associazioni sono dello stesso tipo mantengo la profilatura (devo confrontare complex e flgMultivalore)
					// le associazioni sono dello stesso tipo se sono entrambe complesse o non complesse e hanno lo stesso valore del flag multivalore
					if ((("COMPLEX".equalsIgnoreCase(lInputModuliCompBean.getAttrType()) && "COMPLEX".equalsIgnoreCase(lInputModuliCompSalvatoBean.getAttrType())) ||
							(!"COMPLEX".equalsIgnoreCase(lInputModuliCompBean.getAttrType()) && !"COMPLEX".equalsIgnoreCase(lInputModuliCompSalvatoBean.getAttrType()))) &&
							areEqual(lInputModuliCompBean.getFlgMultivalore(), lInputModuliCompSalvatoBean.getFlgMultivalore())) {
						lInputModuliCompBean.setAttrName(lInputModuliCompSalvatoBean.getAttrName());
						lInputModuliCompBean.setAttrType(lInputModuliCompSalvatoBean.getAttrType());
						lInputModuliCompBean.setNroColonna(lInputModuliCompSalvatoBean.getNroColonna());
						lInputModuliCompBean.setFlgBarcode(lInputModuliCompSalvatoBean.getFlgBarcode());
						lInputModuliCompBean.setTipoBarcode(lInputModuliCompSalvatoBean.getTipoBarcode());													
					}
				} 
			}
		}		
		// salvo le nuove associazioni del modello, dopo aver cancellato prima quelle vecchie
		salvaProfilaturaInSession(session, idModello, lista);				
	}
	
	@Operation(name = "salvaProfilatura")
	public void salvaProfilaturaWithLogin(AurigaLoginBean loginBean, String idModello, TSerializableList<InputModuliCompBean> lista) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(loginBean.getSchema());
		SubjectUtil.subject.set(subject);
		salvaProfilatura(idModello, lista);	
	}
	
	public void salvaProfilatura(String idModello, TSerializableList<InputModuliCompBean> lista) throws Exception {
		Session session = null;
		Transaction ltransaction = null;
		try {
			session = HibernateUtil.begin();
			ltransaction = session.beginTransaction();
			salvaProfilaturaInSession(session, idModello, lista);				
			session.flush();
			ltransaction.commit();
		} catch(Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}			
	}
	
	public void salvaProfilaturaInSession(Session session, String idModello, TSerializableList<InputModuliCompBean> lista) throws Exception {		
		// verifico se c'erano già delle associazioni precedenti su quel modello
		TFilterFetch<InputModuliCompBean> filter = new TFilterFetch<InputModuliCompBean>();
		InputModuliCompBean filterBean = new InputModuliCompBean();
		filterBean.setIdModello(StringUtils.isNotBlank(idModello) ? new BigDecimal(idModello) : null);
		filter.setFilter(filterBean);				
		TPagingList<InputModuliCompBean> lTPagingListInputModuliComp = search(filter);		
		// cancello le vecchie associazioni
		if(lTPagingListInputModuliComp != null && lTPagingListInputModuliComp.getData() != null) {
			for(int i = 0; i < lTPagingListInputModuliComp.getData().size(); i++) {
				InputModuliCompBean lInputModuliCompBean = lTPagingListInputModuliComp.getData().get(i);
				DmtInputModuliComp lDmtInputModuliComp = (DmtInputModuliComp) UtilPopulate.populate(lInputModuliCompBean, DmtInputModuliComp.class, new InputModuliCompBeanToDmtInputModuliCompConverter());			
				session.delete(lDmtInputModuliComp);	
			}
		}			
		// salvo le nuove associazioni
		if(lista != null && lista.getData() != null) {
			for(InputModuliCompBean lInputModuliCompBean : lista.getData()) {
				DmtInputModuliComp lDmtInputModuliComp = (DmtInputModuliComp) UtilPopulate.populate(lInputModuliCompBean, DmtInputModuliComp.class, new InputModuliCompBeanToDmtInputModuliCompConverter());			
				session.save(lDmtInputModuliComp);			 				
			}
		}
	}
	
}

