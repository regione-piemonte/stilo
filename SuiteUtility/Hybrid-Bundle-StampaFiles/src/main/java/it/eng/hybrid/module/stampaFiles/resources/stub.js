(function() {
 
 
    // Public API
    function Client() {
    	
    	this.stampaFiles = function(options, callback) {
    		this.remoteCall("stampaFiles",options, function (response) {
    			callback(response);
    		});
    	};
    	
    }
    
    Client.prototype = new Hybrid.ClientStub('stampaFiles');
    
    return new Client();
})()