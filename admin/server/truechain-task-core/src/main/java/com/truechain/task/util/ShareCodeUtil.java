package com.truechain.task.util;


/**
 * @author tangbinqi
 * 根据一个值M（可以是用户唯一表示，如主键id，或手机号）来生成对应的码N，如果M相同，生成的N始终相同 
 */
public class ShareCodeUtil {

	public static void main(String[] args) {  
		String code = ShareCodeUtil.numToCode(12345678998L);
		System.out.println(code);
		System.out.println(ShareCodeUtil.codeToNum(code));  
	}  

	/** 自定义进制(0,1没有加入,容易与o,l混淆) */  
	private static final char[] r=new char[]{ 'q', 'x', '9', 'c', '7', 's', '2', 'd', 
		'z',  'p', 'w', 'e', '8', 'a','5', 'i', 'k', '3', 'm', 'j', 'u', 'f', 'r', 
		'4', 'v', 'y', 'l', 't', 'n', '6', 'b', 'g', 'h'};  

	/** 进制长度 */  
	private static final int binLen=r.length; 	

	/** 
	 * 根据ID生成六位随机码 
	 * @param id ID 
	 * @return 随机码 
	 */  
	public static String numToCode(long id) {  
		char[] buf=new char[32];  
		int charPos=32;  

		while((id / binLen) > 0) {  
			int ind=(int)(id % binLen);  
			buf[--charPos]=r[ind];  
			id /= binLen;  
		}  
		buf[--charPos]=r[(int)(id % binLen)];  
		String str=new String(buf, charPos, (32 - charPos)); 		
		return str;  
	}  

	public static long codeToNum(String code) {  
		char chs[]=code.toCharArray();  
		long res=0L;  
		for(int i=0; i < chs.length; i++) {  
			int ind=0;  
			for(int j=0; j < binLen; j++) {  
				if(chs[i] == r[j]) {  
					ind=j;  
					break;  
				}  
			} 
			if(i > 0) {  
				res=res * binLen + ind;  
			} else {  
				res=ind;  
			}  			
		}  
		return res;  
	}  
}
