package math.transformations;

import math.vectors.Vector2D;

/**
 * Interface for 2D transformations.
 */
public interface Transform2D {
  Vector2D transform(Vector2D point);
}