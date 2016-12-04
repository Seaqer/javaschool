package sbt.lesson5;

import java.util.*;

/**
 * Created by Артём on 24.09.16
 */
class TerminalServer {
    private final HashMap<String, Integer> customers;
    private boolean workOnServer;


    TerminalServer() {
        customers = new HashMap<>();
        workOnServer = false;
        customers.put("1", 0);
        customers.put("2", 0);
        customers.put("3", 0);
    }

    /**
     * Получить состояние счета
     */
    Integer getAccountCash(String id) throws IllegalArgumentException, IllegalStateException {

        if (!workOnServer) {
            for (Map.Entry<String, Integer> entry : customers.entrySet()) {
                if (entry.getKey().equals(id)) {
                    return entry.getValue();
                }
            }
            throw new IllegalArgumentException(String.format("Cчёт c ID %s отсутсвует", id));
        }
        throw new IllegalStateException("Проводятся регламентные работы");
    }

    /**
     * Получить деньги со счета
     */
    String takeAccountCash(String id, Integer sum) throws IllegalArgumentException, IllegalStateException {

        if (!workOnServer) {
            int cash = getAccountCash(id) - sum;

            if (cash >= 0) {
                customers.put(id, cash);
                return "Операция выполнена";
            } else {
                throw new IllegalArgumentException("Недостаточно денег на счёте");
            }
        }
        throw new IllegalStateException("Проводятся регламентные работы");
    }

    /**
     * Положить деньги на счет
     */
    String putAccountCash(String id, Integer sum) throws IllegalStateException {

        if (!workOnServer) {
            customers.put(id, getAccountCash(id) + sum);
            return "Операция выполнена";
        }
        throw new IllegalStateException("Проводятся регламентные работы");
    }

    /**
     * Установить состояние сервера для регламентных работ
     */
    String setWork() {
        workOnServer = !workOnServer;
        return Boolean.toString(workOnServer);
    }
}
