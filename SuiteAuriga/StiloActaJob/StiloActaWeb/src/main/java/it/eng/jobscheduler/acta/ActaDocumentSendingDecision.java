/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.logic.OperationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.batch.api.Decider;
import javax.batch.runtime.StepExecution;
import javax.inject.Named;
import java.util.Arrays;
import java.util.Optional;

@Named
public class ActaDocumentSendingDecision implements Decider {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String decide(StepExecution[] stepExecutions) {
        logger.debug(getClass().getSimpleName() + "-decide");

        final Optional<String> exitStatus =
                Arrays.stream(stepExecutions).filter(s -> s.getStepName().equals("actaVolume")).map(StepExecution::getExitStatus).findFirst();

        return exitStatus.orElse(OperationResult.ResultType.FAILED.name());
    }

}
