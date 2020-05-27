package webmail.bean;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;

import ezvcard.*;
import ezvcard.io.text.VCardReader;
import ezvcard.parameter.*;
import ezvcard.property.*;
import ezvcard.property.Address;
import webmail.wsdl.DisplayContact;

public class DisplayEZVcard {

	public DisplayContact dispContactDetails(String vcfiostrm)
	{
		DisplayContact dispres=new DisplayContact();
		
		try
		{
		InputStream is= IOUtils.toInputStream(vcfiostrm);
		
		 VCardReader vcardReader = new VCardReader(is);
		 VCard vcard = Ezvcard.parse(is).first();
		 FormattedName fn=vcard.getFormattedName();
		 dispres.setWebamilFullName(fn.getValue());
		 
		 Uid uidd=vcard.getUid();
		 dispres.setWebamilUid(uidd.getValue());
		 
		 List<Title> list_tit =vcard.getTitles();
		 for(Title tit: list_tit)
		 {
			 dispres.setWebamilJob(tit.getValue());
			 break;
		 }
		 List <Photo> phlst=vcard.getPhotos();
		 String ph1="";
		 try
		 {
		 for(Photo ph: phlst)
		 {
			// ph1=ph.getContentType().toString();
			 
			 byte[] bt= ph.getData();        //ph.getUrl().getBytes();
			// ph1= new sun.misc.BASE64Encoder().encode(bt);
			 try
			 {
				 ph1= new sun.misc.BASE64Encoder().encode(bt);
			 }
			 catch(Exception ee)
			 {
				 ph1=ph.getUrl();
			 }
			 
			 dispres.setWebamilPhoto(ph1); 
			/* Byte []bbt=new Byte[bt.length];
			
			 int i=0;    
			 // Associating Byte array values with bytes. (byte[] to Byte[])
			 for(byte b: bt)
			   bbt[i++] = b;  // Autoboxing.
		 List<Byte> listbyte =Arrays.asList(bbt);
			 dispres.getWebamilPhotoByte().addAll(listbyte);*/
		 }
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		// System.out.println("!!!!!!!!!!!!!!!!!!!Address:"+ad.getStreetAddress()+" type:"+ad.getTypes());
		Organization org= vcard.getOrganization();
	if(org!=null)
	{
		List<String> orgvlst= org.getValues();
		if(orgvlst.size()>0)
		{
		dispres.setWebamilCompany( orgvlst.get(0));
		//System.out.println("^^^^^^^^^^^org="+orgvlst.get(0));
		}
		else
		{
			dispres.setWebamilCompany( "");
		}
	}
		List<Note> notelst= vcard.getNotes();
		for(Note nt: notelst)
		{
			dispres.setWebamilNote(nt.getValue());
			break;
		}
		 
		 
		 List<Email> elst=	vcard.getEmails();
			
			 for(Email em: elst)
			 {
				 dispres.setWebamilEmail(em.getValue());
				// System.out.println("**************email type="+em.getTypes()+": email="+em.getValue());
				 break;
			 }
			 
			 List<Telephone> moblst=	vcard.getTelephoneNumbers();
			 for(Telephone tel: moblst)
			 { 
				 if(tel.getTypes().toString().contains("home"))
						 {
					 dispres.setWebamilPhoneHome(tel.getText()); 
						 }
				 else  if(tel.getTypes().toString().contains("work"))
						 {
					  dispres.setWebamilPhoneWork(tel.getText()); 
					 }
				 else if(tel.getTypes().toString().contains("cell"))
					 {
					 dispres.setWebamilPhoneMob( tel.getText()); 
					 }
			 	 else if(tel.getTypes().toString().contains("fax"))
			 	 	{
			 		dispres.setWebamilPhoneFax( tel.getText()); 
			 	 	}
				// System.out.println("!!!!!!!!!!!!!!!!!!!mob:"+tel.getText()+" type:"+tel.getTypes());
				 
			 }
			 
			 List<Address> addr= vcard.getAddresses();
			 for(Address ad: addr)
			 {
				 if(ad.getTypes().toString().contains("home"))
				 {
					 dispres.setWebamilAddrHome( ad.getStreetAddress() ); 
				 }
				 else if(ad.getTypes().toString().contains("work"))
				 {
					 dispres.setWebmailAddrWork(ad.getStreetAddress());
				 }
				 //System.out.println("Address:"+ad.getCountry()+" type:"+ad.getTypes());
				System.out.println("!!!!!!!!!!!!!!!!!!!Address:"+ad.getStreetAddress()+" type:"+ad.getTypes());
			 }
			 
			List<Url> urllst= vcard.getUrls();
			for(Url ulst: urllst)
			{
				dispres.setWebamilWebPage(ulst.getValue());
			}
			is.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return dispres;
	}
	
}
