/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(QueueDocAttributeTest.class)
public abstract class QueueDocAttributeTest
{
  public static volatile SingularAttribute<QueueDocAttribute, String> configuration;
  public static volatile SingularAttribute<QueueDocAttribute, Boolean> disabled;
  public static volatile SingularAttribute<QueueDocAttribute, Long> idDocument;
  public static volatile SingularAttribute<QueueDocAttribute, String> type;
  public static final String CONFIGURATION = "configuration";
  public static final String DISABLED = "disabled";
  public static final String IDDOCUMENT = "idDocument";
  public static final String TYPE = "type";
}
