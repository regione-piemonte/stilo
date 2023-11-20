/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import { Component, html, Router } from '../../../core';
import './navbar.css';

/**
 * Componente responsabile della visualizzazione
 * della navbar
 */
class Navbar extends Component {
  render() {
    if (Router.isStorico()) {
      this.title = "STORICO ALBO PRETORIO ";
      this.hrefPrefix = "storico";
    } else if (Router.isStoricoAut()) {
      this.title = "STORICO ALBO PRETORIO ";
      this.hrefPrefix = "alboriservato/home";
    } else {
      this.title = "ALBO PRETORIO ";
      this.hrefPrefix = "albo/home";
    }
    
    if(config && config.isMultiEnte){
      window.document.currentEnte = Router.getEnte();
      window.document.title = this.title + (window.document.currentEnte && window.document.currentEnte.nomeEnte ? "- " + window.document.currentEnte.nomeEnte : "");
    } else {
      window.document.title = this.title + (config && config.ente ? "- " + config.ente : "");
    }
    if (Router.isStoricoAut()) {
      return html`
      <nav
        class="navbar navbar-dark fixed-top bg-secondary flex-md-nowrap p-0 shadow"
      >
        <a class="navbar-brand" href="#/${this.hrefPrefix}" style="
        position: relative;
        padding-right: 15px;
        padding-left: 15px;
    ">${config && config.logo ? '<img src="'+window.location.pathname + config.logo+'" style="float:left;height:43px;">' : ''}&nbsp;${window.document.title}</a>
        <ul class="navbar-nav px-3">
          <li class="nav-item text-nowrap">
            <a style="color: rgba(255,255,255,.5);" href="#/${this.hrefPrefix}"> <i id="homeButton" class="fa fa-home"></i>
            <a style="color: rgba(255,255,255,.5);"><i id="logout" title="logout" class="fa fa-fas fa-power-off"></i></a>
          </a>
          </li>
        </ul>
      </nav>
    `;
    } else if(config && config.isMultiEnte) {
      return html`
      <nav
        class="navbar navbar-dark fixed-top bg-secondary flex-md-nowrap p-0 shadow"
      >
        <a class="navbar-brand" href="#/${this.hrefPrefix}" style="
        position: relative;
        padding-right: 15px;
        padding-left: 15px;
    ">${config && window.document.currentEnte && window.document.currentEnte.logoEnte ? '<img src="'+window.location.pathname + window.document.currentEnte.logoEnte+'" style="float:left;height:43px;">' : ''}&nbsp;${window.document.title}</a>
        <ul class="navbar-nav px-3">
          <li class="nav-item text-nowrap">
            <a style="color: rgba(255,255,255,.5);" href="#/${this.hrefPrefix}"> <i id="homeButton" class="fa fa-home"></i>
          </a>
          </li>
        </ul>
      </nav>
    `;
    } else {
    return html`
      <nav
        class="navbar navbar-dark fixed-top bg-secondary flex-md-nowrap p-0 shadow"
      >
        <a class="navbar-brand" href="#/${this.hrefPrefix}" style="
        position: relative;
        padding-right: 15px;
        padding-left: 15px;
    ">${config && config.logo ? '<img src="'+window.location.pathname + config.logo+'" style="float:left;height:43px;">' : ''}&nbsp;${window.document.title}</a>
        <ul class="navbar-nav px-3">
          <li class="nav-item text-nowrap">
            <a style="color: rgba(255,255,255,.5);" href="#/${this.hrefPrefix}"> <i id="homeButton" class="fa fa-home"></i>
          </a>
          </li>
        </ul>
      </nav>
    `;
    }
  }

}

// export dell'unica instanza del componente
export default new Navbar();
