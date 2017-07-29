# Process Scheduler
by: Andrew Tenhagen

This project was created for the Summer 2017 Operating Systems course (comp 3475-01). It uses basic java with no gui implimentation to schedule processes. The system runs in the command line, so it can be run on any system with a JRE. The algorithms that work are:

  - First Come First Serve
  - Shortest Job First
  - Shortest Remaining Job
  - Priority
  - Round Robin Fixed Quantum
  - Round Robin Variable Quantum

# Features!
At the end of the processing, the full ghant chart is output to the console as well as the average wait and turn around times. If there is any idle time while processing, the CPU usage percentage will also show under the ghant chart. 
Added delay to the output of the ghant chart
Added rerun option
## Running
After cloning the repository 
```sh
$ cd /Directory/of/repository/src/com/company/
$ javac Scheduler.java Ghant.java Process.java
$ java Scheduler
```


