// @ts-check

import React, { useEffect, useState } from 'react';
import {
  Switch,
  Route,
} from 'react-router-dom';
import { useDispatch } from 'react-redux';
import axios from 'axios';

import Notification from './Notification.jsx';

import Navbar from './Navbar.jsx';
import Welcome from './Welcome.jsx';
import NotFoundPage from './NotFoundPage.jsx';
import LocationsComponent from './Locations/Locations.jsx';

import routes from '../routes.js';

import { actions as locationsActions } from '../slices/locationsSlice.js';

import { useNotify } from '../hooks/index.js';

import NewLocation from './Locations/NewLocation';
import AboutPage from './AboutPage.jsx';
import Location from './Locations/Location.jsx';

const App = () => {
  const notify = useNotify();
  const dispatch = useDispatch();
  const [isLoading, setLoading] = useState(true);

  useEffect(() => {
    const dataRoutes = [
      {
        name: 'locations',
        getData: async () => {
          const { data } = await axios.get(routes.locations());
          if (!Array.isArray(data)) {
            notify.addError('Сервер не вернул список пользователей');
            dispatch(locationsActions.addLocations([]));
            return;
          }
          dispatch(locationsActions.addLocations(data));
        },
      },
    ];
    const promises = dataRoutes.map(({ getData }) => getData());
    Promise.all(promises)
      .finally(() => setLoading(false));
  });

  if (isLoading) {
    return null;
  }

  return (
    <>
      <Navbar />
      <div className="container wrapper flex-grow-1">
        <Notification />
        <h1 className="my-4">{null}</h1>
        <Switch>
          <Route exact path={routes.homePagePath()} component={Welcome} />

          <Route exact path={routes.locationsPagePath()}><LocationsComponent /></Route>

          <Route exact path={routes.locationPagePath(':locationId')}><Location /></Route>

          <Route exact path={routes.addLocationPagePath()}><NewLocation /></Route>

          <Route exact path={routes.aboutPagePath()}><AboutPage /></Route>

          <Route path="*" component={NotFoundPage} />
        </Switch>
      </div>
      <footer>
        <div className="border-top text-center bg-dark">
          <a rel="noreferrer" className="text-white" href="https://t.me/wtffka">Created by Vyacheslav Balakhonov</a>
        </div>
      </footer>
    </>
  );
};

export default App;
