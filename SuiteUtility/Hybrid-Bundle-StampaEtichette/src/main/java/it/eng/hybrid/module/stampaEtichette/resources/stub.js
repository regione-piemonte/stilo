/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
 
 
    // Public API
    function Client() {
    	
    	this.stampaEtichette = function(options, callback) {
    		this.remoteCall("stampaEtichette",options, function (response) {
    			callback(response);
    		});
    	};
    	
    }
    
    Client.prototype = new Hybrid.ClientStub('stampaEtichette');
    
    return new Client();
})()