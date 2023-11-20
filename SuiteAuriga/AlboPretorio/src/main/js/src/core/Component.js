/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
 * Classe base per tutti i componenti
 */
export class Component {
  async before_render() {}
  async render() {}
  async after_render() {}
}
