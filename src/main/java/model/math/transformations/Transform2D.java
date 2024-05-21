package model.math.transformations;

import model.math.vectors.Vector2D;

/**
 * Interface for 2D transformations.
 */
public interface Transform2D {
  Vector2D transform(Vector2D point);
}