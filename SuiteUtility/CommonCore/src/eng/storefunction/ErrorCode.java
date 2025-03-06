package eng.storefunction;

import  eng.database.exception.EngException;

import  java.sql.Types;

public class ErrorCode extends HttpPrimitive
{
    private static  final String  NAME_DEFAULT = "ErrorCode";

    public  ErrorCode(int position) throws EngException
    {
      this(position, NAME_DEFAULT);
    }

    public  ErrorCode(int position, String name) throws EngException
    {
      /*ATTENZIONE! Il nome DEVE essere diverso da null,
        altrimenti la gestione dell'errore nel in qualche figlio di TableManager
        può funzionare non correttamente*/
      super(position, Types.NUMERIC, name==null?NAME_DEFAULT:name);
      setModeOut();
    }

    public  void  makeValue(Object o)  throws EngException
    {
    }

    public  String  toString()
    {
      StringBuffer  sb = new StringBuffer("(ErrorCode:");
      sb.append(super.toString());

      sb.append(")");
      return  sb.toString();
    }
    
    public Parameter cloneMe()  throws eng.database.exception.EngException {
        
      
        ErrorCode p = new ErrorCode(this.getPosition(), this.getName());
        if (this.isModeIn()) p.setModeIn();
        else if (this.isModeOut()) p.setModeOut();
        else if (this.isModeInOut()) p.setModeInOut();
        
        return p;
      
    }
}
