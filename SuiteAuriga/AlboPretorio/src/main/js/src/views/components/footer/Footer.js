/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import { Component, html } from '../../../core';

/**
 * Componente responsabile della visualizzazione
 * del footer
 */
class Footer extends Component {
  render() {
    return html`
      <fotter class="footer fixed-bottom bg-dark py-3">
        <div class="footer-copyright text-center text-white">
          © 2019 Copyright:
          <a href="https://eng.it"> Eng</a>
        </div>
      </fotter>
    `;
  }
}

// export dell'unica instanza del componente
export default new Footer();
