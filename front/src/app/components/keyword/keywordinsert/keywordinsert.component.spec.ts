import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { KeywordinsertComponent } from './keywordinsert.component';

describe('KeywordinsertComponent', () => {
  let component: KeywordinsertComponent;
  let fixture: ComponentFixture<KeywordinsertComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ KeywordinsertComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(KeywordinsertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
