/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Parser;
import Lexer.Num;
import Lexer.Token;
import Lexer.Lexer;
import Lexer.Real;
import Lexer.Word;
import Lexer.Tag;
import java.io.IOException;
import java.util.Stack;
/**
 *
 * @author Aydin
 */
public class Parser {
    private static int lookahead;
    private static Stack stack = new Stack();
    Lexer l= new Lexer();
    Token token;
    StringBuffer b = new StringBuffer();
    public Parser() throws IOException{
        token =l.scan();
        lookahead = token.tag; 
    }
    public void expr() throws IOException{
        term();
        while(true){
            if(lookahead == '@'){
                match('@');
                term();
                double num1 = Double.parseDouble(stack.pop().toString());
                b.append('-');
                double num= -1*num1;
                stack.push(num);
            }
            else if(lookahead == '+'){
                match('+');
                term();
                double num1 = Double.parseDouble(stack.pop().toString());
                double num2 = Double.parseDouble(stack.pop().toString());
                double sum = num1+num2;
                b.append('+');
                stack.push(sum); 
            }else if(lookahead == '-'){
                match('-');
                term();
                double num1 = Double.parseDouble(stack.pop().toString());
                double num2 = Double.parseDouble(stack.pop().toString());
                double minez=num1-num2;
                b.append('-');
                stack.push(minez); 

            }else return;
        }
    }
    private void term() throws IOException {
       power();
       while(true){
           if(lookahead == '*'){
               match('*');
               power();
               double num1 = Double.parseDouble(stack.pop().toString());
               double num2 = Double.parseDouble(stack.pop().toString());
               double zarb=num1*num2;
               b.append('*');
               stack.push(zarb); 

           }else if(lookahead == '/'){
               match('/');
               power();
               double num1 = Double.parseDouble(stack.pop().toString());
               double num2 = Double.parseDouble(stack.pop().toString());
               double tagsim = num1/num2;
               b.append('/');
               stack.push(tagsim); 

           }else if(lookahead == Tag.mod){//bagi mande
               match(Tag.mod);
               power();
                double num1 = Double.parseDouble(stack.pop().toString());
                double num2 = Double.parseDouble(stack.pop().toString());
                double bagimande=num1%num2;               
                b.append("mod");
                stack.push(bagimande); 

           }else if(lookahead == Tag.div){//khareje gesmat
               match(Tag.div);
               power();
               double num1 = Double.parseDouble(stack.pop().toString());
               double num2 = Double.parseDouble(stack.pop().toString());
               double div=(int)num1/num2; 
               b.append("div");
               stack.push(div); 
           }else return;
       }
    }
    private void power() throws IOException {
        factor();
        powerp();
    }
    private void powerp() throws IOException{
            if(lookahead == '^'){
                match(lookahead);
                power();
                double num1 = Double.parseDouble(stack.pop().toString());
                double num2 = Double.parseDouble(stack.pop().toString());
                double po= Math.pow(num2,num1);
                b.append('^');
                stack.push(po); 
            }else
                return;  
    }
    private void factor() throws IOException {

        while(true){
            if(lookahead == Tag.ID){
                Word w = (Word)token;
                b.append(w.lexeme);
                stack.push(w.getvalue(w.lexeme));
                match(lookahead);
             }else if(lookahead == Tag.NUM){
                Num n = (Num)token;
                b.append(n.value);
                stack.push(n.value);
                match(lookahead);
            }else if(lookahead == Tag.REAL){
                Real r = (Real)token;
                b.append(r.value);
                stack.push(r.value);
                match(lookahead);
            }
             
             else if(lookahead == '('){
                match('(');
                expr();
                match(')');
            }else if(lookahead == Tag.sin){
                match(lookahead);
                factor();
                double num1 = Double.parseDouble(stack.pop().toString());
                double sinc=Math.sin(num1);
                b.append("sin");
                stack.push(sinc); 
            }else if(lookahead == Tag.cos){
                match(lookahead);
                factor();
                double num1 = Double.parseDouble(stack.pop().toString());
                double cosc=Math.cos(num1);
                b.append("cos");
                stack.push(cosc); 
            }else if(lookahead == Tag.tan){
                match(lookahead);
                factor();
                double num1 = Double.parseDouble(stack.pop().toString());
                double tanc=Math.tan(num1);
                b.append("tan");
                stack.push(tanc); 
            }else if(lookahead == Tag.cot){
                match(lookahead);
                factor();    
                double num1 = Double.parseDouble(stack.pop().toString());
                double cotc = 1/( Math.tan(num1));
                b.append("cot");
                stack.push(cotc); 
            }else return;
        }
    }
    private void match(int t) throws IOException { 
        if(lookahead == t){
            token = l.scan();
            lookahead = token.tag;    
        }
        else throw new Error("syntax error");
    }
    public void print() throws IOException{
        String output = b.toString();
        System.out.print("postfix format for your query is: "+output);
        System.out.println();
        System.out.print("output is: "+stack.pop().toString());
        System.out.println();
    }
    
    
}

