/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "hibernate-configuration")
public class HibernateStorageConfig implements Serializable {

	private static final long serialVersionUID = -6714264542325727927L;

	private HibernateSessionFactory sessionFactory;

	public HibernateStorageConfig() {
		// NECESSARIO PER JAXB
	}
	
	public static class HibernateSessionFactory implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private HibernateSessionProperty[] property;
		
		public HibernateSessionFactory() {
			// NECESSARIO PER JAXB
		}
		
		public HibernateSessionProperty[] getProperty() {
			return property;
		}
		public void setProperty(HibernateSessionProperty[] property) {
			this.property = property;
		}
	}

	public static class HibernateSessionProperty implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String name;
		private String value;

		public HibernateSessionProperty() {
			// NECESSARIO PER JAXB
		}
		
		public HibernateSessionProperty(String name, String value) {
			this.name = name;
			this.value = value;
		}
		
		@XmlAttribute(required=true)
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		@XmlValue
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
	}
	
	@XmlElement(name = "session-factory")
	public HibernateSessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(HibernateSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
