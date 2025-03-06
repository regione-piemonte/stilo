(function() {
 
 
    // Public API
    function Client() {
    	
    	this.jpedal = function(options, callback) {
    		this.remoteCall("jpedal",options, function (response) {
    			callback(response);
    		});
    	};
    	
    }
    
    Client.prototype = new Hybrid.ClientStub('jpedal');
    
    return new Client();
})()