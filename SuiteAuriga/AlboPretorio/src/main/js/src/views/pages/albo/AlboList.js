/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import { PageTitle } from "../../components/page";
import AlboService from "../../../services/AlboService";
import X2JS from "x2js";
import filtroAlbo from "../../../models/FiltroAlbo";
import { Filter } from "../../../core/filter/Filter";
import { AttiList } from "../../../core/list/AttiList";
import Login from "../login/Login";
import { Wait } from "../../../core/file/Wait";

/**
 * Componente responsabile della visualizzazione
 * della lista degli atti
 */
class AlboList extends Component {

	async before_render() {
		let that = this;
		
		/**
		 * Discriminante per capire se sono nello storico o nell'albo
		 */
		this.isStorico = Router.isStorico();
		this.isStoricoAut = Router.isStoricoAut();
		if (this.isStorico) {
			this.defaultTitle = 'STORICO ATTI';
			this.prefixHref = 'storico';

			if (window.document.storicoTipiDocs) {
				this.tipiDocs = window.document.storicoTipiDocs;
			} else {
				await AlboService.getTipi()
				.then(res => {
					window.document.storicoTipiDocs = res;
					that.tipiDocs = res;
				})
				.catch((error) => {
					that.tipiDocs = [];
					console.error("Errore durante il recupero dei tipi", error);
					alert(error)
				});
			}

		} else if(this.isStoricoAut) {
			this.defaultTitle = 'STORICO ATTI';
			this.prefixHref = 'alboriservato';

			if (window.document.storicoTipiDocs) {
				this.tipiDocs = window.document.storicoTipiDocs;
			} else {
				await AlboService.getTipi()
				.then(res => {
					window.document.storicoTipiDocs = res;
					that.tipiDocs = res;
				})
				.catch((error) => {
					that.tipiDocs = [];
					console.error("Errore durante il recupero dei tipi", error);
					alert(error)
				});
			}
		} else {

			this.defaultTitle = 'ATTI IN PUBBLICAZIONE';
			this.prefixHref = 'albo';

			if (window.document.tipiDocs) {
				this.tipiDocs = window.document.tipiDocs;
			} else {
				await AlboService.getTipi()
				.then(res => {
					window.document.tipiDocs = res;
					that.tipiDocs = res;
				})
				.catch((error) => {
					that.tipiDocs = [];
					console.error("Errore durante il recupero dei tipi", error);
					alert(error)
				});
			}
		}

		/**
		 * idRouter mi permette di capire che tipo di ricerca sto facendo o devo fare
		 * - se è un numero vuol dire che sto ricercando atti di un determinato tipo con idType = idRouter
		 * - se è "backlist" vuol dire che sto tornando alla lista da un dettaglio
		 */

		let idRouter = Router.getId();
		if (idRouter && idRouter == "backlist") {
			//se ho premuto il tasto "ritorna alla lista" dal dettaglio, 
			
			if (this.backtolist && !(this.isStorico || this.isStoricoAut)) {
				// se sono nell'albo e ho una lista di dati salvati
				// verifico di non aver cambiato entrypoint (albo, storico)
				// a mano dalla barra degli url
				if (!this.backListModeCoincide()) {
					this.goToRicercaAvanzata();
				} else {

					this.idUdBack = window.idUdBack;
					this.title = this.titleBackList;
					this.attiList = this.backtolist;
					this.table = this.setList(this.title, this.defaultTitle, this.attiList);
				}
			} else if (this.backtolistStorico && (this.isStorico || this.isStoricoAut)) {
				// se sono nello storico e ho una lista di dati salvati
				// verifico di non aver cambiato entrypoint (albo, storico)
				// a mano dalla barra degli url
				if (!this.backListModeCoincide()) {
					this.goToRicercaAvanzata();
				} else {
					this.idUdBack = window.idUdBack;
					this.title = this.titleBackListStorico;
					this.attiList = this.backtolistStorico;
					this.table = this.setList(this.title, this.defaultTitle, this.attiList);
				}
			} else {
				//Se non c'è una lista di atti pre-salvata vado alla ricerca avanzata
				this.goToRicercaAvanzata();
			}

		} else if ((isNaN(parseInt(idRouter)) && isNaN(parseInt(this.idDocType)) && !this.attiList)) {
			//Se non c'è una lista di atti pre-salvata e non sto facendo una ricerca specifica, vado alla ricerca avanzata
			this.goToRicercaAvanzata();

		} else if (!isNaN(parseInt(this.idDocType)) && this.attiList) {
			// ho fatto una ricerca nei metadati (filtro Cerca) su una lista di atti con tipo specifico 
			if (window.idUdBack) {
				delete window.idUdBack;
			}

			let idTipoDoc = this.idDocType;
			let currentTipoDoc = this.tipiDocs.filter(tipoDoc => {
				if (tipoDoc.idType == idTipoDoc) {
					return tipoDoc;
				}
			});

			if (currentTipoDoc && currentTipoDoc.length > 0) {
				this.title = currentTipoDoc[0].descrizione;
			} else {
				this.title = this.defaultTitle;
			}

			this.table = new AttiList(this.attiList, false);

		} else if (this.attiList) {

			// se ho fatto una ricerca dalla ricerca avanzata e non è andata in errore
			if (window.idUdBack) {
				delete window.idUdBack;
			}

			if (window.fullText) {
				delete window.fullText;
			}

			this.title = this.defaultTitle;
			this.table = new AttiList(this.attiList, true);

		} else if (!isNaN(parseInt(idRouter))) {

			// Ho cliccato su una tipologia, sto facendo una ricerca solo per tipo da link
			if (window.idUdBack) {
				delete window.idUdBack;
			}

			if (window.fullText) {
				delete window.fullText;
			}

			let idTipoDoc = idRouter;
			let currentTipoDoc = this.tipiDocs.filter(tipoDoc => {
				if (tipoDoc.idType == idTipoDoc) {
					return tipoDoc;
				}
			});

			if (currentTipoDoc && currentTipoDoc.length > 0) {
				this.title = currentTipoDoc[0].descrizione;
			} else {
				this.title = this.defaultTitle;
			}
			this.idDocType = idTipoDoc;
			await this.getAtti(idTipoDoc)
			.then(res => {
				that.attiList = res;
				that.table = that.setList(that.title, this.defaultTitle, that.attiList)

			})
			.catch((error) => {
				console.error("Errore durante il recupero degl atti", error);
				alert(error);
				this.goToRicercaAvanzata();

			});

		} else {
			// sono in un caso che non è gestito, torno alla ricerca avanzata 
			this.goToRicercaAvanzata();
		}

		this.initForm();
	}

