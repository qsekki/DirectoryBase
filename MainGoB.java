package sample;

public class MainGoB
implements ImainGo
{
  public  Main M;
  public ButtonListPane B;
  public FieldListPane F;

  public MainGoB(Main main){
    M = main;
  }

  public int goCmd(String s){
    switch (s) {
      case "append-B" : //freeBtn.getBtn("append-B")
        M.addItem(M.freeTxt.getTxt("nameB").getText()); return 0;
      case "remove-B" : //freeBtn.getBtn("remove-B")
        M.removeItem(); return 0;
      case "closeAll-B0" :
        M.ppp(s); return 0;
      case "closeAll-B1" :
        M.ppp(s); return 0;
      case "toParent-B0" :
        M.ppp(s); return 0;
      case "toParent-B1" :
        M.ppp(s); return 0;
      case "fileTgl-B0" : //freeBtn.getBtn("fileTgl-B0")
        M.dspFile0 = M.setDspFile(-1); return 0;
      case "fileTgl-B1" : //freeBtn.getBtn("fileTgl-B1")
        M.dspFile1 = M.setDspFile(-1); return 0;
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

  public void goCmdT(String s){
    return;
  }




}
