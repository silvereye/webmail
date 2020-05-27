package webmail.controller;

import java.util.*;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.SearchTerm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.*;
/**
 * This program demonstrates how to search for e-mail messages which satisfy
 * a search criterion.
 * @author www.codejava.net
 */




public class temp
{
	public static int s=0,l=0,m=0;
	
	
		
	  public static void main(String[] args) {
	        int[] arr = {2 ,3 ,9 ,10, 12, 15};
	        int k = 3;
	     
	        combineNp(arr, k);
	   System.out.println(s+" "+ m+" "+l);
	    }

	    private static void combineNp(int[] arr, int r) {
	        int[] res = new int[r];
	        npsCombine(arr, res, 0, 0, r);
	    }



	    private static void npsCombine(int[] arr, int[] res, int currIndex, int level, int r) {
	        if(level == r){
	        /*	int no=0;
		        for (int i = 0; i < res.length; i++) {
		        	no+=res[i];
		           System.out.print(res[i] + " ");
		        }
		        lst.add(no);
		        System.out.println();*/
	        	npfind(res);
	            return;
	        }
	        for (int i = currIndex; i < arr.length; i++) {
	            res[level] = arr[i];
	            npsCombine(arr, res, i+1, level+1, r);
	            
	        }
	    }
	
	    private static void npfind(int arr[])
	    {
	    	int a=arr[0];
	    	int b=arr[1];
	    	int c=arr[2];
	    	int aa=a*a;
	    	int bb=b*b;
	    	int cc=c*c;
	    	
	    	if(a<b+c && b<c+a && c<a+b)
	    	{
	    		if(aa==bb+cc || bb==aa+cc || cc==bb+aa )
	    		{
	    			m++;
	    		}
	    		else if(aa>bb+cc || bb>aa+cc || cc>bb+aa )
	    		{
	    			l++;
	    		}
	    		else if(aa<bb+cc || bb<aa+cc || cc<bb+aa )
	    		{
	    			s++;
	    		}
	    	}
	    }
	
	/*public static String nprev(String original)
	{
		String reverse="";
		int length = original.length();
		 
	      for ( int i = length - 1; i >= 0; i-- )
	         reverse = reverse + original.charAt(i);
	      
	      return reverse;
	}
	 public static void main( String[] args )
	    {
		 boolean flg=true;
		 int input1=5;
		 String input3="DISQ";
		 String []input2={"E#D#E#E#E","D#I#S#K#E","E#S#E#E#E","E#C#E#E#E","E#E#E#E#E"};
		 
		 if(!(input1>=1 && input1<=50))
		 {
			 return 0;
		 }
		 
		 String rev =nprev(input3);
		 if(rev.equals(input3))
		 {
			 flg=false;
		 }
		 
		 String str1 = Arrays.toString(input2); 
		 str1 = str1.substring(1, str1.length()-1).replaceAll(" ", "");
		 String arr[][]=new String[input1][input1];
		String tmp1[]=str1.split(",");
		for(int i=0;i<tmp1.length;i++)
		{
			arr[i]=tmp1[i].split("#");
		}
		
		for(int i=0;i<input1;i++)
		{
			
			for(int j=0;j<input1;j++)
			{
				System.out.print(arr[i][j]);
			}
			System.out.println();
		}
		//System.out.println("--------------------");
		int cnt=0;
		String d1="";
		String d2="";
		for(int i=0;i<input1;i++)
		{
			String s1="";
			String s2="";
			for(int j=0;j<input1;j++)
			{
				s1+=arr[i][j];
				s2+=arr[j][i];
				if(i==j)
				{
					d1+=arr[i][j];
				}
				if(j==(input1-1-i))
				{
					d2+=arr[j][i];
				}
			}
			//System.out.println(i+"---"+s1);
		//	System.out.println(i+"---"+s2);
			//System.out.println("-------");
			String tst1="";
			String tst2="";
			for(int k=0;k<input1;k++)
			{
				tst1=s1.substring(k);
				if(tst1.contains(input3))
				{
					cnt++;
				}
				tst2=s2.substring(k);
				if(tst2.contains(input3))
				{
					cnt++;
				}
			}
			if(flg)
			{
				s1=nprev(s1);
				s2=nprev(s2);
				for(int k=0;k<input1;k++)
				{
					tst1=s1.substring(k);
					if(tst1.contains(input3))
					{
						cnt++;
					}
					tst2=s2.substring(k);
					if(tst2.contains(input3))
					{
						cnt++;
					}
				}
				
			}
		}
		
		String tst1="";
		String tst2="";
		for(int k=0;k<input1;k++)
		{
			tst1=d1.substring(k);
			if(tst1.contains(input3))
			{
				cnt++;
			}
			tst2=d2.substring(k);
			if(tst2.contains(input3))
			{
				cnt++;
			}
		}
		
		if(flg)
		{
			d1=nprev(d1);
			d2=nprev(d2);
		for(int k=0;k<input1;k++)
		{
			tst1=d1.substring(k);
			if(tst1.contains(input3))
			{
				cnt++;
			}
			tst2=d2.substring(k);
			if(tst2.contains(input3))
			{
				cnt++;
			}
		}
		}
		//System.out.println("d1---"+d1);
		//System.out.println("d2---"+d2);
		System.out.println(cnt);
	    */
	    	/*FileInputStream fileInputStream=null;
	 
	        File file1 = new File("D:\\new1.txt");
	        File file2 = new File("D:\\new2.txt");
	 
	        byte[] bFile1 = new byte[(int) file1.length()];
	        byte[] bFile2 = new byte[(int) file2.length()];
	        try {
	            //convert file into array of bytes
		    fileInputStream = new FileInputStream(file1);
		    fileInputStream.read(bFile1);
		    fileInputStream.close();
	 
		    //convert array of bytes into file
		    FileOutputStream fileOuputStream = new FileOutputStream("D:\\new3.txt"); 
		    fileOuputStream.write(bFile1);
		 //   fileOuputStream.close();
		    
		   fileInputStream = new FileInputStream(file2);
		    fileInputStream.read(bFile2);
		    fileInputStream.close();
	 
		    //convert array of bytes into file
		  //  fileOuputStream = new FileOutputStream("D:\\new3.txt"); 
		    fileOuputStream.write(bFile2);
		    fileOuputStream.close();
	 
		    System.out.println("Done");
	        }catch(Exception e){
	            e.printStackTrace();*/
	      //  }
	   // }
	
	
//	 public static List<Integer> lst=new  ArrayList<Integer>();
	
