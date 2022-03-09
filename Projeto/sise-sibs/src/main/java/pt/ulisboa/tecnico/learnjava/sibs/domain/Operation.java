package pt.ulisboa.tecnico.learnjava.sibs.domain;


import pt.ulisboa.tecnico.learnjava.bank.exceptions.AccountException;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.BankException;
import pt.ulisboa.tecnico.learnjava.bank.services.Services;
import pt.ulisboa.tecnico.learnjava.sibs.exceptions.OperationException;

public class Operation {

	private final int value;
	private final String targetIban;
	private final String sourceIban;
	private State current;
	private int id;

	public Operation(String sourceIban, String targetIban, int value, int id) throws OperationException {
		checkParameters(value);
		this.value = value;
		current = new Registered();
		this.id = id;

		if (invalidString(targetIban)) {
			throw new OperationException(); //
		}
		if (invalidString(sourceIban)) {
			throw new OperationException();
		}

		this.targetIban = targetIban;
		this.sourceIban = sourceIban;
	}
	
	public int getId() {
		return id;
	}
	
	 public void setState(State state) {
         current = state;
     }
	
	 public void process(Services s) throws AccountException, BankException {
	     current.pull(this, s);
	 }
     
     public State getState() {
    	 return current;
     }     
     
	private void checkParameters(int value) throws OperationException {

		if (value <= 0) {
			throw new OperationException(value);
		}
	}

	public int getValue() {
		return this.value;
	}

	private boolean invalidString(String name) {
		return name == null || name.length() == 0;
	}

	public int commission() {
		//return Math.round(getValue()*0.02*100.0)/100.0;		// Arredondado a duas casas decimais
		return (int) (getValue()*0.02);
	}

	public String getTargetIban() {
		return this.targetIban;
	}
	public String getSourceIban() {
		return this.sourceIban;
	}
	
	public void cancel(Services s) throws AccountException, BankException{
		current.cancel(this, s);
	}

}