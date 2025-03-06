package it.eng.dm.sira.service.procedimento;

import it.eng.dm.sira.service.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FasiService {

	public FasiService() {
	}

	public FasiBeanOut getInfoFasi(String idProcess) throws Exception {
		FasiBeanOut out = new FasiBeanOut();
		Connection connection = ConnectionUtil.getConnection();
		Statement statement = null;
		String selectTableSQL = null;
		String faseAttiva = null;
		if (idProcess != null) {
			selectTableSQL = "select des_evento from dmt_process_history where cod_tipo_evento ='#F' and ts_fine is null and id_process="
					+ idProcess;
			try {
				statement = connection.createStatement();
				System.out.println(selectTableSQL);
				// execute insert SQL stetement
				ResultSet rs = statement.executeQuery(selectTableSQL);
				while (rs.next()) {
					faseAttiva = rs.getString("DES_EVENTO");
				}
				out.setFaseAttiva(faseAttiva);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			} finally {
				if (statement != null) {
					statement.close();
				}
			}
			try {
				String nuovaSelect = "select process_name, min(nro_ordine_vis) as ordine from dmt_dett_attivita_wf where prov_ci_ty_flusso_wf = (select SUBSTR(prov_ci_ty_flusso_wf,1,INSTR(prov_ci_ty_flusso_wf,':')-1) from dmt_processes where id_process ="
						+ idProcess + ") group by process_name";
				statement = connection.createStatement();
				System.out.println(nuovaSelect);
				// execute insert SQL stetement
				ResultSet rs = statement.executeQuery(nuovaSelect);
				List<String> fasi = new ArrayList<String>();
				while (rs.next()) {
					fasi.add(rs.getString("PROCESS_NAME"));
				}
				out.setFasi(fasi);
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
		}
		return out;
	}
}
