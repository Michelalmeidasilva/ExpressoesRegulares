package expressoesregulares.conversao;

import expressoesregulares.Regex;
import org.junit.Test;

public class RegexConversaoTest {
  Converter random = new Converter();

  @Test
  public void fechoDeKleene(){
    NFA a = new NFA();
    NFA b = new NFA();
    NFA concat = new NFA();
    NFA fechoDeKleene = new NFA();
    a= random.automatoUnitario(new Regex('a'+""));
    b= random.automatoUnitario(new Regex('b'+""));
    concat = random.automatoConcatenacao(a, b);
    fechoDeKleene = random.automatoFechoDeKleene(concat);
  }

  @Test
  public void concatenacaoTest() {
    NFA a = new NFA();
    NFA b = new NFA();
    NFA concat = new NFA();
    a= random.automatoUnitario(new Regex('a'+""));
    b= random.automatoUnitario(new Regex('b'+""));
    concat = random.automatoConcatenacao(a, b);
  }

  @Test
  public void escolhaTest() {
    NFA a = new NFA();
    NFA b = new NFA();
    NFA escolha = new NFA();
    a= random.automatoUnitario(new Regex('a'+""));
    b= random.automatoUnitario(new Regex('b'+""));
    escolha = random.automatoEscolha(a, b);
  }

}
