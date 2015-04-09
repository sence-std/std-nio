/**
 * @FileName:IHelloService
 * @Package: com.std.service
 *
 * @author sence
 * @created 4/9/2015 4:20 PM
 *
 * Copyright 2011-2015 Asura
 */
package com.std.service;

import com.std.entity.Hello;

/**
 *
 * <p>简单的helloworld</p>
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
public interface IHelloService {

	/**
	 *
	 * @param name
	 * @param word
	 * @return
	 */
	public Hello sayHello(String name,String word);

}
