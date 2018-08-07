package sample;

//*||FileTreeItem.java
//JavaFX TreeViewとTreeItem-------------------------------
//||http://mklearning.blogspot.jp/2013/01/javafx-treeviewtreeitem.html
//TreeItemを開くときと閉じるときのイベント処理------------
//||http://mklearning.blogspot.jp/2013/01/treeitem.html
//java.io.Fileを継承--------------------------------------
//||https://detail.chiebukuro.yahoo.co.jp/qa/question_detail/q1373692063
//JavaFX TreeView Example---------------------------------
//||https://examples.javacodegeeks.com/core-java/javafx-treeview-example/

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;


import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;

// ファイルツリーの根や分岐や葉を表すクラス
// 根は値を持たないものとする
public class FileTreeItem extends TreeItem<FileTreeItem.FileX> {
  static int flgF = 0; //１だったら、ファイルも表示
  static int flgN = 5000; //フォルダの展開に子ファイル数によって制限をかける
  static Main MA = null;
  static boolean iswin = System.getProperty("os.name").toLowerCase().startsWith("windows");

//  public FileTreeItem() { //MainクラスのインスタンスからrootItemを作るとき呼ばれる
//    this(null);
//  }

  public FileTreeItem(final FileX file) { //MainクラスのインスタンスからrootItemを作るときnullを使う
    // 要素の値を設定
    super(file);
    //if (file != null && file.isDirectory()) super.setGraphic(ND);
    ItemCollapsed hn = new ItemCollapsed();
    addEventHandler(TreeItem.<FileX> branchCollapsedEvent(), hn);
    addEventHandler(TreeItem.<FileX> branchExpandedEvent(), hn);
  }

//  public FileTreeItem(final FileX file, Node nd) {
//    super(file, nd);
//    addEventHandler(TreeItem.<FileX>branchCollapsedEvent(), new ItemCollapsed());
//  }

  public FileFilter filter1 = new FileFilter(){
    public boolean accept(File file){
      if (!file.isFile()) return true;
      return false;
    }
  };

  FilenameFilter filter2 = new FilenameFilter() {
    @Override
    public boolean accept(File dir, String name) {
      String s = name + "\n";
      if(s.indexOf(".exe\n") >= 0 ) {
        return true;
      } else {
        return false;
      }
    }
  };

  // 木の要素が葉かどうか(末端かどうか)を返す
  // 要素が持つ値は根の場合はnullで，
  // それ以外では通常のFileオブジェクトになる
  @Override
  public boolean isLeaf() {
    FileX file = getValue();
    if (file == null) return false; //ルートのみnullになっている
    return (file.leaf != 0);
    //return file != null && file.isFile();
  }
/* この処理はbranchExpandedEventのハンドラで処理している ItemCollapsed.java
  //@Override
  public ObservableList<TreeItem<FileX>> getChildren() {
    // 既に一度子要素を作っている場合はそのキャッシュを利用する
    ObservableList<TreeItem<FileX>> children = super.getChildren();
    if (!children.isEmpty())
      return children;

    // 根にファイルを追加する前に通る
    File file = super.getValue();
    if (file == null)
      return children;

    // ファイルの場合
    if (!file.isDirectory()) {
      return children;
      //return FXCollections.emptyObservableList();
    }

    // ディレクトリの場合
    File[] files;
    //----------------------------
    if (flgF == 1) {files = file.listFiles();}else {files = file.listFiles(filter1);}
    //--------------
    // (flgF == 0)
    //-----------------------------
    int flg = 0;
    if (files == null){flg = 1;} //System.out.println("not access");
    if (flg == 0 && files.length > 5000){flg = 1;} //System.out.println(file.getName() + files.length);
    if (flg == 1) {
      System.out.println(children.size());return children;
    }
    for (File f : files) {
      children.add(new FileTreeItem(new FileX(f)));
      MA.ppp(f.getAbsolutePath());
    }
    return children;
  }
*/

  static public TreeItem<FileX> createN(final FileX f) { //
    return new TreeItem<FileX>(f) {
/*
      static int flgF = 1; //１だったら、ファイルも表示
      static int flgN = 5000; //フォルダの展開に子ファイル数によって制限をかける
      static Main MA = null;
*/

      private boolean isLeaf;
      private boolean isFirstTimeChildren = true;
      private boolean isFirstTimeLeaf = true;

      @Override public ObservableList<TreeItem<FileX>> getChildren() {
        if (isFirstTimeChildren) {
          isFirstTimeChildren = false; if (MA != null) MA.ppp("開く " + this.toString());
          super.getChildren().setAll(buildChildren(this));
        }
        return super.getChildren();
      }

      @Override public boolean isLeaf() {
        if (isFirstTimeLeaf) {
          isFirstTimeLeaf = false;
          FileX f = (FileX) getValue();
          //isLeaf = f.isFile();
          isLeaf = (f.leaf != 0);
        }
        return isLeaf;
      }

/*
      private ObservableList<TreeItem<FileX>> buildChildren(TreeItem<FileX> TreeItem) {
        File f = TreeItem.getValue();
        if (f != null && f.isDirectory()) {
          File[] files = f.listFiles();
          if (files != null) {
            ObservableList<TreeItem<FileX>> children = FXCollections.observableArrayList();
            for (File childFile : files) {
              children.add(FileTreeItem.createN(new FileX(childFile)));
            }
            return children;
          }
        }
        return FXCollections.emptyObservableList();
      }
*/


      private ObservableList<TreeItem<FileX>> buildChildren(TreeItem<FileX> TreeItem) {
        FileX f = TreeItem.getValue();
        if (f != null && f.leaf == 0) {
          File[] files;
          if (FileTreeItem.flgF == 1) { //********************************
            files = f.listFiles(); //全てのフォルダとファイル
          } else {
            files = f.listFiles(filter1); //*****************
          }
          if (files != null) {
            ObservableList<TreeItem<FileX>> children = FXCollections.observableArrayList();
            for (File childFile : files) {
              children.add(FileTreeItem.createN(new FileX(childFile)));
            }
            return children;
          }
        }
        return FXCollections.emptyObservableList();
      }


      public FileFilter filter1 = new FileFilter(){ // classインスタンス
        public boolean accept(File file){
          if (!file.isFile()) return true;
          return false;
        }
      };
    };
  } //メソッド createN


