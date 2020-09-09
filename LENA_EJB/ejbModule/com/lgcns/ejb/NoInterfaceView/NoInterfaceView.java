package com.lgcns.ejb.NoInterfaceView;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class NoInterfaceView
 */
@Stateless
@LocalBean
public class NoInterfaceView {

    /**
     * Default constructor. 
     */
    public NoInterfaceView() {
        
    }
    
    public String sayHello() {
    	return "Hello NoInterfaceView ~!!";
    }

}
