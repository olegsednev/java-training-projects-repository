import java.io.FileWriter;
import java.io.IOException;

public class ResultWriter {
    public static void writeResultIntoFile(Disk disk, String path) {
        try (FileWriter fileWriter = new FileWriter(path, false)){
            for (Blade blade : disk.getBladeList()) {
                String template;
                String name;
                if (blade.isFixed()) {
                    template = "%-5d %-10s %-5d";
                    name = blade.getName() + "*";
                }
                else {
                    template = "%-5d %-10s %-5d";
                    name = blade.getName();
                }
                String data = String.format((template), blade.getSlot(), name, blade.getStaticMoment());
                fileWriter.append(data);
                fileWriter.append("\n");
            }
            String template1 = "System disbalance after balancing [gram * sm]: %-5.2f";
            fileWriter.append(String.format((template1), BladeBalancer.calculateDisbalance(disk).getValue()));
            fileWriter.append("\n");
            String template2 = "System disbalance after balancing [gram * mm]: %-5.2f";
            fileWriter.append(String.format((template2), BladeBalancer.calculateDisbalance(disk).getValue() * 10));
            fileWriter.append("\n");
            fileWriter.append("Heavy slot: ").append(String.valueOf(BladeBalancer.calculateDisbalance(disk).getPosition()));
            fileWriter.append("\n");
            fileWriter.append("Light slot: ").append(String.valueOf(BladeBalancer.findOppositeSlot(disk, BladeBalancer.calculateDisbalance(disk).getPosition())));
            fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
