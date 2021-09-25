package Simulations
//importing cloudsim libraries
import HelperUtils.{CreateLogger, ObtainConfigReference}
import org.cloudbus.cloudsim.allocationpolicies.{VmAllocationPolicyBestFit, VmAllocationPolicyRoundRobin, VmAllocationPolicySimple}
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.cloudbus.cloudsim.cloudlets.{Cloudlet, CloudletSimple}
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.{Datacenter, DatacenterSimple}
import org.cloudbus.cloudsim.hosts.HostSimple
import org.cloudbus.cloudsim.resources.PeSimple
import org.cloudbus.cloudsim.utilizationmodels.UtilizationModelDynamic
import org.cloudbus.cloudsim.vms.{Vm, VmCost, VmSimple}
import org.cloudsimplus.builders.tables.CloudletsTableBuilder

import collection.JavaConverters.*

class CloudSimBasic

object CloudSimBasic {
  val config = ObtainConfigReference("cloudSimulator1") match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
  }

  val logger = CreateLogger(classOf[CloudSimBasic])

  // val initialization and import from config file
  val hostRam = config.getLong("cloudSimulator1.data_center.host.HOST_RAM");
  val hostStorage = config.getLong("cloudSimulator1.data_center.host.HOST_STORAGE");
  val hostBW = config.getLong("cloudSimulator1.data_center.host.HOST_BW");
  val hostMIPS = config.getLong("cloudSimulator1.data_center.host.HOST_MIPS");
  val hostPes = config.getInt("cloudSimulator1.data_center.host.HOST_PES");
  val vmPes: Long = config.getLong("cloudSimulator1.data_center.vm.VM_PES");
  val vmRam: Long = config.getLong("cloudSimulator1.data_center.vm.VM_RAM");
  val vmBW: Long = config.getLong("cloudSimulator1.data_center.vm.VM_BW");
  val vmSize: Long = config.getLong("cloudSimulator1.data_center.vm.VM_SIZE");
  val vmNum: Int = config.getInt("cloudSimulator1.data_center.vm.VM_No");
  
  val CostperBW = config.getDouble("cloudSimulator1.data_center.CostPer_BW")
  val CostperMEM = config.getDouble("cloudSimulator1.data_center.CostPer_MEM")
  val CostperSEC = config.getDouble("cloudSimulator1.data_center.CostPer_SEC")
  val CostperSTORAGE = config.getDouble("cloudSimulator1.data_center.CostPer_STORAGE")
  
  //main 
  def Start() = {
    val cloudsim = new CloudSim(); //create cloudsim object
    val datacenter0 = createDatacenter(cloudsim);


    val broker0 = new DatacenterBrokerSimple(cloudsim);


    val vmList = createVms();


    val cloudletList = createCloudlets();

    broker0.submitVmList(vmList.asJava);
    broker0.submitCloudletList(cloudletList.asJava);
    logger.info("Starting cloud simulation...")

    cloudsim.start();

    new CloudletsTableBuilder(broker0.getCloudletFinishedList()).build();
    printTotalVmsCost(broker0);
  }

  def createDatacenter(cloudsim: CloudSim): Datacenter = {
    //create data center and import values from application.conf file
    val pesList = (1 to hostPes).map(pl => new PeSimple(hostMIPS)); //multiple PEs craeted

    val hostList = (1 to config.getInt("cloudSimulator1.data_center.host.HOSTS")).map(hl =>
      new HostSimple(hostRam, hostStorage, hostBW, pesList.asJava)).toList

    logger.info(s"Created one processing element: $hostPes") //logging info
    logger.info(s"Created one host: $hostList")
    val datacenter: Datacenter = new DatacenterSimple(cloudsim, hostList.asJava, new VmAllocationPolicySimple())
    println(CostperBW)
    datacenter.getCharacteristics()
      .setCostPerBw(CostperBW)
      .setCostPerMem(CostperMEM)
      .setCostPerSecond(CostperSEC)
      .setCostPerStorage(CostperSTORAGE)
    return datacenter;
  }

  def createVms(): List[Vm] = {
    // function to create Virtaul machines list using values like Bandwidth, RAM, PEs
    val vmList = (1 to vmNum).map(vm => new VmSimple(hostMIPS, vmPes).setSize(vmSize).setBw(vmBW).setRam(vmRam)).toList
    logger.info(s"Created one virtual machine: $vmList")
    return vmList.toList;
  }

  def createCloudlets(): List[Cloudlet] = {
    val utilizationModel = new UtilizationModelDynamic(config.getDouble("cloudSimulator1.UTILIZATIONRATION"));
    // Use the cloudlet
    val cloudlet = (1 to config.getInt("cloudSimulator1.data_center.cloudlet.CLOUDLETS")).map(cl =>
      new CloudletSimple(config.getLong("cloudSimulator1.data_center.cloudlet.CLOUDLET_LENGTH"),
        config.getInt("cloudSimulator1.data_center.cloudlet.CLOUDLET_PES"), utilizationModel)).toList
    logger.info(s"Created a list of cloudlets1: $cloudlet")

    return cloudlet.toList;

  }
// Function to calculate and display cost results
  def printTotalVmsCost(broker0: DatacenterBrokerSimple): Unit = {
    var total_cost: Double = 0.0
    var Total_cost_processing: Double = 0.0
    var Non_Idle_VM: Double = 0.0
    var Memory_Tot_Cost: Double = 0.0
    var Storage_Tot_Cost: Double = 0.0
    var BW_Tot_Cost: Double = 0.0

    val vmList: List[Vm] = broker0.getVmCreatedList().asScala.toList

    vmList.map((vm) => {
      val cost: VmCost = new VmCost(vm);
      Total_cost_processing += cost.getProcessingCost()
      Memory_Tot_Cost += cost.getMemoryCost()
      Storage_Tot_Cost += cost.getStorageCost()
      BW_Tot_Cost += cost.getBwCost()
      total_cost += cost.getTotalCost()
      Non_Idle_VM = Non_Idle_VM + {
        if (vm.getTotalExecutionTime > 0) 1 else 0
      }
      println(cost)
    });

    println(f"Total cost ($$) for ${Non_Idle_VM.asInstanceOf[Int]}%3d created VMs from " +
      f"${broker0.getVmsNumber}%3d in DC   : ${Total_cost_processing}%8.2f$$ " +
      f"${Memory_Tot_Cost}%13.2f$$ ${Storage_Tot_Cost}%17.2f$$ ${BW_Tot_Cost}%12.2f$$ ${total_cost}%15.2f$$")
  }
}



