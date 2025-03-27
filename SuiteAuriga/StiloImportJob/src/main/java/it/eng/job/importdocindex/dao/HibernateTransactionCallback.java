/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.job.importdocindex.dao;

import org.hibernate.Session;

public interface HibernateTransactionCallback<T> {

	T execute(Session session);

}
