package client;

import cb.*;

/**
 *
 */
public class PrintCallback extends CallBackPOA {

	@Override
	public void call_back(java.lang.String mesg){
		System.out.println(mesg);
	}
}
