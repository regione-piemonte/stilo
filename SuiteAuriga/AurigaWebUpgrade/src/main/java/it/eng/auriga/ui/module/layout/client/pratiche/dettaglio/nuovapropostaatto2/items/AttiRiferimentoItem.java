/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class AttiRiferimentoItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		AttiRiferimentoCanvas lAttiRiferimentoCanvas = new AttiRiferimentoCanvas(this);
		return lAttiRiferimentoCanvas;
	}
	
	public boolean isRequiredAttoRiferimento() {
		return false;
	}

	public boolean isFromDeterminaAggiudicaProceduraGara() {
		return false;
	}
	
	public boolean isFromDeterminaRimodulazioneSpesaGaraAggiudicata() {
		return false;
	}
	
	public boolean isFromRatificaDeliberaUrgenza() {
		return false;
	}
				
	public boolean showFlgPresentaASistemaItem() {
		return false;
	}
	
	public String getTitleFlgPresentaASistemaItem() {
		return null;
	}
	
	public String getDefaultValueFlgPresentaASistemaItem() {
		return null;
	}
	
	public boolean isRequiredFlgPresentaASistemaItem() {
		return false;
	}
	
	public boolean isEditabileFlgPresentaASistemaItem() {
		return false;
	}
		
	public String getTitleCategoriaReg() {
		return null;
	}
	
	public HashMap<String, String> getValueMapCategoriaReg() {
		return null;
	}
	
	public String getDefaultValueCategoriaReg() {
		return null;
	}
	
	public boolean showTipoAttoRif() {
		return false;
	}
	
	public String getTitleTipoAttoRif() {
		return null;
	}
	
	public boolean isRequiredTipoAttoRif() {
		return false;
	}
	
	public String getDefaultValueTipoAttoRif() {
		return null;
	}
	
	public String getTipoLoadComboTipoAttoRif() {
		return null;
	}
	
	public String getAltriParamLoadComboTipoAttoRif() {
		return null;
	}
	
	public boolean getFlgSoloVldLoadComboTipoAttoRif() {
		return false;
	}
	
}
