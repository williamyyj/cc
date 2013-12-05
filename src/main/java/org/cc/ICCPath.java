/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc;

import java.util.Date;
import java.util.List;

/**
 * @author William
 */
public interface ICCPath<M> {

  public M model(M model, String path);

  public List<M> list(M model, String path);

  public void set(M model, String path, Object value);

  public String _str(M model, String path, String dv);

  public String _str(M model, String path);

  public int _int(M model, String path, int dv);

  public int _int(M model, String path);

  public long _long(M model, String path, long dv);

  public long _long(M model, String path);

  public double _double(M model, String path, double dv);

  public double _double(M model, String path);

  public Date _date(M model, String path, Date dv);

  public Date _date(M model, String path);

}
