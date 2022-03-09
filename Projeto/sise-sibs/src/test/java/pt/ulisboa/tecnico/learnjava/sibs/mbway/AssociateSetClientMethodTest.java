package pt.ulisboa.tecnico.learnjava.sibs.mbway;

import java.util.Hashtable;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import pt.ulisboa.tecnico.learnjava.bank.domain.Bank;
import pt.ulisboa.tecnico.learnjava.bank.domain.Client;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.AccountException;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.BankException;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.ClientException;
import pt.ulisboa.tecnico.learnjava.bank.domain.Bank.AccountType;
import pt.ulisboa.tecnico.learnjava.bank.services.Services;
import pt.ulisboa.tecnico.learnjava.sibs.mbway.controllers.AssociateMBwayController;
import pt.ulisboa.tecnico.learnjava.sibs.mbway.domain.MBway;

public class AssociateSetClientMethodTest {

	private String iban;
	private String phone_number;
	private String confirmation_code;
	private MBway mbway = new MBway();
	private Services services;
	private Bank bank;
	private Client client;
	private AssociateMBwayController controller;
	private Hashtable<String, String[]> db;
	
	@Before
	public void setUp() throws BankException, AccountException, ClientException {
		this.services = new Services();
		this.bank = new Bank("CGD");
		this.client = new Client(bank, "Maria", "Soares", "123456789", "912346987", "Rua Alves Redol", 25);
		this.iban = bank.createAccount(AccountType.CHECKING, client, 1000, 0);
		this.phone_number = "987654321";
	}
	
	@Test
	public void success() {
		this.controller = new AssociateMBwayController(iban, phone_number, mbway, services);
		confirmation_code = controller.setClient();
		db = mbway.getDB();
		assertEquals(6, confirmation_code.length());
		assertEquals(1, db.size());
	}
	
	@Test
	public void invalidIban() {
		this.controller = new AssociateMBwayController("IBAN", phone_number, mbway, services);
		confirmation_code = controller.setClient();
		db = mbway.getDB();
		assertEquals(1, confirmation_code.length());
		assertEquals(true, confirmation_code.equals("0"));
		assertEquals(0, db.size());
	}
	
	@Test
	public void invalidPhoneNumber() {
		this.controller = new AssociateMBwayController(iban, "12345678", mbway, services);
		confirmation_code = controller.setClient();
		db = mbway.getDB();
		assertEquals(1, confirmation_code.length());
		assertEquals(true, confirmation_code.equals("1"));
		assertEquals(0, db.size());
	}
	
	@After
	public void tearDown() {
		Bank.clearBanks();
		MBway.clearMBway();
	}
	
}
