package expressoesregulares;

import java.util.ArrayList;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Main {
  public static void main(String[] args) throws Exception {
    ER expressoesRegulares = new ER();
    ArrayList<String> out = new ArrayList<String>();
    /*
    String input = "(000|1*).(000|1)*";
    // String input = "((1|0).00)";

    out.addAll(Input.testCalc(input));
    System.out.println("Input = " + input);
    System.out.println("\u03A3 = " + out);
    */
    String entrada = "(10)(10|ε)(11|ε)(100|ε)*(1000)";
    out = expressoesRegulares.GerarPalavras(20, entrada);
    GerarAutomato.inicia(entrada);
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

}
