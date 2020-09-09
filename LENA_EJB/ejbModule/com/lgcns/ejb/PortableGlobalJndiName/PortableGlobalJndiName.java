package com.lgcns.ejb.PortableGlobalJndiName;

import javax.ejb.Stateless;

/**
 * Session Bean implementation class PortableGlobalJndiName
 */
@Stateless
public class PortableGlobalJndiName {

    /**
     * Default constructor. 
     */
    public PortableGlobalJndiName() {
        // TODO Auto-generated constructor stub
    }
    
    public String sayHello() {
        String s = "These are greetings from the session bean " +
          "with a portable global jndi name!";
        return s;
    }

}
