package expressoesregulares.conversao;
import com.sun.org.apache.bcel.internal.generic.InstructionList;
import expressoesregulares.Regex;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

public class Converter {

  static int estado = 0;
  List<Parenteses> mapa = new ArrayList<>();

  /**
   * @return
   */
  public NFA automatoUnitario(Regex regex){
    NFA nfa = new NFA();
    nfa.K.add(estado + "");
    nfa.K.add(estado+1 + "");
    nfa.E.add(regex.getExpression());
    nfa.delta.add(estado +","+regex.getExpression() +"," + (estado+1) + "");
    nfa.s = estado+"";
    nfa.F.add(estado+1+"");
    estado= estado+2;
    return nfa;
  }

  /**
   * @param automato1
   * @param automato2
   * @return
   */
  public NFA automatoConcatenacao(NFA automato1, NFA automato2){
    NFA novoAutomato = new NFA();

    List<String> estados = Uniao(automato1.K, automato2.K);
    List<String> alfabeto= Uniao(automato1.E, automato2.E);

    for (String estado: automato1.F) {
      String novaTransicao = estado + "," +"ε"+ "," + automato2.s;
      novoAutomato.delta.add(novaTransicao);
    }

    novoAutomato.K= estados;
    novoAutomato.E = alfabeto;
    novoAutomato.s = automato1.s;
    novoAutomato.F = automato2.F;

    novoAutomato.delta.addAll(automato1.delta);
    novoAutomato.delta.addAll(automato2.delta);


    return novoAutomato;
  }

  /**
   * K = União de K1 e K2 + s
   * E = União entre E1 e E2 + e
   * Δ = União entre Δ¹e Δ² + transições vazias entre s e s¹Us²
   * s = s
   * F = União entre F¹ e F²
   * @param automato1
   * @param automato2
   * @return
   */
  public NFA automatoEscolha(NFA automato1, NFA automato2){
    NFA novoAutomato = new NFA();
    String novoEstado = estado + "";
    estado++;
    novoAutomato.K = Uniao(automato1.K, automato2.K);
    novoAutomato.E = Uniao(automato1.E, automato2.E) ;
    novoAutomato.s = novoEstado;
    novoAutomato.F = Uniao(automato1.F, automato2.F);

    novoAutomato.delta.addAll(automato1.delta);
    novoAutomato.delta.addAll(automato2.delta);
    novoAutomato.K.add(novoEstado);
    novoAutomato.E.add("ε");

    String novaTransicao1 =novoEstado+"," + 'ε' +"," + automato1.s;
    String novaTransicao2 =novoEstado+","+'ε'+"," + automato2.s;

    novoAutomato.delta.add(novaTransicao1);
    novoAutomato.delta.add(novaTransicao2);
    return novoAutomato;
  }

  /**
   * M =
   ({K¹U{s,qf}},{Σ¹U{e}}, {Δ1U (s,e,s1), (s1,e,qf),(qf,e,s1) U (F1Xqf)},(s,e,s²)}},s,{qf})
   * K = K¹ + s + qf
   * E = E¹ + e
   * Δ = Δ¹ U (s,e,s¹), (s¹,e,qf),  (qf,e,s¹) U (F¹Xqf)
   * s = s      (novo estado inicial)
   * F = {qf}   (novo estado final)
   * @return
   */
  public NFA automatoFechoDeKleene(NFA automato){
    NFA novoAutomato = new NFA();
    String novoEstadoFinal = estado + "";
    estado++;
    String novoEstadoInicial = estado + "";
    estado++;
    String novaTransicao1 = novoEstadoInicial+ "," +"ε," + automato.s;
    String novaTransicao2 = automato.s + "," + "ε," + novoEstadoFinal;
    String novaTransicao3 = novoEstadoFinal + "," + "ε," + automato.s;

    novoAutomato.delta.addAll(automato.delta);

    novoAutomato.delta.add(novaTransicao1);
    novoAutomato.delta.add(novaTransicao2);
    novoAutomato.delta.add(novaTransicao3);

    for (int i = 0; i < automato.F.size() ; i++) {
      String novaTransicao = automato.F.get(i) + "," + 'ε' + ',' + novoEstadoFinal;
      novoAutomato.delta.add(novaTransicao);
    }

    novoAutomato.K.addAll(automato.K);
    novoAutomato.K.add(novoEstadoFinal);
    novoAutomato.K.add(novoEstadoInicial);

    novoAutomato.E= Uniao(automato.E , new ArrayList<String>(Collections.singleton("ε")));
    novoAutomato.F.add(novoEstadoFinal);
    novoAutomato.s = novoEstadoInicial;
    return novoAutomato;
  }

  /**
   * @param conjuntoA
   * @param conjuntoB
   * @return
   */
  public List<String> Uniao(List<String> conjuntoA, List<String> conjuntoB){
    HashSet<String> uniao = new HashSet<String>();

    uniao.addAll(conjuntoA);
    uniao.addAll(conjuntoB);

    List<String> novoConjuntoUniao = new ArrayList<String>();
    for ( String elemento: uniao) {
      novoConjuntoUniao.add(elemento);
    }

    return novoConjuntoUniao;
  }

  public NFA transformRegexToAFND(Regex expressaoRegular){
    return null;
  }

  public void setNfa(NFA nfa) {
  }
}

