//Nahome Kifle
//cmsc256
//3/30/2023
//project 5

package cmsc256;
import bridges.connect.Bridges;
import bridges.base.BinTreeElement;

import java.util.Stack;

public class Project5 {
    public static void main(String[]args) throws Exception{
        //Bridges API main method, connecst to bridges using username and ID
        Bridges bridges = new Bridges(5,"Nahome1212","485728358356"); // i think this is supposed to say the project number
        bridges.setTitle("Project 5 - Debra Duke");
        bridges.setDescription("CMSC 256");

    }
    public static bridges.base.BinTreeElement<String> buildParseTree(String expression){
        // this method parses the expression into a tree
        BinTreeElement<String> parsedTreeRoot = new BinTreeElement<>("root", "");
        BinTreeElement<String> current = parsedTreeRoot;
        Stack<BinTreeElement> stackTree = new Stack<>();
        String[] splitExpression = expression.split(" ");  // splits the expression using all of the empty spaces

// for loop to go through the new String array
        for(int i = 0; i < splitExpression.length ; i++){

            if(splitExpression[i].equals("(")){ // if the value is an opneing parenthesis then you will add to the left child
                current.setLeft(new BinTreeElement<>("l","e"));
                stackTree.push(current);
                current = current.getLeft();
            } else if(splitExpression[i].equals("+") || splitExpression[i].equals("-") || splitExpression[i].equals("*") || splitExpression[i].equals("/")){
                current.setValue(splitExpression[i]);// if the value is a operator then you will add a new node to the right child and the current becomes the the right child
                current.setLabel(splitExpression[i]);

                current.setRight(new BinTreeElement<>("r", "e")); // current set to the right
                stackTree.push(current);
                current = current.getRight();
            } else if(canBeParsed(splitExpression[i])){ // if the value is a number
                     // if the the catch block didnt need to execute then it is a number and we continue
                        current.setValue(splitExpression[i]); // set the current value and label to the value
                        current.setLabel(splitExpression[i]);
                if(!stackTree.empty()){
                    current = stackTree.pop();
                }

            } else if(splitExpression[i].equals(")")){
                if(!stackTree.empty()){
                    current = stackTree.pop();
                }

            }
            else{
                throw new IllegalArgumentException();
            }

        }
        return parsedTreeRoot;
    }
    public static double evaluate(bridges.base.BinTreeElement<String> tree){
     if(tree.getValue() == null){  // check if the root is null
         return 0.0;
     }
     if(tree.getLeft() == null && tree.getRight() == null){  // recursive call to keep getting the left and right childs
         return Double.parseDouble(tree.getValue());
     }

      double left = evaluate(tree.getLeft()); // recursive call to keep getting the left and right childs
      double right = evaluate(tree.getRight());
String op = tree.getValue();
      switch (op){
          case "+": {
              return left + right; // adding .....
          }
          case "-":{
              return left - right;
          }
          case "*": {
              return left * right;
          }
          case "/": {
              if(!(left == 0) && !(right == 0)){
                  return left / right;
              }else {
                  throw new ArithmeticException();
              }
          }
          default: {
              throw new IllegalArgumentException(); // default throws an expection
          }

      }




    }

    public static String getEquation(bridges.base.BinTreeElement<String> tree){
        if(canBeParsed(tree.getValue())){ // if it can be parsed so it isnt an operation
            return tree.getValue();
        }else {
            String l = getEquation(tree.getLeft()); // recursive call to itself
            String r = getEquation(tree.getRight());

            return "( " + l + " " + tree.getValue() + " " + r + " )"; // building the string equation
        }
    }
    public static boolean canBeParsed(String s) { // method to see if the str is a number
        if (s == null) {
            return false; // check to see if the string is null
        }
        try {
            double d = Double.parseDouble(s); // if you can parse it then it is a number
        } catch (NumberFormatException e) { // if not throw an exception
            return false;
        }
        return true;
    }
}
