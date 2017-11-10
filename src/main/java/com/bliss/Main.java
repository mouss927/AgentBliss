package com.bliss;

import com.bliss.model.Test;


public class Main {
	

	  public Main() {
	  
	      try {
	    	  while(true) {
	    	  Test t=new Test();
	    	  t.EnvoyeInfo();
	        // et faire une pause
	        Thread.sleep(30000);
	        }
	      }
	      catch (InterruptedException ex) {}
	    
	  }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Main();
	

	}

	
	
	

}
