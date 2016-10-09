/* 2016.8.7, improve algorithm of AI
 * 1) search
 * 2) learning
 * make jar:
 * jar cfe Watermelon.jar Watermelon *.class
 * http://www.picb.ac.cn/~xuyichi/Watermelon.jar
 */
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
public class Watermelon extends Thread implements ActionListener{
    JFrame f;
    gamePanel p;
    JLabel lb;
    JLabel lb2;
    JLabel lb1;
    roundButton[] bt=new roundButton[21];
    int i=0;
    boolean turn=true;
    int id1;
//    int id2;
    int id;
    boolean state=true;
    int AI=1;
    boolean win=false;
    JMenuBar bar=new JMenuBar();
    JMenu menu=new JMenu("Watermelon");
    JMenuItem reset=new JMenuItem("Restart");
    JMenuItem rival=new JMenuItem("Choose AI");
    JMenuItem help=new JMenuItem("Help");
    JMenuItem about=new JMenuItem("About");
    JMenuItem quit=new JMenuItem("Quit");
    JMenu aiMenu = new JMenu("AI");
    JRadioButtonMenuItem aiButton0 = new JRadioButtonMenuItem("0. No");
    JRadioButtonMenuItem aiButton1 = new JRadioButtonMenuItem("1. Rookie");
    JRadioButtonMenuItem aiButton2 = new JRadioButtonMenuItem("2. Master");
    ButtonGroup bg=new ButtonGroup();
    javax.swing.Timer timer;
    boolean test_timer=true;
    public static void main(String[] args) {
	new Watermelon();
    }
    public Watermelon(){
	/*
	timer = new javax.swing.Timer(3000, this);
	timer.setRepeats(true);
	*/
    f = new JFrame("Watermelon 2.0");
    p=new gamePanel();
    lb=new JLabel();
    lb2=new JLabel();
    lb1=new JLabel("Turn: ");
    Container cp=f.getContentPane();
    menu.add(reset);
    //    menu.add(rival);
    menu.add(help);
    menu.add(about);
    menu.addSeparator();
    menu.add(quit);
    bar.add(menu);
    bg.add(aiButton0);
    bg.add(aiButton1);
    bg.add(aiButton2);
    aiButton0.addActionListener(this);
    aiButton1.addActionListener(this);
    aiButton1.setSelected(true);
    aiButton2.addActionListener(this);
    aiMenu.add(aiButton0);
    aiMenu.add(aiButton1);
    aiMenu.add(aiButton2);
    bar.add(aiMenu);
    f.setJMenuBar(bar);
    bar.setOpaque(true);
    reset.addActionListener(this);
    //    rival.addActionListener(this);
    quit.addActionListener(this);
    about.addActionListener(this);
    help.addActionListener(this);
    for(i=0;i<21;i++){
        if(i<12) bt[i]=new roundButton(3,i);
        else bt[i]=new roundButton(4,i);
        cp.add(bt[i]);
        bt[i].addActionListener(this);
    }
    bt[0].setBounds(400,(int)(230-125*Math.sqrt(2)),50,50);
    bt[1].setBounds(275,25,50,50);
    bt[2].setBounds(150,(int)(230-125*Math.sqrt(2)),50,50);
    bt[3].setBounds(230-(int)(125*Math.sqrt(2)),150,50,50);
    bt[4].setBounds(25,275,50,50);
    bt[5].setBounds(230-(int)(125*Math.sqrt(2)),400,50,50);
    bt[6].setBounds(150,320+(int)(125*Math.sqrt(2)),50,50);
    bt[7].setBounds(275,525,50,50);
    bt[8].setBounds(400,320+(int)(125*Math.sqrt(2)),50,50);
    bt[9].setBounds(320+(int)(125*Math.sqrt(2)),400,50,50);
    bt[10].setBounds(525,275,50,50);
    bt[11].setBounds(320+(int)(125*Math.sqrt(2)),150,50,50);
    bt[12].setBounds(275,105,50,50);
    bt[13].setBounds(105,275,50,50);
    bt[14].setBounds(275,445,50,50);
    bt[15].setBounds(445,275,50,50);
    bt[16].setBounds(275,195,50,50);
    bt[17].setBounds(195,275,50,50);
    bt[18].setBounds(275,355,50,50);
    bt[19].setBounds(355,275,50,50);
    bt[20].setBounds(275,275,50,50);
    for(i=0;i<4;i++){
        bt[i].setBackground(Color.green);
        bt[i].fill=2;
    }
    bt[11].setBackground(Color.green);
    bt[12].setBackground(Color.green);
    bt[11].fill=2;
    bt[12].fill=2;
    for(i=5;i<10;i++){
        bt[i].setBackground(Color.red);
        bt[i].fill=1;
    }
    bt[14].setBackground(Color.red);
    bt[14].fill=1;
    bt[0].con1(bt[1],bt[11],bt[12]);
    bt[1].con1(bt[0], bt[2], bt[12]);
    bt[2].con1(bt[1], bt[3], bt[12]);
    bt[3].con1(bt[2], bt[4], bt[13]);
    bt[4].con1(bt[3], bt[5], bt[13]);
    bt[5].con1(bt[4], bt[6], bt[13]);
    bt[6].con1(bt[5], bt[7], bt[14]);
    bt[7].con1(bt[6], bt[8], bt[14]);
    bt[8].con1(bt[7], bt[9], bt[14]);
    bt[9].con1(bt[8], bt[10], bt[15]);
    bt[10].con1(bt[9], bt[11], bt[15]);
    bt[11].con1(bt[10], bt[0], bt[15]);
    bt[12].con2(bt[0],bt[1],bt[2],bt[16]);
    bt[13].con2(bt[3],bt[4],bt[5],bt[17]);
    bt[14].con2(bt[6],bt[7],bt[8],bt[18]);
    bt[15].con2(bt[9],bt[10],bt[11],bt[19]);
    bt[16].con2(bt[12],bt[17],bt[19],bt[20]);
    bt[17].con2(bt[13],bt[16],bt[18],bt[20]);
    bt[18].con2(bt[14],bt[17],bt[19],bt[20]);
    bt[19].con2(bt[15],bt[16],bt[18],bt[20]);
    bt[20].con2(bt[16],bt[17],bt[18],bt[19]);
    lb.setOpaque(true);
    cp.add(lb);
    lb.setBounds(550,10,25,25);
    lb.setBackground(Color.red);
    lb2.setBounds(10,10,30,30);
    lb2.setText("AI: "+AI);
    cp.add(lb2);
    lb1.setBounds(500,0,50,50);
    cp.add(lb1);
    cp.add(p);
    f.setSize(620, 640);
    f.setResizable(false);
    f.setVisible(true);
    f.setLocation(300,0);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void Reset(){
        for(i=0;i<21;i++){
            bt[i].setBackground(Color.white);
            bt[i].fill=0;
        }
        for(i=0;i<4;i++){
        bt[i].setBackground(Color.green);
        bt[i].fill=2;
        }
        bt[11].setBackground(Color.green);
        bt[12].setBackground(Color.green);
        bt[11].fill=2;
        bt[12].fill=2;
        for(i=5;i<10;i++){
        bt[i].setBackground(Color.red);
        bt[i].fill=1;
        }
        bt[14].setBackground(Color.red);
        bt[14].fill=1;
        lb.setBackground(Color.red);
        win=false;
        state=true;
        turn=true;
	//        AI=0;
	//	int[] init_state={1,0,0,0,1,0,0,0,1,0,0,0,2,2,2,2,0,0,0,0,0};
	//	set_state(init_state);
    }
    public void actionPerformed(ActionEvent e){
        String str="";
        String[] yesNo={"No","Rookie","Advance"};
        String message="";
        int type=JOptionPane.PLAIN_MESSAGE;
        String title="";
        if(e.getSource()==quit) System.exit(0);
        else if(e.getSource()==about) {
        title="About";
        message="For my grandmother and grandfather"+"\n"+"\n"+"Author: Ian (sheeeets@gmail.com)"+"\n"+"Version: 2.0";
        JOptionPane.showMessageDialog(f,message,title,type);
        }
        else if(e.getSource()==help){
        title="Help";
        message="1) Like GO, one or more stones will be out if they are closed by stones from another side. Losing more than three stones will lose the game."+"\n"+"2) In each step, one stone is allowed to be moved to a neighboring gap position by clicking the stone and then clicking the gap."+"\n"+"3) The player (red) takes the first step. The AI that controls green is selected in manu \"AI\".";
        JOptionPane.showMessageDialog(f,message,title,type);
        }
        else if(e.getSource()==reset){
            Reset();
        }
	/*
        if(e.getSource()==rival){
        title="AI";
        message="Need AI control green?";
        type=JOptionPane.QUESTION_MESSAGE;
        str=(String)JOptionPane.showInputDialog(f,message,title,type,null,yesNo,yesNo[0]);
	if(str!=null){
	    if(str.equals("Rookie")) AI=1;
	    else if(str.equals("No")) AI=0;
	    else if(str.equals("Advance")) AI=2;
	    lb2.setText("AI: "+AI);
	}
        }
	*/
	else if(e.getSource()==aiButton0){
	    AI=0;
	    lb2.setText("AI: "+AI);
	}
	else if(e.getSource()==aiButton1){
	    AI=1;
	    lb2.setText("AI: "+AI);
	}
	else if(e.getSource()==aiButton2){
	    AI=2;
	    lb2.setText("AI: "+AI);
	}
	else if(win) return;
	else{
        for(i=0;i<21;i++){
         if(e.getSource()==bt[i])  {
	     id=i;
	     //	     System.out.println(i);
	     break;
	 }
        }
        if(turn&&state){
            if(bt[id].fill==1){
                id1=id;
                state=!state;
            }
        }
        if(turn&&!state){
            if(bt[id].fill==0&&bt[id1].connnect(bt[id])){
                bt[id1].setBackground(Color.white);
                bt[id1].fill=0;
                bt[id].setBackground(Color.red);
                bt[id].fill=1;
                eat(bt[id],turn);
                state=!state;
                turn=!turn;
                lb.setBackground(Color.green);
                if(AI==1&&!win){    //without win
		    //		    randomAI();
                    highAI();
                }
                if(AI==2&&!win){
		    //                    highAI();
		    abpruning();
                }
            }
            if(bt[id].fill==1){
                id1=id;
            }
        }
        if(!turn&&state){
            if(bt[id].fill==2){
                id1=id;
                state=!state;
            }
        }
        if(!turn&&!state){
            if(bt[id].fill==0&&bt[id1].connnect(bt[id])){
                bt[id1].setBackground(Color.white);
                bt[id1].fill=0;
                bt[id].setBackground(Color.green);
                bt[id].fill=2;
		/*
		int[] test_state=new int[bt.length];
		for(int j=0;j<bt.length;j++) test_state[j]=bt[j].fill;
		System.out.println("state1: "+array_to_string(test_state));
		System.out.println("state2: "+array_to_string(update_state(test_state,true)));
		*/
                eat(bt[id],turn);
                state=!state;
                turn=!turn;
                lb.setBackground(Color.red);
            }
            if(bt[id].fill==2){
                id1=id;
            }
        }
	}
    }
    void randomAI(){
        int ai;
        int blank;
        int count=0;
        do{
        p5:
        while (true) {
            random randomGreen = new random();
            ai = randomGreen.r;
            if (bt[ai].fill == 2) {
                for (int t = 0; t < bt[ai].con.length; t++) {
                    if (bt[ai].con[t].fill == 0) {
                        break p5;
                    }
                }
            }
        }
        while (true) {
            random randomBlank = new random(bt[ai].con.length);
            blank = randomBlank.r;
            if (bt[ai].con[blank].fill == 0) {
                break;
            }
        }
        }
        while(eatGreen(bt[ai],bt[ai].con[blank])&&count++<50);
        bt[ai].setBackground(Color.white);
        bt[ai].fill = 0;
        bt[ai].con[blank].setBackground(Color.green);
        bt[ai].con[blank].fill = 2;
        eat(bt[ai].con[blank], turn);
        state = !state;
        turn = !turn;
        lb.setBackground(Color.red);
    }
    void highAI(){
        int j=0;
        int k=0;
        int s=0;
        int t=0;
        int u=0;
        int v=0;
        for(j=0;j<21;j++){
            if(bt[j].fill==2){
                for(k=0;k<bt[j].con.length;k++){
                    if(bt[j].con[k].fill==0&&eatRed(bt[j],bt[j].con[k])) {
                        bt[j].setBackground(Color.white);
                        bt[j].fill=0;
                        bt[j].con[k].setBackground(Color.green);
                        bt[j].con[k].fill=2;
                        eat(bt[j].con[k],turn);
                        state = !state;
                        turn = !turn;
                        lb.setBackground(Color.red);
                        return;
                    }
                }
            }
        }
        for(j=0;j<21;j++){
            if(bt[j].fill==2){
                for(k=0;k<bt[j].con.length;k++){
                    if(bt[j].con[k].fill==0) {
                        bt[j].fill=0;
                        bt[j].con[k].fill=2;
                        for(s=0;s<21;s++){
                            if(bt[s].fill==2){
                                for(t=0;t<bt[s].con.length;t++){
                                    if(bt[s].con[t].fill==0&&eatRed(bt[s],bt[s].con[t])&&!eatGreen(bt[s],bt[s].con[t])) {
                                        bt[j].setBackground(Color.white);
                                        bt[j].fill = 0;
                                        bt[j].con[k].setBackground(Color.green);
                                        bt[j].con[k].fill = 2;
//                                        eat(bt[j].con[k], turn);
                                        state = !state;
                                        turn = !turn;
                                        lb.setBackground(Color.red);
                                        return;
                                    }
                                }
                            }
                        }
                        bt[j].fill=2;
                        bt[j].con[k].fill=0;
                    }
                }
            }
        }
        for(j=0;j<21;j++){
            if(bt[j].fill==2){
                for(k=0;k<bt[j].con.length;k++){
                    if(bt[j].con[k].fill==0) {
                        bt[j].fill=0;
                        bt[j].con[k].fill=2;
                        for(s=0;s<21;s++){
                            if(bt[s].fill==2){
                                for(t=0;t<bt[s].con.length;t++){
                                    if(bt[s].con[t].fill==0){
                                        bt[s].fill=0;
                                        bt[s].con[t].fill=2;
                                        for(u=0;u<21;u++){
                                            if(bt[u].fill==2){
                                                for(v=0;v<bt[u].con.length;v++){
                                                    if(bt[u].con[v].fill==0&&eatRed(bt[u],bt[u].con[v])&&!eatGreen(bt[s],bt[s].con[t])){
                                                        bt[j].setBackground(Color.white);
                                                        bt[j].fill = 0;
                                                        bt[j].con[k].setBackground(Color.green);
                                                        bt[j].con[k].fill = 2;
                                                        bt[s].fill=2;
                                                        bt[s].con[t].fill=0;
//                                                        eat(bt[j].con[k], turn);
                                                        state = !state;
                                                        turn = !turn;
                                                        lb.setBackground(Color.red);
                                                        return;
                                                    }
                                                }
                                            }
                                        }
                                        bt[s].fill=2;
                                        bt[s].con[t].fill=0;
                                    }
                                }
                            }
                        }
                        bt[j].fill=2;
                        bt[j].con[k].fill=0;
                    }
                }
            }
        }
        randomAI();
    }

    /* 2016.10.1, write an AI using alpha-beta pruning
     * score = green# - red#
     * create a vector storing the state of the board (0/1/2 of all positions)
     */
    int ab_depth=7; // the search depth
    int max=99;
    int min=-99;
    HashMap<int[],Integer> state_score;
    ArrayList<ArrayList<int[]>> state_tree;
    void abpruning(){
	state_score=new HashMap<int[],Integer>();
	state_tree=new ArrayList<ArrayList<int[]>>(); // for debug the strategy tree
	ArrayList<int[]> one_level=new ArrayList<int[]>();
	int current_score;
	int[] current_state=new int[bt.length];
	int[] next_state=new int[bt.length];
	int expect_score;
	HashMap<int[],Integer> state_red=new HashMap<int[],Integer>();
	HashMap<int[],Integer> state_green=new HashMap<int[],Integer>();
	int current_red=0;
	int current_green=0;
	ArrayList<int[]> cand_state=new ArrayList<int[]>();
	ArrayList<int[]> keystone_state=new ArrayList<int[]>();
	for(int j=0;j<current_state.length;j++){
	    current_state[j]=bt[j].fill;
	    if(current_state[j]==1) current_red++;
	    else if(current_state[j]==2) current_green++;
	}
	current_score=green_air(current_state);
	one_level.add(current_state);
	state_tree.add(one_level);
	expect_score=pruning(current_state,1,min,max,true);
	//	System.out.println("state: "+array_to_string(current_state));
	int next_red=0;
	int min_next_red=99;
	int next_green=0;
	int next_key=0;
	int max_next_key=-99;
	for(int[] state : state_score.keySet()){
	    next_red=0;
	    next_green=0;
	    for(int k=0;k<state.length;k++){
		if(state[k]==1) next_red++;
		else if(state[k]==2) next_green++;
	    }
	    if((min_next_red>next_red)&&(state_score.get(state)==expect_score)) min_next_red=next_red;
	    state_red.put(state,next_red);
	    state_green.put(state,next_green);
	}
	for(int[] state : state_score.keySet()){
	    if(state_score.get(state)==expect_score){
		cand_state.add(state);
		next_key=key_stone(state,true);
		if(max_next_key<next_key) max_next_key=next_key;
	    }
	}
	// choose next state: 1) from states with maximum expect score; 2) eat red in next step; 3) not eaten by red in next step; 4) more key stones;
	if(cand_state.size()==1){
	    next_state=cand_state.get(0);
	}else if(min_next_red<current_red){
	    for(int[] state: cand_state){
		if(state_red.get(state)==min_next_red){
		    next_state=state;
		    break;
		}
	    }
	}else{
	    // filter the state containing less green
	    ArrayList<Integer> less_green=new ArrayList<Integer>();
	    for(int j=0;j<cand_state.size();j++){
		if(state_green.get(cand_state.get(j))<current_green) less_green.add(j);
	    }
	    if(less_green.size()!=cand_state.size()){
		for(Integer ind:less_green) cand_state.remove(ind);
	    }
	    // filter with max key_stones in cand_state

	    keystone_state=new ArrayList<int[]>();
	    for(int[] state: cand_state){
		if(key_stone(state,true)==max_next_key) keystone_state.add(state);
	    }
	    next_state=keystone_state.get((new random(keystone_state.size())).r);

	    //	    next_state=cand_state.get((new random(cand_state.size())).r);
	}
	int next_index=-1;
	for(int j=0;j<current_state.length;j++){
	    if((current_state[j]!=next_state[j])&&(current_state[j]==0)){
		bt[j].setBackground(Color.green);
		bt[j].fill=2;
		next_index=j;
	    }else if((current_state[j]!=next_state[j])&&(current_state[j]==2)){
		bt[j].setBackground(Color.white);
		bt[j].fill=0;
	    }
	}
	/*
	System.out.println(array_to_string(next_state));
	int next_score=green_air(next_state);
	System.out.println("score: "+current_score+"; score2: "+next_score+"; expect_score in "+ab_depth+": "+expect_score+"; total states: "+state_score.size()+"; step index: "+next_index);
	for(int[] tmp:state_score.keySet()){
	    System.out.println(array_to_string(tmp)+" "+state_score.get(tmp));
	}
	System.out.println();
	*/
	eat(bt[next_index], turn);
	state = !state;
	turn = !turn;
	lb.setBackground(Color.red);
    }
    void set_state(int[] state){
	for(int i=0;i<bt.length;i++){
	    if(state[i]==0){
		bt[i].setBackground(Color.white);
		bt[i].fill=0;
	    }else if(state[i]==1){
		bt[i].setBackground(Color.red);
		bt[i].fill=1;
	    }else if(state[i]==2){
		bt[i].setBackground(Color.green);
		bt[i].fill=2;
	    }
        }
    }
    int green_index_sum(int[] state){
	int res=0;
	for(int j=0;j<state.length;j++){
	    if(state[j]==2) res=res+j;
	}
	return(res);
    }
    int key_stone(int[] state, boolean is_green){ // whether (12,16), (13,17), (14,18), (15,19) have stones
	int res=0;
	int flag;
	if(is_green) flag=2;
	else flag=1;
	if(state[12]==flag||state[16]==flag) res++;
	if(state[13]==flag||state[17]==flag) res++;
	if(state[14]==flag||state[18]==flag) res++;
	if(state[15]==flag||state[19]==flag) res++;
	return(res);
    }
    int stone_diff(int[] state){
	int res=0;
	for(int j=0;j<state.length;j++){
	    if(state[j]==1) res--;
	    if(state[j]==2) res++;
	}
	return(res);
    }
    int green_air(int[] state){ // score based on the number of air that green have
	int res=0;
	int red_num=0;
	int green_num=0;
	HashSet<Integer> airG=new HashSet<Integer>();
	HashSet<Integer> airR=new HashSet<Integer>();
	for(int j=0;j<state.length;j++){
	    if(state[j]==2){
		res=res+4;
		green_num++;
		if(pattern_punish(state,j,true)) res=res-5;
		for(int k=0;k<bt[j].con.length;k++){
		    if(state[bt[j].con[k].index]==0) airG.add(bt[j].con[k].index);
		}
	    }
	    if(state[j]==1){
		red_num++;
		for(int k=0;k<bt[j].con.length;k++){
		    if(state[bt[j].con[k].index]==0) airR.add(bt[j].con[k].index);
		}
	    }
	}
	for(Integer ind : airG){
	    if(airR.contains(ind)) res++;
	    else res=res+2;
	}
	if(red_num<3) res=res+99;
	return(res);
    }
    boolean pattern_punish(int[] state, int stone, boolean is_green){
	boolean punish=false;
	// punish on (this green + linked air)<=3 but they are surrounded by red
	LinkedList<Integer> cluster=new LinkedList<Integer>();
	ListIterator<Integer> iter;
	cluster.add(stone);
	iter=cluster.listIterator(0);
	int ind;
	int size_limit=4;
	p11:if(is_green){
	    do{
		ind=iter.next();
		for(int j=0;j<bt[ind].con.length;j++){
		    if((state[bt[ind].con[j].index]!=1)&&(!cluster.contains(bt[ind].con[j].index))){
			iter.add(bt[ind].con[j].index);
			if(cluster.size()>size_limit) break p11;
			iter.previous();
		    }
		}
	    }while(iter.hasNext());
	    if(cluster.size()<=size_limit){
		punish=true;
		//		System.out.println("state: "+array_to_string(state)+"; cluster"+cluster+"; stone: "+stone);
	    }
	}
	return(punish);
    }
    int red_air(int[] state){ // the number of air that red have
	int res=0;
	HashSet<Integer> air=new HashSet<Integer>();
	for(int j=0;j<state.length;j++){
	    if(state[j]==1){
		air.add(j);
		for(int k=0;k<bt[j].con.length;k++){
		    if(state[bt[j].con[k].index]==0) air.add(bt[j].con[k].index);
		}
	    }
	}
	return(air.size());
    }
    int green_cluster(int[] state){ // the number of green clusters
	int res=0;
	LinkedList<Integer> cluster;
	ListIterator<Integer> iter;
	HashSet<Integer> counted=new HashSet<Integer>();
	int ind;
	for(int j=0;j<state.length;j++){
	    if((state[j]==2)&&(!counted.contains(j))){
		res++;
		cluster=new LinkedList<Integer>();
		cluster.add(j);
		counted.add(j);
		iter=cluster.listIterator(0);
		do{
		    ind=iter.next();
		    for(int k=0;k<bt[ind].con.length;k++){
			if((state[bt[ind].con[k].index]==2)&&(!cluster.contains(bt[ind].con[k].index))){
			    iter.add(bt[ind].con[k].index);
			    iter.previous();
			    counted.add(bt[ind].con[k].index);
			}
		    }
		}while(iter.hasNext());
		//		System.out.println(cluster);
	    }
	}
	return(res);
    }
    String array_to_string(int[] a){
	StringBuilder sb=new StringBuilder();
	for(int j=0;j<a.length;j++) sb.append(a[j]+" ");
	sb.append(";");
	return(sb.toString());
    }
    int pruning(int[] origin_state, int depth, int alpha, int beta, boolean is_max){
	int[] state=new int[origin_state.length];
	for(int j=0;j<state.length;j++){
	    state[j]=origin_state[j];
	}
	int res;
	if(depth==ab_depth){
	    return(green_air(state));
	}
	int next_score;
	int from;
	int to;
	ArrayList<int[]> one_level=new ArrayList<int[]>();
	if(is_max){ // for green (AI)
	    res=min;
	    p7:for(int j=0;j<state.length;j++){
		if(state[j]==2){
		    for(int k=0;k<bt[j].con.length;k++){
			if(state[bt[j].con[k].index]==0){
			    int[] next_state=new int[state.length];
			    for(int l=0;l<state.length;l++){
				next_state[l]=state[l];
			    }
			    next_state[j]=0;
			    next_state[bt[j].con[k].index]=2;
			    next_state=update_state(next_state, true);
			    from=j;
			    to=bt[j].con[k].index;
			    one_level=new ArrayList<int[]>();
			    if(state_tree.size()<=depth){
				one_level.add(next_state);
				state_tree.add(one_level);
			    }else{
				one_level=state_tree.get(depth);
				one_level.add(next_state);
				state_tree.set(depth,one_level);
			    }
			    //			    System.out.println("green depth "+depth+" state_tree "+state_tree.size());
			    next_score=pruning(next_state,depth+1,alpha,beta,false);
			    //			    System.out.println("depth: "+(depth+1)+"; id: "+state_tree.get(depth).indexOf(next_state)+"; mother: "+state_tree.get(depth-1).indexOf(origin_state)+"; green from "+from+" to "+to+" expect "+next_score+"; next_state: "+array_to_string(next_state));
			    res=Math.max(res,next_score);
			    alpha=Math.max(alpha,res);
			    if(depth==1){ // record the value at the second level so that green can choose next step
				state_score.put(next_state,next_score);
			    }
			    if(alpha>beta){ // > but not >= seems to be very important! >= can get the maximum value. But > is necessary to choose the right step!
				//				System.out.println("break at depth: "+(depth+1)+"; id: "+state_tree.get(depth).indexOf(next_state)+"; mother: "+state_tree.get(depth-1).indexOf(origin_state)+"; green from "+from+" to "+to+" expect "+next_score+"; next_state: "+array_to_string(next_state)+" alpha "+alpha+" beta "+beta);
				break p7;
			    }
			}
		    }
		}
	    }
	}else{
	    res=max;
	    p8:for(int j=0;j<state.length;j++){
		if(state[j]==1){
		    for(int k=0;k<bt[j].con.length;k++){
			if(state[bt[j].con[k].index]==0){
			    int[] next_state=new int[state.length];
			    for(int l=0;l<state.length;l++){
				next_state[l]=state[l];
			    }
			    next_state[j]=0;
			    next_state[bt[j].con[k].index]=1;
			    next_state=update_state(next_state, false);
			    from=j;
			    to=bt[j].con[k].index;
			    one_level=new ArrayList<int[]>();
			    if(state_tree.size()<=depth){
				one_level.add(next_state);
				state_tree.add(one_level);
			    }else{
				one_level=state_tree.get(depth);
				one_level.add(next_state);
				state_tree.set(depth,one_level);
			    }
			    //			    System.out.println("red depth "+depth+" state_tree "+state_tree.size());
			    next_score=pruning(next_state,depth+1,alpha,beta,true);
			    //			    System.out.println("depth: "+(depth+1)+"; id: "+state_tree.get(depth).indexOf(next_state)+"; mother: "+state_tree.get(depth-1).indexOf(origin_state)+"; red from "+from+" to "+to+" expect "+next_score+"; next_state: "+array_to_string(next_state));
			    res=Math.min(res,next_score);
			    beta=Math.min(beta,res);
			    if(alpha>beta){
				//				System.out.println("break at depth: "+(depth+1)+"; id: "+state_tree.get(depth).indexOf(next_state)+"; mother: "+state_tree.get(depth-1).indexOf(origin_state)+"; red from "+from+" to "+to+" expect "+next_score+"; next_state: "+array_to_string(next_state)+" alpha "+alpha+" beta "+beta);
				break p8;
			    }
			}
		    }
		}
	    }
	}
	return(res);
    }
    int[] update_state(int[] origin_state, boolean check_red){ // whether some stone is eaten
	int[] state=new int[origin_state.length];
	for(int j=0;j<state.length;j++)
	    state[j]=origin_state[j];
	HashMap<Integer,Boolean> stone_hit=new HashMap<Integer,Boolean>();
	LinkedList<Integer> stone_cluster; // ListIterator can iterate and add element into a list at the same time
	int current_stone;
	if(check_red){
	    for(int j=0;j<state.length;j++){
		if(state[j]==1){
		    stone_hit.put(j,false);
		}
	    }
	    p9:for(Integer index:stone_hit.keySet()){
		stone_cluster=new LinkedList<Integer>();
		stone_cluster.add(index);
		ListIterator<Integer> stone_iter=stone_cluster.listIterator(0);
		do{
		    current_stone=stone_iter.next();
		    for(int j=0;j<bt[current_stone].con.length;j++){
			if(state[bt[current_stone].con[j].index]==0) continue p9;
			if(state[bt[current_stone].con[j].index]==1&&!stone_cluster.contains(bt[current_stone].con[j].index)){
			    stone_iter.add(bt[current_stone].con[j].index);
			    stone_iter.previous();
			}
		    }
		}while(stone_iter.hasNext());
		stone_hit.put(stone_cluster.peek(),true);
	    }
	}else{
	    for(int j=0;j<state.length;j++){
		if(state[j]==2){
		    stone_hit.put(j,false);
		}
	    }
	    p10:for(Integer index:stone_hit.keySet()){
		stone_cluster=new LinkedList<Integer>();
		stone_cluster.add(index);
		ListIterator<Integer> stone_iter=stone_cluster.listIterator(0);
		do{
		    current_stone=stone_iter.next();
		    for(int j=0;j<bt[current_stone].con.length;j++){
			if(state[bt[current_stone].con[j].index]==0) continue p10;
			if(state[bt[current_stone].con[j].index]==2&&!stone_cluster.contains(bt[current_stone].con[j].index)){
			    stone_iter.add(bt[current_stone].con[j].index);
			    stone_iter.previous();
			}
		    }
		}while(stone_iter.hasNext());
		stone_hit.put(stone_cluster.peek(),true);
	    }
        }
	for(Integer index:stone_hit.keySet()){
	    //	    System.out.println(index+" "+stone_hit.get(index));
	    if(stone_hit.get(index)){
		state[index]=0;
	    }
	}
	return(state);
    }

    boolean eatRed(roundButton bt1,roundButton bt2){
        bt1.fill=0;
        bt2.fill=2;
        boolean b=false;
        int j=0;
        int k=0;
        list lt;
        roundButton nd=new roundButton(4,0);
        for(j=0;j<bt2.con.length;j++){
            p3: if(bt2.con[j].fill==1){
                lt=new list(bt2.con[j]);
                nd=lt.header;
                while(nd!=null){
                    for(k=0;k<nd.con.length;k++){
                        if(nd.con[k].fill==0) break p3;
                        if(nd.con[k].fill==1&&!lt.isExist(nd.con[k])) lt.insertEnd(nd.con[k]);
                    }
                    nd=nd.next;
                }
                b=true;
                break;
            }
        }
        bt1.fill=2;
        bt2.fill=0;
        return b;
    }
    boolean eatGreen(roundButton bt1,roundButton bt2){
        boolean b=false;
        int j=0;
        int k=0;
        int s=0;
        int t=0;
        list lt;
        roundButton nd=new roundButton(4,0);
        bt1.fill=0;
        bt2.fill=2;
        p6:
        for (s = 0; s < 21; s++) {
            if (bt[s].fill == 1) {
                for (t = 0; t < bt[s].con.length; t++) {
                    if (bt[s].con[t].fill == 0) {
                        bt[s].fill = 0;
                        bt[s].con[t].fill = 1;
                        for (j = 0; j < bt[s].con[t].con.length; j++) {
                            p4:
                            if (bt[s].con[t].con[j].fill == 2) {
                                lt = new list(bt[s].con[t].con[j]);
                                nd = lt.header;
                                while (nd != null) {
                                    for (k = 0; k < nd.con.length; k++) {
                                        if (nd.con[k].fill == 0) {
                                            break p4;
                                        }
                                        if (nd.con[k].fill == 2 && !lt.isExist(nd.con[k])) {
                                            lt.insertEnd(nd.con[k]);
                                        }
                                    }
                                    nd = nd.next;
                                }
                                b = true;
                                bt[s].fill = 1;
                                bt[s].con[t].fill = 0;
                                break p6;
                            }
                        }
                        bt[s].fill = 1;
                        bt[s].con[t].fill = 0;
                    }
                }
            }
        }
        bt1.fill=2;
        bt2.fill=0;
        return b;
    }
    void eat(roundButton bt,boolean b){
        int j=0;
        int k=0;
        list lt;
        roundButton nd=new roundButton(4,0);
//        int count=0;
        if(b){
            for(j=0;j<bt.con.length;j++){
                p1:if(bt.con[j].fill==2){
                    lt=new list(bt.con[j]);
                    nd=lt.header;
                    while(nd!=null){
			for(k=0;k<nd.con.length;k++){
			    if(nd.con[k].fill==0) break p1;
			    if(nd.con[k].fill==2&&!lt.isExist(nd.con[k])) lt.insertEnd(nd.con[k]);
			}
			nd=nd.next;
                    }
		    nd=lt.header;
		    while(nd!=null){
			nd.setBackground(Color.black);
			nd.fill=0;
			nd=nd.next;
		    }
		    /* don't Calling Thread.sleep(...) on the event thread will prevent it from performing its necessary actions
		    try{
			this.sleep(2000);
		    }catch(InterruptedException e){
			throw new RuntimeException(e);
		    }
		    // next code does not work
		    javax.swing.Timer timer=new javax.swing.Timer(5000,new ActionListener(){
			    public void actionPerformed(ActionEvent event){
				System.out.println("here");
			    }
			}
			);
		    */
		    try{
			Thread.sleep(1000);
		    }catch(InterruptedException ie){}
		    nd=lt.header;
		    while(nd!=null){
			nd.setBackground(Color.white);
			nd=nd.next;
                    }
                    if(!win) checkWin();
                }
            }
        }
        else{
            for(j=0;j<bt.con.length;j++){
                p2:if(bt.con[j].fill==1){
                    lt=new list(bt.con[j]);
                    nd=lt.header;
                    while(nd!=null){
                    for(k=0;k<nd.con.length;k++){
                        if(nd.con[k].fill==0) break p2;
                        if(nd.con[k].fill==1&&!lt.isExist(nd.con[k])) lt.insertEnd(nd.con[k]);
                    }
                    nd=nd.next;
                    }
		    try{
			Thread.sleep(1000);
		    }catch(InterruptedException ie){}
                    nd=lt.header;
                    while(nd!=null){
                    nd.setBackground(Color.white);
                    nd.fill=0;
                    nd=nd.next;
                    }
                    if(!win) checkWin();
                }
            }
        }


    }
    void checkWin(){
        int red=0;
        int green=0;
        for(int t=0;t<21;t++){
            if(bt[t].fill==1) red++;
            if(bt[t].fill==2) green++;
        }
        if(red<3)  {JOptionPane.showMessageDialog(f,"Green win!","Win",JOptionPane.PLAIN_MESSAGE);win=true;}
        if(green<3) {JOptionPane.showMessageDialog(f,"Red win!","Win",JOptionPane.PLAIN_MESSAGE);win=true;}
    }
}

class list{
    roundButton header=new roundButton(4,0);
    public list(){
        header.next=null;
    }
    public list(roundButton bt){
        header=bt;
        bt.next=null;
    }
    void insertEnd(roundButton bt){
        lastNode().next=bt;
        bt.next=null;
    }
    roundButton lastNode(){
        roundButton bt=header;
        while(bt.next!=null)
            bt=bt.next;
        return bt;
    }
    boolean isExist(roundButton bt){
        boolean b=false;
        roundButton nd=header;
        while(nd!=null){
            if(nd==bt) {b=true;break;}
            nd=nd.next;
        }
        return b;
    }
}

class random extends Random{
    int r;
    public random(){
        r=nextInt(21);
    }
    public random(int x){
        r=nextInt(x);
    }
}
class gamePanel extends JPanel{

