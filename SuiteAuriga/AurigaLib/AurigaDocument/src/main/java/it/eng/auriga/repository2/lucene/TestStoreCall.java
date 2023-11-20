/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_titolario.bean.DmpkTitolarioGetidpianoclassifspaooBean;
import it.eng.auriga.database.store.dmpk_titolario.store.Getidpianoclassifspaoo;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.ReflectionUtil;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.core.config.ConfigUtil;
import it.eng.spring.utility.SpringAppContext;

import java.math.BigDecimal;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.hibernate.cfg.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestStoreCall {
	
	private static Logger log = Logger.getLogger(TestStoreCall.class);
	
	public static void main(String[] args) throws Exception {
		SpringAppContext.setContext(new ClassPathXmlApplicationContext("aurigarepositoryContext.xml"));
		//prepara una connessione
		Configuration configuration = new Configuration();
		configuration.configure("hibernate-auriga.cfg.xml");

		//Configuro il db dello storage
		//			StorageConfig.configure("C:/data", "storage", "/storage-auriga.cfg.xml");

		ConfigUtil.initialize();

		HibernateUtil.setEntitypackage("it.eng.auriga.module.business.entity");
		ReflectionUtil.setEntityPackage("it.eng.auriga.module.business.entity");

		HibernateUtil.addSessionFactory("trasversale", configuration);
		HibernateUtil.addEnte("trasversaleEnte", "trasversale");

		SubjectBean lSubjectBean = new SubjectBean();
		lSubjectBean.setIdapplicazione("WS");
		lSubjectBean.setIdDominio("WS");
		lSubjectBean.setIduser("WS");
		lSubjectBean.setTokenid("WS");
		lSubjectBean.setUuidtransaction(UUID.randomUUID().toString());
		SubjectUtil.subject.set(lSubjectBean);
		DmpkTitolarioGetidpianoclassifspaooBean bean = new DmpkTitolarioGetidpianoclassifspaooBean();
		bean.setIdspaooin(new BigDecimal("2"));
		Getidpianoclassifspaoo store = new Getidpianoclassifspaoo();
		SchemaBean lSchemaBean = new SchemaBean();
		lSchemaBean.setSchema("OWNER_1");
		StoreResultBean<DmpkTitolarioGetidpianoclassifspaooBean> result = store.execute(lSchemaBean, bean);
		if (result.isInError()) log.error(result.getDefaultMessage());
		else result.getResultBean();
	}

}
