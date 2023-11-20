/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import { PageTitle } from "../../components/page";
import { Button } from "../../../core/button/Button";
import AlboService from "../../../services/AlboService";
import { File } from "../../../core/file/File";
import { IconButton } from "../../../core/button/IconButton";
import { Wait } from "../../../core/file/Wait";
import Login from "../login/Login";

/**
 * Componente per la visualizzazione del dettaglio di un Atto
 * 
 */
class AlboDetail extends Component {

	async before_render() {
		if (this.detail) {
			this.detail = {};
		}

		let that = this;

		this.idUD = Router.getId();
		this.idPubblicazione = Router.getIdPubblicazione() ? Router.getIdPubblicazione() : "";
		if(Router.isStorico()){
            this.hrefPrefix = "storico";
        } else if (Router.isStoricoAut()) {
            this.hrefPrefix = "alboriservato";
        } else {
            this.hrefPrefix = "albo";
        }
		// this.hrefPrefix = Router.isStorico() ? 'storico' : 'albo';
		if (this.idUD) {
			window.idUdBack = this.idUD;
			window.idUdModeIsStorico = Router.isStorico();
			window.idUdModeIsStoricoAut = Router.isStoricoAut();
			await AlboService.get(this.idUD, this.idPubblicazione)
			.then(res => {
				that.detail = res;
			})
			.catch((error) => {
				console.error("Errore durante il recupero del dettaglio", error);
				alert(error);
				Router.navigate('/' +  this.hrefPrefix + '/filter');
			});
		} else {
			Router.navigate('/' +  this.hrefPrefix + '/filter');
		}
	}

	downloadzip() {
		let that = this;
		let files = [];
		let primario = {};
		primario.nome = this.detail.filePrimario.displayFilename;
		primario.uri = this.detail.filePrimario.uriFile;
		files.push(primario);
		this.detail.allegati.forEach(allegato => {
			let file = {};
			file.nome = allegato.displayFilename;
			file.uri = allegato.uriFile;
			files.push(file);
		});
		AlboService.getZip(files)
		.then(res => {

			res.arrayBuffer().then((arrayBuffer) => {
				
			const blob = new Blob([new Uint8Array(arrayBuffer)], {type: 'application/zip' });
			
			const blobUrl = URL.createObjectURL(blob);
			let blobAnchor = document.createElement('a');
			blobAnchor.href = blobUrl;
			blobAnchor.download = primario.nome.substr(0, primario.nome.lastIndexOf('.')) +'.zip';
			blobAnchor.style.display = 'none';
			document.body.appendChild(blobAnchor);
			blobAnchor.click();
			document.body.removeChild(blobAnchor);
		})
	})
		.catch((error) => {
			console.error("Errore durante il recupero dei file zip", error);
			alert(error);
		});
	}

