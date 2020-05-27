package webmail.bean;

import java.io.UnsupportedEncodingException;

import javax.mail.internet.MimeUtility;

public  class TextDecode {

	public static String decodeUTF8Text(String res)
	{
		String temp=res;
		try {
			if(res!=null && res.length()>0)
			{
				String sut=res;
				int ind=sut.toUpperCase().indexOf("=?UTF-8?");
				if(ind>0)
				{
				String str1=sut.substring(0, ind);
				String str2=sut.substring(ind);
				str2=MimeUtility.decodeText(str2);
				res=str1+str2;
				}
				else
				{
					res=MimeUtility.decodeText(res);
				}
				
			}
			else
			{
				res="";
			}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			res=temp;
			e.printStackTrace();
		}
		return res;
	}
}
