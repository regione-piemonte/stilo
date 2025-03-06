(function() {
 
 
    // Public API
    function Client() {
    	
    	this.siss = function(options, callback) {
    		this.remoteCall("siss",options, function (response) {
    			callback(response);
    		});
    	};
    	
    }
    
    Client.prototype = new Hybrid.ClientStub('siss');
    
    return new Client();
})()