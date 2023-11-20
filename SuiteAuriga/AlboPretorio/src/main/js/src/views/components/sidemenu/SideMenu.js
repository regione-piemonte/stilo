/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import { Component, html, Router } from '../../../core';
import ItemMenu from '../item-menu/ItemMenu';
import AlboService from '../../../services/AlboService';

import './Sidemenu.css';

/**
 * SideMenu con la lista tipi atto e la ricerca avanzata
 */
class SideMenu extends Component {

  async before_render() {
    let that = this;
    this.isStorico = Router.isStorico();
    this.isStoricoAut = Router.isStoricoAut();
    if (this.isStorico || this.isStoricoAut) {
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
      if (config.treeSideNavbar) {
        if (window.document.tipiDocsTree) {
          this.tipiDocsTree = window.document.tipiDocsTree;
        } else {
          await AlboService.getTipiTree()
            .then(res => {
              window.document.tipiDocsTree = res;
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
   * Esegue il render del menù utilizzando il componente
   * della singola voce di menù
   */
  render() {
    if(Router.isStorico()){
        this.hrefPrefix = "storico";
    } else if (Router.isStoricoAut()) {
        this.hrefPrefix = "alboriservato";
    } else {
        this.hrefPrefix = "albo";
    }
    // this.hrefPrefix = Router.isStorico() ? "storico" : "albo";
    let that = this;
    
    if (config.treeSideNavbar) {
      if(window.document.isTipiTreeEmpty){
        return html`
          <nav
            id="side_menu"
            class="col-md-3 d-none d-md-block bg-light sidebar position-fixed sidebar-sticky mt-5 w-auto"
          >
          <ul class="nav flex-column">
          ${new ItemMenu('Ricerca', 'search', '/' + this.hrefPrefix + '/filter')}
          </ul>
            </nav>
          `;
      } else {
        return html`
          <nav
            id="side_menu"
            class="col-md-3 d-none d-md-block bg-light sidebar position-fixed sidebar-sticky mt-5 w-auto"
          >
          <ul class="nav flex-column">
          ${new ItemMenu('Ricerca avanzata', 'search', '/' + this.hrefPrefix + '/filter')}
              ${this.tipiDocsTree.map(
        item => html`
                  ${new ItemMenu(item.descrizione.toUpperCase(), "", '/' + that.hrefPrefix + '/' + item.idType, item.children)}
                `
      )}
        </ul>
          </nav>
        `;
      }
    } else {
      if(window.document.isTipiTreeEmpty){
        return html`
          <nav
            id="side_menu"
            class="col-md-3 d-none d-md-block bg-light sidebar position-fixed sidebar-sticky mt-5 w-auto"
          >
            <ul class="nav flex-column">
              ${new ItemMenu('Ricerca', 'search', '/' + this.hrefPrefix + '/filter')}
            </ul>
          </nav>
        `;  
      } else {
        return html`
          <nav
            id="side_menu"
            class="col-md-3 d-none d-md-block bg-light sidebar position-fixed sidebar-sticky mt-5 w-auto"
          >
            <ul class="nav flex-column">
              ${new ItemMenu('Ricerca avanzata', 'search', '/' + this.hrefPrefix + '/filter')}
              ${this.tipiDocs.map(
        item => html`
                  ${new ItemMenu(item.descrizione.toUpperCase(), "", '/' + that.hrefPrefix + '/' + item.idType)}
                `
      )}
            </ul>
          </nav>
        `;
      }
    }

  }

}

// export dell'unica instanza del componente
export default new SideMenu();