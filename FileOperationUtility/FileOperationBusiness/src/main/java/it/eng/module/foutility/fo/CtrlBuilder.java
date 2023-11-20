/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.module.foutility.beans.generated.AbstractInputOperationType;
import it.eng.module.foutility.beans.generated.AbstractResponseOperationType;

public interface CtrlBuilder {

	public IFileController build(AbstractInputOperationType input);
	public IFileController getCTRLFromResponse(AbstractResponseOperationType response);
}
