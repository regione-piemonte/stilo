package it.eng.suiteutility.module.mimedb.dao;

//import it.eng.core.business.TFilterFetch;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
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
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.impl.SessionFactoryImpl;
import org.hibernate.jdbc.Work;
import org.reflections.Reflections;

/**
 * Classe di gestione delle connessioni duplicata dal core
 * 
 */
public class HibernateUtil {

	static Logger log = Logger.getLogger(HibernateUtil.class);

	private static SessionFactory sessionFactory;

	private static String entitypackage;

	private static String sessionModule;

	private static Configuration configuration;

	/**
	 * Aggiunge una sessionFactory al sistema
	 * 
	 * @param configFile
	 * @param jobName
	 *            da inserire nella v$session nel campo MODULE
	 * @return SessionFactory
	 */
	public static SessionFactory buildSessionFactory(String configFile, String jobName) {
		try {
			// aggiungi le classi annotate alla configurazione
			if (entitypackage == null) {
				throw new Exception("Nessun entitypackage configurato!");
			}
			configuration = new Configuration();

			Reflections reflections = new Reflections(entitypackage);
			Set<Class<?>> entities = reflections.getTypesAnnotatedWith(Entity.class);
			Iterator<Class<?>> iteratore = entities.iterator();
			while (iteratore.hasNext()) {
				Class<?> entityClass = iteratore.next();
				log.debug("Aggiungo la classe entity " + entityClass.getName());
				configuration.addAnnotatedClass(entityClass);
			}

			log.info("Utilizzo il file di configurazione per la connessione al db: " + configFile);
			configuration.configure(configFile);
			log.info("Configurazione effettuata, costruisco la sessionFactory");
			

			// Create the SessionFactory from hibernate.cfg.xml
			sessionFactory = configuration.buildSessionFactory();
			log.info("sessionFactory costruita");
			
			sessionModule = jobName;

			return sessionFactory;

		} catch (Throwable ex) {
			ex.printStackTrace();
			log.error("Error:", ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static Session openSession() throws JDBCConnectionException, Exception {
		//log.debug("apro la connessione al db");
		
		Session session = getSessionFactory().openSession();
		return session;
	}

	public static Session openSession(String action) throws JDBCConnectionException, Exception {
		Session session = openSession();
		
		if (action != null) {
			SQLQuery query = session.createSQLQuery(
					"BEGIN dbms_application_info.set_module('" + sessionModule + "', '" + action + "'); END;");
			query.executeUpdate();
			
			//log.debug("connessione aperta da " + sessionModule + " per " + action);
		}
		return session;
	}

	public static StatelessSession openStatelessSession() throws Exception {
		//log.debug("apro la connessione al db");
		return getSessionFactory().openStatelessSession();

	}

	public static Session begin() throws Exception {
		return begin(null);

	}

	public static Session begin(String action) throws JDBCConnectionException, Exception {
		Session session = openSession(action);
		// session.createSQLQuery("BEGIN dbms_application_info.set_module('" +
		// sessionModule + "', '" + action + "'); END;").executeUpdate();
		session.beginTransaction();
		return session;

	}

	/**
	 * Alle release delle sessione viene effettuato un flush e viene aggiornato
	 * lo stato e la data di utilizzo
	 * 
	 * @throws Exception
	 *             Nel caso in cui la session corrispondente all'uuid non esista
	 *             o sia scaduta
	 */
	public static void release(Session session) throws JDBCConnectionException, Exception {
		if (session != null) {
			// Effettuo un flush delle eventuali operazioni
			//log.debug("chiudo la connessione al db per questa sessione, eseguendo il rollback delle operazioni pendenti.");
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
			//log.debug("chiudo la connessione al db per questa sessione");
			try {
				session.close();
			} catch (Exception e) {
				log.error("Errore in chiusura della sessione", e);
			}
		}
	}

	public static void closeConnection() {
		if (sessionFactory != null) {
			//log.debug("chiudo la connessione al db");
			try {
				if (sessionFactory instanceof SessionFactoryImpl) {
					SessionFactoryImpl sfi = (SessionFactoryImpl) sessionFactory;
					ConnectionProvider conn = sfi.getConnectionProvider();
					if (conn instanceof C3P0ConnectionProvider) {
						((C3P0ConnectionProvider) conn).close();
					}
				}
				sessionFactory.close();
			} catch (Exception e) {
				log.error("Errore in chiusura della connessione", e);
			}
		}
	}

	/**
	 * Effettua una commit della transazione passata in ingresso o eventualmente
	 * della Session hibernate
	 * 
	 * @param uuid
	 * @param session
	 * @throws Exception
	 */
	public static void commit(Session session) throws JDBCConnectionException, Exception {
		if (session != null) {
			// Effettuo un flush delle eventuali operazioni
			Transaction transaction = session.getTransaction();
			if (transaction != null && !transaction.wasCommitted()) {
				try {
					//log.debug("commit");
					transaction.commit();
				} catch (Exception e) {
					log.error("Errore ", e);
				}
			}
		}
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

	/**
	 * Pagina la lista corrispondente a quei criteria
	 * 
	 * @param criteria
	 * @param startRow
	 * @param endRow
	 * @return
	 * @throws Exception
	 */
	public static List<?> pagingByCriteria(Criteria criteria, Integer startRow, Integer endRow) throws JDBCConnectionException, Exception {
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
//	public static Criteria pagingByCriteria(Criteria criteria, TFilterFetch<?> tFilter) throws JDBCConnectionException, Exception {
//		Integer startRow = tFilter.getStartRow();
//		startRow = (startRow != null) ? startRow : 0;
//		Integer endRow = tFilter.getEndRow();
//		criteria.setFirstResult(startRow);
//		if (endRow != null) {
//			criteria.setMaxResults(endRow - startRow + 1);
//		}
//		return criteria;
//	}

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
			log.error(
					String.format("Errore nella reflection del metodo %s per la classe %s", nomeMetodoGet, entityClass),
					e);
		}

		return -1;
	}
	
	// For Hibernate 3
	public static Long getNextVal(final String sequenceName) throws JDBCConnectionException, Exception {
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
	
	/*
		// For Hibernate 4
		public Long getID(final String sequenceName) {
		    ReturningWork<Long> maxReturningWork = new ReturningWork<Long>() {
		        @Override
		        public Long execute(Connection connection) throws SQLException {
		            DialectResolver dialectResolver = new StandardDialectResolver();
		            Dialect dialect =  dialectResolver.resolveDialect(connection.getMetaData());
		            PreparedStatement preparedStatement = null;
		            ResultSet resultSet = null;
		            try {
		                preparedStatement = connection.prepareStatement( dialect.getSequenceNextValString(sequenceName));
		                resultSet = preparedStatement.executeQuery();
		                resultSet.next();
		                return resultSet.getLong(1);
		            }catch (SQLException e) {
		                throw e;
		            } finally {
		                if(preparedStatement != null) {
		                    preparedStatement.close();
		                }
		                if(resultSet != null) {
		                    resultSet.close();
		                }
		            }
		
		        }
		    };
		    Long maxRecord = sessionFactory.getCurrentSession().doReturningWork(maxReturningWork);
		    return maxRecord;
		}
	 */
	
}