	 /*   public static void main(String[] args) {
	        int[] arr = {1, 2, 3, 4, 4, 5};
	        int k = 3;
	        int cnt=0;
	        
	        if(k<1 && k>10)
	        {
	        	// return cnt;
	        }
	        
	        if(k>arr.length){
	        	// return cnt;
	        }
	        
	        combine(arr, k);
	        Collections.sort(lst);
	        
	      //  System.out.println(lst);
	        for(int i=lst.size()-1; i>=0;i--)
	        {
	        	if(lst.get(i)== lst.get(lst.size()-1))
	        	{
	        		cnt++;
	        	}
	        	else
	        	{
	        		break;
	        	}
	        }
	      //  return cnt;
	        //System.out.println(cnt);
	    }

	    private static void combine(int[] arr, int r) {
	        int[] res = new int[r];
	        npCombine(arr, res, 0, 0, r);
	    }



	    private static void npCombine(int[] arr, int[] res, int currIndex, int level, int r) {
	        if(level == r){
	        	int no=0;
		        for (int i = 0; i < res.length; i++) {
		        	no+=res[i];
		           // System.out.print(res[i] + " ");
		        }
		        lst.add(no);
		        //System.out.println();
	            return;
	        }
	        for (int i = currIndex; i < arr.length; i++) {
	            res[level] = arr[i];
	            npCombine(arr, res, i+1, level+1, r);
	            
	        }
	    }

	  */

	
	
	
	
	
	   /* public static int fg = 0;
	    public static String res = "";

	      public static void main(String arr[])
	    {
	    	  String input1="1,5,9,2,3,5,6";
	        if (input1.split(",").length == 1)
	        {   
	        	System.out.println(input1);
	           // return input1;
	            }
	        else
	            {
	           npM1(input1);
	            }
	        System.out.println(res);
	       // return result;
	       
	    }

	    public static String npM1(String input1) {
	        String[] str = input1.split(",");
	       
	        String flStr = "";

	   
	        if (fg == 2) {
	           
	            return flStr;
	        }else{
	            for (int i = 0; i < str.length - 1; i++) {

	                int npdiff = Integer.parseInt(str[i + 1]) - Integer.parseInt(str[i]);
	                flStr += "," + Integer.toString(npdiff);
	            }
	            

	            res=input1;
	            if (flStr.split(",").length > 1) {
	            	npM1(flStr.substring(1));
	            } else {
	                fg = 2;
	            }
	            if (fg == 0) {
	                fg = 1;
	            }
	            return npM1(flStr);
	        }
	       

	    }

*/
	
	
	
