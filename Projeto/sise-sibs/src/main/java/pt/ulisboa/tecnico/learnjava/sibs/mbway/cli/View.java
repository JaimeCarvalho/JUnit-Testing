package pt.ulisboa.tecnico.learnjava.sibs.mbway.cli;

import java.util.Scanner;

import pt.ulisboa.tecnico.learnjava.bank.domain.Bank;
import pt.ulisboa.tecnico.learnjava.bank.domain.Client;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.AccountException;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.BankException;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.ClientException;
import pt.ulisboa.tecnico.learnjava.bank.services.Services;
import pt.ulisboa.tecnico.learnjava.bank.domain.Bank.AccountType;
import pt.ulisboa.tecnico.learnjava.sibs.mbway.controllers.AssociateMBwayController;
import pt.ulisboa.tecnico.learnjava.sibs.mbway.controllers.ConfirmMBwayController;
import pt.ulisboa.tecnico.learnjava.sibs.mbway.controllers.MBwaySplitInsuranceController;
import pt.ulisboa.tecnico.learnjava.sibs.mbway.controllers.MBwayTransferController;
import pt.ulisboa.tecnico.learnjava.sibs.mbway.domain.MBway;
import pt.ulisboa.tecnico.learnjava.sibs.mbway.domain.MBwayFriends;

public class View {

	
	public static void main(String[] args) throws BankException, ClientException, AccountException {
		Scanner sc = new Scanner(System.in);
		String iban;
		String phone_number;
		String confirmation_code;
		String target_phone_number;
		int amount;
		int num_family_members;
		View view = new View();
		MBway mbway = new MBway();
		
		// Populate´
		Services services = new Services();
		Bank bank1 = new Bank("CGD");
		Bank bank2 = new Bank("BPI");
		
		Client client1 = new Client(bank1, "Maria", "Soares", "123456789", "912346987", "Rua Alves Redol", 25);
		Client client2 = new Client(bank2, "Clara", "Rodrigues", "987654321", "917895234", "Rua Redol", 30);
		Client client3 = new Client(bank2, "Marco", "Andrade", "345123678", "967896734", "Rua da Figueira", 40);
		
		String iban1 = bank1.createAccount(AccountType.CHECKING, client1, 1000, 0);
		String iban2 = bank2.createAccount(AccountType.CHECKING, client2, 5000, 0);
		String iban3 = bank2.createAccount(AccountType.CHECKING, client3, 2500, 0);
		
		
		while (true) {
			String metodo = sc.next();
			switch(metodo) {
			case "exit":
				sc.close();
				System.exit(0);
				break;
			case "associate-mbway":
				iban = sc.next();
				phone_number = sc.next();
				AssociateMBwayController task1 = new AssociateMBwayController(iban, phone_number, mbway, services);
				confirmation_code = task1.setClient();
				view.associateMBwayView(confirmation_code);
				break;
			case "confirm-mbway":
				phone_number = sc.next();
				confirmation_code = sc.next();
				ConfirmMBwayController task2 = new ConfirmMBwayController(phone_number, confirmation_code, mbway);
				int result_task2 = task2.confirmMBwayCode();
				System.out.println();
				view.confirmMBwayView(result_task2);
				break;
			case "mbway-transfer":
				phone_number = sc.next();
				target_phone_number = sc.next();
				amount = sc.nextInt();
				MBwayTransferController task3 = new MBwayTransferController(phone_number, target_phone_number, amount, mbway, services);
				int result_task3 = task3.transfer();
				view.transferView(result_task3);
				break;
			case "mbway-split-insurance":
				MBwayFriends mbway_friends = new MBwayFriends();
				num_family_members = sc.nextInt();
				amount = sc.nextInt();
				boolean end = false;
				while(end == false) {
					String input = sc.next();
					switch(input) {
					case "end":
						end = true;
						break;
					case "friend":
						phone_number = sc.next();
						int amount_friend = sc.nextInt();
						mbway_friends.addFriend(phone_number, amount_friend);
						break;
					}
				}
				MBwaySplitInsuranceController task4 = new MBwaySplitInsuranceController(num_family_members, amount, mbway_friends, mbway);
				int result_task4 = task4.splitInsurance();
				view.splitInsuranceView(result_task4);
				break;
			default:
				view.invalidInputView();
			}
			
			
		}

	}

	private void invalidInputView() {
		System.out.println("This input is not valid. Try again.");
	}

	private void splitInsuranceView(int result) {
		if (result == 0)
			System.out.println("Oh no! One family member is missing.");
		else if (result == 1)
			System.out.println("Insurance paid successfully!");
		else if (result == 2)
			System.out.println("Oh no! More than one family member is missing.");
		else if (result == 3)
			System.out.println("Oh no! You have one too many family members.");
		else if (result == 4)
			System.out.println("Oh no! Too many family members.");
		else if (result == 5)
			System.out.println("Something is wrong. Is the insurance amount right?");
		else if (result == 6)
			System.out.println("Oh no! One family member does not have MBway.");
		else if (result == 7)
			System.out.println("Oh no! More than one family member does not have MBway");
		else if (result == 8)
			System.out.println("Something is wrong. Are the phone numbers correct?");
		else if (result == 9)
			System.out.println("Oh no! At least one family member doesn't have money to pay!");
	}

	private void transferView(int result) {
		if (result == 0)
			System.out.println("Something is wrong. Are the phone numbers correct?");
		else if (result == 1)
			System.out.println("Transfer perfomed successfully!");
		else if (result == 2)
			System.out.println("Not enough money on the source account.");
		else
			System.out.println("Wrong phone number.");
	}

	public void associateMBwayView(String code) {
		if (code.equals("0"))
			System.out.println("This IBAN is invalid. Try association again.");
		else if (code.equals("1"))
			System.out.println("This phone number is invalid. Try association again.");
		else
			System.out.println("Code: " + code + " (don't share it with anyone)");
	}
	
	private void confirmMBwayView(int result) {
		if (result == 0)
			System.out.println("Wrong confirmation code. Try association again.");
		else if (result == 1)
			System.out.println("MBWAY association confirmed successfully!");
		else
			System.out.println("This phone number is invalid. Try association again.");
	}
	
}