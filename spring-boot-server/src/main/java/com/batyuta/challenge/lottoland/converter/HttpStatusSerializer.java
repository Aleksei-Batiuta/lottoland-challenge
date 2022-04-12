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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.http.HttpStatus;

import java.io.IOException;

/**
 * Custom {@link HttpStatus} JSON serializer.
 */
public class HttpStatusSerializer extends StdSerializer<HttpStatus> {

	/**
	 * Field code value name.
	 */
	public static final String FIELD_CODE_VALUE = "codeValue";

	/**
	 * Field code name.
	 */
	public static final String FIELD_CODE = "code";

	/**
	 * Default constructor.
	 */
	public HttpStatusSerializer() {
		super(HttpStatus.class);
	}

	/**
	 * Method that can be called to ask implementation to serialize values of type this
	 * serializer handles.
	 * @param status Value to serialize; can <b>not</b> be null.
	 * @param generator Generator used to output resulting Json content
	 * @param provider Provider that can be used to get serializers for serializing
	 * Objects value contains, if any.
	 */
	@Override
	public void serialize(final HttpStatus status, final JsonGenerator generator, final SerializerProvider provider)
			throws IOException {
		generator.writeStartObject();
		generator.writeFieldName(FIELD_CODE_VALUE);
		generator.writeNumber(status.value());
		generator.writeFieldName(FIELD_CODE);
		generator.writeString(status.getReasonPhrase());
		generator.writeEndObject();
	}

}
