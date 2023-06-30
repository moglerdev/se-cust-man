import { create } from 'zustand';
import { Customer, Project, Task } from './model';

// customer store with undo and redo 

export const useCustomerStore = create<{
  customers: Customer[];
  redoState: Customer[][];
  undoState: Customer[][];
  addCustomer: (customer: Customer) => number;
  setCustomer: (customer: Customer) => void;
  removeCustomer: (id: number) => void;
  getProjectByCustomer(customerId: number): Project[];
  getTaskByProject(projectId: number): Task[];
  undo: () => void;
  redo: () => void;
}>((set, get) => ({
  customers: [],
  redoState: [],
  undoState: [],
  getProjectByCustomer: (customerId) => {
    const customer = get().customers.find((c) => c.id === customerId);
    return customer ? customer.project : [];
  },
  getTaskByProject: (projectId) => {
    const project = get().getProjectByCustomer(projectId).find((p) => p.id === projectId);
    return project ? project.tasks : [];
  },
  addCustomer: (customer) => {
    customer.id = get().customers.length + 1;
    set((state) => {
      const { customers, undoState } = state;
      const undoStateNew = [...undoState, [...customers]]
      return {
        customers: [...customers, customer],
        undoState: undoStateNew,
        redoState: []
      };
    })
    return customer.id;
  },
  setCustomer: (customer) => {
    set((state) => {
      const { customers, undoState } = state;
      const undoStateNew = [...undoState, [...customers]]
      return {
        customers: customers.map((c) => (c.id === customer.id ? customer : c)),
        undoState: undoStateNew,
        redoState: []
      };
    })
  },
  removeCustomer: (id) => {
    set((state) => {
      const { customers, undoState } = state;
      const undoStateNew = [...undoState, [...customers]]
      return {
        customers: customers.filter((c) => c.id !== id),
        undoState: undoStateNew,
        redoState: []
      };
    })
  },
  undo: () => {
    set((state) => {
      const { undoState, redoState, customers } = state
      const undo = undoState.pop();
      if (undo) {
        console.log(undo)
        return {
          customers: undo,
          undoState: [...undoState],
          redoState: [...redoState, customers]
        };
      }
      return state;
    })
  },
  redo: () => {
    set((state) => {
      const { undoState, redoState, customers } = state
      const redo = redoState.pop();
      if (redo) {
        return {
          customers: redo,
          undoState: [...undoState, customers],
          redoState: [...redoState]
        };
      }
      return state;
    })
  }
}));
