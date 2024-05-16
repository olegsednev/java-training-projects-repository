import java.util.*;
import java.util.stream.Collectors;

public class BladeBalancer {

    public static Disk arrangeNotFixedBlades (Disk disk) {
        ArrayList<Integer> slotList = new ArrayList<>();
        ArrayList<Blade> sortedBladeList = disk.getBladeList().stream()
                .filter(blade -> !blade.isFixed())
                .peek(b -> slotList.add(b.getSlot())) //peek allows us to make action on each stream element without updating it
                .sorted((o1, o2) -> o2.getStaticMoment() - o1.getStaticMoment())
                .collect(Collectors.toCollection(ArrayList::new));

        int slotNum = 0;
        for (int i = 0; i < sortedBladeList.size(); i++) {
            if (i % 2 == 0) {
                //check this
                Blade blade = sortedBladeList.get(i);
                blade.setSlot(slotList.get(slotNum));
                disk.setBladeIntoList(blade, slotList.get(slotNum));
                slotNum++;
            }
        }
        for (int i = 0; i < sortedBladeList.size(); i++) {
            if (i % 2 != 0) {
                //check this
                Blade blade = sortedBladeList.get(i);
                blade.setSlot(slotList.get(slotNum));
                disk.setBladeIntoList(blade, slotList.get(slotNum));
                slotNum++;
            }
        }
        return disk;
    }

    public static Disk balance(Disk disk, double requiredMinDsb) {

        Disbalance currentDsb = calculateDisbalance(disk);
        int heavySlot = currentDsb.getPosition();
        int lightSlot = findOppositeSlot(disk, heavySlot);

        boolean equatorStatement = true; //true if we do not cross the disk's equator line during completing steps to left or right.
        int iterationCount = 0;

        while (currentDsb.getValue() >= requiredMinDsb && iterationCount < 1000 && equatorStatement) {
            int r = (int) Math.round((disk.getBladeList().size() * 0.5 * 0.2));
            int radius = Math.max(r, 1);
            PriorityQueue<Blade> heavyBladeQueue = captureBladesAroundTheHeavyPlace(disk, heavySlot, radius);
            PriorityQueue<Blade> lightBladeQueue = captureBladesAroundTheLightPlace(disk, lightSlot, radius);

            assert heavyBladeQueue.peek() != null;
            Blade heavyBlade = heavyBladeQueue.poll();
            assert lightBladeQueue.peek() != null;
            Blade lightBlade = lightBladeQueue.poll();

            if (lightBlade.getStaticMoment() >= heavyBlade.getStaticMoment()) {
                while (lightBlade.getStaticMoment() >= heavyBlade.getStaticMoment() && radius <= Math.round(disk.getBladeList().size() * 0.25)) {
                    radius = radius + 1;
                    heavyBladeQueue = captureBladesAroundTheHeavyPlace(disk, heavySlot, radius);
                    lightBladeQueue = captureBladesAroundTheLightPlace(disk, lightSlot, radius);

                    assert heavyBladeQueue.peek() != null;
                    heavyBlade = heavyBladeQueue.poll();
                    assert lightBladeQueue.peek() != null;
                    lightBlade = lightBladeQueue.poll();
                }
            }

            if (radius > Math.round(disk.getBladeList().size() * 0.25)) {
                System.out.println("Alarm1");
                equatorStatement = false;
            }

            while (currentDsb.getValue() <= (heavyBlade.getStaticMoment() - lightBlade.getStaticMoment())
                    && (heavyBladeQueue.size() > 1) && equatorStatement) {
                heavyBlade = heavyBladeQueue.poll();
            }

            while (currentDsb.getValue() <= (heavyBlade.getStaticMoment() - lightBlade.getStaticMoment())
                     && (lightBladeQueue.size() > 1) && equatorStatement) {
                lightBlade = lightBladeQueue.poll();
            }

            if (heavyBlade.getStaticMoment() > lightBlade.getStaticMoment()
                    && (currentDsb.getValue()) >= (heavyBlade.getStaticMoment() - lightBlade.getStaticMoment())
                    && equatorStatement) {
                System.out.println("Iteration: " + iterationCount);
                System.out.println(heavyBlade.getName() + " - " + heavyBlade.getStaticMoment() + " - " + heavyBlade.getSlot());
                System.out.println(lightBlade.getName() + " - " + lightBlade.getStaticMoment() + " - " + lightBlade.getSlot());
                System.out.println();
                disk.swapBlades(heavyBlade.getSlot(), lightBlade.getSlot());
                iterationCount++; //increment iterationCount field after each swap operation
                currentDsb = calculateDisbalance(disk);
                System.out.println(currentDsb.getValue());
            }

        }
        System.out.println(iterationCount);
        return disk;
    }

