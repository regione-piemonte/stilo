package eng.storefunction;

import  eng.database.exception.EngException;

import  javax.servlet.http.HttpServletRequest;

public class HttpEnteDbParameter extends HttpPrimitive
{
	
	private static final String _DEFAULT_NAME = "Valore letto da DB";
	
    public  HttpEnteDbParameter(int position, int type, String name, String extName)
    {
      super(position, type, (name==null?_DEFAULT_NAME:name), extName);
    }
    
    public  HttpEnteDbParameter(int position, int type, String name, String extName, boolean required)
    {
      super(position, type, (name==null?_DEFAULT_NAME:name), extName, required);
    }

    public  void  makeValue(Object httpRequest) throws EngException
    {
      String  name;
      String  extName;
      String  value = null;

      name = getName(); //nome del parametro
      extName = getExtendedName(); //nome del parametro da prendere da DB
      
      // se viene passato il name cerco un eventuale valore dell'attribute/parameter
      // corrispondente al name
      if (name != null && !_DEFAULT_NAME.equals(name))
      {
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
      }
      
      // se il name è valorizzato o non ho trovato nulla nella request prendo il parametro da DB
      if (value == null) 
      {
    	  value = eng.util.GetProperties.getEnteProperty(extName,((HttpServletRequest)httpRequest).getSession());
      }

      if (value == null)
      {
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

        HttpEnteDbParameter p = new HttpEnteDbParameter(this.getPosition(), this.getType(), this.getName(), this.getExtendedName());
        if (this.isModeIn()) p.setModeIn();
        else if (this.isModeOut()) p.setModeOut();
        else if (this.isModeInOut()) p.setModeInOut();

        return p;
    }

}
