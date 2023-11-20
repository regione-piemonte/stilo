/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.Dependent;
import javax.xml.ws.BindingProvider;

import it.doqui.acta.acaris.common.IdSmistamentoType;
import it.doqui.acta.acaris.common.NavigationConditionInfoType;
import it.doqui.acta.acaris.common.ObjectIdType;
import it.doqui.acta.acaris.common.PagingResponseType;
import it.doqui.acta.acaris.common.PrincipalIdType;
import it.doqui.acta.acaris.common.PropertyFilterType;
import it.doqui.acta.acaris.common.QueryConditionType;
import it.doqui.acta.acaris.common.QueryableObjectType;
//import it.doqui.acta.acaris.common.*;
import it.doqui.acta.acaris.sms.DestinatarioType;
import it.doqui.acta.acaris.sms.InfoCreazioneType;
import it.doqui.acta.acaris.sms.MittenteType;
import it.doqui.acta.acaris.sms.OggettoSmistamentoType;
import it.doqui.acta.acaris.smsservice.SMSService;
import it.doqui.acta.acaris.smsservice.SMSServicePort;

@Dependent
public class SMSServiceClient extends AbstractServiceClient implements QueryableServiceClient{

    private static final String SERVICE_NAME = "service.sms";
    private SMSServicePort smsServicePort;

    public SMSServiceClient() {
        super(SERVICE_NAME);
    }

    @Override
    protected void initService() {
        printInfo(SERVICE_NAME, "init");
        final URL url = getServiceURL(SERVICE_NAME);
        if (smsServicePort == null) {
            try {
                final SMSService smsService = new SMSService(url);
                smsServicePort = smsService.getSMSServicePort();
                setHandler(((BindingProvider) smsServicePort).getBinding());
                printInfo(SERVICE_NAME, "initialized");
            } catch (Exception e) {
                printError(SERVICE_NAME, "init", e);
                throw e;
            }
        }
    }

    public IdSmistamentoType createSwitching(final ObjectIdType repositoryId, final PrincipalIdType principalId,
            final MittenteType senderId, final List<DestinatarioType> receiverList,
            final List<OggettoSmistamentoType> switchedList, final InfoCreazioneType infoId, final Map<String,
            Object> paramMap) throws Exception {
        printInfo(SERVICE_NAME, "create-switching");

        IdSmistamentoType switchId = null;
        try {
            initService();
            setServiceIdentifier((BindingProvider) smsServicePort, paramMap);
            switchId = smsServicePort.creaSmistamento(repositoryId, principalId, senderId, receiverList, switchedList
                    , infoId);
        } catch (Exception e) {
            printError(SERVICE_NAME, "create-switching", e.getMessage());
            throw e;
        }
        return switchId;
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
            setServiceIdentifier((BindingProvider) smsServicePort, paramMap);
            return smsServicePort.query(repositoryId, principalId, queryableObject, propertyFilter,
                    queryConditions, navigationConditionInfo, maxItems, skipCount);
        } catch (Exception e) {
            printError(SERVICE_NAME, "query", e.getMessage());
           // return null;
            throw e;
        }
    }

}
