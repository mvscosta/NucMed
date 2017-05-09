package nucMed;

/**
 *  classe Projetor
 *
 *  criado dia 15 de março de 2006
 *
 */

import ij.ImagePlus;
import ij.gui.NewImage;
import ij.process.ImageProcessor;

/**
 * Classe Utilizada no algoritmo MLEM.
 *
 * @author Michele Alberton Andrade, Marcus Vinicius da Silva Costa
 * @version <b>1.3</b>
 */

public class Projetor {

	public static ImagePlus sinograma;

	public ImageProcessor p_sino;

	public ImageProcessor p_img;

	public ImageProcessor p_mapa;

	public static float imgpixels[];

	public static float sinopixels[];

	public static float mapa[];

	public static int nproj, dim, xmax;

	public static int view, view90, view180, view270;

	/**
	 * Construtor da classe para projeção com mapa de atenuação.
	 * 
	 * @param ip
	 *            imagem a ser projetada.
	 * @param ip_mapa
	 *            imagem do mapa de atenuação
	 * @param n_projecoes
	 *            quantidade de projeções informadas no MLEM.
	 */
	public Projetor(ImageProcessor ip, ImageProcessor ip_mapa, int n_projecoes) {

		p_img = ip;
		p_mapa = ip_mapa;

		nproj = n_projecoes;
		dim = p_img.getWidth();
		xmax = ((dim + 1) - 2) / 2;

		Projetor.sinograma = NewImage.createFloatImage("Sinograma", dim, nproj,
				1, NewImage.FILL_BLACK);
		this.p_sino = sinograma.getProcessor();

		imgpixels = (float[]) this.p_img.getPixels();
		sinopixels = (float[]) this.p_sino.getPixels();
		mapa = (float[]) p_mapa.getPixels();

		view90 = (int) (0.25 * nproj);
		view180 = (int) (0.5 * nproj);
		view270 = (int) (0.75 * nproj);
	}

	/**
	 * Construtor da classe para projeção sem mapa de atenuação.
	 * 
	 * @param ip
	 *            imagem a ser projetada.
	 * @param n_projecoes
	 *            quantidade de projeções informadas no MLEM.
	 */
	public Projetor(ImageProcessor ip, int n_projecoes) {

		p_img = ip;

		nproj = n_projecoes;
		dim = p_img.getWidth();
		xmax = ((dim + 1) - 2) / 2;

		Projetor.sinograma = NewImage.createFloatImage("Sinograma", dim, nproj,
				1, NewImage.FILL_BLACK);
		this.p_sino = sinograma.getProcessor();

		imgpixels = (float[]) p_img.getPixels();
		sinopixels = (float[]) this.p_sino.getPixels();

		view90 = (int) (0.25 * nproj);
		view180 = (int) (0.5 * nproj);
		view270 = (int) (0.75 * nproj);

	}

	/**
	 * Executa a projeção de acordo com os parâmetros informados anteriormente.
	 * 
	 * @param comMapa
	 *            Deve se informar se a projeção utiliza mapa de atenuação
	 * @return retorna um ImagePlus com a imagem projetada.
	 */
	public ImageProcessor projetar(boolean comMapa) {

		if (comMapa == false) {

			view0(imgpixels);
			quadrante1(imgpixels);
			view90(imgpixels);
			quadrante2(imgpixels);
			view180(imgpixels);
			quadrante3(imgpixels);
			view270(imgpixels);
			quadrante4(imgpixels);
		}

		if (comMapa == true) {

			view0(mapa, imgpixels);
			quadrante1(mapa, imgpixels);
			view90(mapa, imgpixels);
			quadrante2(mapa, imgpixels);
			view180(mapa, imgpixels);
			quadrante3(mapa, imgpixels);
			view270(mapa, imgpixels);
			quadrante4(mapa, imgpixels);
		}

		p_sino.resetMinAndMax();
		return p_sino;
	}

	/**
	 * Método para fazer view zero - sem correção de atenuação
	 * 
	 * @param imgpixels
	 *            array float com os valores da imagem
	 */
	public void view0(float[] imgpixels) {

		int s, ymax;
		int xmax = (dim - 2) / 2;
		int view = 0;
		double x;
		for (x = -xmax + 0.625; x < xmax - 0.625; x++) {
			// for( x= -xmax; x < xmax; x++) {

			ymax = (int) (Math.sqrt(Math.pow(xmax, 2) - Math.pow(x, 2)) + 0.5);
			for (int y = -ymax; y <= ymax; y++) {

				s = (int) Math.floor(x + 0.5);
				int p1 = mudaCoord(view, s, true);
				int p2 = mudaCoord(y, s, false);
				sinopixels[p1] += imgpixels[p2];
				// Sinograma[view][x] += img[y+xmax][x];
			}
		}
	}

	/**
	 * Método para fazer view 90 - sem correção de atenuação
	 * 
	 * @param imgpixels
	 *            array float com os valores da imagem
	 */
	public void view90(float[] imgpixels) {

		int s, fovmax;
		int xmax = (dim - 2) / 2;
		double x;
		for (x = -xmax + 0.625; x < xmax - 0.625; x++) {
			// for( x= -xmax; x < xmax; x++) {

			fovmax = (int) (Math.sqrt(Math.pow(xmax, 2) - Math.pow(x, 2)) + 0.5);
			for (int y = -fovmax; y <= +fovmax; y++) {

				s = (int) Math.floor(x + 0.5);
				int p1 = mudaCoord(view90, s, true);
				int p2 = mudaCoord(s, -y, false);
				sinopixels[p1] += imgpixels[p2];
			}
		}
	}

