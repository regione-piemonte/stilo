package eng.storefunction;

import it.eng.auriga.repository2.xml.sezionecache.Colonna;
import it.eng.auriga.repository2.xml.sezionecache.Lista;
import it.eng.auriga.repository2.xml.sezionecache.Riga;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import oracle.sql.CLOB;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import eng.database.exception.EngException;
import eng.database.exception.EngSqlNoApplException;
import eng.database.modal.EngResultSet;

public class HttpClob extends Parameter
{
    //private static final int LINES_MAXIMUM = 1000;
    public static  final String COLUMN_SEPARATOR = "|*|";           
    private static  final int       COLUMN_MAXIMUM          = 100;              
    
    public  HttpClob(int position) throws EngException
    {
    	this(position, null);
    }

    public  HttpClob(int position, String name) throws EngException
    {
    	super(position, java.sql.Types.CLOB , name);
    }

    public static CLOB makeCLOB(Connection connection, Vector array)
                            throws  SQLException, EngSqlNoApplException
    {
      try
      {
      	// Ciclo l'array per sostituire dei caratteri MOLTO speciali
    	/*
        for (int i=0; i<array.size(); i++)
        {
            array.set(i,eng.util.XMLUtil.verySpecialEscape(array.get(i).toString()));
        }
        */
        
        String valore = "";
        String elementoVector = "";
        int contaColonne = 0;
        StringBuffer  sb = new StringBuffer("");
        
        sb.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
        sb.append("<Lista xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n");

        /*
         * Il Vector della lista contiene per ogni riga una riga della lista da 
         * restituire in uscita con l'XML
         */
        for (int i=0; i<=array.size()-1; i++)
        {
        	sb.append("<Riga>\n");
        	//elementoVector = eng.util.XMLUtil.xmlEscape( (String)array.elementAt(i), 2 );
        	//elementoVector = eng.util.XMLUtil.xmlEscape( new String(((String)array.elementAt(i)).getBytes("UTF-8")), 2 );
        	//elementoVector = new String(((String)array.elementAt(i)).getBytes("UTF-8"));
        	String[] linea = (String[])array.elementAt(i);
        	if ( elementoVector != null )
        	{
				contaColonne=0;
				for (int j = 0; j < linea.length; j++) {
					contaColonne++;
					valore = linea[j];
					sb.append("<Colonna Nro=\"" + contaColonne + "\">" + eng.util.XMLUtil.xmlEscapeEntities(valore) + "</Colonna>\n");
				}
        	}
        	sb.append("</Riga>\n");
        }
        sb.append("</Lista>\n");

        eng.util.Logger.getLogger().info( "HttpClob.MakeCLOB: " + sb.toString() );
        CLOB clobOracle = getCLOB( sb.toString(), connection);
        return clobOracle;
      }
      //catch(java.io.UnsupportedEncodingException u)
      //{
    //	  throw new EngSqlNoApplException("Errore interno (104)" + u);
      //}
      catch(ArrayStoreException ex)
      {
        throw new EngSqlNoApplException("Errore interno (105)" + ex);
      }
      catch(SQLException ex)
      {
        throw new EngSqlNoApplException("Errore interno (106)");
      }
    }

//    public  static  Vector  makeColumns(String line) throws  EngSqlNoApplException
//    {
//      if (line !=null && line.startsWith("0x") && line.indexOf("|*|") < 0)
//      {
//      	String new_line ="";
//      	// Stringa in formato binario su stringa...
//      	line = line.substring(2);
//      	while (line.length() > 0)
//      	{
//      		String cc = line.substring(0,2);
//      		line = line.substring(2);
//      		int i = Integer.parseInt(cc, 16);
//      		new_line = new_line + new String(new char[]{(char)i});
//      			
//      	}	
//      	line = new_line;
//
//      }
//    
//      Vector  v       = new Vector();
//      String  column;
//      int     posPrec = 0;
//      int     posLast;
//
//      if (line==null)
//      {
//        line = "";
//      }
//
//      while((posLast = line.indexOf(COLUMN_SEPARATOR, posPrec))!=-1)
//      {
//          column = line.substring(posPrec, posLast);
//          v.add(column);
//          posPrec = posLast + COLUMN_SEPARATOR.length();
//          if (v.size() > COLUMN_MAXIMUM)
//          {
//            throw new EngSqlNoApplException("Errore interno (131)" + line + ".");
//          }
//      }
//      return  v;
//    }
    
    public void  executeSetValueArray(Vector  lines) throws EngException
    {
    	setValueArray(lines);
    }
    
