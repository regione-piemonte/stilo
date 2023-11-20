/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.log4j.Logger;

import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;

/**
 * 
 * @author DANCRIST
 * 
 * Classe relativa al caricamento delle preference di default per il barcode, nel caso in cui non si è deciso
 * di saltare la scelta delle stesse
 *
 */

@Datasource(id="LoadCopertinaDefaultDataSource")
public class LoadCopertinaDefaultDataSource extends AbstractServiceDataSource<OpzioniCopertinaBean, OpzioniCopertinaBean>{

	private static Logger mLogger = Logger.getLogger(LoadCopertinaDefaultDataSource.class);
	
	@Override
	public OpzioniCopertinaBean call(OpzioniCopertinaBean pInBean) throws Exception {
		
		TimbraCopertinaUtility lCopertinaUtility = new TimbraCopertinaUtility();
		
		Integer nrAllegato = null;
		
		/**
		 *  PROVENIENZA = A allora Provengo da un allegato altrimenti provengo da File Primario
		 *  POSIZIONALE = se P allora Nro documento + posizione altrimenti Nro documento
		 */
		if(pInBean.getProvenienza() != null && "A".equals(pInBean.getProvenienza())){
			if(pInBean.getPosizionale() != null && "P".equals(pInBean.getPosizionale())){
				nrAllegato = Integer.valueOf(pInBean.getNumeroAllegato());
			}else{
				nrAllegato = null;
			}
		} else {
			if(pInBean.getPosizionale() != null && "P".equals(pInBean.getPosizionale())){
				nrAllegato = 0;
			} else {
				nrAllegato = null;
			}	
		}
		
		/**
		 * Utilizzata store: DmpkRegistrazionedocGettimbrospecxtipo con Flgdocfolderin F o D
		 */
		if(pInBean.getTipoTimbroCopertina() != null && "T".equals(pInBean.getTipoTimbroCopertina())){
			mLogger.debug("Barcode con tipologia");
			return lCopertinaUtility.loadTimbroTipologiaDefault(pInBean, getSession(), getLocale(), nrAllegato);
		} else{
			/**
			 * PRATICA PREGRESSA: DmpkRegistrazionedocGetbarcodedacapofilapratica
			 * PROTOCOLLAZIONE: DmpkRegistrazionedocGettimbrodigreg
			 */
			mLogger.debug("Barcode con segnatura");
			return lCopertinaUtility.loadTimbroSegnaturaDefault(pInBean, getSession(), getLocale(), nrAllegato);
		}
	}
}