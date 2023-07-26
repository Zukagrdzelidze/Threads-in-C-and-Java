// Bank.java

/*
 Creates a bunch of accounts and uses threads
 to post transactions to the accounts concurrently.
*/

import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class Bank {
	public static final int ACCOUNTS = 20;	 // number of accounts
	private final Transaction nullTrans = new Transaction(-1, 0, 0);
	private CountDownLatch latch;
	private List<Account> list;
	private BlockingQueue<Transaction> que;



	public Bank(int num){
		latch = new CountDownLatch(num);
		que = new ArrayBlockingQueue<Transaction>(num);
		list = new ArrayList<Account>();
		for(int i = 0; i < ACCOUNTS; i++){
			Account forList = new Account(this, i, 1000);
			list.add(forList);
		}
	}
	
	/*
	 Reads transaction data (from/to/amt) from a file for processing.
	 (provided code)
	 */
	public void readFile(String file) {
			try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			// Use stream tokenizer to get successive words from file
			StreamTokenizer tokenizer = new StreamTokenizer(reader);
			
			while (true) {
				int read = tokenizer.nextToken();
				if (read == StreamTokenizer.TT_EOF) break;  // detect EOF
				int from = (int)tokenizer.nval;
				
				tokenizer.nextToken();
				int to = (int)tokenizer.nval;
				
				tokenizer.nextToken();
				int amount = (int)tokenizer.nval;
				
				// Use the from/to/amount
				
				Transaction transaction = new Transaction(from, to, amount);
				que.put(transaction);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/*
	 Processes one file of transaction data
	 -fork off workers
	 -read file into the buffer
	 -wait for the workers to finish
	*/
	public void processFile(String file, int numWorkers) throws InterruptedException {
		for(int i = 0; i < numWorkers; i++){
			Worker worker = new Worker();
			worker.start();
		}
		readFile(file);
		for(int i = 0; i < numWorkers; i++){
			que.put(nullTrans);
		}
		latch.await();
		for(int i = 0; i < list.size(); i++){
			System.out.println(list.get(i));
		}
	}
	public Account getAcc(int n){
		return list.get(n);
	}


	/*
	 Looks at commandline args and calls Bank processing.
	*/
	public static void main(String[] args) throws InterruptedException {
		// deal with command-lines args
		if (args.length == 0) {
			System.out.println("Args: transaction-file [num-workers [limit]]");
			System.exit(1);
		}
		
		String file = args[0];
		
		int numWorkers = 1;
		if (args.length >= 2) {
			numWorkers = Integer.parseInt(args[1]);
		}

		
		Bank bank = new Bank(numWorkers);
		bank.processFile(file, numWorkers);
	}

	private class Worker extends Thread {
		@Override
		public void run(){
			while(true){
				Transaction transaction = null;
				try {
					transaction = que.take();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				if(transaction.equals(nullTrans)) break;
				Account.createTransaction(Bank.this, transaction);
				latch.countDown();
			}
		}
	}
}

