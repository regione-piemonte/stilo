package it.eng.suiteutility.module.mimedb;

import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.DaoGenericOperations;
import it.eng.core.business.HibernateConstants;
import it.eng.core.business.KeyGenerator;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.core.business.converter.UtilPopulate;
import it.eng.suiteutility.module.mimedb.beanconverters.FormatoDigitaleBeanToTAnagFormatiDigConverter;
import it.eng.suiteutility.module.mimedb.beanconverters.TAnagFormatiDigToFormatoDigitaleBeanConverter;
import it.eng.suiteutility.module.mimedb.beans.FormatoDigitaleBean;
import it.eng.suiteutility.module.mimedb.beans.Mimetype;
import it.eng.suiteutility.module.mimedb.entity.TAnagFormatiDig;
import it.eng.suiteutility.module.mimedb.entity.TEstensioniFmtDig;
import it.eng.suiteutility.module.mimedb.entity.TEstensioniFmtDigId;
import it.eng.suiteutility.module.mimedb.entity.TMimetypeFmtDig;
import it.eng.suiteutility.module.mimedb.entity.TMimetypeFmtDigId;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * Classe Dao per la tabella di T_ANAG_FORMATI_DIG
 *
 */

@Service(name="MimeDaoAnagraficaFormatiDigitali")
public class DaoAnagraficaFormatiDigitali extends DaoGenericOperations<FormatoDigitaleBean>{

	public static final  Logger logger = LogManager.getLogger(DaoAnagraficaFormatiDigitali.class);

	public DaoAnagraficaFormatiDigitali() {		
	}

