package sample;

public class DrbNode {
  public  DrbTreeView tv;
  //public String S;
  public int pd, pf;

  public DrbNode(int d, int f){
    //this.S = ss;
    tv = null;
    this.pd = d;
    this.pf = f;
  }
  public DrbNode(DrbTreeView v, int d, int f){
    //this.S = ss;
    tv = v;
    this.pd = d;
    this.pf = f;
  }

  public String toString() {

    int k = tv.S.indexOf('\00', pd);
    String s = tv.S.substring(pd, k);
    int k1 = s.indexOf('\01');
    if (k1 >= 2) return s.substring(1, k1); else return s.substring(1);
  }


}
