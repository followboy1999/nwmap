import java.awt.*;  

import javax.swing.*;  
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import java.awt.event.*;  
import java.io.*;  
import java.util.Vector;

 


public class nwmap extends JFrame  
{  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JTextArea editor;  
    private Container c;  
    private Font f=new Font("sanserif",Font.PLAIN,12); 
    
    //菜单栏声明  
    private JMenuBar mb;        
    private JMenu fileMenu;     //文件菜单  
    private JMenu helpMenu;     //help菜单  
    private JMenuItem fileMenuOpen,fileMenuExit;   //文件菜单的菜单项  
    private JMenuItem helpMenuAbout;   //Help菜单的菜单项  
   
  
    //工具栏成员属性声明  
    private JToolBar toolBar;   //工具栏
    private JButton b2,b5,b6; //toolbar声明3个按钮，分别为：“打开”、“分析”、“绘图”  
      
    //弹出式菜单属性声明  
    private JPopupMenu nodePMenu;  
    private JMenuItem item1,item2,item4,item5,item6,item7;  
    
    private JPanel jsonPanel;
    private static JTree projectTree;
    DefaultTreeModel model; 
    public Vector<String> data = new Vector<String>();
    public static String outputPath = "F:/xampp/tomcat/webapps/ROOT/attackmap/";
      
  
  
    public nwmap()  
    {  
        super("Attack Mapping by zzt001");  
        setSize(800,600);  
        setResizable(false);
        
        //在屏幕中间显示   
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); 
        setLocation((dim.width - getWidth()) / 2, (dim.height - getHeight()) / 2);
        
        try{  
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  
        } catch (Exception e){ System.err.println("reason:"+e);}  
          
  
  
        c=getContentPane();         //创建一个内容面板  
        editor = new JTextArea();   //创建一个文本区  
        
        //设置滚动条，并添加到内容面板,显示json        
        jsonPanel = new JPanel();
        jsonPanel.setLayout(new BorderLayout());
        jsonPanel.setPreferredSize(new Dimension(500, 0));
        jsonPanel.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        jsonPanel.add(new JScrollPane(editor),BorderLayout.CENTER);        
        c.add(jsonPanel,BorderLayout.EAST);
               
        
        //弹出菜单的实现  
        nodePMenu =new JPopupMenu();   //创建弹出菜单  
        item1 = new JMenuItem("New Node");  
        item2 = new JMenuItem("Child Node");  
        item4 = new JMenuItem("Import File");  
        item5 = new JMenuItem("Anaylyse");  
        item6 = new JMenuItem("Print");
        item7 = new JMenuItem("Delete");
        
        JPHandler JP = new JPHandler();  
        item1.addActionListener(JP);  //注册菜单项的鼠标事件监听器  
        item2.addActionListener(JP);   
        item4.addActionListener(JP);  
        item5.addActionListener(JP);
        item6.addActionListener(JP);
        item7.addActionListener(JP);
        
        /*
        nodePMenu.add(item1);  nodePMenu.add(item2);  
        nodePMenu.add(item3);  nodePMenu.add(item4);  
        nodePMenu.add(item5);  nodePMenu.add(item6);
        */
        
