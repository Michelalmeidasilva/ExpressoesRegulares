package expressoesregulares.conversao;

import afnd.domain.NaoDeterministicoE;
import afnd.exceptions.IsNotBelongOnLanguage;
import expressoesregulares.Regex;
import expressoesregulares.arvoresintatica.BinaryTree;
import expressoesregulares.arvoresintatica.Node;
import org.junit.Assert;
import org.junit.Test;

public class RegexConversaoTest {
  ThompsonAlgorithm random = new ThompsonAlgorithm();

  @Test
  public void fechoDeKleene(){
    NfaMap a = new NfaMap();
    NfaMap b = new NfaMap();
    NfaMap concat = new NfaMap();
    NfaMap fechoDeKleene = new NfaMap();
    a= random.automatoUnitario(new Regex('a'+""));
    b= random.automatoUnitario(new Regex('b'+""));
    concat = random.automatoConcatenacao(a, b);
    fechoDeKleene = random.automatoFechoDeKleene(concat);
  }

  @Test
  public void concatenacaoTest() {
    NfaMap a = new NfaMap();
    NfaMap b = new NfaMap();
    NfaMap concat = new NfaMap();
    a= random.automatoUnitario(new Regex('a'+""));
    b= random.automatoUnitario(new Regex('b'+""));
    concat = random.automatoConcatenacao(a, b);
  }

  @Test
  public void criarArvoreSintatica(){
    String regex = "((a|b)*a)*";
    Node root;
    BinaryTree bt = new BinaryTree();
    root = bt.generateTree(regex);
    bt.searchInorder(root);
    bt.executeInOrder(root);
  }

  @Test
  public void transformarRegex2(){
    Regex regex = new Regex( "((a|b)a)");
    ThompsonAlgorithm convert = new ThompsonAlgorithm();
    NaoDeterministicoE automato = convert.transformRegexToAFND(regex);
  }

  @Test
  public void transformarRegex3(){
    String input = "((((aaa|bb)|(c|e))|d)|x)";
    Regex regex = new Regex(input);
    NaoDeterministicoE automato = regex.transformRegexToAFND(regex);
  }

  @Test
  public void executarRegexAceitandoPalavra(){
    String input = "((((aaa|bb)|(c|e))|d)|x)";
    Regex regex = new Regex(input);
    try {
      Assert.assertEquals("Espera um true retornado baseada na expressã regular e na entrada da execução",true, regex.transformRegexToAFND(regex).executar("aaa"));
    } catch (IsNotBelongOnLanguage isNotBelongOnLanguage) {
      isNotBelongOnLanguage.printStackTrace();
    }
  }

  @Test
  public void executarRegexRejeitandoPalavra(){
    String input = "((((aaa|bb)|(c|e))|d)|x)";
    Regex regex = new Regex(input);
    try {
      Assert.assertEquals("Espera um false retornado baseada na expressão regular e na entrada da execução",false, regex.transformRegexToAFND(regex).executar("aaaa"));
    } catch (IsNotBelongOnLanguage isNotBelongOnLanguage) {
      isNotBelongOnLanguage.printStackTrace();
    }
  }
}
