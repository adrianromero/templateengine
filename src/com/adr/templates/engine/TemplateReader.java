//    Template Engine is a simple template engine.
//    Copyright (C) 2011 Adrián Romero Corchado.
//
//    This file is part of Template Engine
//
//    Template Engine is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    Template Engine is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with Template Engine. If not, see <http://www.gnu.org/licenses/>.

package com.adr.templates.engine;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author adrian
 */
public class TemplateReader extends Reader {

    private TemplateLanguage lang;
    private Reader r;
    // -2 : end reached
    // -1 : ready to start
    //  0 : readint text
    //  1 : read <
    private int state;
    private int statetext;

    private Queue<Integer> buffer;

    public TemplateReader(TemplateLanguage lang, Reader r) {
        this.lang = lang;
        this.r = r;
        state = -1;
        statetext = -1;
        buffer = new LinkedList<Integer>();
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        
        int end = off + len;
        for (int i = off; i < end; i++) {
                int ch = read();
                if (ch == -1) {
                    return i == off ? -1 : i - off;
                }
                cbuf[i] = (char) ch;
        }
        return len;
    }

    @Override
    public void close() throws IOException {
        buffer.clear();
        r.close();
    }
    
    @Override
    public int read() throws IOException {
        while (isBufferEmpty()) {
            translate();
        }
        
        return getNextChar();
    }
                
    private boolean isBufferEmpty() {
        return buffer.isEmpty();
    }
    private int getNextChar() {
        return buffer.remove();
    }
    private void printBufferEnd() {
        buffer.add(-1);
    }
    
    private void translate() throws IOException {
        
        if (state == -2){
            printBufferEnd(); // Trying to read after the end
        } else {
            int c = r.read();
            if (state == -1 && c == -1) {
                printBufferEnd();
                state = -2;
            } else if (state == 0 && c == -1) {
                translateTextEnd();
                printBufferEnd();
                state = -2;
             
            // start tag    
            } else if (state == -1 && c == '<') {
                state = 1;
            } else if (state == 0 && c == '<') {
                state = 11;
            } else if (state == 1 && c == '%') {
                state = 2;      
            } else if (state == 11 && c == '%') {
                translateTextEnd();
                state = 2;                 
            } else if (state == 1) {
                state = 0;
                translateTextBegin();             
                translateText('<');
                translateText((char) c);
            } else if (state == 11) {
                state = 0;
                translateText('<');
                translateText((char) c);               

            // tag comment
            } else if (state == 2 && c == '#') {
                state = 4;
            } else if (state == 4 && c == '%') {
                state = 41;
            } else if (state == 41 && c == '>') {
                state = -1;
            } else if (state == 41) {
                state = 4;
                // nothing
            } else if (state == 4) {
                // nothing        
                
            // tag expression
            } else if (state == 2 && c == '=') {
                state = 3; 
                printExpressionBegin();
            } else if (state == 3 && c == '%') {
                state = 31;
            } else if (state == 31 && c =='>') {
                state = -1;
                printExpressionEnd();
            } else if (state == 31) {
                state = 3;
                printExpression('%');
                printExpression((char) c);
            } else if (state == 3) {
                printExpression((char) c);     
                
            // tag command
            } else if (state == 2) {
                state = 5; 
                printCommandBegin();
                printCommand((char) c);
            } else if (state == 5 && c == '%') {
                state = 51;
            } else if (state == 51 && c == '>') {
                state = -1;
                printCommandEnd();
            } else if (state == 51) {
                state = 5;
                printCommand('%');
                printCommand((char) c);
            } else if (state == 5) {
                printCommand((char) c);   
                
            } else if (state == -1) {                
                translateTextBegin();
                translateText((char) c);
                state = 0;
                
            } else if (state == 0) {
                translateText((char) c);                             
                
            } else {
                throw new IOException("Bad parsing state");
            }                
        }
            
    }
    
    private void translateTextBegin()  throws IOException {
        
        if (statetext != -1) {
            throw new IOException("Bad parsing state");  
        }
        
        statetext = 0;
        printTextBegin();
    }
    
    private void translateText(char c) throws IOException {
        
        if (statetext == 0 && c == '<') {
            statetext = 1;
        } else if (statetext == 1 && c =='/') {
            statetext = 2;
        } else if (statetext == 1) {
            printText('<');
            printText(c);
            statetext = 0;
        } else if (statetext == 2 && c == '%') {
            printText('<');
            printText('%');
            statetext = 0;
        } else if (statetext == 2) {
            printText('<');
            printText('/');
            printText(c);
            statetext = 0;
            
        } else if (statetext == 0 && c == '%') {
            statetext = 11;
        } else if (statetext == 11 && c == '/') {
            statetext = 12;
        } else if (statetext == 11) {
            printText('%');
            printText(c);
            statetext = 0;
        } else if (statetext == 12 && c == '>') {
            printText('%');
            printText('>');
            statetext = 0;
        } else if (statetext == 12) {
            printText('%');
            printText('/');
            printText(c);
            statetext = 0;
                
        } else if (statetext == 0)  {
            printText(c);
            
        } else {
            throw new IOException("Bad parsing state");            
        }
    }
    
    private void translateTextEnd() throws IOException {
        
        if (statetext == 0) {
            // nothing to do
        } else if (statetext == 1) {
            printText('<');
        } else if (statetext == 2) {
            printText('<');
            printText('/');
        } else if (statetext == 11) {
            printText('%');
        } else if (statetext == 12) {
            printText('%');
            printText('/');
        } else {
            throw new IOException("Bad parsing state");      
        }
        
        printTextEnd();
        statetext = -1;   
    }

    private void printTextBegin() {
        lang.printTextBegin(buffer);
    }
    private void printText(char c) {
        lang.printText(buffer, c);
    }
    private void printTextEnd() {
        lang.printTextEnd(buffer);
    }

    private void printExpressionBegin() {
        lang.printExpressionBegin(buffer);
    }
    private void printExpression(char c) {
        lang.printExpression(buffer, c);
    }
    private void printExpressionEnd() {
        lang.printExpressionEnd(buffer);
    }

    private void printCommandBegin() {
        lang.printCommandBegin(buffer);
    }
    private void printCommand(char c) {
        lang.printCommand(buffer, c);
    }
    private void printCommandEnd() {
        lang.printCommandEnd(buffer);
    }
   
}
