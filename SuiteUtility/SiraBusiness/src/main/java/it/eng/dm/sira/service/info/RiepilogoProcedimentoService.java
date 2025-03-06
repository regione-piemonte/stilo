package it.eng.dm.sira.service.info;

import it.eng.dm.sira.service.bean.CodificaRuolo;
import it.eng.dm.sira.service.bean.ProcessReferences;
import it.eng.dm.sira.service.bean.RiepilogoInfo;
import it.eng.dm.sira.service.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RiepilogoProcedimentoService {

	/**
	 * 
	 * select prov_ci_process,etichetta_proponente from dmt_processes where id_process=1677
	 * 
	 * select a.ruolo_sogg_in_proc, b.des_user from dmt_process_sogg_int a,dmt_users b where id_process=1677 and
	 * b.id_user=a.sogg_value.id_in_anag_prov
	 */

	public RiepilogoProcedimentoService() {
	}

	public RiepilogoInfo searchDatiRiepilogo(String idProcess) throws Exception {
		RiepilogoInfo info = new RiepilogoInfo();
		Connection connection = ConnectionUtil.getConnection();
		Statement statement = null;
		String selectTableSQL = null;
		String numeroProcedimento = null;
		String etichettaProponente = null;
		Date dataAvvio = null;
		Date dataFineTermini = null;
		if (idProcess != null) {
			selectTableSQL = "select ts_avvio, dt_chiusura_termini, prov_ci_process,etichetta_proponente from dmt_processes where id_process="
					+ idProcess;
			try {
				statement = connection.createStatement();
				System.out.println(selectTableSQL);
				// execute insert SQL stetement
				ResultSet rs = statement.executeQuery(selectTableSQL);
				while (rs.next()) {
					dataAvvio = rs.getDate("TS_AVVIO");
					dataFineTermini = rs.getDate("DT_CHIUSURA_TERMINI");
					numeroProcedimento = (rs.getString("PROV_CI_PROCESS"));
					etichettaProponente = (rs.getString("ETICHETTA_PROPONENTE"));
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			} finally {
				if (statement != null) {
					statement.close();
				}
			}
			try {
				String nuovaSelect = "select  a.ruolo_sogg_in_proc, b.des_user from dmt_process_sogg_int a,dmt_users b where b.id_user=a.sogg_value.id_in_anag_prov and id_process="
						+ idProcess;
				statement = connection.createStatement();
				System.out.println(nuovaSelect);
				// execute insert SQL stetement
				ResultSet rs = statement.executeQuery(nuovaSelect);
				List<ProcessReferences> listaRif = new ArrayList<ProcessReferences>();
				while (rs.next()) {
					ProcessReferences pr = new ProcessReferences();
					pr.setRuolo(rs.getString("RUOLO_SOGG_IN_PROC"));
					pr.setDescrizione(rs.getString("DES_USER"));
					listaRif.add(pr);
				}
				for (ProcessReferences ref : listaRif) {
					if (ref.getRuolo().equals(CodificaRuolo.RESPONSABILE.getValue())) {
						info.setDesResponsabile(ref.getDescrizione());
					} else {
						if (ref.getRuolo().equals(CodificaRuolo.REFERENTE_AMMINISTRATIVO.getValue())) {
							info.setDesRefAmministrativo(ref.getDescrizione());
						} else {
							if (ref.getRuolo().equals(CodificaRuolo.REFERENTE_TECNICO.getValue())) {
								info.setDesRefTecnico(ref.getDescrizione());
							}
						}
					}
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
		info.setNumeroProcedimento(numeroProcedimento);
		info.setEtcihettaProcedimento(etichettaProponente);
		info.setDataAvvio(dataAvvio);
		info.setDataFineTermini(dataFineTermini);
		return info;
	}
}
