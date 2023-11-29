package idioms;

public class WaitInCycleExample {
    public class CounterThread implements Runnable {
        private boolean isBusy;

        @Override
        public void run() {

            isBusy = true;

            for (int repeats = 0; repeats < 2; repeats++) {

                for (int tick = 0; tick < 10; tick++) {
                    System.out.println(tick + " tick");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                synchronized (this) {
                    this.notify();
                }
            }
            isBusy = false;

        }

        public boolean getIsBusy() {
            return this.isBusy;
        }
    }

    public class WaitingThread implements Runnable {
        private CounterThread counterThreadInstance;

        @Override
        public void run() {
            if (counterThreadInstance != null) {
                while (counterThreadInstance.getIsBusy()) {
                    synchronized (counterThreadInstance) {
                        try {
                            System.out.println("Waiting");
                            counterThreadInstance.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                System.out.println("Counter thread died");
            }
        }

        public void setCounterThread(CounterThread counterThread) {
            counterThreadInstance = counterThread;
        }
    }

    public void useWait() {
        CounterThread myCounterThread = new CounterThread();
        WaitingThread myWaitingThread = new WaitingThread();
        myWaitingThread.setCounterThread(myCounterThread);

        Thread cThread = new Thread(myCounterThread);
        Thread wThread = new Thread(myWaitingThread);

        cThread.start();
        wThread.start();

        try {
            cThread.join();
            wThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
