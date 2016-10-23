package lesson8;


/**
 * Created by Артём on 24.09.16
 */
class PinValidator {
    private String pin = "pincode";
    private int countIncorrectAttempts;
    private long lockTime;


    PinValidator() {
        countIncorrectAttempts = 0;
        lockTime = -1;
    }

    /**
     * Проверка пароля
     */
    boolean validate(String pin) throws IllegalArgumentException, AccountIsLockedException {

        if (!isLoked() && this.pin.equals(pin)) {
            countIncorrectAttempts = 0;
            return true;
        } else {
            countIncorrectAttempts += 1;
            if (countIncorrectAttempts > 2 && lockTime < 0) {
                lockTime = System.currentTimeMillis() + 5000;
            }
            throw new IllegalArgumentException("Некорректный пароль");
        }
    }

    /**
     * Проверка блокировки терминала
     */
    private boolean isLoked() throws AccountIsLockedException {

        if (lockTime > 0) {
            long lastTime = lockTime - System.currentTimeMillis();
            if (lastTime > 0) {
                throw new AccountIsLockedException(lastTime);
            }
            countIncorrectAttempts = 0;
            lockTime = -1;
        }
        return false;
    }
}
