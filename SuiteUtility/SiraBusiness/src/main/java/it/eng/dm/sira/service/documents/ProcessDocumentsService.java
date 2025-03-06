package it.eng.dm.sira.service.documents;

import it.eng.dm.sira.service.bean.DocumentToManage;
import it.eng.dm.sira.service.bean.ProcessDocuments;
import it.eng.dm.sira.service.bean.UdToManage;
import it.eng.dm.sira.service.bean.UnitaDocRuoloProcedimento;
import it.eng.dm.sira.service.util.ConnectionConfig;
import it.eng.dm.sira.service.util.ConnectionUtil;
import it.eng.spring.utility.SpringAppContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProcessDocumentsService {

	/***
	 * select prov_ci_process,etichetta_proponente from dmt_processes where id_process=1677
	 * 
	 * select a.ruolo_sogg_in_proc, b.des_user from dmt_process_sogg_int a,dmt_users b where id_process=1677 and
	 * b.id_user=a.sogg_value.id_in_anag_prov
	 */

	public ProcessDocumentsService() {
	}

	public ProcessDocuments searchProcessDocuments(String idProcess) throws Exception {
		ProcessDocuments faldone = new ProcessDocuments();
		List<UdToManage> uds = new ArrayList<UdToManage>();
		List<UnitaDocRuoloProcedimento> idsUd = new ArrayList<UnitaDocRuoloProcedimento>();
		Connection connection = ConnectionUtil.getConnection();
		Statement statement = null;
		String selectTableSQL = null;
		if (idProcess != null) {
			selectTableSQL = "select id_ud,cod_ruolo_doc from dmt_process_ud where id_process=" + idProcess;
			try {
				statement = connection.createStatement();
				System.out.println(selectTableSQL);
				// execute insert SQL stetement
				ResultSet rs = statement.executeQuery(selectTableSQL);

				while (rs.next()) {
					Integer id = (rs.getInt("ID_UD"));
					String ruolo = (rs.getString("COD_RUOLO_DOC"));
					UnitaDocRuoloProcedimento udr = new UnitaDocRuoloProcedimento();
					udr.setCodRuolo(ruolo);
					udr.setIdUd(id);
					idsUd.add(udr);
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			} finally {
				if (statement != null) {
					statement.close();
				}
			}
			try {
				for (UnitaDocRuoloProcedimento udrp : idsUd) {
					Integer idUd = udrp.getIdUd();
					String nuovaSelect = "select D.id_doc,nt.DISPLAY_FILENAME, nt.RIF_IN_REPOSITORY,nt.nro_progr FROM DMT_DOCUMENTS d,TABLE (d.versioni) nt where d.id_ud="
							+ idUd
							+ " and nt.nro_progr=(select max(nt1.nro_progr) from DMT_DOCUMENTS d1, TABLE (d1.VERSIONI) nt1 where d1.id_ud="
							+ idUd + ")";
					statement = connection.createStatement();
					System.out.println(nuovaSelect);
					// execute insert SQL stetement
					ResultSet rs = statement.executeQuery(nuovaSelect);
					UdToManage udtm = new UdToManage();
					udtm.setIdUd(idUd.toString());
					udtm.setCodRuolo(udrp.getCodRuolo());
					List<DocumentToManage> listaDoc = new ArrayList<DocumentToManage>();
					while (rs.next()) {
						DocumentToManage dtm = new DocumentToManage();
						dtm.setName(rs.getString("DISPLAY_FILENAME"));
						dtm.setUri(rs.getString("RIF_IN_REPOSITORY"));
						listaDoc.add(dtm);
					}
					udtm.setDocumenti(listaDoc);
					uds.add(udtm);
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
		}
		faldone.setUnitaDocumentarie(uds);
		return faldone;
	}
}
