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

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.List;

import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphqlio.wsf.domain.WsfFrame;
import com.graphqlio.wsf.domain.WsfFrameType;
import com.graphqlio.wsf.exception.WsfException;

import co.nstant.in.cbor.CborDecoder;
import co.nstant.in.cbor.CborEncoder;
import co.nstant.in.cbor.model.ByteString;
import co.nstant.in.cbor.model.DataItem;

/**
 * Abstract class for dispatching to correct converter method
 *
 * @author Michael Schäfer
 * @author Torsten Kühnert
 */
public abstract class WsfAbstractConverter implements WsfFrameToMessageConverter {

  private final Logger logger = LoggerFactory.getLogger(WsfAbstractConverter.class);

  public static final String SUB_PROTOCOL_TEXT = "text";
  public static final String SUB_PROTOCOL_CBOR = "cbor";
  public static final String SUB_PROTOCOL_MSGPACK = "msgpack";

  protected WsfFrameType frameType;

  abstract WsfFrame convert(String message);

  abstract String convert(WsfFrame frame);

  @Override
  public WsfFrame convert(Object message, String protocol) {
    logger.info("convert::protocol = " + protocol);
    logger.info("convert::message = " + message);

    if (message instanceof String) {
      return convert((String) message);

    } else if (message instanceof ByteBuffer) {

      if (SUB_PROTOCOL_CBOR.equalsIgnoreCase(protocol)) {
        return convert(fromCbor((ByteBuffer) message));

      } else if (SUB_PROTOCOL_MSGPACK.equalsIgnoreCase(protocol)) {
        return convert(fromMsgPack((ByteBuffer) message));

      } else {
        throw new IllegalStateException(
            "Unexpected converter protocol & message type: " + protocol + " & " + message);
      }

    } else {
      throw new IllegalStateException(
          "Unexpected converter protocol & message type: " + protocol + " & " + message);
    }
  }

  @Override
  public Object convert(WsfFrame frame, String protocol) {
    logger.info("convert::protocol = " + protocol);
    logger.info("convert::frame = " + frame);

    if (SUB_PROTOCOL_CBOR.equalsIgnoreCase(protocol)) {
      return toCbor(convert(frame));

    } else if (SUB_PROTOCOL_MSGPACK.equalsIgnoreCase(protocol)) {
      return toMsgPack(convert(frame));

    } else {
      // default Text/String
      return convert(frame);
    }
  }

  public static String fromCbor(ByteBuffer message) {
    try {
      List<DataItem> dataItems = CborDecoder.decode(message.array());

      // wenn keine Exception:
      if (dataItems == null || dataItems.isEmpty()) {
        // logging

      } else if (!dataItems.isEmpty()) {
        if (dataItems.size() >= 2) {
          // logging
        }

        DataItem dataItem = dataItems.get(0);

        if (dataItem instanceof ByteString) {
          String input = new String(((ByteString) dataItem).getBytes());
          return input;

        } else {
          // logging
        }
      }

      return null;

    } catch (Exception e) {
      throw new WsfException();
    }
  }

  public static String fromMsgPack(ByteBuffer message) {
    try {
      MessageUnpacker unpacker = null;
      String input = null;
      try {
        unpacker = MessagePack.newDefaultUnpacker(message.array());
        input = unpacker.unpackString();

      } finally {
        if (unpacker != null) {
          unpacker.close();
        }
      }

      return input;

    } catch (Exception e) {
      throw new WsfException();
    }
  }

  public static byte[] toCbor(String message) {
    try {
      byte[] bytes = message.getBytes();
      DataItem dataItem = new ByteString(bytes);

      ByteArrayOutputStream os = new ByteArrayOutputStream();
      new CborEncoder(os).encode(dataItem);

      return os.toByteArray();

    } catch (Exception e) {
      throw new WsfException();
    }
  }

  public static byte[] toMsgPack(String message) {
    try {
      MessageBufferPacker packer = null;
      try {
        packer = MessagePack.newDefaultBufferPacker();
        packer.packString(message);
      } finally {
        if (packer != null) packer.close();
      }
      return packer.toByteArray();

    } catch (Exception e) {
      throw new WsfException();
    }
  }
}
