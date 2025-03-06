var Hybrid = function() {
	
	// Internet Explorer 6-11
	var isIE = /*@cc_on!@*/false || !!document.documentMode;
	var chiamataSincrona = true;
	if (isIE){
		chiamataSincrona = false;
	}

	//functione per comunicazione ajax cross-browser
	var ajax = function(url, successCallback, data, options) {
		var i, XMLHttpRequestObject = false, XMLHttpRequestObjects = [
				function() {
					return new window.XMLHttpRequest(); // IE7+, Firefox,
														// Chrome, Opera,
														// Safari
				}, function() {
					return new window.ActiveXObject('Msxml2.XMLHTTP.6.0');
				}, function() {
					return new window.ActiveXObject('Msxml2.XMLHTTP.3.0');
				}, function() {
					return new window.ActiveXObject('Msxml2.XMLHTTP');
				} ];

		for (i = 0; i < XMLHttpRequestObjects.length; i += 1) {
			try {
				XMLHttpRequestObject = XMLHttpRequestObjects[i]();
			} catch (ignore) {
			}

			if (XMLHttpRequestObject !== false) {
				break;
			}
		}
		
		var method = (data) ? 'POST' : 'GET';
		
		XMLHttpRequestObject.open(method, url, chiamataSincrona);
		if (options && options.timeout) {
			XMLHttpRequestObject.timeout = options.timeout;
		}


		XMLHttpRequestObject.onreadystatechange = function() {
			try{
				if (XMLHttpRequestObject.readyState === 4) {
					if (XMLHttpRequestObject.status === 200) {
						successCallback(XMLHttpRequestObject.responseText, XMLHttpRequestObject.status, XMLHttpRequestObject.statusText);
					} else {
						if (options && options.errorCallback) {
							options.errorCallback(XMLHttpRequestObject.responseText, XMLHttpRequestObject.status, XMLHttpRequestObject.statusText)	
						}
						
					}
				}
			}catch (e) {
				options.errorCallback(XMLHttpRequestObject.responseText, 400, XMLHttpRequestObject.statusText)
			}
		};
		
		if (method == 'GET') {
			XMLHttpRequestObject.send();
		} else {
			XMLHttpRequestObject.send(data);
		}

	};
	
	
/*	
	var jsonp = function(url, successCallback, data, options) {
		var uniqueCallback = 'jsonpCallback' + Math.random();
		window[uniqueCallback] = function(result) {
			successCallback(result);
		};
		var script = document.createElement('script');
		script.src = url + '?callback='+uniqueCallback;
		document.getElementsByTagName('head')[0].appendChild(script);		
	}
	
	if (false) {
		ajax = jsonp;
	}
*/	

	
	
	//Stub base da usare come prototype con funzioni per la comunicazione con il server Hybrid
	ClientStub = function(module, useWebSockets) {
		this.module = module;
		
		this.hybridUrl ='${client.base}';
		this.hybridModuleUrl = this.hybridUrl + '/' + module;
		
		this.hybridSocketUrl = "${client.ws}";
		this.hybridModuleSocketUrl = "${client.ws}" + '/' + module;
	
		this.hello = function(name) {
			return("Hello, "+name);
		}
		
		//Gestione WebSocket
		this.wsOpen = function() {
			this.socket = new WebSocket(this.hybridModuleSocketUrl);
			
			var _this = this;
			
			this.socket.onopen = function() {
				_this.wsOnOpen();
			}
			
			this.socket.onmessage = function(event) {
				_this.wsOnMessage(event.data);
			}
			
			this.socket.onclose = function() {
				_this.wsOnClose();
			}
			
			this.socket.onerror = function() {
				_this.wsOnError();
			}
			
		}
		
		this.wsClose = function() {
			this.socket.close();
		}
		
		
		this.wsOnOpen = function() {
			//console.log('wsOnOpen');
		}
		
		this.wsOnMessage = function(event) {
			//console.log('wsOnMessage '+event.data);
		}
		
		this.wsOnClose = function() {
			//console.log('wsOnClose');
		}
		
		this.wsOnError = function() {
			//console.log('wsOnError');
		}
		
		//Gestione http
		this.remoteCall = function(method, parameters, onSuccess, options) {
			var _this = this;
			var data;
			if (parameters) {
				// TODEL: questo controllo crea scasini con exlorer
				// if (!Array.isArray(parameters)) {
					// parameters = [parameters];
				// }
				 if (!(parameters.constructor === Array)) {
					 parameters = [parameters];
				 }
				
				data = JSON.stringify(parameters);
			}
			ajax(this.hybridModuleUrl+"/call/"+method, function(result) {
				var jResult = JSON.parse(result);
				if (onSuccess) {
				  onSuccess.call(_this, jResult);
				}
			}, data);
		}
		
		
		
		
	}
	

	var WindowLoader = function() {

		var queue = [];
		var loaded = false;

		if(window.jQuery || window.$) {
			$(document).ready(function() {
				loaded = true;
				for (var i = 0; i < queue.length; i++) {
					queue[i]();
				}
			});
		} else {
			window.onload = function() {
				loaded = true;
				for (var i = 0; i < queue.length; i++) {
					queue[i]();
				}
			}
		}

		return {
			onWindowLoaded : function(callback) {
				if (!loaded)
					queue.push(callback);
				else
					callback();
			}
		};
	}();
	
	

	var Modal = (function() {
		"use strict";
		/* global document: false */
		/* global window: false */

		// create object method
		var method = {}, settings = {},

		modalOverlay = document.createElement('div'), modalContainer = document
				.createElement('div'), modalHeader = document
				.createElement('div'), modalContent = document
				.createElement('div'), modalClose = document
				.createElement('div'),

		centerModal,

		closeModalEvent,

		defaultSettings = {
			width : 'auto',
			height : 'auto',
			lock : false,
			hideClose : false,
			draggable : false,
			closeAfter : 0,
			openCallback : false,
			closeCallback : false,
			hideOverlay : false
		};
		
		// Open the modal
		method.open = function(parameters) {
			settings.width = parameters.width || defaultSettings.width;
			settings.height = parameters.height || defaultSettings.height;
			settings.lock = parameters.lock || defaultSettings.lock;
			settings.hideClose = parameters.hideClose
					|| defaultSettings.hideClose;
			settings.draggable = parameters.draggable
					|| defaultSettings.draggable;
			settings.closeAfter = parameters.closeAfter
					|| defaultSettings.closeAfter;
			settings.closeCallback = parameters.closeCallback
					|| defaultSettings.closeCallback;
			settings.openCallback = parameters.openCallback
					|| defaultSettings.openCallback;
			settings.hideOverlay = parameters.hideOverlay
					|| defaultSettings.hideOverlay;

			centerModal = function() {
				method.center({});
			};

			if (parameters.content && !parameters.ajaxContent) {
				modalContent.innerHTML = parameters.content;
			} else if (parameters.ajaxContent && !parameters.content) {
				modalContainer.className = 'modal-loading';
				method.ajax(parameters.ajaxContent, function insertAjaxResult(
						ajaxResult) {
					modalContent.innerHTML = ajaxResult;
				});
			} else {
				modalContent.innerHTML = '';
			}

			modalContainer.style.width = settings.width;
			modalContainer.style.height = settings.height;

			method.center({});

			if (settings.lock || settings.hideClose) {
				modalClose.style.visibility = 'hidden';
			}
			if (!settings.hideOverlay) {
				modalOverlay.style.visibility = 'visible';
			}
			modalContainer.style.visibility = 'visible';

			document.onkeypress = function(e) {
				if (e.keyCode === 27 && settings.lock !== true) {
					method.close();
				}
			};

			modalClose.onclick = function() {
				if (!settings.hideClose) {
					method.close();
				} else {
					return false;
				}
			};
			modalOverlay.onclick = function() {
				if (!settings.lock) {
					method.close();
				} else {
					return false;
				}
			};

			if (window.addEventListener) {
				window.addEventListener('resize', centerModal, false);
			} else if (window.attachEvent) {
				window.attachEvent('onresize', centerModal);
			}

			if (settings.draggable) {
				modalHeader.style.cursor = 'move';
				modalHeader.onmousedown = function(e) {
					method.drag(e);
					return false;
				};
			} else {
				modalHeader.onmousedown = function() {
					return false;
				};
			}
			if (settings.closeAfter > 0) {
				closeModalEvent = window.setTimeout(function() {
					method.close();
				}, settings.closeAfter * 1000);
			}
			if (settings.openCallback) {
				settings.openCallback();
			}
		};

		// Drag the modal
		method.drag = function(e) {
			var xPosition = (window.event !== undefined) ? window.event.clientX
					: e.clientX, yPosition = (window.event !== undefined) ? window.event.clientY
					: e.clientY, differenceX = xPosition
					- modalContainer.offsetLeft, differenceY = yPosition
					- modalContainer.offsetTop;

			document.onmousemove = function(e) {
				xPosition = (window.event !== undefined) ? window.event.clientX
						: e.clientX;
				yPosition = (window.event !== undefined) ? window.event.clientY
						: e.clientY;

				modalContainer.style.left = ((xPosition - differenceX) > 0) ? (xPosition - differenceX)
						+ 'px'
						: 0;
				modalContainer.style.top = ((yPosition - differenceY) > 0) ? (yPosition - differenceY)
						+ 'px'
						: 0;

				document.onmouseup = function() {
					window.document.onmousemove = null;
				};
			};
		};

		// Perform XMLHTTPRequest
		method.ajax = function(url, successCallback) {
			var i, XMLHttpRequestObject = false, XMLHttpRequestObjects = [
					function() {
						return new window.XMLHttpRequest(); // IE7+, Firefox,
															// Chrome, Opera,
															// Safari
					}, function() {
						return new window.ActiveXObject('Msxml2.XMLHTTP.6.0');
					}, function() {
						return new window.ActiveXObject('Msxml2.XMLHTTP.3.0');
					}, function() {
						return new window.ActiveXObject('Msxml2.XMLHTTP');
					} ];

			for (i = 0; i < XMLHttpRequestObjects.length; i += 1) {
				try {
					XMLHttpRequestObject = XMLHttpRequestObjects[i]();
				} catch (ignore) {
				}

				if (XMLHttpRequestObject !== false) {
					break;
				}
			}

			XMLHttpRequestObject.open('GET', url, chiamataSincrona);

			XMLHttpRequestObject.onreadystatechange = function() {
				if (XMLHttpRequestObject.readyState === 4) {
					if (XMLHttpRequestObject.status === 200) {
						successCallback(XMLHttpRequestObject.responseText);
						modalContainer.removeAttribute('class');
					} else {
						successCallback(XMLHttpRequestObject.responseText);
						modalContainer.removeAttribute('class');
					}
				}
			};

			XMLHttpRequestObject.send(null);
		};

		// Close the modal
		method.close = function() {
			modalContent.innerHTML = '';
			modalOverlay.setAttribute('style', '');
			modalOverlay.style.cssText = '';
			modalOverlay.style.visibility = 'hidden';
			modalContainer.setAttribute('style', '');
			modalContainer.style.cssText = '';
			modalContainer.style.visibility = 'hidden';
			modalHeader.style.cursor = 'default';
			modalClose.setAttribute('style', '');
			modalClose.style.cssText = '';

			if (closeModalEvent) {
				window.clearTimeout(closeModalEvent);
			}

			if (settings.closeCallback) {
				settings.closeCallback();
			}

			if (window.removeEventListener) {
				window.removeEventListener('resize', centerModal, false);
			} else if (window.detachEvent) {
				window.detachEvent('onresize', centerModal);
			}
		};

		// Center the modal in the viewport
		method.center = function(parameters) {
			var documentHeight = Math.max(document.body.scrollHeight,
					document.documentElement.scrollHeight),

			modalWidth = Math.max(modalContainer.clientWidth,
					modalContainer.offsetWidth), modalHeight = Math.max(
					modalContainer.clientHeight, modalContainer.offsetHeight),

			browserWidth = 0, browserHeight = 0,

			amountScrolledX = 0, amountScrolledY = 0;

			if (typeof (window.innerWidth) === 'number') {
				browserWidth = window.innerWidth;
				browserHeight = window.innerHeight;
			} else if (document.documentElement
					&& document.documentElement.clientWidth) {
				browserWidth = document.documentElement.clientWidth;
				browserHeight = document.documentElement.clientHeight;
			}

			// A volte in IE non leggo correttamente le dimensioni
			if ((browserWidth === 0) || (browserHeight === 0)){
				browserWidth = document.body.clientWidth;
				browserHeight = document.body.clientHeight;
			}
			
			if (typeof (window.pageYOffset) === 'number') {
				amountScrolledY = window.pageYOffset;
				amountScrolledX = window.pageXOffset;
			} else if (document.body && document.body.scrollLeft) {
				amountScrolledY = document.body.scrollTop;
				amountScrolledX = document.body.scrollLeft;
			} else if (document.documentElement
					&& document.documentElement.scrollLeft) {
				amountScrolledY = document.documentElement.scrollTop;
				amountScrolledX = document.documentElement.scrollLeft;
			}

			if (!parameters.horizontalOnly) {
				modalContainer.style.top = amountScrolledY
						+ (browserHeight / 2) - (modalHeight / 2) + 'px';
			}

			modalContainer.style.left = amountScrolledX + (browserWidth / 2)
					- (modalWidth / 2) + 'px';

			modalOverlay.style.height = documentHeight + 'px';
			modalOverlay.style.width = '100%';
		};

		// Set the id's, append the nested elements, and append the complete
		// modal to the document body
		modalOverlay.setAttribute('id', 'modal-overlay');
		modalContainer.setAttribute('id', 'modal-container');
		modalHeader.setAttribute('id', 'modal-header');
		modalContent.setAttribute('id', 'modal-content');
		modalClose.setAttribute('id', 'modal-close');
		modalHeader.appendChild(modalClose);
		modalContainer.appendChild(modalHeader);
		modalContainer.appendChild(modalContent);

		modalOverlay.style.visibility = 'hidden';
		modalContainer.style.visibility = 'hidden';

		WindowLoader.onWindowLoaded(function() {
			document.body.appendChild(modalOverlay);
			document.body.appendChild(modalContainer);
		});

		return method;
	}());


	var closeModal = function() {
		Modal.close();
	};

	
	var jnlp = '${jnlp}';
	var base = '${base}';
	var client = '${client.base}';
	var installStatus = null;
	var installStartTime;	

	return {
		//
		ClientStub: ClientStub,
		
		checkClient: function(onSuccess, onError) {
			
			var loadingModalTimeout = window.setTimeout(function() {
				Modal.open({
					ajaxContent : base + 'loading.html?d=' + new Date().getTime(),
					width : '50%',
					height : '140px',
					hideclose : true,
					lock : false,
					closeCallback : function() {
					}
				});Modal.open
			}, 500);
			
			var clientReadyCallback = function(response) {
				//console.log('Client attivo');
				window.clearTimeout(loadingModalTimeout);
				Modal.close();
				onSuccess();
			};
			
			var clientWaitCompleteInstall = function() {
				//Controllo inserito per fare in modo che quando si chiude la finestra non continui a ciclare in background
				if (installStatus != "stop") {  
					if (installStatus == "download") {
						installStatus = "installing"
						Modal.open({
							ajaxContent : base + 'starting.html?d=' + new Date().getTime(),
							width : '50%',
							height : '140px',
							hideclose : true,
							lock : false,
							closeCallback : function() {
							}
						});
					}
					
					if (installStatus == "installing") {
						if (Date.now() - installStartTime > 120000) {
							clientAbortCallback();
						}
						//console.log('Verifica completamento installazione...');
					} else {
						//console.log('In attesa di inizio installazione...');
					}
					
					ajax(client+"/init/ping", clientReadyCallback , null, {
						timeout:1000, 
						errorCallback:function(responseText, status, statusText) {
							//console.log("Errore:"+responseText+","+status+","+statusText);
							window.setTimeout(function() {
								clientWaitCompleteInstall();
							}, 500);				
						}
					});
				}
				else{
					installStatus = null;
				}
					
			}
			
			
			var clientErrorCallback = function(response, status) {
				//console.log('Client non presente, mostro pagina di installazione');
				
				//console.log('valore di installStatus: '+ installStatus);
				//Mostro la maschera di installazione
				
				//Commentando le sewguenti righe tolgo la visualizzazione della schermata di avvio di Hybrid
				//Mostro la maschera di installazione
								
				Modal.open({
					ajaxContent : base + 'instruction.html?d=' + new Date().getTime(),
					width : '50%',
					height : '340px',
					hideclose : true,
					lock : false,
					closeCallback : function() {
						installStatus = "stop";
					}
				});
				
				installStatus = "waiting";
				
				//Fine codice commentato
				
				/*
				 * Inserisco il codice del metodo install per passare direttamente
				 * all'installazione di Hybrid, se non presente, senza chiedere 
				 * conferma all'utente
				 */
//				installStatus = "download";
//				installStartTime = Date.now();
//				window.open(jnlp);
				//Fine codice inserito
				
				clientWaitCompleteInstall();
			}
			
			
			var clientAbortCallback = function(response, status) {
				//console.log('Il Client non verrà installato');
				//Mostro la maschera di installazione
				Modal.open({
					ajaxContent : base + 'abort.html?d=' + new Date().getTime(),
					width : '50%',
					height : '190px',
					hideclose : false,
					lock : false,
					closeCallback : function() {
						installStatus = "stop";
						return; //Nel caso esca si deve chiudere tutto
					}
				});
				
				//Per continuare a ciclare così da cambiare la schermata nel caso si decida di fare il nuovo download
				installStatus = "waiting";  
			}
			
			
		ajax(client+"/init/ping", clientReadyCallback , null, {timeout:2000, errorCallback:clientErrorCallback});
	},
		
		
	
	install : function() {
		installStatus = "download";
		installStartTime = Date.now();
		window.open(jnlp);
	},
	
		
		
	require : function(modules, onsuccess, onerror) {
			var client = '${client.base}';
			if (typeof modules == "string") {
				modules = [modules];
			}
			
			
			this.checkClient(function() {
				//Passo il cookie del server e forzo il caricamento dei moduli
				ajax(client+"/init/modules?modules="+modules.join(','), function(text) {
					var result = eval("("+text+")");
					var onsuccesParameters = [];
					
					var moduleGetStub = function(uri, idx) {
						ajax(client + uri, function(moduleCode) {
							try {
								var moduleStub = eval("(" + moduleCode + ")");
								onsuccesParameters[idx] = moduleStub;
								if (onsuccesParameters.length == result.modules.length) {
									try {
										onsuccess.apply(Hybrid, onsuccesParameters);
									} catch (e2) {
										//console.log("Errore nell'esecuzione del callback "+e2);
									}
								}
							} catch (e1) {
								//console.log("Errore interpretazione codice stub:"+e1);
								if (onerror) {
									onerror(e1);
								} 
							}
						},null, {errorCallback:onerror});
					}
					
					if (result.modules) {
						for (var idx = 0; idx < result.modules.length; idx++) {
							moduleGetStub(result.modules[idx].uri, idx);
						}
					}
				},null, {errorCallback:onerror});
				
				
			});
		}
		
	};

}();
