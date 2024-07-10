package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Reader {

    public List<ScreenshotData> screenshotData = new ArrayList<>();
    public void Reading (String ReferenceScreenshots)  {

        File file = new File(ReferenceScreenshots+"InformationScreenshots.txt");

        try(BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                String[] parts = line.split(" ");
                screenshotData.add(new ScreenshotData(parts[0],Integer.parseInt(parts[1]),Integer.parseInt(parts[2]),Integer.parseInt(parts[3]),Integer.parseInt(parts[4])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
