(function() {
 
 
    // Public API
    function Client() {
    	
    	this.selectCertificati = function(options, callback) {
    		this.remoteCall("selectCertificati",options, function (response) {
    			callback(response);
    		});
    	};
    	
    }
    
    Client.prototype = new Hybrid.ClientStub('selectCertificati');
    
    return new Client();
})()