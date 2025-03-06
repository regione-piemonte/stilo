(function() {
 
 
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