package it.eng.dm.sira.populate;

import it.eng.dm.sira.exceptions.SiraBusinessException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hyperborea.sira.ws.CostNost;
import com.hyperborea.sira.ws.CostNostId;

public class ProcObjTypesManager {

	private static final String DB_USER = "auri_owner_1";

	private static final String DB_PASSWD = "auri_owner_1";

	private static final String DB_URL = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=sira2-dbt-scan)(PORT=1521))(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME=SIRADBS)))";

	private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";

	public ProcObjTypesManager() {
	}

	private Connection getConnection() throws Exception {
		Class.forName(DB_DRIVER);
		Connection connection = null;
		connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD);
		return connection;
	}

	public List<String> selectRecordAttributi(String mainLabel) throws Exception {
		List<String> attributi = new ArrayList<String>();
		Connection connection = getConnection();
		Statement statement = null;
//		String selectTableSQL = "SELECT ATTR_NAME from dmt_attributes_def d WHERE d.attr_name LIKE '" + mainLabel
//				+ "_%' and (d.ATTR_NAME_SUP is null OR d.ATTR_TYPE='COMPLEX')";
		String selectTableSQL = "SELECT ATTR_NAME from dmt_attributes_def d WHERE d.attr_name LIKE '" + mainLabel + "_%' and d.ATTR_NAME_SUP='" + mainLabel + "_CARATTERIZZAZIONIOST__O'";
		try {
			statement = connection.createStatement();
			System.out.println(selectTableSQL);
			// execute insert SQL stetement
			ResultSet rs = statement.executeQuery(selectTableSQL);
			while (rs.next()) {
				attributi.add(rs.getString("ATTR_NAME"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		return attributi;
	}

	private void init(Connection connection, String idProcObjType) throws SQLException {
		String updateSql = "update dmt_proc_obj_types m set attr_add_x_proc_obj_del_tipo=NVL(attr_add_x_proc_obj_del_tipo, dmto_attr_def()) where m.id_proc_obj_type="
				+ idProcObjType;
		Statement statement = null;
		try {
			statement = connection.createStatement();
			System.out.println(updateSql);
			statement.executeUpdate(updateSql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
		}
	}

	public void insertProcType(List<String> attributi, String idProcObjType) throws Exception {
		Connection connection = getConnection();
		Statement statement = null;
		init(connection, idProcObjType);
		try {
			for (int i = 0; i < attributi.size(); i++) {
				String flgObblig = "0";
				String maxNumValues = "1";
				// inserire qui le logiche su obbligatoriet o su eventuali liste
				if (isListaOObbligatorio(attributi.get(i))) {
					if (attributi.get(i).endsWith("__OL")) {
						flgObblig = "1";
						maxNumValues = null;
					} else {
						if (attributi.get(i).endsWith("__L")) {
							maxNumValues = null;
						} else {
							if (attributi.get(i).endsWith("__O")) {
								flgObblig = "1";
							}
						}
					}
				}
				String insertTableSQL = "insert into TABLE(select m.attr_add_x_proc_obj_del_tipo from dmt_proc_obj_types m where m.id_proc_obj_type="
						+ idProcObjType
						+ ")"
						+ "(ATTR_NAME, NRO_POSIZIONE, FLG_OBBLIG, MAX_NUM_VALUES) VALUES('"
						+ attributi.get(i)
						+ "',"
						+ i + "," + flgObblig + "," + maxNumValues + ")";
				try {
					statement = connection.createStatement();
					System.out.println(insertTableSQL);
					statement.executeUpdate(insertTableSQL);
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				} finally {
					if (statement != null) {
						statement.close();
					}
				}
			}
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	private boolean isListaOObbligatorio(String in) {
		if (in.endsWith("_OL") || in.endsWith("_L") || in.endsWith("_O"))
			return true;
		return false;
	}
	
	public boolean callDeleteAttrDynProcedure(int categoria, int natura, String etichetta, int objType) throws SiraBusinessException, SQLException, Exception {
		boolean retVal = false;
		
		Connection connection = getConnection();
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("begin DELETE_ATTR_DYN(?,?,?,?); end;");
	
			//CATEGORIASEL
			statement.setInt(1, categoria);
			//NATURASEL
			statement.setInt(2, natura);
			//ETICHETTA
			statement.setString(3, etichetta);
			//OBJ_TYPE
			statement.setInt(4, objType);

			statement.execute();
			statement.close();
			
			retVal = true;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SiraBusinessException("Errore nella inizializzazione di ATTRIBUTI_OST, DMT_ATTRIBUTES_DEF e DMT_PROC_OBJ_TYPES.");
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		
		return retVal;
		
	}
	
	
	public String getIdProcObjTypeByCostNost(CostNost in) throws SiraBusinessException, Exception {
		String retVal = null;
		
		Connection connection = getConnection();
		Statement statement = null;

		String selectTableSQL = "SELECT ID_PROC_OBJ_TYPE from DMT_PROC_OBJ_TYPES where PROV_CI_PROC_OBJ_TYPE = "
				+ in.getId().getCodCost() + " and PROV_CI_PROC_OBJ_MACROTYPE = " + in.getId().getCodNost();
		try {
			statement = connection.createStatement();
//			System.out.println(selectTableSQL);
			// execute insert SQL stetement
			
//			System.out.println("\nRicerca tipi procedimento per la coppia: " + in.getCodCost() + ", " + in.getCodNost());
			
			ResultSet rs = statement.executeQuery(selectTableSQL);
			
			int numResults = 0;
			while (rs.next()) {
				retVal = rs.getBigDecimal("ID_PROC_OBJ_TYPE").toPlainString();
				numResults++;
			}
			
			String tipoGeometria = "PUNTO";
			if(in.getTipoGeometria() == "POL")
				tipoGeometria = "POLIGONO";
			if(in.getTipoGeometria() == "LIN")
				tipoGeometria = "LINEA";
			
			if(numResults == 0) {
				System.err.println("--> Tipo procedimento non trovato! Si istanzia un nuovo ID");
				
				String getNextIdProcTypeSQL = "select max(ID_PROC_OBJ_TYPE) + 1 as NEXTID from DMT_PROC_OBJ_TYPES";
				ResultSet rsNew = statement.executeQuery(getNextIdProcTypeSQL);
				
				while (rsNew.next()) {
					retVal = rsNew.getBigDecimal("NEXTID").toPlainString();
				}
				
				String insertProcSQL = "insert into DMT_PROC_OBJ_TYPES values (" +
						retVal + ", null, null, null, " +
						"'" + in.getCategorieOst().getDescrizione() + "', " + //NOME_PROC_OBJ_TYPE
						"null, null, AURI_MASTER.DMTO_ATTR_DEF(), null, null, null, AURI_MASTER.DMTO_ATTR_VALUES(), null, " +
						in.getId().getCodNost() + ", " + //NATURA
						in.getId().getCodCost() + ", " + //CATEGORIA
						"'" + tipoGeometria + "', " + //TIPO_GEOMETRIA
						"null, null, SYSDATE, null, null, null)";
				
				System.err.println("\n\n--> " + insertProcSQL + "\n\n");
				
				statement.executeUpdate(insertProcSQL);
				
				
			} else if(numResults == 1) {
				
				String updateProcSQL = "update DMT_PROC_OBJ_TYPES set " +
						"NOME_PROC_OBJ_TYPE = '" + in.getCategorieOst().getDescrizione() + "', " + 
						"TIPO_GEOMETRIA = '" + tipoGeometria + "', " + 
						"TS_LAST_UPD = SYSDATE " +
						"where ID_PROC_OBJ_TYPE = " + retVal; 
				
				System.err.println("\n\n--> " + updateProcSQL + "\n\n");
				
				statement.executeUpdate(updateProcSQL);
				
			} else if(numResults > 1) {
				System.err.println("--> Troppi tipi procedimento associati: " + numResults);
				retVal = null;
				throw new SiraBusinessException("DataModelGetter: Troppi tipi procedimento associati.");
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		
		return retVal;
	}
	
	
	public String getIdProcObjTypeByCodCostAndNost(int codCost, int codNost) throws Exception {
		String retVal = null;
		
		Connection connection = getConnection();
		Statement statement = null;

		String selectTableSQL = "SELECT ID_PROC_OBJ_TYPE from DMT_PROC_OBJ_TYPES where PROV_CI_PROC_OBJ_TYPE = "
				+ codCost + " and PROV_CI_PROC_OBJ_MACROTYPE = " + codNost;
		try {
			statement = connection.createStatement();
//			System.out.println(selectTableSQL);
			// execute insert SQL stetement
			
//			System.out.println("\nRicerca tipi procedimento per la coppia: " + in.getCodCost() + ", " + in.getCodNost());
			
			ResultSet rs = statement.executeQuery(selectTableSQL);
			
			int numResults = 0;
			while (rs.next()) {
				retVal = rs.getBigDecimal("ID_PROC_OBJ_TYPE").toPlainString();
				numResults++;
			}
			if(numResults == 0) {
				System.err.println("Non è stato trovato un processo associato alla coppia (" + codCost + "," + codNost + ")");
				retVal = null;
			} else if(numResults == 1) {
				System.out.println("Trovato un processo associato alla coppia (" + codCost + "," + codNost + ") --> " + retVal);
			} else {
				System.err.println("Troppi processi associati alla coppia (" + codCost + "," + codNost + ")");
				retVal = null;
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		System.out.println("getId retVal " + retVal);
		return retVal;
	}
		
	
	
	public boolean insertAltriAttributiProcType(String relazione, String idProc, String num_value, int nro_occ_value) throws Exception {
		boolean retVal = false;
		Connection connection = getConnection();
		Statement statement = null;
		init(connection, idProc);
		try {
			//for (int i = 0; i < attributi.size(); i++) {
				
						
				String insertTableSQLCat = "insert into TABLE(select m.altri_attributi from dmt_proc_obj_types m where m.id_proc_obj_type="
				+ idProc
				+ ")"
				+ "(ATTR_NAME, NRO_OCC_VALUE, STR_VALUE, NUM_VALUE, DATE_VALUE, TS_INS, ID_USER_INS, TS_LAST_UPD, ID_USER_LAST_UPD, ID_VALORE_ATTR, ID_VALORE_ATTR_SUP) VALUES("
				+ "'GPA_NATURA_REL_PROC_OBJ_TY'" + "," + nro_occ_value + ", '" + relazione + "'," + "null, null, null, null, null, null, null, null)";
				
				String insertTableSQLNat = "insert into TABLE(select m.altri_attributi from dmt_proc_obj_types m where m.id_proc_obj_type="
				+ idProc
				+ ")"
				+ "(ATTR_NAME, NRO_OCC_VALUE, STR_VALUE, NUM_VALUE, DATE_VALUE, TS_INS, ID_USER_INS, TS_LAST_UPD, ID_USER_LAST_UPD, ID_VALORE_ATTR, ID_VALORE_ATTR_SUP) VALUES("
				+ "'GPA_ID_PROC_OBJ_TY_REL_OBJ_TY'" + "," + nro_occ_value + "," + "null" + "," + num_value + "," + "null, null, null, null, null, null, null)";


				try {
					statement = connection.createStatement();
					System.out.println(insertTableSQLCat);
					System.out.println(insertTableSQLNat);
					int q1Ret = statement.executeUpdate(insertTableSQLCat);
					int q2Ret = statement.executeUpdate(insertTableSQLNat);
					
					retVal = (q1Ret > 0) & (q2Ret > 0);
					
				} catch (SQLException e) {
					System.err.println(e.getMessage());
				} finally {
					if (statement != null) {
						statement.close();
					}
				}
			//}
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		
		return retVal;
	}
}
