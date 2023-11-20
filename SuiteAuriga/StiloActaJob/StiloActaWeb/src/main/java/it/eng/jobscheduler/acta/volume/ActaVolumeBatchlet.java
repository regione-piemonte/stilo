/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.logic.OperationResult;
import it.eng.stilo.logic.service.GenericDataAccessEJB;
import it.eng.stilo.model.core.AdminOrganization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.batch.api.AbstractBatchlet;
import javax.ejb.EJB;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public abstract class ActaVolumeBatchlet extends AbstractBatchlet {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @EJB
    private GenericDataAccessEJB<AdminOrganization> adminOrganizationEJB;

    protected abstract OperationResult doProcess(final AdminOrganization adminOrganization);

    @Override
    public String process() {
        logger.debug(getClass().getSimpleName() + "-process");

        OperationResult operationResult;

        // Get list of all organizations
        final List<AdminOrganization> adminOrganizations = adminOrganizationEJB.loadList(AdminOrganization.class);

        // Result types container
        final Set<OperationResult.ResultType> resultTypes = new HashSet<>();

        // Close Volume for each organization
        for (final AdminOrganization adminOrganization : adminOrganizations) {
            operationResult = doProcess(adminOrganization);
            resultTypes.add(operationResult.getResultType());
        }

        return /* if at least one operation fails, return type must be FAILED */
                resultTypes.contains(OperationResult.ResultType.FAILED) ? OperationResult.ResultType.FAILED.name() :
                        /* otherwise if at least one operation complete successfully, return type must be SUCCESS */
                        resultTypes.contains(OperationResult.ResultType.SUCCESS) ?
                                OperationResult.ResultType.SUCCESS.name() :
                                /* otherwise it means that all operation has already done, return AUTO_SUCCESS */
                                OperationResult.ResultType.AUTO_SUCCESS.name();
    }

    protected String getId(final Map<OperationResult.AttributeType, Object> attributesMap,
            OperationResult.AttributeType attributeType) {
        final Object value = attributesMap.get(attributeType);
        return value != null ? String.valueOf(value) : null;
    }

}
