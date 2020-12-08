package expressoesregulares.arvoresintatica;
import expressoesregulares.conversao.NfaMap;

public class Node {

  private String symbol;
  public NfaMap automato;
  private Node parent;
  private Node left;
  private Node right;

  public Node(String symbol) {
    automato = new NfaMap();
    this.symbol = symbol;
    parent = null;
    left = null;
    right = null;
  }

  /**
   * @return symbol
   */
  public String getSymbol() {
    return symbol;
  }

  /**
   * @param parent
   */
  public void setParent(Node parent) {
    this.parent = parent;
  }

  /**
   * @return  left
   */
  public Node getLeft() {
    return left;
  }

  /**
   * @param left
   */
  public void setLeft(Node left) {
    this.left = left;
  }

  /**
   * @return  right
   */
  public Node getRight() {
    return right;
  }

  /**
   * @param right  right
   */
  public void setRight(Node right) {
    this.right = right;
  }
}