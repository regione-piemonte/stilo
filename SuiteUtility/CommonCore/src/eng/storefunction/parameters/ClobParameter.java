package eng.storefunction.parameters;


import  eng.storefunction.*;
import eng.util.XMLUtil;

import it.eng.auriga.repository2.xml.sezionecache.Colonna;
import it.eng.auriga.repository2.xml.sezionecache.Lista;
import it.eng.auriga.repository2.xml.sezionecache.Riga;

import  java.util.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.*;

import org.apache.commons.io.IOUtils;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import eng.database.exception.*;

import oracle.sql.CLOB;
import eng.database.modal.EngResultSet;


public class ClobParameter extends StoreParameter {


    // ATTENZIONE! DEVE essere uguale al numero di elementi di
    // string_varray definito nel database
    private static final int LINES_MAXIMUM           = 1000;

    // ATTENZIONE! DEVE essere uguale a quello definito nelle store
    // procedure e nelle JSP
    public  static final String COLUMN_SEPARATOR        = "|*|";

//    //Questo valore viene utilizzato solo per evitare che un eventuale
//    //errore di formattazione dati generi un loop infinito.
//    private static final int COLUMN_MAXIMUM          = 100;

    /**
     * Usato per un clob in input
     */
    public ClobParameter(int index, String name,Vector lines, int inOut)
    {
    	super(index, name, lines, Types.CLOB, inOut );
    	if (this.value == null)
    	{
    		this.value = new Vector();
    	}
    }

    /**
     * Usato per un clob in output
     */
    public ClobParameter(int index, String name)
    {
    	this(index,name,null,OUT);
		this.value = new Vector();
    }

    /**
     * Usato per un clob in output
     */
    public ClobParameter(int index )
    {
    	this(index,null,new Vector(), OUT );
    }

    /**
     * Usato per un clob in inout
     */
    public ClobParameter(int index, String name, Vector lines)
    {
    	this(index,name,lines,IN);
    }

    /**
     * Usato per un clob in inout
     */
    public ClobParameter(int index, Vector lines)
    {
    	this(index,null,lines,IN);
    }

    /**
     * Metodo che legge il Clob e lo trasforma in un Vector
     * da memorizzare nella classe
     */
    public void setValue(Object value)
    {
    	if (value != null)
    	{
    		//eng.util.Logger.getLogger().info("Imposto valore per Clob: " + value.getClass().getName());
    	}
    	else
    	{
    		return;
    	}
    	if (value instanceof Clob )
    	{
    		try 
    		{
    			Clob c = (Clob)value;
                Vector v = null;
                v = getVectorFromClob(c);
    			this.value = v;
    		} 
    		catch (Exception ex)
    		{
    			eng.util.Logger.getLogger().error("Exception in ClobParameter." + ex + ".");
    		}
    	}
    	else if (value instanceof Vector)
    	{
    		this.value = value;
    	}
    	else
    	{
    		this.value = new Vector();
    		((Vector)this.value).add("" +value);
    	}
    }

    /**
       Ritorna un EngResultSet o null in caso di errore.
    */
    public Object getValue()
    {
    	return this.value;
    }

    public EngResultSet getEngResultSet() throws EngSqlNoApplException
    {
    	EngResultSet result = new EngResultSet();
    	Iterator     lines = ((Vector)(this.value)).iterator();
    	Vector       line;

    	int cline = 0;
    	while(lines.hasNext())
    	{
//    		line = (Vector)lines.next();
    		String[] columns = (String[])lines.next();//makeColumns(line);
    		for (int i=0; i<columns.length; i++)
    		{
    			result.addColumn(columns[i]);
    		}
    		result.addRow();
    		cline++;
    	}
    	return result;
    }

