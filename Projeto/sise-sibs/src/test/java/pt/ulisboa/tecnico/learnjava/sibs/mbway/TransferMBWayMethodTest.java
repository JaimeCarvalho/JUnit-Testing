package pt.ulisboa.tecnico.learnjava.sibs.mbway;

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
import pt.ulisboa.tecnico.learnjava.sibs.mbway.controllers.MBwayTransferController;
import pt.ulisboa.tecnico.learnjava.sibs.mbway.domain.MBway;

public class TransferMBWayMethodTest {

		private String source_iban;
		private String target_iban;
		private String source_phone;
		private String target_phone;
		private MBway mbway = new MBway();
		private Services services;
		private Bank bank_source;
		private Bank bank_target;
		private Client client1;
		private Client client2;
//		private AssociateMBwayController associateController_source;
//		private AssociateMBwayController associateController_target;
//		private ConfirmMBwayController confirmController_source;
//		private ConfirmMBwayController confirmController_target;
		private MBwayTransferController transferController;
		
		@Before
		public void setUp() throws BankException, AccountException, ClientException {
			this.services = new Services();
			this.bank_source = new Bank("CGD");
			this.bank_target = new Bank("BPI");
			this.client1 = new Client(bank_source, "Maria", "Soares", "123456789", "912346987", "Rua Alves Redol", 25);
			this.client2 = new Client(bank_target, "Maria", "Soares", "123456788", "912346982", "Rua Alves Redol", 25);
			this.source_iban = bank_source.createAccount(AccountType.CHECKING, client1, 1000, 0);
			this.target_iban = bank_target.createAccount(AccountType.CHECKING, client2, 1000, 0);
			this.source_phone = "987654321";
			this.target_phone = "967654321";
			
			
		}
		
		@Test
		public void success() throws BankException, AccountException{
			this.mbway.createNewClient(source_phone,source_iban, "source_code");
			this.mbway.createNewClient(target_phone,target_iban, "transfer_code");
//			this.associateController_source = new AssociateMBwayController(source_iban, source_phone,mbway, services);
//			this.source_confirmation_code = associateController_source.setClient();
//			this.confirmController_source = new ConfirmMBwayController(source_phone,source_confirmation_code,mbway);
//			this.associateController_target = new AssociateMBwayController(target_iban, target_phone,mbway, services);
//			this.target_confirmation_code = associateController_target.setClient();
//			this.confirmController_target = new ConfirmMBwayController(target_phone,target_confirmation_code,mbway);
			this.transferController = new MBwayTransferController(source_phone,target_phone,50,mbway,services);
			int result = transferController.transfer();
			int source_balance = (services.getAccountByIban(source_iban)).getBalance();
			int target_balance = (services.getAccountByIban(target_iban)).getBalance();
			assertEquals(1,result);
			assertEquals(950, source_balance);
			assertEquals(1050, target_balance);
		}
		
		@Test 
		public void invalidSourcePhoneWithFewerNumbersInTransfer () throws BankException, AccountException {
			this.mbway.createNewClient(target_phone,target_iban, "transfer_code");
			this.transferController = new MBwayTransferController("91325688",target_phone,50,mbway,services);//source code is wrong (only 8 char)
			int result = transferController.transfer();
			int source_balance = (services.getAccountByIban(source_iban)).getBalance();
			int target_balance = (services.getAccountByIban(target_iban)).getBalance();
			assertEquals(0, result);
			assertEquals(1000, source_balance);
			assertEquals(1000, target_balance);
		}
		
		@Test 
		public void invalidTargetWithFewerNumbersInPhoneInTransfer () throws BankException, AccountException{
			this.mbway.createNewClient(source_phone,source_iban, "source_code");
			this.transferController = new MBwayTransferController(source_phone,"98963625",50,mbway,services);//target phone is wrong (only 8 char)
			int result = transferController.transfer();
			int source_balance = (services.getAccountByIban(source_iban)).getBalance();
			int target_balance = (services.getAccountByIban(target_iban)).getBalance();
			assertEquals(0, result);
			assertEquals(1000, source_balance);
			assertEquals(1000, target_balance);
		}
		
