package pt.ulisboa.tecnico.learnjava.sibs.domain;

import pt.ulisboa.tecnico.learnjava.bank.exceptions.AccountException;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.BankException;
import pt.ulisboa.tecnico.learnjava.bank.services.Services;

public class Registered extends State {
	
	@Override
	public void pull(Operation wrapper, Services s) {
		try {
			s.withdraw(wrapper.getSourceIban(), wrapper.getValue());
			wrapper.setState(new Withdrawn());
		} catch (AccountException e) {
			wrapper.setState(new Retry(this));			
		} catch (BankException e) {
			wrapper.setState(new Retry(this));
		}
	}
	
	@Override
	public void cancel(Operation wrapper, Services s) {
    	wrapper.setState(new Cancelled());
    }
	
}