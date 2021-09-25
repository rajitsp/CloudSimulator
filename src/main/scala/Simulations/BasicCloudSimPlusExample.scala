package Simulations

import HelperUtils.{CreateLogger, ObtainConfigReference}
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.cloudbus.cloudsim.cloudlets.CloudletSimple
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.DatacenterSimple
import org.cloudbus.cloudsim.hosts.HostSimple
import org.cloudbus.cloudsim.resources.{Pe, PeSimple}
import org.cloudbus.cloudsim.utilizationmodels.UtilizationModelDynamic
import org.cloudbus.cloudsim.vms.VmSimple
import org.cloudsimplus.builders.tables.CloudletsTableBuilder

import collection.JavaConverters.*

class BasicCloudSimPlusExample

object BasicCloudSimPlusExample:
  val config = ObtainConfigReference("cloudSimulator1") match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
  }
  val logger = CreateLogger(classOf[BasicCloudSimPlusExample])

  def Start() =
    val cloudsim = new CloudSim();
    val broker0 = new DatacenterBrokerSimple(cloudsim);

    val hostPes = List(new PeSimple(config.getLong("cloudSimulator1.host.mipsCapacity")))
    logger.info(s"Created one processing element: $hostPes")

    val hostList = List(new HostSimple(config.getLong("cloudSimulator1.host.RAMInMBs"),
      config.getLong("cloudSimulator1.host.StorageInMBs"),
      config.getLong("cloudSimulator1.host.BandwidthInMBps"),
      hostPes.asJava))

    logger.info(s"Created one host: $hostList")

    val dc0 = new DatacenterSimple(cloudsim, hostList.asJava);

    val vmList = List(
      new VmSimple(config.getLong("cloudSimulator1.vm.mipsCapacity"), hostPes.length)
      .setRam(config.getLong("cloudSimulator1.vm.RAMInMBs"))
      .setBw(config.getLong("cloudSimulator1.vm.BandwidthInMBps"))
      .setSize(config.getLong("cloudSimulator1.vm.StorageInMBs"))
    )
    logger.info(s"Created one virtual machine: $vmList")

    val utilizationModel = new UtilizationModelDynamic(config.getDouble("cloudSimulator1.utilizationRatio"));
    val cloudletList = new CloudletSimple(config.getLong("cloudSimulator1.cloudlet.size"), config.getInt("cloudSimulator1.cloudlet.PEs"), utilizationModel) ::
      new CloudletSimple(config.getLong("cloudSimulator1.cloudlet.size"), config.getInt("cloudSimulator1.cloudlet.PEs"), utilizationModel) :: Nil

    logger.info(s"Created a list of cloudlets: $cloudletList")

    broker0.submitVmList(vmList.asJava);
    broker0.submitCloudletList(cloudletList.asJava);

    logger.info("Starting cloud simulation...")
    cloudsim.start();

    new CloudletsTableBuilder(broker0.getCloudletFinishedList()).build();