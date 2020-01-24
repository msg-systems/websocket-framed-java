/*******************************************************************************
 * *
 * **  Design and Development by msg Applied Technology Research
 * **  Copyright (c) 2019-2020 msg systems ag (http://www.msg-systems.com/)
 * **  All Rights Reserved.
 * ** 
 * **  Permission is hereby granted, free of charge, to any person obtaining
 * **  a copy of this software and associated documentation files (the
 * **  "Software"), to deal in the Software without restriction, including
 * **  without limitation the rights to use, copy, modify, merge, publish,
 * **  distribute, sublicense, and/or sell copies of the Software, and to
 * **  permit persons to whom the Software is furnished to do so, subject to
 * **  the following conditions:
 * **
 * **  The above copyright notice and this permission notice shall be included
 * **  in all copies or substantial portions of the Software.
 * **
 * **  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * **  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * **  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * **  IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * **  CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * **  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * **  SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * *
 ******************************************************************************/
package com.graphqlio.wsf.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Class for testing graphqlio classes WsfFrame and WsfFrameType
 *
 * @author Michael Schäfer
 * @author Torsten Kühnert
 */

public final class WsfFrameTest {

	@Test
	public void whenFrame2IsMadeOfFrame1ThenFieldsAreCorrectSet() {
		String fid = "fid";
		String rid_1 = "123987";
		String rid_2 = "123988";
		WsfFrameType type = WsfFrameType.GRAPHQLREQUEST;
		String data = "data";

		WsfFrame f_1 = WsfFrame.builder().fid(fid).rid(rid_1).type(type).data(data).build();
		WsfFrame f_2 = WsfFrame.builder().fromRequestMessage(f_1).build();

		Assertions.assertTrue(f_1.getFid().equals(f_2.getFid()));

		// rid's are unequal, rid-2 is rid-1 incremented:
		Assertions.assertTrue(!f_1.getRid().equals(f_2.getRid()));
		Assertions.assertTrue(f_1.getRid().equals(rid_1));
		Assertions.assertTrue(f_2.getRid().equals(rid_2));

		// types are unequal, type-2 is response:
		Assertions.assertTrue(!f_1.getType().equals(f_2.getType()));
		Assertions.assertTrue(f_1.getType().equals(WsfFrameType.GRAPHQLREQUEST));
		Assertions.assertTrue(f_2.getType().equals(WsfFrameType.GRAPHQLRESPONSE));

		// data's are unequal, data-2 is not set (null):
		Assertions.assertTrue(!f_1.getData().equals(f_2.getData()));
		Assertions.assertTrue(f_1.getData().equals(data));
		Assertions.assertTrue(f_2.getData() == null);

		// so over all: f-1 is not equal f-2:
		Assertions.assertTrue(!f_1.toString().equals(f_2.toString()));

		// this are the expected types:
		Assertions.assertTrue(f_1.getType().toString().equals("GRAPHQL-REQUEST"));
		Assertions.assertTrue(f_2.getType().toString().equals("GRAPHQL-RESPONSE"));
	}

}
