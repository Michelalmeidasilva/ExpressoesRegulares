package expressoesregulares.conversao;

import java.util.ArrayList;
import java.util.List;

class NFA{
  String K;                                   //estados
  String E;                                   //alfabeto
  List<String> delta = new ArrayList();       //transicoes
  String s;                                   //estado ini
  String F;                                   //estado final
}
