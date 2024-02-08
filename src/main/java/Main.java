import vectors.Complex;

public class Main {
    public static void main(String[] args) {
      Complex b = new Complex(0.3, 0.6);
      Complex a = new Complex(0.4, 0.2);

      Complex c = b.subtract(a);
      System.out.println(c.sqrt().getX0());
      System.out.println(c.sqrt().getX1());

    }
}
