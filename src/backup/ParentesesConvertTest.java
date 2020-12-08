//package backup;
//
//import backup.Parenteses;
//import expressoesregulares.conversao.ThompsonAlgorithm;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.Parameterized;
//
//
//import java.util.Arrays;
//import java.util.Collection;
//
//import static org.junit.Assert.assertEquals;
//
//@RunWith(Parameterized.class)
//public class ParentesesConvertTest {
//  ThompsonAlgorithm convert ;
//  String entrada;
//  boolean expectedResult;
//
//  @Before
//  public void initialize() {
//
//    convert = new ThompsonAlgorithm();
//  }
//
//  public ParentesesConvertTest(String inputString, Boolean expectedResult) {
//    this.entrada = inputString;
//    this.expectedResult = expectedResult;
//  }
//
//  // a|b  checaParenteses = valida
//  // (a|b) | (ab)      = valida
//  // (a|b             = false é invalida
//  @Parameterized.Parameters
//  public static Collection EntradaComParenteses() {
//    return Arrays.asList(new Object[][] {
//      { "a|b", true },
//      { "(a|b)|(ab)", true },
//      { "(ab)|((ab)(100|ε)(a|b))", true },
//      { "(((ab)(100|ε)(a|b))) |(ab)", true },
//      { "(((ab)))| ((ab)(100|ε)(a|b))", true },
//      { "((ab)(100|ε)(a|b))|(ab)", true},
//      { "((ab)(100|ε)(a|b))|(ab", false}
//    });
//  }
//
//  @Test
//  public void checaValidadeDeParenteses() throws Exception {
//    String entrada = "(a|b (a|b))";
//    ThompsonAlgorithm convert = new ThompsonAlgorithm();
//    assertEquals("Deve retornar uma entrada invalida", true, Parenteses.checaParenteses(entrada)) ;
//  }
//
//}
