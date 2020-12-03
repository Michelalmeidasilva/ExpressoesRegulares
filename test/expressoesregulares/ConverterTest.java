package expressoesregulares;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ConverterTest {

  @Test
  public void executarTestMaluco ()  {
    Converter convert = new Converter();
    List<NFA> valoresUnitario = new ArrayList<NFA>();

    valoresUnitario.add(convert.executarUnitario("a"));
    valoresUnitario.add(convert.executarUnitario("b"));


//    convert.executarConcatenacao(valoresUnitario.get(0), valoresUnitario.get(1));
//    convert.executarEscolha(valoresUnitario.get(0), valoresUnitario.get(1));

    convert.executarFechoDeKleene(valoresUnitario.get(0));
  }

  @Test
  public void with0Parenteses(){
    String entrada = "a|b";
    Converter converter = new Converter ();
    converter.transformRegexToAFND(entrada);
  }

  @Test
  public void with1Parenteses(){
    String entrada = "(a|b) | (ab)";
    Converter converter = new Converter ();
    converter.transformRegexToAFND(entrada);
  }

  @Test
  public void with2Parenteses(){
    String entrada = "((ab)(100|ε)(a|b))|(ab)";
    Converter converter = new Converter ();
    converter.transformRegexToAFND(entrada);
  }

  @Test
  public void with2Expressions(){
    String entrada = "(ab)| ((ab)(100|ε)(a|b))";
    Converter converter = new Converter ();
    converter.transformRegexToAFND(entrada);
  }

  @Test
  public void with3Parenteses(){
    String entrada = "(((ab)(100|ε)(a|b))) |(ab)";
    Converter converter = new Converter ();
    converter.transformRegexToAFND(entrada);
  }

  @Test
  public void piorCenario(){
    String entrada = "(((ab)))| ((ab)(100|ε)(a|b))";
    Converter converter = new Converter ();
    converter.transformRegexToAFND(entrada);
  }


  @Test
  public void checaValidadeDeParenteses() throws Exception {
    String entrada = "(a|b (a|b))";
    Converter convert = new Converter();
    assertEquals("Deve retornar uma entrada invalida", true, convert.checaParenteses(entrada)) ;
  }
}
