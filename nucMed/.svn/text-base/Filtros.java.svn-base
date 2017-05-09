/**
 *
 */
package nucMed;

import funcoesNM.Complexo;
import funcoesNM.Funcoes;

/**
 * @author Marcus Vin�cius da Silva Costa
 *
 * @version <b>1.7</b>
 *
 */

public class Filtros {
  
  private int tamImg;
  
  private float fc;
  
  private float N;
  
  private int tipoFiltro;
  
  /**
   * Constructor da classe Filtros
   * 
   * Inicaliza as variaveis utilizadas na classe.
   *
   */
  public Filtros(float fc,float N, int tipoFiltro, int tamImg) {
    
    this.tamImg = tamImg;
    this.fc = fc;
    this.N= N;
    this.tipoFiltro = tipoFiltro;
  }
  
  public Complexo[] gera_filtro_rampa() {
    
    Complexo filter_rampa[] = new Complexo[(int) (tamImg)];
    float f;// , fc;
    int aux;
    // fc = Float.parseFloat(tfParametro1.getText());
    for (aux = 0; aux < tamImg; aux++) {
      f = ((float) aux / (float) tamImg);
      if (tipoFiltro == Funcoes.FILTRO_RAMPA)
        if (fc < f)
          if (Funcoes.filtroCorte)
            filter_rampa[aux] = new Complexo(0.0, 0.0);
          else
            filter_rampa[aux] = new Complexo((f), 0.0);
        else
          filter_rampa[aux] = new Complexo((f), 0.0);
      else
        filter_rampa[aux] = new Complexo((f), 0.0);
    }
                /*
                 * if (execucao_grafica) { setSize(JANELA_RAMPA_WIDTH,
                 * JANELA_RAMPA_HEIGHT); btnReconstruir.setLocation(JANELA_RAMPA_WIDTH -
                 * 160, JANELA_RAMPA_HEIGHT - 60); btnReconstruir.setVisible(true); }
                 */
    return filter_rampa;
  }
  
  /**
   * Função que calcula o vetor para o filtro.
   *
   * @return Retorna um vetor de complexo.
   */
  public Complexo[] gera_filtro_butterworth() {
    
    Complexo filter_butter[] = new Complexo[(int) (tamImg)];
    float f, c1;// ,fc
    int aux;// , N;
    
    // N = Integer.parseInt(tfParametro1.getText());
    // fc = Float.parseFloat(tfParametro2.getText());
    for (aux = 0; aux < tamImg; aux++) {
      f = (float) aux / tamImg;
      c1 = (float) ((Math.pow((f / fc), (N * 2))) + 1);
      filter_butter[aux] = new Complexo((1 / c1), 0.0);
    }
                /*
                 * if (execucao_grafica) { setSize(JANELA_BUTTERWORTH_WIDTH,
                 * JANELA_BUTTERWORTH_HEIGHT);
                 * btnReconstruir.setLocation(JANELA_BUTTERWORTH_WIDTH - 160,
                 * JANELA_BUTTERWORTH_HEIGHT - 60); btnReconstruir.setVisible(true); }
                 */
    return filter_butter;
  }
  
  /**
   * Função que calcula o vetor para o filtro.
   *
   * @return Retorna um vetor de complexo.
   */
  public Complexo[] gera_filtro_shepp_logan() {
    
    Complexo filter_shepp[] = new Complexo[(int) (tamImg)];
    float f, c1, c2;// , fc
    int aux;
    // fc = Float.parseFloat(tfParametro1.getText());
    
    for (aux = 0; aux < tamImg; aux++) {
      f = (float) aux / tamImg;
      c1 = (float) (Math.PI * f) / (2 * fc);
      c2 = (float) Math.sin(c1);
      if (fc < f)
        if (Funcoes.filtroCorte)
          filter_shepp[aux] = new Complexo(0.0, 0.0);
        else
          filter_shepp[aux] = new Complexo((c2 / c1), 0.0);
      else
        filter_shepp[aux] = new Complexo((c2 / c1), 0.0);
      filter_shepp[0] = new Complexo(1.0, 0.0);
    }
                /*
                 * if (execucao_grafica) { setSize(JANELA_SHEPP_LOGAN_WIDTH,
                 * JANELA_SHEPP_LOGAN_HEIGHT);
                 * btnReconstruir.setLocation(JANELA_SHEPP_LOGAN_WIDTH - 160,
                 * JANELA_SHEPP_LOGAN_HEIGHT - 60); btnReconstruir.setVisible(true); }
                 */
    return filter_shepp;
  }
  
  /**
   * Função que calcula o vetor para o filtro.
   *
   * @return Retorna um vetor de complexo.
   */
  public Complexo[] gera_filtro_hamming() {
    
    Complexo filter_hamming[] = new Complexo[(int) (tamImg)];
    float f, c1, c2;// , fc
    int aux;
    // fc = Float.parseFloat(tfParametro1.getText());
    for (aux = 0; aux < tamImg; aux++) {
      f = (float) aux / tamImg;
      c1 = (float) (Math.PI * f) / fc;
      c2 = (float) (0.46 * Math.cos(c1));
      if (fc < f)
        if (Funcoes.filtroCorte)
          filter_hamming[aux] = new Complexo(0.0, 0.0);
        else
          filter_hamming[aux] = new Complexo(
            (Funcoes.CONST_FILTRO_HAMMING + c2), 0.0);
      else
        filter_hamming[aux] = new Complexo(
          (Funcoes.CONST_FILTRO_HAMMING + c2), 0.0);
    }
                /*
                 * if (execucao_grafica) { setSize(JANELA_HAMMING_WIDTH,
                 * JANELA_HAMMING_HEIGHT);
                 * btnReconstruir.setLocation(JANELA_HAMMING_WIDTH - 160,
                 * JANELA_HAMMING_HEIGHT - 60); btnReconstruir.setVisible(true); }
                 */
    return filter_hamming;
  }
  
}
