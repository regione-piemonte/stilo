/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import { html } from "../render/html";
import './Wait.css';

export class Wait extends Component {
    constructor() {
        super();
    }

    async render(){ 
      return await html`
      <div id="waitPopup" class="modal">
      <div id="loader">
      </div>
      </div>
      `
    }
}