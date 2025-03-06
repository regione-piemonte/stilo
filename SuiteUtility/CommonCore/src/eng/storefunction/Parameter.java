package eng.storefunction;

import it.eng.auriga.repository2.xml.sezionecache.SezioneCache;

import java.util.Vector;

import eng.database.exception.EngException;

public abstract class Parameter
{
    public    static  final int MODE_IN     = 1;
    public    static  final int MODE_OUT    = 2;
    public    static  final int MODE_INOUT  = 3;

    private   static  final int TYPE_ENGINEERING_ARRAY  = 22222;
    private   static  final String  ATTRIBUTE_SUFFIX    = "#attribute";

    private   final int       position;
    private   final int       type;
    private   final String    name;
    private   final String    extendedName;
    private   final boolean   required;
    private         int       mode            = MODE_IN;
    private         String    valuePrimitive  = null;
    private         Vector    valueArray      = null;
    private         SezioneCache    valueSezioneCache       = null;
    private         boolean   valueIsNull     = true;

    
    public  Parameter(int position, int type, String name, String extendedName, boolean required)
    {
      this.position     = position;
      this.type         = type;
      this.name         = name;
      this.extendedName = extendedName;
      this.required = required;
    }
    
    public  Parameter(int position, int type, String name, String extendedName)
    {
      this.position     = position;
      this.type         = type;
      this.name         = name;
      this.extendedName = extendedName;
      this.required = false;
    }
    
    public  Parameter(int position, int type, String name)
    {
      this.position     = position;
      this.type         = type;
      this.name         = name;
      this.extendedName = "";
      this.required = false;
    }

    //public  abstract  void  makeValue(Object o)  throws EngException, SQLException, IOException;
    public  abstract  void  makeValue(Object o)  throws EngException;

    public  String    getName()
    {
      return name;
    }

    public  int       getPosition()
    {
      return position;
    }

    public  int       getType()
    {
      return type;
    }
    
    public  String    getExtendedName()
    {
      return extendedName;
    }
    
    public  boolean    isRequired()
    {
      return required;
    }

    public  static  int getTypeArray()
    {
      return TYPE_ENGINEERING_ARRAY;
    }

    public  String    getValuePrimitive()
    {
      return valuePrimitive;
    }

    public  Vector    getValueArray()
    {
      return valueArray;
    }

    /*public Vector getValueClob() //Vector
    {
      return valueClob;
    }*/

    public  boolean   isModeIn()
    {
      return mode==MODE_IN;
    }

    public  boolean   isModeOut()
    {
      return mode==MODE_OUT;
    }

    public  boolean   isModeInOut()
    {
      return mode==MODE_INOUT;
    }

    public  static  boolean isNameAttribute(String name)
    {
      if (name != null)
        return  name.endsWith(ATTRIBUTE_SUFFIX);
      else
        return  false;
    }

    public  boolean   isPrimitive()
    {
      return (type!=TYPE_ENGINEERING_ARRAY && type!=java.sql.Types.CLOB) ;
    }

    public  boolean   isArray()
    {
      return type==TYPE_ENGINEERING_ARRAY;
    }

    public  boolean   isValueNull()
    {
      return valueIsNull;
    }

    void      setValuePrimitive(String value)
    {
      if (value == null)
      {
        value = "";
      }
      valuePrimitive  = value;
      valueIsNull     = valuePrimitive.equals("") ? true : false;
    }

    void    setValueArray(Vector value)
    {
      valueArray      = value;
      valueIsNull     = valueArray.size()==0 ? true : false;
    }

    /*void setValueClob(Vector value)
    {
    	valueClob = value;
    	valueIsNull = valueClob.size()==0 ? true : false;
    }*/


    public  Parameter setModeIn() throws EngException
    {
      mode       = MODE_IN;
      return this;
    }

    public  Parameter setModeInOut() throws EngException
    {
      mode       = MODE_INOUT;
      return this;
    }

    public  Parameter setModeOut() throws EngException
    {
      mode       = MODE_OUT;
      return this;
    }

    public  String    toString()
    {
      StringBuffer  sb = new StringBuffer("(Parameter:");

      sb.append(position + "," + type + "," + mode + "," + valueIsNull);

      if (valuePrimitive != null)
      {
        sb.append("," + valuePrimitive);
      }

      if (valueArray != null)
      {
        for (int i=0; i<valueArray.size(); i++)
        {
          sb.append("\n" + valueArray.elementAt(i) + ((i==(valueArray.size()-1))?"\n":","));
        }
      }
      
      /*if (valueClob != null)
      {
          for (int i=0; i<valueClob.size(); i++)
          {
            sb.append("\n" + valueClob.elementAt(i) + ((i==(valueClob.size()-1))?"\n":","));
          }
      }*/
      return  sb.toString();      
    }
    abstract public Parameter cloneMe()  throws EngException;

	public SezioneCache getValueSezioneCache() {
		return valueSezioneCache;
	}

	public void setValueSezioneCache(SezioneCache valueSezioneCache) 
	{
		this.valueSezioneCache = valueSezioneCache;
		valueIsNull     = valueSezioneCache==null ? true : false;
	}
}
