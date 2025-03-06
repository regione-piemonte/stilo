// 
// Decompiled by Procyon v0.5.36
// 

package eng.bean;

import java.util.Properties;
import java.io.Serializable;

public class UserProperties implements Serializable
{
    public static final String UP_ID_ENTE = "UP_ID_ENTE";
    public static final String UP_DOMINIO = "UP_DOMINIO";
    public static final String UP_NOME_UTENTE = "UP_NOME_UTENTE";
    public static final String UP_REAL_NOME_UTENTE = "UP_REAL_NOME_UTENTE";
    private Properties userProperties;
    
    public UserProperties(final String idEnte, final String dominio, final String user) {
        this.userProperties = new Properties();
        this.setIdEnte(idEnte);
        this.setDominio(dominio);
        this.setUser(user);
    }
    
    public String getUserProperty(final String name, final String default_value) {
        return this.userProperties.getProperty(name, default_value);
    }
    
    public String getUserProperty(final String name) {
        return this.userProperties.getProperty(name);
    }
    
    public void setUserProperty(final String name, final String value) {
        this.userProperties.setProperty(name, value);
    }
    
    public String getIdEnte() {
        return this.userProperties.getProperty("UP_ID_ENTE");
    }
    
    private void setIdEnte(final String val) {
        this.userProperties.setProperty("UP_ID_ENTE", nvl(val));
    }
    
    public String getDominio() {
        return this.userProperties.getProperty("UP_DOMINIO");
    }
    
    private void setDominio(final String val) {
        this.userProperties.setProperty("UP_DOMINIO", nvl(val));
    }
    
    public String getUser() {
        return this.userProperties.getProperty("UP_NOME_UTENTE");
    }
    
    private void setUser(final String val) {
        this.userProperties.setProperty("UP_NOME_UTENTE", nvl(val));
    }
    
    private static String nvl(final String s) {
        return (s == null) ? "" : s;
    }
}
