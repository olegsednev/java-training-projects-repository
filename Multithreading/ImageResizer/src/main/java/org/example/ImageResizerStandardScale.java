package org.example;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageResizerStandardScale implements Runnable{
    private File[] files;
    private int newWidth;
    private String dstFolder;

    public ImageResizerStandardScale(File[] files, int newWidth, String dstFolder) {
        this.files = files;
        this.newWidth = newWidth;
        this.dstFolder = dstFolder;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        try {
            for (File file : files) {
                BufferedImage image = ImageIO.read(file);
                if (image == null) {
                    continue;
                }

                int newHeight = (int) Math.round(
                        image.getHeight() / (image.getWidth() / (double) newWidth)
                );

                BufferedImage newImage = new BufferedImage(
                        newWidth, newHeight, BufferedImage.TYPE_INT_RGB
                );

                int widthStep = image.getWidth() / newWidth;
                int heightStep = image.getHeight() / newHeight;

                for (int x = 0; x < newWidth; x++) {
                    for (int y = 0; y < newHeight; y++) {
                        int rgb = image.getRGB(x * widthStep, y * heightStep);
                        newImage.setRGB(x, y, rgb);
                    }
                }
                String fileName = file.getName();
                File newFile = new File(dstFolder + "/" + fileName);
                String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
                ImageIO.write(newImage, fileType, newFile);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Duration: " + (System.currentTimeMillis() - start));
    }
}