		@Test 
		public void invalidSourcePhoneWithLettersInTransfer () throws BankException, AccountException {
			this.mbway.createNewClient(target_phone,target_iban, "transfer_code");
			this.transferController = new MBwayTransferController("91325688N",target_phone,50,mbway,services);//source phone is wrong (contains letters)
			int result = transferController.transfer();
			int source_balance = (services.getAccountByIban(source_iban)).getBalance();
			int target_balance = (services.getAccountByIban(target_iban)).getBalance();
			assertEquals(0, result);
			assertEquals(1000, source_balance);
			assertEquals(1000, target_balance);
		}
		
		@Test 
		public void invalidTargeInPhonetWithLettersInTransfer () throws BankException, AccountException {
			this.mbway.createNewClient(source_phone,source_iban, "source_code");
			this.transferController = new MBwayTransferController(source_phone,"98963625N",50,mbway,services);//target phone is wrong (contains Letters)
			int result = transferController.transfer();
			int source_balance = (services.getAccountByIban(source_iban)).getBalance();
			int target_balance = (services.getAccountByIban(target_iban)).getBalance();
			assertEquals(0, result);
			assertEquals(1000, source_balance);
			assertEquals(1000, target_balance);
		}
		
		@Test 
		public void invalidTargetIbanInTransfer () throws BankException, AccountException {
			this.mbway.createNewClient(source_phone,source_iban, "source_code");
			this.mbway.createNewClient(target_phone,"target_iban", "target_code");
			this.transferController = new MBwayTransferController(source_phone,target_phone,50,mbway,services);//target iban is wrong 
			int result = transferController.transfer();
			int source_balance = (services.getAccountByIban(source_iban)).getBalance();
			int target_balance = (services.getAccountByIban(target_iban)).getBalance();
			assertEquals(2, result);
			assertEquals(1000, source_balance);
			assertEquals(1000, target_balance);
		}
		
		@Test 
		public void invalidSourceIbanInTransfer () throws BankException, AccountException {
			this.mbway.createNewClient(source_phone,"source_iban", "source_code");
			this.mbway.createNewClient(target_phone,target_iban, "target_code");
			this.transferController = new MBwayTransferController(source_phone,target_phone,50,mbway,services);//source iban is wrong 
			int result = transferController.transfer();
			int source_balance = (services.getAccountByIban(source_iban)).getBalance();
			int target_balance = (services.getAccountByIban(target_iban)).getBalance();
			assertEquals(2, result);
			assertEquals(1000, source_balance);
			assertEquals(1000, target_balance);
		}
		
		@Test 
		public void invalidAmountInTransfer () throws BankException, AccountException {
			this.mbway.createNewClient(source_phone,source_iban, "source_code");
			this.mbway.createNewClient(target_phone,target_iban, "transfer_code");
			this.transferController = new MBwayTransferController(source_phone,target_phone,-50,mbway,services);//amount is negative
			int result = transferController.transfer();
			int source_balance = (services.getAccountByIban(source_iban)).getBalance();
			int target_balance = (services.getAccountByIban(target_iban)).getBalance();
			assertEquals(2, result);
			assertEquals(1000, source_balance);
			assertEquals(1000, target_balance);
		}
		
		@Test 
		public void amountBiggerThanBalanceInTransfer () throws BankException, AccountException {
			this.mbway.createNewClient(source_phone,source_iban, "source_code");
			this.mbway.createNewClient(target_phone,target_iban, "transfer_code");
			this.transferController = new MBwayTransferController(source_phone,target_phone,1050,mbway,services); //amount is bigger than balance
			int result = transferController.transfer();
			int source_balance = (services.getAccountByIban(source_iban)).getBalance();
			int target_balance = (services.getAccountByIban(target_iban)).getBalance();
			assertEquals(2, result);
			assertEquals(1000, source_balance);
			assertEquals(1000, target_balance);
		}
		
		@Test 
		public void phoneNumberNotAssociatedInTransfer () throws BankException, AccountException {
			this.mbway.createNewClient(source_phone,source_iban, "source_code");
			this.transferController = new MBwayTransferController(source_phone,target_phone,1050,mbway,services);
			int result = transferController.transfer();
			int source_balance = (services.getAccountByIban(source_iban)).getBalance();
			assertEquals(3, result);
			assertEquals(1000, source_balance);
		}
		
		@After
		public void tearDown() {
			Bank.clearBanks();
			MBway.clearMBway();
		}
		
	}

