(function() {
 
 
    // Public API
    function Client() {
    	
    	this.scan = function(options, callback) {
    		this.remoteCall("scan",options, function (response) {
    			callback(response);
    		});
    	};
    	
    }
    
    Client.prototype = new Hybrid.ClientStub('scan');
    
    return new Client();
})()