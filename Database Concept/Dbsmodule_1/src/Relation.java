import static java.lang.reflect.Array.set;
import java.util.*;

public class Relation {
    LinkedHashSet<String> r = new LinkedHashSet<String>();
    String[] x;
    public String normalform="";
    public String candkey="";
    public String decomp="";
    
    Relation(String[] x){
        this.x=x;
        for(int i=0;i<x.length;i++){
            r.add(x[i]);
        }
    }
    
    
    ArrayList<FuncDep> mincover(ArrayList<FuncDep> dep){
        ArrayList<FuncDep> dep1=new ArrayList<FuncDep>();        
        dep1 = (ArrayList)dep.clone();        
        ArrayList<FuncDep> dept=(ArrayList)dep1.clone();        
        for(int i=0;i<dep1.size();i++){
            Iterator<String> j = (dep1.get(i)).sety.iterator();
            while(j.hasNext()){
                if(((dep1.get(i)).sety).size()==1){
                    break;
                }
                HashSet<String> sety2 = new HashSet<String>();
                String r=j.next();
                sety2.add(r);
                j.remove();
                dep1.add(new FuncDep((dep1.get(i)).setx,sety2));
            }
        } 
        for(int i=0;i<dep1.size();i++){
            Iterator<String> j = (dep1.get(i)).setx.iterator();
            ArrayList<FuncDep> temp1=new ArrayList<FuncDep>();
            temp1 = (ArrayList)dep1.clone();
            temp1.remove(dep1.get(i));
            while(j.hasNext()){
               ArrayList<FuncDep> temp2=new ArrayList<FuncDep>();
               HashSet<String> setx2 = new HashSet<String>();
               setx2=(HashSet)dep1.get(i).setx.clone();
               String z=j.next();
               setx2.remove(z);
               temp2.add(new FuncDep(setx2,dep1.get(i).sety));
               Set<FuncDep> temp3 = new LinkedHashSet(temp1);
               temp3.addAll(temp2);
               ArrayList<FuncDep> temp4 = new ArrayList(temp3);
               if(equi(temp4,dep1)){
                   dep1.remove(dep1.get(i));
                   dep1.addAll(temp2);
                   
               }
           }
            
        }
        return dep1;
    }
    boolean equi(ArrayList<FuncDep> p,ArrayList<FuncDep> q){
        for(int i=0;i<p.size();i++){
            HashSet <String> c;
            c=closure((p.get(i)).setx,q);
            for(int j=0;j<q.size();j++){
                if(c.containsAll((q.get(j)).sety)){
                    continue;
                }
                else{
                    return false;
                }
            }
        }
        for(int i=0;i<q.size();i++){
            HashSet <String> c;
            c=closure((q.get(i)).setx,p);
            for(int j=0;j<p.size();j++){
                if(c.containsAll((p.get(j)).sety)){
                    continue;
                }
                else{
                    return false;
                } 
            }
        }
        return true;
    }
    HashSet<String> closure(HashSet<String> h,ArrayList<FuncDep> r){
        HashSet<String> c=new HashSet<String>();
        HashSet<String> oc=new HashSet<String>();
        c.clear();
        c=(HashSet)h.clone();
        do{
            oc.clear();
            oc=(HashSet)c.clone();
            for(int k=0;k<r.size();k++){
                if(c.containsAll((r.get(k)).setx)){
                    c.addAll(r.get(k).sety);
                }
            }
        }while(!oc.equals(c));
        return c;
    }
    LinkedHashSet<String> primkey(ArrayList<FuncDep> h){
        LinkedHashSet<String> k=new LinkedHashSet<String>();
        k=(LinkedHashSet)r.clone();
        Iterator<String> j = k.iterator();
        while(j.hasNext()){
            String u=j.next();
            LinkedHashSet<String> t=(LinkedHashSet)k.clone();
            t.remove(u);
            HashSet<String> t1=new HashSet<String>();
            t1=closure(t,h);
            if(t1.containsAll(r)){
                j.remove();
            }
        }
    return k;
    }
    void isbcnf(ArrayList<FuncDep> dep,ArrayList<LinkedHashSet> ck){
        int cnt=0;
        for(FuncDep fd:dep){
            int flag=0;
            for(LinkedHashSet c:ck){
                if((fd.setx.containsAll(c))){
                    flag=1;
                    break;
                }  
            }
            if(flag==1){
                cnt++;
            } 
        }
        if(cnt!=dep.size()){
            is3nf(dep,ck);
        }
        else{
            this.normalform="Relation is in BCNF";
        }
    }
    void is3nf(ArrayList<FuncDep> dep,ArrayList<LinkedHashSet> ck){
        int cnt=0;
        for(FuncDep fd:dep){
            int flag=0;
            for(LinkedHashSet c:ck){
                if(fd.setx.containsAll(c)){
                    flag=1;
                    break;         
                }
                else{
                    int flag1=0;
                    for(String va :fd.sety){
                        if(c.contains(va)){
                            flag1=1;
                            break;
                        }
                    }
                    if(flag1==1){
                        flag=1;
                        break;
                    }
                }
            }
            if(flag==1){
                cnt++;
            }
        }
        if(cnt!=dep.size()){
            is2nf(dep,ck);
        }
        else{
            this.normalform="Relation is in 3NF"; 
            ctobcnf(dep,ck);
        }
    }
    void is2nf(ArrayList<FuncDep> dep,ArrayList<LinkedHashSet> ck){
        int cnt=0;
        for(FuncDep fd:dep){
            int flag=1;
            for(LinkedHashSet c:ck){
                Iterator <String> yy=c.iterator();
                while(yy.hasNext()){
                    String y=yy.next();
                    if(fd.setx.contains(y) && !fd.setx.containsAll(c)){
                        flag=0;
                        break;
                    }
                }
            }
            if(flag==1){
                cnt++;
            }
        }
        if(cnt!=dep.size()){
            is1nf(dep,ck);
        }
        else{
            this.normalform=("Relation is in 2NF");
            cto3nf(dep,ck);
        }
    }
    void is1nf(ArrayList<FuncDep> dep,ArrayList<LinkedHashSet> ck){
        if(r.isEmpty()){
            this.normalform="Relation is empty";
        }
        else{
            this.normalform="Given Relation is in 1NF";
            cto2nf(dep,ck);
        }
    }
    void cto2nf(ArrayList<FuncDep> dep,ArrayList<LinkedHashSet> ck){
        ArrayList<LinkedHashSet> delation=new ArrayList<LinkedHashSet>();
        LinkedHashSet<String> main=(LinkedHashSet)r.clone();
        LinkedHashSet<String> temp2=new LinkedHashSet<String>();
        for(FuncDep fd:dep){
            int flag=1;
            for(LinkedHashSet c:ck){
                Iterator <String> yy=c.iterator();
                while(yy.hasNext()){
                    String y=yy.next();
                    if(fd.setx.contains(y) && !fd.setx.containsAll(c)){
                        flag=0;
                        break;
                    }
                }
            }
                if(flag==0){
                    LinkedHashSet<String> temp=new LinkedHashSet<String>();
                    temp.addAll(fd.setx);
                    temp.addAll(fd.sety);
                    delation.add(temp);
                    main.removeAll(fd.sety);
                }
                else{
                    temp2.addAll(fd.setx);
                    temp2.addAll(fd.sety);
                }
        }
        System.out.println("Relations and candidate keys with 2NF are: ");
        this.decomp+="Relations and candidate keys with 2NF are: "+"\n";
        if(!delation.containsAll(main)){
            delation.add(main);
        }
        subrel(delation,dep,ck);
    }   
    void cto3nf(ArrayList<FuncDep> dep,ArrayList<LinkedHashSet> ck){
        ArrayList<LinkedHashSet> delation=new ArrayList<LinkedHashSet>();
        LinkedHashSet<String> main=(LinkedHashSet)r.clone();
        ArrayList<FuncDep> dum=mincover(dep);
        HashMap<HashSet<String>,List<HashSet>> map=new HashMap<HashSet<String>,List<HashSet>>();
        for(FuncDep fd:dum){
            HashSet<String> hs=fd.setx;
            if(map.containsKey(hs)){
                List<HashSet> l=map.get(hs);
                l.add(fd.sety);
            }
            else{
                List<HashSet> l=new ArrayList<HashSet>();
                l.add(fd.sety);
                map.put(hs,l);
            }
        }
        Iterator<Map.Entry<HashSet<String>,List<HashSet>>> itr=map.entrySet().iterator();
        while(itr.hasNext()){
            LinkedHashSet<String> xyz=new LinkedHashSet<String>();
            Map.Entry<HashSet<String>,List<HashSet>> entry=itr.next();
            xyz.addAll(entry.getKey());
            for(HashSet no:entry.getValue()){
                xyz.addAll(no);
            }
            if(!delation.containsAll(xyz)){
                delation.add(xyz);
            }
        }
        int flag=0;
        for(LinkedHashSet h:delation){           
            for(LinkedHashSet c:ck){
                if(h.containsAll(c)){
                    flag=1;
                    break;
                }
            }
        }
            if(flag==0){
                LinkedHashSet<String> lma=new LinkedHashSet<String>();
                for(LinkedHashSet c:ck){
                    if(c.size()>1){
                        lma.addAll(c);
                        break;
                    }
                }
                delation.add(lma);
            }
        for(LinkedHashSet de: delation){
            main.removeAll(de);
        }
        if(!main.isEmpty()){
            delation.add(main);
        }
        System.out.println("Relations and candidate keys with 3NF are: ");
        this.decomp+="Relations and candidate keys with 3NF are: "+"\n";
        subrel(delation,dep,ck);

    }
    void ctobcnf(ArrayList<FuncDep> dep,ArrayList<LinkedHashSet> ck){
        ArrayList<LinkedHashSet> delation=new ArrayList<LinkedHashSet>();
        LinkedHashSet<String> main=(LinkedHashSet)r.clone();
        LinkedHashSet<String> temp2=new LinkedHashSet<String>();
        for(FuncDep fd:dep){
            int flag=0;
            for(LinkedHashSet c:ck){
                if(fd.setx.containsAll(c)){
                    flag=1;
                    break;
                }
            }
            if(flag==0){
                LinkedHashSet<String> temp=new LinkedHashSet<String>();
                temp.addAll(fd.setx);
                temp.addAll(fd.sety);
                delation.add(temp);
                temp2.addAll(fd.setx);
                temp2.removeAll(fd.sety);
                main.removeAll(temp2);
                delation.add(main);
            }
        }
        System.out.println("Relations and candidate keys with bCNF are: ");
        this.decomp+="Relations and candidate keys with bCNF are: "+"\n";
        subrel(delation,dep,ck);  
    }
    
    
    void subrel(ArrayList<LinkedHashSet> delation,ArrayList<FuncDep> dep,ArrayList<LinkedHashSet> ck){
        for(LinkedHashSet d:delation){
                String[] arr=new String[d.size()];
                arr=(String[]) d.toArray(arr);
                Relation rc=new Relation(arr);
                ArrayList<FuncDep> depc=rc.mincover(dep);
                ArrayList<String []> zed=new ArrayList<String []>();
                for(int i=0;i<arr.length-1;i++){
                    zed.add(arr);
                }
                ArrayList<LinkedHashSet> sck=new ArrayList<LinkedHashSet>(); 
                for(int j=0;j<arr.length;j++){
                    String temp=arr[0];
                    for(int i=0;i<arr.length-1;i++){
                        arr[i]=arr[i+1];
                    }
                    arr[arr.length-1]=temp;
                    zed.add(arr);
                    Relation rt=new Relation(arr); 
                    LinkedHashSet<String> spk=rt.primkey(depc);
                    if(!sck.contains(spk)){
                        sck.add(spk);
                    }
                }
                System.out.print("decomposed Relation is    :   ");
                this.decomp+="decomposed Relation is    :   ";
                Iterator<String> iter=d.iterator();
                while(iter.hasNext()){
                    String p=iter.next();
                    System.out.print(p);
                    this.decomp+=p;
                }
                this.decomp+="\n";
                System.out.print("\n");
                System.out.print("The candidate key(s) for new relation is/are   :   ");
                this.decomp+="The candidate key(s) for new relation is/are   :   ";
                for(LinkedHashSet f:sck){
                    Iterator<String> j=f.iterator();
                    while(j.hasNext()){
                        String ash=j.next();
                        System.out.print(ash);
                        this.decomp+=ash;
                    }
                    System.out.print("  ");
                    this.decomp+=" ";
                }
                System.out.print("\n");
                System.out.print("\n");
                this.decomp+="\n\n";
            
        }
    }
    
 
}

