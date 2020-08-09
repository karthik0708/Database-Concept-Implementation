/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2;

/**
 *
 * @author ashwi
 */
import java.util.*;
import java.lang.*;

import java.awt.FlowLayout;   
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;




class Directory{
	String index;
	Bucket bucket;

	Directory(String index){
		this.index = index;
	}
}



class Bucket{
	String index;
	ArrayList<Integer> data;
	int bfr;
	int lD;

	Bucket(String index,int bfr,int lD){
		this.index = index;
		this.data = new ArrayList<Integer>();
		this.bfr = bfr;
		this.lD = lD;

	}

	boolean Overflow(){
		if(this.data.size()==this.bfr){
			return true;
		}
		else{
			return false;
		}
	}

	void addData(int val){
		if(!this.Overflow()){
			System.out.println("YES NO OVERFLOW");
			this.data.add(val);

		}

		else{
                    System.out.println("THERE IS OVERFLOW");
			if(this.lD<M2.gD){
                                     int flag=0;
                                    for(int i=0;i<this.data.size();i++){
                                        if(data.get(i)==val){
                                            continue;
                                        }
                                        else{
                                            flag=1;
                                            break;
                                        }
                                    }
                                    if(flag==0){
                                        JOptionPane.showMessageDialog(null,"The distribution of keys are skewed , Increase the bfr","Problem",JOptionPane.INFORMATION_MESSAGE);
                                        System.out.println("I can't accomadate this same item more than Bfr");
                                        return;
                                    }
                                    
                                    flag=0;
                                    for(int i=0;i<this.data.size();i++){
                                        if(M2.hash(data.get(i))== M2.hash(val)){
                                            
                                            
                                            continue;
                                        }
                                        else{
                                            flag=1;
                                            break;
                                        }
                                    }
                                    if(flag==0){
                                        JOptionPane.showMessageDialog(null,"The distribution of keys are skewed , Change the hash function","Problem",JOptionPane.INFORMATION_MESSAGE);
                                        System.out.println("I can't accomadate this same item more than Bfr");
                                        return;
                                    }
                                //System.out.println("IM HERE where lD is less than GD");
				this.lD++;

				Bucket b = new Bucket(this.index,this.bfr,this.lD);
                                   
				b.index = "1"+this.index;
				this.index = "0"+this.index;
				M2.buck.add(b);
				ArrayList<Directory> dur= new ArrayList<Directory>();
				for(int i=0;i<M2.dir.size();i++){
					Directory dar = M2.dir.get(i);
					if(dar.bucket == this)
						dur.add(dar);	
				}
				for(Directory a:dur){

					for(Bucket bA:M2.buck){
						String indu = bA.index;
                                                String compare = a.index.substring(a.index.length()-indu.length());
//						
                                                if(compare.equals(indu)){
                                                    a.bucket=bA;
                                                    break;
                                                }
                                              
                                        }            
                                        }


				for(int i=0;i<this.data.size();i++){
					int send = data.get(i);

					int ans = send%23;
					String check = Integer.toBinaryString(ans);
                                        while(check.length()<this.lD){
                                            check = "0"+check;
                                        }
					String ind = this.index;
                                        System.out.println("CHECK - " +check+ " and index of buck is "+ this.index );
                                        String compare = check.substring(check.length()-ind.length());
//					
                                        if(compare.equals(this.index))
                                            continue;
                                        
                                        else{
                                            b.data.add(send);
                                            this.data.remove(new Integer(ans));
                                        }
				}
                                
                                String check = Integer.toBinaryString(val);
                                while(check.length()<b.index.length()){
                                    check="0"+check;
                                }
                                String compare = check.substring(check.length()-b.index.length());
                                if(compare.equals(b.index)){
                                    System.out.println("OH");
                                    b.addData(val);
                                    
                                }
                                    
                                else{
                                    System.out.println("I added it in "+ this.index);
                                    this.addData(val);
                                }
                                
                                

			}
			else{
				double times = Math.pow(2,M2.gD);
				M2.gD++;
				for(int i =0;i<times;i++){
					Directory d = M2.dir.get(i);
					Directory dup = new Directory(d.index);
					dup.index = "1"+d.index;
					d.index = "0"+ d.index;
					dup.bucket = d.bucket;
                                        M2.dir.add(dup);
				}

				this.addData(val);

			}
		}
	}
	

}


