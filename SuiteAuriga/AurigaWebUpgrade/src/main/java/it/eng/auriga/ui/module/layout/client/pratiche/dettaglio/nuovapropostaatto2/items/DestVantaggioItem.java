/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class DestVantaggioItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		DestVantaggioCanvas lDestVantaggioCanvas = new DestVantaggioCanvas(this);
		return lDestVantaggioCanvas;
	}
	
	public boolean isBeneficiariTrasparenza() {
		return false;
	}
	
	public boolean showTipo() {
		return false;
	}
	
	public String getTitleTipo() {
		return "Tipo";
	}
	
	public String getDefaultValueTipo() {
		return null;
	}
	
	public boolean isRequiredTipo() {
		return false;
	}
	
	public boolean isEditableTipo() {
		return true;
	}
		
	public boolean showTipoPersona() {
		return true;
	}
	
	public String getTitleTipoPersona() {
		return "Persona";
	}
	
	public boolean isRequiredTipoPersona() {
		return false;
	}						
	
	public HashMap<String, String> getValueMapTipoPersona() {
		return null;
	}
	
	public String getDefaultValueTipoPersona() {
		return null;
	}
		
	public boolean isEditableTipoPersona() {
		return true;
	}
	
	public boolean showCognome() {
		return true;
	}
	
	public String getTitleCognome() {
		return "Cognome";
	}	
	
	public boolean isRequiredCognome() {
		return false;
	}	
	
	public boolean isEditableCognome() {
		return true;
	}	
	
	public boolean showNome() {
		return true;
	}	
	
	public String getTitleNome() {
		return "Nome";
	}	
	
	public boolean isRequiredNome() {
		return false;
	}	
	
	public boolean isEditableNome() {
		return true;
	}	
	
	public boolean showRagioneSociale() {
		return true;
	}
		
	public String getTitleRagioneSociale() {
		return "Ragione sociale";
	}
		
	public boolean isRequiredRagioneSociale() {
		return false;
	}
		
	public boolean isEditableRagioneSociale() {
		return true;
	}	
	
	public boolean showCodFiscalePIVA() {
		return true;
	}	
	
	public String getTitleCodFiscalePIVA() {
		return "C.F./P.I.";
	}	
	
	public String getTitleCodFiscale() {
		return "C.F.";
	}	
	
	public boolean isRequiredCodFiscalePIVA() {
		return false;
	}	
	
	public boolean isEditableCodFiscalePIVA() {
		return true;
	}	 
	
	public boolean showImporto() {
		return true;
	}	
	
	public String getTitleImporto() {
		return "Importo";
	}	
	
	public boolean isRequiredImporto() {
		return false;
	}
		
	public boolean isEditableImporto() {
		return true;
	}
		
	public boolean showFlgPrivacy() {
		return false;
	}
	
	public String getTitleFlgPrivacy() {
		return null;
	}
	
	public boolean getDefaultValueAsBooleanFlgPrivacy() {
		return false;
	}
	
	public boolean isEditableFlgPrivacy() {
		return false;
	}
	
}
