/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import './ImprontaPopup.css';
import { html } from "../render/html";
import { VerificaImpronta } from "./VerificaImpronta";
import { IconButton } from "../button/IconButton";


export class ImprontaPopup extends Component {
    constructor(titolo, id, impronta, algoritmo, encoding) {
        super();
        this.titolo = titolo; 
        this.id = id;
        this.impronta = impronta;
        this.algoritmo = algoritmo;
        this.encoding = encoding;
    }

    async render(){ 
      let verimprontafun = this.verificaImpronta.bind(this);
      return await html`<div id="${this.id}" class="modal">
        <div class="impronta-modal-content">
          <span id="close${this.id}" class="close">&times;</span><h4 class="text-info">Impronta ${this.titolo}&nbsp;</h4>
          <p>Impronta File: ${this.impronta}</p>
          <p>Algoritmo : ${this.algoritmo}</p>
          <p>Encoding : ${this.encoding}</p>
          <p>Clicca per verificare l'attendibilità dell'impronta ${new IconButton("Verifica validità impronta", "verificaImpronta" + this.id, verimprontafun, "check-circle")}
        ${new VerificaImpronta(this.titolo, "ver"+this.id)} </p>
        </div>
        </div>
      `
    }

    close(){
        var modal = document.getElementById(this.id);
        modal.style.display = "none";
    }
    verificaImpronta(){
      let x = document.getElementById("ver"+this.id);
      x.className = "show";
      setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
    }
}