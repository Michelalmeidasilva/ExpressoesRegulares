package expressoesregulares;

import afnd.domain.NaoDeterministicoE;
import afnd.exceptions.IsNotBelongOnLanguage;
import afnd.utils.IOValidator;
import org.junit.Test;

public class afndTest {
  @Test
  public void shouldBeCorrectInputEnfa() throws IsNotBelongOnLanguage {
    String entrada = "02020101";
    String alfabeto = "012a";

    int [][][]  transicao =
        {{{1},   {-99},    {-99},  {-99}},
        {{-99},  {1},       {1}, {1}},
        {{-99},  {-99},    {-99}}} ;

    int [][] transicaoVazia = {
                                  {-99}, {-99,1,0},{-99},{0}};
    for (int i = 0; i < transicaoVazia.length; i++) {
      for (int j = 0; j < transicaoVazia[i].length; j++) {
        System.out.println(transicaoVazia[i][j]);
      }
    }

    int estadoInicial = 0;
    int aceitacao[] = {0};
    NaoDeterministicoE naoDeterministicoE = new NaoDeterministicoE(aceitacao,estadoInicial, transicao,  transicaoVazia, alfabeto);
    naoDeterministicoE.setDebug(true);
    naoDeterministicoE.executar(entrada);
  }
}
