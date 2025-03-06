package it.eng.dm.sira.service.bean;

public class GenericOSTSearchInputBean extends SearchBeanIn {

	private static final long serialVersionUID = -7972765568840501232L;

	private Integer categoria;

	private Integer natura;

	public Integer getCategoria() {
		return categoria;
	}

	public void setCategoria(Integer categoria) {
		this.categoria = categoria;
	}

	public Integer getNatura() {
		return natura;
	}

	public void setNatura(Integer natura) {
		this.natura = natura;
	}
}
