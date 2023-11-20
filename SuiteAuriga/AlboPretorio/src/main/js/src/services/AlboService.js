/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import Atto from '../models/Atto';
import TipoDocumento from '../models/TipoDocumento';
import UO from '../models/UO';
import { Router } from "../core/Router";
require('isomorphic-fetch');


/**
 * Classe per gestire le comunicazioni con il backend
 *
 */
class AlboService {
	url = window.location.pathname.substr(0,window.location.pathname.lastIndexOf('/') +1) + 'dispatcher/alboPretorioServlet/invoke';
	
	genericError = "Impossibile evadere la richiesta. Riprova più tardi";
	
	getHeaders(){
		return {'Content-Type': 'application/x-www-form-urlencoded',
			'isMultiEnte' : window.config.isMultiEnte ? window.config.isMultiEnte : false,
			'codEnte' : window.config.isMultiEnte ? Router.getCodEnte() : "",
			'isStorico': Router.isStorico(),
			'isStoricoAut': Router.isStoricoAut()};
	}

	getHeadersCustom(){
		return {'Content-Type': 'application/json',
			'isStorico': Router.isStorico(),
			'isStoricoAut': Router.isStoricoAut()};
	}
	

	overlayon() {
		if(document.getElementById("overlay")){
			document.getElementById("overlay").style.display = "block";
		}
	}

	overlayoff() {
		if(document.getElementById("overlay")){
			document.getElementById("overlay").style.display = "none";
		}
	}

	/**
	 * Metodo che dato in ingresso il filtro per la ricerca full text ritorna lo stesso con i wildcard '*'
	 * per ogni parola inserita
	 * 
	 * @param {*} filter 
	 */
	buildFullTextFilter(filter) {
		let valueToken = filter.value.split(" ");
		let filterValue = "";
		if (valueToken.length > 0 ) {
			for (let pos = 0; pos < valueToken.length; pos++) {
				if (valueToken[pos] != "") {
					filterValue += valueToken[pos] + "* "
				}
			}
		}
		
		return filterValue.trim();
	}
	
	/**
	 * Metodo che dato in ingresso il file in base64 e il contentType, genera il blob
	 * 
	 * @param {*} b64Data 
	 * @param {*} contentType 
	 */
	b64toBlob(b64Data, contentType = '') {
		const sliceSize = 512;
		const byteCharacters = atob(b64Data);
		const byteArrays = [];

		for (let offset = 0; offset < byteCharacters.length; offset += sliceSize) {
			const slice = byteCharacters.slice(offset, offset + sliceSize);

			const byteNumbers = new Array(slice.length);
			for (let i = 0; i < slice.length; i++) {
				byteNumbers[i] = slice.charCodeAt(i);
			}

			const byteArray = new Uint8Array(byteNumbers);
			byteArrays.push(byteArray);
		}

		const blob = new Blob(byteArrays, {type: contentType});
		return blob;
	}

	/**
	 * Metodo asincrono per ottenere la lista degli atti
	 */
	async search(filters) {
		// options della request
		let normalizeErrorMessagefun = this.normalizeErrorMessage.bind(this);
		const data = "dataInputXml=" + filters + "&nameService=search";

		const options = {
				method: 'POST',
				body: data,
				headers: this.getHeaders()
		};

		try {
			this.overlayon();
			const response = await fetch(this.url, options);
			if (!response.ok) {
				if (response.headers.get("storeException") == 'false'){
					let popup = document.getElementById("waitPopup");
					popup.style.display = "none";
					let loader = document.getElementById("loader");
					loader.style.display = "none";
					throw new Error(this.genericError);
				} else {
					await response.text().then(function (text) {
						let popup = document.getElementById("waitPopup");
						popup.style.display = "none";
						let loader = document.getElementById("loader");
						loader.style.display = "none";
						throw normalizeErrorMessagefun(text);
					});
				}
			}
			const json = await response.json();
			if (!json || !json.data) {
				return [];
			} else if (json.data instanceof Array) {
				return json.data.map(atto => new Atto(atto));
			} else {
				return [json.data].map(atto => new Atto(atto));
			}
		} catch (err) {
			return Promise.reject(err);
		}  finally {
			this.overlayoff();
		}
	}

