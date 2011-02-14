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

import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

/**
 *
 * @author adrian
 */
public class TemplateEval {
    
//    private ScriptEngine engine = null;
//    private ScriptContext context = null;

    private TemplateLanguage language;
    private TemplateScope scope;

    public TemplateEval(String name) throws Exception {
        this(TemplateLanguageFactory.get(name));
    }

    public TemplateEval(TemplateLanguage language) throws Exception {
        if (language == null) {
            throw new TemplateException("Template language not found.");
        }
        this.language = language;
        this.scope = language.createScope();
    }

    public void put(String key, Object value) {
        scope.put(key, value);
    }
    
    public void eval(Reader r, Writer w) throws Exception {
        scope.setOut(new PrintWriter(w));
        scope.eval(new TemplateReader(language, r), "<template>");
    }
}
