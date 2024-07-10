import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class FlowStateService {
  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  createFlow(name: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/flows`, { name });
  }

  getAllFlows(): Observable<any> {
    return this.http.get(`${this.apiUrl}/flows`);
  }

  createState(name: string, flowId: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/states`, { name, flowId });
  }

  getAllStatesByFlowId(flowId: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/states/flow/${flowId}`);
  }
}
