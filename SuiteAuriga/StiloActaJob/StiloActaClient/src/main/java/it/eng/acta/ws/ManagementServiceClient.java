/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.Dependent;
import javax.xml.ws.BindingProvider;

import it.doqui.acta.acaris.common.AnnotazioniPropertiesType;
import it.doqui.acta.acaris.common.EnumCommonObjectType;
import it.doqui.acta.acaris.common.IdAnnotazioniType;
import it.doqui.acta.acaris.common.ObjectIdType;
import it.doqui.acta.acaris.common.PrincipalIdType;
import it.doqui.acta.acaris.management.VitalRecordCodeType;
import it.doqui.acta.acaris.managementservice.ManagementService;
import it.doqui.acta.acaris.managementservice.ManagementServicePort;

@Dependent
public class ManagementServiceClient extends AbstractServiceClient {

    private static final String SERVICE_NAME = "service.management";
    private ManagementServicePort managementServicePort;

    public ManagementServiceClient() {
        super(SERVICE_NAME);
    }

    @Override
    protected void initService() {
        printInfo(SERVICE_NAME, "init");
        final URL url = getServiceURL(SERVICE_NAME);
        if (managementServicePort == null) {
            try {
                final ManagementService backOfficeService = new ManagementService(url);
                managementServicePort = backOfficeService.getManagementServicePort();
                setHandler(((BindingProvider) managementServicePort).getBinding());
                printInfo(SERVICE_NAME, "initialized");
            } catch (Exception e) {
                printError(SERVICE_NAME, "init", e);
                throw e;
            }
        }
    }

    public List<VitalRecordCodeType> getVitalRecordCode(final ObjectIdType repositoryId,
                                                        final Map<String, Object> paramMap) throws Exception {
        printInfo(SERVICE_NAME, "get-vitalRecordCode");
        List<VitalRecordCodeType> vitalRecordCodes = null;
        try {
            initService();
            setServiceIdentifier((BindingProvider) managementServicePort, paramMap);
            vitalRecordCodes = managementServicePort.getVitalRecordCode(repositoryId);
        } catch (Exception e) {
            printError(SERVICE_NAME, "get-vitalRecordCode", e.getMessage());
            throw e;
        }
        return vitalRecordCodes;
    }
    
    public ObjectIdType addAnnotazioni(final ObjectIdType repositoryId, final PrincipalIdType principalId,
    		final Map<String, Object> paramMap, String annotazione, boolean annotazioneFormale, ObjectIdType objectIdDocumento) throws Exception{
    	printInfo(SERVICE_NAME, "addAnnotazioni");
    	ObjectIdType objAnnotazione = null;
    	try {
    		initService();
    		setServiceIdentifier((BindingProvider) managementServicePort, paramMap);
    		
    		AnnotazioniPropertiesType annType = new AnnotazioniPropertiesType();
			annType.setDescrizione(annotazione);
			annType.setAnnotazioneFormale(annotazioneFormale);
			annType.setObjectTypeId(EnumCommonObjectType.ANNOTAZIONI_PROPERTIES_TYPE);
			
    		
			IdAnnotazioniType idAnnotazione = managementServicePort.addAnnotazioni(repositoryId, objectIdDocumento, principalId, annType);
    	} catch (Exception e) {
    		printError(SERVICE_NAME, "addAnnotazioni", e.getMessage());
    		throw e;
    	}
    	return objAnnotazione;
    }
    
    

}
