package funcoesNM;

import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.plugin.filter.Duplicater;
import ij.process.ImageConverter;
import ij.process.StackConverter;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 * Classe criada para armazemar a janela e funções de alteração de imagem que
 * são informadas pelo usuário para ser feito a reconstrução.
 *
 * @author Marcus Vin�cius da Silva Costa, Michele Alberton Andrade.
 * @version <b>1.8</b>
 */

public class AbrirImagem {
  
  public class combo {
    
    public boolean ativo;
    public String titulo;
    public ImagePlus imagemSelecionada;
  }
  
  public static combo combo1 , combo2;
  
  protected static JDialog dlg;
  
  protected static final String Titulo = "Selecao de Imagem";
  
  /**
   * Construtor da classe
   *
   * seta como default para nao exibicao da combo 2
   */ 
  public AbrirImagem() {
    
    combo1 = new combo();
    combo1.titulo = "Imagem Sinograma";
    combo1.ativo = true;
    
    combo2 = new combo();
    combo2.titulo = "Imagem do mapa de atenuacao";
    combo2.ativo = false;
    
    dlg = new JDialog(ij.IJ.getInstance(), "", true);
  }
  
  /**
   * Função para a abertura de uma imagem, janela com 1 ComboBox. Para a Classe
   * FBP_.
   *
   * @return Retorna a imagem selecionada na combo.
   */
  public static ImagePlus Abrir_FBP() {
    
    int[] wList = null;
    wList = WindowManager.getIDList();
    if (wList == null) {
      IJ.noImage();
      return null;
    } else {
      if (wList.length == 1)
        btnOkActionPerformed();
      else {
        initComponents();
        dlg.setVisible(true);
        dlg.dispose();
      }
    }
    if (Funcoes.debug) combo1.imagemSelecionada.show();
    
    return combo1.imagemSelecionada;
  }
  
  /**
   * Função abrir imagem para a classe ML_EM_.
   *
   * @return Retorna um vetor de ImagePlus com a imagem selecionada em primeira
   *         posição e o mapa de atenuação em segunda posição.
   */
  public static ImagePlus[] Abrir_ML_EM() {
    
    int[] wList = null;
    wList = WindowManager.getIDList();
    ImagePlus[] imagens = new ImagePlus[2];
    combo1.titulo = "Imagem Original";
    combo2.titulo = "Imagem Reconstruida";
    if (wList == null) {
      IJ.noImage();
      return imagens;
    } else {
      
      if (wList.length > 1 ) {
        initComponents();
        dlg.setVisible(true);
        dlg.dispose();
      } else if( (wList.length == 1) && (combo2.ativo) ) {
        IJ.error(Funcoes.NOME_PLUGIN,Funcoes.MENSAGEM_ERRO_IMAGEM_MAPA);
        return imagens;
      }
      if (wList.length == 1) {
        btnOkActionPerformed();
      }
    }
    
    imagens[0] = combo1.imagemSelecionada;
    imagens[1] = combo2.imagemSelecionada;
    return imagens;
  }
  