    public void  makeValue(Object httpRequest) throws EngException
    {
    	Vector  lines;
    	String name = getName();
    	lines   = getLista((EngResultSet)((HttpServletRequest)httpRequest).getAttribute(name));
    	executeSetValueArray(lines);
    }

    private Vector getLista(EngResultSet linee) throws EngSqlNoApplException
    {
    	Vector lines = new Vector();
//    	StringBuffer lineValue = new StringBuffer();
    	String columnValue;

    	// Verifica la presenza della lista in cache oppure
    	// che la lista abbia almeno una riga
    	if (linee!=null)
    	{    		
    		// scorro l'EngResultSet per costruire il Vector 
    		Vector riga = new Vector(); 
    		for (int i=1; i <= linee.numberRows(); i++ )
    		{
    			riga = linee.getRow(i);
    			String[] lineValue = new String[riga.size()];
    			for (int j=0; j<riga.size(); j++)
    			{
    				columnValue = (String)riga.get(j);
    				lineValue[j] = columnValue;
    			}
    			lines.add(lineValue);
    		}
    	}
    	return lines;
	}

    /*
    public void  makeValue(Object httpRequest) throws EngException
    {
    	Vector  lines;
    	lines   = getListaFromCache((HttpServletRequest)httpRequest, this.getNomeLista(), this.getNumBackLevel() );
    	setValueArray(lines);
    }

    private Vector getListaFromCache(HttpServletRequest request, String nomeLista, int numBackLevel) throws EngSqlNoApplException
    {
    	Vector lines = new Vector();
    	StringBuffer lineValue = new StringBuffer();
    	String columnValue;

    	// viene reperita la lista dalla Cache
    	PageCacheAdmin pca = (eng.cache.PageCacheAdmin)request.getSession().getAttribute(PageCacheAdmin._SESSION_ATT_NAME_PAGE_CACHE);
    	AbstractCacheElement ags = pca.getElement(pca.getLivello() - numBackLevel);
    	TableData td = ags.getTableParam(nomeLista);

    	// Verifica la presenza della lista in cache oppure
    	// che la lista abbia almeno una riga
    	if (td != null && td.getNumRighe()>0)
    	{
    		EngResultSet linee = ags.getTableResultSet(nomeLista);      
    		//td.getResultSet();
    		
    		// scorro l'EngResultSet per costruire il Vector 
    		Vector riga = new Vector(); 
    		for (int i=1; i <= linee.numberRows(); i++ )
    		{
    			riga = linee.getRow(i);
    			for (int j=0; j<riga.size(); j++)
    			{
    				columnValue = (String)riga.get(j);
    				lineValue.append(columnValue + COLUMN_SEPARATOR);
    			}
    			lines.add(lineValue.toString());
    			lineValue.delete(0,lineValue.length());
    		}
    	}
    	return lines;
	}
	*/

    public  String  toString()
    {
    	StringBuffer  sb = new StringBuffer("(HttpClob:");
    	sb.append(super.toString() + ",");
    	sb.append(")");
    	return  sb.toString();
    }
    
    public Parameter cloneMe()  throws  eng.database.exception.EngException
    {
    	HttpClob p = new HttpClob(this.getPosition(), this.getName());
    	if (this.isModeIn()) p.setModeIn();
    	else if (this.isModeOut()) p.setModeOut();
    	else if (this.isModeInOut()) p.setModeInOut();
        
        return p;
    }

    private static CLOB getCLOB(String xmlData, Connection conn) throws SQLException
    {
        CLOB tempClob = null;
        try{
        	// If the temporary CLOB has not yet been created, create new
        	tempClob = CLOB.createTemporary(conn.getMetaData().getConnection(), true, CLOB.DURATION_SESSION); 

        	// Open the temporary CLOB in readwrite mode to enable writing
        	tempClob.open(CLOB.MODE_READWRITE); 
        	// Get the output stream to write
        	Writer tempClobWriter = tempClob.setCharacterStream(0);
        	// Write the data into the temporary CLOB
        	tempClobWriter.write(xmlData); 

        	// Flush and close the stream
        	tempClobWriter.flush();
        	tempClobWriter.close(); 

        	// Close the temporary CLOB 
        	tempClob.close();    
  	  	} 
        catch(SQLException sqlexp)
        {
        	tempClob.freeTemporary(); 
        	sqlexp.printStackTrace();
  	  	} 
        catch(Exception exp)
        {
        	tempClob.freeTemporary(); 
        	exp.printStackTrace();
  	  	}
  	  	return tempClob; 
  	} 

