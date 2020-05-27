package webmail.bean;

import java.util.Comparator;

import webmail.wsdl.MailImapFolders;
import webmail.wsdl.VCFLdapDirAtt;

public class NPFolderCompare implements Comparator<MailImapFolders> {

@Override
public int compare(MailImapFolders obj1, MailImapFolders obj2) {
    return (obj1.getFolderFullName()).compareTo((obj2.getFolderFullName()));
}

}
