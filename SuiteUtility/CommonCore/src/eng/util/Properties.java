package eng.util;

public class Properties extends java.util.Properties {

	public void put(String key, Object value) {
	   if (value != null)
	   	if ((new String()).getClass().isInstance(value))
		{
		   if (!(((String)value).trim().length() == 0 ||
              ((String)value).trim().equals("null")))
			 super.put(key,value);

		}
		else
		   super.put(key,value);
	}

}