	backListModeCoincide() {
		return ((window.idUdModeIsStorico || window.idUdModeIsStoricoAut) && (this.isStorico || this.isStoricoAut)) || (!(window.idUdModeIsStorico || window.idUdModeIsStoricoAut) && !(this.isStorico || this.isStoricoAut));
	}

	setList(title, defaultTitle, lista) {
		// if(document.getElementById("waitPopup") && document.getElementById("loader")){
		// 	let popup = document.getElementById("waitPopup");
		// 	popup.style.display = "block";
		// 	let loader = document.getElementById("loader");
		// 	loader.style.display = "block";
		// }
		if (title == defaultTitle) {
			// se il titolo della lista da cui sto tornando è 
			// uguale al titolo generico, vuol dire che sto tornando ad 
			// una lista di atti mista quindi voglio mostrare
			// il tipo nel dettaglio della lista
			return new AttiList(lista, true);
		} else {
			// se sto tornando ad una lista di atti dello stesso
			// tipo non serve mostrare nel dettaglio della lista
			// il tipo dell'atto
			return new AttiList(lista, false);
		}
	}

	goToRicercaAvanzata() {
		if (window.idUdBack) {
			delete window.idUdBack;
		}
		if (window.fullText) {
			delete window.fullText;
		}

		Router.navigate('/' + this.prefixHref + '/filter');
	}

	initForm() {
		if (this.title == this.defaultTitle) {
			this.form = "";
		} else {
			this.form = new Filter(this.idDocType, [], true);
			// inizializza gli eventi
			this.form.on('search', this.search.bind(this));
			this.form.on('success', this.searchSuccess.bind(this));
			this.form.on('error', this.searchError.bind(this));
		}
	}


	/**
	 * Metodo richiamato al submit del form usato
	 * per chiamare il servizio con i filtri avanzati
	 * @param {HTMLFormControlsCollection} filtersXml collections di input del form
	 */
	search(filtri) {
		let filtersXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?\><filtriAlboPretorio>";
		let popup = document.getElementById("waitPopup");
        popup.style.display = "block";
        let loader = document.getElementById("loader");
        loader.style.display = "block";
		for (let index = 0; index < filtri.length - 1; index++) {
			const filter = filtri[index];
			filtersXml += "<filtroAlbo>";

			if (filter.name == 'FULL_TEXT' ) {
				window.fullText = filter.value;
				filtersXml += this.x2js.js2xml(new filtroAlbo(filter.name, AlboService.buildFullTextFilter(filter)));
			} else {
				filtersXml += this.x2js.js2xml(new filtroAlbo(filter.name, filter.value)); 
			}
			filtersXml += "</filtroAlbo>";

		}

		filtersXml += "<filtroAlbo>";
		filtersXml += this.x2js.js2xml(new filtroAlbo("NASCONDI_ANNULLATE", "VALIDA"));
		filtersXml += "</filtroAlbo>";

		filtersXml += "</filtriAlboPretorio>";
		return AlboService.search(filtersXml);
	}


