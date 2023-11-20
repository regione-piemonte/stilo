/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WSLoginOutBean implements Serializable {

	private String codIdConnectionTokenOut;
	private String desUserOut;
	private String idDominioOut;	
	private String desDominioOut;	
	private String flgTpDominioAutOut;
	
	public String getCodIdConnectionTokenOut() {
		return codIdConnectionTokenOut;
	}
	public String getDesUserOut() {
		return desUserOut;
	}
	public String getIdDominioOut() {
		return idDominioOut;
	}
	public String getDesDominioOut() {
		return desDominioOut;
	}
	public void setCodIdConnectionTokenOut(String codIdConnectionTokenOut) {
		this.codIdConnectionTokenOut = codIdConnectionTokenOut;
	}
	public void setDesUserOut(String desUserOut) {
		this.desUserOut = desUserOut;
	}
	public void setIdDominioOut(String idDominioOut) {
		this.idDominioOut = idDominioOut;
	}
	public void setDesDominioOut(String desDominioOut) {
		this.desDominioOut = desDominioOut;
	}
	public String getFlgTpDominioAutOut() {
		return flgTpDominioAutOut;
	}
	public void setFlgTpDominioAutOut(String flgTpDominioAutOut) {
		this.flgTpDominioAutOut = flgTpDominioAutOut;
	}
}
