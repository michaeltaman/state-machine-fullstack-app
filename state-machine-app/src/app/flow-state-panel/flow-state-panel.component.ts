import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { FlowStateService } from '../services/flow-state.service';
import { State } from '../models/state.interface'; // Import the State interface

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
  states: State[] = []; // Use the State interface for typing
  selectedFlowId: number | null = null;
  selectedStateId: number | null = null;
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
        (error: any) => {
          this.message = `It is not possible to duplicate the same FLOW ${this.flowName}`;
          this.messageType = 'error';
        }
      );
    }
  }

  createState() {
    if (this.stateName && this.selectedFlowId !== null) {
      this.flowStateService
        .createState(this.stateName, this.selectedFlowId)
        .subscribe(
          () => {
            this.getStates();
            this.message = `State ${this.stateName} is created`;
            this.messageType = 'success';
            this.stateName = '';
          },
          (error: any) => {
            this.message = `It is not possible to duplicate the same State ${this.stateName}`;
            this.messageType = 'error';
          }
        );
    }
  }

  getFlows() {
    this.flowStateService.getAllFlows().subscribe(
      (data) => {
        console.log('Flows:', data);
        this.flows = data;
        if (this.flows.length > 0) {
          this.selectedFlowId = this.flows[0].id;
          this.getStates();
        }
      },
      (error: any) => {
        console.error('Error fetching flows:', error);
      }
    );
  }

  getStates() {
    if (this.selectedFlowId !== null) {
      this.flowStateService.getAllStatesByFlowId(this.selectedFlowId).subscribe(
        (data: State[]) => {
          console.log('States:', data);
          this.states = data.map((state: State) => ({
            ...state,
            associated: state.flow.id === this.selectedFlowId,
          }));
          if (this.states.length > 0) {
            this.selectedStateId = this.states[0].id;
          }
        },
        (error: any) => {
          console.error('Error fetching states:', error);
        }
      );
    }
  }
}
