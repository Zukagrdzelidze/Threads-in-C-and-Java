// Transaction.java
/*
 (provided code)
 Transaction is just a dumb struct to hold
 one transaction. Supports toString.
*/
public class Transaction {
	public int from;
	public int to;
	public int amount;
	
   	public Transaction(int from, int to, int amount) {
		this.from = from;
		this.to = to;
		this.amount = amount;
	}
	public String toString() {
		return("from:" + from + " to:" + to + " amt:" + amount);
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null || obj.getClass() != getClass()) return false;
		return  (from == ((Transaction)obj).from) && (to == ((Transaction)obj).to) && (amount == ((Transaction)obj).amount);
	}

	public int getFromId(){
		   return from;
	}

	public int getToId() {
		   return to;
	}

	public int getMoney(){
		return amount;
	}
}

