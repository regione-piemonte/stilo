package it.eng.dm.sira.dao;

import it.eng.core.business.HibernateUtil;
import it.eng.dm.sira.entity.DmtRelazioneOst;
import it.eng.dm.sira.service.hibernate.SiraHibernate;
import it.eng.spring.utility.SpringAppContext;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class DaoDmtRelazioneOst {

	@SuppressWarnings("unchecked")
	public List<DmtRelazioneOst> searchRelazioniByProcedimento(Long idProc) throws Exception {
		List<DmtRelazioneOst> relazioni = new ArrayList<DmtRelazioneOst>();
		Session session = null;
		try {
			SpringAppContext.getContext().getBean(SiraHibernate.class).initSubject();
			session = HibernateUtil.begin();
			
			relazioni = session.createQuery(
				    "from DmtRelazioneOst r where r.idProc = ?")
				    .setLong(0, idProc)
				    .list();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
		return relazioni;
	}
	
	public DmtRelazioneOst get(Long idRelazione) throws Exception {
		Session session = null;
		DmtRelazioneOst result = null;
		try {
			SpringAppContext.getContext().getBean(SiraHibernate.class).initSubject();
			session = HibernateUtil.begin();
			result =  (DmtRelazioneOst) session.load(DmtRelazioneOst.class, idRelazione);
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
		return result;
	}
	
	
	public boolean update(DmtRelazioneOst relazione) throws Exception {
		Session session = null;
		
		try {
			SpringAppContext.getContext().getBean(SiraHibernate.class).initSubject();
			session = HibernateUtil.begin();
			
			String hql = "UPDATE DmtRelazioneOst set idCcostPadre = :idCcostPadre, idOstPadre = :idOstPadre, idCcostFiglio = :idCcostFiglio, idOstFiglio = :idOstFiglio " +
					"WHERE idRelazione = :idRelazione";
			Query query = session.createQuery(hql);
			
			
			
			query.setParameter("idCcostPadre", (relazione.getIdCcostPadre() == null) ? "null" : relazione.getIdCcostPadre());
			query.setParameter("idOstPadre", (relazione == null) ? "null" : relazione.getIdOstPadre());
			query.setParameter("idCcostFiglio", (relazione.getIdCcostFiglio() == null) ? "null" : relazione.getIdCcostFiglio());
			query.setParameter("idOstFiglio", (relazione.getIdOstFiglio() == null) ? "null" : relazione.getIdOstFiglio());
			query.setParameter("idRelazione", (relazione.getIdRelazione() == null) ? "null" : relazione.getIdRelazione());
			
			int result = query.executeUpdate();
			
			session.flush();
			if(result > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}		
	}
}
