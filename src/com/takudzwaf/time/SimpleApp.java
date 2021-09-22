package com.takudzwaf.time;

public class SimpleApp {
    public static void main(String[] args) throws InterruptedException {
        /*
            Simple Java main method acting as a playground for SimpleSopWatchTimer
         */

        System.out.println("Simple Stop Watch Playground ");
        SimpleStopWatchTimer simpleStopWatchTimer = new SimpleStopWatchTimer();
        simpleStopWatchTimer.start();
        Thread.sleep(1000);
        simpleStopWatchTimer.stop();
        simpleStopWatchTimer.printClock();

    }
}
