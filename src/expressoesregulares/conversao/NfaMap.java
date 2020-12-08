package expressoesregulares.conversao;

import java.util.ArrayList;
import java.util.List;

public class NfaMap {

  public List<String>   K = new ArrayList<String>();
  public List<String>   E = new ArrayList<String>();                                   //alfabeto
  public List<String> delta = new ArrayList();                                         //transicoes
  public String s;                                                                    //estado ini
  public List<String>   F = new ArrayList<String>();                                   //estado final
  public ThompsonAlgorithm converter = new ThompsonAlgorithm();

  /**
   * Sprint 3
   * retorno dos estados
   * @author Michel Silva, Kelvin Souza
   * @return
   */
  public int[] getK() {
    int estados [ ] = new int[K.size()];
    for (int i = 0; i < estados.length; i++) {
      estados[i] = Integer.parseInt(K.get(i));
    }
   return estados;
  }

  /**
   * Sprint 3
   * retorno do estado inicial
   * @author Michel Silva, Kelvin Souza
   * @return
   */
  public int getS() {
    return Integer.parseInt(s);
  }

  /**
   * Sprint 3
   * retorno dos estados finais
   * @author Michel Silva, Kelvin Souza
   * @return
   */
  public int[] getF() {
    int[] estadosFinais = new int[F.size()];
    for (int i = 0; i < estadosFinais.length; i++) {
      estadosFinais[i] = Integer.parseInt(F.get(i));
    }
    return estadosFinais;
  }
}

