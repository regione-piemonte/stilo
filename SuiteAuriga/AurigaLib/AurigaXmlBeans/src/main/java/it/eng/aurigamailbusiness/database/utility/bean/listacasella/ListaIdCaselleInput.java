/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.annotation.Obbligatorio;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean di input per il metodo getListaCaselleUtente della 
 * classe {@link CasellaUtility}.
 * 
 * @author Rametta
 *
 */
@XmlRootElement
@Obbligatorio
public class ListaIdCaselleInput implements Serializable{

	private static final long serialVersionUID = -4028847205561266615L;
	@Obbligatorio
	private String idUtente;
	private List<FlagArrivoInvio> flag;
	private String idEnteAOO;
	
	public enum FlagArrivoInvio {
		IN(new String[]{"smistatore", "gestore", "destinatario_per_competenza"}), OUT(new String[]{"mittente"});
	
		private String[] profili;
		
		private FlagArrivoInvio(String[] profili){
			this.setProfili(profili);
		}

		public void setProfili(String[] profili) {
			this.profili = profili;
		}

		public String[] getProfili() {
			return profili;
		}
		
	}

	public void setFlag(List<FlagArrivoInvio> flag) {
		this.flag = flag;
	}

	public List<FlagArrivoInvio> getFlag() {
		return flag;
	}

	public void setIdUtente(String idUtente) {
		this.idUtente = idUtente;
	}

	public String getIdUtente() {
		return idUtente;
	}

	public void setIdEnteAOO(String idEnteAOO) {
		this.idEnteAOO = idEnteAOO;
	}

	public String getIdEnteAOO() {
		return idEnteAOO;
	}
	
}
