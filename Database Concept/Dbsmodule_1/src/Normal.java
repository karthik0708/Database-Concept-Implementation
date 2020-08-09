//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.LinkedHashSet;
//import java.util.Scanner;
//public class Normal {
//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        System.out.println("Give the attributes of relation");
//        String t=in.nextLine();
//        String[] c=t.split(",");
//        String []a=new String[c.length];
//        for(int i=0;i<c.length;i++){
//            a[i]=c[i];
//        }
//        Relation r1=new Relation(a);
//        String s="";
//        ArrayList<FuncDep> dep=new ArrayList<FuncDep>();
//        ArrayList<FuncDep> eme=new ArrayList<FuncDep>();
//        while(true){
//            s="";
//            s=in.nextLine();
//            if(s.equals("stop")){
//                break;
//            }
//            String[] d;
//            d=s.split("->");
//            String[] b = new String[2];
//            for(int i=0;i<d.length;i++){
//                b[i]=d[i];
//            }
//            String [] k;
//            k=b[0].split(",");
//            String [] y=new String[k.length];
//            for(int i=0;i<k.length;i++){
//                y[i]=k[i];
//            }
//            HashSet<String> setx = new HashSet<String>();
//            HashSet<String> esetx = new HashSet<String>();
//            for(int i=0;i<y.length;i++){
//                setx.add(y[i]);
//                esetx.add(y[i]);
//            }
//            String[] n;
//            n=b[1].split(",");
//            String [] m=new String[n.length];
//            for(int i=0;i<n.length;i++){
//                m[i]=n[i];
//            }
//            HashSet<String> sety = new HashSet<String>();
//            HashSet<String> esety = new HashSet<String>();
//            for(int i=0;i<m.length;i++){
//                sety.add(m[i]);
//                esety.add(m[i]);
//            }
//            dep.add(new FuncDep(setx,sety));
//            eme.add(new FuncDep(esetx,esety));
//        }
//        
//        ArrayList<FuncDep> dep1=r1.mincover(dep);
//        ArrayList<String []> z=new ArrayList<String []>();
//        for(int i=0;i<a.length-1;i++){
//            z.add(a);
//        }
//        ArrayList<LinkedHashSet> ck=new ArrayList<LinkedHashSet>(); 
//            for(int j=0;j<a.length;j++){
//                String temp=a[0];
//                for(int i=0;i<a.length-1;i++){
//                    a[i]=a[i+1];
//                }
//                a[a.length-1]=temp;
//                z.add(a);
//                Relation rt=new Relation(a); 
//                LinkedHashSet<String> pk=rt.primkey(dep1);
//                if(!ck.contains(pk)){
//                    ck.add(pk);
//                }
//            }
//        System.out.println("The candidate keys is/are : ");
//        for(LinkedHashSet f:ck){
//            Iterator<String> j=f.iterator();
//            while(j.hasNext()){
//                String ash=j.next();
//                System.out.print(ash);
//            }
//            System.out.print("\n");
//        }
//        r1.isbcnf(eme,ck);
//    }
//}