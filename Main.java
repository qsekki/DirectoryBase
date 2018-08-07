package sample;

//*||Main.java
//JavaFX TreeView Example---------------------------------
//||https://examples.javacodegeeks.com/core-java/javafx-treeview-example/
//◆◆javafx.event.EventTypeの使用
// https://docs.oracle.com/javase/jp/9/docs/api/javafx/event/class-use/EventType.html

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Main extends Application {
  public final boolean iswin = System.getProperty("os.name").toLowerCase().startsWith("windows");
  private final Node rootIcon = new ImageView(new Image(getClass().getResourceAsStream("folder.bmp")));
  private final Node depIcon = new ImageView(new Image(getClass().getResourceAsStream("folder_images.bmp")));

  public Node getIcon(){
    return new ImageView(new Image(getClass().getResourceAsStream("folder.bmp")));
  }
  //===================================
  public ImainGo MGM;
  public ImainGo MG;
  public ImainGo MGB;
  public final TreeView<String> treeView = new TreeView<String>();
  public final TreeView<FileTreeItem.FileX> treeV = new TreeView<FileTreeItem.FileX>();
  public final TextArea textArea = new TextArea();
  public TabPane TP;
  public StackPane STP;//左側 Tab
  public StackPane SP;//右側 FlowPanel
  public List<FlowPane> SpList = Arrays.<FlowPane>asList(null, null, null, null);
  public Menu MNU1;
  public Menu MNU2;
  public Label lblTitle = new Label("-");

  //--------------------------
  public ButtonListPane btnM ;
  private FlowPane flowPaneM ;
  //--------------------
  public ButtonListPane btnA ;
  public FlowPane flowPaneA ;
  //--------------------
  public FieldListPane cheA;
  public FlowPane cheFlowA;
  //---------------------
  public ButtonListPane freeBtn;
  public FieldListPane  freeTxt;
  //---------------------
  public ContextItemList CN;
  public ContextMenu CNM;

  //==================================================
  List<Employee> employees;
  public int dspFile = 1;
  public int dspFile0 = 1;
  public int dspFile1 = 1;
  public int outP = 0;
  public String[] outPs = new String[] { "A-1","A-2","A-3","A-4","A-5"};
  //////////////////////////////////////////////////
  public static void main(String[] args) {
    launch(args);
  }
  //=================================================
  @Override
  public void start(Stage stage) {
    TreeItem<String> t0 = new TreeItem<String>("123");
    TreeItem<String> t1 = new TreeItem<String>("abc");
    TreeItem<String> t2 = new TreeItem<String>("def");
    t0.getChildren().addAll(t1, t2);
    treeView.setRoot(t0);
    //treeView.setShowRoot(false);
    treeView.setEditable(true);
    treeView.setCellFactory(TextFieldTreeCell.forTreeView());

    //---------------------------=
    //◆◆javafx.event.EventTypeの使用
    // https://docs.oracle.com/javase/jp/9/docs/api/javafx/event/class-use/EventType.html
    //event.getEventType()
    //TreeView.editStartEvent() ---> TreeView.<String>editStartEvent()
    // EventHandler<TreeView.EditEvent<String>>

    //◇treeView.setOnEditStart Commit Cancel
    EventHandler<TreeView.EditEvent<String>> teAction = treeItemEdit();
    treeView.setOnEditStart(teAction);
    treeView.setOnEditCommit(teAction);
    treeView.setOnEditCancel(teAction);

    /*    個別にイベントハンドラを設定した
    treeView.setOnEditStart(new EventHandler<TreeView.EditEvent<String>>() {
      @Override
      public void handle(TreeView.EditEvent<String> event) {
       if (event.getEventType() == TreeView.<String>editStartEvent()) ppp("edit start");
        //ppp("内容の変更開始");
      }
    });
    //TreeView.editCommitEvent() ---> TreeView.<String>editCommitEvent()
    treeView.setOnEditCommit(new EventHandler<TreeView.EditEvent<String>>() {
      @Override
      public void handle(TreeView.EditEvent<String> event) {
        if (event.getEventType() == TreeView.<String>editCommitEvent()) ppp("edit commit");
        //ppp("内容の変更決定");
        //ppp(event.getOldValue());
        //ppp(event.getNewValue());
      }
    });
    //TreeView.editCancelEvent() ---> TreeView.<String>editCanselEvent()
    treeView.setOnEditCancel(new EventHandler<TreeView.EditEvent<String>>() {
      @Override
      public void handle(TreeView.EditEvent<String> event) {
        if (event.getEventType() == TreeView.<String>editCancelEvent()) ppp("edit cancel");
        //ppp("内容の変更を中止");
      }
    });
    */
//-----------------------------------------------------------
    stage.setTitle("Tree View Sample");

    //===========================================
    // ImainGoの実装クラスを作成 MGB = new MainGoB(this);
    // ボタンリストペインの作成 ButtonListPane freeBtn
    // freeBtn.getBtn("Id") 各ボタンを取り出す
    //============================================
    MGB = new MainGoB(this);
    freeBtn = new ButtonListPane(MGB); //★★★
    freeBtn.setDoBtn(new String[]{
      "append-B/新項目を追加", "remove-B/選択項目を削除", "closeAll-B0/下層をすべて閉じる",
      "closeAll-B1/下層をすべて閉じる", "toParent-B0/親へ", "toParent-B1/親へ",
      "fileTgl-B0/[ON] ファイルの表示","fileTgl-B1/[ON] ファイルの表示"
    });

    //========================
    // ImainGoの実装クラスを作成 MGB = new MainGoB(this);
    // フィールドリストペインの作成 FieldListPane freeTxt
    // freeTxt.getTxt("Id") 各テキストフィールドを取り出す
    // freeTxt.getLbl("Id") 各ラベルを取り出す
    //============================
    MG = new MainGo(this);
    //MGM = new MainGoB(this);
    freeTxt = new FieldListPane(MG); //★★★
    freeTxt.setDoTxt(new String[] {"nameB/名前"}); //TextField 一つ　ラベル一つ
    //============================================
    // ImainGoの実装クラスを作成 MG = new MainGo(this);
    // フィールドリストペインの作成 FieldListPane btnM
    // cheA.getTxt("Id") 各テキストフィールドを取り出す
    // cheA.getLbl("Id") 各ラベルを取り出す
    //=============================================
    //MG = new MainGo(this);
    cheA = new FieldListPane(MG);
    cheA.setDoTxt(new String[] {
      "name/名前 ", "kuni/  国名 ", "time/  時間 "
    });
    cheFlowA = cheA.FP; // FlowPaneを取り出す
    //==================================================
    // ImainGoの実装クラスを作成 MG = new MainGo(this);
    // ボタンリストペインの作成 ButtonListPane btnA
    // btnA.getBtn("Id") 各ボタンを取り出す
    //===================================================
    //MG = new MainGo(this);
    btnA = new ButtonListPane(MG); //ボタンリストペインの作成
    flowPaneA = btnA.FP; // FlowPaneを取り出す
    //アイテムの登録
    btnA.setDoBtn(new String[]{
      "stp-main/メイン(1)", "stp-tree1/ツリー(2)", "stp-tree2/ツリー(3)",
      "stp-tree3/ツリー(4)"
    });
    btnA.setDoBtn(new String[]{
      "out_pages/出力先: A-1", "wrap/折り返し表示 on", "cls/クリア"
    });
    flowPaneA.setPadding(new Insets(5, 5, 5, 5));
    flowPaneA.setHgap(5);
    //==============================================================
    // ImainGoの実装クラスを作成 MG = new MainGo(this);
    // コンテキストアイテムリストの作成 ContextItemList
    // CN.getMni("Id") 各メニューアイテムを取り出す
    //===============================================================
    // MG = new MainGo(this);
    CN = new ContextItemList(MG); //コンテキストアイテムリストの作成
    CNM = CN.CM; //コンテキストメニューの取り出し
    CN.setDoMni(new String[] { //アイテムの登録
      "P00/A-1","P01/A-2","P02/A-3","P03/A-4","P04/A-5"
    });
    //==============================================
    // ImainGoの実装クラスを作成 MGM = new MainGoM(this);
    // ボタンリストペインの作成 ButtonListPane btnM
    // btnM.getBtn("Id") 各ボタンを取り出す
    //==============================================
    MGM = new MainGoM(this); //ImainGo MGM
    btnM = new ButtonListPane(MGM); //★★★
    flowPaneM = btnM.FP; // FlowPaneを取り出す
    btnM.setDoBtn(new String[]{
      "test/drbツリーの作成", "test_del/drbツリーの削除", "drb_data/drb出力",
      "dot_data/dot出力", "0005/0005", "0006/0006",
      "0007/0007", "0008/0008", "0009/0009"
    });
    flowPaneM.setPadding(new Insets(5, 5, 5, 5));
    flowPaneM.setHgap(5);

    //============================
    //左側
    // Add the TreeView to the HBox
    //treeView.setMinWidth(300);
    treeView.setId("stp-tree2");

    mkTreeV(); //
    treeV.setId("stp-main");

    BorderPane border = getMenuBarBorder(); // STPの作成
    border.setMinWidth(400);

    TreeView tv =mkTvEmp(); tv.setId("stp-tree3");

    TreeView tv1 = mkTvFile(); tv1.setId("stp-tree1");

    STP.getChildren().addAll(tv, tv1, treeView, treeV);

    TP = new TabPane();
    TP.setMinWidth(400);
    Tab tab = new Tab();
    tab.setText("Main");
    tab.setContent(border);
    TP.getTabs().add(tab);

    for (int i = 1; i <= 5; i++) {
      tab = new Tab();
      tab.setText("T-" + Integer.toString(i));
      tab.setContent(new DrbTreeView());
      TP.getTabs().add(tab);
    }

    for (int i = 1; i <= 5; i++) {
      tab = new Tab();
      tab.setText("A-" + Integer.toString(i));
      tab.setContent(new TextArea());
      TP.getTabs().add(tab);
    }
////////////////////////////////////////////////////////////////////////
// ◇TabPane SelectionModel selectedItemProperty addListener
// イベントハンドラを実装したクラスのインスタンスを作成し返す関数 actionにそのインスタンスが入る
    ChangeListener<Tab> action = tabPaneAct();

    TP.getSelectionModel().selectedItemProperty().addListener(action);

    TP.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

    //=================================================
    VBox rightPane = getRightPane(); //右側
    HBox root = new HBox();//****************************
    root.setSpacing(5);
    root.getChildren().addAll(TP, rightPane);//***************************
    //setDisableTree();

    //textField = freeTxt.getTxt("nameB");
    TextField tf = freeTxt.getTxt("nameB");
    // ◇ TextField textProperty ChangedListener///////////////////////////
    ChangeListener<Object> tfcg = tfChange();
    tf.textProperty().addListener(tfcg);

    //tf.textProperty().addListener((observable, oldValue, newValue) -> {
    //  ppp("textField changed from " + oldValue + " to " + newValue);
    //});
    //treeView.getRoot().setExpanded(true);
    Scene scene = new Scene(root, 1060, 550);
    //scene.setFill(LIGHTGRAY);
    //TP.getSelectionModel().select(2);
    followSTP();
    stage.setScene(scene);
    stage.show();
  }
  ////////////////////////////////////////////////////////
////////////////////////////////////////////////////////
 /* ボタンインライン /////////////////////////
  button2.setOnAction(new EventHandler<ActionEvent>() {
    @Override public void handle(ActionEvent e) {
      label.setText("Accepted");
    }
  })
  */
  //====================
  // BorderPaneのTopにメニューを配置
  //=====================
  private BorderPane getMenuBarBorder(){

    MenuBar menuBar = new MenuBar();
    /////////////////////////////////////////////////////////////////
    // ◇MenuItem setOnAction ///////////////////////////////////
    // イベントハンドラを実装したクラスのインスタンスを作成し返す関数 actionにそのインスタンスが入る
    EventHandler<ActionEvent> action = menuBarAct();

    ///////"Direction"
    MNU1 = new Menu("Direction");
    MenuItem mi;
    mi = new MenuItem("Left"); mi.setId(mi.getText());
    mi.setOnAction(action);
    MNU1.getItems().add(mi);

    mi = new MenuItem("Right"); mi.setId(mi.getText());
    mi.setOnAction(action);
    MNU1.getItems().add(mi);

    mi = new MenuItem("Top"); mi.setId(mi.getText());
    mi.setOnAction(action);
    MNU1.getItems().add(mi);

    mi = new MenuItem("Bottom"); mi.setId(mi.getText());
    mi.setOnAction(action);
    MNU1.getItems().add(mi);
    //-----------
    menuBar.getMenus().add(MNU1);

    ///////"Select"
    MNU2 = new Menu("Select");

    mi = new MenuItem("Directory");
    mi.setId("stp-main");
    mi.setOnAction(action);
    MNU2.getItems().add(mi);

    mi = new MenuItem("TreeItem<FileX>");
    mi.setId("stp-tree1");
    mi.setOnAction(action);
    MNU2.getItems().add(mi);

    mi = new MenuItem("TreeItem<String>");
    mi.setId("stp-tree2");
    mi.setOnAction(action);
    MNU2.getItems().add(mi);

    mi = new MenuItem("Employees");
    mi.setId("stp-tree3");
    mi.setOnAction(action);
    MNU2.getItems().add(mi);
    //-----------
    menuBar.getMenus().add(MNU2);

    BorderPane borderPane = new BorderPane();

    //borderPane.prefHeightProperty().bind(scene.heightProperty());
    //borderPane.prefWidthProperty().bind(scene.widthProperty());

    borderPane.setTop(menuBar);
    STP = new StackPane();

    borderPane.setCenter(STP);

    return borderPane;

  }


/*
  private VBox getRightPane2()
  {
    */
/*Create the addItemBtn and its corresponding Event Handler
    Button addItemBtn = new Button("新項目を追加");
    addItemBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        addItem(textField.getText());
      }
    });
    *//*
   */
/* Create the removeItemBtn and its corresponding Event Handler
    Button removeItemBtn = new Button("選択項目を削除");
    removeItemBtn.setOnAction(new EventHandler<ActionEvent>()
    {
      @Override
      public void handle(ActionEvent event)
      {
        removeItem();
      }
    });
    *//*
    //Button addItemBtn = freeBtn.getBtn("append-B");
    //Button removeItemBtn = freeBtn.getBtn("remove-B");
    // Set the preferred number of text rows
    textArea.setPrefRowCount(20);
    // Set the preferred number of text columns
    textArea.setPrefColumnCount(48);
    // Create the HBox
    SP = new StackPane();
    FlowPane flp = new FlowPane(); flp.setId("stp-main");
    flp.getChildren().addAll(//fileTgl-B0
      freeBtn.getBtn("fileTgl-B0"), freeBtn.getBtn("toParent-B0"), freeBtn.getBtn("closeAll-B0"));
    SpList.set(3,flp); //SP.getChildren().add(flp);

    flp = new FlowPane(); flp.setId("stp-tree1");
    flp.getChildren().addAll(
      freeBtn.getBtn("fileTgl-B1"), freeBtn.getBtn("toParent-B1"), freeBtn.getBtn("closeAll-B1"));
    SpList.set(2,flp); //SP.getChildren().add(flp);
    Button addItemBtn = freeBtn.getBtn("append-B");
    Button removeItemBtn = freeBtn.getBtn("remove-B");
    flp = new FlowPane(); flp.setId("stp-tree2"); //TreeItem<FileX>
    flp.getChildren().addAll(new Label("項目:"), freeTxt.getTxt("nameB"), addItemBtn,
      new Label("   "), removeItemBtn); //freeTxt.getTxt("nameB") textField
    SpList.set(1,flp); //SP.getChildren().add(flp);

    flp = new FlowPane(); flp.setId("stp-tree3");
    SpList.set(0,flp); SP.getChildren().add(flp);

    for (FlowPane fp : SpList) {
      fp.setPadding(new Insets(5, 5, 5, 5));
      fp.setHgap(5);
    }

    // Add Children to the HBox
    //hbox.getChildren().addAll(new Label("項目:"), freeTxt.getTxt("nameB"), addItemBtn,
    //  new Label("   "), removeItemBtn); //freeTxt.getTxt("nameB") textField

    // Create the VBox "削除する項目と追加する新項目の親項目を選択してください"
    VBox vbox = new VBox();
    // Add children to the VBox 6ブロック追加
    vbox.getChildren().addAll(lblTitle, flowPaneM,
      flowPaneA, SP, cheFlowA, new Label("イベントメッセージの記録:"), textArea);
    // Set the vertical space between each child in the VBox
    vbox.setSpacing(2);
    textArea.setWrapText(false); MG.goCmd("wrap"); //go_wrap("wrap");//textArea.isWrapText --> true
    return vbox;

  }
*/

  //============
  // 右側VBoxの設定
  //============
  private VBox getRightPane()
  {
    // Create the HBox
    SP = new StackPane();
    FlowPane flp = new FlowPane(); flp.setId("stp-main");
    flp.getChildren().addAll(//fileTgl-B0
      freeBtn.getBtn("fileTgl-B0"), freeBtn.getBtn("toParent-B0"), freeBtn.getBtn("closeAll-B0"));
    SpList.set(3,flp); //SP.getChildren().add(flp);

    flp = new FlowPane(); flp.setId("stp-tree1");
    flp.getChildren().addAll(
      freeBtn.getBtn("fileTgl-B1"), freeBtn.getBtn("toParent-B1"), freeBtn.getBtn("closeAll-B1"));
    SpList.set(2,flp); //SP.getChildren().add(flp);


    Button addItemBtn = freeBtn.getBtn("append-B");
    Button removeItemBtn = freeBtn.getBtn("remove-B");
    flp = new FlowPane(); flp.setId("stp-tree2"); //TreeItem<FileX>
    flp.getChildren().addAll(new Label("項目:"), freeTxt.getTxt("nameB"), addItemBtn,
      new Label("   "), removeItemBtn); //freeTxt.getTxt("nameB") textField
    SpList.set(1,flp); //SP.getChildren().add(flp);

    flp = new FlowPane(); flp.setId("stp-tree3");
    SpList.set(0,flp); SP.getChildren().add(flp);

    for (FlowPane fp : SpList) {
      fp.setPadding(new Insets(5, 5, 5, 5));
      fp.setHgap(5);
    }

    VBox vbox = new VBox();
    vbox.getChildren().addAll(lblTitle, flowPaneM,
      flowPaneA, SP, cheFlowA, new Label("イベントメッセージの記録:"), textArea);
    vbox.setSpacing(2);
    textArea.setPrefRowCount(20);
    textArea.setPrefColumnCount(48);
    textArea.setWrapText(false); MG.goCmd("wrap"); //go_wrap("wrap");//textArea.isWrapText --> true
    return vbox;

  }


  /////////////////////////////////////////////////////////////////////////
// ◆treeView.setOnEditStart Commit Cancel ////////////////////////////////
// イベントハンドラを実装したクラスのインスタンスを作成し返す関数
  private EventHandler<TreeView.EditEvent<String>> treeItemEdit(){
    return new EventHandler<TreeView.EditEvent<String>>() {
      @Override
      public void handle(TreeView.EditEvent<String> event) {
        if (event.getEventType() == TreeView.<String>editStartEvent()){
          ppp("edit start"); return;
        }
        if (event.getEventType() == TreeView.<String>editCommitEvent()){
          ppp("edit commit"); return;
        }
        if (event.getEventType() == TreeView.<String>editCancelEvent()){
          ppp("edit cancel"); return;
        }

      }
    };
  }

  /////////////////////////////////////////////////////////////////////////////////////
// ◆MenuItem イベントハンドラを実装したクラスのインスタンスを作成し返す関数
  private EventHandler<ActionEvent> menuBarAct() {
    return new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        MenuItem mItem = (MenuItem) event.getSource();

        String s = mItem.getId();
        //ppp(s);
        if (("/Left/Right/Top/Bottom/").indexOf("/" + s + "/") >= 0){
          ppp(mItem.getText()); return;
        }
        if (("/stp-main/stp-tree1/stp-tree2/stp-tree3/").indexOf("/" + s + "/") >= 0){
          //ppp(s);
          MG.goCmd(s); return;
        }
        switch (s) {
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

      }
    };
  }
  ////////////////////////////////////////////////////////////////////////////////