    public String toString()
    {
    	Iterator     lines = ((Vector)(this.value)).iterator();
//    	String       line = "";
    	String[] line = null;
    	String ret = "";
    	while(lines.hasNext())
    	{
    		line = (String[])lines.next();
    		for (int i = 0; i < line.length; i++) {
				String str = line[i];
				ret += str + COLUMN_SEPARATOR;
			}
//    		line += (String)lines.next();
    	}
    	return ret;
    }


//    public EngResultSet getEngResultSet(String col_sep) throws EngSqlNoApplException
//    {
//    	EngResultSet result = new EngResultSet();
//    	Iterator     lines = ((Vector)(this.value)).iterator();
//    	String       line;
//
//    	int cline = 0;
//    	while(lines.hasNext())
//    	{
//    		line = (String)lines.next();
//    		Vector columns = makeColumns(line, col_sep);
//    		for (int i=0; i<columns.size(); i++)
//    		{
//    			result.addColumn((String)columns.elementAt(i));
//    		}
//    		result.addRow();
//    		cline++;
//    	}
//    	return result;
//    }


//     public  static  Vector  makeColumns(String line) throws  EngSqlNoApplException
//    {
//    	return makeColumns(line,COLUMN_SEPARATOR);
//    }
//
//    public  static  Vector  makeColumns(String line, String col_sep) throws  EngSqlNoApplException
//    {
//    	if (line !=null && line.startsWith("0x") && line.indexOf("|*|") < 0)
//    	{
//    		String new_line ="";
//    		// Stringa in formato binario su stringa...
//    		line = line.substring(2);
//    		while (line.length() > 0)
//    		{
//    			String cc = line.substring(0,2);
//    			line = line.substring(2);
//    			int i = Integer.parseInt(cc, 16);
//    			new_line = new_line + new String(new char[]{(char)i});
//    		}
//    		line = new_line;
//    	}
//
//    	Vector  v       = new Vector();
//    	String  column;
//    	int     posPrec = 0;
//    	int     posLast;
//
//    	if (line==null)
//    	{
//    		line = "";
//    	}
//
//    	while((posLast = line.indexOf(col_sep, posPrec))!=-1)
//    	{
//    		column = line.substring(posPrec, posLast);
//    		v.add(column);
//    		posPrec = posLast + col_sep.length();
//    		if (v.size() > COLUMN_MAXIMUM)
//    		{
//    			throw new EngSqlNoApplException("Errore interno (131)" + line + ".");
//    		}
//        //eng.util.Logger.getLogger().info("V :" + v);
//    	}
//    	return  v;
//    }


    public void prepareStore(CallableStatement stmt) throws SQLException, EngSqlNoApplException
    {
    	if ( (getInOut() & IN) != 0 )
    	{
    		prepareStore((PreparedStatement)stmt);
    	}

    	if ( (getInOut() & OUT) != 0 )
    	{
    		stmt.registerOutParameter( index, Types.CLOB );
    	}
    }

    public void prepareStore(PreparedStatement stmt) throws SQLException, EngSqlNoApplException
    {
    	if ( (getInOut() & IN) != 0  && value != null)
    	{
    		CLOB c = makeCLOB( stmt.getConnection() , (Vector)value);
    		stmt.setClob(index, c);
    	}
    	else
    	{
    		stmt.setNull( index,  Types.CLOB );
    	}
    }


