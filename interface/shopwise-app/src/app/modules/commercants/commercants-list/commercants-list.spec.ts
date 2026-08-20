import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommercantsList } from './commercants-list';

describe('CommercantsList', () => {
  let component: CommercantsList;
  let fixture: ComponentFixture<CommercantsList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CommercantsList],
    }).compileComponents();

    fixture = TestBed.createComponent(CommercantsList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
