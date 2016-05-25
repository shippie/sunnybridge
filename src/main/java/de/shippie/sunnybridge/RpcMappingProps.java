package de.shippie.sunnybridge;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sunnybridge.xmlrpc", ignoreUnknownFields = true)
public class RpcMappingProps
{
	private String stamp;
	private boolean stamp_on;

	private String pv;
	private boolean pv_on;

	private String feedin;
	private boolean feedin_on;

	private String gridconsumption;
	private boolean gridconsumption_on;

	private String selfconsumption;
	private boolean selfconsumption_on;

	private String directconsumption;
	private boolean directconsumption_on;

	private String totalconsumption;
	private boolean totalconsumption_on;

	private String directconsumptionquote;
	private boolean directconsumptionquote_on;

	private String selfsupply;
	private boolean selfsupply_on;

	public final String getSelfsupply()
	{
		return selfsupply;
	}

	public final void setSelfsupply(String selfsupply)
	{
		this.selfsupply = selfsupply;
	}

	public final boolean isSelfsupply_on()
	{
		return selfsupply_on;
	}

	public final void setSelfsupply_on(boolean selfsupply_on)
	{
		this.selfsupply_on = selfsupply_on;
	}

	public final String getSelfconsumption()
	{
		return selfconsumption;
	}

	public final void setSelfconsumption(String selfconsumption)
	{
		this.selfconsumption = selfconsumption;
	}

	public final boolean isSelfconsumption_on()
	{
		return selfconsumption_on;
	}

	public final void setSelfconsumption_on(boolean selfconsumption_on)
	{
		this.selfconsumption_on = selfconsumption_on;
	}

	private String selfconsumptionquote;
	private boolean selfconsumptionquote_on;

	private String autarkyquote;
	private boolean autarkyquote_on;

	private String batteryin;
	private boolean batteryin_on;

	private String batteryout;
	private boolean batteryout_on;

	private String batterychargestatus;
	private boolean batterychargestatus_on;

	private String batterystateofhealth;
	private boolean batterystateofhealth_on;

	private String operationhealth;
	private boolean operationhealth_on;

	private String moduletemperature;
	private boolean moduletemperature_on;

	private String environmenttemperature;
	private boolean environmenttemperature_on;

	private String windspeed;
	private boolean windspeed_on;

	private String insolation;
	private boolean insolation_on;

	private String infomessages;
	private boolean infomessages_on;

	private String warningmessages;
	private boolean warningmessages_on;

	public final String getStamp()
	{
		if (stamp == null) {
			DateFormat df = new SimpleDateFormat("MMddyyyy_HHmmss");
			stamp = df.format(Calendar.getInstance().getTime());
		}
		return stamp;
	}

	public final void setStamp(String stamp)
	{
		this.stamp = stamp;
	}

	public final boolean isStamp_on()
	{
		return stamp_on;
	}

	public final void setStamp_on(boolean stamp_on)
	{
		this.stamp_on = stamp_on;
	}

	public final String getPv()
	{
		return pv;
	}

	public final void setPv(String pv)
	{
		this.pv = pv;
	}

	public final boolean isPv_on()
	{
		return pv_on;
	}

	public final void setPv_on(boolean pv_on)
	{
		this.pv_on = pv_on;
	}

	public final String getFeedin()
	{
		return feedin;
	}

	public final void setFeedin(String feedin)
	{
		this.feedin = feedin;
	}

	public final boolean isFeedin_on()
	{
		return feedin_on;
	}

	public final void setFeedin_on(boolean feedin_on)
	{
		this.feedin_on = feedin_on;
	}

	public final String getGridconsumption()
	{
		return gridconsumption;
	}

	public final void setGridconsumption(String gridconsumption)
	{
		this.gridconsumption = gridconsumption;
	}

	public final boolean isGridconsumption_on()
	{
		return gridconsumption_on;
	}

	public final void setGridconsumption_on(boolean gridconsumption_on)
	{
		this.gridconsumption_on = gridconsumption_on;
	}

	public final String getDirectconsumption()
	{
		return directconsumption;
	}

