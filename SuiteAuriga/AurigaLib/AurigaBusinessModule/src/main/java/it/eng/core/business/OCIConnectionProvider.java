/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map.Entry;
import java.util.Properties;

import oracle.jdbc.pool.OracleOCIConnectionPool;

import org.hibernate.HibernateException;
import org.hibernate.connection.ConnectionProvider;

public class OCIConnectionProvider implements ConnectionProvider {

	   private OracleOCIConnectionPool pool;
	   
	   public OCIConnectionProvider() {
	   }
	   
	   @Override
	   public void configure(Properties props) throws HibernateException {
	      
	      try {
	         // Mandatory properties
	         String url = props.getProperty("connection.url");
	         String userName = props.getProperty("connection.username");
	         String password = props.getProperty("connection.password");
	      
	         pool = new OracleOCIConnectionPool();
	         pool.setURL(url);
	         pool.setUser(userName);
	         pool.setPassword(password);	         
	         
	         // Pool properties
	         Properties poolConfig = new Properties();
	         for (Entry<Object,Object> entry : props.entrySet()) {
	            if (entry.getKey().equals("oci.min_limit")) {
	               poolConfig.put(OracleOCIConnectionPool.CONNPOOL_MIN_LIMIT, entry.getValue());
	            } else if (entry.getKey().equals("oci.max_limit")) {
	               poolConfig.put(OracleOCIConnectionPool.CONNPOOL_MAX_LIMIT, entry.getValue());
	            } else if (entry.getKey().equals("oci.increment")) {
	               poolConfig.put(OracleOCIConnectionPool.CONNPOOL_INCREMENT, entry.getValue());
	            } else if (entry.getKey().equals("oci.timeout")) {
	               poolConfig.put(OracleOCIConnectionPool.CONNPOOL_TIMEOUT, entry.getValue());
	            } else if (entry.getKey().equals("oci.nowait")) {
	               poolConfig.put(OracleOCIConnectionPool.CONNPOOL_NOWAIT, entry.getValue());
	            }
	         }
	         pool.setPoolConfig(poolConfig);
	         
	      } catch (Exception e) {
	         throw new HibernateException("Cannot configure connection pool",e); 
	      }
	   }

	   @Override
	   public Connection getConnection() throws SQLException {
	      return pool.getConnection();
	   }
	   
	   @Override
	   public void closeConnection(Connection conn) throws SQLException {
	      conn.close();
	   }

	   @Override
	   public void close() throws HibernateException {
	      try {
	         pool.close();
	      } catch (SQLException e) {
	         throw new HibernateException(e);
	      }
	   }

	   @Override
	   public boolean supportsAggressiveRelease() {
	      return false;
	   }

	}