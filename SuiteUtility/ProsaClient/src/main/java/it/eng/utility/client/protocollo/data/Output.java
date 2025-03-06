package it.eng.utility.client.protocollo.data;

public class Output<T> extends Esito {

	private T result;

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

}
