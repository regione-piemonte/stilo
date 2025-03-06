package eng.storefunction;

import  eng.database.exception.EngException;

import  java.sql.Types;

public class ErrorMessage extends HttpPrimitive
{
    private static  final String  NAME_DEFAULT = "ErrorMessage";

    public  ErrorMessage(int position) throws EngException
    {
      this(position, NAME_DEFAULT);
    }

    public  ErrorMessage(int position, String name) throws EngException
    {
      /*ATTENZIONE! Il nome DEVE essere diverso da null,
        altrimenti la gestione dell'errore nel in qualche figlio di TableManager
        può funzionare non correttamente*/
      super(position,  Types.VARCHAR, name==null?NAME_DEFAULT:name);
      setModeOut();
    }

    public  void  makeValue(Object o)  throws EngException
    {
    }

    public  String  toString()
    {
      StringBuffer  sb = new StringBuffer("(ErrorMessage:");
      sb.append(super.toString());

      sb.append(")");
      return  sb.toString();
    }
    
     public Parameter cloneMe()  throws eng.database.exception.EngException {
        
      
        ErrorMessage p = new ErrorMessage(this.getPosition(), this.getName());
        if (this.isModeIn()) p.setModeIn();
        else if (this.isModeOut()) p.setModeOut();
        else if (this.isModeInOut()) p.setModeInOut();
        
        return p;
      
    }
}
