package eng.storefunction;

import  eng.database.exception.EngException;

import  java.sql.Types;

public class IdLeaderCode extends HttpPrimitive
{
    public  IdLeaderCode(int position) throws EngException
    {
      this(position, null);
    }

    public  IdLeaderCode(int position, String name) throws EngException
    {
      super(position, Types.INTEGER, name);
      setModeIn();
    }

    public  void  makeValue(Object IdLeaderCode)  throws EngException
    {
      setValuePrimitive(((String)IdLeaderCode));
    }

    public  String  toString()
    {
      StringBuffer  sb = new StringBuffer("(IdLeaderCode:");
      sb.append(super.toString());

      sb.append(")");
      return  sb.toString();
    }
    
    public Parameter cloneMe()  throws eng.database.exception.EngException {
        
      
        IdLeaderCode p = new IdLeaderCode(this.getPosition(), this.getName());
        if (this.isModeIn()) p.setModeIn();
        else if (this.isModeOut()) p.setModeOut();
        else if (this.isModeInOut()) p.setModeInOut();
        
        return p;
      
    }
}
