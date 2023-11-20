/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.log4j.Logger;

import it.csi.siac.ricerche.svc._1.RicercaAccertamento;
import it.csi.siac.ricerche.svc._1.RicercaCapitoloEntrataGestione;
import it.csi.siac.ricerche.svc._1.RicercaCapitoloUscitaGestione;
import it.csi.siac.ricerche.svc._1.RicercaDettaglioSoggetti;
import it.csi.siac.ricerche.svc._1.RicercaDocumentoEntrata;
import it.csi.siac.ricerche.svc._1.RicercaDocumentoSpesa;
import it.csi.siac.ricerche.svc._1.RicercaEstesaLiquidazioni;
import it.csi.siac.ricerche.svc._1.RicercaEstesaOrdinativiSpesa;
import it.csi.siac.ricerche.svc._1.RicercaImpegno;
import it.csi.siac.ricerche.svc._1.RicercaLiquidazione;
import it.csi.siac.ricerche.svc._1.RicercaOrdinativoIncasso;
import it.csi.siac.ricerche.svc._1.RicercaOrdinativoSpesa;
import it.csi.siac.ricerche.svc._1.RicercaProvvisoriDiCassa;
import it.csi.siac.ricerche.svc._1.RicercaSinteticaSoggetti;

public class ValidazioneRequestRicerche {
	
	private static final Logger logger = Logger.getLogger(ValidazioneRequestRicerche.class);
	
	/*
	 * Metodo per controllare parametri di input obbligatori per ricercaAccertamento
	 */
	public static boolean checkInputRicercaAccertamento(RicercaAccertamento input) {
		boolean result = true;
		
		// controllo codice fruitore obbligatorio
		if (input.getCodiceFruitore() == null) {
			result = false;
			
			logger.error("codiceFruitore obbligatorio non può essere null");
		}
		
		// controllo codice ente obbligatorio
		if (input.getCodiceEnte() == null) {
			result = false;
			
			logger.error("codiceEnte obbligatorio non può essere null");
		}
		
		// controllo anno bilancio obbligatorio
		if (input.getAnnoBilancio() == null) {
			result = false;
			
			logger.error("annoBilancio obbligatorio non può essere null");
		}
		
		// controllo anno provvedimento obbligatorio se numero accertamento non valorizzato
		if (input.getNumeroAccertamento() == null && input.getAnnoProvvedimento() == null) {
			result = false;
			
			logger.error("annoProvvedimento obbligatorio se numeroAccertamento null");
		}
		
		// controllo numero provvedimento obbligatorio se numero accertamento non valorizzato
		if (input.getNumeroAccertamento() == null && input.getNumeroProvvedimento() == null) {
			result = false;
			
			logger.error("numeroProvvedimento obbligatorio se numeroAccertamento null");
		}
		
		// controllo tipo codice provvedimento obbligatorio se numero accertamento non valorizzato
		if (input.getNumeroAccertamento() == null && input.getCodiceTipoProvvedimento() == null) {
			result = false;
			
			logger.error("codiceTipoProvvedimento obbligatorio se numeroAccertamento null");
		}
		
		// controllo anno accertamento obbligatorio se numero provvedimento non valorizzato
		if (input.getNumeroProvvedimento() == null && input.getAnnoAccertamento() == null) {
			result = false;
			
			logger.error("annoAccertamento obbligatorio se numeroProvvedimento null");
		}
		
		// controllo dati paginazione
		//result = checkPaginazione(input.getNumeroElementiPerPagina(), input.getNumeroPagina());
		
		return result;
	}
	
