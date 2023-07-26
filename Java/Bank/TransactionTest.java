import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class TransactionTest {


    @Test
    public void test1(){
        Transaction transaction = new Transaction(3, 4, 999);
        assertEquals(3, transaction.getFromId());
        assertEquals(4, transaction.getToId());
        assertEquals(999, transaction.getMoney());
    }

    @Test
    public void test2(){
        Transaction transaction = new Transaction(15, 1, 8);
        assertEquals(15, transaction.getFromId());
        assertEquals(1, transaction.getToId());
        assertEquals(8, transaction.getMoney());
    }


    @Test
    public void test3() {
        Bank bank = new Bank(5);
        Account acc = new Account(bank, 5, 950);
        acc.updateBalance(250);
        assertEquals("acct:5 bal:1200 trans:0", acc.toString());
    }

}
