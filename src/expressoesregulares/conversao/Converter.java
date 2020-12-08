package expressoesregulares.conversao;

import afnd.domain.NaoDeterministicoE;
import expressoesregulares.Regex;

public interface Converter {
  public NaoDeterministicoE transformRegexToAFND(Regex regex);
}
