
package de.shippie.sunnybridge.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "__type", "Timestamp", "PV", "FeedIn", "GridConsumption", "DirectConsumption", "SelfConsumption",
		"SelfSupply", "TotalConsumption", "DirectConsumptionQuote", "SelfConsumptionQuote", "AutarkyQuote", "BatteryIn",
		"BatteryOut", "BatteryChargeStatus", "OperationHealth", "BatteryStateOfHealth", "ModuleTemperature",
		"EnvironmentTemperature", "WindSpeed", "Insolation", "InfoMessages", "WarningMessages", "ErrorMessages",
		"Info" })
public class PortalDataResult
{

	@JsonProperty("__type")
	private String Type;
	@JsonProperty("Timestamp")
	private de.shippie.sunnybridge.model.Timestamp Timestamp;
	@JsonProperty("PV")
	private Object PV;
	@JsonProperty("FeedIn")
	private Object FeedIn;
	@JsonProperty("GridConsumption")
	private Object GridConsumption;
	@JsonProperty("DirectConsumption")
	private Object DirectConsumption;
	@JsonProperty("SelfConsumption")
	private Object SelfConsumption;
	@JsonProperty("SelfSupply")
	private Object SelfSupply;
	@JsonProperty("TotalConsumption")
	private Object TotalConsumption;
	@JsonProperty("DirectConsumptionQuote")
	private Object DirectConsumptionQuote;
	@JsonProperty("SelfConsumptionQuote")
	private Object SelfConsumptionQuote;
	@JsonProperty("AutarkyQuote")
	private Object AutarkyQuote;
	@JsonProperty("BatteryIn")
	private Object BatteryIn;
	@JsonProperty("BatteryOut")
	private Object BatteryOut;
	@JsonProperty("BatteryChargeStatus")
	private Object BatteryChargeStatus;
	@JsonProperty("OperationHealth")
	private Object OperationHealth;
	@JsonProperty("BatteryStateOfHealth")
	private Object BatteryStateOfHealth;
	@JsonProperty("ModuleTemperature")
	private Object ModuleTemperature;
	@JsonProperty("EnvironmentTemperature")
	private Object EnvironmentTemperature;
	@JsonProperty("WindSpeed")
	private Object WindSpeed;
	@JsonProperty("Insolation")
	private Object Insolation;
	@JsonProperty("InfoMessages")
	private List<String> InfoMessages = new ArrayList<String>();
	@JsonProperty("WarningMessages")
	private List<String> WarningMessages = new ArrayList<String>();
	@JsonProperty("ErrorMessages")
	private List<String> ErrorMessages = new ArrayList<String>();
	@JsonProperty("Info")
	private de.shippie.sunnybridge.model.Info Info;
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
	 * @return The Timestamp
	 */
	@JsonProperty("Timestamp")
	public de.shippie.sunnybridge.model.Timestamp getTimestamp()
	{
		return Timestamp;
	}

	/**
	 * 
	 * @param Timestamp The Timestamp
	 */
	@JsonProperty("Timestamp")
	public void setTimestamp(de.shippie.sunnybridge.model.Timestamp Timestamp)
	{
		this.Timestamp = Timestamp;
	}

	/**
	 * 
	 * @return The PV
	 */
	@JsonProperty("PV")
	public Object getPV()
	{
		return PV;
	}

	/**
	 * 
	 * @param PV The PV
	 */
	@JsonProperty("PV")
	public void setPV(Object PV)
	{
		this.PV = PV;
	}

	/**
	 * 
	 * @return The FeedIn
	 */
	@JsonProperty("FeedIn")
	public Object getFeedIn()
	{
		return FeedIn;
	}

	/**
	 * 
	 * @param FeedIn The FeedIn
	 */
	@JsonProperty("FeedIn")
	public void setFeedIn(Object FeedIn)
	{
		this.FeedIn = FeedIn;
	}

	/**
	 * 
	 * @return The GridConsumption
	 */
	@JsonProperty("GridConsumption")
	public Object getGridConsumption()
	{
		return GridConsumption;
	}

	/**
	 * 
	 * @param GridConsumption The GridConsumption
	 */
	@JsonProperty("GridConsumption")
	public void setGridConsumption(Object GridConsumption)
	{
		this.GridConsumption = GridConsumption;
	}

	/**
	 * 
	 * @return The DirectConsumption
	 */
	@JsonProperty("DirectConsumption")
	public Object getDirectConsumption()
	{
		return DirectConsumption;
	}