	/**
	 * Metodo asincrono per ottenere la verifica della firma di un file
	 * 
	 */
	async verificaFirma(uri, nomeFile, impronta, algoritmo, encoding) {
		// options della request
		let dataXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?\><dataInputXml><uriFile>" + uri + "</uriFile><nomeFile>" + nomeFile + "</nomeFile><impronta>" + impronta + "</impronta><algoritmo>" + algoritmo + "</algoritmo><encoding>" + encoding + "</encoding></dataInputXml>";
		const data = "dataInputXml=" + dataXml + "&nameService=checkFirma";

		const options = {
				method: 'POST',
				body: data,
				responseType: 'arraybuffer',
				dataType:'blob',
				headers: this.getHeaders()
		};

		try {
			this.overlayon();
			const response = await fetch(this.url, options);
			if (!response.ok) {
				let popup = document.getElementById("waitPopup");
            	popup.style.display = "none";
				let loader = document.getElementById("loader");
            	loader.style.display = "none";
				throw new Error(this.genericError);
			}
			
			return response;
			/*const json = await response.text();
			if (!json) {
				return "";
			} else {
				return json;
			}*/
		} catch (err) {
			return Promise.reject(err);
		} finally {
			this.overlayoff();
		}
	}


	/**
	 * Metodo asincrono per il file sbustato 
	 * 
	 */
	async sbusta(uri, nomeFile) {
		// options della request
		let dataXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?\><dataInputXml><uriFile>" + uri + "</uriFile><nomeFile>" + nomeFile + "</nomeFile></dataInputXml>";
		const data = "dataInputXml=" + dataXml + "&nameService=sbusta";


		const options = {
				method: 'POST',
				body: data,
				responseType: 'arraybuffer',
				dataType:'blob',
				headers: this.getHeaders()
		};

		try {
			this.overlayon();
			const response = await fetch(this.url, options);
			if (!response.ok) {
				let popup = document.getElementById("waitPopup");
            	popup.style.display = "none";
				let loader = document.getElementById("loader");
            	loader.style.display = "none";
				throw new Error(this.genericError);
			}
		
			return response;
			/*const json = await response.json();
			if (!json) {
				return "";
			} else {
				return json;
			}*/
		} catch (err) {
			return Promise.reject(err);
		} finally {
			this.overlayoff();
		}
	}

	/**
	 * Metodo asincrono per ottenere le UO
	 * 
	 */
	async getUO() {

		// options della request
		const data = "nameService=getUO";

		const options = {
				method: 'POST',
				body: data,
				headers: this.getHeaders()
		};

		try {
			this.overlayon();
			const response = await fetch(this.url, options);
			if (!response.ok) {
				throw new Error(this.genericError);
			}
			const json = await response.json();
			if (!json || !json.data) {
				return [];
			} else if (json.data instanceof Array) {
				return json.data.map(uo => new UO(uo));
			} else {
				return [json.data].map(uo => new UO(uo));
			}
		} catch (err) {
			return Promise.reject(err);
		} finally {
			this.overlayoff();
		}
	}

	/**
	 * Metodo asincrono per ottenere le tipologie degli atti
	 * 
	 */
	async getTipi() {
		// options della request
		const data = "nameService=getTipologieDoc";

		const options = {
				method: 'POST',
				body: data,
				headers: this.getHeaders()
		};

		try {
			this.overlayon();
			const response = await fetch(this.url, options);
			if (!response.ok) {
				throw new Error(this.genericError);
			}
			const json = await response.json();
			if (!json || !json.data) {
				return [];
			} else if (json.data instanceof Array) {
				return json.data.map(tipoDocumento => new TipoDocumento(tipoDocumento));
			} else {
				return [json.data].map(tipoDocumento => new TipoDocumento(tipoDocumento));
			}
		} catch (err) {
			return Promise.reject(err);
		} finally {
			this.overlayoff();
		}
	}

