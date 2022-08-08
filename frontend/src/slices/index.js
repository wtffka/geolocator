// @ts-check

import { configureStore } from '@reduxjs/toolkit';
import locationsReducer from './locationsSlice.js';
import notifyReducer from './notificationSlice.js';

export default configureStore({
  reducer: {
    locations: locationsReducer,
    notify: notifyReducer,
  },
});
