package eng.util;

/**
 * <p>Title: GetProperties </p>
 * <p>Description: Fornisce metodi statici per reperire le propriet? dell'utente, dell'ente e del sistema </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Engineering</p>
 * @author Nalesso Mirko
 * @version 1.0
 */

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import eng.bean.UserProperties;
import eng.singleton.EntePropertiesSingleton;

public class GetProperties {

	public GetProperties() {
	}

	// Metodo per reperire una propriet? dell'utente
	public static String getUserProperty(String text, HttpServletRequest req)
	{
		return getUserProperty(text,req.getSession());
	}

	// Metodo per reperire una propriet? dell'utente
	public static String getUserProperty(String text, HttpSession session)
	{
		String res = "";
		if( text == null) return res;
		UserProperties userProp=(UserProperties)session.getAttribute("userProperties");
		eng.util.Logger.getLogger().info("session.getAttribute(userProperties) :" + session.getAttribute("userProperties"));
		res = userProp.getUserProperty(text);
		return res;
	}

	// Metodo per reperire una propriet? dell'utente
	public static String getEnteProperty(String text, HttpServletRequest req)
	{
		return getEnteProperty(text,req.getSession());
	}

	// Metodo per reperire una propriet? dell'utente
	public static String getEnteProperty(String text, HttpSession session)
	{
		String res = "";
		if( text == null) return res;
		//UserProperties userProp=(UserProperties)session.getAttribute("userProperties");
		//int codEnte = Integer.parseInt(userProp.getUserProperty("UP_ID_ENTE"));

		int codEnte = Integer.parseInt((String)session.getAttribute(Costanti.SESSION_ID_ENTE));
		res = getEnteProperty(codEnte, text);
		return res;
	}
	
	// Metodo per reperire una propriet? dell'utente
	public static String getDominioProperty(String text, HttpServletRequest req)
	{
		return getDominioProperty(text,req.getSession());
	}

	// Metodo per reperire una propriet? dell'utente
	public static String getDominioProperty(String text, HttpSession session)
	{
		String res = "";
		if( text == null) return res;

		int codEnte = Integer.parseInt((String)session.getAttribute(Costanti.SESSION_ID_ENTE));
		int dominio = Integer.parseInt((String)session.getAttribute(Costanti.SESSION_ID_DOMINIO));
		
		res = getEnteProperty(codEnte, dominio, text);
		return res;
	}	


	// Metodo per reperire una propriet? dell'ente
	public static String getEnteProperty(int codEnte, String text)
	{
		String res = "";
		if( text == null) return res;

		try
		{
			java.util.Hashtable eps = EntePropertiesSingleton.getInstance(codEnte);
			res = (String)eps.get(text);
		}
		catch(Exception e)
		{
			eng.util.Logger.getLogger().error(e.getMessage());
		}
		return res;
	}
	
	// Metodo per reperire una propriet? dell'ente
	public static String getEnteProperty(int codEnte, int dominio, String text)
	{
		String res = "";
		if( text == null) return res;

		try
		{
			java.util.Hashtable eps = EntePropertiesSingleton.getInstance(codEnte, dominio);
			res = (String)eps.get(text);
		}
		catch(Exception e)
		{
			eng.util.Logger.getLogger().error(e.getMessage());
		}
		return res;
	}

	// Metodo per reperire una propriet? dell'applicazione
	public static String getApplicationProperty(String text, ServletContext sc)
	{
		String res = "";
		if( text == null) return res;

		try
		{
			res = sc.getInitParameter(text);
		}
		catch(Exception e)
		{
			eng.util.Logger.getLogger().error(e.getMessage());
		}
		return res;
	}

}
