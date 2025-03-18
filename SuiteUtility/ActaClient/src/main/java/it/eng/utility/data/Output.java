/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

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
