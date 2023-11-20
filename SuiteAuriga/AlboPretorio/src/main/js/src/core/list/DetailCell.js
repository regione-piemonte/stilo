/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import { Router } from "../Router";
import { html } from "../render/html";
import { DetailCellRow } from "./DetailCellRow";

export class DetailCell extends Component {

    constructor(atto, hrefPrefix = undefined) {
        super()
        this.atto = atto;
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
        // this.hrefPrefix = hrefPrefix ? hrefPrefix : (Router.isStorico() ? 'storico' : 'albo');
    }

    async render() {
        
        let rowDetailHtml = await html`<td>
            ${new DetailCellRow("", "", "", "Oggetto", this.atto.oggetto)}
            <br>`;
            if(this.atto.dataAdozione){
                rowDetailHtml += await html`
                <br>
                ${new DetailCellRow("", "", "", "Data adozione", this.atto.dataAdozione)}`
            }
            if(this.atto.dataAtto){
                rowDetailHtml += await html`<br>
            ${new DetailCellRow("", "", "", "Data registrazione atto", this.atto.dataAtto)}
            `;
            }
            if(this.atto.flgImmediatamenteEsegiubile && this.atto.flgImmediatamenteEsegiubile == 1 && this.atto.esecutivoDal){
                rowDetailHtml += await html`<br>
                ${new DetailCellRow("", "", "", "Immediatamente eseguibile  Esecutivo dal", this.atto.esecutivoDal)}`
            } else if(this.atto.flgImmediatamenteEsegiubile && this.atto.flgImmediatamenteEsegiubile == 1 && !this.atto.esecutivoDal){
                rowDetailHtml += await html`<br>
                ${new DetailCellRow("", "", "", "Immediatamente eseguibile", " ")}`
            } else if(this.atto.flgImmediatamenteEsegiubile && this.atto.flgImmediatamenteEsegiubile == 0 && this.atto.esecutivoDal){
                rowDetailHtml += await html`<br>
                ${new DetailCellRow("", "", "", "Esecutivo dal", this.atto.esecutivoDal)}`
            }
            rowDetailHtml += await html`<br>
            ${new DetailCellRow("", "", "", "Pubblicato dal", this.atto.inizioPubbl)} ${new DetailCellRow("", "", "", " al", this.atto.finePubbl)}<br>`;
            if (config.showColonnalOrigine !== undefined && !config.showColonnalOrigine) {
                rowDetailHtml += await html`
                ${new DetailCellRow("", "", "", config.labelOrigin !== undefined && config.labelOrigin !== "" ? config.labelOrigin : "Origine", this.atto.richiedente)}<br>`;
            }
            

        if (this.showTipo) {
            rowDetailHtml += await html`${new DetailCellRow("", "", "", "Tipologia", this.atto.tipo)}<br>`;
        }

        // if (this.atto.stato && this.atto.stato.includes("RETTIFICA")) {
        //     rowDetailHtml += await html`${new DetailCellRow("a", "text-info", 'href="#/' + this.hrefPrefix + '/atto/' + this.atto.idUdRettifica + '"', "Stato pubblicazione", this.atto.stato)}<br>`;
        // } else {
            rowDetailHtml += await html`${new DetailCellRow("", "", "", "Stato pubblicazione", this.atto.stato)}<br>`;
        // }
        return rowDetailHtml;
    }


}