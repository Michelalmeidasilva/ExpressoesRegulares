package expressoesregulares.conversao;

import java.util.ArrayList;
import java.util.List;

public class  NFA{
  List<String>     K = new ArrayList<String>();
  //estados
  List<String>   E = new ArrayList<String>();                                   //alfabeto
  List<String> delta = new ArrayList();       //transicoes
  String s ;                                   //estado ini
  List<String>   F = new ArrayList<String>();                                   //estado final
  public Converter converter = new Converter(){

  };


}

