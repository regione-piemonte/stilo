/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import { Component, html } from '../../../core';

/**
 * Componente responsabile della visualizzazione
 * del title delle pagine
 */
class PageTitle extends Component {
  constructor(title, cssClass = 'col-12') {
    super();
    this.title = title;
    this.cssClass = cssClass;
  }

  render() {
    return html`
      <div class="${this.cssClass}">
        <h1 class="text-secondary">${this.title}</h1>
      </div>
    `;
  }
}

// export del componente
export { PageTitle };
