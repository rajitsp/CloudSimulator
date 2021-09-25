package Simulations

import HelperUtils.ObtainConfigReference
import Simulations.BasicCloudSimPlusExample.config
import org.cloudbus.cloudsim.cloudlets.{Cloudlet, CloudletSimple}
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.Datacenter
import org.cloudbus.cloudsim.vms.Vm
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SimTest extends AnyFlatSpec with Matchers {

  val config = ObtainConfigReference("cloudSimulator1") match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
  }

  it should "Check number of cloudlets" in {
    val cloudletno = config.getInt("cloudSimulator1.data_center.cloudlet.CLOUDLETS")
    val CloudList = CloudSimBasic.createCloudlets()
    assert(cloudletno == CloudList.length)

  }
  it should "Check number of VMs created" in {
    val Vms = config.getInt("cloudSimulator1.data_center.vm.VM_No")
    val VM_List = CloudSimBasic.createVms()
    assert(VM_List.length == Vms)
  }
  val cloudsim = new CloudSim();
  it should "Check returned data type of Data Center" in {
    val datacentre_type= CloudSimBasic.createDatacenter(cloudsim)
    assert(datacentre_type.isInstanceOf[Datacenter])
  }


  it should "Check returned data type of VM" in {
    val VM_type= CloudSimBasic.createVms()
    assert(VM_type.isInstanceOf[List[Vm]])
  }

  it should "Check returned data type of Cloudlet" in {
    val VM_type= CloudSimBasic.createVms()
    assert(VM_type.isInstanceOf[List[Cloudlet]])
  }
}
