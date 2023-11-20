/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.function.bean.acta.ActaInputGetClassificazioneEstesa;
import it.eng.document.function.bean.acta.ActaInputGetDestinatariSmistamento;
import it.eng.document.function.bean.acta.ActaInputGetFascicoloDossierInVoceTitolario;
import it.eng.document.function.bean.acta.ActaOutputGetClassificazioneEstesa;
import it.eng.document.function.bean.acta.ActaOutputGetDestinatariSmistamento;
import it.eng.document.function.bean.acta.ActaOutputGetFascicoloDossierInVoceTitolario;

public interface EsportazioneDocActa {
	
	ActaOutputGetClassificazioneEstesa getClassificazioneEstesa(ActaInputGetClassificazioneEstesa input);
	
	ActaOutputGetFascicoloDossierInVoceTitolario getFascicoloDossierInVoceTitolario(ActaInputGetFascicoloDossierInVoceTitolario input);
	
	ActaOutputGetDestinatariSmistamento getDestinatariSmistamento(ActaInputGetDestinatariSmistamento input);

}