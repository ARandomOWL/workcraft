package org.workcraft.plugins.xmas.tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.workcraft.Tool;
import org.workcraft.dom.Node;
import org.workcraft.dom.visual.VisualGroup;
import org.workcraft.gui.graph.GraphEditorPanel;
import org.workcraft.gui.graph.tools.AbstractTool;
import org.workcraft.gui.graph.tools.Decorator;
import org.workcraft.gui.graph.tools.GraphEditor;
import org.workcraft.plugins.xmas.VisualXmas;
import org.workcraft.plugins.xmas.Xmas;
import org.workcraft.plugins.xmas.components.QueueComponent;
import org.workcraft.plugins.xmas.components.SyncComponent;
import org.workcraft.plugins.xmas.components.VisualQueueComponent;
import org.workcraft.plugins.xmas.components.VisualSyncComponent;
import org.workcraft.plugins.xmas.gui.SolutionsDialog1;
import org.workcraft.plugins.xmas.gui.SolutionsDialog2;
import org.workcraft.util.Hierarchy;
import org.workcraft.util.WorkspaceUtils;
import org.workcraft.workspace.WorkspaceEntry;


public class VerQuery extends AbstractTool implements Tool {

	@Override
	public String getSection() {
		return "Verification";
	}

	@Override
	public String getDisplayName() {
		return "Query";
	}

	private static class qslist {
		String name;
		int chk;

		public qslist(String s1,int n) {
			name = s1;
			chk=n;
		}
	}

	int cnt_syncnodes=0;
	int index=0;
	JFrame mainFrame = null;
	JComboBox mdcombob = null;
	static JComboBox q1combob = null;
	static JComboBox q2combob = null;
	JComboBox qscombob = null;
	static String level="";
	static String display="";
	static String highlight="";
	static String soln="";
	static List<qslist> qslist = new ArrayList<qslist>();

	public void dispose() {
		mainFrame.setVisible(false);
	}

    private static String ProcessArg(String file,int index) {
		   String typ=null;
		   Scanner sc=null;
		   try {
		      sc=new Scanner(new File(file));
		   } catch (FileNotFoundException e) {
		      System.err.println("Error: " + e.getMessage());
		   }
		   String targ="";
		   String larg="";
		   String sarg="";
		   String aarg="";
		   String qarg="";
		   String arg="";
		   int num;
		   while(sc.hasNextLine()) {
		     Scanner line_=new Scanner(sc.nextLine());
		     Scanner nxt=new Scanner(line_.next());
		     String check=nxt.next();
		     String str;
		     if(check.startsWith("trace")) {
		       nxt=new Scanner(line_.next());
		       targ="-t";
		       targ = targ + nxt.next();
		     }
		     else if(check.startsWith("level")) {
		    	 nxt=new Scanner(line_.next());
		    	 larg="-v";
		    	 str = nxt.next();
		    	 level = str;
		    	 if(str.equals("normal")) {
	     	         //System.out.println("Read v1");
		    		 larg = "-v1";
		    	 }
		    	 else if(str.equals("advanced")) {
	     	        //System.out.println("Read v2");
		    		larg = "-v2";
		    	 }
		     }
		     else if(check.startsWith("display")) {
		    	 nxt=new Scanner(line_.next());
		    	 str = nxt.next();
	     	     //System.out.println("strrr=" + str);
	     	     display = str;
		     }
		     else if(check.startsWith("highlight")) {
		    	 nxt=new Scanner(line_.next());
		    	 str = nxt.next();
	     	     //System.out.println("strrr=" + str);
	     	     highlight = str;
		     }
		     else if(check.startsWith("soln")) {
		    	 nxt=new Scanner(line_.next());
		    	 str = nxt.next();
	     	     System.out.println("solnnnnnnnnnnnnnnnnn=" + str);
	     	     soln = str;
	     	     sarg = " -s" + str;
		     }
		   }
		   System.out.println("aaaaaaaaaaaindex==============" + index);
		   //aarg = " -a" + index;
		   if(index>0) {
			   String queue1="";
			   String queue2="";
			   if(index==2) {
			     queue1 = (String) q1combob.getSelectedItem();
			     queue2 = (String) q2combob.getSelectedItem();
			   }
			   qarg = " -q" + index + queue1 + queue2;
		   }
   	     System.out.println("aaaaaaaaaaaaaaarggggg=" + aarg);
		   arg = targ + " " + larg + sarg + aarg + qarg;
		   return arg;
    }

