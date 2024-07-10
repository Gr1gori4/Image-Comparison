package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Reader {

    public ScreenshotData screenshotData;
    public void Reading (String NameScreenshots)  {

        // ------------------------------------------------------
        File file = new File("/home/ubuntu/IdeaProjects/ImageComparison/ReferenceScreenshots/InformationScreenshots.txt"); // Linux
        //File file = new File("C:\\Users\\d.andronychev\\AutoTest\\RefScreen\\InformationScreenshots.txt"); // Window
        // ------------------------------------------------------

        try(BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                String[] parts = line.split(" ");
                if (parts[0].equals(NameScreenshots) ) {
                    screenshotData = new ScreenshotData(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]));
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
