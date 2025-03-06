package eng.util;


import java.lang.StringBuffer;
/**
 * SafeJavaScriptStrings.java
 *
 * Classe statica che si preoccupa di fornire 2 filtri che assicurino
 * che le stringhe in output sono literal JavaScritp validi (per quel
 * che riguarda i caratteri " (ascii 34) e ' (ascii 39)
 *
 * Created: Thu Dec 18 15:28:21 2003
 *
 * @author <a href="mailto: saint@eng.it">Gian Uberto Lauri</a>
 * @version $Revision: 1.1.1.1 $
 *
 * Copyleft Gian Uberto Lauri
 *
 * Distributed under GNU LGPL
 */

public class SafeJavaScriptStrings
{
    /**
     * <code>safeSWithQuotes</code> Trasforma una stringa in una costante
     * String corretta Javascript delimitata da apici singoli. I caratteri
     * "'" vengono sostituiti con la stringa "\'"
     *
     * @param a di tipo <code>String</code> stringa originale (usualmente
     * prelevata da un DB)
     * @return <code>String</code> la stringa originale trasformata in un
     * corretto literal JavaScript
     */
    public static final String safeQuotes(String a)
    {
        int idx = 0;
        int odx = 0;
        int lnt = a.length();

        if ((idx = a.indexOf("'",0)) == -1)
        {
           return  a;

        } // end of if (a.indexOf("'",0) == -1)
        else
        {
            StringBuffer buf = new StringBuffer();

            // Primo passo, fuori dal loop per sveltirlo
            if (idx == 0)
            {
                buf.append("\\'");
                odx = 1;
            }
            else
            {
                buf.append(a.substring(odx,idx));
                buf.append("\\'");
                odx = idx + 1;
            }

            if (odx < lnt) idx = a.indexOf("'",odx);

            // Adesso il loop
            while (idx != -1 && odx < lnt )
            {
                buf.append(a.substring(odx,idx));
                buf.append("\\'");
                odx = idx + 1;
                if (odx < lnt) idx = a.indexOf("'",odx);
            }

            // Parte rimanente della stringa
            if (odx < lnt)
            {
                buf.append(a.substring(odx));
            } // end of if (odx < lnt)

            return buf.toString();

        } // end of if (a.indexOf("'",0) == -1)else

    }

    /**
     * <code>safeStringWithDoubleQuotes</code> Trasforma una stringa in una
     * costante String corretta Javascript delimitata da apici singoli. I
     * caratteri '"' vengono sostituiti con la stringa '\"'
     *
     * @param a di tipo <code>String</code> stringa originale (usualmente
     * prelevata da un DB)
     * @return <code>String</code> la stringa originale trasformata in un
     * corretto literal JavaScript
     */
    public static final String safeDoubleQuotes(String a)
    {
        int idx = 0;
        int odx = 0;
        int lnt = a.length();

        if ((idx = a.indexOf("\"",0)) == -1)
        {
            return a;
        } // end of if (a.indexOf("\"",0) == -1)
        else
        {
            StringBuffer buf = new StringBuffer();

            // Primo passo, fuori dal loop per sveltirlo
            if (idx == 0)
            {
                buf.append("\\\"");
                odx = 1;
            }
            else
            {
                buf.append(a.substring(odx,idx));
                buf.append("\\\"");
                odx = idx + 1;
            }

            if (odx < lnt) idx = a.indexOf("\"",odx);

            // Adesso il loop
            while (idx != -1 && odx < lnt )
            {
                buf.append(a.substring(odx,idx));
                buf.append("\\\"");
                odx = idx + 1;
                if (odx < lnt) idx = a.indexOf("'",odx);
            }

            // parte rimanente della stringa
            if (odx < lnt)
            {
                buf.append(a.substring(odx));
            } // end of if (odx < lnt)

            return buf.toString();

        } // end of if (a.indexOf("'",0) == -1)else
    }

    public static void main (String[] args)
    {
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
    } // end of main ()

}// SafeJavaScriptStrings
