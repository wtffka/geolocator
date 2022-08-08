// @ts-check

import React from 'react';
import { useSelector } from 'react-redux';
import { useTranslation } from 'react-i18next';
import { Button, Table } from 'react-bootstrap';
import { Link } from 'react-router-dom';

import { selectors } from '../../slices/locationsSlice.js';

import routes from '../../routes.js';

const LocationsComponent = () => {
  const { t } = useTranslation();

  const locations = useSelector(selectors.selectAll);

  if (!locations) {
    return null;
  }

  return (
    <>
      <Button variant="primary">
        <Link className="nav-link text-white" to={routes.addLocationPagePath()}>
          {t('addLocation')}
        </Link>
      </Button>
      <Table striped hover>
        <thead>
          <tr>
            <th>{t('id')}</th>
            <th>{t('coordinates')}</th>
            <th>{t('country')}</th>
            <th>{t('province')}</th>
            <th>{t('locality')}</th>
            <th>{t('street')}</th>
            <th>{t('house')}</th>
            <th>{t('otherDetails')}</th>
            <th>{null}</th>
          </tr>
        </thead>
        <tbody>
          {locations.map((location) => (
            <tr key={location.id}>
              <td>{location.id}</td>
              <td>{location.coordinates}</td>
              <td>{location.country}</td>
              <td>{location.province}</td>
              <td>{location.locality}</td>
              <td>{location.street}</td>
              <td>{location.house}</td>
              <td>{location.otherDetails}</td>
              <td>
                <Button variant="primary" className="btn-sm">
                  <Link className="nav-link text-white" to={routes.locationPagePath(location.id)}>
                    {t('locationCard')}
                  </Link>
                </Button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
    </>
  );
};

export default LocationsComponent;
