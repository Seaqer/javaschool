package lesson9;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Terminal terminal = new TerminalImpl();
        String inputString;
        Scanner in = new Scanner(System.in);

        System.out.println("Введите строку в формате:\n Проверить баланс - [id],get,[pass]\n Пополнить счет - [id],put,[pass],[sum]\n Снять со счета - [id],take,[pass],[sum]");
        while (true) {
            inputString = in.nextLine();
            if (inputString.equals("exit")) {
                break;
            }
            if (inputString.equals("work")) {
                System.out.println("Введите пароль");
                System.out.println(terminal.setWork(in.nextLine()));
            } else {
                System.out.println(terminal.getStarted(inputString));
            }
        }

    }
}