    private static String process_loc(String file)
	{
		Scanner sc=null;
		try {
			sc=new Scanner(new File(file));
		} catch (FileNotFoundException e) {
		    System.err.println("Error: " + e.getMessage());
		}
		String str="";
		while(sc.hasNextLine()) {
			String line_ = sc.nextLine();
			//System.out.println(sc.next());
			str = str + line_ + '\n';
		}
		return str;
	}

    private static void process_qsl(String file)
    {
    	qslist.clear();
		Scanner sc=null;
		try {
			sc=new Scanner(new File(file));
		} catch (FileNotFoundException e) {
		    System.err.println("Error: " + e.getMessage());
		}
		while(sc.hasNextLine()) {
		    Scanner line_=new Scanner(sc.nextLine());
		    Scanner nxt=new Scanner(line_.next());
		    String check=nxt.next();
		    nxt=new Scanner(line_.next());
	    	String str = nxt.next();
	    	int num = Integer.parseInt(str);
	    	System.out.println("qsl " + check + " " + str + " " + num);
			qslist.add(new qslist(check,num));
		}
    }

    private static String process_eq(String file)
	{
		Scanner sc=null;
		try {
			sc=new Scanner(new File(file));
		} catch (FileNotFoundException e) {
		    System.err.println("Error: " + e.getMessage());
		}
		String str="";
		while(sc.hasNextLine()) {
			String line_ = sc.nextLine();
			//System.out.println(sc.next());
			str = str + line_ + '\n';
		}
		return str;
	}

    private static String process_que(String file)
	{
		Scanner sc=null;
		try {
			sc=new Scanner(new File(file));
		} catch (FileNotFoundException e) {
		    System.err.println("Error: " + e.getMessage());
		}
		String str="";
		while(sc.hasNextLine()) {
			String line_ = sc.nextLine();
			//System.out.println(sc.next());
			str = str + line_ + '\n';
		}
		return str;
	}

	public int check_type(String s) {

		if(s.contains("DEADLOCK FREE")) {
			return 0;
		}
		else if(s.contains("TRACE FOUND")) {
			return 1;
		}
		else if(s.contains("Local")) {
			return 2;
		}
		return 0;
	}

	public void init_highlight(Xmas xnet,VisualXmas vnet) {
		QueueComponent qc;
		SyncComponent sc;
		VisualQueueComponent vqc;
		VisualSyncComponent vsc;

		for (Node node : vnet.getNodes()) {
			if(node instanceof VisualQueueComponent) {
				vqc=(VisualQueueComponent)node;
		        vqc.setForegroundColor(Color.black);
		    }
			else if(node instanceof VisualSyncComponent) {
				vsc=(VisualSyncComponent)node;
		        vsc.setForegroundColor(Color.black);
		    }
		}
	}

	public void local_highlight(String s,Xmas xnet,VisualXmas vnet) {
		QueueComponent qc;
		SyncComponent sc;
		VisualQueueComponent vqc;
		VisualSyncComponent vsc;

		//System.out.println("s=" + s);
		for(String st : s.split(" |\n")){
		    if(st.startsWith("Q") || st.startsWith("S")){
		        System.out.println(st);
		        for (Node node : vnet.getNodes()) {
					if(node instanceof VisualQueueComponent) {
		            	vqc=(VisualQueueComponent)node;
		            	qc=vqc.getReferencedQueueComponent();
		            	//if(xnet.getName(qc).contains(st)) {
			            if(xnet.getName(qc).equals(st)) {
		            		vqc.setForegroundColor(Color.red);
		            	}
		            }
					else if(node instanceof VisualSyncComponent) {
		            	vsc=(VisualSyncComponent)node;
		            	sc=vsc.getReferencedSyncComponent();
		            	//if(xnet.getName(qc).contains(st)) {
			            if(xnet.getName(sc).equals(st)) {
		            		vsc.setForegroundColor(Color.red);
		            	}
		            }
				}
		    }
		}
	}

