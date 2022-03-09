package pt.ulisboa.tecnico.learnjava.sibs.mbway.domain;

import java.util.Hashtable;


public class MBway {	// O modelo so tem crude methods

	private static Hashtable<String, String[]> db = new Hashtable<String, String[]>();
	
	public void createNewClient(String phone, String iban, String code) {
		String[] values = new String[2];
		values[0] = iban;
		values[1] = code;
		db.put(phone, values);
	}

	public String getCodeByPhoneNumber(String phone) {
		return db.get(phone)[1];
	}
	
//	public void printdb() {
//		Object[] phones = getPhones();
//		for (int i = 0; i < db.size(); i++) {
//			String code = getCodeByPhoneNumber((String)phones[i]);
//			String iban = getIbanByPhoneNumber((String)phones[i]);
//			System.out.println("phone: " + phones[i] + "\niban: " + iban + "\ncode: " + code);
//		}
//	}
//	
//	public void printCodes() {
//		Object[] phones = getPhones();
//		for (int i = 0; i < db.size(); i++) {
//			System.out.println(getCodeByPhoneNumber((String)phones[i]));
//		}
//	}
//	
//	public void printIban() {
//		Object[] phones = getPhones();
//		for (int i = 0; i < db.size(); i++) {
//			System.out.println(getIbanByPhoneNumber((String)phones[i]));
//		}
//	}
	
	public String getIbanByPhoneNumber(String phone) {
		return db.get(phone)[0];
	}

	public boolean phoneNumberIsAssociated(String phone) {
		return db.containsKey(phone);
	}
	
	public Object[] getPhones() {
		return db.keySet().toArray();
	}
	
	public Hashtable<String, String[]> getDB() {
		return db;
	}
	
	public static void clearMBway() {
		db.clear();
	}
	
}
