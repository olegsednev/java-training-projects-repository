package org.example;

import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageResizer implements Runnable{

    private File[] files;
    private int newWidth;
    private String dstFolder;

    public ImageResizer(File[] files, int newWidth, String dstFolder) {
        this.files = files;
        this.newWidth = newWidth;
        this.dstFolder = dstFolder;
    }


    @Override
    public void run() {
        long start = System.currentTimeMillis();
        try {
            for (File file: files) {
                BufferedImage image = ImageIO.read(file);
                if (image == null) {
                    continue;
                }

                int newHeight = (int) Math.round(
                  image.getHeight() / (image.getWidth() / (double) newWidth)
                );
                BufferedImage newImage = Scalr.resize(image, newWidth, newHeight);
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
