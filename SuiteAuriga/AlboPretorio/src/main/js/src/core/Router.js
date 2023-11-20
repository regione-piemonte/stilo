/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import { Url } from './Url';

/**
 * Classe che permette di gestire il routing del'app
 */
class _Router {

  init(routes, onChange) {
    this.routes = routes;
    this.onChange = onChange;
    this.handleHashChange();
  }

  /**
   * Handler dell'evento di cambio dell'hash
   * della barra degli indirizzi
   */
  handleHashChange() {
    window.addEventListener('hashchange', this.onChange);
  }

  /**
   * Restituisce la pagina da mostrare in base all'url
   * presente sulla barra degli indirizzi
   */
  getPage() {
    // Ottiene l'url presente sulla barra degli indirizzi
    this.request = Url.parseRequest();

    // Analizza l'url e constrolla se è presente il parametro id
    let asd = this.request.id
    ? isNaN(parseInt(this.request.id, 10))
      ? '/' + this.request.id
      : '/:id'
    : '';
    let banana = isNaN(parseInt(this.request.id, 10));
    let ciao = this.request.id;
    let parsedURL =
      (this.request.resource ? '/' + this.request.resource : '/') +
      (this.request.id
        ? isNaN(parseInt(this.request.id, 10))
          ? '/' + this.request.id
          : '/:id'
        : '') +
      (this.request.verb ? '/' + ':idPubblicazione' : '');

    // Ottiene la pagina dalla url presente sulle rotte
    // se la rotta non esiste ritorna null
    let notFound;
    if (this.request.resource != 'albo' && this.request.resource != 'storico' && this.request.resource != 'alboriservato') {
      notFound = '/notfound';
    } else {
      notFound = '/' + this.request.resource + '/notfound'
    }

    if(this.request.resource == 'storico' && parsedURL && parsedURL == '/storico'){
      parsedURL='/storico/filter'
    }
    if(this.request.resource == 'alboriservato' && parsedURL && (parsedURL == '/alboriservato' || parsedURL == '/alboriservato/home' )){
      parsedURL='/storico/filter'
    }
    return this.routes[parsedURL] ? this.routes[parsedURL] : this.routes[notFound];
  }

  /**
   * Restituisce l'id della risorsa nel caso di rotta con :id
   */
  getId() {
    return this.request.id;
  }

    /**
   * Restituisce l'id di pubblicazione della risorsa nel caso di rotta con :idPubblicazione
   */
     getIdPubblicazione() {
      return this.request.verb;
    }

  /**
   * Restituisce la request 
   */
  getRequest(){
    return this.request;
  }

  /**
   * Restituisce la risorsa 
   */
  getResource(){
    return this.request.resource;
  }

  isStorico(){
    return this.request.resource.lastIndexOf("storico") >= 0;
  }

  isStoricoAut(){
    return this.request.resource.lastIndexOf("alboriservato") >= 0;
  }

  getName(name){
    if(name=(new RegExp('[?&]'+encodeURIComponent(name)+'=([^&]*)')).exec(location.search))
       return decodeURIComponent(name[1]);
 }

  getCodEnte(){
    let url = window.location.href;
    let codiceEnte = this.getName('codEnte');
    var ente = ' ';
    if(window.config.elencoEnti && window.config.elencoEnti.length>0){
      ente = window.config.elencoEnti.find(element => {
        return element.codEnte==codiceEnte;
    });
    }
    if(this.isStorico()) {
      return ente.codEnte+"_STORICO";
    } else if(this.isStoricoAut()) {
      return ente.codEnte+"_STORICO_AUT";
    } else {
      return ente.codEnte;
    }
  }

  getEnte(){
    let url = window.location.href;
    var ente = ' ';
    if(window.config.elencoEnti && window.config.elencoEnti.length>0){
      ente = window.config.elencoEnti.find(element => {
        return url.includes(element.codEnte);
    });
    }
    return ente;
  }
  /**
   * Naviga sulla rotta passata come parametro
   * @param {*} route
   */
  navigate(route) {
    let forceReload = false;
    if (window.location.hash.indexOf(route) >0){
      forceReload = true;
    }
    window.location.assign(`#${route}`);
    if (forceReload){
      this.onChange();
    }
  }
}

// export dell'unica instanza del router
const Router = new _Router();
export { Router };
