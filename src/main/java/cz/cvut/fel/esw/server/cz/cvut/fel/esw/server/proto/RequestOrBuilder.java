// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: messages.proto

package cz.cvut.fel.esw.server.proto;

public interface RequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:Request)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.Request.GetCount getCount = 1;</code>
   * @return Whether the getCount field is set.
   */
  boolean hasGetCount();
  /**
   * <code>.Request.GetCount getCount = 1;</code>
   * @return The getCount.
   */
  cz.cvut.fel.esw.server.proto.Request.GetCount getGetCount();
  /**
   * <code>.Request.GetCount getCount = 1;</code>
   */
  cz.cvut.fel.esw.server.proto.Request.GetCountOrBuilder getGetCountOrBuilder();

  /**
   * <code>.Request.PostWords postWords = 2;</code>
   * @return Whether the postWords field is set.
   */
  boolean hasPostWords();
  /**
   * <code>.Request.PostWords postWords = 2;</code>
   * @return The postWords.
   */
  cz.cvut.fel.esw.server.proto.Request.PostWords getPostWords();
  /**
   * <code>.Request.PostWords postWords = 2;</code>
   */
  cz.cvut.fel.esw.server.proto.Request.PostWordsOrBuilder getPostWordsOrBuilder();

  public cz.cvut.fel.esw.server.proto.Request.MsgCase getMsgCase();
}