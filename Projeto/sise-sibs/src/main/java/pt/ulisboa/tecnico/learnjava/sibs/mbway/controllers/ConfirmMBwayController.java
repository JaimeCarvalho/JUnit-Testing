package pt.ulisboa.tecnico.learnjava.sibs.mbway.controllers;

import pt.ulisboa.tecnico.learnjava.bank.exceptions.ClientException;
import pt.ulisboa.tecnico.learnjava.sibs.mbway.domain.MBway;

public class ConfirmMBwayController {

	private String phone;
	private String code;
	private MBway mbway;
	
	public ConfirmMBwayController(String _phone, String _code, MBway _mbway) {
		phone = _phone;
		code = _code;
		mbway = _mbway;
	}
	
	public int confirmMBwayCode() {
		try {
			checkParameters();
		} catch (ClientException e) {
			return 2;
		}
		if (mbway.phoneNumberIsAssociated(phone)) {
			if (code.equals(mbway.getCodeByPhoneNumber(phone)))
				return 1;
			else
				return 0;
		}
		return 2;
	}
	
	public void checkParameters() throws ClientException {
		if (phone.length() != 9 || !phone.matches("[0-9]+"))
			throw new ClientException();
	}
	
}