	public void rel_highlight(String s,Xmas xnet,VisualXmas vnet) {
		int typ=0;
		String str="";
		QueueComponent qc;
		SyncComponent sc;
		VisualQueueComponent vqc;
		VisualSyncComponent vsc;

		for(String st : s.split(" |;|\n")) {
			int n=1;
		    //if(st.startsWith("Q")){
		        if(st.contains("->")) {
			        //System.out.println("testst" + st);
		        	typ=0;
		        	for(String st_ : st.split("->")) {
		        		str=st_;
				       // System.out.println("str===" + str);
		        		for(Node node : vnet.getNodes()) {
							if(node instanceof VisualQueueComponent) {
				            	vqc=(VisualQueueComponent)node;
				            	qc=vqc.getReferencedQueueComponent();
				            	//System.out.println("x===" + xnet.getName(qc));
				            	if(xnet.getName(qc).equals(str) && typ==0) {
				            		vqc.setForegroundColor(Color.pink);
				            	}
				            }
							else if(node instanceof VisualSyncComponent) {
				            	vsc=(VisualSyncComponent)node;
				            	sc=vsc.getReferencedSyncComponent();
						        //System.out.println("strrr===" + str + ' ' + xnet.getName(sc));
				            	if(xnet.getName(sc).equals(str) && typ==0) {
				            		vsc.setForegroundColor(Color.pink);
				            	}
							}
						}
		        	}
		        }
		        else if(st.contains("<-")) {
			        //System.out.println("testst_" + st);
		        	typ=1;
		        	for(String st_ : st.split("<-")) {
		        		str=st_;
				        //System.out.println("str===" + str);
		        		for(Node node : vnet.getNodes()) {
							if(node instanceof VisualQueueComponent) {
				            	vqc=(VisualQueueComponent)node;
				            	qc=vqc.getReferencedQueueComponent();
				            	if(xnet.getName(qc).equals(str) && typ==1) {
				            		vqc.setForegroundColor(Color.red);
				            	}
				            }
							else if(node instanceof VisualSyncComponent) {
				            	vsc=(VisualSyncComponent)node;
				            	sc=vsc.getReferencedSyncComponent();
				            	if(xnet.getName(sc).equals(str) && typ==1) {
				            		vsc.setForegroundColor(Color.red);
				            	}
							}
						}
		        	}
		        }

		    //}
		}
	}

	public void active_highlight(Xmas xnet,VisualXmas vnet) {
		QueueComponent qc;
		SyncComponent sc;
		VisualQueueComponent vqc;
		VisualSyncComponent vsc;

		for (qslist ql : qslist) {
			if(ql.chk==0) {
        		for(Node node : vnet.getNodes()) {
					if(node instanceof VisualQueueComponent) {
		            	vqc=(VisualQueueComponent)node;
		            	qc=vqc.getReferencedQueueComponent();
		            	if(xnet.getName(qc).equals(ql.name)) {
		            		vqc.setForegroundColor(Color.green);
		            	}
					}
					else if(node instanceof VisualSyncComponent) {
			            vsc=(VisualSyncComponent)node;
			            sc=vsc.getReferencedSyncComponent();
			            if(xnet.getName(sc).equals(ql.name)) {
			            	vsc.setForegroundColor(Color.green);
			            }
		            }
				}
			}
		}
	}

	public boolean isApplicableTo(WorkspaceEntry we) {
		return WorkspaceUtils.canHas(we, Xmas.class);
	}

	GraphEditorPanel editor1;
	Graphics2D g;

    static List<JCheckBox> jcbn = new ArrayList<JCheckBox>();
    JCheckBox jcb, jcblast;

    void populate_md(int grnum)
    {
    	int i;

    	mdcombob.addItem("ALL");
    	for(i=1;i<=grnum;i++) {
    		int n = i;
        	mdcombob.addItem("L" + n);
    	}
    }

