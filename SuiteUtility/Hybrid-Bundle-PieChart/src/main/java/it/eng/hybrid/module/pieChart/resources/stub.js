/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
 
 
    // Public API
    function Client() {
    	
    	this.pieChart = function(options, callback) {
    		this.remoteCall("pieChart",options, function (response) {
    			callback(response);
    		});
    	};
    	
    }
    
    Client.prototype = new Hybrid.ClientStub('pieChart');
    
    return new Client();
})()