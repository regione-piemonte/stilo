// 
// Decompiled by Procyon v0.5.36
// 

package eng.util;

import javax.servlet.http.HttpSession;

public class CheckSession
{
    public static boolean check(final HttpSession session) {
        if (session == null) {
            return false;
        }
        if (session.getAttribute("CodIdConnectionToken") != null && session.getAttribute("CodIdConnectionToken").equals("")) {
            Logger.getLogger().error((Object)"Sessione non valida. session.invalidate()");
            session.invalidate();
            return false;
        }
        return true;
    }
}
