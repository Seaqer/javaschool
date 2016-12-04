package sbt.lesson8;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Артём on 17.10.2016.
 */
public class TerminalImplTest {
    Terminal terminal;
    @Before
    public void setUp() throws Exception {
        terminal = new TerminalImpl();
    }

    @Test
    public void testGetBalance()  {
        assertEquals("0",terminal.getStarted("1,get,pincode"));
    }

    @Test
    public void testMoreArgument() {
        assertEquals("Неверное число аргументов",terminal.getStarted("1,put,pincode"));
    }

    @Test
    public void testNumberFormatException() {
        assertEquals("Некорретный ввод суммы",terminal.getStarted("1,get,pincode,1л0"));
    }

    @Test
    public void testCorrectSum() {
        assertEquals("Значение суммы должно быть кратно 100",terminal.getStarted("1,put,pincode,120"));
    }

}