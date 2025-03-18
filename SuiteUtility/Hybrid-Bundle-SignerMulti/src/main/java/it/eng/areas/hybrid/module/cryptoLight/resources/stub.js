/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
 
 
    // Public API
    function Client() {
    	
    	this.sign = function(options, callback) {
    		this.remoteCall("sign",options, function (response) {
    			callback(response);
    		});
    	};
    	
    }
    
    Client.prototype = new Hybrid.ClientStub('cryptoLight');
    
    return new Client();
})()