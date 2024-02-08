import vectors.Complex;

public class Main {
    public static void main(String[] args) {
      Complex c = new Complex(0.1, 0.4);
      System.out.println(c.sqrt().getX0());
      System.out.println(c.sqrt().getX1());
    }
}
