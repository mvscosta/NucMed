package target;

/*
 * ML_EM_.java
 *
 * Created on April 27, 2006, 5:17 PM
 *
 */

import funcoesNM.AbrirImagem;
import funcoesNM.AbrirImagem;
import funcoesNM.Funcoes;
import ij.WindowManager;
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
 * Classe principal. Cont√©m a cria√ß√£o da janela do plugin para reconstru√ß√£o
 * de imagem atrav√©s do m√©todo de <i>Maximum Likehood Expectation Maximization</i>.
 * 
 * @author Marcus Vinicius da Silva Costa, Michele Alberton Andrade.
 * @version <B>1.9</B>
 */

public class ML_EM_ extends javax.swing.JFrame implements ij.plugin.PlugIn {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// DECLARACAO DAS CONSTANTES
	public static final int TAMANHO_TF_NUM_ITER = 2;

	// VARIAVEIS UTILIZADAS PARA RECONHECIMENTO DE IMAGEM
	public static int tamanho_imagem = 0;

	public static int type;

	private ImagePlus imagemOriginal = null;

	/**
	 * Fun√ß√£o herdada da classe PluginFilter do ImageJ.
	 */
	public void run(String str) {

		ImagePlus imgOrig = IJ.getImage();
		tamanho_imagem = imgOrig.getWidth();
		type = imgOrig.getType();

		if ((tamanho_imagem > 0) && (Funcoes.isPowerOf2(tamanho_imagem))) {
			// new ML_EM_ ().setVisible (true);
			IJ.register(ML_EM_.class);
			setVisible(true);
		} else {
			ij.IJ.showMessage(Funcoes.MENSAGEM_IMAGEM_NAO_COMPATIVEL);
			dispose();
		}
	}

	/**
	 * Cria e inicia a janela do ML_EM_
	 */
	public ML_EM_() {

		initComponents();
		setSize(240, 270);
		setLocation(super.getX() + 200, super.getY() + 200);
		// setVisible (true);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("MLEM OK");
	}