	/**
	 * Método para fazer view 180 - sem correção de atenuação
	 * 
	 * @param imgpixels
	 *            array float com os valores da imagem
	 */
	public static void view180(float[] imgpixels) {

		int s, ymax;
		int xmax = (dim - 2) / 2;
		double x;
		for (x = -xmax + 0.625; x < xmax - 0.625; x++) {
			// for( x= -xmax; x < xmax; x++) {

			ymax = (int) (Math.sqrt(Math.pow(xmax, 2) - Math.pow(x, 2)) + 0.5);
			for (int y = ymax; y >= -ymax; y--) {

				s = (int) Math.floor(x + 0.5);
				int p1 = mudaCoord(view180, s, true);
				int p2 = mudaCoord(y, -s, false);

				sinopixels[p1] += imgpixels[p2];
				// Sinograma[view180][x] += W*img[y+xmax][-x+2*xmax]; ?????
			}
		}
	}

	/**
	 * Método para fazer view 270 - sem correção de atenuação
	 * 
	 * @param imgpixels
	 *            array float com os valores da imagem
	 */
	public static void view270(float[] imgpixels) {

		int s, ymax;
		int xmax = (dim - 2) / 2;
		double x;
		for (x = -xmax + 0.625; x < xmax - 0.625; x++) {
			// for( x= -xmax; x < xmax; x++) {

			ymax = (int) (Math.sqrt(Math.pow(xmax, 2) - Math.pow(x, 2)) + 0.5);
			for (int y = -ymax; y <= ymax; y++) {

				s = (int) Math.floor(x + 0.5);
				int p1 = mudaCoord(view270, s, true);
				int p2 = mudaCoord(-s, y, false);
				sinopixels[p1] += imgpixels[p2];
				// Sinograma[view270][x] += W*img[-x+2*xmax][y+xmax];
			}
		}
	}

	/**
	 * Método para Quadrante um - view1 até view89 - sem correção de atenuação
	 * 
	 * @param imgpixels
	 *            array float com os valores da imagem
	 */
	public static void quadrante1(float[] imgpixels) {

		float phi, cphi, sphi;
		float x, x1, x2, y1, y2;
		float dx, dy, i_dx, i_dy;
		float min_x, max_x, min_y, max_y;
		float ax[], ay[], a;
		int X, Y;
		int p1, p2;

		for (int view = 1; view < view90; view++) {

			phi = (float) ((view * 2 * Math.PI) / (nproj));
			sphi = (float) Math.sin(phi);
			cphi = (float) Math.cos(phi);

			for (x = (float) (-xmax); x <= (float) (xmax); x++) {

				float fov = (float) Math.sqrt(Math.pow(xmax, 2)
						- Math.pow(x, 2));

				// Pontos de interesecao da linha do detetor com o FOV: (x1,y1)
				// ,
				// (x2,y2)
				x1 = (x * cphi + sphi * fov);
				y1 = (x * sphi - cphi * fov);

				x2 = (x * cphi - sphi * fov);
				y2 = (x * sphi + cphi * fov);

				dx = (x1 - x2);
				i_dx = 1 / dx;
				dy = (y2 - y1);
				i_dy = 1 / dy;

				// D = (float) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));

				min_x = (float) (Math.floor(x1 + 0.5) + 0.5);
				max_x = (float) (Math.floor(x2 - 0.5) + 0.5);
				min_y = (float) (Math.floor(y1 - 0.5) + 0.5);
				max_y = (float) (Math.floor(y2 + 0.5) + 0.5);

				X = (int) (min_x - 0.5);
				Y = (int) (min_y + 0.5);

				int kmax = (int) (1 + min_x - max_x);
				ax = new float[kmax + 10];
				ax[0] = (x1 - min_x) * i_dx;

				int k = 1;
				while (k <= kmax) {
					ax[k] = ax[k - 1] + i_dx; // mi.write(ax[k]+" ");
					k++;
				}

				kmax = (int) (1 + max_y - min_y);
				ay = new float[kmax + 10];
				ay[0] = (min_y - y1) * i_dy;

				k = 1;
				kmax = (int) (1 + max_y - min_y);

				while (k <= kmax) {
					ay[k] = ay[k - 1] + i_dy; // mi.write(ay[k]+" ");
					k++;
				}

				if (ax[0] > ay[0])
					a = ax[0];
				else
					a = ay[0];

				int ix, iy;
				ix = iy = 1;

				while (a < 1) {

					if (ax[ix] < ay[iy]) {

						a = ax[ix++];

						p1 = mudaCoord(view, (int) Math.floor(x), true);
						p2 = mudaCoord(Y, X, false);
						sinopixels[p1] += imgpixels[p2];

						X--;
					} else {
						a = ay[iy++];

						p1 = mudaCoord(view, (int) Math.floor(x), true);
						p2 = mudaCoord(Y, X, false);
						sinopixels[p1] += imgpixels[p2];
						Y++;
					}
				}

			}
		}

	}