// ◆TabPane イベントハンドラを実装したクラスのインスタンスを作成し返す関数
  private ChangeListener<Tab> tabPaneAct() {
    return new ChangeListener<Tab>() {
      @Override
      public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
        followSTP();
      }
    };
  }

/*
  //---------------インライン関数---------------------------
  TP.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
    System.out.println("Tab change: " + oldTab + "/" + newTab);
  });

  //---------------インライン関数---------------------------
   TP.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
     @Override
     public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
    }
   });
*/

  //////////////////////////////////////////////////////////////
// ◆TextField textProperty ChangedListener //////////////////
  private ChangeListener tfChange() {
    return new ChangeListener<Object>(){
      @Override
      public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {

        ppp(oldValue.toString());
      }
    };
  }

/////////////////////////////////////////////////////////////////////////////////




  //====================================================
//======= //freeBtn.getBtn("append-B")===================================
  public void addItem(String value){
    if (value == null || value.trim().equals("")){
      //this.ppp("Item cannot be empty.");
      return;
    }
    TreeItem<String> parent = treeView.getSelectionModel().getSelectedItem();
    if (parent == null){
      //this.ppp("Select a node to add this item to.");
      return;
    }
    // Check for duplicate
    for(TreeItem<String> child : parent.getChildren()){
      if (child.getValue().equals(value)){
        //this.ppp(value + " already exists under " + parent.getValue());
        return;
      }
    }
    TreeItem newItem = new TreeItem(value);
    parent.getChildren().add(newItem);
    if (!parent.isExpanded()){
      parent.setExpanded(true);
    }
  }
  // ------------//freeBtn.getBtn("remove-B")-----------------------------
  public void removeItem(){
    TreeItem item = treeView.getSelectionModel().getSelectedItem();
    if (item == null){
      //this.ppp("Select a node to remove.");
      return;
    }
    TreeItem parent = item.getParent();
    if (parent == null ){
      //this.ppp("Cannot remove the root node.");
    }else{
      parent.getChildren().remove(item);
    }
  }
  //====================================================
  public void ppp(String msg) {
    this.textArea.appendText(msg + "\n");
  }

  public void pppx(String s){
    char c;
    StringBuffer sb = new StringBuffer();
    for(int i = 0; i < s.length(); i++){
      c = s.charAt(i);
      if (c < 32){
        if (c <= 15)
          sb.append(("(0" + Integer.toHexString((int)c)) + ')');
        else
          sb.append(('(' + Integer.toHexString((int)c)) + ')');
      } else {
        sb.append(c);
      }
    }

    ppp(sb.toString());
  }

  //-------------------------------------------
  public void followSTP(){
    boolean bf = !lblTitle.getText().equals("-"); //題目の初期化が不要
    int size = STP.getChildren().size();
    Node n = STP.getChildren().get(size-1);//最後の追加が前面に出る
    Node fp0 = SP.getChildren().get(0);
    int k = TP.getSelectionModel().getSelectedIndex();
    if (k >= 6 || k == 0) {
      btnM.getBtn("test").setDisable(true);//text drb作成
    } else {
      btnM.getBtn("test").setDisable(false);
    }
    if (bf && (k >= 1)) {fp0.setDisable(true); flowPaneM.setDisable(false) ;return;}
    flowPaneM.setDisable(true);
    String s = n.getId();
    if(bf && SP.getChildren().get(0).getId().equals(s)) {fp0.setDisable(false); return;}
    for (FlowPane fp : SpList) {
      if (fp.getId().equals(s)){
        fp.setDisable(false);
        if (s.equals("stp-main")){
          setDspFile(dspFile0);
        } else if (s.equals("stp-tree1")){
          setDspFile(dspFile1);
        }
        SP.getChildren().set(0, fp); break;
      }
    }
    for (MenuItem mi : MNU2.getItems()) {
      if (mi.getId().equals(s)) {
        lblTitle.setText(mi.getText()); break;
      }
    }
    return;
  }

  //-------------------------------------------
  int setDspFile(int flg){
    if (flg != 0 && flg != 1){
      dspFile = dspFile != 0 ? 0 : 1;
    } else {
      dspFile = flg;
    }
    FileTreeItem.flgF = dspFile;
    String s = dspFile == 1 ? "[ON] ファイルの表示" : "[OFF] ファイルの表示";
    freeBtn.getBtn("fileTgl-B0").setText(s); freeBtn.getBtn("fileTgl-B1").setText(s);
    return dspFile;
  }