	/**
	 * Cria√ß√£o e inicializa√ß√£o de todos os objetos da janela ML_EM_
	 * incluindo as suas chamadas de fun√ß√µes
	 */
	private void initComponents() {

		pnlGeral = new java.awt.Panel();
		Label1 = new java.awt.Label();
		Label2 = new java.awt.Label();
		Label3 = new java.awt.Label();
		Label4 = new java.awt.Label();
		pnlNumIter = new javax.swing.JPanel();
		pnlCorrecaoAtt = new javax.swing.JPanel();
		cbSim = new java.awt.Checkbox();
		cbNao = new java.awt.Checkbox();
		btnReconstruir = new javax.swing.JButton();
		tfNumIter = new javax.swing.JTextField();

		getContentPane().setLayout(
				new javax.swing.BoxLayout(getContentPane(),
						javax.swing.BoxLayout.X_AXIS));
		setTitle("ML-EM");
		setName("frmIterativo");
		setResizable(false);

		// pnlGeral.setBackground(java.awt.Color.lightGray);
		pnlGeral.setLayout(null);

		btnReconstruir.setFont(new java.awt.Font("Dialog", 1, 12));
		btnReconstruir.setText("Reconstruir");
		btnReconstruir.setBounds(50, 200, 120, 25);
		btnReconstruir.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Thread teste = new Thread();

				teste.run();
				Reconstruir();
			}
		});
		pnlGeral.add(btnReconstruir);

		Label1.setText("Aplicar Correcao de Atenuacao");
		Label1.setBounds(10, 10, 190, 15);
		pnlCorrecaoAtt.add(Label1);

		Label2.setFont(new java.awt.Font("Dialog", 1, 12));
		Label2.setText("Reconstrucao Tomografica");
		Label2.setAlignment(java.awt.Label.CENTER);
		Label2.setBounds(20, 10, 180, 15);
		pnlGeral.add(Label2);

		Label3.setFont(new java.awt.Font("Dialog", 1, 12));
		Label3.setText("ML-EM");
		Label3.setAlignment(java.awt.Label.CENTER);
		Label3.setBounds(20, 40, 180, 15);
		pnlGeral.add(Label3);

		Label4.setText("Numero de Iteracoes:");
		Label4.setBounds(10, 10, 140, 15);
		pnlNumIter.add(Label4);

		// pnlCorrecaoAtt.setBackground(java.awt.Color.lightGray);
		pnlCorrecaoAtt.setBorder(new javax.swing.border.EtchedBorder());
		pnlCorrecaoAtt.setLayout(null);

		cbSim.setLabel("Sim");
		cbSim.setState(false);
		cbSim.setBounds(50, 30, 60, 23);
		cbSim.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				cbSimStateChanged(evt);
			}
		});
		pnlCorrecaoAtt.add(cbSim);

		cbNao.setLabel("Nao");
		cbNao.setState(true);
		cbNao.setBounds(110, 30, 60, 23);
		cbNao.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				cbNaoStateChanged(evt);
			}
		});
		pnlCorrecaoAtt.add(cbNao);

		pnlGeral.add(pnlCorrecaoAtt);
		pnlCorrecaoAtt.setBounds(10, 70, 210, 60);

		pnlNumIter.setLayout(null);
		pnlNumIter.setBorder(new javax.swing.border.EtchedBorder());
		// pnlNumIter.setBackground(java.awt.Color.lightGray);
		pnlNumIter.setBorder(new javax.swing.border.EtchedBorder());

		tfNumIter.setBackground(new java.awt.Color(255, 255, 255));
		tfNumIter.setFont(new java.awt.Font("Dialog", 1, 12));
		tfNumIter.setText("5");
		tfNumIter.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				Funcoes.limitaTextField(evt, tfNumIter, TAMANHO_TF_NUM_ITER);
			}
		});
		pnlNumIter.add(tfNumIter);
		tfNumIter.setBounds(150, 10, 50, 19);

		pnlGeral.add(pnlNumIter);
		pnlNumIter.setBounds(10, 140, 210, 40);

		getContentPane().add(pnlGeral);

		// pack();
	}

	/*
	 * Procedimento que altera os valores de vari√°veis e objetos da janela
	 * relacionado com a imagem para mapa de atenua√ß√£o.
	 */
	private void cbNaoStateChanged(java.awt.event.ItemEvent evt) {

		if (cbNao.getState()) {
			cbSim.setState(false);
		} else {
			cbSim.setState(true);
		}
	}

	private void cbSimStateChanged(java.awt.event.ItemEvent evt) {

		if (cbSim.getState()) {
			cbNao.setState(false);
		} else {
			cbNao.setState(true);
		}
	}

	/**
	 * Fun√ß√£o para obter as imagens para efetuar a reconstru√ß√£o
	 * 
	 * @result Retorna um vetor de ImagePlus sendo a primeira posi√ß√£o para o
	 *         sinograma e a segunda para o mapa de atenua√ß√£o quando
	 *         necess√°rio.
	 */
	private ImagePlus[] AbrirImg() {

		AbrirImagem abrir = new AbrirImagem();
		abrir.combo1.ativo = true;
		abrir.combo2.ativo = cbSim.getState();
		int[] list = WindowManager.getIDList();
		if ((abrir.combo2.ativo) && (list.length < 2)) {
			IJ
					.showMessage("Erro, nao e possivel efetuar a operacao devido ao numero de imagens abertas.");
			return null;
		}
		ImagePlus[] imgOrig = null;
		boolean repetir = true;
		boolean repetirMapa = true;
		while (repetir) {
			setVisible(false);
			imgOrig = abrir.Abrir_ML_EM();

			if (imgOrig[0] == null)
				if (IJ.showMessageWithCancel("Erro",
						"Nao foi possivel abrir a imagem, Repetir?"))
					repetir = true;
				else {
					repetir = false;
					setVisible(true);
					return imgOrig;
				}
			else {
				repetir = false;
				if (cbNao.getState())
					setVisible(true);
			}
		}
		if (cbSim.getState())
			while (repetirMapa) {
				if (imgOrig[1] == null)
					if (IJ
							.showMessageWithCancel("Erro",
									"Nao foi possivel abrir o Mapa de Atenuacao, Repetir?"))
						repetirMapa = true;
					else {
						repetirMapa = false;
						setVisible(true);
						return imgOrig;
					}
				else {
					setVisible(true);
					return imgOrig;
				}
			}
		return (imgOrig);
	}

	private void Reconstruir() {

		ImagePlus imgFinal = null;
		ImagePlus[] imgsOrig = AbrirImg();
		if (imgsOrig[0] != null) {

			long startTime = System.currentTimeMillis();
			tamanho_imagem = imgsOrig[0].getWidth();
			type = imgsOrig[0].getType();
			if ((tamanho_imagem < 0) || !(Funcoes.isPowerOf2(tamanho_imagem))) {
				IJ.showMessage(Funcoes.MENSAGEM_IMAGEM_NAO_COMPATIVEL);
				return;
			}
			if (cbNao.getState()) {
				if (type == ImagePlus.GRAY32)
					imgFinal = Reconstruir_32bits(imgsOrig[0]);
				else if (type == ImagePlus.COLOR_RGB)
					imgFinal = Reconstruir_RGB(imgsOrig[0]);

				imgFinal.setTitle(imgFinal.getShortTitle() + " (MLEM - "
						+ tfNumIter.getText()
						+ " Iteracoes; Sem correcao de atenuacao)");
			} else if (cbSim.getState()) {
				if (type == ImagePlus.GRAY32)
					imgFinal = Reconstruir_32bits_ATT(imgsOrig);
				else if (type == ImagePlus.COLOR_RGB)
					imgFinal = Reconstruir_RGB_ATT(imgsOrig);

				imgFinal.setTitle(imgFinal.getShortTitle() + " (MLEM -"
						+ tfNumIter.getText()
						+ " Iteracoes; Com correcao de atenuacao)");
			}

			if (Funcoes.ativarVSA) {
				ImagePlus[] imgs = new ImagePlus[2];
				imgs[0] = imgsOrig[0];
				imgs[1] = ((new AbrirImagem().Abrir("imgSinograma", "", true,
						false))[0]);
				Funcoes.calculaVSA(imgs);

			}
			if (Funcoes.ativarMSE) {
				ImagePlus[] imgs = new ImagePlus[2];
				imgs[0] = imagemOriginal;
				imgs[1] = imgFinal;
				Funcoes.calculaMSE(imgs);
			}

			imgFinal.show();
			IJ.showTime(imgsOrig[0], startTime, "MLEM - "
					+ Integer.parseInt(tfNumIter.getText()) + " iteracoes. ");
		}
	}

	private ImageProcessor criaIdentidade(ImagePlus imgOrig) {

		float[] identidade = new float[imgOrig.getWidth() * imgOrig.getHeight()];
		FloatProcessor ipIdentidade = new FloatProcessor(imgOrig.getWidth(),
				imgOrig.getHeight());

		for (int a = 0; a < identidade.length; a++)
			identidade[a] = 1;

		ipIdentidade.setPixels(identidade);

		return ipIdentidade;
	}

	private ImagePlus Reconstruir_32bits(ImagePlus imgOrig) {
        
        float afImgOrig[], afSinograma[];
        float afRatio[], afFinal[];
        ImagePlus imgFinal, imgSino;
        ImageStack is_imagemFinal, is_imagemSino;
        
        is_imagemSino = new ImageStack(imgOrig.getWidth(), imgOrig.getHeight());
        is_imagemFinal = new ImageStack(tamanho_imagem, tamanho_imagem);
        
// FBP_ FBP = new FBP_(imgOrig);
// ImagePlus imgEstInicial = FBP.FBP_Reconstruir(FBP_.FILTRO_RAMPA);
        ImagePlus imgEstInicial = EstimativaInicial(imgOrig);
        
        ImageProcessor ipIdentidade = criaIdentidade(imgOrig);
        Retroprojetor retIdent = new Retroprojetor(ipIdentidade);
        ImageProcessor ipIdentidadeTemp = retIdent.retroProjetar(false);
        float[] afipIdentidadeTemp = (float[]) ipIdentidadeTemp.getPixels();
        
        if (Funcoes.debug)
            imgEstInicial.show();
        int numIterTotal = Integer.parseInt(tfNumIter.getText());
        int num_stacks = imgEstInicial.getStackSize();
        for (int stack_count = 1; stack_count <= num_stacks; stack_count++) {
            
            ImageProcessor ipEstInicial = imgEstInicial.getImageStack().getProcessor(stack_count);
            for (int numIteracoes = 0; numIteracoes < numIterTotal; numIteracoes++) {
                
                Projetor projetor = new Projetor(ipEstInicial, imgOrig
                        .getHeight());
                ImageProcessor ipSinograma = projetor.projetar(false);
                ipSinograma.resetMinAndMax();
                ImagePlus[] imgsSino = {imgOrig ,(new ImagePlus("Imagem Sinograma",ipSinograma))};
                if (Funcoes.ativarVSA)
                    Funcoes.calculaVSA(imgsSino);
                
                  if (Funcoes.debug) { 
                	  ImagePlus implus = new ImagePlus("MLEM -Imagem Sinograma; iteracao "+(numIteracoes+1),ipSinograma);
                  implus.show(); 
                  }
                 
                is_imagemSino.addSlice("MLEM - Imagem Sinograma; iteracao "
                        + (numIteracoes + 1), ipSinograma, numIteracoes);
                
                IJ.showStatus("MLEM - Reconstruindo... Stack " + stack_count
                        + ", Iteracao " + (numIteracoes + 1));
                afImgOrig = (float[]) imgOrig.getImageStack().getProcessor(
                        stack_count).getPixels();
                afSinograma = (float[]) ipSinograma.getPixels();
                afRatio = new float[afImgOrig.length];
                afFinal = new float[afImgOrig.length];
                for (int a = 0; a < afImgOrig.length; a++)
                    afRatio[a] = afImgOrig[a] / afSinograma[a];
                
                FloatProcessor ipRatio = new FloatProcessor(imgOrig.getWidth(),
                        imgOrig.getHeight());
                ipRatio.setPixels(afRatio);
                
                Retroprojetor retroprojetor = new Retroprojetor(ipRatio);
                
                ImageProcessor ipReconstruidaTemp = retroprojetor.retroProjetar(false);
                
                float[] afipReconstruidaTemp = (float[]) ipReconstruidaTemp.getPixels();
                
                float[] afipEstInicial = (float[]) ipEstInicial.getPixels();
                afFinal = afipReconstruidaTemp;
                
                
                
                if (numIteracoes > 1)
                    for (int i = 0; i < afipEstInicial.length; i++)
                        afFinal[i] = (afipEstInicial[i] * afipReconstruidaTemp[i])/afipIdentidadeTemp[i];
                ipEstInicial = ipReconstruidaTemp;
                
                 if (Funcoes.ativarMSE) {
                    ImagePlus[] imagens = new ImagePlus[2];
                    if (numIteracoes == 0) {
                        AbrirImagem abrir = new AbrirImagem();
                        imagens = abrir.Abrir("Imagem Original","", true, false);
                        imagemOriginal = imagens[0];
                    }
                    imagens[0] = imagemOriginal;
                    imagens[1] = new ImagePlus("Imagem Reconstruida IteraÁ„o "+numIteracoes+1,ipReconstruidaTemp);
                    Funcoes.calculaMSE(imagens);
                }
                
            }
            ipEstInicial.resetMinAndMax();
            is_imagemFinal.addSlice("MLEM - Reconstrucao",
                    ipEstInicial, stack_count - 1);
            
            if (Funcoes.debug) {
                imgSino = new ImagePlus(imgOrig.getShortTitle()
                + " Imagem Sinograma; stack " + num_stacks,
                        is_imagemSino);
                imgSino.show();
            }
        }
        
        imgFinal = new ImagePlus(imgOrig.getShortTitle()
        + " (MLEM - Imagem Reconstruida)", is_imagemFinal);
        return imgFinal;
    }

	private ImagePlus Reconstruir_32bits_ATT(ImagePlus[] imgsOrig) {

		float afImgOrig[], afSinograma[];
		float afRatio[], afFinal[];

		ImagePlus imgOrig = imgsOrig[0];
		ImagePlus imgMapaAtt = imgsOrig[1];
		ImagePlus imgFinal, imgSino;
		ImageStack is_imagemFinal, is_imagemSino;

		is_imagemSino = new ImageStack(imgOrig.getWidth(), imgOrig.getHeight());
		is_imagemFinal = new ImageStack((int) tamanho_imagem,
				(int) tamanho_imagem);

		// FBP_ FBP = new FBP_(imgOrig);
		// ImagePlus imgReconstruidaFBP =
		// FBP.FBP_Reconstruir(FBP_.FILTRO_RAMPA);
		ImagePlus imgEstInicial = EstimativaInicial(imgOrig);
		
		ImageProcessor ipIdentidade = criaIdentidade(imgOrig);
		
		/*
		 * 
		 * Sabendo que "ipIdentidade" ser· passada para o construtor da classe Retroprojetar,
		 * esta pode receber 0 (zero)??? 
		 * 
		 * 
		 */
		
		
		Retroprojetor retIdent = new Retroprojetor(ipIdentidade, imgMapaAtt
				.getProcessor());
		
		ImageProcessor ipIdentidadeTemp = retIdent.retroProjetar(true);
		float[] afipIdentidadeTemp = (float[]) ipIdentidadeTemp.getPixels();

		// esta condiÁ„o n„o existia para este mÈtodo
		if (Funcoes.debug)
			imgEstInicial.show();
		int numIterTotal = Integer.parseInt(tfNumIter.getText());

		/*
		 * MudanÁas realizadas no dia 20/05/2008 //num_stacks estava recebendo o
		 * valor do comando abaixo (era da imagem original e n„o da imagem que
		 * foi passada ao mÈtodo Estimativainicial //int num_stacks =
		 * imgOrig.getStackSize();
		 * 
		 * //o comando abaixo foi adicionado, no lugar de
		 * "imgOrig.getStackSize();" foi adicionado
		 * "imgReconstruidaFBP.getStackSize();" // observe a semelhanÁa com o
		 * mÈtodo Reconstruir_32bits sem mapa de atenuaÁ„o
		 */

		int num_stacks = imgEstInicial.getStackSize();
		for (int stack_count = 1; stack_count <= num_stacks; stack_count++) {

			ImageProcessor ipEstInicial = imgEstInicial.getImageStack()
					.getProcessor(stack_count);
			ImageProcessor ipImgMapa = imgMapaAtt.getImageStack().getProcessor(
					stack_count);

			for (int numIteracoes = 0; numIteracoes < numIterTotal; numIteracoes++) {

				Projetor projetor = new Projetor(ipEstInicial, ipImgMapa,
						imgOrig.getHeight());
				ImageProcessor ipSinograma = projetor.projetar(true);
				ipSinograma.resetMinAndMax();
				ImagePlus[] imgsSino = { imgOrig,
						(new ImagePlus("Imagem Sinograma", ipSinograma)) };
				if (Funcoes.ativarVSA)
					Funcoes.calculaVSA(imgsSino);
				// if (numIteracoes==(numIterTotal-1))
				is_imagemSino.addSlice("MLEM - Imagem Sinograma iteracao "
						+ numIteracoes, ipSinograma, numIteracoes);

				ij.IJ.showStatus("MLEM - Reconstruindo... Stack " + stack_count
						+ ", Iteracao " + (numIteracoes + 1));

				afImgOrig = (float[]) imgOrig.getImageStack().getProcessor(
						stack_count).getPixels();
				afSinograma = (float[]) ipSinograma.getPixels();
				afRatio = new float[afImgOrig.length];
				afFinal = new float[afImgOrig.length];

				for (int a = 0; a < afImgOrig.length; a++) {
					if (afSinograma[a] > 0)
						afRatio[a] = afImgOrig[a] / afSinograma[a];
					else
						afRatio[a] = 1;
				}

				FloatProcessor ipRatio = new FloatProcessor(imgOrig.getWidth(),
						imgOrig.getHeight());
				ipRatio.setPixels(afRatio);

				
				/*
				 * 
				 * 
				 *verificar se ipratio nao esta em branco ao efetuar a segunda iteracao
				 *
				 *na verdade ipRatio est· em branco ainda na primeira iteraÁ„o!!!!
				 *TambÈm est· em branco nas demais iteraÁıes!
				 * 
				 */
				
				Retroprojetor retroprojetor = new Retroprojetor(ipRatio);
				
				///verificar imagem de retorno do retroprojetar
				// se imagem retorno == em branco (0) problema e no retroprojetor
				///senao verificar funcoes abaixo
				
				
				/*
				 * 
				 * CONFIRMADOOOOOO!!!!!
				 * 
				 * retroprojetor est· recebendo a imagem do sinograma "zerada", e agora?
				 * Experimenta debugar e observe que ao entrar no construtor da classe
				 * Retroprojetar, passando ipRatio, "retroprojetor" recebe p_sino com valor m·ximo = 0!!
				 * 
				 */
				
				ImageProcessor ipReconstruidaTemp = retroprojetor
						.retroProjetar(true);
				ipReconstruidaTemp.resetMinAndMax();
				
				/*
				 * 
				 * CONFIRMADOOOOO!!!
				 * 
				 * ipReconstruidaTemp est· nula ao retornar do mÈtodo retroProjetar (ainda na primeira iteraÁ„o!!!)
				 * 
				 */

				float[] afipReconstruidaTemp = (float[]) ipReconstruidaTemp.getPixels();
				float[] afipEstInicial = (float[]) ipEstInicial.getPixels();
				
				afFinal = afipReconstruidaTemp.clone();
				// if (numIteracoes > 1)
				for (int i = 0; i < afipEstInicial.length; i++)
					if (!Float.isNaN(afipIdentidadeTemp[i]))
						afFinal[i] = (afipEstInicial[i] * afipReconstruidaTemp[i])
								/ afipIdentidadeTemp[i];
				
				//ipReconstruidaTemp pode estar em branco verificar ao retornar do retroprojetar
				
				ipEstInicial = ipReconstruidaTemp.duplicate();

				if (Funcoes.ativarMSE) {
					ImagePlus[] imagens = new ImagePlus[2];
					if (numIteracoes == 0) {
						AbrirImagem abrir = new AbrirImagem();
						imagens = abrir.Abrir("Imagem Original", "", true,
								false);
						imagemOriginal = imagens[0];
					}
					imagens[0] = imagemOriginal;
					imagens[1] = new ImagePlus("Imagem Reconstruida IteraÁ„o "
							+ numIteracoes + 1, ipEstInicial);
					Funcoes.calculaMSE(imagens);
				}
				
				
			}// fim da quantidade de iteracoers
			ipEstInicial.resetMinAndMax();
			is_imagemFinal.addSlice("MLEM - Reconstrucao Simples",
					ipEstInicial, stack_count - 1);
		}
		imgSino = new ImagePlus(imgOrig.getShortTitle()
				+ " (MLEM - Imagem Sinograma)", is_imagemSino);
		imgFinal = new ImagePlus(imgOrig.getShortTitle()
				+ " (MLEM - Imagem Reconstruida)", is_imagemFinal);

		if (Funcoes.debug)
			imgSino.show();

		return imgFinal;
	}

	private ImagePlus Reconstruir_RGB(ImagePlus imgOrig) {

		ImagePlus imagemTemp = null;
		ImagePlus imagemRGBReconstruida = null, imagemRGBStackReconstruida = null;
		ImageStack is_imagemFinal = new ImageStack((int) tamanho_imagem,
				(int) tamanho_imagem);

		int num_stacks = imgOrig.getStackSize();
		for (int stack_count = 1; stack_count <= num_stacks; stack_count++) {

			imagemTemp = new ImagePlus("imagemTemp", imgOrig.getImageStack()
					.getProcessor(stack_count));

			ImageConverter imgConvRGBStack = new ImageConverter(imagemTemp);
			imgConvRGBStack.convertToRGBStack();

			StackConverter ConvGray32 = new StackConverter(imagemTemp);
			ConvGray32.convertToGray32();

			if (Funcoes.debug)
				imagemTemp.show();

			imagemRGBStackReconstruida = Reconstruir_32bits(imagemTemp);

			if (Funcoes.debug)
				imagemRGBStackReconstruida.show();

			StackConverter ConvGray8 = new StackConverter(
					imagemRGBStackReconstruida);
			ConvGray8.convertToGray8();

			ImageConverter ConvStackToRGB = new ImageConverter(
					imagemRGBStackReconstruida);
			ConvStackToRGB.convertRGBStackToRGB();

			is_imagemFinal.addSlice("", imagemRGBStackReconstruida
					.getProcessor(), stack_count - 1);

		}
		imagemRGBReconstruida = new ImagePlus(imgOrig.getShortTitle()
				+ " (MLEM - Imagem Reconstruida)", is_imagemFinal);

		return imagemRGBReconstruida;
	}

	private ImagePlus Reconstruir_RGB_ATT(ImagePlus[] imgsOrig) {

		ImagePlus imgOrig = imgsOrig[0];
		ImagePlus imgMapaAtt = imgsOrig[1];
		ImagePlus[] imagemTemp = null;
		ImagePlus imagemRGBReconstruida = null, imagemRGBStackReconstruida = null;
		ImageStack is_imagemFinal = new ImageStack((int) tamanho_imagem,
				(int) tamanho_imagem);

		int num_stacks = imgOrig.getStackSize();
		for (int stack_count = 1; stack_count <= num_stacks; stack_count++) {

			imagemTemp[0] = new ImagePlus("", imgOrig.getImageStack()
					.getProcessor(stack_count));

			ImageConverter imgConvRGBStack = new ImageConverter(imagemTemp[0]);
			imgConvRGBStack.convertToRGBStack();

			StackConverter ConvGray32 = new StackConverter(imagemTemp[0]);
			ConvGray32.convertToGray32();
			imagemTemp[1] = imgMapaAtt;
			imagemRGBStackReconstruida = Reconstruir_32bits_ATT(imagemTemp);

			StackConverter ConvGray8 = new StackConverter(
					imagemRGBStackReconstruida);
			ConvGray8.convertToGray8();

			ImageConverter ConvStackToRGB = new ImageConverter(
					imagemRGBStackReconstruida);
			ConvStackToRGB.convertRGBStackToRGB();

			is_imagemFinal.addSlice("", imagemRGBStackReconstruida
					.getProcessor(), stack_count - 1);

		}
		imagemRGBReconstruida = new ImagePlus(imgOrig.getShortTitle()
				+ " (MLEM - Imagem Reconstruida; com mapa de Atenuacao)",
				is_imagemFinal);

		return imagemRGBReconstruida;
	}

	private ImagePlus EstimativaInicial(ImagePlus iplus) {

		int imgWidth = iplus.getWidth();
		int imgHeight = iplus.getHeight();
		ImageStack is = new ImageStack(imgWidth, imgWidth);
		for (int sl = 1; sl <= iplus.getStackSize(); sl++) {

			float[] aPixels = (float[]) iplus.getImageStack().getProcessor(sl)
					.getPixels();
			float somaPixels = 0;
			int xmax, fovmax = 0, mediaPixels = 0;
			FloatProcessor ipEstInicial = new FloatProcessor(imgWidth, imgWidth);
			xmax = (imgWidth - 2) / 2;

			for (int a = 0; a < aPixels.length; a++)
				somaPixels = somaPixels + aPixels[a];

			mediaPixels = (int) somaPixels / aPixels.length;
			int newmedia = (int) (somaPixels / imgHeight / Math.PI / xmax / xmax);

			// IJ.log("mediaPixels= "+mediaPixels);
			// IJ.log("newmedia= "+newmedia);

			for (int y = -xmax; y <= xmax; y++) {
				fovmax = Funcoes.calculaFOV(xmax, y);
				for (int x = -fovmax; x <= fovmax; x++)
					ipEstInicial.putPixelValue(x + (imgWidth / 2) - 1, y
							+ (imgWidth / 2) - 1, newmedia);
			}
			is.addSlice("M√©dia das Contagens " + sl, ipEstInicial);
		}
		ImagePlus EstInicial = new ImagePlus(
				"Estimativa Inicial M√©dia das Contagens", is);

		if (Funcoes.debug) {
			EstInicial.show();
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