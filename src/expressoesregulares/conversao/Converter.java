package expressoesregulares.conversao;
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
    nfa.K = estado+"," +(estado +1);
    nfa.E = regex.getExpression().toString();
    nfa.delta.add(estado +","+regex.getExpression() +"," + (estado+1) + "");
    nfa.s = estado+"";
    nfa.F = (estado + 1) + "";
    estado= estado +2;
    return nfa;
  }

  /**
   * @param automato1
   * @param automato2
   * @return
   */
  public NFA automatoConcatenacao(NFA automato1, NFA automato2){
    NFA novoAutomato = new NFA();

    String estados = Uniao(automato1.K, automato2.K);
    String alfabeto= Uniao(automato1.E, automato2.E);
    String novaTransicao = automato1.F + "," +"ε"+ "," + automato2.s;

    novoAutomato.K= estados;
    novoAutomato.E = alfabeto;
    novoAutomato.s = automato1.s;
    novoAutomato.F = automato2.F;

    novoAutomato.delta.addAll(automato1.delta);
    novoAutomato.delta.addAll(automato2.delta);

    novoAutomato.delta.add(novaTransicao);

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
    String novoEstado = (estado) + "";
    estado++;
    novoAutomato.K = Uniao(automato1.K, automato2.K + ',' + novoEstado);
    novoAutomato.E = Uniao(automato1.E, automato2.E + "ε" ) ;
    novoAutomato.s = novoEstado;
    novoAutomato.F = Uniao(automato1.F, automato2.F);

    novoAutomato.delta.addAll(automato1.delta);
    novoAutomato.delta.addAll(automato2.delta);

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
    String[] estadosFinais = automato.F.split(",");

    for (int i = 0; i < estadosFinais.length ; i++) {
      novoAutomato.delta.add(estadosFinais[i] + "," + 'ε' + ',' + novoEstadoFinal);
    }

    novoAutomato.K = automato.K + "," + novoEstadoFinal + "," + novoEstadoInicial;
    novoAutomato.E= Uniao(automato.E , "ε");
    novoAutomato.F = novoEstadoFinal + "";
    novoAutomato.s = novoEstadoInicial;
    return novoAutomato;
  }

  /**
   * @param conjuntoA
   * @param conjuntoB
   * @return
   */
  public String Uniao(String conjuntoA, String conjuntoB){
    TreeSet<String> uniao = new TreeSet<String>();
    conjuntoA = conjuntoA.replaceAll(",", "");
    conjuntoB = conjuntoB.replaceAll(",", "");
    for (int i = 0; i < conjuntoA.length(); i++) {
      uniao.add(conjuntoA.charAt(i)+"");
    }

    for (int i = 0; i < conjuntoB.length(); i++) {
        uniao.add(conjuntoB.charAt(i)+"");
    }


    StringBuilder testBuilder;
    return uniao.toString();
  }

  /**
   * @return
   */
  public String findFechoDeKleene(){
//    throw new NotImplementedException();
    return "";
  }

  /**
   * @return
   */
  public String findConcatenacao(){
//    throw new NotImplementedException();
    return "";  }
  /**
   * @return
   */
  public String findEscolha(){
   throw new NotImplementedException();
  }

  /**
   * @return
   */
  public NFA transformRegexToAFND(Regex expressaoRegular){
      Parenteses.procuraParenteses(mapa,expressaoRegular.getExpression());
      return null;
  }

  public void setNfa(NFA nfa) {
  }
}

