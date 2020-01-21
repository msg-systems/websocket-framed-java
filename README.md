# About
websocket-framed-java is the **Java** implementation of the **JavaScript** websocket-framed reference implementation. 
The documentation of the reference implementation can be found at (https://github.com/rse/websocket-framed). 

# Installation
## Maven 

```
mvn clean install
```

## Gradle 

FIXME

# Usage 

## Maven 
```
<dependency>
	<groupId>com.thinkenterprise</groupId>
	<artifactId>websocket-framed-java</artifactId>
	<version>0.0.9</version>
</dependency>

```

## Gradle 

```
dependencies {
  compile 'com.thinkenterprise:websocket-framed-java:0.0.9'
}
```


# Sample 

Working with codecs

``` java
		String data = "this text is for excryption and decryption";

		WsfCodec codec = new WsfCBORCodec();
		byte[] bytes = codec.encode(data);
		String str = codec.decode(bytes);
		// data equals str == true
```

Working with frames

``` java
		String fid = "fid";
		String rid = "123987";
		WsfFrameType type = WsfFrameType.GRAPHQLREQUEST;
		String data = "{\"query\": \"{data}\"}";

		WsfFrame input_frame = WsfFrame.builder().fid(fid).rid(rid).type(type).data(data).build();
```

Working with converter

``` java
		WsfFrameToMessageConverter conv = new WsfConverter(WsfFrameType.GRAPHQLREQUEST);
		String string_from_frame = conv.convert(input_frame);
```


# License 
Design and Development by msg Applied Technology Research
Copyright (c) 2019-2020 msg systems ag (http://www.msg-systems.com/)
All Rights Reserved.
 
Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
"Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:
 
The above copyright notice and this permission notice shall be included
in all copies or substantial portions of the Software.
 
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
