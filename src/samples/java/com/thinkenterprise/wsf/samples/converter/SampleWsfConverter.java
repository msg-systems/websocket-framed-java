package com.thinkenterprise.wsf.samples.converter;

import com.thinkenterprise.wsf.converter.WsfConverter;
import com.thinkenterprise.wsf.converter.WsfFrameToMessageConverter;
import com.thinkenterprise.wsf.domain.WsfFrame;
import com.thinkenterprise.wsf.domain.WsfFrameType;

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
		WsfFrameToMessageConverter conv = new WsfConverter(WsfFrameType.GRAPHQLREQUEST);

		// convert from WsfFrame to String
		String result = conv.convert(frame_input);
		System.out.println("result = " + result);

		// convert from String to WsfFrame
		WsfFrame frame_output = conv.convert(result);
		System.out.println("frame_output = " + frame_output);
	}

}
