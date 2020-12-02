package expressoesregulares;

import afnd.domain.NaoDeterministicoE;

import java.io.IOException;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Main {
  public static void main(String[] args) throws IOException {
    ER expressoesRegulares = new ER();
    ArrayList<String> out = new ArrayList<String>();
    /*
    String input = "(000|1*).(000|1)*";
    // String input = "((1|0).00)";
    out.addAll(Input.testCalc(input));
    System.out.println("Input = " + input);
    System.out.println("\u03A3 = " + out);
    */

    out = expressoesRegulares.GerarPalavras(20, "(10)(10|ε)(11|ε)(100|ε)*(1000)");
    // FUNÇAO QUE ESCREVE NO ARQUIVO TEXTO
    FileWriter arq = new FileWriter("linguagem.txt", false);
    PrintWriter gravarArq = new PrintWriter(arq);
    gravarArq.printf("\u03A3 = {");
    for (int i = 0; i < out.size(); i++) {
      gravarArq.printf(out.get(i) + ",\n");
    }
    arq.close();

    System.out.println(out);

  }


  public static void transformRegexToAFND (String entrada) {


    createAFND();
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
    //  String  entrada = "00000000";
    int aceitacao[] = {3};
    int estadoInicial = 0;
    NaoDeterministicoE naoDeterministicoE = new NaoDeterministicoE(aceitacao,estadoInicial,  transicao, transicaoVazia, alfabeto);

  }

}
