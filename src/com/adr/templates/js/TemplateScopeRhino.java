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

import com.adr.templates.engine.TemplateScope;
import java.io.PrintWriter;
import java.io.Reader;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 *
 * @author adrian
 */
public class TemplateScopeRhino implements TemplateScope {

    private TemplateLanguageRhino rhinoparent;
    private Scriptable scope;

    TemplateScopeRhino(TemplateLanguageRhino lang) throws Exception {

        rhinoparent = lang;

        Context cx = rhinoparent.enterContext();
        try {
            scope = cx.initStandardObjects();
        } finally {
            Context.exit();
        }
    }

    @Override
    public Object eval(Reader code, String name) throws Exception {
        Context cx = rhinoparent.enterContext();
        try {
            return cx.evaluateReader(scope, code, name == null ? "<cmd>" : name, 1, null);
        } finally {
            Context.exit();
        }
    }

    @Override
    public void put(String name, Object value) {
        Context cx = rhinoparent.enterContext();
        try {
            ScriptableObject.putProperty(scope, name, Context.javaToJS(value, scope));
         } finally {
            Context.exit();
        }
    }

    @Override
    public void setOut(PrintWriter out) {
        Context cx = rhinoparent.enterContext();
        try {
            ScriptableObject.putProperty(scope, "out", Context.javaToJS(out, scope));
         } finally {
            Context.exit();
        }
    }
}
