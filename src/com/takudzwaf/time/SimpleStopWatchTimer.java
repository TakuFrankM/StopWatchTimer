package com.takudzwaf.time;

import java.util.ArrayList;


public class SimpleStopWatchTimer {
    /*
     * todo:=> Add the lap functionality ... Make a different interface for laptimes and use the arraylist
     * todo:=> Finish the JavaDocs
     */

    /**
     * Enum representing the current state of the time.
     * Ready: Stop watch has been "zeroed" and is ready to be used
     * Running: Stop watch is "running" i.e. keeping track of time
     * Paused: Stop watch is still while holding any previous time(s)
     * Stopped: Stop watch has been stopped and is not keeping track of time
     */
    public enum STATUS {
        READY, RUNNING, PAUSED, STOPPED
    }

    private STATUS status;
    private long mainTime;

    private long baseStartTime;
    private long resumeStartTime;
    private long pauseStartTime;

    private long pauseTimeAccumulator;
    private long timeAccumulator;

    private ArrayList<Long> laptimes;

    public SimpleStopWatchTimer() {
        this.reset();
    }

    public void reset() {
        // Zero everything
        this.mainTime = 0;
        this.timeAccumulator = 0;
        this.pauseTimeAccumulator = 0;
        this.laptimes = new ArrayList<>();
        this.status = STATUS.READY;

    }

    public void start() {
        switch (this.status) {
            case RUNNING: // In case its already running do nothing
                break;
            case PAUSED: // Resumes timer is previously paused
                this.resume();
                break;
            case STOPPED: // Starts timer from zero if previously stopped
                this.reset();
            case READY: // Start the timer as normal
                this.baseStartTime = System.currentTimeMillis();
                this.resumeStartTime = this.baseStartTime;
                this.status = STATUS.RUNNING;
        }
    }

    public void pause() {
        this.status = STATUS.PAUSED;

        // Add the difference to the accumulator and record the time it was paused
        this.timeAccumulator += System.currentTimeMillis() - resumeStartTime;
        this.pauseStartTime = System.currentTimeMillis();
        this.updateValues();
    }

    public void resume() {
        this.pauseTimeAccumulator += System.currentTimeMillis() - pauseStartTime;
        this.resumeStartTime = System.currentTimeMillis();
        this.status = STATUS.RUNNING;
    }

    public void stop() {
        if (this.status == STATUS.RUNNING)
            this.updateAccumulator();
        this.status = STATUS.STOPPED;
        this.updateValues();
    }

    public void lap() {
        this.updateValues();
        this.laptimes.add(getTimeElapsed());
    }

    private void updateAccumulator() {
        long timeSinceResume = (System.currentTimeMillis() - resumeStartTime);
        this.timeAccumulator += timeSinceResume;
    }

    public long getTimeElapsed() {
        if (this.status == STATUS.RUNNING) {
            long timeSinceResume = (System.currentTimeMillis() - resumeStartTime);
            return this.timeAccumulator + timeSinceResume;
        }
        return this.timeAccumulator;
    }

    private void updateValues() {
        this.mainTime = this.getTimeElapsed();
    }

    public Long getMainTime() {
        // Update values to just for error mitigation and return
        this.updateValues();
        return this.mainTime;
    }

    public boolean isRunning() {
        return (this.status == STATUS.RUNNING);
    }

    public ArrayList<Long> getLaptimes() {
        return this.laptimes;
    }


    /**
     * todo:=> Thing about implementing a listener system using this method and how not to make this clash with the main stopWatch
     *
     */
//    public void runForTime(long runTime) {
//        start();
//        while (System.currentTimeMillis() - resumeStartTime < runTime) {
//            assert true;
//        }
//        System.out.println(new CompactTime(System.currentTimeMillis() - resumeStartTime));
//    }

    // todo: Make use of utility methods for a more readably, user friendly output
    public void printClock() {
        System.out.println(this.mainTime);
    }

    // todo: Refactor utility methods below to aid with flexibility
//    public long getHours() {
//        return mainTime.getHours();
//    }
//
//    public long getMinutes() {
//        return mainTime.getMinutes();
//    }
//
//    public long getSeconds() {
//        return mainTime.getSeconds();
//    }
//
//    public long getMilliSeconds() {
//        return mainTime.getMilliSeconds();
//    }

    @Override
    public String toString() {
        return "SimpleStopWatchTimer{" +
                "status=" + status +
                ", mainTime=" + mainTime +
                ", baseStartTime=" + baseStartTime +
                ", resumeStartTime=" + resumeStartTime +
                ", pauseStartTime=" + pauseStartTime +
                ", pauseTimeAccumulator=" + pauseTimeAccumulator +
                ", timeAccumulator=" + timeAccumulator +
                ", laptimes=" + laptimes +
                '}';
    }
}