	b64toBlob(b64Data, contentType='') {
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
	 * Render della pagina per creare un nuovo
	 * utente con il rispettivo form
	 */
	async render() {
		if (Router.isStoricoAut() && !(sessionStorage.getItem("islogged")=="logged")) {
            return html`
              ${new Login(config.username, config.password)}
            `;
        } else {
		let downloadzipfun = this.downloadzip.bind(this);
		let that = this;
		let detailHtml = '<div class="row">';
		detailHtml += `   <h3 class="text-info">${this.detail.tipo + ' - ' + this.detail.attoNumero}</h3>`;
		detailHtml += `&nbsp;&nbsp;&nbsp;<span style="right: 0;"><a><i id="helpButton" title="Help" class="far fa-question-circle text-secondary" style="font-size: x-large;"></i></a>&nbsp;</span>`;
		detailHtml += `</div>`;

		detailHtml += `<div class="row">`;
		detailHtml += `   <h3 class="text-secondary"> Pubblicazione n° ${this.detail.nroPubbl}</h3>`;
		detailHtml += '</div>';

		detailHtml += '<div class="row">';
		detailHtml += `<strong class="text-secondary">Periodo di pubblicazione dal </strong>&nbsp;${this.detail.inizioPubbl}`;
		detailHtml += `<strong class="text-secondary">&nbsp;al&nbsp;</strong>${this.detail.finePubbl}`;
		detailHtml += '</div>';

		detailHtml += '<div class="row">';
		detailHtml += `<strong class="text-secondary">Forma di pubblicazione:</strong>&nbsp;${this.detail.formaPubblicazione}`;
		detailHtml += '</div>';

		detailHtml += '<div class="row">';
		detailHtml += `<strong class="text-secondary">Data e ora di pubblicazione:</strong>&nbsp;${this.detail.dataPubbl}`;
		detailHtml += '</div>';

		detailHtml += '<div class="row" style="margin-top: 10px; margin-bottom: 10px;">';
		detailHtml += `<h4 class="text-secondary"><strong>Oggetto&nbsp;</strong>${this.detail.oggetto}</h4>`   
			detailHtml += '</div>';

		detailHtml += '<div class="row">';
		detailHtml += `<span>`;
		if(this.detail.dataAdozione) {
			detailHtml += `<strong class="text-secondary">Data adozione&nbsp;</strong>${this.detail.dataAdozione}<br>`;
		}
		if(this.detail.dataAtto){
			detailHtml += `<strong class="text-secondary">Data registrazione atto&nbsp;</strong>${this.detail.dataAtto}<br>`;
		}
		if(this.detail.flgImmediatamenteEsegiubile && this.detail.flgImmediatamenteEsegiubile == 1 && this.detail.esecutivoDal){
			detailHtml += `<strong class="text-secondary">Immediatamente eseguibile&nbsp;</strong> <strong class="text-secondary">&nbsp;Esecutivo dal&nbsp;</strong>${this.detail.esecutivoDal}<br>`;
		} else if(this.detail.flgImmediatamenteEsegiubile && this.detail.flgImmediatamenteEsegiubile == 1 && !this.detail.esecutivoDal) {
			detailHtml += `<strong class="text-secondary">Immediatamente eseguibile&nbsp;</strong><br>`;
		} else if(this.detail.flgImmediatamenteEsegiubile && this.detail.flgImmediatamenteEsegiubile == 0 && this.detail.esecutivoDal){
			detailHtml += `<strong class="text-secondary">Esecutivo dal&nbsp;</strong>${this.detail.esecutivoDal}<br>`;
		}
		detailHtml += `<strong class="text-secondary">${config.labelOrigin !== undefined && config.labelOrigin !== "" ? config.labelOrigin : "Origine"}&nbsp;</strong>${this.detail.richiedente}<br>`;
		detailHtml += `<strong class="text-secondary">Tipologia&nbsp;</strong>${this.detail.tipo}<br>`;
		detailHtml += '</div>';
		detailHtml += '<div class="row">';
		
		if(this.detail.stato && (this.detail.stato.toUpperCase() === "RETTIFICATA" || this.detail.stato.toUpperCase() === "ANNULLATA")){
			detailHtml += `<strong class="text-secondary">Stato pubblicazione&nbsp;</strong><span class="stato-non-valido">${this.detail.stato}</span></span>`;
		} else if(this.detail.stato && this.detail.stato.toUpperCase() === "VALIDA") {
			detailHtml += `<strong class="text-secondary">Stato pubblicazione&nbsp;</strong><span>${this.detail.stato}</span></span>`;
		}
		
		if (this.detail.stato && this.detail.stato.toUpperCase() === "RETTIFICATA") {
			detailHtml += `&nbsp;<a href="#/${this.hrefPrefix}/atto/${this.detail.idUD}" class="text-secondary"><strong class="text-info-ultima-rev">${"Vai all'ultima pubblicazione"}</strong></a>`;
		}
		detailHtml += '</div>';

		if (this.detail.stato && this.detail.stato.toUpperCase() === "ANNULLATA") {
			detailHtml += '<div class="row">';
			detailHtml += `<strong class="text-secondary">Motivo annullamento&nbsp;</strong>${this.detail.motivoAnn}`;
			detailHtml += '</div>';
			return await html` <section class="section">
			<div class="row">
			${new PageTitle( "" )}
			</div>
			<div class="col-12">
			${detailHtml}
			</div>
			</section>

			<div class="row" style="margin-top:30px;">
			<div class="large-6 columns" style="text-align: end;">  
			</div>
			<div class="large-6 columns" style="text-align: end;">  
			${new Button('/' + this.hrefPrefix + '/backlist', 'Torna alla lista', 'backToList')}
			${new Button('/' + this.hrefPrefix + '/filter', 'Nuova Ricerca', 'backToSearch')}
			</div>
			</div>

			`;
		} else if (this.detail.filePrimario && this.detail.allegati && this.detail.allegati.length > 0 ) {
			return await html` <section class="section">
			<div class="row">
			${new PageTitle( "" )}
			</div>
			<div class="col-12">
			${detailHtml}

			<div class="row">
			<strong class="text-secondary">Testo atto&nbsp;</strong> ${new File(this.detail.filePrimario)}

			</div>
			<div class="row">
			<strong class="text-secondary">N.ro di allegati&nbsp;</strong>${this.detail.allegati.length}
			</div>
			${this.detail.allegati.map(
			allegato => html`
			<div class="row">
			${new File(allegato)}
			</div>
			`
			)}

			</div>
			</section>
			<div class="row" style="margin-top:30px;">
			<div class="large-6 columns" style="text-align: end;">  
			</div>
			<div class="large-6 columns" style="text-align: end;">  
			${new Button('/' + this.hrefPrefix + '/backlist', 'Torna alla lista', 'backToList')}
			${new Button('/' + this.hrefPrefix + '/filter', 'Nuova Ricerca', 'backToSearch')}
			</div>
			</div>
			${new Wait()}
			`;
		}  else {
			return await html` <section class="section">
			<div class="row">
			${new PageTitle( "" )}
			</div>
			<div class="col-12">
			${detailHtml}
			<div class="row">
			<strong class="text-secondary">Testo atto&nbsp;</strong> ${new File(this.detail.filePrimario)}
			</div>
			</div>
			</section>
			<div class="row" style="margin-top:30px;">
			<div class="large-6 columns" style="text-align: end;">  
			</div>
			<div class="large-6 columns" style="text-align: end;">  
			${new Button('/' + this.hrefPrefix + '/backlist', 'Torna alla lista', 'backToList')}
			${new Button('/' + this.hrefPrefix + '/filter', 'Nuova Ricerca', 'backToSearch')}
			</div>
			</div>
			${new Wait()}
			`;
		}
	}
	}

}


//export dell'unica instanza della pagina
export default new AlboDetail();