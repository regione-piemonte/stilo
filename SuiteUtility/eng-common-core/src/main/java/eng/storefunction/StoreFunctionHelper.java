// 
// Decompiled by Procyon v0.5.36
// 

package eng.storefunction;

import eng.database.exception.EngException;
import java.util.Vector;

public class StoreFunctionHelper
{
    private static final String ARRAY_SEPARATOR_NAMES = "_";
    
    public static Parameter[] makeParametersDefault(final boolean isFunction, final String[] names, final int[] types) throws EngException {
        final Vector v = new Vector();
        int positionStart = 1;
        Parameter[] parameters = null;
        try {
            if (isFunction) {
                v.add(new ReturnCode(1));
                ++positionStart;
            }
            for (int namesIndex = 0, position = positionStart; namesIndex < names.length; ++namesIndex, ++position) {
                final int arrayCounter = extractParameterArrayCounter(names, namesIndex);
                if (arrayCounter == 0) {
                    v.add(new HttpPrimitive(position, types[namesIndex], names[namesIndex]));
                }
                else {
                    v.add(new HttpArray(position, null, names, namesIndex, arrayCounter));
                    namesIndex += arrayCounter - 1;
                }
            }
            v.add(new ErrorCode(v.size() + 1));
            v.add(new ErrorMessage(v.size() + 1));
            parameters = new Parameter[v.size()];
            for (int i = 0; i < v.size(); ++i) {
                parameters[i] = v.elementAt(i);
            }
        }
        catch (EngException ex) {
            throw ex;
        }
        return parameters;
    }
    
    static String extractParameterArrayName(final String name) {
        final int index = name.indexOf("_");
        if (index == -1) {
            return null;
        }
        return name.substring(0, index);
    }
    
    static int extractParameterArrayCounter(final String[] names, final int startIndex) {
        final String arrayNameStart = extractParameterArrayName(names[startIndex]);
        if (arrayNameStart == null) {
            return 0;
        }
        int counter = 1;
        for (int i = startIndex + 1; i < names.length; ++i) {
            final String arrayName = extractParameterArrayName(names[i]);
            if (arrayName == null) {
                break;
            }
            if (!arrayName.equals(arrayNameStart)) {
                break;
            }
            ++counter;
        }
        return counter;
    }
}
