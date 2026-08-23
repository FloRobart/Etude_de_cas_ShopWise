import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommercantsDetails } from './commercants-details';

describe('CommercantsDetails', () => {
  let component: CommercantsDetails;
  let fixture: ComponentFixture<CommercantsDetails>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CommercantsDetails],
    }).compileComponents();

    fixture = TestBed.createComponent(CommercantsDetails);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