        //tree
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Project Tree");
        DefaultMutableTreeNode node1 = new DefaultMutableTreeNode("Company Name");
        DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("Unit Name");
        DefaultMutableTreeNode node3 = new DefaultMutableTreeNode("Access Point");
        root.add(node1);
        node1.add(node2);
        node1.add(node3);
        projectTree = new JTree(root);
        projectTree.setFont(new Font("Dialog", Font.PLAIN, 12));
        projectTree.setPreferredSize(new Dimension(290,0));
        projectTree.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        
        model = (DefaultTreeModel)projectTree.getModel(); //获取JTree对应的TreeModel对象 
        //设置JTree可编辑  
        projectTree.setEditable(true); 
        c.add(projectTree, BorderLayout.WEST);

        
        projectTree.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {//是否为右键
            		nodePMenu.add(item1);          	
            		nodePMenu.add(item2);  
//                    nodePMenu.add(item3);
                    nodePMenu.add(item4);
            		nodePMenu.add(item5);  
            		nodePMenu.add(item6);
            		nodePMenu.add(item7);              	              	
                	nodePMenu.show(projectTree, e.getX(), e.getY()); //treeNodeMenu就是定义的弹出工菜单
                }
            }});
       
        mb = new JMenuBar();    //创建菜单栏
        fileMenu = new JMenu("File");//创建菜单  
        fileMenuOpen = new JMenuItem("Open");         
        fileMenuExit = new JMenuItem("Exit"); 
        
        JMHandler JM = new JMHandler();
        fileMenuOpen.addActionListener(JM);
        fileMenuExit.addActionListener(JM);
        
        
        //控件添加到menu
        fileMenu.add(fileMenuOpen);             
        fileMenu.addSeparator();    //添加分隔线  
        fileMenu.add(fileMenuExit);  
        fileMenu.setFont(f);        //设置菜单中文体的字体  
  
          
        //Help菜单的实现  
        helpMenu = new JMenu("Help");  
        helpMenuAbout = new JMenuItem("About");  
        helpMenuAbout.addActionListener(JM);

        helpMenu.add(helpMenuAbout);  
        helpMenu.setFont(f);  
  
        //将菜单全部添加菜单lan  
        mb.add(fileMenu);  
        mb.add(helpMenu);  

  
        //工具栏的实现  
        toolBar =new JToolBar();    //创建工具棒  

        b2= new JButton(new ImageIcon("img/open.png"));    
        b5= new JButton(new ImageIcon("img/analyse.png"));
        b6= new JButton(new ImageIcon("img/print.png"));

        b2.setToolTipText("Open Json File");
        b5.setToolTipText("Analyse All Connections");
        b6.setToolTipText("Draw A Picture");
        
        JBHandler JB = new JBHandler();
        b2.addActionListener(JB);
        b5.addActionListener(JB);
        b6.addActionListener(JB);
        toolBar.setPreferredSize(new Dimension(40, 40));

        //把按钮全部添加到工具棒中  
        toolBar.add(b2);   
        toolBar.add(b5);    toolBar.add(b6);
          
        //把菜单栏、工具栏、弹出菜单添加到内容面板  
        setJMenuBar(mb);        //显示菜单栏  
        c.add(toolBar,BorderLayout.NORTH);         
  
        setVisible(true);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    }  
   
    //自定义类实现弹出式菜单的事件处理  
    private class JPHandler implements ActionListener  
    {  
        public void actionPerformed(ActionEvent e)  
        {  
            if(e.getSource()==item1)//new node
            {
            	Addnode();
            }  
            else if(e.getSource()==item2)//添加child节点
            {
            	Addchildnode();
            }   
            else if(e.getSource()==item4) //导入文件 
            {  
	            ImportFile();                              	
            }  
            else if(e.getSource()==item5)//分析
            { 
                AnalyseData();
            }  
            else if(e.getSource()==item6)//绘图
            {
                //open json file to editor here 
            	Print();
            }
            else if(e.getSource()==item7)//delete
            {
            	Delnode();
            }  
            }
        }
    
    
    public void Addnode(){//添加平级节点
    	
    	String nodeName = JOptionPane.showInputDialog(null, "Name:","");
    	if(nodeName.isEmpty()){
    		return;
    	}else{
    	
    	//获取选中节点  
        DefaultMutableTreeNode selectedNode  
            = (DefaultMutableTreeNode) projectTree.getLastSelectedPathComponent();  

        //如果节点为空，直接返回  
        if (selectedNode == null) return;  
        //获取该选中节点的父节点  
        DefaultMutableTreeNode parent  
            = (DefaultMutableTreeNode)selectedNode.getParent();  
        //如果父节点为空，直接返回  
        if (parent == null) return;  
        //创建一个新节点  
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(nodeName);  
        //获取选中节点的选中索引  
        int selectedIndex = parent.getIndex(selectedNode);  
        //在选中位置插入新节点  
        model.insertNodeInto(newNode, parent, selectedIndex + 1);  
        //--------下面代码实现显示新节点（自动展开父节点）-------  
        //获取从根节点到新节点的所有节点  
        TreeNode[] nodes = model.getPathToRoot(newNode);  
        //使用指定的节点数组来创建TreePath  
        TreePath path = new TreePath(nodes);  
        //显示指定TreePath  
        projectTree.scrollPathToVisible(path);
    	}
    }
    
    public void Delnode(){
    	DefaultMutableTreeNode selectedNode  
        = (DefaultMutableTreeNode) projectTree.getLastSelectedPathComponent();  
	    if (selectedNode != null && selectedNode.getParent() != null)  
	    {  
	        //删除指定节点  
	        model.removeNodeFromParent(selectedNode); 
	        //需要考虑前边添加的标志符（+ * 对勾）
	        String str = selectedNode.getUserObject().toString().replace("+", "").replace("*", "").replace("√", "");
			if( data.indexOf(str) != -1){
		        data.removeElement(str);
		        data.removeElementAt(data.indexOf(str)+1);
			}
	    }//删除节点
	    
	    
    }
    
    public void Addchildnode(){//添加子节点
    	
    	String nodeName = JOptionPane.showInputDialog(null, "Name:","");
    	if(nodeName.isEmpty()){
    		return;
    	}else{
    	
    	 //获取选中节点  
        DefaultMutableTreeNode selectedNode  
            = (DefaultMutableTreeNode) projectTree.getLastSelectedPathComponent();  
        //如果节点为空，直接返回  
        if (selectedNode == null) return;  
        //创建一个新节点  
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(nodeName);  
        
        //直接通过model来添加新节点，则无需通过调用JTree的updateUI方法  
        //model.insertNodeInto(newNode, selectedNode, selectedNode.getChildCount());  
        //直接通过节点添加新节点，则需要调用tree的updateUI方法  
        selectedNode.add(newNode);  
        //--------下面代码实现显示新节点（自动展开父节点）-------  
        TreeNode[] nodes = model.getPathToRoot(newNode);  
        TreePath path = new TreePath(nodes);  
        projectTree.scrollPathToVisible(path);  
        projectTree.updateUI(); 
        
    	}
    }
    
    
  //自定义类实现菜单的事件处理  
    private class JMHandler implements ActionListener  
    {  
    	//fileMenuNew,fileMenuOpen,fileMenuImport,fileMenuExport,fileMenuExit
        public void actionPerformed(ActionEvent e)  
        {  
  
            if(e.getSource()==fileMenuOpen)//打开json
            {
            	OpenJson();
            }   
            else if(e.getSource()==fileMenuExit)
            {
            	System.exit(1);
            }  
            else if(e.getSource()==helpMenuAbout)
            {
            	JOptionPane.showMessageDialog(null, "Attack Mapping v1.0 by zzt001");
            }
            
        }
    }
    
  //自定义类实现toolbar的事件处理  
    private class JBHandler implements ActionListener  
    {  //b1,b2,b3,b4,b5,b6
        public void actionPerformed(ActionEvent e)  
        {  
        	if(e.getSource()==b2)//打开json
            {
            	OpenJson();
            } //  	   
            else if(e.getSource()==b5)//分析
            {  
            	AnalyseTotal();
            }  
            else if(e.getSource()==b6)//绘图
            {
            	//open json file to editor
            	PrintTotal();
            }
            
        }
    }
    
    public static void PrintTotal(){}//
    
    public static void Print()//需要添加参数绘制那个json关系图
    {
    	
    	DefaultMutableTreeNode selectedNode  
        = (DefaultMutableTreeNode) projectTree.getLastSelectedPathComponent();  
        Object userobject=selectedNode.getUserObject();
        if(userobject.toString().contains("+") && userobject.toString().contains("*"))
        {
            if(userobject.toString().contains("√"))
            {
            	JOptionPane.showMessageDialog(null, "Already Printed!!!"); 
            }
            else{
                selectedNode.setUserObject("√"+userobject.toString());
                String apName = userobject.toString().replace("+", "").replace("*", "").replace("√", "");//获得节点名并去除标志符,即节点名称
                //print data here
                String htmlData = "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GB2312\" />" +  
				"<style type=\"text/css\">"+
				".node {"+
				  "stroke: #fff;"+
				  "stroke-width: 1.5px;"+
				  "cursor:pointer;"+
				"}"+
				
				".nodetext {"+
				  "fill: #000;"+
				  "font-size:12px;"+
				  "cursor:pointer;"+
				  "pointer-events:none;"+
				 "}"+
				"</style>"+
				"<body>"+
				"<script src=\"d3.js\"></script>"+
				 "<div class=\"codeBox\">"+
					"<div class=\"codeArea\" id=\"img\">"+
					"<p><b><font color=\"#FF6600\">攻击路径分析示例</font></b></p>"+
					"<font color=\"#0099FF\">"+
					"<p>力学参数：width:800 height:600 charge:-150 linkDistance:200 </p>"+
					"<p>交互性：鼠标悬停在节点上显示名称，节点可拖拽。</p>"+
					"</font>"+
				
				"<script type=\"text/javascript\">"+
					"var width = 800;"+
					"var height = 600;"+
					
					"//取得20个颜色的序列"+"\n"+
					"var color = d3.scale.category20();"+
					
					"//定义画布"+"\n"+
					"var svg = d3.select(\"body\").append(\"svg\")"+
						".attr(\"width\", width)"+
						".attr(\"height\", height);"+
					
					"//定义力学结构"+"\n"+
					"var force = d3.layout.force()"+
						".charge(-150)"+
						".linkDistance(200)"+
						".size([width, height]);"+
					
					"//读取数据"+"\n"+
					"d3.json(\""+apName+".json"+"\", function(error, graph) {"+
					  "force"+
						  ".nodes(graph.nodes)"+
						  ".links(graph.links)"+
						  ".start();"+
					  
					"//定义连线"+"\n"+
					"var link = svg.selectAll(\".link\")"+
					  ".data(graph.links)"+
					  ".enter()"+
					  ".append(\"line\")"+
					  ".attr(\"class\", \"link\")"+
					  ".attr(\"stroke\",\"#09F\")"+
					  ".attr(\"stroke-opacity\",\"0.4\")"+
					  ".style(\"stroke-width\",1);"+
					  
					"//定义节点标记"+"\n"+
					"var node = svg.selectAll(\".node\")"+
					  ".data(graph.nodes)"+
					  ".enter()"+
					  ".append(\"g\")"+
					  ".call(force.drag);"+
					
					"//节点圆形标记"+"\n"+
					"node.append(\"circle\")"+
					  ".attr(\"class\", \"node\")"+
					  ".attr(\"r\",function(d){return 10+d.group;})"+
					  ".style(\"fill\", function(d) { return color(d.group); });"+
					
					"//标记鼠标悬停的标签"+"\n"+
					"node.append(\"title\")"+
					  ".text(function(d) { return d.name; });"+
					
					
					"//节点上显示的姓名"+"\n"+
					"node.append(\"text\")"+
				"//	  .attr(\"dy\", \".3em\")"+"\n"+	
					  ".attr(\"class\",\"nodetext\")"+
				"//	  .style(\"text-anchor\", \"middle\")"+"\n"+
				      ".attr(\"dx\", 15)"+
				      ".attr(\"dy\", -15)"+
					  ".text(function(d) { return d.name; });"+
				"//	  .text(\"业支\");"+"\n"+
					
					"//开始力学动作"+"\n"+
					"force.on(\"tick\", function() {"+
						"link.attr(\"x1\", function(d) { return d.source.x; })"+
							".attr(\"y1\", function(d) { return d.source.y; })"+
							".attr(\"x2\", function(d) { return d.target.x; })"+
							".attr(\"y2\", function(d) { return d.target.y; });"+
						
						"node.attr(\"transform\", function(d){ return \"translate(\"+d.x+\",\" + d.y + \")\";});"+
					"});"+
					"});"+
							
				"</script>"+
				"</div>"+
				"</div>"+
				"</body>"; 
                
               //写入html文件
            	try {
					FileWriter jsonfile = new FileWriter(outputPath+apName+".html");
					jsonfile.write(htmlData);
					jsonfile.close();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
                String cmd = "cmd.exe /c F:/Google/Chrome/Application/chrome.exe localhost:8080/attackmap/"+apName+".html";
            	Runtime run = Runtime.getRuntime();  
                try {  
                    Process p = run.exec(cmd);  
                    BufferedInputStream in = new BufferedInputStream(p.getInputStream());  
                    BufferedReader inBr = new BufferedReader(new InputStreamReader(in));  
                    String lineStr;  
                    while ((lineStr = inBr.readLine()) != null)                    
                        System.out.println(lineStr);            
                    if (p.waitFor() != 0) {  
                        if (p.exitValue() == 1)//p.exitValue()==0表示正常结束，1：非正常结束  
                            System.err.println("exec failed!");  
                    }  
                    inBr.close();  
                    in.close();  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }
        }
        else
        {
        	JOptionPane.showMessageDialog(null, "Please Import And Analyse First!!!"); 
        }
    	
    }
    class JsonFileFilter extends FileFilter {    
        public String getDescription() {    
            return "*.json";    
        }    
        
        public boolean accept(File file) {    
            String name = file.getName();    
            return file.isDirectory() || name.toLowerCase().endsWith(".json");
        }    
    }    
    class TxtFileFilter extends FileFilter {    
        public String getDescription() {    
            return "*.txt";    
        }    
        
        public boolean accept(File file) {    
            String name = file.getName();    
            return file.isDirectory() || name.toLowerCase().endsWith(".txt");
        }    
    }  
    public void OpenJson()  
    {
    	
        JFileChooser fc=new JFileChooser();     
        JsonFileFilter jsonFilter = new JsonFileFilter(); //json过滤器    
        fc.addChoosableFileFilter(jsonFilter);  
        fc.setFileFilter(jsonFilter);
        
        int r=fc.showOpenDialog(this);  
        if(r==JFileChooser.APPROVE_OPTION)  
        {  
            File file=fc.getSelectedFile();  
            try{ editor.read(new FileReader(file),null);}  
            catch(IOException e){}  
        } 
    }
    public void ImportFile(){
    	
        DefaultMutableTreeNode selectedNode  
        = (DefaultMutableTreeNode) projectTree.getLastSelectedPathComponent();  
        Object userobject=selectedNode.getUserObject();
        if(userobject.toString().contains("+"))
        {
        	JOptionPane.showMessageDialog(null, "Already Imported!!!"); 
        }
        else{

            //import file here
           	JFileChooser fc=new JFileChooser(); 
            TxtFileFilter txtFilter = new TxtFileFilter();
            fc.addChoosableFileFilter(txtFilter);  
            fc.setFileFilter(txtFilter);
        	int r=fc.showOpenDialog(this);  
            if(r==JFileChooser.APPROVE_OPTION)  
            {  
                File file=fc.getSelectedFile();                 
                System.out.print(file.getName());
                
                //添加到vector数组中 ,以便后期的比较使用
                data.addElement(userobject.toString());
                data.addElement(file.getAbsoluteFile().toString());//需要绝对路径
               
                //最后 添加标记 ，以免污染vector数组 
                selectedNode.setUserObject("+"+userobject.toString());
      
            } 
        }
 
    }
    public void AnalyseTotal(){}
    public void AnalyseData(){//现只针对ap节点进行分析，因此获得的节点名应为ap节点名
    	BufferedReader br = null;
    	DefaultMutableTreeNode selectedNode  
        = (DefaultMutableTreeNode) projectTree.getLastSelectedPathComponent();  
        Object userobject=selectedNode.getUserObject();
        if(userobject.toString().contains("+"))
        {                	                    
            if(userobject.toString().contains("*"))
            {
            	JOptionPane.showMessageDialog(null, "Already Analysed!!!"); 
            }
            else{ 
            	selectedNode.setUserObject("*"+userobject.toString()); //添加标志            	
            	String nodeName = userobject.toString().replace("+", "").replace("*", "");//获得节点名并去除标志符，即AP名称
				
				//初始化json数据结构
				String jsonNodeData = "{" + "\n" +
						"\"nodes\":[" + "\n" +
						"{\"name\":\"" + nodeName +"\",\"group\":1}," + "\n";//写入所选ap的名称				
				String jsonLinkData = "\"links\":[" + "\n"; //连接关系
               
              //analyse data here
                String nodeFilename = data.elementAt(data.indexOf(nodeName)+1);//获得该节点的导入的文件名                
                for(int i=0; i < data.size(); i+=2){//遍历所有vector向量，即获得所有导入的文件以便进行数据分析
//                	if(userobject.toString().contains(data.elementAt(i).toString())){////vector中的nodeName无添加的标志符,可以直接比较,如果选中的note与vector中的元素相等，则不比较
            		if(data.elementAt(i).toLowerCase().contains("ap")){
                		continue;//暂时过滤所有的AP节点
                	}else{
                		//是否存在连接标志位
                		boolean isConnected = false;
                		//所选的ap节点与所有单元数据进行比较
                		String unitName = data.elementAt(i);
                		File compareFile = new File(data.elementAt(i+1));//获得导入的单元数据
                        FileReader reader = null;

						try {
							reader = new FileReader(compareFile);
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                        int fileLen = (int)compareFile.length();
                        char[] chars = new char[fileLen];
                        try {
							reader.read(chars);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}//一次性读出所有内容
                        String filedata = String.valueOf(chars);//转化为string
                        try {
							reader.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                        
                        File nodeFile = new File(nodeFilename);//ap节点的文件
                        String line = null;
                        try {
							br = new BufferedReader(new FileReader(nodeFile));
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
            			try {
							while ((line = br.readLine())!=null) {//每次读出一行，使用一行与目标文件的所有内容比较
								if(filedata.contains(line)){//data为unit数据，line为ap每行数据
									//unit中数据包含ap中的数据，
									//表明存在同样的ip后，表明存在连接关系
									//找到所有相同的ip备用（暂时未实现），现直接写入link信息后，break;
									isConnected = true;
									break;
								}
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						jsonNodeData += "{\"name\":\"" + unitName + "\",\"group\":2}," + "\n";//写入unit名称
						if(isConnected){//存在连接关系才能写入填充json node 和 links                			                			
                			jsonLinkData += "{\"source\":\"" + nodeName + "\",\"target\":\"" + unitName + "\",\"value\":1}," + "\n";	                		
						}else{
							jsonLinkData += "{}," + "\n";
						}
                		
                	}
                }
                
                
                //jsonNodeData去掉最后的逗号和回车，添加“],”
    			jsonNodeData = jsonNodeData.substring(0, jsonNodeData.length()-2) + "\n" + "],";
    			//jsonNodeData去掉最后的逗号和回车，添加“]}”
    			jsonLinkData = jsonLinkData.substring(0, jsonLinkData.length()-2) + "\n" + "]" + "\n" + "}";
              
    			
    			//所有分析完成，将数据写入json文件
            	try {
					FileWriter jsonfile = new FileWriter(outputPath+nodeName+".json");
					jsonfile.write(jsonNodeData+jsonLinkData);
					jsonfile.close();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
	            try{ editor.read(new FileReader(outputPath+nodeName+".json"),null);}  
	            catch(IOException e){}  
    			
            }
        	
        }else
        {
        	JOptionPane.showMessageDialog(null, "Aplease Import First!!!"); 
        }
    }
  
    public static void Analyse()
    {}
    public static void main(String []args)  
    {  
        new nwmap();  
    }  
          
}  