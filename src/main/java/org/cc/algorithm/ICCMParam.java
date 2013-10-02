/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.algorithm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author william
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ICCMParam {
	String exclude();
	String express() ; 
}
