import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertThrows;

public class BankTest {


    @Test
    public void constructor() {
        int numWorkers = 5;
        Bank b = new Bank(numWorkers);
    }

    @Test
    public void test() throws IOException, InterruptedException {
        int numWorkers = 5;
        Bank b = new Bank(numWorkers);
        b.processFile("5k.txt", numWorkers);
        for (int i = 0; i < Bank.ACCOUNTS; i++) {
            assertEquals(b.getAcc(i).getBalance(), 1000);
        }

    }


}
