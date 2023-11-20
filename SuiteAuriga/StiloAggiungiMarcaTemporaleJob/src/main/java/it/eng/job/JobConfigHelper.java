/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.lang.invoke.MethodHandles;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import it.eng.config.AurigaBusinessClientConfig;
import it.eng.core.config.ConfigUtil;
import it.eng.core.service.client.FactoryBusiness.BusinessType;
import it.eng.core.service.client.config.Configuration;
import it.eng.core.service.serialization.SerializationType;

public class JobConfigHelper {

	final static Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private BusinessType jobBusinessType;

	private String nodeName = "";

	private static JobConfigHelper instance;

	private JobConfigHelper(AurigaJobManager aurigaJobManager) {
		// inizializzo core ecc.
		try {

			this.nodeName = aurigaJobManager.getNodename();

			if ("API".equalsIgnoreCase(aurigaJobManager.getBusinessType()))
				jobBusinessType = BusinessType.API;
			else if ("SOAP".equalsIgnoreCase(aurigaJobManager.getBusinessType()))
				jobBusinessType = BusinessType.SOAP;
			else
				jobBusinessType = BusinessType.REST;

			ConfigUtil.initialize();

			Configuration.getInstance().setSerializationtype(SerializationType.XML);

			//Configuration.getInstance().setUrl(aurigaJobManager.getServiceUrl());
			//Configuration.getInstance().setBusinesstype(jobBusinessType);

			//AurigaBusinessClientConfig.getInstance().setBusinesstype(jobBusinessType);
			//AurigaBusinessClientConfig.getInstance().setUrl(aurigaJobManager.getServiceUrl());

			//Configuration.getInstance().initClient();

			//logger.info("BUSINESS TYPE = " + jobBusinessType.toString());

		} catch (Exception e) {
			logger.error("Errore configurazione coremodule: " + e.getMessage(), e);
		}
	}

	public synchronized static JobConfigHelper initialize(AurigaJobManager irisJobManager) {
		if (instance == null)
			instance = new JobConfigHelper(irisJobManager);
		return instance;
	}

	public static JobConfigHelper getInstance() {
		return instance;
	}

	public String getNodeName() {
		return nodeName;
	}

	public BusinessType getJobBusinessType() {
		return jobBusinessType;
	}

	public void setJobBusinessType(BusinessType jobBusinessType) {
		this.jobBusinessType = jobBusinessType;
	}

}
