package it.eng.core.business;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.hibernate.type.Type;
import org.reflections.Reflections;

import it.eng.core.business.TOrderBy.OrderByType;
import it.eng.core.business.exception.ServiceException;
import it.eng.core.business.listener.IrisPreDeleteEventListener;
import it.eng.core.business.listener.IrisPreInsertEventListener;
import it.eng.core.business.listener.IrisPreUpdateEventListener;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.core.config.ConfigKey;
import it.eng.core.config.ConfigUtil;
import it.eng.core.config.CoreConfig;

/**
 * Classe di gestione delle connessioni
 */
public class HibernateUtil {

	private static ThreadLocal<Session> currentSession = new ThreadLocal<Session>();

	static Logger log = Logger.getLogger(HibernateUtil.class);

	/**
	 * Package dove sono contenute le entity
	 */
	private static String entitypackage = null;

	/**
	 * Lista con package dove sono contenute le entity
	 */

	private static List<String> listEntityPackage = null;

	/**
	 * Mappa statica con le sessionfactory configurate nell'applicativo
	 */
	private static Map<String, SessionFactory> mapSessionFactory = Collections.synchronizedMap(new HashMap<String, SessionFactory>());

	/**
	 * Mappa statica con l'associazione tra l'ente configurato e la sua sessionfacty
	 */
	private static Map<String, String> relationEnteSessionFactory = Collections.synchronizedMap(new HashMap<String, String>());

	/**
	 * Mappa statica che ingloba i connection bean per la gestione della transaction multithreading
	 */
	private static Map<String, SessionBean> sessions = Collections.synchronizedMap(new HashMap<String, SessionBean>());

	/**
	 * Mappa statica che ingloba i connection bean per la gestione della transaction multithreading
	 */
	private static Map<String, Statement> statements = Collections.synchronizedMap(new HashMap<String, Statement>());

	private static Map<String, Connection> connections = Collections.synchronizedMap(new HashMap<String, Connection>());

	/**
	 * Setta il listener di default
	 * 
	 * @param configuration
	 */
	public static void settingDefaultListener(Configuration configuration) {
		configuration.setListener("save", new IrisPreInsertEventListener());
		configuration.setListener("update", new IrisPreUpdateEventListener());
		configuration.setListener("delete", new IrisPreDeleteEventListener());
	}

