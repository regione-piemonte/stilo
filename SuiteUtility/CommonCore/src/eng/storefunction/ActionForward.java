package eng.storefunction;

import  java.io.IOException;
import  java.util.Iterator;
import  javax.servlet.ServletException;
import  javax.servlet.ServletContext;
import  javax.servlet.http.HttpServletRequest;
import  javax.servlet.http.HttpServletResponse;

public class ActionForward  extends Action
{
  public ActionForward(String command)
  {
    super.addCommand(command);
  }

  public  void  execute(ServletContext context, HttpServletRequest request, HttpServletResponse response)
  {
    String  urlRedirect = super.getFirstCommand();
    try
    {
      context.getRequestDispatcher(urlRedirect).forward(request, response);
    }
    catch(Exception ex)
    {
	/* Da gestire */
    }
  }

  public  String  normalizeCommand(String command)
  {
    return  command;
  }
}
