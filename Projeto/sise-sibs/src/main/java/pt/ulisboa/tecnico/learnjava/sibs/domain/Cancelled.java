package pt.ulisboa.tecnico.learnjava.sibs.domain;

import pt.ulisboa.tecnico.learnjava.bank.exceptions.AccountException;
import pt.ulisboa.tecnico.learnjava.bank.services.Services;

public class Cancelled extends State {

	@Override
	public void pull(Operation wrapper, Services s) throws AccountException {
	}

	@Override
	public void cancel(Operation wrapper, Services s) throws AccountException {
	}
	
}
