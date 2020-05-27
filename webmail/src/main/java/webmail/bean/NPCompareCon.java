package webmail.bean;

import java.util.Comparator;

import net.fortuna.ical4j.vcard.VCard;



public class NPCompareCon implements Comparator<VCard> {

@Override
public int compare(VCard obj1, VCard obj2) {
	String cmp1="";
	String cmp2="";
	
	try
	{
	if(obj1.getProperty(net.fortuna.ical4j.vcard.Property.Id.EMAIL)!=null)
		cmp1=obj1.getProperty(net.fortuna.ical4j.vcard.Property.Id.EMAIL).getValue().toLowerCase();
	else if( obj1.getProperty(net.fortuna.ical4j.vcard.Property.Id.NAME)!=null)
		cmp1= obj1.getProperty(net.fortuna.ical4j.vcard.Property.Id.NAME).getValue().toLowerCase();
	else	if( obj1.getProperty(net.fortuna.ical4j.vcard.Property.Id.FN)!=null )
		cmp1= obj1.getProperty(net.fortuna.ical4j.vcard.Property.Id.FN).getValue().toLowerCase();
	
	if(obj2.getProperty(net.fortuna.ical4j.vcard.Property.Id.EMAIL)!=null)
		cmp2=obj2.getProperty(net.fortuna.ical4j.vcard.Property.Id.EMAIL).getValue().toLowerCase();
	else if( obj2.getProperty(net.fortuna.ical4j.vcard.Property.Id.NAME)!=null)
		cmp2= obj2.getProperty(net.fortuna.ical4j.vcard.Property.Id.NAME).getValue().toLowerCase();
	else	if( obj2.getProperty(net.fortuna.ical4j.vcard.Property.Id.FN)!=null )
		cmp2= obj2.getProperty(net.fortuna.ical4j.vcard.Property.Id.FN).getValue().toLowerCase();
	
	 return (cmp1).compareTo(cmp2);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
	return 0;
}

}
