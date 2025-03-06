// 
// Decompiled by Procyon v0.5.36
// 

package eng.storefunction.parameters;

import java.io.Writer;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import it.eng.auriga.repository2.xml.sezionecache.Colonna;
import it.eng.auriga.repository2.xml.sezionecache.Riga;
import java.io.Reader;
import java.io.InputStream;
import it.eng.auriga.repository2.xml.sezionecache.Lista;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import eng.util.XMLUtil;
import java.sql.Connection;
import oracle.sql.CLOB;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import eng.database.exception.EngSqlNoApplException;
import java.util.Iterator;
import eng.database.modal.EngResultSet;
import eng.util.Logger;
import java.sql.Clob;
import java.util.Vector;
import eng.storefunction.StoreParameter;

public class ClobParameter extends StoreParameter
{
    private static final int LINES_MAXIMUM = 1000;
    public static final String COLUMN_SEPARATOR = "|*|";
    
    public ClobParameter(final int index, final String name, final Vector lines, final int inOut) {
        super(index, name, lines, 2005, inOut);
        if (this.value == null) {
            this.value = new Vector();
        }
    }
    
    public ClobParameter(final int index, final String name) {
        this(index, name, null, 2);
        this.value = new Vector();
    }
    
    public ClobParameter(final int index) {
        this(index, null, new Vector(), 2);
    }
    
    public ClobParameter(final int index, final String name, final Vector lines) {
        this(index, name, lines, 1);
    }
    
    public ClobParameter(final int index, final Vector lines) {
        this(index, null, lines, 1);
    }
    
    @Override
    public void setValue(final Object value) {
        if (value != null) {
            if (value instanceof Clob) {
                try {
                    final Clob c = (Clob)value;
                    Vector v = null;
                    v = getVectorFromClob(c);
                    this.value = v;
                }
                catch (Exception ex) {
                    Logger.getLogger().error((Object)("Exception in ClobParameter." + ex + "."));
                }
            }
            else if (value instanceof Vector) {
                this.value = value;
            }
            else {
                this.value = new Vector();
                ((Vector)this.value).add("" + value);
            }
        }
    }
    
    @Override
    public Object getValue() {
        return this.value;
    }
    
    public EngResultSet getEngResultSet() throws EngSqlNoApplException {
        final EngResultSet result = new EngResultSet();
        final Iterator lines = ((Vector)this.value).iterator();
        int cline = 0;
        while (lines.hasNext()) {
            final String[] columns = lines.next();
            for (int i = 0; i < columns.length; ++i) {
                result.addColumn(columns[i]);
            }
            result.addRow();
            ++cline;
        }
        return result;
    }
    
    @Override
    public String toString() {
        final Iterator lines = ((Vector)this.value).iterator();
        String[] line = null;
        String ret = "";
        while (lines.hasNext()) {
            line = lines.next();
            for (int i = 0; i < line.length; ++i) {
                final String str = line[i];
                ret = ret + str + "|*|";
            }
        }
        return ret;
    }
    
    @Override
    public void prepareStore(final CallableStatement stmt) throws SQLException, EngSqlNoApplException {
        if ((this.getInOut() & 0x1) != 0x0) {
            this.prepareStore((PreparedStatement)stmt);
        }
        if ((this.getInOut() & 0x2) != 0x0) {
            stmt.registerOutParameter(this.index, 2005);
        }
    }
    
    @Override
    public void prepareStore(final PreparedStatement stmt) throws SQLException, EngSqlNoApplException {
        if ((this.getInOut() & 0x1) != 0x0 && this.value != null) {
            final CLOB c = makeCLOB(stmt.getConnection(), (Vector)this.value);
            stmt.setClob(this.index, (Clob)c);
        }
        else {
            stmt.setNull(this.index, 2005);
        }
    }
    
    public static CLOB makeCLOB(final Connection connection, final Vector array) throws SQLException, EngSqlNoApplException {
        Logger.getLogger().info((Object)"Dentro makeCLOB di ClobParameter");
        try {
            for (int i = 0; i < array.size(); ++i) {
                array.set(i, XMLUtil.verySpecialEscape(array.get(i).toString()));
            }
            String valore = "";
            final String elementoVector = "";
            int contaColonne = 0;
            final StringBuffer sb = new StringBuffer("");
            sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            sb.append("<Lista xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n");
            for (int j = 0; j <= array.size() - 1; ++j) {
                sb.append("<Riga>\n");
                final String[] linea = (Object)array.elementAt(j);
                if (linea != null) {
                    contaColonne = 0;
                    for (int k = 0; k < linea.length; ++k) {
                        ++contaColonne;
                        valore = linea[k];
                        sb.append("<Colonna Nro=\"" + contaColonne + "\">" + XMLUtil.xmlEscape(new String(valore.getBytes("UTF-8")), 2) + "</Colonna>\n");
                    }
                }
                sb.append("</Riga>\n");
            }
            sb.append("</Lista>\n");
            Logger.getLogger().info((Object)("ClobParameter.MakeCLOB: XML = " + sb.toString()));
            final CLOB clobOracle = getCLOB(sb.toString(), connection);
            return clobOracle;
        }
        catch (UnsupportedEncodingException u) {
            throw new EngSqlNoApplException("Errore interno (104)" + u);
        }
        catch (ArrayStoreException ex) {
            throw new EngSqlNoApplException("Errore interno (105)" + ex);
        }
        catch (SQLException ex2) {
            throw new EngSqlNoApplException("Errore interno (106)");
        }
    }
    
    @Override
    public StoreParameter cloneMe() {
        return new ClobParameter(this.index, this.name, (Vector)this.value, this.inOut);
    }
    
    @Override
    public Object getAttributeValue() {
        EngResultSet rs = null;
        try {
            rs = this.getEngResultSet();
        }
        catch (Exception ex) {
            rs = null;
        }
        return rs;
    }
    
    public static Vector getVectorFromClob(final Clob c) {
        final Vector result = new Vector();
        try {
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
            Logger.getLogger().info((Object)"ENTRATO NELLA ECCEZIONE (Controlla Catalina.log).");
            ex.printStackTrace();
            return null;
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
    
    private static CLOB getCLOB(final String xmlData, final Connection conn) throws SQLException {
        Logger.getLogger().info((Object)"Dentro getCLOB di ClobParameter");
        CLOB tempClob = null;
        try {
            tempClob = CLOB.createTemporary(conn, true, 10);
            tempClob.open(1);
            final Writer tempClobWriter = tempClob.getCharacterOutputStream();
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
}