	/**
	 * Método para Quadrante dois - view90 até view179 - sem correção de
	 * atenuação
	 * 
	 * @param imgpixels
	 *            array float com os valores da imagem
	 */
	public static void quadrante2(float[] imgpixels) {

		float phi, cphi, sphi;
		float x, x1, x2, y1, y2;
		float dx, dy, i_dx, i_dy;
		float min_x, max_x, min_y, max_y;
		float ax[], ay[], a;
		int X, Y;
		int p1, p2;

		for (int view = (view90 + 1); view < view180; view++) {

			phi = (float) ((view * 2 * Math.PI) / (nproj));
			sphi = (float) Math.sin(phi);
			cphi = (float) Math.cos(phi);

			for (x = (float) (-xmax); x <= (float) (xmax); x++) {

				float fov = (float) Math.sqrt(Math.pow(xmax, 2)
						- Math.pow(x, 2));

				// Pontos de interesecao da linha do detetor com o FOV: (x1,y1)
				// ,
				// (x2,y2)
				x1 = (x * cphi + sphi * fov);
				y1 = (x * sphi - cphi * fov);

				x2 = (x * cphi - sphi * fov);
				y2 = (x * sphi + cphi * fov);

				dx = (x1 - x2);
				i_dx = 1 / dx;
				dy = (y1 - y2);
				i_dy = 1 / dy;

				// D = (float) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));

				min_x = (float) (Math.floor(x1 + 0.5) + 0.5);
				max_x = (float) (Math.floor(x2 - 0.5) + 0.5);
				min_y = (float) (Math.floor(y1 + 0.5) + 0.5);
				max_y = (float) (Math.floor(y2 - 0.5) + 0.5);

				X = (int) (min_x - 0.5);
				Y = (int) (min_y - 0.5);

				int kmax = (int) (1 + min_x - max_x);
				ax = new float[kmax + 10];
				ax[0] = (x1 - min_x) * i_dx;

				int k = 1;
				while (k <= kmax) {
					ax[k] = ax[k - 1] + i_dx; // mi.write(ax[k]+" ");
					k++;
				}

				kmax = (int) (1 + min_y - max_y);
				ay = new float[kmax + 10];
				ay[0] = (-min_y + y1) * i_dy;

				k = 1;
				while (k <= kmax) {
					ay[k] = ay[k - 1] + i_dy; // mi.write(ay[k]+" ");
					k++;
				}

				if (ax[0] > ay[0])
					a = ax[0];
				else
					a = ay[0];

				int ix, iy;
				ix = iy = 1;

				while (a < 1) {

					if (ax[ix] < ay[iy]) {
						// IJ.log("sino["+view+"]["+(int)(x)+"] =
						// img["+Y+"]["+X+"]");

						p1 = mudaCoord(view, (int) Math.floor(x), true);
						p2 = mudaCoord(Y, X, false);
						sinopixels[p1] += imgpixels[p2];

						a = ax[ix++];

						X--;
					} else {
						// IJ.log("sino["+view+"]["+(int)(x)+"] =
						// img["+Y+"]["+X+"]");

						p1 = mudaCoord(view, (int) Math.floor(x), true);
						p2 = mudaCoord(Y, X, false);
						sinopixels[p1] += imgpixels[p2];
						a = ay[iy++];

						Y--;
					}
				}

			}
		}

	}

	/**
	 * Método para Quadrante três - view180 até view269 - sem correção de
	 * atenuação
	 * 
	 * @param imgpixels
	 *            array float com os valores da imagem
	 */
	public static void quadrante3(float[] imgpixels) {

		float phi, cphi, sphi;
		float x, x1, x2, y1, y2;
		float dx, dy, i_dx, i_dy;
		float min_x, max_x, min_y, max_y;
		float ax[], ay[], a;
		int X, Y;
		int p1, p2;

		for (int view = (view180 + 1); view < view270; view++) {

			phi = (float) ((view * 2 * Math.PI) / (nproj));
			sphi = (float) Math.sin(phi);
			cphi = (float) Math.cos(phi);

			for (x = (float) (-xmax); x <= (float) (xmax); x++) {

				float fov = (float) Math.sqrt(Math.pow(xmax, 2)
						- Math.pow(x, 2));

				// Pontos de interesecao da linha do detetor com o FOV: (x1,y1)
				// ,
				// (x2,y2)
				x1 = (x * cphi + sphi * fov);
				y1 = (x * sphi - cphi * fov);

				x2 = (x * cphi - sphi * fov);
				y2 = (x * sphi + cphi * fov);

				dx = (x2 - x1);
				i_dx = 1 / dx;
				dy = (y1 - y2);
				i_dy = 1 / dy;

				// D = (float) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));

				min_x = (float) (Math.floor(x1 - 0.5) + 0.5);
				max_x = (float) (Math.floor(x2 + 0.5) + 0.5);
				min_y = (float) (Math.floor(y1 + 0.5) + 0.5);
				max_y = (float) (Math.floor(y2 - 0.5) + 0.5);

				X = (int) (min_x + 0.5);
				Y = (int) (min_y - 0.5);

				int kmax = (int) (1 + max_x - min_x);
				ax = new float[kmax + 10];
				ax[0] = (-x1 + min_x) * i_dx;

				int k = 1;
				while (k <= kmax) {
					ax[k] = ax[k - 1] + i_dx; // mi.write(ax[k]+" ");
					k++;
				}

				kmax = (int) (1 + min_y - max_y);
				ay = new float[kmax + 10];
				ay[0] = (-min_y + y1) * i_dy;

				k = 1;
				while (k <= kmax) {
					ay[k] = ay[k - 1] + i_dy; // mi.write(ay[k]+" ");
					k++;
				}

				if (ax[0] > ay[0])
					a = ax[0];
				else
					a = ay[0];

				int ix, iy;
				ix = iy = 1;

				while (a < 1) {

					if (ax[ix] < ay[iy]) {

						a = ax[ix++];

						p1 = mudaCoord(view, (int) Math.floor(x), true);
						p2 = mudaCoord(Y, X, false);
						sinopixels[p1] += imgpixels[p2];

						X++;
					} else {
						a = ay[iy++];

						p1 = mudaCoord(view, (int) Math.floor(x), true);
						p2 = mudaCoord(Y, X, false);
						sinopixels[p1] += imgpixels[p2];

						Y--;
					}
				}

			}
		}

	}

