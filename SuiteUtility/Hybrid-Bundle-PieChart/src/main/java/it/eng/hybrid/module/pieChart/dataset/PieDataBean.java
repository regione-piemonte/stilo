package it.eng.hybrid.module.pieChart.dataset;

public class PieDataBean implements Comparable<PieDataBean>{

	private String idSoggetto;
	private String label;
	private String valore;
	private Float perc;
	private Float percArrotondata;
	
	@Override
	public int compareTo(PieDataBean o) {
		// TODO Auto-generated method stub
		return label.compareTo(o.getLabel());
	}

	public String getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(String idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValore() {
		return valore;
	}

	public void setValore(String valore) {
		this.valore = valore;
	}

	public Float getPerc() {
		return perc;
	}

	public void setPerc(Float perc) {
		this.perc = perc;
	}

	public Float getPercArrotondata() {
		return percArrotondata;
	}

	public void setPercArrotondata(Float percArrotondata) {
		this.percArrotondata = percArrotondata;
	}

}
