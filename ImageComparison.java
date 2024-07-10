package org.example;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ImageComparison {

    private static BufferedImage image, image1, screenShot, referenceScreenshots;
    private static Reader reader = new Reader();
    public static double compare_image(BufferedImage img_1, BufferedImage img_2) {
        // Использование веделенного метода
        Mat mat_1 = conv_Mat(img_1);
        Mat mat_2 = conv_Mat(img_2);

        Mat hist_1 = new Mat();
        Mat hist_2 = new Mat();

        MatOfFloat ranges = new MatOfFloat(0f, 256f);
        MatOfInt histSize = new MatOfInt(25);

        Imgproc.calcHist(Arrays.asList(mat_1), new MatOfInt(0), new Mat(), hist_1, histSize, ranges);
        Imgproc.calcHist(Arrays.asList(mat_2), new MatOfInt(0), new Mat(), hist_2, histSize, ranges);

        return Imgproc.compareHist(hist_1, hist_2, Imgproc.CV_COMP_CORREL);
    }
    public static Mat conv_Mat(BufferedImage img){
        byte[] data = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
        Mat mat = new Mat(img.getHeight(),img.getWidth(),CvType.CV_8UC3);
        mat.put(0,0,data);

        Mat mat1 = new Mat(img.getHeight(),img.getWidth(),CvType.CV_8UC3);
        Imgproc.cvtColor(mat, mat1, Imgproc.COLOR_RGB2HSV);

        return mat1;
    }

    public static void CompareImage(Reader reader, String pathScreenshots,String ReferenceScreenshots)
    {
        for(ScreenshotData data: reader.screenshotData)
        {
            File fileScreenShot = new File(pathScreenshots+data.getScreenshotName()+".png");
            try {
                image = ImageIO.read(new File(ReferenceScreenshots+data.getScreenshotName()+".png"));
                image1 = ImageIO.read(fileScreenShot);
            } catch (IOException e1) {
                System.out.println("Invalid address");
            }

            referenceScreenshots = image.getSubimage(data.getXCoordinates(),data.getYCoordinates(),data.getWidth(),data.getHeight());
            screenShot = image1.getSubimage(data.getXCoordinates(),data.getYCoordinates(),data.getWidth(),data.getHeight());

            if(compare_image(referenceScreenshots, screenShot)!=1)
            {
                System.out.println("Изображения различны");

                try {
                    ImageIO.write(screenShot,"png",new File(pathScreenshots+data.getScreenshotName()+"_obr.png"));
                } catch (IOException e) {
                    System.out.println("Invalid address");
                }
            }
            else {
                System.out.println("Идентичные изображения");

                if (fileScreenShot.delete()) {
                    System.out.println("File deleted successfully");
                }
            }
        }
    }
    public static void FileDelete(File fileScreenShot)
    {
        if (fileScreenShot.delete()) {
            System.out.println("File deleted successfully");
        }
    }

    public static void main(String[] args) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        reader.Reading(args[1]);
        CompareImage(reader,args[0],args[1]);
    }
}
