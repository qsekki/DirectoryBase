package sample;

//*||ButtonListPane.java

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;
import java.util.List;

public class ButtonListPane implements EventHandler<ActionEvent> {
  public ImainGo M;
  public final FlowPane FP = new FlowPane();
  //private String CMD = "";
  private String CMN = "";
  private List<Button> CB = new ArrayList<Button>();

  public ButtonListPane(ImainGo m){
    M = m;
  }

  @Override
  public void handle(ActionEvent event) {
    Button btn = (Button)event.getSource();
    String s = getN(btn);

    M.goCmd(s);

  }


  public String getN(Button btn) {
    if (btn == null) return "";
    return btn.getId();
  }

  public Button getBtn(String s) {
    int n = CMN.indexOf("/" + s + "\t");
    if (n < 0) return null;
    int m = CMN.lastIndexOf("\t", n - 1);
    String  s1 = CMN.substring(m + 1, n);
    //return (Button)(FP.getChildren().get(Integer.parseInt(s1)));
    return CB.get(Integer.parseInt(s1));
  }

  public void setDoBtn(String[] ss){
    int c = FP.getChildren().size();
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
        Button b = new Button(s1); FP.getChildren().add(b); b.setDisable(true);
        CB.add(b);
        c++; continue;
      }

      Button b = new Button(s2); b.setId(sn);
      FP.getChildren().add(b);
      CB.add(b);
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
    int k = FP.getChildren().size();

  }



}
