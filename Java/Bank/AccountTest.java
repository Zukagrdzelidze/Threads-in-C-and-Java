import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {

    @Test
    public void test1(){
        Bank bank = new Bank(5);
        Account account = new Account(bank, 0, 1500);
        assertEquals(1500, account.getBalance());
    }
    @Test
    public void test2() {
        Bank bank = new Bank(5);
        Transaction transaction = new Transaction(3, 4, 700);
        Account.createTransaction(bank, transaction);
        assertEquals(300, bank.getAcc(3).getBalance());
        assertEquals(1700, bank.getAcc(4).getBalance());
    }

    @Test
    public void test3() {
        Bank bank = new Bank(5);
        Account account = new Account(bank, 0, 1000);
        account.updateBalance(850);
        assertEquals(1850, account.getBalance());
    }

    @Test
    public void test4() {
        Bank b = new Bank(5);
        Account acc = new Account(b, 0, 700);
        acc.updateBalance(300);
        assertEquals("acct:0 bal:1000 trans:0", acc.toString());
    }
}
