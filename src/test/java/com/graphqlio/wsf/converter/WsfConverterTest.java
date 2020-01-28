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
package com.graphqlio.wsf.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.graphqlio.wsf.domain.WsfFrame;
import com.graphqlio.wsf.domain.WsfFrameType;

/**
 * Class for testing graphqlio class WsfConverter
 *
 * @author Michael Schäfer
 * @author Torsten Kühnert
 */

public class WsfConverterTest {

	@Test
	public void whenFrameIsConvertedByRequestConverterThenResultIsCorrect() {
		String fid = "fid";
		String rid = "123987";
		WsfFrameType type = WsfFrameType.GRAPHQLREQUEST;
		String data = "data";

		WsfFrame frame = WsfFrame.builder().fid(fid).rid(rid).type(type).data(data).build();

		WsfFrameToMessageConverter conv = new WsfRequestConverterImpl();
		String result = (String) conv.convert(frame, WsfAbstractConverter.SUB_PROTOCOL_TEXT);

		String expected = "[fid,123987,\"GRAPHQL-REQUEST\",data]";
		Assertions.assertTrue(result.equals(expected));
	}

	@Test
	public void whenFrameIsConvertedByResponseConverterThenResultIsCorrect() {
		String input = "[fid,123987,\"GRAPHQL-RESPONSE\",{\"query\":\"data\"}]";

		WsfFrameToMessageConverter conv = new WsfResponseConverterImpl();
		WsfFrame result = conv.convert(input, WsfAbstractConverter.SUB_PROTOCOL_TEXT);

		String fid = "fid";
		String rid = "123987";
		WsfFrameType type = WsfFrameType.GRAPHQLRESPONSE;
		String data = "data";

		Assertions.assertTrue(result.getFid().equals(fid));
		Assertions.assertTrue(result.getRid().equals(rid));
		Assertions.assertTrue(result.getType().equals(type));
		Assertions.assertTrue(result.getData().equals(data));
	}

	private class WsfRequestConverterImpl extends WsfConverter {

		public WsfRequestConverterImpl() {
			super(WsfFrameType.GRAPHQLREQUEST);
		}

	}

	private class WsfResponseConverterImpl extends WsfConverter {

		public WsfResponseConverterImpl() {
			super(WsfFrameType.GRAPHQLRESPONSE);
		}

	}

}
