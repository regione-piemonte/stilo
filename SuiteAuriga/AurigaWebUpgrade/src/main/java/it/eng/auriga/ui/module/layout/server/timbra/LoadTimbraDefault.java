/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.log4j.Logger;

import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;

@Datasource(id = "LoadTimbraDefault")
public class LoadTimbraDefault extends AbstractServiceDataSource<OpzioniTimbroBean, OpzioniTimbroBean>{

	private static Logger mLogger = Logger.getLogger(LoadTimbraDefault.class);
	
	@Override
	public OpzioniTimbroBean call(OpzioniTimbroBean bean) throws Exception {
		TimbraUtility timbraUtility = new TimbraUtility();
		
		/*
		 *  Apponi segnatura registrazione SR prende i dati da DmpkRegistrazionedocGettimbrodigreg
		 *  Timbra T :  prende i dati store procedure dmpk_RegistrazioneDoc.GetTimbroSpecXTipo
		 */
		
		if("T".equals(bean.getTipoTimbro())){
			mLogger.debug("Nuova Timbratura");
			return timbraUtility.loadTimbraDefault(bean, getSession(), getLocale());
		} else{
			mLogger.debug("Apponi segnatura registrazione (Vecchio timbra)");
			return timbraUtility.loadSegnatureRegistrazioneDefault(bean, getSession(), getLocale());
		}
	}
}