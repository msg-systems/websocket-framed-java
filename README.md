# websocket-framed-java

Framed WebSocket Communication for Java.

About
-----

This is a small Java library for Java
to encode/decode WebSocket messages to/from a wrapping "frame" data structure.

Usage of converter
------------------

```java
		// build frame input
		WsfFrame frame_input = WsfFrame.builder().fid("fid").rid("123").type(WsfFrameType.GRAPHQLREQUEST).data("data").build();
		// create converter
		WsfConverter conv = new WsfConverter(WsfFrameType.GRAPHQLREQUEST);
		// convert from WsfFrame to String
		String result = conv.convert(frame_input);
		// convert from String to WsfFrame
		WsfFrame frame_output = conv.convert(result);
```

Usage of codecs
---------------

```java
		WsfCodec codec = new WsfCBORCodec();
		byte[] bytes = codec.encode(data);
		String result = codec.decode(bytes);
```

Frame Format
------------

```
Frame: [ fid: number, rid: number, type: string, data: any ]

+--------+--------+--------+--------+
|  fid   |  rid   |  type  |  data  |
+--------+--------+--------+--------+
```

- The `fid` is the frame id, a numeric unique id between 0 and 2^32 the sending side manages per connection.

- The `rid` is the reply id, the frame id of a previously received frame the current sent frame references.

- The `type` is the frame type, a string identifying the type of `data`.

- The `data` is the arbitrary data structure send in the frame. Any JSON is valid here.
