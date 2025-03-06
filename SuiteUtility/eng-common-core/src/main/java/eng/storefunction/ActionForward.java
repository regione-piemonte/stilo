// 
// Decompiled by Procyon v0.5.36
// 

package eng.storefunction;

import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;

public class ActionForward extends Action
{
    public ActionForward(final String command) {
        super.addCommand(command);
    }
    
    public void execute(final ServletContext context, final HttpServletRequest request, final HttpServletResponse response) {
        final String urlRedirect = super.getFirstCommand();
        try {
            context.getRequestDispatcher(urlRedirect).forward((ServletRequest)request, (ServletResponse)response);
        }
        catch (Exception ex) {}
    }
    
    @Override
    public String normalizeCommand(final String command) {
        return command;
    }
}
