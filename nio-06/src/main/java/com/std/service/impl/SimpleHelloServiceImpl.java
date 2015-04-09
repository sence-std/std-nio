/**
 * @FileName:HelloServiceImpl
 * @Package: com.std.service.impl
 *
 * @author sence
 * @created 4/9/2015 4:20 PM
 *
 * Copyright 2011-2015 Asura
 */
package com.std.service.impl;

import com.std.entity.Hello;
import com.std.service.IHelloService;

/**
 *
 * <p>HelloService的实现</p>
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
public class SimpleHelloServiceImpl implements IHelloService{

	/**
	 *
	 * @param name
	 * @param word
	 * @return
	 */
	@Override
	public Hello sayHello (String name, String word) {
		return new Hello(name,word);
	}
}
