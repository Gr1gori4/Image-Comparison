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

    private static BufferedImage screenShot, referenceScreenshots;
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

    public static int CompareImage(Reader reader,String NameScreenshots, String pathScreenshots)
    {
        ScreenshotData data = reader.screenshotData;

            //File fileScreenShot = new File("/home/ubuntu/IdeaProjects/ImageComparison/Screenshots/"+pathScreenshots);
            //File fileScreenShot = new File("C:\\Users\\d.andronychev\\AutoTest\\Screen\\"+pathScreenshots);
            try {
                // ----------------------Window--------------------------------
                //referenceScreenshots = ImageIO.read(new File("C:\\Users\\d.andronychev\\AutoTest\\RefScreen\\"+NameScreenshots+".png"))
                //                              .getSubimage(data.getXCoordinates(),data.getYCoordinates(),data.getWidth(),data.getHeight());

                //screenShot = ImageIO.read(new File("C:\\Users\\d.andronychev\\AutoTest\\Screen\\"+pathScreenshots))
                //                    .getSubimage(data.getXCoordinates(),data.getYCoordinates(),data.getWidth(),data.getHeight());

                // -----------------------Linux-------------------------------
                referenceScreenshots = ImageIO.read(new File("/home/ubuntu/IdeaProjects/ImageComparison/Screenshots/"+NameScreenshots+".png"))
                                              .getSubimage(data.getXCoordinates(),data.getYCoordinates(),data.getWidth(),data.getHeight());

                screenShot = ImageIO.read(new File("/home/ubuntu/IdeaProjects/ImageComparison/Screenshots/"+pathScreenshots))
                                    .getSubimage(data.getXCoordinates(),data.getYCoordinates(),data.getWidth(),data.getHeight());

            } catch (IOException e1) {
                System.out.println("Invalid address");
            }

            if(compare_image(referenceScreenshots, screenShot)!=1)
                return 1;
            else
                return 0;

    }

    private static void loadLibraries(){
        try {
            System.load("/home/ubuntu/opencv-4.8.0/build/lib/libopencv_java480.so");
        } catch (Exception e){
            throw new RuntimeException("Failed to load opencv native library", e);
        }

    }


    public static int main(String[] args) {

        // ------------------------------------------------------
        loadLibraries(); // Linux
        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // Window
        // ------------------------------------------------------

        reader.Reading(args[0]);
        return CompareImage(reader,args[0],args[1]);


    }
}
