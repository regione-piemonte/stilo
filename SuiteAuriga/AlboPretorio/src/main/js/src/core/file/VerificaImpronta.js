/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import { html } from "../render/html";
import './VerificaImpronta.css';


export class VerificaImpronta extends Component {
    constructor(titolo, id) {
        super();
        this.titolo = titolo; 
        this.id = id;
    }

    async render(){ 
      return await html`
      <div id="${this.id}">L'impronta risulta conforme al documento</div>
      `
    }
}