    public void paintComponent(Graphics gg){
        Graphics2D g=(Graphics2D)gg;
        super.paintComponent(g);
	//        add(lb1);
	//        lb1.setBounds(500,0,50,50);
//        lb1.setForeground(Color.red);
//        lb2.setBackground(Color.red);
        g.setStroke(new BasicStroke(5));
        Ellipse2D.Double r1=new Ellipse2D.Double(50,50,500,500);
        Ellipse2D.Double r2=new Ellipse2D.Double(220,220,160,160);
        Arc2D.Double a1=new Arc2D.Double(300-125*Math.sqrt(2),130-250*Math.sqrt(2),250*Math.sqrt(2),250*Math.sqrt(2),227,86,Arc2D.OPEN);
        Arc2D.Double a2=new Arc2D.Double(130-250*Math.sqrt(2),300-125*Math.sqrt(2),250*Math.sqrt(2),250*Math.sqrt(2),43,-86,Arc2D.OPEN);
        Arc2D.Double a3=new Arc2D.Double(300-125*Math.sqrt(2),470,250*Math.sqrt(2),250*Math.sqrt(2),47,86,Arc2D.OPEN);
        Arc2D.Double a4=new Arc2D.Double(470,300-125*Math.sqrt(2),250*Math.sqrt(2),250*Math.sqrt(2),137,86,Arc2D.OPEN);
        g.drawLine(50, 300, 550, 300);
        g.drawLine(300, 50, 300, 550);
        g.draw(r1);
        g.draw(r2);
        g.draw(a1);
        g.draw(a2);
        g.draw(a3);
        g.draw(a4);
    }
}

class roundButton extends JButton {
    int fill=0;
    roundButton[] con;
    roundButton next;
    int index;
    public boolean connnect(roundButton bt){
        boolean b=false;
        for(int i=0;i<con.length;i++){
            if(bt==con[i]) {b=!b;break;}
        }
        return b;
    }
    public void con1(roundButton bt1,roundButton bt2,roundButton bt3){
        con[0]=bt1;
        con[1]=bt2;
        con[2]=bt3;
    }
    public void con2(roundButton bt1,roundButton bt2,roundButton bt3,roundButton bt4){
        con[0]=bt1;
        con[1]=bt2;
        con[2]=bt3;
        con[3]=bt4;
    }

    public roundButton(int a, int b) {
	super();
	con=new roundButton[a];
	setBackground(Color.white);
	Dimension size = getPreferredSize();
	size.width = size.height = Math.max(size.width, size.height);
	setPreferredSize(size);
	setContentAreaFilled(false);
	setBorderPainted(false);
	index=b;
  }


  protected void paintComponent(Graphics g) {
    if (getModel().isArmed()) {

      g.setColor(Color.lightGray);
    } else {
      g.setColor(getBackground());
    }
    g.fillOval(0, 0, getSize().width-1,
      getSize().height-1);

    super.paintComponent(g);
  }


  protected void paintBorder(Graphics g) {
    g.setColor(getForeground());
    g.drawOval(0, 0, getSize().width-1,
      getSize().height-1);
  }


  Shape shape;
  public boolean contains(int x, int y) {

    if (shape == null ||
      !shape.getBounds().equals(getBounds())) {
      shape = new Ellipse2D.Float(0, 0,
        getWidth(), getHeight());
    }
    return shape.contains(x, y);
  }

}
