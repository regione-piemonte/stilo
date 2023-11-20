/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.Dependent;
import javax.xml.ws.BindingProvider;

import it.doqui.acta.acaris.archive.EnumFolderObjectType;
import it.doqui.acta.acaris.common.ChangeTokenType;
import it.doqui.acta.acaris.common.NavigationConditionInfoType;
import it.doqui.acta.acaris.common.ObjectIdType;
import it.doqui.acta.acaris.common.PagingResponseType;
import it.doqui.acta.acaris.common.PrincipalIdType;
import it.doqui.acta.acaris.common.PropertiesType;
import it.doqui.acta.acaris.common.PropertyFilterType;
import it.doqui.acta.acaris.common.PropertyType;
import it.doqui.acta.acaris.common.QueryConditionType;
import it.doqui.acta.acaris.common.QueryNameType;
import it.doqui.acta.acaris.common.QueryableObjectType;
import it.doqui.acta.acaris.common.SimpleResponseType;
import it.doqui.acta.acaris.common.ValueType;
import it.doqui.acta.acaris.objectservice.AcarisException;
import it.doqui.acta.acaris.objectservice.ObjectService;
import it.doqui.acta.acaris.objectservice.ObjectServicePort;

@Dependent
public class ObjectServiceClient extends AbstractServiceClient implements QueryableServiceClient {

    private static final String SERVICE_NAME = "service.object";
    private ObjectServicePort objectServicePort;

    public ObjectServiceClient() {
        super(SERVICE_NAME);
    }

    @Override
    protected void initService() {
        printInfo(SERVICE_NAME, "init");
        final URL url = getServiceURL(SERVICE_NAME);
        if (objectServicePort == null) {
            try {
                final ObjectService objectService = new ObjectService(url);
                objectServicePort = objectService.getObjectServicePort();
                setHandler(((BindingProvider) objectServicePort).getBinding());
                printInfo(SERVICE_NAME, "initialized");
            } catch (Exception e) {
                printError(SERVICE_NAME, "init", e);
                throw e;
            }
        }
    }

    public ObjectIdType createFolder(final ObjectIdType repositoryId, final EnumFolderObjectType typeId,
            final PrincipalIdType principalId, final PropertiesType properties,
            final ObjectIdType folderId, final Map<String, Object> paramMap) throws Exception {
        printInfo(SERVICE_NAME, "create-folder");
        ObjectIdType objectId = null;
        try {
            initService();
            setServiceIdentifier((BindingProvider) objectServicePort, paramMap);
            objectId = objectServicePort.createFolder(repositoryId, typeId, principalId, properties, folderId);
        } catch (Exception e) {
            printError(SERVICE_NAME, "create-folder", e.getMessage());
            throw e;
        }
        return objectId;
    }

    public SimpleResponseType closeFolder(final ObjectIdType repositoryId, final PrincipalIdType principalId,
            final ObjectIdType objectId, final Map<String, Object> paramMap) throws Exception {
        printInfo(SERVICE_NAME, "close-folder");
        try {
            initService();
            setServiceIdentifier((BindingProvider) objectServicePort, paramMap);
            return objectServicePort.closeFolder(repositoryId, objectId, principalId);
        } catch (Exception e) {
            printError(SERVICE_NAME, "close-folder", e.getMessage());
           // return null;
            throw e;
        }
    }

    @Override
    public PagingResponseType query(final ObjectIdType repositoryId, final PrincipalIdType principalId,
            final QueryableObjectType queryableObject,
            final PropertyFilterType propertyFilter,
            final List<QueryConditionType> queryConditions,
            final NavigationConditionInfoType navigationConditionInfo,
            final Map<String, Object> paramMap) throws Exception{
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
            setServiceIdentifier((BindingProvider) objectServicePort, paramMap);
            return objectServicePort.query(repositoryId, principalId, queryableObject, propertyFilter,
                    queryConditions, navigationConditionInfo, maxItems, skipCount);
        } catch (Exception e) {
            printError(SERVICE_NAME, "query", e.getMessage());
            throw e;
        }
    }

    public SimpleResponseType updateProperties(final ObjectIdType repositoryId, final ObjectIdType objectId,
            final PrincipalIdType principalId, final Map<QueryNameType, ValueType> propertiesMap, final Map<String,
            Object> paramMap) throws Exception {
        printInfo(SERVICE_NAME, "updateProperties");
        try {
            initService();
            setServiceIdentifier((BindingProvider) objectServicePort, paramMap);
            final List<PropertyType> properties = new ArrayList<>();
            propertiesMap.forEach((k, v) -> {
                final PropertyType property = new PropertyType();
                property.setQueryName(k);
                property.setValue(v);
                properties.add(property);
            });
            return objectServicePort.updateProperties(repositoryId, objectId, principalId, null, properties);
        } catch (AcarisException e) {
            printError(SERVICE_NAME, "updateProperties", e.getMessage());
            //return null;
            throw e;
        }
    }
    
    public SimpleResponseType updateProperties(final ObjectIdType repositoryId, final ObjectIdType objectId,
            final PrincipalIdType principalId, final Map<QueryNameType, ValueType> propertiesMap, final ChangeTokenType changeToken,
            final Map<String,Object> paramMap) throws Exception {
        printInfo(SERVICE_NAME, "updateProperties");
        try {
            initService();
            setServiceIdentifier((BindingProvider) objectServicePort, paramMap);
            final List<PropertyType> properties = new ArrayList<>();
            propertiesMap.forEach((k, v) -> {
                final PropertyType property = new PropertyType();
                property.setQueryName(k);
                property.setValue(v);
                properties.add(property);
            });
            return objectServicePort.updateProperties(repositoryId, objectId, principalId, changeToken, properties);
        } catch (AcarisException e) {
            printError(SERVICE_NAME, "updateProperties", e.getMessage());
            //return null;
            throw e;
        }
    }

}
