package nucMed;

import funcoesNM.Funcoes;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;

/**
 * Classe com as funções para a reconstrução pelo método FBP.
 *
 * @author Michele Alberton Andrade
 * @version <B>1.7</B>
 */

public class Backprojection {
    
    public ImageProcessor sinogramaFiltrado;
    
    public static ImageProcessor imagemFinal;
    
    public static ImageProcessor ipFiltrado;
    
    public static float sinopixels[];
    
    public static float imgpixels[];
    
    public static int nproj, dim, xmax;
    
    public static int view, view45, view90, view180, view225, view270;
    
    public static int plus90, plus270;
    
    /**
     * Construtor da classe, inicializa as variáveis globais.
     *
     * @param imp Recebe o ImageProcessor com a imagem do sinograma.
     */
    public Backprojection(ImageProcessor imp) {
        
        sinopixels = new float[imp.getHeight() * imp.getWidth()];
        ImageProcessor ipFiltrado = imp;
        nproj = ipFiltrado.getHeight();
        dim = ipFiltrado.getWidth();
        xmax = ((dim + 1) - 2) / 2;
        sinopixels = (float[]) ipFiltrado.getPixels();
    }
    
    /**
     * Função para a Reconstrução de Imagem.
     *
     * @return Função que retorna a imagem Final Reconstruida
     */
    public ImageProcessor Reconstruir() {
        
        imagemFinal = new FloatProcessor(dim, dim);
        imgpixels = (float[]) imagemFinal.getPixels();
        
        view90 = (int) (0.25 * nproj);
        view45 = (int) (0.5 * view90);
        view180 = (int) (0.5 * nproj);
        view225 = (int) (0.625 * nproj);
        view270 = (int) (0.75 * nproj);
        
        viewZero(sinopixels, imgpixels);
        view1to45(sinopixels, imgpixels);
        view45(sinopixels, imgpixels);
        view90(sinopixels, imgpixels);
        view180(sinopixels, imgpixels);
        view181to225(sinopixels, imgpixels);
        view225(sinopixels, imgpixels);
        view270(sinopixels, imgpixels);
        
        for (int x = 0; x < imgpixels.length; x++)
            imgpixels[x] = imgpixels[x] / nproj;
        
        return imagemFinal;
    }
    
    /**
     * Procedimento para fazer view zero
     *
     * @param sinopixels : Vetor com os valores do sinograma da imagem
     *          selecionada.
     * @param imgpixels : Vetor com os valores da imagem final.
     */
    public void viewZero(float[] sinopixels, float[] imgpixels) {
        
        view = 0;
        for (int y = -xmax; y <= xmax; y++) {
            
            int fovmax = Funcoes.calculaFOV(xmax, y);
            for (int x = -fovmax; x <= fovmax; x++) {
                
                int p1 = Funcoes.mudaCoord(y, x, false, xmax, dim);
                int p2 = Funcoes.mudaCoord(view, x, true, xmax, dim);
                imgpixels[p1] += sinopixels[p2];
            }
        }
    }
    
    /**
     * Procedimento para fazer view1 até view44
     *
     * @param sinopixels : Vetor com os valores do sinograma da imagem
     *          selecionada.
     * @param imgpixels : Vetor com os valores da imagem final.
     */
    public void view1to45(float[] sinopixels, float[] imgpixels) {
        
        float t;
        for (view = 1; view < view45; view++) {
            
            int min90 = view90 - view;
            int plus90 = view90 + view;
            int min180 = (nproj / 2) - view;
            float phi = view * (float) Math.PI / (nproj / 2);
            float sphi = (float) Math.sin(phi);
            float cphi = (float) Math.cos(phi);
            for (int y = -xmax; y <= xmax; y++) {
                
                int fovmax = Funcoes.calculaFOV(xmax, y);
                t = -fovmax * cphi + y * sphi;
                for (int x = -fovmax; x <= fovmax; x++) {
                    
                    int j = (int) Math.round(t);
                    float m = t - j;
                    int p1 = Funcoes.mudaCoord(y, x, false, xmax, dim);
                    int p2 = Funcoes.mudaCoord(view, j, true, xmax, dim);
                    int p3 = Funcoes.mudaCoord(view, j + 1, true, xmax, dim);
                    
                    try {
                        imgpixels[p1] += ((1. - m) * sinopixels[p2]) + (m * sinopixels[p3]);
                    } catch (ArrayIndexOutOfBoundsException exc) {
                    }
                    
                    int p4 = Funcoes.mudaCoord(x, y, false, xmax, dim);
                    int p5 = Funcoes.mudaCoord(min90, j, true, xmax, dim);
                    int p6 = Funcoes.mudaCoord(min90, (j + 1), true, xmax, dim);
                    try {
                        imgpixels[p4] += ((1. - m) * sinopixels[p5]) + (m * sinopixels[p6]);
                    } catch (ArrayIndexOutOfBoundsException exc) {
                    }
                    int p7 = Funcoes.mudaCoord(x, -y, false, xmax, dim);
                    int p8 = Funcoes.mudaCoord(plus90, j, true, xmax, dim);
                    int p9 = Funcoes.mudaCoord(plus90, (j + 1), true, xmax, dim);
                    try {
                        imgpixels[p7] += ((1. - m) * sinopixels[p8]) + (m * sinopixels[p9]);
                    } catch (ArrayIndexOutOfBoundsException exc) {
                    }
                    
                    int p10 = Funcoes.mudaCoord(y, -x, false, xmax, dim);
                    int p11 = Funcoes.mudaCoord(min180, j, true, xmax, dim);
                    int p12 = Funcoes.mudaCoord(min180, (j + 1), true, xmax, dim);
                    try {
                        imgpixels[p10] += ((1. - m) * sinopixels[p11]) + (m * sinopixels[p12]);
                    } catch (ArrayIndexOutOfBoundsException exc) {
                    }
                    t += cphi;
                }
            }
        }
    }
    
