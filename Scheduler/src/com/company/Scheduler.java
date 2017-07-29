

package com.company;




import java.util.*;

public class Scheduler {

    public static void main(String[] args) {
        //Variable declaration
        ArrayList<Process> processes = null;
        int numProcesses = 0;
        char choice = ' ';
        char again = 'y';
        boolean repeat = true;
        int quantum = 0;
        boolean doOver = true;
        double averageTurnAround = 0;
        double averageWaitTime = 0;
        double bestVal = Double.MAX_VALUE;
        char bestChar = ' ';
        Ghant result;
        Scanner scanner = new Scanner(System.in);

        String processPromt = "**********************************************************\n*Enter the number of processes you would like to Simulate*\n**********************************************************";
        String algorithmPromt = "**********************************************************\n*  Enter the Algorithm that you want to use to schedule  *\n*                (F)irst come First serve                *\n*                  Shortest (J)ob first                  *\n*                       (P)riority                       *\n*                (S)hortest remaining job                *\n*                 Round Robin (V)ariable                 *\n*                   (R)ound Robin Fixed                  *\n**********************************************************";
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
        //used if the user wants to repeat the values for different algorithms
        while(repeat){
        //Get algorithm
        while (doOver) { //displays options until a valid option is picked
            System.out.println(algorithmPromt);
            choice = scanner.next().toLowerCase().charAt(0);
            if ((options.indexOf(choice)) >= 0) {
                doOver = false;
                if (roundRobin.indexOf(choice) >= 0) {
                    System.out.println(quantumPromt);
                    quantum = scanner.nextInt();
                }
            } else {
                System.out.println(errorPromt);
            }

        }
        //for first time through
        if(processes == null) {
            if (choice == 'p') {
                processes = readProcesses(true, numProcesses, choice);
            } else {
                processes = readProcesses(false, numProcesses, choice);
            }
            //For other times through
        }else{
            //if priority, assign priority to processes
            if (choice == 'p'){
                for(Process p : processes){
                    p.setAlgorithm(' ');
                }
                Collections.sort(processes);
                for(Process p : processes){
                    System.out.printf("************Enter the priority of Process %d *************\n",p.getProcessID());
                    p.setPriority(scanner.nextInt());
                    p.setAlgorithm(choice);
                    //before running the algorithm, resets the counters and flags in the processes
                    p.reset();
                }
                //for other processes
            }else{
                for(Process p : processes){
                    p.setAlgorithm(choice);
                    p.reset();
                }
            }

        }
        //Sorts algorithm in the correct order
        //System.out.println(processes);
        Collections.sort(processes);
        //System.out.println(processes);
        if (preemptive.indexOf(choice) >= 0) {
            //for preemptive algorithms (Priority, SRT)
            result = preemptiveSim(processes);
        } else if (nonPreemptive.indexOf(choice) >= 0) {
            //for non-preemptive algorithms (FCFS,SJF)
            result = nonPreemptiveSim(processes);
        } else {
            //for Round Robin algorithms
            if (choice == 'v') {
                result = roundRobinSim(processes, quantum, false);
            } else {
                result = roundRobinSim(processes, quantum, true);
            }
        }
        //calculate average wait and turn around time
        for (Process p : processes) {
            averageTurnAround += p.getTurnAroundTime();
            averageWaitTime += p.getWaitTime();
        }
        averageTurnAround = averageTurnAround / processes.size();
        averageWaitTime = averageWaitTime / processes.size();

        //Sets the best values if the current algorithm runs with a better time than the previous best.
        if(averageTurnAround + averageWaitTime < bestVal){
            bestVal = averageTurnAround + averageWaitTime;
            bestChar = processes.get(0).getAlgorithm();
        }

        //Sorts the Processes by PID before display
        for(Process p : processes){
            p.setAlgorithm(' ');
        }
        Collections.sort(processes);

        //Prints the processes
        System.out.println("\n\n\n\n\n\n**********************************************************");
        System.out.println("                      Processes Entered                   ");
        for (Process p : processes) {
            System.out.println(p);
        }
        System.out.println("**********************************************************");
        //print out Ghant chart from the results
        System.out.println("**********************************************************");
        System.out.println("                        Ghant Chart                       ");
        result.display();
        System.out.println("**********************************************************");

        //Print out Wait time with proper formatting
        System.out.println("**********************************************************");
        nextOut = "*                 Average Wait Time:";
        nextOut = nextOut.concat(Double.toString(averageWaitTime));
        while (nextOut.length() < 57) {
            nextOut = nextOut.concat(" ");
        }
        nextOut = nextOut.concat("*");
        System.out.println(nextOut);
        //Print out Turn Around time with proper formatting
        nextOut = "*             Average Turn Around Time:";
        nextOut = nextOut.concat(Double.toString(averageTurnAround));
        while (nextOut.length() < 57) {
            nextOut = nextOut.concat(" ");
        }
        nextOut = nextOut.concat("*");
        System.out.println(nextOut);
        System.out.println("**********************************************************");
        System.out.println("*    So far the best algorithm is "+bestString(bestChar) + " *");
        System.out.println("*            Would you like to run a different           *\n*            algorithm on these Processes(Y/N)           *");
        System.out.println("**********************************************************");
        again = scanner.next().toLowerCase().charAt(0);
        //if choice = y then true, else false
        repeat = ((again == 'y') ? true : false);
        //resets the global variables
        averageTurnAround = 0;
        averageWaitTime = 0;
        doOver = true;
    }
    goodByeMessege();


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

    public static void goodByeMessege(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("**********************************************************");
        System.out.println("*    Thank you for using the scheduling program that I   *");
        System.out.println("*have created, you can find the project on github at     *");
        System.out.println("*     https://github.com/tenhagena/Process_Scheduler     *");
        System.out.println("*feedback would be appreciated on any existing bugs, or  *");
        System.out.println("*if you think a new feature would be useful. Thank you!  *");
        System.out.println("**********************************************************");
        System.out.println("****************Press the enter key to exit***************");
        System.out.println("**********************************************************");
        scanner.nextLine();
    }

    //returns the full name of an algorithm based on the char passed in
    public static String bestString(char algorithm){
        switch (algorithm) {
            case 'f':
                return "First Come First Serve";
            case 'j':
                return "Shortest Job First    ";
            case 'p':
                return "Priority              ";
            case 'r':
                return "Round Robin Fixed     ";
            case 'v':
                return "Round Robin Variable  ";
            case 's':
                return "Shortest Remaining Job";
        }
        return "ERROR";
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
