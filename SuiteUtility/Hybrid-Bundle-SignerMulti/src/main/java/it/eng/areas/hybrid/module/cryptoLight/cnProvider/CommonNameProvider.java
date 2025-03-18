/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

public interface CommonNameProvider {
	
	public List<String> sendCommonName(String commonName);
	public void saveOutputParameter() throws Exception;
	
}
