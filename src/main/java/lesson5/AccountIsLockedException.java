package lesson5;
/**

 * Created by Артём on 25.09.16
 */
class AccountIsLockedException extends Exception {

    AccountIsLockedException(long time) {
        super(String.format("Терминал временно заблокирован, до разблокировки: %d секунд", time / 1000));
    }
}
