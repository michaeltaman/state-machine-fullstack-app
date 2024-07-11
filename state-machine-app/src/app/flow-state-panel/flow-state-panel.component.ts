import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { FlowStateService } from '../flow-state.service';

@Component({
  selector: 'app-flow-state-panel',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './flow-state-panel.component.html',
  styleUrls: ['./flow-state-panel.component.css'],
})
export class FlowStatePanelComponent implements OnInit {
  flowName: string = '';
  stateName: string = '';
  flows: any[] = [];
  selectedFlowId: number | null = null;
  message: string = '';
  messageType: 'success' | 'error' = 'success';

  constructor(private flowStateService: FlowStateService) {}

  ngOnInit(): void {
    this.getFlows();
  }

  createFlow() {
    if (this.flowName) {
      this.flowStateService.createFlow(this.flowName).subscribe(
        () => {
          this.getFlows();
          this.message = `FLOW ${this.flowName} is created`;
          this.messageType = 'success';
          this.flowName = '';
        },
        (error) => {
          this.message = `It is not possible to duplicate the same FLOW ${this.flowName}`;
          this.messageType = 'error';
        }
      );
    }
  }

  getFlows() {
    this.flowStateService.getAllFlows().subscribe((data) => {
      this.flows = data;
    });
  }

  createState() {
    if (this.stateName && this.selectedFlowId !== null) {
      this.flowStateService
        .createState(this.stateName, this.selectedFlowId)
        .subscribe(
          () => {
            this.message = `State ${this.stateName} is created`;
            this.messageType = 'success';
            this.stateName = '';
          },
          (error) => {
            this.message = `It is not possible to duplicate the same State ${this.stateName}`;
            this.messageType = 'error';
          }
        );
    }
  }
}