	/*
	 * Metodo per controllare parametri di input obbligatori per ricercaImpegno
	 */
	public static boolean checkInputRicercaImpegno(RicercaImpegno input) {
		boolean result = true;
		
		// controllo codice fruitore obbligatorio
		if (input.getCodiceFruitore() == null) {
			result = false;
			
			logger.error("codiceFruitore obbligatorio non può essere null");
		}
		
		// controllo codice ente obbligatorio
		if (input.getCodiceEnte() == null) {
			result = false;
			
			logger.error("codiceEnte obbligatorio non può essere null");
		}
		
		// controllo anno bilancio obbligatorio
		if (input.getAnnoBilancio() == null) {
			result = false;
			
			logger.error("annoBilancio obbligatorio non può essere null");
		}
		
		// controllo anno provvedimento obbligatorio se numero impegno non valorizzato
		if (input.getNumeroImpegno() == null && input.getAnnoProvvedimento() == null) {
			result = false;
				
			logger.error("annoProvvedimento obbligatorio se numeroImpegno null");
		}
		
		// controllo numero provvedimento obbligatorio se numero provvedimento non valorizzato
		if (input.getNumeroImpegno() == null && input.getNumeroProvvedimento() == null) {
			result = false;
				
			logger.error("numeroProvvedimento obbligatorio se numeroImpegno null");
		}
		
		// controllo tipo codice provvedimento obbligatorio se numero impegno non valorizzato
		if (input.getNumeroImpegno() == null && input.getCodiceTipoProvvedimento() == null) {
			result = false;
			
			logger.error("codiceTipoProvvedimento obbligatorio se numeroImpegno null");
		}
		
		// controllo anno impegno obbligatorio se numero provvedimento non valorizzato
		if (input.getNumeroProvvedimento() == null && input.getAnnoImpegno() == null) {
			result = false;
			
			logger.error("annoImpegno obbligatorio se numeroProvvedimento null");
		}
		
		// controllo dati paginazione
		//result = checkPaginazione(input.getNumeroElementiPerPagina(), input.getNumeroPagina());
		
		return result;
	}
	
	/*
	 * Metodo per controllare parametri di input obbligatori per ricercaCapitoloEntrataGestione
	 */
	public static boolean checkInputRicercaCapitoloEntrataGestione(RicercaCapitoloEntrataGestione input) {
		boolean result = true;
		
		// controllo codice fruitore
		if (input.getCodiceFruitore() == null) {
			result = false;
			
			logger.error("codiceFruitore obbligatorio non può essere null");
		}
		
		// controllo codice ente
		if (input.getCodiceEnte() == null) {
			result = false;
			
			logger.error("codiceEnte obbligatorio non può essere null");
		}
		
		// controllo anno bilancio obbligatorio
		if (input.getAnnoBilancio() == null) {
			result = false;
			
			logger.error("annoBilancio obbligatorio non può essere null");
		}
		
		// controllo numero capitolo obbligatorio
		if (input.getNumeroCapitolo() == null) {
			result = false;
			
			logger.error("numeroCapitolo obbligatorio non può essere null");
		}
		
		// controllo numero articolo obbligatorio
		if (input.getNumeroArticolo() == null) {
			result = false;
			
			logger.error("numeroArticolo obbligatorio non può essere null");
		}
		
		return result;
	}
	
	/*
	 * Metodo per controllare parametri di input obbligatori per ricercaCapitoloUscitaGestione
	 */
	public static boolean checkInputRicercaCapitoloUscitaGestione(RicercaCapitoloUscitaGestione input) {
		boolean result = true;
		
		// controllo codice fruitore
		if (input.getCodiceFruitore() == null) {
			result = false;
			
			logger.error("codiceFruitore obbligatorio non può essere null");
		}
		
		// controllo codice ente
		if (input.getCodiceEnte() == null) {
			result = false;
			
			logger.error("codiceEnte obbligatorio non può essere null");
		}
		
		// controllo anno bilancio obbligatorio
		if (input.getAnnoBilancio() == null) {
			result = false;
				
			logger.error("annoBilancio obbligatorio non può essere null");
		}
		
		// controllo numero capitolo obbligatorio
		if (input.getNumeroCapitolo() == null) {
			result = false;
				
			logger.error("numeroCapitolo obbligatorio non può essere null");
		}
		
		// controllo numero articolo obbligatorio
		if (input.getNumeroArticolo() == null) {
			result = false;
				
			logger.error("numeroArticolo obbligatorio non può essere null");
		}
		
		return result;
	}
	
	/*
	 * Metodo per controllare parametri di input obbligatori per ricercaDettaglioSoggetto
	 */
	public static boolean checkInputRicercaDettaglioSoggetto(RicercaDettaglioSoggetti input) {
		boolean result = true;
		
		// controllo codice fruitore
		if (input.getCodiceFruitore() == null) {
			result = false;
				
			logger.error("codiceFruitore obbligatorio non può essere null");
		}
		
		// controllo codice ente
		if (input.getCodiceEnte() == null) {
			result = false;
				
			logger.error("codiceEnte obbligatorio non può essere null");
		}
		
		// controllo sia valorizzato almeno uno tra codice soggetto, partitca iva e codice fiscale
		if (input.getCodice() == null || input.getPartitaIva() == null || input.getCodiceFiscale() == null) {
			result = false;
			
			logger.error("almeno uno tra codice, partitaIVa, codiceFiscale non può essere null");
		}
		
		return result;
	}
	
