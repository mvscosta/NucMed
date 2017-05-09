package funcoesNM;


/**
 * Classe Criada para executar a transformada de Fourier e a transformada
 * inversa.
 *
 * @author  Michele Alberton Andrade, Marcus Vinícius da Silva Costa
 * @version <b>1.7</b>
 */

public class Fourier1D {

	private static double TWO_PI = 2 * (java.lang.Math.PI);

	/** Creates a new instance of Math */
	public Fourier1D() {
	}

	/**
	 * Faz a Transformada de Fourier 1D a partir de um array tipo double
	 * 
	 * @param data :
	 *            Vetor informado com os dados da imagem para a transformada.
	 * @return Retorna um vetor do tipo Complexo com os dados da transformada.
	 */
	public static Complexo[] transform(final double data[]) {
		final int N = data.length;
		final double arrayRe[] = new double[N];
		final double arrayIm[] = new double[N];
		int i, j;

		if (!funcoesNM.Funcoes.isPowerOf2(N))
			throw new IllegalArgumentException(
					"The number of samples must be a power of 2.");

		int numBits = numberOfBitsNeeded(N);
		// Simultaneous data copy and bit-reversal ordering into output
		for (i = 0; i < N; i++) {
			j = reverseBits(i, numBits);
			arrayRe[j] = data[i];
		}
		// FFT
		fft(arrayRe, arrayIm, TWO_PI);

		final Complexo answer[] = new Complexo[N];
		for (i = 0; i < N; i++)
			answer[i] = new Complexo(arrayRe[i], arrayIm[i]);
		return answer;
	}

	/**
	 * Executa a transformada de Fourier.
	 */
	public static Complexo[] transform(final Complexo data[]) {
		final int N = data.length;
		final double arrayRe[] = new double[N];
		final double arrayIm[] = new double[N];
		int i, j;

		if (!funcoesNM.Funcoes.isPowerOf2(N))
			throw new IllegalArgumentException(
					"The number of samples must be a power of 2.");

		int numBits = numberOfBitsNeeded(N);
		// Simultaneous data copy and bit-reversal ordering into output
		for (i = 0; i < N; i++) {
			j = reverseBits(i, numBits);
			arrayRe[j] = data[i].real();
			arrayIm[j] = data[i].imag();
		}
		// FFT
		fft(arrayRe, arrayIm, TWO_PI);

		final Complexo answer[] = new Complexo[N];
		for (i = 0; i < N; i++)
			answer[i] = new Complexo(arrayRe[i], arrayIm[i]);
		return answer;
	}

	/**
	 * Faz a Transformada de Fourier 1D INVERSA a partir de um array tipo
	 * Complex
	 */
	public static Complexo[] inverseTransform(final Complexo data[]) {
		final int N = data.length;
		final double arrayRe[] = new double[N];
		final double arrayIm[] = new double[N];
		int i, j;

		if (!funcoesNM.Funcoes.isPowerOf2(N))
			throw new IllegalArgumentException(
					"Data length must be a power of 2.");

		int numBits = numberOfBitsNeeded(N);
		// Simultaneous data copy and bit-reversal ordering into output
		for (i = 0; i < N; i++) {
			j = reverseBits(i, numBits);
			arrayRe[j] = data[i].real();
			arrayIm[j] = data[i].imag();
		}
		// inverse FFT
		fft(arrayRe, arrayIm, -TWO_PI);
		// Normalize
		final Complexo answer[] = new Complexo[N];
		final double denom = N;
		for (i = 0; i < N; i++)
			answer[i] = new Complexo(arrayRe[i] / denom, arrayIm[i] / denom);
		return answer;
	}

	/**
	 * Função da transformada de Fourier.
	 * 
	 * @param twoPi
	 *            TWO_PI for transform, -TWO_PI for inverse transform.
	 */
	public static void fft(double arrayRe[], double arrayIm[],
			final double twoPi) {
		final int N = arrayRe.length;
		int i, j, k, n;
		double deltaAngle, angleRe, angleIm, tmpRe, tmpIm;
		double alpha, beta; // used in recurrence relation

		int blockEnd = 1;
		for (int blockSize = 2; blockSize <= N; blockSize <<= 1) {
			deltaAngle = twoPi / blockSize;
			alpha = java.lang.Math.sin(0.5 * deltaAngle);
			alpha = 2.0 * alpha * alpha;
			beta = java.lang.Math.sin(deltaAngle);
			for (i = 0; i < N; i += blockSize) {
				angleRe = 1.0;
				angleIm = 0.0;
				for (j = i, n = 0; n < blockEnd; j++, n++) {
					k = j + blockEnd;
					tmpRe = angleRe * arrayRe[k] - angleIm * arrayIm[k];
					tmpIm = angleRe * arrayIm[k] + angleIm * arrayRe[k];
					arrayRe[k] = arrayRe[j] - tmpRe;
					arrayIm[k] = arrayIm[j] - tmpIm;
					arrayRe[j] += tmpRe;
					arrayIm[j] += tmpIm;
					tmpRe = alpha * angleRe + beta * angleIm;
					tmpIm = alpha * angleIm - beta * angleRe;
					angleRe -= tmpRe;
					angleIm -= tmpIm;
				}
			}
			blockEnd = blockSize;
		}
	}

	/**
	 * private static boolean isPowerOf2(final int x) { final int BITS_PER_WORD =
	 * 32; for (int i = 1, y = 2; i < BITS_PER_WORD; i++, y <<= 1) { if (x ==
	 * y) return true; } return false; }
	 */
	 // @author Don Cross
	/**
	 * Number of bits needed.
	 */
	private static int numberOfBitsNeeded(final int pwrOf2) {
		if (pwrOf2 < 2)
			throw new IllegalArgumentException();
		for (int i = 0;; i++) {
			if ((pwrOf2 & (1 << i)) > 0)
				return i;
		}
	}

	/**
	 * Reverse bits.
	 * 
	 * @author Don Cross
	 */
	
	/**
	 * 
	 * 
	 */
	private static int reverseBits(int index, final int numBits) {
		int i, rev;
		for (i = rev = 0; i < numBits; i++) {
			rev = (rev << 1) | (index & 1);
			index >>= 1;
		}
		return rev;
	}
}
