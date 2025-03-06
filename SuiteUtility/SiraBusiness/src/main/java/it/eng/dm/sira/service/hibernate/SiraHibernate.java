package it.eng.dm.sira.service.hibernate;

import it.eng.core.business.HibernateUtil;
import it.eng.core.business.ReflectionUtil;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;

import org.apache.log4j.Logger;
import org.hibernate.cfg.Configuration;

public class SiraHibernate {

	private static Logger mLogger = Logger.getLogger(SiraHibernate.class);
	
	private String idDominio;
	
	public SiraHibernate() throws Exception{
		mLogger.debug("Inizializzo Sira hibernate");
		Configuration configuration = new Configuration();
		configuration.configure("hibernate.sira.cfg.xml");
		HibernateUtil.setEntitypackage("it.eng.dm.sira.entity");
		ReflectionUtil.setEntityPackage("it.eng.dm.sira.entity");
		HibernateUtil.addSessionFactory("sirabusiness", configuration);
		HibernateUtil.addEnte("sirabusiness", "sirabusiness");
		idDominio = "sirabusiness";
		mLogger.debug("Inizializzo Sira hibernate");
	}
	
	public void initSubject(){
		SubjectBean lSubjectBean = new SubjectBean();
		lSubjectBean.setIdDominio(idDominio);
		SubjectUtil.subject.set(lSubjectBean);
	}
}

