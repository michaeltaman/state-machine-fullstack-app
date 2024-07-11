export interface State {
  id: number;
  name: string;
  value: string;
  initialState: boolean;
  flow: {
    id: number;
    name: string;
  };
  associated?: boolean; // Optional property to indicate association
}
