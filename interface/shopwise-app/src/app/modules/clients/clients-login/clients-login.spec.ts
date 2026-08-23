import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientsLogin } from './clients-login';

describe('ClientsLogin', () => {
  let component: ClientsLogin;
  let fixture: ComponentFixture<ClientsLogin>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClientsLogin],
    }).compileComponents();

    fixture = TestBed.createComponent(ClientsLogin);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
