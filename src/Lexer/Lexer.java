/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Lexer;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author Masoud
 */
public class Lexer {
    public int line=1;
    private char peek = ' ';
    private Hashtable words = new Hashtable();
    Scanner scan = new Scanner(System.in);
    Stack idstack = new Stack();
    String stmt = scan.nextLine(); 
    int i=0;
    //constructor---------------------------------------------------------------
    public Lexer() throws IOException{
        //add reserves keys to hashtable
        reserve(Word.True);
        reserve(Word.False);
        reserve(new Word(Tag.cos , "cos"));
        reserve(new Word(Tag.sin , "sin"));
        reserve(new Word(Tag.div , "div"));
        reserve(new Word(Tag.mod , "mod"));
        reserve(new Word(Tag.tan , "tan"));
        reserve(new Word(Tag.cot , "cot"));
    }
    //--------------------------------------------------------------------------
    void reserve(Word t){
        words.put(t.lexeme,t);
    }
    //--------------------------------------------------------------------------
    
    public Token scan() throws IOException{
        // delete white spaces//////////////////////////////////////////////////.
        
         for(;peek!=';' ; peek =stmt.charAt(i)){
            if(peek == ' '||peek=='\t') 
                continue;
            else if(peek=='\n')
                line = line+1;
            else break;
        }
        //determination of digits///////////////////////////////////////////////
        if(Character.isDigit(peek)){
            int v=0;
            while(Character.isDigit(peek)){     
                v = 10*v + Character.digit(peek, 10);
                peek=stmt.charAt(i+1);
                    i++;
            }
               if(peek != '.'){
                   System.out.println(v);
                    return new Num(v);
                }
                float x = v;
                float d = 10;
                for(;;){
                    peek=stmt.charAt(i+1);
                    i++;
                    if(!Character.isDigit(peek))
                        break;
                    x = x + Character.digit(peek, 10) / d;
                    d = d*10;
        }
         return new Real(x);       
        }
                
        //determination of letters//////////////////////////////////////////////
        if(Character.isLetter(peek)){
            StringBuffer b = new StringBuffer();
            int count=1;
            while(Character.isLetter(peek) && count<=3){      
                    b.append(peek);
                    peek=stmt.charAt(i+1);
                    i++;   
                    count++;   
            }
            String st = b.toString();
            if(st.equals("sin")||st.equals("cos")||
                    st.equals("tan")||st.equals("cot")||
                    st.equals("mod")||st.equals("div")){
                        Word w = (Word)words.get(b.toString());
                        return w;
            }else{
                while(Character.isLetterOrDigit(peek)){
                b.append(peek);
                peek=stmt.charAt(i+1);
                    i++;
                   
                }
                String s = b.toString();
                Word w = (Word)words.get(s);
                if(w !=null)//if w is in symboltable
                    return w;
                else{
                    w = new Word(Tag.ID , s);//if w isn't in symboltable add it in   
                    System.out.print("please enter value of "+w.lexeme+" :");
                    double value = Double.parseDouble(scan.next());
                    w.setvalue(value);
                    words.put(s, w);
                    return w;
                }
            }
        }
        Token t = new Token(peek);
        i++;
        peek=' ';
        return t;
    }
    //--------------------------------------------------------------------------

    
}
