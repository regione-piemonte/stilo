/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.PrintWriter;
import java.io.StringWriter;

import org.codehaus.plexus.util.StringUtils;

import it.eng.aurigamailbusiness.bean.ResultBean;

public class LogUtil {
	
	public static final String stackTraceToString(Exception ex) {
		if (ex == null) return "";
		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}
	
	public static final <T> String exMessageAndCauseToString(ResultBean<T> resultBean) {
		if (resultBean != null) {
			String[] st = StringUtils.split(resultBean.getErrorStackTrace(), "\n");
			String cause = null;
			if (st != null && st.length > 0) {
				cause = st[0];
			} else {
				cause = "";
			}
			return exMessageAndCauseToString(new Exception(resultBean.getDefaultMessage(), new Exception(cause)));
		} else {
			return null;
		}
	}
	
	public static final String exMessageAndCauseToString(Throwable ex) {
		StringBuilder sb = new StringBuilder();
		if (ex != null) {
			if (ex.getMessage() != null) sb.append(ex.getMessage()).append(" (").append(ex.getClass().getName()).append(")");
			if (ex.getCause() != null && ex.getCause().getMessage() != null) {
				if (sb.length() > 0) sb.append(", ");
				sb.append("causa: ").append(ex.getCause().getMessage()).append(" (").append(ex.getCause().getClass().getName()).append(")");
			}
		}
		return sb.toString();
	}
}
