import { TestBed } from '@angular/core/testing';

import { FlowStateService } from './services/flow-state.service';

describe('FlowStateService', () => {
  let service: FlowStateService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FlowStateService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
