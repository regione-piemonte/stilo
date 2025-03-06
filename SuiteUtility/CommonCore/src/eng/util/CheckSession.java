package eng.util;

import javax.servlet.http.HttpSession;

public class CheckSession {

/**
 * Metodo che controlla l'esistenza di una sessione utente
 *
 * @param   HttpSession
 */
public static boolean check(HttpSession session)
{
    // verifica seesione nulla
    if (session == null)
    {
        return false;
    }
    // controlla la sessione per l'attributo che mi aspetto
//eng.util.Logger.getLogger().info("L'ogetto sessione esiste.Controllo " + eng.commercio.servlet.Login.SESSION_CONNECTION_TOKEN + ": " + session.getAttribute(eng.commercio.servlet.Login.SESSION_CONNECTION_TOKEN) );
    /* if( (session.getAttribute(eng.commercio.servlet.Login.SESSION_CONNECTION_TOKEN)==null) ||
        (session.getAttribute(eng.commercio.servlet.Login.SESSION_CONNECTION_TOKEN).toString().equals("")==true) ) */
    if ((session.getAttribute(Costanti.SESSION_CONNECTION_TOKEN)!=null) && (session.getAttribute(Costanti.SESSION_CONNECTION_TOKEN).equals("")))
    {
eng.util.Logger.getLogger().error("Sessione non valida. session.invalidate()" );
        session.invalidate();
        return false;
    }
//eng.util.Logger.getLogger().error("Sessione valida. return true;" );
    return true;
}


} // fine della classe
















