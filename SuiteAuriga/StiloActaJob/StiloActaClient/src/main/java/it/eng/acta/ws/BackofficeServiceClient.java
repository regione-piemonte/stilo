/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.Dependent;
import javax.xml.ws.BindingProvider;

import it.doqui.acta.acaris.backoffice.ClientApplicationInfo;
import it.doqui.acta.acaris.backoffice.PrincipalExtResponseType;
import it.doqui.acta.acaris.backoffice.PrincipalResponseType;
import it.doqui.acta.acaris.backofficeservice.BackOfficeService;
import it.doqui.acta.acaris.backofficeservice.BackOfficeServicePort;
import it.doqui.acta.acaris.common.CodiceFiscaleType;
import it.doqui.acta.acaris.common.NavigationConditionInfoType;
import it.doqui.acta.acaris.common.ObjectIdType;
import it.doqui.acta.acaris.common.PagingResponseType;
import it.doqui.acta.acaris.common.PrincipalIdType;
import it.doqui.acta.acaris.common.PropertyFilterType;
import it.doqui.acta.acaris.common.QueryConditionType;
import it.doqui.acta.acaris.common.QueryableObjectType;

@Dependent
public class BackofficeServiceClient extends AbstractServiceClient implements QueryableServiceClient{

    private static final String SERVICE_NAME = "service.backoffice";
    private BackOfficeServicePort backOfficeServicePort;

    public BackofficeServiceClient() {
        super(SERVICE_NAME);
    }

    @Override
    protected void initService() {
        printInfo(SERVICE_NAME, "init");
        final URL url = getServiceURL(SERVICE_NAME);
        if (backOfficeServicePort == null) {
            try {
                final BackOfficeService backOfficeService = new BackOfficeService(url);
                backOfficeServicePort = backOfficeService.getBackOfficeServicePort();
                setHandler(((BindingProvider) backOfficeServicePort).getBinding());
                printInfo(SERVICE_NAME, "initialized");
            } catch (Exception e) {
                printError(SERVICE_NAME, "init", e);
                throw e;
            }
        }
    }

    public List<PrincipalResponseType> getPrincipals(final ObjectIdType repositoryId, final String fiscalCode,
            final Map<String, Object> paramMap) {
        printInfo(SERVICE_NAME, "get-principals");
        final CodiceFiscaleType codiceFiscale = new CodiceFiscaleType();
        codiceFiscale.setValue(fiscalCode);

        List<PrincipalResponseType> principals = Collections.emptyList();
        try {
            initService();
            setServiceIdentifier((BindingProvider) backOfficeServicePort, paramMap);
            principals = backOfficeServicePort.getPrincipal(repositoryId, codiceFiscale, null, null, null);
        } catch (Exception e) {
            printError(SERVICE_NAME, "get-principals", e.getMessage());
        }
        return principals;
    }

    public List<PrincipalExtResponseType> getPrincipalsExt(final ObjectIdType repositoryId, final String fiscalCode,
            final String appKey, final Map<String, Object> paramMap) throws Exception  {
        printInfo(SERVICE_NAME, "get-principalsExt");
        final CodiceFiscaleType codiceFiscale = new CodiceFiscaleType();
        codiceFiscale.setValue(fiscalCode);
        final ClientApplicationInfo clientApplicationInfo = new ClientApplicationInfo();
        clientApplicationInfo.setAppKey(appKey);

        List<PrincipalExtResponseType> principals = Collections.emptyList();
        try {
            initService();
            setServiceIdentifier((BindingProvider) backOfficeServicePort, paramMap);
            principals =
                    backOfficeServicePort.getPrincipalExt(repositoryId, codiceFiscale, null, null, null,
                            clientApplicationInfo);
        } catch (Exception e) {
            printError(SERVICE_NAME, "get-principalsExt", e.getMessage());
            throw e;
        }
        return principals;
    }
    
    @Override
    public PagingResponseType query(final ObjectIdType repositoryId, final PrincipalIdType principalId,
            final QueryableObjectType queryableObject,
            final PropertyFilterType propertyFilter,
            final List<QueryConditionType> queryConditions,
            final NavigationConditionInfoType navigationConditionInfo,
            final Map<String, Object> paramMap) throws Exception {
        return query(repositoryId, principalId, queryableObject, propertyFilter, queryConditions,
                navigationConditionInfo, null, null, paramMap);
    }

    @Override
    public PagingResponseType query(final ObjectIdType repositoryId, final PrincipalIdType principalId,
            final QueryableObjectType queryableObject,
            final PropertyFilterType propertyFilter,
            final List<QueryConditionType> queryConditions,
            final NavigationConditionInfoType navigationConditionInfo,
            final Integer maxItems, final Integer skipCount,
            final Map<String, Object> paramMap) throws Exception {
        printInfo(SERVICE_NAME, "query");
        try {
            initService();
            setServiceIdentifier((BindingProvider) backOfficeServicePort, paramMap);
            return backOfficeServicePort.query(repositoryId, principalId, queryableObject, propertyFilter,
                    queryConditions, navigationConditionInfo, maxItems, skipCount);
        } catch (Exception e) {
            printError(SERVICE_NAME, "query", e.getMessage());
            //return null;
            throw e;
        }
    }

}
