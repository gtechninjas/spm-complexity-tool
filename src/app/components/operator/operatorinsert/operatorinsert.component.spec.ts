import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OperatorinsertComponent } from './operatorinsert.component';

describe('OperatorinsertComponent', () => {
  let component: OperatorinsertComponent;
  let fixture: ComponentFixture<OperatorinsertComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OperatorinsertComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OperatorinsertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
