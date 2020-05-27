package webmail.bean;

import java.util.Comparator;

import webmail.wsdl.TaskBean;
import webmail.wsdl.VCFLdapDirAtt;

public class TaskCompare implements Comparator<TaskBean>
{
	@Override
	public int compare(TaskBean obj1, TaskBean obj2) {
	    return (obj1.getDuedate().toGregorianCalendar()).compareTo((obj2.getDuedate().toGregorianCalendar()));
	}
}
