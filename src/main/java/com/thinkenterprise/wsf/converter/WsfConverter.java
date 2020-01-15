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
package com.thinkenterprise.wsf.converter;

import java.util.Arrays;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.thinkenterprise.wsf.domain.WsfFrame;
import com.thinkenterprise.wsf.domain.WsfFrameType;
import com.thinkenterprise.wsf.exception.WsfException;

/**
 * Class for implementing converters for both directions
 *
 * @author Michael Schäfer
 * @author Dr. Edgar Müller
 */

public class WsfConverter implements WsfFrameToMessageConverter, WsfMessageToFrameConverter {

	private final Logger logger = LoggerFactory.getLogger(WsfConverter.class);
	
	
	private WsfFrameType frameType;	

	public WsfConverter(WsfFrameType frameType) {
		this.frameType = frameType;
	}
	
		
	@Override
	public String convert(WsfFrame message) {
		
		
		if(message.getType()!=frameType) {
			logger.warn(String.format("WsfConverter: Expected type (%s), got type (%s)", frameType, message.getType()));
/*
*			not necessary to throw an exception here.
*			converter is able to handle message type
* 
*/
			//			throw new WsfException();
		}

		// Create frame from message
		String frame = "[" + message.getFid() + "," + message.getRid() + "," + "\""+ message.getType() + "\"" + "," + message.getData() + "]";
		return frame;
	}


	@Override
	public WsfFrame convert(String frame) {
		String fid;
		String rid;
		WsfFrameType type;
		String data;

		// Delete not nedded characters 
		frame = StringUtils.deleteAny(frame, "[");
		frame = StringUtils.deleteAny(frame, "]");

		// Tokenize String 
		String[] messageValues = StringUtils.tokenizeToStringArray(frame, ",");

		logger.info("messageValues (a) = " + Arrays.toString(messageValues));

		// Check count of values 
		if (messageValues.length != 4)
			throw new WsfException();

		// Delete excape characters for double quotas 
		// ToDo: not 2 insted 3 or?
		messageValues[2] = StringUtils.deleteAny(messageValues[2], "\"");
		messageValues[3] = StringUtils.deleteAny(messageValues[3], "\"");
		messageValues[3] = StringUtils.deleteAny(messageValues[3], "\r\n");
		messageValues[3] = StringUtils.delete(messageValues[3], "\\n");
		messageValues[3] = StringUtils.replace(messageValues[3], "query:", "query ");

		logger.info("messageValues (b) = " + Arrays.toString(messageValues));

		if (messageValues[3].length() > 2) {
			int len = messageValues[3].length();
			String dataMessage = messageValues[3];
			if (dataMessage.charAt(0) == '{'  &&  dataMessage.charAt(len-1) == '}') {
				messageValues[3] = dataMessage.substring(1,len-1);
			}
		}
		logger.info("messageValues (c) = " + Arrays.toString(messageValues));

		// Actually we only convert the GRAPHQLREQUEST frame type  
		WsfFrameType graphQLIOMessageType = WsfFrameType
				.valueOf(frameType.name());

		// Check the right frame type 
		if (!messageValues[2].equals(graphQLIOMessageType.toString()))
			throw new WsfException();

		// Set local variables more readable 
		fid=messageValues[0];
		rid=messageValues[1];
		type=graphQLIOMessageType;
		data=messageValues[3];

		// Build Message  
		return  WsfFrame.builder().fid(fid).rid(rid).type(type).data(data).build();
	}

	private String surroundWithQuotes(String value) {
		return "\"" + value + "\"";
	}

	public String createData(Set<String> set) {
        // ToDo : Build Response Data with JSON - JSON-field: data, JSON-Data: Array of sids 
        // "data":["5c989173-0eed-55b6-8f48-44890f621aaa"]		

		String result = set.isEmpty() ? "" : surroundWithQuotes(String.join(surroundWithQuotes(", "), set));
		return "{" + surroundWithQuotes("data") + ":[" +  result + "]}";
	}
	
	
}
