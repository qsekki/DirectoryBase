package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;
import java.util.List;

public class ContextItemList implements EventHandler<ActionEvent> {
  public ImainGo M;
  public final ContextMenu CM = new ContextMenu();
  //private String CMD = "";
  private String CMN = "";
  private List<MenuItem> MI = new ArrayList<MenuItem>();

  public ContextItemList(ImainGo m){
    M = m;
  }

  //@Override
  public void handle(ActionEvent event) {
    MenuItem mi = (MenuItem)event.getSource();
    String s = getN(mi);
    M.goCmd(s);

  }

  public String getN(MenuItem mi) {
    if (mi == null) return "";
    return mi.getId();
  }

  public MenuItem getMni(String s) {
    int n = CMN.indexOf("/" + s + "\t");
    if (n < 0) return null;
    int m = CMN.lastIndexOf("\t", n - 1);
    String  s1 = CMN.substring(m + 1, n);
    //return (Button)(FP.getChildren().get(Integer.parseInt(s1)));
    return MI.get(Integer.parseInt(s1));
  }

  public void setDoMni(String[] ss){
    int c = CM.getItems().size();
    for (String s : ss) {
      int k = s.length(); if (k == 0) {s = "???"; k = 3;}
      int n = s.indexOf('/');
      String s1 = "";
      String s2 = "";
      String sn = "";
      if (n <= 0) s1 = s; else {
        if (n == k - 1) s1 = s; else {
          sn = s.substring(0,n);
          s2 = s.substring(n + 1);
        }
      }

      if (s1.length() >= 1 || s2.indexOf('/') >= 0) {
        if (s1.length() == 0) s1 = s2;
        MenuItem b = new MenuItem(s1); CM.getItems().add(b); b.setDisable(true);
        MI.add(b);
        c++; continue;
      }

      MenuItem b = new MenuItem(s2); b.setId(sn);
      CM.getItems().add(b);
      MI.add(b);
      if ((CMN + "\t").indexOf("/" + sn + "\t") >= 0){
        b.setDisable(true); c++; continue;
      }

      b.setOnAction(this); //★★★ M.btnPA

      //CMD += "\t" + sn +  "/" + s2;
      s1 = Integer.toString(c); //★★★
      CMN += "\t" + s1 +  "/" + sn;
      c++;
    }
    CMN += "\t";
    int k = CM.getItems().size();

  }



}
