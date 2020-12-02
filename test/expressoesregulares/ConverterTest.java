package expressoesregulares;

import org.junit.Test;

public class ConverterTest {

  @Test
  public void whatever () throws Exception {

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

}
