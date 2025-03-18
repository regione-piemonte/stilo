/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public interface TimerListener{
  public void begin(int timeleft);
  public void tick(int timeleft);
  public void end(int timeleft);
}