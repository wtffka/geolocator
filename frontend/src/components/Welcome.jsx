// @ts-check

import React from 'react';
import { useTranslation } from 'react-i18next';
import { Card, Button } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import routes from '../routes.js';

const Welcome = () => {
  const { t } = useTranslation();
  return (
    <Card>
      <Card.Body className="p-5 bg-dark text-light">
        <div className="display-4">{t('welcome.title')}</div>
        <div className="lead">
          {t('welcome.body')}
        </div>
        <hr />
        <Button variant="primary" className="btn-lg">
          <Link className="nav-link text-white" to={routes.aboutPagePath()}>
            {t('welcome.buttonText')}
          </Link>
        </Button>
      </Card.Body>
    </Card>
  );
};

export default Welcome;
