/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.fileOperation.clientws.CodificaTimbro;
import it.eng.fileOperation.clientws.MappaParametri;
import it.eng.fileOperation.clientws.PaginaTimbro;
import it.eng.fileOperation.clientws.PosizioneRispettoAlTimbro;
import it.eng.fileOperation.clientws.PosizioneTimbroNellaPagina;
import it.eng.fileOperation.clientws.TestoTimbro;
import it.eng.fileOperation.clientws.TipoRotazione;

public class OpzioniCopertinaTimbroBean {
	
	private Integer timeout;
	private CodificaTimbro codifica;
	private TipoRotazione rotazioneTimbro;
	private TestoTimbro testoTimbro;
	private TestoTimbro intestazioneTimbro;
	private PosizioneTimbroNellaPagina posizioneTimbro;
	private PosizioneRispettoAlTimbro posizioneTestoInChiaro;
	private PosizioneRispettoAlTimbro posizioneIntestazione;
	private PaginaTimbro paginaTimbro;
	private MappaParametri mappaParametri;
	private boolean righeMultiple;
	
	public Integer getTimeout() {
		return timeout;
	}
	
	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}
	
	public CodificaTimbro getCodifica() {
		return codifica;
	}
	
	public void setCodifica(CodificaTimbro codifica) {
		this.codifica = codifica;
	}
	
	public TipoRotazione getRotazioneTimbro() {
		return rotazioneTimbro;
	}
	
	public void setRotazioneTimbro(TipoRotazione rotazioneTimbro) {
		this.rotazioneTimbro = rotazioneTimbro;
	}
	
	public TestoTimbro getTestoTimbro() {
		return testoTimbro;
	}
	
	public void setTestoTimbro(TestoTimbro testoTimbro) {
		this.testoTimbro = testoTimbro;
	}
	
	public TestoTimbro getIntestazioneTimbro() {
		return intestazioneTimbro;
	}
	
	public void setIntestazioneTimbro(TestoTimbro intestazioneTimbro) {
		this.intestazioneTimbro = intestazioneTimbro;
	}
	
	public PosizioneTimbroNellaPagina getPosizioneTimbro() {
		return posizioneTimbro;
	}
	
	public void setPosizioneTimbro(PosizioneTimbroNellaPagina posizioneTimbro) {
		this.posizioneTimbro = posizioneTimbro;
	}
	
	public PosizioneRispettoAlTimbro getPosizioneTestoInChiaro() {
		return posizioneTestoInChiaro;
	}
	
	public void setPosizioneTestoInChiaro(PosizioneRispettoAlTimbro posizioneTestoInChiaro) {
		this.posizioneTestoInChiaro = posizioneTestoInChiaro;
	}
	
	public PosizioneRispettoAlTimbro getPosizioneIntestazione() {
		return posizioneIntestazione;
	}
	
	public void setPosizioneIntestazione(PosizioneRispettoAlTimbro posizioneIntestazione) {
		this.posizioneIntestazione = posizioneIntestazione;
	}
	
	public PaginaTimbro getPaginaTimbro() {
		return paginaTimbro;
	}
	
	public void setPaginaTimbro(PaginaTimbro paginaTimbro) {
		this.paginaTimbro = paginaTimbro;
	}
	
	public MappaParametri getMappaParametri() {
		return mappaParametri;
	}
	
	public void setMappaParametri(MappaParametri mappaParametri) {
		this.mappaParametri = mappaParametri;
	}
	
	public boolean isRigheMultiple() {
		return righeMultiple;
	}
	
	public void setRigheMultiple(boolean righeMultiple) {
		this.righeMultiple = righeMultiple;
	}
	
}
