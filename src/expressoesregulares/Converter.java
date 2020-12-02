package expressoesregulares;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;


class Parenteses {
  int posIni;
  int posFim;
}

// [1][10]
// [2][]
// [3][4]
// [5][6]
// [7][]
// [11][]
// entrada: a((()()()))()

public class Converter {
  private String entrada;
  public Converter(String entrada){
    this.entrada = entrada;
  }
  boolean temParenteses = false;
  String entradaAuxiliar;
  //"((ab(a|b))(100|ε))";
  List<Parenteses> parentesesList = new ArrayList<>();


  public void transformRegexToAFND(){
    boolean inicio = true;
    entradaAuxiliar = entrada;
    while(temParenteses || inicio == true){
      inicio = false;
      temParenteses = procuraParenteses();
    }


//    procuraParentesesMatriz();
//    createAFND();
  }

  private void procuraParentesesMatriz() {

  }

    private boolean procuraParenteses() {
    int qtdParentenses = 0;
    boolean primeiro= false;
    StringBuilder sb = new StringBuilder(entradaAuxiliar);

    Parenteses parente = new Parenteses();

    for (int i = 0; i < this.entrada.length(); i++) {

      if(this.entradaAuxiliar.charAt(i)== '('){
        temParenteses = true;
        if(primeiro == false){
          sb.deleteCharAt(i);
          primeiro = true;
          parente.posIni = i;
        }
        qtdParentenses++;
      }

      if(this.entradaAuxiliar.charAt(i) == ')'){
        qtdParentenses--;
      }

      if(qtdParentenses == 0){
        parente.posFim = i;
        parentesesList.add(parente);
        sb.deleteCharAt(i -1);

        primeiro = false;
      }
    }

    entradaAuxiliar = sb.toString();
    return temParenteses;
  }

  public void createAFND(){

  }
}
