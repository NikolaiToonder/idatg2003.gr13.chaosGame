package gui;

import chaosgameclasses.ChaosGameDescription;
import java.util.List;
import matrix.Matrix2x2;
import transformations.AffineTransform2D;
import transformations.Transform2D;
import vectors.Vector2D;

public class DescriptionFactory {
  public ChaosGameDescription createAffine2D(String chosenFractal) {
    switch (chosenFractal) {
      case "Barnsley" -> {
        return createBarnsley();
      }
      case "Sierpinski" -> {
        return createSierpinski();
      }
      default -> {
        return null;
      }
    }
  }

  private ChaosGameDescription createSierpinski() {
    Matrix2x2 transformationMatrix1 = new Matrix2x2(.5, 0, 0, .5);
    Matrix2x2 transformationMatrix2 = new Matrix2x2(.5, 0, 0, .5);
    Matrix2x2 transformationMatrix3 = new Matrix2x2(.5, 0, 0, .5);
    Vector2D transformationVector1 = new Vector2D(0, 0);
    Vector2D transformationVector2 = new Vector2D(.25, .5);
    Vector2D transformationVector3 = new Vector2D(.5, 0);

    AffineTransform2D transformation1 = new AffineTransform2D(transformationMatrix1, transformationVector1);
    AffineTransform2D transformation2 = new AffineTransform2D(transformationMatrix2, transformationVector2);
    AffineTransform2D transformation3 = new AffineTransform2D(transformationMatrix3, transformationVector2);
    List<Transform2D> affineTransforms = List.of(transformation1, transformation2, transformation3);
    return new chaosgameclasses.ChaosGameDescription(affineTransforms, new Vector2D(0,0), new Vector2D(1,1));
  }

  private ChaosGameDescription createBarnsley() {
    Matrix2x2 transformationMatrix1 = new Matrix2x2(0, 0, 0, 0.16);
    Matrix2x2 transformationMatrix2 = new Matrix2x2(0.85, 0.04, -0.04, 0.85);
    Matrix2x2 transformationMatrix3 = new Matrix2x2(0.2, -0.26, 0.23, 0.22);
    Matrix2x2 transformationMatrix4 = new Matrix2x2(-0.15, 0.28, 0.26, 0.24);
    Vector2D transformationVector1 = new Vector2D(0, 0);
    Vector2D transformationVector2 = new Vector2D(0, 1.6);
    Vector2D transformationVector3 = new Vector2D(0, 0.44);

    AffineTransform2D transformation1 = new AffineTransform2D(transformationMatrix1, transformationVector1);
    AffineTransform2D transformation2 = new AffineTransform2D(transformationMatrix2, transformationVector2);
    AffineTransform2D transformation3 = new AffineTransform2D(transformationMatrix3, transformationVector2);
    AffineTransform2D transformation4 = new AffineTransform2D(transformationMatrix4, transformationVector3);
    List<Transform2D> affineTransforms = List.of(transformation1, transformation2, transformation3, transformation4);
    return new chaosgameclasses.ChaosGameDescription(affineTransforms, new Vector2D(-2, -2), new Vector2D(10, 10));
  }
}