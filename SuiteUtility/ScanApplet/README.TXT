              
			  ISTRUZIONI
========================================================

- PER CREARE IL JAR
-------------------------------------------------------

. Copiare ANT su C:\ANT
. Aprire una sessione DOS
. Andare nella cartella \ScanApplet
. lanciare il comando C:\ANT\BIN\ant.bat  
. Verrà creato il file \ScanApplet\build\ScanApplet.jar



- PER CREARE IL CERTIFICATO
-------------------------------------------------------

. Aprire una sessione DOS.
. Andare nella cartella \ScanApplet
. Lanciare il comando:

   keytool -genkey -alias myself -keystore myKeystore

   password: password