	public final void setDirectconsumption(String directconsumption)
	{
		this.directconsumption = directconsumption;
	}

	public final boolean isDirectconsumption_on()
	{
		return directconsumption_on;
	}

	public final void setDirectconsumption_on(boolean directconsumption_on)
	{
		this.directconsumption_on = directconsumption_on;
	}

	public final String getTotalconsumption()
	{
		return totalconsumption;
	}

	public final void setTotalconsumption(String totalconsumption)
	{
		this.totalconsumption = totalconsumption;
	}

	public final boolean isTotalconsumption_on()
	{
		return totalconsumption_on;
	}

	public final void setTotalconsumption_on(boolean totalconsumption_on)
	{
		this.totalconsumption_on = totalconsumption_on;
	}

	public final String getDirectconsumptionquote()
	{
		return directconsumptionquote;
	}

	public final void setDirectconsumptionquote(String directconsumptionquote)
	{
		this.directconsumptionquote = directconsumptionquote;
	}

	public final boolean isDirectconsumptionquote_on()
	{
		return directconsumptionquote_on;
	}

	public final void setDirectconsumptionquote_on(boolean directconsumptionquote_on)
	{
		this.directconsumptionquote_on = directconsumptionquote_on;
	}

	public final String getSelfconsumptionquote()
	{
		return selfconsumptionquote;
	}

	public final void setSelfconsumptionquote(String selfconsumptionquote)
	{
		this.selfconsumptionquote = selfconsumptionquote;
	}

	public final boolean isSelfconsumptionquote_on()
	{
		return selfconsumptionquote_on;
	}

	public final void setSelfconsumptionquote_on(boolean selfconsumptionquote_on)
	{
		this.selfconsumptionquote_on = selfconsumptionquote_on;
	}

	public final String getAutarkyquote()
	{
		return autarkyquote;
	}

	public final void setAutarkyquote(String autarkyquote)
	{
		this.autarkyquote = autarkyquote;
	}

	public final boolean isAutarkyquote_on()
	{
		return autarkyquote_on;
	}

	public final void setAutarkyquote_on(boolean autarkyquote_on)
	{
		this.autarkyquote_on = autarkyquote_on;
	}

	public final String getBatteryin()
	{
		return batteryin;
	}

	public final void setBatteryin(String batteryin)
	{
		this.batteryin = batteryin;
	}

	public final boolean isBatteryin_on()
	{
		return batteryin_on;
	}

	public final void setBatteryin_on(boolean batteryin_on)
	{
		this.batteryin_on = batteryin_on;
	}

	public final String getBatteryout()
	{
		return batteryout;
	}

	public final void setBatteryout(String batteryout)
	{
		this.batteryout = batteryout;
	}

	public final boolean isBatteryout_on()
	{
		return batteryout_on;
	}

	public final void setBatteryout_on(boolean batteryout_on)
	{
		this.batteryout_on = batteryout_on;
	}

	public final String getBatterychargestatus()
	{
		return batterychargestatus;
	}

	public final void setBatterychargestatus(String batterychargestatus)
	{
		this.batterychargestatus = batterychargestatus;
	}

	public final boolean isBatterychargestatus_on()
	{
		return batterychargestatus_on;
	}

	public final void setBatterychargestatus_on(boolean batterychargestatus_on)
	{
		this.batterychargestatus_on = batterychargestatus_on;
	}

	public final String getOperationhealth()
	{
		return operationhealth;
	}

	public final void setOperationhealth(String operationhealth)
	{
		this.operationhealth = operationhealth;
	}

	public final boolean isOperationhealth_on()
	{
		return operationhealth_on;
	}

	public final void setOperationhealth_on(boolean operationhealth_on)
	{
		this.operationhealth_on = operationhealth_on;
	}

	public final String getModuletemperature()
	{
		return moduletemperature;
	}

	public final void setModuletemperature(String moduletemperature)
	{
		this.moduletemperature = moduletemperature;
	}

	public final boolean isModuletemperature_on()
	{
		return moduletemperature_on;
	}

