package funcoesNM;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.plugin.filter.Transformer;
import ij.process.ImageProcessor;
import java.awt.event.KeyEvent;

/**
 * Classe criada para armazenar as funções variadas que são utilizadas das
 * outras classes.
 *
 * @author Marcus Vinícius da Silva Costa, Michele Alberton Andrade.
 * @version <b>1.9</b>
 */

public class Funcoes {
    
    /**
     * Mensagens Utilizadas Pelos Programas de Reconstrução
     */
    public static final String MENSAGEM_IMAGEM_NAO_COMPATIVEL = "The image is not compatible";
    
    public static final String NOME_PLUGIN = "RT SPECT";
    
    public static final String MENSAGEM_ERRO_IMAGEM_MAPA = "Verifique os parametros, erro ao adquirir a imagem do Mapa de Atenuacao";
    
    public static final String MENSAGEM_ERRO_ABRIR_IMAGEM = "Verifique os parametros, erro ao adquirir a imagem";
    
    public static final String MSE_LABEL_COMBO1 = "Imagem Original";
    
    public static final String MSE_LABEL_COMBO2 = "Imagem Reconstruida";
    
    /**
     * Tipos de Filtros utilizados na reconstrução por FBP
     */
    public static final int FILTRO_RAMPA = 1;
    
    public static final int FILTRO_BUTTERWORTH = 2;
    
    public static final int FILTRO_SHEPP_LOGAN = 3;
    
    public static final int FILTRO_HAMMING = 4;
    
        /*
         * Constantes utilizadas para Alterações sobre diversos fatores
         */
    public static final boolean debug = true;
    
    public static final boolean ativarVSA = false;
    
    public static final boolean ativarMSE = false;
    
    public static final float CONST_FILTRO_HAMMING = (float)0.54;
        /*
         * Relacionado com a abertura ou do corte direto relativo ao filtro utilizado no método FBP.
         *
         * Se true, ativa o corte no filtro referente a frequência de corte estipulada.
         * Se false, libera a filtragem até atingido o tamanho máximo.
         */
    public static final boolean filtroCorte = true;
    
    /**
     * Procedimento utilizado para girar uma imagem 90 graus para a esquerda.
     *
     * @param imp :
     *            Recebe um ImagePlus com a imagem a ser rotada.
     */
    public static void Rotar(ImagePlus imp) {
        // int slices;
        ImageProcessor ip;
        Transformer trans = new Transformer();
        int resultado = trans.setup("left", imp);
        if (!((resultado - PlugInFilter.DOES_ALL - PlugInFilter.NO_UNDO) % 128 == 0))
            return;
        ip = imp.getStack().getProcessor(1);
        trans.run(ip);
    }
    
    /**
     * Função utilizada para alterar o caracter de "." para "," para o ImageJ
     * adquirir o nome na opção de salvar imagem.
     *
     * @param nome
     *            Parâmetro com o nome inicial da imagem a ser alterada.
     * @return Retorna o nome da imagem alterada.
     */
    public static String AlteraNomeImagem(String nome) {
        char[] nome_c = new char[nome.length()];
        char[] titulo_c = new char[nome.length()];
        
        nome.getChars(0, nome.length(), titulo_c, 0);
        nome.getChars(0, nome.length(), nome_c, 0);
        for (int c = 0; c < nome.length(); c++) {
            if (nome_c[c] == '.')
                titulo_c[c] = ',';
            else
                titulo_c[c] = nome_c[c];
        }
        return String.valueOf(titulo_c);
    }
    
    /**
     * Função para testar se a imagem a ser aberta tem os parâmetros corretos,
     * neste caso se o tamanho em x da imagem e múltiplo de 2.
     *
     * @param largura :
     *            largura da imagem a ser testada.
     * @return Retorna se a imagem passou no teste.
     */
    public static boolean isPowerOf2(float largura) {
        
        float aux = largura % 2;
        while ((aux == 0) && (largura > 1)) {
            largura = (largura / 2);
            aux = (largura % 2);
        }
        if (largura == 1)
            return (true);
        else
            return (false);
    }
    
    /**
     * Funçãoo para calcular o tamanho do campo de visão.
     *
     * @param x :
     *            Informa o valor do x na imagem;
     * @param y :
     *            Informa o valor do y na imagem;
     * @return Retorna o campo de visão em uma variavel int.
     */
    public static int calculaFOV(int x, int y) {
        
        int fov = (int) (Math.sqrt(x * x - y * y) + 0.5);
        return fov;
    }
    
    /**
     * Função para verificar a posição de insersão do pixel na imagem.
     *
     * @param l
     *            Valor da linha da imagem selecionada.
     * @param c
     *            Valor do coluna da imagem selecionada.
     * @param s
     *            Informa se a imagem selecionada é um sinograma.
     * @param xmax
     *            Recebe o xmax;
     * @param dim
     *            Valor da largura da imagem selecionada.
     * @return Retorna a posição do vetor onde deve ser colocado o valor do
     *         pixel;
     */
    public static int mudaCoord(int l, int c, boolean s, int xmax, int dim) {
        int pixel = 0;
        if (s == false) {
            if ((l < -xmax) || (l > xmax) || (c > xmax) || (c < -xmax))
                return -1;
            pixel = (l + xmax) * dim + (c + xmax);
            return pixel;
        } else {
            if ((c > xmax) || (c < -xmax))
                return -1;
            pixel = (l * dim) + (c + xmax);
            return pixel;
        }
    }
    
