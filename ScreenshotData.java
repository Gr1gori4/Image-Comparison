package org.example;


public class ScreenshotData {

    private String screenshotName;
    private int xCoordinates;
    private int yCoordinates;
    private int width;
    private int height;

    public ScreenshotData(String screenshotName,int xCoordinates, int yCoordinates, int width, int height)
    {
        this.screenshotName = screenshotName;
        this.xCoordinates = xCoordinates;
        this.yCoordinates = yCoordinates;
        this.width = width;
        this.height = height;
    }
    public String getScreenshotName() { return screenshotName; }
    public int getXCoordinates()
    {
        return xCoordinates;
    }
    public int getYCoordinates()
    {
        return yCoordinates;
    }
    public int getWidth()
    {
        return width;
    }
    public int getHeight()
    {
        return height;
    }
}