	/*public static boolean flag=false;
	 public static void main(String[] args) 
	   {
		
		 int[] input1={1, 11, 2, 10, 4, 5, 2, 1};
		 int max=bitonic_sequence(input1);
		 System.out.println( max);
		 
	   }
	 
	 
	 
	 
	 
	 
	 
	 public static int getwinner(int input1, int input2 ){
			int brr[]=new int[input1+1];
			int temp;
			for(int i=1;i<=input1;i++){
				brr[i]=i;
			}
			for(int i=1;i<input1;i++){
				for(int j=1;j<input2;j++){
					temp=brr[1];
					
					for(int k=1;k<brr.length-1;k++){
						brr[k]=brr[k+1];
					}
					brr[brr.length-1]=temp;
				}
				for (int a = 0; a < brr.length; a++)
			   {
			       if (a ==0 )
			       {
			           int[] copy = new int[brr.length-1];
			           System.arraycopy(brr, 0, copy, 0, a);
			           System.arraycopy(brr, a+1, copy, a, brr.length-a-1);
			           brr=copy;
			       }
			   }
				
			}
			return brr[1];
		}
		

	 
	 
	 
	 
	 
	 
	 public static int bitonic_sequence(int[] input1)
	 {
	 //Write code here
	 int i, j;
	 int n=input1.length;
	 int l[] = new int[input1.length];
	 for ( i = 0; i < n; i++ )
	 l[i] = 1;
	 for ( i = 1; i < n; i++ )
	 for ( j = 0; j < i; j++ )
	 if ( input1[i] > input1[j] && l[i] < l[j] + 1)
	 l[i] = l[j] + 1;
	 int ls[] = new int [input1.length];
	 for ( i = 0; i < n; i++ )
	 ls[i] = 1;
	 for ( i = n-2; i >= 0; i-- )
	 for ( j = n-1; j > i; j-- )
	 if ( input1[i] > input1[j] && ls[i] < ls[j] + 1)
	 ls[i] = ls[j] + 1;
	 int max = l[0] + ls[0] - 1;
	 for (i = 1; i < n; i++)
	 if (l[i] + ls[i] - 1 > max)
	 max = l[i] + ls[i] - 1;
	 return(max);
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	
	 
	 public static int minimumBottles(int input1)
	    {
	    
	         int amt=input1;
		        int[] arr = {1, 5, 7, 10};
		        
		      
		        //Arrays.sort(arr);
		        combine(amt,arr, 1);
		        if(flag==true)
		        {
		        	 return 1;
		        }
		        else
		        {
		        	combine(amt,arr, 2);
			        if(flag==true)
			        {
			        	return  2;
			        }
			        else
			        {
			        	combine(amt,arr, 3);
				        if(flag==true)
				        {
				        	return  3;
				        }
				        else
				        {
				        	int n1=0,n2=0,n3=0,n4=0;
				        
				        n1=	amt/10;
				        amt=amt%10;
				        if(amt!=0)
				        {
				        n2=	amt/7;
				        amt=amt%7;
				        }
				        if(amt!=0)
				        {
				        n3=	amt/5;
				        amt=amt%5;
				        }
				        if(amt!=0)
				        {
				        n4=amt;
				        }
				        return  n1+n2+n3+n4;
			        }
		        }
		        }
	    }
	      private static void combine(int amt,int[] arr, int r) {
		        int[] res = new int[r];
		        doCombine(amt,arr, res, 0, 0, r);
		    }



		    private static void doCombine(int amt, int[] arr, int[] res, int currIndex, int level, int r) {
		        if(level == r){
		        	int sum=0;
			        for (int i = 0; i < res.length; i++) {
			            System.out.print(res[i] + " ");
			            sum+=res[i];
			        }
			        System.out.println(" = "+sum);
			        System.out.println();
			        if(sum==amt)
			        {
			        	flag=true;
			        }
		            return;
		        }
		        for (int i = currIndex; i < arr.length; i++) {
		            res[level] = arr[i];
		            if(flag==false)
		            doCombine(amt, arr, res, i+1, level+1, r);
		            //way to avoid printing duplicates
		            if(i < arr.length-1 && arr[i] == arr[i+1]){
		                i++;
		            }
		        }
		    }*/
			//System.out.println("Done");
		 
		 /*   int amt=9;
	        int[] arr = {1, 5, 7, 10};
	        
	        int r = 17;
	        //Arrays.sort(arr);
	        combine(amt,arr, 1);
	        if(flag==true)
	        {
	        	System.out.println("1");
	        }
	        else
	        {
	        	combine(amt,arr, 2);
		        if(flag==true)
		        {
		        	System.out.println("2");
		        }
		        else
		        {
		        	combine(amt,arr, 3);
			        if(flag==true)
			        {
			        	System.out.println("3");
			        }
			        else
			        {
			        	int n1=0,n2=0,n3=0,n4=0;
			        
			        n1=	amt/10;
			        amt=amt%10;
			        if(amt!=0)
			        {
			        n2=	amt/7;
			        amt=amt%7;
			        }
			        if(amt!=0)
			        {
			        n3=	amt/5;
			        amt=amt%5;
			        }
			        if(amt!=0)
			        {
			        n4=amt;
			        }
			        System.out.println(n1+n2+n3+n4);
		        }
	        }
	        }
	    }

	    private static void combine(int amt,int[] arr, int r) {
	        int[] res = new int[r];
	        doCombine(amt,arr, res, 0, 0, r);
	    }



	    private static void doCombine(int amt, int[] arr, int[] res, int currIndex, int level, int r) {
	        if(level == r){
	        	int sum=0;
		        for (int i = 0; i < res.length; i++) {
		            System.out.print(res[i] + " ");
		            sum+=res[i];
		        }
		        System.out.println(" = "+sum);
		        System.out.println();
		        if(sum==amt)
		        {
		        	flag=true;
		        }
	            return;
	        }
	        for (int i = currIndex; i < arr.length; i++) {
	            res[level] = arr[i];
	            if(flag==false)
	            doCombine(amt, arr, res, i+1, level+1, r);
	            //way to avoid printing duplicates
	            if(i < arr.length-1 && arr[i] == arr[i+1]){
	                i++;
	            }
	        }*/
	    

}
    	
