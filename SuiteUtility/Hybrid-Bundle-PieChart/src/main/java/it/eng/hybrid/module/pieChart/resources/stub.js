(function() {
 
 
    // Public API
    function Client() {
    	
    	this.pieChart = function(options, callback) {
    		this.remoteCall("pieChart",options, function (response) {
    			callback(response);
    		});
    	};
    	
    }
    
    Client.prototype = new Hybrid.ClientStub('pieChart');
    
    return new Client();
})()