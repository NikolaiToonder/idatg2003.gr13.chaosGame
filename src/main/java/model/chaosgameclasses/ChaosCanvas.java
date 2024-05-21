package model.chaosgameclasses;

import model.math.matrix.Matrix2x2;
import model.math.transformations.AffineTransform2D;
import model.math.vectors.Vector2D;

/**
 * Class responsible for making a canvas for the chaos game. The canvas is a 2D array of integers,
 * where 1 represents a pixel and 0 represents no pixel.
 *
 * @version 1.0
 */
public class ChaosCanvas {

  private int[][] canvas;
  private int[][] canvasHeatMap;
  private int width;
  private int height;
  private Vector2D minCoords;
  private Vector2D maxCoords;
  private Vector2D transformed;
  private final Vector2D originalMinCoords;
  private final Vector2D originalMaxCoords;

  /**
   * Constructor for the chaosGameClasses.ChaosCanvas class.
   *
   * @param width     The width of the canvas
   * @param height    The height of the canvas
   * @param minCoords The minimum coordinates of the canvas
   * @param maxCoords The maximum coordinates of the canvas
   */
  public ChaosCanvas(int height, int width, Vector2D minCoords, Vector2D maxCoords) {
    setWidth(width);
    setHeight(height);
    this.originalMinCoords = minCoords;
    this.minCoords = minCoords;
    this.originalMaxCoords = maxCoords;
    this.maxCoords = maxCoords;
    this.canvas = new int[height][width];
    this.canvasHeatMap = new int[width][height];
  }

  public void setWidth(int width) {
    this.width = width;
    this.canvas = new int[width][height];
    this.canvasHeatMap = new int[width][height];
  }

  public void setHeight(int height) {
    this.height = height;
    this.canvas = new int[width][height];
    this.canvasHeatMap = new int[width][height];
  }

  /**
   * Gets the pixel at the given coordinates.
   *
   * @param point The coordinates of the pixel (vector)
   * @return The pixel at the given coordinates
   */
  public int getPixel(Vector2D point) {
    double x = point.getX0();
    double y = point.getX1();
    if (x < 0 || x >= width || y < 0 || y >= height) {
      return 0;
    }
    return canvas[(int) x][(int) y];
  }

  /**
   * Puts a pixel at the given coordinates.
   *
   * @param point The coordinates of the pixel (vector)
   */
  public void putPixel(Vector2D point) {
    this.transformed = transformCoordsToIndices(point);
    int xindex = (int) transformed.getX0();
    int yindex = (int) transformed.getX1();
    if (xindex < 0 || xindex >= width || yindex < 0 || yindex >= height) {
      return;
    }
    canvas[xindex][yindex] = 1;
    canvasHeatMap[xindex][yindex]++;
  }

  /**
   * Gets the width of the canvas.
   *
   * @return The width of the canvas
   */
  public int getWidth() {
    return width;
  }

  /**
   * Gets the height of the canvas.
   *
   * @return The height of the canvas
   */
  public int getHeight() {
    return height;
  }

  /**
   * Gets the 2D array representing the canvas.
   *
   * @return The 2D array representing the canvas
   */
  public int[][] getCanvasArray() {
    return canvas;
  }

  /**
   * Clears the canvas.
   */
  public void clear() {
    this.canvas = new int[width][height];
    this.canvasHeatMap = new int[width][height];
  }

  /**
   * Transforms a Vector2D point to an index x and y value on our canvas.
   *
   * @param point The coordinates of the pixel (vector)
   * @return The transformed vector
   */
  public Vector2D transformCoordsToIndices(Vector2D point) {
    Matrix2x2 a = new Matrix2x2(0,
            (width - 1) / (minCoords.getX1() - maxCoords.getX1()),
            (height - 1) / (maxCoords.getX0() - minCoords.getX0()),
            0);
    Vector2D b = new Vector2D(
            ((width - 1) * maxCoords.getX1()) / (maxCoords.getX1() - minCoords.getX1()),
            ((height - 1) * minCoords.getX0()) / (minCoords.getX0() - maxCoords.getX0()));

    AffineTransform2D transformCoordsToIndices = new AffineTransform2D(a, b);
    return transformCoordsToIndices.transform(point);
  }

  /**
   * Getter for the field transformed
   * @return Vector2D transformed
   */
  public Vector2D getTransformed() {
    return this.transformed;
  }

  /**
   * Method to zoom into the fractal. Only scales the min and max coordinated of the program.
   * So this is not a real zoom, because the program will redraw the whole fractal.
   * @param scalar 1-10x scalar to zoom
   */
  public void zoom(double scalar) {
    this.minCoords = this.originalMinCoords.multiply(1/scalar);
    this.maxCoords = this.originalMaxCoords.multiply(1/scalar);
  }

  /**
   * Getter for the HeatMap field.
   * @return canvasHeatMap int[][]
   */
  public int[][] getCanvasHeatMap(){
    return this.canvasHeatMap;
  }
}