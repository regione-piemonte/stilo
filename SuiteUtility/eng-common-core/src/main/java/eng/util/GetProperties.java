// 
// Decompiled by Procyon v0.5.36
// 

package eng.util;

import javax.servlet.ServletContext;
import java.util.Hashtable;
import eng.singleton.EntePropertiesSingleton;
import eng.bean.UserProperties;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

public class GetProperties
{
    public static String getUserProperty(final String text, final HttpServletRequest req) {
        return getUserProperty(text, req.getSession());
    }
    
    public static String getUserProperty(final String text, final HttpSession session) {
        String res = "";
        if (text == null) {
            return res;
        }
        final UserProperties userProp = (UserProperties)session.getAttribute("userProperties");
        Logger.getLogger().info((Object)("session.getAttribute(userProperties) :" + session.getAttribute("userProperties")));
        res = userProp.getUserProperty(text);
        return res;
    }
    
    public static String getEnteProperty(final String text, final HttpServletRequest req) {
        return getEnteProperty(text, req.getSession());
    }
    
    public static String getEnteProperty(final String text, final HttpSession session) {
        String res = "";
        if (text == null) {
            return res;
        }
        final int codEnte = Integer.parseInt((String)session.getAttribute("idEnte"));
        res = getEnteProperty(codEnte, text);
        return res;
    }
    
    public static String getDominioProperty(final String text, final HttpServletRequest req) {
        return getDominioProperty(text, req.getSession());
    }
    
    public static String getDominioProperty(final String text, final HttpSession session) {
        String res = "";
        if (text == null) {
            return res;
        }
        final int codEnte = Integer.parseInt((String)session.getAttribute("idEnte"));
        final int dominio = Integer.parseInt((String)session.getAttribute("idDominioAut"));
        res = getEnteProperty(codEnte, dominio, text);
        return res;
    }
    
    public static String getEnteProperty(final int codEnte, final String text) {
        String res = "";
        if (text == null) {
            return res;
        }
        try {
            final Hashtable eps = EntePropertiesSingleton.getInstance(codEnte);
            res = eps.get(text);
        }
        catch (Exception e) {
            Logger.getLogger().error((Object)e.getMessage());
        }
        return res;
    }
    
    public static String getEnteProperty(final int codEnte, final int dominio, final String text) {
        String res = "";
        if (text == null) {
            return res;
        }
        try {
            final Hashtable eps = EntePropertiesSingleton.getInstance(codEnte, dominio);
            res = eps.get(text);
        }
        catch (Exception e) {
            Logger.getLogger().error((Object)e.getMessage());
        }
        return res;
    }
    
    public static String getApplicationProperty(final String text, final ServletContext sc) {
        String res = "";
        if (text == null) {
            return res;
        }
        try {
            res = sc.getInitParameter(text);
        }
        catch (Exception e) {
            Logger.getLogger().error((Object)e.getMessage());
        }
        return res;
    }
}
