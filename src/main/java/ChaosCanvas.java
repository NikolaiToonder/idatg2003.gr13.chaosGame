import javafx.scene.transform.Affine;
import matrix.Matrix2x2;
import transformations.AffineTransform2D;
import vectors.Vector2D;

/**
 * Class responsible for making a canvas for the chaos game.
 * The canvas is a 2D array of integers, where 1 represents a pixel and 0 represents no pixel.
 */

public class ChaosCanvas {

private int[][] canvas;
private int width;
private int height;
private Vector2D minCoords;
private Vector2D maxCoords;
private AffineTransform2D transformCoordsToIndices;

public ChaosCanvas(int width, int height, Vector2D minCoords, Vector2D maxCoords) {

  this.width = width;
  this.height = height;
  this.minCoords = minCoords;
  this.maxCoords = maxCoords;
  setTransformCoordsToIndices();
}

public int getPixel(Vector2D point) {
  return canvas[(int) point.getX0()][(int) point.getX1()];
}

public void putPixel(Vector2D point) {
  canvas[(int) point.getX0()][(int) point.getX1()] = 1;
}

public int[][] getCanvasArray() {
  return canvas;
}

public void clear() {
  canvas = new int[width][height];
}

public void setTransformCoordsToIndices() {
  double[][] a = {
      {0, (width - 1) / (minCoords.getX1() - maxCoords.getX1())},
      {(height - 1) / (maxCoords.getX0() - minCoords.getX0()), 0}
  };
  Vector2D b = new Vector2D((width-1)*maxCoords.getX1()/(maxCoords.getX1()-minCoords.getX1()),
      (height-1)*minCoords.getX0()/(minCoords.getX0()-maxCoords.getX0()));

  transformCoordsToIndices = new AffineTransform2D(new Matrix2x2(a[0][0], a[0][1], a[1][0], a[1][1])
      ,b);
}
}
