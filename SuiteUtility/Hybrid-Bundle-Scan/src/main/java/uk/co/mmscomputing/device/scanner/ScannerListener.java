/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public interface ScannerListener{
  public void update(ScannerIOMetadata.Type type, ScannerIOMetadata metadata);
}