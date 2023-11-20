/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import { html } from "../render/html";
import './Filter.css';
import { Label } from "../elements/Label";
import $ from 'jquery';
import datepickerFactory from 'jquery-datepicker';
import datepickerITFactory from 'jquery-datepicker/i18n/jquery.ui.datepicker-it';


export class Filter extends Component {
    constructor(tipiDoc = [], listaUO = [], onlyFullText = false, items) {
        super();
        this.tipiDoc = tipiDoc;
        this.listaUO = listaUO;
        this.events = new Map();
        this.onlyFullText = onlyFullText;
        this.items = items;
    }

    on(eventName, fun) {
        this.events.set(eventName, fun);
    }

    render() {
        // Just pass your jquery instance and you're done
        if ($.datepicker === undefined) {
            datepickerFactory($);
            datepickerITFactory($);
        }
            $(function() {
                $('.datepicker').datepicker({
                    prevText: '<',
                    nextText: '>',
                    showOn: 'focus'
                });
            });
        if (this.onlyFullText){
            return html`<form id="filter" class="col-12">
            <div class="row">
                <div class="large-12 columns" style="display:flex;padding-left: 0px;">   
                    <input type="text" class="text" maxlength="700" size="40" placeholder="immetti qui le parole da ricercare: la ricerca viene effettuata anche sul contenuto dei file degli atti in pubblicazione" id="FULL_TEXT" name="FULL_TEXT" value="${window.fullText ? window.fullText : ''}" style="vertical-align:middle; height:60%;">  
                    <span>&nbsp;</span>            
                    <input type="text" name="TIPO_DOC" id="TIPO_DOC" value="${this.tipiDoc}" hidden> 
                    <input type="submit" class="btn btn-info" value="Cerca" style="height: fit-content; margin-top:8px; margin-bottom: 8px; height: 60%; line-height: 1;">
                </div> 
            </div>       
            </form>
            <div class="row"> </div>`;
        } else {


            // let formHtml = `<form id="filter" class="col-12">`;
            
            // for (let index = 0; index < this.items.length; index++) {
            //     const element = this.items[index];
            //     formHtml += await html`<div class="row">${new Label(element,)}`;

            //     switch (this.item.type) {
            //         case input:
            //             formHtml += await html`${new Input(element)}`;
            //             break;
            //         case select:
            //             if (element.name == "TIPO_DOC"){
            //                 formHtml += await html`${new Select(element, "Tutte le tipoligie", this.tipiDoc)}`;
            //             } else if (element.name == "RICHIEDENTE"){
            //                 formHtml += await html`${new Select(element, "Tutte le strutture", this.listaUO)}`;
            //             } else {
            //                 formHtml += await html`${new Select(element, "Tutte le opzioni", [])}`;
            //             }
            //             break; 
            //         default:
            //             break;
            //     }
            //     formHtml += await html`</div>`;
            // }


        return html`<form id="filter" class="col-12">
            <div class="row">
                ${new Label("FULL_TEXT", "Cerca", "large-2 columns", "text-align: end;")}
                <div class="large-10 columns">   
                    <input type="text" class="text" maxlength="700" size="40" placeholder="immetti qui le parole da ricercare: la ricerca viene effettuata anche sul contenuto dei file degli atti in pubblicazione" id="FULL_TEXT" name="FULL_TEXT" value="" style="vertical-align:middle;">  
                </div> 
            </div>       
            
            <div class="row">
                ${new Label("TIPO_DOC", "Tipo di atto", "large-2 columns", "text-align: end;")}
                <div class="large-10 columns">  
                    <select name="TIPO_DOC" id="TIPO_DOC" class="liste selectFissa"> 
                        <option value="">Tutte le tipologie</option>
                        ${this.tipiDoc.map(
                            tipoDoc => html`<option value="${tipoDoc.idType}">${tipoDoc.descrizione}</option>`
                        )}; 
                </select></div>
            </div>

            <div class="row">
                ${new Label("RICHIEDENTE", "Struttura interna", "large-2 columns", "text-align: end;")}
                <div class="large-10 columns">  
                    <select name="RICHIEDENTE" id="RICHIEDENTE" class="liste selectFissa"> 
                        <option value="">Tutte le strutture</option>
                        ${this.listaUO.map(
                            uo => html`<option value="${uo.id}">${uo.displayValue}</option>`
                        )}; 
                    </select>
                </div>  
            </div> 
            
            <div class="row">
                ${new Label("NUMERO", "Atto N°", "large-2 columns", "text-align: end;")} 
                <div class="large-2 columns">  
                    <input type="text" class="text" id="NUMERO" name="NUMERO" value="" style="vertical-align:middle;">  
                </div>
                ${new Label("OGGETTO", "Oggetto", "large-1 columns", "text-align: end;")} 
                <div class="large-6 columns">   
                    <input type="text" class="text" maxlength="700" size="40" id="OGGETTO" name="OGGETTO" value="" style="vertical-align:middle;">  
                </div> 
            </div> 
    
            <div class="row">
            ${new Label("DATA_DA", "Data atto dal", "large-2 columns", "text-align: end;")}    
            <div class="large-3 columns">  
                <input type="text" class="text datepicker" pattern="[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}" placeholder="GG/MM/AAAA" maxlength="10" size="10" id="DATA_DA" name="DATA_DA" value="" style="text-align:middle; margin-right:2em;">  
            </div>  
            ${new Label("DATA_A", "al", "large-1 columns", "text-align: end;")}
            <div class="large-3 columns">  
            <input type="text" class="text datepicker" pattern="[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}" placeholder="GG/MM/AAAA" maxlength="10" size="10" id="DATA_A" name="DATA_A" value="" style="text-align:middle;">  
            </div>
            </div>  

            <div class="row">  
            ${new Label("INIZIO_PUBBL_DA", "Inizio pubblicazione dal", "large-2 columns", "text-align: end;")} 
            <div class="large-3 columns">  
            <input type="text" class="text datepicker" pattern="[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}" placeholder="GG/MM/AAAA" maxlength="10" size="10" id="INIZIO_PUBBL_DA" name="INIZIO_PUBBL_DA" value="" style="text-align:middle; margin-right:2em;">  
            </div>  
            <div class="large-1 columns" style="text-align: end;">   
            <label for="INIZIO_PUBBL_A" style="padding-right:1.5em;font-size:smaller;">al</label>  
            </div>  
            <div class="large-3 columns">  
            <input type="text" class="text datepicker" pattern="[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}" placeholder="GG/MM/AAAA" maxlength="10" size="10" id="PUBBL_A" name="INIZIO_PUBBL_A" value="" style="text-align:middle;">  
            </div>
            </div>

            <div class="row">  
            <div class="large-2 columns" style="text-align: end;">  
            <label for="FINE_PUBBL_DA" style="padding-right:0.5em;font-size:smaller;">Termine pubblicazione dal</label>  
            </div>  
            <div class="large-3 columns">  
            <input type="text" class="text datepicker" pattern="[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}" placeholder="GG/MM/AAAA" maxlength="10" size="10" id="FINE_PUBBL_DA" name="FINE_PUBBL_DA" value="" style="text-align:middle; margin-right:2em;">  
            </div>  
            <div class="large-1 columns" style="text-align: end;">   
            <label for="FINE_PUBBL_A" style="padding-right:1.5em;font-size:smaller;">al</label>  
            </div>  
            <div class="large-3 columns">  
            <input type="text" class="text datepicker" pattern="[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}" placeholder="GG/MM/AAAA" maxlength="10" size="10" id="FINE_PUBBL_A" name="FINE_PUBBL_A" value="" style="text-align:middle;">  
            </div>
            </div>

            <div class="row">  
                <div class="large-2 columns">
                </div>
                <div class="custom-control custom-checkbox" style="margin:0 0 0.88889rem 0;">
                    <input type="checkbox" class="custom-control-input" style="background-color:#138496;" id="NASCONDI_ANNULLATE" name="NASCONDI_ANNULLATE" checked>
                    <label class="custom-control-label" for="NASCONDI_ANNULLATE">Nascondi pubblicazioni annullate</label>
                </div>
            </div>
        <input type="submit" class="btn btn-info" value="Cerca"></form>`;
                            }
    }

    handleSubmit(event) {
        event.preventDefault();
        const successFun = this.events.get('success');
        const errorFun = this.events.get('error');
        const searchFun = this.events.get('search');
        searchFun(event.target.elements)
            .then(res => successFun(res))
            .catch((error) => errorFun(error));
    }

    after_render() {
        document.getElementById('filter').addEventListener('submit', this.handleSubmit.bind(this));
    }
}