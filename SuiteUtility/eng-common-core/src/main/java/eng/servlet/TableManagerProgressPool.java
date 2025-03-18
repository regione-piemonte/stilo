/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package eng.servlet;

import eng.database.exception.EngSqlNoApplException;
import eng.database.tablemanager.TableManagerDbProgress;
import eng.database.tablemanager.TableManagerDb;

public class TableManagerProgressPool extends TableManagerProgress
{
    @Override
    protected TableManagerDb createTableManagerDb(final String a) throws EngSqlNoApplException {
        return new TableManagerDbProgress(a);
    }
}
