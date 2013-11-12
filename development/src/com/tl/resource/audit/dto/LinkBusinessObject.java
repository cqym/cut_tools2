package com.tl.resource.audit.dto;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 审核关联的业务对象
 */
public class LinkBusinessObject {
	private String id;

	private String name;

	private Map properties = new LinkedHashMap();

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the properties
	 */
	public Map getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(Map properties) {
		this.properties = properties;
	}
	public void addProperty(String name,Object value)
	{
		this.properties.put(name, value);
	}
	public void removeProperty(String name)
	{
		this.properties.remove(name);
	}
	
}
