// Account.java

/*
 Simple, thread-safe Account class encapsulates
 a balance and a transaction count.
*/
public class Account {
	private int id;
	private int balance;
	private int transactions;
	
	// It may work out to be handy for the account to
	// have a pointer to its Bank.
	// (a suggestion, not a requirement)
	private Bank bank;  
	
	public Account(Bank bank, int id, int balance) {
		this.bank = bank;
		this.id = id;
		this.balance = balance;
		transactions = 0;
	}

	public int getBalance(){
		return balance;
	}

	public synchronized void updateBalance(int amount){
		balance += amount;
	}



	@Override
	public String toString() {
		String acc = "acct:";
		String bal = "bal:";
		String trans = "trans:";
		String result = acc + id + " " + bal + balance + " " + trans + transactions;
		return result;
	}

	public static void createTransaction(Bank bank, Transaction transaction){
		Account first = bank.getAcc(transaction.getFromId());
		Account second = bank.getAcc(transaction.getToId());
		int money = transaction.getMoney();
		first.updateBalance(money * (-1));
		second.updateBalance(money);
		first.transactions++;
		second.transactions++;
	}
	
}
