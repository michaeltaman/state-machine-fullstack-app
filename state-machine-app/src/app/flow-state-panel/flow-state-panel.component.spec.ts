import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FlowStatePanelComponent } from './flow-state-panel.component';

describe('FlowStatePanelComponent', () => {
  let component: FlowStatePanelComponent;
  let fixture: ComponentFixture<FlowStatePanelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FlowStatePanelComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FlowStatePanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
