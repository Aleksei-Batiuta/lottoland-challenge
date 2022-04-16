/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.batyuta.challenge.lottoland.converter;

import com.batyuta.challenge.lottoland.exception.DataException;
import com.batyuta.challenge.lottoland.vo.LogEntryData;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.IntNode;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/** Json data converter. */
@Service
public class JsonConverter {
    private static final SimpleModule module = new SimpleModule() {
        {
            addDeserializer(HttpStatus.class, new HttpStatusDeserializer(HttpStatus.class));
        }
    };
    /** JSON mapper. */
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper().findAndRegisterModules().registerModule(module);

    public <T> T toObject(final String source, final Class<T> aClass) {
        try {
            return JSON_MAPPER.readValue(source, aClass);
        } catch (JsonProcessingException e) {
            throw new DataException("error.unsupported.operation", e.getMessage(), e);
        }
    }

    public <T> T toObject(final String source, final TypeReference<T> typeReference) {
        try {
            return JSON_MAPPER.readValue(source, typeReference);
        } catch (JsonProcessingException e) {
            throw new DataException("error.unsupported.operation", e.getMessage(), e);
        }
    }

    /**
     * Log Object to JSON serialization.
     *
     * @param object
     *            object
     * @param pretty
     *            output JSON pretty format flag
     *
     * @return String view of message
     */
    public String toString(final Object object, final boolean pretty) {
        return this.toString(object, pretty, 0);
    }

    /**
     * Log Object to JSON serialization.
     *
     * @param object
     *            object
     * @param pretty
     *            output JSON pretty format flag
     * @param depth
     *            depth of call
     *
     * @return String view of message
     */
    private String toString(final Object object, final boolean pretty, final int depth) {
        if (object != null) {
            try {
                if (object instanceof ServletResponse || object instanceof ServletRequest) {
                    // Skips getters of Writer and Reader!
                    return object.toString();
                }
                if (pretty) {
                    return JSON_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(object);
                }
                return JSON_MAPPER.writeValueAsString(object);
            } catch (JsonProcessingException e) {
                if (object instanceof LogEntryData && depth == 0) {
                    LogEntryData message = (LogEntryData) object;
                    Map<String, Object> args = message.getArgs();
                    Map<String, String> result = new HashMap<>();
                    if (args != null) {
                        args.forEach((key, value) -> result.put(key, toString(value, pretty, 1)));
                        args.putAll(result);
                    }
                    message.setResult(toString(message.getResult(), pretty, 1));
                    return toString(message, pretty, 1);
                } else {
                    return object.toString();
                }
            }
        }
        return null;
    }

    static class HttpStatusDeserializer extends StdDeserializer<HttpStatus> {
        protected HttpStatusDeserializer(Class<?> vc) {
            super(vc);
        }

        protected HttpStatusDeserializer(JavaType valueType) {
            super(valueType);
        }

        protected HttpStatusDeserializer(StdDeserializer<?> src) {
            super(src);
        }

        @Override
        public HttpStatus deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            JsonNode node = p.getCodec().readTree(p);
            int id = (Integer) ((IntNode) node.get(HttpStatusSerializer.FIELD_CODE_VALUE)).numberValue();
            return HttpStatus.resolve(id);
        }
    }
}
