package it.eng.utility.data;

public class Output<T> {

	private Outcome outcome = new Outcome();
	private T data;

	public Outcome getOutcome() {
		return outcome;
	}

	public void setOutcome(Outcome result) {
		this.outcome = result;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
