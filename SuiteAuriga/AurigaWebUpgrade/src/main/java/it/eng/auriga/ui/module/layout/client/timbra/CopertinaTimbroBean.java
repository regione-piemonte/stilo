/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;

/**
 * 
 * @author DANCRIST
 *
 */

public class CopertinaTimbroBean extends Record {
	
	public CopertinaTimbroBean(Record record){
		
		setIdUd(record.getAttribute("idUd"));
		setNrAllegato(record.getAttribute("numeroAllegato"));
		setTipoTimbroCopertina(record.getAttribute("tipoTimbroCopertina"));
		setRotazioneTimbroPref(record.getAttribute("rotazioneTimbro"));
		setPosizioneTimbroPref(record.getAttribute("posizioneTimbro"));
		setProvenienza(record.getAttribute("provenienza"));
		setPosizionale(record.getAttribute("posizionale"));
		setIdDoc(record.getAttribute("idDoc"));
		setBarcodePraticaPregressa(record.getAttribute("barcodePraticaPregressa"));
		setIdFolder(record.getAttribute("idFolder"));
		setSezionePratica(record.getAttribute("sezionePratica"));
	}
	
	private void setBarcodePraticaPregressa(String barcodePraticaPregressa){
		setAttribute("barcodePraticaPregressa", barcodePraticaPregressa);
	}
	
	private void setIdFolder(String idFolder){
		setAttribute("idFolder", idFolder);
	}
	
	private void setSezionePratica(String sezionePratica){
		setAttribute("sezionePratica", sezionePratica);
	}
	
	private void setProvenienza(String provenienza){
		setAttribute("provenienza", provenienza);
	}
	
	private void setPosizionale(String posizionale){
		setAttribute("posizionale", posizionale);
	}
	
	private void setNrAllegato(String numeroAllegato){
		setAttribute("numeroAllegato", numeroAllegato);
	}
	
	private void setIdDoc(String idDoc) {
		setAttribute("idDoc", idDoc);
	}
	
	private void setIdUd(String idUd) {
		setAttribute("idUd", idUd);
	}
	
	private void setTipoTimbroCopertina(String tipoTimbroCopertina) {
		setAttribute("tipoTimbroCopertina", tipoTimbroCopertina);
	}
	
	private void setRotazioneTimbroPref(String rotazioneTimbro) {
		setAttribute("rotazioneTimbroPref", rotazioneTimbro);
	}
	
	private void setPosizioneTimbroPref(String posizioneTimbro) {
		setAttribute("posizioneTimbroPref", posizioneTimbro);
	}

}
