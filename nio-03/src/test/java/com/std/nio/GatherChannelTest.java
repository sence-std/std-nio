/**
 * @FileName:GatherChannelTest
 * @Package: com.std.nio
 *
 * @author sence
 * @created 3/16/2015 1:31 PM
 *
 * Copyright 2011-2015 Asura
 */
package com.std.nio;

import org.junit.Test;

import java.io.IOException;

/**
 *
 * <p>Gather </p>
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
public class GatherChannelTest {

	private String[] col1 = {"sence mile","duka lio","stake kila","lisa mike","chris nbone","huke curs"};
	private String[] col2 = {"microsoft","apple","google","alibaba","adobe","oracle"};
	private String[] col3 = {"washington","new york","landon","北京"};

	@Test
	public void test(){
		try {
			GatherChannel.gatherWrite(col3,"aa.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
