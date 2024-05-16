import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class BladeBalancerTest {

    @Test
    public void balance() {
        Disbalance dsb = new Disbalance(0.0, 0);
        ArrayList<Blade> bladeList = new ArrayList<>();

        Blade blade1 = new Blade(1, "blade1", 5, false);
        Blade blade2 = new Blade(2, "blade2", 9, false);
        Blade blade3 = new Blade(3, "blade3", 5, false);
        Blade blade4 = new Blade(4, "blade4", 9, false);
        Blade blade5 = new Blade(5, "blade5", 5, false);
        Blade blade6 = new Blade(6, "blade6", 5, false);
        Blade blade7 = new Blade(7, "blade7", 5, false);
        Blade blade8 = new Blade(8, "blade8", 8, false);
        Blade blade9 = new Blade(9, "blade9", 3, false);
        Blade blade10 = new Blade(10, "blade10", 8, false);
        Blade blade11 = new Blade(11, "blade10", 5, false);
        Blade blade12 = new Blade(12, "blade10", 5, false);

        bladeList.add(blade1);
        bladeList.add(blade2);
        bladeList.add(blade3);
        bladeList.add(blade4);
        bladeList.add(blade5);
        bladeList.add(blade6);
        bladeList.add(blade7);
        bladeList.add(blade8);
        bladeList.add(blade9);
        bladeList.add(blade10);
        bladeList.add(blade11);
        bladeList.add(blade12);

        Disk disk = new Disk(dsb, bladeList);

        double requiredMinDsb = 1.0;
        Disk actualDisk = BladeBalancer.balance(disk, requiredMinDsb);
        Double actualDsbValue  = BladeBalancer.calculateDisbalance(actualDisk).getValue();
        Assert.assertTrue(actualDsbValue < requiredMinDsb);
        Assert.assertEquals(
                actualDisk.getBladeList().stream()
                        .map(Blade::getName)
                        .collect(Collectors.toSet()),
                bladeList.stream()
                        .map(Blade::getName)
                        .collect(Collectors.toSet()));
    }

    @Test
    public void captureBladesAroundTheHeavyPlace() {
        Disbalance dsb = new Disbalance(0.0, 0);
        ArrayList<Blade> bladeList = new ArrayList<>();
        Blade blade1 = new Blade(1, "blade1", 5, false);
        Blade blade2 = new Blade(2, "blade2", 9, false);
        Blade blade3 = new Blade(3, "blade3", 5, false);
        Blade blade4 = new Blade(4, "blade4", 9, false);
        Blade blade5 = new Blade(5, "blade5", 5, false);
        Blade blade6 = new Blade(6, "blade6", 5, false);
        Blade blade7 = new Blade(7, "blade7", 5, false);
        Blade blade8 = new Blade(8, "blade8", 8, false);
        Blade blade9 = new Blade(9, "blade9", 3, false);
        Blade blade10 = new Blade(10, "blade10", 8, false);
        Blade blade11 = new Blade(11, "blade10", 5, false);
        Blade blade12 = new Blade(12, "blade10", 5, false);

        bladeList.add(blade1);
        bladeList.add(blade2);
        bladeList.add(blade3);
        bladeList.add(blade4);
        bladeList.add(blade5);
        bladeList.add(blade6);
        bladeList.add(blade7);
        bladeList.add(blade8);
        bladeList.add(blade9);
        bladeList.add(blade10);
        bladeList.add(blade11);
        bladeList.add(blade12);

        Disk disk = new Disk(dsb, bladeList);

        PriorityQueue<Blade> actualQueue = BladeBalancer.captureBladesAroundTheHeavyPlace(disk, 3, 1);

        Assert.assertEquals(blade2.getStaticMoment(), Objects.requireNonNull(actualQueue.poll()).getStaticMoment());
        Assert.assertEquals(blade4.getStaticMoment(), Objects.requireNonNull(actualQueue.poll()).getStaticMoment());
        Assert.assertEquals(blade3.getStaticMoment(), Objects.requireNonNull(actualQueue.poll()).getStaticMoment());

    }

    @Test
    public void captureBladesAroundTheLightPlace() {
        Disbalance dsb = new Disbalance(0.0, 0);
        ArrayList<Blade> bladeList = new ArrayList<>();
        Blade blade1 = new Blade(1, "blade1", 5, false);
        Blade blade2 = new Blade(2, "blade2", 9, false);
        Blade blade3 = new Blade(3, "blade3", 5, false);
        Blade blade4 = new Blade(4, "blade4", 9, false);
        Blade blade5 = new Blade(5, "blade5", 5, false);
        Blade blade6 = new Blade(6, "blade6", 5, false);
        Blade blade7 = new Blade(7, "blade7", 5, false);
        Blade blade8 = new Blade(8, "blade8", 8, false);
        Blade blade9 = new Blade(9, "blade9", 3, false);
        Blade blade10 = new Blade(10, "blade10", 8, false);
        Blade blade11 = new Blade(11, "blade10", 5, false);
        Blade blade12 = new Blade(12, "blade10", 5, false);

        bladeList.add(blade1);
        bladeList.add(blade2);
        bladeList.add(blade3);
        bladeList.add(blade4);
        bladeList.add(blade5);
        bladeList.add(blade6);
        bladeList.add(blade7);
        bladeList.add(blade8);
        bladeList.add(blade9);
        bladeList.add(blade10);
        bladeList.add(blade11);
        bladeList.add(blade12);

        Disk disk = new Disk(dsb, bladeList);

        PriorityQueue<Blade> actualQueue = BladeBalancer.captureBladesAroundTheLightPlace(disk, 9, 1);

        Assert.assertEquals(blade9.getStaticMoment(), Objects.requireNonNull(actualQueue.poll()).getStaticMoment());
        Assert.assertEquals(blade8.getStaticMoment(), Objects.requireNonNull(actualQueue.poll()).getStaticMoment());
        Assert.assertEquals(blade10.getStaticMoment(), Objects.requireNonNull(actualQueue.poll()).getStaticMoment());
    }

    @Test
    public void findRightNeighbor() {
        Disbalance dsb = new Disbalance(0.0, 0);
        ArrayList<Blade> bladeList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            bladeList.add(new Blade(i + 1, "blade" + (i + 1), 1001 + i, false));
        }
        Disk disk = new Disk(dsb, bladeList);

        int slot = 5;
        int expectedNeighbor = 6;

        int actualNeighbor = BladeBalancer.findRightNeighbor(disk, slot);

        Assert.assertEquals(expectedNeighbor, actualNeighbor);
    }

    @Test
    public void findRightNeighborBoarderCase() {
        Disbalance dsb = new Disbalance(0.0, 0);
        ArrayList<Blade> bladeList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            bladeList.add(new Blade(i + 1, "blade" + (i + 1), 1001 + i, false));
        }
        Disk disk = new Disk(dsb, bladeList);

        int slot = 10;
        int expectedNeighbor = 1;

        int actualNeighbor = BladeBalancer.findRightNeighbor(disk, slot);

        Assert.assertEquals(expectedNeighbor, actualNeighbor);
    }


    @Test
    public void findLeftNeighbor() {
        Disbalance dsb = new Disbalance(0.0, 0);
        ArrayList<Blade> bladeList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            bladeList.add(new Blade(i + 1, "blade" + (i + 1), 1001 + i, false));
        }
        Disk disk = new Disk(dsb, bladeList);

        int slot = 5;
        int expectedNeighbor = 4;

        int actualNeighbor = BladeBalancer.findLeftNeighbor(disk, slot);

        Assert.assertEquals(expectedNeighbor, actualNeighbor);
    }

    @Test
    public void findLeftNeighborBoarderCase(){
        Disbalance dsb = new Disbalance(0.0, 0);
        ArrayList<Blade> bladeList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            bladeList.add(new Blade(i + 1, "blade" + (i + 1), 1001 + i, false));
        }
        Disk disk = new Disk(dsb, bladeList);

        int slot = 1;
        int expectedNeighbor = 10;

        int actualNeighbor = BladeBalancer.findLeftNeighbor(disk, slot);

        Assert.assertEquals(expectedNeighbor, actualNeighbor);
    }

    @Test
    public void arrangeNotFixedBladesAllUnfixed() {
        Disbalance dsb = new Disbalance(0.0, 0);

        Blade blade1 = new Blade(1, "blade1", 1001, false);
        Blade blade2 = new Blade(2, "blade2", 1002, false);
        Blade blade3 = new Blade(3, "blade3", 1003, false);
        Blade blade4 = new Blade(4, "blade4", 1004, false);
        Blade blade5 = new Blade(5, "blade5", 1005, false);
        Blade blade6 = new Blade(6, "blade6", 1006, false);
        Blade blade7 = new Blade(7, "blade7", 1007, false);
        Blade blade8 = new Blade(8, "blade8", 1008, false);
        Blade blade9 = new Blade(9, "blade9", 1009, false);
        Blade blade10 = new Blade(10, "blade10", 1010, false);

        ArrayList<Blade> bladeList = new ArrayList<>();
        bladeList.add(blade1);
        bladeList.add(blade2);
        bladeList.add(blade3);
        bladeList.add(blade4);
        bladeList.add(blade5);
        bladeList.add(blade6);
        bladeList.add(blade7);
        bladeList.add(blade8);
        bladeList.add(blade9);
        bladeList.add(blade10);
        Disk disk = new Disk(dsb, bladeList);

        Disk actual = BladeBalancer.arrangeNotFixedBlades(disk);

        ArrayList<Blade> expectedBladeList = new ArrayList<>();
        expectedBladeList.add(blade10);
        expectedBladeList.add(blade8);
        expectedBladeList.add(blade6);
        expectedBladeList.add(blade4);
        expectedBladeList.add(blade2);
        expectedBladeList.add(blade9);
        expectedBladeList.add(blade7);
        expectedBladeList.add(blade5);
        expectedBladeList.add(blade3);
        expectedBladeList.add(blade1);

        Disk expected = new Disk(dsb, expectedBladeList);

        for (int i = 0; i < bladeList.size(); i++) {
            Assert.assertEquals(expected.getBladeList().get(i).getStaticMoment(), actual.getBladeList().get(i).getStaticMoment());
            Integer exp = i + 1;
            Assert.assertEquals(exp, actual.getBladeList().get(i).getSlot());
        }
    }

    @Test
    public void arrangeNotFixedBladesHalfFixed() {
        Disbalance dsb = new Disbalance(0.0, 0);

        Blade blade1 = new Blade(1, "blade1", 1001, true);
        Blade blade2 = new Blade(2, "blade2", 1002, true);
        Blade blade3 = new Blade(3, "blade3", 1003, true);
        Blade blade4 = new Blade(4, "blade4", 1004, true);
        Blade blade5 = new Blade(5, "blade5", 1005, true);
        Blade blade6 = new Blade(6, "blade6", 1006, false);
        Blade blade7 = new Blade(7, "blade7", 1007, false);
        Blade blade8 = new Blade(8, "blade8", 1008, false);
        Blade blade9 = new Blade(9, "blade9", 1009, false);
        Blade blade10 = new Blade(10, "blade10", 1010, false);

        ArrayList<Blade> bladeList = new ArrayList<>();
        bladeList.add(blade1);
        bladeList.add(blade2);
        bladeList.add(blade3);
        bladeList.add(blade4);
        bladeList.add(blade5);
        bladeList.add(blade6);
        bladeList.add(blade7);
        bladeList.add(blade8);
        bladeList.add(blade9);
        bladeList.add(blade10);
        Disk disk = new Disk(dsb, bladeList);

        Disk actual = BladeBalancer.arrangeNotFixedBlades(disk);

        ArrayList<Blade> expectedBladeList = new ArrayList<>();
        expectedBladeList.add(blade1);
        expectedBladeList.add(blade2);
        expectedBladeList.add(blade3);
        expectedBladeList.add(blade4);
        expectedBladeList.add(blade5);
        expectedBladeList.add(blade10);
        expectedBladeList.add(blade8);
        expectedBladeList.add(blade6);
        expectedBladeList.add(blade9);
        expectedBladeList.add(blade7);

        Disk expected = new Disk(dsb, expectedBladeList);

        for (int i = 0; i < bladeList.size(); i++) {
            Assert.assertEquals(expected.getBladeList().get(i).getStaticMoment(), actual.getBladeList().get(i).getStaticMoment());
            Integer exp = i + 1;
            Assert.assertEquals(exp, actual.getBladeList().get(i).getSlot());
        }
    }

    @Test
    public void findOppositeSlotSimpleCase() {
        ArrayList<Blade> bladeList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            bladeList.add(new Blade(i + 1, "blade" + (i + 1), 1000, false));
        }
        Disbalance dsb = new Disbalance(0.0, 4);
        Disk disk = new Disk(dsb, bladeList);

        int slot = 1;
        int expected = 6;

        int actual = BladeBalancer.findOppositeSlot(disk, slot);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findOppositeSlotRealDisk() {
        ArrayList<Blade> bladeList = new ArrayList<>();
        for (int i = 0; i < 73; i++) {
            bladeList.add(new Blade(i + 1, "blade" + (i + 1), 1000, false));
        }
        Disbalance dsb = new Disbalance(0.0, 4);
        Disk disk = new Disk(dsb, bladeList);

        int slot = 7;
        int expected = 44;

        int actual = BladeBalancer.findOppositeSlot(disk, slot);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void calculateAngle() {
        double[] expected = {0.0, 7.0, 30.0, 89.0, 90.0, 91.0, 107.0, 180.0, 181.0, 260.0, 270.0, 290.0, 315.0, 360.0};
        double value = 100;

        double[] x = new double[expected.length];
        double[] y = new double[expected.length];
        for (int i = 0; i < expected.length; i++) {
            x[i] = value * Math.sin(Math.toRadians(expected[i]));
            y[i] = value * Math.cos(Math.toRadians(expected[i]));
        }

        for (int i = 0; i < expected.length; i++) {
            double actual = BladeBalancer.calculateAngle(x[i], y[i]);
            Assert.assertTrue(Math.abs(actual - expected[i]) < 0.1);
        }
    }

    @Test
    public void calculateDisbalance_EQUAL_BLADES_ZERO_DISK_DSB() {
        ArrayList<Blade> bladeList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            bladeList.add(new Blade(i + 1, "blade" + (i + 1), 1000, false));
        }
        Disbalance dsb = new Disbalance(0.0, 0);
        Disk disk = new Disk(dsb, bladeList);

        Disbalance actual = BladeBalancer.calculateDisbalance(disk);
        Assert.assertTrue(actual.getValue() < 0.001);
    }

    @Test
    public void calculateDisbalance_EQUAL_BLADES_NO_ZERO_DISK_DSB() {
        ArrayList<Blade> bladeList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            bladeList.add(new Blade(i + 1, "blade" + (i + 1), 1000, false));
        }
        Disbalance dsb = new Disbalance(70.0, 4);
        Disk disk = new Disk(dsb, bladeList);

        Disbalance actual = BladeBalancer.calculateDisbalance(disk);

        Disbalance expected = new Disbalance(dsb.getValue(), dsb.getPosition());
        Assert.assertTrue(actual.getValue() - expected.getValue() < 0.001);
        Assert.assertEquals(expected.getPosition(), actual.getPosition());
    }
}