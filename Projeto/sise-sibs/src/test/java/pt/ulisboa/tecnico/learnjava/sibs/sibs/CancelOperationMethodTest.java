package pt.ulisboa.tecnico.learnjava.sibs.sibs;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import org.junit.After;

import pt.ulisboa.tecnico.learnjava.bank.exceptions.AccountException;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.BankException;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.ClientException;
import pt.ulisboa.tecnico.learnjava.bank.services.Services;
import pt.ulisboa.tecnico.learnjava.sibs.domain.Sibs;
import pt.ulisboa.tecnico.learnjava.sibs.exceptions.OperationException;
import pt.ulisboa.tecnico.learnjava.sibs.exceptions.SibsException;

public class CancelOperationMethodTest {

	private static final String TARGET_IBAN = "TargetIban";
	private static final String SOURCE_IBAN = "SourceIban";

	private Sibs sibs;
	private Services services;

	private static final int VALUE = 100;

	@Before
	public void setUp() throws OperationException, SibsException, BankException, ClientException, AccountException {		
		this.services = new Services();
		this.sibs = new Sibs(100, services);
		this.sibs.addOperation(SOURCE_IBAN, TARGET_IBAN, VALUE);
		
	}
	
	@Test
	public void success() throws SibsException, OperationException, AccountException, BankException{
		sibs.cancelOperation(0);
		assertEquals(null, sibs.getOperation(0));
		
	}
	
	@Test (expected = OperationException.class)
	public void InvalidId() throws AccountException, OperationException, SibsException, BankException {
		sibs.cancelOperation(2);
	}
	
	@After
	public void tearDown() {
		this.sibs = null;
		this.services = null;
	}
	
	
	
	
	

}
