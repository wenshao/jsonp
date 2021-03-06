/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2012 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package org.glassfish.json;

import junit.framework.TestCase;
import org.w3c.dom.Document;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;

/**
 * @author Jitendra Kotamraju
 */
public class JsonReaderTest extends TestCase {
    public JsonReaderTest(String testName) {
        super(testName);
    }

    public void testObject() throws Exception {
        JsonObject person = readPerson();
        JsonObjectTest.testPerson(person);
    }

    public void testEscapedString() throws Exception {
        // u00ff is escaped once, not escaped once
        JsonReader reader = new JsonReader(new StringReader("[\"\\u0000\\u00ff\u00ff\"]"));
        JsonArray array = reader.readArray();
        reader.close();
        String str = array.getValue(0, JsonString.class).getValue();
        assertEquals("\u0000\u00ff\u00ff", str);
    }

    public void testUnknownFeature() throws Exception {
        JsonConfiguration config = new JsonConfiguration().with(new JsonFeature() {
        });
        try {
            JsonReader reader = new JsonReader(new StringReader("{}"), config);
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // no-op
        }
    }

    public void testIllegalStateExcepton() throws Exception {
        JsonReader reader = new JsonReader(new StringReader("{}"));
        JsonObject obj = reader.readObject();
        try {
            reader.readObject();
        } catch (IllegalStateException expected) {
            // no-op
        }
        reader.close();

        reader = new JsonReader(new StringReader("[]"));
        JsonArray array = reader.readArray();
        try {
            reader.readArray();
        } catch (IllegalStateException expected) {
            // no-op
        }
        reader.close();

        reader = new JsonReader(new StringReader("{}"));
        JsonStructure struct = reader.read();
        try {
            reader.read();
        } catch (IllegalStateException expected) {
            // no-op
        }
        reader.close();
    }


    static JsonObject readPerson() throws Exception {

        Reader wikiReader = new InputStreamReader(JsonReaderTest.class.getResourceAsStream("/wiki.json"));
        JsonReader reader = new JsonReader(wikiReader);
        JsonValue value = reader.readObject();
        reader.close();

        assertTrue(value instanceof JsonObject);
        return (JsonObject) value;
    }

}
