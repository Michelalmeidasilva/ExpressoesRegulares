package expressoesregulares.conversao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Parenteses {
  int posIni;
  int posFim;
  int nivel;
  String conteudo;
  public Parenteses(){ }
  public Parenteses (int posIni, int posFim, String conteudo, int nivel){
    this.posIni = posIni;
    this.posFim = posFim;
    this.conteudo = conteudo;
    this.nivel = nivel;
  }
  /**
   * @param entrada
   * Mapeamento de parenteses
   * da entrada por nivel seguindo a logica
   * de precedencia de parenteses, quanto mais profundo
   * o parenteses maior o  nivel acoplado ao objeto da classe Parenteses
   */
  static  void procuraParenteses(List<Parenteses> mapa  , String entrada) {
    Map<Integer , Boolean> posicoes = new HashMap< Integer, Boolean>();
    Parenteses parenteses = new Parenteses();
    int nivel = 0;

    mapa.add(new Parenteses (0 ,( entrada.length() -1) , entrada, 0));
    for (int i =0; i < entrada.length(); i ++){
      posicoes.put( i, false);
    }

    for (int i = 0; i < entrada.length(); i++) {
      char atual= entrada.charAt(i);

      if(atual == '(') nivel++;

      if(atual == '(' && posicoes.get(i) == false) parenteses.posIni = i;

      if(atual == ')' && posicoes.get(i) == false){
        parenteses.posFim = i;
        parenteses.nivel = nivel;
        parenteses.conteudo = entrada.substring(parenteses.posIni + 1, parenteses.posFim);
        posicoes.put(parenteses.posIni, true);
        posicoes.put(parenteses.posFim, true);
        mapa.add(parenteses);
        parenteses = new Parenteses();
        i = -1;
        nivel = 0;
      }

      if(atual == ')' && nivel > 0)  nivel --;
    }
  }
  /**
   * @param entrada
   * @return
   * @throws Exception
   */
  static public boolean checaParenteses(String entrada) throws Exception {
    int qtdParenteses = 0;
    for (int i = 0; i < entrada.length(); i++) {
      if (entrada.charAt(i) == '(')
        qtdParenteses++;
      if(entrada.charAt(i) == (')'))
        qtdParenteses--;
    }
    if(qtdParenteses != 0)
      throw new Exception("Entrada mal formatada e ou invalida");
    else
      return true;
  }
}

class ParentesesException extends Exception{
  public ParentesesException(String message) {
    super(message);
  }
}