/*


  void setDisableTree(){
    int size = STP.getChildren().size();
    Node n = STP.getChildren().get(size-1);

    boolean bf = (TP.getSelectionModel().getSelectedIndex() == 0);

    if (bf && !(n.getId().equals("stp-tree1"))) bf = false;
    bf = !bf;
    freeBtn.getBtn("append-B").getParent().setDisable(bf);
    freeBtn.getBtn("remove-B").setDisable(bf);
    return;
  }

*/





  //---------------------------------------------
  public static class Employee {

    private final SimpleStringProperty name;
    private final SimpleStringProperty department;

    private Employee(String name, String department) {
      this.name = new SimpleStringProperty(name);
      this.department = new SimpleStringProperty(department);
    }

    public String getName() {
      return name.get();
    }

    public void setName(String fName) {
      name.set(fName);
    }

    public String getDepartment() {
      return department.get();
    }

    public void setDepartment(String fName) {
      department.set(fName);
    }
  }
  //------------------------------------------
  TreeView mkTvEmp() {
    List<Employee> employees = Arrays.<Employee>asList(
      new Employee("a1", "A"),
      new Employee("a2", "A"),
      new Employee("a3", "A"),
      new Employee("b1", "B"),
      new Employee("b2", "B"),
      new Employee("b3", "B"),
      new Employee("c1", "C"),
      new Employee("c2", "C"),
      new Employee("c3", "C"),
      new Employee("c4", "C"),
      new Employee("e1", "E"));

    TreeItem<String> rootNode = new TreeItem<String>("Root",rootIcon);
    rootNode.setExpanded(true);
    for (Employee employee : employees) {
      TreeItem<String> empLeaf = new TreeItem<String>(employee.getName());
      boolean found = false;
      for (TreeItem<String> depNode : rootNode.getChildren()) {
        if (depNode.getValue().contentEquals(employee.getDepartment())){
          depNode.getChildren().add(empLeaf);
          found = true;
          break;
        }
      }
      if (!found) {
        TreeItem depNode = new TreeItem(employee.getDepartment());
        rootNode.getChildren().add(depNode);
        depNode.getChildren().add(empLeaf);
      }
    }
    TreeView<String> treeView = new TreeView<String>(rootNode);
    treeView.setShowRoot(true);
    treeView.setEditable(true);
    return treeView;
  }

  //------------------------------------
  TreeView mkTvFile(){
    String s = "/home";
    if (iswin) s = "C:/";
    //TreeItem<FileTreeItem.FileX> root =  FileTreeItem.createN(new FileTreeItem.FileX(new File(s)));
    TreeItem<FileTreeItem.FileX> root = new FileTreeItem(new FileTreeItem.FileX(new File(s)));
    TreeView tv = new TreeView<FileTreeItem.FileX>(root);
    return tv;
  }

  //-------------------------------------
  void mkTreeV() {
    FileTreeItem rootNode = new FileTreeItem(null);

    File[] rootFiles = File.listRoots();

    for(File f : rootFiles){
      rootNode.getChildren().add(new FileTreeItem(new FileTreeItem.FileX(f)));
    }
    treeV.setRoot(rootNode);
    treeV.setShowRoot(false);
    FileTreeItem.MA = this;
    FileTreeItem.ItemCollapsed.MA = this;
  }

}