	/*
	 * Metodo per controllare parametri di input obbligatori per ricercaSinteticaSoggetti
	 */
	public static boolean checkInputRicercaSinteticaSoggetti(RicercaSinteticaSoggetti input) {
		boolean result = true;
		
		// controllo codice fruitore
		if (input.getCodiceFruitore() == null) {
			result = false;
					
			logger.error("codiceFruitore obbligatorio non può essere null");
		}
		
		// controllo codice ente
		if (input.getCodiceEnte() == null) {
			result = false;
					
			logger.error("codiceEnte obbligatorio non può essere null");
		}
			
		// controllo sia valorizzato almeno uno tra codice soggetto, partitca iva e codice fiscale
		if (input.getCodice() == null || input.getPartitaIva() == null || input.getCodiceFiscale() == null) {
			result = false;
				
			logger.error("almeno uno tra codice, partitaIVa, codiceFiscale non può essere null");
		}
		
		return result;
	}
	
	/*
	 * Metodo per controllare parametri di input obbligatori per ricercaDocumentoEntrata
	 */
	public static boolean checkInputRicercaDocumentoEntrata(RicercaDocumentoEntrata input) {
		boolean result = true;
		
		// controllo codice fruitore
		if (input.getCodiceFruitore() == null) {
			result = false;
					
			logger.error("codiceFruitore obbligatorio non può essere null");
		}
		
		// controllo codice ente
		if (input.getCodiceEnte() == null) {
			result = false;
					
			logger.error("codiceEnte obbligatorio non può essere null");
		}
		
		// controllo anno bilancio
		if (input.getAnnoBilancio() == null) {
			result = false;
			
			logger.error("annoBilancio obbligatorio non può essere null");
		}
		
		// controllo anno documento
		if (input.getAnnoDocumento() == null) {
			result = false;
			
			logger.error("annoDocumento obbligatorio non può essere null");
		}
		
		// controllo sia valorizzato almeno uno tra numero documento e codice soggetto
		if (input.getNumeroDocumento() == null || input.getCodiceSoggetto() == null) {
			result = false;
			
			logger.error("almeno uno tra numeroDocumento, e codiceSoggetto non può essere null");
		}
		
		// controllo tipo documento
		if (input.getTipoDocumento() == null) {
			result = false;
			
			logger.error("tipoDocumento obbligatorio non può essere null");
		}
		
		// controllo dati paginazione
		result = checkPaginazione(input.getNumeroElementiPerPagina(), input.getNumeroPagina());
		
		return result;
	}
	
	/*
	 * Metodo per controllare parametri di input obbligatori per ricercaDocumentoSpesa
	 */
	public static boolean checkInputRicercaDocumentoSpesa(RicercaDocumentoSpesa input) {
		boolean result = true;
		
		// controllo codice fruitore
		if (input.getCodiceFruitore() == null) {
			result = false;
					
			logger.error("codiceFruitore obbligatorio non può essere null");
		}
		
		// controllo codice ente
		if (input.getCodiceEnte() == null) {
			result = false;
					
			logger.error("codiceEnte obbligatorio non può essere null");
		}
		
		// controllo anno bilancio
		if (input.getAnnoBilancio() == null) {
			result = false;
			
			logger.error("annoBilancio obbligatorio non può essere null");
		}
		
		// controllo anno documento
		if (input.getAnnoDocumento() == null) {
			result = false;
			
			logger.error("annoDocumento obbligatorio non può essere null");
		}
		
		// controllo sia valorizzato almeno uno tra numero documento e codice soggetto
		if (input.getNumeroDocumento() == null || input.getCodiceSoggetto() == null) {
			result = false;
			
			logger.error("almeno uno tra numeroDocumento, e codiceSoggetto non può essere null");
		}
		
		// controllo tipo documento
		if (input.getTipoDocumento() == null) {
			result = false;
			
			logger.error("tipoDocumento obbligatorio non può essere null");
		}
		
		// controllo dati paginazione
		result = checkPaginazione(input.getNumeroElementiPerPagina(), input.getNumeroPagina());
		
		return result;
	}
	
