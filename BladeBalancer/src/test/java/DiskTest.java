import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DiskTest {

    @Test
    public void fixAllBlades() {
        Disbalance dsb = new Disbalance(0.0, 0);
        ArrayList<Blade> bladeList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            bladeList.add(new Blade(i + 1, "blade" + (i + 1), 1001 + i, false));
        }
        Disk disk = new Disk(dsb, bladeList);
        disk.fixAllBlades();
        for (int i = 0; i < 5; i++) {
            Assert.assertTrue(disk.getBladeList().get(i).isFixed());
        }
    }

    @Test
    public void unfixAllBlades() {
        Disbalance dsb = new Disbalance(0.0, 0);
        ArrayList<Blade> bladeList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            bladeList.add(new Blade(i + 1, "blade" + (i + 1), 1001 + i, true));
        }
        Disk disk = new Disk(dsb, bladeList);
        disk.unfixAllBlades();
        for (int i = 0; i < 5; i++) {
            Assert.assertEquals(false, disk.getBladeList().get(i).isFixed());
        }
    }

    @Test
    public void fixBlades() {
        ArrayList<Integer> listOfBlades = new ArrayList<>(List.of(3, 4));
        Disbalance dsb = new Disbalance(0.0, 0);
        ArrayList<Blade> bladeList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            bladeList.add(new Blade(i + 1, "blade" + (i + 1), 1001 + i, false));
        }
        Disk disk = new Disk(dsb, bladeList);
        disk.fixBlades(listOfBlades);

        Assert.assertFalse(disk.getBladeList().get(0).isFixed());
        Assert.assertFalse(disk.getBladeList().get(1).isFixed());
        Assert.assertTrue(disk.getBladeList().get(2).isFixed());
        Assert.assertTrue(disk.getBladeList().get(3).isFixed());
        Assert.assertFalse(disk.getBladeList().get(4).isFixed());
    }

    @Test
    public void unfixBlades() {
        ArrayList<Integer> listOfBlades = new ArrayList<>(List.of(3, 4));
        Disbalance dsb = new Disbalance(0.0, 0);
        ArrayList<Blade> bladeList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            bladeList.add(new Blade(i + 1, "blade" + (i + 1), 1001 + i, true));
        }
        Disk disk = new Disk(dsb, bladeList);
        disk.unfixBlades(listOfBlades);

        Assert.assertTrue(disk.getBladeList().get(0).isFixed());
        Assert.assertTrue(disk.getBladeList().get(1).isFixed());
        Assert.assertFalse(disk.getBladeList().get(2).isFixed());
        Assert.assertFalse(disk.getBladeList().get(3).isFixed());
        Assert.assertTrue(disk.getBladeList().get(4).isFixed());
    }

    @Test
    public void swapBlades() {
        Disbalance dsb = new Disbalance(0.0, 0);
        ArrayList<Blade> bladeList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            bladeList.add(new Blade(i + 1, "blade" + (i + 1), 1001 + i, false));
        }
        Disk disk = new Disk(dsb, bladeList);

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

        ArrayList<Blade> expectedBladeList = new ArrayList<>();
        expectedBladeList.add(blade5);
        expectedBladeList.add(blade2);
        expectedBladeList.add(blade3);
        expectedBladeList.add(blade4);
        expectedBladeList.add(blade1);
        expectedBladeList.add(blade6);
        expectedBladeList.add(blade7);
        expectedBladeList.add(blade8);
        expectedBladeList.add(blade9);
        expectedBladeList.add(blade10);

        int slot1 = 1;
        int slot5 = 5;

        disk.swapBlades(slot1, slot5);

        for (int i = 0; i < bladeList.size(); i++) {
            Assert.assertEquals(expectedBladeList.get(i).getName(), disk.getBladeList().get(i).getName());
            Assert.assertEquals(bladeList.get(i).getSlot(), disk.getBladeList().get(i).getSlot());
        }
    }
}