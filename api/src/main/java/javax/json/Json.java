/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2011-2012 Oracle and/or its affiliates. All rights reserved.
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

package javax.json;

import javax.json.spi.JsonProvider;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParserFactory;
import java.io.*;

/**
 * Factory to create {@link JsonParser}, {@link JsonGenerator},
 * {@link JsonParserFactory} and {@link JsonGeneratorFactory} instances.
 * This lists only commonly used methods to create {@link JsonParser}
 * and {@link JsonGenerator} objects, the corresponding factories
 * have all the methods to create these objects.
 *
 * <p>
 * All the methods would locate a provider instance, which is returned by
 * the {@link JsonProvider#provider() provider} method, and is used
 * to create {@link JsonParser}, {@link JsonGenerator}
 * {@link JsonParserFactory} and {@link JsonGeneratorFactory} instances.
 *
 * <p>
 * For example, a JSON parser for parsing an empty array could be created as
 * follows:
 * <pre>
 * <code>
 * StringReader reader = new StringReader("[]");
 * JsonParser parser = Json.createParser(reader);
 * </code>
 * </pre>
 *
 * <p>
 * All of the methods in this class are safe for use by multiple concurrent
 * threads.
 *
 * @author Jitendra Kotamraju
 */
public class Json {

    private Json() {
    }

    /**
     * Creates a JSON parser from the specified character stream
     *
     * @param reader i/o reader from which JSON is to be read
     */
    public static JsonParser createParser(Reader reader) {
        return JsonProvider.provider().createParser(reader);
    }

    /**
     * Creates a JSON parser from the specified byte stream.
     * The character encoding of the stream is determined
     * as per the <a href="http://tools.ietf.org/rfc/rfc4627.txt">RFC</a>.
     *
     * @param in i/o stream from which JSON is to be read
     * @throws JsonException if encoding cannot be determined
     *         or i/o error (IOException would be cause of JsonException)
     */
    public static JsonParser createParser(InputStream in) {
        return JsonProvider.provider().createParser(in);
    }

    /**
     * Creates a JSON generator which can be used to write JSON text to the
     * specified character stream.
     *
     * @param writer a i/o writer to which JSON is written
     */
    public static JsonGenerator createGenerator(Writer writer) {
        return JsonProvider.provider().createGenerator(writer);
    }

    /**
     * Creates a JSON generator which can be used to write JSON text to the
     * specified byte stream.
     *
     * @param out i/o stream to which JSON is written
     */
    public static JsonGenerator createGenerator(OutputStream out) {
        return JsonProvider.provider().createGenerator(out);
    }

    /**
     * Creates a parser factory which can be used to create {@link JsonParser}.
     *
     * @return JSON parser factory
     */
    public static JsonParserFactory createParserFactory() {
        return JsonProvider.provider().createParserFactory();
    }

    /**
     * Creates a parser factory which can be used to create {@link JsonParser}.
     * The created parser factory is configured with the specified
     * configuration
     *
     * @param config configuration of the parser factory
     * @return JSON parser factory
     * @throws IllegalArgumentException if a feature in the configuration
     * is not known
     */
    public static JsonParserFactory createParserFactory(JsonConfiguration config) {
        return JsonProvider.provider().createParserFactory(config);
    }

    /**
     * Creates a generator factory which can be used to create {@link JsonGenerator}.
     *
     * @return JSON generator factory
     */
    public static JsonGeneratorFactory createGeneratorFactory() {
        return JsonProvider.provider().createGeneratorFactory();
    }

    /**
     * Creates a generator factory which can be used to create {@link JsonGenerator}.
     * The created generator factory is configured with the specified
     * configuration.
     *
     * @param config configuration of the generator factory
     * @return JSON generator factory
     * @throws IllegalArgumentException if a feature in the configuration
     * is not known
     */
    public static JsonGeneratorFactory createGeneratorFactory(JsonConfiguration config) {
        return JsonProvider.provider().createGeneratorFactory(config);
    }

}
