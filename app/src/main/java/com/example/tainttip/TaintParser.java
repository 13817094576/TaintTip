package com.example.tainttip;

import java.util.*;
import java.util.Map.Entry;

class TaintInfo {
	String name;
	String description;
}

class TaintParser {

	static Map<Integer, TaintInfo> taintTips;
	
	static
	{
		//
		// Initialize the mapping of taint values and tips
		taintTips = new HashMap<Integer, TaintInfo>();
		
		TaintInfo info = new TaintInfo();
		info.name = "TAINT_LOCATION";
		info.description = "Location of phone";
		taintTips.put(0x00000001, info);
		
		info = new TaintInfo();
		info.name = "TAINT_CONTACTS";
		info.description = "Contacts stored in phone";
		taintTips.put(0x00000002, info);
		
		info = new TaintInfo();
		info.name = "TAINT_MIC";
		info.description = "Microphone of phone";
		taintTips.put(0x00000004, info);
		
		info = new TaintInfo();
		info.name = "TAINT_PHONE_NUMBER";
		info.description = "Phone number stored in phone";
		taintTips.put(0x00000008, info);
		
		info = new TaintInfo();
		info.name = "TAINT_LOCATION_GPS";
		info.description = "Location acquired by GPS";
		taintTips.put(0x00000010, info);
		
		info = new TaintInfo();
		info.name = "TAINT_LOCATION_NET";
		info.description = "Location acquired by network";
		taintTips.put(0x00000020, info);
		
		info = new TaintInfo();
		info.name = "TAINT_LOCAITON_LAST";
		info.description = "Last location of phone";
		taintTips.put(0x00000040, info);
		
		info = new TaintInfo();
		info.name = "TAINT_CAMERA";
		info.description = "Camera of phone";
		taintTips.put(0x00000080, info);
		
		info = new TaintInfo();
		info.name = "TAINT_ACCELEROMETER";
		info.description = "Accelerometer of phone";
		taintTips.put(0x00000100, info);
		
		info = new TaintInfo();
		info.name = "TAINT_SMS";
		info.description = "Text Messages on phone";
		taintTips.put(0x00000200, info);
		
		info = new TaintInfo();
		info.name = "TAINT_IMEI";
		info.description = "International Mobile Equipment Identity";
		taintTips.put(0x00000400, info);
		
		info = new TaintInfo();
		info.name = "TAINT_IMSI";
		info.description = "International Mobile Subscriber Identification Number";
		taintTips.put(0x00000800, info);
		
		info = new TaintInfo();
		info.name = "TAINT_ICCID";
		info.description = "Integrated Circuit Card Identity";
		taintTips.put(0x00001000, info);
		
		info = new TaintInfo();
		info.name = "TAINT_DEVICE_SN";
		info.description = "Device Serial Number";
		taintTips.put(0x00002000, info);
		
		info = new TaintInfo();
		info.name = "TAINT_ACCOUNT";
		info.description = "Accounts on phone";
		taintTips.put(0x00004000, info);
		
		info = new TaintInfo();
		info.name = "TAINT_HISTORY";
		info.description = "Web browsing history of user";
		taintTips.put(0x00008000, info);
	}
	
	static TaintInfo[] taintToTips(int taint)
	{
		List<TaintInfo> tips = new LinkedList<TaintInfo>();
		Iterator<Entry<Integer, TaintInfo>> taints = taintTips.entrySet().iterator();
		
		//
		// Iterate all possible taint to check if specified taint exists
		while (taints.hasNext())
		{
			Entry<Integer, TaintInfo> currentEntry = taints.next();
			
			// Test if current taint exists
			if ((taint & currentEntry.getKey()) != 0)
			{
				tips.add(currentEntry.getValue());
			}
		}

        TaintInfo[] tipsArray = new TaintInfo[tips.size()];
		tips.toArray(tipsArray);
        return tipsArray;
	}
}
