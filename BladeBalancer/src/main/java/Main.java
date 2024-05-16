import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args){

        ArrayList<Integer> listOfShortBlades = new ArrayList<>(List.of(
                11, 12, 23, 24,
                35, 36, 47, 48,
                59, 60, 72, 73));
        /*
        ArrayList<Integer> listOfPrepN1LongBlades = new ArrayList<>(List.of(
                4, 5, 6,
                16, 17,
                28, 29,
                52, 53,
                64, 65
        ));

        ArrayList<Integer> listOfPrepN2LongBlades = new ArrayList<>(List.of(
                18, 19,
                30, 31,
                40, 41, 42,
                54, 55,
                66, 67
        ));
         */

        ArrayList<Integer> listOfPrepLongBlades = new ArrayList<>(List.of(
                4, 5, 6,
                16, 17, 18, 19,
                28, 29, 30, 31,
                40, 41, 42,
                52, 53, 54, 55,
                64, 65, 66, 67
        ));


        ArrayList<Integer> listOfOtherLongBlades = new ArrayList<>();
        IntStream.range(1, 73 + 1).forEach(listOfOtherLongBlades::add);
        ArrayList<Integer> list = new ArrayList<>();
        Stream.of(listOfShortBlades, listOfPrepLongBlades).forEach(list::addAll);
        Collections.sort(list);
        Collections.reverse(list);
        for (Integer i: list) {
            listOfOtherLongBlades.remove(i);
        }

        Disk disk = DiskFromTxtParser.parse("/home/oleg/IdeaProjects/BladeBalancer/file.txt");

        System.out.println("Disk preparation for balancing. AT first we will arrange all blade inside groups");
        System.out.println();
        System.out.println("Initial disk disbalance without blades: " + disk.getSelfDsb().getValue() + disk.getSelfDsb().getPosition());
        System.out.println("Initial system disbalance: " + BladeBalancer.calculateDisbalance(disk).getValue() + BladeBalancer.calculateDisbalance(disk).getPosition());


        disk.fixAllBlades();
        disk.unfixBlades(listOfOtherLongBlades);
        BladeBalancer.arrangeNotFixedBlades(disk);
        System.out.println("System disbalance after not prep long blades arranging: " + BladeBalancer.calculateDisbalance(disk).getValue());
        System.out.println();

        disk.fixAllBlades();
        disk.unfixBlades(listOfShortBlades);
        BladeBalancer.arrangeNotFixedBlades(disk);
        System.out.println("System disbalance after short blades arranging: " + BladeBalancer.calculateDisbalance(disk).getValue());
        System.out.println();

        for (int i = 0; i < 6; i++) {
            System.out.println("_______________________________________________________________________________________________");
            System.out.println("Iteration number " + i);
            disk.fixAllBlades();
            disk.unfixBlades(listOfOtherLongBlades);
            BladeBalancer.balance(disk, 1.0);
            System.out.println("System disbalance after balancing not prep long blades: " + BladeBalancer.calculateDisbalance(disk).getValue());
            System.out.println();

            disk.fixAllBlades();
            disk.unfixBlades(listOfShortBlades);
            BladeBalancer.balance(disk, 1.0);
            System.out.println("System disbalance after balancing short blades: " + BladeBalancer.calculateDisbalance(disk).getValue());
        }
        disk.unfixAllBlades();
        disk.fixBlades(listOfPrepLongBlades);
        System.out.println("Heavy slot: " + BladeBalancer.calculateDisbalance(disk).getPosition());
        System.out.println("Light slot: " + BladeBalancer.findOppositeSlot(disk, BladeBalancer.calculateDisbalance(disk).getPosition()));

        ResultWriter.writeResultIntoFile(disk, "/home/oleg/IdeaProjects/BladeBalancer/BalanceResult.txt");
    }

}
