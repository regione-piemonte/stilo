package eng.storefunction;

import  eng.database.exception.EngException;

import  java.sql.Types;

public class UserCode extends HttpPrimitive
{
    public  UserCode(int position) throws EngException
    {
      this(position, null);
    }

    public  UserCode(int position, String name) throws EngException
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
      StringBuffer  sb = new StringBuffer("(UserCode:");
      sb.append(super.toString());

      sb.append(")");
      return  sb.toString();
    }
    
    public Parameter cloneMe()  throws eng.database.exception.EngException {
        
      
        UserCode p = new UserCode(this.getPosition(), this.getName());
        if (this.isModeIn()) p.setModeIn();
        else if (this.isModeOut()) p.setModeOut();
        else if (this.isModeInOut()) p.setModeInOut();
        
        return p;
      
    }
    
}
