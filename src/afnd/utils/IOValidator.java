package afnd.utils;


import afnd.exceptions.InvalidFormatException;

public class IOValidator {
  private  boolean debug;
  private int linha ;
  private int coluna = 0;
  private int  profundidade = 0 ;

  /**
   * @param fitaDeEntrada
   * @param alfabeto
   * @return
   * @throws InvalidFormatException
   */
  public  static boolean input(String fitaDeEntrada, String[] alfabeto) throws InvalidFormatException {
    boolean validar = false;
    for (int i = 0; i < fitaDeEntrada.length(); i ++) {
      validar = false;
      String letra = fitaDeEntrada.charAt(i) + "";
      for (int j = 0; j < alfabeto.length ; j++){
        if(letra.contains(alfabeto[j])){
          validar = true;
        }
      }
      if(validar == false) throw new InvalidFormatException("Alfabeto não esta coerente com a Entrada ['" + fitaDeEntrada + "']");
    }
    return validar;
  }

  /**
   * @param matriz
   * @return
   */
  public  int [][][] convertMatrizCharToInt(char[][][] matriz){
    sizeOfMatriz(matriz);
    int [][][] tabelaTransicaoInt  = new int[linha][coluna][profundidade];
    for (int i = 0; i < matriz.length; i ++){
      for (int j = 0 ; j < matriz[i].length; j ++){
        for (int k = 0; k < matriz[i][j].length ; k ++){
          tabelaTransicaoInt[i][j][k] = Integer.parseInt( matriz[i][j][k] + "");
          if(debug) System.out.println(tabelaTransicaoInt[i][j][k]);
        }
      }
    }
    return tabelaTransicaoInt;
  }


  public int[][][] convertMatrizIntegerToInt(Integer[][][] matriz, int tam1, int tam2, int tam3) {
    int[][][] retorno = new int[tam1][tam2][tam3];
    for (int i = 0; i < tam1 ; i++) {
      for (int t = 0; t < tam2; t++) {
        for (int x = 0; x < tam3; x++) {
          if(matriz[i][t][x]!= null){
            retorno[i][t][x] = matriz[i][t][x].intValue();
          } else {
            retorno[i][t][x] = -1;
          }
        }
      }
    }
    return retorno;
  }

  public int[][] convertMatrizIntegerToInt(Integer[][] matriz, int tam1, int tam2) {
    int[][] retorno = new int[tam1][tam2];
    for (int i = 0; i < tam1; i++) {
      for (int t = 0; t < tam2; t++) {
        if (matriz[i][t] != null) {
          retorno[i][t] = matriz[i][t].intValue();
        } else {
          retorno[i][t] = -1;
        }
      }
    }
    return retorno;
  }

  /**
   * @param matriz
   * @return
   */
  public  int [][] convertMatrizCharToInt(char[][] matriz){
    sizeOfMatriz(matriz);
    int [][] tabelaTransicaoInt  = new int[linha][coluna];
    for (int i = 0; i < matriz.length; i ++){
      for (int j = 0 ; j < matriz[i].length; j ++){
        tabelaTransicaoInt[i][j] = Integer.parseInt( matriz[i][j] + "");
        if(debug) System.out.println(tabelaTransicaoInt[i][j]);
      }
    }
    return tabelaTransicaoInt;
  }

  private  void sizeOfMatriz(char [][][] matriz) {
    this.linha = matriz.length  ;
    this.coluna = 0;
    this.profundidade = 0 ;
    for (int i = 0; i < matriz.length; i ++){
      this.coluna = matriz[i].length > coluna ? matriz[i].length : coluna;
      for (int j = 0 ; j < matriz[i].length; j ++){
        this.profundidade = matriz[i][j].length > profundidade ? matriz[i][j].length : profundidade;
      }
    }
    if(debug){
      System.out.println("Tamanho max de linha        " + linha);
      System.out.println("Tamanho max de colunas      " + coluna);
      System.out.println("Tamanho max de profundidade " + profundidade);
    }
  }

  private  void sizeOfMatriz(char [][] matriz) {
    this.linha = matriz.length  ;
    this.coluna = 0;
    for (int i = 0; i < matriz.length; i ++){
      this.coluna = matriz[i].length > coluna ? matriz[i].length : coluna;
    }
  }

  /**
   * @param matriz
   * @param debug
   * @return
   */
  public  int [][][] convertMatrizCharToInt(char[][][] matriz,boolean debug) {
    this.debug = debug;
    return  convertMatrizCharToInt(matriz);
  }

  /**
   * @param matriz
   * @param debug
   * @return
   */
  public  int [][] convertMatrizCharToInt(char[][] matriz,boolean debug) {
    this.debug = debug;
    return  convertMatrizCharToInt(matriz);
  }

  /**
   * @param array
   * @return
   */
  public  int [] convertArrayCharToArrayInt(char[] array) {
    int [] arrayInt = new int[array.length];
    for (int i = 0; i < array.length; i++) {
      arrayInt[i] = Integer.parseInt( array[i] + "");;
    }
    return arrayInt;
  }
}
