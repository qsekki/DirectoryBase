package sample;

import javafx.scene.control.*;

public class MainGoM implements ImainGo {
  public Main M;
  public ButtonListPane B;
  public FieldListPane F;

  public MainGoM(Main main) {
    M = main;
  }

  public int goCmd(String s) {
    //System.out.println(s);
    if (s.charAt(0) == 'P') {goCmdM(s); return 0;}
    switch (s) {
      case "test":
        setDrbTreeX(); return 0;
      case "drb_data":
        pppDrbX(); return 0;
      case "dot_data":
        pppDrbDotX(); return 0;
      case "test_del":
        delDrbTreeX(); return 0;
      case "05":

      case "06":

      case "07":

      case "08":

      case "09":

      case "10":

      default:
        break;
    }
    return 0;
  }

  //////////////////////

  public void goCmdM(String s) {
    switch (s) {
      case "P00":
      case "P01":
      case "P02":

      case "P03":

      case "P04":
        M.outP  = Integer.parseInt(s.substring(1));
        Button b = M.btnA.getBtn("out_pages");
        b.setText("出力先: " + M.outPs[M.outP]);
        return;
      case "05":

      case "06":

      case "07":

      case "08":

      case "09":

      case "10":

      default:
        break;
    }
  }

////////////////////////////

    public void goCmdT(String s) {
    switch (s) {
      case "01":

      case "02":

      case "03":

      case "04":

      case "05":

      case "06":

      case "07":

      case "08":

      case "09":

      case "10":

      default:
        break;
    }
  }
///////////////////////////////////
  private void setDrbTree1(){
    return;
/*
    Tab t = (Tab)M.TP.getSelectionModel().getSelectedItem();
    M.ppp(t.getText());
    TreeView tv = (TreeView)t.getContent();
    TreeItem<String> t0;
    t0 = tv.getRoot();
    if (t0 == null) {
      t0 = new TreeItem<String>(t.getText());
      tv.setRoot(t0); t0.setExpanded(true); return;
    }
    int k = t0.getChildren().size();
    t0.getChildren().add(new TreeItem<String>("Item-" + Integer.toString(++k)));

*/
  }
///////////////////////////////////////////
  private void setDrbTree(){
    int k = M.TP.getSelectionModel().getSelectedIndex();
    if (k == 0 || k >= 6) return;
    Tab t = (Tab)M.TP.getSelectionModel().getSelectedItem();
    TreeView<NodeDrb> tv = (TreeView<NodeDrb>)t.getContent();
    TreeItem<NodeDrb> t0;
    t0 = tv.getRoot();
    if (t0 == null) {
      //t0 = new TreeItem<NodeDrb>(t.getText());

      String s = DrbData.getDrbS(DrbData.getS1());
      //ppp(s);
      M.pppx(s);
      //ppp(DrbData.mkDotTreeS(s));
      NodeDrb.SX = s;
      TreeItem<NodeDrb> drb = DrbData.mkTreeDrb(s);
      if (drb == null) {M.ppp("null"); return;}
      M.ppp(DrbData.pppDrb(drb, 0));
      //tv.setRoot(drb); DrbData.addLst(tv, s); //********************
      DrbData.setRoot(tv, drb); //*****************************
      drb.setExpanded(true); return;
    }
  }

  private void setDrbTreeX(){
    int k = M.TP.getSelectionModel().getSelectedIndex();
    if (k <=0 || k >=6) return;
    Tab t = (Tab)M.TP.getSelectionModel().getSelectedItem();
    DrbTreeView tv = (DrbTreeView)t.getContent();
    TreeItem<DrbNode> t0 = tv.getRoot();
    if (t0 == null) {
      String s = DrbData.getDrbS(DrbData.getS1());
      M.pppx(s);
      //tv.setS(s);
      //tv.mkTree();

      //tv.mkTree(null);//全て初期化

      tv.M = M;
      tv.mkTree(s);
    }
  }

  private void delDrbTreeX() {
    int k = M.TP.getSelectionModel().getSelectedIndex();
    if (k <= 0 || k >= 6) return;
    Tab t = (Tab) M.TP.getSelectionModel().getSelectedItem();
    DrbTreeView tv = (DrbTreeView) t.getContent();
    TreeItem<DrbNode> t0 = tv.getRoot();
    if (t0 != null) {
      tv.mkTree(null);//全て初期化
    }
  }
  //=========================
//  選択ノードのdrbデータを取り出す
//=========================
  void pppDrbX() {
    int k = M.TP.getSelectionModel().getSelectedIndex();
    if (k <= 0 || k >= 6) return;
    Tab t = (Tab)M.TP.getSelectionModel().getSelectedItem();
    DrbTreeView tv = (DrbTreeView)t.getContent();
    if (tv.err != 0) return;
    TreeItem<DrbNode> item =  tv.getSelectionModel().getSelectedItem();
    if (item == null) tv.getSelectionModel().select(tv.getRoot());
    item =  tv.getSelectionModel().getSelectedItem();
    if (item == null) return;
    item.toString();
    String s = tv.mkDrbStr(item);
    M.pppx(s);

  }

/*
  void pppDrb() {
    int k = M.TP.getSelectionModel().getSelectedIndex();
    if (k <= 0 || k >= 6) return;
    M.ppp("++++");

    pppDrb5(); return;
    Tab t = (Tab)M.TP.getSelectionModel().getSelectedItem();
    TreeView<NodeDrb> tv = (TreeView<NodeDrb>)t.getContent();
    TreeItem<NodeDrb> t0 = tv.getRoot();
    if (t0 == null) {
      M.ppp("not Exist TreeItemNode");
      return;
    }
    TreeItem<NodeDrb> item =  tv.getSelectionModel().getSelectedItem();
    if (item == null){
      M.ppp("not Selected TreeItemNode");
      return;
    }
    String s = DrbData.mkDrbStr(item, tv, M);
    M.pppx(s);
  }
*/

  //=========================
  //  選択ノードの階層化テキストデータを取り出す
  //=========================
  void pppDrbDotX() {
    int k = M.TP.getSelectionModel().getSelectedIndex();
    if (k <= 0 || k >= 6) return;
    Tab t = (Tab)M.TP.getSelectionModel().getSelectedItem();
    DrbTreeView tv = (DrbTreeView)t.getContent();
    if (tv.err != 0) return;
    TreeItem<DrbNode> item =  tv.getSelectionModel().getSelectedItem();
    if (item == null) tv.getSelectionModel().select(tv.getRoot());
    item =  tv.getSelectionModel().getSelectedItem();
    if (item == null) return;
    String s = tv.mkDotTreeS(item);
    M.ppp(s);

  }







}
