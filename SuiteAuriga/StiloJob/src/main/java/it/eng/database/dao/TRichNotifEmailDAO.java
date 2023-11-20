/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import org.hibernate.Session;

import it.eng.entity.TRichNotifEmail;

public interface TRichNotifEmailDAO {
	
	public List<TRichNotifEmail> getRecordsByStato(String stato, int tryNumber, Session session) throws Exception;
	
	public void save(TRichNotifEmail bean, Session session) throws Exception;
	
	public void update(TRichNotifEmail bean, Session session) throws Exception;
	
	public void delete(TRichNotifEmail bean, Session session) throws Exception;
	
}