	public final void setModuletemperature_on(boolean moduletemperature_on)
	{
		this.moduletemperature_on = moduletemperature_on;
	}

	public final String getEnvironmenttemperature()
	{
		return environmenttemperature;
	}

	public final void setEnvironmenttemperature(String environmenttemperature)
	{
		this.environmenttemperature = environmenttemperature;
	}

	public final boolean isEnvironmenttemperature_on()
	{
		return environmenttemperature_on;
	}

	public final void setEnvironmenttemperature_on(boolean environmenttemperature_on)
	{
		this.environmenttemperature_on = environmenttemperature_on;
	}

	public final String getWindspeed()
	{
		return windspeed;
	}

	public final void setWindspeed(String windspeed)
	{
		this.windspeed = windspeed;
	}

	public final boolean isWindspeed_on()
	{
		return windspeed_on;
	}

	public final void setWindspeed_on(boolean windspeed_on)
	{
		this.windspeed_on = windspeed_on;
	}

	public final String getInsolation()
	{
		return insolation;
	}

	public final void setInsolation(String insolation)
	{
		this.insolation = insolation;
	}

	public final boolean isInsolation_on()
	{
		return insolation_on;
	}

	public final void setInsolation_on(boolean insolation_on)
	{
		this.insolation_on = insolation_on;
	}

	public final String getInfomessages()
	{
		return infomessages;
	}

	public final void setInfomessages(String infomessages)
	{
		this.infomessages = infomessages;
	}

	public final boolean isInfomessages_on()
	{
		return infomessages_on;
	}

	public final void setInfomessages_on(boolean infomessages_on)
	{
		this.infomessages_on = infomessages_on;
	}

	public final String getWarningmessages()
	{
		return warningmessages;
	}

	public final void setWarningmessages(String warningmessages)
	{
		this.warningmessages = warningmessages;
	}

	public final boolean isWarningmessages_on()
	{
		return warningmessages_on;
	}

	public final void setWarningmessages_on(boolean warningmessages_on)
	{
		this.warningmessages_on = warningmessages_on;
	}

	public final String getBatterystateofhealth()
	{
		return batterystateofhealth;
	}

	public final void setBatterystateofhealth(String batterystateofhealth)
	{
		this.batterystateofhealth = batterystateofhealth;
	}

	public final boolean isBatterystateofhealth_on()
	{
		return batterystateofhealth_on;
	}

	public final void setBatterystateofhealth_on(boolean batterystateofhealth_on)
	{
		this.batterystateofhealth_on = batterystateofhealth_on;
	}

	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	//	
	//
	//		pv=SUNNY_PV
	//		pv.on=true
	//
	//		feedin=SUNNY_FEEDIN
	//		feedin.on=true
	//
	//		gridconsumption=SUNNY_GRIDCONSUMPTION
	//		gridconsumption.on=true
	//
	//		directconsumption=SUNNY_DIRECTCONSUMPTION
	//		directconsumption.on=true
	//
	//		totalconsumption=SUNNY_TOTALCONSUMPTION
	//		totalconsumption.on=true
	//
	//		directconsumptionquote=SUNNY_DIRECTCONSUMPTIONQUOTE
	//		directconsumptionquote.on=true
	//
	//		selfconsumptionquote=SUNNY_SELFCONSUMPTIONQUOTE
	//		selfconsumptionquote.on=true
	//
	//		autarkyquote=SUNNY_AUTARKYQUOTE
	//		autarkyquote.on=true
	//
	//		batteryin=SUNNY_BATTERYIN
	//		batteryin.on=true
	//
	//		batteryout=SUNNY_BATTERYOUT
	//		batteryout.on=true
	//
	//		batterychargestatus=SUNNY_BATTERYCHARGESTATUS
	//		batterychargestatus.on=true
	//
	//		operationhealth=SUNNY_OPERATIONHEALTH
	//		operationhealth.on=true
	//
	//		moduletemperature=SUNNY_MODULETEMPERATURE
	//		moduletemperature.on=true
	//
	//		environmenttemperature=SUNNY_ENVIRONMENTTEMPERATURE
	//		environmenttemperature.on=true
}
