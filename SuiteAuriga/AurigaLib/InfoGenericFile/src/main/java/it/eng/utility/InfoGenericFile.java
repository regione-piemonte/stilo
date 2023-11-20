/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import it.eng.document.function.bean.Firmatario;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.ui.servlet.bean.Firmatari;
import it.eng.utility.ui.servlet.bean.InfoFirmaMarca;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class InfoGenericFile {

	private static final Logger log = Logger.getLogger(InfoGenericFile.class);

	public GenericFile get(RebuildedFile lRebuildedFile, String idUserScansione) throws Exception {

		GenericFile lGenericFile = new GenericFile();
		try {

			InfoFileUtility lFileUtility = new InfoFileUtility();
			MimeTypeFirmaBean lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(lRebuildedFile.getFile().toURI().toString(), lRebuildedFile.getFile().getName(), false, null);
			
			setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);

			if (lMimeTypeFirmaBean != null) {
				
				if (lMimeTypeFirmaBean.isDaScansione()) {
					lGenericFile.setDaScansione(Flag.SETTED);
					lGenericFile.setDataScansione(new Date());
					lGenericFile.setIdUserScansione(idUserScansione);
				} else {
					lGenericFile.setDaScansione(Flag.NOT_SETTED);
				}
				
				lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
				lGenericFile.setDisplayFilename(lGenericFile.getDisplayFilename());
				lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
				lGenericFile.setAlgoritmo(lMimeTypeFirmaBean.getAlgoritmo());
				lGenericFile.setEncoding(lMimeTypeFirmaBean.getEncoding());
			}
		} catch (Exception e) {
			throw new Exception("ERRORE nella funzione InfoGenericFile = " + e.getMessage());
		}

		return lGenericFile;
	}
	
	// ATTENZIONE!! Questo medodo viene usato anche dalla classe AbstractDatasource di BaseUI per settare le proprietà del file prima del versionamento
	public static void setProprietaGenericFile(GenericFile lGenericFile, MimeTypeFirmaBean lMimeTypeFirmaBean) throws ParseException {
		
		if (lMimeTypeFirmaBean != null) {
			lGenericFile.setPdfProtetto(lMimeTypeFirmaBean.isPdfProtetto() ? Flag.SETTED : Flag.NOT_SETTED);
			lGenericFile.setPdfEditabile(lMimeTypeFirmaBean.isPdfEditabile() ? Flag.SETTED : Flag.NOT_SETTED);
			lGenericFile.setPdfConCommenti(lMimeTypeFirmaBean.isPdfConCommenti() ? Flag.SETTED : Flag.NOT_SETTED);
			lGenericFile.setNroPagineVersioneDoc(lMimeTypeFirmaBean.getNumPaginePdf() != null ? lMimeTypeFirmaBean.getNumPaginePdf() : null);
			
			if (lMimeTypeFirmaBean.getBuste() != null || lMimeTypeFirmaBean.getFirmatari() != null) {
				List<Firmatario> lList = new ArrayList<Firmatario>();
				
				if (lMimeTypeFirmaBean.getBuste() != null) {
					for (Firmatari busta : lMimeTypeFirmaBean.getBuste()) {
						Firmatario lFirmatario = new Firmatario();
						lFirmatario.setCommonName(busta.getNomeFirmatario());
						lFirmatario.setDataOraFirma(busta.getDataFirma());
						lFirmatario.setFirmatoDaAuriga(busta.isFirmaExtraAuriga() ? Flag.NOT_SETTED : Flag.SETTED);
						lFirmatario.setDataOraVerificaFirma(busta.getDataVerificaFirma());
						lFirmatario.setFirmaNonValida(busta.isFirmaValida() ? Flag.NOT_SETTED : Flag.SETTED);
						lFirmatario.setInfoFirma(busta.getInfoFirma());
						lFirmatario.setTipoFirma(busta.getTipoFirma());
						lFirmatario.setBustaEsterna(("1".equalsIgnoreCase(busta.getFiglioDi())) ? Flag.SETTED : Flag.NOT_SETTED);
						lFirmatario.setTipoFirmaQA(busta.getTipoFirmaQA()); 
						lFirmatario.setTitolareFirma(busta.getTitolareFirma());
						lFirmatario.setCodiceActivityFirma(busta.getCodiceActivityFirma());
						lFirmatario.setIdUtenteLavoroFirma(busta.getIdUtenteLavoroFirma());
						lFirmatario.setIdUtenteLoggatoFirma(busta.getIdUtenteLoggatoFirma());
						Firmatario lFirmatarioControFirma = null;
						if (busta.getControFirma() != null) {
							Firmatari bustaControFirma = busta.getControFirma();
							lFirmatarioControFirma = new Firmatario();
							lFirmatarioControFirma.setCommonName(bustaControFirma.getNomeFirmatario());
							lFirmatarioControFirma.setDataOraFirma(bustaControFirma.getDataFirma());
							lFirmatarioControFirma.setFirmatoDaAuriga(bustaControFirma.isFirmaExtraAuriga() ? Flag.NOT_SETTED : Flag.SETTED);
							lFirmatarioControFirma.setDataOraVerificaFirma(bustaControFirma.getDataVerificaFirma());
							lFirmatarioControFirma.setFirmaNonValida(bustaControFirma.isFirmaValida() ? Flag.NOT_SETTED : Flag.SETTED);
							lFirmatarioControFirma.setInfoFirma(bustaControFirma.getInfoFirma());
							lFirmatarioControFirma.setTipoFirma(bustaControFirma.getTipoFirma());
							lFirmatarioControFirma.setBustaEsterna(("1".equalsIgnoreCase(bustaControFirma.getFiglioDi())) ? Flag.SETTED : Flag.NOT_SETTED);
						}
						lFirmatario.setDataOraEmissioneCertificatoFirma(busta.getDataEmissione());
						lFirmatario.setDataOraScadenzaCertificatoFirma(busta.getDataScadenza());
						lList.add(lFirmatario);
						if (lFirmatarioControFirma != null) {
							lList.add(lFirmatarioControFirma);
						}
					}
				} else {
					for (String firmatario : lMimeTypeFirmaBean.getFirmatari()) {
						Firmatario lFirmatario = new Firmatario();
						lFirmatario.setCommonName(firmatario);
						lList.add(lFirmatario);
					}
				}
				
				lGenericFile.setFirmatari(lList);
				lGenericFile.setFirmato(Flag.SETTED);
		
				// Costruisco le informazioni su busta crittografica e marca temmporale
				if (lMimeTypeFirmaBean.getInfoFirmaMarca() != null){
					InfoFirmaMarca lInfoFirmaMarca = lMimeTypeFirmaBean.getInfoFirmaMarca();
					lGenericFile.setFlgBustaCrittograficaDocElFattaDaAuriga(lInfoFirmaMarca.isBustaCrittograficaFattaDaAuriga() ? Flag.SETTED : Flag.NOT_SETTED);
					lGenericFile.setFlgBustaCrittograficaDocElInComplPassoIter(lInfoFirmaMarca.isBustaCrittograficaInComplPassoIter() ? Flag.SETTED : Flag.NOT_SETTED);
					lGenericFile.setTsVerificaBustaCrittograficaDocEl(lInfoFirmaMarca.getDataOraVerificaFirma());
					lGenericFile.setFlgBustaCrittograficaDocElNonValida(lInfoFirmaMarca.isFirmeNonValideBustaCrittografica() ? Flag.SETTED : Flag.NOT_SETTED);
					lGenericFile.setTipoBustaCrittograficaDocEl(lInfoFirmaMarca.getTipoBustaCrittografica());
					lGenericFile.setInfoVerificaBustaCrittograficaDocEl(lInfoFirmaMarca.getInfoBustaCrittografica());
					
					lGenericFile.setTsMarcaTemporale(lInfoFirmaMarca.getDataOraMarcaTemporale());
					lGenericFile.setFlgMarcaTemporaleAppostaDaAuriga(lInfoFirmaMarca.isMarcaTemporaleAppostaDaAuriga() ? Flag.SETTED : Flag.NOT_SETTED);
					lGenericFile.setTsVerificaMarcaTempDocEl(lInfoFirmaMarca.getDataOraVerificaMarcaTemporale());
					lGenericFile.setFlgMarcaTemporaleDocElNonValida(lInfoFirmaMarca.isMarcaTemporaleNonValida() ? Flag.SETTED : Flag.NOT_SETTED);
					lGenericFile.setTipoMarcaTemporaleDocEl(lInfoFirmaMarca.getTipoMarcaTemporale());
					lGenericFile.setInfoVerificaMarcaTemporaleDocEl(lInfoFirmaMarca.getInfoMarcaTemporale());
				}
				
			} else if (lMimeTypeFirmaBean.isFirmato()) {
				lGenericFile.setFirmato(Flag.SETTED);
			}
		}
	} 

}