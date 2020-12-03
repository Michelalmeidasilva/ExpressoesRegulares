package expressoesregulares;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;


class Parenteses {
  int posIni;
  int posFim;
  int nivel;
  String conteudo;
  public Parenteses(){ }
  public Parenteses (int posIni, int posFim, String conteudo, int nivel){
    this.posIni = posIni;
    this.posFim = posFim;
    this.conteudo = conteudo;
    this.nivel = nivel;
  }
}

class NFA{
  String K;     //estados
  String E;     //alfabeto
  List<String> delta = new ArrayList(); //transicoes
  String s;     //estado ini
  String F;     //estado final

}


public class Converter {
  int estado = 0;


  List<Parenteses> mapa = new ArrayList<>();


  public NFA executarUnitario(String expressao){
    NFA nfa = new NFA();
    nfa.K = estado+"," +(estado +1);
    nfa.E = expressao.toString();
    nfa.delta.add(estado +","+expressao +"," + (estado+1) + "");
    nfa.s = estado+"";
    nfa.F = (estado + 1) + "";
    estado= estado +2;
    return nfa;
  }


  public String Uniao(String conjuntoA, String conjuntoB){
    HashSet<String> uniao = new HashSet<String>();
    uniao.add(conjuntoA);
    uniao.add(conjuntoB);
    return uniao.toString().replace("[", "").replace("]", "");
  }

  public NFA executarConcatenacao(NFA automato1, NFA automato2){
    NFA novoAutomato = new NFA();

    String estados = Uniao(automato1.K, automato2.K);
    String alfabeto= Uniao(automato1.E, automato2.E);
    String novaTransicao = automato1.F + "," +"ε"+ "," + automato2.s;

    novoAutomato.K= estados;
    novoAutomato.E = alfabeto;
    novoAutomato.s = automato1.s;
    novoAutomato.F = automato2.F;

    novoAutomato.delta.add(automato1.delta.toString().replace("[", "").replace("]", ""));
    novoAutomato.delta.add(automato2.delta.toString().replace("[", "").replace("]", ""));
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

  public NFA executarEscolha(NFA automato1, NFA automato2){
    NFA novoAutomato = new NFA();
    String novoEstado = (estado) + "";
    estado++;
    novoAutomato.K = Uniao(automato1.K, automato2.K + ',' + novoEstado);
    novoAutomato.E = Uniao(automato1.E, automato2.E + ",ε" ) ;
    novoAutomato.s = novoEstado;
    novoAutomato.F = Uniao(automato1.F, automato2.F);
    novoAutomato.delta.add(automato1.delta.toString().replace("[", "").replace("]", ""));
    novoAutomato.delta.add(automato2.delta.toString().replace("[", "").replace("]", ""));
    String novaTransicao1 =novoEstado+"," + 'ε' +"," + automato1.s;
    String novaTransicao2 =novoEstado+","+'ε'+"," + automato2.s;
    novoAutomato.delta.add(novaTransicao1);
    novoAutomato.delta.add(novaTransicao2);


    return novoAutomato;
  }

  /**
   * M* = ({K¹U{s,qf}},{Σ¹U{e}}, {Δ1U (s,e,s1), (s1,e,qf),(qf,e,s1) U (F1Xqf)},(s,e,s²)}},s,{qf})
   * K = K¹ + s + qf
   * E = E¹ + e
   * Δ = Δ¹ U (s,e,s¹), (s¹,e,qf),  (qf,e,s¹) U (F¹Xqf)
   * s = s      (novo estado inicial)
   * F = {qf}   (novo estado final)
   * @return
   */
  public NFA executarFechoDeKleene(NFA automato){
    NFA novoAutomato = new NFA();
    String novoEstadoFinal = estado + "";
    estado++;
    String novoEstadoInicial = estado + "";
    estado++;
    String novaTransicao1 = novoEstadoInicial+ "," +"ε," + automato.s;
    String novaTransicao2 = automato.s + "," + "ε," + novoEstadoFinal;
    String novaTransicao3 = novoEstadoFinal + "," + "ε," + automato.s;
//    List<String> novasTransicao4= new ArrayList<>();
    novoAutomato.delta.add(automato.delta.toString().replace("[", "").replace("]", ""));
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


  public void findFechoDeKleene(){
    throw new NotImplementedException();
  }

  public void findConcatenacao(){
    throw new NotImplementedException();
  }

  public void findEscolha(){
    throw new NotImplementedException();
  }

  public void transformRegexToAFND(String entrada){
      procuraParenteses(entrada);
  }

  /**
   * @param entrada
   * Mapeamento de parenteses
   * da entrada por nivel seguindo a logica
   * de precedencia de parenteses, quanto mais profundo
   * o parenteses maior o  nivel acoplado ao objeto da classe Parenteses
   */
  private void procuraParenteses(String entrada) {
    Map<Integer , Boolean> posicoes = new HashMap< Integer, Boolean>();
    Parenteses parenteses = new Parenteses();
    int nivel = 0;

    mapa.add(new Parenteses (0 ,( entrada.length() -1) , entrada, 0));
    for (int i =0; i < entrada.length(); i ++){
      posicoes.put( i, false);
    }

    for (int i = 0; i < entrada.length(); i++) {
      char atual= entrada.charAt(i);

      if(atual == '(') nivel++;

      if(atual == '(' && posicoes.get(i) == false) parenteses.posIni = i;

      if(atual == ')' && posicoes.get(i) == false){
        parenteses.posFim = i;
        parenteses.nivel = nivel;
        parenteses.conteudo = entrada.substring(parenteses.posIni + 1, parenteses.posFim);
        posicoes.put(parenteses.posIni, true);
        posicoes.put(parenteses.posFim, true);
        mapa.add(parenteses);
        parenteses = new Parenteses();
        i = -1;
        nivel = 0;
      }

      if(atual == ')' && nivel > 0)  nivel --;
    }
  }


  public boolean checaParenteses(String entrada) throws Exception {
    int qtdParenteses = 0;
    for (int i = 0; i < entrada.length(); i++) {
      if (entrada.charAt(i) == '(')
        qtdParenteses++;
      if(entrada.charAt(i) == (')'))
        qtdParenteses--;
    }

    if(qtdParenteses != 0)
      throw new Exception("Entrada mal formatada e ou invalida");
    else
      return true;
  }

  public void createAFND(){

  }
}
