(function() {
 
 
    // Public API
    function Client() {
    	
    	this.printerScanner = function(options, callback) {
    		this.remoteCall("printerScanner",options, function (response) {
    			callback(response);
    		});
    	};
    	
    }
    
    Client.prototype = new Hybrid.ClientStub('printerScanner');
    
    return new Client();
})()