package transformations;

import java.util.List;
import matrix.Matrix2x2;
import vectors.Vector2D;

/**
 * Interface for 2D transformations.
 */
public interface Transform2D {
  Vector2D transform(Vector2D point);
}
