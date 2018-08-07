package sample;

//*||DrbData.java

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.ArrayList;
import java.util.List;

public class DrbData {

  static String S;
  static String SD;
  static StringBuffer SB;
  static String[] SA;
  static int m_l;
  static int m_p;
  static int m_err;
  static String S1;
  static List<DrbData.DataD> Lst = new ArrayList<DrbData.DataD>();

  static TreeItem<NodeDrb> tS1;

  public static String getS1() {
    StringBuffer sb = new StringBuffer("");
    sb.append(".a1\nabc\ndef\n");
    sb.append("..a-2\n123\n456\n");
    sb.append("..a-3\n");
    sb.append("...a--4\n");
    sb.append("...a--5\n");
    sb.append("...a--6\n");
    sb.append("....a---7\n");
    sb.append("...a--8\n");
    sb.append("...a--9\n");
    sb.append("..a-10\n");
    sb.append(".a-11\n");
    sb.append(".a-12\n");
    return sb.toString();
  }

//==================================================

  private static int getN(String s) {
    int k = s.length();
    int n = 0;
    for (int i = 0; i < k; i++) {
      if (s.charAt(i) != '.') return n;
      n++;
    }
    return n;
  }

  private static String getSN(int n, String s) {
    String sn = s.substring(n);
    return sn.trim();
  }

  private static int getFirst(int n) {
    //String s1 = s;
    int m0 = SB.length();
    int m1;
    int cnt = 1;
    int n1, k;
    for (; ; ) {
      m1 = SB.length();
      //String sn = getSN(n, S1);
      String sn = S1.substring(n).trim();
      if (sn.length() == 0) sn = "title-" + Integer.toString(cnt++);
      SB.append("0" + sn); /////////////////////////////////
      k = 0;
      for (; ; ) {
        if (m_p >= m_l) {
          n1 = 0;
          m_err = -1;
          break;
        } // データが終了 エラーと異なる
        S1 = SA[m_p++];
        n1 = getN(S1);
        if (n1 >= 1) break;
        if (k == 0) {
          SB.append('\01');
          k = 1;
        }
        SB.append(S1 + "\n");
      }
      SB.append('\00');
      if (n1 == 0) break;
      if (n1 == n + 1) {
        n1 = getFirst(n1);
      }
      if (n1 == n) continue;
      if (n1 < n) break;
      m_err = -10;
      return -10; // n1 >= n + 2
    }
    if (m0 == m1) {
      SB.setCharAt(m0, '1');
    } else {
      SB.setCharAt(m0, '2');
      SB.setCharAt(m1, '3');
    }
    return n1;
  }

  private static int mkTree() {
    // Sに文字列データを入れる
    //S = S.replace("\r\n", "\n");
    //S = S.replace('\r', '\n');
    SA = S.split("\n");
    m_l = SA.length;
    m_p = 0;
    m_err = 0;
    int n = 0;
    for (; ; ) {
      if (m_p >= m_l) {
        System.out.println("not get string");
        return -1;
      }
      S1 = SA[m_p++];
      n = getN(S1);
      if (n == 0) continue;
      if (n == 1) break;
      System.out.println("not get one dot string in the top");
      return -2;
    }
    //S1に読み込まれている現在の行がはいる
    SB = new StringBuffer("1-\00");
    int n1 = getFirst(n); //nは1//////////////////////////////////////////
    if (n1 != 0) {//階層指定が不正
      System.out.println("not get tree");
      return -3;
    }
    S = SB.toString(); //Sに結果が入る
    //System.out.println(S);
    return 0;
  }

  //////entry//////////
  public static String getDrbData() {
    S = getS1();
    //System.out.println(S);
    if (mkTree() == 0) { //Sに結果が入る drb階層を作成
      return S;
      //if (mkDotTree() == 0) { //SDに結果が入る drb階層をdot階層に変換
      // return SD;
      //}
    }
    return null;
  }

  //////entry/////////
  public static String getDrbS(String s) {
    S = s;
    if (mkTree() == 0) { //Sに結果が入る drb階層を作成
      String s1 = S;
      S = null;
      SB = null;
      SA = null;
      return s1;
      //if (mkDotTree() == 0) { //SDに結果が入る drb階層をdot階層に変換
      // return SD;
      //}
    }
    return null;
  }


  //===========================================================
  private static void outTitleExp(String s) {
    int k = s.indexOf('\01');
    if (k <= -1) {
      SB.append(s + '\n');
      return;
    }
    if (k == 0)
      SB.append(" 無題\n");
    else
      SB.append(' ' + s.substring(0, k) + '\n');
    s = s.substring(k + 1);
    k = s.length();
    if (k == 0) return;
    if (s.charAt(k - 1) != '\n') k = 1;
    else k = 0;
    s = s.replace("\n.", "\n .");
    if (s.charAt(0) == '.')
      SB.append(' ' + s + ((k == 1) ? '\n' : ""));
    else
      SB.append(s + ((k == 1) ? '\n' : ""));
  }

  private static char get_12(char ch, String dt) {
    for (; ; ) {
      //ppp(S1);
      SB.append(dt + ' ');
      outTitleExp(S1.substring(1));

      if (m_p >= m_l) return '4';
      S1 = SA[m_p++];
      char ch1 = S1.charAt(0);
      if (ch1 == '1' || ch1 == '2') {
        ch1 = get_12(ch1, dt + "."); //ch1 = S1.charAt(0); 0か3が返される
      }
      //ch1は0か3が入っている
      if (ch == '1' || ch >= '3') return ch1; //呼び出しもとで処理させる
      //ch == 2 || ch ==0
      ch = ch1; //chは0か3が入る
    }
  }

