// 
// Decompiled by Procyon v0.5.36
// 

package eng.util;

public class SafeJavaScriptStrings
{
    public static final String safeQuotes(final String a) {
        int idx = 0;
        int odx = 0;
        final int lnt = a.length();
        if ((idx = a.indexOf("'", 0)) == -1) {
            return a;
        }
        final StringBuffer buf = new StringBuffer();
        if (idx == 0) {
            buf.append("\\'");
            odx = 1;
        }
        else {
            buf.append(a.substring(odx, idx));
            buf.append("\\'");
            odx = idx + 1;
        }
        if (odx < lnt) {
            idx = a.indexOf("'", odx);
        }
        while (idx != -1 && odx < lnt) {
            buf.append(a.substring(odx, idx));
            buf.append("\\'");
            odx = idx + 1;
            if (odx < lnt) {
                idx = a.indexOf("'", odx);
            }
        }
        if (odx < lnt) {
            buf.append(a.substring(odx));
        }
        return buf.toString();
    }
    
    public static final String safeDoubleQuotes(final String a) {
        int idx = 0;
        int odx = 0;
        final int lnt = a.length();
        if ((idx = a.indexOf("\"", 0)) == -1) {
            return a;
        }
        final StringBuffer buf = new StringBuffer();
        if (idx == 0) {
            buf.append("\\\"");
            odx = 1;
        }
        else {
            buf.append(a.substring(odx, idx));
            buf.append("\\\"");
            odx = idx + 1;
        }
        if (odx < lnt) {
            idx = a.indexOf("\"", odx);
        }
        while (idx != -1 && odx < lnt) {
            buf.append(a.substring(odx, idx));
            buf.append("\\\"");
            odx = idx + 1;
            if (odx < lnt) {
                idx = a.indexOf("'", odx);
            }
        }
        if (odx < lnt) {
            buf.append(a.substring(odx));
        }
        return buf.toString();
    }
    
    public static void main(final String[] args) {
        System.out.println(safeQuotes("prova (safeQuotes no apici)"));
        System.out.println(safeQuotes("pro'va (safeQuotes apice singolo)"));
        System.out.println(safeQuotes("prova (safeQuotes apice singolo finale)'"));
        System.out.println(safeQuotes("'prova (safeQuotes apice singolo iniziale)"));
        System.out.println(safeQuotes("pro\"va (safeQuotes apice doppio)"));
        System.out.println(safeDoubleQuotes("prova (safeDoubleQuotes no doppi apici"));
        System.out.println(safeDoubleQuotes("pro\"va (safeDoubleQuotes apice doppio)"));
        System.out.println(safeDoubleQuotes("prova (safeDoubleQuotes apice doppio finale)\""));
        System.out.println(safeDoubleQuotes("\"prova (safeDoubleQuotes apice doppio) iniziale"));
        System.out.println(safeDoubleQuotes("pro'va (safeDoubleQuotes apice singolo)"));
        System.out.println("[" + safeQuotes("") + "] (safeQuotes stringa vuota");
        System.out.println("[" + safeDoubleQuotes("") + "] (safeDoubleQuotes stringa vuota");
    }
}
