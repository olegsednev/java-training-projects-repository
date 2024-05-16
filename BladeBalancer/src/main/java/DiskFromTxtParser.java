import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DiskFromTxtParser {
    public static Disk parse(String path) {
        Disk disk = new Disk();
        try {
            List<String> lines = Files.readAllLines(Path.of(path));
            Disbalance dsb = parseSelfDiskDsb(lines.get(0));
            disk.setSelfDsb(dsb);
            ArrayList<Blade> bladeList = new ArrayList<>();
            for (int i = 1; i < lines.size(); i++) {
                bladeList.add(parseBlade(lines.get(i)));
            }
            disk.setBladeList(bladeList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return disk;
    }

    private static Blade parseBlade(String line) {
        String[] bladeInfo = line.split("\\s+");
        return new Blade(
                Integer.parseInt(bladeInfo[0]),
                bladeInfo[1],
                Integer.parseInt(bladeInfo[2]),
                "fixed".equals(bladeInfo[3])
        );
    }

    private static Disbalance parseSelfDiskDsb(String line) {
        String[] diskInfo = line.split("\\s+");
        return new Disbalance(Double.parseDouble(diskInfo[1]), Integer.parseInt(diskInfo[0]));
    }


}
