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

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 *
 * @author adrian
 */
public class TemplateLanguageFactory {
    
    public static Map<String, TemplateLanguage> namedlanguages = null;
    
    private TemplateLanguageFactory() {
    }
    
    public static TemplateLanguage get(String name) {

        if (namedlanguages == null) {
             ServiceLoader<TemplateLanguage> service = ServiceLoader.load(TemplateLanguage.class);
             namedlanguages = new HashMap<String, TemplateLanguage>();
             for (TemplateLanguage te : service) {
                 namedlanguages.put(te.getLanguage(), te);
             }
        }

        return namedlanguages.get(name);
    }
}
