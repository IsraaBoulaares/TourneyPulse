import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TournoiComponent } from './tournoi.component';

describe('TournoiComponent', () => {
  let component: TournoiComponent;
  let fixture: ComponentFixture<TournoiComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TournoiComponent]
    });
    fixture = TestBed.createComponent(TournoiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
