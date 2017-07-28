

package com.company;




import java.util.*;

public class Scheduler {

    public static void main(String[] args) {
        //Variable declaration
        ArrayList<Process> processes = new ArrayList<Process>();
        int numProcesses = 0;
        char choice = ' ';
        int quantum = 0;
        boolean doOver = true;
        double averageTurnAround = 0;
        double averageWaitTime = 0;
        Ghant result;
        Scanner scanner = new Scanner(System.in);

        String processPromt = "**********************************************************\n*Enter the number of processes you would like to Simulate*\n**********************************************************";
        String algorithmPromt = "**********************************************************\n***Enter the Algorithm that you want to use to schedule***\n*                (F)irst come First serve                *\n*                  Shortest (J)ob first                  *\n*                       (P)riority                       *\n*                (S)hortest remaining job                *\n*                 Round Robin (V)ariable                 *\n*                   (R)ound Robin Fixed                  *\n**********************************************************";
        String quantumPromt = "**********************************************************\n*              Please enter a Quantum Value              *\n**********************************************************";
        String errorPromt = "**********************************************************\n*               Please enter a valid option              *\n**********************************************************";

        String options = "frvpsj";
        String preemptive = "ps";
        String nonPreemptive = "fj";
        String roundRobin = "rv";
        String nextOut = "";


        //Begin user input
        welcomeMessege();
        //get number of processes
        System.out.println(processPromt);
        numProcesses = scanner.nextInt();
        //Get algorithm
        while(doOver) { //displays options until a valid option is picked
            System.out.println(algorithmPromt);
            choice = scanner.next().toLowerCase().charAt(0);
            if((options.indexOf(choice)) >= 0) {
                doOver = false;
                if (roundRobin.indexOf(choice) >= 0) {
                    System.out.println(quantumPromt);
                    quantum = scanner.nextInt();
                }
            }else{
                System.out.println(errorPromt);
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
        if(preemptive.indexOf(choice) >= 0){
            //for preemptive algorithms (Priority, SRT)
            result = preemptiveSim(processes);
        }else if(nonPreemptive.indexOf(choice) >= 0){
            //for non-preemptive algorithms (FCFS,SJF)
            result = nonPreemptiveSim(processes);
        }else{
            //for Round Robin algorithms
            if(choice == 'v') {
                result = roundRobinSim(processes, quantum,false );
            }else{
                result = roundRobinSim(processes, quantum, true);
            }
        }
        //calculate average wait and turn around time
        for(Process p : processes){
            averageTurnAround += p.getTurnAroundTime();
            averageWaitTime += p.getWaitTime();
        }
        averageTurnAround = averageTurnAround/processes.size();
        averageWaitTime = averageWaitTime/processes.size();

        //print out Ghant chart from the results
        System.out.println("\n\n\n\n\n\n**********************************************************");
        System.out.println("                        Ghant Chart                       ");
        System.out.println(result.toString());
        System.out.println("**********************************************************");

        //Print out Wait time with proper formatting
        System.out.println("**********************************************************");
        nextOut = "*                    WT:";
        nextOut = nextOut.concat(Double.toString(averageWaitTime));
        while(nextOut.length() < 57){
            nextOut = nextOut.concat(" ");
        }
        nextOut = nextOut.concat("*");
        System.out.println(nextOut);
        //Print out Turn Around time with proper formatting
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

    //Gets the array of processes that will be used in the Algorithms.
    public static ArrayList<Process> readProcesses(boolean priorityNeeded,int numProcesses, char algorithm){
        //variable declaration
        ArrayList<Process> processes = new ArrayList<Process>();
        int currentID = 1;
        Scanner scanner = new Scanner(System.in);
        int priority = 0;
        int burstTime;
        int arrivalTime;

        //loop for the number of processes and prompt for their values
        for(int i = 0; i < numProcesses; i++){
            System.out.printf("***********************Process #%d ************************\n",currentID);
            System.out.println("**********Enter the arrival time of the process***********");

            arrivalTime = scanner.nextInt();
            System.out.println("***********Enter the burst time of the process************");
            burstTime = scanner.nextInt();
            //if priority is needed, get the value of its priority
            if(priorityNeeded){
                System.out.println("************Enter the priority of the process*************");
                priority = scanner.nextInt();
            }
            System.out.println("**********************************************************");
            //Add process to the process pool
            processes.add(new Process(arrivalTime,burstTime,priority,currentID,algorithm));
            currentID++;
        }


        return processes;
    }

    //runs a simulation for the preemptive algorithms (Priority, SRJ)
    public static Ghant preemptiveSim(ArrayList<Process> processes){
        //variable declaration
        int time = 0;
        boolean getNext = true;
        Process current = null;
        int numToComplete = processes.size();
        Ghant result = new Ghant();

        //while there are still processes to run
        while(numToComplete > 0) {
            //gets the first available process that is not complete
            for (Process p : processes) {
                if (time >= p.getArrivalTime() && !p.isComplete()) {
                    current = p;
                    break;
                }
            }
            //if there is idle, marks idle
            if (current == null) {
                result.addToChart(0);
            } else {
                //adds its value to the ghant chart, decreases the time that is left to run
                result.addToChart(current.getProcessID());
                current.tick();
                //if the process is complete, puts the completed time in the process and and sets the current process to null
                if (current.getTimeLeft() == 0) {
                    current.completeProcess(time +1);
                    numToComplete--;
                    current = null;
                }
            }
            //increments time and sorts the jobs, in the case of SRJ resorting is needed
            time++;
            Collections.sort(processes);
        }

        return result;
    }

    //runs a simulation for the non-preemptive algorithms (FCFS, SJF)
    public static Ghant nonPreemptiveSim(ArrayList<Process> processes){
        //variable declaration
        int time = 0;
        boolean getNext = true;
        Process current = null;
        Ghant result = new Ghant();
        int numToComplete = processes.size();

        //while there are still processes to run
        while(numToComplete > 0){
            //if there is no current process, gets the first available process that is not complete
            if(getNext){
                for(Process p : processes){
                    if(time >= p.getArrivalTime() && !p.isComplete()){
                        current = p;
                        getNext = false;
                        break;
                    }
                }
            }
            //if there is idle, marks idle
            if(current == null){
                result.addToChart(0);
            }else{
                //adds its value to the ghant chart, decreases the time that is left to run
                result.addToChart(current.getProcessID());
                current.tick();
                //if the process is complete, puts the completed time in the process and and sets the current process to null. then sets the flag to get next process
                if(current.getTimeLeft() == 0){
                    current.completeProcess(time + 1);
                    current = null;
                    numToComplete--;
                    getNext = true;
                }
            }
            //increments time
            time++;
        }

        return result;
    }

    //runs a simulation for the round robin algorithms (Variable, Fixed)
    public static Ghant roundRobinSim(ArrayList<Process> processes, int quantum,boolean fixed){
        //variable declaration
        int time = 0;
        int currentQuantum =1;
        int numToComplete = processes.size();
        boolean getNext = true;
        Process current = null;
        Ghant result = new Ghant();
        Process temp = null;
        //while there are still processes to complete
        while(numToComplete > 0){
            currentQuantum++;
            if(getNext){
                for(Process p : processes){
                    //if there is no current process, gets the first available process that is not complete, resets quantum
                    if(time >= p.getArrivalTime() && !p.isComplete()){
                        current = p;
                        getNext = false;
                        currentQuantum = 1;
                        break;
                    }
                }
            }
            //if end of quantum
            if(currentQuantum == quantum){
                getNext = true;
            }
            //if there is idle, marks idle
            if(current == null){
                result.addToChart(0);
            }else{
                //ages the process and adds it to the chart.
                result.addToChart(current.getProcessID());
                current.tick();

                //if the process is complete, puts the completed time in the process and and sets the current process to null, if not fixed, sets flag to get next process
                if(current.getTimeLeft() == 0){
                    current.completeProcess(time + 1);
                    current = null;
                    numToComplete--;
                    if(!fixed) {
                        getNext = true;
                    }
                }
                //if the process still has time left and reaches end of quantum, moves the process to the end of the queue
                if(currentQuantum == quantum && current!= null){
                    temp = current;
                    processes.remove(current);
                    processes.add(temp);
                }
            }
            //increment time
            time++;


        }






        return result;
    }
}
