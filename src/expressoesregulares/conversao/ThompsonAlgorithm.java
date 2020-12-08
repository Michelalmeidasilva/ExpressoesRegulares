package expressoesregulares.conversao;
import afnd.domain.NaoDeterministicoE;
import expressoesregulares.Regex;
import expressoesregulares.arvoresintatica.BinaryTree;
import expressoesregulares.arvoresintatica.Node;

import java.util.*;

public class ThompsonAlgorithm   {

  static int estado = 0;

  /**
   * Sprint 03
   * @author Michel Silva, Kelvin Souza
   * Através da definição do algoritmo de thompson é instanciado um novo automato
   * M1= (K, E, Δ, s , F )
   * K¹ = {q0, q1}
   * Σ¹ = {a}
   * Δ¹ = { (q0,a,q1) }
   * s¹= q0
   * F¹ = {q1}
   * ER(A) => M1 =  ( {q0, q1}, {a}, {(q0, a, q1)}, q0, {q1} )
   * @return
   */
  public NfaMap automatoUnitario(Regex regex){
    NfaMap nfaMap = new NfaMap();
    nfaMap.K.add(estado + "");
    nfaMap.K.add(estado+1 + "");
    nfaMap.E.add(regex.getExpression());
    nfaMap.delta.add(estado +","+regex.getExpression() +"," + (estado+1) + "");
    nfaMap.s = estado+"";
    nfaMap.F.add(estado+1+"");
    estado= estado+2;
    return nfaMap;
  }

