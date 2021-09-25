# Basic Cloud Simulator - Cloud Sim Plus 
The application creates simulations of running jobs on a cloud computing datacenter using the Cloud Sim Plus framework. Cloud Sim Plus is a software library that simulates cloud environments and assists with the operation of various cloud models. This framework includes classes and interfaces for implementing and simulating hosts, virtual machines, and cloudlets in a datacenter.

The application is built almost purely on Scala, which is a functional programming language designed to be concise as compared to Java

### Instructions:

1) This project requires SBT(Simple Bulid Toolkit) to build the program.
2) Clone the repository in your local machine.
3) Adjust the values in the src/main/resources/application.conf file.
4) Build the project and run the Simulation.scala file.

### Important Files:

CloudSimBasic.scala:

Located in- src/main/scala/Simulations/CloudSimBasic.scala
This is the main file that initializes the immutable variables, creates the data center and displays the simulation results.

Simulation.scala:

Located in- src/main/scala/Simulation.scala
Run this file to observe the results of the simulation

application.conf:
As mentioned above, edit this file to with your values to observe the simulation results.

### Simulation:

```

                                         SIMULATION RESULTS

Cloudlet|Status |DC|Host|Host PEs |VM|VM PEs   |CloudletLen|CloudletPEs|StartTime|FinishTime|ExecTime
ID|       |ID|  ID|CPU cores|ID|CPU cores|         MI|  CPU cores|  Seconds|   Seconds| Seconds
-----------------------------------------------------------------------------------------------------
       0|SUCCESS| 1|   0|        8| 0|        2|      10000|          2|        0|        20|      20
       1|SUCCESS| 1|   0|        8| 1|        2|      10000|          2|        0|        20|      20
       2|SUCCESS| 1|   0|        8| 2|        2|      10000|          2|        0|        20|      20
       3|SUCCESS| 1|   0|        8| 3|        2|      10000|          2|        0|        20|      20
-----------------------------------------------------------------------------------------------------
Vm 0 costs ($) for    20.22 execution seconds - CPU:     0.20$ RAM:     1.54$ Storage:  1000.00$ BW:     2.00$ Total:  1003.74$
Vm 1 costs ($) for    20.22 execution seconds - CPU:     0.20$ RAM:     1.54$ Storage:  1000.00$ BW:     2.00$ Total:  1003.74$
Vm 2 costs ($) for    20.22 execution seconds - CPU:     0.20$ RAM:     1.54$ Storage:  1000.00$ BW:     2.00$ Total:  1003.74$
Vm 3 costs ($) for    20.22 execution seconds - CPU:     0.20$ RAM:     1.54$ Storage:  1000.00$ BW:     2.00$ Total:  1003.74$
Total cost ($) for   4 created VMs from   4 in DC   :     0.81$          6.14$           4000.00$         8.00$         4014.95$
19:34:51.373 [main] INFO  java.lang.Class - Finished cloud simulation...

Process finished with exit code 0
```
### Unit Tests:

```
[info] SimTest:
[info] - should Check number of cloudlets
[info] - should Check number of VMs created
[info] - should Check returned data type of Data Center
[info] - should Check returned data type of VM
[info] - should Check returned data type of Cloudlet
[info] Run completed in 481 milliseconds.
[info] Total number of tests run: 5
[info] Suites: completed 2, aborted 0
[info] Tests: succeeded 5, failed 0, canceled 0, ignored 0, pending 0
[info] All tests passed.
```

### Conclusion:
- VmAllocationPolicyBestFit

```text
21:09:46.084 [main] INFO  VmAllocationPolicy - 0.00: VmAllocationPolicyBestFit: Vm 0 has been allocated to Host 0/DC 1
21:09:46.086 [main] INFO  VmAllocationPolicy - 0.00: VmAllocationPolicyBestFit: Vm 1 has been allocated to Host 0/DC 1
21:09:46.088 [main] INFO  VmAllocationPolicy - 0.00: VmAllocationPolicyBestFit: Vm 2 has been allocated to Host 0/DC 1
21:09:46.089 [main] INFO  VmAllocationPolicy - 0.00: VmAllocationPolicyBestFit: Vm 3 has been allocated to Host 0/DC 1
```

- VmAllocationPolicyRoundRobin
```text
21:13:18.008 [main] INFO  VmAllocationPolicy - 0.00: VmAllocationPolicyRoundRobin: Vm 0 has been allocated to Host 0/DC 1
21:13:18.010 [main] INFO  VmAllocationPolicy - 0.00: VmAllocationPolicyRoundRobin: Vm 1 has been allocated to Host 1/DC 1
21:13:18.011 [main] INFO  VmAllocationPolicy - 0.00: VmAllocationPolicyRoundRobin: Vm 2 has been allocated to Host 2/DC 1
21:13:18.011 [main] INFO  VmAllocationPolicy - 0.00: VmAllocationPolicyRoundRobin: Vm 3 has been allocated to Host 3/DC 1
```

- VmAllocationPolicySimple
```text
21:14:50.348 [main] INFO  VmAllocationPolicy - 0.00: VmAllocationPolicySimple: Vm 0 has been allocated to Host 0/DC 1
21:14:50.350 [main] INFO  VmAllocationPolicy - 0.00: VmAllocationPolicySimple: Vm 1 has been allocated to Host 1/DC 1
21:14:50.351 [main] INFO  VmAllocationPolicy - 0.00: VmAllocationPolicySimple: Vm 2 has been allocated to Host 2/DC 1
21:14:50.353 [main] INFO  VmAllocationPolicy - 0.00: VmAllocationPolicySimple: Vm 3 has been allocated to Host 3/DC 1
```
