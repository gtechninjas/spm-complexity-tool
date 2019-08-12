import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { KeywordupdateComponent } from './keywordupdate.component';

describe('KeywordupdateComponent', () => {
  let component: KeywordupdateComponent;
  let fixture: ComponentFixture<KeywordupdateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ KeywordupdateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(KeywordupdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