public class M2{

public static int gD;
public static int lD;
public static int bfr;
public static ArrayList<Bucket> buck;
public static ArrayList<Directory> dir;

        public static int hash(int a){
            return (a%10);
        }    
        
	public static void main(String args[]){
		buck = new ArrayList<Bucket>();
		dir= new ArrayList<Directory>();
		gD = 2;
		lD = 1;
                bfr=2;
		for(int i=0;i<Math.pow(2,lD);i++){
                        String omg = Integer.toBinaryString(i);
                        while(omg.length()<lD){
                           omg = "0"+omg;
                        }
			Bucket b = new Bucket(omg,bfr,lD);
                        System.out.println("AWESOME RIGHT");
			buck.add(b);
		}
		for(int i=0;i<Math.pow(2,gD);i++){
                        String omg = Integer.toBinaryString(i);
                        
                        while(omg.length()<gD){
                            omg = "0"+omg;
                        }
			Directory d = new Directory(omg);

			for(Bucket b:buck){
				String indu = b.index;
                                int lengthofDir = d.index.length();
                                String compare = d.index.substring(lengthofDir-indu.length());
//				
                                //System.out.println("DIR - "+compare + "BUCKET "+ indu);
                                if(compare.equals(b.index)){
                                    //System.out.println("YES");
                                    d.bucket=b;
                                    break;
                                }
                               
			}
                        System.out.println("AWESOME"+ i);
			dir.add(d);
		}  
                
                JFrame frame = new JFrame("Module 2");
                JPanel panel = new JPanel();
                panel.setLayout(new FlowLayout());
                
                JLabel label = new JLabel("Give in your entries");
                JLabel globD = new JLabel("Global Depth is "+gD);
                

                
                
                JButton button = new JButton();
                JButton clear = new JButton();
                JButton dbcl = new JButton();
                JTextField tf = new JTextField(" (Integers Only)  ");
                Rectangle r = new Rectangle();
                r.setSize(30,200);
                tf.setBounds(r);
                button.setText("Insert");
                clear.setText("Clear Table");
                dbcl.setText("Clear Database");
                
                panel.add(label);
                panel.add(tf);
                panel.add(button);
                panel.add(clear);
                panel.add(dbcl);
//                
                
                JTable dirtab = new JTable();
                JScrollPane contDir = new JScrollPane(dirtab);
                Object[] col = {"Directories","Bucket"};
                DefaultTableModel mod =  new DefaultTableModel();
                mod.setColumnIdentifiers(col);
                dirtab.setModel(mod);
                
                
                 
                JTable bucktab = new JTable();
                JScrollPane contBuck = new JScrollPane(bucktab);
                Object[] col2 = new Object[bfr+1];
                col2[0] ="Bucket";
                int jk=1;
                while(jk<=bfr){
                    col2[jk]= "Data "+jk;
                  jk++;
               }
                DefaultTableModel mod2 =  new DefaultTableModel();
                mod2.setColumnIdentifiers(col2);
                bucktab.setModel(mod2);
                
                panel.add(contDir);
                panel.add(contBuck);


                frame.add(panel);
                frame.setSize(1500,500);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                ActionListener al;
                ActionListener ae;
                ActionListener db;
                
                ae = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                        mod.setRowCount(0);
                        mod2.setRowCount(0);
                        
                        
                    }
                };
                
                
                
                
                db = new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        mod.setRowCount(0);
                        mod2.setRowCount(0);
                        
                        
                        buck = new ArrayList<Bucket>();
		dir= new ArrayList<Directory>();
		
		for(int i=0;i<Math.pow(2,lD);i++){
                       String omg = Integer.toBinaryString(i);
                       while(omg.length()<lD){
                            omg = "0"+omg;
                        }
			Bucket b = new Bucket(omg,bfr,lD);
			buck.add(b);
		}
		for(int i=0;i<Math.pow(2,gD);i++){
                        String omg = Integer.toBinaryString(i);
                        while(omg.length()<gD){
                            omg = "0"+omg;
                        }
			Directory d = new Directory(omg);

			for(Bucket b:buck){
				String indu = b.index;
                                int lengthofDir = d.index.length();
                                String compare = d.index.substring(lengthofDir-indu.length());
//				
                                //System.out.println("DIR - "+compare + "BUCKET "+ indu);
                                if(compare.equals(b.index)){
                                    //System.out.println("YES");
                                    d.bucket=b;
                                    break;
                                }
                               
			}
			dir.add(d);
		}  
                
                     Object[] row = new Object[2];
            mod.addRow(row);
            for(Directory d:dir){
                row[0] = d.index;
                row[1]=d.bucket.index;
                mod.addRow(row);
            }
            
            Object[] row2 = new Object[1+bfr];
            mod2.addRow(row2);
            for(Bucket b:buck){
                
                row2[0]=b.index;
                int t=1;
                for(int i=0;i<b.data.size();i++){
                    row2[t]=b.data.get(i).toString();
                    t++;
                }
                mod2.addRow(row2);
            }
                        
                    }
                    
                };
                
              
                
                
                
    al = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String tex = tf.getText();
            tf.setText("");
            System.out.println(tex);
            int anss = Integer.parseInt(tex);
            
            int ans = hash(anss);
            
            String check = Integer.toBinaryString(ans);
            
            
            for(int k=0;k<buck.size();k++){
                
                Bucket insbuck = buck.get(k);
                String Buckind = insbuck.index;
                while(check.length()<Buckind.length()){
                    check="0"+check;
                }
                String compare = check.substring(check.length()-Buckind.length());
//
            if(compare.equals(Buckind)){
                insbuck.addData(anss);
                break;
             }
            else
                continue;

            }
            Object[] row = new Object[2];
            mod.addRow(row);
            for(Directory d:dir){
                row = new Object[2];
                row[0] = d.index;
                row[1]=d.bucket.index;
                mod.addRow(row);
            }
            
            Object[] row2 = new Object[1+bfr];
            mod2.addRow(row2);
            for(Bucket b:buck){
                row2 = new Object[1+bfr];
                row2[0]=b.index;
                int t=1;
                for(int i=0;i<b.data.size();i++){
                    row2[t]=b.data.get(i).toString();
                    t++;
                }
                mod2.addRow(row2);
            }
            
            
