// 
// Decompiled by Procyon v0.5.36
// 

package eng.servlet;

import java.util.Iterator;
import eng.database.exception.EngException;
import eng.util.Logger;
import eng.storefunction.Parameter;
import eng.util.Properties;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import eng.database.definition.ResultSelection;
import eng.database.modal.EngConnectionForSelection;
import java.util.Vector;

public class Commarea
{
    private Vector parameters;
    public EngConnectionForSelection connection;
    public ResultSelection resultSelection;
    public HttpServletRequest request;
    public HttpServletResponse response;
    public String accessSchemaName;
    public Properties accessSchemaDefinition;
    
    public Commarea() {
        this.parameters = null;
        this.connection = null;
        this.resultSelection = null;
        this.request = null;
        this.response = null;
        this.accessSchemaName = null;
        this.accessSchemaDefinition = null;
        this.parameters = new Vector();
    }
    
    public void putParameter(final Parameter p, final Object value) throws EngException {
        Parameter newParam = null;
        newParam = p.cloneMe();
        newParam.makeValue(value);
        Logger.getLogger().info((Object)("Parametro " + newParam.getPosition() + " " + newParam.getName() + " value: " + newParam.getValuePrimitive()));
        this.parameters.add(newParam);
    }
    
    public void putParameter(final Parameter p) throws EngException {
        Parameter newParam = null;
        newParam = p.cloneMe();
        this.parameters.add(newParam);
    }
    
    public Parameter getParameter(final int index) {
        return this.parameters.elementAt(index);
    }
    
    public Iterator getParametersIterator() {
        return this.parameters.iterator();
    }
    
    public void pulisci() {
        if (this.parameters != null) {
            this.parameters.removeAllElements();
        }
        this.parameters = null;
        this.connection = null;
        this.resultSelection = null;
        this.request = null;
        this.response = null;
        this.accessSchemaName = null;
        this.accessSchemaDefinition = null;
    }
}
