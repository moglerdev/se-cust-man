import { create } from 'zustand';
import { Customer } from './model';

// customer store with undo and redo 

export const useCustomerStore = create<{
  customers: Customer[];
  redoState: Customer[][];
  undoState: Customer[][];
  addCustomer: (customer: Customer) => number;
  setCustomer: (customer: Customer) => void;
  removeCustomer: (id: number) => void;
  undo: () => void;
  redo: () => void;
}>((set, get) => ({
  customers: [],
  redoState: [],
  undoState: [],
  addCustomer: (customer) => {
    const { customers, undoState } = get();
    customer.id = customers.length + 1;
    set((state) => ({
      customers: [...customers, customer],
      undoState: [...undoState, customers],
      redoState: []
    }));
    return customer.id;
  },
  setCustomer: (customer) => {
    const { customers, undoState } = get();
    set((state) => ({
      customers: customers.map((c) => (c.id === customer.id ? customer : c)),
      undoState: [...undoState, customers],
      redoState: []
    }));
  },
  removeCustomer: (id) =>
    set((state) => ({ customers: state.customers.filter((c) => c.id !== id) })),
  undo: () => {
    const { redoState, undoState } = get();
    console.log(redoState);
    if (undoState.length > 0) {
      set((state) => ({
        customers: undoState[undoState.length - 1],
        redoState: [...redoState, undoState[undoState.length - 1]],
        undoState: undoState.slice(0, undoState.length - 1)
      }));
    }
  },
  redo: () => {
    const { redoState, undoState } = get();
    if (redoState.length > 0) {
      set((state) => ({
        customers: redoState[redoState.length - 1],
        undoState: [...undoState, redoState[redoState.length - 1]],
        redoState: redoState.slice(0, redoState.length - 1)
      }));
    }
  }
}));
