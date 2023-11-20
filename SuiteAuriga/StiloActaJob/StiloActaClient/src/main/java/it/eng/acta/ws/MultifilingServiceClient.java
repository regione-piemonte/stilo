/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.net.URL;
import java.util.Map;

import javax.enterprise.context.Dependent;
import javax.xml.ws.BindingProvider;

import it.doqui.acta.acaris.common.ObjectIdType;
import it.doqui.acta.acaris.common.PrincipalIdType;
import it.doqui.acta.acaris.common.VarargsType;
import it.doqui.acta.acaris.multifilingservice.AcarisException;
import it.doqui.acta.acaris.multifilingservice.MultifilingService;
import it.doqui.acta.acaris.multifilingservice.MultifilingServicePort;

@Dependent
public class MultifilingServiceClient extends AbstractServiceClient {

    private static final String SERVICE_NAME = "service.multifilling";
    private static final String CLASSIFICATION_ERROR_SER_E_167 = "SER_E_167";
    private MultifilingServicePort multifilingServicePort;

    public MultifilingServiceClient() {
        super(SERVICE_NAME);
    }

    @Override
    protected void initService() {
        printInfo(SERVICE_NAME, "init");
        final URL url = getServiceURL(SERVICE_NAME);
        if (multifilingServicePort == null) {
            try {
                final MultifilingService multiFilingService = new MultifilingService(url);
                multifilingServicePort = multiFilingService.getMultifilingServicePort();
                setHandler(((BindingProvider) multifilingServicePort).getBinding());
                printInfo(SERVICE_NAME, "initialized");
            } catch (Exception e) {
                printError(SERVICE_NAME, "init", e);
                throw e;
            }
        }
    }

    public ObjectIdType addClassification(final ObjectIdType repositoryId, final PrincipalIdType principalId,
            final ObjectIdType initClass, final ObjectIdType targetClass, final VarargsType varargsType,
            final Map<String, Object> paramMap) throws AcarisException, Exception {
        printInfo(SERVICE_NAME, "addClassification");

        ObjectIdType result = null;

        try {
            initService();
            setServiceIdentifier((BindingProvider) multifilingServicePort, paramMap);
            result = multifilingServicePort.aggiungiClassificazione(repositoryId, principalId, initClass, targetClass
                    , varargsType);
        } catch (AcarisException e) {
            if (CLASSIFICATION_ERROR_SER_E_167.equals(e.getMessage())) {
                throw e;
            }
        } catch (Exception e) {
            printError(SERVICE_NAME, "addClassification", e.getMessage());
            throw e;
        }
        return result;
    }
}