  /**
   * Sprint 03
   * @author Michel Silva, Kelvin Souza
   * Através da definição do algoritmo de thompson é instanciado um novo automato utilizando
   * dois outros automatos instanciados possibilitando o reconhecimento de uma escolha
   * @param automato1
   * @param automato2
   * @return
   */
  public NfaMap automatoConcatenacao(NfaMap automato1, NfaMap automato2){
    NfaMap novoAutomato = new NfaMap();
    automato1.E.add("ε");
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
   * Sprint 03
   * @author Michel Silva, Kelvin Souza
   * Através da definição do algoritmo de thompson é instanciado um novo automato utilizando
   * dois outros automatos instanciados
   * K = União de K1 e K2 + s
   * E = União entre E1 e E2 + e
   * Δ = União entre Δ¹e Δ² + transições vazias entre s e s¹Us²
   * s = s
   * F = União entre F¹ e F²
   * @param automato1
   * @param automato2
   * @return
   */
  public NfaMap automatoEscolha(NfaMap automato1, NfaMap automato2){
    NfaMap novoAutomato = new NfaMap();
    String novoEstado = estado + "";
    estado++;
    novoAutomato.K = Uniao(automato1.K, automato2.K);
    novoAutomato.E.add("ε");
    novoAutomato.E = Uniao(automato1.E, automato2.E) ;
    novoAutomato.s = novoEstado;
    novoAutomato.F = Uniao(automato1.F, automato2.F);

    novoAutomato.delta.addAll(automato1.delta);
    novoAutomato.delta.addAll(automato2.delta);
    novoAutomato.K.add(novoEstado);

    String novaTransicao1 =novoEstado+"," + 'ε' +"," + automato1.s;
    String novaTransicao2 =novoEstado+","+'ε'+"," + automato2.s;

    novoAutomato.delta.add(novaTransicao1);
    novoAutomato.delta.add(novaTransicao2);
    return novoAutomato;
  }

  /**
   * Sprint 03
   * @author Michel Silva, Kelvin Souza
   * Através da definição é transformado para um automato fecho de kleene
   * ({K¹U{s,qf}},{Σ¹U{e}}, {Δ1U (s,e,s1), (s1,e,qf),(qf,e,s1) U (F1Xqf)},(s,e,s²)}},s,{qf})
   * K = K¹ + s + qf
   * E = E¹ + e
   * Δ = Δ¹ U (s,e,s¹), (s¹,e,qf),  (qf,e,s¹) U (F¹Xqf)
   * s = s      (novo estado inicial)
   * F = {qf}   (novo estado final)
   * @return
   */
  public NfaMap automatoFechoDeKleene(NfaMap automato){
    NfaMap novoAutomato = new NfaMap();
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
   * Sprint 03
   * @author Michel Silva, Kelvin Souza
   * União entre dois conjuntos quaisquer
   * @param conjuntoA
   * @param conjuntoB
   * @return
   */
  private List<String> Uniao(List<String> conjuntoA, List<String> conjuntoB){
    HashSet<String> uniao = new HashSet<String>();

    uniao.addAll(conjuntoA);
    uniao.addAll(conjuntoB);

    List<String> novoConjuntoUniao = new ArrayList<String>();
    for ( String elemento: uniao) {
      novoConjuntoUniao.add(elemento);
    }

    return novoConjuntoUniao;
  }

  /**
   * Sprint 03
   * obtenção da arvore sintatica de uma expressão regular
   * @author Michel Silva, Kelvin Souza
   * @param expressaoRegular
   * @return
   */
  private Node getArvore(Regex expressaoRegular){
    Node root;
    BinaryTree bt = new BinaryTree();
    root = bt.generateTree(expressaoRegular.getExpression());
    bt.searchInorder(root);
    bt.executeInOrder(root);
    return root;
  }

  /**
   * Sprint 03
   * Transformação de uma Expressão regular, da classe Regex para um automato finito não deterministico.
   * @author Michel Silva, Kelvin Souza
   * @param expressaoRegular
   * @return
   */
  public NaoDeterministicoE transformRegexToAFND(Regex expressaoRegular){
    Node root = getArvore(expressaoRegular);
    int aceitacao[]  = root.automato.getF();
    int estadoInicial = root.automato.getS();
    int estados[]  = root.automato.getK();
    Arrays.sort(estados);
    Arrays.sort(aceitacao);

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

    for (int i = 0; i < transicoes.length; i++) {
      for (int j = 0; j < transicoes[i].length; j++) {
        for (int k = 0; k < transicoes[i][j].length; k++) {
          transicoes[i][j][k]= -99;
        }
      }
    }
    for (int j = 0; j < transicoesVazias.length; j++) {
      for (int k = 0; k < transicoesVazias[j].length; k++) {
        transicoesVazias[j][k]= -99;
      }
    }
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
        transicoesMap.get(primeiraPosicao).alfabetoColunas.get(segundaParte).transicao.add(terceiraParte);
      }
    }

    for (int i = 0; i < transicoesVazias.length; i++) {
      for (int j = 0; j < transicoesVaziasMap.get(i).size(); j++) {
        if ( transicoesVaziasMap.get(i).get(j) != null){
          transicoesVazias[i][j]= transicoesVaziasMap.get(i).get(j);
        }
      }
    }

    for (int i = 0; i < transicoes.length; i++) {
      for (int j = 0; j < transicoes[i].length ; j++) {
        for (int k = 0; k < transicoesMap.get(i).alfabetoColunas.get(j).transicao.size(); k++) {
          transicoes[i][j][k] = transicoesMap.get(i).alfabetoColunas.get(j).transicao.get(k);
        }
      }
    }
    return  new NaoDeterministicoE(aceitacao, estadoInicial, transicoes, transicoesVazias, alfabeto);
  }

  /**
   * @param root
   * @return
   */
  private HashMap<String, Integer> mapearElementosDoAlfabeto(Node root) {
    HashMap<String, Integer> mapAlfabeto = new HashMap<>();
    HashSet<String> exclusao = new HashSet<>();
    for (String elemento: root.automato.E) {
      if(elemento != "ε"){
        exclusao.add(elemento);
      }
    }
    int i=0;
    for ( String elemento: exclusao) {
      mapAlfabeto.put(elemento, i);
      i++;
    }
    mapAlfabeto.put("ε", i);
    return mapAlfabeto;
  }

  /**
   * @param root
   * @param estados
   * @return
   */
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

}

class PosicaoLinha {
  List<AlfabetoColuna> alfabetoColunas = new ArrayList<AlfabetoColuna>();
  public PosicaoLinha(){
  }
}

class AlfabetoColuna{
 List<Integer> transicao= new ArrayList<Integer>();
 public AlfabetoColuna(){ }
}


