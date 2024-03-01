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
    this.canvas = new int[width][height];
  }

  public int getPixel(int x, int y) {
    if (x < 0 || x >= width || y < 0 || y >= height) {
      return 0;
    }
    return canvas[x][y];
  }

  public void putPixel(Vector2D point) {
    Vector2D transformed = setTransformCoordsToIndices(point);
    int xIndex = (int) transformed.getX0();
    int yIndex = (int) transformed.getX1();
    System.out.println("xIndex: " + xIndex + " yIndex: " + yIndex);

    // Check if the calculated indices are within the bounds of the canvas
    if (xIndex >= 0 && xIndex < width && yIndex >= 0 && yIndex < height) {
      canvas[xIndex][yIndex] = 1;
    } else {
      canvas[xIndex][yIndex] = 0;
    }
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

  public Vector2D setTransformCoordsToIndices(Vector2D point) {
    Matrix2x2 a = new Matrix2x2(0,
        (width - 1) / (minCoords.getX1() - maxCoords.getX1()),
        (height - 1) / (maxCoords.getX0() - minCoords.getX0()),
        0);
    Vector2D b = new Vector2D(
        ((width - 1) * maxCoords.getX1()) / (maxCoords.getX1() - minCoords.getX1()),
        ((height - 1) * minCoords.getX0()) / (minCoords.getX0() - maxCoords.getX0()));

    transformCoordsToIndices = new AffineTransform2D(a, b);
    return transformCoordsToIndices.transform(point);
  }

}
