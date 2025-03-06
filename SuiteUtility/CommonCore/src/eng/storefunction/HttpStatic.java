package eng.storefunction;

import  eng.database.exception.EngException;

import  java.sql.Types;

public class HttpStatic extends HttpPrimitive
{
    private static  final String  NAME_DEFAULT = "Valore fisso";
    private String value = "";

    public HttpStatic(int position, int tipo, String valore) throws EngException
    {
      super(position, tipo, NAME_DEFAULT );
      this.value = valore;

    }



    public String getValuePrimitive()
    {
        return this.value;
    }

    public void setValuePrimitive(String value)
    {

    }


    public  boolean isSuccess()
    {
        if (! super.isValueNull())
        {
            int     valore = Integer.parseInt(getValuePrimitive());
            return  valore == 1;
        }

        return  false;
    }

    public  void  makeValue(Object o)  throws EngException
    {
    }

    public  String  toString()
    {
      StringBuffer  sb = new StringBuffer("(Valore fisso:");
      sb.append(super.toString());

      sb.append(")");
      return  sb.toString();
    }

    public Parameter cloneMe()  throws eng.database.exception.EngException {


        HttpStatic p = new HttpStatic(this.getPosition(), this.getType(), this.getValuePrimitive());
        if (this.isModeIn()) p.setModeIn();
        else if (this.isModeOut()) p.setModeOut();
        else if (this.isModeInOut()) p.setModeInOut();

        return p;
    }

    /**
     * Getter for property value.
     * @return Value of property value.
     */
    public java.lang.String getValue() {
        return value;
    }

    /**
     * Setter for property value.
     * @param value New value of property value.
     */
    public void setValue(java.lang.String value) {
        this.value = value;
    }

    public  boolean   isValueNull()
    {
      return value == null;
    }
}
