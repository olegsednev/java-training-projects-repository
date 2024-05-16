public class Main {
    public static void main(String[] args) {

        final Friend kolya = new Friend("Вася");
        final Friend petya = new Friend("Петя");

        new Thread(() -> kolya.throwBallToWithDeadLock(petya)).start();
        new Thread(() -> petya.throwBallToWithDeadLock(kolya)).start();

//        new Thread(() -> kolya.throwBallTo(petya)).start();
//        new Thread(() -> petya.throwBallTo(kolya)).start();
    }
}