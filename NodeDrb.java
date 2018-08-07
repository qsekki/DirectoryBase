package sample;

public class NodeDrb {
  static String SX;
  //public String S;
  public int pd, pf;

  public NodeDrb(int d, int f){
    //this.S = ss;
    this.pd = d;
    this.pf = f;
  }

  public String toString() {
    int k = SX.indexOf('\00', pd);
    String s = SX.substring(pd, k);
    int k1 = s.indexOf('\01');
    if (k1 >= 2) return s.substring(1, k1); else return s.substring(1);
  }

}
