package expressoesregulares.arvoresintatica;

public class LeafNode extends Node {
  private int num;

  public LeafNode(String symbol, int num) {
    super(symbol);
    this.num = num;
  }
}