    public static CLOB makeCLOB(Connection connection, Vector array) throws  SQLException, EngSqlNoApplException
    {
    	eng.util.Logger.getLogger().info("Dentro makeCLOB di ClobParameter" );

    	try
    	{
    		// Ciclo l'array per sostituire dei caratteri MOLTO speciali
    		for (int i=0; i<array.size(); i++)
    		{
    			array.set(i,eng.util.XMLUtil.verySpecialEscape(array.get(i).toString()));
    		}

    		String valore = "";
    		String elementoVector = "";
    		int contaColonne = 0;
    		StringBuffer  sb = new StringBuffer("");
    		
    		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
    		sb.append("<Lista xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n");
    		
    		/*
    		 * 	Il Vector della lista contiene per ogni riga una riga della lista da 
    		 * 	restituire in uscita con l'XML
    		 */
    		for (int i=0; i<=array.size()-1; i++)
    		{
    			sb.append("<Riga>\n");
    			String[] linea = (String[])array.elementAt(i);
    			if ( linea != null )
    			{
    				contaColonne=0;
    				for (int j = 0; j < linea.length; j++) {
    					contaColonne++;
    					valore = linea[j];
    					sb.append("<Colonna Nro=\"" + contaColonne + "\">" +  eng.util.XMLUtil.xmlEscape( new String(valore.getBytes("UTF-8")), 2 )+ "</Colonna>\n");
					}
    			}
    			
//    			elementoVector = eng.util.XMLUtil.xmlEscape( new String(((String)array.elementAt(i)).getBytes("UTF-8")), 2 );
//    			//elementoVector = eng.util.XMLUtil.xmlEscape( (String)array.elementAt(i) );
//    			if ( elementoVector != null )
//    			{
//    				contaColonne=0;
//    				while ( elementoVector.indexOf(COLUMN_SEPARATOR) >= 0 )
//    				{	
//    					eng.util.Logger.getLogger().info("dentro while"+elementoVector);
//    					contaColonne++;
//    					valore = elementoVector.substring(0,elementoVector.indexOf(COLUMN_SEPARATOR));
//    					eng.util.Logger.getLogger().info("valore letto :" + valore);
//    					sb.append("<Colonna Nro=\"" + contaColonne + "\">" + valore + "</Colonna>\n");
//    					elementoVector=elementoVector.substring(elementoVector.indexOf(COLUMN_SEPARATOR) + COLUMN_SEPARATOR.length() );
//    					eng.util.Logger.getLogger().info("elementoVectorDopo :"+elementoVector);
//    				}
//    			}
    			sb.append("</Riga>\n");
    		}
    		sb.append("</Lista>\n");
    		
    		eng.util.Logger.getLogger().info( "ClobParameter.MakeCLOB: XML = " + sb.toString() );
    		CLOB clobOracle = getCLOB( sb.toString(), connection);
    		return clobOracle;
    	}
    	catch(java.io.UnsupportedEncodingException u)
        {
      	  throw new EngSqlNoApplException("Errore interno (104)" + u);
        }
    	catch(ArrayStoreException ex)
    	{
    		throw new EngSqlNoApplException("Errore interno (105)" + ex);
    	}
    	catch(SQLException ex)
    	{
    		throw new EngSqlNoApplException("Errore interno (106)");
    	}
    }

    public StoreParameter cloneMe()
    {
    	return new ClobParameter(index, name, (Vector)value, inOut );
    }

    public Object getAttributeValue()
    {
    	EngResultSet rs = null;
    	try
    	{
    		rs = getEngResultSet();
    	} 
    	catch (Exception ex)
    	{
    		rs = null;
    	}
    	return rs;
    }

/*
 * Da HttpClob
 */

