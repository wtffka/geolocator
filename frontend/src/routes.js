// @ts-check

import path from 'path';

// const hostname = 'localhost';
// const port = process.env.REACT_APP_PORT || 5001;
const apiUrl = '/v1';
const { host, protocol } = window.location;
const fullHost = `${protocol}//${host}`;

const buildUrl = (part) => () => {
  const urlPath = path.join(apiUrl, part);
  const url = new URL(urlPath, fullHost);
  return url.toString();
};

const buildLocalUrl = (part) => () => `/${part}`;

const routes = {
  homePagePath: buildLocalUrl(''),
  locationsPagePath: buildLocalUrl('v1/locations'),
  locationPagePath: (id) => `${buildLocalUrl('v1/locations')()}/${id}`,
  addLocationPagePath: () => `${buildLocalUrl('locations')()}/new`,
  aboutPagePath: buildLocalUrl('about'),

  locations: buildUrl('locations'),
  addLocation: buildUrl('locations'),
  location: (id) => `${buildUrl('locations')()}/${id}`,
};

export default routes;
