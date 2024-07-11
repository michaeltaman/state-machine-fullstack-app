import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class FlowStateService {
  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  getAllFlows(): Observable<any> {
    return this.http
      .get(`${this.apiUrl}/flows`)
      .pipe(catchError(this.handleError));
  }

  createFlow(name: string): Observable<any> {
    return this.http
      .post(`${this.apiUrl}/flows`, { name })
      .pipe(catchError(this.handleError));
  }

  createState(name: string, flowId: number): Observable<any> {
    return this.http
      .post(`${this.apiUrl}/states`, { name, flowId })
      .pipe(catchError(this.handleError));
  }

  getAllStatesByFlowId(flowId: number): Observable<any> {
    return this.http
      .get(`${this.apiUrl}/states/flow/${flowId}`)
      .pipe(catchError(this.handleError));
  }

  getAssociatedStates(flowId: number): Observable<any> {
    return this.http
      .get(`${this.apiUrl}/flows/${flowId}/associated-states`)
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse) {
    console.error('An error occurred:', error.error.message);
    return throwError('Something went wrong; please try again later.');
  }
}