  private static int mkDotTree() {
    // Sに文字列データを入れる
    SA = S.split("\00");
    m_l = SA.length;
    m_p = 0;
    m_err = 0;
    //S1に読み込まれている現在の行がはいる
    SB = new StringBuffer("");
    S1 = SA[m_p++];
    char c1 = get_12(S1.charAt(0), "."); //nは1
    if (c1 != '4') {//階層指定が不正
      System.out.println("not get tree");
      return -3;
    }
    S = SB.toString(); //SDに結果が入る
    //System.out.println(S);
    return 0;
  }

  /////entry/////////
  public static String mkDotTreeS(String s) {
    S = s;
    if (mkDotTree() == 0) {
      String s1 = S;
      S = null;
      SB = null;
      SA = null;
      return s1;
    }
    return null;
  }


  //========================================

  private static void ppp(String s) {
    System.out.println(s);
  }

  private static void ppp(int n) {
    System.out.println(n);
  }

//=====================================

  private static TreeItem<NodeDrb> getT() {
    //ppp(m_p);
    //ppp(m_l);

    int k = S.indexOf('\00', m_p);
    String s = S.substring(m_p, k);
    int k1 = s.indexOf('\01');
    if (k1 >= 2) s = s.substring(1, k1);
    else s = s.substring(1);
    k1 = m_p;
    m_p = ++k;
    return new TreeItem<NodeDrb>(new NodeDrb(k1, 0));
  }

  private static char get_12Drb(char ch) {
    TreeItem<NodeDrb> tsp = tS1;
    for (; ; ) {
      TreeItem<NodeDrb> ts1 = getT(); //フォルダのとき
      tsp.getChildren().add(ts1);
      // ファイルのとき ch1は 3になるまでスキップ 1  2...3  0...3

      if (m_p >= m_l) return '4';
      char ch1 = S.charAt(m_p);
      if (ch1 == '1' || ch1 == '2') { //フォルダのとき
        tS1 = ts1;
        ch1 = get_12Drb(ch1); //ch1 0か3が返される
      }
      //ch1は0か3が入っている
      if (ch == '1' || ch >= '3') {
        return ch1; //呼び出しもとで処理させる
      }
      //ch == 2 || ch ==0
      ch = ch1; //chは0か3が入る
    }
  }

  public static TreeItem<NodeDrb> mkTreeDrb(String s) {
    S = s;
    m_l = S.length();
    m_p = 0;
    m_err = 0;
    if (m_l - 3 < m_p) return null;
    char ch1 = S.charAt(m_p);
    TreeItem<NodeDrb> ts1 = new TreeItem<NodeDrb>(new NodeDrb(0, 0));
    tS1 = ts1;
    ch1 = get_12Drb(ch1);
    S = null;
    tS1 = null;
    int size = ts1.getChildren().size();
    if (ch1 != '4' || size != 1) {//階層指定が不正
      System.out.println("not get tree");
      return null;
    }
    return ts1.getChildren().get(0);
  }

  //====================================
  public static void setRoot(TreeView tv, TreeItem<NodeDrb> tm) {
    tv.setRoot(tm); addLst(tv, NodeDrb.SX);
  }
  public static void setStrD(TreeView tv){
    NodeDrb.SX = getStrLst(tv);
  }

  public static void addLst(TreeView tv, String s){
    int f = 0;
    DataD d1 = null;
    for (DataD d : Lst){
      if (d.tv == tv) { d1 = d; f = 1; break;}
    }
    if (f == 1){
      d1.s = s;
    } else {
      Lst.add(new DataD(tv, s));
    }
  }

  public static String getStrLst(TreeView tv){
    for (DataD d : Lst){
      if (d.tv == tv) return d.s;
    }
    return null;
  }

  public void rmLst(TreeView tv){
    for (DataD d : Lst){
      if (d.tv == tv){
        Lst.remove(d);
      }
    }
  }


  //=======================================
  private static void pppDrb1(TreeItem<NodeDrb> t, String sm) {
    for (TreeItem<NodeDrb> nd : t.getChildren()) {
      SB.append(sm + nd.getValue().toString() + '\n');
      if (nd.getChildren().size() >= 1) pppDrb1(nd, sm + "    ");
    }
  }

  public static String pppDrb(TreeItem<NodeDrb> t, int flgP) {//flgP == 0 最初の親をスキップ
    String s = "";
    SB = new StringBuffer();
    if (flgP != 0) {
      SB.append(t.getValue().toString() + '\n');
      s += "    ";
    }
    if (t.getChildren().size() != 0) pppDrb1(t, s);
    s = SB.toString();
    SB = null;
    return s;

  }

  //============================================

  public static String mkDrbStr(TreeItem<NodeDrb> t, TreeView tv, Main M)  {

    setStrD(tv);
    if (NodeDrb.SX == null) return null;
    M.ppp(t.getValue().toString());
    int k0 = t.getValue().pd;
    int k1 = k0;
    for (; ; ) {
      char ch = NodeDrb.SX.charAt(k1);
      if (ch == '0' || ch == '2') {
        int k2 = t.nextSibling().getValue().pd;
        return NodeDrb.SX.substring(k0, k2);
      }
      t = t.getParent();
      if (t == null) {
        return NodeDrb.SX.substring(k0);
      }
      k1 = t.getValue().pd;
    }


  }

  //////////////////////////////////////////////////////////
  static class DataD {
    TreeView tv;
    String s;

    DataD(TreeView t, String ss) {
      tv = t;
      s = ss;
    }
  }

}