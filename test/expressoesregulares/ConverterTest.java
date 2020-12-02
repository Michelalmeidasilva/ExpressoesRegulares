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

    String entrada = "((ab)(100|ε))";

    Converter converter = new Converter (entrada);
    converter.transformRegexToAFND();
  }




}
