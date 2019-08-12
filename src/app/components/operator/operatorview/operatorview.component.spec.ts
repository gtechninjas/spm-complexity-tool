import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OperatorviewComponent } from './operatorview.component';

describe('OperatorviewComponent', () => {
  let component: OperatorviewComponent;
  let fixture: ComponentFixture<OperatorviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OperatorviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OperatorviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
