package expressoesregulares;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Main {
  public static void main(String[] args) throws IOException {
    GerarPalavras expressoesRegulares = new GerarPalavras();

    ArrayList<String> out = new ArrayList<String>();

    String input = "(0|1)*";
    Regex regex = new Regex(input);
    regex.transformRegexToAFND(regex);

//    out = expressoesRegulares.GerarPalavras(1, new Regex("(1010)|(10)|(111)"));
    FileWriter arq = new FileWriter("linguagem.txt", false);
    PrintWriter gravarArq = new PrintWriter(arq);
    gravarArq.printf("\u03A3 = {");

    for (int i = 0; i < out.size(); i++) {
      gravarArq.printf(out.get(i) + ",\n");
    }
    arq.close();

  }


  public static void createAFND(){
    int [][][]  transicao = {
      // 0       1
      /*q0*/{{0,1},{0,2}},
      /*q1*/{{3},  {}},
      /*q2*/{{},   {3}},
      /*q3*/{{3},  {3}}
    };

    //estado 0 transição espontanea
    //estado 1 transição espontanea
    //estado 2 transição espontanea
    //estado 3 transição espontanea

    int [][] transicaoVazia = {{}, {}, {}, {}};
    String alfabeto = "01";
    int aceitacao[] = {3};
    int estadoInicial = 0;
//  NaoDeterministicoE naoDeterministicoE = new NaoDeterministicoE(aceitacao,estadoInicial,  transicao, transicaoVazia, alfabeto);
  }

}
