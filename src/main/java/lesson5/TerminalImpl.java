package lesson5;

/**
 * Created by Артём on 24.09.16
 */
class TerminalImpl {
    private final TerminalServer server;
    private final PinValidator pinValidator;


    TerminalImpl() {
        server = new TerminalServer();
        pinValidator = new PinValidator();
    }

    /**
     *
     */
    String getStarted(String inputString) {
        try {
            String[] args = inputString.split(",");
            String answer;

            switch (args.length) {
                case 3:
                    if (args[1].equals("get")) {
                        answer = callOpeartion(args[0], args[1], args[2], -1);
                    } else {
                        throw new IllegalArgumentException("Неверное число аргументов");
                    }
                    break;
                case 4:
                    answer = callOpeartion(args[0], args[1], args[2], Integer.parseInt(args[3]));
                    break;
                default:
                    throw new IllegalArgumentException("Неверное число аргументов");
            }
            return answer;

        } catch (NumberFormatException ex) {
            return "Некорретный ввод суммы";
        } catch (IllegalArgumentException | IllegalStateException | AccountIsLockedException ex) {
            return ex.getMessage();
        } catch (Exception ex) {
            return "Ошибка в работе терминала";
        }
    }

    /**
     * Выбрать операцию
     */
    private String callOpeartion(String id, String command, String pin, Integer sum) throws IllegalArgumentException, IllegalStateException, AccountIsLockedException {
        String answer = "";

        if (pinValidator.validate(pin)) {
            switch (command) {
                case "get":
                    answer = getStateAccount(id);
                    break;
                case "put":
                    answer = putCash(id, sum);
                    break;
                case "take":
                    answer = takeCash(id, sum);
                    break;
                default:
                    throw new IllegalArgumentException("Неизвестная команда: " + command);
            }
        }
        return answer;
    }

    /**
     * Проверить состояние счета
     */
    private String getStateAccount(String id) throws IllegalStateException {
        return server.getAccountCash(id).toString();
    }

    /**
     * Положить деньги на счет
     */
    private String putCash(String id, int sum) throws IllegalArgumentException, IllegalStateException {

        if (sum % 100 == 0) {
            return server.putAccountCash(id, sum);
        }
        throw new IllegalArgumentException("Значение суммы должно быть кратно 100");
    }

    /**
     * Снять деньги со счета
     */
    private String takeCash(String id, int sum) throws IllegalArgumentException, IllegalStateException {

        if (sum % 100 == 0) {
            return server.takeAccountCash(id, sum);
        }
        throw new IllegalArgumentException("Значение суммы должно быть кратно 100");
    }

    String setWork(String pin) {
        try {
            if (pinValidator.validate(pin)) {
                return server.setWork();
            }
        } catch (IllegalArgumentException | IllegalStateException | AccountIsLockedException ex) {
            return ex.getMessage();
        } catch (Exception ex) {
            return "Ошибка в работе терминала";
        }
        return null;
    }
}