//            for(int i=0;i<dir.size();i++){
//                Directory one = dir.get(i);
//                column[i][0]= one.index;
//                column[i][1]= one.bucket.index;
//            }
//            
//            
//            int k=0;
//            for(Bucket b:buck){
//                row[k][0]=b.index;
//                for(int j=0;j<b.data.size();j++){
//                    row[k][j+1]=b.data.get(j).toString();
//                }
//                k++;
//            }


//                JTable buckya = new JTable(row,cool2);
//                buckya.setBounds(30,40,200,300);
//                JScrollPane sp22 = new JScrollPane(buckya);
//                
//                frame.add(sp22);    
            
            
            
            
            
            
            
            
            
            System.out.println("GD = "+ gD);
            for(int i=0;i<dir.size();i++){
                Directory z = dir.get(i);
                System.out.println("DIRECTORY - " + z.index + " points to Bucket - " +z.bucket.index);
                
            }
            for(int i=0;i<buck.size();i++){
                Bucket b = buck.get(i);
                System.out.println("BUCKET - " + b.index + "LD - " +b.lD + "Bfr = "+b.bfr );
                System.out.println("DATA : ");
                
                for(int j=0;j<b.data.size();j++){
                    
                    System.out.println(b.data.get(j));
                }
                
            }
            
        }
    };
                button.addActionListener(al);
                clear.addActionListener(ae);
                dbcl.addActionListener(db);
		Scanner sc = new Scanner(System.in);
		//gD=2;
		//lD=1;b
		//dir[] = new Directory[2^gD];

		while(true){

			int a = sc.nextInt();

			

		}


	

	}
}
