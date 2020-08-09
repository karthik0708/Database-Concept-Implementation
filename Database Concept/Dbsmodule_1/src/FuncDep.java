import java.util.HashSet;
public class FuncDep {
    HashSet<String> setx = new HashSet<String>();
    HashSet<String> sety = new HashSet<String>();
    FuncDep(HashSet<String> setx,HashSet<String> sety){
        this.setx=setx;
        this.sety=sety;
    }
}
