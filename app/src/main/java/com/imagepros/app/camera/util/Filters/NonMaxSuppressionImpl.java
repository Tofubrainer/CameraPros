/**
 * <class>NonMaxSuppressionImpl</class>
 *
 * <summary>
 * This Class is used by CannyEdgeDetectionImpl to apply Non-maximum suppression
 * on an image of int array
 * </summary>
 *
 * <author>Jack Wang</author>
 * <date>March 5 2014</date>
 * */

package com.imagepros.app.camera.util.Filters;

import android.util.Log;

public class NonMaxSuppressionImpl {

    private int count = 0;

    // This Class is used by CannyEdgeDetectionImpl to apply Non-maximum suppression
    // on an image of int array
    // returns the int array image after the suppression
    public void applyNonMaxSuppression(int[] srcImage, char[] thetaArr, int srcImageWidth, int startIndex, int endIndex) {
        for(int index = startIndex; index < endIndex; index++) {
            srcImage[index] = checkIfMax(srcImage, thetaArr, index - startIndex, srcImageWidth, startIndex, endIndex);
        }
    }

    // Checks if the pixel at index is the maximum in its direction
    // return its value if it is the max, return 0 otherwise
    private int checkIfMax(int[] srcImage, char[] thetaArr, int index, int srcImageWidth, int startIndex, int endIndex) {
        char theta = thetaArr[index];

        int srcImageValue1;
        int srcImageValue2;
        int srcOrigValue;

        if(theta == (char)0) { // 0 degree
            srcImageValue1 = index + 1;
            srcImageValue2 = index - 1;
        } else if(theta == (char)45) { //45 degree
            srcImageValue1 = index + srcImageWidth - 1;
            srcImageValue2 = index - srcImageWidth + 1;
        } else if(theta == (char)90) { // 90 degree
            srcImageValue1 = index + srcImageWidth;
            srcImageValue2 = index - srcImageWidth;
        } else if(theta == (char)135) { //135 degree
            srcImageValue1 = index + srcImageWidth + 1;
            srcImageValue2 = index - srcImageWidth - 1;
        } else {
            return 0;
        }

        // check for boundaries
        if(srcImageValue1>= thetaArr.length || srcImageValue2 < 0) {
            return 0;
        }

        // if the pixel it is comparing with has different direction values
        // suppress the index value by returning 0
        if (thetaArr[srcImageValue1] != theta || thetaArr[srcImageValue2] != theta) {
            return 0;
        }

        srcImageValue1 = srcImage[srcImageValue1 + startIndex];
        srcImageValue2 = srcImage[srcImageValue2 + startIndex];
        srcOrigValue = srcImage[index + startIndex];

        // check if value at index is the maximum
        if(srcOrigValue >= srcImageValue1 && srcOrigValue >= srcImageValue2) {
            return srcOrigValue;
        }
        return 0;
    }
}