	/**
	 * 
	 * @param DirectConsumption The DirectConsumption
	 */
	@JsonProperty("DirectConsumption")
	public void setDirectConsumption(Object DirectConsumption)
	{
		this.DirectConsumption = DirectConsumption;
	}

	/**
	 * 
	 * @return The SelfConsumption
	 */
	@JsonProperty("SelfConsumption")
	public Object getSelfConsumption()
	{
		return SelfConsumption;
	}

	/**
	 * 
	 * @param SelfConsumption The SelfConsumption
	 */
	@JsonProperty("SelfConsumption")
	public void setSelfConsumption(Object SelfConsumption)
	{
		this.SelfConsumption = SelfConsumption;
	}

	/**
	 * 
	 * @return The SelfSupply
	 */
	@JsonProperty("SelfSupply")
	public Object getSelfSupply()
	{
		return SelfSupply;
	}

	/**
	 * 
	 * @param SelfSupply The SelfSupply
	 */
	@JsonProperty("SelfSupply")
	public void setSelfSupply(Object SelfSupply)
	{
		this.SelfSupply = SelfSupply;
	}

	/**
	 * 
	 * @return The TotalConsumption
	 */
	@JsonProperty("TotalConsumption")
	public Object getTotalConsumption()
	{
		return TotalConsumption;
	}

	/**
	 * 
	 * @param TotalConsumption The TotalConsumption
	 */
	@JsonProperty("TotalConsumption")
	public void setTotalConsumption(Object TotalConsumption)
	{
		this.TotalConsumption = TotalConsumption;
	}

	/**
	 * 
	 * @return The DirectConsumptionQuote
	 */
	@JsonProperty("DirectConsumptionQuote")
	public Object getDirectConsumptionQuote()
	{
		return DirectConsumptionQuote;
	}

	/**
	 * 
	 * @param DirectConsumptionQuote The DirectConsumptionQuote
	 */
	@JsonProperty("DirectConsumptionQuote")
	public void setDirectConsumptionQuote(Object DirectConsumptionQuote)
	{
		this.DirectConsumptionQuote = DirectConsumptionQuote;
	}

	/**
	 * 
	 * @return The SelfConsumptionQuote
	 */
	@JsonProperty("SelfConsumptionQuote")
	public Object getSelfConsumptionQuote()
	{
		return SelfConsumptionQuote;
	}

	/**
	 * 
	 * @param SelfConsumptionQuote The SelfConsumptionQuote
	 */
	@JsonProperty("SelfConsumptionQuote")
	public void setSelfConsumptionQuote(Object SelfConsumptionQuote)
	{
		this.SelfConsumptionQuote = SelfConsumptionQuote;
	}

	/**
	 * 
	 * @return The AutarkyQuote
	 */
	@JsonProperty("AutarkyQuote")
	public Object getAutarkyQuote()
	{
		return AutarkyQuote;
	}

	/**
	 * 
	 * @param AutarkyQuote The AutarkyQuote
	 */
	@JsonProperty("AutarkyQuote")
	public void setAutarkyQuote(Object AutarkyQuote)
	{
		this.AutarkyQuote = AutarkyQuote;
	}

	/**
	 * 
	 * @return The BatteryIn
	 */
	@JsonProperty("BatteryIn")
	public Object getBatteryIn()
	{
		return BatteryIn;
	}

	/**
	 * 
	 * @param BatteryIn The BatteryIn
	 */
	@JsonProperty("BatteryIn")
	public void setBatteryIn(Object BatteryIn)
	{
		this.BatteryIn = BatteryIn;
	}

	/**
	 * 
	 * @return The BatteryOut
	 */
	@JsonProperty("BatteryOut")
	public Object getBatteryOut()
	{
		return BatteryOut;
	}

	/**
	 * 
	 * @param BatteryOut The BatteryOut
	 */
	@JsonProperty("BatteryOut")
	public void setBatteryOut(Object BatteryOut)
	{
		this.BatteryOut = BatteryOut;
	}

	/**
	 * 
	 * @return The BatteryChargeStatus
	 */
	@JsonProperty("BatteryChargeStatus")
	public Object getBatteryChargeStatus()
	{
		return BatteryChargeStatus;
	}

	/**
	 * 
	 * @param BatteryChargeStatus The BatteryChargeStatus
	 */
	@JsonProperty("BatteryChargeStatus")
	public void setBatteryChargeStatus(Object BatteryChargeStatus)
	{
		this.BatteryChargeStatus = BatteryChargeStatus;
	}

