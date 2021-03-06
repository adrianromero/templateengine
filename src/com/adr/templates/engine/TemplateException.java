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

/**
 *
 * @author adrian
 */
public class TemplateException extends RuntimeException {

    /**
     * Creates a new instance of <code>TemplateException</code> without detail message.
     */
    public TemplateException() {
    }

    /**
     * Constructs an instance of <code>TemplateException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public TemplateException(String msg) {
        super(msg);
    }
    
    public TemplateException(Throwable t) {
        super(t);
    }    
}
