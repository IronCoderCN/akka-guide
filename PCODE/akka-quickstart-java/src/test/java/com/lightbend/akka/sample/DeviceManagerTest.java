package com.lightbend.akka.sample;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import com.example.iot.DeviceManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

/**
 * @author Charies Gavin
 *         https:github.com/guobinhit
 * @date 2/15/19,10:13 AM
 * @description
 */
public class DeviceManagerTest {
    static ActorSystem system;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create();
    }

    @AfterClass
    public static void teardown() {
        TestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    public void testRegisterDeviceGroupActor() {
        TestKit probe = new TestKit(system);
        ActorRef deviceManagerActor = system.actorOf(DeviceManager.props(), "deviceMangerActor");

        deviceManagerActor.tell(new DeviceManager.RequestTrackDevice("groupTest", "device1"), probe.getRef());
        probe.expectMsgClass(DeviceManager.DeviceRegistered.class);
        ActorRef deviceActor1 = probe.getLastSender();

        deviceManagerActor.tell(new DeviceManager.RequestTrackDevice("groupTest", "device2"), probe.getRef());
        probe.expectMsgClass(DeviceManager.DeviceRegistered.class);
        ActorRef deviceActor2 = probe.getLastSender();
        assertNotEquals(deviceActor1, deviceActor2);
    }
}
