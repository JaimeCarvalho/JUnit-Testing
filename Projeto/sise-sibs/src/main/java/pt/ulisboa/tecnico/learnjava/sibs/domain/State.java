package pt.ulisboa.tecnico.learnjava.sibs.domain;

import pt.ulisboa.tecnico.learnjava.bank.exceptions.AccountException;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.BankException;
import pt.ulisboa.tecnico.learnjava.bank.services.Services;

abstract class State {
	
    public abstract void pull(Operation wrapper, Services s) throws AccountException, BankException;
    
    
    public abstract void cancel(Operation wrapper, Services s) throws AccountException, BankException;
    
}