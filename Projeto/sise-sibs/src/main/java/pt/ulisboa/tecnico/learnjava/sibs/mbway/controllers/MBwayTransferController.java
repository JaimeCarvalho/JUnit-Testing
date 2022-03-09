package pt.ulisboa.tecnico.learnjava.sibs.mbway.controllers;

import pt.ulisboa.tecnico.learnjava.bank.exceptions.AccountException;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.BankException;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.ClientException;
import pt.ulisboa.tecnico.learnjava.bank.services.Services;
import pt.ulisboa.tecnico.learnjava.sibs.mbway.domain.MBway;

public class MBwayTransferController {

	private String source_phone;
	private String target_phone;
	private int amount;
	private MBway mbway;
	private Services services;
	
	public MBwayTransferController(String _source_phone, String _target_phone, int _amount, MBway _mbway, Services _services) {
		source_phone = _source_phone;
		target_phone = _target_phone;
		amount = _amount;
		mbway = _mbway;
		services = _services;
	}
	
	public int transfer() throws AccountException, BankException {
		try {
			checkParameters();
		} catch (ClientException e1) {
			return 0;
		}
		if (mbway.phoneNumberIsAssociated(source_phone) &&
				mbway.phoneNumberIsAssociated(target_phone)) {
			String iban_source = mbway.getIbanByPhoneNumber(source_phone);
			String iban_target = mbway.getIbanByPhoneNumber(target_phone);
			try {
				services.withdraw(iban_source, amount);				
			} catch (AccountException e) {
				return 2;
			} catch (BankException e) {
				return 2;
			}
			try {
				services.deposit(iban_target, amount);
			} catch (BankException e) {
				services.deposit(iban_source, amount);
				return 2;
			}
			return 1;
		}
		return 3;
	}
	
	public void checkParameters() throws ClientException {
		if (source_phone.length() != 9 || !source_phone.matches("[0-9]+"))
			throw new ClientException();
		else if (target_phone.length() != 9 || !target_phone.matches("[0-9]+"))
			throw new ClientException();
	}
	
}
