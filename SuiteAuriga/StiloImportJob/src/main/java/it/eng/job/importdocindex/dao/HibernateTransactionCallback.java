/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.hibernate.Session;

public interface HibernateTransactionCallback<T> {

	T execute(Session session);

}
