// Cracker.java
/*
 Generates SHA hashes of short strings in parallel.
*/

import java.security.*;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class Cracker {
	// Array of chars used to produce strings
	public static final char[] CHARS = "abcdefghijklmnopqrstuvwxyz0123456789.,-!".toCharArray();

	private int num;
	private CountDownLatch latch;
	private byte[] arr;

	public Cracker(int num, String str){
		this.num = num;
		latch = new CountDownLatch(num);
		this.arr = hexToArray(str);
	}

	public void crack(int len) throws InterruptedException, NoSuchAlgorithmException {
		boolean b = true;
		int start = 0;
		int end = -1;
		while(true){
			if(!b){
				break;
			}
			start = end + 1;
			int temp = CHARS.length / num;
			end += temp;
			if(end >= CHARS.length - 1){
				end = CHARS.length - 1;
				b = false;
			}
			ThreadPassword pass = new ThreadPassword(start, end, len);
			pass.start();
		}
		latch.await();
		System.out.println("all done");
	}
	
	/*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	*/
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	
	/*
	 Given a string of hex byte values such as "24a26f", creates
	 a byte[] array of those values, one byte value -128..127
	 for each 2 chars.
	 (provided code)
	*/
	public static byte[] hexToArray(String hex) {
		byte[] result = new byte[hex.length()/2];
		for (int i=0; i<hex.length(); i+=2) {
			result[i/2] = (byte) Integer.parseInt(hex.substring(i, i+2), 16);
		}
		return result;
	}
	
	
	
	public static void main(String[] args) throws NoSuchAlgorithmException, InterruptedException {
		if (args.length < 1) {
			System.out.println("Args: target length [workers]");
			System.exit(1);
		}
		// args: targ len [num]
		String targ = args[0];

		int num = 1;
		if (args.length>2) {
			int len = Integer.parseInt(args[1]);
			num = Integer.parseInt(args[2]);
			Cracker cracker = new Cracker(num, targ);
			cracker.crack(len);
		}else{
			generateHashPassword(targ);
		}
		// a! 34800e15707fae815d7c90d49de44aca97e2d759
		// xyz 66b27417d37e024c46526c2f6d358a754fc552f3
		
		// YOUR CODE HERE
	}

	private static void generateHashPassword(String targ) throws NoSuchAlgorithmException {

		MessageDigest message = MessageDigest.getInstance("SHA-1");
		message.update(targ.getBytes());
		String hash = hexToString(message.digest());
		System.out.println(hash);
	}

	private class ThreadPassword extends Thread{
		private int start;
		private int end;
		private int len;
		private MessageDigest message;

		public ThreadPassword(int start, int end, int len) throws NoSuchAlgorithmException {
			this.start = start;
			this.end = end;
			this.len = len;
			message = MessageDigest.getInstance("SHA-1");
		}
		@Override
		public void run(){
			for(int i = start; i < (end + 1); i++){
				StringBuilder str = new StringBuilder(String.valueOf(CHARS[i]));
				generateString(str);
			}
			latch.countDown();
		}

		private void generateString(StringBuilder str){
			if(str.length() > len){
				return;
			}
			message.update(str.toString().getBytes());
			if(Arrays.equals(message.digest(), arr)){
				System.out.println(str);
			}
			for(int j = 0; j < CHARS.length; j++){
				str.append(CHARS[j]);
				generateString(str);
				str.deleteCharAt(str.length() - 1);
			}
		}
	}
}
