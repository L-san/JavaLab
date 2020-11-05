package ru.ssau.tk.lsan.mathBar;

import org.testng.annotations.Test;
import ru.ssau.tk.lsan.graphicsPack.algorithms.MadgwickAlgorithm;

public class MainTest extends MadgwickAlgorithm{
    @Test
    public void theShortestWayInGraph(){
        double[][] distanceMatrix = new double[][]{
                {Double.POSITIVE_INFINITY, 1, 3, 2, 2, 1, 2, 1, Double.POSITIVE_INFINITY},
                {Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 4, Double.POSITIVE_INFINITY, 1, Double.POSITIVE_INFINITY, 3, 2, Double.POSITIVE_INFINITY},
                {Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY},
                {Double.POSITIVE_INFINITY, 1, 5, Double.POSITIVE_INFINITY, 2, Double.POSITIVE_INFINITY, 4, 3, Double.POSITIVE_INFINITY},
                {Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 3, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 2, 1, Double.POSITIVE_INFINITY},
                {Double.POSITIVE_INFINITY, 2, 6, 1, 3, Double.POSITIVE_INFINITY, 5, 4, Double.POSITIVE_INFINITY},
                {Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 1, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY},
                {Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 2, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 1, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY},
                {1, 2, 1, 3, 3, 2, 3, 2, Double.POSITIVE_INFINITY}};
        double[][] road2 = matrixMultiplication(distanceMatrix,distanceMatrix,9,9,9);
        double[][] road3 = matrixMultiplication(road2,distanceMatrix,9,9,9);
        double[][] road4 = matrixMultiplication(road3,distanceMatrix,9,9,9);
        for(int i = 0; i<9; i++){
            for(int j = 0; j<9; j++){
                System.out.print(road4[i][j]);
            }
            System.out.print('\n');
        }
    }

}