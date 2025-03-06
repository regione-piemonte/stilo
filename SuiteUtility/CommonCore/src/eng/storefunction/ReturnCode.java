package eng.storefunction;

import  eng.database.exception.EngException;

import  java.sql.Types;

public class ReturnCode extends HttpPrimitive
{
    private static  final String  NAME_DEFAULT = "ReturnCode";

    public  ReturnCode(int position) throws EngException
    {
      this(position, NAME_DEFAULT);
    }

    public  ReturnCode(int position, String name) throws EngException
    {
      /*ATTENZIONE! Il nome DEVE essere diverso da null,
        altrimenti la gestione dell'errore nel in qualche figlio di TableManager
        può funzionare non correttamente*/
      super(position, Types.INTEGER, name==null?NAME_DEFAULT:name);
      setModeOut();
    }

    public  boolean isSuccess()
    {
        if (! super.isValueNull())
        {
            int     valore = Integer.parseInt(super.getValuePrimitive());
            return  valore == 1;
        }

        return  false;
    }

    public  void  makeValue(Object o)  throws EngException
    {
    }

    public  String  toString()
    {
      StringBuffer  sb = new StringBuffer("(ReturnCode:");
      sb.append(super.toString());

      sb.append(")");
      return  sb.toString();
    }
    
         public Parameter cloneMe()  throws eng.database.exception.EngException {
        
      
        ReturnCode p = new ReturnCode(this.getPosition(), this.getName());
        if (this.isModeIn()) p.setModeIn();
        else if (this.isModeOut()) p.setModeOut();
        else if (this.isModeInOut()) p.setModeInOut();
        
        return p;
      
    }
}
