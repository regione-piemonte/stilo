/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import { Component, Url, html, Router } from '../../../core';
import { Link } from '../../../core/link/Link';
import './ItemMenu.css';

/**
 * Componente responsabile della visualizzazione
 * della singola voce di menu
 */
export default class ItemMenu extends Component {

  constructor(label = 'Item', icon = 'fa-place', link = '#', children = [], parentId = undefined) {
    super();
    this.label = label;
    this._icon = icon;
    this.link = link;
    this.id = 'link' + this.link.substr(this.link.lastIndexOf("/") + 1);
    this.children = children;
    this.parentId = parentId;
  }

  get id() {
    return this._id;
  }

  set id(id) {
    this._id = id;
  }

  set icon(icon) {
    if (icon == "") {
      this._icon = "";
    } else {
      this._icon = 'fa fa-' + icon;
    }

  }

  get icon() {
    return this._icon;
  }

  set link(link) {
    this._link = link.replace('/#', '');
  }

  get link() {
    return this._link;
  }

  get isActive() {
    return this.link === window.activeMenuItem;
  }

  render() {
    let labelLink = "<i class=" + this.icon + "></i>" + this.label + "<span class='sr-only'>(current)</span>";
    if(Router.isStorico()){
        this.hrefPrefix = "storico";
    } else if (Router.isStoricoAut()) {
        this.hrefPrefix = "alboriservato";
    } else {
        this.hrefPrefix = "albo";
    }
    // this.hrefPrefix = Router.isStorico() ? "storico" : "albo";
    let that = this;
    if (/\/(albo|storico)\/\d+/i.test(Url.getActiveRoute()) || /\/(albo|storico)\/filter+/i.test(Url.getActiveRoute()) ) {
      window.activeMenuItem=Url.getActiveRoute();
    }

    
    if (config.treeSideNavbar) {
      if (this.children.length > 0) {
        // setto null come href perchè in caso di tipologia doc con figli, non va mostrata la ricerca sulla tipologia doc padre
        return html`
        <li class="nav-item">
          <span class="caret" >
            <i class="icon-tree fa fa-chevron-circle-right text-info" id="${that.id + 'toggle'}" ${(that.parentId ? ' data-parentToggleId="' + that.parentId +'toggle"': '')}></i>
            ${new Link(labelLink, this.id, null, 'parent-nav-link nav-link' + (this.isActive ? ' active' : ''), null)}
          </span>
            <ul class="nested">
              ${this.children.map(
          item => html`${new ItemMenu(item.descrizione.toUpperCase(), "", '/' + that.hrefPrefix + '/' + item.idType, item.children, that.id)}`
        )}
            </ul>
        </li>
      `;
      } else {
        return html`
       
        <li class="nav-item">
        <span class="caretOff" >
        <i class="icon-tree fas fa-circle text-secondary" id="${that.id + 'toggle'}" ${(that.parentId ? ' data-parentToggleId="' + that.parentId +'toggle"': '')} ></i>
        ${new Link(labelLink, this.id, null, 'nav-link' + (this.isActive ? ' active' : ''), '#' + this.link)}
        </span>
        </li>
      `;
      }
    } else {
      return html`
    <li class="nav-item">
      ${new Link(labelLink, this.id, null, 'nav-link' + (this.isActive ? ' active' : ''), '#' + this.link)}
    </li>
  `;
    }

  }

}