    public static Vector getVectorFromClob(Clob c)
    {
    	//eng.util.Logger.getLogger().info("Dentro getVectorFromClob di ClobParameter" );
        
        Vector result = new Vector();
    	
               // per ora effetuo il parse a mano in attesa di JAXB...
                try 
                {
                    java.io.InputStream is =((oracle.sql.CLOB)c).binaryStreamValue();
                    
        			//TODO Effettuo sempre l'unescape del clob xml
        			//RM 17/12/2010
                                                       
        			//InputStream is2 = IOUtils.toInputStream(XMLUtil.newUnescapeXML(IOUtils.toString(is))); 
                    
                    Reader rdr = new InputStreamReader(is, "ISO-8859-1");
                    Lista ls = null;
                    
                    ls = Lista.unmarshal(rdr);
                    
                    
                    int maxNro = 1;
                    //prendo il max numero come attributo di colonna contando su tutte
                    for (int i = 0; i < ls.getRigaCount(); i++) {
                    	Riga r = ls.getRiga(i);
                    	for (int j = 0; j < r.getColonnaCount(); j++) {
                    		Colonna col = r.getColonna(j);
                    		if (col.getNro() > maxNro) maxNro = col.getNro();
                    	}
                    }
                    
                    //ciclo sulle righe, per ogni riga metto un valore nella posizione corrispondente
                    for (int i = 0; i < ls.getRigaCount(); i++) {
                    	Riga r = ls.getRiga(i);
                        String[] rigaV = new String[maxNro];
                    	for (int j = 0; j < r.getColonnaCount(); j++) {
                    		Colonna col = r.getColonna(j);
                    		rigaV[col.getNro()-1] = col.getContent();
                    	}
                        //normalizzo a stringa vuota i campi nulli
                    	for (int j = 0; j < maxNro - 1; j++) {
                    		if (rigaV[j] == null) rigaV[j]="";
                    	}
                    	result.add(rigaV);
                    }          
                    
              
                    
//                    DOMParser parser = new DOMParser();
//                    String value = "";
//                    //String desc = "";
//                    parser.parse( new org.xml.sax.InputSource(is) );
//                    
//                    Document document = parser.getDocument();
//                                        
//                    // E' sufficiente prendere solo il primo nodo -Lista per ricavare a mano i Childs
//                    Node listaNode = goToNodeElement("Lista",document.getDocumentElement());
//                    if (listaNode == null) throw new Exception("Root element Lista non trovato nell'XML!");
//                    NodeList righe = listaNode.getChildNodes();
//                    int i,j,k;
//                    int flag = 0;
//                    int cont = 65000;
//                    String [][] struttura ;
//                    String [] struttura2 ;
//                    Node riga ;
//                    NodeList colonne ;
//		    Node elemento ;
//       		    String row = "";
//                    // faccio un primo ciclo di parsing che mi trova il valore massimo
//                    // del Nro di colonne...
//                    for ( i=0; i < righe.getLength(); ++i ) {
//                        riga = righe.item(i);
//                            if ( ( riga != null ) && (riga.getNodeName().equalsIgnoreCase("Riga")) )  {
//                 	       colonne = riga.getChildNodes();
//                               if (colonne != null ) 
//                                   for ( j=0; j < colonne.getLength(); ++j ) {
//                                       elemento = colonne.item(j);
//              			       if (elemento != null) {
//                                           value = getNodeAttributeValue(elemento,"Nro","");
//                                           if (Integer.parseInt(value) > flag ) { flag = Integer.parseInt(value); }
//                                           if (Integer.parseInt(value) < cont ) { cont = Integer.parseInt(value); }
//                                       }
//                                   }  
//                            }
//                    }
//                    
//                    // ora FLAG contiene il valore max di Nro e count il min....
//                    struttura = new String[flag+1][2] ; //in realtà crea un elemento in più...
//                    struttura2 = new String[flag+1] ; //in realtà crea un elemento in più...
//
//                    // questo ciclo scorre sia le righe che le colonne
//                    for ( i=0; i < righe.getLength(); ++i ) {
//            			riga = righe.item(i);
//                           if ( ( riga != null ) && (riga.getNodeName().equalsIgnoreCase("Riga")) )  {
//                    		colonne = riga.getChildNodes();
//                    		row = "";
//                                    if (colonne != null ) {
//                                        // qui devo svuotare struttura...anziche crearne una nuova
//                                        for ( k=0; k <= flag; ++k ) 
//                                            { struttura2[k]  = COLUMN_SEPARATOR; 
//                                              struttura[k][0]= ""; 
//                                              struttura[k][1]= COLUMN_SEPARATOR ; 
//                                        }
//                                        for ( j=0; j < colonne.getLength(); ++j ) {
//                                            elemento = colonne.item(j);
//                    			    if (elemento != null) {
//                    			        value = readPCDATA(elemento).trim();
//                                                struttura[j][1] = value + struttura[j][1];
//                                                struttura[j][0] = getNodeAttributeValue(elemento,"Nro","");
//                                            } 
//                                        }
//                                        // Riordino i valori letti per l'ordine di colonne da Oracle
//                                        // e li metto nella riga unica, per il resultset da restituire
//                                       for ( j=cont; j <= flag ; ++j ) {
//                                           for ( k=0; k < colonne.getLength() ; ++k ) {
//                                               if  ( Integer.parseInt(struttura[k][0]) == j ) 
//                                                   {  
//                                                    struttura2[j] = struttura[k][1];
//                                               } 
//                                           }
//                                        }
//                                     
//                                     // Aggiungere le colonne vuote   
//                                       for ( j=1; j <= flag ; j++ ) {
//                                                    row = row + struttura2[j]; 
//                                         }
//                                    }
//                       		result.addElement( row );
//                       		eng.util.Logger.getLogger().info("row:" + row );
//                            }
//               	    }
                    
                    
                    
                } catch (Exception ex)
                {
 eng.util.Logger.getLogger().info("ENTRATO NELLA ECCEZIONE (Controlla Catalina.log).");
                    ex.printStackTrace();
                    return null;
                } 	
                return result;
    }

