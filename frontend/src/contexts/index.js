// @ts-check

import { createContext } from 'react';

export const NotificationContext = createContext({
  addMessage: () => {},
  addError: () => {},
  clean: () => {},
});
