/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import { html } from "../render/html";

export class DetailCellRow extends Component {
    constructor(elementHtml = "", classes = "", attributes = "", title = "", value = "") {
        super();
        this.elementHtml = elementHtml;
        this.classes = classes;
        this.attributes = attributes;
        this.title = title;
        this.value = value;
    }
    
    render() {
        let content = "";
        if (this.elementHtml && this.elementHtml != "") {
            content = '<' + this.elementHtml;
            if (this.classes & this.classes != "") {
                content += ' class="' + this.classes + '" ';
            }

            if (this.attributes && this.attributes != "") {
                content += this.attributes; 
            }
            content += '>' + this.value +'</' + this.elementHtml + '>';
        }
        if (content == "") {
            content = this.value;
        }
        return  html`<strong style="color:lightslategrey;">${this.title} </strong>${content}`;
    }
}