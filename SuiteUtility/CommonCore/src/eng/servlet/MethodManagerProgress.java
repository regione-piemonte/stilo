package eng.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MethodManagerProgress extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_METHOD = "service";
	
	
	public void service(ServletRequest req, ServletResponse res)throws ServletException, IOException {
		//Chiamata alla classe di business
		String className = ((HttpServletRequest)req).getServletPath();
		int index = className.lastIndexOf(".");
		className = className.substring(1,index);
		Method method = null;
		String methodName = DEFAULT_METHOD;
		if(req.getParameter("method")!=null){
			methodName = req.getParameter("method").toLowerCase();
		}
		try{
			Class classe = Class.forName(className);
			method = classe.getMethod(methodName, new Class[]{HttpServletRequest.class,HttpServletResponse.class,HttpServlet.class});	
			method.invoke(classe.newInstance(), new Object[]{req,res,this});
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