    public static Vector getVectorFromClob(Clob c)
    {
    	Vector result = new Vector();

    	try 
    	{
    		if ( c != null )
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
                

                
//    			DOMParser parser = new DOMParser();
//    			String value = "";
//    			parser.parse( new org.xml.sax.InputSource(is) );
//
//    			Document document = parser.getDocument();
//
//    			// E' sufficiente prendere solo il primo nodo -Lista per ricavare a mano i Childs
//    			Node listaNode = goToNodeElement("Lista",document.getDocumentElement());
//    			if (listaNode == null) throw new Exception("Root element Lista non trovato nell'XML!");
//    			NodeList righe = listaNode.getChildNodes();
//    			int i,j,k ;
//    			int flag = 0;
//    			int cont = 65000;
//    			String [][] struttura ;
//    			String [] struttura2 ;
//    			Node riga ;
//    			NodeList colonne ;
//    			Node elemento ;
//    			String row = "";
//    			// faccio un primo ciclo di parsing che mi trova il valore massimo
//    			// del Nro di colonne...
//    			for ( i=0; i < righe.getLength(); ++i ) {
//    				riga = righe.item(i);
//    				if ( ( riga != null ) && (riga.getNodeName().equalsIgnoreCase("Riga")) )  {
//    					colonne = riga.getChildNodes();
//    					if (colonne != null ) 
//    						for ( j=0; j < colonne.getLength(); ++j ) {
//    							elemento = colonne.item(j);
//    							if (elemento != null) {
//    								value = getNodeAttributeValue(elemento,"Nro","");
//    								if (Integer.parseInt(value) > flag ) { flag = Integer.parseInt(value); }
//    								if (Integer.parseInt(value) < cont ) { cont = Integer.parseInt(value); }
//    							}
//    						}  
//    				}
//    			}
//    			// ora FLAG contiene il valore max di Nro e count il min....
//    			struttura = new String[flag+1][2] ; //in realtà crea un elemento in più...
//    			struttura2 = new String[flag+1] ; //in realtà crea un elemento in più...
//
//    			// questo ciclo scorre sia le righe che le colonne
//    			for ( i=0; i < righe.getLength(); ++i )
//    			{
//    				riga = righe.item(i);
//    				if ( ( riga != null ) && (riga.getNodeName().equalsIgnoreCase("Riga")) )  {
//    					colonne = riga.getChildNodes();
//    					row = "";
//    					if (colonne != null ) {
//    						// qui devo svuotare struttura...anziche crearne una nuova
//    						for ( k=0; k <= flag; ++k ) 
//    						{ struttura2[k]  = COLUMN_SEPARATOR; 
//    						struttura[k][0]= ""; 
//    						struttura[k][1]= COLUMN_SEPARATOR ; 
//    						}
//    						for ( j=0; j < colonne.getLength(); ++j ) {
//    							elemento = colonne.item(j);
//    							if (elemento != null) {
//    								value = readPCDATA(elemento).trim();
//    								struttura[j][1] = value + struttura[j][1];
//    								struttura[j][0] = getNodeAttributeValue(elemento,"Nro","");
//    							} 
//    						}
//    						// Riordino i valori letti per l'ordine di colonne da Oracle
//    						// e li metto nella riga unica, per il resultset da restituire
//    						for ( j=cont; j <= flag ; ++j ) {
//    							for ( k=0; k < colonne.getLength() ; ++k ) {
//    								if  ( Integer.parseInt(struttura[k][0]) == j ) 
//    								{  
//    									struttura2[j] = struttura[k][1];
//    								} 
//    							}
//    						}
//
//    						// Aggiungere le colonne vuote   
//    						for ( j=1; j <= flag ; j++ ) {
//    							row = row + struttura2[j]; 
//    						}
//    					}
//    					result.addElement( row );
//    					eng.util.Logger.getLogger().info("row:" + row );
//    				}
//    			}
    		}
    		else
    		{
    			throw new Exception ("Nessun XML da parsare");
    		}
    	} 
    	catch (Exception ex)
    	{
    		eng.util.Logger.getLogger().info("Errore durante il l'elaborazione del codice XML");
    		ex.printStackTrace();
    		return null;
    	} 	
    	return result;
    }
    
    public static EngResultSet getEngResultSetFromClob(Clob c) throws  EngSqlNoApplException
    {
    	EngResultSet result = new EngResultSet();
    	
    	Vector v = getVectorFromClob(c);
    	
    	if (v != null)
    	{
    		Iterator lines = v.iterator();

    		int cline = 0;
    		while (lines.hasNext())
    		{
    			String[] line = (String[]) lines.next();
    			for (int i = 0; i < line.length; i++)
    			{
    				result.addColumn( line[i]);
    			}
    			result.addRow();
    			cline++;
    		}
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
}