	/**
	 * Aggiunge una sessionFactory al sistema
	 * 
	 * @param id
	 * @param properties
	 */
	public static void addSessionFactory(String id, Configuration configuration) {
		try {
			// aggiungi le classi annotate alla configurazione
			if (entitypackage == null && (listEntityPackage == null || listEntityPackage.isEmpty())) {
				throw new Exception("Nessun entityPackage configurato!");
			} else {
				if ((listEntityPackage == null || listEntityPackage.isEmpty())) {
					listEntityPackage = new ArrayList<String>();
					listEntityPackage.add(entitypackage);
				} else if (entitypackage != null && !listEntityPackage.contains(entitypackage)) {
					listEntityPackage.add(entitypackage);
				}
				log.debug("entityPackages: " + listEntityPackage);
			}

			// scorro la lista di package e recupero le entità
			for (String tmpEntityPackage : listEntityPackage) {
				Reflections reflections = new Reflections(tmpEntityPackage);
				Set<Class<?>> entities = reflections.getTypesAnnotatedWith(Entity.class);
				Iterator<Class<?>> iteratore = entities.iterator();
				while (iteratore.hasNext()) {
					configuration.addAnnotatedClass(iteratore.next());
				}
			}

			SessionFactory sessionfactory = configuration.buildSessionFactory();
			if (StringUtils.isNotBlank(id)) {
				synchronized (mapSessionFactory) {
					log.debug("Aggiungo alle sessionFactory la sessione con id: " + id);
					mapSessionFactory.put(id, sessionfactory);
				}
			} else {
				throw new Exception("id non valorizzato");
			}
		} catch (Throwable ex) {
			log.error("Errore addSessionFactory per id: " + id, ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	/**
	 * Recupero la sessionfactory configurata in base all'ente
	 * 
	 * @param ente
	 * @return
	 * @throws Exception
	 */
	private static SessionFactory getSessionFactory(String ente) throws Exception {
		if (StringUtils.isNotBlank(ente)) {
			// Controllo se ho l'ente configurato
			synchronized (relationEnteSessionFactory) {
				if (relationEnteSessionFactory.containsKey(ente)) {
					// Recupero la sessionfactory associata
					synchronized (mapSessionFactory) {
						if (mapSessionFactory.containsKey(relationEnteSessionFactory.get(ente))) {
							return mapSessionFactory.get(relationEnteSessionFactory.get(ente));
						}
					}
				} else {
					log.info("Nessuna SessionFactory associata all'ente " + ente + " - provo a recuperare la SessionFactory di default");
				}
			}
			return getDefaultSessionFactory();
		} else {
			log.info("L'ente è null - provo a recuperare la SessionFactory di default");
			return getDefaultSessionFactory();
		}
	}

	private static SessionFactory getDefaultSessionFactory() throws Exception {
		log.debug("Ottengo la sessione di default: " + ConfigUtil.getConfig().getString(ConfigKey.DATABASE_DEFAULT));
		if (StringUtils.isNotBlank(ConfigUtil.getConfig().getString(ConfigKey.DATABASE_DEFAULT))) {
			synchronized (mapSessionFactory) {
				if (mapSessionFactory.containsKey(ConfigUtil.getConfig().getString(ConfigKey.DATABASE_DEFAULT))) {
					return mapSessionFactory.get(ConfigUtil.getConfig().getString(ConfigKey.DATABASE_DEFAULT));
				}
			}
		}
		throw new ServiceException(CoreConfig.modulename, "DEF_DB_NOT_FOUND");

	}

	/**
	 * Associa l'ente alla sessionfactory corrispondente
	 * 
	 * @param id
	 * @param idsessionfactory
	 */
	public static void addEnte(String id, String idsessionfactory) {
		synchronized (relationEnteSessionFactory) {
			relationEnteSessionFactory.put(id, idsessionfactory);
		}
	}

	/**
	 * Aggiunge una session hibernate al singleton, se esiste gia viene lanciato un errore
	 * 
	 * @param uuid
	 * @param session
	 * @throws Exception
	 *             Nel caso in cui esista gia una Session hibernate con lo stesso uuid
	 */
	public static void addSession(String uuid, Session session) throws Exception {
		if (StringUtils.isNotBlank(uuid)) {
			synchronized (sessions) {
				if (sessions.containsKey(uuid)) {
					throw new Exception("La session con uuid " + uuid + " esiste gia!");
				} else {
					session.beginTransaction();
					session.setFlushMode(FlushMode.COMMIT);
					SessionBean sessionbean = new SessionBean();
					sessionbean.setLastUse(new Date());
					sessionbean.setSession(session);
					sessionbean.setInUse(false);
					sessionbean.setUuid(uuid);
					sessions.put(uuid, sessionbean);
				}
			}
		} else {
			throw new Exception("uuid non valorizzato");
		}
	}

	public static boolean isForcedToCloseConnection(String uuid) throws Exception {
		if (StringUtils.isNotBlank(uuid)) {
			synchronized (connections) {
				if (!connections.containsKey(uuid)) {
					return true;
				} else
					return false;
			}
		} else
			return false;
	}

	public static void addConnection(String uuid, Connection connection) throws Exception {
		if (StringUtils.isNotBlank(uuid)) {
			synchronized (connections) {
				if (connections.containsKey(uuid)) {
					throw new Exception("La connessione con uuid " + uuid + " esiste gia!");
				} else {
					connections.put(uuid, connection);
				}

			}
		} else {
			throw new Exception("uuid non valorizzato");
		}
	}

	public static void closeConnection(String uuid) throws Exception {
		if (StringUtils.isNotBlank(uuid)) {
			synchronized (connections) {
				if (connections.containsKey(uuid)) {
					Connection lConnection = connections.get(uuid);
					connections.remove(uuid);
					lConnection.close();
				} else {
					// throw new Exception("La connessione con uuid "+uuid+" non esiste");
				}
			}
		}
	}

	public static void removeConnection(String uuid) throws Exception {
		if (StringUtils.isNotBlank(uuid)) {
			synchronized (connections) {
				if (connections.containsKey(uuid)) {
					connections.remove(uuid);
				} else {
					// throw new Exception("La connessione con uuid "+uuid+" non esiste");
				}
			}
		}
	}

	/**
	 * Aggiunge una session hibernate al singleton, se esiste gia viene lanciato un errore
	 * 
	 * @param uuid
	 * @param session
	 * @throws Exception
	 *             Nel caso in cui esista gia una Session hibernate con lo stesso uuid
	 */
	public static void addStatement(String uuid, Statement statement) throws Exception {
		if (StringUtils.isNotBlank(uuid)) {
			synchronized (statements) {

				if (statements.containsKey(uuid)) {
					throw new Exception("Lo statement con uuid " + uuid + " esiste gia!");
				} else {
					statements.put(uuid, statement);
				}

			}
		} else {
			throw new Exception("uuid non valorizzato");
		}
	}

	/**
	 * Aggiunge una session hibernate al singleton, se esiste gia viene lanciato un errore
	 * 
	 * @param uuid
	 * @param session
	 * @throws Exception
	 *             Nel caso in cui esista gia una Session hibernate con lo stesso uuid
	 */
	public static void closeStatement(String uuid) throws Exception {
		if (StringUtils.isNotBlank(uuid)) {
			synchronized (statements) {
				if (statements.containsKey(uuid)) {
					Statement lStatement = statements.get(uuid);
					lStatement.cancel();
				} else {
					// throw new
					// Exception("Lo Statement con uuid "+uuid+" non esiste");
				}
			}
		}
	}

	/**
	 * Aggiunge una session hibernate al singleton, se esiste gia viene lanciato un errore
	 * 
	 * @param uuid
	 * @param session
	 * @throws Exception
	 *             Nel caso in cui esista gia una Session hibernate con lo stesso uuid
	 */
	public static void removeStatement(String uuid) throws Exception {
		if (StringUtils.isNotBlank(uuid)) {
			synchronized (statements) {
				if (StringUtils.isNotBlank(uuid) && statements.containsKey(uuid)) {
					statements.remove(uuid);
				} else {
					// throw new
					// Exception("Lo Statement con uuid "+uuid+" non esiste");
				}
			}
		}
	}

	/**
	 * Aggiunge una session hibernate al singleton, se esiste gia viene lanciato un errore
	 * 
	 * @param uuid
	 * @param session
	 * @throws Exception
	 *             Nel caso in cui esista gia una Session hibernate con lo stesso uuid
	 */
	public static boolean isStatementClosed(String uuid) throws Exception {
		if (StringUtils.isNotBlank(uuid)) {
			synchronized (statements) {
				if (statements.containsKey(uuid)) {
					boolean result = statements.get(uuid).isClosed();
					statements.remove(uuid);
					return result;
				} else {
					return false;
				}
			}
		} else {
			return false;
		}
	}

	/**
	 * Toglie la Session dal singleton in base alla uuidtransaction settata sul ThreadLocal subject
	 * 
	 * @param uuid
	 * @throws Exception
	 */
	public static void deleteSession() throws Exception {
		if (StringUtils.isNotBlank(SubjectUtil.subject.get().getUuidtransaction())) {
			synchronized (sessions) {
				if (sessions.containsKey(SubjectUtil.subject.get().getUuidtransaction())) {
					sessions.remove(SubjectUtil.subject.get().getUuidtransaction());
				} else {
					throw new Exception("La session con uuid " + SubjectUtil.subject.get().getUuidtransaction() + " non esiste!");
				}
			}
		} else {
			throw new Exception("La session con uuid " + SubjectUtil.subject.get().getUuidtransaction() + " non esiste!");
		}
	}

	/**
	 * Restituisce la session di hibernate dall'uuid passato in ingresso
	 * 
	 * @throws Exception
	 *             Nel caso in cui la session corrispondente all'uuid non esista o sia scaduta
	 */
	public static Session openSession(String ente) throws Exception {
		return getSessionFactory(ente).openSession();
	}

	/**
	 * Restituisce la session di hibernate dall'uuid passato in ingresso
	 * 
	 * @throws Exception
	 *             Nel caso in cui la session corrispondente all'uuid non esista o sia scaduta
	 */
	public static Session begin() throws Exception {
		// Recupero della variabili dal ThreadLocal
		String uuid = SubjectUtil.subject.get().getUuidtransaction();
		String ente = SubjectUtil.subject.get().getIdDominio();
		Session session = null;
		boolean containSession = false;
		SessionBean sessionbean = null;
		if (StringUtils.isNotBlank(uuid)) {
			// Controllo se esite la connessione altrimenti lancio un errore
			synchronized (sessions) {
				containSession = sessions.containsKey(uuid);
				if (containSession) {
					sessionbean = sessions.get(uuid);
				}
			}
		}
		if (containSession) {
			sessionbean.setInUse(true);
			sessionbean.setLastUse(new Date());
			Session sessionret = sessionbean.getSession();
			currentSession.set(sessionret);
			return sessionret;
		} else {
			session = getSessionFactory(ente).openSession();
			// session.beginTransaction();
			currentSession.set(session);
			return session;

		}

	}

	/**
	 * Alle release delle sessione viene effettuato un flush e viene aggiornato lo stato e la data di utilizzo
	 * 
	 * @throws Exception
	 *             Nel caso in cui la session corrispondente all'uuid non esista o sia scaduta
	 */
	public static void release(Session session) throws Exception {
		// Recupero della variabili dal ThreadLocal
		String uuid = SubjectUtil.subject.get().getUuidtransaction();
		boolean containSession = false;
		SessionBean sessionbean = null;
		if (StringUtils.isNotBlank(uuid)) {
			synchronized (sessions) {
				containSession = sessions.containsKey(uuid);
				if (containSession) {
					sessionbean = sessions.get(uuid);
				}
			}
		}
		if (containSession) {
			sessionbean.setInUse(false);
			sessionbean.setLastUse(new Date());
			sessionbean.getSession().flush();
		} else {
			if (session != null) {
				// Effettuo un flush delle eventuali operazioni
				Transaction transaction = session.getTransaction();
				if (transaction != null) {
					if (!transaction.wasCommitted()) {
						if (transaction.isActive()) {
							transaction.rollback();
						}
					}
				}
				session.close();
			}
		}
	}

	/**
	 * Effettua rollback
	 * 
	 * @throws Exception
	 */
	public static void rollback(Session session) throws Exception {
		if (session != null) {
			// Effettuo un flush delle eventuali operazioni
			Transaction transaction = session.getTransaction();
			if (transaction != null) {
				if (!transaction.wasCommitted()) {
					if (transaction.isActive()) {
						transaction.rollback();
					}
				}
			}
		}
	}

	/**
	 * Effettua una commit della transazione passata in ingresso o eventualmente della Session hibernate
	 * 
	 * @param uuid
	 * @param session
	 * @throws Exception
	 */
	public static void commit(Session session) throws Exception {
		// Recupero della variabili dal ThreadLocal
		String uuid = SubjectUtil.subject.get().getUuidtransaction();
		boolean containSession = false;
		SessionBean sessionbean = null;
		if (StringUtils.isNotBlank(uuid)) {
			synchronized (sessions) {
				containSession = sessions.containsKey(uuid);
				if (containSession) {
					sessionbean = sessions.get(uuid);
				}
			}
		}
		if (containSession) {
			sessionbean.getSession().flush();
			return;
		} else if (session != null) {
			// Effettuo un flush delle eventuali operazioni
			Transaction transaction = session.getTransaction();
			if (transaction != null) {
				transaction.commit();
			}
		}
	}

	public static String getPrimaryKey(Class<?> entity) throws Exception {
		String ente = SubjectUtil.subject.get().getIdDominio();
		ClassMetadata meta = getSessionFactory(ente).getClassMetadata(entity);
		return meta.getIdentifierPropertyName();
	}

	public static String getPrimaryKey(String entityClassName) throws Exception {
		String ente = SubjectUtil.subject.get().getIdDominio();
		ClassMetadata meta = getSessionFactory(ente).getClassMetadata(entityClassName);
		return meta.getIdentifierPropertyName();
	}

	public static Type getPrimaryKeyType(Class<?> entity) throws Exception {
		String ente = SubjectUtil.subject.get().getIdDominio();
		ClassMetadata meta = getSessionFactory(ente).getClassMetadata(entity);
		if (meta == null) {
			return null;
		}
		return meta.getIdentifierType();
	}

	public static Type getPrimaryKeyType(String entityClassName) throws Exception {
		String ente = SubjectUtil.subject.get().getIdDominio();
		ClassMetadata meta = getSessionFactory(ente).getClassMetadata(entityClassName);
		if (meta == null) {
			return null;
		}
		return meta.getIdentifierType();
	}

	/**
	 * ritorna i metadati associati alle entity caricate nella sessionfactory dell'ente corrente
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String, ClassMetadata> getEntities() throws Exception {
		String ente = SubjectUtil.subject.get().getIdDominio();
		return getSessionFactory(ente).getAllClassMetadata();
	}

	public static Map<String, CollectionMetadata> getCollectionMeta() throws Exception {
		String ente = SubjectUtil.subject.get().getIdDominio();
		return getSessionFactory(ente).getAllCollectionMetadata();
	}

	/**
	 * Meta data della tabella
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static SingleTableEntityPersister getTableEntityPersister(Class<?> entity) throws Exception {
		String ente = SubjectUtil.subject.get().getIdDominio();
		Map<String, ClassMetadata> metadata = getSessionFactory(ente).getAllClassMetadata();
		Iterator<String> metaiterator = metadata.keySet().iterator();
		SingleTableEntityPersister ret = null;
		while (metaiterator.hasNext()) {
			String meta = metaiterator.next();
			String entityname = ((SingleTableEntityPersister) metadata.get(meta)).getEntityName();
			// to TEST pua essere necessario se l'oggetto a proxato (ad es:
			// collection of relation) ma ci vuole l'entity non la class
			// String
			// objentityname=org.hibernate.proxy.HibernateProxyHelper.getClassWithoutInitializingProxy(entity).getName();
			// if (entity instanceof HibernateProxy) {
			// entity =
			// ((HibernateProxy)entity).getHibernateLazyInitializer().getImplementation();
			// }
			if (entity.getName().equals(entityname)) {
				ret = (SingleTableEntityPersister) metadata.get(meta);
				break;
			}
		}
		return ret;
	}

	/**
	 * Prepara i criteri di ordinamento
	 * 
	 * @param criteria
	 * @param orderByList
	 * @return
	 * @throws Exception
	 */
	public static Criteria addOrderCriteria(Criteria criteria, List<TOrderBy> orderByList) throws Exception {
		if (orderByList != null) {
			for (TOrderBy orderBy : orderByList) {
				if (StringUtils.isNotBlank(orderBy.getPropname())) {
					OrderByType orderByType = (orderBy.getType() != null) ? orderBy.getType() : OrderByType.ASCENDING;
					if (orderByType.equals(OrderByType.ASCENDING)) {
						criteria.addOrder(Order.asc(orderBy.getPropname()));
					} else if (orderByType.equals(OrderByType.DESCENDING)) {
						criteria.addOrder(Order.desc(orderBy.getPropname()));
					}
				}
			}
		}
		return criteria;
	}

	public static Criteria addKeyFilter(Criteria criteria, Criterion mainFilter, Class<?> entityClazz, TKeyFilter keyFilter) throws Exception {
		if (keyFilter != null) {
			Criterion critIds = CriteriaUtil.checkIds(entityClazz, keyFilter);
			if (critIds != null) {
				// ((CriteriaImpl)criteria).getEntityOrClassName();
				// aggiungo filtro sugli id
				criteria.add(Restrictions.or(mainFilter, critIds));
			} else {
				// il filtro sugli id non ritorna nulla per cui a un filtro
				// vuoto
				criteria.add(mainFilter);
			}
		} else {
			criteria.add(mainFilter);
		}
		return criteria;
	}

	public static String getEntitypackage() {
		return entitypackage;
	}

	public static void setEntitypackage(String entitypackage) {
		if (HibernateUtil.entitypackage != null) {
			log.warn("warning entity package change from " + HibernateUtil.entitypackage + " to " + entitypackage);
		}
		log.debug("setting entitypackage to " + entitypackage);
		HibernateUtil.entitypackage = entitypackage;
	}

	public static String getTableName(String ente, Class<?> entityClazz) throws Exception {
		String ret = null;
		ClassMetadata hibernateMetadata = getSessionFactory(ente).getClassMetadata(entityClazz);
		if (hibernateMetadata == null) {
			return ret;
		}
		if (hibernateMetadata instanceof AbstractEntityPersister) {
			AbstractEntityPersister persister = (AbstractEntityPersister) hibernateMetadata;
			String tableName = persister.getTableName();
			ret = tableName;
			// String[] columnNames = persister.getKeyColumnNames();
		}
		return ret;
	}

	/**
	 * Ritorna la sessione corrente sullo stesso thread
	 * 
	 * @return
	 */
	public static Session getCurrentSessionThread() {
		return currentSession.get();
	}

	/**
	 * Pagina la lista corrispondente a quei criteria
	 * 
	 * @param criteria
	 * @param startRow
	 * @param endRow
	 * @return
	 * @throws Exception
	 */
	public static List<?> pagingByCriteria(Criteria criteria, Integer startRow, Integer endRow) throws Exception {
		criteria.setFirstResult((startRow != null) ? startRow : 0);
		if (endRow != null) {
			criteria.setMaxResults(endRow - startRow + 1);
		}
		return criteria.list();
	}

	/**
	 * applica la paginazione ad un criteria
	 * 
	 * @param criteria
	 * @param tFilter
	 * @return
	 * @throws Exception
	 */
	public static Criteria pagingByCriteria(Criteria criteria, TFilterFetch<?> tFilter) throws Exception {
		Integer startRow = tFilter.getStartRow();
		startRow = (startRow != null) ? startRow : 0;
		Integer endRow = tFilter.getEndRow();
		criteria.setFirstResult(startRow);
		if (endRow != null) {
			criteria.setMaxResults(endRow - startRow + 1);
		}
		return criteria;
	}

	public static List<String> getListEntityPackage() {
		return listEntityPackage;
	}

	public static void setListEntityPackage(List<String> listEntityPackage) {
		if (HibernateUtil.listEntityPackage != null) {
			log.warn("warning entity package change from " + HibernateUtil.listEntityPackage + " to " + listEntityPackage);
		}
		HibernateUtil.listEntityPackage = listEntityPackage;
	}

}