  /**
   * Método utilizado para a criação dos componentes e suas chamadas de função
   * Utilizado quando abrirImagem for chamado pelo ML_EM_.
   */
  private static void initComponents() {
    
    btnOk = new javax.swing.JButton();
    Label1 = new javax.swing.JLabel();
    Label2 = new javax.swing.JLabel();
    Label3 = new javax.swing.JLabel();
    cbImg1 = new javax.swing.JComboBox();
    cbImg2 = new javax.swing.JComboBox();
    
    dlg.setLayout(null);
    
    dlg.setLocation(dlg.getOwner().getX() + 20, dlg.getOwner().getY() + 20);
    
    if (combo2.ativo) {
      dlg.setSize(245, 275);
      btnOk.setBounds(55, 200, 120, 25);
    } else {
      btnOk.setBounds(55, 125, 120, 25);
      dlg.setSize(245, 205);
    }
    
    dlg.setResizable(false);
    dlg.setTitle(Titulo);
    dlg.setDefaultCloseOperation(javax.swing.JDialog.DISPOSE_ON_CLOSE);
    dlg.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosed(java.awt.event.WindowEvent evt) {
        combo1.imagemSelecionada = null;
        combo2.imagemSelecionada = null;
      }
    });
    
    btnOk.setText("Adquirir");
    btnOk.setFont(new java.awt.Font("Dialog", 1, 12));
    
    btnOk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnOkActionPerformed(evt);
      }
    });
    dlg.add(btnOk);
    
    
    Label1.setText("Aquisicao de Imagem");
    Label1.setFont(new java.awt.Font("Dialog", 1, 12));
    Label1.setBounds(50, 15, 150, 20);
    dlg.add(Label1);
    
    Label2.setFont(new java.awt.Font("Dialog", 1, 12));
    Label2.setText(combo1.titulo);
    Label2.setBounds(15, 55, 230, 20);
    dlg.add(Label2);
    
    Label3.setVisible(combo2.ativo);
    Label3.setFont(new java.awt.Font("Dialog", 1, 12));
    Label3.setText(combo2.titulo);
    Label3.setBounds(15, 125, 230, 20);
    dlg.add(Label3);
    
    int[] wList = null;
    wList = WindowManager.getIDList();
    String[] titles = new String[wList.length];
    
    cbImg2.setVisible(combo2.ativo);
    cbImg2.setEditable(false);
    cbImg2.setFont(new java.awt.Font("Dialog", 1, 12));
    cbImg2.setBounds(15, 150, 210, 20);
    
    for (int i = 0; i < wList.length; i++) {
      ImagePlus imp = WindowManager.getImage(wList[i]);
      if (imp != null)
        titles[i] = imp.getTitle();
      else
        titles[i] = "";
      cbImg2.addItem(imp.getTitle());
    }
    cbImg2.setSelectedIndex(0);
    cbImg2.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        cbImg2.setToolTipText( (String)cbImg2.getSelectedItem() );
      }
    });
    dlg.add(cbImg2);
    
    cbImg1.setEnabled(combo1.ativo);
    cbImg1.setEditable(false);
    cbImg1.setFont(new java.awt.Font("Dialog", 1, 12));
    cbImg1.setBounds(15, 85, 210, 20);
    for (int i = 0; i < wList.length; i++) {
      ImagePlus imp = WindowManager.getImage(wList[i]);
      if (imp != null)
        titles[i] = imp.getTitle();
      else
        titles[i] = "";
      cbImg1.addItem(imp.getTitle());
    }
    cbImg1.setSelectedIndex(0);
    cbImg1.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        cbImg1.setToolTipText((String) cbImg1.getSelectedItem());
      }
    });
    dlg.add(cbImg1);
    
  }
  
  private static void btnOkActionPerformed() {
    btnOkActionPerformed(null);
  }
  
  private static void btnOkActionPerformed(java.awt.event.ActionEvent evt) {
    
    int[] wList = null;
    wList = WindowManager.getIDList();
    ImagePlus imagemTemp1 = null;
    if (cbImg1 == null)
      imagemTemp1 = WindowManager.getImage(wList[0]);
    else
      imagemTemp1 = ij.WindowManager.getImage((String) cbImg1.getSelectedItem());
    
    ImagePlus imagemTemp2 = null;
    if ( combo2.ativo )
      imagemTemp2 = ij.WindowManager.getImage((String) cbImg2.getSelectedItem());
    int tamanho_imagem = imagemTemp1.getWidth();
    
    if ((tamanho_imagem > 0) && (Funcoes.isPowerOf2(tamanho_imagem))) {
      Duplicater duplicar = new Duplicater();
      combo1.imagemSelecionada = duplicar.duplicateStack(imagemTemp1, imagemTemp1.getTitle());
      
      int Tipo = imagemTemp1.getType();
      if (imagemTemp1.getStackSize() > 1) {
        StackConverter converter = new StackConverter(combo1.imagemSelecionada);
        switch (Tipo) {
          case ImagePlus.GRAY8:
          case ImagePlus.GRAY16:
            converter.convertToGray32();
            break;
          case ImagePlus.COLOR_256:
            converter.convertToRGB();
            break;
        }
      } else {
        ImageConverter converter = new ImageConverter(combo1.imagemSelecionada);
        switch (Tipo) {
          case ImagePlus.GRAY8:
          case ImagePlus.GRAY16:
            converter.convertToGray32();
            break;
          case ImagePlus.COLOR_256:
            converter.convertToRGB();
            break;
        }
      }
      if ( combo2.ativo ) {
        
        combo2.imagemSelecionada = duplicar.duplicateStack(imagemTemp2, imagemTemp2.getTitle());
        Tipo = imagemTemp2.getType();
        if (imagemTemp2.getStackSize() > 1) {
          StackConverter converter = new StackConverter(combo2.imagemSelecionada);
          switch (Tipo) {
            case ImagePlus.GRAY8:
            case ImagePlus.GRAY16:
              converter.convertToGray32();
              break;
            case ImagePlus.COLOR_256:
              converter.convertToRGB();
              break;
          }
        } else {
          ImageConverter converter = new ImageConverter(combo2.imagemSelecionada);
          switch (Tipo) {
            case ImagePlus.GRAY8:
            case ImagePlus.GRAY16:
              converter.convertToGray32();
              break;
            case ImagePlus.COLOR_256:
              converter.convertToRGB();
              break;
          }
        }
      }
    } else {
      IJ.showMessage(Funcoes.MENSAGEM_IMAGEM_NAO_COMPATIVEL);
      combo1.imagemSelecionada = null;
      combo2.imagemSelecionada = null;
    }
    dlg.setVisible(false);
  }
  
  
  private static javax.swing.JComboBox cbImg1;
  
  private static javax.swing.JComboBox cbImg2;
  
  private static javax.swing.JButton btnOk;
  
  private static javax.swing.JLabel Label1;
  
  private static javax.swing.JLabel Label2;
  
  private static javax.swing.JLabel Label3;
}