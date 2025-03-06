package it.eng.core.business.exception;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.service.client.ExceptionType;

/**
 * se viene generata una eccezione in seguito ad errori durante l'invocazione di un servizio il core la wrappa in questo bean ( non usiamo una eccezione per
 * evitare di serializzarla)
 * 
 * @author Russo
 */
@XmlRootElement
public class CoreExceptionBean implements Serializable {

	private String msg;
	private String stackTraceMsg;
	private ExceptionType type = ExceptionType.MANAGED;
	private List<String> warnings = new ArrayList<String>();
	private Map<String, String> errori;
	/**
	 * 
	 */
	private static final long serialVersionUID = 724217370648736814L;

	public CoreExceptionBean() {
	}

	public CoreExceptionBean(String msg) {
		this.msg = msg;
	}

	public CoreExceptionBean(String msg, String stackTraceMsg) {
		this.msg = msg;
		this.stackTraceMsg = stackTraceMsg;
	}

	public CoreExceptionBean(Throwable aThrowable) {
		this.msg = aThrowable.getLocalizedMessage();
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		this.stackTraceMsg = result.toString();
		if (aThrowable instanceof ServiceException) {
			this.warnings = ((ServiceException) aThrowable).getWarnings();
			this.type = ((ServiceException) aThrowable).getType();
			this.errori = ((ServiceException) aThrowable).getErrori();

		} else {
			this.type = ExceptionType.UNMANAGED;
		}
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;

	}

	public String getStackTraceMsg() {
		return stackTraceMsg;
	}

	public void setStackTraceMsg(String stackTraceMsg) {
		this.stackTraceMsg = stackTraceMsg;
	}

	public List<String> getWarnings() {
		return warnings;
	}

	public void setWarnings(List<String> warnings) {
		this.warnings = warnings;
	}

	@Override
	public String toString() {
		return "CoreExceptionBean [msg=" + msg + "]";
	}

	public ExceptionType getType() {
		return type;
	}

	/**
	 * @return the errori
	 */
	public final Map<String, String> getErrori() {
		return errori;
	}

	/**
	 * @param errori
	 *            the errori to set
	 */
	public final void setErrori(Map<String, String> errori) {
		this.errori = errori;
	}

}
