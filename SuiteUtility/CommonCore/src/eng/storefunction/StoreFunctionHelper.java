package eng.storefunction;

import  eng.database.exception.EngException;

import  java.util.Vector;

public class StoreFunctionHelper
{
  private static  final String    ARRAY_SEPARATOR_NAMES   = "_";    //'_', underscore, Asci 95, Hex 0x5f, Unicode 0x0095.

  public  static  Parameter[] makeParametersDefault(boolean isFunction, String[] names, int[] types)
                              throws EngException
  {
      Vector  v = new Vector();
      int     arrayCounter;
      int     positionStart = 1;
      Parameter[] parameters = null;

      try
      {
        if (isFunction)
        {
          v.add(new ReturnCode(1));
          positionStart++;
        }
        for(int namesIndex=0, position=positionStart; namesIndex<names.length; namesIndex++, position++)
        {
          arrayCounter = extractParameterArrayCounter(names, namesIndex);
          if (arrayCounter == 0)
          {
              v.add(new HttpPrimitive(position, types[namesIndex], names[namesIndex]));
          }
          else
          {
              v.add(new HttpArray(position, null, names, namesIndex, arrayCounter));
              namesIndex += arrayCounter - 1;
          }
        }

        v.add(new ErrorCode(v.size() + 1));
        v.add(new ErrorMessage(v.size() + 1));

        parameters = new Parameter[v.size()];
        for (int i=0; i<v.size(); i++)
        {
          parameters[i] = (Parameter)v.elementAt(i);
        }
      }
      catch(EngException ex)
      {
        throw ex;
      }

      return  (parameters);
  }

  static  String  extractParameterArrayName(String name)
  {
    int index = name.indexOf(ARRAY_SEPARATOR_NAMES);

    if  (index == -1)
    {
      return null;
    }
    else
    {
      return name.substring(0, index);
    }
  }

  static  int     extractParameterArrayCounter(String[] names, int startIndex)
  {
    String  arrayNameStart = extractParameterArrayName(names[startIndex]);

    if  (arrayNameStart == null)
    {
      return 0;
    }
    else
    {
      String  arrayName;
      int     counter = 1;
      for (int i=startIndex + 1; i<names.length; i++)
      {
        arrayName = extractParameterArrayName(names[i]);
        if (arrayName == null)
        {
          break;
        }
        else
        {
          if (arrayName.equals(arrayNameStart))
          {
            counter++;
          }
          else
          {
            break;
          }
        }
      }

      return counter;
    }
  }
}
