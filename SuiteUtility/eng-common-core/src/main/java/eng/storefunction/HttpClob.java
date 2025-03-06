// 
// Decompiled by Procyon v0.5.36
// 

package eng.storefunction;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import java.util.Iterator;
import it.eng.auriga.repository2.xml.sezionecache.Colonna;
import it.eng.auriga.repository2.xml.sezionecache.Riga;
import java.io.Reader;
import java.io.InputStream;
import it.eng.auriga.repository2.xml.sezionecache.Lista;
import java.io.InputStreamReader;
import java.sql.Clob;
import java.io.Writer;
import javax.servlet.http.HttpServletRequest;
import eng.database.modal.EngResultSet;
import java.sql.SQLException;
import eng.database.exception.EngSqlNoApplException;
import eng.util.Logger;
import eng.util.XMLUtil;
import oracle.sql.CLOB;
import java.util.Vector;
import java.sql.Connection;
import eng.database.exception.EngException;

public class HttpClob extends Parameter
{
    public static final String COLUMN_SEPARATOR = "|*|";
    private static final int COLUMN_MAXIMUM = 100;
    
    public HttpClob(final int position) throws EngException {
        this(position, null);
    }
    
    public HttpClob(final int position, final String name) throws EngException {
        super(position, 2005, name);
    }
    
    public static CLOB makeCLOB(final Connection connection, final Vector array) throws SQLException, EngSqlNoApplException {
        try {
            String valore = "";
            final String elementoVector = "";
            int contaColonne = 0;
            final StringBuffer sb = new StringBuffer("");
            sb.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
            sb.append("<Lista xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n");
            for (int i = 0; i <= array.size() - 1; ++i) {
                sb.append("<Riga>\n");
                final String[] linea = array.elementAt(i);
                if (elementoVector != null) {
                    contaColonne = 0;
                    for (int j = 0; j < linea.length; ++j) {
                        ++contaColonne;
                        valore = linea[j];
                        sb.append("<Colonna Nro=\"" + contaColonne + "\">" + XMLUtil.xmlEscapeEntities(valore) + "</Colonna>\n");
                    }
                }
                sb.append("</Riga>\n");
            }
            sb.append("</Lista>\n");
            Logger.getLogger().info((Object)("HttpClob.MakeCLOB: " + sb.toString()));
            final CLOB clobOracle = getCLOB(sb.toString(), connection);
            return clobOracle;
        }
        catch (ArrayStoreException ex) {
            throw new EngSqlNoApplException("Errore interno (105)" + ex);
        }
        catch (SQLException ex2) {
            throw new EngSqlNoApplException("Errore interno (106)");
        }
    }
    
    public void executeSetValueArray(final Vector lines) throws EngException {
        this.setValueArray(lines);
    }
    
    @Override
    public void makeValue(final Object httpRequest) throws EngException {
        final String name = this.getName();
        final Vector lines = this.getLista((EngResultSet)((HttpServletRequest)httpRequest).getAttribute(name));
        this.executeSetValueArray(lines);
    }
    
    private Vector getLista(final EngResultSet linee) throws EngSqlNoApplException {
        final Vector lines = new Vector();
        if (linee != null) {
            Vector riga = new Vector();
            for (int i = 1; i <= linee.numberRows(); ++i) {
                riga = linee.getRow(i);
                final String[] lineValue = new String[riga.size()];
                for (int j = 0; j < riga.size(); ++j) {
                    final String columnValue = riga.get(j);
                    lineValue[j] = columnValue;
                }
                lines.add(lineValue);
            }
        }
        return lines;
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("(HttpClob:");
        sb.append(super.toString() + ",");
        sb.append(")");
        return sb.toString();
    }
    
    @Override
    public Parameter cloneMe() throws EngException {
        final HttpClob p = new HttpClob(this.getPosition(), this.getName());
        if (this.isModeIn()) {
            p.setModeIn();
        }
        else if (this.isModeOut()) {
            p.setModeOut();
        }
        else if (this.isModeInOut()) {
            p.setModeInOut();
        }
        return p;
    }
    
