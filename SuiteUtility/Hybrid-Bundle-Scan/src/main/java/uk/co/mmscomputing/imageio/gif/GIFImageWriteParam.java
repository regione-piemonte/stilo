/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.*;
import javax.imageio.*;

public class GIFImageWriteParam extends ImageWriteParam{

  public GIFImageWriteParam(Locale locale){
    super(locale);
//    setController(new GIFIIOParamController(locale));
  }
}

