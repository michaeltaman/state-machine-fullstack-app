import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LogRecordPanelComponent } from './log-record-panel.component';

describe('LogRecordPanelComponent', () => {
  let component: LogRecordPanelComponent;
  let fixture: ComponentFixture<LogRecordPanelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LogRecordPanelComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LogRecordPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
