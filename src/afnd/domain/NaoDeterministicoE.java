package afnd.domain;

import afnd.exceptions.IsNotBelongOnLanguage;
import afnd.utils.IOValidator;
import java.util.Set;
import java.util.TreeSet;

public class NaoDeterministicoE extends Automato {
    static IOValidator validator = new IOValidator();

    /**
     * Construtor padrão passando todos os parametros para um automato finito nao deterministico com transição espontanea
     * @param aceitacao
     * @param estadoInicial
     * @param transicao
     * @param transicaoVazia
     * @param alfabeto
     */
    public NaoDeterministicoE(int[] aceitacao, int estadoInicial, int [][][] transicao, int [][] transicaoVazia , String alfabeto ) {
        super(aceitacao, estadoInicial, transicao, transicaoVazia, alfabeto);
    }

    /**
     *  Construtor com conversão de matriz int para char para um automato nao deterministico com transição espontanea
     * @param aceitacao
     * @param estadoInicial
     * @param transicao
     * @param transicaoVazia
     * @param alfabeto
     */
    public NaoDeterministicoE(char[] aceitacao, char estadoInicial, char [][][] transicao, char [][] transicaoVazia , String alfabeto ) {
        super(validator.convertArrayCharToArrayInt(aceitacao), estadoInicial,  validator.convertMatrizCharToInt(transicao), validator.convertMatrizCharToInt(transicaoVazia), alfabeto);
    }

    /**
     *  Construtor para automato não deterministico
     *  este nao possui transição espontanea
     * @param aceitacao
     * @param estadoInicial
     * @param transicao
     * @param alfabeto
     */
    public NaoDeterministicoE(int[] aceitacao, int estadoInicial, int [][][] transicao, String alfabeto ) {
        super(aceitacao, estadoInicial, transicao, alfabeto);
        IOValidator validator = new IOValidator();
    }

    /** A aplicação é executada a partir da entrada podendo dar a exception da entrada nao pertencer ao alfabeto
     * @param entrada
     * @return
     * @throws IsNotBelongOnLanguage
     */
    public boolean executar(String entrada) throws IsNotBelongOnLanguage {
        int [] estados = leitura(entrada);
        if (aceita(estados)) {
            return true;
        } else {
            return false;
        }
    }

    private int[] leitura(String entrada) throws IsNotBelongOnLanguage {
        int posicao = 0;
        int[] estados = eclose(new int[]{estadoInicial});
        while (posicao < entrada.length()) {
            if (isDebug()) print(entrada, estados, posicao);
            String elemento = entrada.substring(posicao, posicao + 1);
            estados = controleFinito(estados, elemento);
            if (estados.length == 0) {
                break;
            }
            posicao++;
        }
        if (isDebug()) {
            print(entrada, estados, posicao);
        }
        return estados;
    }

    private int [] controleFinito(int [] estados, String elemento) throws IsNotBelongOnLanguage {
        int[] novosEstados = new int[]{};
        for (int i : estados) {
            int iElemento = alfabeto.indexOf(elemento);
            if (iElemento == -1)
                throw new IsNotBelongOnLanguage( "elemento:{" + elemento + "} Nao pertence ao alfabeto" );

            int[] destinoTransicao = transicao[i][iElemento];
            novosEstados = uniao(novosEstados, destinoTransicao);
            novosEstados = eclose(novosEstados);

        }

        estados = novosEstados;
        return estados;
    }

    private int[] eclose(int[] estados) {
        int[] eclose = estados;
        for (int i : estados) {
            int[] ecloseAux = transicaoVazia[i];
            int[] ecloseAux2 = eclose(ecloseAux);
            eclose = uniao(eclose, ecloseAux);
            eclose = uniao(eclose, ecloseAux2);
        }
        return eclose;
    }

    private int[] uniao(int[] estados, int[] novosEstados) {
        Set<Integer> uniao = new TreeSet<Integer>();
        for (int i : estados) {
            uniao.add(i);
        }
        for (int i : novosEstados) {
            uniao.add(i);
        }
        int[] ret = new int[uniao.size()];
        int j = 0;
        for (int i : uniao) {
            ret[j++] = i;
        }
        return ret;
    }

    private boolean aceita(int[] estados) {
        for (int i : estados) {
            for (int j : aceitacao) {
                if (i == j) {
                    return true;
                }
            }
        }
        return false;
    }

    private void print(String entrada, int[] estados, int posicao) {
        System.out.print(entrada.substring(0, posicao) + "{");
        for (int i = 0; i < estados.length; i++) {
            System.out.print("q" + estados[i]);
            if (i < estados.length - 1) {
                System.out.print(",");
            }
        }
        System.out.println("}" + entrada.substring(posicao));
    }
}