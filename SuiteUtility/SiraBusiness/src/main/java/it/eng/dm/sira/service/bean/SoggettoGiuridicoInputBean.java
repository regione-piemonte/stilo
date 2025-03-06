package it.eng.dm.sira.service.bean;

import com.hyperborea.sira.ws.CcostSoggettiGiuridici;
import com.hyperborea.sira.ws.CostNostId;
import com.hyperborea.sira.ws.FontiDati;
import com.hyperborea.sira.ws.TipologieFontiDati;
import com.hyperborea.sira.ws.UbicazioniOst;

public class SoggettoGiuridicoInputBean {
	
	private CostNostId id;

	private CcostSoggettiGiuridici caratterizzazione;
	
	private UbicazioniOst ubicazione;
	
	private FontiDati Fonti;
	
	private TipologieFontiDati tipologiaFonte;

	public CostNostId getId() {
		return id;
	}

	public void setId(CostNostId id) {
		this.id = id;
	}

	public CcostSoggettiGiuridici getCaratterizzazione() {
		return caratterizzazione;
	}

	public void setCaratterizzazione(CcostSoggettiGiuridici caratterizzazione) {
		this.caratterizzazione = caratterizzazione;
	}

	public UbicazioniOst getUbicazione() {
		return ubicazione;
	}

	public void setUbicazione(UbicazioniOst ubicazione) {
		this.ubicazione = ubicazione;
	}

	public FontiDati getFonti() {
		return Fonti;
	}

	public void setFonti(FontiDati fonti) {
		Fonti = fonti;
	}

	public TipologieFontiDati getTipologiaFonte() {
		return tipologiaFonte;
	}

	public void setTipologiaFonte(TipologieFontiDati tipologiaFonte) {
		this.tipologiaFonte = tipologiaFonte;
	}

	
}
