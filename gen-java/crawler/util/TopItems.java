/**
 * Autogenerated by Thrift Compiler (0.9.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package crawler.util;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TopItems implements org.apache.thrift.TBase<TopItems, TopItems._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TopItems");

  private static final org.apache.thrift.protocol.TField ELEMENTS_FIELD_DESC = new org.apache.thrift.protocol.TField("elements", org.apache.thrift.protocol.TType.LIST, (short)1);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TopItemsStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TopItemsTupleSchemeFactory());
  }

  public List<Item> elements; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ELEMENTS((short)1, "elements");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // ELEMENTS
          return ELEMENTS;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ELEMENTS, new org.apache.thrift.meta_data.FieldMetaData("elements", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Item.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TopItems.class, metaDataMap);
  }

  public TopItems() {
  }

  public TopItems(
    List<Item> elements)
  {
    this();
    this.elements = elements;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TopItems(TopItems other) {
    if (other.isSetElements()) {
      List<Item> __this__elements = new ArrayList<Item>();
      for (Item other_element : other.elements) {
        __this__elements.add(new Item(other_element));
      }
      this.elements = __this__elements;
    }
  }

  public TopItems deepCopy() {
    return new TopItems(this);
  }

  @Override
  public void clear() {
    this.elements = null;
  }

  public int getElementsSize() {
    return (this.elements == null) ? 0 : this.elements.size();
  }

  public java.util.Iterator<Item> getElementsIterator() {
    return (this.elements == null) ? null : this.elements.iterator();
  }

  public void addToElements(Item elem) {
    if (this.elements == null) {
      this.elements = new ArrayList<Item>();
    }
    this.elements.add(elem);
  }

  public List<Item> getElements() {
    return this.elements;
  }

  public TopItems setElements(List<Item> elements) {
    this.elements = elements;
    return this;
  }

  public void unsetElements() {
    this.elements = null;
  }

  /** Returns true if field elements is set (has been assigned a value) and false otherwise */
  public boolean isSetElements() {
    return this.elements != null;
  }

  public void setElementsIsSet(boolean value) {
    if (!value) {
      this.elements = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ELEMENTS:
      if (value == null) {
        unsetElements();
      } else {
        setElements((List<Item>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ELEMENTS:
      return getElements();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ELEMENTS:
      return isSetElements();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TopItems)
      return this.equals((TopItems)that);
    return false;
  }

  public boolean equals(TopItems that) {
    if (that == null)
      return false;

    boolean this_present_elements = true && this.isSetElements();
    boolean that_present_elements = true && that.isSetElements();
    if (this_present_elements || that_present_elements) {
      if (!(this_present_elements && that_present_elements))
        return false;
      if (!this.elements.equals(that.elements))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(TopItems other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    TopItems typedOther = (TopItems)other;

    lastComparison = Boolean.valueOf(isSetElements()).compareTo(typedOther.isSetElements());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetElements()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.elements, typedOther.elements);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("TopItems(");
    boolean first = true;

    sb.append("elements:");
    if (this.elements == null) {
      sb.append("null");
    } else {
      sb.append(this.elements);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TopItemsStandardSchemeFactory implements SchemeFactory {
    public TopItemsStandardScheme getScheme() {
      return new TopItemsStandardScheme();
    }
  }

  private static class TopItemsStandardScheme extends StandardScheme<TopItems> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TopItems struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ELEMENTS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.elements = new ArrayList<Item>(_list0.size);
                for (int _i1 = 0; _i1 < _list0.size; ++_i1)
                {
                  Item _elem2; // optional
                  _elem2 = new Item();
                  _elem2.read(iprot);
                  struct.elements.add(_elem2);
                }
                iprot.readListEnd();
              }
              struct.setElementsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, TopItems struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.elements != null) {
        oprot.writeFieldBegin(ELEMENTS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.elements.size()));
          for (Item _iter3 : struct.elements)
          {
            _iter3.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TopItemsTupleSchemeFactory implements SchemeFactory {
    public TopItemsTupleScheme getScheme() {
      return new TopItemsTupleScheme();
    }
  }

  private static class TopItemsTupleScheme extends TupleScheme<TopItems> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TopItems struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetElements()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetElements()) {
        {
          oprot.writeI32(struct.elements.size());
          for (Item _iter4 : struct.elements)
          {
            _iter4.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TopItems struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.elements = new ArrayList<Item>(_list5.size);
          for (int _i6 = 0; _i6 < _list5.size; ++_i6)
          {
            Item _elem7; // optional
            _elem7 = new Item();
            _elem7.read(iprot);
            struct.elements.add(_elem7);
          }
        }
        struct.setElementsIsSet(true);
      }
    }
  }

}

