package it.eng.dm.sira.service.mgu;

import java.util.List;

import it.eng.sira.mgu.ws.MguSgiuridicoOstDTO;

import com.hyperborea.sira.ws.OggettiStruttureTerritoriali;

public class UserPermessi {
	
	private OggettiStruttureTerritoriali soggettoGiuridico;

	private List<MguSgiuridicoOstDTO> unitaLocali;

	public OggettiStruttureTerritoriali getSoggettoGiuridico() {
		return soggettoGiuridico;
	}

	public void setSoggettoGiuridico(OggettiStruttureTerritoriali soggettoGiuridico) {
		this.soggettoGiuridico = soggettoGiuridico;
	}

	public List<MguSgiuridicoOstDTO> getUnitaLocali() {
		return unitaLocali;
	}

	public void setUnitaLocali(List<MguSgiuridicoOstDTO> unitaLocali) {
		this.unitaLocali = unitaLocali;
	}

}
