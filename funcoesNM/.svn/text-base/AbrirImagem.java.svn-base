package funcoesNM;

import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.plugin.filter.Duplicater;
import ij.process.ImageConverter;
import ij.process.StackConverter;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import javax.swing.JDialog;

/**
 * Classe criada para armazemar a janela e funções de alteração de imagem que
 * são informadas pelo usuário para ser feito a reconstrução.
 * 
 * @author Marcus Vinícius da Silva Costa, Michele Alberton Andrade.
 * @version <b>1.3</b>
 */

public class AbrirImagem {

	private static final int FBP = 1;

	private static final int ML_EM = 2;

	private static String title1 = "";

	private static boolean debugAtivo;

	public static ImagePlus imagemTemp;

	public static ImagePlus imagemTempFinal;

	public static ImagePlus imagemTempMapa;

	public static ImagePlus imagemTempMapaFinal;

	public static JDialog dlg;

	public static int[] wList;

	public static int tipo_abrir;

	static int tamanho_imagem = 0;

	static Frame dono;

	static String Titulo;

	/**
   * Função para a abertura de uma imagem, janela com 1 ComboBox. Para a Classe
   * FBP_.
   * 
   * @param owner Informa o Frame owner para a criação da janela de seleção da
   *          imagem.
   * @param debug informa se e para abrir no modo de teste.
   * @return Retorna a imagem selecionada na combo.
   */
	public static ImagePlus Abrir_FBP(java.awt.Frame owner, boolean debug) {

		dono = owner;
		debugAtivo = debug;
		Titulo = "Selecione a Imagem";
		tipo_abrir = FBP;
		imagemTemp = null;
		imagemTempFinal = null;
		wList = null;
		wList = WindowManager.getIDList();
		if (wList == null) {
			IJ.noImage();
			return null;
		} else {
			if (wList.length == 1)
				btnOkActionPerformed_FBP(new ActionEvent(new Object(), 9999, ""), WindowManager.getImage(wList[0]).getTitle());
			else {
				initComponents_FBP();
				dlg.setVisible(true);
				dlg.dispose();
			}
		}
		if (debugAtivo) imagemTempFinal.show();

		return imagemTempFinal;
	}

	/**
   * Função abrir imagem para a classe ML_EM_.
   * 
   * @param owner Parâmetro para ser informado a janela pai da janela de seleção
   *          de imagem.
   * @param debug Parâmetro para ser informado se a janela deve ser aberta no
   *          modo de teste.
   * @param comMapa Informa se a janela de seleção de imagem deve conter a combo
   *          para seleção do mapa de atenuação.
   * @return Retorna um vetor de ImagePlus com a imagem selecionada em primeira
   *         posição e o mapa de atenuação em segunda posição.
   */
	public static ImagePlus[] Abrir_ML_EM(java.awt.Frame owner, boolean debug, boolean comMapa) {

		dono = owner;
		debugAtivo = debug;
		Titulo = "Selecione as Imagens";
		tipo_abrir = ML_EM;
		imagemTemp = null;
		imagemTempFinal = null;
		imagemTempMapa = null;
		imagemTempMapaFinal = null;
		wList = null;
		wList = WindowManager.getIDList();
		if (wList == null) {
			IJ.noImage();
//			return null;
		} else {
			if (comMapa) {
				if (wList.length >= 1 ) {
					initComponents_ML_EM();
					dlg.setVisible(true);
					dlg.dispose();
				}
				else {
					IJ.error(Funcoes.NOME_PLUGIN,Funcoes.MENSAGEM_ERRO_IMAGEM_MAPA);
					imagemTempFinal = null;
					imagemTempMapaFinal = null;
				}
			} else {
				if (wList.length == 1) {
					btnOkActionPerformed_FBP(new ActionEvent(new Object(), 9999, ""), WindowManager.getImage(wList[0]).getTitle());
				} else {
					initComponents_FBP();
					dlg.setVisible(true);
					dlg.dispose();
				}
			}
		}
		ImagePlus[] imagens = new ImagePlus[2];
		imagens[0] = imagemTempFinal;
		imagens[1] = imagemTempMapaFinal;
		return imagens;
	}

