/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import org.hibernate.Session;

import it.eng.entity.TNotifAllEmail;

public interface TNotifAllEmailDAO {
	
	public List<TNotifAllEmail> getAllegatiByIdRich(String idRichiesta, Session session) throws Exception;
	
	public void update(TNotifAllEmail bean, Session session) throws Exception;
	
	public void delete(TNotifAllEmail bean, Session session) throws Exception;
	
}
