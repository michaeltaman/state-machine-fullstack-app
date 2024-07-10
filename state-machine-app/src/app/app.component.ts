import { Component } from '@angular/core';
import { FlowStatePanelComponent } from './flow-state-panel/flow-state-panel.component';
import { LogRecordPanelComponent } from './log-record-panel/log-record-panel.component';
import { LogPanelComponent } from './log-panel/log-panel.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    FlowStatePanelComponent,
    LogRecordPanelComponent,
    LogPanelComponent,
  ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'flow-state-app';
}
