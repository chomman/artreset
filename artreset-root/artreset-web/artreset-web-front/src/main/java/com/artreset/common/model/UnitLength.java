/**
 * 
 */
package com.artreset.common.model;

/**
 * @author Taehyun Jung
 *
 */
public enum UnitLength {
	
	MM("mm"), CM("cm"), M("m"), KM("km"), IN("in"), FT("ft"), YD("yd"), MILE("mile"), 尺("尺"), 間("間"), 町("町"), 里("里"), 海里("海里");
	
	private String unit;
	
	private UnitLength(String unit) {
		this.unit = unit;
	}
	
	public String getUnit(){
		return unit;
	}

}