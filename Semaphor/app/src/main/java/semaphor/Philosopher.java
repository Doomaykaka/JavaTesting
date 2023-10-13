package semaphor;

import java.util.concurrent.Semaphore;

class Philosopher extends Thread {

    private Semaphore sem;

    // поел ли философ
    private boolean full = false;

    private String name;

    Philosopher(Semaphore sem, String name) {
        this.sem = sem;
        this.name = name;
    }

    public void run() {
        try {
            // если философ еще не ел
            if (!full) {
                // Запрашиваем у семафора разрешение на выполнение
                sem.acquire();
                System.out.println(name + " садится за стол");

                // философ ест
                sleep(300);
                full = true;

                System.out.println(name + " поел! Он выходит из-за стола");
                sem.release();

                // философ ушел, освободив место другим
                sleep(300);
            }
        } catch (InterruptedException e) {
            System.out.println("Что-то пошло не так!");
        }
    }
}
