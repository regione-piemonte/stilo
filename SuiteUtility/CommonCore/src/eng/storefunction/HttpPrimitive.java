package eng.storefunction;

import  eng.database.exception.EngException;

import  javax.servlet.http.HttpServletRequest;

public class HttpPrimitive  extends Parameter
{
    public  HttpPrimitive(int position, int type)
    {
      this(position, type, null);
    }

    public  HttpPrimitive(int position, int type, String name)
    {
      super(position, type, name);
    }
    
    public  HttpPrimitive(int position, int type, String name, String extName)
    {
      super(position, type, name, extName);
    }
    
    public  HttpPrimitive(int position, int type, String name, String extName, boolean required)
    {
      super(position, type, name, extName, required);
    }

    public  void  makeValue(Object httpRequest) throws EngException
    {
      String  name;
      String  value = null;

      name = getName();
      // 18/01/07 MRK Faccio in modo che provi prima a prendersi i dati dalla Attribute
      if (((HttpServletRequest)httpRequest).getAttribute(name) != null)
      {
        value = (String)((HttpServletRequest)httpRequest).getAttribute(name);
      }
      if (value == null)
      {
	      if (isNameAttribute(name))
	      {
	        Object  valueAttribute = ((HttpServletRequest)httpRequest).getAttribute(name);
	        value = (valueAttribute instanceof String) ? (String)valueAttribute : null;
	      }
	      else
	      {
	        value = ((HttpServletRequest)httpRequest).getParameter(name);
	      }
      }
      /*
      if (value == null)
      {
        //eng.util.Logger.getLogger().info(">>>>>>>>>> Controllo MakeValue ---> " + name + " e' nullo come parametro!");
        value = (String)((HttpServletRequest)httpRequest).getAttribute(name);
      }
      */

      if (value == null)
      {
        //eng.util.Logger.getLogger().info(">>>>>>>>>> Controllo MakeValue ---> " + name + " e' nullo!");
        value = "";
      }
      setValuePrimitive(value);
    }

    public  String  toString()
    {
      StringBuffer  sb = new StringBuffer("(HttpPrimitive:");
      sb.append(super.toString() + ",");

      sb.append(getName());

      sb.append(")");
      return  sb.toString();
    }

    public Parameter cloneMe()  throws EngException {

        HttpPrimitive p = new HttpPrimitive(this.getPosition(), this.getType(), this.getName());
        if (this.isModeIn()) p.setModeIn();
        else if (this.isModeOut()) p.setModeOut();
        else if (this.isModeInOut()) p.setModeInOut();

        return p;
    }

}
