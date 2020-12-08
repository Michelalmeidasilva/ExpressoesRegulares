package expressoesregulares;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class GerarPalavras {

  //Alcance de aleatoriedade dos fechos de kleener
  public int randomRange = 6;
  /**
   * Sprint 02
   *
   * @param numero
   * @param regex
   * @return
   * @author Thiago Mendes
   */
  public ArrayList gerarPalavras(int numero, String regex) {
    ArrayList<String> retorno = new ArrayList<>();
    String expressao = regex;
    //Remover todos os espaços brancos
    expressao = expressao.replaceAll(" ", "");
    for (int x = 0; x < numero; x++) {
      //Usar uma expressão temporária para trabalhar
      String expressaoTemporaria = expressao;
      //Resolvemos todas as partes da expressão
      expressaoTemporaria = KleenerComParenteses(expressaoTemporaria);
      expressaoTemporaria = KleenerSemParenteses(expressaoTemporaria);
      expressaoTemporaria = Alternancia(expressaoTemporaria);
      //Removemos todos os vazios
      expressaoTemporaria = expressaoTemporaria.replaceAll("ε", "");
      //Removemos os '.' dado que não precisamos tratar concatenações
      expressaoTemporaria = expressaoTemporaria.replaceAll("\\.", "");
      //Removemos quaisquer parenteses que ainda estejam na expressão
      expressaoTemporaria = expressaoTemporaria.replaceAll("\\(", "");
      expressaoTemporaria = expressaoTemporaria.replaceAll("\\)", "");
      //Testamos se a expressão gerada já existe na lista (está repetida)
      if (!retorno.contains(expressaoTemporaria)) {
        retorno.add(expressaoTemporaria);
      } else {
        x--;
      }
    }
    //Ordenamos a lista em ordem de tamanho das palavras
    Collections.sort(retorno, Comparator.comparingInt(String::length));
    return retorno;
  }

  /**
   * Sprint 02
   *
   * @param expressao
   * @return
   * @author Thiago Mendes
   */
  private String KleenerSemParenteses(String expressao) {
    //Variável temporária para trabalhar com a expressão
    String exp = expressao, tempStr;
    //Variável usada para funções avançadas de String
    StringBuilder expressaoTemporaria = new StringBuilder();
    expressaoTemporaria.append(expressao);
    Boolean saida = true;
    int tempInt;
    Random random = new Random();
    //Faz uma modificação em Kleeners com parenteses para não ser confundido
    exp = exp.replace(")*", "),");
    //Se mantém no loop até resolver todos os fechos de kleener
    while (saida) {
      //Busca o primeiro fecho de kleener não resolvido
      if ((tempInt = exp.indexOf("*")) != -1) {
        //Salva a letra a ser afetada
        tempStr = exp.substring(tempInt - 1, tempInt);
        //Remove a letra original e o fecho
        expressaoTemporaria.delete(tempInt - 1, tempInt + 1);
        //Reinsere a letra um numero aleatorio de vezes
        int randomValue = random.nextInt(randomRange);
        for (int x = 0; x < randomValue; x++) {
          expressaoTemporaria.insert(tempInt - 1, tempStr);
        }
        exp = expressaoTemporaria.toString();
        //Retorna fechos com parenteses a forma normal
        exp = exp.replace(")*", "),");
      } else {
        saida = false;
      }
    }
    return expressaoTemporaria.toString();
  }

  /**
   * Sprint 02
   *
   * @param expressao
   * @return
   * @author Thiago Mendes
   */
  private String KleenerComParenteses(String expressao) {
    String tempStr;
    //Variável usada para montar e funções avançadas de String
    StringBuilder expressaoTemporaria = new StringBuilder();
    expressaoTemporaria.append(expressao);
    Boolean saida = true;
    Random random = new Random();
    //Variáveis usadas para marcar o começo e o fim dos parenteses
    int fim, inicio;
    //Se mantém no loop até resolver todos os fechos de kleener
    while (saida) {
      //Busca o primeiro fecho de kleener com parenteses não resolvido
      if ((fim = expressaoTemporaria.indexOf(")*")) != -1) {
        //Busca a posição de inicio e fim e insere nas variáveis
        inicio = fim;
        while (!String.valueOf(expressaoTemporaria.toString().charAt(inicio)).equals("(")) {
          inicio--;
        }
        //Salva o intervalo a ser afetado
        tempStr = expressaoTemporaria.substring(inicio, fim + 1);
        //Deleta o intervalo original
        expressaoTemporaria.delete(inicio, fim + 2);
        //Insere o intervalo salvo um numero aleatório de vezes
        int randomValue = random.nextInt(randomRange);
        for (int x = 0; x < randomValue; x++) {
          expressaoTemporaria.insert(inicio, tempStr);
        }
      } else {
        saida = false;
      }
    }
    return expressaoTemporaria.toString();
  }

  /**
   * Sprint 02
   *
   * @param expressao
   * @return
   * @author Thiago Mendes
   */
  private String Alternancia(String expressao) {
    String tempStr = "";
    //Lista que salva todas as opções de escolha
    ArrayList<String> opcoes;
    //Variável usada para montar e funções avançadas de String
    StringBuilder expressaoTemporaria = new StringBuilder();
    expressaoTemporaria.append(expressao);
    Boolean saida = true;
    Random random = new Random();
    //Variáveis para marcar o incio, fim e sinais internos da alternancia
    int fim, inicio, sinal;
    //Se mantém no loop até resolver todas as alternancias
    while (saida) {
      //Limpa a lista de opcoes
      opcoes = new ArrayList<>();
      //Busca a primeira alternancia não resolvida
      if ((sinal = expressaoTemporaria.indexOf("|")) != -1) {
        inicio = fim = sinal;
        //Busca o fim da expressao de alternancia
        while (!String.valueOf(expressaoTemporaria.toString().charAt(fim)).equals(")")) {
          fim++;
          if (fim == expressaoTemporaria.length()) {
            expressaoTemporaria.insert(expressaoTemporaria.length(), ")");
            break;
          }
          //Adiciona opções adicionais encontradas a lista e as remove da expressao
          if (String.valueOf(expressaoTemporaria.toString().charAt(fim)).equals("|")) {
            opcoes.add(expressaoTemporaria.substring(sinal + 1, fim));
            expressaoTemporaria.delete(sinal, fim);
            fim -= (fim - sinal);
          }
        }
        //Adiciona a penultima opção a lista
        opcoes.add(expressaoTemporaria.substring(sinal + 1, fim));
        //Busca o inicio da expressao de alternancia
        while (!String.valueOf(expressaoTemporaria.toString().charAt(inicio)).equals("(")) {
          inicio--;
          if (inicio == -1) {
            sinal++;
            expressaoTemporaria.insert(0, "(");
            inicio = 0;
            break;
          }
        }
        //Adiciona a última opção a lista
        opcoes.add(expressaoTemporaria.substring(inicio + 1, sinal));
        //Deleta tudo o que restou da operacao alternancia da expressao
        expressaoTemporaria.delete(inicio, expressaoTemporaria.indexOf(")", inicio) + 1);
        //Insere uma opção aleatória na expressao
        int randomValue = random.nextInt(opcoes.size());
        expressaoTemporaria.insert(inicio, opcoes.get(randomValue));
      } else {
        saida = false;
      }
    }
    return expressaoTemporaria.toString();
  }

  public void salvarPalavrasGeradas(ArrayList<String> out ){
    FileWriter arq = null;
    try {
      arq = new FileWriter("linguagem.txt", false);
    } catch (IOException e) {
      e.printStackTrace();
    }
    PrintWriter gravarArq = new PrintWriter(arq);
    gravarArq.printf("\u03A3 = {");

    for (int i = 0; i < out.size(); i++) {
      gravarArq.printf(out.get(i) + ",\n");
    }
    try {
      arq.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
