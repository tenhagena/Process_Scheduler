

package com.company;




import java.util.*;

public class Main {

    public static void main(String[] args) {
        //Variable declaration
        ArrayList<Process> processes = new ArrayList<Process>();
        int numProcesses = 0;
        char choice = ' ';
        Scanner scanner = new Scanner(System.in);
        String options = "frvpsj";
        String premtive = "ps";
        String nonPremptive = "fj";
        String roundRobin = "rv";
        String nextOut = "";
        int quantum = 0;
        boolean doOver = true;
        double averageTurnAround = 0;
        double averageWaitTime = 0;
        Ghant result;

        //Begin user input
        welcomeMessege();
        //get number of processes
        System.out.println("**********************************************************");
        System.out.println("*Enter the number of processes you would like to Simulate*");
        System.out.println("**********************************************************");
        numProcesses = scanner.nextInt();
        //Get algorithm
        while(doOver) {
            System.out.println("**********************************************************");
            System.out.println("***Enter the Algorithm that you want to use to schedule***");
            System.out.println("*                (F)irst come First serve                *");
            System.out.println("*                   (R)ound Robin Fixed                  *");
            System.out.println("*                 Round Robin (V)ariable                 *");
            System.out.println("*                       (P)riority                       *");
            System.out.println("*                (S)hortest remaining job                *");
            System.out.println("*                  Shortest (J)ob first                  *");
            System.out.println("**********************************************************");
            choice = scanner.next().toLowerCase().charAt(0);
            if((options.indexOf(choice)) >= 0) {
                doOver = false;
                if (roundRobin.indexOf(choice) >= 0) {
                    System.out.println("**********************************************************");
                    System.out.println("*              Please enter a Quantum Value              *");
                    System.out.println("**********************************************************");
                    quantum = scanner.nextInt();
                }
            }else{
                    System.out.println("**********************************************************");
                    System.out.println("*               Please enter a valid option              *");
                    System.out.println("**********************************************************");
                }

        }
        if(choice == 'p'){
           processes =  readProcesses(true,numProcesses,choice);
        }else {
           processes =  readProcesses(false, numProcesses,choice);
        }
        //Sorts algorithm in the correct order
        //System.out.println(processes);
        Collections.sort(processes);
        //System.out.println(processes);
        if(premtive.indexOf(choice) >= 0){
            result = premtiveSim(processes);
        }else if(nonPremptive.indexOf(choice) >= 0){
            result = nonPremptiveSim(processes);
        }else{
            if(choice == 'v') {
                result = roundRobinSim(processes, quantum,false );
            }else{
                result = roundRobinSim(processes, quantum, true);
            }
        }

        System.out.println("\n\n\n\n\n\n**********************************************************");
        System.out.println("                        Ghant Chart                       ");

        System.out.println(result.toString());

        System.out.println("**********************************************************");

        for(Process p : processes){
            averageTurnAround += p.getTurnAroundTime();
            averageWaitTime += p.getWaitTime();
        }
        averageTurnAround = averageTurnAround/processes.size();
        averageWaitTime = averageWaitTime/processes.size();


        System.out.println("**********************************************************");
        nextOut = "*                    WT:";
        nextOut = nextOut.concat(Double.toString(averageWaitTime));
        while(nextOut.length() < 57){
            nextOut = nextOut.concat(" ");
        }
        nextOut = nextOut.concat("*");
        System.out.println(nextOut);
        nextOut = "*                   TAT:";
        nextOut = nextOut.concat(Double.toString(averageTurnAround));
        while(nextOut.length() < 57){
            nextOut = nextOut.concat(" ");
        }
        nextOut = nextOut.concat("*");
        System.out.println(nextOut);
        System.out.println("**********************************************************");

    }

