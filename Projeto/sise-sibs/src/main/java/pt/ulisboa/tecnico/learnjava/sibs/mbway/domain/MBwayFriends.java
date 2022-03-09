package pt.ulisboa.tecnico.learnjava.sibs.mbway.domain;

import java.util.Hashtable;

public class MBwayFriends {

	private Hashtable<String, Integer> db = new Hashtable<String, Integer>();
	
	public void addFriend(String phone, int amount) {
		db.put(phone, amount);
	}

	public int getTotalAmount() {
		int total_amount = 0;
		for (Integer i : db.values()) {
			total_amount += i;
		}
		return total_amount;
	}
	
	public Hashtable<String, Integer> getDB() {
		return db;
	}
	
	public int getTotalNumberOfFriends() {
		return db.size();
	}
	
	public String getPhoneNumber(int i) {
		return (String) db.keySet().toArray()[i];
	}
	
	public int getAmount(String phone) {
		return db.get(phone);
	}
	
}
