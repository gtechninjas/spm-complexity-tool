import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OperatorupdateComponent } from './operatorupdate.component';

describe('operatorupdateComponent', () => {
  let component: OperatorupdateComponent;
  let fixture: ComponentFixture<OperatorupdateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OperatorupdateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OperatorupdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
