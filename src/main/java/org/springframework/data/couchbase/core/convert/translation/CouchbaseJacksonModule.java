/*
 * Copyright 2020 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.couchbase.core.convert.translation;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.data.couchbase.core.mapping.CouchbaseDocument;
import org.springframework.data.couchbase.core.mapping.CouchbaseList;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author spac-valentin
 * @author Michael Reiche
 */
public class CouchbaseJacksonModule extends SimpleModule {

	public CouchbaseJacksonModule() {
		super(new Version(1, 0, 0, null, "com.couchbase", "CouchbaseDocumentAndList"));
		addSerializer(CouchbaseDocument.class, new CouchbaseDocumentSerializer());
		addSerializer(CouchbaseList.class, new CouchbaseListSerializer());
	}

	public static class CouchbaseDocumentSerializer extends JsonSerializer<CouchbaseDocument> {

		@Override
		public void serialize(CouchbaseDocument value, JsonGenerator gen, SerializerProvider serializers) {
			try {
				Map<String, Object> valueContent = value.getContent();
				JsonSerializer<Object> serializer = serializers.findValueSerializer(valueContent.getClass());
				serializer.serialize(valueContent, gen, serializers);
			} catch (IOException ioe) {
				throw new RuntimeException((ioe));
			}
		}
	}

	public static class CouchbaseListSerializer extends JsonSerializer<CouchbaseList> {
		@Override
		public void serialize(CouchbaseList value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
			List<Object> objects = value.export();
			JsonSerializer<Object> serializer = serializers.findValueSerializer(objects.getClass());
			serializer.serialize(objects, gen, serializers);
		}
	}
}
