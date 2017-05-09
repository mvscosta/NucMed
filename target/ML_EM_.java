
/*
 * ML_EM_.java
 *
 * Created on April 27, 2006, 5:17 PM
 *
 */

import funcoesNM.AbrirImagem;
import funcoesNM.Funcoes;
import nucMed.Projetor;
import nucMed.Retroprojetor;
import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.process.FloatProcessor;
import ij.process.ImageConverter;
import ij.process.ImageProcessor;
import ij.process.StackConverter;

/**
 * Classe principal. Contém a criação da janela do plugin para reconstrução de
 * imagem através do método de <i>Maximum Likehood Expectation Maximization</i>.
 *
 * @author Marcus Vinicius da Silva Costa, Michele Alberton Andrade.
 * @version <B>1.3</B>
 */

public class ML_EM_ extends javax.swing.JFrame implements
  ij.plugin.filter.PlugInFilter {
  
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  
  // public static Iterativo_ Iterativo;
  public static ImagePlus imgOrig;
  
  public static ij.ImageStack ims;
  
  // public static ImageProcessor ipGlobal;
  public static ij.ImageStack isRGBFiltrada = null;
  
  // DECLARACAO DAS CONSTANTES
  public static final int TAMANHO_TF_NUM_ITER = 2;
  
  // VARIAVEIS UTILIZADAS PARA RECONHECIMENTO DE IMAGEM
  public static int tamanho_imagem = 0;
  
  public static int type;
  
  // declaracao das variaveis globais
  public static ij.ImagePlus imgMapaAtt = null;
  
	/*
	 * Variável utilizada para ativar a exibição de imagens e mensagens, para
	 * testes.
	 */
  public static boolean debugAtivo = false;
  
  /**
   * Função herdada da classe PluginFilter do ImageJ.
   */
  public int setup (String arg, ImagePlus imp) {
    imgOrig = imp;
    return DOES_ALL;
  }
  
  /**
   * Função herdada da classe PluginFilter do ImageJ.
   */
  public void run (ij.process.ImageProcessor ip) {
    
    // ipGlobal = imgOrig.getProcessor();
    tamanho_imagem = imgOrig.getWidth ();
    type = imgOrig.getType ();
    
    if ((tamanho_imagem > 0) && (Funcoes.isPowerOf2 (tamanho_imagem))) {
      // new ML_EM_ ().setVisible (true);
      ij.IJ.register (ML_EM_.class);
    } else {
      ij.IJ.showMessage (Funcoes.MENSAGEM_IMAGEM_NAO_COMPATIVEL);
      dispose ();
    }
  }
  
  /**
   * Cria e inicia a janela do ML_EM_
   */
  public ML_EM_ () {
    
    initComponents ();
    setSize (240, 270);
    setLocation (super.getX () + 200, super.getY () + 200);
    setVisible (true);
  }
  
  /**
   * @param args
   */
  public static void main (String[] args) {
    // TODO Auto-generated method stub
    System.out.println ("MLEM OK");
  }
  /**
   * Criação e inicialização de todos os objetos da janela ML_EM_ incluindo as
   * suas chamadas de funções
   */
  private void initComponents () {
    
    pnlGeral = new java.awt.Panel ();
    Label1 = new java.awt.Label ();
    Label2 = new java.awt.Label ();
    Label3 = new java.awt.Label ();
    Label4 = new java.awt.Label ();
    pnlNumIter = new javax.swing.JPanel ();
    pnlCorrecaoAtt = new javax.swing.JPanel ();
    cbSim = new java.awt.Checkbox ();
    cbNao = new java.awt.Checkbox ();
    btnReconstruir = new javax.swing.JButton ();
    tfNumIter = new javax.swing.JTextField ();
    
    getContentPane ().setLayout (
      new javax.swing.BoxLayout (getContentPane (),
      javax.swing.BoxLayout.X_AXIS));
    setTitle ("ML-EM");
    setName ("frmIterativo");
    setResizable (false);
    
    // pnlGeral.setBackground(java.awt.Color.lightGray);
    pnlGeral.setLayout (null);
    
    btnReconstruir.setFont (new java.awt.Font ("Dialog", 1, 12));
    btnReconstruir.setText ("Reconstruir");
    btnReconstruir.setBounds (50, 200, 120, 25);
    btnReconstruir.addActionListener (new java.awt.event.ActionListener () {
      public void actionPerformed (java.awt.event.ActionEvent evt) {
	Reconstruir ();
      }
    });
    pnlGeral.add (btnReconstruir);
    
    Label1.setText ("Aplicar Correcao de Atenuacao");
    Label1.setBounds (10, 10, 190, 15);
    pnlCorrecaoAtt.add (Label1);
    
    Label2.setFont (new java.awt.Font ("Dialog", 1, 12));
    Label2.setText ("Reconstrucao Tomografica");
    Label2.setAlignment (java.awt.Label.CENTER);
    Label2.setBounds (20, 10, 180, 15);
    pnlGeral.add (Label2);
    
    Label3.setFont (new java.awt.Font ("Dialog", 1, 12));
    Label3.setText ("ML-EM");
    Label3.setAlignment (java.awt.Label.CENTER);
    Label3.setBounds (20, 40, 180, 15);
    pnlGeral.add (Label3);
    
    Label4.setText ("Numero de Iteracoes:");
    Label4.setBounds (10, 10, 140, 15);
    pnlNumIter.add (Label4);
    
    // pnlCorrecaoAtt.setBackground(java.awt.Color.lightGray);
    pnlCorrecaoAtt.setBorder (new javax.swing.border.EtchedBorder ());
    pnlCorrecaoAtt.setLayout (null);
    
    cbSim.setLabel ("Sim");
    cbSim.setState (false);
    cbSim.setBounds (50, 30, 60, 23);
    cbSim.addItemListener (new java.awt.event.ItemListener () {
      public void itemStateChanged (java.awt.event.ItemEvent evt) {
	cbSimStateChanged (evt);
      }
    });
    pnlCorrecaoAtt.add (cbSim);
    
    cbNao.setLabel ("Nao");
    cbNao.setState (true);
    cbNao.setBounds (110, 30, 60, 23);
    cbNao.addItemListener (new java.awt.event.ItemListener () {
      public void itemStateChanged (java.awt.event.ItemEvent evt) {
	cbNaoStateChanged (evt);
      }
    });
    pnlCorrecaoAtt.add (cbNao);
    
    pnlGeral.add (pnlCorrecaoAtt);
    pnlCorrecaoAtt.setBounds (10, 70, 210, 60);
    
    pnlNumIter.setLayout (null);
    pnlNumIter.setBorder (new javax.swing.border.EtchedBorder ());
    // pnlNumIter.setBackground(java.awt.Color.lightGray);
    pnlNumIter.setBorder (new javax.swing.border.EtchedBorder ());
    
    tfNumIter.setBackground (new java.awt.Color (255, 255, 255));
    tfNumIter.setFont (new java.awt.Font ("Dialog", 1, 12));
    tfNumIter.setText ("5");
    tfNumIter.addKeyListener (new java.awt.event.KeyAdapter () {
      public void keyTyped (java.awt.event.KeyEvent evt) {
	Funcoes.limitaTextField (evt, tfNumIter, TAMANHO_TF_NUM_ITER);
      }
    });
    pnlNumIter.add (tfNumIter);
    tfNumIter.setBounds (150, 10, 50, 19);
    
    pnlGeral.add (pnlNumIter);
    pnlNumIter.setBounds (10, 140, 210, 40);
    
    getContentPane ().add (pnlGeral);
    
    // pack();
  }
  
  // /**
  // * Procedimento utilizado para limitar o tamanho de componentes TextField.
  // *
  // * @param evt : Parâmetro tratado pelo JAVA.
  // * @param tf : Objeto que chamou a função.
  // * @param tamanho : Tamanho máximo de caracteres.
  // */
	/*
	 * private void limitaTextField(java.awt.event.KeyEvent evt,
	 * javax.swing.JTextField tf, int tamanho) {
	 *
	 * if (evt.getKeyChar() == ',') evt.setKeyChar('.'); // troca o caracter "," //
	 * por "." if (evt.getKeyChar() != KeyEvent.VK_BACK_SPACE) { // deixa o
	 * usuario // precionar // backspace if (evt.getSource().equals(tf)) if
	 * (tf.getText().length() > tamanho) {
	 * tf.setText(tf.getText().concat(Character.toString(evt.getKeyChar())));
	 * evt.setKeyChar(' '); tf.transferFocus(); } else if (tf.getText().length() >=
	 * tamanho) { evt.setKeyChar(' '); evt.consume(); } } if (evt.getKeyChar() ==
	 * KeyEvent.VK_ENTER) // altera o focus caso o usuario // tecle // enter;
	 * tf.transferFocus(); }
	 */
	/*
	 * Procedimento que altera os valores de variáveis e objetos da janela
	 * relacionado com a imagem para mapa de atenuação.
	 */
  private void cbNaoStateChanged (java.awt.event.ItemEvent evt) {
    
    if (cbNao.getState ()) {
      imgMapaAtt = null;
      cbSim.setState (false);
    } else {
      cbSim.setState (true);
    }
  }
  
  private void cbSimStateChanged (java.awt.event.ItemEvent evt) {
    
    if (cbSim.getState ()) {
      cbNao.setState (false);
    } else {
      imgMapaAtt = null;
      cbNao.setState (true);
    }
  }
  
  private boolean AbrirImg () {
    
    boolean repetir = true;
    boolean repetirMapa = true;
    ImagePlus[] imagens = null;
    imgOrig = null;
    imgMapaAtt = null;
    while (repetir) {
      setVisible (false);
      imagens = AbrirImagem.Abrir_ML_EM (IJ.getInstance (), debugAtivo,
	cbSim.getState ());
      imgOrig = imagens[0];
      imgMapaAtt = imagens[1];
      if (imgOrig == null)
	if (IJ.showMessageWithCancel ("Erro",
	"Nao foi possivel abrir a imagem, Repetir?"))
	  repetir = true;
	else {
	repetir = false;
	setVisible (true);
	return false;
	} else {
	repetir = false;
	if (cbNao.getState ())
	  setVisible (true);
	}
    }
    if (cbSim.getState ())
      while (repetirMapa) {
      if (imgMapaAtt == null)
	if (IJ
	.showMessageWithCancel ("Erro",
	"Nao foi possivel abrir o Mapa de Atenuação, Repetir?"))
	  repetirMapa = true;
	else {
	repetirMapa = false;
	setVisible (true);
	return false;
	} else {
	setVisible (true);
	return true;
	}
      }
    return (imgOrig != null);
  }
  
  private void Reconstruir () {
    
    ImagePlus imgFinal = null;
    if (AbrirImg ()) {
      
      long startTime = System.currentTimeMillis ();
      tamanho_imagem = imgOrig.getWidth ();
      type = imgOrig.getType ();
      if ((tamanho_imagem < 0) || !(Funcoes.isPowerOf2 (tamanho_imagem))) {
	IJ.showMessage (Funcoes.MENSAGEM_IMAGEM_NAO_COMPATIVEL);
	return;
      }
      if (cbNao.getState ()) {
	if (type == ImagePlus.GRAY32)
	  imgFinal = Reconstruir_32bits (imgOrig);
	else if (type == ImagePlus.COLOR_RGB)
	  imgFinal = Reconstruir_RGB (imgOrig);
	
	imgFinal.setTitle (imgFinal.getShortTitle () + " (MLEM - "
	  + tfNumIter.getText ()
	  + " Iteracoes; Sem correcao de atenuacao)");
      } else if (cbSim.getState ()) {
	if (type == ImagePlus.GRAY32)
	  imgFinal = Reconstruir_32bits_ATT (imgOrig);
	else if (type == ImagePlus.COLOR_RGB)
	  imgFinal = Reconstruir_RGB_ATT (imgOrig);
	
	imgFinal.setTitle (imgFinal.getShortTitle () + " (MLEM -"
	  + tfNumIter.getText ()
	  + " Iteracoes; Com correcao de atenuacao)");
      }
      imgFinal.show ();
      IJ.showTime (imgOrig, startTime, "MLEM - "
	+ Integer.parseInt (tfNumIter.getText ()) + " iteracoes. ");
    }
  }
  
  private ImagePlus Reconstruir_32bits (ImagePlus imgOrig) {
    
    float afImgOrig[], afSinograma[];
    float afRatio[], afFinal[];
    int num_stacks = imgOrig.getStackSize ();
    ImagePlus imgFinal, imgSino;
    ImageStack is_imagemFinal, is_imagemSino;
    
    is_imagemSino = new ImageStack (imgOrig.getWidth (), imgOrig.getHeight ());
    is_imagemFinal = new ImageStack (tamanho_imagem, tamanho_imagem);
    
//        FBP_ FBP = new FBP_(imgOrig);
//        ImagePlus imgReconstruidaFBP = FBP.FBP_Reconstruir(FBP_.FILTRO_RAMPA);
    ImagePlus imgReconstruidaFBP = EstimativaInicial (imgOrig);
    // ImagePlus imgReconstruidaFBP = FBP.FBP_Reconstruir(0);
    // float[] fimgBP =
    // (float[])imgReconstruidaBP.getProcessor().getPixels();
    
    int numIterTotal = Integer.parseInt (tfNumIter.getText ());
    for (int stack_count = 1; stack_count <= num_stacks; stack_count++) {
      
      ImageProcessor ipEstInicial = imgReconstruidaFBP.getImageStack ()
      .getProcessor (stack_count);
      for (int numIteracoes = 0; numIteracoes < numIterTotal; numIteracoes++) {
	
	Projetor projetor = new Projetor (ipEstInicial, imgOrig
	  .getHeight ());
	ImageProcessor ipSinograma = projetor.projetar (false);
	ipSinograma.resetMinAndMax ();
	/**
	 * if (debugAtivo) { ImagePlus implus = new ImagePlus("MLEM -
	 * Imagem Sinograma; iteracao "+(numIteracoes+1),ipSinograma);
	 * implus.show(); }
	 */
	is_imagemSino.addSlice ("MLEM - Imagem Sinograma; iteracao "
	  + (numIteracoes + 1), ipSinograma, numIteracoes);
	
	IJ.showStatus ("MLEM - Reconstruindo... Stack " + stack_count
	  + ", Iteracao " + (numIteracoes + 1));
	afImgOrig = (float[]) imgOrig.getImageStack ().getProcessor (
	  stack_count).getPixels ();
	afSinograma = (float[]) ipSinograma.getPixels ();
	afRatio = new float[afImgOrig.length];
	afFinal = new float[afImgOrig.length];
	for (int a = 0; a < afImgOrig.length; a++)
	  if (afSinograma[a] > 0)
	    afRatio[a] = afImgOrig[a] / afSinograma[a];
	  else
	    afRatio[a] = 0;
	
	FloatProcessor ipRatio = new FloatProcessor (imgOrig.getWidth (),
	  imgOrig.getHeight ());
	ipRatio.setPixels (afRatio);
	
	Retroprojetor retroprojetor = new Retroprojetor (ipRatio);
	
	ImageProcessor ipReconstruidaTemp = retroprojetor
	  .retroProjetar ();
	float[] afipReconstruidaTemp = (float[]) ipReconstruidaTemp
	  .getPixels ();
	float[] afipEstInicial = (float[]) ipEstInicial.getPixels ();
	afFinal = afipReconstruidaTemp;
	if (numIteracoes > 1)
	  for (int i = 0; i < afipEstInicial.length; i++)
	    afFinal[i] = (afipEstInicial[i] * afipReconstruidaTemp[i]);
	ipEstInicial = ipReconstruidaTemp;
      }
      ipEstInicial.resetMinAndMax ();
      is_imagemFinal.addSlice ("MLEM - Reconstrucao Simples",
	ipEstInicial, stack_count - 1);
      
      if (debugAtivo) {
	imgSino = new ImagePlus (imgOrig.getShortTitle ()
	+ " Imagem Sinograma; stack " + num_stacks,
	  is_imagemSino);
	imgSino.show ();
      }
    }
    
    imgFinal = new ImagePlus (imgOrig.getShortTitle ()
    + " (MLEM - Imagem Reconstruida)", is_imagemFinal);
    return imgFinal;
  }
  
  private ImagePlus Reconstruir_32bits_ATT (ImagePlus imgOrig) {
    
    float afImgOrig[], afSinograma[];
    float afRatio[], afFinal[];
    int num_stacks = imgOrig.getStackSize ();
    ImagePlus imgFinal, imgSino;
    ImageStack is_imagemFinal, is_imagemSino;
    
    is_imagemSino = new ImageStack (imgOrig.getWidth (), imgOrig.getHeight ());
    is_imagemFinal = new ImageStack ((int) tamanho_imagem,
      (int) tamanho_imagem);
    
//        FBP_ FBP = new FBP_(imgOrig);
//        ImagePlus imgReconstruidaFBP = FBP.FBP_Reconstruir(FBP_.FILTRO_RAMPA);
    ImagePlus imgReconstruidaFBP = EstimativaInicial (imgOrig);
    
    int numIterTotal = Integer.parseInt (tfNumIter.getText ());
    for (int stack_count = 1; stack_count <= num_stacks; stack_count++) {
      
      ImageProcessor ipEstInicial = imgReconstruidaFBP.getImageStack ()
      .getProcessor (stack_count);
      ImageProcessor ipImgMapa = imgMapaAtt.getImageStack ().getProcessor (
	stack_count);
      for (int numIteracoes = 0; numIteracoes < numIterTotal; numIteracoes++) {
	
	Projetor projetor = new Projetor (ipEstInicial, ipImgMapa,
	  imgOrig.getHeight ());
	ImageProcessor ipSinograma = projetor.projetar (true);
	ipSinograma.resetMinAndMax ();
	// if (numIteracoes==(numIterTotal-1))
	is_imagemSino.addSlice ("MLEM - Imagem Sinograma iteracao "
	  + numIteracoes, ipSinograma, numIteracoes);
	
	ij.IJ.showStatus ("MLEM - Reconstruindo... Stack " + stack_count
	  + ", Iteracao " + (numIteracoes + 1));
	afImgOrig = (float[]) imgOrig.getImageStack ().getProcessor (
	  stack_count).getPixels ();
	afSinograma = (float[]) ipSinograma.getPixels ();
	afRatio = new float[afImgOrig.length];
	afFinal = new float[afImgOrig.length];
	for (int a = 0; a < afImgOrig.length; a++)
	  if (afSinograma[a] > 0)
	    afRatio[a] = afImgOrig[a] / afSinograma[a];
	  else
	    afRatio[a] = 0;
	
	FloatProcessor ipRatio = new FloatProcessor (imgOrig.getWidth (),
	  imgOrig.getHeight ());
	ipRatio.setPixels (afRatio);
	Retroprojetor retroprojetor = new Retroprojetor (ipRatio);
	
	ImageProcessor ipReconstruidaTemp = retroprojetor
	  .retroProjetar ();
	ipReconstruidaTemp.resetMinAndMax ();
	float[] afipReconstruidaTemp = (float[]) ipReconstruidaTemp
	  .getPixels ();
	float[] afipEstInicial = (float[]) ipEstInicial.getPixels ();
	afFinal = afipReconstruidaTemp;
	// if (numIteracoes > 1)
	for (int i = 0; i < afipEstInicial.length; i++)
	  afFinal[i] = (afipEstInicial[i] * afipReconstruidaTemp[i]);
	ipEstInicial = ipReconstruidaTemp;
      }
      ipEstInicial.resetMinAndMax ();
      is_imagemFinal.addSlice ("MLEM - Reconstrucao Simples",
	ipEstInicial, stack_count - 1);
    }
    imgSino = new ImagePlus (imgOrig.getShortTitle ()
    + " (MLEM - Imagem Sinograma)", is_imagemSino);
    imgFinal = new ImagePlus (imgOrig.getShortTitle ()
    + " (MLEM - Imagem Reconstruida)", is_imagemFinal);
    
    if (debugAtivo)
      imgSino.show ();
    
    return imgFinal;
  }
  
  private ImagePlus Reconstruir_RGB (ImagePlus imgOrig) {
    
    ImagePlus imagemTemp = null;
    ImagePlus imagemRGBReconstruida = null, imagemRGBStackReconstruida = null;
    ImageStack is_imagemFinal = new ImageStack ((int) tamanho_imagem,
      (int) tamanho_imagem);
    
    isRGBFiltrada = new ImageStack (imgOrig.getWidth (), imgOrig.getHeight ());
    
    int num_stacks = imgOrig.getStackSize ();
    for (int stack_count = 1; stack_count <= num_stacks; stack_count++) {
      
      imagemTemp = new ImagePlus ("", imgOrig.getImageStack ()
      .getProcessor (stack_count));
      
      ImageConverter imgConvRGBStack = new ImageConverter (imagemTemp);
      imgConvRGBStack.convertToRGBStack ();
      
      StackConverter ConvGray32 = new StackConverter (imagemTemp);
      ConvGray32.convertToGray32 ();
      // ipGlobal = imagemTemp.getImageStack().getProcessor(stack_count);
      imagemRGBStackReconstruida = Reconstruir_32bits (imagemTemp);
      
      StackConverter ConvGray8 = new StackConverter (
	imagemRGBStackReconstruida);
      ConvGray8.convertToGray8 ();
      
      ImageConverter ConvStackToRGB = new ImageConverter (
	imagemRGBStackReconstruida);
      ConvStackToRGB.convertRGBStackToRGB ();
      
      is_imagemFinal.addSlice ("", imagemRGBStackReconstruida
	.getProcessor (), stack_count - 1);
      
    }
    imagemRGBReconstruida = new ImagePlus (imgOrig.getShortTitle ()
    + " (MLEM - Imagem Reconstruida)", is_imagemFinal);
    
    return imagemRGBReconstruida;
  }
  
  private ImagePlus Reconstruir_RGB_ATT (ImagePlus imgOrig) {
    
    // ImagePlus imagemRGBStack, imagem = null;
    ImagePlus imagemTemp = null;
    ImagePlus imagemRGBReconstruida = null, imagemRGBStackReconstruida = null;
    ImageStack is_imagemFinal = new ImageStack ((int) tamanho_imagem,
      (int) tamanho_imagem);
    
    isRGBFiltrada = new ImageStack (imgOrig.getWidth (), imgOrig.getHeight ());
    
    int num_stacks = imgOrig.getStackSize ();
    for (int stack_count = 1; stack_count <= num_stacks; stack_count++) {
      
      imagemTemp = new ImagePlus ("", imgOrig.getImageStack ()
      .getProcessor (stack_count));
      
      ImageConverter imgConvRGBStack = new ImageConverter (imagemTemp);
      imgConvRGBStack.convertToRGBStack ();
      
      StackConverter ConvGray32 = new StackConverter (imagemTemp);
      ConvGray32.convertToGray32 ();
      
      imagemRGBStackReconstruida = Reconstruir_32bits_ATT (imagemTemp);
      
      StackConverter ConvGray8 = new StackConverter (
	imagemRGBStackReconstruida);
      ConvGray8.convertToGray8 ();
      
      ImageConverter ConvStackToRGB = new ImageConverter (
	imagemRGBStackReconstruida);
      ConvStackToRGB.convertRGBStackToRGB ();
      
      is_imagemFinal.addSlice ("", imagemRGBStackReconstruida
	.getProcessor (), stack_count - 1);
      
    }
    imagemRGBReconstruida = new ImagePlus (imgOrig.getShortTitle ()
    + " (MLEM - Imagem Reconstruida; com mapa de Atenuacao)",
      is_imagemFinal);
    
    return imagemRGBReconstruida;
  }
  
  private ImagePlus EstimativaInicial (ImagePlus iplus) {
    ImageStack is = new ImageStack ((int) tamanho_imagem,(int) tamanho_imagem);
    
    for(int sl=1;sl <= iplus.getNSlices ();sl++) {
      
      float[] aPixels = (float[]) iplus.getImageStack ().getProcessor (sl).getPixels ();
      float somaPixels = 0;
      int xmax, fovmax = 0, mediaPixels = 0;
      FloatProcessor ipEstInicial = new FloatProcessor (tamanho_imagem,tamanho_imagem);
      xmax = (tamanho_imagem - 2) / 2;
      for (int a = 0; a < aPixels.length; a++)
	somaPixels = somaPixels + aPixels[a];
      mediaPixels = (int) somaPixels / aPixels.length;
      for (int y = -xmax; y <= xmax; y++) {
	fovmax = Funcoes.calculaFOV (xmax, y);
	for (int x = -fovmax; x <= fovmax; x++)
	  ipEstInicial.putPixelValue (x + (tamanho_imagem / 2) -1, y + (tamanho_imagem / 2) - 1, mediaPixels);
      }
      is.addSlice ("Média das Contagens "+sl,ipEstInicial);
    }
    ImagePlus EstInicial = new ImagePlus ("Estimativa Inicial Média das Contagens", is);
    if (debugAtivo) {
      EstInicial.show ();
    }
    return EstInicial;
  }
  
  private javax.swing.JButton btnReconstruir;
  
  private java.awt.Checkbox cbNao;
  
  private java.awt.Checkbox cbSim;
  
  private java.awt.Label Label1;
  
  private java.awt.Label Label2;
  
  private java.awt.Label Label3;
  
  private java.awt.Label Label4;
  
  private java.awt.Panel pnlGeral;
  
  private javax.swing.JPanel pnlCorrecaoAtt;
  
  private javax.swing.JPanel pnlNumIter;
  
  private javax.swing.JTextField tfNumIter;
  
}