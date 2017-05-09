package target;
import funcoesNM.*;
import funcoesNM.AbrirImagem;
import nucMed.*;
import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.gui.PlotWindow;
import ij.plugin.filter.Duplicater;
import ij.process.FloatProcessor;
import ij.process.ImageConverter;
import ij.process.ImageProcessor;
import ij.process.StackConverter;

import java.awt.Color;
import java.awt.event.KeyEvent;

/**
 * Classe principal. Contém a criação da janela do plugin para reconstrução de
 * imagem através do método <i>Filtered BackProjection</i>.
 *
 * @author Marcus Vinicius da Silva Costa, Michele Alberton Andrade.
 * @version <B>1.7</B>
 */

public class FBP_ extends javax.swing.JFrame implements
  ij.plugin.PlugIn {
  
  private static final long serialVersionUID = 1L;
  
  public static ImagePlus imp;
  
  public static ij.ImageStack ims;
  
  public static ij.ImageStack isRGBFiltrada = null;
  
  // mensagens e constantes de valor String
  public static final String NOME_PROGRAMA = "FBP";
  
  public static final String LABEL_FILTRO = "Filtro";
  
  public static final String LABEL_SELECAO_JANELA = "Selecao de janela";
  
  public static final String LABEL_PARAMETRO = "Parametro";
  
  public static final String LABEL_DIVISAO_FILTRO = " '' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' '";
  
  public static final String LABEL_DIVISAO_PARAMETRO = "*************************";
  
  public static final String LABEL_PARAMETRO_FREQUENCIA_BUTTERWORTH = "Frequencia Critica (fc): ";
  
  public static final String LABEL_PARAMETRO_FREQUENCIA = "Frequencia de Corte (fc): ";
  
  public static final String LABEL_PARAMETRO_ORDEM_BUTTERWORTH = "Ordem do filtro (N): ";
  
  public static final String LABEL_BUTTON_VISUALIZAR_FILTRO = "Visualizar Filtro";
  
  public static final String LABEL_BUTTON_RECONSTRUIR = "Reconstruir";
  
  public static final String TITULO_IMAGEM_SIMPLES = "Retroprojecao Simples";
  
  public static final String TITULO_IMAGEM_RAMPA = "Rampa";
  
  public static final String TITULO_IMAGEM_BUTTERWORTH = "Rampa_Butterworth";
  
  public static final String TITULO_IMAGEM_SHEPP_LOGAN = "Rampa_Shepp-Logan";
  
  public static final String TITULO_IMAGEM_HAMMING = "Rampa_Hamming";
  
  // constantes globais de valor inteiro
  public static final int JANELA_WIDTH_SIMPLES = 200;
  
  public static final int TAMANHO_TEXT_FIELD_FC = 4;
  
  public static final int TAMANHO_TEXT_FIELD_N = 4;
  
  public static final int JANELA_RETROPROJECAO_SIMPLES_HEIGHT = 280;
  
  public static final int JANELA_RAMPA_HEIGHT = 370;
  
  public static final int JANELA_RAMPA_WIDTH = 200;
  
  public static final int JANELA_BUTTERWORTH_HEIGHT = 410;
  
  public static final int JANELA_BUTTERWORTH_WIDTH = 200;
  
  public static final int JANELA_SHEPP_LOGAN_HEIGHT = 370;
  
  public static final int JANELA_SHEPP_LOGAN_WIDTH = 200;
  
  public static final int JANELA_HAMMING_HEIGHT = 370;
  
  public static final int JANELA_HAMMING_WIDTH = 200;
  
  public static final int TELA_RETROPROJECAO_SIMPLES = 0;
  
  public static final int TELA_RAMPA = 1;
  
  public static final int TELA_BUTTERWORTH = 2;
  
  public static final int TELA_SHEPP_LOGAN = 3;
  
  public static final int TELA_HAMMING = 4;
  
  public static final int FILTRO_RAMPA = 1;
  
  public static final int FILTRO_BUTTERWORTH = 2;
  
  public static final int FILTRO_SHEPP_LOGAN = 3;
  
  public static final int FILTRO_HAMMING = 4;
  
  // VARIAVEIS UTILIZADAS PARA RECONHECIMENTO DE IMAGEM E TELA SELECIONADA
  public static int tamanho_imagem = 0;
  
  public static int type;
  
  public static int janela_ativa = TELA_RAMPA; // Variável da janela
  
  // inicial
  
  // que deve ser montada,
  // iniciada em tipo de janela
  // para filtro Rampa.
  
  public static String TITULO_IMAGEM;
  
  public static String TITULO_IMAGEM_FILTRADA;
  
        /*
         * Variável utilizada somente quando o FBP_ é chamado do ML_EM_.
         */
  public static boolean execucao_grafica = true;
  
/**
 * Variável utilizada para ativar a exibição de imagens e mensagens, para
 * testes.
 */
//  public static boolean debugAtivo = false;
  
/**
 * Função herdada da classe PluginFilter do ImageJ.
 */
/*  public int setup(String arg, ImagePlus imp) {
    if ( arg == "about" )
      IJ.showMessage("Plugin para Reconstrução de imagens Tomográficas\n"+
        "Desenvido por NIMed - PUCRS - Brasil");
    FBP_.imp = imp;
    return DOES_ALL;
  }
 */
  /**
   * Função herdada da classe PlugIn do ImageJ.
   */
  public void run(String str) {
    if ( str.equals( "about" ) )
      IJ.showMessage("\n"+
                     "        Desenvolvido por NIMed - PUCRS - Brasil\n "+
                     "\n     Homepage http://www.pucrs.br/fisica/nucmed ");
    else {
      imp = IJ.getImage();
      tamanho_imagem = imp.getWidth();
      type = imp.getType();
      if ((tamanho_imagem > 0) && (Funcoes.isPowerOf2(tamanho_imagem))) {
        IJ.register(FBP_.class);
        setVisible(true);
      } else {
        IJ.showMessage(Funcoes.MENSAGEM_IMAGEM_NAO_COMPATIVEL);
      }
    }
  }
  
  /**
   * Cria e inicia a janela do FBP_
   */
  public FBP_() {
    initComponents();
    tela_parametros(TELA_RAMPA, false);
    setLocation(250, 100);
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    
    System.out.println("FBP OK");
  }
  
  /**
   * Cria e inicia o FBP_ para ser chamado pelo ML_EM_ utilizado para
   * estimativa inicial da reconstrução.
   *
   * @param img :
   *            recebe a imagem do sinograma selecionada para a reconstrução
   */
  public FBP_(ImagePlus img) {
    
    imp = img;
    tfParametro1 = new javax.swing.JTextField();
    tfParametro2 = new javax.swing.JTextField();
    tfParametro1.setText("0.5");
    tfParametro2.setText("0.5");
    cbRampa = new java.awt.Checkbox("", true);
    cbHamming = new java.awt.Checkbox("", false);
    cbShepp = new java.awt.Checkbox("", false);
    cbButter = new java.awt.Checkbox("", false);
    
    execucao_grafica = false;
    TITULO_IMAGEM = TITULO_IMAGEM_RAMPA;
    tamanho_imagem = img.getWidth();
  }
  
  /**
   * Função que executa a reconstrução da imagem sem a janela
   *
   * @param tipoFiltro
   *            Recebe o tipo de filtro a ser aplicado na reconstrução.
   * @return Retorna um ImagePlus com a imagem Reconstruída.
   */
  public ImagePlus FBP_Reconstruir(int tipoFiltro) {
    
    ImagePlus imagemOrig = null;
    ImageStack is_imagemReconstruida = null;
    is_imagemReconstruida = new ImageStack(tamanho_imagem, tamanho_imagem);
    int num_stacks = imp.getStackSize();
    if (tipoFiltro != 0) {
      Complexo[] filtro = gerar_filtro(true, FILTRO_RAMPA);
      ImagePlus imagemFiltrada = filtrar(imp, filtro);
      if (Funcoes.debug)
        imagemFiltrada.show();
      imagemOrig = imagemFiltrada;
    } else
      imagemOrig = imp;
    
    for (int stack_count = 1; stack_count <= num_stacks; stack_count++) {
      
      ImageProcessor ip_imgOrig = imagemOrig.getImageStack()
      .getProcessor(stack_count);
      Backprojection back_proj = new Backprojection(ip_imgOrig);
      ImageProcessor ip_imagemReconstruida = back_proj.Reconstruir();
      float[] ipFinal = (float[]) ip_imagemReconstruida.getPixels();
      for (int a = 0; a < ipFinal.length; a++)
        if (ipFinal[a] < 0)
          ipFinal[a] = 0;
      ip_imagemReconstruida.resetMinAndMax();
      is_imagemReconstruida.addSlice("Imagem Reconstruida",
        ip_imagemReconstruida, stack_count - 1);
      IJ.showStatus("Filtrando: "
        + (int) (((float) stack_count / (float) num_stacks) * 100)
        + "%");
    }
    ImagePlus imagemReconstruida = new ImagePlus("Imagem Reconstruida FBP",
      is_imagemReconstruida);
    imagemReconstruida.setTitle(imp.getShortTitle() + " " + TITULO_IMAGEM);
    // Funcoes.Rotar(imagemReconstruida);
    if (Funcoes.debug)
      imagemReconstruida.show();
    return imagemReconstruida;
  }
  
  /**
   * Criação e inicialização de todos os objetos da janela FBP_ incluindo as
   * suas chamadas de funções
   */
  private void initComponents() {
    
    panel1 = new javax.swing.JPanel();
    cbButter = new java.awt.Checkbox();
    cbRampa = new java.awt.Checkbox();
    cbShepp = new java.awt.Checkbox();
    cbHamming = new java.awt.Checkbox();
    lblFiltro = new javax.swing.JLabel();
    lblDivParam = new javax.swing.JLabel();
    lblDivFiltro = new javax.swing.JLabel();
    lblSelecao_Janela = new javax.swing.JLabel();
    lblParametro1 = new javax.swing.JLabel();
    lblParametro2 = new javax.swing.JLabel();
    lblParametro = new javax.swing.JLabel();
    tfParametro1 = new javax.swing.JTextField();
    tfParametro2 = new javax.swing.JTextField();
    btnVisualizarFiltro = new javax.swing.JButton();
    btnReconstruir = new javax.swing.JButton();
    
    getContentPane().setLayout(
      new javax.swing.BoxLayout(getContentPane(),
      javax.swing.BoxLayout.X_AXIS));
    setTitle(NOME_PROGRAMA);
    setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    // setLocationByPlatform(true);
    setResizable(false);
    panel1.setLayout(null);
    // panel1.setBackground(java.awt.Color.lightGray);
    // cbButter.setBackground(java.awt.Color.lightGray);
    cbButter.setLabel("Butterworth");
    cbButter.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        selecao_butter();
      }
    });
    panel1.add(cbButter);
    cbButter.setBounds(30, 100, 105, 19);
    
    cbRampa.setLabel("Rampa");
    cbRampa.setState(true);
    cbRampa.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        selecao_rampa();
      }
    });
    panel1.add(cbRampa);
    cbRampa.setBounds(30, 30, 67, 19);
    
    cbShepp.setLabel("Shepp-Logan");
    cbShepp.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        selecao_shepp();
      }
    });
    panel1.add(cbShepp);
    cbShepp.setBounds(30, 130, 103, 19);
    
    cbHamming.setLabel("Hamming");
    cbHamming.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        selecao_hamming();
      }
    });
    panel1.add(cbHamming);
    cbHamming.setBounds(30, 160, 81, 19);
    
    lblFiltro.setHorizontalTextPosition(javax.swing.JLabel.LEFT);
    // lblFiltro.setFont(new java.awt.Font("Dialog", 1, 12));
    lblFiltro.setText(LABEL_FILTRO);
    panel1.add(lblFiltro);
    lblFiltro.setBounds(10, 10, 120, 19);
    
    lblDivParam.setHorizontalTextPosition(javax.swing.JLabel.CENTER);
    lblDivParam.setHorizontalAlignment(javax.swing.JLabel.CENTER);
    // lblDivParam.setFont(new java.awt.Font("Dialog", 1, 10));
    lblDivParam.setText(LABEL_DIVISAO_PARAMETRO);
    panel1.add(lblDivParam);
    lblDivParam.setBounds(0, 190, 190, 10);
    
    lblDivFiltro.setHorizontalTextPosition(javax.swing.JLabel.CENTER);
    lblDivFiltro.setHorizontalAlignment(javax.swing.JLabel.CENTER);
    // lblDivFiltro.setFont(new java.awt.Font("Dialog", 1, 10));
    lblDivFiltro.setText(LABEL_DIVISAO_FILTRO);
    panel1.add(lblDivFiltro);
    lblDivFiltro.setBounds(0, 60, 190, 10);
    
    lblSelecao_Janela.setHorizontalTextPosition(javax.swing.JLabel.LEFT);
    // lblSelecao_Janela.setFont(new java.awt.Font("Dialog", 1, 12));
    lblSelecao_Janela.setText(LABEL_SELECAO_JANELA);
    panel1.add(lblSelecao_Janela);
    lblSelecao_Janela.setBounds(10, 70, 120, 19);
    
    lblParametro1.setHorizontalTextPosition(javax.swing.JLabel.LEFT);
    lblParametro1.setText("lblParametro1");
    panel1.add(lblParametro1);
    lblParametro1.setBounds(10, 230, 170, 19);
    
    lblParametro2.setHorizontalTextPosition(javax.swing.JLabel.LEFT);
    lblParametro2.setText("lblParametro2");
    panel1.add(lblParametro2);
    lblParametro2.setBounds(10, 270, 170, 19);
    
    lblParametro.setHorizontalTextPosition(javax.swing.JLabel.LEFT);
    // lblParametro.setFont(new java.awt.Font("Dialog", 1, 12));
    lblParametro.setText(LABEL_PARAMETRO);
    panel1.add(lblParametro);
    lblParametro.setBounds(10, 200, 170, 19);
    
    // tfParametro1.setBackground(new java.awt.Color(255, 255, 255));
    // tfParametro1.setFont(new java.awt.Font("Dialog", 1, 12));
    tfParametro1.setText("tfParametro1");
    tfParametro1.setColumns(5);
    panel1.add(tfParametro1);
    tfParametro1.setBounds(90, 250, 89, 19);
    tfParametro1.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(java.awt.event.FocusEvent evt) {
        Focused(evt, tfParametro1);
      }
    });
    
    // tfParametro2.setBackground(new java.awt.Color(255, 255, 255));
    // tfParametro2.setFont(new java.awt.Font("Dialog", 1, 12));
    tfParametro2.setText("tfParametro2");
    panel1.add(tfParametro2);
    tfParametro2.setBounds(90, 290, 89, 19);
    tfParametro2.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(java.awt.event.FocusEvent evt) {
        Focused(evt, tfParametro2);
      }
    });
    
    // btnVisualizarFiltro.setFont(new java.awt.Font("SansSerif", 1, 12));
    btnVisualizarFiltro.setText(LABEL_BUTTON_VISUALIZAR_FILTRO);
    btnVisualizarFiltro
      .addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        visualizar_filtro(evt);
      }
    });
    panel1.add(btnVisualizarFiltro);
    btnVisualizarFiltro.setBounds(35, 280, 125, 24);
    
    // btnReconstruir.setFont(new java.awt.Font("Dialog", 1, 12));
    btnReconstruir.setText(LABEL_BUTTON_RECONSTRUIR);
    btnReconstruir.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        Reconstruir(evt);
      }
    });
    panel1.add(btnReconstruir);
    btnReconstruir.setBounds(25, 380, 125, 24);
    btnReconstruir.setVisible(false);
    setVisible(false);
    getContentPane().add(panel1);
    
    // pack();
  }
  
  /**
   * Procedimento chamado quando solicitado pelo usuário a visualização do
   * filtro para a reconstrução da imagem.
   *
   * @param evt :
   *            parâmetro tratado pelo JAVA.
   */
  private void visualizar_filtro(java.awt.event.ActionEvent evt) {
    
    if (tamanho_imagem > 1) {
      Complexo filtro[];
      filtro = gerar_filtro((cbRampa.getState()), janela_ativa);
      gera_grafico(filtro, (int) tamanho_imagem - 1);
      String tf1 = tfParametro1.getText();
      String tf2 = tfParametro2.getText();
      if (cbRampa.getState())
        tela_parametros(janela_ativa, true);
      else
        tela_parametros(TELA_RETROPROJECAO_SIMPLES, true);
      tfParametro1.setText(tf1);
      tfParametro2.setText(tf2);
    } else
      IJ.error(Funcoes.MENSAGEM_IMAGEM_NAO_COMPATIVEL);
  }
  
  /**
   * Função chamada pelos procedimentos de Recostrução, serve para confirmar a
   * imagem selecionada para a reconstrução. Chama a classe AbrirImagem.
   *
   * @return Retorna se a imagem aberta pode ser usada ou não para a
   *         reconstrução.
   */
  private boolean AbrirImg() {
    
    AbrirImagem abrir =  new AbrirImagem();
    boolean repetir = true;
    imp = null;
    while (repetir) {
      setVisible(false);
      imp = abrir.Abrir_FBP();
      if (imp == null)
        if (IJ.showMessageWithCancel("Erro",
        "Não foi possível abrir a imagem, Repetir?"))
          repetir = true;
        else {
        repetir = false;
        setVisible(true);
        return false;
        }
      
      else {
        setVisible(true);
        return true;
      }
    }
    return (imp != null);
  }
  
  /**
   * Procedimento chamado quando o usuário clicar no botão para reconstrução
   *
   * @param evt :
   *            Par�metro tratado pelo JAVA.
   */
  private void Reconstruir(java.awt.event.ActionEvent evt) {
    
    ImagePlus imagemReconstruida = null;
    if (AbrirImg()) {
      
      long startTime = System.currentTimeMillis();
      tamanho_imagem = imp.getWidth();
      type = imp.getType();
      
      if (type == ImagePlus.GRAY32) {
        imagemReconstruida = Reconstruir_FBP_32bits(imp);
        // Funcoes.Rotar(imagemReconstruida);
      } else if (type == ImagePlus.COLOR_RGB) {
        imagemReconstruida = Reconstruir_FBP_RGB(imp);
        if (janela_ativa != TELA_RETROPROJECAO_SIMPLES) {
          ImagePlus imgRGBFiltrada = new ImagePlus(Funcoes
            .AlteraNomeImagem(TITULO_IMAGEM_FILTRADA),
            isRGBFiltrada);
          imgRGBFiltrada.show();
        }
        // Funcoes.Rotar(imagemReconstruida);
      }
      imagemReconstruida.show();
      IJ.showTime(imp, startTime, " Tempo de Execu��o do FBP ");
      if (Funcoes.ativarMSE)
        Funcoes.calculaMSE();
    }
    // selecao_rampa ();
  }
  
  /**
   * Função chamada pelo Reconstrir, quando a imagem informada for do tipo
   * RGB.
   *
   * @param img :
   *            imagem informada do sinograma para reconstru��o
   * @return Retorna a imagem Reconstruida.
   */
  private ImagePlus Reconstruir_FBP_RGB(ImagePlus img) {
    
    // ImagePlus imagem,imagemRGBStack = null;
    ImagePlus imagemTemp = null;
    ImagePlus imagemRGBReconstruida = null, imagemRGBStackReconstruida = null;
    ImageStack is_imagemFinal = new ImageStack((int) tamanho_imagem,
      (int) tamanho_imagem);
    
    isRGBFiltrada = new ImageStack(img.getWidth(), img.getHeight());
    
    int num_stacks = img.getStackSize();
    for (int stack_count = 1; stack_count <= num_stacks; stack_count++) {
      
      imagemTemp = new ImagePlus("", img.getImageStack().getProcessor(
        stack_count));
      
      ImageConverter imgConvRGBStack = new ImageConverter(imagemTemp);
      imgConvRGBStack.convertToRGBStack();
      
      StackConverter ConvGray32 = new StackConverter(imagemTemp);
      ConvGray32.convertToGray32();
      
      imagemRGBStackReconstruida = Reconstruir_FBP_32bits(imagemTemp);
      
      StackConverter ConvGray8 = new StackConverter(
        imagemRGBStackReconstruida);
      ConvGray8.convertToGray8();
      
      ImageConverter ConvStackToRGB = new ImageConverter(
        imagemRGBStackReconstruida);
      ConvStackToRGB.convertRGBStackToRGB();
      
      is_imagemFinal.addSlice("", imagemRGBStackReconstruida
        .getProcessor(), stack_count - 1);
      
    }
    imagemRGBReconstruida = new ImagePlus(Funcoes
      .AlteraNomeImagem(TITULO_IMAGEM_FILTRADA), is_imagemFinal);
    
    return imagemRGBReconstruida;
  }
  
  /**
   * Função chamada pelo Reconstrir, quando a imagem informada for do tipo
   * 32bits.
   *
   * @param img :
   *            imagem informada do sinograma para reconstru��o
   * @return Retorna a imagem Reconstruida.
   */
  private ImagePlus Reconstruir_FBP_32bits(ImagePlus img) {
    
    Complexo filtro[];
    // float SinoFiltrado[];
    
    ImagePlus imagemFinal, imagemFiltrada;
    ImageStack is_imagemFinal = null;
    ImageProcessor ip_imgOrig = null;
    int num_stacks = 0, stack_count = 0;
    // float nCompleto;
    String img_title;
    String img_title_temp;
    
    if (janela_ativa == TELA_RETROPROJECAO_SIMPLES) {
      num_stacks = img.getStackSize();
      is_imagemFinal = new ImageStack((int) tamanho_imagem,
        (int) tamanho_imagem);
      // ip_imgOrig = img.getImageStack().getProcessor(1);
      
      for (stack_count = 1; stack_count <= num_stacks; stack_count++) {
        ip_imgOrig = img.getImageStack().getProcessor(stack_count);
        Backprojection FBP = new Backprojection(ip_imgOrig);
        ImageProcessor ip_imagemFinal = FBP.Reconstruir();
        float[] ipFinal = (float[]) ip_imagemFinal.getPixels();
        for (int a = 0; a < ipFinal.length; a++)
          if (ipFinal[a] < 0)
            ipFinal[a] = 0;
        // ip_imagemFinal.resetMinAndMax ();
        is_imagemFinal.addSlice("FBP - Reconstrucao Simples",
          ip_imagemFinal, stack_count - 1);
        
        IJ
          .showStatus("Concluido: "
          + (int) (((float) stack_count / (float) num_stacks) * 100)
          + "%");
        // is_imagemFinal.getProcessor (stack_count).resetMinAndMax ();
      }
      imagemFinal = new ImagePlus("FBP - Reconstrucao Simples",
        is_imagemFinal);
      imagemFinal.setTitle(imp.getShortTitle() + " (" + TITULO_IMAGEM
        + " Reconstru��o Simples)");
      TITULO_IMAGEM_FILTRADA = imp.getShortTitle()
      + " Reconstru��o Simples";
      return imagemFinal;
    } else {
      
      filtro = gerar_filtro(cbRampa.getState(), janela_ativa);
      imagemFiltrada = filtrar(img, filtro);
      imagemFiltrada.setTitle(Funcoes.AlteraNomeImagem(imagemFiltrada
        .getTitle()));
      if (type == ImagePlus.COLOR_RGB) {
        
        Duplicater duplicar = new Duplicater();
        ImagePlus imagemRGBFiltradaTemp = duplicar.duplicateStack(
          imagemFiltrada, imagemFiltrada.getTitle());
        
        StackConverter ConvGray8 = new StackConverter(
          imagemRGBFiltradaTemp);
        ConvGray8.convertToGray8();
        
        ImageConverter ConvRGBStackToRGB = new ImageConverter(
          imagemRGBFiltradaTemp);
        ConvRGBStackToRGB.convertRGBStackToRGB();
        
        isRGBFiltrada.addSlice("",
          imagemRGBFiltradaTemp.getProcessor(), isRGBFiltrada
          .getSize());
      } else
        imagemFiltrada.show();
      
      num_stacks = imagemFiltrada.getStackSize();
      
      is_imagemFinal = new ImageStack((int) tamanho_imagem,
        (int) tamanho_imagem);
      
      for (stack_count = 1; stack_count <= num_stacks; stack_count++) {
        
        ip_imgOrig = imagemFiltrada.getImageStack().getProcessor(
          stack_count);
        ip_imgOrig.resetMinAndMax();
        Backprojection FBP = new Backprojection(ip_imgOrig);
        ImageProcessor ip_imagemFinal = FBP.Reconstruir();
        float[] ipFinal = (float[]) ip_imagemFinal.getPixels();
        for (int a = 0; a < ipFinal.length; a++)
          if (ipFinal[a] < 0)
            ipFinal[a] = 0;
        is_imagemFinal.addSlice("", ip_imagemFinal, stack_count - 1);
        ip_imagemFinal.resetMinAndMax();
        IJ
          .showStatus("Concluido: "
          + (int) (((float) stack_count / (float) num_stacks) * 100)
          + "%");
      }
      imagemFinal = new ImagePlus("", is_imagemFinal);
      img_title = img.getTitle();
      img_title_temp = img.getShortTitle();
      while (img_title != img_title_temp) {
        img_title_temp = img.getShortTitle();
        img.setTitle(img_title_temp);
        img_title_temp = img.getShortTitle();
        img_title = img.getTitle();
      }
      if (janela_ativa == TELA_BUTTERWORTH) {
        
        TITULO_IMAGEM_FILTRADA = img.getShortTitle() + " (FBP - "
          + TITULO_IMAGEM + "; N= " + tfParametro1.getText()
          + " fc= " + tfParametro2.getText() + ")";
        TITULO_IMAGEM_FILTRADA = Funcoes
          .AlteraNomeImagem(TITULO_IMAGEM_FILTRADA);
      } else {
        TITULO_IMAGEM_FILTRADA = img.getShortTitle() + " (FBP - "
          + TITULO_IMAGEM + "; fc= " + tfParametro1.getText()
          + ")";
        TITULO_IMAGEM_FILTRADA = Funcoes
          .AlteraNomeImagem(TITULO_IMAGEM_FILTRADA);
      }
      imagemFinal.setTitle(TITULO_IMAGEM_FILTRADA);
      return imagemFinal;
    }
  }
  
  /**
   * Procedimento usado para alterar o foco do cursos em componentes TextField
   *
   * @param evt :
   *            Par�metro tratado pelo JAVA.
   * @param tf :
   *            Par�metro informa o componente selecionado.
   */
  private void Focused(java.awt.event.FocusEvent evt,
    javax.swing.JTextField tf) {
    
    tf.setCaretPosition(tf.getText().length());
  }
  
  /**
   * Procedimento utilizado para limitar o tamanho de componentes TextField.
   *
   * @param evt :
   *            Par�metro tratado pelo JAVA.
   * @param tf :
   *            Objeto que chamou a fun��o.
   * @param tamanho :
   *            Tamanho m�ximo de caracteres.
   */
  private void LimitaTextField(java.awt.event.KeyEvent evt,
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
      // tecle
      // enter;
      tf.transferFocus();
  }
  
  /**
   * Procedimento executado quando trocado o estado do ComboBox para
   * redimensionar a tela para o filtro Butterworth
   */
  private void selecao_butter() {
    
    if (cbButter.getState()) {
      cbRampa.setState(true);
      cbHamming.setState(false);
      cbShepp.setState(false);
      tela_parametros(TELA_BUTTERWORTH, true);
      janela_ativa = TELA_BUTTERWORTH;
    } else if (!(cbShepp.getState()) && !(cbHamming.getState())) {
      tela_parametros(TELA_RAMPA, true);
      janela_ativa = TELA_RAMPA;
    }
    cbHamming.transferFocus();
  }
  
  /**
   * Procedimento executado quando trocado o estado do ComboBox para
   * redimensionar a tela para o filtro Rampa
   */
  private void selecao_rampa() {
    
    if (cbRampa.getState()) {
      tela_parametros(TELA_RAMPA, true);
      janela_ativa = TELA_RAMPA;
    } else {
      cbButter.setState(false);
      cbShepp.setState(false);
      cbHamming.setState(false);
      tela_parametros(TELA_RETROPROJECAO_SIMPLES, true);
      janela_ativa = TELA_RETROPROJECAO_SIMPLES;
    }
    cbHamming.transferFocus();
  }
  
  /**
   * Procedimento executado quando trocado o estado do ComboBox para
   * redimensionar a tela para o filtro Shepp-Logan
   */
  private void selecao_shepp() {
    
    if (cbShepp.getState()) {
      cbRampa.setState(true);
      cbButter.setState(false);
      cbHamming.setState(false);
      tela_parametros(TELA_SHEPP_LOGAN, true);
      janela_ativa = TELA_SHEPP_LOGAN;
    } else if (!(cbButter.getState()) && !(cbHamming.getState())) {
      tela_parametros(TELA_RAMPA, true);
      janela_ativa = TELA_RAMPA;
    }
    cbHamming.transferFocus();
  }
  
  /**
   * Procedimento executado quando trocado o estado do ComboBox para
   * redimensionar a tela para o filtro Hamming
   */
  private void selecao_hamming() {
    
    if (cbHamming.getState()) {
      cbRampa.setState(true);
      cbButter.setState(false);
      cbShepp.setState(false);
      tela_parametros(TELA_HAMMING, true);
      janela_ativa = TELA_HAMMING;
    } else if (!(cbButter.getState()) && !(cbShepp.getState())) {
      tela_parametros(TELA_RAMPA, true);
      janela_ativa = TELA_RAMPA;
    }
    cbHamming.transferFocus();
  }
  
  /**
   * Procedimento utilizado quando alterado o tipo de filtro utilizado, altera
   * o tamanho da janela, quantidade de TextField, labels.
   *
   * @param tipo_parametro :
   *            Par�metro informa o tipo de janela para altera��o. Para
   *            vari�vel tipo_parametro podemos ter as seguintes entradas: 0 =
   *            Configura tela para retroproje��o simples 1 = configura tela
   *            para parametros filtro Butterworth 2 = configura tela para
   *            parametros filtro Sheep-Logan 3 = configura tela para
   *            parametros filtro Hamming
   */
  private void tela_parametros(int tipo_parametro, boolean visible) {
    
    setVisible(false);
    switch (tipo_parametro) {
      case TELA_RETROPROJECAO_SIMPLES: {
        setSize(JANELA_WIDTH_SIMPLES, JANELA_RETROPROJECAO_SIMPLES_HEIGHT);
        lblDivParam.setVisible(true);
        btnReconstruir.setText(LABEL_BUTTON_RECONSTRUIR);
        lblParametro.setVisible(false);
        lblParametro1.setVisible(false);
        lblParametro2.setVisible(false);
        tfParametro1.setVisible(false);
        tfParametro2.setVisible(false);
        btnReconstruir.setLocation(JANELA_WIDTH_SIMPLES - 165, 210);
        TITULO_IMAGEM = TITULO_IMAGEM_SIMPLES;
        cbHamming.transferFocus();
      }
      break;
      case TELA_RAMPA: {
        setSize(JANELA_WIDTH_SIMPLES, JANELA_RAMPA_HEIGHT);
        cbButter.setState(false);
        cbShepp.setState(false);
        cbHamming.setState(false);
        
        lblDivParam.setVisible(true);
        lblParametro.setVisible(true);
        lblParametro1.setVisible(true);
        lblParametro1.setText(LABEL_PARAMETRO_FREQUENCIA);
        tfParametro1.setVisible(true);
        tfParametro1.setText("0.5");
        tfParametro1.addKeyListener(new java.awt.event.KeyAdapter() {
          public void keyTyped(java.awt.event.KeyEvent evt) {
            LimitaTextField(evt, tfParametro1, TAMANHO_TEXT_FIELD_FC);
          }
        });
        tfParametro2.setVisible(false);
        lblParametro2.setVisible(false);
        btnVisualizarFiltro.setLocation(JANELA_WIDTH_SIMPLES - 165,
          JANELA_RAMPA_HEIGHT - 90);
        btnReconstruir.setLocation(JANELA_WIDTH_SIMPLES - 165,
          JANELA_RAMPA_HEIGHT - 60);
        btnReconstruir.setVisible(true);
        TITULO_IMAGEM = TITULO_IMAGEM_RAMPA;
      }
      break;
      case TELA_BUTTERWORTH: {
        setSize(JANELA_WIDTH_SIMPLES, JANELA_BUTTERWORTH_HEIGHT);
        panel1.setSize(JANELA_WIDTH_SIMPLES, JANELA_BUTTERWORTH_HEIGHT);
        lblDivParam.setVisible(true);
        lblParametro.setVisible(true);
        lblParametro1.setVisible(true);
        lblParametro1.setText(LABEL_PARAMETRO_FREQUENCIA_BUTTERWORTH);
        lblParametro2.setVisible(true);
        lblParametro2.setText(LABEL_PARAMETRO_ORDEM_BUTTERWORTH);
        tfParametro1.setVisible(true);
        tfParametro1.setText("0.5");
        tfParametro1.addKeyListener(new java.awt.event.KeyAdapter() {
          public void keyTyped(java.awt.event.KeyEvent evt) {
            LimitaTextField(evt, tfParametro1, TAMANHO_TEXT_FIELD_FC);
          }
        });
        tfParametro2.setVisible(true);
        tfParametro2.setText("5");
        tfParametro2.addKeyListener(new java.awt.event.KeyAdapter() {
          public void keyTyped(java.awt.event.KeyEvent evt) {
            LimitaTextField(evt, tfParametro2, TAMANHO_TEXT_FIELD_N);
          }
        });
        btnVisualizarFiltro.setLocation(JANELA_WIDTH_SIMPLES - 165,
          JANELA_BUTTERWORTH_HEIGHT - 90);
        btnReconstruir.setLocation(JANELA_WIDTH_SIMPLES - 165,
          JANELA_BUTTERWORTH_HEIGHT - 60);
        btnReconstruir.setVisible(true);
        TITULO_IMAGEM = TITULO_IMAGEM_BUTTERWORTH;
      }
      break;
      case TELA_SHEPP_LOGAN: {
        setSize(JANELA_WIDTH_SIMPLES, JANELA_SHEPP_LOGAN_HEIGHT);
        lblDivParam.setVisible(true);
        lblDivParam.setVisible(true);
        lblParametro.setVisible(true);
        lblParametro1.setVisible(true);
        lblParametro1.setText(LABEL_PARAMETRO_FREQUENCIA);
        tfParametro1.setVisible(true);
        tfParametro1.setText("0.5");
        tfParametro1.addKeyListener(new java.awt.event.KeyAdapter() {
          public void keyTyped(java.awt.event.KeyEvent evt) {
            LimitaTextField(evt, tfParametro1, TAMANHO_TEXT_FIELD_FC);
          }
        });
        tfParametro2.setVisible(false);
        lblParametro2.setVisible(false);
        btnVisualizarFiltro.setLocation(JANELA_WIDTH_SIMPLES - 165,
          JANELA_SHEPP_LOGAN_HEIGHT - 90);
        btnVisualizarFiltro.setText(LABEL_BUTTON_VISUALIZAR_FILTRO);
        btnReconstruir.setLocation(JANELA_WIDTH_SIMPLES - 165,
          JANELA_SHEPP_LOGAN_HEIGHT - 60);
        btnReconstruir.setVisible(true);
        TITULO_IMAGEM = TITULO_IMAGEM_SHEPP_LOGAN;
      }
      break;
      case TELA_HAMMING: {
        setSize(JANELA_WIDTH_SIMPLES, JANELA_HAMMING_HEIGHT);
        lblDivParam.setVisible(true);
        lblParametro.setVisible(true);
        lblParametro1.setVisible(true);
        lblParametro1.setText(LABEL_PARAMETRO_FREQUENCIA);
        tfParametro1.setVisible(true);
        tfParametro1.setText("0.5");
        tfParametro1.addKeyListener(new java.awt.event.KeyAdapter() {
          public void keyTyped(java.awt.event.KeyEvent evt) {
            LimitaTextField(evt, tfParametro1, TAMANHO_TEXT_FIELD_FC);
          }
        });
        tfParametro2.setVisible(false);
        lblParametro2.setVisible(false);
        btnVisualizarFiltro.setLocation(JANELA_WIDTH_SIMPLES - 165,
          JANELA_HAMMING_HEIGHT - 90);
        btnReconstruir.setLocation(JANELA_WIDTH_SIMPLES - 165,
          JANELA_HAMMING_HEIGHT - 60);
        btnReconstruir.setVisible(true);
        TITULO_IMAGEM = TITULO_IMAGEM_HAMMING;
      }
      break;
    }
    setVisible(visible);
  }
  
  /**
   * Fun��o que retorna como vetor do tipo Complexo o filtro para a
   * reconstru��o.
   *
   * @param rampa
   *            informa se esta sendo utilizado o filtro rampa.
   * @param tipo_filtro
   *            se for informado um filtro passa banda, informa o qual o
   *            filtro.
   * @return Retorna um Vetor do tipo Complexo.
   */
  private Complexo[] gerar_filtro(boolean rampa, int tipo_filtro) {
    
    int aux;
    boolean isband = false;
    float tf1=0, tf2=0;
    try {
      tf1 = Float.parseFloat(tfParametro1.getText());
      tf2 = Float.parseFloat(tfParametro2.getText());      
    }
    catch (java.lang.NumberFormatException evt) { }
    Complexo filtro_rampa[] = new Complexo[(int) (tamanho_imagem)];
    Complexo filtro_banda[] = new Complexo[(int) (tamanho_imagem)];
    Filtros filtros = new Filtros(tf1,tf2, tipo_filtro,tamanho_imagem);
    
    if (rampa) {
      filtro_rampa = filtros.gera_filtro_rampa();
      
      switch (tipo_filtro) {
        case FILTRO_BUTTERWORTH: {
          filtro_banda = filtros.gera_filtro_butterworth();
          for (aux = 0; aux < tamanho_imagem; aux++)
            filtro_banda[aux] = filtro_rampa[aux]
              .multiply(filtro_banda[aux]);
          isband = true;
        }
        break;
        case FILTRO_SHEPP_LOGAN: {
          filtro_banda = filtros.gera_filtro_shepp_logan();
          for (aux = 0; aux < tamanho_imagem; aux++)
            filtro_banda[aux] = filtro_rampa[aux]
              .multiply(filtro_banda[aux]);
          isband = true;
        }
        break;
        case FILTRO_HAMMING: {
          filtro_banda = filtros.gera_filtro_hamming();
          for (aux = 0; aux < tamanho_imagem; aux++)
            filtro_banda[aux] = filtro_rampa[aux]
              .multiply(filtro_banda[aux]);
          isband = true;
        }
        break;
        default: {
          isband = false;
        }
      }
      
    }
    if (isband)
      return filtro_banda;
    else
      return filtro_rampa;
  }
  
  /**
   * Procedimento utilizado para criar e exibir a janela contendo o gr�fico
   * referente ao filtro selecionado.
   *
   * @param filtro
   *            Vetor do tipo Complexo que informa o filtro.
   * @param y
   *            Tamanho em 'x' da imagem selecionada.
   */
  private void gera_grafico(Complexo filtro[], int y) {
    
    if (IJ.versionLessThan("1.27t"))
      return;
    Complexo[] ya = filtro;
    Complexo[] xa = new Complexo[(int) tamanho_imagem];
    double gx[] = new double[(int) tamanho_imagem];
    double gy[] = new double[(int) tamanho_imagem];
    PlotWindow plot = null;
    int aux;
    double lx, ly;
    
    for (aux = 0; aux < y + 1; aux++)
      xa[aux] = new Complexo((float) aux / tamanho_imagem, 0.0);
    
    lx = xa[0].real();
    for (aux = 0; aux < (int) tamanho_imagem; aux++)
      if (xa[aux].real() > lx)
        lx = xa[aux].real();
    
    ly = ya[0].real();
    for (aux = 0; aux < (int) tamanho_imagem; aux++)
      if (ya[aux].real() > ly)
        ly = ya[aux].real();
    
    for (aux = 0; aux < (int) tamanho_imagem; aux++)
      gx[aux] = xa[aux].real();
    
    for (aux = 0; aux < (int) tamanho_imagem; aux++)
      gy[aux] = ya[aux].real();
    String titulo;
    if (janela_ativa == TELA_BUTTERWORTH) {
      titulo = Funcoes.AlteraNomeImagem("Filtro " + TITULO_IMAGEM_FILTRADA + ", N= "
        + tfParametro1.getText() + " fc= "
        + tfParametro2.getText());
      plot = new PlotWindow(titulo
        , "f", "", gx, gy);
    } else {
      titulo = Funcoes.AlteraNomeImagem("Filtro " + TITULO_IMAGEM_FILTRADA + ", fc= "
        + tfParametro1.getText());
      plot = new PlotWindow(titulo, "f", "", gx, gy);
    }
    plot.setLimits(0, lx, 0, ly);
    plot.setLineWidth(2);
    plot.setColor(Color.blue);
    plot.draw();
  }
  
  /**
   * Fun��o utilizada para fazer a filtragem da imagem selecionada antes da
   * reconstru��o.
   *
   * @param img :
   *            Informa a imagem selecionada para a filtragem.
   * @param filtro :
   *            vetor do tipo Complexo contendo os valores do filtro.
   * @return Retorna um ImagePlus com a imagem Filtrada.
   */
  public ImagePlus filtrar(ImagePlus img, Complexo filtro[]) {
    
    int coluna = 0, coluna_i = 0;
    int linha;
    int imf_i = 0, imf = 0;
    ImageStack is_imgFinal;
    ImagePlus imgFinal = null;
    
    ImageProcessor ip_imgOrig = null;
    
    int num_stacks = img.getStackSize();
    
    is_imgFinal = new ImageStack(tamanho_imagem, img.getHeight());
    
    for (int stack_count = 1; stack_count <= num_stacks; stack_count++) {
      ImageProcessor ip_imgFinal = new FloatProcessor(
        (int) tamanho_imagem, img.getHeight());
      ip_imgOrig = img.getImageStack().getProcessor(stack_count);
      ip_imgOrig.resetMinAndMax();
      float[] sinopixels = (float[]) ip_imgOrig.getPixels();
      float[] imagem2 = (float[]) ip_imgFinal.getPixels();
      
      linha = 0;
      coluna = 0;
      imf = 0;
      
      while (linha < img.getHeight()) {
        Complexo projecao[] = new Complexo[tamanho_imagem];
        
        for (coluna_i = coluna; coluna_i < tamanho_imagem * (linha + 1); coluna_i++)
          projecao[coluna_i - coluna] = new Complexo(
            (double) sinopixels[coluna_i], 0.0);
        
        Complexo projecao_fft[] = Fourier1D.transform(projecao);
        Complexo resultado_conv[] = new Complexo[tamanho_imagem];
        
        for (int i = 0; i < tamanho_imagem; i++) {
          resultado_conv[i] = projecao_fft[i].multiply(filtro[i]);
          // IJ.log("filtro ="+filtro[i]);
          // IJ.log("result conv ="+resultado_conv[i]);
        }
        Complexo inverseTF[] = Fourier1D
          .inverseTransform(resultado_conv);
        
        for (imf_i = imf; imf_i < tamanho_imagem * (linha + 1); imf_i++) {
          imagem2[imf_i] = ((float) inverseTF[imf_i - imf].real());
        }
        imf = imf_i;
        coluna = coluna_i;
        linha++;
      }
      ip_imgFinal.setPixels(imagem2);
      ip_imgFinal.resetMinAndMax();
      is_imgFinal.addSlice("Imagem Retroprojetada", ip_imgFinal,
        stack_count - 1);
    }
    String img_title = imp.getTitle();
    String img_title_temp = imp.getShortTitle();
    while (img_title != img_title_temp) {
      img_title_temp = imp.getShortTitle();
      imp.setTitle(img_title_temp);
      img_title_temp = imp.getShortTitle();
      img_title = imp.getTitle();
    }
    String TITULO = " ";
    if (janela_ativa == TELA_BUTTERWORTH)
      TITULO = img_title + " (Sinograma filtrado com " + TITULO_IMAGEM
        + "; N= " + tfParametro1.getText() + " fc= "
        + tfParametro2.getText() + ")";
    else
      TITULO = img_title + " (Sinograma filtrado com " + TITULO_IMAGEM
        + "; fc= " + tfParametro1.getText() + ")";
    
    imgFinal = new ImagePlus(TITULO, is_imgFinal);
    
    return imgFinal;
  }
  
  // Variables declaration
  private javax.swing.JButton btnVisualizarFiltro;
  
  private javax.swing.JButton btnReconstruir;
  
  private java.awt.Checkbox cbButter;
  
  private java.awt.Checkbox cbHamming;
  
  private java.awt.Checkbox cbRampa;
  
  private java.awt.Checkbox cbShepp;
  
  private javax.swing.JLabel lblDivFiltro;
  
  private javax.swing.JLabel lblDivParam;
  
  private javax.swing.JLabel lblFiltro;
  
  private javax.swing.JLabel lblParametro;
  
  private javax.swing.JLabel lblParametro1;
  
  private javax.swing.JLabel lblParametro2;
  
  private javax.swing.JLabel lblSelecao_Janela;
  
  private javax.swing.JPanel panel1;
  
  private javax.swing.JTextField tfParametro1;
  
  private javax.swing.JTextField tfParametro2;
  // End of variables declaration
  
}