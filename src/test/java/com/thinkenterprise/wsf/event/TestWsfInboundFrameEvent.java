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
package com.thinkenterprise.wsf.event;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thinkenterprise.wsf.domain.WsfFrame;
import com.thinkenterprise.wsf.domain.WsfFrameType;
import com.thinkenterprise.wsf.event.WsfInboundFrameEvent;

/**
 * Class for testing graphqlio class WsfInboundFrameEvent
 *
 * @author Michael Schäfer
 * @author Torsten Kühnert
 */

public class TestWsfInboundFrameEvent {

	@Test
	public void testWsfInboundFrameEvent() {
		String fid = "fid";
		String rid = "rid";
		WsfFrameType type = WsfFrameType.GRAPHQLREQUEST;
		String data = "data";

		WsfFrame frame = WsfFrame.builder().fid(fid).rid(rid).type(type).data(data).build();
		String cid = "cid";

		WsfInboundFrameEvent in = new WsfInboundFrameEvent(frame, cid);

		Assertions.assertTrue(in.getFrame().equals(frame));
		Assertions.assertTrue(in.getCid().equals(cid));

		Assertions.assertTrue(in.getFrame().getFid().equals(fid));
		Assertions.assertTrue(in.getFrame().getRid().equals(rid));
		Assertions.assertTrue(in.getFrame().getType().equals(type));
		Assertions.assertTrue(in.getFrame().getData().equals(data));

		Assertions.assertTrue(in.getSource().equals(frame));
		Assertions.assertTrue(in.getTimestamp() != 0);
	}

}