    /**
     * Procedimento para fazer view45
     *
     * @param sinopixels : Vetor com os valores do sinograma da imagem
     *          selecionada.
     * @param imgpixels : Vetor com os valores da imagem final.
     */
    public void view45(float[] sinopixels, float[] imgpixels) {
        
        int view = view45;
        plus90 = view90 + view;
        float t, m, phi, sphi, cphi;
        phi = view * (float) (Math.PI) / (nproj / 2);
        sphi = (float) Math.sin(phi);
        cphi = (float) Math.cos(phi);
        
        for (int y = -xmax; y <= xmax; y++) {
            
            int fovmax = Funcoes.calculaFOV(xmax, y);
            t = -fovmax * cphi + y * sphi;
            for (int x = -fovmax; x <= fovmax; x++) {
                
                int j = (int) Math.round(t);
                m = t - j;
                int p1 = Funcoes.mudaCoord(y, x, false, xmax, dim);
                int p2 = Funcoes.mudaCoord(view, j, true, xmax, dim);
                int p3 = Funcoes.mudaCoord(view, (j + 1), true, xmax, dim);
                try {
                    imgpixels[p1] += ((1. - m) * sinopixels[p2]) + (m * sinopixels[p3]);
                } catch (ArrayIndexOutOfBoundsException exc) {
                }
                
                int p4 = Funcoes.mudaCoord(x, -y, false, xmax, dim);
                int p5 = Funcoes.mudaCoord(plus90, j, true, xmax, dim);
                int p6 = Funcoes.mudaCoord(plus90, (j + 1), true, xmax, dim);
                
                try {
                    imgpixels[p4] += ((1. - m) * sinopixels[p5]) + (m * sinopixels[p6]);
                } catch (ArrayIndexOutOfBoundsException exc) {
                }
                
                t += cphi;
            }
        }
    }
    
    /**
     * Procedimento para fazer view 90
     *
     * @param sinopixels : Vetor com os valores do sinograma da imagem
     *          selecionada.
     * @param imgpixels : Vetor com os valores da imagem final.
     */
    public void view90(float[] sinopixels, float[] imgpixels) {
        
        view = view90;
        for (int y = -xmax; y <= xmax; y++) {
            
            int fovmax = Funcoes.calculaFOV(xmax, y);
            for (int x = -fovmax; x <= fovmax; x++) {
                
                int p1 = Funcoes.mudaCoord(y, x, false, xmax, dim);
                int p2 = Funcoes.mudaCoord(view, y, true, xmax, dim);
                imgpixels[p1] += sinopixels[p2];
            }
        }
    }
    
    /**
     * Procedimento para fazer view 180
     *
     * @param sinopixels : Vetor com os valores do sinograma da imagem
     *          selecionada.
     * @param imgpixels : Vetor com os valores da imagem final.
     */
    public void view180(float[] sinopixels, float[] imgpixels) {
        
        view = view180;
        for (int y = -xmax; y <= xmax; y++) {
            
            int fovmax = Funcoes.calculaFOV(xmax, y);
            for (int x = -fovmax; x <= fovmax; x++) {
                
                int p1 = Funcoes.mudaCoord(-y, -x, false, xmax, dim);
                int p2 = Funcoes.mudaCoord(view, x, true, xmax, dim);
                imgpixels[p1] += sinopixels[p2];
            }
        }
    }
    
