package pt.ulisboa.tecnico.learnjava.sibs.sibs;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.learnjava.bank.domain.Bank;
import pt.ulisboa.tecnico.learnjava.bank.domain.Client;
import pt.ulisboa.tecnico.learnjava.bank.domain.Bank.AccountType;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.AccountException;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.BankException;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.ClientException;
import pt.ulisboa.tecnico.learnjava.bank.services.Services;
import pt.ulisboa.tecnico.learnjava.sibs.domain.Completed;
import pt.ulisboa.tecnico.learnjava.sibs.domain.Sibs;
import pt.ulisboa.tecnico.learnjava.sibs.exceptions.OperationException;
import pt.ulisboa.tecnico.learnjava.sibs.exceptions.SibsException;

public class ProcessOperationsMethodTest {
	
	private static final int VALUE = 100;
	private Services services;
	
	private Sibs sibs;
	
	private static final String ADDRESS = "Ave.";
	private static final String PHONE_NUMBER = "987654321";
	private static final String NIF = "123456789";
	private static final String LAST_NAME = "Silva";
	private static final String FIRST_NAME = "António";

	private Bank sourceBank;
	private Bank targetBank;
	private Client sourceClient;
	private Client targetClient;

	@Before
	public void setUp() throws OperationException, SibsException, AccountException, ClientException, BankException {
		services = new Services();
		this.sibs = new Sibs(5, services);
		
		this.sourceBank = new Bank("CGD");
		this.targetBank = new Bank("BPI");
		this.sourceClient = new Client(this.sourceBank, FIRST_NAME, LAST_NAME, NIF, PHONE_NUMBER, ADDRESS, 33);
		this.targetClient = new Client(this.targetBank, FIRST_NAME, LAST_NAME, NIF, PHONE_NUMBER, ADDRESS, 22);
		
		String source_iban = sourceBank.createAccount(AccountType.CHECKING, sourceClient, 1000, 50);
		String target_iban = targetBank.createAccount(AccountType.CHECKING, targetClient, 1000, 50);
		
		sibs.addOperation(source_iban, target_iban, VALUE);
		sibs.addOperation(source_iban, target_iban, VALUE);
		sibs.getOperation(1).process(services);
		sibs.addOperation(source_iban, target_iban, VALUE);
		sibs.getOperation(2).process(services);
		sibs.getOperation(2).process(services);
		sibs.addOperation(source_iban, target_iban, VALUE);
		sibs.getOperation(3).process(services);
		sibs.getOperation(3).process(services);
		sibs.getOperation(3).process(services);
				
	}
	
	@Test
	public void success() throws AccountException, SibsException, BankException, ClientException{
		sibs.processOperations();
		assertEquals(true, sibs.getOperation(0).getState() instanceof Completed);
		assertEquals(true, sibs.getOperation(1).getState() instanceof Completed);
		assertEquals(true, sibs.getOperation(2).getState() instanceof Completed);
		assertEquals(true, sibs.getOperation(3).getState() instanceof Completed);
		
	}
	
	@After
	public void tearDown() {
		this.sibs = null;
		this.services = null;
	}
}
