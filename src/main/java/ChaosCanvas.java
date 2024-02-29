import javafx.scene.transform.Affine;
import transformations.AffineTransform2D;
import vectors.Vector2D;

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
}

private int getPixel(Vector2D point) {
  return canvas[(int) point.getX0()][(int) point.getX1()];
}

private void putPixel(Vector2D point) {
  canvas[(int) point.getX0()][(int) point.getX1()] = 1;
}

private int[][] getCanvasArray() {
  return canvas;
}

private void clear() {
  canvas = new int[width][height];
}

}
