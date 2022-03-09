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
import pt.ulisboa.tecnico.learnjava.sibs.domain.Sibs;
import pt.ulisboa.tecnico.learnjava.sibs.exceptions.OperationException;
import pt.ulisboa.tecnico.learnjava.sibs.exceptions.SibsException;

public class TransferImprovedMethodTest {
	
	private static final String ADDRESS = "Ave.";
	private static final String PHONE_NUMBER = "987654321";
	private static final String NIF = "123456789";
	private static final String LAST_NAME = "Silva";
	private static final String FIRST_NAME = "António";

	private Sibs sibs;
	private Bank sourceBank;
	private Bank targetBank;
	private Client sourceClient;
	private Client targetClient;
	private Services services;

	@Before
	public void setUp() throws BankException, AccountException, ClientException {
		this.services = new Services();
		this.sibs = new Sibs(100, services);
		this.sourceBank = new Bank("CGD");
		this.targetBank = new Bank("BPI");
		this.sourceClient = new Client(this.sourceBank, FIRST_NAME, LAST_NAME, NIF, PHONE_NUMBER, ADDRESS, 33);
		this.targetClient = new Client(this.targetBank, FIRST_NAME, LAST_NAME, NIF, PHONE_NUMBER, ADDRESS, 22);
	}

	@Test
	public void success() throws BankException, AccountException, SibsException, OperationException, ClientException {
		String source_iban = sourceBank.createAccount(AccountType.CHECKING, sourceClient, 100, 50);
		String target_iban = targetBank.createAccount(AccountType.CHECKING, targetClient, 100, 50);
		sibs.transfer_improved(source_iban, target_iban, 10);
		int num_operations = sibs.getNumberOfOperations();
		assertEquals(1, num_operations);
	}
	

	@Test (expected = OperationException.class)
	public void invalidTargetIbanInTransfer() throws OperationException, BankException, AccountException, ClientException, SibsException {
		String source_iban = sourceBank.createAccount(AccountType.CHECKING, sourceClient, 100, 50);
		String target_iban = "";
		sibs.transfer_improved(source_iban, target_iban, 10);
	}

	@Test (expected = OperationException.class)
	public void invalidSourceIbanInTransfer() throws OperationException, BankException, AccountException, ClientException, SibsException {
		String source_iban = "";
		String target_iban = targetBank.createAccount(AccountType.CHECKING, targetClient, 100, 50);
		sibs.transfer_improved(source_iban, target_iban, 10);
	}

	@After
	public void tearDown() {
		Bank.clearBanks();
	}


}
