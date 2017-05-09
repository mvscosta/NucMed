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
 * @version <b>1.7</b>
 */

public class Funcoes {
  
  /**
   * Mensagens Utilizadas Pelos Programas de Reconstrução
   */
  public static final String MENSAGEM_IMAGEM_NAO_COMPATIVEL = "The image is not compatible";
  
  public static final String NOME_PLUGIN = "RT SPECT";
  
  public static final String MENSAGEM_ERRO_IMAGEM_MAPA = "Verifique os par�metros, erro ao adquirir a imagem";
  
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
  public static final boolean debug = false;
  
  public static final boolean ativarMSE = true;
  
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
  public static double calculaDesvPad(ImagePlus imgReconstruida){
    
    double media = calculaMedia( imgReconstruida.getProcessor() );
    float[] img = (float[]) imgReconstruida.getProcessor().getPixels();
    float somatorio = 0;
    
    for (int i=0; i<img.length; i++){
      somatorio += Math.pow((img[i] - media),2);
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
  public static double calculaMedia(ImageProcessor imgReconstruida){
    
    float[] img = (float[]) imgReconstruida.getPixels();
    float total = 0, media;
    
    for (int i=0; i<img.length; i++){
      total += img[i];
    }
    
    media = total/img.length;
    
    return media;
  }
  
  /**
   * Método utilizado para calcular a média referente a imagem reconstruida.
   *
   */
  public static void calculaMSE(){
    
    double total = 0;
    ImagePlus[] imgsOrig = null;
    
    AbrirImagem abrir = new AbrirImagem();
    abrir.combo1.ativo = true;    
    abrir.combo2.ativo = true;
    imgsOrig = abrir.Abrir_ML_EM();
    
    float[] imgOrig = (float[]) imgsOrig[0].getProcessor().getPixels();
    float[] imgRec = (float[])  imgsOrig[1].getProcessor().getPixels();
    

    for (int i=0; i<imgRec.length; i++){
      total += Math.pow((imgRec[i] - imgOrig[i]),2);
    }
    
    double MSE = total/imgRec.length*imgOrig.length;
    ij.IJ.log("Calculo da media de erro quadratico foi de "+(MSE));
    ij.IJ.log("Calculo do desvio padrao foi de "+calculaDesvPad(imgsOrig[1]));
  }
  
  
}