package pt.ulisboa.tecnico.learnjava.sibs.operation;

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
import pt.ulisboa.tecnico.learnjava.sibs.domain.Cancelled;
import pt.ulisboa.tecnico.learnjava.sibs.domain.Completed;
import pt.ulisboa.tecnico.learnjava.sibs.domain.Operation;
import pt.ulisboa.tecnico.learnjava.sibs.exceptions.OperationException;
import pt.ulisboa.tecnico.learnjava.sibs.exceptions.SibsException;

public class CancelMethodTest {
	
	private static final String ADDRESS = "Ave.";
	private static final String PHONE_NUMBER = "987654321";
	private static final String NIF = "123456789";
	private static final String LAST_NAME = "Silva";
	private static final String FIRST_NAME = "António";
	
	private static final int VALUE = 50;
	private static final int ID = 1;

	private Bank sourceBank;
	private Bank targetBank;
	private Client sourceClient;
	private Client targetClient;
	private Services services;
	private Operation operation;
	private String source_iban;
	private String target_iban;
	private Operation operation_retry;

	@Before
	public void setUp() throws BankException, AccountException, ClientException, OperationException {
		this.services = new Services();
		this.sourceBank = new Bank("CGD");
		this.targetBank = new Bank("BPI");
		this.sourceClient = new Client(this.sourceBank, FIRST_NAME, LAST_NAME, NIF, PHONE_NUMBER, ADDRESS, 33);
		this.targetClient = new Client(this.targetBank, FIRST_NAME, LAST_NAME, NIF, PHONE_NUMBER, ADDRESS, 22);
		this.source_iban = sourceBank.createAccount(AccountType.CHECKING, sourceClient, 100, 50);
		this.target_iban = targetBank.createAccount(AccountType.CHECKING, targetClient, 100, 50);
		this.operation = new Operation(source_iban, target_iban, VALUE, ID);
	}

	@Test
	public void Registeredsuccess() throws BankException, AccountException, SibsException, OperationException, ClientException {
		operation.cancel(services);
		assertEquals(true, operation.getState() instanceof Cancelled);
	}
	
	@Test
	public void Withdrawnsuccess() throws BankException, AccountException, SibsException, OperationException, ClientException {
		operation.process(services);
		operation.cancel(services);
		assertEquals(true, operation.getState() instanceof Cancelled);
	}
	
	@Test
	public void Depositedsuccess() throws BankException, AccountException, SibsException, OperationException, ClientException {
		operation.process(services);
		operation.process(services);
		operation.cancel(services);
		assertEquals(true, operation.getState() instanceof Cancelled);
	}
	
	@Test
	public void Completedsuccess() throws BankException, AccountException, SibsException, OperationException, ClientException {
		operation.process(services);
		operation.process(services);
		operation.process(services);
		operation.cancel(services);
		assertEquals(true, operation.getState() instanceof Completed);
	}

	@Test
	public void CancelledSuccess() throws AccountException, BankException {
		operation.cancel(services);
		operation.cancel(services);
		assertEquals(true, operation.getState() instanceof Cancelled);
	}
	
	@Test
	public void RetrySuccess() throws OperationException, AccountException, BankException {
		operation_retry = new Operation(source_iban, target_iban, 150, ID);
		operation_retry.process(services);
		operation_retry.cancel(services);
		assertEquals(true, operation_retry.getState() instanceof Cancelled);
	}
	@After
	public void tearDown() {
		Bank.clearBanks();
	}



}
