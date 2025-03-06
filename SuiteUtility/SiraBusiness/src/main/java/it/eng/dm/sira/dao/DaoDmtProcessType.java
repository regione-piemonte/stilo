package it.eng.dm.sira.dao;

import it.eng.core.business.HibernateUtil;
import it.eng.dm.sira.entity.DmtProcessTypes;
import it.eng.dm.sira.service.hibernate.SiraHibernate;
import it.eng.spring.utility.SpringAppContext;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class DaoDmtProcessType {

	public List<DmtProcessTypes> searchProcedimento(String idTipoProcedimento) throws Exception {
		List<DmtProcessTypes> procedimenti = new ArrayList<DmtProcessTypes>();
		Session session = null;
		try {
			SpringAppContext.getContext().getBean(SiraHibernate.class).initSubject();
			session = HibernateUtil.begin();
			String hql = null;
			if (idTipoProcedimento == null) {
				hql = "select t.idProcessType, t.nomeProcessType, t.desProcessType, t.flgAnn from DmtProcessTypes t where (t.flgAnn is null OR t.flgAnn <>1) and t.desProcessType is not null";
			} else {
				hql = "select t.idProcessType, t.nomeProcessType, t.desProcessType, t.flgAnn from DmtProcessTypes t where (t.flgAnn is null OR t.flgAnn <>1) and t.desProcessType is not null and t.idProcessType=" + idTipoProcedimento;
			}
			Query query = session.createQuery(hql);
			List<Object[]> results = query.list();
			for (Object[] proc : results) {
				DmtProcessTypes dest = new DmtProcessTypes();
				dest.setIdProcessType((Long) proc[0]);
				dest.setNomeProcessType((String) proc[1]);
				dest.setDesProcessType((String) proc[2]);
				procedimenti.add(dest);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
		return procedimenti;
	}
	
	public List<DmtProcessTypes> searchProcedimentoWithNameProc(String nameTipoProcedimento) throws Exception {
		List<DmtProcessTypes> procedimenti = new ArrayList<DmtProcessTypes>();
		Session session = null;
		try {
			SpringAppContext.getContext().getBean(SiraHibernate.class).initSubject();
			session = HibernateUtil.begin();
			String hql = null;
			if (nameTipoProcedimento == null && "".equals(nameTipoProcedimento)) {
				hql = "select t.idProcessType, t.nomeProcessType, t.desProcessType, t.flgAnn from DmtProcessTypes t where (t.flgAnn is null OR t.flgAnn <>1) and t.desProcessType is not null";
			} else {
				hql = "select t.idProcessType, t.nomeProcessType, t.desProcessType, t.flgAnn from DmtProcessTypes t where (t.flgAnn is null OR t.flgAnn <>1) and t.desProcessType is not null and t.nomeProcessType like '"+nameTipoProcedimento+"%'";
			}
			Query query = session.createQuery(hql);
			List<Object[]> results = query.list();
			for (Object[] proc : results) {
				DmtProcessTypes dest = new DmtProcessTypes();
				dest.setIdProcessType((Long) proc[0]);
				dest.setNomeProcessType((String) proc[1]);
				dest.setDesProcessType((String) proc[2]);
				procedimenti.add(dest);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
		return procedimenti;
	}

}