	/**
	 * Método para Quadrante quatro - view280 até view359 - sem correção de
	 * atenuação
	 * 
	 * @param imgpixels
	 *            array float com os valores da imagem
	 */
	public static void quadrante4(float[] imgpixels) {

		float phi, cphi, sphi;
		float x, x1, x2, y1, y2;
		float dx, dy, i_dx, i_dy;
		float min_x, max_x, min_y, max_y;
		float ax[], ay[], a;
		int X, Y;
		int p1, p2;

		for (int view = (view270 + 1); view < nproj; view++) {

			phi = (float) ((view * 2 * Math.PI) / (nproj));
			sphi = (float) Math.sin(phi);
			cphi = (float) Math.cos(phi);

			for (x = (float) (-xmax); x <= (float) (xmax); x++) {

				float fov = (float) Math.sqrt(Math.pow(xmax, 2)
						- Math.pow(x, 2));

				// Pontos de interesecao da linha do detetor com o FOV: (x1,y1)
				// ,
				// (x2,y2)
				x1 = (x * cphi + sphi * fov);
				y1 = (x * sphi - cphi * fov);

				x2 = (x * cphi - sphi * fov);
				y2 = (x * sphi + cphi * fov);

				dx = (x2 - x1);
				i_dx = 1 / dx;
				dy = (y2 - y1);
				i_dy = 1 / dy;

				// D = (float) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));

				min_x = (float) (Math.floor(x1 - 0.5) + 0.5);
				max_x = (float) (Math.floor(x2 + 0.5) + 0.5);
				min_y = (float) (Math.floor(y1 - 0.5) + 0.5);
				max_y = (float) (Math.floor(y2 + 0.5) + 0.5);

				X = (int) (min_x + 0.5);
				Y = (int) (min_y + 0.5);

				int kmax = (int) (1 + max_x - min_x);
				ax = new float[kmax + 10];
				ax[0] = (-x1 + min_x) * i_dx;

				int k = 1;
				while (k <= kmax) {
					ax[k] = ax[k - 1] + i_dx; // mi.write(ax[k]+" ");
					k++;
				}

				kmax = (int) (1 + max_y - min_y);
				ay = new float[kmax + 10];
				ay[0] = (min_y - y1) * i_dy;

				k = 1;
				while (k <= kmax) {
					ay[k] = ay[k - 1] + i_dy; // mi.write(ay[k]+" ");
					k++;
				}

				if (ax[0] > ay[0])
					a = ax[0];
				else
					a = ay[0];

				int ix, iy;
				ix = iy = 1;

				while (a < 1) {

					if (ax[ix] < ay[iy]) {

						p1 = mudaCoord(view, (int) Math.floor(x), true);
						p2 = mudaCoord(Y, X, false);
						sinopixels[p1] += imgpixels[p2];

						a = ax[ix++];

						X++;

					} else {

						p1 = mudaCoord(view, (int) Math.floor(x), true);
						p2 = mudaCoord(Y, X, false);
						sinopixels[p1] += imgpixels[p2];

						a = ay[iy++];

						Y++;
					}

				} // fim do while

			}
		}

	}

	/**
	 * Metodo para fazer view zero com correcão de atenuação
	 * 
	 * @param mapa
	 *            array float com os valores do mapa de atenuação
	 * @param imgpixels
	 *            array float com os valores da imagem
	 */
	public void view0(float[] mapa, float[] imgpixels) {

		int s, ymax;
		int view = 0;
		float A_ij, mi, soma_mi;
		float W;
		double x;
		// for( x = -xmax; x < xmax; x++) {
		for (x = -xmax + 0.625; x < xmax - 0.625; x += 1) {

			ymax = (int) Math.sqrt(Math.pow(xmax, 2) - Math.pow((x), 2) + 0.5);
			soma_mi = 0;
			for (int y = -ymax; y <= ymax; y++) {

				s = (int) Math.floor(x + 0.5);
				int p1 = mudaCoord(view, s, true);
				int p2 = mudaCoord(y, s, false);
				mi = mapa[p2];
				if (mi == 0) {
					A_ij = (float) Math.exp(-soma_mi);
					W = A_ij;
				} else {
					A_ij = (float) Math.exp(-soma_mi);
					W = (float) ((A_ij / mi) * (1 - Math.exp(-mi)));
					soma_mi += mi;
				}
				sinopixels[p1] += W * imgpixels[p2];
			}
		}
	}

