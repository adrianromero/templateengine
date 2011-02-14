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
//    along with Task Executor. If not, see <http://www.gnu.org/licenses/>.

package com.adr.templates.js;

import com.adr.templates.engine.TemplateLanguage;
import com.adr.templates.engine.TemplateScope;
import java.util.Queue;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;

/**
 *
 * @author adrian
 */
public class TemplateLanguageRhino implements TemplateLanguage {

    private ContextFactory factory;

    public TemplateLanguageRhino() {
        factory = ContextFactory.getGlobal();
    }

    @Override
    public String getLanguage() {
        return "javascript";
    }

    @Override
    public TemplateScope createScope() throws Exception {
        return new TemplateScopeRhino(this);
    }

    private void printBuffer(Queue<Integer> q, String s) {
        for (int i = 0; i < s.length(); i++) {
            q.add((int) s.charAt(i));
        }
    }

    private void printBuffer(Queue<Integer> q, char c) {
        q.add((int) c);
    }

    @Override
    public void printTextBegin(Queue<Integer> q) {
        printBuffer(q, "out.print(\"");
    }

    @Override
    public void printText(Queue<Integer> q, char c) {
        if (c == '\"') {
            printBuffer(q, "\\\"");
        } else if (c == '\'') {
            printBuffer(q, "\\\'");
        } else if (c == '\\') {
            printBuffer(q, "\\\\");
        } else if (c == '\b') {
            printBuffer(q, "\\b");
        } else if (c == '\t') {
            printBuffer(q, "\\t");
        } else if (c == '\n') {
            printBuffer(q, "\\n");
        } else if (c == '\f') {
            printBuffer(q, "\\f");
        } else if (c == '\r') {
            printBuffer(q, "\\r");
        } else if (c < ' ') {
            String u = Integer.toHexString(c);
            printBuffer(q, "\\u" + "0000".substring(u.length()) + u);
        } else {
            printBuffer(q, c);
        }
    }

    @Override
    public void printTextEnd(Queue<Integer> q) {
        printBuffer(q, "\");\n");
    }

    @Override
    public void printExpressionBegin(Queue<Integer> q) {
        printBuffer(q, "out.print(String(");
    }

    @Override
    public void printExpression(Queue<Integer> q, char c) {
        printBuffer(q, c);
    }

    @Override
    public void printExpressionEnd(Queue<Integer> q) {
        printBuffer(q, "));\n");
    }

    @Override
    public void printCommandBegin(Queue<Integer> q) {
    }

    @Override
    public void printCommand(Queue<Integer> q, char c) {
        printBuffer(q, c);
    }

    @Override
    public void printCommandEnd(Queue<Integer> q) {
        printBuffer(q, "\n");
    }

    Context enterContext() {
        return factory.enterContext();
    }
}
