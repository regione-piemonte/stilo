package eng.bean;

/**
 * <p>Title: UserProperties </p>
 * <p>Description: Memorizza le proprietà dell'utente collegato </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: Engineering S.p.A</p>
 * @author Antonio Vendramini
 * @version 1.0
 */

import java.util.Properties;

public class UserProperties  implements java.io.Serializable {

    /* Costanti per la lettura dei parametri */
    public static final String UP_ID_ENTE = "UP_ID_ENTE";
    public static final String UP_DOMINIO = "UP_DOMINIO";
    public static final String UP_NOME_UTENTE = "UP_NOME_UTENTE";
    public static final String UP_REAL_NOME_UTENTE = "UP_REAL_NOME_UTENTE";

    private Properties userProperties;

    public UserProperties(String idEnte, String dominio, String user) {
        this.userProperties = new Properties();
        this.setIdEnte(idEnte);
        this.setDominio(dominio);
        this.setUser(user);
    }

    public String getUserProperty(String name, String default_value)
    {
      return userProperties.getProperty(name, default_value);
    }

    public String getUserProperty(String name)
    {
      return userProperties.getProperty(name);
    }

    public void setUserProperty(String name, String value)
    {
        userProperties.setProperty(name,value);
    }

    public String getIdEnte()
    {
      return userProperties.getProperty(this.UP_ID_ENTE );
    }


    private void setIdEnte(String val)
    {
         userProperties.setProperty(this.UP_ID_ENTE , nvl(val));
    }


    public String getDominio()
    {
      return userProperties.getProperty(this.UP_DOMINIO );
    }


    private void setDominio(String val)
    {
         userProperties.setProperty(this.UP_DOMINIO , nvl(val));
    }


    public String getUser()
    {
      return userProperties.getProperty(this.UP_NOME_UTENTE );
    }


    private void setUser(String val)
    {
	 userProperties.setProperty(this.UP_NOME_UTENTE , nvl(val));
    }

    private static String nvl(String s)
    {
      return (s == null) ? "" : s;
    }
}
