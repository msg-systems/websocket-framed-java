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
package com.graphqlio.wsf.samples.converter;

import com.graphqlio.wsf.converter.WsfAbstractConverter;
import com.graphqlio.wsf.converter.WsfConverter;
import com.graphqlio.wsf.converter.WsfFrameToMessageConverter;
import com.graphqlio.wsf.domain.WsfFrame;
import com.graphqlio.wsf.domain.WsfFrameType;

/**
 * Class showing converter functionality
 *
 * @author Michael Schäfer
 * @author Torsten Kühnert
 */

public class SampleWsfConverter {

	public static void main(String[] args) throws Exception {
		String fid = "fid";
		String rid = "123987";
		WsfFrameType type = WsfFrameType.GRAPHQLREQUEST;
		String data = "{\"query\": \"{data}\"}";

		// build frame input
		WsfFrame frame_input = WsfFrame.builder().fid(fid).rid(rid).type(type).data(data).build();
		System.out.println("frame_input = " + frame_input);

		// create converter
		WsfFrameToMessageConverter conv = new WsfRequestConverterImpl();

		// convert from WsfFrame to String
		String result = (String) conv.convert(frame_input, WsfAbstractConverter.SUB_PROTOCOL_TEXT);
		System.out.println("result = " + result);

		// convert from String to WsfFrame
		WsfFrame frame_output = conv.convert(result, WsfAbstractConverter.SUB_PROTOCOL_TEXT);
		System.out.println("frame_output = " + frame_output);
	}

	private static class WsfRequestConverterImpl extends WsfConverter {

		public WsfRequestConverterImpl() {
			super(WsfFrameType.GRAPHQLREQUEST);
		}

	}

}
