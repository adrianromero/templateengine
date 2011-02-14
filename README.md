Simple Template Engine
======================

Simple Template Engine is a java library that combines templates with a data model to produce a result document. It is inspired in the JSP markup language and uses the java scripting functionality included in the java platform since the 1.6 version and can use other scripting languages supported by the java platform. Currenty only supports the javascript language.

Usage
-----

To use the template engine you only need to instantiate the class `TemplateEval` using one of the supported scripting languages:

    TemplateEval e = new TemplateEval("javax.script:javascript");

Set the model to the `TemplateEval` instance.

    e.put("a", 5);
    e.put("b", 1);

Evaluate the template and get the result.

    String result = e.eval(template);

This is a complete example using a test template document.

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
         
         System.out.println(result);

That prints to the console the following result:

    Document header
    Nothing goes here: 
    These are expression evaluations: 6, null.
    Escape: <% and %>
    Scripting instructions to repeat 5 times a template section:
    Template section 0
    Template section 1
    Template section 2
    Template section 3
    Template section 4
    Template section 5
    Template section 6
    Template section 7
    Template section 8
    Template section 9

Markup syntax
-------------

* Comments: `<%# This is a comment. %>`
* Expressions: `<%= a + b %>`
* Scripting: `<% for (var i = 0; i < 10; i++) { %>`
* Escape tags: `</%` and `%/>`

Scripting languages supported
-----------------------------

* Javascript.

`javax.script:javascript`

* Rhino. The javascript implementation in java by Mozilla.

`javascript`

How to add a new scripting language 
-----------------------------------

