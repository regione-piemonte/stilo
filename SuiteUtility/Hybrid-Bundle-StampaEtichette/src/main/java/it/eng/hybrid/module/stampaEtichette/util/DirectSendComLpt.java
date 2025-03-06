package it.eng.hybrid.module.stampaEtichette.util;

import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Vector;
import javax.comm.CommPortIdentifier;
import javax.comm.ParallelPort;
import javax.comm.SerialPort;

public class DirectSendComLpt {

	private Vector AvailablePorts = null;

	private String SelectedPortName = null;

	private SerialPort SerialPortInst = null;

	private ParallelPort ParallelPortInst = null;

	private PrintStream PortPrintStream = null;

	private InputStreamReader PortInputStreamReader = null;

	public DirectSendComLpt() throws Exception {
		loadAvailablePort();
	}

	public String[] getAvailablePort() throws Exception {
		String[] availPorts = null;
		availPorts = new String[this.AvailablePorts.size()];
		this.AvailablePorts.toArray((Object[]) availPorts);
		return availPorts;
	}

	private void loadAvailablePort() throws Exception {
		Enumeration enumer = null;
		this.AvailablePorts = new Vector(1);
		enumer = CommPortIdentifier.getPortIdentifiers();
		while (enumer.hasMoreElements())
			this.AvailablePorts.add(((CommPortIdentifier) enumer.nextElement()).getName());
	}

	public void setSelectedPortName(String portName) {
		this.SelectedPortName = portName;
	}

	public void openConnection() throws Exception {
		CommPortIdentifier portId = null;
		portId = CommPortIdentifier.getPortIdentifier(this.SelectedPortName);
		if (portId.getPortType() == 1) {
			this.SerialPortInst = (SerialPort) portId.open("DirectSendComLpt", 200);
			this.SerialPortInst.setSerialPortParams(9600, 8, 1, 0);
			this.PortPrintStream = new PrintStream(this.SerialPortInst.getOutputStream());
			this.PortInputStreamReader = new InputStreamReader(this.SerialPortInst.getInputStream());
		} else {
			this.ParallelPortInst = (ParallelPort) portId.open("DirectSendComLpt", 200);
			this.ParallelPortInst.setMode(4);
			this.PortPrintStream = new PrintStream(this.ParallelPortInst.getOutputStream());
		}
	}

	public void sendCommand(String command) throws Exception {
		this.PortPrintStream.print(command);
	}

	public String receiveCommand() throws Exception {
		char[] response = null;
		if (!this.PortInputStreamReader.ready())
			return null;
		response = new char[this.SerialPortInst.getInputBufferSize()];
		this.PortInputStreamReader.read(response);
		return new String(response);
	}

	public void closeConnection() throws Exception {
		this.PortPrintStream.flush();
		this.PortPrintStream.close();
		this.PortInputStreamReader.close();
		if (this.SerialPortInst != null) {
			this.SerialPortInst.close();
		} else {
			this.ParallelPortInst.close();
		}
	}
}
