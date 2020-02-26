/**
 * *****************************************************************************
 *
 * <p>Design and Development by msg Applied Technology Research Copyright (c) 2019-2020 msg systems
 * ag (http://www.msg-systems.com/) All Rights Reserved.
 *
 * <p>Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * <p>The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * <p>THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * <p>****************************************************************************
 */
package com.graphqlio.wsf.converter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphqlio.wsf.domain.WsfFrame;
import com.graphqlio.wsf.domain.WsfFrameType;
import com.graphqlio.wsf.exception.WsfException;

/**
 * Class for implementing converters for both directions
 *
 * @author Michael Schäfer
 * @author Dr. Edgar Müller
 * @author Torsten Kühnert
 */
public abstract class WsfConverter extends WsfAbstractConverter {

  private final Logger logger = LoggerFactory.getLogger(WsfConverter.class);

  protected WsfConverter(WsfFrameType frameType) {
    this.frameType = frameType;
  }

  @Override
  public String convert(WsfFrame message) {

    if (message.getType() != frameType) {
      logger.warn(
          String.format(
              "WsfConverter: Expected type (%s), got type (%s)", frameType, message.getType()));
      /*
       * not necessary to throw an exception here. converter is able to handle message
       * type
       *
       */
      // throw new WsfException();
    }

    // Create frame from message
    String frame =
        "["
            + message.getFid()
            + ","
            + message.getRid()
            + ","
            + "\""
            + message.getType()
            + "\""
            + ","
            + message.getData()
            + "]";
    return frame;
  }

  @Override
  public WsfFrame convert(String frame) {
    String fid;
    String rid;
    WsfFrameType type;
    String data;

    // Actually we only convert the GRAPHQLREQUEST frame type
    WsfFrameType graphQLIOMessageType = WsfFrameType.valueOf(frameType.name());

    JSONArray arr = null;
    try {
      arr = new JSONArray(frame);
      logger.info("arr = " + arr);

    } catch (JSONException e) {
      throw new WsfException();
    }

    logger.info("arr.length = " + arr.length());
    if (arr.length() < 4) {
      throw new WsfException();

    } else {
      try {
        Object obj = arr.get(0);
        if (obj instanceof String) {
          fid = (String) obj;
        } else if (obj instanceof Integer) {
          fid = "" + (Integer) obj;
        } else {
          throw new WsfException();
        }
        logger.info("fid = " + fid);

      } catch (JSONException e) {
        throw new WsfException();
      }
      try {
        Object obj = arr.get(1);
        if (obj instanceof String) {
          rid = (String) obj;
        } else if (obj instanceof Integer) {
          rid = "" + (Integer) obj;
        } else {
          throw new WsfException();
        }
        logger.info("rid = " + rid);

      } catch (JSONException e) {
        throw new WsfException();
      }
      try {
        if (!arr.getString(2).equals(graphQLIOMessageType.toString())) {
          throw new WsfException();
        }

      } catch (JSONException e) {
        throw new WsfException();
      }
      type = graphQLIOMessageType;
      logger.info("type = " + type);
      try {
        JSONObject obj = arr.getJSONObject(3);
        // keine Exception? dann weiter:
        if (obj.has("query")) {
          data = obj.getString("query");
          logger.info("data = " + data);

        } else {
          throw new WsfException();
        }

      } catch (JSONException e) {
        throw new WsfException();
      }
    }

    // Build Message
    return WsfFrame.builder().fid(fid).rid(rid).type(type).data(data).build();
  }
}
