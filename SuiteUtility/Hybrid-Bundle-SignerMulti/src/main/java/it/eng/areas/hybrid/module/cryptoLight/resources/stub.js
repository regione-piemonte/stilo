(function() {
 
 
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