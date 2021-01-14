package com.brandWall.util;

import java.awt.Color;

public class ColorChange {

	public static String rgba16(String rgba){
		String sz=rgba.replace("rgba", "").replace("(", "").replace(")", "");
		String[] szs=sz.split(",");
		Color color = new Color(
				Integer.valueOf(szs[0].trim()),
				Integer.valueOf(szs[1].trim()),
				Integer.valueOf(szs[2].trim()));
        int rValue = color.getRed();
        int gValue = color.getGreen();
        int bValue = color.getBlue();

        String r16Str = Integer.toHexString(rValue);
        String g16Str = Integer.toHexString(gValue);
        String b16Str = Integer.toHexString(bValue);

//        System.out.println("Color(255,110,220)对应16进制颜色表示为：#" + r16Str + g16Str
//                + b16Str);
        String str=r16Str + g16Str + b16Str;
        int strLen = str.length();  
        if (strLen < 6) {  
            while (strLen < 6) {  
                StringBuffer sb = new StringBuffer();  
//                sb.append("0").append(str);// 左补0  
                 sb.append(str).append("0");//右补0  
                str = sb.toString();  
                strLen = str.length();  
            }  
        } 
        return "#"+str;
	}
	
	public static String rgb16(String rgb){
		String sz=rgb.replace("rgb", "").replace("(", "").replace(")", "");
		String[] szs=sz.split(",");
		Color color = new Color(
				Integer.valueOf(szs[0].trim()),
				Integer.valueOf(szs[1].trim()),
				Integer.valueOf(szs[2].trim()));
        int rValue = color.getRed();
        int gValue = color.getGreen();
        int bValue = color.getBlue();

        String r16Str = Integer.toHexString(rValue);
        String g16Str = Integer.toHexString(gValue);
        String b16Str = Integer.toHexString(bValue);
        
        r16Str=(r16Str.length()==2?"":"0")+r16Str;
        g16Str=(g16Str.length()==2?"":"0")+g16Str;
        b16Str=(b16Str.length()==2?"":"0")+b16Str;
        
        String str=r16Str + g16Str + b16Str;
        return "#"+str;
	}
	
	public static String ch16(String val){
		if (ValidateUtil.isValid(val)) {
			if (val.split("rgba").length>1) {
				return rgba16(val);
			}else if (val.split("rgb").length>1) {
				return rgb16(val);
			}
		}
		return val;
	}
	
	public static void main(String[] args) {
		ColorChange.rgba16("rgba(0, 0, 0, 0.95)");
	}
}
