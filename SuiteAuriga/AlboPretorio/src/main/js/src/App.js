/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
'use strict';

//import del core
import { Router, html } from './core';
import $ from 'jquery';
//import delle views
import Home from './views/pages/home/Home';

//import dei componenti
import Navbar from './views/components/navbar/Navbar';
import SideMenu from './views/components/sidemenu/SideMenu';
import Footer from './views/components/footer/Footer';
import AlboList from './views/pages/albo/AlboList';
import AlboFilter from './views/pages/albo/AlboFilter';
import AlboDetail from './views/pages/albo/AlboDetail';
import PageNotFound from './views/pages/PageNotFound';
import AlboService from './services/AlboService';

//import dello stile
import 'bootstrap/dist/css/bootstrap.min.css';

import './App.css';
import './config.json';

//Lista delle rotte supportate dall'app. 
//Ogni altra url non compresa verrà rimandata alla PageNotFound
const routes = {
		'/notfound': PageNotFound,
		'/albo': Home,
		'/albo/home': Home,
		'/albo/notfound': PageNotFound,
		'/albo/:id': AlboList, // se faccio una ricerca per tipologia atto dai link 
		'/albo/list': AlboList, // se faccio una ricerca avanzata
		'/albo/backlist': AlboList, // se torno alla lista a partire da un dettaglio
		'/albo/filter': AlboFilter,
		'/albo/atto/:id': AlboDetail,
		'/albo/atto/:id/:idPubblicazione': AlboDetail,
		'/storico': Home,
		'/storico/notfound': PageNotFound,
		'/storico/:id': AlboList, // se faccio una ricerca per tipologia atto dai link 
		'/storico/list': AlboList, // se faccio una ricerca avanzata
		'/storico/backlist': AlboList, // se torno alla lista a partire da un dettaglio
		'/storico/filter': AlboFilter,
		'/storico/atto/:id': AlboDetail,
		'/storico/atto/:id/:idPubblicazione': AlboDetail,
		'/alboriservato': Home,
		'/alboriservato/home': Home,
		'/alboriservato/home/:id': AlboList,
		'/alboriservato/notfound': PageNotFound,
		'/alboriservato/:id': AlboList, // se faccio una ricerca per tipologia atto dai link 
		'/alboriservato/list': AlboList, // se faccio una ricerca avanzata
		'/alboriservato/backlist': AlboList, // se torno alla lista a partire da un dettaglio
		'/alboriservato/filter': AlboFilter,
		'/alboriservato/atto/:id': AlboDetail,
		'/alboriservato/atto/:id/:idPubblicazione': AlboDetail,
};

/**
 * Classe main dell'app in cui
 * - è definito il layout generale
 * - è gestito il routing per avere dinamicità del contenuto
 */
class App {
	constructor() {
		this.init();
	}

	/**
	 * Metodo getter per la pagina ottenuta dal router
	 */
	get page() {
		return Router.getPage();
	}

	/**
	 * Inizializzazione
	 */
	init() {
		this.initRouter();
		this.initAppDOMElement();
		this.bootstrap();

	}

	/**
	 * Handler dell'evento load della pagina principale
	 * Esegue il bootstrap dell'applicazione
	 */
	bootstrap() {
		window.addEventListener('load', this.render.bind(this));
	}

	/**
	 * Inizializza il router
	 */
	initRouter() {
		Router.init(routes, this.render.bind(this));
	}

	/**
	 * Ottiene il nodo DOM principale sul quale rendere l'applicazione
	 */
	initAppDOMElement() {
		this.app = null || document.getElementById('app');
	}

	/**
	 * Metodo di render dell'applicazione principale
	 * dove è definito il layout generale dell'applicazione
	 */
	async render() {

		if (!window.config) {
			await $.getJSON('config.json',
					function (data) {
				window.config = data;
			});
		}

		this.app.innerHTML = await html`
		<div id="overlay" class="col-md-10 ml-sm-auto col-lg-9 px-5" >
		<span id="spinnerContainer"> 
		<span id="spinner" class="spinner-border text-secondary">
		</span>
		</span>
		</div>
		<!-- Navbar -->
		${Navbar}
		<!-- /Navbar -->
		<div class="container-fluid">
		<!-- SideMenu -->
		${SideMenu}
		<!-- /SideMenu -->
		<main
		id="page_container"
		role="main"
		class="col-md-10 ml-sm-auto col-lg-9 px-5 mt-5"
		>
		<!-- Contentuto principale -->
		${this.page}
		<!-- /Contentuto principale -->
		</main>
		</div>
		<!-- Footer -->
		<!-- ${Footer} -->
		<!-- /Footer -->`
		this.after_render();
	}

	checkParentToToggle(activeItem) {

		let activeItemToggle = activeItem.previousElementSibling;
		if (activeItemToggle) {
			if (activeItemToggle.attributes["data-parentToggleId"]) {
				let toggleParent = document.getElementById(activeItemToggle.attributes["data-parentToggleId"].value);
				toggleParent.click();
				if (toggleParent.nextElementSibling.classList.contains('nav-link')) {
					this.checkParentToToggle(toggleParent.nextElementSibling);
				}
			} 
		}
	}
	
