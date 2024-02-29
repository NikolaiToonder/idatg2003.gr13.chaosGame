import javafx.scene.transform.Affine;
import vectors.Vector2D;

public class ChaosCanvas {

private int[][] canvas;
private int width;
private int height;
private Vector2D minCoords;
private Vector2D maxCoords;
private Affine transformCoordsToIndices;

public ChaosCanvas(int width, int height, Vector2D minCoords, Vector2D maxCoords) {

  this.width = width;
  this.height = height;
  this.minCoords = minCoords;
  this.maxCoords = maxCoords;
}
  /*
  this.transformCoordsToIndices = new Affine();
  this.transformCoordsToIndices.appendScale(width / (maxCoords.getX0() - minCoords.getX0()),
      height / (maxCoords.getX1() - minCoords.getX1()));
  this.transformCoordsToIndices.appendTranslation(-minCoords.getX0(), -minCoords.getX1());
  */

private int getPixel(Vector2D point) {

}

private void putPixel(Vector2D point) {

}

private int[][] getCanvasArray() {
  return canvas;
}

private void clear() {
  canvas = new int[width][height];
}

}
