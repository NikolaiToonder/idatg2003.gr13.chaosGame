import transformations.JuliaTransform;
import vectors.Complex;
import vectors.Vector2D;

/**
 * Main class for the program.
 */
public class Main {
    public static void main(String[] args) {
        /* Test the JuliaTransform class */
        /* Will not be a part of the main program */
        Complex z = new Complex(0.4, 0.2);
        JuliaTransform transform = new JuliaTransform(new Complex(0.3, 0.6), 1);
        Vector2D result = transform.transform(z);
        System.out.println(result.getX0());
        System.out.println(result.getX1());
    }
}
