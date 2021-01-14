package com.brandWall.util;

public class StaticTest {

	
	static int x=1;
	int y;
	StaticTest(){
		y++;
		
	}
	
	static{
		x++;
	}
	
	public static void main(String[] args) {
		
		StaticTest st=new StaticTest();
		System.out.println("x="+x);
		System.out.println("st.y="+st.y);
		st=new StaticTest();
		System.out.println("st.y="+st.y);
	}
	
}
