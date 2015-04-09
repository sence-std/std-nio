/**
 * @FileName:Hello
 * @Package: com.std.entity
 *
 * @author sence
 * @created 4/9/2015 4:21 PM
 *
 * Copyright 2011-2015 Asura
 */
package com.std.entity;

import java.io.Serializable;

/**
 *
 * <p>实体</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sence
 * @since 1.0
 * @version 1.0
 */
public class Hello implements Serializable {

	private String name;

	private String sayWord;


	public Hello () {

	}

	public Hello (String name, String sayWord) {
		this.name = name;
		this.sayWord = sayWord;
	}

	public String getSayWord () {
		return sayWord;
	}

	public void setSayWord (String sayWord) {
		this.sayWord = sayWord;
	}

	public String getName () {
		return name;
	}

	public void setName (String name) {
		this.name = name;
	}
}
