package lesson9;


/**
 * Created by Артём on 17.10.2016.
 */
interface Terminal {
    String getStarted(String inputString);
    String callOpeartion(String id, String command, String pin, Integer sum) throws AccountIsLockedException;
    String getStateAccount(String id);
    String putCash(String id, int sum);
    String takeCash(String id, int sum);
    String setWork(String pin);
}
