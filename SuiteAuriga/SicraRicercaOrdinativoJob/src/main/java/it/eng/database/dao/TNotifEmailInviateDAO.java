/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.hibernate.Session;

import it.eng.entity.TNotifEmailInviate;

public interface TNotifEmailInviateDAO {
	
	public int getNotificaEmailById(String idRic, Session session) throws Exception;
	
	public void save(TNotifEmailInviate bean, Session session) throws Exception;
	
	public void update(TNotifEmailInviate bean, Session session) throws Exception;
	
	public void delete(TNotifEmailInviate bena, Session session) throws Exception;
	
}
