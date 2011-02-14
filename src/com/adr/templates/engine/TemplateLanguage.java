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

package com.adr.templates.engine;

import java.util.Queue;

/**
 *
 * @author adrian
 */
public interface TemplateLanguage {

    public String getLanguage();

    public TemplateScope createScope() throws Exception;

    public void printTextBegin(Queue<Integer> q);
    public void printText(Queue<Integer> q, char c);
    public void printTextEnd(Queue<Integer> q);

    public void printExpressionBegin(Queue<Integer> q);
    public void printExpression(Queue<Integer> q, char c);
    public void printExpressionEnd(Queue<Integer> q);

    public void printCommandBegin(Queue<Integer> q);
    public void printCommand(Queue<Integer> q, char c);
    public void printCommandEnd(Queue<Integer> q);
}
