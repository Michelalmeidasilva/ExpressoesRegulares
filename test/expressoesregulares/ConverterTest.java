package expressoesregulares;

import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ConverterTest {

  @Test
  public void whatever () throws IOException {
    ER expressoesRegulares = new ER();
    ArrayList<String> out = new ArrayList<String>();
    out = expressoesRegulares.GerarPalavras(20, "(10)(10|ε)(11|ε)(100|ε)*(1000)");
    for (int i = 0; i < out.size(); i++) {
      System.out.println(out.get(i) );
    }
    String entrada = "(10)(10|ε)(11|ε)(100|ε)*(1000)";
    Converter converter = new Converter (entrada);
  }




}
