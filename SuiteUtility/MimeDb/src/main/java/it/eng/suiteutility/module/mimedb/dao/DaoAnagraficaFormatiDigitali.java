package it.eng.suiteutility.module.mimedb.dao;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import it.eng.suiteutility.module.mimedb.entity.TAnagFormatiDig;
import it.eng.suiteutility.module.mimedb.entity.TEstensioniFmtDig;
import it.eng.suiteutility.module.mimedb.entity.TMimetypeFmtDig;

/**
 * Classe Dao per la tabella di T_ANAG_FORMATI_DIG
 *
 */
public class DaoAnagraficaFormatiDigitali /*extends DaoGenericOperations<FormatoDigitaleBean>*/{

	public static final  Logger logger = LogManager.getLogger(DaoAnagraficaFormatiDigitali.class);

	public DaoAnagraficaFormatiDigitali() {		
	}



//	@Operation(name="save")
//	public FormatoDigitaleBean save(FormatoDigitaleBean bean) throws Exception {		
//		Session session = null;
//		try {			
//			session = HibernateUtil.begin();			
//			if(bean != null) {	
//				Calendar cal = new GregorianCalendar();
//				if(StringUtils.isBlank(bean.getEstensionePrincipale())) {
//					throw new Exception("Impossibile salvare il formato digitale poichè non è stata definita un'estensione principale.");
//				}
//
//				if(StringUtils.isBlank(bean.getIdDigFormat())) {
//					bean.setIdDigFormat(KeyGenerator.gen());					
//				} 
//
//				//Creo la tupla del formato digitale
//				TAnagFormatiDig anagFormatiDig = (TAnagFormatiDig) UtilPopulate.populate(
//						bean, TAnagFormatiDig.class, new FormatoDigitaleBeanToTAnagFormatiDigConverter(session));			
//				anagFormatiDig.setTsAggStato(cal.getTime());		//Aggiungo il tsAggStato
//				session.save(anagFormatiDig);	
//
//				//Aggiungo al bean delle estensioni anche l'estensionePrincipale
//				Set<String> estensioni = bean.getEstensioni();
//				if(estensioni==null) {
//					estensioni = new HashSet<String>();
//				}
//				estensioni.add(bean.getEstensionePrincipale());
//				bean.setEstensioni(estensioni);
//
//				//Creo le estensioni associate al nuovo formato digitale
//				for(String estens:bean.getEstensioni()) {
//					this.createEstensione(session, bean.getIdDigFormat(), estens);
//				}
//
//				//Creo i mimetype associati al nuovo formato digitale
//				for(Mimetype mimetype:bean.getMimetypes()) {
//					this.createMimetype(session, bean.getIdDigFormat(), mimetype);
//				}
//			}								
//			HibernateUtil.commit(session);
//		}catch(Exception e){
//			throw e;
//		}finally{
//			HibernateUtil.release(session);
//		}	
//		return bean;
//	}

	

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

	private static Map<String, TAnagFormatiDig> mappaFormati;




	/**
	 * TODO: a aprtire dal mimetype calcolato con i detector sul file in ingresso 
	 * devi determinare il formato corrispondente e devi ritornare i dati del formato ID e altro
	 * in particolare  devi ritornare il valore della colonna mimtypeprincipale uan volta determianto i formato
	 * nota devi escludere i formati annullati e se ce ne sono più di uno prendi il primo
	 */
	public TAnagFormatiDig findFormatByMimeType(String mimeType) throws Exception {
		if (mappaFormati == null){
			logger.debug("Inizializzo la mappa formati");
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