package funcoesNM;

/**
 * Classe criada para o tratamento de números imaginários, devido a função de
 * transformada de Fourier.
 * 
 * @author  Michele Alberton Andrade, Marcus Vinícius da Silva Costa.
 * @version <b>1.3</b>
 */

public class Complexo {

	private double re;

	private double im;

	/**
   * Constructs the complex number x+iy.
   * 
   * @param x the real value of a complex number.
   * @param y the imaginary value of a complex number.
   */
	public Complexo(final double x, final double y) {
		re = x;
		im = y;
	}

	/**
   * @return retorna uma string com os valores do numero imaginario
   */
	public String toString() {
		return toString(re, im);
	}

	/**
   * Returns a string representing the value of this complex number.
   */
	public static String toString(double real, double imag) {
		final StringBuffer buf = new StringBuffer(10);
		buf.append(real);
		if (imag >= 0.0) buf.append("+");
		buf.append(imag);
		buf.append("i");
		return buf.toString();
	}

	/**
   * Returns true if either the real or imaginary part is NaN.
   */
	public boolean isNaN() {
		return (re == Double.NaN) || (im == Double.NaN);
	}

	/**
   * Returns the real part of this complex number.
   */
	public double real() {
		return re;
	}

	/**
   * Returns the imaginary part of this complex number.
   */
	public double imag() {
		return im;
	}

	// ============
	// OPERATIONS
	// ============

	/**
   * Returns the negative of this complex number.
   */
	public Complexo negate() {
		return new Complexo(-re, -im);
	}

	/**
   * Returns the inverse of this complex number.
   */
	public Complexo inverse() {
		double denominator, real, imag;
		if (Math.abs(re) < Math.abs(im)) {
			real = re / im;
			imag = -1.0;
			denominator = re * real + im;
		} else {
			real = 1.0;
			imag = -im / re;
			denominator = re - im * imag;
		}
		return new Complexo(real / denominator, imag / denominator);
	}

	/**
   * Returns the complex conjugate of this complex number.
   */
	public Complexo conjugate() {
		return new Complexo(re, -im);
	}

	// ADDITION

	/**
   * Returns the addition of this complex number and another.
   * 
   * @param z a complex number.
   */
	public Complexo add(final Complexo z) {
		return new Complexo(re + z.re, im + z.im);
	}

	/**
   * Returns the addition of this complex number with a real part.
   * 
   * @param real a real part.
   */
	public Complexo addReal(final double real) {
		return new Complexo(re + real, im);
	}

	/**
   * Returns the addition of this complex number with an imaginary part.
   * 
   * @param imag an imaginary part.
   */
	public Complexo addImag(final double imag) {
		return new Complexo(re, im + imag);
	}

	// SUBTRACTION

	/**
   * Returns the subtraction of this complex number by another.
   * 
   * @param z a complex number.
   */
	public Complexo subtract(final Complexo z) {
		return new Complexo(re - z.re, im - z.im);
	}

	/**
   * Returns the subtraction of this complex number by a real part.
   * 
   * @param real a real part.
   */
	public Complexo subtractReal(final double real) {
		return new Complexo(re - real, im);
	}

	/**
   * Returns the subtraction of this complex number by an imaginary part.
   * 
   * @param imag an imaginary part.
   */
	public Complexo subtractImag(final double imag) {
		return new Complexo(re, im - imag);
	}

	// MULTIPLICATION

	/**
   * Returns the multiplication of this Complexo number and another.
   * 
   * @param z a complex number.
   */
	public Complexo multiply(final Complexo z) {
		return new Complexo(re * z.re - im * z.im, re * z.im + im * z.re);
	}

	/**
   * Returns the multiplication of this complex number by a scalar.
   * 
   * @param x a real number.
   */
	public Complexo multiply(final double x) {
		return new Complexo(x * re, x * im);
	}

	// DIVISION

	/**
   * Returns the division of this complex number by another.
   * 
   * @param z a complex number.
   * @exception ArithmeticException If divide by zero.
   */
	public Complexo divide(final Complexo z) {
		double denominator, real, imag, a;
		if (Math.abs(z.re) < Math.abs(z.im)) {
			a = z.re / z.im;
			denominator = z.re * a + z.im;
			real = re * a + im;
			imag = im * a - re;
		} else {
			a = z.im / z.re;
			denominator = z.re + z.im * a;
			real = re + im * a;
			imag = im - re * a;
		}
		return new Complexo(real / denominator, imag / denominator);
	}

	/**
   * Returns the division of this complex number by a scalar.
   * 
   * @param x a real number.
   * @exception ArithmeticException If divide by zero.
   */
	public Complexo divide(final double x) {
		return new Complexo(re / x, im / x);
	}

	/**
   * Returns the square of this complex number.
   */
	public Complexo sqr() {
		return new Complexo(re * re - im * im, 2.0 * re * im);
	}

	// ===========
	// FUNCTIONS
	// ===========

	// EXP

	/**
   * Returns the exponential number e (2.718...) raised to the power of a
   * complex number.
   * 
   * @param z a complex number.
   */
	public static Complexo exp(final Complexo z) {
		return new Complexo(Math.exp(z.re) * Math.cos(z.im), Math.exp(z.re) * Math.sin(z.im));
	}

	/*
   * public static void main(String args[]) { Complexo mi = new Complexo(5,7);
   * String str = mi.toString(); System.out.println(str); }
   */
}
