package it.eng.core.business.exception;

import it.eng.core.config.MessageHelper;
import it.eng.core.service.client.ExceptionType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Ecezzione generata dalla business Per la gestione di eventuali dati
 * aggiuntivi, utilizzare il field extraBean
 * @author Administrator
 */
public class ServiceException extends Exception implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7119111842814492560L;
    // contesto dell'errore o del warning
    private String errorcontext;
    // stringa identificativa dell'errore univoca nel contesto
    private String errorcode;
    // exceptiontype
    private ExceptionType type = ExceptionType.MANAGED;

    private List<WarningBean> warnings = new ArrayList<ServiceException.WarningBean>();

    private Object[] values = null;
    // not used
    private Locale locale = Locale.getDefault();

    /**
     * Mappa degli errori: per ogni field una stringa di errore.
     */
    private Map<String, String> errori;

    public ServiceException(String message, Map<String, String> errori) {
        super(message);
        this.errorcontext = message;
        this.errorcode = message;
        this.errori = errori;
    }

    /**
     * @param errorcontext
     * @param errorcode
     */
    public ServiceException(String errorcontext, String errorcode) {
        super("context:" + errorcontext + " code" + errorcode);
        this.errorcontext = errorcontext;
        this.errorcode = errorcode;
    }

    public ServiceException(ExceptionType type, String errorcontext, String errorcode) {
        super("context:" + errorcontext + " code" + errorcode);
        this.errorcontext = errorcontext;
        this.errorcode = errorcode;
    }

    /**
     * @param errorcontext
     * @param errorcode
     * @param values
     */
    public ServiceException(String errorcontext, String errorcode, Object... values) {
        super("context:" + errorcontext + " code" + errorcode);
        this.errorcontext = errorcontext;
        this.errorcode = errorcode;
        this.values = values;

    }

    public ServiceException(ExceptionType type, String errorcontext, String errorcode, Object... values) {
        super("context:" + errorcontext + " code" + errorcode);
        this.errorcontext = errorcontext;
        this.errorcode = errorcode;
        this.values = values;

    }

    public ServiceException(String errorcontext, String errorcode, Locale locale, Object... values) {
        super("context:" + errorcontext + " code" + errorcode);
        this.errorcontext = errorcontext;
        this.errorcode = errorcode;
        this.values = values;
        this.locale = locale;

    }

    /**
     * @param errorcontext
     * @param errorcode
     * @param cause
     */
    public ServiceException(String errorcontext, String errorcode, Throwable cause) {
        super("context:" + errorcontext + " code" + errorcode, cause);
        this.errorcontext = errorcontext;
        this.errorcode = errorcode;
    }

    public ServiceException(Throwable cause) {
        super(cause);
        this.errorcontext = cause.getMessage();
        this.errorcode = cause.getMessage();
    }

    public ServiceException(ExceptionType type, String errorcontext, String errorcode, Throwable cause) {
        super("context:" + errorcontext + " code" + errorcode, cause);
        this.errorcontext = errorcontext;
        this.errorcode = errorcode;
        this.type = type;
    }

    public ServiceException(String errorcontext, String errorcode, Throwable cause, Object... values) {
        super("context:" + errorcontext + " code" + errorcode, cause);
        this.errorcontext = errorcontext;
        this.errorcode = errorcode;
        this.values = values;
    }

    public ServiceException(ExceptionType type, String errorcontext, String errorcode, Throwable cause, Object... values) {
        super("context:" + errorcontext + " code" + errorcode, cause);
        this.errorcontext = errorcontext;
        this.errorcode = errorcode;
        this.values = values;
        this.type = type;
    }

    public List<String> getWarnings() {
        ArrayList<String> ret = new ArrayList<String>();
        for (WarningBean warnBean : warnings) {
            ret.add(MessageHelper.getMessage(this.errorcontext + "_" + warnBean.getWarnCode(), warnBean.getValues()));
        }
        return ret;
    }

    public String getErrorcontext() {
        return errorcontext;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public Object[] getValues() {
        return values;
    }

    @Override
    public String getLocalizedMessage() {
        return MessageHelper.getMessage(errorcontext + "_" + errorcode, values);
    }

    /**
     * messaggio da visaulizzare all'utente
     * @return
     */
    public String getUserMessage() {
        return getLocalizedMessage();
    }

    public ExceptionType getType() {
        return type;
    }

    public void setType(ExceptionType type) {
        this.type = type;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    // mange warnings

    class WarningBean {
        private String warnCode;
        private Object[] values = null;

        public WarningBean(String warnCode) {
            this.warnCode = warnCode;
        }

        public WarningBean(String warnCode, Object... values) {
            this.warnCode = warnCode;
            this.values = values;
        }

        public String getWarnCode() {
            return warnCode;
        }

        public void setWarnCode(String warnCode) {
            this.warnCode = warnCode;
        }

        public Object[] getValues() {
            return values;
        }

        public void setValues(Object[] values) {
            this.values = values;
        }
    }

    public void addWarning(String warncode) {
        this.warnings.add(new WarningBean(warncode));
    }

    public void addWarning(String warncode, Object... values) {
        this.warnings.add(new WarningBean(warncode, values));
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Map<String, String> getErrori() {
        return errori;
    }

    public void setErrori(Map<String, String> errori) {
        this.errori = errori;
    }

    public void setErrorcontext(String errorcontext) {
        this.errorcontext = errorcontext;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public void setWarnings(List<WarningBean> warnings) {
        this.warnings = warnings;
    }

    public void setValues(Object[] values) {
        this.values = values;
    }

}
