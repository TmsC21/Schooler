import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RobotMotionComponent } from './robot-motion.component';

describe('RobotMotionComponent', () => {
  let component: RobotMotionComponent;
  let fixture: ComponentFixture<RobotMotionComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RobotMotionComponent]
    });
    fixture = TestBed.createComponent(RobotMotionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
