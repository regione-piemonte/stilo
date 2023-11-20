/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.web.dto.JobMetaDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.batch.operations.JobOperator;
import javax.batch.operations.NoSuchJobExecutionException;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.JobExecution;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.Properties;
import java.util.Set;

@Path("/job")
public class JobResource {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Returns a set of all job names known to the batch runtime. It's not a simple select on job repository, in fact
     * this set just contains the names of jobs that have run at least once after server restart.
     *
     * @return Set of job names
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<String> getAll() {
        logger.info(getClass().getSimpleName() + "-getAll");
        final JobOperator jobOperator = BatchRuntime.getJobOperator();
        final Set<String> jobs = jobOperator.getJobNames();
        logger.info(getClass().getSimpleName() + "-getAll{#" + jobs.size() + "}");
        return jobs;
    }

    /**
     * Creates a new job instance and starts the first execution of that instance.
     *
     * @param jobName The name of the job to start
     * @return The execution identifier
     */
    @GET
    @Path("/{job}")
    public Long start(@PathParam("job") String jobName) {
        logger.info(getClass().getSimpleName() + "-start{" + jobName + "}");
        final JobOperator jobOperator = BatchRuntime.getJobOperator();
        final Long execId = jobOperator.start(jobName, new Properties());
        logger.info(getClass().getSimpleName() + "-start{" + jobName + ":" + execId + "}");
        return execId;
    }

    /**
     * Returns the status of job execution related to the given execution identifier.
     *
     * @param executionId The execution identifier
     * @return The execution status
     */
    @GET
    @Path("/status/{execId}")
    public String getStatus(@PathParam("execId") Long executionId) {
        logger.info(getClass().getSimpleName() + "-getStatus{" + executionId + "}");
        final JobOperator jobOperator = BatchRuntime.getJobOperator();
        try {
            final BatchStatus batchStatus = jobOperator.getJobExecution(executionId).getBatchStatus();
            logger.info(getClass().getSimpleName() + "-getStatus{" + executionId + ":" + batchStatus.name() + "}");
            return batchStatus.name();
        } catch (NoSuchJobExecutionException e) {
            logger.warn("", e);
        }
        return null;
    }

    /**
     * Returns a {@link JobMetaDataDTO} with data related to the given execution identifier.
     *
     * @param executionId The execution identifier
     * @return Execution meta data
     */
    @GET
    @Path("/metadata/{execId}")
    @Produces(MediaType.APPLICATION_JSON)
    public JobMetaDataDTO getMetaData(@PathParam("execId") Long executionId) {
        logger.info(getClass().getSimpleName() + "-getMetaData{" + executionId + "}");
        final JobMetaDataDTO jobMetaData = new JobMetaDataDTO();
        final JobOperator jobOperator = BatchRuntime.getJobOperator();
        try {
            final JobExecution jobExecution = jobOperator.getJobExecution(executionId);
            final BatchStatus batchStatus = jobExecution.getBatchStatus();
            final Date endTime = jobExecution.getEndTime();
            logger.info(getClass().getSimpleName() + "-getMetaData{" + executionId + ":" + batchStatus.name() + ";" + endTime + "}");
            jobMetaData.setStatus(batchStatus.name());
            jobMetaData.setEndTime(endTime);
        } catch (NoSuchJobExecutionException e) {
            logger.warn("", e);
        }

        return jobMetaData;
    }

}