	/**
	 * Metodo para fazer view 90 com correcão de atenuação
	 * 
	 * @param mapa
	 *            array float com os valores do mapa de atenuação
	 * @param imgpixels
	 *            array float com os valores da imagem
	 */
	public static void view90(float mapa[], float imgpixels[]) {

		int s, fovmax;
		float A_ij, mi, soma_mi;
		float W;
		double x;
		// for( x= -xmax; x < xmax; x++) {
		for (x = -xmax + 0.625; x < xmax - 0.625; x += 1) {

			fovmax = (int) Math.sqrt(Math.pow(xmax, 2)
					- Math.pow((x - xmax), 2) + 0.5);
			soma_mi = 0;
			for (int y = -fovmax; y <= fovmax; y++) {

				s = (int) Math.floor(x + 0.5);
				int p1 = mudaCoord(view90, s, true);
				int p2 = mudaCoord(s, -y, false);
				mi = mapa[p2];
				if (mi == 0) {
					A_ij = (float) Math.exp(-soma_mi);
					W = A_ij;
				} else {
					A_ij = (float) Math.exp(-soma_mi);
					W = (float) ((A_ij / mi) * (1 - Math.exp(-mi)));
					soma_mi += mi;
				}
				sinopixels[p1] += W * imgpixels[p2];
			}
		}
	}

	/**
	 * Metodo para fazer view 180 com correcão de atenuação
	 * 
	 * @param mapa
	 *            array float com os valores do mapa de atenuação
	 * @param imgpixels
	 *            array float com os valores da imagem
	 */
	public static void view180(float mapa[], float[] imgpixels) {

		int s, ymax;
		float A_ij, mi, soma_mi;
		float W;
		double x;
		// for( x= -xmax; x < xmax; x++) {
		for (x = -xmax + 0.625; x < xmax - 0.625; x += 1) {

			ymax = (int) Math.sqrt((Math.pow(xmax, 2) - Math.pow(x, 2) + 0.5));
			soma_mi = 0;
			for (int y = ymax; y >= -ymax; y--) {

				s = (int) Math.floor(x + 0.5);
				int p1 = mudaCoord(view180, s, true);
				int p2 = mudaCoord(y, -s, false);
				mi = mapa[p2];
				if (mi == 0) {
					A_ij = (float) Math.exp(-soma_mi);
					W = A_ij;
				} else {
					A_ij = (float) Math.exp(-soma_mi);
					W = (float) ((A_ij / mi) * (1 - Math.exp(-mi)));
					soma_mi += mi;
				}
				sinopixels[p1] += W * imgpixels[p2];
			}
		}
	}

	/**
	 * Metodo para fazer view 270 com correcão de atenuação
	 * 
	 * @param mapa
	 *            array float com os valores do mapa de atenuação
	 * @param img
	 *            array float com os valores da imagem
	 */
	public static void view270(float mapa[], float img[]) {

		int s, ymax;
		float A_ij, mi, soma_mi;
		float W;
		double x;
		// for( x= -xmax; x < xmax; x++) {
		for (x = -xmax + 0.625; x < xmax - 0.625; x++) {

			soma_mi = 0;
			ymax = (int) (Math.sqrt(Math.pow(xmax, 2) - Math.pow(x, 2)) + 0.5);
			for (int y = -ymax; y <= ymax; y++) {

				s = (int) Math.floor(x + 0.5);
				int p1 = mudaCoord(view270, s, true);
				int p2 = mudaCoord(-s, y, false);

				mi = mapa[p2];
				if (mi == 0) {
					A_ij = (float) Math.exp(-soma_mi);
					W = A_ij;
				} else {
					A_ij = (float) Math.exp(-soma_mi);
					W = (float) ((A_ij / mi) * (1 - Math.exp(-mi)));
					soma_mi += mi;
				}
				sinopixels[p1] += W * imgpixels[p2];
			}
		}
	}

