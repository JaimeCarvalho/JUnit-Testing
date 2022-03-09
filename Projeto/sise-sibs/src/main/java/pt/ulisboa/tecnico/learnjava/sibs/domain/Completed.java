package pt.ulisboa.tecnico.learnjava.sibs.domain;

import pt.ulisboa.tecnico.learnjava.bank.services.Services;

public class Completed extends State {
	
	@Override
	public void cancel(Operation wrapper, Services s) {
	}

	@Override
	public void pull(Operation wrapper, Services s) {
	}

}
