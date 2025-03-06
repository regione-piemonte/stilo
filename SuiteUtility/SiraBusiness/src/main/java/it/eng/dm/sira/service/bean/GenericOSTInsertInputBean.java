package it.eng.dm.sira.service.bean;

import java.util.List;

import com.hyperborea.sira.ws.CaratterizzazioniOst;
import com.hyperborea.sira.ws.CostNostId;
import com.hyperborea.sira.ws.FontiDati;
import com.hyperborea.sira.ws.RelazioniOst;
import com.hyperborea.sira.ws.StatiOst;
import com.hyperborea.sira.ws.UbicazioniOst;

public class GenericOSTInsertInputBean {
	
	private Integer idOstToUpdate;
	
	private CaratterizzazioniOst caratterizzazioni;

	private CostNostId id;

	private UbicazioniOst ubicazione;

	private FontiDati Fonti;

	private List<RelazioniOst> relazioni;

	private StatiOst stati;

	public CaratterizzazioniOst getCaratterizzazioni() {
		return caratterizzazioni;
	}

	public void setCaratterizzazioni(CaratterizzazioniOst caratterizzazioni) {
		this.caratterizzazioni = caratterizzazioni;
	}

	public CostNostId getId() {
		return id;
	}

	public void setId(CostNostId id) {
		this.id = id;
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

	public List<RelazioniOst> getRelazioni() {
		return relazioni;
	}

	public void setRelazioni(List<RelazioniOst> relazioni) {
		this.relazioni = relazioni;
	}

	public StatiOst getStati() {
		return stati;
	}

	public void setStati(StatiOst stati) {
		this.stati = stati;
	}

	public Integer getIdOstToUpdate() {
		return idOstToUpdate;
	}

	public void setIdOstToUpdate(Integer idOstToUpdate) {
		this.idOstToUpdate = idOstToUpdate;
	}
	
}
