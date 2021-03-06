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
package com.graphqlio.wsf.domain;

/**
 * Class for holding the inbound / outbound frame data
 *
 * @author Michael Schäfer
 * @author Dr. Edgar Müller
 */
public final class WsfFrame {

  private String fid;
  private String rid;
  private WsfFrameType type;
  private String data;

  private WsfFrame(Builder builder) {
    this.fid = builder.fid;
    this.rid = builder.rid;
    this.type = builder.type;
    this.data = builder.data;
  }

  public String getFid() {
    return fid;
  }

  public String getRid() {
    return rid;
  }

  public WsfFrameType getType() {
    return type;
  }

  public String getData() {
    return data;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {

    private String fid;
    private String rid;
    private WsfFrameType type;
    private String data;

    private Builder() {}

    public Builder fid(String fid) {
      this.fid = fid;
      return this;
    }

    public Builder rid(String rid) {
      this.rid = rid;
      return this;
    }

    public Builder data(String data) {
      this.data = data;
      return this;
    }

    public Builder type(WsfFrameType type) {
      this.type = type;
      return this;
    }

    public WsfFrame build() {
      return new WsfFrame(this);
    }

    public Builder fromRequestMessage(WsfFrame message) {
      this.fid = message.getFid();

      Long ridValue = Long.decode(message.getRid());
      ++ridValue;
      this.rid = ridValue.toString();

      this.type = WsfFrameType.GRAPHQLRESPONSE;

      return this;
    }
  }

  @Override
  public String toString() {
    return "WsfFrame [fid=" + fid + ", rid=" + rid + ", type=" + type + ", data=" + data + "]";
  }
}
