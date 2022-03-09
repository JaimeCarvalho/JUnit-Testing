package pt.ulisboa.tecnico.learnjava.sibs.mbway.controllers;

import pt.ulisboa.tecnico.learnjava.bank.exceptions.BankException;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.ClientException;
import pt.ulisboa.tecnico.learnjava.bank.services.Services;
import pt.ulisboa.tecnico.learnjava.sibs.mbway.domain.MBway;

public class AssociateMBwayController {

	private String iban;
	private String phone;
	private MBway mbway;
	private int code_number;
	private String code;
	Services services;
	
	public AssociateMBwayController(String _iban, String _phone, MBway _mbway, Services _services) {
		iban = _iban;
		phone = _phone;
		mbway = _mbway;
		services = _services;
	}
	
	public String setClient() {
		try {
			checkParameters();
		} catch (ClientException e1) {
			code = String.valueOf(1);
			return code;
		}
		try {
			services.getAccountByIban(iban);
			code_number = (int) Math.floor(Math.random()*(999999 - 100000 + 1) + 100000);
			code = String.valueOf(code_number);
			mbway.createNewClient(phone, iban, code);
		} catch (BankException e) {
			code = String.valueOf(0);
		}
		return code;
	}
	
	public void checkParameters() throws ClientException {
		if (phone.length() != 9 || !phone.matches("[0-9]+"))
			throw new ClientException();
	}
	
}