    /**
     * Procedimento utilizado para limitar o tamanho de componentes TextField.
     *
     * @param evt :
     *            Par�metro tratado pelo JAVA.
     * @param tf :
     *            Objeto que chamou a função.
     * @param tamanho :
     *            Tamanho máximo de caracteres.
     */
    public static void limitaTextField(java.awt.event.KeyEvent evt,
            javax.swing.JTextField tf, int tamanho) {
        
        if (evt.getKeyChar() == ',')
            evt.setKeyChar('.'); // troca o caracter ","
        // por "."
        if (evt.getKeyChar() != KeyEvent.VK_BACK_SPACE) { // deixa o usuario
            // precionar
            // backspace
            if (evt.getSource().equals(tf))
                if (tf.getText().length() > tamanho) {
                tf.setText(tf.getText().concat(
                        Character.toString(evt.getKeyChar())));
                evt.setKeyChar(' ');
                tf.transferFocus();
                } else if (tf.getText().length() >= tamanho) {
                evt.setKeyChar(' ');
                evt.consume();
                }
        }
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) // altera o focus caso o
            // usuario
            // tecle enter;
            tf.transferFocus();
    }
    
    /**
     * Método utilizado para calcular o desvio padrão da imagem reconstruida.
     *
     * @param imgReconstruida
     *            ImageProcessor da imagem reconstruida
     * @return
     *            Retorna o resultado do calculo do desvio padrão da imagem reconstruida.
     */
    public static double calculaDesvPad(ImagePlus imgReconstruida) {
        
        double media = calculaMedia( imgReconstruida.getProcessor() );
        float[] img = (float[]) imgReconstruida.getProcessor().getPixels();
        float somatorio = 0;
        
        for (int i=0; i<img.length; i++) {
            float value = img[i];
            if ( Float.isNaN(value) )
                value = 0;
            somatorio += Math.pow((value - media),2);
        }
        
        double desvioPadrao = Math.sqrt(somatorio/img.length);
        
        return desvioPadrao;
    }
    
    /**
     * Método utilizado para calcular a média referente a imagem reconstruida.
     *
     * @param imgReconstruida
     *            ImageProcessor da imagem reconstruida
     * @return
     *            Retorna o resultado do calculo de média da imagem reconstruida.
     */
    public static double calculaMedia(ImageProcessor imgReconstruida) {
        
        float[] img = (float[]) imgReconstruida.getPixels();
        float total = 0, media;
        
        for (int i=0; i<img.length; i++) {
            float value = img[i];
            if ( Float.isNaN(value) )
                value = 0;
            total += value;
        }
        
        media = total/img.length;
        
        return media;
    }
    
    /**
     * Método utilizado para calcular a média referente a imagem reconstruida.
     * @param imgs Vetor com imagens. Posi��o [0] Imagem Original
     *                           [1] Imagem Reconstruido
     */
    public static void calculaMSE(ImagePlus[] imgs) {
        double total = 0;
        if( imgs == null ) {
            AbrirImagem abrir = new AbrirImagem();
            imgs = abrir.Abrir(MSE_LABEL_COMBO1,MSE_LABEL_COMBO2, true, true);
        }
        if ( (imgs[0]==null) || (imgs[1]==null) ) {
            ij.IJ.showMessage("Erro ao abrir as imagens para calculo do MSE");
            return;
        }
        
        float[] imgOrig = (float[]) imgs[0].getProcessor().getPixels();
        float[] imgRec = (float[])  imgs[1].getProcessor().getPixels();
        
        for (int i=0; i<imgRec.length; i++) {
            float valueOrig = imgOrig[i];
            if ( Float.isNaN(valueOrig) )
                valueOrig = 0;
            float valueRec = imgRec[i];
            if ( Float.isNaN(valueRec) )
                valueRec = 0;
            total += Math.pow((valueRec - valueOrig),2);
        }
        
        double MSE = total/imgRec.length*imgOrig.length;
        ij.IJ.log("MSE= "+(MSE));
        ij.IJ.log("DP= "+calculaDesvPad(imgs[1])+"\n\n");
    }
    
    /*
     *
     * M�todo para calcular a verossimilhan�a entre o sinograma de cada itera��o com o sinograma original
     *
     */
    /**
     * Fun��o para calcular a verossimilhan�a entre as imagens do sinograma original e 
     * o sinograma reconstruido
     * @param imgs Vetor com imagens. posi��o  [0] sinograma Original
     *                            [1] sinograma Reconstruido
     */
    public static void calculaVSA(ImagePlus[] imgs) {
        
        double vsa = 0.0;
        for (int i=0;i<imgs[0].getWidth();i++) {
            for(int j=0;j<imgs[0].getHeight();j++) {
                
                float dSinoIter = Float.intBitsToFloat(imgs[1].getProcessor().getPixel(i,j));
                if ( Double.isNaN(dSinoIter))
                    dSinoIter = 0;
                
                float dSinoOrig = Float.intBitsToFloat(imgs[0].getProcessor().getPixel(i,j));
                if ( Double.isNaN(dSinoOrig))
                    dSinoOrig = 0;
                
                double logarit = Math.log10(Math.abs(dSinoIter));
                if ((Double.isNaN(logarit) ) || (Double.isInfinite(logarit)) )
                    logarit = 0;
                vsa += (-dSinoIter)+dSinoOrig*logarit;
            }
        }
        ij.IJ.log("VSA= "+(vsa)+"\n");
        
    }
    public static void ContaNaNeInfinity(ImageProcessor ip) {
    
        int NaN = 0;
        int Infinity = 0;
        float[] aIP = (float[])ip.getPixels();
        for (int i=0;i<aIP.length;i++) {
            if (Float.isNaN(aIP[i]))
                NaN++;
            if (Float.isInfinite(aIP[i]))
                Infinity++;
            
            ij.IJ.log("quantidade de NaN="+NaN+" quantidade de Infinity= "+Infinity);
        }
            
    }
}
        
        