    private static CLOB getCLOB(final String xmlData, final Connection conn) throws SQLException {
        CLOB tempClob = null;
        try {
            tempClob = CLOB.createTemporary(conn, true, 10);
            tempClob.open(1);
            final Writer tempClobWriter = tempClob.setCharacterStream(0L);
            tempClobWriter.write(xmlData);
            tempClobWriter.flush();
            tempClobWriter.close();
            tempClob.close();
        }
        catch (SQLException sqlexp) {
            tempClob.freeTemporary();
            sqlexp.printStackTrace();
        }
        catch (Exception exp) {
            tempClob.freeTemporary();
            exp.printStackTrace();
        }
        return tempClob;
    }
    
    public static Vector getVectorFromClob(final Clob c) {
        final Vector result = new Vector();
        try {
            if (c == null) {
                throw new Exception("Nessun XML da parsare");
            }
            final InputStream is = ((CLOB)c).binaryStreamValue();
            final Reader rdr = new InputStreamReader(is, "ISO-8859-1");
            Lista ls = null;
            ls = Lista.unmarshal(rdr);
            int maxNro = 1;
            for (int i = 0; i < ls.getRigaCount(); ++i) {
                final Riga r = ls.getRiga(i);
                for (int j = 0; j < r.getColonnaCount(); ++j) {
                    final Colonna col = r.getColonna(j);
                    if (col.getNro() > maxNro) {
                        maxNro = col.getNro();
                    }
                }
            }
            for (int i = 0; i < ls.getRigaCount(); ++i) {
                final Riga r = ls.getRiga(i);
                final String[] rigaV = new String[maxNro];
                for (int k = 0; k < r.getColonnaCount(); ++k) {
                    final Colonna col2 = r.getColonna(k);
                    rigaV[col2.getNro() - 1] = col2.getContent();
                }
                for (int k = 0; k < maxNro - 1; ++k) {
                    if (rigaV[k] == null) {
                        rigaV[k] = "";
                    }
                }
                result.add(rigaV);
            }
        }
        catch (Exception ex) {
            Logger.getLogger().info((Object)"Errore durante il l'elaborazione del codice XML");
            ex.printStackTrace();
            return null;
        }
        return result;
    }
    
    public static EngResultSet getEngResultSetFromClob(final Clob c) throws EngSqlNoApplException {
        final EngResultSet result = new EngResultSet();
        final Vector v = getVectorFromClob(c);
        if (v != null) {
            final Iterator lines = v.iterator();
            int cline = 0;
            while (lines.hasNext()) {
                final String[] line = lines.next();
                for (int i = 0; i < line.length; ++i) {
                    result.addColumn(line[i]);
                }
                result.addRow();
                ++cline;
            }
        }
        return result;
    }
    
    public static Node goToNodeElement(final String name, final Node node) {
        if (node.getNodeType() == 1 && node.getNodeName().equalsIgnoreCase(name)) {
            return node;
        }
        final NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); ++i) {
            final Node child = goToNodeElement(name, list.item(i));
            if (child != null) {
                return child;
            }
        }
        return null;
    }
    
    public static String readPCDATA(final Node textNode) {
        final NodeList list_child = textNode.getChildNodes();
        for (int ck = 0; ck < list_child.getLength(); ++ck) {
            final Node child_child = list_child.item(ck);
            if (child_child.getNodeType() == 4 || child_child.getNodeType() == 3) {
                return child_child.getNodeValue();
            }
        }
        return "";
    }
    
    public static Node getChildNode(final Node padre, final String nomeNodo) {
        final NodeList list = padre.getChildNodes();
        for (int i = 0; i < list.getLength(); ++i) {
            final Node child = list.item(i);
            if (child.getNodeType() == 1 && child.getNodeName().equalsIgnoreCase(nomeNodo)) {
                return child;
            }
        }
        return null;
    }
    
    public static String getNodeAttributeValue(final Node node, final String attrName, final String defaultVal) {
        final NamedNodeMap map = node.getAttributes();
        for (int i = 0; i < map.getLength(); ++i) {
            final Node child = map.item(i);
            if (child.getNodeType() == 2 && child.getNodeName().equalsIgnoreCase(attrName)) {
                return child.getNodeValue();
            }
        }
        return defaultVal;
    }
}
