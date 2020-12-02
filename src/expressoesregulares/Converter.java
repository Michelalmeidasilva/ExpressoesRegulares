package expressoesregulares;

import java.util.*;


class Parenteses {
  int posIni;
  int posFim;
}

class Posicao {
  boolean marcacao;
  String value;
}
public class Converter {
  List<Parenteses> parentesesList = new ArrayList<>();

  public void transformRegexToAFND(String entrada){
      procuraParenteses(entrada);
  }

  private void procuraParenteses(String entrada) {
    Map<Integer , Boolean> posicoes = new HashMap< Integer, Boolean>();
    for (int i =0; i < entrada.length(); i ++){
      posicoes.put( i, false);
    }

    Parenteses parenteses = new Parenteses();

    for (int i = 0; i < entrada.length(); i++) {

      if(entrada.charAt(i) == '(' && posicoes.get(i) == false){
         parenteses.posIni = i;
      }

      if(entrada.charAt(i) == ')' && posicoes.get(i) == false){
        parenteses.posFim = i;
        posicoes.put(parenteses.posIni, true);
        posicoes.put(parenteses.posFim, true);
        parentesesList.add(parenteses);
        parenteses = new Parenteses();
        i = 0;
      }
    }
  }

  public void createAFND(){

  }
}
