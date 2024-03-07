package chaosGameClasses;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Class responsible for reading and writing to files.
 *
 * @version 1.0
 */


public class ChaosGameFileHandler {

  /**
   * Reads the description of the chaos game from a file.
   * @param path The path to the file containing the description.
   * @return A list of strings containing the description of the chaos game.
   */

  public List<String> readFromFile(String path) {
    Path filePath = Paths.get(path);
    try (BufferedReader reader = Files.newBufferedReader(filePath)) {
      String transform = reader.readLine().replaceAll("#.*", "")
          .replaceAll("\\s", "");

      if (transform.contains("Affine2D")) {
        return readAffineTransform(reader, transform);
      } else if (transform.contains("Julia")) {
        return readJuliaTransform(reader, transform);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new IllegalArgumentException("Error reading file");
    }
    return null; // Consider returning an empty list instead of null to avoid potential NullPointerExceptions in the caller
  }

  /**
   * Reads the description of the chaos game from a file, specifically for Julia transformation.
   * The file should contain information about canvas coordinates and transforms.
   *
   * @param reader The reader to read the file.
   * @param transform The type of transform to be used.
   * @return A list of strings containing the description of the chaos game.
   * @throws IllegalArgumentException If an error occurs while reading the file.
   */

  public List<String> readJuliaTransform(BufferedReader reader, String transform) {
    try {
      String lowerLeft = reader.readLine().replaceAll("#.*", "")
          .replaceAll("\\s", "");
      String lowerRight = reader.readLine().replaceAll("#.*", "")
          .replaceAll("\\s", "");
      String complexNumber = reader.readLine().replaceAll("#.*", "")
          .replaceAll("\\s", "");
      return List.of(transform, lowerLeft, lowerRight, complexNumber);
    } catch (Exception e) {
      e.printStackTrace();
      throw new IllegalArgumentException("Error reading file");
    }
  }

  /**
   * Reads the description of the chaos game from a file, specifically for Affine transformation.
   * The file should contain information about canvas coordinates and transforms.
   *
   * @param reader The reader to read the file.
   * @param transform The type of transform to be used.
   * @return A list of strings containing the description of the chaos game.
   * @throws IllegalArgumentException If an error occurs while reading the file.
   */

  public List<String> readAffineTransform(BufferedReader reader, String transform) {
    try {
      String lowerLeft = reader.readLine().replaceAll("#.*", "")
          .replaceAll("\\s", "");
      String lowerRight = reader.readLine().replaceAll("#.*", "")
          .replaceAll("\\s", "");
      List<String> values = new java.util.ArrayList<>(List.of(transform, lowerLeft, lowerRight));
      boolean stop = false;
      while (!stop) {
        String line = reader.readLine();
        if (line == null) {
          stop = true;
        } else {
          line = line.replaceAll("#.*", "")
              .replaceAll("\\s", "");
          values.add(line);
        }
      }
      return values;
    } catch (Exception e) {
      e.printStackTrace();
      throw new IllegalArgumentException("Error reading file");
    }
  }

  /**
   * Writes the canvas to a file. The canvas is a 2D array of integers, where 1 represents a pixel and 0 represents no pixel.
   *
   * @param values The canvas to be written to a file.
   */
  public static void writeToFile(int[][] values) {
    try (BufferedWriter writer = Files.newBufferedWriter(Path.of("out.txt"))) {
      for (int[] row : values) {
        for (int column : row) {
          if (column == 1) {
            writer.write("*");
          } else {
            writer.write(" ");
          }
        }
        writer.newLine();
      }
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
      throw new IllegalArgumentException("Error writing to file");
    }
  }
}