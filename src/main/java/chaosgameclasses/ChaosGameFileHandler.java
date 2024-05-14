package chaosgameclasses;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import matrix.Matrix2x2;

/**
 * Class responsible for reading and writing to files.
 *
 * @version 1.0
 */


public class ChaosGameFileHandler {

  /**
   * Reads the description of the chaos game from a file.
   *
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
    return Collections.emptyList(); //Returns empty list to avoid nullpointerexception
  }

  /**
   * Reads the description of the chaos game from a file, specifically for Julia transformation. The
   * file should contain information about canvas coordinates and transforms.
   *
   * @param reader    The reader to read the file.
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
   * @param reader    The reader to read the file.
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
   * Writes the canvas to a file. The canvas is a 2D array of integers, where 1 represents a pixel
   * and 0 represents no pixel.
   *
   * @param values The canvas to be written to a file.
   */
  public static void writeToFile(int[][] values) {
    try (BufferedWriter writer = Files.newBufferedWriter(Path.of("src/resources/out.txt"))) {
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
    } catch (IOException e) {
      e.printStackTrace();
      throw new IllegalArgumentException("Error writing to file");
    }
  }

  public static void changeLine(String path, List<String> valuesToChange, int lineNumber) {
    try {
      Path filePath = Paths.get(path);
      List<String> lines = Files.readAllLines(filePath);
      String lineToChange = lines.get(lineNumber).replaceAll("#.*", "");
      String[] values = lineToChange.split(",");
      System.out.println(valuesToChange.size());
      if (valuesToChange.size() >3) {
        for (int i = 0; i < values.length - 2; i++) {
          values[i] = valuesToChange.get(i);
        }
      } else {
        int lastIndex = values.length -1;
        values[lastIndex] = valuesToChange.get(1);
        values[lastIndex-1] = valuesToChange.get(0);
      }
      lines.set(lineNumber, String.join(",", values));

      Files.write(filePath, lines);
    } catch (IOException e) {
      e.printStackTrace();
      throw new IllegalArgumentException("Error writing to file");
    }
  }
  public void resetFractals(String path){
    try {
      Path filePath = Paths.get(path);
      if(path.contains("pinski")){
        Path standardFilePath = Paths.get("src/resources/sierpinskiTemplate.txt");
        List<String> lines = Files.readAllLines(standardFilePath);
        Files.write(filePath, lines);
      } else if (path.contains("arnsley")) {
        Path standardFilePath = Paths.get("src/resources/barnsleyTemplate.txt");
        List<String> lines = Files.readAllLines(standardFilePath);
        Files.write(filePath, lines);
      } else if (path.contains("ulia")) {
        Path standardFilePath = Paths.get("src/resources/juliaTemplate.txt");
        List<String> lines = Files.readAllLines(standardFilePath);
        Files.write(filePath, lines);
      }
    } catch (IOException e) {
      e.printStackTrace();
      throw new IllegalArgumentException("Error writing to file");
    }
  }
}