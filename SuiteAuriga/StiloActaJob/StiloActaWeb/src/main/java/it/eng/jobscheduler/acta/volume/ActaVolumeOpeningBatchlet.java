/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.logic.OperationResult;
import it.eng.stilo.logic.service.ActaIntegrationEJB;
import it.eng.stilo.model.core.AdminOrganization;

import javax.ejb.EJB;
import javax.inject.Named;

@Named
public class ActaVolumeOpeningBatchlet extends ActaVolumeBatchlet {

    @EJB
    private ActaIntegrationEJB actaIntegrationEJB;

    @Override
    protected OperationResult doProcess(final AdminOrganization adminOrganization) {
        // Create Volume for given organization
        logger.info("VolumeCreating[" + adminOrganization.getId().getDocumentTypeId() + "]" +
                "[" + adminOrganization.getId().getAooCode() + "]");
        OperationResult operationResult = actaIntegrationEJB.createFolder(adminOrganization, null);
        logger.info(String.format("[VolumeCreating][OpResult=%s][Key=%s][VolumeId=%s]",
                operationResult.getResultType().name(), operationResult.getKey(),
                getId(operationResult.getAttributes(), OperationResult.AttributeType.OBJECT_ID_DOCUMENT)));

        return operationResult;
    }

}