    void populate_qlists(Xmas cnet)
    {
    	for (Node node : cnet.getNodes()) {
            if(node instanceof QueueComponent) {
            	//System.out.println("QQQQ " + cnet.getName(node) + ".");
            	q1combob.addItem(cnet.getName(node));
            	q2combob.addItem(cnet.getName(node));
            }
		}
    }

    void populate_qslists(Xmas cnet)
    {
    	int cnt=0;

    	for (Node node : cnet.getNodes()) {
            if(node instanceof SyncComponent) {
            	//System.out.println("QQQQ " + cnet.getName(node) + ".");
            	qscombob.addItem(cnet.getName(node));
            	cnt++;
            }
		}
    	if(cnt>1) {
    		qscombob.addItem("ALL");
    	}
    	else {
    		qscombob.addItem("NONE");
    	}
    }

    void create_panel(List<JPanel> panellist,String file,Xmas cnet,int grnum) {
    	int no=1;
        String typ=null;
		Scanner sc=null;
		try {
		   sc=new Scanner(new File(file));
		} catch (FileNotFoundException e) {
		   System.err.println("Error: " + e.getMessage());
		}
		panellist.add(new JPanel());
		panellist.get(panellist.size()-1).add(new JLabel(" Sources" + ": "));
		panellist.get(panellist.size()-1).add(mdcombob = new JComboBox());
		panellist.get(panellist.size()-1).add(jcb=new JCheckBox(""));
		populate_md(grnum);
		ItemListener itemListener1 = new ItemListener() {
		@Override
		public void itemStateChanged(ItemEvent e) {
			if(e.getSource() instanceof JCheckBox){
				JCheckBox sjcb=(JCheckBox) e.getSource();
				if(sjcb.isSelected()) {
					index = jcbn.indexOf(sjcb) + 1;
					System.out.println("indexb==" + index);
				}
				if(jcblast!=null) jcblast.setSelected(false);
				jcblast=sjcb;
				//String name = sjcb.getName();
				//System.out.println(name);
				}
			}
		};
		jcb.addItemListener(itemListener1);
		jcbn.add(jcb);
		panellist.add(new JPanel());
		panellist.get(panellist.size()-1).add(new JLabel(" Pt-to-pt" + ": "));
		panellist.get(panellist.size()-1).add(q1combob = new JComboBox());
		panellist.get(panellist.size()-1).add(q2combob = new JComboBox());
		populate_qlists(cnet);
		panellist.get(panellist.size()-1).add(jcb=new JCheckBox(""));
		ItemListener itemListener2 = new ItemListener() {
		@Override
		public void itemStateChanged(ItemEvent e) {
			if(e.getSource() instanceof JCheckBox){
				JCheckBox sjcb=(JCheckBox) e.getSource();
				if(sjcb.isSelected()) {
					index = jcbn.indexOf(sjcb) + 1;
					System.out.println("indexb==" + index);
				}
				if(jcblast!=null) jcblast.setSelected(false);
				jcblast=sjcb;
				//String name = sjcb.getName();
				//System.out.println(name);
				}
			}
		};
		jcb.addItemListener(itemListener2);
		jcbn.add(jcb);
		panellist.add(new JPanel());
		panellist.get(panellist.size()-1).add(new JLabel(" Synchroniser" + ": "));
		panellist.get(panellist.size()-1).add(qscombob = new JComboBox());
		populate_qslists(cnet);
		panellist.get(panellist.size()-1).add(jcb=new JCheckBox(""));
		ItemListener itemListener = new ItemListener() {
		@Override
		public void itemStateChanged(ItemEvent e) {
			if(e.getSource() instanceof JCheckBox){
				JCheckBox sjcb=(JCheckBox) e.getSource();
				if(sjcb.isSelected()) {
					index = jcbn.indexOf(sjcb) + 1;
					System.out.println("indexb==" + index);
				}
				if(jcblast!=null) jcblast.setSelected(false);
				jcblast=sjcb;
				//String name = sjcb.getName();
				//System.out.println(name);
				}
			}
		};
		jcb.addItemListener(itemListener);
		jcbn.add(jcb);
    }

