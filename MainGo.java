package sample;

import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.util.List;

public class MainGo
  implements ImainGo
{

  public  Main M;
  public ButtonListPane B;
  public FieldListPane F;

  public MainGo(Main main){
    M = main;
  }


  public int goCmd(String s){
    if (s.charAt(0) == 'P') {goCmdM(s); return 0;}
    switch (s) {
      case "wrap": //btnA.getBtn("wrap")
        return go_wrap();
      case "cls": //btnA.getBtn("cls")
        M.textArea.clear(); return 0;
      case "save" : //btnA.getBtn("save")
        return 0;
      case "stp-tree1" : //btnA.getBtn("stp-tree1")
      case "stp-tree2" : //btnA.getBtn("stp-tree2")
      case "stp-tree3" : //btnA.getBtn("stp-tree3")
      case "stp-main" : //btnA.getBtn("stp-main")
        return toTop2(s);
      case "out_pages":
        //M.ppp(M.CNM.toString());
        //M.ppp("" + M.CNM.getItems().size());
        Node n= (Node)M.btnA.getBtn("out_pages");
        M.CNM.show(n, Side.BOTTOM,0, 00);
        return 0;
      case "04" :

      case "05" :

      case "06" :

      case "07" :

      case "08" :

      case "09" :

      case "11" :

      case "12" :

      case "13" :

      case "14" :

      case "15" :

      case "16" :

      case "17" :

      case "18" :

      case "19" :

      case "20" :

      case "21" :

      default:
        break;
    }

    return 0;


  }


  public void goCmdM(String s) {
    Button b;
    switch (s) {
      case "P00":
      case "P01":
      case "P02":

      case "P03":

      case "P04":
        M.outP  = Integer.parseInt(s.substring(1));
        b = M.btnA.getBtn("out_pages");
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




  public void goCmdT(String s) {
    switch (s) {
      case "nameB":
        M.ppp(s);
        return;
      case "name":
        M.ppp(s);
        return;
      case "03":

      case "04":

      case "05":

      case "06":

      case "07":

      case "08":

      case "09":

      case "11":

    }
  }

  //★============//btnA.getBtn("wrap")========================================
  int go_wrap(){
    Button btn = M.btnA.getBtn("wrap"); if (btn == null) return 0;
    String s;
    if (M.textArea.isWrapText()){
      M.textArea.setWrapText(false); s = "折り返し表示 off";
      //btn.setBackground(new Background( new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
      //--btn.setBorder(new  Border(new BorderStroke(BLACK,
      //-- BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }else{
      M.textArea.setWrapText(true); s = "折り返し表示 on";
      //--new BackgroundFill(Color.RED, new CornerRadii( 127 ), Insets.EMPTY)
      //btn.setBackground(new Background( new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
      //--btn.setBorder(new  Border(new BorderStroke(BLUE,
      //--BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }
    btn.setText(s);
    return 0;
  }
  //★--//btnA.getBtn("stp-tree1")--//btnA.getBtn("stp-main")----------
  int toTop2(String s) {
    int size = M.STP.getChildren().size();
    for (;;) {
      if (size <= 1) break;
      int cnt = 0;
      for (Node n : M.STP.getChildren()) {
        if (n.getId().equals(s)) break;
        cnt++;
      }
      for (; ; ) {
        if (cnt >= size - 1) break;

        Node n1 = M.STP.getChildren().get(cnt);
        //Node n2 = STP.getChildren().get(size - 1);
        M.STP.getChildren().remove(cnt);
        M.STP.getChildren().add(n1);
        break;
      }
      break;
    }
    int k = M.TP.getSelectionModel().getSelectedIndex();

    if (k != 0) {
      M.TP.getSelectionModel().select(0); //タブページが1
    }
    followSTP();
    M.STP.getChildren().get(size -1).requestFocus();

    return 0;
  }

  //☆
  void followSTP() {
    M.followSTP();
  }

//=========================
//  選択ノードのdrbデータを取り出す
//=========================



}