  static public class ItemCollapsed
    implements EventHandler<TreeModificationEvent<FileX>>
  {
    static Main MA = null;
    static String PT = "";
    public void handle(TreeItem.TreeModificationEvent<FileX> event) {
      if(event.getEventType() == TreeItem.<FileX>branchExpandedEvent()) {
        //System.out.println("expanded");
        TreeItem<FileX> tr = event.getTreeItem(); //***********************
        if (event.getSource().getValue() != null) {
          String s = event.getSource().getValue().getAbsolutePath();
          if (tr.isLeaf()) return;
          if (tr.getChildren().size() >= 1) return;
          File[] fs;
          if (FileTreeItem.flgF == 1){ //********************************
            fs = tr.getValue().listFiles(); //全てのフォルダとファイル
          } else {
            fs = tr.getValue().listFiles(((FileTreeItem)tr).filter1);
          }
          // ここで並び替え
          // java.io.File[] をファイル名でソートする
          // http://d.hatena.ne.jp/shammer/20120822/p1
          java.util.Arrays.sort(fs, new java.util.Comparator<File>() {
            public int compare(File file1, File file2){
              return file1.getName().compareToIgnoreCase(file2.getName());
            }
          });
          for (File f : fs) {
            if (!f.isFile())
              tr.getChildren().add(new FileTreeItem(new FileX(f)));
          }
          for (File f : fs) {
            if (f.isFile())
              tr.getChildren().add(new FileTreeItem(new FileX(f)));
          }
          //PT = s;
          if (MA != null) MA.ppp("開く " + s);
          //event.consume();
        }
        return;
      }

      if(event.getEventType() == TreeItem.<FileX>branchCollapsedEvent()) {
        TreeItem<FileX> tr = event.getTreeItem();
        ObservableList<TreeItem<FileX>> children = tr.getChildren();
        if (children != null && !children.isEmpty()){
          children.remove(0, children.size());
          event.consume();
        }
        if (event.getSource().getValue() != null) {
          if (MA != null) MA.ppp("閉じる " + event.getSource().getValue().getAbsolutePath());
        }
      }
    }
  }


  static public class FileX extends File {
    int leaf = 0; // 2はファイル  3はアクセス権なし  4は直下の子ファイル数が多すぎる

    //public FileX(String pathname) {
    //  super(pathname);
    //}

    public FileX(File file){
      super(file.getAbsolutePath());

      for(;;) {
        if (file == null) {leaf = 0; break;} //ルートのみ
        if (file.isFile()) {leaf = 2; break;}
        File[] files = file.listFiles();
        if (files == null) {leaf = 3; break;} //アクセス権なし
        if (files.length > FileTreeItem.flgN) {leaf = 4; break;} //直下の子ファイル数が多すぎる
        if (FileTreeItem.flgF == 1){ //****************************************** leaf == 1 ファイルがあれば表示展開の対象

          if (files.length == 0) leaf = 1;// 子のないフォルダはleafが非ゼロになり、展開マークをつけない

        }else{// ファイルだけで構成されているフォルダは展開マークをつけない

          int flg = 0;
          for (File f : file.listFiles()) {
            if (f.isDirectory()) {
              flg = 1;
              break;
            }
          }
          if (flg == 0) leaf = 1;
        }
        break;
      }

    }

    @Override
    public String toString() {
      String s = super.toString();
      String s1;
      if (leaf >= 2) {
        if (leaf == 4) s1 = "[L] "; else s1 = (leaf == 2) ? "[f] " : "[N] ";
      } else{
        s1 = "";
      }
      if (s.lastIndexOf('\\') >= 1 && s.length() == 3) return s;
      int k = s.lastIndexOf('\\');
      if (k >= 0) return s1 + s.substring(k + 1);
      if (!iswin) {
        if (s.length() >= 2) {
          k = s.lastIndexOf('/');
          s = s.substring(k + 1);
        }
      }
      return s1 + s;
    }

  }




}
