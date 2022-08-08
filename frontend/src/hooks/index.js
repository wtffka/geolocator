// @ts-check

import { useContext } from 'react';

import { NotificationContext } from '../contexts/index.js';

export const useNotify = () => useContext(NotificationContext);
