/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
 * Classe bean del Tipo documento
 * 
 */
class TipoDocumento {
    constructor(tipoDocumento) {
     this.idType = tipoDocumento.idType;
     this.descrizione = tipoDocumento.descrizione;
     this.children = tipoDocumento.children
    }
  }
  
  export default TipoDocumento;
  