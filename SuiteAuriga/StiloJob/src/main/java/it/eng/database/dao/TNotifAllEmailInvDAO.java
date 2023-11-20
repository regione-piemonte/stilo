/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.hibernate.Session;

import it.eng.entity.TNotifAllEmailInv;

public interface TNotifAllEmailInvDAO {
	
	public int getAllegatiEmailByIdRich(String idRic, Session session) throws Exception;
	
	public void save(TNotifAllEmailInv bean, Session session) throws Exception;
	
}
