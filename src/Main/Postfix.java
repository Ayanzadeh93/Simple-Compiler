/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Parser.Parser;
import java.io.IOException;

/**
 *
 * @author Masoud
 */
public class Postfix {
    public static void main(String[] args) throws IOException {
       Parser parse = new Parser();
       parse.expr();
       parse.print();
    }
}