    public static PriorityQueue<Blade> captureBladesAroundTheHeavyPlace(Disk disk, int slot, int radius) {
        PriorityQueue<Blade> queue = new PriorityQueue<>(Collections.reverseOrder(Comparator.comparingDouble(Blade::getStaticMoment)));
        int heavySlotLeftNeighbor = slot;
        int heavySlotRightNeighbor = slot;

        if (!disk.getBladeList().get(slot - 1).isFixed()) {
            queue.add(new Blade(disk.getBladeList().get(slot - 1)));
        }
        for (int i = 0; i < radius; i++) {
            heavySlotLeftNeighbor = findLeftNeighbor(disk, heavySlotLeftNeighbor);
            heavySlotRightNeighbor = findRightNeighbor(disk, heavySlotRightNeighbor);

            if (!disk.getBladeList().get(heavySlotLeftNeighbor - 1).isFixed()) {
                queue.add(new Blade(disk.getBladeList().get(heavySlotLeftNeighbor - 1)));
            }
            if (!disk.getBladeList().get(heavySlotRightNeighbor - 1).isFixed()) {
                queue.add(new Blade(disk.getBladeList().get(heavySlotRightNeighbor - 1)));
            }
        }
        return queue;
    }

    public static PriorityQueue<Blade> captureBladesAroundTheLightPlace(Disk disk, int slot, int radius) {
        PriorityQueue<Blade> queue = new PriorityQueue<>(Comparator.comparingDouble(Blade::getStaticMoment));
        int lightSlotLeftNeighbor = slot;
        int lightSlotRightNeighbor = slot;

        if (!disk.getBladeList().get(slot - 1).isFixed()) {
            queue.add(new Blade(disk.getBladeList().get(slot - 1)));
        }
        for (int i = 0; i < radius; i++) {
            lightSlotLeftNeighbor = findLeftNeighbor(disk, lightSlotLeftNeighbor);
            lightSlotRightNeighbor = findRightNeighbor(disk, lightSlotRightNeighbor);

            if (!disk.getBladeList().get(lightSlotLeftNeighbor - 1).isFixed()) {
                queue.add(new Blade(disk.getBladeList().get(lightSlotLeftNeighbor - 1)));
            }
            if (!disk.getBladeList().get(lightSlotRightNeighbor - 1).isFixed()) {
                queue.add(new Blade(disk.getBladeList().get(lightSlotRightNeighbor - 1)));
            }
        }
        return queue;
    }

    public static Integer findLeftNeighbor(Disk disk, int slot) {
        int leftNeighbor;
        if (slot > 1) {
            leftNeighbor = slot - 1;
        }
        else {
            leftNeighbor = disk.getBladeList().size();
        }
        return leftNeighbor;
    }

    public static Integer findRightNeighbor(Disk disk, int slot) {
        int rightNeighbor;
        if (slot < disk.getBladeList().size()) {
            rightNeighbor = slot + 1;
        }
        else {
            rightNeighbor = 1;
        }
        return rightNeighbor;
    }

    public static Disbalance calculateDisbalance (Disk disk) {
        double bladeCount = disk.getBladeList().size();
        double degreePerBlade = 360 / bladeCount;
        double x = disk.getSelfDsb().getValue() * Math.sin(Math.toRadians(disk.getSelfDsb().getPosition() * degreePerBlade));
        double y = disk.getSelfDsb().getValue() * Math.cos(Math.toRadians(disk.getSelfDsb().getPosition() * degreePerBlade));
        int i = 0;
        for (Blade blade : disk.getBladeList()) {
            x += blade.getStaticMoment() * Math.sin(Math.toRadians(i * degreePerBlade));
            y += blade.getStaticMoment() * Math.cos(Math.toRadians(i * degreePerBlade));
            i++;
        }
        double dsbValue = Math.pow(x * x + y * y, 0.5);
        double angle = calculateAngle(x, y);
        int dsbPosition = (int) Math.round(angle / degreePerBlade);
        return new Disbalance(dsbValue, dsbPosition);
    }

    public static Double calculateAngle (double x, double y) {

        if (x == 0.0) {
            if (y > 0) {
                return 0.0;
            }
            if (y < 0) {
                return 270.0;
            }
        }

        if (y == 0.0) {
            if (x > 0) {
                return 360.0;
            }
            if (x < 0) {
                return 180.0;
            }
        }

        double angle = 0;
        double degrees = Math.toDegrees(Math.atan(Math.abs(y / x)));
        //the first quarter
        if (x > 0 & y > 0) {
            angle = 90 - degrees;
        }

        //the second quarter
        if (x > 0 & y < 0) {
            angle = 90 + degrees;
        }

        //the third quarter
        if (x < 0 & y < 0) {
            angle = 270 - degrees;
        }

        //the fourth quarter
        if (x < 0 & y > 0) {
            angle = 270 + degrees;
        }

        return angle;
    }

    public static Integer findOppositeSlot (Disk disk, Integer slot) {
        double bladeCount = disk.getBladeList().size();
        double degreePerBlade = 360 / bladeCount;
        int oppositeSlot;
        if (slot * degreePerBlade > 180) {
            oppositeSlot = (int) Math.round(slot - 180 / degreePerBlade);
        }
        else oppositeSlot = (int) Math.round(slot + 180 / degreePerBlade);

        return oppositeSlot;
    }

}
