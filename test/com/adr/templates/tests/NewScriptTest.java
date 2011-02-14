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

package com.adr.templates.tests;

import com.adr.templates.engine.TemplateEval;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author adrian
 */
public class NewScriptTest {

    public NewScriptTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

     @Test
     public void translateTest1() throws Exception {
         
         String template =
                 "Document header\n" +
                 "Nothing goes here: <%# This is a comment %>\n" +
                 "These are expression evaluations: <%= a + b %>, <%= null %>.\n" +
                 "Escape: </% and %/>\n" +
                 "Scripting instructions to repeat 5 times a template section:\n" +
                 "<% for (var i = 0; i < 10; i++) { %>" +
                 "Template section <%= i %>\n" +
                 "<% } %>";

         TemplateEval e = new TemplateEval("javax.script:javascript");
         
         e.put("a", 5);
         e.put("b", 1);
         
         String result = e.eval(template);

         String resultexpected =
                "Document header\n" +
                "Nothing goes here: \n" +
                "These are expression evaluations: 6, null.\n" +
                "Escape: <% and %>\n" +
                "Scripting instructions to repeat 5 times a template section:\n" +
                "Template section 0\n" +
                "Template section 1\n" +
                "Template section 2\n" +
                "Template section 3\n" +
                "Template section 4\n" +
                "Template section 5\n" +
                "Template section 6\n" +
                "Template section 7\n" +
                "Template section 8\n" +
                "Template section 9\n";
         
         Assert.assertEquals(resultexpected, result);
     }     
}