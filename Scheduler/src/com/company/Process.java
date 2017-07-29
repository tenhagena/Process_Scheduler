package com.company;

import javax.print.DocFlavor;

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

    public char getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(char algorithm) {
        this.algorithm = algorithm;
    }

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
    public void reset(){
        this.timeLeft = this.burstTime;
        this.complete = false;
        this.waitTime = 0;
        this.turnAroundTime = 0;
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
    //Displays the process as a string
    @Override
    public String toString() {
        return String.format("PID:%d\tBurst Time:%d\tArival Time:%d \tWait Time:%d \tTurn Around Time:%d",processID,burstTime,arrivalTime,waitTime,turnAroundTime);
    }


    //used to sort, by using the switch statement, calling sort will sort the process corectly for the given algorithm picked
    @Override
    public int compareTo(Process o) {
        switch (algorithm){
            case 'f': return Integer.compare(this.arrivalTime,o.arrivalTime); // First come first serve
            case 's': return Integer.compare(this.timeLeft,o.timeLeft); // Shortest Remaining job
            case 'p': return Integer.compare(this.priority,o.priority); //Priority
            case 'r': return Integer.compare(this.arrivalTime,o.arrivalTime); //Round Robin Fixed
            case 'j': return Integer.compare(this.burstTime,o.burstTime); //Shortest Job First
            case 'v': return Integer.compare(this.arrivalTime,o.arrivalTime); //Round Robin Variable
        }
        //other statment to satify java, the only options that algorithm can ever be are listed above.
        return Integer.compare(this.arrivalTime,o.arrivalTime);
    }
}
