// Created by Yuanhao Ruan, ruanx054, ID 5085043
import java.awt.event.KeyEvent;
import java.io.IOException;

public class CollisionLogger {
    private int screenWidth;
    private int screenHeight;
    private int bucketWidth;
    private int[][] originalHeatMap;
    private int column;
    private int row;
    private int[][] normalizedHeatMap;

    public CollisionLogger(int screenWidth, int screenHeight, int bucketWidth) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.bucketWidth = bucketWidth;
        this.row = screenWidth / bucketWidth;
        this.column = screenHeight / bucketWidth;
        this.originalHeatMap = new int[row][column];
        this.normalizedHeatMap=new int[row][column];
        //create an empty heatmap, every bucket has 0 collision
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                originalHeatMap[i][j] = 0;
            }
        }
    }

    /**
     * This method records the collision event between two balls. Specifically, in increments the bucket corresponding to
     * their x and y positions to reflect that a collision occurred in that bucket.
     */
    public void collide(Ball one, Ball two) {
        // determine the collision position
        double x = (one.getXPos() + two.getXPos()) / 2;
        double y = (one.getYPos() + two.getYPos()) / 2;
        //convert the collision position to its corresponding bucket
        int bucketx = (int)(x / bucketWidth);
        int buckety = (int)(y / bucketWidth);
        // if the collision happens in the bucket, update the collision number+1
        if (bucketx < row && buckety < column) {
            originalHeatMap[bucketx][buckety] += 1;
        }
    }

    /**
     * Returns the heat map scaled such that the bucket with the largest number of collisions has value 255,
     * and buckets without any collisions have value 0.
     */
    public int[][] getNormalizedHeatMap() {
        // Find the largest number in the original heatmap
        int max = originalHeatMap[0][0];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (originalHeatMap[i][j] > max) {
                    max = originalHeatMap[i][j];
                }
            }
        }
        // A normalization factor that will help normalize all the number in the map
        // Convert the number in the original heatmap to the normalized heatmap
        double normalizationFactor = max / 255.0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                normalizedHeatMap[i][j] = (int) (originalHeatMap[i][j] / normalizationFactor);
            }
        }
        return normalizedHeatMap;
    }
}