    public static void welcomeMessege(){
        Scanner scanner = new Scanner(System.in);
        System.out.flush();
        System.out.println("**********************************************************");
        System.out.println("*    Hello and welcome to the all in one process         *");
        System.out.println("*simulator created for the summer 2017 Operating         *");
        System.out.println("*systems class taught by the wonderful and newly         *");
        System.out.println("*AMERICAN Durga Suresh Meynon. In this program you       *");
        System.out.println("*can see what its like to be the scheduler of process    *");
        System.out.println("*in a system.                                            *");
        System.out.println("**********************************************************");
        System.out.println("**************Press the enter key to continue*************");
        System.out.println("**********************************************************");
        scanner.nextLine();
        System.out.flush();


    }
    public static ArrayList<Process> readProcesses(boolean priorityNeeded,int numProcesses, char algorithm){
        //variable declaration
        ArrayList<Process> processes = new ArrayList<Process>();
        int currentID = 1;
        Scanner scanner = new Scanner(System.in);
        int priority = 0;
        int burstTime;
        int arrivalTime;


        for(int i = 0; i < numProcesses; i++){
            System.out.printf("***********************Process #%d ************************\n",currentID);
            System.out.println("**********Enter the arrival time of the process***********");

            arrivalTime = scanner.nextInt();
            System.out.println("***********Enter the burst time of the process************");
            burstTime = scanner.nextInt();
            if(priorityNeeded){
                System.out.println("************Enter the priority of the process*************");
                priority = scanner.nextInt();
            }
            System.out.println("**********************************************************");

            processes.add(new Process(arrivalTime,burstTime,priority,currentID,algorithm));
            currentID++;
        }


        return processes;
    }
    public static Ghant premtiveSim(ArrayList<Process> processes){
        int time = 0;
        boolean getNext = true;
        Process current = null;
        int numToComplete = processes.size();
        Ghant result = new Ghant();
        while(numToComplete > 0) {
            for (Process p : processes) {
                if (time >= p.getArrivalTime() && !p.isComplete()) {
                    current = p;
                    break;
                }
            }
            if (current == null) {
                result.addToChart(0);
            } else {
                result.addToChart(current.getProcessID());
                current.tick();
                if (current.getTimeLeft() == 0) {
                    current.completeProcess(time +1);
                    numToComplete--;
                    current = null;
                }
            }
            time++;
            Collections.sort(processes);
        }

        return result;
    }
    public static Ghant nonPremptiveSim(ArrayList<Process> processes){
        int time = 0;
        boolean getNext = true;
        Process current = null;
        Ghant result = new Ghant();
        int numToComplete = processes.size();
        while(numToComplete > 0){
            if(getNext){
                for(Process p : processes){
                    if(time >= p.getArrivalTime() && !p.isComplete()){
                        current = p;
                        getNext = false;
                        break;
                    }
                }
            }
            if(current == null){
                result.addToChart(0);
            }else{
                result.addToChart(current.getProcessID());
                current.tick();
                if(current.getTimeLeft() == 0){
                    current.completeProcess(time + 1);
                    current = null;
                    numToComplete--;
                    getNext = true;
                }
            }
            time++;
        }

        return result;
    }
    public static Ghant roundRobinSim(ArrayList<Process> processes, int quantum,boolean fixed){
        int time = 0;
        int currentQuantum =1;
        int numToComplete = processes.size();
        boolean getNext = true;
        Process current = null;
        Ghant result = new Ghant();

        while(numToComplete > 0){
            currentQuantum++;
            if(getNext){
                for(Process p : processes){
                    if(time >= p.getArrivalTime() && !p.isComplete()){
                        current = p;
                        getNext = false;
                        currentQuantum = 1;
                        break;
                    }
                }
            }
            if(currentQuantum == quantum){
                getNext = true;
            }
            if(current == null){
                result.addToChart(0);
            }else{
                result.addToChart(current.getProcessID());
                current.tick();

                if(current.getTimeLeft() == 0){
                    current.completeProcess(time + 1);
                    current = null;
                    numToComplete--;
                    if(!fixed) {
                        getNext = true;
                    }
                }
            }
            time++;


        }






        return result;
    }
}
