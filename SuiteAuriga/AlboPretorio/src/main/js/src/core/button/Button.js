/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import { Router } from "../Router";
import { html } from "../render/html";

export class Button extends Component {
    constructor(path, label, id) {
        super();
        this.path = path;
        this.label = label;
        if (id.indexOf('.') > 0 ) {
            this.id = id.substr(0, id.indexOf('.'));
        } else {
            this.id = id;
        }
    }

    async render() {
        return await html`
        <button class="btn btn-secondary" id='${this.id}'>${this.label}</button>`
    }
    
    async navigateToPath(event) {
        if(this.path.includes("backlist")){
            
            let popup = document.getElementById("waitPopup");
            popup.style.display = "block";
            let loader = document.getElementById("loader");
            loader.style.display = "block";
            
        }
       
        setTimeout(()=>{
            Router.navigate(this.path);
        }, 500);
    }

    after_render() {
        document.querySelector('#' + this.id).addEventListener('click', this.navigateToPath.bind(this));
    }

}