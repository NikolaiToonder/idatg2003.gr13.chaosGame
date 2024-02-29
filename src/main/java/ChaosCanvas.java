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
public AffineTransform2D transformCoordsToIndices;

public ChaosCanvas(int width, int height, Vector2D minCoords, Vector2D maxCoords) {

  this.width = width;
  this.height = height;
  this.minCoords = minCoords;
  this.maxCoords = maxCoords;
  this.canvas = new int[width][height];
}

public int getPixel(Vector2D point) {
  Vector2D transformed = setTransformCoordsToIndices(point);
  return canvas[(int) transformed.getX0()][(int) transformed.getX1()];
}

public void putPixel(Vector2D point) {
  Vector2D transformed = setTransformCoordsToIndices(point);
  canvas[(int) transformed.getX0()][(int) transformed.getX1()] = 1;
}
public int getWidth() {
  return width;
}
public int getHeight() {
  return height;
}

public int[][] getCanvasArray() {
  return canvas;
}

public void clear() {
  canvas = new int[width][height];
}
/*
public Vector2D setTransformCoordsToIndices(Vector2D point) {
  Matrix2x2 a = new Matrix2x2(0,
      (height - 1) / (minCoords.getX1() - maxCoords.getX1()),
  (width - 1) / (maxCoords.getX0() - minCoords.getX0()),
      0);
  Vector2D b = new Vector2D(((height-1)*maxCoords.getX1())/(maxCoords.getX1()-minCoords.getX1()),
      ((width-1)*minCoords.getX0())/(minCoords.getX0()-maxCoords.getX0()));

  transformCoordsToIndices = new AffineTransform2D(a,b);
  return transformCoordsToIndices.transform(point);
  }*/

  public Vector2D setTransformCoordsToIndices(Vector2D point) {
    double a11 = 0;
    double a12 = (width - 1) / (minCoords.getX1() - maxCoords.getX1());
    double a21 = (height - 1) / (maxCoords.getX0() - minCoords.getX0());
    double a22 = 0;

    Matrix2x2 a = new Matrix2x2(a11, a12, a21, a22);
    Vector2D b = new Vector2D((width - 1) * maxCoords.getX1() / (minCoords.getX1() - maxCoords.getX1()),
        (height - 1) * minCoords.getX0() / (maxCoords.getX0() - minCoords.getX0()));

    transformCoordsToIndices = new AffineTransform2D(a, b);
    return transformCoordsToIndices.transform(point);
  }


}
