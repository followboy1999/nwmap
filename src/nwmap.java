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
    
    //�˵�������  
    private JMenuBar mb;        
    private JMenu fileMenu;     //�ļ��˵�  
    private JMenu helpMenu;     //help�˵�  
    private JMenuItem fileMenuOpen,fileMenuExit;   //�ļ��˵��Ĳ˵���  
    private JMenuItem helpMenuAbout;   //Help�˵��Ĳ˵���  
   
  
    //��������Ա��������  
    private JToolBar toolBar;   //������
    private JButton b2,b5,b6; //toolbar����3����ť���ֱ�Ϊ�����򿪡�����������������ͼ��  
      
    //����ʽ�˵���������  
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
        
        //����Ļ�м���ʾ   
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); 
        setLocation((dim.width - getWidth()) / 2, (dim.height - getHeight()) / 2);
        
        try{  
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  
        } catch (Exception e){ System.err.println("reason:"+e);}  
          
  
  
        c=getContentPane();         //����һ���������  
        editor = new JTextArea();   //����һ���ı���  
        
        //���ù�����������ӵ��������,��ʾjson        
        jsonPanel = new JPanel();
        jsonPanel.setLayout(new BorderLayout());
        jsonPanel.setPreferredSize(new Dimension(500, 0));
        jsonPanel.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        jsonPanel.add(new JScrollPane(editor),BorderLayout.CENTER);        
        c.add(jsonPanel,BorderLayout.EAST);
               
        
        //�����˵���ʵ��  
        nodePMenu =new JPopupMenu();   //���������˵�  
        item1 = new JMenuItem("New Node");  
        item2 = new JMenuItem("Child Node");  
        item4 = new JMenuItem("Import File");  
        item5 = new JMenuItem("Anaylyse");  
        item6 = new JMenuItem("Print");
        item7 = new JMenuItem("Delete");
        
        JPHandler JP = new JPHandler();  
        item1.addActionListener(JP);  //ע��˵��������¼�������  
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
        
        model = (DefaultTreeModel)projectTree.getModel(); //��ȡJTree��Ӧ��TreeModel���� 
        //����JTree�ɱ༭  
        projectTree.setEditable(true); 
        c.add(projectTree, BorderLayout.WEST);

        
        projectTree.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {//�Ƿ�Ϊ�Ҽ�
            		nodePMenu.add(item1);          	
            		nodePMenu.add(item2);  
//                    nodePMenu.add(item3);
                    nodePMenu.add(item4);
            		nodePMenu.add(item5);  
            		nodePMenu.add(item6);
            		nodePMenu.add(item7);              	              	
                	nodePMenu.show(projectTree, e.getX(), e.getY()); //treeNodeMenu���Ƕ���ĵ������˵�
                }
            }});
       
        mb = new JMenuBar();    //�����˵���
        fileMenu = new JMenu("File");//�����˵�  
        fileMenuOpen = new JMenuItem("Open");         
        fileMenuExit = new JMenuItem("Exit"); 
        
        JMHandler JM = new JMHandler();
        fileMenuOpen.addActionListener(JM);
        fileMenuExit.addActionListener(JM);
        
        
        //�ؼ���ӵ�menu
        fileMenu.add(fileMenuOpen);             
        fileMenu.addSeparator();    //��ӷָ���  
        fileMenu.add(fileMenuExit);  
        fileMenu.setFont(f);        //���ò˵������������  
  
          
        //Help�˵���ʵ��  
        helpMenu = new JMenu("Help");  
        helpMenuAbout = new JMenuItem("About");  
        helpMenuAbout.addActionListener(JM);

        helpMenu.add(helpMenuAbout);  
        helpMenu.setFont(f);  
  
        //���˵�ȫ����Ӳ˵�lan  
        mb.add(fileMenu);  
        mb.add(helpMenu);  

  
        //��������ʵ��  
        toolBar =new JToolBar();    //�������߰�  

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

        //�Ѱ�ťȫ����ӵ����߰���  
        toolBar.add(b2);   
        toolBar.add(b5);    toolBar.add(b6);
          
        //�Ѳ˵������������������˵���ӵ��������  
        setJMenuBar(mb);        //��ʾ�˵���  
        c.add(toolBar,BorderLayout.NORTH);         
  
        setVisible(true);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    }  
   
    //�Զ�����ʵ�ֵ���ʽ�˵����¼�����  
    private class JPHandler implements ActionListener  
    {  
        public void actionPerformed(ActionEvent e)  
        {  
            if(e.getSource()==item1)//new node
            {
            	Addnode();
            }  
            else if(e.getSource()==item2)//���child�ڵ�
            {
            	Addchildnode();
            }   
            else if(e.getSource()==item4) //�����ļ� 
            {  
	            ImportFile();                              	
            }  
            else if(e.getSource()==item5)//����
            { 
                AnalyseData();
            }  
            else if(e.getSource()==item6)//��ͼ
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
    
    
    public void Addnode(){//���ƽ���ڵ�
    	
    	String nodeName = JOptionPane.showInputDialog(null, "Name:","");
    	if(nodeName.isEmpty()){
    		return;
    	}else{
    	
    	//��ȡѡ�нڵ�  
        DefaultMutableTreeNode selectedNode  
            = (DefaultMutableTreeNode) projectTree.getLastSelectedPathComponent();  

        //����ڵ�Ϊ�գ�ֱ�ӷ���  
        if (selectedNode == null) return;  
        //��ȡ��ѡ�нڵ�ĸ��ڵ�  
        DefaultMutableTreeNode parent  
            = (DefaultMutableTreeNode)selectedNode.getParent();  
        //������ڵ�Ϊ�գ�ֱ�ӷ���  
        if (parent == null) return;  
        //����һ���½ڵ�  
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(nodeName);  
        //��ȡѡ�нڵ��ѡ������  
        int selectedIndex = parent.getIndex(selectedNode);  
        //��ѡ��λ�ò����½ڵ�  
        model.insertNodeInto(newNode, parent, selectedIndex + 1);  
        //--------�������ʵ����ʾ�½ڵ㣨�Զ�չ�����ڵ㣩-------  
        //��ȡ�Ӹ��ڵ㵽�½ڵ�����нڵ�  
        TreeNode[] nodes = model.getPathToRoot(newNode);  
        //ʹ��ָ���Ľڵ�����������TreePath  
        TreePath path = new TreePath(nodes);  
        //��ʾָ��TreePath  
        projectTree.scrollPathToVisible(path);
    	}
    }
    
    public void Delnode(){
    	DefaultMutableTreeNode selectedNode  
        = (DefaultMutableTreeNode) projectTree.getLastSelectedPathComponent();  
	    if (selectedNode != null && selectedNode.getParent() != null)  
	    {  
	        //ɾ��ָ���ڵ�  
	        model.removeNodeFromParent(selectedNode); 
	        //��Ҫ����ǰ����ӵı�־����+ * �Թ���
	        String str = selectedNode.getUserObject().toString().replace("+", "").replace("*", "").replace("��", "");
			if( data.indexOf(str) != -1){
		        data.removeElement(str);
		        data.removeElementAt(data.indexOf(str)+1);
			}
	    }//ɾ���ڵ�
	    
	    
    }
    
    public void Addchildnode(){//����ӽڵ�
    	
    	String nodeName = JOptionPane.showInputDialog(null, "Name:","");
    	if(nodeName.isEmpty()){
    		return;
    	}else{
    	
    	 //��ȡѡ�нڵ�  
        DefaultMutableTreeNode selectedNode  
            = (DefaultMutableTreeNode) projectTree.getLastSelectedPathComponent();  
        //����ڵ�Ϊ�գ�ֱ�ӷ���  
        if (selectedNode == null) return;  
        //����һ���½ڵ�  
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(nodeName);  
        
        //ֱ��ͨ��model������½ڵ㣬������ͨ������JTree��updateUI����  
        //model.insertNodeInto(newNode, selectedNode, selectedNode.getChildCount());  
        //ֱ��ͨ���ڵ�����½ڵ㣬����Ҫ����tree��updateUI����  
        selectedNode.add(newNode);  
        //--------�������ʵ����ʾ�½ڵ㣨�Զ�չ�����ڵ㣩-------  
        TreeNode[] nodes = model.getPathToRoot(newNode);  
        TreePath path = new TreePath(nodes);  
        projectTree.scrollPathToVisible(path);  
        projectTree.updateUI(); 
        
    	}
    }
    
    
  //�Զ�����ʵ�ֲ˵����¼�����  
    private class JMHandler implements ActionListener  
    {  
    	//fileMenuNew,fileMenuOpen,fileMenuImport,fileMenuExport,fileMenuExit
        public void actionPerformed(ActionEvent e)  
        {  
  
            if(e.getSource()==fileMenuOpen)//��json
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
    
  //�Զ�����ʵ��toolbar���¼�����  
    private class JBHandler implements ActionListener  
    {  //b1,b2,b3,b4,b5,b6
        public void actionPerformed(ActionEvent e)  
        {  
        	if(e.getSource()==b2)//��json
            {
            	OpenJson();
            } //  	   
            else if(e.getSource()==b5)//����
            {  
            	AnalyseTotal();
            }  
            else if(e.getSource()==b6)//��ͼ
            {
            	//open json file to editor
            	PrintTotal();
            }
            
        }
    }
    
    public static void PrintTotal(){}//
    
    public static void Print()//��Ҫ��Ӳ��������Ǹ�json��ϵͼ
    {
    	
    	DefaultMutableTreeNode selectedNode  
        = (DefaultMutableTreeNode) projectTree.getLastSelectedPathComponent();  
        Object userobject=selectedNode.getUserObject();
        if(userobject.toString().contains("+") && userobject.toString().contains("*"))
        {
            if(userobject.toString().contains("��"))
            {
            	JOptionPane.showMessageDialog(null, "Already Printed!!!"); 
            }
            else{
                selectedNode.setUserObject("��"+userobject.toString());
                String apName = userobject.toString().replace("+", "").replace("*", "").replace("��", "");//��ýڵ�����ȥ����־��,���ڵ�����
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
					"<p><b><font color=\"#FF6600\">����·������ʾ��</font></b></p>"+
					"<font color=\"#0099FF\">"+
					"<p>��ѧ������width:800 height:600 charge:-150 linkDistance:200 </p>"+
					"<p>�����ԣ������ͣ�ڽڵ�����ʾ���ƣ��ڵ����ק��</p>"+
					"</font>"+
				
				"<script type=\"text/javascript\">"+
					"var width = 800;"+
					"var height = 600;"+
					
					"//ȡ��20����ɫ������"+"\n"+
					"var color = d3.scale.category20();"+
					
					"//���廭��"+"\n"+
					"var svg = d3.select(\"body\").append(\"svg\")"+
						".attr(\"width\", width)"+
						".attr(\"height\", height);"+
					
					"//������ѧ�ṹ"+"\n"+
					"var force = d3.layout.force()"+
						".charge(-150)"+
						".linkDistance(200)"+
						".size([width, height]);"+
					
					"//��ȡ����"+"\n"+
					"d3.json(\""+apName+".json"+"\", function(error, graph) {"+
					  "force"+
						  ".nodes(graph.nodes)"+
						  ".links(graph.links)"+
						  ".start();"+
					  
					"//��������"+"\n"+
					"var link = svg.selectAll(\".link\")"+
					  ".data(graph.links)"+
					  ".enter()"+
					  ".append(\"line\")"+
					  ".attr(\"class\", \"link\")"+
					  ".attr(\"stroke\",\"#09F\")"+
					  ".attr(\"stroke-opacity\",\"0.4\")"+
					  ".style(\"stroke-width\",1);"+
					  
					"//����ڵ���"+"\n"+
					"var node = svg.selectAll(\".node\")"+
					  ".data(graph.nodes)"+
					  ".enter()"+
					  ".append(\"g\")"+
					  ".call(force.drag);"+
					
					"//�ڵ�Բ�α��"+"\n"+
					"node.append(\"circle\")"+
					  ".attr(\"class\", \"node\")"+
					  ".attr(\"r\",function(d){return 10+d.group;})"+
					  ".style(\"fill\", function(d) { return color(d.group); });"+
					
					"//��������ͣ�ı�ǩ"+"\n"+
					"node.append(\"title\")"+
					  ".text(function(d) { return d.name; });"+
					
					
					"//�ڵ�����ʾ������"+"\n"+
					"node.append(\"text\")"+
				"//	  .attr(\"dy\", \".3em\")"+"\n"+	
					  ".attr(\"class\",\"nodetext\")"+
				"//	  .style(\"text-anchor\", \"middle\")"+"\n"+
				      ".attr(\"dx\", 15)"+
				      ".attr(\"dy\", -15)"+
					  ".text(function(d) { return d.name; });"+
				"//	  .text(\"ҵ֧\");"+"\n"+
					
					"//��ʼ��ѧ����"+"\n"+
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
                
               //д��html�ļ�
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
                        if (p.exitValue() == 1)//p.exitValue()==0��ʾ����������1������������  
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
        JsonFileFilter jsonFilter = new JsonFileFilter(); //json������    
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
                
                //��ӵ�vector������ ,�Ա���ڵıȽ�ʹ��
                data.addElement(userobject.toString());
                data.addElement(file.getAbsoluteFile().toString());//��Ҫ����·��
               
                //��� ��ӱ�� ��������Ⱦvector���� 
                selectedNode.setUserObject("+"+userobject.toString());
      
            } 
        }
 
    }
    public void AnalyseTotal(){}
    public void AnalyseData(){//��ֻ���ap�ڵ���з�������˻�õĽڵ���ӦΪap�ڵ���
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
            	selectedNode.setUserObject("*"+userobject.toString()); //��ӱ�־            	
            	String nodeName = userobject.toString().replace("+", "").replace("*", "");//��ýڵ�����ȥ����־������AP����
				
				//��ʼ��json���ݽṹ
				String jsonNodeData = "{" + "\n" +
						"\"nodes\":[" + "\n" +
						"{\"name\":\"" + nodeName +"\",\"group\":1}," + "\n";//д����ѡap������				
				String jsonLinkData = "\"links\":[" + "\n"; //���ӹ�ϵ
               
              //analyse data here
                String nodeFilename = data.elementAt(data.indexOf(nodeName)+1);//��øýڵ�ĵ�����ļ���                
                for(int i=0; i < data.size(); i+=2){//��������vector��������������е�����ļ��Ա�������ݷ���
//                	if(userobject.toString().contains(data.elementAt(i).toString())){////vector�е�nodeName����ӵı�־��,����ֱ�ӱȽ�,���ѡ�е�note��vector�е�Ԫ����ȣ��򲻱Ƚ�
            		if(data.elementAt(i).toLowerCase().contains("ap")){
                		continue;//��ʱ�������е�AP�ڵ�
                	}else{
                		//�Ƿ�������ӱ�־λ
                		boolean isConnected = false;
                		//��ѡ��ap�ڵ������е�Ԫ���ݽ��бȽ�
                		String unitName = data.elementAt(i);
                		File compareFile = new File(data.elementAt(i+1));//��õ���ĵ�Ԫ����
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
						}//һ���Զ�����������
                        String filedata = String.valueOf(chars);//ת��Ϊstring
                        try {
							reader.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                        
                        File nodeFile = new File(nodeFilename);//ap�ڵ���ļ�
                        String line = null;
                        try {
							br = new BufferedReader(new FileReader(nodeFile));
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
            			try {
							while ((line = br.readLine())!=null) {//ÿ�ζ���һ�У�ʹ��һ����Ŀ���ļ����������ݱȽ�
								if(filedata.contains(line)){//dataΪunit���ݣ�lineΪapÿ������
									//unit�����ݰ���ap�е����ݣ�
									//��������ͬ����ip�󣬱����������ӹ�ϵ
									//�ҵ�������ͬ��ip���ã���ʱδʵ�֣�����ֱ��д��link��Ϣ��break;
									isConnected = true;
									break;
								}
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						jsonNodeData += "{\"name\":\"" + unitName + "\",\"group\":2}," + "\n";//д��unit����
						if(isConnected){//�������ӹ�ϵ����д�����json node �� links                			                			
                			jsonLinkData += "{\"source\":\"" + nodeName + "\",\"target\":\"" + unitName + "\",\"value\":1}," + "\n";	                		
						}else{
							jsonLinkData += "{}," + "\n";
						}
                		
                	}
                }
                
                
                //jsonNodeDataȥ�����Ķ��źͻس�����ӡ�],��
    			jsonNodeData = jsonNodeData.substring(0, jsonNodeData.length()-2) + "\n" + "],";
    			//jsonNodeDataȥ�����Ķ��źͻس�����ӡ�]}��
    			jsonLinkData = jsonLinkData.substring(0, jsonLinkData.length()-2) + "\n" + "]" + "\n" + "}";
              
    			
    			//���з�����ɣ�������д��json�ļ�
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