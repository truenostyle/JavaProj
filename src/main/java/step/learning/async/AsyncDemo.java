package step.learning.async;

import java.util.Locale;
import java.util.concurrent.CountDownLatch;

public class AsyncDemo {
    private final CountDownLatch latch1 = new CountDownLatch(1);
    private final CountDownLatch latch2 = new CountDownLatch(2);

    public void run() {
        int month = 12;
        Thread[] threads = new Thread[month];
        sum = 100.0;
        System.out.println("AsyncDemo");
        for (int i = 0; i < month; i++) {
            threads[i] = new Thread( new MonthRate(i + 1));
            threads[i].start();
        }

        try {
            for(int i = 0; i < month; i++)
            {
                threads[i].join();
            }
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.printf(Locale.US,"----------%ntotal: %.2f%n", sum);
//
//        Thread thread1 = new Thread(() -> {
//            System.out.println("1 start");
//            try {
//                latch2.await();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("1 finish");
//        });
//
//        Thread thread2 = new Thread(() -> {
//            System.out.println("2 start");
//            latch1.countDown();
//            System.out.println("2 finish");
//            latch2.countDown();
//        });
//
//        Thread thread3 = new Thread(() -> {
//            try {
//                latch1.await();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("3 start");
//            System.out.println("3 finish");
//            latch2.countDown();
//        });
//
//        Thread finalThread = new Thread(() -> {
//            try {
//                latch2.await();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("final");
//        });
//
//        thread1.start();
//        thread2.start();
//        thread3.start();
//        finalThread.start();
   }

    private double sum;
    private Object sumLocker = new Object();
   class MonthRate implements Runnable {

        private final int month;


       public MonthRate(int month) {
           this.month = month;
       }

       @Override
       public void run() {

               try {
                   Thread.sleep(1000);
               }
               catch (InterruptedException ignore) {}
               double p = 0.1;
               double localSum;
           synchronized (sumLocker) {
               localSum = sum = sum * (1.0 + p);

           }
           System.out.printf(Locale.US, "month: %02d, percent: %.2f, sum: %.2f%n", month, p, localSum);
        }
   }
}
