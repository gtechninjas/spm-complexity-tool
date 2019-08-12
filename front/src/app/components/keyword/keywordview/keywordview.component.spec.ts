import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { KeywordviewComponent } from './keywordview.component';

describe('KeywordviewComponent', () => {
  let component: KeywordviewComponent;
  let fixture: ComponentFixture<KeywordviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ KeywordviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(KeywordviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
