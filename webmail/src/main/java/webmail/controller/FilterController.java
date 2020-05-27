package webmail.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sieve.manage.ManageSieveClient;
import com.sieve.manage.ManageSieveResponse;
import com.sieve.manage.ParseException;
import com.sieve.manage.SieveScript;
import com.sieve.manage.examples.ConnectAndListScripts;

@Controller
@ResponseBody
public class FilterController {

	@RequestMapping("/saveFilter")
	public void saveFilter(HttpServletRequest request, Principal principal,@RequestParam String scriptBody,@RequestParam String scriptName) throws IOException
	{
System.out.println(scriptBody+"  going to save");
ManageSieveResponse    resp=null;
ManageSieveClient client=null;
        try {
        	HttpSession hs=request.getSession();
        	String server=hs.getAttribute("host").toString();
        	int port=Integer.parseInt( hs.getAttribute("sieveport").toString());
        	String id=(String)hs.getAttribute("id");
    		String pass=(String)hs.getAttribute("pass");
        	resp=       ConnectAndListScripts.getConnect(server,port,id,pass);
        	// Setup a new client
            client = ConnectAndListScripts.client;
        	
            // Create a simple script
          /*  final String scriptBody =
                    "require [\"fileinto\"];\n"
                    + "\n"
                    + "if address :is \"to\" \"managesieve@example.com\"\n"
                    + "{\n"
                    + "    fileinto \"managesieve\";\n"
                    + "}";*/


            // upload it to the server
            resp = client.putscript(scriptName, scriptBody);
            if (!resp.isOk()) {
                throw new IOException("Can't upload script to server: " + resp.getMessage());
            }
            // and set it active
            resp = client.setactive(scriptName);
            if (!resp.isOk()) {
                throw new IOException("Can't set script [" + scriptName + "] to active: " + resp.getMessage());
            }
           ConnectAndListScripts.closeConnection(client,resp);
        } catch (ParseException ex) {
            System.out.println("The server said something unexpeced:" + ex.getMessage());
        }
    finally{
    	 try {
    		 
			ConnectAndListScripts.closeConnection(client,resp);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
		
		
		
		
		
	}
	@RequestMapping("/saveVacation")
	public void saveVacation(HttpServletRequest request, Principal principal,@RequestParam String scriptBody,@RequestParam String scriptName) throws IOException
	{
		ManageSieveResponse    resp=null;
		ManageSieveClient client=null;
		        try {
		        	HttpSession hs=request.getSession();
		        	String server=hs.getAttribute("host").toString();
		        	int port=Integer.parseInt( hs.getAttribute("sieveport").toString());
		        	String id=(String)hs.getAttribute("id");
		    		String pass=(String)hs.getAttribute("pass");
		        	resp=       ConnectAndListScripts.getConnect(server,port,id,pass);
		        	// Setup a new client
		            client = ConnectAndListScripts.client;
        	
            // Create a simple script
          /*  final String scriptBody =
                    "require [\"fileinto\"];\n"
                    + "\n"
                    + "if address :is \"to\" \"managesieve@example.com\"\n"
                    + "{\n"
                    + "    fileinto \"managesieve\";\n"
                    + "}";*/


            // upload it to the server
            resp = client.putscript(scriptName, scriptBody);
            if (!resp.isOk()) {
                throw new IOException("Can't upload script to server: " + resp.getMessage());
            }
            // and set it active
          //  resp = client.setactive(scriptName);
            if (!resp.isOk()) {
                throw new IOException("Can't set script [" + scriptName + "] to active: " + resp.getMessage());
            }
           ConnectAndListScripts.closeConnection(client,resp);
        } catch (ParseException ex) {
            System.out.println("The server said something unexpeced:" + ex.getMessage());
        }
        finally{
       	 try {
       		 
   			ConnectAndListScripts.closeConnection(client,resp);
   		} catch (ParseException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
		
        }}
	
	
	
	@RequestMapping("/deleteVacation")
	public String deleteVacation(HttpServletRequest request, Principal principal,@RequestParam String scriptName) throws IOException
	{
		String st="false";
		ManageSieveResponse    resp=null;
		ManageSieveClient client=null;
		        try {
		        	HttpSession hs=request.getSession();
		        	String server=hs.getAttribute("host").toString();
		        	int port=Integer.parseInt( hs.getAttribute("sieveport").toString());
		        	String id=(String)hs.getAttribute("id");
		    		String pass=(String)hs.getAttribute("pass");
		        	resp=       ConnectAndListScripts.getConnect(server,port,id,pass);
		        	// Setup a new client
		            client = ConnectAndListScripts.client;
        	
            // Create a simple script
          /*  final String scriptBody =
                    "require [\"fileinto\"];\n"
                    + "\n"
                    + "if address :is \"to\" \"managesieve@example.com\"\n"
                    + "{\n"
                    + "    fileinto \"managesieve\";\n"
                    + "}";*/


            // upload it to the server
		   

		   		// Create a list to hold the result of the next command
		   		List<SieveScript> scripts = new ArrayList<SieveScript>();

		   		// Get the list of this users scripts. The current contents of
		   		// the list will be deleted first.
		   		resp = client.listscripts(scripts);
		   		if (!resp.isOk()) {
		   		   // throw new IOException("Could not get list of scripts: " + resp.getMessage());
		   		 st="false";
		   		}
		   		else
		   		{
		            
            resp = client.deletescript(scriptName);
            st="true";
            if (!resp.isOk()) {
               // throw new IOException("Can't upload script to server: " + resp.getMessage());
            }
            // and set it active
          //  resp = client.setactive(scriptName);
            if (!resp.isOk()) {
               // throw new IOException("Can't set script [" + scriptName + "] to active: " + resp.getMessage());
            }
		   		}
           ConnectAndListScripts.closeConnection(client,resp);
        } catch (ParseException ex) {
            System.out.println("The server said something unexpeced:" + ex.getMessage());
            st="false";
        }
        finally{
       	 try {
       		 
   			ConnectAndListScripts.closeConnection(client,resp);
   		} catch (ParseException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
		
        }
		        return st;
		        }
	
	/*@RequestMapping("getFilters")
	public String getFilters(Principal principal,@RequestParam String scriptName) throws IOException{
		try{
		 ManageSieveClient client = new ManageSieveClient();
     	ManageSieveResponse    resp=       ConnectAndListScripts.getConnect();
		// Create a list to hold the result of the next command
        List<SieveScript> scripts = new ArrayList<SieveScript>();

        // Get the list of this users scripts. The current contents of
        // the list will be deleted first.
        resp = client.listscripts(scripts);
        if (!resp.isOk()) {
            throw new IOException("Could not get list of scripts: " + resp.getMessage());
        }

       
		  } catch (ParseException ex) {
	            System.out.println("The server said something unexpeced:" + ex.getMessage());
	        }
		return "";
	}*/
	
}
