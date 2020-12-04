package afnd.domain;


public class Automato {
  protected boolean debug = false;

  protected int[] aceitacao;
  protected int estadoInicial = 0;
  protected int[][][] transicao;
  protected int[][] transicaoVazia;
  protected String alfabeto;

  protected String fitaDeEntrada;

  protected Automato(int[] aceitacao, int estadoInicial, int[][][] transicao, int[][] transicaoVazia, String alfabeto) {
    System.out.println(estadoInicial);
    this.aceitacao = aceitacao;
    this.estadoInicial = estadoInicial;
    this.transicaoVazia =transicaoVazia;
    this.alfabeto = alfabeto;
    this.transicao = transicao;
  }

  protected Automato(int[] aceitacao, int estadoInicial, int[][][] transicao, String alfabeto) {
    this.aceitacao = aceitacao;
    this.transicao = transicao;
    this.alfabeto = alfabeto;
    this.estadoInicial = estadoInicial;
  }

  public Automato(){}

  public void setDebug(boolean debug) {
    this.debug = debug;
  }
  public boolean isDebug() {
    return debug;
  }

  public int[] getAceitacao() {
    return aceitacao;
  }

  public void setAceitacao(int[] aceitacao) {
    this.aceitacao = aceitacao;
  }

  public int getEstadoInicial() {
    return estadoInicial;
  }

  public void setEstadoInicial(int estadoInicial) {
    this.estadoInicial = estadoInicial;
  }

  public int[][][] getTransicao() {
    return transicao;
  }

  public void setTransicao(int[][][] transicao) {
    this.transicao = transicao;
  }

  public int[][] getTransicaoVazia() {
    return transicaoVazia;
  }

  public void setTransicaoVazia(int[][] transicaoVazia) {
    this.transicaoVazia = transicaoVazia;
  }

  public String getAlfabeto() {
    return alfabeto;
  }

  public void setAlfabeto(String alfabeto) {
    this.alfabeto = alfabeto;
  }

  public String getFitaDeEntrada() {
    return fitaDeEntrada;
  }

  public void setFitaDeEntrada(String fitaDeEntrada) {
    this.fitaDeEntrada = fitaDeEntrada;
  }

  /**Metodo para converter um automato finito nao deterministico para um automato deterministico
   * @param naoDeterministicoE
   */
  protected void convertToDfa(NaoDeterministicoE naoDeterministicoE){
  }

  public Automato createObject(Automato automato) {
    return automato;
  }

}
