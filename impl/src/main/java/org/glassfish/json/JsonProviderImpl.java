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

import javax.json.JsonConfiguration;
import javax.json.JsonFeature;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParserFactory;
import javax.json.spi.JsonProvider;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jitendra Kotamraju
 */
public class JsonProviderImpl extends JsonProvider {
    private static final Set<JsonFeature> KNOWN_FEATURES = new HashSet<JsonFeature>();
    static {
        KNOWN_FEATURES.add(JsonFeature.PRETTY_PRINTING);
    }

    @Override
    public JsonGenerator createGenerator(Writer writer) {
        return new JsonGeneratorImpl(writer);
    }


    @Override
    public JsonGenerator createGenerator(OutputStream out) {
        return new JsonGeneratorImpl(out);
    }

    @Override
    public JsonParser createParser(Reader reader) {
        return new JsonParserImpl(reader);
    }

    @Override
    public JsonParser createParser(InputStream in) {
        return new JsonParserImpl(in);
    }

    @Override
    public JsonParserFactory createParserFactory() {
        return new JsonParserFactoryImpl();
    }

    @Override
    public JsonParserFactory createParserFactory(JsonConfiguration config) {
        validateConfiguration(config);
        return new JsonParserFactoryImpl(config);
    }

    @Override
    public JsonGeneratorFactory createGeneratorFactory() {
        return new JsonGeneratorFactoryImpl();
    }

    @Override
    public JsonGeneratorFactory createGeneratorFactory(JsonConfiguration config) {
        validateConfiguration(config);
        return new JsonGeneratorFactoryImpl(config);
    }

    private static void validateConfiguration(JsonConfiguration config) {
        Set<JsonFeature> unknown = null;
        Iterable<JsonFeature> features = config.getFeatures();
        for(JsonFeature feature : features) {
            if (!KNOWN_FEATURES.contains(feature)) {
                if (unknown == null) {
                    unknown = new HashSet<JsonFeature>();
                }
                unknown.add(feature);
            }
        }
        if (unknown != null && !unknown.isEmpty()) {
            throw new IllegalArgumentException("Specified config contains unknown features :"+unknown);
        }
    }

    static boolean isPrettyPrintingEnabled(JsonConfiguration config) {
        Iterable<JsonFeature> features = config.getFeatures();
        for(JsonFeature feature : features) {
            if (feature == JsonFeature.PRETTY_PRINTING)
                return true;
        }
        return false;
    }
}
