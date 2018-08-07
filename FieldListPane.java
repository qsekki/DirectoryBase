package sample;

//*||FieldLsitPane.java

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

import javax.xml.soap.Text;
import java.util.ArrayList;
import java.util.List;

public class FieldListPane implements ChangeListener, EventHandler<ActionEvent> {
  public ImainGo M;
  public final FlowPane FP = new FlowPane();
  //private String CMD = "";
  private String CMN = "";
  private List<TextField> CB = new ArrayList<TextField>();
  private List<Label> CBL = new ArrayList<Label>();

  public FieldListPane(ImainGo m){
    M = m;
  }

  @Override
  public void changed(ObservableValue observable, Object oldValue, Object newValue) {

  }

  @Override
  public void handle(ActionEvent event) {
    TextField t = (TextField)event.getSource();
    String s = getN(t);
    //M.ppp(s);
    M.goCmdT(s);
  }



  public String getN(TextField t) {
    if (t == null) return "";
    return t.getId();
  }

  public String getN(Label a) {
    if (a == null) return "";
    return a.getId();
  }


  public TextField getTxt(String s) {
    int n = CMN.indexOf("/" + s + "\t");
    if (n < 0) return null;
    int m = CMN.lastIndexOf("\t", n - 1);
    String  s1 = CMN.substring(m + 1, n);
    //return (TextField)(FP.getChildren().get(Integer.parseInt(s1)));
    return CB.get(Integer.parseInt(s1));
  }

  public Label getLbl(String s) {
    int n = CMN.indexOf("/" + s + "\t");
    if (n < 0) return null;
    int m = CMN.lastIndexOf("\t", n - 1);
    String  s1 = CMN.substring(m + 1, n);
    //return (Label)FP.getChildren().get(Integer.parseInt(s1) - 1);
    return CBL.get(Integer.parseInt(s1));
  }

  public void setDoTxt(String[] ss){
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
        TextField t = new TextField();
        Label lb = new Label(s1);
        FP.getChildren().addAll(lb, t);
        CB.add(t); CBL.add(lb);
        t.setDisable(true); c += 1;; continue;
      }

      TextField t = new TextField(); t.setId(sn);
      Label lb = new Label(s2); lb.setId(sn);
      FP.getChildren().addAll(lb, t);
      CB.add(t); CBL.add(lb);
      if ((CMN + "\t").indexOf("/" + sn + "\t") >= 0){
        t.setDisable(true); c += 1; continue;
      }

      t.setOnAction(this); //★★★
      //t.textProperty().addListener(this);

      //CMD += "\t/" + sn;
      s1 = Integer.toString(c); //★★★
      CMN += "\t" + s1 +  "/" + sn;
      c += 1;
    }
    CMN += "\t";
    //M.ppp(CMN);
  }




}