	after_render() {
		let that = this;
		if (config.treeSideNavbar) {
				var toggler = document.getElementsByClassName("caret");
				var i;

				for (i = 0; i < toggler.length; i++) {
					let togglerIcon = toggler[i].querySelector('i');
					togglerIcon.addEventListener("click", function () {
						this.parentElement.parentElement.querySelector(".nested").classList.toggle("active");
						this.classList.toggle("fa-chevron-circle-right");
						this.classList.toggle("fa-chevron-circle-down");
					});
				}

				var activeItem = document.getElementsByClassName("nav-link active")[0];
				if (activeItem) {
					that.checkParentToToggle(activeItem);  
				}

				// in caso di tipologie doc che hanno tipologie figlie, al click della tipologia doc padre, il nodo va esploso
				// e vengono mostrare le categorie doc figlie
				// INIZIO
				var parentTogglerList = document.getElementsByClassName("parent-nav-link");

				if(parentTogglerList && parentTogglerList.length>0){
					for (i = 0; i < parentTogglerList.length; i++) {
						let parentToggler = parentTogglerList[i];
							parentToggler.addEventListener("click", function () {
								let toggleParent = document.getElementById(parentToggler.attributes["id"].value+"toggle");
								toggleParent.click();
							});
					}
				}
				// FINE

		}

		// Scroll della lista delle tipologie documentali alla tipologia attualmente attiva
		if (window.activeMenuItem) {
			if (/\/(albo|storico)\/\d+/i.test(window.activeMenuItem) || /\/(albo|storico)\/filter+/i.test(window.activeMenuItem) ) {
			  let idType = window.activeMenuItem.substr(window.activeMenuItem.lastIndexOf('/')+1);
			  let idElem = 'link'+idType;
			  var elmnt = document.getElementById(idElem);
			  if (elmnt) {
				elmnt.scrollIntoView({behavior: "auto", block: "center", inline: "nearest"});
			  }
	  
			}
		}

		if(Router.isStoricoAut()){
			document.getElementById('logout').addEventListener('click', this.logout.bind(this));
		}

		document.getElementById('helpButton').addEventListener('click', this.help.bind(this));
	}
	
	  logout(){
		sessionStorage.removeItem('islogged');
		location.reload();
	  }

	  help(){
		let that = this;

        
		let popup = document.getElementById("waitPopup");
		popup.style.display = "block";
		let loader = document.getElementById("loader");
		loader.style.display = "block";
		AlboService.getManuale()
			.then(response => {
				response.arrayBuffer()
				.then((arrayBuffer) => {
					const blob = new Blob([new Uint8Array(arrayBuffer)], {type: 'application/pdf' });

					if (window.navigator && window.navigator.msSaveOrOpenBlob) {
						// IE or Edge
						window.navigator.msSaveOrOpenBlob(blob, "Guida rapida");
						popup.style.display = "none";
						loader.style.display = "none"; 
					}
					else {
						// #toolbar=0 per non far apparire il nome alfanumerico 
						// del blob nella viewer
						const blobUrl = URL.createObjectURL(blob);
						if (navigator.userAgent.indexOf("Chrome") === -1){
							
							var win = open('', 'name', 'height='+window.height+', width='+ window.width);
							let  iframe = document.createElement('iframe');
							let title = document.createElement('title');

							title.appendChild(document.createTextNode("Guida rapida"));

							iframe.src = blobUrl;
							iframe.width = '100%';
							iframe.height = '100%';
							iframe.style.border = 'none';

							win.document.head.appendChild(title);
							win.document.body.appendChild(iframe);
							win.document.body.style.margin = 0;
								
							popup.style.display = "none";
							loader.style.display = "none";
							
						} else {
						let win = window.open(blobUrl + '#toolbar=0', "_blank");
						if (win) {
							
							win.addEventListener('load', function () {
								popup.style.display = "none";
								loader.style.display = "none";    
								setTimeout(() => {
									
									win.document.title = "Guida rapida";
									}, 1000);
								}); 
						} else {
							popup.style.display = "none";
							loader.style.display = "none"; 
							let errorMessage = "Errore durante l'apertura della finestra di anteprima, verificare che sia consentita l'apertura dei pop-up sul browser";
							console.warn(errorMessage);
							alert(errorMessage);
						}
					}
					}
				
				})
				.catch((error) => {
					popup.style.display = "none";
					loader.style.display = "none";
					console.error("Errore durante il recupero della guida rapida", error);
					alert(error);
				});
			}).catch((error) => {
				popup.style.display = "none";
				loader.style.display = "none";
				console.error("Errore durante il recupero della guida rapida", error);
				alert(error);
			});
                    
        
	  }
}

const app = new App();