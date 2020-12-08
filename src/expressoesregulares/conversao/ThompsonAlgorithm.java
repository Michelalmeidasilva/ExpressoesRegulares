package expressoesregulares.conversao;
import afnd.domain.NaoDeterministicoE;
import afnd.exceptions.IsNotBelongOnLanguage;
import expressoesregulares.Regex;
import expressoesregulares.tree.BinaryTree;
import expressoesregulares.tree.Node;

import java.util.*;

public class Converter   {

  static int estado = 0;
//  List<Parenteses> mapa = new ArrayList<>();

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

  public Node getArvore(Regex expressaoRegular){
    Node root;
    BinaryTree bt = new BinaryTree();
    root = bt.generateTree(expressaoRegular.getExpression());
    bt.searchInorder(root);
    bt.executeInOrder(root);
    return root;
  }

  public NaoDeterministicoE transformRegexToAFND(Regex expressaoRegular){
    Node root = getArvore(expressaoRegular);
    int aceitacao[]  = root.automato.getF();
    int estadoInicial = root.automato.getS();
    int estados[]  = root.automato.getK();
    int maxValueInMap = maxNumeroDeTransicoes(root, estados);

    String alfabeto = "";
    for (int i = 0; i < root.automato.E.size(); i++) {
      if (!(root.automato.E.get(i) == "ε")) {
        alfabeto = alfabeto + root.automato.E.get(i);
      }
    }

    HashMap <String, Integer> mapAlfabeto = mapearElementosDoAlfabeto(root);
    int[][][] transicoes = new int[estados.length][alfabeto.length()][maxValueInMap];
    int[][] transicoesVazias = new int[estados.length][maxValueInMap];

    HashMap<Integer, ArrayList<Integer>> transicoesVaziasMap = new HashMap<>();
    List<PosicaoLinha> transicoesMap = new ArrayList<PosicaoLinha>() ;

    for (int i = 0; i < estados.length; i++) {
      transicoesMap.add(estados[i],new PosicaoLinha());
      for (int j = 0; j <alfabeto.length() ; j++) {
        String elemento = alfabeto.charAt(j) + "";
        if(elemento != "ε"){
          transicoesMap.get(estados[i]).alfabetoColunas.add(new AlfabetoColuna());
        }
      }
      transicoesVaziasMap.put(estados[i], new ArrayList<>());
    }

    for (int i = 0; i < root.automato.delta.size(); i++) {
      String transicao = root.automato.delta.get(i);
      int primeiraPosicao = Integer.parseInt(transicao.split(",")[0]);
      int segundaParte= mapAlfabeto.get(transicao.split(",")[1]);
      int terceiraParte = Integer.parseInt(transicao.split(",")[2]);
      if(transicao.contains("ε")){
        transicoesVaziasMap.get(primeiraPosicao).add(terceiraParte);
      } else {
        transicoesMap.get(primeiraPosicao).alfabetoColunas.get(segundaParte).transicao = terceiraParte;
      }
    }

    for (int i = 0; i < transicoesVazias.length; i++) {
      int vetor[] = new int[transicoesVaziasMap.get(i).size()];
      for (int j = 0; j < vetor.length; j++) {
        if(transicoesVaziasMap.get(i).get(j) != 0 ){
          vetor[j] = transicoesVaziasMap.get(i).get(j);
        }
      }
      transicoesVazias[i] = vetor;
    }

    int test[][][] = new int[11][2][3];
    for (int i = 0; i < transicoes.length; i++) {
      int[][] vetor  = new int[2][3];
      for (int j = 0; j < transicoes[i].length; j++) {
        int vetorTest[] = new int[3];
        for (int k = 0; k < transicoes[i][j].length; k++) {
          if (transicoesMap.get(i).alfabetoColunas.get(j).transicao != null) {
            vetorTest[k] = transicoesMap.get(i).alfabetoColunas.get(j).transicao;
          }
        }
        vetor[i] = vetorTest;
      }
      test[i] = vetor;
    }

    transicoesVazias[0]= new int[]{};
    transicoesVazias[2]= new int[]{};
    transicoesVazias[4]= new int[]{};

    NaoDeterministicoE afnd = new NaoDeterministicoE(aceitacao, estadoInicial, transicoes, transicoesVazias, alfabeto);
    try {
      afnd.executar("aaa");
    } catch (IsNotBelongOnLanguage isNotBelongOnLanguage) {
      isNotBelongOnLanguage.printStackTrace();
    }
    return null;
  }

  private HashMap<String, Integer> mapearElementosDoAlfabeto(Node root) {
    HashMap<String, Integer> mapAlfabeto = new HashMap<>();
    for (int i = 0; i < root.automato.E.size(); i++) {
      mapAlfabeto.put(root.automato.E.get(i), i);
    }
    return mapAlfabeto;

  }

  private int maxNumeroDeTransicoes(Node root, int[] estados) {
    HashMap <Integer, Integer> countMaxTransicoes= new HashMap<Integer, Integer>();
    for (int i = 0; i <root.automato.delta.size() ; i++) {
      String [] transicao = root.automato.delta.get(i).split(",");
      for (int j = 0; j < estados.length; j++) {
        if(Integer.parseInt(transicao[0]) ==estados[j]){
          if (countMaxTransicoes.containsKey(estados[j])){
            countMaxTransicoes.put( estados[j] , countMaxTransicoes.get(estados[j]) + 1);
          }else {
            countMaxTransicoes.put( estados[j] , 1);
          }
        }
      }
    }

    return  (Collections.max(countMaxTransicoes.values()));
  }

  public void setNfa(NFA nfa) {
  }


}


class PosicaoLinha {
  List<AlfabetoColuna> alfabetoColunas = new ArrayList<AlfabetoColuna>();
  public PosicaoLinha(){
  }
}
//
class AlfabetoColuna{
  Integer transicao;
  public AlfabetoColuna( Integer transicao) {
    this.transicao = transicao;
  }

  public AlfabetoColuna(){}
  }


