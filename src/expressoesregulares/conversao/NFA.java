package expressoesregulares.conversao;

import java.util.ArrayList;
import java.util.List;

public class  NFA{
  public List<String>     K = new ArrayList<String>();
  //estados
  public List<String>   E = new ArrayList<String>();                                   //alfabeto
  public List<String> delta = new ArrayList();       //transicoes
  public String s ;                                   //estado ini
  public List<String>   F = new ArrayList<String>();                                   //estado final
  public ThompsonAlgorithm converter = new ThompsonAlgorithm();

  public int[] getK() {
    int estados [ ] = new int[K.size()];
    for (int i = 0; i < estados.length; i++) {
      estados[i] = Integer.parseInt(K.get(i));
    }
   return estados;
  }

  public int getS() {
    return Integer.parseInt(s);
  }

  public int[] getF() {
    int[] estadosFinais = new int[F.size()];
    for (int i = 0; i < estadosFinais.length; i++) {
      estadosFinais[i] = Integer.parseInt(F.get(i));
    }
    return estadosFinais;
  }
}

