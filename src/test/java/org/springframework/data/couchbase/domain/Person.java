/*
 * Copyright 2012-2020 the original author or authors
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
package org.springframework.data.couchbase.domain;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.annotation.*;
import org.springframework.data.couchbase.core.mapping.Document;

@Document
@TypeAlias("p") // this will result in p : <className> or p : "t" using the CustomMappingCouchbaseConverter
public class Person extends AbstractEntity {
	Optional<String> firstname;
	Optional<String> lastname;

	@CreatedBy
	private String creator;

	@LastModifiedBy
	private String lastModifiedBy;

	@LastModifiedDate
	private long lastModification;

	@CreatedDate
	private long creationDate;

	@Version
	private long version;

	private Object middlename;

	public Person() {
	}

	public Person(String firstname, String lastname) {
		this();
		setFirstname(firstname);
		setLastname(lastname);
	}

	public Person(int id, String firstname, String lastname) {
		this(firstname, lastname);
		setId(new UUID(id, id));
	}

	static String optional(String name, Optional<String> obj) {
		if (obj != null) {
			if (obj.isPresent()) {
				return ("  " + name + ": '" + obj.get() + "'\n");
			} else {
				return "  " + name + ": null\n";
			}
		}
		return "";
	}

	public Optional<String> getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname == null ? null : (Optional.ofNullable(firstname.equals("") ? null : firstname));
	}

	public void setFirstname(Optional<String> firstname) {
		this.firstname = firstname;
	}

	public Optional<String> getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname == null ? null : (Optional.ofNullable(lastname.equals("") ? null : lastname));
	}

	public void setLastname(Optional lastname) {
		this.lastname = lastname;
	}

	public long getVersion() {
		return version;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Person : {\n");
		sb.append("  id : " + getId());
		sb.append(optional(", firstname", firstname));
		sb.append(optional(", lastname", lastname));
		sb.append(", version : " + version);
		if (creator != null) {
			sb.append(", creator : " + creator);
		}
		if (creationDate != 0) {
			sb.append(", creationDate : " + creationDate);
		}
		if (lastModifiedBy != null) {
			sb.append(", lastModifiedBy : " + lastModifiedBy);
		}
		if (lastModification != 0) {
			sb.append(", lastModification : " + lastModification);
		}
		sb.append("}");
		return sb.toString();
	}

	static String optional(String name, String obj) {
		if (obj != null) {
			if (obj != null /*.isPresent() */) {
				return ("  " + name + ": '" + obj/*.get()*/ + "'\n");
			} else {
				return "  " + name + ": null\n";
			}
		}
		return "";
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}
}
