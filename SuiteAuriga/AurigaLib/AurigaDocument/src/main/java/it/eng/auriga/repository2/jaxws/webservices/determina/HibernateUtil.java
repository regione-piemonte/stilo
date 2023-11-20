/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.connection.C3P0ConnectionProvider;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.resolver.DialectResolver;
import org.hibernate.dialect.resolver.StandardDialectResolver;
import org.hibernate.impl.SessionFactoryImpl;
import org.hibernate.jdbc.Work;
import org.hibernate.stat.SessionStatistics;
import org.hibernate.stat.Statistics;
import org.reflections.Reflections;

/**
 * WSGetDetermina
 */

/**
 * Classe di gestione delle connessioni duplicata dal core
 * 
 * 
 */
public class HibernateUtil {

    static Logger log = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	// private static SessionFactory sessionFactory;

	private static HashMap<String, SessionFactory> sessionFactories;
	
	private static SessionFactory sessionFactory;
	
	private static String sessionModule;

	private static String entitypackage;

	private static Configuration configuration;

	static {
		sessionFactories = new HashMap<String, SessionFactory>();
	}

	public static SessionFactory buildSessionFactory(String configFile, String jobName) {
		try {
			log.info("Verifico il conf file: " + configFile);
			if (configuration == null) {
				log.info("configuration is NULL ");
				configuration = new Configuration();

				if (entitypackage != null) {
					Reflections reflections = new Reflections(entitypackage);
					Set<Class<?>> entities = reflections.getTypesAnnotatedWith(Entity.class);
					Iterator<Class<?>> iteratore = entities.iterator();
					while (iteratore.hasNext()) {
						Class<?> entityClass = iteratore.next();
						log.debug("Aggiungo la classe entity " + entityClass.getName());
						configuration.addAnnotatedClass(entityClass);
					}
				}

				log.info("Utilizzo il file di configurazione per la connessione al db: " + configFile);
				configuration.configure(configFile);
			}
			else
			{
				log.info("configuration is NOT NULL");
			}
			// Create the SessionFactory from hibernate.cfg.xml
			if (!sessionFactories.containsKey(jobName)) {
				try {
					sessionFactory = configuration.buildSessionFactory();
				} catch (Exception e) {
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e.printStackTrace(pw);
					log.error("configuration.buildSessionFactor: "+sw.toString());
					return null;
				}
				
				sessionFactories.put(jobName, sessionFactory);
		    }
			sessionModule = jobName;

			return sessionFactory;

		} catch (Exception ex) {
			log.error("Error:", ex.getMessage());
			closeConnection(jobName);
			return null;
		}
	}

	public static SessionFactory getSessionFactory(String jobName) {
		synchronized(sessionFactories){
			return sessionFactories.get(jobName);	
		}
	}

	public static Session openSession(String jobName) throws Exception {
		log.debug("apro la connessione al db");
		Session session = getSessionFactory(jobName).openSession();
		return session;
	}

	public static Session openSession(String jobName, String action) throws Exception {
		Session session = openSession(jobName);
		if (action != null) {
			session.createSQLQuery("BEGIN dbms_application_info.set_module('" + jobName + "', '" + action + "'); END;").executeUpdate();
			log.debug("connessione aperta da " + jobName + " per " + action);
		}
		return session;
	}

	public static StatelessSession openStatelessSession(String jobName) throws Exception {
		log.debug("apro la connessione al db");
		return getSessionFactory(jobName).openStatelessSession();

	}

	public static Session begin(String jobName) throws Exception {
		return begin(jobName, null);

	}

	public static Session begin(String jobName, String action) throws Exception {
		Session session = openSession(jobName, action);
		return session;

	}

	/**
	 * Alle release delle sessione viene effettuato un flush e viene aggiornato lo stato e la data di utilizzo
	 * 
	 * @throws Exception
	 *             Nel caso in cui la session corrispondente all'uuid non esista o sia scaduta
	 */
	public static void release(Session session) throws Exception {
		if (session != null) {
			// Effettuo un flush delle eventuali operazioni
			log.debug("chiudo la connessione al db per questa sessione, eseguendo il rollback delle operazioni pendenti.");
			Transaction transaction = session.getTransaction();
			if (transaction != null) {
				if (!transaction.wasCommitted()) {
					if (transaction.isActive()) {
						log.warn("Attenzione: eseguito rollback");
						transaction.rollback();
					}
				}
			}
			session.close();
		}
	}

