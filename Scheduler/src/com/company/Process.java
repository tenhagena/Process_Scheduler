package com.company;

public class Process implements Comparable<Process>{
    //variable declaration
    private int priority;
    private int arrivalTime;
    private int burstTime;
    private int timeLeft;
    private int processID;
    private char algorithm;
    private boolean complete;
    private int waitTime;
    private int turnAroundTime;

    //Getters and Setters
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public int getProcessID() {
        return processID;
    }

    public void setProcessID(int processID) {
        this.processID = processID;
    }

    public void tick(){
        timeLeft--;
    }

    public boolean isComplete() {
        return complete;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public int getTurnAroundTime() {
        return turnAroundTime;
    }

    public void completeProcess(int time){
        complete = true;
        turnAroundTime = time;
        waitTime = turnAroundTime - arrivalTime - burstTime;
    }

    public Process(int arrivalTime, int burstTime, int priority, int processID, char algorithm) {
        //Default constructor for class
        this.priority = priority;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.timeLeft = burstTime;
        this.processID = processID;
        this.algorithm = algorithm;

        this.complete = false;
}

    @Override
    public String toString() {
        return "Process{PID:"+ processID+", AT:"+ arrivalTime +", BT:" + burstTime+", TimeLeft:" + timeLeft+", Priority:" + priority+"}\n";
    }

    @Override
    public int compareTo(Process o) {
        switch (algorithm){
            case 'f': return Integer.compare(this.arrivalTime,o.arrivalTime); // First come first serve
            case 's': return Integer.compare(this.timeLeft,o.timeLeft);
            case 'p': return Integer.compare(this.priority,o.priority);
            case 'r': return Integer.compare(this.arrivalTime,o.arrivalTime);
            case 'j': return Integer.compare(this.burstTime,o.burstTime);
            case 'v': return Integer.compare(this.arrivalTime,o.arrivalTime);
        }
        return Integer.compare(this.arrivalTime,o.arrivalTime);
    }
}