	/*
	 * Metodo per controllare parametri di input obbligatori per ricercaEstesaLiquidazioni
	 */
	public static boolean checkInputRicercaEstesaLiquidazioni(RicercaEstesaLiquidazioni input) {
		boolean result = true;
		
		// controllo codice fruitore
		if (input.getCodiceFruitore() == null) {
			result = false;
			
			logger.error("codiceFruitore obbligatorio non può essere null");
		}
		
		// controllo codice ente
		if (input.getCodiceEnte() == null) {
			result = false;
						
			logger.error("codiceEnte obbligatorio non può essere null");
		}
		
		// controllo anno bilancio
		if (input.getAnnoBilancio() == null) {
			result = false;
			
			logger.error("annoBilancio obbligatorio non può essere null");
		}
		
		// controllo anno provvisorio
		if (input.getAnnoProvvedimento() == null) {
			result = false;
			
			logger.error("annoProvvedimento obbligatorio non può essere null");
		}
		
		// controllo numero provvedimento
		if (input.getNumeroProvvedimento() == null) {
			result = false;
			
			logger.error("numeroProvvedimento obbligatorio non può essere null");
		}
		
		// controllo tipo codice provvedimento
		if (input.getCodiceTipoProvvedimento() == null) {
			result = false;
			
			logger.error("codiceTipoProvvedimento obbligatorio non può essere null");
		}
		
		return result;
	}
	
	/*
	 * Metodo per controllare parametri di input obbligatori per ricercaEstesaOrdinativiSpesa
	 */
	public static boolean checkInputRicercaEstesaOrdinativiSpesa(RicercaEstesaOrdinativiSpesa input) {
		boolean result = true;
		
		// conrollo codice fruitore
		if (input.getCodiceFruitore() == null) {
			result = false;
			
			logger.error("codiceFruitore obbligatorio non può essere null");
		}
		
		// controllo codice ente
		if (input.getCodiceEnte() == null) {
			result = false;
						
			logger.error("codiceEnte obbligatorio non può essere null");
		}
		
		// controllo anno bilancio
		if (input.getAnnoBilancio() == null) {
			result = false;
			
			logger.error("annoBilancio obbligatorio non può essere null");
		}
		
		// controllo anno provvedimento
		if (input.getAnnoProvvedimento() == null) {
			result = false;
			
			logger.error("annoProvvedimento obbligatorio non può essere null");
		}
		
		// controllo numero provvedimento
		if (input.getNumeroProvvedimento() == null) {
			result = false;
			
			logger.error("numeroProvvedimento obbligatorio non può essere null");
		}
		
		// controllo codice tipo proveddimento
		if (input.getCodiceTipoProvvedimento() == null) {
			result = false;
			
			logger.error("codiceTipoProvvedimento obbligatorio non può essere null");
		}
		
		return result;
	}
	
	/*
	 * Metodo per controllare parametri di input obbligatori per ricercaLiquidazione
	 */
	public static boolean checkInputRicercaLiquidazione(RicercaLiquidazione input) {
		boolean result = true;
		
		// controllo codice fruitore
		if (input.getCodiceFruitore() == null) {
			result = false;
			
			logger.error("codiceFruitore obbligatorio non può essere null");
		}
		
		// controllo codice ente
		if (input.getCodiceEnte() == null) {
			result = false;
						
			logger.error("codiceEnte obbligatorio non può essere null");
		}
		
		// controllo anno bilancio
		if (input.getAnnoBilancio() == null) {
			result = false;
			
			logger.error("annoBilancio obbligatorio non può essere null");
		}
		
		// controllo anno provvedimento
		if (input.getAnnoProvvedimento() == null) {
			result = false;
			
			logger.error("annoProvvedimento obbligatorio non può essere null");
		}
		
		// controllo numero provvedimento
		if (input.getNumeroProvvedimento() == null) {
			result = false;
			
			logger.error("numeroProvvedimento obbligatorio non può essere null");
		}
		
		// controllo codice tipo proveddimento
		if (input.getCodiceTipoProvvedimento() == null) {
			result = false;
			
			logger.error("codiceTipoProvvedimento obbligatorio non può essere null");
		}
		
		// controllo dati paginazione
		result = checkPaginazione(input.getNumeroElementiPerPagina(), input.getNumeroPagina());
		
		return result;
	}
	
