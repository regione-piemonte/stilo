/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.module.foutility.beans.OutputOperations;
import it.eng.module.foutility.beans.generated.AbstractInputOperationType;

import java.util.ArrayList;
import java.util.List;

/**
 * controller di base per le operazioni sui file
 */
public abstract class AbstractFileController  implements IFileController {
	 
	//controller bloccante se fallisce
	protected boolean critical = false;
	
	public boolean isCritical() {
		return critical;
	}

	public void setCritical(boolean critical) {
		this.critical = critical;
	}

	private List<IFileController> predecessors= new ArrayList<IFileController>();
	 

	public List<IFileController> getPredecessors() {
		return predecessors;
	}

	public void setPredecessors(List<IFileController> predecessors) {
		this.predecessors = predecessors;
	}

	private int priority;
	
//	public boolean execute(InputFileBean input,OutputFileBean output)throws ExceptionController{
//		  execute((InputFileBean)input, (OutputFileBean)output);
//		 
//	}
	
	public abstract boolean execute(InputFileBean input,AbstractInputOperationType customInput, 
			OutputOperations output, String requestKey);

	 

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	
}
