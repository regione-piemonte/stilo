/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import { Component, html, Router } from '../../../core';
import { PageTitle } from '../../components/page';
import AlboService from '../../../services/AlboService';
import AlboList from '../albo/AlboList';
import './Home.css';
import X2JS from 'x2js';
import filtroAlbo from '../../../models/FiltroAlbo';
import ItemMenu from '../../components/item-menu/ItemMenu';
import Login from '../login/Login';
import { Wait } from '../../../core/file/Wait';
/**
 * Componente responsabile della visualizzazione
 * della pagina principale dell'app
 */
class Home extends Component {


  async before_render() {
    let that = this;
    this.isStorico = Router.isStorico();
    this.isStoricoAut = Router.isStoricoAut();
    this.idRouter = Router.getId();

    if (this.isStorico) {
      this.titleString = 'STORICO ATTI';
      this.prefixHref = 'storico';
      if (config.treeSideNavbar) {
        if (window.document.storicoTipiDocsTree) {
          this.tipiDocsTree = window.document.storicoTipiDocsTree;
        } else {
          await AlboService.getTipiTree()
            .then(res => {
              window.document.storicoTipiDocsTree = res;
              that.tipiDocsTree = res;
              if (typeof res == 'undefined' || res.length == 0) {
                window.document.isTipiTreeEmpty=true;
              } else {
                window.document.isTipiTreeEmpty=false;
              }
            })
            .catch((error) => {
              that.tipiDocsTree = [];
              console.error("Errore durante il recupero dei tipi", error);
              alert(error)
            });
        }
      } else {

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
      }
    } else if (this.isStoricoAut) {
      this.titleString = 'STORICO ATTI';
      this.prefixHref = 'alboriservato';
      if (config.treeSideNavbar) {
        if (window.document.storicoTipiDocsTree) {
          this.tipiDocsTree = window.document.storicoTipiDocsTree;
        } else {
          await AlboService.getTipiTree()
            .then(res => {
              window.document.storicoTipiDocsTree = res;
              that.tipiDocsTree = res;
              if (typeof res == 'undefined' || res.length == 0) {
                window.document.isTipiTreeEmpty=true;
            } else {
                window.document.isTipiTreeEmpty=false;
              }
            })
            .catch((error) => {
              that.tipiDocsTree = [];
              console.error("Errore durante il recupero dei tipi", error);
              alert(error)
            });
        }
      } else {

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
      }
    } else {
      this.titleString = 'ATTI IN PUBBLICAZIONE';
      this.prefixHref = 'albo';

      if (config.treeSideNavbar) {
        if (window.document.tipiDocsTree) {
          this.tipiDocsTree = window.document.tipiDocsTree;
        } else {
          await AlboService.getTipiTree()
            .then(res => {
              window.document.tipiDocsTree = res;
              that.tipiDocsTree = res;
            })
            .catch((error) => {
              that.tipiDocsTree = [];
              console.error("Errore durante il recupero dei tipi", error);
              alert(error)
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
              console.error("Errore durante il recupero dei tipi", error);
              alert(error)
            });
        }
      }
    }

  }

