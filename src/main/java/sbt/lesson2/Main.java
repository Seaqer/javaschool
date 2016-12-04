package sbt.lesson2;

public class Main {


    public static void main(String[] args) {
        Person manIgor = new Person(true, "Igor");
        Person womanLiza = new Person(false, "Liza");
        Person womanElise = new Person(false, "Elise");
        Person manEgor = new Person(true, "Elise");

        if (!womanElise.marry(manEgor)) {
            System.out.println("ERROR: womanElise.marry(manEgor) not married");
        }

        if (!manIgor.marry(womanLiza)) {
            System.out.println("ERROR: manIgor.marry(womanLiza) not married");
        }

        if (manIgor.marry(womanLiza)) {
            System.out.println("ERROR: manIgor.marry(womanLiza) remarried");
        }

        if (!manIgor.marry(womanElise)) {
            System.out.println("ERROR: manIgor.marry(womanElise) not married");
        }

        if (manEgor.marry(manIgor)) {
            System.out.println("ERROR: manEgor.marry(manIgor) married");
        }

        if (manIgor.marry(null)) {
            System.out.println("ERROR: manIgor.marry(null)");
        }
    }
}