    static public   Node goToNodeElement(String name, Node node )
    {
        if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equalsIgnoreCase(name))
        {
            return node;
        }

        NodeList list = node.getChildNodes();
        for (int i=0; i < list.getLength(); ++i)
        {
            Node child = goToNodeElement(name, list.item(i) );
            if (child != null) return child;
        }

        return null;
    }

    static public String readPCDATA(Node textNode)
    {
    	NodeList list_child = textNode.getChildNodes();
    	for (int ck=0; ck< list_child.getLength(); ck++)
    	{
             Node child_child = (Node)list_child.item(ck);
             if (child_child.getNodeType() == Node.CDATA_SECTION_NODE ||
                 child_child.getNodeType() == Node.TEXT_NODE)
             {
                   return (String)child_child.getNodeValue();
             }
    	}
    	return "";
    }

    static public  Node getChildNode(Node padre, String nomeNodo)
    {
          NodeList list = padre.getChildNodes();
          for (int i=0; i < list.getLength(); ++i)
          {
            // Procediamo con i nodi di primo livello sotto il nodo Dati...
            Node child = list.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equalsIgnoreCase(nomeNodo))
            {
               return child;
            }
          }
          return null;
    }

    static public  String getNodeAttributeValue(Node node, String attrName, String defaultVal)
    {
    	NamedNodeMap map = node.getAttributes();
    	for (int i=0; i< map.getLength(); ++i)
    	{
    		Node child = map.item(i);
    		if (child.getNodeType() == Node.ATTRIBUTE_NODE && child.getNodeName().equalsIgnoreCase(attrName))
    		{
    			return child.getNodeValue();
    		}	
    	}
    	return defaultVal;
    }

    private static CLOB getCLOB(String xmlData, Connection conn) throws SQLException{

    	eng.util.Logger.getLogger().info("Dentro getCLOB di ClobParameter" );

        CLOB tempClob = null;
  	  try{
  	    // If the temporary CLOB has not yet been created, create new
  	    tempClob = CLOB.createTemporary(conn.getMetaData().getConnection(), true, CLOB.DURATION_SESSION); 

  	    // Open the temporary CLOB in readwrite mode to enable writing
  	    tempClob.open(CLOB.MODE_READWRITE); 
  	    // Get the output stream to write
  	    Writer tempClobWriter = tempClob.getCharacterOutputStream(); 
  	    // Write the data into the temporary CLOB
  	    tempClobWriter.write(xmlData); 

  	    // Flush and close the stream
  	    tempClobWriter.flush();
  	    tempClobWriter.close(); 

  	    // Close the temporary CLOB 
  	    tempClob.close();    
  	  } catch(SQLException sqlexp){
  	    tempClob.freeTemporary(); 
  	    sqlexp.printStackTrace();
  	  } catch(Exception exp){
  	    tempClob.freeTemporary(); 
  	    exp.printStackTrace();
  	  }
  	  return tempClob; 
  	} 


}
