package pt.ulisboa.tecnico.learnjava.sibs.domain;

import pt.ulisboa.tecnico.learnjava.bank.exceptions.AccountException;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.BankException;
import pt.ulisboa.tecnico.learnjava.bank.services.Services;
import pt.ulisboa.tecnico.learnjava.sibs.exceptions.OperationException;
import pt.ulisboa.tecnico.learnjava.sibs.exceptions.SibsException;

public class Sibs {
	final Operation[] operations;
	Services services;
	private int id; 	

	public Sibs(int maxNumberOfOperations, Services services) {
		this.operations = new Operation[maxNumberOfOperations];
		this.services = services;
	}

	public void transfer(String sourceIban, String targetIban, int amount)
			throws SibsException, AccountException, OperationException, BankException {
		
		addOperation(sourceIban, targetIban, amount);
		
		services.withdraw(sourceIban, amount);;
		
		services.deposit(targetIban, amount);
	}
	
	public void transfer_improved(String sourceIban, String targetIban, int amount) throws OperationException, SibsException {
		addOperation(sourceIban, targetIban, amount);
	}
	
	public void processOperations() throws AccountException, BankException {
		for(Operation op: operations) {
			if(op != null) {
				while(!(op.getState() instanceof Completed) && !(op.getState() instanceof Cancelled))
					op.process(services);
			}
		}
	}
	
	public int addOperation(String sourceIban, String targetIban, int value) throws OperationException, SibsException {
		int position = -1;
		for (int i = 0; i < this.operations.length; i++) {
			if (this.operations[i] == null) {
				position = i;
				break;
			}
		}

		if (position == -1) {
			throw new SibsException();
		}

		Operation operation = new Operation(sourceIban, targetIban, value, id++);

		this.operations[position] = operation;
		return position;
	}
	
	public int getPositionById(int id) throws OperationException, SibsException {
		for(int i = 0; i < this.operations.length; i++) {
			if ((getOperation(i) != null) && (id == getOperation(i).getId())) {
				return i;
			}	
		}
		throw new OperationException();
	}
	
	public void cancelOperation(int id) throws AccountException, OperationException, SibsException, BankException {
		getOperation(getPositionById(id)).cancel(services);
		removeOperation(getPositionById(id));
	}

	public void removeOperation(int position) throws SibsException {
		if (position < 0 || position > this.operations.length) {
			throw new SibsException();
		}
		this.operations[position] = null;
	}

	public Operation getOperation(int position) throws SibsException {
		if (position < 0 || position > this.operations.length) {
			throw new SibsException();
		}
		return this.operations[position];
	}
	
	public int getNumberOfOperations() {
		int counter = 0;
		for (Operation op : operations) {
			if (op != null)
				counter++;		
			}
		return counter;
	}

	public int getTotalValueOfOperations() {
		int counter = 0;
		for (Operation op : operations) {
			if (op != null)
				counter += op.getValue();
			}
		return counter;
	}
	
	public Services getServices() {
		return services;
	}
}
