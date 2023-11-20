/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
 * Classe bean dell'atto per lista e dettaglio
 * 
 */
class Atto {
    constructor(atto) {
      this.idUD = atto.idUd;
      this.nroPubbl = atto.pubblicazioneNumero;
      this.attoNumero = atto.attoNumero;
      this.dataAtto = atto.dataAtto? new Date(atto.dataAtto).toLocaleDateString() : "";
      this.inizioPubbl = atto.dataInizioPubbl;
      this.finePubbl = atto.dataFinePubbl;
      this.richiedente = atto.richiedente;
      this.oggetto = atto.oggetto;
      this.tipo = atto.tipo;
      this.stato = atto.statoPubblicazione;
      this.idUdRettifica = atto.idUdRettifica;
      this.motivoAnn = atto.motivoAnnullamento;
      this.dataPubbl = atto.tsPubblicazione ? new Date(atto.tsPubblicazione).toLocaleDateString() + " " + new Date(atto.tsPubblicazione).toLocaleTimeString() : "";
      this.formaPubblicazione = atto.formaPubblicazione;
      this.esecutivoDal = atto.esecutivoDal ? new Date(atto.esecutivoDal).toLocaleDateString() : "";
      this.dataAdozione = atto.dataAdozione ? new Date(atto.dataAdozione).toLocaleDateString() : "";
      this.flgImmediatamenteEsegiubile = atto.flgImmediatamenteEsegiubile;
      this.idPubblicazione = atto.idPubblicazione;
      this.allegati = [];
      if (atto.filesAlbo){
        if (!(atto.filesAlbo instanceof Array)){
          atto.filesAlbo = [atto.filesAlbo];
        }
        for (let index = 0; index < atto.filesAlbo.length; index++) {
          const file = atto.filesAlbo[index];
          if (file.flgPrimario == '1') {
            this.filePrimario = file;
          } else {
            this.allegati.push(file);
          }
          
        };
      }
    }
  }
  
  export default Atto;
  