	/**
	 * Metodo asincrono per ottenere le tipologie degli atti con struttura alberata
	 * 
	 */
	async getTipiTree() {
		// options della request
		const data = "nameService=getTipologieDocTree";

		const options = {
				method: 'POST',
				body: data,
				headers: this.getHeaders()
		};
		try {
			this.overlayon();
			const response = await fetch(this.url, options);
			if (!response.ok) {
				throw new Error(this.genericError);
			 }
			const json = await response.json();
			if (!json || !json.data) {
				return [];
			} else if (json.data instanceof Array) {
				return json.data.map(tipoDocumento => new TipoDocumento(tipoDocumento));
			} else {
				return [json.data].map(tipoDocumento => new TipoDocumento(tipoDocumento));
			}
		} catch (err) {
			return Promise.reject(err);
		} finally {
			this.overlayoff();
		}
	}

	/**
	 * Metodo asincrono per ottenere il dettaglio dell'atto
	 * 
	 */
	async get(idUd, idPubblicazione) {
		// options della request
		let normalizeErrorMessagefun = this.normalizeErrorMessage.bind(this);
		let idUdXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?\><idUd>" + idUd + "</idUd><idPubblicazione>" + idPubblicazione.toUpperCase() + '</idPubblicazione>';
		const data = "dataInputXml=" + idUdXml + "&nameService=getDettaglio";
		const options = {
				method: 'POST',
				body: data,
				headers: this.getHeaders()
		};

		try {
			this.overlayon();
			const response = await fetch(this.url, options);
			if (!response.ok) {
				if (response.headers.get("storeException") == 'false'){
					throw new Error(this.genericError);
				} else {
					await response.text().then(function (text) {
						throw normalizeErrorMessagefun(text);
					});
				}
			}
			const json = await response.json();
			if (!json) {
				return new Atto();
			} else {
				// return new Atto(json.alboUDBean);
				return new Atto(json);
			}
		} catch (err) {
			return Promise.reject(err);
		} finally {
			this.overlayoff();
		}
	}

	/**
	 * Metodo asincrono per ottenere un file dato l'uri
	 * 
	 */
	async getFile(uri, mime = "", flgConvertibile = "", nomeFile = "", flgFirmato = "") {
		// options della request
		let dataInputXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?\><uriFile>" + uri + "</uriFile>";
		dataInputXml += "<flgConvertibile>" + flgConvertibile + "</flgConvertibile>";
		dataInputXml += "<mimetype>" + mime + "</mimetype>";
		dataInputXml += "<nomeFile>" + nomeFile + "</nomeFile>";
		dataInputXml += "<flgFirmato>" + flgFirmato + "</flgFirmato>";
		"dataInputXml=" + dataInputXml;
		const data = "dataInputXml=" + dataInputXml + "&nameService=recuperaFile";
		const options = {
				method: 'POST',
				body: data,
				responseType: 'arraybuffer',
				dataType:'blob',
				headers: this.getHeaders()
		};

		try {
			this.overlayon();
			const response = await fetch(this.url, options);
			if (!response.ok) {
				let popup = document.getElementById("waitPopup");
            	popup.style.display = "none";
				let loader = document.getElementById("loader");
            	loader.style.display = "none";
				throw new Error(this.genericError);
			}
			return response;
			 
			/*const json = await response.text();
			if (!json) {
				return "";
			} else {
				return json;
			}*/
		} catch (err) {
			return Promise.reject(err);
		} finally {
			this.overlayoff();
		}
	}

