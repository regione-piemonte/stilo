/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
 
 
    // Public API
    function Client() {
    	
    	this.portScanner = function(options, callback) {
    		this.remoteCall("portScanner",options, function (response) {
    			callback(response);
    		});
    	};
    	
    }
    
    Client.prototype = new Hybrid.ClientStub('portScanner');
    
    return new Client();
})()