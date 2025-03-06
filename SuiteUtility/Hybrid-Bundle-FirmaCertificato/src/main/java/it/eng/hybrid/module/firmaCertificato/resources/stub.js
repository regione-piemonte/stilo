(function() {
 
 
    // Public API
    function Client() {
    	
    	this.firmaCertificato = function(options, callback) {
    		this.remoteCall("firmaCertificato",options, function (response) {
    			callback(response);
    		});
    	};
    	
    }
    
    Client.prototype = new Hybrid.ClientStub('firmaCertificato');
    
    return new Client();
})()