package sbt.lesson8;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Артём on 17.10.2016.
 */
public class TerminalServerImplTest {
    TerminalServer server;

    @Before
    public void setUp() throws Exception {
        server = new TerminalServerImpl();
    }

    @Test
    public void testGetBalance() {
        assertEquals("0", server.getAccountCash("1").toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetUnknowID() {
        assertEquals("0", server.getAccountCash("0"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotEnoughMoney() {
        server.takeAccountCash("1", 100);
    }

    @Test
    public void testPutCache() {
        assertEquals("Операция выполнена", server.putAccountCash("1", 100));
    }

    @Test
    public void testTakeCache() {
        server.putAccountCash("1", 100);
        assertEquals("Операция выполнена", server.takeAccountCash("1", 100));
    }
}




