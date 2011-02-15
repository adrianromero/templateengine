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

package com.adr.templates.js;

import com.adr.templates.engine.TemplateScope;
import java.io.PrintWriter;
import java.io.Reader;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 *
 * @author adrian
 */
public class TemplateScopeJS implements TemplateScope {

    private static final String SCRIPT_LANGUAGE = "javascript";

    private ScriptEngine engine = null;

    TemplateScopeJS(TemplateLanguageJS lang) throws Exception {

        ScriptEngineManager factory = new ScriptEngineManager();
        // create a JavaScript engine
        engine = factory.getEngineByName(SCRIPT_LANGUAGE);
        if (engine == null) {
            throw new Exception("Script language not supported: " + SCRIPT_LANGUAGE);
        }
    }

    @Override
    public Object eval(Reader code, String name) throws Exception {
        return engine.eval(code);
    }

    @Override
    public void put(String name, Object value) {
        engine.put(name, value);
    }

    @Override
    public void setOut(PrintWriter out) {
        engine.getContext().setWriter(out);
    }
}
