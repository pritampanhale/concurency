package concurrency;

public class WaitNotifyDemo {

    int counter = 1;
    boolean ifPing = true;
    Object lock = new Object();

    static int N;

    public void ping() {
        synchronized (lock) {
            while (counter < N) {
                while (ifPing) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Ping");
                ifPing = !ifPing;
                counter++;
                lock.notify();
            }
        }
    }

    public void pong() {
        synchronized (lock) {
            while (counter < N) {
                while (!ifPing) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Pong");
                ifPing = !ifPing;
                counter++;
                lock.notify();
            }
        }
    }

    public void random() {
        int counter = 0;
        while (counter < N) {
            System.out.println("Randong");
            counter++;
        }
    }

    public static void main(String[] args) {
        N = 10;
        WaitNotifyDemo mt = new WaitNotifyDemo();
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                mt.pong();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                mt.ping();
            }
        });

        Thread t3 = new Thread(() -> {
            mt.random();
        });

        t1.start();
        t2.start();
        //t3.start();
    }
}
