/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.doqui.acta.acaris.common.*;

import java.util.List;
import java.util.Map;

public interface QueryableServiceClient {

    PagingResponseType query(final ObjectIdType repositoryId, final PrincipalIdType principalId,
            final QueryableObjectType queryableObject, final PropertyFilterType propertyFilter,
            final List<QueryConditionType> queryConditions, final NavigationConditionInfoType navigationConditionInfo
            , final Map<String, Object> paramMap) throws Exception;

    PagingResponseType query(final ObjectIdType repositoryId, final PrincipalIdType principalId,
            final QueryableObjectType queryableObject, final PropertyFilterType propertyFilter,
            final List<QueryConditionType> queryConditions, final NavigationConditionInfoType navigationConditionInfo
            , final Integer maxItems, final Integer skipCount, final Map<String, Object> paramMap) throws Exception;

}
