(function() {
 
 
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