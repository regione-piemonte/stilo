/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
 
 
    // Public API
    function Client() {
    	
    	this.jpedal = function(options, callback) {
    		this.remoteCall("jpedal",options, function (response) {
    			callback(response);
    		});
    	};
    	
    }
    
    Client.prototype = new Hybrid.ClientStub('jpedal');
    
    return new Client();
})()