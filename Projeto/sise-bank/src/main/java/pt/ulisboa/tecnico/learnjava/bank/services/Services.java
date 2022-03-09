package pt.ulisboa.tecnico.learnjava.bank.services;

import pt.ulisboa.tecnico.learnjava.bank.domain.Account;
import pt.ulisboa.tecnico.learnjava.bank.domain.Bank;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.AccountException;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.BankException;

public class Services {
	public void deposit(String iban, int amount) throws AccountException, BankException {
		Account account = getAccountByIban(iban);

		account.deposit(amount);
	}

	public void withdraw(String iban, int amount) throws AccountException, BankException {
		Account account = getAccountByIban(iban);

		account.withdraw(amount);
	}

	public Account getAccountByIban(String iban) throws BankException {/////////////////////
		String code = iban.substring(0, 3);
		String accountId = iban.substring(3);

		Bank bank = Bank.getBankByCode(code);
		if (bank == null)////////////////ADICIONADO
			throw new BankException();
		Account account = bank.getAccountByAccountId(accountId);

		return account;
	}

}