	/**
   * Método utilizado para a criação dos componentes e suas chamadas de função
   * Método utilizado quando abrirImagem for chamado do FBP_.
   */
	private static void initComponents_FBP() {

		btnOk = new javax.swing.JButton();
		Label1 = new javax.swing.JLabel();
		Label2 = new javax.swing.JLabel();
		cbImg = new javax.swing.JComboBox();
		dlg = new JDialog(dono, "", true);

		dlg.setLayout(null);
		dlg.setLocation(dono.getX() + 8, dono.getY() + 10);
		dlg.setSize(245, 210);
		dlg.setResizable(false);
		dlg.setTitle(Titulo);
		dlg.setName("frmAdquirirImagem");
		dlg.setDefaultCloseOperation(javax.swing.JDialog.DISPOSE_ON_CLOSE);
		dlg.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosed(java.awt.event.WindowEvent evt) {
				imagemTemp = null;
				imagemTempMapa = null;
			}
		});

		btnOk.setText("Ok");
		btnOk.setFont(new java.awt.Font("Dialog", 1, 12));
		btnOk.setBounds(55, 145, 120, 25);
		btnOk.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnOkActionPerformed_FBP(evt, (String) cbImg.getSelectedItem());
				dlg.setVisible(false);
			}
		});
		dlg.add(btnOk);

		Label1.setText("Aquisicao de Imagem");
		Label1.setFont(new java.awt.Font("Dialog", 1, 12));
		Label1.setBounds(15, 15, 200, 20);
		dlg.add(Label1);

		Label2.setFont(new java.awt.Font("Dialog", 1, 12));
		Label2.setText("Selecione a Imagem na lista abaixo:");
		Label2.setBounds(15, 45, 210, 20);
		dlg.add(Label2);

		cbImg.setEnabled(true);
		cbImg.setEditable(false);
		cbImg.setFont(new java.awt.Font("Dialog", 1, 12));
		cbImg.setBounds(15, 85, 200, 20);

		String[] titles = new String[wList.length];
		for (int i = 0; i < wList.length; i++) {
			ImagePlus imp = WindowManager.getImage(wList[i]);
			if (imp != null)
				titles[i] = imp.getTitle();
			else
				titles[i] = "";
			cbImg.addItem(imp.getTitle());

		}
		String defaultItem;
		if (title1.equals(""))
			defaultItem = titles[0];
		else
			defaultItem = title1;
		cbImg.setSelectedItem(defaultItem);
		cbImg.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				cbImg.setToolTipText((String) cbImg.getSelectedItem());
			}
		});
		dlg.add(cbImg);
	}

	/**
   * Método utilizado para a criação dos componentes e suas chamadas de função
   * Método utilizado quando abrirImagem for chamado do ML_EM_.
   */
	private static void initComponents_ML_EM() {

		btnOk = new javax.swing.JButton();
		Label1 = new javax.swing.JLabel();
		Label2 = new javax.swing.JLabel();
		Label3 = new javax.swing.JLabel();
		cbImg = new javax.swing.JComboBox();
		cbImgMapa = new javax.swing.JComboBox();

		dlg = new JDialog(dono, "", true);

		dlg.setLayout(null);
		dlg.setLocation(dono.getX() + 16, dono.getY() + 20);
		dlg.setSize(245, 270);
		dlg.setResizable(false);
		dlg.setTitle(Titulo);
		dlg.setName("frmAdquirirImagem");
		dlg.setDefaultCloseOperation(javax.swing.JDialog.DISPOSE_ON_CLOSE);
		dlg.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosed(java.awt.event.WindowEvent evt) {
				imagemTemp = null;
				imagemTempMapa = null;
			}
		});

		btnOk.setText("Ok");
		btnOk.setFont(new java.awt.Font("Dialog", 1, 12));
		btnOk.setBounds(55, 190, 120, 25);
		btnOk.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnOkActionPerformed_ML_EM(evt);
			}
		});
		dlg.add(btnOk);

		Label1.setText("Aquisicao de Imagem");
		Label1.setFont(new java.awt.Font("Dialog", 1, 12));
		Label1.setBounds(15, 15, 200, 20);
		dlg.add(Label1);

		Label2.setFont(new java.awt.Font("Dialog", 1, 12));
		Label2.setText("Selecione a Imagem na lista abaixo:");
		Label2.setBounds(15, 45, 210, 20);
		dlg.add(Label2);

		Label3.setFont(new java.awt.Font("Dialog", 1, 12));
		Label3.setText("Selecione o mapa de Atenuação:");
		Label3.setBounds(15, 125, 210, 20);
		dlg.add(Label3);

		String[] titles = new String[wList.length];
		cbImgMapa.setEnabled(true);
		cbImgMapa.setEditable(false);
		cbImgMapa.setFont(new java.awt.Font("Dialog", 1, 12));
		cbImgMapa.setBounds(15, 150, 200, 20);
		String defaultItem;
		if (title1.equals(""))
			defaultItem = titles[0];
		else
			defaultItem = title1;

		for (int i = 0; i < wList.length; i++) {
			ImagePlus imp = WindowManager.getImage(wList[i]);
			if (imp != null)
				titles[i] = imp.getTitle();
			else
				titles[i] = "";
			cbImgMapa.addItem(imp.getTitle());
		}
		cbImgMapa.setSelectedItem(defaultItem);
		cbImgMapa.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				cbImg.setToolTipText((String) cbImgMapa.getSelectedItem());
			}
		});
		dlg.add(cbImgMapa);

		cbImg.setEnabled(true);
		cbImg.setEditable(false);
		cbImg.setFont(new java.awt.Font("Dialog", 1, 12));
		cbImg.setBounds(15, 75, 200, 20);
		for (int i = 0; i < wList.length; i++) {
			ImagePlus imp = WindowManager.getImage(wList[i]);
			if (imp != null)
				titles[i] = imp.getTitle();
			else
				titles[i] = "";
			cbImg.addItem(imp.getTitle());
		}
		cbImg.setSelectedItem(defaultItem);
		cbImg.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				cbImg.setToolTipText((String) cbImg.getSelectedItem());
			}
		});
		dlg.add(cbImg);

	}

	private static void btnOkActionPerformed_FBP(java.awt.event.ActionEvent evt, String ImagemTitle) {

		imagemTemp = ij.WindowManager.getImage(ImagemTitle);
		int tamanho_imagem = imagemTemp.getWidth();

		if ((tamanho_imagem > 0) && (Funcoes.isPowerOf2(tamanho_imagem))) {
			Duplicater duplicar = new Duplicater();

			imagemTempFinal = duplicar.duplicateStack(imagemTemp, imagemTemp.getTitle());
			int Tipo = imagemTemp.getType();
			if (imagemTemp.getStackSize() > 1) {
				StackConverter converter = new StackConverter(imagemTempFinal);
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
				ImageConverter converter = new ImageConverter(imagemTempFinal);
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
		} else {
			IJ.showMessage(Funcoes.MENSAGEM_IMAGEM_NAO_COMPATIVEL);
			imagemTempFinal = null;
		}
	}

	private static void btnOkActionPerformed_ML_EM(java.awt.event.ActionEvent evt) {

		imagemTemp = ij.WindowManager.getImage((String) cbImg.getSelectedItem());
		imagemTempMapa = ij.WindowManager.getImage((String) cbImgMapa.getSelectedItem());
		int tamanho_imagem = imagemTemp.getWidth();

		if ((tamanho_imagem > 0) && (Funcoes.isPowerOf2(tamanho_imagem))) {
			Duplicater duplicar = new Duplicater();
			imagemTempFinal = duplicar.duplicateStack(imagemTemp, imagemTemp.getTitle());
			imagemTempMapaFinal = duplicar.duplicateStack(imagemTempMapa, imagemTempMapa.getTitle());
			int Tipo = imagemTemp.getType();
			if (imagemTemp.getStackSize() > 1) {
				StackConverter converter = new StackConverter(imagemTempFinal);
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
				ImageConverter converter = new ImageConverter(imagemTempFinal);
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
			Tipo = imagemTempMapa.getType();
			if (imagemTempMapa.getStackSize() > 1) {
				StackConverter converter = new StackConverter(imagemTempMapaFinal);
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
				ImageConverter converter = new ImageConverter(imagemTempMapaFinal);
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
		} else {
			IJ.showMessage(Funcoes.MENSAGEM_IMAGEM_NAO_COMPATIVEL);
			imagemTempFinal = null;
		}
		dlg.setVisible(false);
	}

	private static javax.swing.JComboBox cbImg;

	private static javax.swing.JComboBox cbImgMapa;

	private static javax.swing.JButton btnOk;

	private static javax.swing.JLabel Label1;

	private static javax.swing.JLabel Label2;

	private static javax.swing.JLabel Label3;
}