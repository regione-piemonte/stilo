package it.eng.core.business;

import java.util.Collection;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

/**
 * Classe di utilità per generare Criterion {@link org.hibernate.criterion.Criterion} di utilità generale
 * @author Russo
 *
 */
public class CriteriaUtil {
	private static final Logger log = Logger.getLogger(CriteriaUtil.class);
	/**
	 * costruisce il criterio per includere o escludere gli id. Se non sono passati dati, liste vuote o null, ritorna null
	 */
	
	public static Criterion checkIds(Class entityClazz,TKeyFilter keyFilter ) throws Exception{
		String keyProp=HibernateUtil.getPrimaryKey(entityClazz);
		Criterion ret=null;//inizializzo il ritorno  
		Collection<Object> includeIds=keyFilter.getIncludeId();
		Collection<Object> excludedIds=keyFilter.getExcludeId();
		Object preservedId=keyFilter.getPreserveId();
		
		Disjunction conj=Restrictions.disjunction();
		boolean somethingtoDo=false;
		if(excludedIds!=null && excludedIds.size()>0){
			log.debug("class:"+ entityClazz+" escludo i seguenti ids:"+excludedIds);
			if(preservedId!=null){
				excludedIds.remove(preservedId);
			}
			conj.add(Restrictions.not(Restrictions.in(keyProp, excludedIds)));
			somethingtoDo=true;
		}
		if(includeIds!=null && includeIds.size()>0){
			log.debug("class:"+ entityClazz+" includo i seguenti ids:"+includeIds);
			if(preservedId!=null){
				//if(!includeIds.contains(preservedId))
					includeIds.add(preservedId);
			}
			conj.add(Restrictions.in(keyProp, includeIds));
			somethingtoDo=true;
		}
		//return null fif nothing to do 
		if(somethingtoDo) ret=conj;
		return ret;
	}
	
	//da rivedere getSQL from criteria
/*	public static String toSql(Criteria criteria){
		
	    try{
	    	CriteriaImpl criteriaImpl = (CriteriaImpl)criteria;
	    	SessionImplementor session = criteriaImpl.getSession();
	    	SessionFactoryImplementor factory = session.getFactory();
	    	CriteriaQueryTranslator translator=new CriteriaQueryTranslator(factory,criteriaImpl,criteriaImpl.getEntityOrClassName(),CriteriaQueryTranslator.ROOT_SQL_ALIAS);
	    	String[] implementors = factory.getImplementors( criteriaImpl.getEntityOrClassName() );


	    	CriteriaJoinWalker walker = new CriteriaJoinWalker((OuterJoinLoadable)factory.getEntityPersister(implementors[0]),
	    	                        translator,
	    	                        factory,
	    	                        criteriaImpl,
	    	                        criteriaImpl.getEntityOrClassName(),
	    	                        session.getLoadQueryInfluencers()   );


	    	String sql=walker.getSQLString();
	    	 //translator.getQueryParameters()
	    	return sql;
	    }
	    catch(Exception e){
	      throw new RuntimeException(e); 
	    }
	  }
	
	public static String toSql2( Criteria criteria){
	       try{
	         CriteriaImpl c = (CriteriaImpl) criteria;
	         SessionImpl s = (SessionImpl)c.getSession();
	         SessionFactoryImplementor factory = (SessionFactoryImplementor)s.getSessionFactory();
	         String[] implementors = factory.getImplementors( c.getEntityOrClassName() );
	         CriteriaLoader loader = new CriteriaLoader((OuterJoinLoadable)factory.getEntityPersister(implementors[0]),
	           factory, c, implementors[0], s.getLoadQueryInfluencers());
	        
	         Field f = OuterJoinLoader.class.getDeclaredField("sql");
	         f.setAccessible(true);
	         String sql= (String) f.get(loader);
	         return sql;
	       }
	       catch(Exception e){
	         throw new RuntimeException(e);
	       }
	     }
	
	public static String translateParams(Session session, String hqlQueryText){
	       if (hqlQueryText!=null && hqlQueryText.trim().length()>0){
	         final QueryTranslatorFactory translatorFactory = new ASTQueryTranslatorFactory();
	         final SessionFactoryImplementor factory =
	           (SessionFactoryImplementor) session.getSessionFactory();
	         final QueryTranslator translator = translatorFactory.
	           createQueryTranslator(
	             hqlQueryText,
	             hqlQueryText,
	             java.util.Collections.EMPTY_MAP, factory
	           );
	         translator.compile(Collections.EMPTY_MAP, false);
	         return translator.getSQLString();
	       }
	       return null;
	     }
	     */
	
	//verifica che le date inseriti non generano un intervallo che si sovrappone con gli intervalli nel db 
	//i record nel db che hanno data null sono considerati rispettivamente da sempre (datainizio) e per sempre dtfine
	//se applicando il critrerio hai record vuol dire che esistono sovrapposizioni
	public static Criterion checkDateRange(String dbDateInizioProp,String dbDateFineProp,Date pageDateInizio,Date pageDateFine){

		//prendi sempre i record con entrambe le date sul db a null vuol di re che sono record validi sempre
		//
		Junction casoNull=Restrictions.disjunction()
		.add(Restrictions.conjunction()
				.add(Restrictions.isNull(dbDateInizioProp))
				.add(Restrictions.isNull(dbDateFineProp))
		);
		//se i campi page sono null non devi inserire le condizioni di controllo  
		
		//nei casi in cui dbDateFine  è null  devi controllare solo dbDateInizio<=pageDateFine  
		//oppure dbDataFine<pageDateInizio
		if(pageDateInizio!=null || pageDateFine!=null){
			Conjunction conj=Restrictions.conjunction();
			conj.add( Restrictions.isNull(dbDateFineProp))
					.add( Restrictions.isNotNull(dbDateInizioProp));
			if(pageDateFine!=null){
				conj.add( Restrictions.le(dbDateInizioProp, pageDateFine) );
			}else{
				conj.add( Restrictions.ge(dbDateInizioProp  , pageDateInizio) );
			}
			casoNull.add(conj);
		}
		//nei casi in cui dbDateInizio  è null  devi controllare solo  dbDateFine >= pageDateInizio
		//oppure  dbDataFine>pageDAtaFine
		if(pageDateInizio!=null || pageDateFine!=null){
		
			Conjunction conj=Restrictions.conjunction();
			conj.add( Restrictions.isNull(dbDateInizioProp))
						.add( Restrictions.isNotNull(dbDateFineProp));
			if(pageDateInizio!=null){
				conj.add( Restrictions.ge(dbDateFineProp, pageDateInizio));
			}else{
				conj.add( Restrictions.le(dbDateFineProp  , pageDateFine) );
			}
			casoNull.add(conj);
		}
		//inserisci le condizioni sulla data fine validità solo se si è scelta not null
		Junction casoNotNull= Restrictions.conjunction();
		if(pageDateFine!=null){
			casoNotNull.add( 
					Restrictions.and(
							Restrictions.le(dbDateInizioProp, pageDateFine),
							Restrictions.isNotNull(dbDateInizioProp))

			);
		}
		if(pageDateInizio!=null){
			casoNotNull.add( 
					Restrictions.and(
							Restrictions.ge(dbDateFineProp, pageDateInizio),
							Restrictions.isNotNull(dbDateFineProp))
			);
		}
		//metti in or il caso in cui le date nel nel db sono valorizzate con quelle in cui non lo sono
		return Restrictions.or(casoNotNull, casoNull);
	}
}
