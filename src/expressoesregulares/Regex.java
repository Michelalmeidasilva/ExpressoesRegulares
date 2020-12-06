package expressoesregulares;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    protected Pattern patternUnion = Pattern.compile("([\\wαβγδζηθι]+)(\\|)([\\wαβγδζηθι]+)");
    protected Pattern patternConcatenate = Pattern.compile("([\\wαβγδζηθι]+)(\\.)([\\wαβγδζηθι]+)");
    protected Pattern patternKleeneClosure = Pattern.compile("([\\wαβγδζηθι]+)(\\*)");
    protected String expression = new String();

    public String getExpression() {
      return expression;
    }

    // α β γ δ ζ η θ ι
    ArrayList<String> varList = new ArrayList<String>(Arrays.asList("0", "0", "0", "0", "0", "0", "0", "0"));
    ArrayList<String> alpha = new ArrayList<String>();
    ArrayList<String> beta = new ArrayList<String>();
    ArrayList<String> gama = new ArrayList<String>();
    ArrayList<String> delta = new ArrayList<String>();
    ArrayList<String> zeta = new ArrayList<String>();
    ArrayList<String> eta = new ArrayList<String>();
    ArrayList<String> theta = new ArrayList<String>();
    ArrayList<String> iota = new ArrayList<String>();

    public  Regex(String expression){
         this.expression = expression;
     }

    public  Regex( ){
    }

    public  ArrayList<String> testCalc(String inp) {
        ArrayList<String> out = new ArrayList<String>();
        expression = inp;
        int validate = 0;
        while (validate != 1) {
            System.out.println("pass "+expression);
            if (patternKleeneClosure.matcher(expression).find()){
                out.addAll(findClosureKleene());
            } else if (patternUnion.matcher(expression).find()) {
                out.addAll(findUnion());
            } else if (patternConcatenate.matcher(expression).find()) {
                out.addAll(findConcatenate());
            } else {
                validate = 1;
            }
        }
        ArrayList<String> list = new ArrayList<String>(new HashSet<>(out));
        Collections.sort(list);
        list.add(0, "ε");
        return list;
    }

     ArrayList<String> defineVariable(String var) {
        ArrayList<String> varList = new ArrayList<String>();
        switch (var) {
            case "α":
                varList.addAll(alpha);
                break;

            case "β":
                varList.addAll(beta);
                break;

            case "γ":
                varList.addAll(gama);
                break;

            case "δ":
                varList.addAll(delta);
                break;
            case "ζ":
                varList.addAll(zeta);
                break;

            case "η":
                varList.addAll(eta);
                break;

            case "θ":
                varList.addAll(theta);
                break;

            case "ι":
                varList.addAll(iota);
                break;

            default:
                varList.addAll(Arrays.asList(var));
                break;
        }
        return varList;
    }

     String findVariable(ArrayList<String> l1) {
        String output = "";
        int ok = 0;
        int i = 0;
        while (ok == 0) {
            if (varList.get(i) == "0") {
                ok = 1;
            } else {
                i++;
            }
        }
        varList.set(i, "1");
        switch (i) {
            case 0:
                alpha.addAll(l1);
                output = "α";
                break;

            case 1:
                beta.addAll(l1);
                output = "β";
                break;

            case 2:
                gama.addAll(l1);
                output = "γ";
                break;

            case 3:
                delta.addAll(l1);
                output = "δ";
                break;

            case 4:
                alpha.addAll(l1);
                output = "ζ";
                break;

            case 5:
                beta.addAll(l1);
                output = "η";
                break;

            case 6:
                gama.addAll(l1);
                output = "θ";
                break;

            case 7:
                delta.addAll(l1);
                output = "ι";
                break;

            default:
                output = "";
                break;
        }
        return output;
    }

    public  ArrayList<String> findUnion() {
        ArrayList<String> output = new ArrayList<String>();
        Matcher matcher = patternUnion.matcher(expression);
        while (matcher.find()) {
             String match = matcher.group();
            String group1 = matcher.group(1);
            String group2 = matcher.group(3);

            output.addAll(Operators.union(defineVariable(group1), defineVariable(group2)));
            expression = expression.replaceFirst("([\\wαβγδζηθι\\(]+)(\\|)([\\wαβγδζηθι\\)]+)", findVariable(output));

             printAux(matcher.start(), matcher.end(), match);

            break;
        }
         printVariables();
        return output;
    }

    public  ArrayList<String> findConcatenate() {
        ArrayList<String> output = new ArrayList<String>();
        Matcher matcher = patternConcatenate.matcher(expression);
        while (matcher.find()) {
             String match = matcher.group();
            String group1 = matcher.group(1);
            String group2 = matcher.group(3);

            output.addAll(Operators.concatenate(defineVariable(group1), defineVariable(group2)));
            expression = expression.replaceFirst("([\\wαβγδζηθι\\(]+)(\\.)([\\wαβγδζηθι\\)]+)", findVariable(output));
             printAux(matcher.start(), matcher.end(), match);
            break;
        }
         printVariables();
        return output;
    }

    public ArrayList<String> findClosureKleene() {
        ArrayList<String> output = new ArrayList<String>();
        Matcher matcher = patternKleeneClosure.matcher(expression);
        while (matcher.find()) {
             String match = matcher.group();
            String group1 = matcher.group(1);

            output.addAll(Operators.kleeneClosure(defineVariable(group1)));
            expression = expression.replaceFirst("([\\wαβγδζηθι]+)(\\*)", findVariable(output));

             printAux(matcher.start(), matcher.end(), match);

            break;
        }
         printVariables();
        return output;
    }

     void printVariables() {
        System.out.println("α = " + alpha);
        System.out.println("β = " + beta);
        System.out.println("γ = " + gama);
        System.out.println("δ = " + delta);
        System.out.println("ζ = " + zeta);
        System.out.println("η = " + eta);
        System.out.println("θ = " + theta);
        System.out.println("ι = " + iota);
    }

     void printAux(int start, int end, String match) {
        System.out.printf("%s - [%d,%d] \t => %s\n", match, start, end, expression);
    }

}
