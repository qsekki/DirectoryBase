package sample;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class DrbTreeView extends TreeView<DrbNode> {
  public String S;
  public int err = -1;
  int m_l;
  int m_p;
  int m_err;
  TreeItem<DrbNode> tS1;
  Main M;
  StringBuffer SB;
  String[] SA;
  String SS;
  String S1;


  DrbTreeView(){
    super();
    S = null;
  }

  DrbTreeView(String s){
    super();
    S = s;
  }

  String getS(){
    return S;
  }

  void setS(String s){
    S = s;
  }

  private  TreeItem<DrbNode> getT() {
    //ppp(m_p);
    //ppp(m_l);
    /*
    int k = S.indexOf('\00', m_p);
    String s = S.substring(m_p, k);
    int k1 = s.indexOf('\01');
    if (k1 >= 2) s = s.substring(1, k1);
    else s = s.substring(1);
    k1 = m_p;
    m_p = ++k;
    return new TreeItem<DrbNode>(new DrbNode(this, k1, 0));
    */
    int k = S.indexOf('\00', m_p);
    int k1 = m_p;
    m_p = ++k;
    return new TreeItem<DrbNode>(new DrbNode(this, k1, 0));
  }


  private char get_12Drb(char ch) {
    TreeItem<DrbNode> tsp = tS1;
    for (; ; ) {
      TreeItem<DrbNode> ts1 = getT(); //フォルダのとき
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


  void mkTree(){
    if (S == null) return;
    m_l = S.length();
    m_p = 0;
    m_err = 0;
    if (m_l - 3 < m_p) return;
    char ch1 = S.charAt(m_p);
    TreeItem<DrbNode> ts1 = new TreeItem<DrbNode>(new DrbNode(this,0, 0));
    tS1 = ts1;
    //M.ppp( "" + ts1.getValue().toString());
    ch1 = get_12Drb(ch1);
    tS1 = null;
    int size = ts1.getChildren().size();
    if (ch1 != '4' || size != 1) {//階層指定が不正
      System.out.println("not get tree");
      err = -2;
      return;
    }
    setRoot(ts1.getChildren().get(0)); err = 0;

  }

  void mkTree(String s){
    S = s;
    if (S == null){
      setRoot(null); err = -1; return;
    }
    mkTree();

  }

  //================-----------
  //   指定したアイテムのdrbツリー文字列を取り出す
  //==========================
  public  String mkDrbStr(TreeItem<DrbNode> t) {
    if (S == null) return null;
    //M.ppp(t.getValue().toString());
    int k0 = t.getValue().pd;
    int k1 = k0;
    for (; ; ) {
      char ch = S.charAt(k1);
      if (ch == '0' || ch == '2') {
        int k2 = t.nextSibling().getValue().pd;
        return S.substring(k0, k2);
      }
      t = t.getParent();
      if (t == null) {
        return S.substring(k0);
      }
      k1 = t.getValue().pd;
    }
  }

  //================-----------
  //   指定したアイテムの階層化ツリー文字列を取り出す
  //==========================
  public  String mkDotTreeS(TreeItem<DrbNode> t) {

    SS = mkDrbStr(t);
    if (SS == null) return null;
    SS = '1' + SS.substring(1);
    if (mkDotTree() == 0) {
      String s1 = SS;
      SS = null;
      S1 = null;
      SB = null;
      SA = null;
      //M.ppp(s1);
      return s1;
    }
    return null;
  }


  private void outTitleExp(String s){
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

  private char get_12(char ch, String dt) {
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

  private int mkDotTree() {
    // Sに文字列データを入れる
    SA = SS.split("\00");
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
    SS = SB.toString(); //SSに結果が入る
    //System.out.println(S);
    return 0;
  }





}








