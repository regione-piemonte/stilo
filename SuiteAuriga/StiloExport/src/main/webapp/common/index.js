/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

var angular = require('angular');
var utils = require('./utils');

angular.module('jberetUI.common', [])
    .service('batchRestService', ['$http', require('./batchRestService')])
    .service('modalService', ['$uibModal', require('./modalService')])
    .service('localRecentJobsService', require('./localRecentJobsService'))
    .directive('batchStatus', function () {
        return {
            restrict: 'A',
            scope: {
                batchStatus: '@'
            },
            templateUrl: 'template/directive/batch-status.html'
        };
    });

