/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Lexer;

/**
 *
 * @author Masoud
 */
public class Word extends Token{
    public String lexeme="";
    public  double value;
    public Word(int t , String s){
        super(t);
        lexeme = s; 
    }
    public void setvalue(double v){
        value = v;
    }
    public double getvalue(String lexeme){
        return value;
    }
    public static final Word
            True = new Word(Tag.TRUE,"true"),
            False = new Word(Tag.FALSE,"false");
}