	public static void closeSession(Session session) {
		if (session != null) {
			log.debug("chiudo la connessione al db per questa sessione");
			SessionStatistics sessionStats = session.getStatistics();
			log.debug("closeSession sessionStats.toString(): "+sessionStats.toString());
			try {
				session.close();
			} catch (Exception e) {
				log.error("Errore in chiusura della sessione", e);
			}
		}
	}
	public static void closeConnection() {
		
		if (sessionFactory != null) {
			Statistics stats = sessionFactory.getStatistics();
			log.debug("stats.toString() before: "+stats.toString());
			log.debug("chiudo la connessione al db");
			try {
				if (sessionFactory instanceof SessionFactoryImpl) {
					SessionFactoryImpl sfi = (SessionFactoryImpl) sessionFactory;
					ConnectionProvider conn = sfi.getConnectionProvider();
					if (conn instanceof C3P0ConnectionProvider) {
						((C3P0ConnectionProvider) conn).close();
					}
				}
				log.debug("stats.toString() after: "+stats.toString());
				sessionFactory.close();
			} catch (Exception e) {
				log.error("Errore in chiusura della connessione", e);
			}
		}
	}
	public static void closeConnection(String jobName) {
		SessionFactory sessionFactory = getSessionFactory(jobName);
		log.info("sessionFactory closeConnection "+jobName+ " : "+sessionFactory);
		if (sessionFactory != null) {
			log.debug("chiudo la connessione al db");
			Statistics stats = sessionFactory.getStatistics();
			log.debug("stats.toString() before: "+stats.toString());
			try {
				if (sessionFactory instanceof SessionFactoryImpl) {
					SessionFactoryImpl sfi = (SessionFactoryImpl) sessionFactory;
					ConnectionProvider conn = sfi.getConnectionProvider();
					if (conn instanceof C3P0ConnectionProvider) {
						((C3P0ConnectionProvider) conn).close();
					}
				}
				sessionFactory.close();
				sessionFactories.remove(jobName);
				log.info("sessionFactories.remove");
			} catch (Exception e) {
				log.error("Errore in chiusura della connessione", e);
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
		if (session != null) {
			Transaction transaction = null;
			// Effettuo un flush delle eventuali operazioni
			if (session.getTransaction() != null)
			{	
			transaction = session.getTransaction();
			}
			else
			{
				session.beginTransaction();
			}
			if (transaction != null && !transaction.wasCommitted()) {
				try {
					log.debug("commit");
					transaction.commit();
				} catch (Exception e) {
					log.error("Errore ", e);
					e.printStackTrace();
				}
			}
		}
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

	public static Configuration getConfiguration() {
		return configuration;
	}

	public static int getLunghezzaCampo(Class<?> entityClass, String propName) {
		Column annotation = null;
		try {
			Field fld = entityClass.getDeclaredField(propName);
			annotation = fld.getAnnotation(Column.class);
			if (annotation != null) {
				if (fld.getType().equals(String.class)) {
					return annotation.length();
				} else {
					return annotation.precision();
				}
			}
		} catch (Exception e) {
			log.error(String.format("Errore nella reflection del campo %s per la classe %s", propName, entityClass), e);
		}

		String nomeMetodoGet = "get" + StringUtils.capitalize(propName);
		try {
			Method mtd = entityClass.getMethod(nomeMetodoGet);
			annotation = mtd.getAnnotation(Column.class);
			if (annotation != null) {
				if (mtd.getReturnType().equals(String.class)) {
					return annotation.length();
				} else {
					return annotation.precision();
				}
			}
		} catch (Exception e) {
			log.error(String.format("Errore nella reflection del metodo %s per la classe %s", nomeMetodoGet, entityClass), e);
		}

		return -1;
	}

	// For Hibernate 3
	public static Long getNextVal(final String sequenceName) throws Exception {
		Session session = null;
		try {
			final List<Long> ids = new ArrayList<Long>(1);

			session = begin(sequenceName + ".NextVal");
			session.doWork(new Work() {

				public void execute(Connection connection) throws SQLException {
					DialectResolver dialectResolver = new StandardDialectResolver();
					Dialect dialect = dialectResolver.resolveDialect(connection.getMetaData());
					PreparedStatement preparedStatement = null;
					ResultSet resultSet = null;
					try {
						preparedStatement = connection.prepareStatement(dialect.getSequenceNextValString(sequenceName));
						resultSet = preparedStatement.executeQuery();
						resultSet.next();
						ids.add(resultSet.getLong(1));
					} catch (SQLException e) {
						throw e;
					} finally {
						if (preparedStatement != null) {
							preparedStatement.close();
						}
						if (resultSet != null) {
							resultSet.close();
						}
					}
				}
			});
			return ids.get(0);
		} finally {
			closeSession(session);
		}
	}

	
	public static void setEntitypackage(String entitypackage) {
		if (HibernateUtil.entitypackage != null) {
			log.warn("warning entity package change from " + HibernateUtil.entitypackage + " to " + entitypackage);
		}
		log.debug("setting entitypackage to " + entitypackage);
		HibernateUtil.entitypackage = entitypackage;
	}
}