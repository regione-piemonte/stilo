/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

var batchRestService = function($http) {
    /**
     * Token for the URL for JBeret REST API. Its value is obtained from either gulp command line args,
     * application config.json file, environment variable, or default value.
     * See gulpfile.js for more details.
     * The URL string includes hostname, port number, application context path, and REST API path.
     * Users need to modify this property for their environment.
     */
    var restApiUrl = '/* @echo __REST_URL__ */';

    var jobsUrl = restApiUrl + '/jobs';
    var jobInstancesUrl = restApiUrl + '/jobinstances';
    var jobExecutionsUrl = restApiUrl + '/jobexecutions';
    var jobSchedulesUrl = restApiUrl + '/schedules';

    var timezones = {
        entries: null
    };
    
    var jobScheduleFeatures = {
        entries: null
    };

    this.getJobs = function () {
        return $http.get(jobsUrl);
    };

    this.startJob = function (jobXmlName, jobParameters) {
        alert("--------------"+jobsUrl + '/' + encodeURIComponent(jobXmlName) + '/start');
    	return $http.post(jobsUrl + '/' + encodeURIComponent(jobXmlName) + '/start', jobParameters);
    };
    
    this.getJobScheduleFeatures = function () {
        if(jobScheduleFeatures.entries === null) {
            $http.get(jobSchedulesUrl + '/features').then(function (responseData) {
                jobScheduleFeatures.entries = responseData.data;
            }, function (responseData) {
            });
        }
        return jobScheduleFeatures;
    };

    this.scheduleJobExecution = function (jobName, jobExecutionId, jobParameters, jobScheduleConfig) {
        jobScheduleConfig.jobName = jobName;
        jobScheduleConfig.jobExecutionId = jobExecutionId;
        jobScheduleConfig.jobParameters = jobParameters;

        // angular-ui timepicker shows the current time when the model is actually null or cleared.
        // so when user sees the default, current time in timepicker, the model may be null.
        // so here we set the time to current time when the model is null, as a workaround.
        if(!jobScheduleConfig.initialDelay && jobScheduleConfig.date) {
            if(!jobScheduleConfig.time) {
                jobScheduleConfig.time = new Date();
            }
            jobScheduleConfig.date.setHours(jobScheduleConfig.time.getHours(), jobScheduleConfig.time.getMinutes());
            jobScheduleConfig.initialDelay = Math.ceil((jobScheduleConfig.date - new Date()) / (1000 * 60));
        }

        if(jobScheduleConfig.scheduleExpression) {
            if(jobScheduleConfig.scheduleExpression.start && jobScheduleConfig.scheduleExpression.start.date) {
                var startDateTime = jobScheduleConfig.scheduleExpression.start.date;
                if(!jobScheduleConfig.scheduleExpression.start.time) {
                    jobScheduleConfig.scheduleExpression.start.time = new Date();
                }
                startDateTime.setHours(jobScheduleConfig.scheduleExpression.start.time.getHours(),
                    jobScheduleConfig.scheduleExpression.start.time.getMinutes());
                jobScheduleConfig.scheduleExpression.start = startDateTime;
            }
            if(jobScheduleConfig.scheduleExpression.end && jobScheduleConfig.scheduleExpression.end.date) {
                var endDateTime = jobScheduleConfig.scheduleExpression.end.date;
                if(!jobScheduleConfig.scheduleExpression.end.time) {
                    jobScheduleConfig.scheduleExpression.end.time = new Date();
                }
                endDateTime.setHours(jobScheduleConfig.scheduleExpression.end.time.getHours(),
                    jobScheduleConfig.scheduleExpression.end.time.getMinutes());
                jobScheduleConfig.scheduleExpression.end = endDateTime;
            }
        }

        delete jobScheduleConfig.date;
        delete jobScheduleConfig.time;
        
        if(jobExecutionId > 0) {
            return $http.post(this.urlForJobExecution(jobExecutionId) + '/schedule', jobScheduleConfig);
        }
        return $http.post(jobsUrl + '/' + encodeURIComponent(jobName) + '/schedule', jobScheduleConfig);
    };

    this.getJobInstances = function (jobName, start, count) {
        var url = jobInstancesUrl;
        var sep = '?';

        if (jobName) {
            url += sep + 'jobName=' + encodeURIComponent(jobName);
            sep = '&';
        }
        if(start) {
            url += sep + 'start=' + start;
            sep = '&';
        }
        if(count) {
            url += sep + 'count=' + count;
        }
        return $http.get(url);
    };

    this.getJobExecutions = function (count, running, jobName, jobInstanceId, jobExecutionId1) {
        var url = jobExecutionsUrl;
        if (running && jobName) {
            url += '/running?jobName=' + encodeURIComponent(jobName);
            return $http.get(url);
        }
        if (jobExecutionId1) {
            url += '?jobExecutionId1=' + jobExecutionId1 + '&jobInstanceId=' + (jobInstanceId ? jobInstanceId : 0);
            if(count && count > 0) {
                url += '&count=' + count;
            }
        } else if (count && count > 0) {
            url += '?count=' + count;
        }
        return $http.get(url);
    };

    this.getJobSchedules = function () {
        return $http.get(jobSchedulesUrl);
    };
    
    this.getTimezoneIds = function () {
        if(timezones.entries === null) {
            $http.get(jobSchedulesUrl + '/timezones').then(function (responseData) {
                    timezones.entries = responseData.data;
                }, function (responseData) {
            });
        }
        return timezones;
    };
    
    this.cancelJobSchedule = function (schedulesId) {
        return $http.post(jobSchedulesUrl + '/' + encodeURIComponent(schedulesId) + '/cancel');  
    };

    this.getJobExecution = function (jobExecutionId) {
        return $http.get(this.urlForJobExecution(jobExecutionId));
    };

    this.stopJobExecution = function (jobExecutionId) {
        return $http.post(this.urlForJobExecution(jobExecutionId) + '/stop', null);
    };

    this.restartJobExecution = function (jobExecutionId, jobParameters) {
        return $http.post(this.urlForJobExecution(jobExecutionId) + '/restart', jobParameters);
    };

    this.abandonJobExecution = function (jobExecutionId) {
        return $http.post(this.urlForJobExecution(jobExecutionId) + '/abandon', null);
    };

    this.getStepExecutions = function (jobExecutionUrl) {
        return $http.get(jobExecutionUrl + '/stepexecutions');
    };

    this.getStepExecution = function (jobExecutionId, stepExecutionId) {
        return $http.get(this.urlForJobExecution(jobExecutionId) + '/stepexecutions/' + stepExecutionId);
    };

    this.urlForJobExecution = function (jobExecutionId) {
        return jobExecutionsUrl + '/' + jobExecutionId;
    };
};

//batchRestService.$inject = ['$http'];

module.exports = batchRestService;