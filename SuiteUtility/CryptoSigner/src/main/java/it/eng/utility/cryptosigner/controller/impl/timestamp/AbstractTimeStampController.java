package it.eng.utility.cryptosigner.controller.impl.timestamp;

import it.eng.utility.cryptosigner.controller.bean.InputBean;
import it.eng.utility.cryptosigner.controller.bean.InputTimeStampBean;
import it.eng.utility.cryptosigner.controller.bean.OutputBean;
import it.eng.utility.cryptosigner.controller.bean.OutputTimeStampBean;
import it.eng.utility.cryptosigner.controller.exception.ExceptionController;
import it.eng.utility.cryptosigner.controller.impl.AbstractController;

public abstract class AbstractTimeStampController extends AbstractController {
	
	public boolean execute(InputBean input,OutputBean output, boolean eseguiValidazioni)throws ExceptionController{
		if (input instanceof InputTimeStampBean && output instanceof OutputTimeStampBean)
			return execute((InputTimeStampBean)input, (OutputTimeStampBean)output, eseguiValidazioni);
		return false;
	}
	
	public abstract boolean execute(InputTimeStampBean input, OutputTimeStampBean output, boolean eseguiValidazioni) throws ExceptionController;

}
