import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;

public class CrackerTest {

    @Test
    public void test1() throws NoSuchAlgorithmException, InterruptedException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);
        String[] args = new String[1];
        args[0] = "molly";
        Cracker.main(args);
        assertEquals("4181eecbd7a755d19fdf73887c54837cbecf63fd\n", os.toString());
    }



    @Test
    public void test3() throws NoSuchAlgorithmException, InterruptedException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);
        String[] args = new String[1];
        args[0] = "flomo";
        Cracker.main(args);
        assertEquals("886ffd41c568469795a19f52486bdde64f5f5bcc\n", os.toString());
    }
}