	@Operation(name="search")
	public TPagingList<FormatoDigitaleBean> search(TFilterFetch<FormatoDigitaleBean> filter) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();			
			//Creo i criteri di ricerca
			Criteria criteria = buildHibernateCriteriaByFilter(session, filter);			
			//Conto i record totali
			Long count = (Long)criteria.setProjection(Projections.rowCount()).uniqueResult();			
			Integer startRow = (filter != null && filter.getStartRow() != null) ? filter.getStartRow() : 0;
			Integer endRow = (filter != null && filter.getEndRow() != null) ? filter.getEndRow() : startRow + count.intValue() - 1;			
			//Creo l'oggetto paginatore
			TPagingList<FormatoDigitaleBean> paginglist = new TPagingList<FormatoDigitaleBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);			
			//Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for(Object obj : HibernateUtil.pagingByCriteria(criteria, filter.getStartRow(), filter.getEndRow())) {
				FormatoDigitaleBean bean = (FormatoDigitaleBean) UtilPopulate.populate((TAnagFormatiDig) obj, FormatoDigitaleBean.class, new TAnagFormatiDigToFormatoDigitaleBeanConverter());
				paginglist.addData(bean);
			}			
			HibernateUtil.commit(session);			
			return paginglist;
		}catch(Exception e){
			throw e;
		}finally{
			HibernateUtil.release(session);
		}				
	}

	//Metodo privato per costruire i criteri di ricerca
	//TODO verificare criteri
	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<FormatoDigitaleBean> filter) throws Exception {		
		Criteria criteria = session.createCriteria(TAnagFormatiDig.class);
		if(filter != null) {
			if(filter.getFilter() != null) {						
				if(StringUtils.isNotBlank(filter.getFilter().getAltriNomi())) {
					Restrictions.like("altriNomi", filter.getFilter().getAltriNomi(), MatchMode.ANYWHERE).ignoreCase();	
				}
				if(StringUtils.isNotBlank(filter.getFilter().getByteOrders())) {
					criteria.add(Restrictions.eq("byteOrders", filter.getFilter().getByteOrders()).ignoreCase());	
				}
				if(StringUtils.isNotBlank(filter.getFilter().getDesEstesa())) {
					criteria.add(Restrictions.like("desEstesa", filter.getFilter().getDesEstesa(), MatchMode.ANYWHERE).ignoreCase());
				}
				if(filter.getFilter().getDtDesupp()!=null) {
					criteria.add(Restrictions.eq("dtDesupp", filter.getFilter().getDtDesupp()));	
				}
				if(filter.getFilter().getDtRilascio()!=null) {
					criteria.add(Restrictions.eq("dtRilascio", filter.getFilter().getDtRilascio()));	
				}
				if(StringUtils.isNotBlank(filter.getFilter().getEstensionePrincipale())) {
					criteria.add(Restrictions.eq("estensionePrincipale", filter.getFilter().getEstensionePrincipale()).ignoreCase());	
				}

				//Questi due filtri servono?
				//				if(StringUtils.isNotBlank(filter.getFilter().getIdRecDizRegistroFmt())) {	
				//					criteria.createAlias("TValDizionarioByIdRecDizRegistroFmt", "ByIdRecDizRegistroFmt");
				//					criteria.add(Restrictions.eq("ByIdRecDizRegistroFmt.idRecDiz", filter.getFilter().getIdRecDizRegistroFmt()));
				//				}
				//
				//				if(StringUtils.isNotBlank(filter.getFilter().getIdRecDizTipoFmt())) {	
				//					criteria.createAlias("TValDizionarioByIdRecDizTipoFmt", "ByIdRecDizTipoFmt");
				//					criteria.add(Restrictions.eq("ByIdRecDizTipoFmt.idRecDiz", filter.getFilter().getIdRecDizTipoFmt()));
				//				}
				//End of Questi due filtri servono?

				//if(filter.getFilter().getFlgCopyright()!=null) {
				//	criteria.add(Restrictions.eq("flgCopyright", filter.getFilter().getFlgCopyright()));	
				//}						
				//if(filter.getFilter().getFlgStato()!=null) {
				//	criteria.add(Restrictions.eq("flgStato", filter.getFilter().getFlgStato()).ignoreCase());	
				//}
				if(StringUtils.isNotBlank(filter.getFilter().getMantenutoDa())) {
					criteria.add(Restrictions.like("mantenutoDa", filter.getFilter().getMantenutoDa(), MatchMode.ANYWHERE).ignoreCase());						
				}
				if(StringUtils.isNotBlank(filter.getFilter().getNome())) {
					criteria.add(Restrictions.like("nome", filter.getFilter().getNome(), MatchMode.ANYWHERE).ignoreCase());
				}
				if(StringUtils.isNotBlank(filter.getFilter().getRischi())) {
					criteria.add(Restrictions.like("rischi", filter.getFilter().getRischi(), MatchMode.ANYWHERE).ignoreCase());	
				}
				if(StringUtils.isNotBlank(filter.getFilter().getSviluppatoDa())) {
					criteria.add(Restrictions.like("sviluppatoDa", filter.getFilter().getSviluppatoDa(), MatchMode.ANYWHERE).ignoreCase());
				}
				if(StringUtils.isNotBlank(filter.getFilter().getVersione())) {
					criteria.add(Restrictions.like("versione", filter.getFilter().getVersione(), MatchMode.ANYWHERE).ignoreCase());
				}
				//Entro qui solo se filtro per flgAnn = 0, altrimenti tiro gi� anche quelli cancellati (flgAnn = 1)
				if (filter.getFilter().getFlgAnn() != null && !filter.getFilter().getFlgAnn()) {
					criteria.add(Restrictions.eq("flgAnn", filter.getFilter().getFlgAnn()));
				}
			}
			HibernateUtil.addOrderCriteria(criteria, filter.getOrders());			
		}			
		return criteria;
	}

	@Operation(name="save")
	public FormatoDigitaleBean save(FormatoDigitaleBean bean) throws Exception {		
		Session session = null;
		try {			
			session = HibernateUtil.begin();			
			if(bean != null) {	
				Calendar cal = new GregorianCalendar();
				if(StringUtils.isBlank(bean.getEstensionePrincipale())) {
					throw new Exception("Impossibile salvare il formato digitale poichè non è stata definita un'estensione principale.");
				}

				if(StringUtils.isBlank(bean.getIdDigFormat())) {
					bean.setIdDigFormat(KeyGenerator.gen());					
				} 

				//Creo la tupla del formato digitale
				TAnagFormatiDig anagFormatiDig = (TAnagFormatiDig) UtilPopulate.populate(
						bean, TAnagFormatiDig.class, new FormatoDigitaleBeanToTAnagFormatiDigConverter(session));			
				anagFormatiDig.setTsAggStato(cal.getTime());		//Aggiungo il tsAggStato
				session.save(anagFormatiDig);	

				//Aggiungo al bean delle estensioni anche l'estensionePrincipale
				Set<String> estensioni = bean.getEstensioni();
				if(estensioni==null) {
					estensioni = new HashSet<String>();
				}
				estensioni.add(bean.getEstensionePrincipale());
				bean.setEstensioni(estensioni);

				//Creo le estensioni associate al nuovo formato digitale
				for(String estens:bean.getEstensioni()) {
					this.createEstensione(session, bean.getIdDigFormat(), estens);
				}

				//Creo i mimetype associati al nuovo formato digitale
				for(Mimetype mimetype:bean.getMimetypes()) {
					this.createMimetype(session, bean.getIdDigFormat(), mimetype);
				}
			}								
			HibernateUtil.commit(session);
		}catch(Exception e){
			throw e;
		}finally{
			HibernateUtil.release(session);
		}	
		return bean;
	}

	//Metodo che recupera lo stato di un utente
	@Operation(name="getStato")
	public String getStato(String idDigFormat) throws Exception {
		Session session = null;
		String stato = null;
		try {			
			session = HibernateUtil.begin();
			if(StringUtils.isNotBlank(idDigFormat)) {
				TAnagFormatiDig afd = (TAnagFormatiDig) session.get(TAnagFormatiDig.class, idDigFormat);
				if(afd!=null) {
					stato = afd.getFlgStato();
				}
			}						
			HibernateUtil.commit(session);
		}catch(Exception e){
			throw e;
		}finally{
			HibernateUtil.release(session);
		}	

		return stato;
	}

	//Operazione DAO di update che aggiorna anche la tabella delle estensioni e del mimetype
	@Operation(name="update")
	public FormatoDigitaleBean update(FormatoDigitaleBean bean) throws Exception {		
		Session session = null;
		try {			
			session = HibernateUtil.begin();			
			if(bean != null) {	
				String stato = null;
				if(StringUtils.isNotBlank(bean.getIdDigFormat())) {
					TAnagFormatiDig afd = (TAnagFormatiDig) session.get(TAnagFormatiDig.class, bean.getIdDigFormat());
					if(afd!=null) {
						stato = afd.getFlgStato();
					}
				}	

				TAnagFormatiDig afd = (TAnagFormatiDig) UtilPopulate.populateForUpdate(session, bean, TAnagFormatiDig.class, new FormatoDigitaleBeanToTAnagFormatiDigConverter(session));				

				//Se ho modificato l'estensione principale devo riflettere questa modifica anche sulla property estensioni
				if(bean.hasPropertyBeenModified("estensionePrincipale")) {
					Set<String> estensioni = bean.getEstensioni();
					if(estensioni==null) {
						estensioni = new HashSet<String>();
					}
					estensioni.add(bean.getEstensionePrincipale());
					bean.setEstensioni(estensioni);
				}

				//Se tra le property modificate c'è quella dell'estensione, opero anche sulla tabella T_ESTENSIONI_FMT_DIG
				if(bean.hasPropertyBeenModified("estensioni")) {
					logger.debug("Sono state modificate le estensioni, itero su quelle modificate per aggiornare la tabella a DB.");
					for(String estens:bean.getEstensioni()) {
						this.createEstensione(session, bean.getIdDigFormat(), estens);
					}
				}	

				//Se tra le property modificate ci sono quelle dei mimetype, opero anche sulla tabella T_MIMETYPE_FMT_DIG
				if(bean.hasPropertyBeenModified("mimetypes")) {
					logger.debug("Sono stati modificati i mimetype, itero su quelli modificati per aggiornare la tabella a DB.");
					//Per ciascun mimetype modificato
					for(Mimetype mt:bean.getMimetypes()) {
						this.createMimetype(session, bean.getIdDigFormat(), mt);
					}
				}	

				if(stato != null && afd.getFlgStato() != null && !stato.equals(afd.getFlgStato())) {
					logger.debug("È stati modificato lo stato, aggiorno il timestamp relativo.");
					Calendar cal = new GregorianCalendar();
					afd.setTsAggStato(cal.getTime());
				}

				session.merge(afd);				

			}								
			HibernateUtil.commit(session);
		}catch(Exception e){
			throw e;
		}finally{
			HibernateUtil.release(session);
		}	
		return bean;
	}			

	/*Metodo che recupera, dal DB, l'eventuale entry nella tabella delle estensioni T_ESTENSIONI_FMT_DIG 
	 *corrispondente alla chiave (idDigFormat, estensioneId) */
	private TEstensioniFmtDig getEstensione(Session session, String idDigFormat, String estensioneId) {
		if(StringUtils.isNotBlank(idDigFormat)) {
			Criteria crit = session.createCriteria(TEstensioniFmtDig.class);
			crit.add(Restrictions.eq("id.idDigFormat",idDigFormat));
			crit.add(Restrictions.eq("id.estensione",estensioneId));
			TEstensioniFmtDig estensione = (TEstensioniFmtDig) crit.uniqueResult();
			return estensione;
		}
		return null;
	}

	/*Metodo che recupera, dal DB, l'eventuale entry nella tabella delle estensioni T_MIMETYPE_FMT_DIG 
	 *corrispondente alla chiave (idDigFormat, mimetypeId) */
	private TMimetypeFmtDig getMimetype(Session session, String idDigFormat, String mimetypeId) {
		if(StringUtils.isNotBlank(idDigFormat)) {
			Criteria crit = session.createCriteria(TMimetypeFmtDig.class);
			crit.add(Restrictions.eq("id.idDigFormat",idDigFormat));
			crit.add(Restrictions.eq("id.mimetype",mimetypeId));
			TMimetypeFmtDig mimetype = (TMimetypeFmtDig) crit.uniqueResult();
			return mimetype;
		}
		return null;
	}

	//Metodo che recupera la lista dei mimetype associati al formato digitale avente id pari a quello passato in input
	private List<TMimetypeFmtDig> getMimetypes(Session session, String idDigFormat) {
		if(StringUtils.isNotBlank(idDigFormat)) {
			Criteria crit = session.createCriteria(TMimetypeFmtDig.class);
			crit.add(Restrictions.eq("id.idDigFormat",idDigFormat));
			@SuppressWarnings("unchecked")
			List<TMimetypeFmtDig> mimetypes = (List<TMimetypeFmtDig>) crit.list();
			return mimetypes;
		}
		return null;
	}

	//Metodo che recupera la lista delle estensioni associate al formato digitale avente id pari a quello passato in input
	private List<TEstensioniFmtDig> getEstensioni(Session session, String idDigFormat) {
		if(StringUtils.isNotBlank(idDigFormat)) {
			Criteria crit = session.createCriteria(TEstensioniFmtDig.class);
			crit.add(Restrictions.eq("id.idDigFormat",idDigFormat));
			@SuppressWarnings("unchecked")
			List<TEstensioniFmtDig> mimetypes = (List<TEstensioniFmtDig>) crit.list();
			return mimetypes;
		}
		return null;
	}

	@Operation(name="delete")
	public void delete(FormatoDigitaleBean bean) throws Exception {		
		Session session = null;
		try {
			session = HibernateUtil.begin();
			if(bean != null && StringUtils.isNotBlank(bean.getIdDigFormat())) {
				TAnagFormatiDig afd = (TAnagFormatiDig) session.get(TAnagFormatiDig.class, bean.getIdDigFormat());
				if(afd != null) {
					session.delete(afd);

					// DECOMMENTARE LE SEGUENTI LINEE DI CODICE SE SI INTENDE PROPAGARE 
					// LA CANCELLAZIONE LOGICA SULLE TABELLE DELLE ESTENSIONI E DEI MIMETYPE

					//					//recupero le estensioni al formato digitale corrente
					//					List<TEstensioniFmtDig> estensioni = this.getEstensioni(session, afd.getIdDigFormat());
					//					if(estensioni!=null) {
					//						for(TEstensioniFmtDig estensione:estensioni) {
					//							session.delete(estensione);
					//						}
					//					}
					//
					//					//recupero i mimetype associati al formato digitale corrente
					//					List<TMimetypeFmtDig> mimetypes = this.getMimetypes(session, afd.getIdDigFormat());
					//					if(mimetypes!=null) {
					//						for(TMimetypeFmtDig mime:mimetypes) {
					//							session.delete(mime);
					//						}
					//					}
				}
			}
			HibernateUtil.commit(session);
		}catch(Exception e){
			throw e;
		}finally{
			HibernateUtil.release(session);
		}
	}

	/**
	 * Metodo di cancellazione fisica di un formato digitale e delle relative estensioni e mimetype
	 */
	@Operation(name="forcedelete")
	public void forcedelete(FormatoDigitaleBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			if(bean != null && StringUtils.isNotBlank(bean.getIdDigFormat())) {
				TAnagFormatiDig afd = (TAnagFormatiDig) session.get(TAnagFormatiDig.class, bean.getIdDigFormat());
				if(afd != null) {
					//recupero le estensioni al formato digitale corrente
					List<TEstensioniFmtDig> estensioni = this.getEstensioni(session, afd.getIdDigFormat());
					if(estensioni!=null) {
						for(TEstensioniFmtDig estensione:estensioni) {
							session.delete(HibernateConstants.Delete.FORCE,estensione);
						}
					}

					//recupero i mimetype associati al formato digitale corrente
					List<TMimetypeFmtDig> mimetypes = this.getMimetypes(session, bean.getIdDigFormat());
					if(mimetypes!=null) {
						for(TMimetypeFmtDig mime:mimetypes) {
							session.delete(HibernateConstants.Delete.FORCE,mime);
						}
					}

					session.delete(HibernateConstants.Delete.FORCE,afd);
				}
			}
			HibernateUtil.commit(session);
		}catch(Exception e){
			throw e;
		}finally{
			HibernateUtil.release(session);
		}
	}

	/*
	 * Metodo privato che crea un'estensione sulla tabella T_ESTENSIONI_FMT_DIG
	 * Secondo la presente logica:
	 * 
	 * Data un'estensione e l'id del formato digitale:
	 * se esiste già a DB un'estensione corrispondente e tale estensione è annullata, la ripristino.
	 * se esiste già a DB un'estensione corrispondente e tale estensione è NON è annullata, non faccio nulla.
	 * se NON esiste a DB un'estensione corrispondente, la creo.
	 */
	private void createEstensione(Session session, String idDigFormat, String estens) throws Exception {
		if(StringUtils.isBlank(estens)) {
			throw new Exception("Si è verificato un errore durante la modifica delle estensioni: " +
			"un'estensione è vuota o nulla");
		}
		//Se esiste già un'estensione associata a questo formato...
		TEstensioniFmtDig estensione = this.getEstensione(session, idDigFormat, estens);
		//Se esiste già un'estensione corrente associata a questo formato, la cancello
		if(estensione!=null) {
			logger.debug("L'estensione "+estens+" per il formato digitale "+idDigFormat+" già esiste a DB.");
			if(estensione.getFlgAnn()) {
				logger.debug("L'estensione "+estens+" per il formato digitale "+idDigFormat+" era stata cancellata, la ripristino.");
				estensione.setFlgAnn(false);
			}
			session.update(estensione);
		}
		//Altrimenti creo una nuova estensione corrispondente ai dati modificati
		else {
			logger.debug("L'estensione "+estens+" per il formato digitale "+idDigFormat+" non esiste a DB, la creo.");
			TEstensioniFmtDigId nuovaEstensioneId = new TEstensioniFmtDigId();
			nuovaEstensioneId.setEstensione(estens);
			nuovaEstensioneId.setIdDigFormat(idDigFormat);
			TEstensioniFmtDig nuovaEstensione = new TEstensioniFmtDig();
			nuovaEstensione.setId(nuovaEstensioneId);
			nuovaEstensione.setFlgAnn(false);
			session.save(nuovaEstensione);
			logger.debug("L'estensione "+estens+" per il formato digitale "+idDigFormat+" è stato creata correttamente.");
		}
	}

	/*
	 * Metodo privato che crea un mimetype sulla tabella T_MIMETYPE_FMT_DIG
	 * Secondo la presente logica:
	 * 
	 * Dato un mimetype e l'id del formato digitale:
	 * se esiste già a DB un mimetype corrispondente e tale mimetype è annullato, lo ripristino.
	 * se esiste già a DB un mimetype corrispondente e tale mimetype NON è annullato, non faccio nulla.
	 * se NON esiste a DB un mimetype corrispondente, lo creo.
	 */
	private void createMimetype(Session session, String idDigFormat, Mimetype mt) throws Exception {
		if(StringUtils.isBlank(mt.getMimetype())) {
			throw new Exception("Si è verificato un errore durante la modifica dei mimetype: " +
			"un mimetype è vuoto o nullo");
		}
		//Se esiste già un mimetype associato a questo formato...
		TMimetypeFmtDig mimetype = this.getMimetype(session, idDigFormat, mt.getMimetype());
		if(mimetype!=null) {
			logger.debug("Il mimetype "+mt.getMimetype()+" per il formato digitale "+idDigFormat+" già esiste a DB.");
			//se era annullato lo ripristino
			if(mimetype.getFlgAnn()) {
				logger.debug("Il mimetype "+mt.getMimetype()+" per il formato digitale "+idDigFormat+" era stato cancellato, lo ripristino.");
				mimetype.setFlgAnn(false);
			}
			mimetype.setRifReader(mt.getRifReader());
			session.update(mimetype);
		}
		//Creo un nuovo mimetype corrispondente ai dati modificati
		else {
			logger.debug("Il mimetype "+mt.getMimetype()+" per il formato digitale "+idDigFormat+" non esiste a DB, lo creo.");
			TMimetypeFmtDigId nuovomimeId = new TMimetypeFmtDigId();
			nuovomimeId.setMimetype(mt.getMimetype());
			nuovomimeId.setIdDigFormat(idDigFormat);
			TMimetypeFmtDig nuovoMime = new TMimetypeFmtDig();
			nuovoMime.setId(nuovomimeId);
			nuovoMime.setRifReader(mt.getRifReader());
			nuovoMime.setFlgAnn(false);
			session.save(nuovoMime);
			logger.debug("Il mimetype "+mt.getMimetype()+" per il formato digitale "+idDigFormat+" è stato creato correttamente.");
		}
	}

	private static Map<String, TAnagFormatiDig> mappaFormati;




	/**
	 * TODO: a aprtire dal mimetype calcolato con i detector sul file in ingresso 
	 * devi determinare il formato corrispondente e devi ritornare i dati del formato ID e altro
	 * in particolare  devi ritornare il valore della colonna mimtypeprincipale uan volta determianto i formato
	 * nota devi escludere i formati annullati e se ce ne sono più di uno prendi il primo
	 */
	@Operation(name="findFormatByMimeType")
	public TAnagFormatiDig findFormatByMimeType(String mimeType) throws Exception {
		if (mappaFormati == null){
			initFormatiDigitali();
		} 
		return mappaFormati.get(mimeType);
	}
	
	public TAnagFormatiDig findFormatByExt(String ext) throws Exception {
		if (mappaFormati == null){
			initFormatiDigitali();
		} 
		
		TAnagFormatiDig result = null;
		Set<String> formati = mappaFormati.keySet();
		for(String formato : formati){
			TAnagFormatiDig lTAnagFormatiDig = mappaFormati.get(formato);
			if( lTAnagFormatiDig.getTEstensioniFmtDigs()!=null ){
				Set<TEstensioniFmtDig> tEstensioniFmtDigs = lTAnagFormatiDig.getTEstensioniFmtDigs();
				Iterator<TEstensioniFmtDig> tEstensioniFmtDigsItr = tEstensioniFmtDigs.iterator();
				while( tEstensioniFmtDigsItr.hasNext() ){
					TEstensioniFmtDig tEstensioniFmtDig = tEstensioniFmtDigsItr.next();
					if( tEstensioniFmtDig.getId()!=null && tEstensioniFmtDig.getId().getEstensione().equalsIgnoreCase(ext)){
						result = lTAnagFormatiDig;
						return result;
					}
				}
			}
		}
		
		return result;
	}
	
	public TAnagFormatiDig findFormatByExtPrincipale(String ext) throws Exception {
		if (mappaFormati == null){
			initFormatiDigitali();
		} 
		
		TAnagFormatiDig result = null;
		Set<String> formati = mappaFormati.keySet();
		for(String formato : formati){
			TAnagFormatiDig lTAnagFormatiDig = mappaFormati.get(formato);
			if( lTAnagFormatiDig!=null ){
				if( lTAnagFormatiDig.getEstensionePrincipale()!=null && lTAnagFormatiDig.getEstensionePrincipale().equalsIgnoreCase(ext)){
					result = lTAnagFormatiDig;
					return result;
				}
			}
		}
		
		return result;
	}

	private void initFormatiDigitali() throws Exception {
		Session session = null;
		TAnagFormatiDig ret=null;
		try {
			session = HibernateUtil.begin();
			Criteria crit = session.createCriteria(TMimetypeFmtDig.class);
			crit.createAlias("TAnagFormatiDig", "TAnagFormatiDig"); 
			//			crit.add(Restrictions.or(Restrictions.eq("flgAnn", false), Restrictions.isNull("flgAnn") ));
			//			crit.add(Restrictions.or(Restrictions.eq("TAnagFormatiDig.flgAnn", false), Restrictions.isNull("TAnagFormatiDig.flgAnn") ));
			List<TMimetypeFmtDig> formati=crit.list();
			mappaFormati = new HashMap<String, TAnagFormatiDig>();
			for (TMimetypeFmtDig lTMimetypeFmtDig : formati){
				TAnagFormatiDig lTAnagFormatiDig = lTMimetypeFmtDig.getTAnagFormatiDig();
				if (lTAnagFormatiDig.getFlgAnn()==null || (!lTAnagFormatiDig.getFlgAnn())){
					if (lTMimetypeFmtDig.getFlgAnn() == null || (!lTMimetypeFmtDig.getFlgAnn())){
						List<TEstensioniFmtDig> lListEstensioni = 
							session.createCriteria(TEstensioniFmtDig.class).createAlias("TAnagFormatiDig", "TAnagFormatiDig").add(Restrictions.eq("TAnagFormatiDig.idDigFormat", lTAnagFormatiDig.getIdDigFormat())).list();
						HashSet<TEstensioniFmtDig> lHashSet = new HashSet<TEstensioniFmtDig>();
						for (TEstensioniFmtDig lTEstensioniFmtDig : lListEstensioni){
							lHashSet.add(lTEstensioniFmtDig);
						}
						lTAnagFormatiDig.setTEstensioniFmtDigs(lHashSet);
						mappaFormati.put(lTMimetypeFmtDig.getId().getMimetype(), lTAnagFormatiDig);
					}
				}
			}

			HibernateUtil.commit(session);
		}catch(Exception e){
			throw e;
		}finally{
			HibernateUtil.release(session);
		}
		logger.debug("mappaFormati " + mappaFormati);
	}
	
