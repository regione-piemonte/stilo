(function() {
 
 
    // Public API
    function Client() {
    	
    	this.wordOpener = function(options, callback) {
    		this.remoteCall("wordOpener",options, function (response) {
    			callback(response);
    		});
    	};
    	
    }
    
    Client.prototype = new Hybrid.ClientStub('wordOpener');
    
    return new Client();
})()