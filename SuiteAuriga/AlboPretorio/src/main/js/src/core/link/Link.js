/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import { html } from "../render/html";

import '../../views/components/sidemenu/Sidemenu.css'

export class Link extends Component {
    constructor(label, id, onclickfun, classes, href = null) {
        super();
        this.label = label;
        if (id.indexOf('.') > 0 ) {
            this.id = id.substr(0, id.indexOf('.'));
        } else {
            this.id = id;
        }
        this.onclickfun = onclickfun;
        this.classes = classes;
        this.href = href;
    }

    before_render() {
            document.querySelector('#' + this.id);
        
    }

    render() {
        return html`
        <a id="${this.id}" ${this.href ? ' href="' + this.href +'"': ''} class="${this.classes}">${this.label}</a>`
    }

    after_render() {
        if (this.onclickfun){
            document.querySelector('#' + this.id).addEventListener('click', this.onclickfun);
        }
    }

}