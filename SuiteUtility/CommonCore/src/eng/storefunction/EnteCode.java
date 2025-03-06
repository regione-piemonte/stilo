package eng.storefunction;

import  eng.database.exception.EngException;

import  java.sql.Types;

public class EnteCode extends HttpPrimitive
{
    public EnteCode(int position) throws EngException
    {
      this(position, null);
    }

    public EnteCode(int position, String name) throws EngException
    {
      super(position, Types.INTEGER, name);
      setModeIn();
    }

    public  void  makeValue(Object userCode)  throws EngException
    {
      setValuePrimitive(((String)userCode));
    }

    public  String  toString()
    {
      StringBuffer  sb = new StringBuffer("(EnteCode:");
      sb.append(super.toString());

      sb.append(")");
      return  sb.toString();
    }
    
    public Parameter cloneMe()  throws eng.database.exception.EngException {
        
      
        EnteCode p = new EnteCode(this.getPosition(), this.getName());
        if (this.isModeIn()) p.setModeIn();
        else if (this.isModeOut()) p.setModeOut();
        else if (this.isModeInOut()) p.setModeInOut();
        
        return p;
      
    }
    
}