/*
void cto2nf(ArrayList<FuncDep> dep,ArrayList<LinkedHashSet> ck){
        ArrayList<LinkedHashSet> delation=new ArrayList<LinkedHashSet>();
        LinkedHashSet<String> main=(LinkedHashSet)r.clone();
        for(FuncDep fd:dep){
            LinkedHashSet<String> del=new LinkedHashSet<String>();
            LinkedHashSet<String> buff=new LinkedHashSet<String>();
            for(LinkedHashSet c:ck){
                Iterator <String> yy=c.iterator();
                while(yy.hasNext()){
                    String y=yy.next();
                    if(fd.setx.contains(y) && !fd.setx.containsAll(c)){
                        Iterator <String> xx=fd.setx.iterator();
                        Iterator <String> xy=fd.sety.iterator();
                        while(xx.hasNext()){
                            String lol=xx.next();
                            String lol2="";
                            if(xy.hasNext()){
                                lol2=xy.next();
                            }
                            del.add(lol);
                            del.add(lol2);
                            if(!lol.equals(y)){
                                main.remove(lol);
                            }
                            if(!lol2.equals(y)){
                                main.remove(lol2);
                            }
                        }
                    }
                }
                yy=c.iterator();
            }
            if(!del.isEmpty() && !delation.contains(del)){
                delation.add(del);
            }
        }
        System.out.println("The relations with increased normal form which is:");
        for(String temp2:main){
            System.out.print(temp2);
        }
        System.out.print("\n");
        for(LinkedHashSet x:delation){
            Iterator<String> iter=x.iterator();
            while(iter.hasNext()){
                String p=iter.next();
                System.out.print(p);
            }
            System.out.print("\n");
        }
    }
    void cto3nf(ArrayList<FuncDep> dep,ArrayList<LinkedHashSet> ck){
        ArrayList<LinkedHashSet> delation=new ArrayList<LinkedHashSet>();
        LinkedHashSet<String> main=(LinkedHashSet)r.clone();
        for(FuncDep fd:dep){
            LinkedHashSet<String> del=new LinkedHashSet<String>();
            for(LinkedHashSet c:ck){
                Iterator <String> yy=c.iterator();
                if(!c.equals(fd.sety) && !c.equals(fd.setx)){
                    for(String t:fd.sety){
                        del.add(t);
                        if(!c.contains(t)){
                            main.remove(t);
                        }
                    }
                    for(String t:fd.setx){
                        del.add(t);
                        if(!c.contains(t)){
                            main.remove(t);
                        }
                    }
                    
                }
            }
            if(!del.isEmpty() && !delation.contains(del)){
                delation.add(del);
            }
        }
        System.out.println("The relations with increased normal form is:");
        for(String temp2:main){
            System.out.print(temp2);
        }
        System.out.print("\n");
        for(LinkedHashSet x:delation){
            Iterator<String> iter=x.iterator();
            while(iter.hasNext()){
                String p=iter.next();
                System.out.print(p);
            }
            System.out.print("\n");
        }
    }
    void ctobcnf(ArrayList<FuncDep> dep,ArrayList<LinkedHashSet> ck){
        ArrayList<LinkedHashSet> delation=new ArrayList<LinkedHashSet>();
        LinkedHashSet<String> main=(LinkedHashSet)r.clone();
        for(FuncDep fd:dep){
            LinkedHashSet<String> main2=new LinkedHashSet<String>();
            int flag=0;
            for(LinkedHashSet c:ck){
                if(fd.setx.containsAll(c)){
                    flag=1;
                }
            }
            if(flag==0){
                main2.addAll(fd.setx);
                main2.addAll(fd.sety);
                HashSet<String> temp=(HashSet)fd.sety.clone();
                temp.removeAll(fd.setx);
                main.removeAll(temp);
                if(!main2.isEmpty() && !delation.containsAll(main2)){
                    delation.add(main2);
                }
            }
        }
        for(String temp2:main){
            System.out.print(temp2);
        }
        System.out.print("\n");
        for(LinkedHashSet x:delation){
            Iterator<String> iter=x.iterator();
            while(iter.hasNext()){
                String p=iter.next();
                System.out.print(p);
            }
            System.out.print("\n");
        }
    }
}
//        ArrayList <FuncDep> mcov=mincover(dep);
//        ArrayList<LinkedHashSet> delation=new ArrayList<LinkedHashSet>();
//        LinkedHashSet<String> main=(LinkedHashSet)r.clone();
//        for(LinkedHashSet c:ck){
//            LinkedHashSet<String> temp=new LinkedHashSet<String>();
//            int flag=0;
//            for(FuncDep fd:mcov){
//                if(fd.setx.(c)){
//                    if(flag==0){
//                        temp.addAll(c);
//                        flag=1;
//                    }
//                    temp.addAll(fd.sety);
//                }
//            }
//            delation.add(temp);
//        }
//        for(LinkedHashSet f:delation){
//            main.removeAll(f);
//        }
//        System.out.println("The relations with increased normal form is:");
//        for(String temp2:main){
//            System.out.print(temp2);
//        }
//        System.out.print("\n");
//        for(LinkedHashSet x:delation){
//            Iterator<String> iter=x.iterator();
//            while(iter.hasNext()){
//                String p=iter.next();
//                System.out.print(p);
//            }
//            System.out.print("\n");
//        }
/
min cov bef retrun 
//        for(int j=0;j<dep1.size();j++){
//                System.out.println((dep1.get(j)).setx+"->"+(dep1.get(j)).sety);
//            }
//        for(int i=0;i<dep1.size();i++){
//            ArrayList<FuncDep> temp1=new ArrayList<FuncDep>();
//            temp1 = (ArrayList)dep1.clone();
//            temp1.remove(dep1.get(i));
//            System.out.println("The list before min is:");
//            
//            if(equi(temp1,dep1) && temp1.size()!=0){
//                System.out.println("lol");
//                dep1.remove(dep1.get(i));
//                //break;
//            }
//        }


*
*/