  /**
   * Render della home composta dalla lista dei post
   */
  render() {
    let that = this;

    if (config.loadListOnOpen && !(this.isStorico || this.isStoricoAut) ) {
    // se bisogna caricare la lista nella home non serve caricare le tipologie, ramo else
      return html`
		<section class="section">
		<div class="row">
		${new PageTitle(this.titleString)}
		</div>
		<div class="row">
		<div class="col-12">
		<h3 class="text-secondary">Caricamento dati in corso...</h3>
	    </div>
		</div>
		</section>
    ${new Wait()}
		`;
    } else if (this.isStoricoAut) {
      if (!(sessionStorage.getItem("islogged")=="logged")) {
        return html`
        ${new Login(config.username, config.password)}
        ${new Wait()}
      `;
      } else {
        if (config.treeSideNavbar) {
          return html`
          <section class="section">
            <div class="row">
              ${new PageTitle(this.titleString)}
            </div>
            <div class="row">
            <div class="large-1 columns">  
            </div>
            <div class="large-6 columns">  
            <a href="#/${this.prefixHref}/filter" class="nav-link"><h3 class="text-info">Ricerca avanzata</h3></a>
            </div>
            </div>
            <div class="row">
            <div class="large-1 columns">  
            </div>
            <div class="large-6 columns">
            <ul>
            ${this.tipiDocsTree.map(
              item => html`
                        ${new ItemMenu(item.descrizione.toUpperCase(), "", '/' + that.prefixHref + '/' + item.idType, item.children)}
                      `
            )}
            </ul>
            </div>
            </div>
  
          </section>
          ${new Wait()}
        `;
        } else {
          return html`
          <section class="section">
            <div class="row">
              ${new PageTitle(this.titleString)}
            </div>
            <div class="row">
            <div class="large-1 columns">  
            </div>
            <div class="large-6 columns">  
            <a href="#/${this.prefixHref}/filter" class="nav-link"><h3 class="text-info">Ricerca avanzata</h3></a>
            </div>
            </div>
            <div class="row">
            <div class="large-1 columns">  
            </div>
            <div class="large-6 columns">
            <ul>
            ${this.tipiDocs.map(
         el => html`
                <li><a href="#/${that.prefixHref}/${el.idType}" class="nav-link">${el.descrizione}</a></li>
              `
       )}
            </ul>
            </div>
            </div>
  
          </section>
          ${new Wait()}
        `;
        }
      }
    }else {

      if (config.treeSideNavbar) {
        return html`
        <section class="section">
          <div class="row">
            ${new PageTitle(this.titleString)}
          </div>
          <div class="row">
          <div class="large-1 columns">  
          </div>
          <div class="large-6 columns">  
          <a href="#/${this.prefixHref}/filter" class="nav-link"><h3 class="text-info">Ricerca avanzata</h3></a>
          </div>
          </div>
          <div class="row">
          <div class="large-1 columns">  
          </div>
          <div class="large-6 columns">
          <ul>
          ${this.tipiDocsTree.map(
            item => html`
                      ${new ItemMenu(item.descrizione.toUpperCase(), "", '/' + that.prefixHref + '/' + item.idType, item.children)}
                    `
          )}
          </ul>
          </div>
          </div>

        </section>
        ${new Wait()}
      `;
      } else {
        return html`
        <section class="section">
          <div class="row">
            ${new PageTitle(this.titleString)}
          </div>
          <div class="row">
          <div class="large-1 columns">  
          </div>
          <div class="large-6 columns">  
          <a href="#/${this.prefixHref}/filter" class="nav-link"><h3 class="text-info">Ricerca avanzata</h3></a>
          </div>
          </div>
          <div class="row">
          <div class="large-1 columns">  
          </div>
          <div class="large-6 columns">
          <ul>
          ${this.tipiDocs.map(
       el => html`
              <li><a href="#/${that.prefixHref}/${el.idType}" class="nav-link">${el.descrizione}</a></li>
            `
     )}
          </ul>
          </div>
          </div>

        </section>
        ${new Wait()}
      `;
      }
    
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

  getAtti() {
    this.x2js = new X2JS({ useDoubleQuotes: true, escapeMode: false });
    let filtersXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?\><filtriAlboPretorio>";

    if(document.getElementById("waitPopup") && document.getElementById("loader")){
        let popup = document.getElementById("waitPopup");
        popup.style.display = "block";
        let loader = document.getElementById("loader");
        loader.style.display = "block";
      }

    filtersXml += "<filtroAlbo>";
    filtersXml += this.x2js.js2xml(new filtroAlbo("TIPO_DOC", ""));
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

  async after_render() {
    if (config.loadListOnOpen && !(this.isStorico || this.isStoricoAut)) {

      let lista = AlboList;
      await this.getAtti()
        .then(res => {
          lista.attiList = res;
          lista.idDocType = undefined;
          if (Router.isStorico()) {
            Router.navigate('/storico/list');
          } else if(Router.isStoricoAut()) {
            Router.navigate('/alboriservato/list');
          } else {
            Router.navigate('/albo/list');
          }
        })
        .catch((error) => {
          console.error("Errore durante il recupero degli atti", error);
          alert(error);
          this.goToRicercaAvanzata();

        });
    }
  }



}

// export dell'unica instanza della pagina
export default new Home();
