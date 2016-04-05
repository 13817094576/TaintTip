package com.example.tainttip;

import android.content.Context;

import java.util.*;
import java.util.Map.Entry;

class TaintInfo {
	String name;
	String description;
}

class TaintParser {

	private Map<Integer, TaintInfo> taintTips;
	
	TaintParser(Context context)
	{
		//
		// Initialize the mapping of taint values and tips
		taintTips = new HashMap<Integer, TaintInfo>();
		
		TaintInfo info = new TaintInfo();
		info.name = context.getString(R.string.taint_name_location);
		info.description = context.getString(R.string.taint_description_location);
		taintTips.put(0x00000001, info);
		
		info = new TaintInfo();
		info.name = context.getString(R.string.taint_name_contacts);
		info.description = context.getString(R.string.taint_description_contacts);
		taintTips.put(0x00000002, info);
		
		info = new TaintInfo();
		info.name = context.getString(R.string.taint_name_mic);
		info.description = context.getString(R.string.taint_description_mic);
		taintTips.put(0x00000004, info);
		
		info = new TaintInfo();
		info.name = context.getString(R.string.taint_name_phonenumber);
		info.description = context.getString(R.string.taint_description_phonenumber);
		taintTips.put(0x00000008, info);
		
		info = new TaintInfo();
		info.name = context.getString(R.string.taint_name_gps);
		info.description = context.getString(R.string.taint_description_gps);
		taintTips.put(0x00000010, info);
		
		info = new TaintInfo();
		info.name = context.getString(R.string.taint_name_location_net);
		info.description = context.getString(R.string.taint_description_location_net);
		taintTips.put(0x00000020, info);
		
		info = new TaintInfo();
		info.name = context.getString(R.string.taint_name_location_last);
		info.description = context.getString(R.string.taint_description_location_last);
		taintTips.put(0x00000040, info);
		
		info = new TaintInfo();
		info.name = context.getString(R.string.taint_name_camera);
		info.description = context.getString(R.string.taint_description_camera);
		taintTips.put(0x00000080, info);
		
		info = new TaintInfo();
		info.name = context.getString(R.string.taint_name_accelerometer);
		info.description = context.getString(R.string.taint_description_accelerometer);
		taintTips.put(0x00000100, info);
		
		info = new TaintInfo();
		info.name = context.getString(R.string.taint_name_sms);
		info.description = context.getString(R.string.taint_description_sms);
		taintTips.put(0x00000200, info);
		
		info = new TaintInfo();
		info.name = context.getString(R.string.taint_name_imei);
		info.description = context.getString(R.string.taint_description_imei);
		taintTips.put(0x00000400, info);
		
		info = new TaintInfo();
		info.name = context.getString(R.string.taint_name_imsi);
		info.description = context.getString(R.string.taint_description_imsi);
		taintTips.put(0x00000800, info);
		
		info = new TaintInfo();
		info.name = context.getString(R.string.taint_name_iccid);
		info.description = context.getString(R.string.taint_description_iccid);
		taintTips.put(0x00001000, info);
		
		info = new TaintInfo();
		info.name = context.getString(R.string.taint_name_devicesn);
		info.description = context.getString(R.string.taint_description_devicesn);
		taintTips.put(0x00002000, info);
		
		info = new TaintInfo();
		info.name = context.getString(R.string.taint_name_account);
		info.description = context.getString(R.string.taint_description_account);
		taintTips.put(0x00004000, info);
		
		info = new TaintInfo();
		info.name = context.getString(R.string.taint_name_history);
		info.description = context.getString(R.string.taint_description_history);
		taintTips.put(0x00008000, info);

		info = new TaintInfo();
		info.name = context.getString(R.string.taint_name_photo);
		info.description = context.getString(R.string.taint_description_photo);
		taintTips.put(0x00010000, info);

		info = new TaintInfo();
		info.name = context.getString(R.string.taint_name_calendar);
		info.description = context.getString(R.string.taint_description_calendar);
		taintTips.put(0x00020000, info);
	}
	
	TaintInfo[] taintToTips(int taint)
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
