/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.function.bean.SibInputAggiornaAtto;
import it.eng.document.function.bean.SibInputCreaProposta;
import it.eng.document.function.bean.SibInputElencoDettagliContabili;
import it.eng.document.function.bean.SibInputGetCapitolo;
import it.eng.document.function.bean.SibInputGetVociPeg;
import it.eng.document.function.bean.SibInputGetVociPegVLiv;
import it.eng.document.function.bean.SibOutputAggiornaAtto;
import it.eng.document.function.bean.SibOutputCreaProposta;
import it.eng.document.function.bean.SibOutputElencoDettagliContabili;
import it.eng.document.function.bean.SibOutputGetCapitolo;
import it.eng.document.function.bean.SibOutputGetVociPeg;

public interface ContabilitaSIB {

	SibOutputAggiornaAtto sibAggiornaAtto(SibInputAggiornaAtto input);

	SibOutputCreaProposta sibCreaProposta(SibInputCreaProposta input);

	SibOutputElencoDettagliContabili sibElencoDettagliContabili(SibInputElencoDettagliContabili input);

	SibOutputGetVociPeg sibGetVociPeg(SibInputGetVociPeg input);

	SibOutputGetVociPeg sibGetVociPegCapitolo(SibInputGetVociPeg input);

	SibOutputGetVociPeg sibGetVociPegVLiv(SibInputGetVociPegVLiv input);

	SibOutputGetCapitolo sibGetCapitolo(SibInputGetCapitolo input);
}
