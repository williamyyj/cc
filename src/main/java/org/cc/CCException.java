/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc;

/**
 *
 * @author william
 */
public class CCException extends Exception {
	
	public CCException(String message) {
        super(message);
    }

  
    public CCException(String message, Throwable cause) {
        super(message, cause);
    }

  
    public CCException(Throwable cause) {
        super(cause);
    }
}
