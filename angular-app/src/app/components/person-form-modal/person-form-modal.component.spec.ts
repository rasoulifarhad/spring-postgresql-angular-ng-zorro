import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonFormModalComponent } from './person-form-modal.component';

describe('PersonFormModalComponent', () => {
  let component: PersonFormModalComponent;
  let fixture: ComponentFixture<PersonFormModalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PersonFormModalComponent]
    });
    fixture = TestBed.createComponent(PersonFormModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
