import java.util.ArrayList;
import java.util.stream.IntStream;

public class Disk {

    private Disbalance selfDsb;
    private ArrayList<Blade> bladeList;

    public Disk() {
    }

    public Disk(Disbalance selfDsb, ArrayList<Blade> bladeList) {
        this.selfDsb = selfDsb;
        this.bladeList = new ArrayList<>(bladeList);
    }

    public void setSelfDsb(Disbalance dsb) {
        this.selfDsb = dsb;
    }

    public void setBladeList(ArrayList<Blade> bladeList) {
        this.bladeList = bladeList;
    }

    public Disbalance getSelfDsb() {
        return selfDsb;
    }

    public ArrayList<Blade> getBladeList() {
        return bladeList;
    }

    public void setBladeIntoList(Blade blade, int slot) {
        bladeList.set(slot - 1, blade);
    }

    public void fixAllBlades() {
        ArrayList<Integer> list = new ArrayList<>();
        IntStream.range(1, bladeList.size() + 1).forEach(list::add);
        for (Integer i : list) {
            if (!bladeList.get(i - 1).isFixed()) {
                Blade blade = new Blade(
                        bladeList.get(i - 1).getSlot(),
                        bladeList.get(i - 1).getName(),
                        bladeList.get(i - 1).getStaticMoment(),
                        true);
                bladeList.set(i - 1, blade);
            }
        }
    }

    public void unfixAllBlades() {
        ArrayList<Integer> list = new ArrayList<>();
        IntStream.range(1, bladeList.size() + 1).forEach(list::add);
        for (Integer i : list) {
            if (bladeList.get(i - 1).isFixed()) {
                Blade blade = new Blade(
                        bladeList.get(i - 1).getSlot(),
                        bladeList.get(i - 1).getName(),
                        bladeList.get(i - 1).getStaticMoment(),
                        false);
                bladeList.set(i - 1, blade);
            }
        }
    }

    public void fixBlades(ArrayList<Integer> list) {
        for (Integer i : list) {
            Blade blade = new Blade(
                    bladeList.get(i - 1).getSlot(),
                    bladeList.get(i - 1).getName(),
                    bladeList.get(i - 1).getStaticMoment(),
                    true);
            bladeList.set(i - 1, blade);
        }
    }

    public void unfixBlades(ArrayList<Integer> list) {
        for (Integer i : list) {
            Blade blade = new Blade(
                    bladeList.get(i - 1).getSlot(),
                    bladeList.get(i - 1).getName(),
                    bladeList.get(i - 1).getStaticMoment(),
                    false);
            bladeList.set(i - 1, blade);
        }
    }

    public void swapBlades(int slot1, int slot2) {
        Blade blade1 = new Blade(bladeList.get(slot1 - 1));
        Blade blade2 = new Blade(bladeList.get(slot2 - 1));
        blade1.setSlot(slot2);
        blade2.setSlot(slot1);
        setBladeIntoList(blade1, slot2);
        setBladeIntoList(blade2, slot1);
    }
}
