package org.cc.json;

/**
 * Configuration object for the JSON parser. The configuration is immutable.
 */
public class JSONParserConfiguration extends ParserConfiguration {

  /**
   * Configuration with the default values.
   */
  public JSONParserConfiguration() {
    super();
  }

  @Override
  protected JSONParserConfiguration clone() {
    return new JSONParserConfiguration();
  }

  @Override
  public JSONParserConfiguration withMaxNestingDepth(final int maxNestingDepth) {
    return super.withMaxNestingDepth(maxNestingDepth);
  }

}