    /**
     * Procedimento para fazer view 181 até view 225
     *
     * @param sinopixels : Vetor com os valores do sinograma da imagem
     *          selecionada.
     * @param imgpixels : Vetor com os valores da imagem final.
     */
    public void view181to225(float[] sinopixels, float[] imgpixels) {
        
        float phi, sphi, cphi, m, t;
        for (view = view180 + 1; view < view225; view++) {
            
            int min270 = (int) (view270 - view + nproj / 2);
            int plus270 = (int) (view270 + view - nproj / 2);
            int min360 = (int) (nproj - view + nproj / 2);
            phi = view * (float) (2. * Math.PI) / (nproj);
            sphi = (float) Math.sin(phi);
            cphi = (float) Math.cos(phi);
            for (int y = -xmax; y <= xmax; y++) {
                
                int fovmax = Funcoes.calculaFOV(xmax, y);
                t = fovmax * cphi - y * sphi;
                for (int x = -fovmax; x <= fovmax; x++) {
                    
                    int j = (int) Math.round(t);
                    m = t - j;
                    int p1 = Funcoes.mudaCoord(-y, -x, false, xmax, dim);
                    int p2 = Funcoes.mudaCoord(view, j, true, xmax, dim);
                    int p3 = Funcoes.mudaCoord(view, j + 1, true, xmax, dim);
                    try {
                        imgpixels[p1] += ((1. - m) * sinopixels[p2]) + (m * sinopixels[p3]);
                    } catch (ArrayIndexOutOfBoundsException exc) {
                    }
                    
                    int p4 = Funcoes.mudaCoord(-x, -y, false, xmax, dim);
                    int p5 = Funcoes.mudaCoord(min270, j, true, xmax, dim);
                    int p6 = Funcoes.mudaCoord(min270, j + 1, true, xmax, dim);
                    try {
                        imgpixels[p4] += ((1. - m) * sinopixels[p5]) + (m * sinopixels[p6]);
                    } catch (ArrayIndexOutOfBoundsException exc) {
                        imgpixels[p4] += ((1. - m) * sinopixels[p5]);
                    }
                    
                    int p7 = Funcoes.mudaCoord(-x, y, false, xmax, dim);
                    int p8 = Funcoes.mudaCoord(plus270, j, true, xmax, dim);
                    int p9 = Funcoes.mudaCoord(plus270, j + 1, true, xmax, dim);
                    try {
                        imgpixels[p7] += ((1. - m) * sinopixels[p8]) + (m * sinopixels[p9]);
                    } catch (ArrayIndexOutOfBoundsException exc) {
                    }
                    
                    int p10 = Funcoes.mudaCoord(-y, x, false, xmax, dim);
                    int p11 = Funcoes.mudaCoord(min360, j, true, xmax, dim);
                    int p12 = Funcoes.mudaCoord(min360, j + 1, true, xmax, dim);
                    try {
                        imgpixels[p10] += ((1. - m) * sinopixels[p11]) + (m * sinopixels[p12]);
                    } catch (ArrayIndexOutOfBoundsException exc) {
                    }
                    
                    t -= cphi;
                }
            }
        }
    }
    
    /**
     * Procedimento para fazer view 225
     *
     * @param sinopixels : Vetor com os valores do sinograma da imagem
     *          selecionada.
     * @param imgpixels : Vetor com os valores da imagem final.
     */
    public void view225(float[] sinopixels, float[] imgpixels) {
        
        view = view225;
        int plus270 = view270 + view - nproj / 2;
        float t, m, phi, sphi, cphi;
        
        phi = view * (float) (Math.PI) / (nproj / 2);
        sphi = (float) Math.sin(phi);
        cphi = (float) Math.cos(phi);
        
        for (int y = -xmax; y <= xmax; y++) {
            
            int fovmax = Funcoes.calculaFOV(xmax, y);
            t = fovmax * cphi - y * sphi;
            for (int x = -fovmax; x <= fovmax; x++) {
                int j = (int) Math.round(t);
                m = t - j;
                int p1 = Funcoes.mudaCoord(-y, -x, false, xmax, dim);
                int p2 = Funcoes.mudaCoord(view, j, true, xmax, dim);
                int p3 = Funcoes.mudaCoord(view, (j + 1), true, xmax, dim);
                int p4 = Funcoes.mudaCoord(-x, y, false, xmax, dim);
                int p5 = Funcoes.mudaCoord(plus270, j, true, xmax, dim);
                int p6 = Funcoes.mudaCoord(plus270, (j + 1), true, xmax, dim);
                try {
                    imgpixels[p1] += ((1. - m) * sinopixels[p2]) + (m * sinopixels[p3]);
                    imgpixels[p4] += ((1. - m) * sinopixels[p5]) + (m * sinopixels[p6]);
                } catch (ArrayIndexOutOfBoundsException exc) {
                }
                t -= cphi;
            }
        }
    }
    
    /**
     * Procedimento para fazer view 270
     *
     * @param sinopixels : Vetor com os valores do sinograma da imagem
     *          selecionada.
     * @param imgpixels : Vetor com os valores da imagem final.
     */
    public void view270(float[] sinopixels, float[] imgpixels) {
        
        view = view270;
        for (int y = -xmax; y <= xmax; y++) {
            
            int fovmax = Funcoes.calculaFOV(xmax, y);
            for (int x = -fovmax; x <= fovmax; x++) {
                
                int p1 = Funcoes.mudaCoord(-y, -x, false, xmax, dim);
                int p2 = Funcoes.mudaCoord(view, y, true, xmax, dim);
                imgpixels[p1] += sinopixels[p2];
            }
        }
    }
    
}
