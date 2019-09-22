export class KeywordModel {
  public keywordId             ?: string;
  public keyword               ?: string;
  public language              ?: string;
  public value                 ?: string;
  constructor(
    keywordId: string,
    keyword: string,
    language: string,
    value: string
  ) {
    this.keywordId = keywordId;
    this.keyword = keyword;
    this.language = language;
    this.value = value;
  }
}
