/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
 * Classe bean dell'UO
 * 
 */
class UO {
  constructor(uoBean) {
    this.id = uoBean.id;
    this.displayValue = uoBean.descrizione;
  }
}

export default UO;