	/**
	 * Método para Quadrante um - view1 até view89 - com correção de atenuação
	 * 
	 * @param mapa
	 *            array float com os valores da imagem de atenuação
	 * @param imgpixels
	 *            array float com os valores da imagem
	 */
	public static void quadrante1(float[] mapa, float[] imgpixels) {

		float phi, cphi, sphi;
		float x, x1, x2, y1, y2;
		float dx, dy, D, i_dx, i_dy;
		float min_x, max_x, min_y, max_y;
		float ax[], ay[], a;
		float mi, soma_mi_x, d, W, A;
		int X, Y;
		int p1, p2;

		for (view = 1; view < view90; view++) {

			phi = (float) ((view * 2 * Math.PI) / (nproj));
			sphi = (float) Math.sin(phi);
			cphi = (float) Math.cos(phi);
			for (x = (float) (-xmax + 0.625); x <= (float) (xmax - 0.625); x += 0.25) {

				float fov = (float) Math.sqrt(Math.pow(xmax, 2)
						- Math.pow(x, 2));
				// Pontos de interesecao da linha do detetor com o FOV: (x1,y1)
				// ,
				// (x2,y2)
				x1 = (x * cphi + sphi * fov);
				y1 = (x * sphi - cphi * fov);
				x2 = (x * cphi - sphi * fov);
				y2 = (x * sphi + cphi * fov);
				dx = (x1 - x2);
				i_dx = 1 / dx;
				dy = (y2 - y1);
				i_dy = 1 / dy;
				D = (float) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
				min_x = (float) (Math.floor(x1 + 0.5) + 0.5);
				max_x = (float) (Math.floor(x2 - 0.5) + 0.5);
				min_y = (float) (Math.floor(y1 - 0.5) + 0.5);
				max_y = (float) (Math.floor(y2 + 0.5) + 0.5);
				X = (int) (min_x - 0.5);
				Y = (int) (min_y + 0.5);
				int kmax = (int) (1 + min_x - max_x);
				ax = new float[kmax];
				ax[0] = (x1 - min_x) * i_dx;
				int k = 1;
				while (k < kmax) {
					ax[k] = ax[k - 1] + i_dx;
					k++;
				}
				kmax = (int) (1 + max_y - min_y);
				ay = new float[kmax];
				ay[0] = (min_y - y1) * i_dy;
				k = 1;
				while (k < kmax) {
					ay[k] = ay[k - 1] + i_dy;
					k++;
				}
				if (ax[0] > ay[0])
					a = ax[0];
				else
					a = ay[0];
				int ix, iy;
				ix = iy = 1;
				soma_mi_x = 0;
				while (a < 1) {
					p1 = mudaCoord(view, (int) Math.floor(x), true);
					p2 = mudaCoord(Y, X, false);
					mi = mapa[p2];
					if (ax[ix] < ay[iy]) {
						d = (ax[ix] - a) * D;
						soma_mi_x += d * mi;
						A = (float) Math.exp(-soma_mi_x);
						if (mi == 0)
							W = A;
						else
							W = (float) ((A / mi) * (1 - Math.exp(-mi * d)));
						// IJ.log("quad1 W="+W);
						sinopixels[p1] += W * imgpixels[p2];
						a = ax[ix++];
						X--;
						// IJ.log("sino["+view+"]["+(int)(x)+"] =
						// img["+Y+"]["+X+"]");
					} else {
						d = (ay[iy] - a) * D;
						soma_mi_x += d * mi;
						A = (float) Math.exp(-soma_mi_x);
						if (mi == 0)
							W = A;
						else
							W = (float) ((A / mi) * (1 - Math.exp(-mi * d)));
						// IJ.log("quad1 W="+W);
						sinopixels[p1] += W * imgpixels[p2];
						a = ay[iy++];
						Y++;
						// IJ.log("sino["+view+"]["+(int)(x)+"] =
						// img["+Y+"]["+X+"]");
					}
				}
			}
		}
	}

	/**
	 * Método para Quadrante dois - view90 até view179 - com correção de atenuação
	 * 
	 * @param mapa
	 *            array float com os valores da imagem de atenuação
	 * @param imgpixels
	 *            array float com os valores da imagem
	 */

