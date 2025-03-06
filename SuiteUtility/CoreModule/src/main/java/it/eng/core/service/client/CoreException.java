package it.eng.core.service.client;

import it.eng.core.business.exception.CoreExceptionBean;

import java.util.List;
import java.util.Map;

/**
 * eccezione generata dal client quando viene rilevata un errore ritornato dal
 * core
 * @author Russo
 */
public class CoreException extends Exception {
    /**
	 * 
	 */
    private static final long serialVersionUID = -860316500783589555L;
    private final CoreExceptionBean bean;
    private Object extraBean;

    public CoreException(CoreExceptionBean bean) {
        this.bean = bean;
    }

    public final Map<String, String> getErrori() {
        if (bean != null)
            return bean.getErrori();
        else
            return null;
    }

    @Override
    public String getMessage() {
        String ret = "";
        if (bean != null) {
            ret = bean.getMsg();
        }
        return ret;
    }

    public String getDetails() {
        String ret = "";
        if (bean != null) {
            ret = bean.getStackTraceMsg();
        }
        return ret;
    }

    public List<String> getWarnings() {
        if (bean != null) {
            return bean.getWarnings();
        }
        return null;

    }

    public ExceptionType getType() {
        ExceptionType type = ExceptionType.UNMANAGED;
        if (bean != null) {
            type = bean.getType();
        }
        return type;
    }

}