	public void run(final WorkspaceEntry we) {
        System.out.println("Query is undergoing implemention");

        final Xmas xnet = (Xmas)we.getModelEntry().getMathModel();
        final VisualXmas vnet = (VisualXmas)we.getModelEntry().getVisualModel();
        Xmas cnet = (Xmas)we.getModelEntry().getMathModel();

        PrintWriter writer = null;

        int grnum=0;
        for(VisualGroup vg: Hierarchy.getDescendantsOfType(vnet.getRoot(), VisualGroup.class)) {
        	grnum++;
        }

        mainFrame = new JFrame("Analysis");
		JPanel panelmain = new JPanel();
        mainFrame.getContentPane().add(panelmain,BorderLayout.PAGE_START);
        panelmain.setLayout(new BoxLayout(panelmain,BoxLayout.PAGE_AXIS));
        List<JPanel> panellist = new ArrayList<JPanel>();

        JPanel panela = new JPanel();
        panela.setLayout(new FlowLayout(FlowLayout.LEFT));
		panela.add(new JLabel(" QUERY [USE DEMO EXAMPLES] "));
        panela.add(Box.createHorizontalGlue());
        panelmain.add(panela);

		jcbn.clear();
        create_panel(panellist,"/home/frank/work_wk/soln",cnet,grnum);
		for (JPanel plist : panellist) {
			panelmain.add(plist);
		}

		JPanel panelb = new JPanel();
        panelb.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelButton = new JButton("Cancel");
        JButton okButton = new JButton("OK");
        panelb.add(Box.createHorizontalGlue());
        panelb.add(cancelButton);
        panelb.add(okButton);
        panelmain.add(panelb);

        mainFrame.pack();
        mainFrame.setVisible(true);

        cancelButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
				dispose();
            }

        });

        okButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
            	int no=1;

				dispose();

				if(index!=0) {
		            try {
		                Process p1 = Runtime.getRuntime().exec("cp /home/frank/work_wk/CPNFile /home/frank/work_wk/in");
		                p1.waitFor();
		                String arg = ProcessArg("/home/frank/work_wk/vsettings",index);
		                System.out.println("arg = " + arg);
		                String command = "vxm " + arg;

		                Process p = Runtime.getRuntime().exec(command);
		                String s, str="", str_="";
		                BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
		                int n=0;
		                int test=0;
		                init_highlight(xnet,vnet);
		                while ((s = stdInput.readLine()) != null) {
		                	if(test==0) test=check_type(s);
		                	if(n>0) str = str + s + '\n';
		                	n++;
		                	System.out.println(s);
		                }
		                if(level.equals("advanced")) {
		                	System.out.println("LEVEL IS ADVANCED ");
		                	process_qsl("/home/frank/work_wk/qsl");
		                	str = process_eq("/home/frank/work_wk/equ");
		                	str_ = process_que("/home/frank/work_wk/que");
		                }
		                else if(level.equals("normal") && test==2) {
		                	System.out.println("LEVEL IS NORMAL ");
		        			str = process_loc("/home/frank/work_wk/loc");
		                }
		                if(test>0) {
		            		if(display.equals("popup")) {
		            			if(!level.equals("advanced")) {
		            				SolutionsDialog1 solutionsDialog = new SolutionsDialog1(test,str_);
		            			}
		            			else {
		            				SolutionsDialog2 solutionsDialog = new SolutionsDialog2(test,str_);
		            			}
		            		}
		                	if(test==2) {
		                		if(highlight.equals("local")) {
		                			local_highlight(str,xnet,vnet);
		                		}
		                		else if(highlight.equals("rel")) {
		                			rel_highlight(str,xnet,vnet);
		                			active_highlight(xnet,vnet);
		                		}
		                	}
		        		} else {
		            		if(display.equals("popup")) {
		            			String message = "The system is deadlock-free.";
		            			JOptionPane.showMessageDialog(null, message);
		            		}
		        		}
		            } catch (Exception e1) {
		                   e1.printStackTrace();
		            }
		        }
		    }
        });
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Decorator getDecorator(GraphEditor editor) {
		// TODO Auto-generated method stub
		return null;
	}

}