//	private void initFormatiDigitaliExt() throws Exception {
//		Session session = null;
//		TAnagFormatiDig ret=null;
//		try {
//			session = HibernateUtil.begin();
//			Criteria crit = session.createCriteria(TMimetypeFmtDig.class);
//			crit.createAlias("TAnagFormatiDig", "TAnagFormatiDig"); 
//			//			crit.add(Restrictions.or(Restrictions.eq("flgAnn", false), Restrictions.isNull("flgAnn") ));
//			//			crit.add(Restrictions.or(Restrictions.eq("TAnagFormatiDig.flgAnn", false), Restrictions.isNull("TAnagFormatiDig.flgAnn") ));
//			List<TMimetypeFmtDig> formati=crit.list();
//			mappaFormati = new HashMap<String, TAnagFormatiDig>();
//			for (TMimetypeFmtDig lTMimetypeFmtDig : formati){
//				TAnagFormatiDig lTAnagFormatiDig = lTMimetypeFmtDig.getTAnagFormatiDig();
//				if (lTAnagFormatiDig.getFlgAnn()==null || (!lTAnagFormatiDig.getFlgAnn())){
//					if (lTMimetypeFmtDig.getFlgAnn() == null || (!lTMimetypeFmtDig.getFlgAnn())){
//						List<TEstensioniFmtDig> lListEstensioni = 
//							session.createCriteria(TEstensioniFmtDig.class).createAlias("TAnagFormatiDig", "TAnagFormatiDig").add(Restrictions.eq("TAnagFormatiDig.idDigFormat", lTAnagFormatiDig.getIdDigFormat())).list();
//						HashSet<TEstensioniFmtDig> lHashSet = new HashSet<TEstensioniFmtDig>();
//						for (TEstensioniFmtDig lTEstensioniFmtDig : lListEstensioni){
//							lHashSet.add(lTEstensioniFmtDig);
//						}
//						lTAnagFormatiDig.setTEstensioniFmtDigs(lHashSet);
//						mappaFormati.put(lTMimetypeFmtDig.getId().getMimetype(), lTAnagFormatiDig);
//					}
//				}
//			}
//
//			HibernateUtil.commit(session);
//		}catch(Exception e){
//			throw e;
//		}finally{
//			HibernateUtil.release(session);
//		}
//
//	}

	public Map<String, TAnagFormatiDig> listAll() throws Exception {
		if (mappaFormati == null){
			initFormatiDigitali();
		} 
		logger.error("I fomrati caricati sono " + mappaFormati.size());
		for (String lString : mappaFormati.keySet()){
			logger.error("Caricato " + mappaFormati.get(lString) + " per chiave " + lString);
		}
		return mappaFormati;
		
	}


}