
package de.shippie.sunnybridge.model;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "__type", "DateTime", "Kind" })
public class Timestamp
{

	@JsonProperty("__type")
	private String Type;
	@JsonProperty("DateTime")
	private String DateTime;
	@JsonProperty("Kind")
	private String Kind;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The Type
	 */
	@JsonProperty("__type")
	public String getType()
	{
		return Type;
	}

	/**
	 * 
	 * @param Type The __type
	 */
	@JsonProperty("__type")
	public void setType(String Type)
	{
		this.Type = Type;
	}

	/**
	 * 
	 * @return The DateTime
	 */
	@JsonProperty("DateTime")
	public String getDateTime()
	{
		return DateTime;
	}

	/**
	 * 
	 * @param DateTime The DateTime
	 */
	@JsonProperty("DateTime")
	public void setDateTime(String DateTime)
	{
		this.DateTime = DateTime;
	}

	/**
	 * 
	 * @return The Kind
	 */
	@JsonProperty("Kind")
	public String getKind()
	{
		return Kind;
	}

	/**
	 * 
	 * @param Kind The Kind
	 */
	@JsonProperty("Kind")
	public void setKind(String Kind)
	{
		this.Kind = Kind;
	}

	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties()
	{
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value)
	{
		this.additionalProperties.put(name, value);
	}

}
