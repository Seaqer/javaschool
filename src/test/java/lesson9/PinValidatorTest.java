package lesson9;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Артём on 17.10.2016.
 */
public class PinValidatorTest {
    PinValidator pinValidator;

    @Before
    public void setUp() throws Exception {
        pinValidator = new PinValidator();
    }

    @Test
    public void testValidate() throws Exception {
        assertTrue("testValidation fail", pinValidator.validate("pincode"));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testWrongPasswordExceprion() throws Throwable {
        pinValidator.validate("wrongpass");
    }

    @Test(expected = AccountIsLockedException.class)
    public void testAccountIsLockedException() throws AccountIsLockedException {
        for (int i = 0; i < 5; i++) {
            try {
                pinValidator.validate("wrongprass");
            } catch (IllegalArgumentException e) {
            }
        }
    }
}

