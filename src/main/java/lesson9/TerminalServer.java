package lesson9;

/**
 * Created by Артём on 17.10.2016.
 */
interface TerminalServer {
    Integer getAccountCash(String id);
    String takeAccountCash(String id, Integer sum);
    String putAccountCash(String id, Integer sum);
    String setWork();
}
