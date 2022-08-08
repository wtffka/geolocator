// @ts-check

import React from 'react';
import { BrowserRouter as Router } from 'react-router-dom';
import { Provider } from 'react-redux';
import i18next from 'i18next';
import { I18nextProvider, initReactI18next } from 'react-i18next';

import App from './components/App.jsx';
import NotificationProvider from './providers/NotificationProvider.jsx';
import resources from './locales/index.js';

import store from './slices/index.js';

const app = async () => {
  const i18n = i18next.createInstance();

  await i18n
    .use(initReactI18next)
    .init({
      resources,
      fallbackLng: 'ru',
    });

  return (
    <Provider store={store}>
      <Router>
        <NotificationProvider>
          <I18nextProvider i18n={i18n}>
            <App />
          </I18nextProvider>
        </NotificationProvider>
      </Router>
    </Provider>
  );
};

export default app;
