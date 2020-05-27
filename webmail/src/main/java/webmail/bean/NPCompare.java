package webmail.bean;

import java.util.Comparator;

import webmail.wsdl.VCFLdapDirAtt;

public class NPCompare implements Comparator<VCFLdapDirAtt> {

@Override
public int compare(VCFLdapDirAtt obj1, VCFLdapDirAtt obj2) {
    return (obj1.getContactName()).compareTo((obj2.getContactName()));
}

}
