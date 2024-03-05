import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class ChaosGameFileHandler {

  public List<String> readFromFile(String path) {
    Path filePath = Paths.get(path);
    try (BufferedReader reader = Files.newBufferedReader(filePath)) {
      String transform = reader.readLine().replaceAll("#.*","")
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
  public List<String> readJuliaTransform(BufferedReader reader, String transform) {
    try {
      String lowerLeft = reader.readLine().replaceAll("#.*","")
          .replaceAll("\\s", "");
      String lowerRight = reader.readLine().replaceAll("#.*","")
          .replaceAll("\\s", "");
      String complexNumber = reader.readLine().replaceAll("#.*","")
          .replaceAll("\\s", "");
      return List.of(transform, lowerLeft, lowerRight, complexNumber);
    } catch (Exception e) {
      e.printStackTrace();
      throw new IllegalArgumentException("Error reading file");
    }
  }
  public List<String> readAffineTransform(BufferedReader reader, String transform) {
    try {
      String lowerLeft = reader.readLine().replaceAll("#.*","")
          .replaceAll("\\s", "");
      String lowerRight = reader.readLine().replaceAll("#.*","")
          .replaceAll("\\s", "");
      List<String> values = new java.util.ArrayList<>(List.of(transform, lowerLeft, lowerRight));
      boolean stop = false;
      while (!stop) {
        String line = reader.readLine();
        if (line == null) {
          stop = true;
        } else {
          line = line.replaceAll("#.*","")
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

  public void writeToFile(ChaosGameDescription description, String path) {
    File file = new File(path);
  }

}
