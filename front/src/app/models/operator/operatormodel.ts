export class OperatorModel {
  public operatorId        ?: string;
  public operator               ?: string;
  public operatorType               ?: string;
  public language              ?: string;
  public value              ?: string;
  constructor(
    operatorId: string,
    operator: string,
    operatorType: string,
    language: string,
    value: string
  ) {
    this.operatorId = operatorId;
    this.operator = operator;
    this.operatorType = operatorType;
    this.language = language;
    this.value = value;
  }
}
