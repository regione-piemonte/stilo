package it.eng.dm.sira.service.bean;

import java.util.List;

public class InfoTecnicheULBean {
	
	private Float superficieCopertaM2;

	private Float superficieScopertaNonpavM2;

	private Float superficieScopertaPavM2;

	private List<AttivitaEconomicheUL> attivitaEconomiche;

	private List<ConsumoMateriePrimeUL> consumoMateriePrime;

	public List<AttivitaEconomicheUL> getAttivitaEconomiche() {
		return attivitaEconomiche;
	}

	public void setAttivitaEconomiche(List<AttivitaEconomicheUL> attivitaEconomiche) {
		this.attivitaEconomiche = attivitaEconomiche;
	}

	public List<ConsumoMateriePrimeUL> getConsumoMateriePrime() {
		return consumoMateriePrime;
	}

	public void setConsumoMateriePrime(List<ConsumoMateriePrimeUL> consumoMateriePrime) {
		this.consumoMateriePrime = consumoMateriePrime;
	}

	public Float getSuperficieCopertaM2() {
		return superficieCopertaM2;
	}

	public void setSuperficieCopertaM2(Float superficieCopertaM2) {
		this.superficieCopertaM2 = superficieCopertaM2;
	}

	public Float getSuperficieScopertaNonpavM2() {
		return superficieScopertaNonpavM2;
	}

	public void setSuperficieScopertaNonpavM2(Float superficieScopertaNonpavM2) {
		this.superficieScopertaNonpavM2 = superficieScopertaNonpavM2;
	}

	public Float getSuperficieScopertaPavM2() {
		return superficieScopertaPavM2;
	}

	public void setSuperficieScopertaPavM2(Float superficieScopertaPavM2) {
		this.superficieScopertaPavM2 = superficieScopertaPavM2;
	}

}
