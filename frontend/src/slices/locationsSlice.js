import { createSlice, createEntityAdapter } from '@reduxjs/toolkit';

const adapter = createEntityAdapter();
const initialState = adapter.getInitialState();

export const locationsSlice = createSlice({
  name: 'locations',
  initialState,
  reducers: {
    addLocations: adapter.addMany,
    addLocation: adapter.addOne,
  },
});

export const selectors = adapter.getSelectors((state) => state.locations);
export const { actions } = locationsSlice;
export default locationsSlice.reducer;
