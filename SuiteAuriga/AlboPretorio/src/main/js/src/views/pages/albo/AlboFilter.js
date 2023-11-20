/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import { PageTitle } from "../../components/page";
import { Filter } from "../../../core/filter/Filter";
import AlboService from "../../../services/AlboService";
import X2JS from "x2js";
import filtroAlbo from "../../../models/FiltroAlbo";
import AlboList from "./AlboList";
import Login from "../login/Login";
import { Wait } from "../../../core/file/Wait";

/**
 * Componente responsabile della visualizzazione
 * della pagina per visualizzare gli atti pubblicati
 */
class AlboFilter extends Component {

	constructor(items) {
		super();
		this.x2js = new X2JS({useDoubleQuotes : true, escapeMode: false});
	}

	async before_render() {
		let that = this;
		this.isStorico = Router.isStorico();
		this.isStoricoAut = Router.isStoricoAut();
		if (this.isStorico || this.isStoricoAut){
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
					console.error("Errore durante il caricamento dei tipi", error);
					alert(error);
				});
			}
		} else {
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
					console.error("Errore durante il caricamento dei tipi", error);
					alert(error);
				});
			}
		}

		//se i dati sono già stati recuperati, prendo quelli
		if (this.isStorico || this.isStoricoAut){
			if (window.document.listaUOStorico) {
				this.listaUO = window.document.listaUOStorico;
			} else {
				await AlboService.getUO()
				.then(res => {
					window.document.listaUOStorico = res;
					that.listaUO = res;
				})
				.catch((error) => {
					that.listaUO = [];
					console.error("Errore durante il recupero delle UO", error);
					alert(error);
				});
			}
		} else {
			if (window.document.listaUO) {
				this.listaUO = window.document.listaUO;
			} else {
				await AlboService.getUO()
				.then(res => {
					window.document.listaUO = res;
					that.listaUO = res;
				})
				.catch((error) => {
					that.listaUO = [];
					console.error("Errore durante il recupero delle UO", error);
					alert(error);
				});
			}
		}

		this.initForm();

	}

	/**
	 * Metodo usato per inizializzare il form con la lista degli input
	 * e gli handler degli eventi di search, success ed error
	 */
	initForm() {
		this.form = new Filter(this.tipiDocs, this.listaUO);
		// inizializza gli eventi
		this.form.on('search', this.search.bind(this));
		this.form.on('success', this.searchSuccess.bind(this));
		this.form.on('error', this.searchError.bind(this));
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
			if (filter.name == "RICHIEDENTE"){
				//select normale
				filtersXml += this.x2js.js2xml(new filtroAlbo("ID_RICHIEDENTE", filter.value));

				// se dovessimo rimettere autocompletamento
				// if(filter.list && filter.list.options && filter.list.options.namedItem(filter.value)){
				//   filtersXml += this.x2js.js2xml(new filtroAlbo("ID_RICHIEDENTE", filter.list.options.namedItem(filter.value).dataset.value));
				// } else {
				//   filtersXml += this.x2js.js2xml(new filtroAlbo("DESC_RICHIEDENTE", filter.value));
				// }
			} else if (filter.name === "NASCONDI_ANNULLATE") {
				if (filter.checked) {
					filtersXml += this.x2js.js2xml(new filtroAlbo("NASCONDI_ANNULLATE", "VALIDA"));
				} else {
					filtersXml += this.x2js.js2xml(new filtroAlbo("NASCONDI_ANNULLATE", ""));
				}
			} else if (filter.name == 'FULL_TEXT') {
				filtersXml += this.x2js.js2xml(new filtroAlbo(filter.name, AlboService.buildFullTextFilter(filter)));
			} else {
				filtersXml += this.x2js.js2xml(new filtroAlbo(filter.name, filter.value));
			}
			filtersXml += "</filtroAlbo>";
		}
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
		AlboList.attiList = attiList;
		AlboList.idDocType = undefined;
		if (Router.isStorico()) {
			Router.navigate('/storico/list'); 
		} else if (Router.isStoricoAut()) {
			Router.navigate('/alboriservato/list');
		} else {
			Router.navigate('/albo/list');
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
			if(window.document.isTipiTreeEmpty){
				return html`
				<section class="section" style="margin-left: -20%;">
				<div class="row">
				${new PageTitle('Ricerca')}
				</div>
				<div class="row">
				${this.form}
				</div>
				</section>
				${new Wait()}
				`;
			} else {
				return html`
				<section class="section">
				<div class="row">
				${new PageTitle('Ricerca avanzata')}
				</div>
				<div class="row">
				${this.form}
				</div>
				</section>
				${new Wait()}
				`;
			}
		
		}
	}
}

//export dell'unica instanza della pagina
export default new AlboFilter();