	public static void quadrante2(float[] mapa, float[] imgpixels) {

		float phi, cphi, sphi;
		float x, x1, x2, y1, y2;
		float dx, dy, D, i_dx, i_dy;
		float min_x, max_x, min_y, max_y;
		float ax[], ay[], a;
		int X, Y;
		float mi, soma_mi_x, d, W, A;
		int p1, p2;

		for (view = (view90 + 1); view < view180; view++) {

			phi = (float) ((view * 2 * Math.PI) / (nproj));
			sphi = (float) Math.sin(phi);
			cphi = (float) Math.cos(phi);
			for (x = (float) (-xmax + 0.625); x <= (float) (xmax - 0.625); x += 0.25) {

				float fov = (float) Math.sqrt(Math.pow(xmax, 2)
						- Math.pow(x, 2));
				// Pontos de interesecao da linha do detetor com o FOV: (x1,y1)
				// ,
				// (x2,y2)
				x1 = (x * cphi + sphi * fov);
				y1 = (x * sphi - cphi * fov);
				x2 = (x * cphi - sphi * fov);
				y2 = (x * sphi + cphi * fov);
				dx = (x1 - x2);
				i_dx = 1 / dx;
				dy = (y1 - y2);
				i_dy = 1 / dy;
				D = (float) (Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)));
				min_x = (float) (Math.floor(x1 + 0.5) + 0.5);
				max_x = (float) (Math.floor(x2 - 0.5) + 0.5);
				min_y = (float) (Math.floor(y1 + 0.5) + 0.5);
				max_y = (float) (Math.floor(y2 - 0.5) + 0.5);
				X = (int) (min_x - 0.5);
				Y = (int) (min_y - 0.5);
				int kmax = (int) (1 + min_x - max_x);
				ax = new float[kmax];
				ax[0] = (x1 - min_x) * i_dx;
				int k = 1;
				while (k < kmax) {
					ax[k] = ax[k - 1] + i_dx;
					k++;
				}
				kmax = (int) (1 + min_y - max_y);
				ay = new float[kmax];
				ay[0] = (-min_y + y1) * i_dy;
				k = 1;
				while (k < kmax) {
					ay[k] = ay[k - 1] + i_dy;
					k++;
				}
				if (ax[0] > ay[0])
					a = ax[0];
				else
					a = ay[0];
				int ix, iy;
				ix = iy = 1;
				soma_mi_x = 0;
				while (a < 1) {
					p1 = mudaCoord(view, (int) Math.floor(x), true);
					p2 = mudaCoord(Y, X, false);
					mi = mapa[p2];
					if (ax[ix] < ay[iy]) {
						d = (ax[ix] - a) * D;
						soma_mi_x += d * mi;
						A = (float) Math.exp(-soma_mi_x);
						if (mi == 0)
							W = A;
						else
							W = (float) ((A / mi) * (1 - Math.exp(-mi * d)));
						sinopixels[p1] += W * imgpixels[p2];
						// IJ.log("quad2 W="+W+ " sino="+sinopixels[p1]);
						a = ax[ix++];
						X--;
						// IJ.log("sino["+view+"]["+(int)(x)+"] =
						// img["+Y+"]["+X+"]");
					} else {
						d = (ay[iy] - a) * D;
						soma_mi_x += d * mi;
						A = (float) Math.exp(-soma_mi_x);
						if (mi == 0)
							W = A;
						else
							W = (float) ((A / mi) * (1 - Math.exp(-mi * d)));
						sinopixels[p1] += W * imgpixels[p2];
						// IJ.log("quad2 W="+W+ " sino="+sinopixels[p1]);
						a = ay[iy++];
						Y--;
						// IJ.log("sino["+view+"]["+(int)(x)+"] =
						// img["+Y+"]["+X+"]");
					}
				}
			}
		}
	}

	/**
	 * Método para Quadrante três - view180 até view269 - com correção de atenuação
	 * 
	 * @param mapa
	 *            array float com os valores da imagem de atenuação
	 * @param imgpixels
	 *            array float com os valores da imagem
	 */
	public static void quadrante3(float[] mapa, float[] imgpixels) {

		float phi, cphi, sphi;
		float x, x1, x2, y1, y2;
		float dx, dy, D, i_dx, i_dy;
		float min_x, max_x, min_y, max_y;
		float ax[], ay[], a;
		float mi, soma_mi_x, d, W, A;
		int X, Y;
		int p1, p2;
		for (view = (view180 + 1); view < view270; view++) {

			phi = (float) ((view * 2 * Math.PI) / (nproj));
			sphi = (float) Math.sin(phi);
			cphi = (float) Math.cos(phi);
			for (x = (float) (-xmax + 0.625); x <= (float) (xmax - 0.625); x += 0.25) {

				float fov = (float) Math.sqrt(Math.pow(xmax, 2)
						- Math.pow(x, 2));
				// Pontos de interesecao da linha do detetor com o FOV: (x1,y1)
				// ,
				// (x2,y2)
				x1 = (x * cphi + sphi * fov);
				y1 = (x * sphi - cphi * fov);
				x2 = (x * cphi - sphi * fov);
				y2 = (x * sphi + cphi * fov);
				dx = (x2 - x1);
				i_dx = 1 / dx;
				dy = (y1 - y2);
				i_dy = 1 / dy;
				D = (float) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
				min_x = (float) (Math.floor(x1 - 0.5) + 0.5);
				max_x = (float) (Math.floor(x2 + 0.5) + 0.5);
				min_y = (float) (Math.floor(y1 + 0.5) + 0.5);
				max_y = (float) (Math.floor(y2 - 0.5) + 0.5);
				X = (int) (min_x + 0.5);
				Y = (int) (min_y - 0.5);
				int kmax = (int) (1 + max_x - min_x);
				ax = new float[kmax];
				ax[0] = (-x1 + min_x) * i_dx;
				int k = 1;
				while (k < kmax) {
					ax[k] = ax[k - 1] + i_dx;
					k++;
				}
				kmax = (int) (1 + min_y - max_y);
				ay = new float[kmax];
				ay[0] = (-min_y + y1) * i_dy;
				k = 1;
				while (k < kmax) {
					ay[k] = ay[k - 1] + i_dy;
					k++;
				}
				if (ax[0] > ay[0])
					a = ax[0];
				else
					a = ay[0];
				int ix, iy;
				ix = iy = 1;
				soma_mi_x = 0;
				while (a < 1) {
					p1 = mudaCoord(view, (int) Math.floor(x), true);
					p2 = mudaCoord(Y, X, false);
					mi = mapa[p2];
					if (ax[ix] < ay[iy]) {
						d = (ax[ix] - a) * D;
						soma_mi_x += d * mi;
						A = (float) Math.exp(-soma_mi_x);
						if (mi == 0)
							W = A;
						else
							W = (float) ((A / mi) * (1 - Math.exp(-mi * d)));
						// IJ.log("quad3 W="+W);
						sinopixels[p1] += W * imgpixels[p2];
						a = ax[ix++];
						X++;
						// IJ.log("sino["+view+"]["+(int)(x)+"] =
						// img["+Y+"]["+X+"]");
					} else {
						d = (ay[iy] - a) * D;
						soma_mi_x += d * mi;
						A = (float) Math.exp(-soma_mi_x);
						if (mi == 0)
							W = A;
						else
							W = (float) ((A / mi) * (1 - Math.exp(-mi * d)));
						// IJ.log("quad3 W="+W);
						sinopixels[p1] += W * imgpixels[p2];
						a = ay[iy++];
						Y--;
						// IJ.log("sino["+view+"]["+(int)(x)+"] =
						// img["+Y+"]["+X+"]");
					}
				}
			}
		}
	}

	/**
	 * Método para Quadrante quatro - view270 até view359 - com correção de atenuação
	 * 
	 * @param mapa
	 *            array float com os valores da imagem de atenuação
	 * @param imgpixels
	 *            array float com os valores da imagem
	 */

	public static void quadrante4(float[] mapa, float[] imgpixels) {

		float phi, cphi, sphi;
		float x, x1, x2, y1, y2;
		float dx, dy, D, i_dx, i_dy;
		float min_x, max_x, min_y, max_y;
		float ax[], ay[], a;
		float mi, soma_mi_x, d, W, A;
		int X, Y;
		int p1, p2;
		for (view = (view270 + 1); view < nproj; view++) {

			phi = (float) ((view * 2 * Math.PI) / (nproj));
			sphi = (float) Math.sin(phi);
			cphi = (float) Math.cos(phi);
			for (x = (float) (-xmax + 0.625); x <= (float) (xmax - 0.625); x += 0.25) {
				float fov = (float) Math.sqrt(Math.pow(xmax, 2)
						- Math.pow(x, 2));
				// Pontos de interesecao da linha do detetor com o FOV: (x1,y1)
				// ,
				// (x2,y2)
				x1 = (x * cphi + sphi * fov);
				y1 = (x * sphi - cphi * fov);
				x2 = (x * cphi - sphi * fov);
				y2 = (x * sphi + cphi * fov);
				dx = (x2 - x1);
				i_dx = 1 / dx;
				dy = (y2 - y1);
				i_dy = 1 / dy;
				D = (float) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
				min_x = (float) (Math.floor(x1 - 0.5) + 0.5);
				max_x = (float) (Math.floor(x2 + 0.5) + 0.5);
				min_y = (float) (Math.floor(y1 - 0.5) + 0.5);
				max_y = (float) (Math.floor(y2 + 0.5) + 0.5);
				X = (int) (min_x + 0.5);
				Y = (int) (min_y + 0.5);
				int kmax = (int) (1 + max_x - min_x);
				ax = new float[kmax];
				ax[0] = (-x1 + min_x) * i_dx;
				int k = 1;
				while (k < kmax) {
					ax[k] = ax[k - 1] + i_dx;
					k++;
				}
				kmax = (int) (1 + max_y - min_y);
				ay = new float[kmax];
				ay[0] = (min_y - y1) * i_dy;
				k = 1;
				while (k < kmax) {
					ay[k] = ay[k - 1] + i_dy;
					k++;
				}
				if (ax[0] > ay[0])
					a = ax[0];
				else
					a = ay[0];
				int ix, iy;
				ix = iy = 1;
				soma_mi_x = 0;
				while (a < 1) {
					p1 = mudaCoord(view, (int) Math.floor(x), true);
					p2 = mudaCoord(Y, X, false);
					mi = mapa[p2];
					if (ax[ix] < ay[iy]) {
						d = (ax[ix] - a) * D;
						soma_mi_x += d * mi;
						A = (float) Math.exp(-soma_mi_x);
						if (mi == 0)
							W = A;
						else
							W = (float) ((A / mi) * (1 - Math.exp(-mi * d)));
						// IJ.log("quad4 W="+W);
						sinopixels[p1] += W * imgpixels[p2];
						a = ax[ix++];
						X++;
						// IJ.log("sino["+view+"]["+(int)(x)+"] =
						// img["+Y+"]["+X+"]");
					} else {
						d = (ay[iy] - a) * D;
						soma_mi_x += d * mi;
						A = (float) Math.exp(-soma_mi_x);
						if (mi == 0)
							W = A;
						else
							W = (float) ((A / mi) * (1 - Math.exp(-mi * d)));
						// IJ.log("quad4 W="+W);
						sinopixels[p1] += W * imgpixels[p2];
						a = ay[iy++];
						Y++;
						// IJ.log("sino["+view+"]["+(int)(x)+"] =
						// img["+Y+"]["+X+"]");
					}
				}
			}
		}
	}

	public static int mudaCoord(int l, int c, boolean s) {

		int pixel = 0;
		if (s == false) {
			if ((l < -xmax) || (l > xmax) || (c > xmax) || (c < -xmax)) {
				return -1;
			}
			pixel = (l + xmax) * dim + (c + xmax);
			return pixel;
		} else {

			if ((c > xmax) || (c < -xmax)) {

				return -1;
			}
			pixel = (l * dim) + (c + xmax);
		} // fim do else
		return pixel;
	} // fim de mudaCoord

}