	/**
	 * Metodo asincrono per ottenere il manuale
	 * 
	 */
	async getManuale() {
		// options della request
		let dataInputXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?\>";
		"dataInputXml=" + dataInputXml;
		const data = "dataInputXml=" + dataInputXml + "&nameService=getManualeRapido";
		const options = {
				method: 'POST',
				body: data,
				responseType: 'arraybuffer',
				dataType:'blob',
				headers: this.getHeaders()
		};

		try {
			this.overlayon();
			const response = await fetch(this.url, options);
			if (!response.ok) {
				let popup = document.getElementById("waitPopup");
            	popup.style.display = "none";
				let loader = document.getElementById("loader");
            	loader.style.display = "none";
				throw new Error(this.genericError);
			}
			return response;
			 
			/*const json = await response.text();
			if (!json) {
				return "";
			} else {
				return json;
			}*/
		} catch (err) {
			return Promise.reject(err);
		} finally {
			this.overlayoff();
		}
	}

	/**
	 * Metodo asincrono per ottenere la versione timbrata di un file
	 * 
	 */
	async getFileTimbrato(uri, mime = "", flgConvertibile = "", nomeFile = "", flgFirmato = "", idUd, idDoc) {
		// options della request
		let dataInputXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?\><uriFile>" + uri + "</uriFile>";
		dataInputXml += "<flgConvertibile>" + flgConvertibile + "</flgConvertibile>";
		dataInputXml += "<mimetype>" + mime + "</mimetype>";
		dataInputXml += "<nomeFile>" + nomeFile + "</nomeFile>";
		dataInputXml += "<flgFirmato>" + flgFirmato + "</flgFirmato>";
		dataInputXml += "<idUd>" + idUd + "</idUd>";
		dataInputXml += "<idDoc>" + idDoc + "</idDoc>";
		"dataInputXml=" + dataInputXml;
		const data = "dataInputXml=" + dataInputXml + "&nameService=getVersioneTimbrata";
		const options = {
				method: 'POST',
				body: data,
				responseType: 'arraybuffer',
				dataType:'blob',
				headers: this.getHeaders()
		};

		try {
			this.overlayon();
			const response = await fetch(this.url, options);
			if (!response.ok) {
				let popup = document.getElementById("waitPopup");
            	popup.style.display = "none";
				let loader = document.getElementById("loader");
            	loader.style.display = "none";
				throw new Error(this.genericError);
			}
			return response;
			 
			/*const json = await response.text();
			if (!json) {
				return "";
			} else {
				return json;
			}*/
		} catch (err) {
			return Promise.reject(err);
		} finally {
			this.overlayoff();
		}
	}

	/**
	 * Metodo asincrono per ottenere lo zip dei file
	 * 
	 */
	async getZip(files) {
		// options della request
		let filesXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?\><filesAlboPretorio>";
		files.forEach(file => {
			filesXml += "<fileAlbo>";
			filesXml += "<nomeFile>";
			filesXml += file.nome;
			filesXml += "</nomeFile>";
			filesXml += "<uriFile>";
			filesXml += file.uri;
			filesXml += "</uriFile>";
			filesXml += "</fileAlbo>";
		});
		filesXml += "</filesAlboPretorio>";
		const data = "dataInputXml=" + filesXml + "&nameService=scaricaZip";

		const options = {
				method: 'POST',
				body: data,
				responseType: 'arraybuffer',
				dataType:'blob',
				headers: this.getHeaders()
		};

		try {
			this.overlayon();
			const response = await fetch(this.url, options);
			if (!response.ok) {
				throw new Error(this.genericError);
			}
			
			return response;
			/*const json = await response.text();
			if (!json) {
				return "";
			} else {
				return json;
			}*/
		} catch (err) {
			return Promise.reject(err);
		} finally {
			this.overlayoff();
		}
	}

	normalizeErrorMessage(errorMessage) {
		if (errorMessage != null) {
			// se ho il messaggio ripetuto due volte lo prendo una volta sola
			let message = errorMessage;
			let index = 0;
			while (true) {
				let pos = message.indexOf("_", index);
				if (pos != -1) {
					let message1 = message.substring(0, pos);
					let message2 = message.substring(pos + 1);
					if (message1==message2) {
						message = message1;
						break;
					}
				} else {
					break;
				}
				index = pos + 1;
			}
			return message;
		} else {
			return "Errore generico";
		}
	}
}

//export dell'unica instanza del servizio
export default new AlboService();