	/**
	 * Metodo richiamato in caso di successo
	 * della api di creazione dell'utente
	 *
	 * Dopo aver mostrato il messaggio di conferma
	 * esegue la navigazione verso la lista
	 *
	 * @param {[Atto]} attiList risposta del server
	 */
	searchSuccess(attiList) {
		this.attiList = attiList;
		if (Router.isStorico()) {
			this.title = "STORICO ALBO PRETORIO ";
			this.hrefPrefix = "storico";
		  } else if (Router.isStoricoAut()) {
			this.title = "STORICO ALBO PRETORIO ";
			this.hrefPrefix = "alboriservato/home";
		  } else {
			this.title = "ALBO PRETORIO ";
			this.hrefPrefix = "albo";
		  }
		// this.hrefPrefix = Router.isStorico() ? "storico" : "albo";

		if (this.idDocType) {
			Router.navigate('/' + this.hrefPrefix + '/' + this.idDocType);
		} else {
			Router.navigate('/' + this.hrefPrefix + '/list');
		}

	}

	/**
	 * Metodo richiamato in caso di errore
	 * della api di creazione dell'utente
	 *
	 * Mostra il messaggio di errore
	 *
	 * @param {*} error
	 */
	searchError(error) {
		console.error('searcherror: ', error);
		alert(error);

	}


	getAtti(idDoc) {
		this.x2js = new X2JS({useDoubleQuotes : true, escapeMode: false});
		
		if(document.getElementById("waitPopup") && document.getElementById("loader")){
			let popup = document.getElementById("waitPopup");
			popup.style.display = "block";
			let loader = document.getElementById("loader");
			loader.style.display = "block";
		}
		
		let filtersXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?\><filtriAlboPretorio>";

		filtersXml += "<filtroAlbo>";
		filtersXml += this.x2js.js2xml(new filtroAlbo("TIPO_DOC", idDoc));
		filtersXml += "</filtroAlbo>";
		filtersXml += "<filtroAlbo>";
		filtersXml += this.x2js.js2xml(new filtroAlbo("NASCONDI_ANNULLATE", "VALIDA"));
		filtersXml += "</filtroAlbo>";
		filtersXml += "<filtroAlbo>";
		filtersXml += this.x2js.js2xml(new filtroAlbo("FULL_TEXT", ""));
		filtersXml += "</filtroAlbo>";

		filtersXml += "</filtriAlboPretorio>";
		return AlboService.search(filtersXml);
	}

	/**
	 * Render della pagina per creare un nuovo
	 * utente con il rispettivo form
	 */
	render() {
		if (Router.isStoricoAut() && !(sessionStorage.getItem("islogged")=="logged")) {
            return html`
              ${new Login(config.username, config.password)}
			  ${new Wait()}
            `;
        } else {
		let nroRecord = this.table.atti && this.table.atti.length > 0 ? this.table.atti.length : "0";
		if (window.document.isTipiTreeEmpty) {
			return html`
			<section class="section" style="margin-left: -20%;">
			<div class="row">
			${new PageTitle(this.title)}
			</div>
			${this.form}
			<div class="row">
			<div class="col-12">
			${this.table}
			</div>
			</div>
			<div class="row">
			<div class="col-12">
			<span class="text-secondary">Totale di atti visualizzati: ${nroRecord}</span>
			</div>
			</div>
			</section>
			${new Wait()}
			`;
		} else {
			return html`
			<section class="section">
			<div class="row">
			${new PageTitle(this.title)}
			</div>
			${this.form}
			<div class="row">
			<div class="col-12">
			${this.table}
			</div>
			</div>
			<div class="row">
			<div class="col-12">
			<span class="text-secondary">Totale di atti visualizzati: ${nroRecord}</span>
			</div>
			</div>
			</section>
			${new Wait()}
			`;
		}
		
		}
	}

	after_render() {
		if (this.idUdBack) {
			let rows = document.querySelectorAll('tr');
			let scrollTo = 0;
			rows.forEach(row => {
				if (row.id === 'ID' + this.idUdBack) {
					scrollTo = row.rowIndex;
				}
			});

			rows[scrollTo].scrollIntoView({
				block: 'center'
			});

			delete this.idUdBack;


		}
		if (this.isStorico || this.isStoricoAut) {
			this.backtolistStorico = this.attiList;
			this.titleBackListStorico = this.title;
			this.backtolist = undefined;
			this.titleBackList = undefined;
		} else {
			this.backtolistStorico = undefined;
			this.titleBackListStorico = undefined;
			this.backtolist = this.attiList;
			this.titleBackList = this.title;
		}

		this.attiList = null;
		this.title = "";
		if(document.getElementById("waitPopup") && document.getElementById("loader")){
			let popup = document.getElementById("waitPopup");
			popup.style.display = "none";
			let loader = document.getElementById("loader");
			loader.style.display = "none";
		}
	}
}



//export dell'unica instanza della pagina
export default new AlboList();