// 
// Decompiled by Procyon v0.5.36
// 

package eng.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;

public class MethodManagerProgress extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_METHOD = "service";
    
    public void service(final ServletRequest req, final ServletResponse res) throws ServletException, IOException {
        String className = ((HttpServletRequest)req).getServletPath();
        final int index = className.lastIndexOf(".");
        className = className.substring(1, index);
        Method method = null;
        String methodName = "service";
        if (req.getParameter("method") != null) {
            methodName = req.getParameter("method").toLowerCase();
        }
        try {
            final Class classe = Class.forName(className);
            method = classe.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class, HttpServlet.class);
            method.invoke(classe.newInstance(), req, res, this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