	/**
	 * 
	 * @return The OperationHealth
	 */
	@JsonProperty("OperationHealth")
	public Object getOperationHealth()
	{
		return OperationHealth;
	}

	/**
	 * 
	 * @param OperationHealth The OperationHealth
	 */
	@JsonProperty("OperationHealth")
	public void setOperationHealth(Object OperationHealth)
	{
		this.OperationHealth = OperationHealth;
	}

	/**
	 * 
	 * @return The BatteryStateOfHealth
	 */
	@JsonProperty("BatteryStateOfHealth")
	public Object getBatteryStateOfHealth()
	{
		return BatteryStateOfHealth;
	}

	/**
	 * 
	 * @param BatteryStateOfHealth The BatteryStateOfHealth
	 */
	@JsonProperty("BatteryStateOfHealth")
	public void setBatteryStateOfHealth(Object BatteryStateOfHealth)
	{
		this.BatteryStateOfHealth = BatteryStateOfHealth;
	}

	/**
	 * 
	 * @return The ModuleTemperature
	 */
	@JsonProperty("ModuleTemperature")
	public Object getModuleTemperature()
	{
		return ModuleTemperature;
	}

	/**
	 * 
	 * @param ModuleTemperature The ModuleTemperature
	 */
	@JsonProperty("ModuleTemperature")
	public void setModuleTemperature(Object ModuleTemperature)
	{
		this.ModuleTemperature = ModuleTemperature;
	}

	/**
	 * 
	 * @return The EnvironmentTemperature
	 */
	@JsonProperty("EnvironmentTemperature")
	public Object getEnvironmentTemperature()
	{
		return EnvironmentTemperature;
	}

	/**
	 * 
	 * @param EnvironmentTemperature The EnvironmentTemperature
	 */
	@JsonProperty("EnvironmentTemperature")
	public void setEnvironmentTemperature(Object EnvironmentTemperature)
	{
		this.EnvironmentTemperature = EnvironmentTemperature;
	}

	/**
	 * 
	 * @return The WindSpeed
	 */
	@JsonProperty("WindSpeed")
	public Object getWindSpeed()
	{
		return WindSpeed;
	}

	/**
	 * 
	 * @param WindSpeed The WindSpeed
	 */
	@JsonProperty("WindSpeed")
	public void setWindSpeed(Object WindSpeed)
	{
		this.WindSpeed = WindSpeed;
	}

	/**
	 * 
	 * @return The Insolation
	 */
	@JsonProperty("Insolation")
	public Object getInsolation()
	{
		return Insolation;
	}

	/**
	 * 
	 * @param Insolation The Insolation
	 */
	@JsonProperty("Insolation")
	public void setInsolation(Object Insolation)
	{
		this.Insolation = Insolation;
	}

	/**
	 * 
	 * @return The InfoMessages
	 */
	@JsonProperty("InfoMessages")
	public List<String> getInfoMessages()
	{
		return InfoMessages;
	}

	/**
	 * 
	 * @param InfoMessages The InfoMessages
	 */
	@JsonProperty("InfoMessages")
	public void setInfoMessages(List<String> InfoMessages)
	{
		this.InfoMessages = InfoMessages;
	}

	/**
	 * 
	 * @return The WarningMessages
	 */
	@JsonProperty("WarningMessages")
	public List<String> getWarningMessages()
	{
		return WarningMessages;
	}

	/**
	 * 
	 * @param WarningMessages The WarningMessages
	 */
	@JsonProperty("WarningMessages")
	public void setWarningMessages(List<String> WarningMessages)
	{
		this.WarningMessages = WarningMessages;
	}

	/**
	 * 
	 * @return The ErrorMessages
	 */
	@JsonProperty("ErrorMessages")
	public List<String> getErrorMessages()
	{
		return ErrorMessages;
	}

	/**
	 * 
	 * @param ErrorMessages The ErrorMessages
	 */
	@JsonProperty("ErrorMessages")
	public void setErrorMessages(List<String> ErrorMessages)
	{
		this.ErrorMessages = ErrorMessages;
	}

	/**
	 * 
	 * @return The Info
	 */
	@JsonProperty("Info")
	public de.shippie.sunnybridge.model.Info getInfo()
	{
		return Info;
	}

	/**
	 * 
	 * @param Info The Info
	 */
	@JsonProperty("Info")
	public void setInfo(de.shippie.sunnybridge.model.Info Info)
	{
		this.Info = Info;
	}

	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
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
