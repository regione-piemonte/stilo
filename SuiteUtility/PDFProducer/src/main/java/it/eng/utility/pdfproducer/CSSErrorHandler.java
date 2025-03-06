package it.eng.utility.pdfproducer;

import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.CSSParseException;

import com.steadystate.css.parser.HandlerBase;

public class CSSErrorHandler extends HandlerBase {

	private String result;

	@Override
	public void error(CSSParseException exception) throws CSSException {
		switch (exception.getCode()) {
		case CSSParseException.SAC_SYNTAX_ERR:
			final String msg = exception.getLocalizedMessage();
			if (msg != null) {// Error in @page rule. (Invalid token "WordSection1". Was expecting one of: <S>, <LBRACE>, ":".)
				final int k = msg.indexOf("@page");
				if (k != -1) {// trovato
					final String[] array = msg.split("Invalid token \"");
					if (array.length > 1) {
						final int h = array[1].indexOf("\"");
						if (h != -1) {
							result = array[1].substring(0, h);
						}
					}
				}
			}
		default:
			super.error(exception);
			break;
		}

	}

	public String getResult() {
		return result;
	}

}
