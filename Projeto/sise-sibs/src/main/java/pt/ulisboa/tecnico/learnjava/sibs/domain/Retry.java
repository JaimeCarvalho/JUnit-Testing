package pt.ulisboa.tecnico.learnjava.sibs.domain;

import pt.ulisboa.tecnico.learnjava.bank.exceptions.AccountException;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.BankException;
import pt.ulisboa.tecnico.learnjava.bank.services.Services;

public class Retry extends State {
	
	private int nTries = 3;
	private State state;
	
	public Retry(State state) {
		this.state = state;
	}

	@Override
	public void pull(Operation wrapper, Services s) throws AccountException, BankException { ////////////////////CONFIRMAR
		state.pull(wrapper, s);
		if (wrapper.getState() instanceof Retry && nTries > 0) {
			nTries--;
			this.pull(wrapper, s);
		}
		else if (wrapper.getState() instanceof Retry && nTries == 0)
			wrapper.setState(new Error());
	}

	@Override
	public void cancel(Operation wrapper, Services s) throws AccountException, BankException {
		state.cancel(wrapper, s);
	}

}
	
//	try {
//		s.withdraw(wrapper.getSourceIban(), wrapper.getValue());
//	} catch (AccountException e) {
//		if(limit == nTries) {
//			State m = new Retry();
//			wrapper.setState(m);
//		}
//		if(nTries!=0) {
//			nTries--;
//			this.pull(wrapper, s);
//		}
//		else
//			wrapper.setState(new Error());
//		
//		}
//	wrapper.setState(new Withdrawn());  
//	
//	}