	/*
	 * Metodo per controllare parametri di input obbligatori per ricercaOrdinativoIncasso
	 */
	public static boolean checkInputRicercaOrdinativoIncasso(RicercaOrdinativoIncasso input) {
		boolean result = true;
		
		// controllo codice fruitore
		if (input.getCodiceFruitore() == null) {
			result = false;
			
			logger.error("codiceFruitore obbligatorio non può essere null");
		}
		
		// controllo codice ente
		if (input.getCodiceEnte() == null) {
			result = false;
						
			logger.error("codiceEnte obbligatorio non può essere null");
		}
		
		// controllo anno bilancio
		if (input.getAnnoBilancio() == null) {
			result = false;
			
			logger.error("annoBilancio obbligatorio non può essere null");
		}
		
		// controllo anno provvedimento
		if (input.getAnnoProvvedimento() == null) {
			result = false;
			
			logger.error("annoProvvedimento obbligatorio non può essere null");
		}
		
		// controllo numero provvedimento
		if (input.getNumeroProvvedimento() == null) {
			result = false;
			
			logger.error("numeroProvvedimento obbligatorio non può essere null");
		}
		
		// controllo codice tipo proveddimento
		if (input.getCodiceTipoProvvedimento() == null) {
			result = false;
			
			logger.error("codiceTipoProvvedimento obbligatorio non può essere null");
		}
		
		// controllo dati paginazione
		result = checkPaginazione(input.getNumeroElementiPerPagina(), input.getNumeroPagina());
		
		return result;
	}
	
	/*
	 * Metodo per controllare parametri di input obbligatori per ricercaOrdinativoSpesa
	 */
	public static boolean checkInputRicercaOrdinativoSpesa(RicercaOrdinativoSpesa input) {
		boolean result = true;
		
		// controllo codice fruitore
		if (input.getCodiceFruitore() == null) {
			result = false;
			
			logger.error("codiceFruitore obbligatorio non può essere null");
		}
		
		// controllo codice ente
		if (input.getCodiceEnte() == null) {
			result = false;
						
			logger.error("codiceEnte obbligatorio non può essere null");
		}
		
		// controllo anno bilancio
		if (input.getAnnoBilancio() == null) {
			result = false;
			
			logger.error("annoBilancio obbligatorio non può essere null");
		}
		
		// controllo anno provvedimento
		if (input.getAnnoProvvedimento() == null) {
			result = false;
			
			logger.error("annoProvvedimento obbligatorio non può essere null");
		}
		
		// controllo numero provvedimento
		if (input.getNumeroProvvedimento() == null) {
			result = false;
			
			logger.error("numeroProvvedimento obbligatorio non può essere null");
		}
		
		// controllo codice tipo proveddimento
		if (input.getCodiceTipoProvvedimento() == null) {
			result = false;
			
			logger.error("codiceTipoProvvedimento obbligatorio non può essere null");
		}
		
		// controllo dati paginazione
		result = checkPaginazione(input.getNumeroElementiPerPagina(), input.getNumeroPagina());
		
		return result;
	}
	
	/*
	 * Metodo per controllare parametri di input obbligatori per ricercaProvvisoriDiCassa
	 */
	public static boolean checkInputRicercaProvvisoriDiCassa(RicercaProvvisoriDiCassa input) {
		boolean result = true;
		
		// controllo codice fruitore
		if (input.getCodiceFruitore() == null) {
			result = false;
			
			logger.error("codiceFruitore obbligatorio non può essere null");
		}
		
		// controllo codice ente
		if (input.getCodiceEnte() == null) {
			result = false;
						
			logger.error("codiceEnte obbligatorio non può essere null");
		}
		
		// controllo anno bilancio
		if (input.getAnnoBilancio() == null) {
			result = false;
			
			logger.error("annoBilancio obbligatorio non può essere null");
		}
		
		// controllo dati paginazione
		result = checkPaginazione(input.getNumeroElementiPerPagina(), input.getNumeroPagina());
		
		return result;
	}
	
	
	private static boolean checkPaginazione(Integer numElementiPagina, Integer numeroPagina) {
		boolean result = false;
		
		// controllo numero elementi per pagina obbligatorio
		if (numElementiPagina != null && numeroPagina != null) {
			result = true;
			
			logger.error("numeroElementiPerPagina e numeroPagina obbligatori non possono essere null");
		}
		
		return result;
	}
	
}
