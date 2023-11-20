/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.module.foutility.beans.OutputOperations;
import it.eng.module.foutility.beans.generated.AbstractInputOperationType;

import java.util.List;

public interface IFileController {
	public boolean execute(InputFileBean input,AbstractInputOperationType customInput, OutputOperations output, String requestKey);
	public int getPriority();
	public List<IFileController> getPredecessors();
	public boolean isCritical() ;
	public String getOperationType() ;
}
