package it.eng.dm.sirabusiness.bean;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.business.beans.AbstractBean;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
public class SiraInfoPraticheBean extends AbstractBean implements Serializable{
	
	private static final long serialVersionUID = 175503336348726521L;
	
	private List<SiraVistaAdaScarichiBean> pratichePreEsistenti;

	public List<SiraVistaAdaScarichiBean> getPratichePreEsistenti() {
		return pratichePreEsistenti;
	}

	public void setPratichePreEsistenti(List<SiraVistaAdaScarichiBean> pratichePreEsistenti) {
		this.pratichePreEsistenti = pratichePreEsistenti;
	}

}
